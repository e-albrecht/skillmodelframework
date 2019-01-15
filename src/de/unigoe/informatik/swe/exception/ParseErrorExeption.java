package de.unigoe.informatik.swe.exception;

public class ParseErrorExeption extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3767172901122517822L;
	private int solutionId;

	public ParseErrorExeption(String string) {
		//System.out.println(string);
	}

	public int getSolutionId() {
		return solutionId;
	}

	public void setSolutionId(int solutionId) {
		this.solutionId = solutionId;
	}
}
