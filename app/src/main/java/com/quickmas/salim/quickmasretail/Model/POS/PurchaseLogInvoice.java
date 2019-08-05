package com.quickmas.salim.quickmasretail.Model.POS;

import android.content.ContentValues;
import android.database.Cursor;

import com.quickmas.salim.quickmasretail.Database.DBInitialization;

import java.util.ArrayList;

/**
 * Created by Forhad on 12/10/2018.
 */

public class PurchaseLogInvoice {
    public int id;
    public String supplier;
    public int sku_quantity;
    public int total_quantity;
    public double total_amount;
    public double total_tax;
    public double total_discount;
    public double net_payable;
    public double amount_paid;
    public String entry_by;
    public String entry_date;
    public int status;
    public int if_data_synced;


    ContentValues getContectValue(DBInitialization db)
    {
        ContentValues insertValues = new ContentValues();
        if(this.getId()>0)
        {
            insertValues.put(db.COLUMN_purchase_log_invoice_id, this.getId());
        }
        insertValues.put(db.COLUMN_purchase_log_invoice_suppler, this.getSupplier());
        insertValues.put(db.COLUMN_purchase_log_invoice_sku_quantity, this.getSku_quantity());
        insertValues.put(db.COLUMN_purchase_log_invoice_total_quantity, this.getTotal_quantity());
        insertValues.put(db.COLUMN_purchase_log_invoice_total_amount, this.getTotal_amount());
        insertValues.put(db.COLUMN_purchase_log_invoice_unit_total_tax, this.getTotal_tax());
        insertValues.put(db.COLUMN_purchase_log_invoice_total_discount, this.getTotal_discount());
        insertValues.put(db.COLUMN_purchase_log_invoice_net_payable, this.getNet_payable());
        insertValues.put(db.COLUMN_purchase_log_invoice_amount_paid, this.getAmount_paid());
        insertValues.put(db.COLUMN_purchase_log_invoice_entry_by, this.getEntry_by());
        insertValues.put(db.COLUMN_purchase_log_invoice_entry_date, this.getEntry_date());
        insertValues.put(db.COLUMN_purchase_log_invoice_status, this.getStatus());
        insertValues.put(db.COLUMN_purchase_log_if_data_synced, this.getIf_data_synced());
        return  insertValues;
    }

    public void insert(DBInitialization db)
    {
        String con = db.COLUMN_purchase_log_invoice_id+"="+this.getId();
        db.insertData(getContectValue(db), db.TABLE_purchase_log_invoice,con);
    }
    public void update(DBInitialization db)
    {
        db.updateData(getContectValue(db), db.TABLE_purchase_log_invoice, db.COLUMN_purchase_log_invoice_id+"="+this.getId());
    }
    public static void update(DBInitialization db,String data,String con)
    {
        db.updateData(data,con, db.TABLE_purchase_log_invoice);
    }
    public static int getCount(DBInitialization db)
    {
        return db.getDataCount(db.TABLE_purchase_log_invoice,"1=1");
    }

    public static ArrayList<PurchaseLogInvoice> select(DBInitialization db, String con)
    {
        Cursor c = db.getData("*", db.TABLE_purchase_log_invoice,con,"");
        ArrayList<PurchaseLogInvoice> purchaseLogs = new ArrayList<PurchaseLogInvoice>();
        if (c !=null && c.getCount() > 0) {
            c.moveToFirst();
            do {
                PurchaseLogInvoice purchaseLog = new PurchaseLogInvoice();
                purchaseLog.setId(c.getInt(c.getColumnIndex(db.COLUMN_purchase_log_invoice_id)));
                purchaseLog.setSupplier(c.getString(c.getColumnIndex(db.COLUMN_purchase_log_invoice_suppler)));
                purchaseLog.setSku_quantity(c.getInt(c.getColumnIndex(db.COLUMN_purchase_log_invoice_sku_quantity)));
                purchaseLog.setTotal_quantity(c.getInt(c.getColumnIndex(db.COLUMN_purchase_log_invoice_total_quantity)));
                purchaseLog.setTotal_amount(c.getDouble(c.getColumnIndex(db.COLUMN_purchase_log_invoice_total_amount)));
                purchaseLog.setTotal_tax(c.getDouble(c.getColumnIndex(db.COLUMN_purchase_log_invoice_unit_total_tax)));
                purchaseLog.setTotal_discount(c.getDouble(c.getColumnIndex(db.COLUMN_purchase_log_invoice_total_discount)));
                purchaseLog.setNet_payable(c.getDouble(c.getColumnIndex(db.COLUMN_purchase_log_invoice_net_payable)));
                purchaseLog.setAmount_paid(c.getDouble(c.getColumnIndex(db.COLUMN_purchase_log_invoice_amount_paid)));
                purchaseLog.setEntry_by(c.getString(c.getColumnIndex(db.COLUMN_purchase_log_invoice_entry_by)));
                purchaseLog.setEntry_date(c.getString(c.getColumnIndex(db.COLUMN_purchase_log_invoice_entry_date)));
                purchaseLog.setStatus(c.getInt(c.getColumnIndex(db.COLUMN_purchase_log_invoice_status)));
                purchaseLog.setIf_data_synced(c.getInt(c.getColumnIndex(db.COLUMN_purchase_log_if_data_synced)));
                purchaseLogs.add(purchaseLog);
            } while (c.moveToNext());
        }
        return purchaseLogs;
    }
    public static int getMaxId(DBInitialization db)
    {
        return db.getMax(db.TABLE_purchase_log_invoice,db.COLUMN_purchase_log_invoice_id, "1=1");
    }
    public void setId(int id) {
        this.id = id;
    }

    public void setSku_quantity(int sku_quantity) {
        this.sku_quantity = sku_quantity;
    }

    public void setTotal_quantity(int total_quantity) {
        this.total_quantity = total_quantity;
    }

    public void setTotal_amount(double total_amount) {
        this.total_amount = total_amount;
    }

    public void setTotal_tax(double total_tax) {
        this.total_tax = total_tax;
    }

    public void setTotal_discount(double total_discount) {
        this.total_discount = total_discount;
    }

    public void setNet_payable(double net_payable) {
        this.net_payable = net_payable;
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

    public void setAmount_paid(double amount_paid) {
        this.amount_paid = amount_paid;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getSupplier() {
        return supplier;
    }

    public double getAmount_paid() {
        return amount_paid;
    }

    public int getStatus() {
        return status;
    }

    public int getId() {
        return id;
    }

    public int getSku_quantity() {
        return sku_quantity;
    }

    public int getTotal_quantity() {
        return total_quantity;
    }

    public double getTotal_amount() {
        return total_amount;
    }

    public double getTotal_tax() {
        return total_tax;
    }

    public double getTotal_discount() {
        return total_discount;
    }

    public double getNet_payable() {
        return net_payable;
    }

    public String getEntry_by() {
        return entry_by;
    }

    public String getEntry_date() {
        return entry_date;
    }

    public void setIf_data_synced(int if_data_synced) {
        this.if_data_synced = if_data_synced;
    }

    public int getIf_data_synced() {
        return if_data_synced;
    }
}
