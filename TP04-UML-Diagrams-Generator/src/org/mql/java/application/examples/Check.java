package org.mql.java.application.examples;

public class Check implements Payment {  
    private String name;  
    private String bankID;  
    private boolean authorized;  
    private double amount;  

    @Override  
    public double getAmount() {  
        return amount;  
    }  

    @Override  
    public void processPayment() {  
        
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBankID() {
		return bankID;
	}

	public void setBankID(String bankID) {
		this.bankID = bankID;
	}

	public boolean isAuthorized() {
		return authorized;
	}

	public void setAuthorized(boolean authorized) {
		this.authorized = authorized;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}  
 
}