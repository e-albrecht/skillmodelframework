package de.unigoe.informatik.swe.eval;

import de.unigoe.informatik.swe.data.Solution;


public class SimilarityResult {

	public enum SimilarityType {SET, PATH};
	
	private int id;
	private Solution solution1;
	private Solution solution2;
	private int level;
	private SimilarityType type;
	private double similarity;
	
	public SimilarityResult() { }
	
	public SimilarityResult(Solution solution1, Solution solution2, int level, SimilarityType type, double similarity) {
		this.solution1 = solution1;
		this.solution2 = solution2;
		this.level = level;
		this.type = type;
		this.similarity = similarity;
	}
	
	public Solution getSolution1() {
		return solution1;
	}
	public void setSolution1(Solution solution1) {
		this.solution1 = solution1;
	}
	public Solution getSolution2() {
		return solution2;
	}
	public void setSolution2(Solution solution2) {
		this.solution2 = solution2;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public SimilarityType getType() {
		return type;
	}
	public void setType(SimilarityType type) {
		this.type = type;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public double getSimilarity() {
		return similarity;
	}
	public void setSimilarity(double similarity) {
		this.similarity = similarity;
	}
	
	public boolean equals(Object o) {
		if (o instanceof SimilarityResult) {
			SimilarityResult result = (SimilarityResult) o;
			return (solution1.equals(result.getSolution1()) && solution2.equals(result.getSolution2()) && type.equals(result.getType()) && level == result.getLevel());
		} 
		return false;
	}
	
	public int hashCode() {
		return solution1.hashCode() + solution2.hashCode() + type.hashCode() + level;
	}
	
	
	
}
