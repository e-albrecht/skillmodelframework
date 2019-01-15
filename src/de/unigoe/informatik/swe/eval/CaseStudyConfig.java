package de.unigoe.informatik.swe.eval;

import org.kohsuke.args4j.Option;

/**
 * Configuration for running case studies, loaded from command line arguments
 * @author ella
 *
 */
public class CaseStudyConfig {
	public enum Step {FIRST, LAST, ALL, EVERY}
	public enum QMatrix {ALL, SHARED, COMMON, UNION, SET, PATH, EVERY}
	public enum Incorrect {ALL, DIFF, EVERY}
	public enum MultipleApp {SINGLE, MULTIPLE, EVERY}
	
	
	
	@Option(name="-s", aliases="--step", usage="Step definition. Allowed options: FIRST, LAST, EVERY")
	private Step step=Step.EVERY;
	
	@Option(name="-q", aliases="--qmatrix", usage="Q-Matrix selection strategy. Allowed options: ALL, SHARED, COMMON, UNION, SET, PATH, EVERY")
	private QMatrix qm=QMatrix.EVERY;
	
	@Option(name="-i", aliases="--incorrect", usage="Defines how incorrect KCs are identified. Allowed options: ALL, DIFF, EVERY")
	private Incorrect inc=Incorrect.EVERY;	
	
	@Option(name="-m", aliases="--multiapp", usage="Counting of KCs. Allowed options: SINGLE, MULTIPLE, EVERY")
	private MultipleApp mapp=MultipleApp.EVERY;
	
	@Option(name="-c", aliases="--course", usage="The course for which the data set should be constructed", required=true)
	private int course;
	
	@Option(name="--old", usage ="The course which shall be used as reference for similarity analysis")
	private int oldCourse = course;
	
	@Option(name="-krm", usage="Should KRMs be constructed? Allowed options: true, false. Default: false")
	private boolean krm;
	
	@Option(name="-sim", usage="Should similarities be calculated? Allowed options: true, false. Default: false")
	private boolean sim;
	
	@Option(name="-l", aliases="--level", usage="Level of KCs. Allowed Option 0-3. Default: all")
	private int level = 4;
	
	@Option(name="-ms", usage="Minimum step size. -1 = every")
	private int minSteps = -1;

	@Option(name="--export", usage="Export KRMs and similarities to file")
	private boolean export;
	
	@Option(name="-t", usage="Number of threads. Default: 1")
	private int numThreads = 1;
	
	public int getNumThreads() {
		return numThreads;
	}
	
	public boolean isExport() {
		return export;
	}
	
	public Step getStep() {
		return step;
	}

	public QMatrix getQm() {
		return qm;
	}

	public Incorrect getInc() {
		return inc;
	}

	public MultipleApp getMapp() {
		return mapp;
	}

	public int getCourse() {
		return course;
	}

	public int getOldCourse() {
		return oldCourse;
	}

	public boolean isKrm() {
		return krm;
	}

	public boolean isSim() {
		return sim;
	}

	public int getLevel() {
		return level;
	}
	
	public void setLevel(int level) {
		this.level = level;
	}
	
	public void setStep(Step step) {
		this.step = step;
	}
	
	public void setQMatrix(QMatrix qm) {
		this.qm = qm;
	}
	
	public void setIncorrect(Incorrect inc) {
		this.inc = inc;
	}
	
	public void setMultiApp(MultipleApp mapp) {
		this.mapp = mapp;
	}
	
	public void setCourse(int course) {
		this.course = course;
	}
	
	public void setMinSteps(int minSteps) {
		this.minSteps = minSteps;
	}
	
	public int getMinSteps() {
		return minSteps;
	}
	
}
