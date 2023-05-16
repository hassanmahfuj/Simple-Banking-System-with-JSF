package com.hum.admin;

import com.hum.util.db;
import java.sql.ResultSet;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author Hassan Mahfuj
 */
@ManagedBean
public class AdminCheckBalanceModel {
    String account_number;
    String holder_name;
    double balance;

    public AdminCheckBalanceModel() {
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

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
    
    public void proccessCheckBalance() {
        try {
            ResultSet rs = db.get().executeQuery("SELECT holder_name, balance FROM customers WHERE account_number = ?", account_number);
            if(rs.next()) {
                holder_name = rs.getString(1);
                balance = rs.getDouble(2);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
