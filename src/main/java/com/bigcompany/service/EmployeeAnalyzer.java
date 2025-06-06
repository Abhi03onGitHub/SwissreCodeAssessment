package com.bigcompany.service;

import com.bigcompany.model.Employee;

import java.io.*;
import java.util.*;

public class EmployeeAnalyzer {

    private Map<String, Employee> employeeMap = new HashMap<>();
    private Employee ceo;

    
    public List<Employee> loadEmployees(String filePath) throws IOException {
        List<Employee> employeeList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            br.readLine(); // skip header
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String id = parts[0].trim();
                String firstName = parts[1].trim();
                String lastName = parts[2].trim();
                double salary = Double.parseDouble(parts[3].trim());
                String managerId = parts.length > 4 ? parts[4].trim() : null;

                Employee emp = new Employee(id, firstName, lastName, salary, managerId);
                employeeMap.put(id, emp);
                employeeList.add(emp);

                if (managerId == null || managerId.isEmpty()) {
                    ceo = emp;
                }
            }

            // Assign subordinates to managers
            for (Employee emp : employeeMap.values()) {
                if (emp.managerId != null && employeeMap.containsKey(emp.managerId)) {
                    employeeMap.get(emp.managerId).subordinates.add(emp);
                }
            }
        }

        return employeeList;
    }
    
    
    public void analyzeSalaries(List<Employee> employees) {
        System.out.println("[DEBUG] Starting salary analysis...");

        // Rebuild employeeMap from the given list
        employeeMap.clear();
        for (Employee emp : employees) {
            employeeMap.put(emp.id, emp);
        }

        for (Employee manager : employeeMap.values()) {
            System.out.printf("[DEBUG] Checking manager: %s (%s)%n", manager.getFullName(), manager.id);

            if (!manager.subordinates.isEmpty()) {
                System.out.printf("[DEBUG] Manager %s has %d subordinates.%n",
                        manager.getFullName(), manager.subordinates.size());

                double avgSubSalary = manager.subordinates.stream()
                        .mapToDouble(e -> e.salary)
                        .average().orElse(0.0);

                double minExpected = avgSubSalary * 1.2;
                double maxExpected = avgSubSalary * 1.5;

                System.out.printf("[DEBUG] Avg Sub Salary: %.2f, Min Expected: %.2f, Max Expected: %.2f, Manager Salary: %.2f%n",
                        avgSubSalary, minExpected, maxExpected, manager.salary);

                if (manager.salary < minExpected) {
                    System.out.printf("Manager %s earns LESS than expected by %.2f%n",
                            manager.getFullName(), minExpected - manager.salary);
                } else if (manager.salary > maxExpected) {
                    System.out.printf("Manager %s earns MORE than expected by %.2f%n",
                            manager.getFullName(), manager.salary - maxExpected);
                }
            } else {
                System.out.printf("[DEBUG] Manager %s has NO subordinates.%n", manager.getFullName());
            }
        }
    }



  

    public void analyzeReportingLines(List<Employee> employees) {
        System.out.println("[DEBUG] Starting reporting line analysis...");

        // Rebuild employeeMap from the given list
        employeeMap.clear();
        for (Employee emp : employees) {
            employeeMap.put(emp.id, emp);
        }

        for (Employee emp : employeeMap.values()) {
            int levels = countManagers(emp);
            System.out.printf("[DEBUG] Employee: %s (%s) - Reporting Levels: %d%n", emp.getFullName(), emp.id, levels);

            if (levels > 4) {
                System.out.printf("Employee %s has reporting line TOO LONG by %d levels%n",
                        emp.getFullName(), levels - 4);
            }
        }
    }



    private int countManagers(Employee emp) {
        int count = 0;
        while (emp.managerId != null && employeeMap.containsKey(emp.managerId)) {
            count++;
            emp = employeeMap.get(emp.managerId);
        }
        return count;
    }
}
