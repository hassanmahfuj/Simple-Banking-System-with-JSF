package com.hum.customer;

import com.hum.util.SessionUtil;
import com.hum.util.db;
import java.sql.ResultSet;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author Hassan Mahfuj
 */
@ManagedBean
public class BalanceQuery {
    String balance;

    public BalanceQuery() {
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }
    
    public String checkBalance() {
        try {
            String b = "";
            ResultSet x = db.get().executeQuery("SELECT balance FROM customers WHERE username = ?", SessionUtil.getUserName());
            while(x.next()) {
                b = "Your current balance is " + x.getString(1);
            }
            return b;
        } catch (Exception e) {
            System.out.println(e);
            return "";
        }
    }
}
