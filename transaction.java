package com.bmsce.banking.model;

public class Transaction {
    private String type; // DEPOSIT or WITHDRAW
    private double amount;

    public Transaction(String type, double amount) {
        this.type = type;
        this.amount = amount;
    }

    // Getters for Encapsulation
    public String getType() { return type; }
    public double getAmount() { return amount; }
}
