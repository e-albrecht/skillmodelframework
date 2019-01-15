package de.unigoe.informatik.swe.data;

public class Course {
	private int id;
	private String name;
	private String year;
	
	public Course() {}
	
	public Course(String name){
		this.name = name;
		this.year = "";
	}
	
	public Course(String name, String year){
		this.name = name;
		this.year = year;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}
	
	@Override
	public boolean equals(Object o){
		if (o instanceof Course &&  ((Course) o).getId() == id) return true;
		return false;
	}
	
	@Override
	public String toString(){
		return name + " " + year;
	}
	
	@Override
	public int hashCode(){
		return id;
	}
}
