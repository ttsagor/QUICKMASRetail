package com.quickmas.salim.quickmasretail.Model.RTM;

import android.content.ContentValues;
import android.database.Cursor;

import com.quickmas.salim.quickmasretail.Database.DBInitialization;

import java.util.ArrayList;

import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_i_id;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_i_invoice_date;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_i_invoice_id;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_i_outlet_id;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_i_product_id;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_i_quantity;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_i_route;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_i_status;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_i_unit_price;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.TABLE_Invoice;

/**
 * Created by Forhad on 19/10/2018.
 */

public class FreeProductInvoice {
    public int id;
    public int invoice_id;
    public int outlet_id;
    public String route;
    public int product_id;
    public int product_quantity;
    public String remark;
    public String details;
    public String invoice_by;
    public String invoice_date;
    public int status;
    public String prefix="Inv-";
    ContentValues getContectValue(DBInitialization db)
    {
        ContentValues insertValues = new ContentValues();
        if(this.getId()>0)
        {
            insertValues.put(db.COLUMN_free_Invoice_id, this.getId());
        }
        insertValues.put(db.COLUMN_free_Invoice_invoice_id, this.getInvoice_id());
        insertValues.put(db.COLUMN_free_Invoice_outlet_id, this.getOutlet_id());
        insertValues.put(db.COLUMN_free_Invoice_route, this.getRoute());
        insertValues.put(db.COLUMN_free_Invoice_product_id, this.getProduct_id());
        insertValues.put(db.COLUMN_free_Invoice_product_quantity, this.getProduct_quantity());
        insertValues.put(db.COLUMN_free_Invoice_product_remark, this.getRemark());
        insertValues.put(db.COLUMN_free_Invoice_product_details, this.getDetails());
        insertValues.put(db.COLUMN_free_Invoice_invoice_by, this.getInvoice_by());
        insertValues.put(db.COLUMN_free_Invoice_invoice_date, this.getInvoice_date());
        insertValues.put(db.COLUMN_free_Invoice_status, this.getStatus());
        return  insertValues;
    }

    public void insert(DBInitialization db)
    {
        String con = db.COLUMN_free_Invoice_invoice_id+"="+this.getInvoice_id()+" AND "+db.COLUMN_free_Invoice_product_id+"="+this.getProduct_id()+" AND "+db.COLUMN_free_Invoice_status+"=0";
        db.insertData(getContectValue(db),db.TABLE_free_Invoice,con);
    }

    public void update(DBInitialization db)
    {
        String con = db.COLUMN_free_Invoice_invoice_id+"="+this.getInvoice_id()+" AND "+db.COLUMN_free_Invoice_product_id+"="+this.getProduct_id()+" AND "+db.COLUMN_free_Invoice_status+"=0";
        db.updateData(getContectValue(db),db.TABLE_free_Invoice,db.COLUMN_free_Invoice_invoice_id+"="+this.getId());
    }
    public static void update(DBInitialization db,String data,String con)
    {
        db.updateData(data,con, db.TABLE_free_Invoice);
    }

    public static int count(DBInitialization db, String con)
    {
        return db. getDataCount(db.TABLE_free_Invoice,con);
    }

    public static int sumData(DBInitialization db,String sel, String con)
    {
        return db.sumData(sel, db.TABLE_free_Invoice, con);
    }

    public static ArrayList<FreeProductInvoice> getPendingInvoices(DBInitialization db)
    {
        return select(db, db.COLUMN_free_Invoice_status+"=0");
    }

    public static int getTotalInvoice(DBInitialization db)
    {
        Cursor c = db.getData("*",db.TABLE_free_Invoice,db.COLUMN_free_Invoice_status+"!=0"+" OR "+db.COLUMN_free_Invoice_status+"!=4",db.COLUMN_free_Invoice_invoice_id);
        return c.getCount();
    }
    public static ArrayList<FreeProductInvoice> select(DBInitialization db, String con)
    {
        Cursor c = db.getData("*",db.TABLE_free_Invoice,con,"");
        ArrayList<FreeProductInvoice> invoices = new ArrayList<FreeProductInvoice>();
        if (c !=null && c.getCount() > 0) {
            c.moveToFirst();
            do {
                FreeProductInvoice invoice = new FreeProductInvoice();
                invoice.setId(c.getInt(c.getColumnIndex(db.COLUMN_free_Invoice_id)));
                invoice.setInvoice_id(c.getInt(c.getColumnIndex(db.COLUMN_free_Invoice_invoice_id)));
                invoice.setOutlet_id(c.getInt(c.getColumnIndex(db.COLUMN_free_Invoice_outlet_id)));
                invoice.setRoute(c.getString(c.getColumnIndex(db.COLUMN_free_Invoice_route)));
                invoice.setProduct_id(c.getInt(c.getColumnIndex(db.COLUMN_free_Invoice_product_id)));
                invoice.setProduct_quantity(c.getInt(c.getColumnIndex(db.COLUMN_free_Invoice_product_quantity)));
                invoice.setRemark(c.getString(c.getColumnIndex(db.COLUMN_free_Invoice_product_remark)));
                invoice.setDetails(c.getString(c.getColumnIndex(db.COLUMN_free_Invoice_product_details)));
                invoice.setInvoice_by(c.getString(c.getColumnIndex(db.COLUMN_free_Invoice_invoice_by)));
                invoice.setInvoice_date(c.getString(c.getColumnIndex(db.COLUMN_free_Invoice_invoice_date)));
                invoice.setStatus(c.getInt(c.getColumnIndex(db.COLUMN_free_Invoice_status)));
                invoices.add(invoice);
            } while (c.moveToNext());
        }
        return invoices;
    }

    public static void deletePendingInvoices(DBInitialization db)
    {
        db.deleteData( db.TABLE_free_Invoice ,db.COLUMN_free_Invoice_status+"=0");
    }

    public static int getMaxInvoice(DBInitialization db)
    {
        return db.getMax(db.TABLE_free_Invoice,db.COLUMN_free_Invoice_invoice_id, db.COLUMN_free_Invoice_status + "!=0");
    }

    public String getInvoice_by() {
        return invoice_by;
    }

    public void setInvoice_by(String invoice_by) {
        this.invoice_by = invoice_by;
    }

    public void setProduct_quantity(int product_quantity) {
        this.product_quantity = product_quantity;
    }

    public int getProduct_quantity() {
        return product_quantity;
    }

    public String getRemark() {
        return remark;
    }

    public String getDetails() {
        return details;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setInvoice_id(int invoice_id) {
        this.invoice_id = invoice_id;
    }

    public void setOutlet_id(int outlet_id) {
        this.outlet_id = outlet_id;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
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

    public int getInvoice_id() {
        return invoice_id;
    }

    public int getOutlet_id() {
        return outlet_id;
    }

    public String getRoute() {
        return route;
    }

    public int getProduct_id() {
        return product_id;
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

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}
