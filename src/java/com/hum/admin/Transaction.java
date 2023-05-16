package com.hum.admin;

import com.hum.util.db;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author Hassan Mahfuj
 */
@ManagedBean
public class Transaction {
    String account_number;
    String holder_name;
    String description;
    double amount;
    double balance;

    public Transaction() {
    }

    public String getAccount_number() {
        return account_number;
    }

    public void setAccount_number(String account_number) {
        this.account_number = account_number;
    }

    public String getHolder_name() {
        return holder_name;
    }

    public void setHolder_name(String holder_name) {
        this.holder_name = holder_name;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public void proccessName() {
        try {
            ResultSet rs = db.get().executeQuery("SELECT holder_name FROM customers WHERE account_number = ?", account_number);
            if(rs.next()) {
                holder_name = rs.getString(1);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    public void proccessBalance() {
        try {
            ResultSet rs = db.get().executeQuery("SELECT balance FROM customers WHERE account_number = ?", account_number);
            if(rs.next()) {
                balance = rs.getDouble(1);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    public void proccessDeposit() {
        proccessBalance();
        double newBalance = balance + amount;
        boolean x = db.get().executeUpdate("UPDATE customers SET balance = ? WHERE account_number = ?", newBalance, account_number);
        boolean y = db.get().executeUpdate("INSERT INTO statements (cus_id, date, amount, type, description, balance) VALUES (?, ?, ?, ?, ?, ?)", account_number, new java.sql.Date(new java.util.Date().getTime()), amount, "Deposit", description, newBalance);
        
    }
    
    public void proccessWithdraw() {
        proccessBalance();
        double newBalance = balance - amount;
        boolean x = db.get().executeUpdate("UPDATE customers SET balance = ? WHERE account_number = ?", newBalance, account_number);
        boolean y = db.get().executeUpdate("INSERT INTO statements (cus_id, date, amount, type, description, balance) VALUES (?, ?, ?, ?, ?, ?)", account_number, new java.sql.Date(new java.util.Date().getTime()), amount, "Withdraw", description, newBalance);
        
    }
}
