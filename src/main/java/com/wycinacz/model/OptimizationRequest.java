package com.wycinacz.model;

import java.util.List;

public class OptimizationRequest {
    private List<Integer> requiredCuts;
    private List<Integer> stockLengths;
    
    public OptimizationRequest() {
    }
    
    public OptimizationRequest(List<Integer> requiredCuts, List<Integer> stockLengths) {
        this.requiredCuts = requiredCuts;
        this.stockLengths = stockLengths;
    }
    
    public List<Integer> getRequiredCuts() {
        return requiredCuts;
    }
    
    public void setRequiredCuts(List<Integer> requiredCuts) {
        this.requiredCuts = requiredCuts;
    }
    
    public List<Integer> getStockLengths() {
        return stockLengths;
    }
    
    public void setStockLengths(List<Integer> stockLengths) {
        this.stockLengths = stockLengths;
    }
}
