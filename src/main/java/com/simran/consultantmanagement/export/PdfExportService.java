package com.simran.consultantmanagement.export;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.simran.consultantmanagement.entity.Consultant;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@Service
public class PdfExportService {

    public void exportConsultants(
            List<Consultant> consultants,
            OutputStream outputStream) throws IOException {

        Document document = new Document();

        try {

            // Connect PDF document to output stream
            PdfWriter.getInstance(document, outputStream);

            // Open PDF
            document.open();

            // =====================================================
            // TITLE
            // =====================================================

            Font titleFont = new Font(
                    Font.HELVETICA,
                    18,
                    Font.BOLD
            );

            Paragraph title = new Paragraph(
                    "Consultant Management Report",
                    titleFont
            );

            title.setAlignment(Paragraph.ALIGN_CENTER);

            document.add(title);

            document.add(
                    new Paragraph(" ")
            );

            // =====================================================
            // TOTAL CONSULTANTS
            // =====================================================

            Font normalFont = new Font(
                    Font.HELVETICA,
                    11,
                    Font.NORMAL
            );

            Paragraph total = new Paragraph(
                    "Total Consultants: " + consultants.size(),
                    normalFont
            );

            document.add(total);

            document.add(
                    new Paragraph(" ")
            );

            // =====================================================
            // TABLE
            // =====================================================

            PdfPTable table = new PdfPTable(8);

            table.setWidthPercentage(100);

            // Table headers
            table.addCell(new Phrase("ID"));
            table.addCell(new Phrase("Name"));
            table.addCell(new Phrase("Email"));
            table.addCell(new Phrase("Phone"));
            table.addCell(new Phrase("Technology"));
            table.addCell(new Phrase("Experience"));
            table.addCell(new Phrase("Status"));
            table.addCell(new Phrase("Joined Date"));

            // =====================================================
            // CONSULTANT DATA
            // =====================================================

            for (Consultant consultant : consultants) {

                // ID
                table.addCell(
                        consultant.getId() != null
                                ? consultant.getId().toString()
                                : ""
                );

                // Name
                table.addCell(
                        consultant.getName() != null
                                ? consultant.getName()
                                : ""
                );

                // Email
                table.addCell(
                        consultant.getEmail() != null
                                ? consultant.getEmail()
                                : ""
                );

                // Phone
                table.addCell(
                        consultant.getPhone() != null
                                ? consultant.getPhone()
                                : ""
                );

                // Technology
                table.addCell(
                        consultant.getTechnology() != null
                                ? consultant.getTechnology()
                                : ""
                );

                // Experience
                table.addCell(
                        consultant.getExperience() != null
                                ? consultant.getExperience() + " years"
                                : ""
                );

                // Status
                table.addCell(
                        consultant.getStatus() != null
                                ? consultant.getStatus()
                                : ""
                );

                // Joined Date
                table.addCell(
                        consultant.getJoinedDate() != null
                                ? consultant.getJoinedDate().toString()
                                : ""
                );
            }

            // Add table to document
            document.add(table);

        } catch (DocumentException e) {

            throw new IOException(
                    "Error while generating PDF",
                    e
            );

        } finally {

            // Close PDF
            if (document.isOpen()) {
                document.close();
            }
        }
    }
}