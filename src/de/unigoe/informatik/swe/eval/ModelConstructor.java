package de.unigoe.informatik.swe.eval;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections4.SetUtils;
import org.eclipse.core.runtime.CoreException;

import com.google.common.collect.Multiset;

import de.unigoe.informatik.swe.data.DBManager;
import de.unigoe.informatik.swe.data.Exercise;
import de.unigoe.informatik.swe.data.Solution;
import de.unigoe.informatik.swe.eval.CaseStudyConfig.Incorrect;
import de.unigoe.informatik.swe.eval.CaseStudyConfig.MultipleApp;
import de.unigoe.informatik.swe.eval.CaseStudyConfig.QMatrix;
import de.unigoe.informatik.swe.eval.CaseStudyConfig.Step;
import de.unigoe.informatik.swe.eval.SimilarityResult.SimilarityType;
import de.unigoe.informatik.swe.krm.KAM;
import de.unigoe.informatik.swe.krm.KnowledgeComponent;
import de.unigoe.informatik.swe.utils.SolutionSorter;
import de.unigoe.informatik.swe.utils.Utilities;

public class ModelConstructor implements Runnable {

	private List<Solution> solutions;
	private CaseStudyConfig config;
	private List<Observation> observations;
	private Map<Integer, List<Solution>> correctSolutions;
	
	private Map<Integer, Set<KnowledgeComponent>> requiredKCs;
	
	public ModelConstructor(CaseStudyConfig config, Map<Integer, List<Solution>> correctSolutionMap) {
		this.config = config;
		requiredKCs = new HashMap<Integer, Set<KnowledgeComponent>>();
		observations = new LinkedList<Observation>();
		this.correctSolutions = correctSolutionMap;
	}
	
	@Override
	public void run() {
		System.out.println("Get solution for step: " + config.getStep());
		solutions = getSolutions(config.getStep()); // get solutions according to step definition
		Collections.sort(solutions, new SolutionSorter()); // sort solutions by students and solution id
		System.out.println("Create observations...");
		createObservations(solutions);
		System.out.println("Export data...");
		exportDataToFile("Dataset/" + config.getMapp() + "_" + config.getLevel() + "_" + config.getMinSteps() + "_" + config.getQm()
							+ "_" + config.getInc() + "_" + config.getStep() + ".csv");
	}
	
	private void createObservations(List<Solution> solutions) {
		int lastStudent = 0;
		int step=0;
		Observation nextObservation = null;
		for (Solution s : solutions) {
			Observation obs;

			if (s.getStudentId() == lastStudent) { // next step of a previous student
				obs = nextObservation;
				step++;
			} else { // first entry for the student
				obs = new Observation(s.getStudentId());
				step=0;
			}
			Set<KnowledgeComponent> reqKCs = getRequiredKCs(s);
			obs.addKcsRequired(reqKCs);
			obs.setResult(s.isCorrect());	
			if (step >= config.getMinSteps()) observations.add(obs);
			lastStudent = s.getStudentId();
			nextObservation = new Observation(lastStudent);
			nextObservation.setKcSuccess(obs.getKcSuccess()); // input previous values for success
			nextObservation.setKcFailure(obs.getKcFailure()); // input previous values for failure		
			updateSuccessAndFailures(nextObservation,s);
		}
	}
	
	private void updateSuccessAndFailures(Observation obs, Solution s) {
		if (s.isCorrect()==1) updateSuccess(obs, s);
		else updateFailure(obs,s);
	}
	
	public void updateSuccess(Observation obs, Solution s) {
		try {
		Object k = s.getKrm(config.getLevel());
		if (k == null) {
			KRMConstructor krmC = new KRMConstructor();
			krmC.buildKAMToSolution(config.getLevel(), s);
		}
		Object o = Utilities.deSerializeObject(s.getKrm(config.getLevel()));
		KAM krm = (KAM) o;
		if (config.getMapp().equals(MultipleApp.SINGLE)) {
			for (KnowledgeComponent kc : krm.getKCs()) {
				obs.updateKcSuccess(kc, 1); // add +1 for each KC used in the solution
			}			
		} else {
			Multiset<KnowledgeComponent> kcs = KRMAnalyzer.getPathsForKAM(krm);
			for (KnowledgeComponent kc : kcs.elementSet()) {
				obs.updateKcSuccess(kc, kcs.count(kc)); // add number of occurences in the solution for each KC
			}
		}} catch (CoreException e) {
			System.err.println("Could not create KC-AST to solution " + s.getId());
			e.printStackTrace();
		}
	}
	
