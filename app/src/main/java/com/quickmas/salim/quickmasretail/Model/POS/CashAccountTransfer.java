package com.quickmas.salim.quickmasretail.Model.POS;

import android.content.ContentValues;
import android.database.Cursor;

import com.quickmas.salim.quickmasretail.Database.DBInitialization;

import java.util.ArrayList;

import static com.quickmas.salim.quickmasretail.Database.DBInitialization.TABLE_cash_transfer;

/**
 * Created by Forhad on 07/09/2018.
 */

public class CashAccountTransfer {
    public int id=0;
    public String account_name="";
    public String remarks="";
    public Double amount=0.0;
    public String receive_type="";
    public String receive_from="";
    public String receive_by="";
    public String receive_date="";
    public String approve_by="";
    public String approve_date="";
    public String pos_by="";
    public String contra="";
    public int status=0;

    ContentValues getContectValue(DBInitialization db)
    {
        ContentValues insertValues = new ContentValues();
        if(this.getId()>0)
        {
            insertValues.put(db.COLUMN_cash_account_id, this.getId());
        }
        insertValues.put(db.COLUMN_cash_account_account_name, this.getAccount_name());
        insertValues.put(db.COLUMN_cash_account_remarks, this.getRemarks());
        insertValues.put(db.COLUMN_cash_account_amount, this.getAmount());
        insertValues.put(db.COLUMN_cash_account_receive_type, this.getReceive_type());
        insertValues.put(db.COLUMN_cash_account_receive_by, this.getReceive_by());
        insertValues.put(db.COLUMN_cash_account_receive_from, this.getReceive_from());
        insertValues.put(db.COLUMN_cash_account_receive_date, this.getReceive_date());
        insertValues.put(db.COLUMN_cash_account_approve_by, this.getApprove_by());
        insertValues.put(db.COLUMN_cash_account_approve_date, this.getApprove_date());
        insertValues.put(db.COLUMN_cash_account_status, this.getStatus());
        insertValues.put(db.COLUMN_cash_account_pos_by, this.getPos_by());
        return  insertValues;
    }
    public void insert(DBInitialization db,String con)
    {
        db.insertData(getContectValue(db), TABLE_cash_transfer,con);
    }
    public void insert(DBInitialization db)
    {
        String con = db.COLUMN_cash_account_id+"="+this.getId();
        db.insertData(getContectValue(db), TABLE_cash_transfer,con);
    }
    public void delete(DBInitialization db)
    {
        String con = db.COLUMN_cash_account_id+"="+this.getId();
        db.deleteData(TABLE_cash_transfer,con);
    }
    public void update(DBInitialization db)
    {
        db.updateData(getContectValue(db), TABLE_cash_transfer, db.COLUMN_cash_account_id+"="+this.getId());
    }
    public static void update(DBInitialization db,String data,String con)
    {
        db.updateData(data,con, TABLE_cash_transfer);
    }
    public static int getCount(DBInitialization db)
    {
        return db.getDataCount(TABLE_cash_transfer,"1=1");
    }

    public static ArrayList<CashAccountTransfer> select(DBInitialization db, String con)
    {
        Cursor c = db.getData("*", TABLE_cash_transfer,con,"");
        ArrayList<CashAccountTransfer> cashPaymentReceives = new ArrayList<CashAccountTransfer>();
        if (c !=null && c.getCount() > 0) {
            c.moveToFirst();
            do {
                CashAccountTransfer cashPaymentReceive = new CashAccountTransfer();
                cashPaymentReceive.setId(c.getInt(c.getColumnIndex(db.COLUMN_cash_account_id)));
                cashPaymentReceive.setAccount_name(c.getString(c.getColumnIndex(db.COLUMN_cash_account_account_name)));
                cashPaymentReceive.setRemarks(c.getString(c.getColumnIndex(db.COLUMN_cash_account_remarks)));
                cashPaymentReceive.setAmount(c.getDouble(c.getColumnIndex(db.COLUMN_cash_account_amount)));
                cashPaymentReceive.setReceive_type(c.getString(c.getColumnIndex(db.COLUMN_cash_account_receive_type)));
                cashPaymentReceive.setReceive_by(c.getString(c.getColumnIndex(db.COLUMN_cash_account_receive_by)));
                cashPaymentReceive.setReceive_from(c.getString(c.getColumnIndex(db.COLUMN_cash_account_receive_from)));
                cashPaymentReceive.setReceive_date(c.getString(c.getColumnIndex(db.COLUMN_cash_account_receive_date)));
                cashPaymentReceive.setApprove_by(c.getString(c.getColumnIndex(db.COLUMN_cash_account_approve_by)));
                cashPaymentReceive.setApprove_date(c.getString(c.getColumnIndex(db.COLUMN_cash_account_approve_date)));
                cashPaymentReceive.setStatus(c.getInt(c.getColumnIndex(db.COLUMN_cash_account_status)));
                cashPaymentReceive.setPos_by(c.getString(c.getColumnIndex(db.COLUMN_cash_account_pos_by)));
                cashPaymentReceives.add(cashPaymentReceive);
            } while (c.moveToNext());
        }
        return cashPaymentReceives;
    }
    public static int getMaxId(DBInitialization db)
    {
        return db.getMax(TABLE_cash_transfer,db.COLUMN_cash_account_id, "1=1");
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void setReceive_type(String receive_type) {
        this.receive_type = receive_type;
    }

    public void setReceive_by(String receive_by) {
        this.receive_by = receive_by;
    }

    public void setReceive_date(String receive_date) {
        this.receive_date = receive_date;
    }

    public void setApprove_by(String approve_by) {
        this.approve_by = approve_by;
    }

    public void setApprove_date(String approve_date) {
        this.approve_date = approve_date;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setReceive_from(String receive_from) {
        this.receive_from = receive_from;
    }

    public String getReceive_from() {
        return receive_from;
    }

    public int getId() {
        return id;
    }

    public String getAccount_name() {
        return account_name;
    }

    public String getRemarks() {
        return remarks;
    }

    public Double getAmount() {
        return amount;
    }

    public String getReceive_type() {
        return receive_type;
    }

    public String getReceive_by() {
        return receive_by;
    }

    public String getReceive_date() {
        return receive_date;
    }

    public String getApprove_by() {
        return approve_by;
    }

    public String getApprove_date() {
        return approve_date;
    }

    public int getStatus() {
        return status;
    }

    public void setPos_by(String pos_by) {
        this.pos_by = pos_by;
    }

    public String getPos_by() {
        return pos_by;
    }
}
