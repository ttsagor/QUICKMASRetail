package com.quickmas.salim.quickmasretail.Model.POS;

import android.content.ContentValues;
import android.database.Cursor;

import com.quickmas.salim.quickmasretail.Database.DBInitialization;

import java.util.ArrayList;

/**
 * Created by Forhad on 10/10/2018.
 */

public class PurchaseLog {
    public int id;
    public int entry_id;
    public String supplier;
    public String sku;
    public int quantity;
    public double unit_price;
    public double amount;
    public String entry_by;
    public String entry_date;
    public int status;
    public int if_data_sycned;

    ContentValues getContectValue(DBInitialization db)
    {
        ContentValues insertValues = new ContentValues();
        if(this.getId()>0)
        {
            insertValues.put(db.COLUMN_purchase_log_id, this.getId());
        }
        insertValues.put(db.COLUMN_purchase_log_entry_id, this.getEntry_id());
        insertValues.put(db.COLUMN_purchase_log_supplier, this.getSupplier());
        insertValues.put(db.COLUMN_purchase_log_sku, this.getSku());
        insertValues.put(db.COLUMN_purchase_log_quantity, this.getQuantity());
        insertValues.put(db.COLUMN_purchase_log_unit_price, this.getUnit_price());
        insertValues.put(db.COLUMN_purchase_log_amount, this.getAmount());
        insertValues.put(db.COLUMN_purchase_log_entry_by, this.getEntry_by());
        insertValues.put(db.COLUMN_purchase_log_entry_date, this.getEntry_date());
        insertValues.put(db.COLUMN_purchase_log_status, this.getStatus());
        insertValues.put(db.COLUMN_purchase_if_data_sycned, this.getIf_data_sycned());
        return  insertValues;
    }

    public void insert(DBInitialization db)
    {
        //String con = db.COLUMN_purchase_log_id+"="+this.getId();
        String con = db.COLUMN_purchase_log_entry_id+"="+this.getEntry_id()+" AND "+db.COLUMN_purchase_log_sku+"='"+this.getSku()+"'";
        db.insertData(getContectValue(db), db.TABLE_purchase_log,con);
    }
    public void update(DBInitialization db)
    {
        String con = db.COLUMN_purchase_log_entry_id+"="+this.getEntry_id()+" AND "+db.COLUMN_purchase_log_sku+"='"+this.getSku()+"'";
        db.updateData(getContectValue(db), db.TABLE_purchase_log, con);
    }
    public static void update(DBInitialization db,String data,String con)
    {
        db.updateData(data,con, db.TABLE_purchase_log);
    }
    public static int getCount(DBInitialization db)
    {
        return db.getDataCount(db.TABLE_purchase_log,"1=1");
    }

    public static ArrayList<PurchaseLog> select(DBInitialization db, String con)
    {
        Cursor c = db.getData("*", db.TABLE_purchase_log,con,"");
        ArrayList<PurchaseLog> purchaseLogs = new ArrayList<PurchaseLog>();
        if (c !=null && c.getCount() > 0) {
            c.moveToFirst();
            do {
                PurchaseLog purchaseLog = new PurchaseLog();
                purchaseLog.setId(c.getInt(c.getColumnIndex(db.COLUMN_purchase_log_id)));
                purchaseLog.setEntry_id(c.getInt(c.getColumnIndex(db.COLUMN_purchase_log_entry_id)));
                purchaseLog.setSupplier(c.getString(c.getColumnIndex(db.COLUMN_purchase_log_supplier)));
                purchaseLog.setSku(c.getString(c.getColumnIndex(db.COLUMN_purchase_log_sku)));
                purchaseLog.setQuantity(c.getInt(c.getColumnIndex(db.COLUMN_purchase_log_quantity)));
                purchaseLog.setUnit_price(c.getDouble(c.getColumnIndex(db.COLUMN_purchase_log_unit_price)));
                purchaseLog.setAmount(c.getDouble(c.getColumnIndex(db.COLUMN_purchase_log_amount)));
                purchaseLog.setEntry_by(c.getString(c.getColumnIndex(db.COLUMN_purchase_log_entry_by)));
                purchaseLog.setEntry_date(c.getString(c.getColumnIndex(db.COLUMN_purchase_log_entry_date)));
                purchaseLog.setStatus(c.getInt(c.getColumnIndex(db.COLUMN_purchase_log_status)));
                purchaseLog.setIf_data_sycned(c.getInt(c.getColumnIndex(db.COLUMN_purchase_if_data_sycned)));
                purchaseLogs.add(purchaseLog);
            } while (c.moveToNext());
        }
        return purchaseLogs;
    }
    public void setId(int id) {
        this.id = id;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setUnit_price(double unit_price) {
        this.unit_price = unit_price;
    }



    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setEntry_by(String entry_by) {
        this.entry_by = entry_by;
    }

    public void setEntry_date(String entry_date) {
        this.entry_date = entry_date;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setEntry_id(int entry_id) {
        this.entry_id = entry_id;
    }

    public int getEntry_id() {
        return entry_id;
    }

    public int getId() {
        return id;
    }

    public String getSupplier() {
        return supplier;
    }

    public String getSku() {
        return sku;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getUnit_price() {
        return unit_price;
    }



    public double getAmount() {
        return amount;
    }

    public String getEntry_by() {
        return entry_by;
    }

    public String getEntry_date() {
        return entry_date;
    }

    public int getStatus() {
        return status;
    }

    public void setIf_data_sycned(int if_data_sycned) {
        this.if_data_sycned = if_data_sycned;
    }

    public int getIf_data_sycned() {
        return if_data_sycned;
    }
}
