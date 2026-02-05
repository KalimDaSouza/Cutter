package com.wycinacz.service;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.wycinacz.model.CutPlan;
import com.wycinacz.model.OptimizationResult;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class ExportService {
    
    public byte[] generatePDF(OptimizationResult result) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);
            
            document.add(new Paragraph("Wyniki optymalizacji cięcia")
                .setFontSize(18)
                .setBold());
            
            List<CutPlan> plans = result.getPlans();
            for (int i = 0; i < plans.size(); i++) {
                CutPlan plan = plans.get(i);
                String line = String.format(
                    "Sztanga #%d (%d mm): %s (strata: %d mm)",
                    i + 1,
                    plan.getStockLength(),
                    plan.getCuts().toString().replaceAll("[\\[\\]]", ""),
                    plan.getWaste()
                );
                document.add(new Paragraph(line));
            }
            
            document.add(new Paragraph("\n" + result.getSummary()).setBold());
            
            document.close();
            return baos.toByteArray();
            
        } catch (Exception e) {
            throw new RuntimeException("Błąd generowania PDF", e);
        }
    }
    
    public String generateTXT(OptimizationResult result) {
        StringBuilder sb = new StringBuilder();
        sb.append("Wyniki optymalizacji cięcia\n");
        sb.append("============================\n\n");
        
        List<CutPlan> plans = result.getPlans();
        for (int i = 0; i < plans.size(); i++) {
            CutPlan plan = plans.get(i);
            sb.append(String.format(
                "Sztanga #%d (%d mm): %s (strata: %d mm)\n",
                i + 1,
                plan.getStockLength(),
                plan.getCuts().toString().replaceAll("[\\[\\]]", ""),
                plan.getWaste()
            ));
        }
        
        sb.append("\n").append(result.getSummary()).append("\n");
        
        return sb.toString();
    }
}
