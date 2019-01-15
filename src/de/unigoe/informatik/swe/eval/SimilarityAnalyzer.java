package de.unigoe.informatik.swe.eval;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections4.SetUtils;

import com.google.common.collect.Multiset;

import de.unigoe.informatik.swe.data.DBManager;
import de.unigoe.informatik.swe.data.Solution;
import de.unigoe.informatik.swe.eval.SimilarityResult.SimilarityType;
import de.unigoe.informatik.swe.krm.KRM;
import de.unigoe.informatik.swe.krm.KRMComplexNode;
import de.unigoe.informatik.swe.krm.KRMSimpleNode;
import de.unigoe.informatik.swe.krm.KAM;
import de.unigoe.informatik.swe.krm.KnowledgeComponent;
import de.unigoe.informatik.swe.utils.Utilities;

public class SimilarityAnalyzer implements Runnable{
	
	private List<Solution> solutions1;
	private List<Solution> solutions2;
	private double[][] setSimilarity;
	private double[][] pathSimilarity;  
	private double[][] pathSimilarity2;  
	private int level;
	private int exerciseNumber;
	private boolean export;
	
	public SimilarityAnalyzer(List<Solution> solutions, int level, int exerciseNumber, boolean export) {
		this(solutions, solutions,level, exerciseNumber,export);
	}

	
	public SimilarityAnalyzer(List<Solution> solutions1, List<Solution> solutions2, int level, int exerciseNumber, boolean export) {
		this.solutions1 = solutions1;
		this.solutions2 = solutions2;
		this.level = level;
		this.exerciseNumber = exerciseNumber;
		this.export = export;
	};
	
	public SimilarityAnalyzer(Solution solution, List<Solution> solutions2, int level, int exerciseNumber, boolean export) {
		List<Solution> solutions1 = new ArrayList<Solution>();
		solutions1.add(solution);
		this.solutions1 = solutions1;
		this.solutions2 = solutions2;
		this.level = level;
		this.exerciseNumber = exerciseNumber;
		this.export = export;	}
	
	public double getAverageSetSimilarity() {
		double sumAll = 0;
		double minAll = 1;
		double minAllId = 0;
		double maxAllId = 0;
		double maxAll = 0;
		for (int i = 0; i < solutions1.size(); i++) {
			double sum = 0;
			double min = 1;
			double max = 0;
			double minSol = 0;
			double maxSol = 0;
			for (int j = 0; j < solutions2.size(); j++){
				if (i!=j) {
					double sim = setSimilarity[i][j];
					sum = sum + sim;
					if (sim < min) {
						min = sim;
						minSol = solutions2.get(j).getId();
						if (min < minAll) {
							minAll = min;
							minAllId = minSol;
						}
					}
					if (sim > max) {
						max = sim;
						maxSol = solutions2.get(j).getId();
						if (max > maxAll) {
							maxAll = max;
							maxAllId = maxSol;
						}
					}
				}
			}
			sumAll += sum;
			setSimilarity[i][solutions2.size()] = sum/(solutions2.size());
			setSimilarity[i][solutions2.size()+1] = min;
			setSimilarity[i][solutions2.size()+2] = minSol;
			setSimilarity[i][solutions2.size()+3] = max;
			setSimilarity[i][solutions2.size()+4] = maxSol;
			
		}
		double avg = sumAll/(solutions1.size()*(solutions2.size()));
		return avg;
	}
	
	public double getAveragePathSimilarity() {
		double sumAll = 0;
		double minAll = 1;
		double minAllId = 0;
		double maxAllId = 0;
		double maxAll = 0;	
		for (int i = 0; i < solutions1.size(); i++) {
			double sum = 0;
			double min = Double.MAX_VALUE;
			double max = 0;
			double minSol = 0;
			double maxSol = 0;
			for (int j = 0; j < solutions2.size(); j++){
				if (i!=j) {
					double sim = pathSimilarity[i][j];
					sum = sum + sim;
					if (sim < min) {
						min = sim;
						minSol = solutions2.get(j).getId();
						if (min < minAll) {
							minAll = min;
							minAllId = minSol;
						}
					}
					if (sim > max) {
						max = sim;
						maxSol = solutions2.get(j).getId();
						if (max > maxAll) {
							maxAll = max;
							maxAllId = maxSol;
						}
					}
				}
			}
			sumAll += sum;
			pathSimilarity[i][solutions2.size()] = sum/(solutions2.size());
			pathSimilarity[i][solutions2.size()+1] = min;
			pathSimilarity[i][solutions2.size()+2] = minSol;
			pathSimilarity[i][solutions2.size()+3] = max;
			pathSimilarity[i][solutions2.size()+4] = maxSol;
			
		}
		double avg = sumAll/(solutions1.size()*(solutions2.size()));
		return avg;
	}
	
