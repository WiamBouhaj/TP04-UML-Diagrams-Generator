package org.mql.java.application.models;

public class Cardinality {  
    private String minOccurs;  
    private String maxOccurs;  

    public Cardinality(String minOccurs, String maxOccurs) {  
        this.minOccurs = minOccurs;  
        this.maxOccurs = maxOccurs;  
    }  

    public String getMinOccurs() {  
        return minOccurs;  
    }  

    public String getMaxOccurs() {  
        return maxOccurs;  
    }  

    @Override  
    public String toString() {  
        return minOccurs + ".." + maxOccurs;  
    }  
}