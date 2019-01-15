package de.unigoe.informatik.swe.data;

public class Solution {
	
	private int id;
	private int studentId;
	private Exercise exercise;
	private int tryNumber;
	private int resultPercentage;
	private String sourceCode;
	private byte[][] krm;
	
	public Solution(){
		this.resultPercentage = 0;
		setSourceCode("");
		krm = new byte[4][];
	}
	
	public Solution(int studentId, Exercise exercise, int tryNumber){
		this.studentId = studentId;
		this.exercise = exercise;
		this.tryNumber = tryNumber;
		this.resultPercentage = 0;
		setSourceCode("");
		krm = new byte[4][];
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public Exercise getExercise() {
		return exercise;
	}

	public void setExercise(Exercise exercise) {
		this.exercise = exercise;
	}

	public int getTryNumber() {
		return tryNumber;
	}

	public void setTryNumber(int tryNumber) {
		this.tryNumber = tryNumber;
	}
	
	@Override
	public boolean equals(Object o){
		if (o instanceof Solution &&  ((Solution) o).getId() == id) return true;
		return false;
	}
	
	public int isCorrect()  {
		if (resultPercentage == 100) return 1;
		else return 0;
	}
	
	@Override
	public String toString(){
		return Integer.toString(id);
	}
	
	@Override
	public int hashCode(){
		return id;
	}

	public int getResultPercentage() {
		return resultPercentage;
	}

	public void setResultPercentage(int resultPercentage) {
		this.resultPercentage = resultPercentage;
	}

	public String getSourceCode() {
		return sourceCode;
	}

	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
	}

	public byte[] getKrm(int level) {
		if (krm!=null) return krm[level];
		return null;
	}

	public void setKrm(byte[] krm,int level) {
		if (this.krm == null) {
			this.krm = new byte[4][];
		}
		this.krm[level] = krm;
	}
	
	public byte[][] getKrm() {
		return krm;
	}
	
	public void setKrm(byte[][] krm) {
		this.krm = krm;
	}
}
