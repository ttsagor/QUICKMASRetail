package com.quickmas.salim.quickmasretail.Model.POS;

import android.content.ContentValues;
import android.database.Cursor;

import com.quickmas.salim.quickmasretail.Database.DBInitialization;

import java.util.ArrayList;

import static com.quickmas.salim.quickmasretail.Database.DBInitialization.TABLE_cash_receive_coa_details;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.TABLE_cash_receive_coa_exp_details;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.TABLE_cash_receive_details;

/**
 * Created by Forhad on 03/09/2018.
 */

public class CoExpAccountReceiveDetails {
    public int id;
    public int receive_id;
    public String account_name;
    public String remarks;
    public double amount;
    public String date_time;
    public String po_id;
    public String uuid;
    public int if_data_synced;

    ContentValues getContectValue(DBInitialization db)
    {
        ContentValues insertValues = new ContentValues();
        if(this.getId()>0)
        {
            insertValues.put(db.COLUMN_cash_receive_coa_exp_details_id, this.getId());
        }
        insertValues.put(db.COLUMN_cash_receive_coa_exp_details_received_id, this.getReceive_id());
        insertValues.put(db.COLUMN_cash_receive_coa_exp_details_account_name, this.getAccount_name());
        insertValues.put(db.COLUMN_cash_receive_coa_exp_details_remarks, this.getRemarks());
        insertValues.put(db.COLUMN_cash_receive_coa_exp_details_amount, this.getAmount());
        insertValues.put(db.COLUMN_cash_receive_coa_exp_details_uuid, this.getUuid_id());
        insertValues.put(db.COLUMN_cash_receive_coa_exp_details_po_id, this.getPo_id());
        insertValues.put(db.COLUMN_cash_receive_coa_exp_details_if_data_synced, this.getIf_data_synced());
        return  insertValues;
    }

    public void insert(DBInitialization db)
    {
        String con =db.COLUMN_cash_receive_coa_exp_details_received_id+"="+this.getReceive_id()+" AND "+db.COLUMN_cash_receive_coa_exp_details_account_name+"='"+this.getAccount_name()+"'";
        con = db.COLUMN_cash_receive_coa_exp_details_id+"="+this.getId();
        db.insertData(getContectValue(db), TABLE_cash_receive_coa_exp_details,con);
    }
    public void update(DBInitialization db)
    {
        String con =db.COLUMN_cash_receive_coa_exp_details_received_id+"="+this.getReceive_id()+" AND "+db.COLUMN_cash_receive_coa_exp_details_account_name+"='"+this.getAccount_name()+"'";
        con = db.COLUMN_cash_receive_coa_exp_details_id+"="+this.getId();
        db.updateData(getContectValue(db), TABLE_cash_receive_coa_exp_details,con);
    }
    public static void update(DBInitialization db,String data,String con)
    {
        db.updateData(data,con, TABLE_cash_receive_coa_exp_details);
    }
    public static int getCount(DBInitialization db)
    {
        return db.getDataCount(TABLE_cash_receive_coa_exp_details,"1=1");
    }

    public static ArrayList<CoExpAccountReceiveDetails> select(DBInitialization db, String con)
    {
        Cursor c = db.getData("*", TABLE_cash_receive_coa_exp_details,con,"");
        ArrayList<CoExpAccountReceiveDetails> coAccountReceiveDetails = new ArrayList<CoExpAccountReceiveDetails>();
        if (c !=null && c.getCount() > 0) {
            c.moveToFirst();
            do {
                CoExpAccountReceiveDetails coAccountReceiveDetail = new CoExpAccountReceiveDetails();
                coAccountReceiveDetail.setId(c.getInt(c.getColumnIndex(db.COLUMN_cash_receive_coa_exp_details_id)));
                coAccountReceiveDetail.setReceive_id(c.getInt(c.getColumnIndex(db.COLUMN_cash_receive_coa_exp_details_received_id)));
                coAccountReceiveDetail.setAccount_name(c.getString(c.getColumnIndex(db.COLUMN_cash_receive_coa_exp_details_account_name)));
                coAccountReceiveDetail.setRemarks(c.getString(c.getColumnIndex(db.COLUMN_cash_receive_coa_exp_details_remarks)));
                coAccountReceiveDetail.setAmount(c.getDouble(c.getColumnIndex(db.COLUMN_cash_receive_coa_exp_details_amount)));
                coAccountReceiveDetail.setUuid_id(c.getString(c.getColumnIndex(db.COLUMN_cash_receive_coa_exp_details_uuid)));
                coAccountReceiveDetail.setPo_id(c.getString(c.getColumnIndex(db.COLUMN_cash_receive_coa_exp_details_po_id)));
                coAccountReceiveDetail.setIf_data_synced(c.getInt(c.getColumnIndex(db.COLUMN_cash_receive_coa_exp_details_if_data_synced)));
                coAccountReceiveDetails.add(coAccountReceiveDetail);
            } while (c.moveToNext());
        }
        return coAccountReceiveDetails;
    }

    public void setPo_id(String po_id) {
        this.po_id = po_id;
    }

    public void setUuid_id(String uuid_id) {
        this.uuid = uuid_id;
    }

    public String getPo_id() {
        return po_id;
    }

    public String getUuid_id() {
        return uuid;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setReceive_id(int receive_id) {
        this.receive_id = receive_id;
    }

    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

    public int getId() {
        return id;
    }

    public int getReceive_id() {
        return receive_id;
    }

    public String getAccount_name() {
        return account_name;
    }

    public String getRemarks() {
        return remarks;
    }

    public double getAmount() {
        return amount;
    }

    public String getDate_time() {
        return date_time;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setIf_data_synced(int if_data_synced) {
        this.if_data_synced = if_data_synced;
    }

    public int getIf_data_synced() {
        return if_data_synced;
    }

    public String getUuid() {
        return uuid;
    }
}
