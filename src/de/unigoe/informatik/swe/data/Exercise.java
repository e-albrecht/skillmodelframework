package de.unigoe.informatik.swe.data;

public class Exercise {
	public enum ProgrammingLanguage{
		Java,C
	}
	
	private int id;
	private Course course;
	private String name;
	private int number;
	private ProgrammingLanguage language;
	private String description;

	public Exercise(){}
	
	public Exercise(Course course, int number, ProgrammingLanguage language){
		this.course = course;
		this.number = number;
		this.language = language;
	}	
	
	public Exercise(Course course, String name, int number, ProgrammingLanguage language){
		this.course = course;
		this.name = name;
		this.number = number;
		this.language = language;
	}	
	
	public Exercise(Course course, String name, int number, ProgrammingLanguage language, String description){
		this.course = course;
		this.name = name;
		this.number = number;
		this.language = language;
		this.description = description;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public ProgrammingLanguage getLanguage() {
		return language;
	}

	public void setLanguage(ProgrammingLanguage language) {
		this.language = language;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
	public boolean equals(Object o){
		if (o instanceof Exercise &&  ((Exercise) o).getId() == id) return true;
		return false;
	}
	
	@Override
	public String toString(){
		if (name != null) return number + " " + name;
		return Integer.toString(number);
	}
	
	@Override
	public int hashCode(){
		return id;
	}
}
