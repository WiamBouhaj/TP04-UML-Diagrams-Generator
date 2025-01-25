package org.mql.java.application.examples;

public class Item {  
    private double shippingWeight;  
    private String description;  

    public double getPriceForQuantity(int quantity) {  
         return 0.0;  
    }  

    public double getWeight() {  
        return shippingWeight;  
    }

	public double getShippingWeight() {
		return shippingWeight;
	}

	public void setShippingWeight(double shippingWeight) {
		this.shippingWeight = shippingWeight;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}  

}