	public void updateFailure(Observation obs, Solution s) {
		try {
		Object k = s.getKrm(config.getLevel());
		if (k == null) {
			KRMConstructor krmC = new KRMConstructor();
			krmC.buildKAMToSolution(config.getLevel(), s);
		}
		Object o = Utilities.deSerializeObject(s.getKrm(config.getLevel()));
		KAM krm = (KAM) o;
		if (config.getInc().equals(Incorrect.ALL)) {
			if (config.getMapp().equals(MultipleApp.SINGLE)) {
				for (KnowledgeComponent kc : krm.getKCs()) {
					obs.updateKcFailure(kc, 1); // add +1 for each KC used in the solution
				}			
			} else {
				Multiset<KnowledgeComponent> kcs = KRMAnalyzer.getPathsForKAM(krm);
				for (KnowledgeComponent kc : kcs.elementSet()) {
					obs.updateKcFailure(kc, kcs.count(kc)); // add number of occurences in the solution for each KC
				}
			}
		} else { // KCs which differ from most similar correct solution are assumed wrong
			 DBManager dbm = DBManager.instance();
			 List<Solution> simSols = dbm.getMostSimilarSolutions(s, config.getLevel(), SimilarityType.PATH);		
			 if (simSols.isEmpty()) {
				 SimilarityAnalyzer simAnaly = new SimilarityAnalyzer(s, correctSolutions.get(s.getExercise().getNumber()), config.getLevel(), s.getExercise().getId(), false);
				 simAnaly.run();
				 simSols = dbm.getMostSimilarSolutions(s, config.getLevel(), SimilarityType.PATH);
			 }
			 Object o2 = Utilities.deSerializeObject(simSols.get(0).getKrm(config.getLevel()));
			 KAM krmCorrect = (KAM) o2;
			
			 if (config.getMapp().equals(MultipleApp.SINGLE)) {
				 Set<KnowledgeComponent> kcsThis = krm.getKCs();
				 Set<KnowledgeComponent> kcsCorr = krmCorrect.getKCs();
				 Set<KnowledgeComponent> correctKcs = SetUtils.intersection(kcsThis, kcsCorr).toSet();
				 Set<KnowledgeComponent> incorrectKcs = SetUtils.union(kcsThis, kcsCorr).toSet();
				 incorrectKcs.removeAll(correctKcs);
					for (KnowledgeComponent kc : incorrectKcs) {
						obs.updateKcFailure(kc, 1); // add +1 for each KC different from the most similar solution
					}					 
			 } else {
				 Multiset<KnowledgeComponent> kcsThis = KRMAnalyzer.getPathsForKAM(krm);
				 Multiset<KnowledgeComponent> kcsCorr = KRMAnalyzer.getPathsForKAM(krmCorrect);
				 for (KnowledgeComponent kc : KnowledgeComponent.values()) {
					 int num = kcsThis.count(kc);
					 int numCorr = kcsCorr.count(kc);
					 obs.updateKcFailure(kc, Math.abs(numCorr-num));
				 }				 
			 }
		}	
		} catch (CoreException e) {
			System.err.println("Could not create KC-AST to solution " + s.getId());
			e.printStackTrace();
		}
	}
	
	private List<Solution> getSolutions(Step step) {
		DBManager dbm = DBManager.instance();
		List<Exercise> exercises = dbm.getExercisesToCourse(config.getCourse());
		List<Solution> allSolutions = new LinkedList<Solution>();
		for (Exercise e : exercises)
		allSolutions.addAll(dbm.getCompilableSolutionsToExercise(e.getId()));

		switch(step) {
		case ALL: return allSolutions;
		case FIRST: return getFirstSubmissions(allSolutions);
		case LAST: return getLastSubmissions(allSolutions);
		default: return null;
		}
	}
	
	private List<Solution> getLastSubmissions(List<Solution> allSolutions) {
		List<Solution> lastSolutions = new LinkedList<Solution>();
		// Maps exercise id -> student id -> last submission number
		HashMap<Integer, HashMap<Integer,Integer>> studentLastSubmission = new HashMap<Integer, HashMap<Integer,Integer>>();
		DBManager dbm = DBManager.instance();
		for (Solution s : allSolutions) {
			int exerciseId = s.getExercise().getId();
			int subNumber = 0;
			int studentId = s.getStudentId();
			if (!studentLastSubmission.containsKey(exerciseId)) { // no entry for exercise
				HashMap<Integer, Integer> lastSubmission = new HashMap<Integer,Integer>();
				subNumber = dbm.getNumberOfSubmissions(studentId, exerciseId);
				lastSubmission.put(studentId, subNumber);
			}
			else {
				HashMap<Integer, Integer> lastSubmission = studentLastSubmission.get(exerciseId);
				if (!lastSubmission.containsKey(studentId)) { // no entry for student
					subNumber = dbm.getNumberOfSubmissions(studentId, exerciseId);
					lastSubmission.put(studentId, subNumber);
				}
				else subNumber = lastSubmission.get(studentId);
			} 
			if (subNumber == s.getTryNumber()) lastSolutions.add(s);
		}	
		return lastSolutions;
	}

	private List<Solution> getFirstSubmissions(List<Solution> allSolutions) {
		List<Solution> solutions = new LinkedList<Solution>();
		for (Solution s : allSolutions) {
			if (s.getTryNumber() == 1) solutions.add(s);
		}
		return solutions;
	}