	private double[][] getSetSimilarityForSolutions(){
		int numSolutions1 = solutions1.size();
		int numSolutions2 = solutions2.size();
		double[][] sim = new double[numSolutions1][numSolutions2+5];
		DBManager dbm = DBManager.instance();
		// calculate similarity between solutions
		for (int i=0; i<numSolutions1; i++) {
			for (int j=0; j<numSolutions2;j++) {
				SimilarityResult result = dbm.getSimilarityFromDB(solutions1.get(i),solutions2.get(j), level, SimilarityType.SET);
				if (result == null) {
					KAM krm1 = (KAM) Utilities.deSerializeObject(solutions1.get(i).getKrm(level));
					KAM krm2 = (KAM) Utilities.deSerializeObject(solutions2.get(j).getKrm(level));
					double calSim = calculateSimilaritySet(krm1, krm2);
					sim[i][j] = calSim;
					result = new SimilarityResult(solutions1.get(i), solutions2.get(j), level, SimilarityType.SET, calSim);
					dbm.saveObjectToDB(result);
				} else sim[i][j] = result.getSimilarity();
			}
		}
		return sim;		
	}
	
	// using the Jaquard index
	private static double calculateSimilaritySet(KAM krm1, KAM krm2) {
		Multiset<KnowledgeComponent> set1 = KRMAnalyzer.getPathsForKAM(krm1);
		Multiset<KnowledgeComponent> set2 = KRMAnalyzer.getPathsForKAM(krm2);
		return jaccardIndex(set1.elementSet(),set2.elementSet());
	}
	
	private static double jaccardIndex(Set<KnowledgeComponent> set1, Set<KnowledgeComponent> set2) {
		Set<KnowledgeComponent> intersect = SetUtils.intersection(set1, set2);
		Set<KnowledgeComponent> union = SetUtils.union(set1, set2);
		double index = 1;
		if (intersect.size() != 0 && union.size() != 0) index = (double) intersect.size()/ (double) union.size();
		return index;
	}
	
	private double[][] getPathSimilarityForSolutions(){
		int numSolutions1 = solutions1.size();
		int numSolutions2 = solutions2.size();
		double[][] sim = new double[numSolutions1][numSolutions2+5];
		DBManager dbm = DBManager.instance();
		// calculate similarity between solutions
		for (int i=0; i<numSolutions1; i++) {
			for (int j=0; j<numSolutions2;j++) {
				SimilarityResult result = dbm.getSimilarityFromDB(solutions1.get(i),solutions2.get(j), level, SimilarityType.PATH);
				if (result == null) {
					KAM krm1 = (KAM) Utilities.deSerializeObject(solutions1.get(i).getKrm(level));
					KAM krm2 = (KAM) Utilities.deSerializeObject(solutions2.get(j).getKrm(level));
					double calSim = calculateSimilarityPath(krm1.getNodes(), krm2.getNodes());
					sim[i][j] = calSim;
					result = new SimilarityResult(solutions1.get(i), solutions2.get(j), level, SimilarityResult.SimilarityType.PATH, calSim);
					dbm.saveObjectToDB(result);
				} else sim[i][j] = result.getSimilarity();
			}
		}
		return sim;		
	}
	
