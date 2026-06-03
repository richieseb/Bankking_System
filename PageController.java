package com.bmsce.banking.controller;

import com.bmsce.banking.model.BankAccount;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
public class PageController {

    private static final Map<Integer, BankAccount> accountsStore = new HashMap<>();
    private static int accountSequence = 1001; // Base sequence ID setup
    private BankAccount currentUserSession = null;

    @GetMapping("/")
    public String showIndexPage(Model model) {
        currentUserSession = null; // Clear session state on home redirect
        return "index";
    }

    @PostMapping("/create-account")
    public String createAccount(@RequestParam String fullName, 
                                @RequestParam String pin, 
                                @RequestParam double initialBalance, 
                                Model model) {
        int newAccountNumber = accountSequence++;
        BankAccount account = new BankAccount(newAccountNumber, fullName, pin, initialBalance);
        accountsStore.put(newAccountNumber, account);
        
        model.addAttribute("accountNumber", newAccountNumber);
        model.addAttribute("name", fullName);
        model.addAttribute("balance", initialBalance);
        return "success";
    }

    @PostMapping("/login")
    public String handleLogin(@RequestParam int accountNumber, 
                              @RequestParam String pin, 
                              Model model) {
        BankAccount account = accountsStore.get(accountNumber);
        
        if (account != null && account.getPin().equals(pin)) {
            currentUserSession = account;
            return "redirect:/dashboard";
        }
        
        model.addAttribute("loginError", "Invalid Account Number or PIN details.");
        return "index";
    }

    @GetMapping("/dashboard")
    public String showDashboard(Model model) {
        if (currentUserSession == null) return "redirect:/";
        model.addAttribute("account", currentUserSession);
        return "dashboard";
    }

    @PostMapping("/deposit")
    public String handleDeposit(@RequestParam double amount, Model model) {
        if (currentUserSession == null) return "redirect:/";
        
        if (amount > 0) {
            currentUserSession.deposit(amount);
            model.addAttribute("message", "Amount deposited successfully");
        }
        model.addAttribute("account", currentUserSession);
        return "dashboard";
    }

    @PostMapping("/withdraw")
    public String handleWithdraw(@RequestParam double amount, Model model) {
        if (currentUserSession == null) return "redirect:/";
        
        boolean success = currentUserSession.withdraw(amount);
        if (success) {
            model.addAttribute("message", "Amount withdrawn successfully");
        } else {
            model.addAttribute("error", "Insufficient balance");
        }
        model.addAttribute("account", currentUserSession);
        return "dashboard";
    }

    @GetMapping("/transaction-history")
    public String showTransactionHistory(Model model) {
        if (currentUserSession == null) return "redirect:/";
        model.addAttribute("transactions", currentUserSession.getTransactionHistory());
        return "transactionHistory";
    }

    @GetMapping("/logout")
    public String handleLogout() {
        currentUserSession = null;
        return "redirect:/";
    }
}
