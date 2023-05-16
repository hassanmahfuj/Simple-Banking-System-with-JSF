package com.hum.admin;

import com.hum.util.db;
import java.sql.ResultSet;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author Hassan Mahfuj
 */
@ManagedBean
public class AdminCloseAccountModel {
    String account_number;
    String holder_name;

    public AdminCloseAccountModel() {
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
    
    public void proccessName() {
        try {
            ResultSet rs = db.get().executeQuery("SELECT holder_name FROM customers WHERE account_number = ?", account_number);
            if(rs.next()) {
                holder_name = rs.getString(1);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public void proccessCloseAccount() {
        try {
            db.get().executeUpdate("DELETE FROM customers WHERE account_number = ?", account_number);
            db.get().executeUpdate("DELETE FROM statements WHERE cus_id = ?", account_number);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
