package de.unigoe.informatik.swe.data;

import java.util.HashSet;
import java.util.Set;

import de.unigoe.informatik.swe.data.CompilationResultError.Severity;

public class CompilationResult {
	private int id;
	private Set<CompilationResultError> errors;
	private String rawCompilationResult;
	private Solution solution;
	
	public CompilationResult(){}
	
	public CompilationResult(String rawCompilationResult, Solution solution){
		this.rawCompilationResult = rawCompilationResult;
		this.setSolution(solution);
		errors = new HashSet<CompilationResultError>();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Set<CompilationResultError> getErrors() {
		return errors;
	}

	public void setErrors(Set<CompilationResultError> errors) {
		this.errors = errors;
	}
	
	public void addError(CompilationResultError error){
		errors.add(error);
	}

	public String getRawCompilationResult() {
		return rawCompilationResult;
	}

	public void setRawCompilationResult(String rawCompilationResult) {
		this.rawCompilationResult = rawCompilationResult;
	}
	
	public void addRawMessageLine(String line){
		rawCompilationResult = rawCompilationResult + line + "\n";
	}
	
	public String getPdfString(){
		if (errors.isEmpty()) return "No errors found.";
		String header = "<table><thead><th>line</th><th>column</th><th>severity</th><th>message</th></thead>";
		String footer ="</table>";
		String result = header;
		for (CompilationResultError error : errors){
			result += "<tr><td>" + error.getLine() + "</td>";
			result += "<td>" + error.getColumn() + "</td>";
			result += "<td>" + error.getSeverity() + "</td>";
			result += "<td>" + error.getMessage() + "</td></tr>";
		}
		result += footer;
		return result;
	}
	
	@Override
	public boolean equals(Object o){
		if (o instanceof CompilationResult &&  ((CompilationResult) o).getId() == id) return true;
		return false;
	}
	
	@Override
	public String toString(){
		return rawCompilationResult;
	}
	
	@Override
	public int hashCode(){
		return id;
	}

	public Solution getSolution() {
		return solution;
	}

	public void setSolution(Solution solution) {
		this.solution = solution;
	}
	
	public boolean hasPassed() {
		for (CompilationResultError error : errors) {
			if (error.getSeverity() == null || error.getSeverity().equals(Severity.error)) return false;
		}
		return true;
	}
}
	

