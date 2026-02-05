package com.wycinacz.model;

import java.util.List;

public class OptimizationResult {
    private List<CutPlan> plans;
    private int totalStockUsed;
    private int totalLength;
    private int totalWaste;
    private String summary;
    
    public OptimizationResult() {
    }
    
    public OptimizationResult(List<CutPlan> plans) {
        this.plans = plans;
        calculateSummary();
    }
    
    private void calculateSummary() {
        this.totalStockUsed = plans.size();
        this.totalLength = plans.stream().mapToInt(CutPlan::getStockLength).sum();
        this.totalWaste = plans.stream().mapToInt(CutPlan::getWaste).sum();
        this.summary = String.format(
            "Użyto sztang: %d | Łączna długość: %d mm | Całkowita strata: %d mm",
            totalStockUsed, totalLength, totalWaste
        );
    }
    
    public List<CutPlan> getPlans() {
        return plans;
    }
    
    public void setPlans(List<CutPlan> plans) {
        this.plans = plans;
        calculateSummary();
    }
    
    public int getTotalStockUsed() {
        return totalStockUsed;
    }
    
    public int getTotalLength() {
        return totalLength;
    }
    
    public int getTotalWaste() {
        return totalWaste;
    }
    
    public String getSummary() {
        return summary;
    }
}