	// using the Levenstein distance
	private static double calculateSimilarityPath(LinkedList<KRMSimpleNode> nodes1, LinkedList<KRMSimpleNode> nodes2) {
		int m = nodes1.size();
		int n = nodes2.size();
		double[][] d = new double[m+1][n+1];
		d[0][0] = 0;
		// initialize D
		for (int i=1;i<=m;i++) {
			d[i][0] = i;
		}
		for (int j=1; j<=n; j++) {
			d[0][j] = j;
		}
		for (int i=1; i<=m; i++) {
			for (int j=1; j<=n; j++) {
				KRMSimpleNode node1 = nodes1.get(i-1);
				KRMSimpleNode node2 = nodes2.get(j-1);
				// simple - simple
				if (node1.isSimple() && node2.isSimple()) {
					double replacement = d[i-1][j-1] + 1 - jaccardIndex(node1.getKCs(),node2.getKCs());
					double insertion = d[i][j-1] + 1;
					double deletion = d[i-1][j] + 1;
					d[i][j] = minimum(replacement,insertion,deletion);
				}
				// simple - complex
				if (node1.isSimple() && node2.isComplex()) {
					LinkedList<KRMSimpleNode> newKRM = new LinkedList<KRMSimpleNode>();
					newKRM.add(node1);
					KRM embedded = ((KRMComplexNode) node2).getEmbeddedKRM();
					double replacement = d[i-1][j-1] + 1 + calculateSimilarityPath(newKRM, embedded.getOrderedNodes());
					double insertion = d[i][j-1] + 1 + embedded.getNodes().size();
					double deletion = d[i-1][j] + 1;
					d[i][j] = minimum(replacement,insertion,deletion);
				}
				// complex - simple
				if (node1.isComplex() && node2.isSimple()) {
					LinkedList<KRMSimpleNode> newKRM = new LinkedList<KRMSimpleNode>();
					newKRM.add(node2);
					KRM embedded = ((KRMComplexNode) node1).getEmbeddedKRM();
					double replacement = d[i-1][j-1] + 1 + calculateSimilarityPath(embedded.getOrderedNodes(), newKRM);
					double insertion = d[i][j-1] + 1;
					double deletion = d[i-1][j] + 1 + embedded.getNodes().size();
					d[i][j] = minimum(replacement,insertion,deletion);
				}
				// complex - complex
				if (node1.isComplex() && node2.isComplex()) {
					KRM embedded1 = ((KRMComplexNode) node1).getEmbeddedKRM();
					KRM embedded2 = ((KRMComplexNode) node2).getEmbeddedKRM();
					int type = 1;
					if (((KRMComplexNode) node1).getType().equals(((KRMComplexNode) node2).getType())) type = 0;
					double replacement = d[i-1][j-1] + type + calculateSimilarityPath(embedded1.getOrderedNodes(), embedded2.getOrderedNodes());
					double insertion = d[i][j-1] + 1 + embedded2.getNodes().size();
					double deletion = d[i-1][j] + 1 + embedded1.getNodes().size();
					d[i][j] = minimum(replacement,insertion,deletion);
				}				
			}
		}
		return d[m][n];
	}
	
	private static double minimum(double a, double b, double c) {
        return Math.min(Math.min(a, b), c);                                      	
	}

	public double[][] getSetSimilarity() {
		return setSimilarity;
	}

	public void setSetSimilarity(double[][] setSimilarity) {
		this.setSimilarity = setSimilarity;
	}

	public double[][] getPathSimilarity() {
		return pathSimilarity;
	}

	public void setPathSimilarity(double[][] pathSimilarity) {
		this.pathSimilarity = pathSimilarity;
	}
	
