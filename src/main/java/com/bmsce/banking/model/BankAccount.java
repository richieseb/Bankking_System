package com.bmsce.banking.model;

import java.util.ArrayList;
import java.util.List;

public class BankAccount {
    private int accountNumber;
    private String fullName;
    private String pin;
    private double balance;
    private List<Transaction> transactionHistory;

    public BankAccount(int accountNumber, String fullName, String pin, double initialBalance) {
        this.accountNumber = accountNumber;
        this.fullName = fullName;
        this.pin = pin;
        this.balance = initialBalance;
        this.transactionHistory = new ArrayList<>();
    }

    // Getters to enforce Encapsulation
    public int getAccountNumber() { return accountNumber; }
    public String getFullName() { return fullName; }
    public String getPin() { return pin; }
    public double getBalance() { return balance; }
    public List<Transaction> getTransactionHistory() { return transactionHistory; }

    public void deposit(double amount) {
        this.balance += amount;
        this.transactionHistory.add(new Transaction("DEPOSIT", amount));
    }

    public boolean withdraw(double amount) {
        if (amount <= this.balance) {
            this.balance -= amount;
            this.transactionHistory.add(new Transaction("WITHDRAW", amount));
            return true;
        }
        return false; // Safely intercept overdrafts
    }
}
