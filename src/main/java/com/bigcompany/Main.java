package com.bigcompany;

import java.util.List;

import com.bigcompany.model.Employee;
import com.bigcompany.service.EmployeeAnalyzer;

public class Main {
	public static void main(String[] args) throws Exception {
		EmployeeAnalyzer analyzer = new EmployeeAnalyzer();
		List<Employee> employees = analyzer.loadEmployees("employees.csv");

		System.out.println("---- Salary Analysis ----");
		analyzer.analyzeSalaries(employees);

		System.out.println("\n---- Reporting Line Analysis ----");
		analyzer.analyzeReportingLines(employees);

	}

}
