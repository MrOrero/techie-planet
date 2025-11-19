package com.techieplanet.application.modules.reports.controller;

import com.techieplanet.application.modules.core.dto.response.SubjectReport;
import com.techieplanet.application.modules.reports.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @Operation(summary = "Get subject reports")
    @GetMapping
    public ResponseEntity<Page<SubjectReport>> getSubjectReports(
            @Parameter(hidden = true) Pageable pageable) {
        return ResponseEntity.ok(reportService.getSubjectReports(pageable));
    }
}