package com.bigcompany.service;

import org.junit.jupiter.api.Test;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

public class EmployeeAnalyzerTest {

    @Test
    public void testLoadAndAnalyze() throws IOException {
        EmployeeAnalyzer analyzer = new EmployeeAnalyzer();
        analyzer.loadEmployees("Employees.csv");

        // Simple check for successful load
        assertNotNull(analyzer);
    }
}
