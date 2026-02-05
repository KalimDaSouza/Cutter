package com.wycinacz.service;

import com.wycinacz.model.CutPlan;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class CuttingOptimizer {
    
    public List<CutPlan> optimizeCuts(List<Integer> requiredCuts, List<Integer> stockLengths) {
        // Sort cuts in descending order (First Fit Decreasing algorithm)
        List<Integer> sortedCuts = new ArrayList<>(requiredCuts);
        sortedCuts.sort(Collections.reverseOrder());
        
        List<CutPlan> result = new ArrayList<>();
        
        for (int cut : sortedCuts) {
            boolean placed = false;
            
            // Try to fit in existing plans
            for (CutPlan plan : result) {
                if (plan.getRemainingLength() >= cut) {
                    plan.addCut(cut);
                    placed = true;
                    break;
                }
            }
            
            // If not placed, create new plan with appropriate stock
            if (!placed) {
                Integer stock = findSuitableStock(cut, stockLengths);
                
                if (stock != null) {
                    CutPlan newPlan = new CutPlan(stock);
                    newPlan.addCut(cut);
                    result.add(newPlan);
                } else {
                    throw new IllegalArgumentException(
                        "Brak kształtownika o długości >= " + cut + " mm"
                    );
                }
            }
        }
        
        return result;
    }
    
    private Integer findSuitableStock(int cutLength, List<Integer> stockLengths) {
        return stockLengths.stream()
            .filter(s -> s >= cutLength)
            .min(Integer::compareTo)
            .orElse(null);
    }
}
