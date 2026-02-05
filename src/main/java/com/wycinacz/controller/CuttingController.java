package com.wycinacz.controller;

import com.wycinacz.model.CutPlan;
import com.wycinacz.model.OptimizationRequest;
import com.wycinacz.model.OptimizationResult;
import com.wycinacz.service.CuttingOptimizer;
import com.wycinacz.service.ExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class CuttingController {
    
    @Autowired
    private CuttingOptimizer optimizer;
    
    @Autowired
    private ExportService exportService;
    
    @PostMapping("/optimize")
    public ResponseEntity<OptimizationResult> optimize(@RequestBody OptimizationRequest request) {
        try {
            List<CutPlan> plans = optimizer.optimizeCuts(
                request.getRequiredCuts(),
                request.getStockLengths()
            );
            
            OptimizationResult result = new OptimizationResult(plans);
            return ResponseEntity.ok(result);
            
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PostMapping("/export/pdf")
    public ResponseEntity<byte[]> exportPDF(@RequestBody OptimizationResult result) {
        byte[] pdfBytes = exportService.generatePDF(result);
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "wycinacz_wyniki.pdf");
        
        return ResponseEntity.ok()
            .headers(headers)
            .body(pdfBytes);
    }
    
    @PostMapping("/export/txt")
    public ResponseEntity<String> exportTXT(@RequestBody OptimizationResult result) {
        String txtContent = exportService.generateTXT(result);
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        headers.setContentDispositionFormData("attachment", "wycinacz_wyniki.txt");
        
        return ResponseEntity.ok()
            .headers(headers)
            .body(txtContent);
    }
}
