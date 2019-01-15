package de.unigoe.informatik.swe.eval;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.eclipse.core.runtime.CoreException;

import de.unigoe.informatik.swe.data.DBManager;
import de.unigoe.informatik.swe.data.Exercise;
import de.unigoe.informatik.swe.data.Solution;
import de.unigoe.informatik.swe.eval.CaseStudyConfig.Incorrect;
import de.unigoe.informatik.swe.eval.CaseStudyConfig.MultipleApp;
import de.unigoe.informatik.swe.eval.CaseStudyConfig.QMatrix;
import de.unigoe.informatik.swe.eval.CaseStudyConfig.Step;
import de.unigoe.informatik.swe.krm.KRM;
import de.unigoe.informatik.swe.krm.KAM;
import de.unigoe.informatik.swe.utils.Utilities;

public class CaseStudyRunner {

	/**
	 * Map exercise number -> list of correct solutions
	 */
	private Map<Integer,List<Solution>> correctSolutionMap;
	
	/**
	 * Map exercise number -> list of incorrect solutions
	 */
	private Map<Integer, List<Solution>> incorrectSolutionMap;
	
	/**
	 * Map exercise number -> list of correct solutions old course
	 */
	private Map<Integer, List<Solution>> oldCorrectSolutionMap;
	
	private List<Exercise> exercises;
	
	/**
	 * Configuration for running the case study
	 */
	private CaseStudyConfig config;
	
	public CaseStudyRunner(CaseStudyConfig config) {
		this.config = config;
		correctSolutionMap = new HashMap<Integer, List<Solution>>();
		incorrectSolutionMap = new HashMap<Integer, List<Solution>>();
		oldCorrectSolutionMap = new HashMap<Integer, List<Solution>>();	
		exercises = new LinkedList<Exercise>();
	}
	
	public void run() {

		System.out.println("Get exercises...");
		getExercises();
		System.out.println("Construct KRMs...");
		if (config.isKrm()) {
			constructKRMs();
			if (config.isExport()) exportKRMs();	
		}
		System.out.println("Load correct solutions...");
		findOldCorrectSolutions(); // load correct solutions from previous course
		findLastSolutions(); // get correct and incorrect solutions at last attempt
		if (config.isSim()) calculateSimilarity();
		System.out.println("Start constructing model");
		constructModel();
	}
	
