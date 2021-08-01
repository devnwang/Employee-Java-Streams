package com.cognixia.jump.intermediatejava.assignments.employeejavastreams;

public class Employee {
	private int id;
	private String fName;
	private String lName;
	private double salary;
	private String dept;
	
	// Constructor
	public Employee(int id, String fName, String lName, double salary, String dept) {
		this.id = id;
		this.fName = fName;
		this.lName = lName;
		this.salary = salary;
		this.dept = dept;
	}

	// Getters and Setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public String getlName() {
		return lName;
	}

	public void setlName(String lName) {
		this.lName = lName;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}
	
}
