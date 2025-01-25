package org.mql.java.application.examples;

public class Cash implements Payment {  
    private double cashTendered;  
    private double amount;  

    @Override  
    public double getAmount() {  
        return amount;  
    }  

    @Override  
    public void processPayment() {  
        // Implémentation pour traiter le paiement en espèces  
    }

	public double getCashTendered() {
		return cashTendered;
	}

	public void setCashTendered(double cashTendered) {
		this.cashTendered = cashTendered;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}  
    
}