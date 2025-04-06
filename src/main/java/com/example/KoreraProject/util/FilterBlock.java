package com.example.KoreraProject.util;
import java.util.Map;

public class FilterBlock {
    private Map<String, String> filters;
    private String id;
    private Boolean xand;

    // Getters and Setters
    public Map<String, String> getFilters() {
        return filters;
    }

    public void setFilters(Map<String, String> filters) {
        this.filters = filters;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getXand() {
        return xand;
    }

    public void setXand(Boolean xand) {
        this.xand = xand;
    }
}