package org.mql.java.application.examples;

public class Credit implements Payment {  
    private String number;  
    private String type;  
    private String expDate;  
    private double amount;  
    private boolean authorized;  

    @Override  
    public double getAmount() {  
        return amount;  
    }  

    @Override  
    public void processPayment() {  
        // Implémentation pour traiter le paiement par carte de crédit  
    }

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getExpDate() {
		return expDate;
	}

	public void setExpDate(String expDate) {
		this.expDate = expDate;
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