	public void printToCSV(String filename) {
		PrintWriter writer;
		try {
			// print set similarity
			File file = new File(filename);
			File path = new File(file.getPath());
			if (!path.exists()) path.mkdirs();
			writer = new PrintWriter(filename + ".csv", "UTF-8");
			String line = "";
			for (Solution s : solutions2) {
				line += ";" + s.getId();
			}
			line += ";avg;min;minId;max; maxId";
			writer.println(line);
			for (int i=0; i< solutions1.size() ; i++) {
				line = "" + solutions1.get(i).getId();
				for (int j=0; j < solutions2.size()+5; j++) {
					if (i!=j) line += ";" + setSimilarity[i][j];
					else line += ";";
				}
				writer.println(line);
			}
			
			// print path similarity
			writer.println();
			line = "";
			for (Solution s : solutions2) {
				line += ";" + s.getId();
			}
			line += ";avg;min;minId;max; maxId";
			writer.println(line);
			for (int i=0; i< solutions1.size() ; i++) {
				line = "" + solutions1.get(i).getId();
				for (int j=0; j < solutions2.size()+5; j++) {
					if (i!=j) line += ";" + pathSimilarity[i][j];
					else line +=";";
				}
				writer.println(line);
			}			
			writer.close();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void exportSetSimilarity(String path, String filename) {
		FileWriter fw;
		try {
			File pathFile = new File(path);
			if (!pathFile.exists()) pathFile.mkdirs();
			fw = new FileWriter(path + "\\" + filename);
			BufferedWriter bw = new BufferedWriter(fw);
			String line ="";
			for (int i=0; i<solutions2.size();i++) {
				line += solutions2.get(i).getId() + ";";
			}
			line += "avg;min;minSol;max;maxSol";
			
			for (int i=0; i<solutions1.size();i++) {
				line = Integer.toString(solutions1.get(i).getId());
				for (int j=0; j<solutions2.size()+5; j++) {
					line += ";" + setSimilarity[i][j];
				}
				bw.write(line+"\n");
			}
			bw.close();
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void exportPathSimilarity(String path, String filename) {
		FileWriter fw;
		try {
			File pathFile = new File(path);
			if (!pathFile.exists()) pathFile.mkdirs();
			fw = new FileWriter(path + "\\" + filename);
			BufferedWriter bw = new BufferedWriter(fw);		
			String line ="";
			for (int i=0; i<solutions2.size();i++) {
				line += solutions2.get(i).getId() + ";";
			}
			line += "avg;min;minSol;max;maxSol";
			
			for (int i=0; i<solutions1.size();i++) {
				line = Integer.toString(solutions1.get(i).getId());
				for (int j=0; j<solutions2.size()+5; j++) {
					line += ";" + pathSimilarity[i][j];
				}
				bw.write(line+"\n");
			}
			bw.close();
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	}


	@Override
	public void run() {
		System.out.println("Calculating set similarity..." + " exercise " + exerciseNumber + " level: " + level);
//		setSetSimilarity(getSetSimilarityForSolutions());
		//getAverageSetSimilarity();
		System.out.println("Calculating path similarity...");
//		setPathSimilarity(getPathSimilarityForSolutions());	
		setPathSimarity2(getPathSimilarity2());
		//getAveragePathSimilarity();		
		String path = "Similarity\\Set\\Level" + level;
		String filename = "Exercise" + exerciseNumber + ".csv";
/*		if (export) exportSetSimilarity(path, filename);
		path = "Similarity\\Path\\Level" + level;                                                     
		if (export) exportPathSimilarity(path, filename);	*/
		path = "Similarity\\Path2\\Level" + level;        
		if (export) exportPathSimilarity2(path, filename);	
	}


	private void exportPathSimilarity2(String path, String filename) {
		FileWriter fw;
		try {
			File pathFile = new File(path);
			if (!pathFile.exists()) pathFile.mkdirs();
			fw = new FileWriter(path + "\\" + filename);
			BufferedWriter bw = new BufferedWriter(fw);		
			String line ="";
			for (int i=0; i<solutions2.size();i++) {
				line += solutions2.get(i).getId() + ";";
			}
			line += "avg;min;minSol;max;maxSol";
			
			for (int i=0; i<solutions1.size();i++) {
				line = Integer.toString(solutions1.get(i).getId());
				for (int j=0; j<solutions2.size()+5; j++) {
					line += ";" + pathSimilarity2[i][j];
				}
				bw.write(line+"\n");
			}
			bw.close();
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
	}


	private void setPathSimarity2(double[][] pathSimilarity2) {
		this.pathSimilarity2 = pathSimilarity2;		
	}


	private double[][] getPathSimilarity2() {
		int numSolutions1 = solutions1.size();
		int numSolutions2 = solutions2.size();
		double[][] sim = new double[numSolutions1][numSolutions2+5];
		DBManager dbm = DBManager.instance();
		// calculate similarity between solutions
		for (int i=0; i<numSolutions1; i++) {
			KAM krm1 = (KAM) Utilities.deSerializeObject(solutions1.get(i).getKrm(level));
			for (int j=0; j<numSolutions2;j++) {
				SimilarityResult result = dbm.getSimilarityFromDB(solutions1.get(i),solutions2.get(j), level, SimilarityType.PATH);
				KAM krm2 = (KAM) Utilities.deSerializeObject(solutions2.get(j).getKrm(level));
				if (result == null) {
					double calSim = calculateSimilarityPath(krm1.getNodes(), krm2.getNodes());
					sim[i][j] = calSim;
					result = new SimilarityResult(solutions1.get(i), solutions2.get(j), level, SimilarityType.PATH, calSim);
					dbm.saveObjectToDB(result);
				} else sim[i][j] = result.getSimilarity();
				int norm = Math.max(krm1.getPathSize(), krm2.getPathSize());
				sim[i][j] = 1-(sim[i][j]/norm);
			}
		}
		return sim;		
	}

}
