package com.hum.admin;

import com.hum.util.db;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Hassan Mahfuj
 */
@ManagedBean
@ViewScoped
public class FundTransfer {
    String fromAccountNumber;
    String fromHolderName;
    double fromBalance;
    
    String toAccountNumber;
    String toHolderName;
    double toBalance;
    
    double amount;

    public FundTransfer() {
    }

    public String getFromAccountNumber() {
        return fromAccountNumber;
    }

    public void setFromAccountNumber(String fromAccountNumber) {
        this.fromAccountNumber = fromAccountNumber;
    }

    public String getFromHolderName() {
        return fromHolderName;
    }

    public void setFromHolderName(String fromHolderName) {
        this.fromHolderName = fromHolderName;
    }

    public double getFromBalance() {
        return fromBalance;
    }

    public void setFromBalance(double fromBalance) {
        this.fromBalance = fromBalance;
    }

    public String getToAccountNumber() {
        return toAccountNumber;
    }

    public void setToAccountNumber(String toAccountNumber) {
        this.toAccountNumber = toAccountNumber;
    }

    public String getToHolderName() {
        return toHolderName;
    }

    public void setToHolderName(String toHolderName) {
        this.toHolderName = toHolderName;
    }

    public double getToBalance() {
        return toBalance;
    }

    public void setToBalance(double toBalance) {
        this.toBalance = toBalance;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
    
    public void proccessFrom() {
        try {
            ResultSet rs = db.get().executeQuery("SELECT holder_name, balance FROM customers WHERE account_number = ?", fromAccountNumber);
            if(rs.next()) {
                fromHolderName = rs.getString(1);
                fromBalance = rs.getDouble(2);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    public void proccessTo() {
        try {
            ResultSet rs = db.get().executeQuery("SELECT holder_name, balance FROM customers WHERE account_number = ?", toAccountNumber);
            if(rs.next()) {
                toHolderName = rs.getString(1);
                toBalance = rs.getDouble(2);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    public void proccessTransfer() {
        double fromNewBalance = fromBalance - amount;
        double toNewBalance = toBalance + amount;
        
        db.get().executeUpdate("UPDATE customers SET balance = ? WHERE account_number = ?", fromNewBalance, fromAccountNumber);
        db.get().executeUpdate("UPDATE customers SET balance = ? WHERE account_number = ?", toNewBalance, toAccountNumber);

        db.get().executeUpdate("INSERT INTO statements (cus_id, date, amount, type, description, balance) VALUES (?, ?, ?, ?, ?, ?)", fromAccountNumber, new java.sql.Date(new java.util.Date().getTime()), amount, "Withdraw", "Transfer to " + toHolderName, fromNewBalance);
        db.get().executeUpdate("INSERT INTO statements (cus_id, date, amount, type, description, balance) VALUES (?, ?, ?, ?, ?, ?)", toAccountNumber, new java.sql.Date(new java.util.Date().getTime()), amount, "Deposit", "Transfer from " + fromHolderName, toNewBalance);
    }
}
