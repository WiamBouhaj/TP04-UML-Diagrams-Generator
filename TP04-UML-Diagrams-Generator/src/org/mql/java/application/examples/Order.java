package org.mql.java.application.examples;

import java.util.List;  

public class Order {  
    private String date;  
    private String status;  
    private List<OrderDetail> orderDetails; // Association  

    public double calcTax() {  
        // Exemple de calcul de taxe  
        return 0.0; // Implémentez le calcul réel  
    }  

    public double calcTotal() {  
        // Exemple de calcul de total  
        return 0.0; // Implémentez le calcul réel  
    }  

    public double calcTotalWeight() {  
        double totalWeight = 0.0;  
        for (OrderDetail detail : orderDetails) {  
            totalWeight += detail.calcWeight();  
        }  
        return totalWeight;  
    }

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<OrderDetail> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(List<OrderDetail> orderDetails) {
		this.orderDetails = orderDetails;
	}  

}