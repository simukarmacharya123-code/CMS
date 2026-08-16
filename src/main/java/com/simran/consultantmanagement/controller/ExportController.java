package com.simran.consultantmanagement.controller;

import com.simran.consultantmanagement.export.ExcelExportService;
import com.simran.consultantmanagement.export.PdfExportService;
import com.simran.consultantmanagement.service.ConsultantService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Controller
@RequestMapping("/export")
public class ExportController {

    private final ConsultantService consultantService;
    private final ExcelExportService excelExportService;
    private final PdfExportService pdfExportService;

    public ExportController(
            ConsultantService consultantService,
            ExcelExportService excelExportService,
            PdfExportService pdfExportService) {

        this.consultantService = consultantService;
        this.excelExportService = excelExportService;
        this.pdfExportService = pdfExportService;
    }

    // ============================================================
    // EXCEL EXPORT
    // ============================================================

    @GetMapping("/excel")
    public void exportExcel(HttpServletResponse response)
            throws IOException {

        response.setContentType(
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
        );

        response.setHeader(
                "Content-Disposition",
                "attachment; filename=consultants.xlsx"
        );

        // Your existing Excel service accepts only OutputStream
        excelExportService.exportConsultants(
                response.getOutputStream()
        );
    }

    // ============================================================
    // PDF EXPORT
    // ============================================================

    @GetMapping("/pdf")
    public void exportPdf(HttpServletResponse response)
            throws IOException {

        response.setContentType("application/pdf");

        response.setHeader(
                "Content-Disposition",
                "attachment; filename=consultants.pdf"
        );

        // PDF service accepts consultants + OutputStream
        pdfExportService.exportConsultants(
                consultantService.getAllConsultants(),
                response.getOutputStream()
        );
    }
}