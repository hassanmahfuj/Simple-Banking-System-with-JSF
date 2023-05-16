package com.hum.admin;

import com.hum.util.SessionUtil;
import com.hum.util.db;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Hassan Mahfuj
 */
@ManagedBean
public class AccountModel {
    String holderName;
    String fathersName;
    String mothersName;
    String nid;
    Date dob;
    String phoneNumber;
    String email;
    String address;
    String nomName;
    String nomNid;
    Date nomDob;
    String nomRelation;
    String accountType;
    String initialDeposit;
    String username;
    String password;

    public AccountModel() {
    }

    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    public String getFathersName() {
        return fathersName;
    }

    public void setFathersName(String fathersName) {
        this.fathersName = fathersName;
    }

    public String getMothersName() {
        return mothersName;
    }

    public void setMothersName(String mothersName) {
        this.mothersName = mothersName;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNomName() {
        return nomName;
    }

    public void setNomName(String nomName) {
        this.nomName = nomName;
    }

    public String getNomNid() {
        return nomNid;
    }

    public void setNomNid(String nomNid) {
        this.nomNid = nomNid;
    }

    public Date getNomDob() {
        return nomDob;
    }

    public void setNomDob(Date nomDob) {
        this.nomDob = nomDob;
    }

    public String getNomRelation() {
        return nomRelation;
    }

    public void setNomRelation(String nomRelation) {
        this.nomRelation = nomRelation;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getInitialDeposit() {
        return initialDeposit;
    }

    public void setInitialDeposit(String initialDeposit) {
        this.initialDeposit = initialDeposit;
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
    
    public String nextCusId() {
        String c = "";
        try {
            ResultSet rs = db.get().executeQuery("SELECT account_number FROM customers ORDER BY account_number DESC LIMIT 1");
            if(rs.next()) {
                int a = rs.getInt(1) + 1;
                c = String.valueOf(a);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return c;
    }
    
    public String submitData() {
        String cus_id = nextCusId();
        String s = "INSERT INTO customers (account_number, holder_name, fathers_name, mothers_name, nid, dob, phone_number, email, address, n_name, n_nid, n_dob, n_relation, account_type, balance, username, password) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        boolean x = db.get().executeUpdate(s, cus_id, holderName, fathersName, mothersName, nid, new java.sql.Date(dob.getTime()), phoneNumber, email, address, nomName, nomNid, new java.sql.Date(nomDob.getTime()), nomRelation, accountType, initialDeposit, username, password);
        boolean y = db.get().executeUpdate("INSERT INTO statements (cus_id, date, amount, type, description, balance) VALUES (?, ?, ?, ?, ?, ?)", cus_id, new java.sql.Date(new Date().getTime()), initialDeposit, "Deposit", "Initial Deposit", initialDeposit);
        if(x) {
            return "open-account.xhtml?faces-redirect=true&msg=Data submitted succesfully.";
        } else {
            return "open-account.xhtml?faces-redirect=true&msg=Something went wrong.";
        }

    }
}