	 private Set<KnowledgeComponent> getRequiredKCs(Solution s) {
		 int exercise = s.getExercise().getId();
		 Set<KnowledgeComponent> reqKCs = requiredKCs.get(exercise);
		 if (reqKCs != null) return reqKCs;
		 
		 QMatrix type = config.getQm();
		 int level = config.getLevel();
		 switch (type) {
		 case ALL: reqKCs = KnowledgeComponent.getKCs(level); break;
		 case SHARED: reqKCs = getSharedKCs(exercise,level); break;
		 case COMMON: reqKCs = getCommonKCs(exercise,level); break;
		 case PATH: return getSetKCs(s,level);
		 case SET: return getPathKCs(s,level);
		 case UNION: reqKCs = getUnionKCs(exercise,level);
		 }
		 requiredKCs.put(exercise, reqKCs);
		 return reqKCs;
	 }
	 
	 private Set<KnowledgeComponent> getSetKCs(Solution solution, int level) {
		 DBManager dbm = DBManager.instance();
		 List<Solution> simSols = dbm.getMostSimilarSolutions(solution, level, SimilarityType.SET);		 
		 if (simSols.isEmpty()) {
			 SimilarityAnalyzer simAnaly = new SimilarityAnalyzer(solution, correctSolutions.get(solution.getExercise().getNumber()), config.getLevel(), solution.getExercise().getId(), false);
			 simAnaly.run();
			 simSols = dbm.getMostSimilarSolutions(solution, config.getLevel(), SimilarityType.SET);
		 }
		 Object o2 = Utilities.deSerializeObject(simSols.get(0).getKrm(level));
		 KAM krmCorrect = (KAM) o2;		 
		 return krmCorrect.getKCs();
	 }
	 
	 private Set<KnowledgeComponent> getPathKCs(Solution solution, int level) {
		 DBManager dbm = DBManager.instance();
		 List<Solution> simSols = dbm.getMostSimilarSolutions(solution, level, SimilarityType.PATH);
		 if (simSols.isEmpty()) {
			 SimilarityAnalyzer simAnaly = new SimilarityAnalyzer(solution, correctSolutions.get(solution.getExercise().getNumber()), config.getLevel(), solution.getExercise().getId(), false);
			 simAnaly.run();
			 simSols = dbm.getMostSimilarSolutions(solution, config.getLevel(), SimilarityType.PATH);
		 }
		 Object o2 = Utilities.deSerializeObject(simSols.get(0).getKrm(level));
		 KAM krmCorrect = (KAM) o2;		 
		 return krmCorrect.getKCs();
	 }
	 
	 private Set<KnowledgeComponent> getCommonKCs(int exercise, int level) {
		
		Set<KAM> krms = new HashSet<KAM>();
		for (Solution solution : getCorrectSolutions(exercise)) {
			krms.add((KAM) Utilities.deSerializeObject(solution.getKrm(level)));
		}
		KRMConstructor constr = new KRMConstructor();
		return KRMAnalyzer.getMostCommonPath(constr.buildKRM(krms, level));
	 }
	 
	 private Set<KnowledgeComponent> getSharedKCs(int exercise, int level) {
		Set<Multiset<KnowledgeComponent>> paths = new HashSet<Multiset<KnowledgeComponent>>();
		for (Solution solution : getCorrectSolutions(exercise)) {
			Multiset<KnowledgeComponent> path = KRMAnalyzer.getPathsForKAM((KAM) Utilities.deSerializeObject(solution.getKrm(level)));
			paths.add(path);
		}
		return KRMAnalyzer.necessaryKCs(paths);
	 }
	 
	 private Set<KnowledgeComponent> getUnionKCs(int exercise, int level) {
		Set<KnowledgeComponent> kcs = new HashSet<KnowledgeComponent>();
		for (Solution solution : getCorrectSolutions(exercise)) {
			Multiset<KnowledgeComponent> path = KRMAnalyzer.getPathsForKAM((KAM) Utilities.deSerializeObject(solution.getKrm(level)));
			kcs.addAll(path.elementSet());
		}
		return kcs;
	 }
	   
	 private Set<Solution> getCorrectSolutions(int exercise) {
		 Set<Solution> correctSolutions = new HashSet<Solution>();
		 for (Solution s : solutions) {
			 if (s.isCorrect()==1 && s.getExercise().getId() == exercise) correctSolutions.add(s);
		 }
		 return correctSolutions;
	 }
	 
		public void exportDataToFile(String filename) {
			 try {
				FileWriter fw = new FileWriter(filename);
				BufferedWriter bw = new BufferedWriter(fw);
				
				// Print header
				String line ="student;";
				for (KnowledgeComponent kc : KnowledgeComponent.getKCs(config.getLevel())) {
					line += kc + "_req;" + kc +"_succ;" + kc + "_fail;"; 
				}
				line += "obs\n";
				bw.write(line);
				
				for (Observation obs : observations) {
					line = "" + obs.getStudentId();
					for (KnowledgeComponent kc : KnowledgeComponent.getKCs(config.getLevel())) {
						int req = obs.getKcRequired().get(kc);
						int succ = obs.getKcSuccess().get(kc);
						int fail = obs.getKcFailure().get(kc);
						line += ";" + req + ";" + succ + ";" + fail; 
					}
					line += ";" + obs.getResult() + "\n";
					bw.write(line);					
				}
				
				bw.close();
				fw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
		 }


}
