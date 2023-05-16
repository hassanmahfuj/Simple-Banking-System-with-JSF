package com.hum.bean;

import com.hum.modal.Statement;
import com.hum.util.SessionUtil;
import com.hum.util.db;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author Hassan Mahfuj
 */
@ManagedBean
public class StatementBean {
    private List<Statement> dataList;

    public List<Statement> getDataList() {
        dataList = retrieveDataFromDatabase();
        return dataList;
    }

    public void setDataList(List<Statement> dataList) {
        this.dataList = dataList;
    }

    private List<Statement> retrieveDataFromDatabase() {
        List<Statement> data = new ArrayList<>();
        try {
            ResultSet rs = db.get().executeQuery("SELECT id, date, description, type, amount, balance FROM statements WHERE cus_id = ? ORDER BY id DESC", SessionUtil.getAccountNumber());
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
}
