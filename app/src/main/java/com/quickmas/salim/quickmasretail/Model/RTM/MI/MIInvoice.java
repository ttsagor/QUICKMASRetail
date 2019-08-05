package com.quickmas.salim.quickmasretail.Model.RTM.MI;

import android.content.ContentValues;
import android.database.Cursor;

import com.quickmas.salim.quickmasretail.Database.DBInitialization;
import com.quickmas.salim.quickmasretail.Model.RTM.CRMInvoice;

import java.util.ArrayList;

/**
 * Created by Forhad on 25/10/2018.
 */

public class MIInvoice {
    public int id;
    public int invoice_id;
    public int product_id;
    public String description;
    public String comment;
    public String invoice_by;
    public String invoice_date;
    public int status;
    public String prefix="Inv-";

    ContentValues getContectValue(DBInitialization db)
    {
        ContentValues insertValues = new ContentValues();
        if(this.getId()>0)
        {
            insertValues.put(db.COLUMN_mi_Invoice_id, this.getId());
        }
        insertValues.put(db.COLUMN_mi_Invoice_invoice_id, this.getInvoice_id());
        insertValues.put(db.COLUMN_mi_Invoice_product_id, this.getProduct_id());
        insertValues.put(db.COLUMN_mi_Invoice_comment, this.getComment());
        insertValues.put(db.COLUMN_mi_Invoice_description, this.getDescription());
        insertValues.put(db.COLUMN_mi_Invoice_invoice_by, this.getInvoice_by());
        insertValues.put(db.COLUMN_mi_Invoice_invoice_date, this.getInvoice_date());
        insertValues.put(db.COLUMN_mi_Invoice_status, this.getStatus());
        return  insertValues;
    }

    public void insert(DBInitialization db)
    {
        String con = db.COLUMN_mi_Invoice_invoice_id+"="+this.getInvoice_id()+" AND "+db.COLUMN_mi_Invoice_product_id+"="+this.getProduct_id()+" AND "+db.COLUMN_mi_Invoice_status+"=0";
        db.insertData(getContectValue(db),db.TABLE_mi_Invoice,con);
    }

    public void update(DBInitialization db)
    {
        db.updateData(getContectValue(db),db.TABLE_mi_Invoice,db.COLUMN_mi_Invoice_invoice_id+"="+this.getId());
    }
    public static void update(DBInitialization db,String data,String con)
    {
        db.updateData(data,con, db.TABLE_mi_Invoice);
    }

    public static int count(DBInitialization db, String con)
    {
        return db. getDataCount(db.TABLE_mi_Invoice,con);
    }

    public static int sumData(DBInitialization db,String sel, String con)
    {
        return db.sumData(sel, db.TABLE_mi_Invoice, con);
    }

    public static ArrayList<MIInvoice> getPendingInvoices(DBInitialization db)
    {
        return select(db, db.COLUMN_mi_Invoice_status+"=0");
    }

    public static int getTotalInvoice(DBInitialization db)
    {
        Cursor c = db.getData("*",db.TABLE_mi_Invoice,db.COLUMN_mi_Invoice_status+"!=0"+" OR "+db.COLUMN_mi_Invoice_status+"!=4",db.COLUMN_mi_Invoice_invoice_id);
        return c.getCount();
    }
    public static ArrayList<MIInvoice> select(DBInitialization db, String con)
    {
        Cursor c = db.getData("*",db.TABLE_mi_Invoice,con,"");
        ArrayList<MIInvoice> invoices = new ArrayList<MIInvoice>();
        if (c !=null && c.getCount() > 0) {
            c.moveToFirst();
            do {
                MIInvoice invoice = new MIInvoice();
                invoice.setId(c.getInt(c.getColumnIndex(db.COLUMN_mi_Invoice_id)));
                invoice.setInvoice_id(c.getInt(c.getColumnIndex(db.COLUMN_mi_Invoice_invoice_id)));
                invoice.setProduct_id(c.getInt(c.getColumnIndex(db.COLUMN_mi_Invoice_product_id)));
                invoice.setDescription(c.getString(c.getColumnIndex(db.COLUMN_mi_Invoice_description)));
                invoice.setComment(c.getString(c.getColumnIndex(db.COLUMN_mi_Invoice_comment)));
                invoice.setInvoice_by(c.getString(c.getColumnIndex(db.COLUMN_mi_Invoice_invoice_by)));
                invoice.setInvoice_date(c.getString(c.getColumnIndex(db.COLUMN_mi_Invoice_invoice_date)));
                invoice.setStatus(c.getInt(c.getColumnIndex(db.COLUMN_mi_Invoice_status)));
                invoices.add(invoice);
            } while (c.moveToNext());
        }
        return invoices;
    }

    public static void deletePendingInvoices(DBInitialization db)
    {
        db.deleteData( db.TABLE_mi_Invoice ,db.COLUMN_mi_Invoice_status+"=0");
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

    public void setComment(String comment) {
        this.comment = comment;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getComment() {
        return comment;
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

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }
}
