package com.hum.customer;

import com.hum.util.SessionUtil;
import com.hum.util.db;
import javax.faces.bean.ManagedBean;
import javax.servlet.http.HttpSession;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Hassan Mahfuj
 */
@ManagedBean
public class Customer {

    private String username;
    private String password;

    public Customer() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String login() {
        try {
            ResultSet rs = db.get().executeQuery("SELECT * FROM customers WHERE username = ? AND password = ?", username, password);
            if(rs.next()) {
                HttpSession session = SessionUtil.getSession();
                session.setAttribute("username", username);
                session.setAttribute("acc", rs.getString("account_number"));
                return "dashboard?faces-redirect=true";
            } else {
                return "login?faces-redirect=true&errmsg=Username or Password is wrong!";
            }
        } catch (SQLException e) {
            System.out.println(e);
            return "login?faces-redirect=true&errmsg=Something went wrong!";
        }
    }
    
    public String logout() {
        HttpSession session = SessionUtil.getSession();
        session.invalidate();
        return "login?faces-redirect=true";
    }
    
    public String getUserName() {
        return SessionUtil.getUserName();
    }
    
    public String getAccountNumber() {
        return SessionUtil.getAccountNumber();
    }
}
