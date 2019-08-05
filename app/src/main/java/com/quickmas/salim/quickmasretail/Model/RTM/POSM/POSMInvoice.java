package com.quickmas.salim.quickmasretail.Model.RTM.POSM;

import android.content.ContentValues;
import android.database.Cursor;

import com.quickmas.salim.quickmasretail.Database.DBInitialization;
import com.quickmas.salim.quickmasretail.Model.RTM.CRMInvoice;

import java.util.ArrayList;

/**
 * Created by Forhad on 23/10/2018.
 */

public class POSMInvoice {
    public int id;
    public int invoice_id;
    public int product_id;
    public int product_quantity;
    public String details;
    public String remark;
    public String invoice_by;
    public String invoice_date;
    public int status;
    public String prefix="Inv-";

    ContentValues getContectValue(DBInitialization db)
    {
        ContentValues insertValues = new ContentValues();
        if(this.getId()>0)
        {
            insertValues.put(db.COLUMN_posm_Invoice_id, this.getId());
        }
        insertValues.put(db.COLUMN_posm_Invoice_invoice_id, this.getInvoice_id());
        insertValues.put(db.COLUMN_posm_Invoice_product_id, this.getProduct_id());
        insertValues.put(db.COLUMN_posm_Invoice_product_quantity, this.getProduct_quantity());
        insertValues.put(db.COLUMN_posm_Invoice_comment, this.getRemark());
        insertValues.put(db.COLUMN_posm_Invoice_description, this.getDetails());
        insertValues.put(db.COLUMN_posm_Invoice_invoice_by, this.getInvoice_by());
        insertValues.put(db.COLUMN_posm_Invoice_invoice_date, this.getInvoice_date());
        insertValues.put(db.COLUMN_posm_Invoice_status, this.getStatus());
        return  insertValues;
    }

    public void insert(DBInitialization db)
    {
        String con = db.COLUMN_posm_Invoice_invoice_id+"="+this.getInvoice_id()+" AND "+db.COLUMN_posm_Invoice_product_id+"="+this.getProduct_id()+" AND "+db.COLUMN_posm_Invoice_status+"=0";
        db.insertData(getContectValue(db),db.TABLE_posm_Invoice,con);
    }

    public void update(DBInitialization db)
    {
        db.updateData(getContectValue(db),db.TABLE_posm_Invoice,db.COLUMN_posm_Invoice_invoice_id+"="+this.getId());
    }
    public static void update(DBInitialization db,String data,String con)
    {
        db.updateData(data,con, db.TABLE_posm_Invoice);
    }

    public static int count(DBInitialization db, String con)
    {
        return db. getDataCount(db.TABLE_posm_Invoice,con);
    }

    public static int sumData(DBInitialization db,String sel, String con)
    {
        return db.sumData(sel, db.TABLE_posm_Invoice, con);
    }

    public static ArrayList<POSMInvoice> getPendingInvoices(DBInitialization db)
    {
        return select(db, db.COLUMN_posm_Invoice_status+"=0");
    }

    public static int getTotalInvoice(DBInitialization db)
    {
        Cursor c = db.getData("*",db.TABLE_posm_Invoice,db.COLUMN_posm_Invoice_status+"!=0"+" OR "+db.COLUMN_posm_Invoice_status+"!=4",db.COLUMN_posm_Invoice_invoice_id);
        return c.getCount();
    }
    public static ArrayList<POSMInvoice> select(DBInitialization db, String con)
    {
        Cursor c = db.getData("*",db.TABLE_posm_Invoice,con,"");
        ArrayList<POSMInvoice> invoices = new ArrayList<POSMInvoice>();
        if (c !=null && c.getCount() > 0) {
            c.moveToFirst();
            do {
                POSMInvoice invoice = new POSMInvoice();
                invoice.setId(c.getInt(c.getColumnIndex(db.COLUMN_posm_Invoice_id)));
                invoice.setInvoice_id(c.getInt(c.getColumnIndex(db.COLUMN_posm_Invoice_invoice_id)));
                invoice.setProduct_id(c.getInt(c.getColumnIndex(db.COLUMN_posm_Invoice_product_id)));
                invoice.setProduct_quantity(c.getInt(c.getColumnIndex(db.COLUMN_posm_Invoice_product_quantity)));
                invoice.setDetails(c.getString(c.getColumnIndex(db.COLUMN_posm_Invoice_description)));
                invoice.setRemark(c.getString(c.getColumnIndex(db.COLUMN_posm_Invoice_comment)));
                invoice.setInvoice_by(c.getString(c.getColumnIndex(db.COLUMN_posm_Invoice_invoice_by)));
                invoice.setInvoice_date(c.getString(c.getColumnIndex(db.COLUMN_posm_Invoice_invoice_date)));
                invoice.setStatus(c.getInt(c.getColumnIndex(db.COLUMN_posm_Invoice_status)));
                invoices.add(invoice);
            } while (c.moveToNext());
        }
        return invoices;
    }

    public static void deletePendingInvoices(DBInitialization db)
    {
        db.deleteData( db.TABLE_posm_Invoice ,db.COLUMN_posm_Invoice_status+"=0");
    }
    public void setId(int id) {
        this.id = id;
    }

    public void setInvoice_id(int invoice_id) {
        this.invoice_id = invoice_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public void setProduct_quantity(int product_quantity) {
        this.product_quantity = product_quantity;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setInvoice_by(String invoice_by) {
        this.invoice_by = invoice_by;
    }

    public void setInvoice_date(String invoice_date) {
        this.invoice_date = invoice_date;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public int getId() {
        return id;
    }

    public int getInvoice_id() {
        return invoice_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public int getProduct_quantity() {
        return product_quantity;
    }

    public String getDetails() {
        return details;
    }

    public String getRemark() {
        return remark;
    }

    public String getInvoice_by() {
        return invoice_by;
    }

    public String getInvoice_date() {
        return invoice_date;
    }

    public int getStatus() {
        return status;
    }

    public String getPrefix() {
        return prefix;
    }
}
