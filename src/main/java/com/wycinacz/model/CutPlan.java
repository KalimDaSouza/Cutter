package com.wycinacz.model;

import java.util.ArrayList;
import java.util.List;

public class CutPlan {
    private int stockLength;
    private List<Integer> cuts;
    
    public CutPlan(int stockLength) {
        this.stockLength = stockLength;
        this.cuts = new ArrayList<>();
    }
    
    public CutPlan(int stockLength, List<Integer> cuts) {
        this.stockLength = stockLength;
        this.cuts = new ArrayList<>(cuts);
    }
    
    public int getStockLength() {
        return stockLength;
    }
    
    public void setStockLength(int stockLength) {
        this.stockLength = stockLength;
    }
    
    public List<Integer> getCuts() {
        return cuts;
    }
    
    public void setCuts(List<Integer> cuts) {
        this.cuts = cuts;
    }
    
    public void addCut(int cut) {
        this.cuts.add(cut);
    }
    
    public int getUsedLength() {
        return cuts.stream().mapToInt(Integer::intValue).sum();
    }
    
    public int getWaste() {
        return stockLength - getUsedLength();
    }
    
    public int getRemainingLength() {
        return stockLength - getUsedLength();
    }
}
