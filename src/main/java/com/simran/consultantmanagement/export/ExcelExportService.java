package com.simran.consultantmanagement.export;

import com.simran.consultantmanagement.entity.Consultant;
import com.simran.consultantmanagement.repository.ConsultantRepository;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@Service
public class ExcelExportService {

    private final ConsultantRepository consultantRepository;

    public ExcelExportService(
            ConsultantRepository consultantRepository) {

        this.consultantRepository = consultantRepository;
    }

    public void exportConsultants(
            OutputStream outputStream) throws IOException {

        // Get all consultants from the database
        List<Consultant> consultants =
                consultantRepository.findAll();

        // Create a new Excel workbook
        try (Workbook workbook = new XSSFWorkbook()) {

            // Create Excel sheet
            Sheet sheet =
                    workbook.createSheet("Consultants");

            // Create header style
            CellStyle headerStyle =
                    workbook.createCellStyle();

            Font headerFont =
                    workbook.createFont();

            headerFont.setBold(true);

            headerStyle.setFont(headerFont);

            // Create header row
            Row headerRow =
                    sheet.createRow(0);

            String[] headers = {
                    "ID",
                    "Name",
                    "Email",
                    "Phone",
                    "Technology",
                    "Experience",
                    "Status",
                    "Joined Date"
            };

            // Add headers
            for (int i = 0; i < headers.length; i++) {

                Cell cell =
                        headerRow.createCell(i);

                cell.setCellValue(headers[i]);

                cell.setCellStyle(headerStyle);
            }

            // Add consultant data
            int rowNumber = 1;

            for (Consultant consultant : consultants) {

                Row row =
                        sheet.createRow(rowNumber++);

                // ID
                row.createCell(0)
                        .setCellValue(
                                consultant.getId() != null
                                        ? consultant.getId()
                                        : 0
                        );

                // Name
                row.createCell(1)
                        .setCellValue(
                                consultant.getName() != null
                                        ? consultant.getName()
                                        : ""
                        );

                // Email
                row.createCell(2)
                        .setCellValue(
                                consultant.getEmail() != null
                                        ? consultant.getEmail()
                                        : ""
                        );

                // Phone
                row.createCell(3)
                        .setCellValue(
                                consultant.getPhone() != null
                                        ? consultant.getPhone()
                                        : ""
                        );

                // Technology
                row.createCell(4)
                        .setCellValue(
                                consultant.getTechnology() != null
                                        ? consultant.getTechnology()
                                        : ""
                        );

                // Experience
                row.createCell(5)
                        .setCellValue(
                                consultant.getExperience() != null
                                        ? consultant.getExperience()
                                        : 0
                        );

                // Status
                row.createCell(6)
                        .setCellValue(
                                consultant.getStatus() != null
                                        ? consultant.getStatus()
                                        : ""
                        );

                // Joined Date
                row.createCell(7)
                        .setCellValue(
                                consultant.getJoinedDate() != null
                                        ? consultant.getJoinedDate()
                                        .toString()
                                        : ""
                        );
            }

            // Automatically adjust column widths
            for (int i = 0; i < headers.length; i++) {

                sheet.autoSizeColumn(i);
            }

            // Write workbook to output
            workbook.write(outputStream);
        }
    }
}