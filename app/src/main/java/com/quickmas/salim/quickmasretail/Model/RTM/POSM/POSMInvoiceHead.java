package com.quickmas.salim.quickmasretail.Model.RTM.POSM;

import android.content.ContentValues;
import android.database.Cursor;

import com.quickmas.salim.quickmasretail.Database.DBInitialization;
import com.quickmas.salim.quickmasretail.Model.RTM.CRMInvoiceHead;

import java.util.ArrayList;

/**
 * Created by Forhad on 23/10/2018.
 */

public class POSMInvoiceHead {
    public int id;
    public String photo;
    public int quantity;
    public String outlet;
    public String invoice_by;
    public String invoice_date;
    public int status;

    ContentValues getContectValue(DBInitialization db)
    {
        ContentValues insertValues = new ContentValues();
        if(this.getId()>0)
        {
            insertValues.put(db.COLUMN_posm_Invoice_head_id, this.getId());
        }
        insertValues.put(db.COLUMN_posm_Invoice_head_outlet, this.getOutlet());
        insertValues.put(db.COLUMN_posm_Invoice_head_quantity, this.getQuantity());
        insertValues.put(db.COLUMN_posm_Invoice_head_photo, this.getPhoto());
        insertValues.put(db.COLUMN_posm_Invoice_head_invoice_by, this.getInvoice_by());
        insertValues.put(db.COLUMN_posm_Invoice_head_invoice_date, this.getInvoice_date());
        insertValues.put(db.COLUMN_posm_Invoice_head_status, this.getStatus());
        return  insertValues;
    }

    public void insert(DBInitialization db)
    {
        String con = db.COLUMN_posm_Invoice_head_id+"="+this.getId();
        db.insertData(getContectValue(db),db.TABLE_posm_Invoice_head,con);
    }

    public void update(DBInitialization db)
    {
        db.updateData(getContectValue(db),db.TABLE_posm_Invoice_head,db.COLUMN_posm_Invoice_head_id+"="+this.getId());
    }
    public static void update(DBInitialization db,String data,String con)
    {
        db.updateData(data,con, db.TABLE_posm_Invoice_head);
    }

    public static int count(DBInitialization db, String con)
    {
        return db. getDataCount(db.TABLE_posm_Invoice_head,con);
    }

    public static ArrayList<POSMInvoiceHead> getPendingInvoices(DBInitialization db)
    {
        return select(db, db.COLUMN_posm_Invoice_head_status+"=0");
    }


    public static ArrayList<POSMInvoiceHead> select(DBInitialization db, String con)
    {
        Cursor c = db.getData("*",db.TABLE_posm_Invoice_head,con,"");
        ArrayList<POSMInvoiceHead> invoices = new ArrayList<POSMInvoiceHead>();
        if (c !=null && c.getCount() > 0) {
            c.moveToFirst();
            do {
                POSMInvoiceHead invoice = new POSMInvoiceHead();
                invoice.setId(c.getInt(c.getColumnIndex(db.COLUMN_posm_Invoice_head_id)));
                invoice.setQuantity(c.getInt(c.getColumnIndex(db.COLUMN_posm_Invoice_head_quantity)));
                invoice.setOutlet(c.getString(c.getColumnIndex(db.COLUMN_posm_Invoice_head_outlet)));
                invoice.setPhoto(c.getString(c.getColumnIndex(db.COLUMN_posm_Invoice_head_photo)));
                invoice.setInvoice_by(c.getString(c.getColumnIndex(db.COLUMN_posm_Invoice_head_invoice_by)));
                invoice.setInvoice_date(c.getString(c.getColumnIndex(db.COLUMN_posm_Invoice_head_invoice_date)));
                invoice.setStatus(c.getInt(c.getColumnIndex(db.COLUMN_posm_Invoice_head_status)));
                invoices.add(invoice);
            } while (c.moveToNext());
        }
        return invoices;
    }

    public static void deletePendingInvoices(DBInitialization db)
    {
        db.deleteData( db.TABLE_posm_Invoice_head ,db.COLUMN_posm_Invoice_head_status+"=0");
    }

    public static int getMaxInvoice(DBInitialization db)
    {
        return db.getMax(db.TABLE_posm_Invoice_head,db.COLUMN_posm_Invoice_head_id, db.COLUMN_posm_Invoice_head_status + "!=0");
    }
    public void setId(int id) {
        this.id = id;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setOutlet(String outlet) {
        this.outlet = outlet;
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

    public int getId() {
        return id;
    }

    public String getPhoto() {
        return photo;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getOutlet() {
        return outlet;
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
}
