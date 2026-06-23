package com.cmps.spring.controller;

import com.cmps.spring.repository.service.ExportService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExportController {

    private final ExportService exportService;

    // Spring automatically injects your ExportService here
    public ExportController(ExportService exportService) {
        this.exportService = exportService;
    }

    @GetMapping("/test-insert")
    public String testInsert() {
        try {
            exportService.registerExportDestinations();
            return "Success! All 7 records have been safely inserted into the database.";
        } catch (Exception e) {
            e.printStackTrace();
            return "Transaction Failed! Check your console log for errors. Database rolled back safely: " + e.getMessage();
        }
    }
}