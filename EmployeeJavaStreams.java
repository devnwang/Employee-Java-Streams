package com.cognixia.jump.intermediatejava.assignments.employeejavastreams;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.Scanner;

/*
 * Using a list of employees (storing id, name, salary, department) implement Java Streams
 * to accomplish the listed objects.
 * 
 * Objectives
 * ---------------------------------
 * 1. Find all employees who have a salary of at least 90,000
 * 2. Find the top paid employee in the Developer department
 * 3. Sort the employees in descending order by their name
 * 4. Sort the employees in descending order by their department
 * 5. Get the lowest paid employee in the Front End Development department
 * 6. Get the sum of all the salaries of the employees
 * 7. Get the average salary of the employees in the Developer department
 */
public class EmployeeJavaStreams {

	public static void main(String[] args) {
		
		boolean done = false;
		Scanner sc = new Scanner(System.in);
		int opt;
		
		// Read from a file the employee information
		File employeeDataFile = new File("resources/assignments/employees.txt");
		
		// Import the employee data
		ArrayList<Employee> employees = new ArrayList<Employee>();
		
		try (BufferedReader bufferedReader = new BufferedReader(new FileReader(employeeDataFile))) {
			
			String dataEntry;
			
			// while the file has data
			while((dataEntry = bufferedReader.readLine()) != null) {
				// Parse the string to get arguments for object constructor
				// Index : Value
				// 0		id
				// 1		fName
				// 2		lName
				// 3		dept
				// 4		salary
				String[] dataFields = dataEntry.split(",");
				
				// if the number of fields don't match the required number of fields
				if (dataFields.length != DataEntryException.expectedFieldCnt) {
					throw new DataEntryException(dataFields.length);
				}
				
				// Attempt to append employee objects to the ArrayList
				try {
					// Employee(int id, String fName, String lName, double salary, String dept)
					employees.add(new Employee(Integer.parseInt(dataFields[0]), dataFields[1], dataFields[2],
							Double.parseDouble(dataFields[4]), dataFields[3]));
				} catch (NumberFormatException e) {
					throw new DataEntryException(e);
				}
			}
			
		} catch (FileNotFoundException e) {
			System.out.println("Import file not found!");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DataEntryException e) {
			System.out.println(e.getMessage());
		}
		
		// Successfully imported data message
		System.out.println("SUCCESSFULLY imported employee data.\n");
		
		// Accomplish the listed objectives
		while (!done) {
			// Retrieve validated selection
			opt = getValidatedInput(sc, displayOptions(), 8);
			
			switch(opt) {
			
				// All employees who have a salary >= 90k
				case 1:
					System.out.println("Employees with a salary of at least $90,000:");
					
					employees.stream()
						// Filters for the employees with salaries >= 90k
						.filter(e -> e.getSalary() >= 90000)
						
						// Prints them out
						.forEach(System.out::println);
					break;
				
				// Find top paid employee in the Developer department
				case 2:
					System.out.println("The top paid employee in the Developer department:");
					
					Employee topEmp = employees.stream()
						// Filter for employees in the Developer department (Front + Back End Dev)
						.filter(e -> e.getDept().matches("^(Front|Back) End Development$"))
						
						// Find the employee with the highest salary
						.reduce((a,b) -> a.getSalary() > b.getSalary() ? a : b)
						.get();
					
					System.out.println(topEmp);
					
					break;
				
				// Sort employees in descending order by their name (assume first name)
				case 3:
					System.out.println("Employees sorted by their first name:");
					
					employees.stream()
						// sort based on employee first name
						.sorted(Comparator.comparing(Employee::getfName))
						
						// print each employee
						.forEach(System.out::println);
					break;
				
				// Sort employee in descending order by their department
				case 4:
					System.out.println("Employees sorted by their department:");
					
					employees.stream()
						// Sort employees based on department
						.sorted(Comparator.comparing(Employee::getDept))
						
						// Print each employee
						.forEach(System.out::println);
					break;
				
				// Get lowest paid employee in the Front End Development department
				case 5:
					System.out.println("Lowest paid employee in the Front End Development department:");
					
					Employee lowEmp = employees.stream()
						.filter(e -> e.getDept().equals("Front End Development"))
						.reduce((a,b) -> a.getSalary() < b.getSalary() ? a : b)
						.get();
					
					System.out.println(lowEmp);
					break;
				
				// Get the sum of all the salaries of all the employees	
				case 6:
					System.out.println("Sum of all employees salaries:");
					
					System.out.println(
							employees.stream()
								.map(e -> e.getSalary())
								.reduce((a,b) -> a + b)
								.get()
					);
					
					break;
				
				// get the average salary of the employees in the developer department	
				case 7:
					System.out.println("Average Salary of all the employees in the Developer department:");
					
					System.out.println(
						employees.stream()	
							.filter(e -> e.getDept().matches("^(Front|Back) End Development$"))
							.mapToDouble(e -> e.getSalary())
							.average().getAsDouble()
					);
					
					break;
				
				// Done with the program
				case 8:
					done = true;
					System.out.println("Have a great day!");
					break;
				
				default:
					System.out.println("Invalid option. Try again.");
					break;
			}
		}

	}
	
	public static String displayOptions() {
		return
				"\n[ ========== MAIN MENU ========== ]\n" +
				"1. Find all employees who have a salary of at least $90,000\n" +
				"2. Find the top paid employee in the Developer department\n" +
				"3. Sort the employees in descending order by their name\n" +
				"4. Sort the employees in descending order by their department\n" +
				"5. Get the lowest paid employee in the Front End Development department\n" +
				"6. Get the sum of all the salaries of the employees\n" +
				"7. Get the average salary of the employees in the Developer department\n" +
				"8. Quit the Program\n\n" +
				"Please enter one of the above options:";
		
	}
	
	public static int getValidatedInput(Scanner sc, String instr, int maxOption) {
		boolean isValid = false;
		int input = 0;
		
		// while choice is not valid
		while (!isValid) {
			
			// print the instruction
			System.out.println(instr);
			
			// attempt to receive valid input
			try {
				input = sc.nextInt();
				
				// Assume that the input is valid at this point
				isValid = true;
				
				// Input is not within the bounds of the options
				if (input < 1 || input > maxOption) {
					isValid = false;
					throw new DataEntryException(1, maxOption);
				}
			
			// integer value not passed
			} catch (InputMismatchException e) {
				System.out.println("ERR: Input must be an integer value.");
				
			// integer input is out of bounds
			} catch (DataEntryException e) {
				System.out.println(e.getMessage());
			} finally {
				// Advance the scanner
				sc.nextLine();
			}
		}
		
		return input;
	}

}
