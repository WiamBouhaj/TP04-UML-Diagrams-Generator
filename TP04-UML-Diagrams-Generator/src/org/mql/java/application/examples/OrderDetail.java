package org.mql.java.application.examples;

public class OrderDetail {  
    private int quantity;  
    private String taxStatus;  
    private Item item;  

    public double calcSubTotal() {  
        return item.getPriceForQuantity(quantity);  
    }  

    public double calcWeight() {  
        return item.getWeight() * quantity;  
    }

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getTaxStatus() {
		return taxStatus;
	}

	public void setTaxStatus(String taxStatus) {
		this.taxStatus = taxStatus;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}  
 
}
