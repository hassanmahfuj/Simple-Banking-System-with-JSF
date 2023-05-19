package com.hum.admin;

import com.hum.modal.Statement;
import com.hum.util.db;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Hassan Mahfuj
 */
@ManagedBean
@ViewScoped
public class AdminViewStatementModel {
    String account_number;
    String holder_name;
    List<Statement> dataList;

    public AdminViewStatementModel() {
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
    
    public List<Statement> getDataList() {
        return dataList;
    }

    public void setDataList(List<Statement> dataList) {
        this.dataList = dataList;
    }

    private List<Statement> retrieveDataFromDatabase() {
        List<Statement> data = new ArrayList<>();
        try {
            ResultSet rs = db.get().executeQuery("SELECT id, date, description, type, amount, balance FROM statements WHERE cus_id = ? ORDER BY id DESC", account_number);
            while(rs.next()) {
                Statement s = new Statement();
                s.setId(rs.getString(1));
                s.setDate(rs.getString(2));
                s.setDescription(rs.getString(3));
                if(rs.getString(4).equals("Deposit")) {
                    s.setDeposit(rs.getString(5));
                    s.setWithdraw("");
                } else {
                    s.setDeposit("");
                    s.setWithdraw(rs.getString(5));
                }
                s.setBalance(rs.getString(6));
                data.add(s);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return data;
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
    
    public void proccessData() {
        proccessName();
        dataList = retrieveDataFromDatabase();
    }
}
