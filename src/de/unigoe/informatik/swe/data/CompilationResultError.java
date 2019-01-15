package de.unigoe.informatik.swe.data;

public class CompilationResultError {
	
	public enum Severity {
		error,warning
	} 
	
	private int id;
	private CompilationResult result;
	private int line;
	private int column;
	private String message;
	private Severity severity;
	
	public CompilationResultError() {
		
	}
	
	public CompilationResultError(CompilationResult result, String message){
		this.result = result;
		this.message = message;
	}
	
	public CompilationResultError(CompilationResult result, int line, String message, Severity severity){
		this.result = result;
		this.line = line;
		this.message = message;
		this.severity = severity;
	}	
	
	public CompilationResultError(CompilationResult result, int line, String message){
		this.result = result;
		this.line = line;
		this.message = message;
	}

	public CompilationResultError(CompilationResult result, int line, int column, String message, Severity severity){
		this.result = result;
		this.line = line;
		this.column = column;
		this.message = message;
		this.severity = severity;
	}
	
	public CompilationResult getResult() {
		return result;
	}

	public void setResult(CompilationResult result) {
		this.result = result;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getLine() {
		return line;
	}

	public void setLine(int line) {
		this.line = line;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public void addMessageLine(String line){
		message = message + line + "\n";
	}
	
	@Override
	public boolean equals(Object o){
		if (o instanceof CompilationResultError &&  ((CompilationResultError) o).getId() == id && ((CompilationResultError) o).getLine() == line && ((CompilationResultError) o).getMessage().equals(message)) return true;
		return false;
	}
	
	@Override
	public String toString(){
		return message;
	}
	
	@Override
	public int hashCode(){
		return id;
	}

	public Severity getSeverity() {
		return severity;
	}

	public void setSeverity(Severity severity) {
		this.severity = severity;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}
	
	
}
