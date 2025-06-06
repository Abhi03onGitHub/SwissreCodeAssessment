package com.bigcompany.model;

import java.util.ArrayList;
import java.util.List;

public class Employee {

		    public String id;
		    public String firstName;
		    public String lastName;
		    public double salary;
		    public String managerId;
		    public List<Employee> subordinates = new ArrayList<>();

		    public Employee(String id, String firstName, String lastName, double salary, String managerId) {
		        this.id = id;
		        this.firstName = firstName;
		        this.lastName = lastName;
		        this.salary = salary;
		        this.managerId = managerId;
		    }

		    public String getFullName() {
		        return firstName + " " + lastName;
		    }
		


}