	private void constructModel() {
		ExecutorService executor = Executors.newFixedThreadPool(config.getNumThreads()); // Initialize executor for parallel execution	
		if (config.getLevel() == 4) {
			for (int i=0; i<4; i++) {
				constructModel(i,executor);
			}
		} else constructModel(config.getLevel(),executor);
		executor.shutdown();
		try {
			executor.awaitTermination(2, TimeUnit.DAYS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
	}
	
	private void constructModel(int level, ExecutorService executor) {
		if (config.getStep() == Step.EVERY) {
			for (Step step : Step.values()) { // calculate for every step definition
				if (step != Step.EVERY) constructModel(level,step,executor);
			}
		} else constructModel(level, config.getStep(),executor);
	}
	
	private void constructModel(int level, Step step, ExecutorService executor) {
		if (config.getQm() == QMatrix.EVERY) {
			for (QMatrix qm : QMatrix.values()) { // calculate for every Qmatrix definition
				if (qm != QMatrix.EVERY) constructModel(level,step,qm,executor);
			}
		}	
		else constructModel(level, step, config.getQm(), executor);
	}
	
	private void constructModel(int level, Step step, QMatrix qm, ExecutorService executor) {
		if (config.getInc() == Incorrect.EVERY) {
			for (Incorrect inc : Incorrect.values()) { // calculate for every incorrect definition
				if (inc != Incorrect.EVERY) constructModel(level,step,qm,inc,executor);
			}
		}	
		else constructModel(level, step, qm, config.getInc(), executor);
	}
	
	private void constructModel(int level, Step step, QMatrix qm, Incorrect inc, ExecutorService executor) {
		if (config.getMapp() == MultipleApp.EVERY) {
			for (MultipleApp mapp : MultipleApp.values()) { // calculate for every multiple application definition
				if (mapp != MultipleApp.EVERY) constructModel(level,step,qm,inc,mapp,executor);
			}
		}	
		else constructModel(level, step, qm, inc, config.getMapp(), executor);
	}
	
	private void constructModel(int level, Step step, QMatrix qm, Incorrect inc, MultipleApp mapp, ExecutorService executor) {
		if (config.getMinSteps() < 0) {
			for (int minSteps = 0; minSteps <= 20; minSteps = minSteps + 5) {
				constructModel(level, step, qm, inc, mapp, minSteps, executor);
			}
		} else constructModel(level, step, qm, inc, mapp, config.getMinSteps(), executor);
	}

	private void constructModel(int level, Step step, QMatrix qm, Incorrect inc, MultipleApp mapp, int minSteps, ExecutorService executor) {
		CaseStudyConfig newConfig = new CaseStudyConfig();
		newConfig.setLevel(level);
		newConfig.setIncorrect(inc);
		newConfig.setMultiApp(mapp);
		newConfig.setQMatrix(qm);
		newConfig.setStep(step);
		newConfig.setCourse(config.getCourse());
		newConfig.setMinSteps(minSteps);
		ModelConstructor mc = new ModelConstructor(newConfig,correctSolutionMap);
		executor.execute(mc);																								
	}

	private void getExercises() {
		DBManager dbm = DBManager.instance();
		exercises = dbm.getExercisesToCourse(config.getCourse());
	}

	private void findOldCorrectSolutions() {
		DBManager dbm = DBManager.instance();
		for (Exercise ex : exercises) {
			// get correct solutions
			List<Solution>  correctSolutions = dbm.getCorrectSolutionsToExercise(ex.getId());
			correctSolutions = getLastSubmissions(ex, correctSolutions);
			oldCorrectSolutionMap.put(ex.getNumber(), correctSolutions);
		}
	}
	
	private void findLastSolutions() {
		DBManager dbm = DBManager.instance();
		for (Exercise ex : exercises) {
			// get correct solutions
			List<Solution>  correctSolutions = dbm.getCorrectSolutionsToExercise(ex.getId());
			correctSolutions = getLastSubmissions(ex, correctSolutions);
			correctSolutionMap.put(ex.getNumber(), correctSolutions);
			// get incorrect solutions
			Set<Solution> compilableSolutions = dbm.getCompilableSolutionsToExercise(ex.getId());
			compilableSolutions.removeAll(correctSolutions);
			List<Solution> incorrectSolutions = new LinkedList<Solution>(compilableSolutions);
			incorrectSolutions = getLastSubmissions(ex, incorrectSolutions);
			incorrectSolutionMap.put(ex.getNumber(), incorrectSolutions);			
		}	
	}
	
	private static List<Solution> getLastSubmissions(Exercise exercise, List<Solution> solutions) {
		List<Solution> lastSolutions = new LinkedList<Solution>();
		HashMap<Integer,Integer> studentLastSubmission = new HashMap<Integer,Integer>();
		DBManager dbm = DBManager.instance();
		for (Solution s : solutions) {
			int subNumber = 0;
			int studentId = s.getStudentId();
			if (!studentLastSubmission.containsKey(studentId)) {
				subNumber = dbm.getNumberOfSubmissions(studentId, exercise.getId());
				studentLastSubmission.put(studentId, subNumber);
			} else subNumber = studentLastSubmission.get(studentId);
			if (subNumber == s.getTryNumber()) lastSolutions.add(s);
		}	
		return lastSolutions;
	}
	
	private void constructKRMs() {
		int level = config.getLevel();
		if (level==4) { // construct KRMs for all levels
			for (int i=0; i<4; i++) {
				constructKRMsForLevel(exercises,i);
			}
		} else constructKRMsForLevel(exercises, level);
	}
	
	private void constructKRMsForLevel(List<Exercise> exercises, int level) {
		System.out.println("Construct KRMs");
		KRMConstructor krmC = new KRMConstructor();
		DBManager dbm = DBManager.instance();
		
		for (Exercise exer : exercises) {
			int exerciseNumber = exer.getNumber();
			System.out.println("Construct KRMs solutions level=" + level +" exercise=" + exerciseNumber);
			for (Solution s : dbm.getCompilableSolutionsToExercise(exer.getId())) {
			// construct KRMs for correct solutions
			try {
				krmC.buildKAMToSolution(level, s);
			} catch (CoreException e) {
				System.err.println("Could not create KC-AST to solution " + s.getId());
				e.printStackTrace();
			}
			}
		}
	}
	
	private void exportKRMs() {
		for (Entry<Integer, List<Solution>> entry : correctSolutionMap.entrySet() ) {
			int number = entry.getKey();
			List<Solution> solutions = entry.getValue();
			List<KRM> krmSet = new LinkedList<KRM>();
			
			// construct KRM from single KRMs
			if (config.getLevel() == 4) {
				for (int level = 0; level < 4; level++) {
					KRM krm = new KRM(level);
					for (Solution sol : solutions) {
						krm.addKRMSingle(((KAM)Utilities.deSerializeObject(sol.getKrm(level))).createKRM(), sol.getId());
					}
					krmSet.add(krm);
				}
			} else {
				KRM krm = new KRM(config.getLevel());
				for (Solution sol : solutions) {
					krm.addKRMSingle(((KAM)Utilities.deSerializeObject(sol.getKrm(config.getLevel()))).createKRM(), sol.getId());
				}
				krmSet.add(krm);				
			}
			
			// print KRMs
			for (KRM krm : krmSet) {
				KRMAnalyzer krmA = new KRMAnalyzer(krm);
				System.out.println("Exercise: " + number + " Level: " + krm.getLevel() + " Number solutions: " + solutions.size() +" Number paths: " + krmA.getPaths().size());
				try {
					File path = new File("KRMs\\Level" + krm.getLevel());
					if (!path.exists()) path.mkdirs();
					FileWriter fw = new FileWriter("KRMs\\Level" + krm.getLevel() + "\\exercise" + number +".graphml");
					BufferedWriter bw = new BufferedWriter(fw);
					bw.write(krm.generateGraphML());
					bw.close();
					fw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	private void calculateSimilarity() {
		ExecutorService executor = Executors.newFixedThreadPool(8); // Initialize executor for parallel execution	
		int level = config.getLevel();
		if (level==4) { // construct KRMs for all levels
			for (int i=0; i<4; i++) {
				calculateSimilarityForLevel(executor,i);
			}
		} else calculateSimilarityForLevel(executor, level);		
		executor.shutdown();
		try {
			executor.awaitTermination(1, TimeUnit.DAYS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	private void calculateSimilarityForLevel(ExecutorService executor, int level) {
		for(Exercise ex : exercises) {
			int exNumber = ex.getNumber();
			List<Solution> correctSolutions = correctSolutionMap.get(exNumber);
			// Similarity between correct solutions
			SimilarityAnalyzer simAnalyCorr = new SimilarityAnalyzer(correctSolutions,correctSolutions, level, exNumber,true);
			executor.execute(simAnalyCorr);			
			// Similarity between incorrect and correct solutions
			Set<Solution> compilableSolutions = DBManager.instance().getCompilableSolutionsToExercise(ex.getId());
			List<Solution> incorrectSolutions = new LinkedList<Solution>();
			for (Solution s : compilableSolutions) {
				if (s.isCorrect() == 1) incorrectSolutions.add(s);
			}
			
			SimilarityAnalyzer simAnalyIncorr = new SimilarityAnalyzer(incorrectSolutions,correctSolutions, level, exNumber,false);
			executor.execute(simAnalyIncorr);				
		}
	}
	
}
