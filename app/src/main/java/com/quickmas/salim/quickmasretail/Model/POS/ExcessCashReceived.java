package com.quickmas.salim.quickmasretail.Model.POS;

import android.content.ContentValues;
import android.database.Cursor;

import com.quickmas.salim.quickmasretail.Database.DBInitialization;

import java.util.ArrayList;

/**
 * Created by Forhad on 30/08/2018.
 */

public class ExcessCashReceived {
    public int id;
    public String customerName;
    public double amount;
    public int receiveId;
    public double received_amount;

    ContentValues getContectValue(DBInitialization db)
    {
        ContentValues insertValues = new ContentValues();
        if(this.getId() > 0) {
            insertValues.put(db.COLUMN_excss_cash_id, this.getId());
        }
        insertValues.put(db.COLUMN_excss_cash_customer, this.getCustomerName());
        insertValues.put(db.COLUMN_excss_cash_amount, this.getAmount());
        insertValues.put(db.COLUMN_excss_cash_receive_id, this.getReceiveId());
        insertValues.put(db.COLUMN_excss_cash_receive_amount, this.getReceived_amount());
        return  insertValues;
    }

    public void insert(DBInitialization db)
    {
        db.insertData(getContectValue(db),db.TABLE_excss_cash,db.COLUMN_excss_cash_id+"="+this.getId());
    }
    public void update(DBInitialization db)
    {
        db.updateData(getContectValue(db),db.TABLE_excss_cash,db.COLUMN_excss_cash_id+"="+this.getId());
    }

    public static ArrayList<ExcessCashReceived> select(DBInitialization db, String con)
    {
        Cursor c = db.getData("*",db.TABLE_excss_cash,con,"");
        ArrayList<ExcessCashReceived> excessCashReceiveds = new ArrayList<ExcessCashReceived>();
        try {
            if (c != null && c.getCount() > 0) {
                c.moveToFirst();
                do {
                    ExcessCashReceived excessCashReceived = new ExcessCashReceived();
                    excessCashReceived.setId(c.getInt(c.getColumnIndex(db.COLUMN_excss_cash_id)));
                    excessCashReceived.setCustomerName(c.getString(c.getColumnIndex(db.COLUMN_excss_cash_customer)));
                    excessCashReceived.setAmount(c.getDouble(c.getColumnIndex(db.COLUMN_excss_cash_amount)));
                    excessCashReceived.setReceiveId(c.getInt(c.getColumnIndex(db.COLUMN_excss_cash_receive_id)));
                    excessCashReceived.setReceived_amount(c.getDouble(c.getColumnIndex(db.COLUMN_excss_cash_receive_amount)));
                    excessCashReceiveds.add(excessCashReceived);
                } while (c.moveToNext());
            }
        }catch (Exception e){}
        return excessCashReceiveds;
    }

    public int getId() {
        return id;
    }

    public int getReceiveId() {
        return receiveId;
    }

    public double getReceived_amount() {
        return received_amount;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setReceiveId(int receiveId) {
        this.receiveId = receiveId;
    }

    public void setReceived_amount(double received_amount) {
        this.received_amount = received_amount;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCustomerName() {
        return customerName;
    }

    public double getAmount() {
        return amount;
    }
}
