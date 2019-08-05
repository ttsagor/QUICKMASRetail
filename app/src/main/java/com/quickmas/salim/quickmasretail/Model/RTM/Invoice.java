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
 * Created by Forhad on 07/04/2018.
 */

public class Invoice {
    private int id;
    private int invoice_id;
    private int outlet_id;
    private String route;
    private int product_id;
    private int quantity;
    private double unit_price;
    private String invoice_date;
    private int status;
    public static String prefix="INV-";
    ContentValues getContectValue(DBInitialization db)
    {
        ContentValues insertValues = new ContentValues();
        if(this.getId()>0)
        {
            insertValues.put(COLUMN_i_id, this.getId());
        }
        insertValues.put(COLUMN_i_invoice_id, this.getInvoice_id());
        insertValues.put(COLUMN_i_outlet_id, this.getOutlet_id());
        insertValues.put(COLUMN_i_route, this.getRoute());
        insertValues.put(COLUMN_i_product_id, this.getProduct_id());
        insertValues.put(COLUMN_i_quantity, this.getQuantity());
        insertValues.put(COLUMN_i_unit_price, this.getUnit_price());
        insertValues.put(COLUMN_i_invoice_date, this.getInvoice_date());
        insertValues.put(COLUMN_i_status, this.getStatus());
        return  insertValues;
    }

    public void insert(DBInitialization db)
    {
        String con = COLUMN_i_invoice_id+"="+this.getInvoice_id()+" AND "+COLUMN_i_product_id+"="+this.getProduct_id()+" AND "+COLUMN_i_status+"=0";
        db.insertData(getContectValue(db),db.TABLE_Invoice,con);
    }

    public void update(DBInitialization db)
    {
        String con = COLUMN_i_invoice_id+"="+this.getInvoice_id()+" AND "+COLUMN_i_product_id+"="+this.getProduct_id()+" AND "+COLUMN_i_status+"=0";
        db.updateData(getContectValue(db),db.TABLE_Invoice,db.COLUMN_o_id+"="+this.getId());
    }
    public static void update(DBInitialization db,String data,String con)
    {
        db.updateData(data,con, db.TABLE_Invoice);
    }

    public static int count(DBInitialization db, String con)
    {
        return db. getDataCount(TABLE_Invoice,con);
    }

    public static int sumData(DBInitialization db,String sel, String con)
    {
        return db.sumData(sel, TABLE_Invoice, con);
    }

    public static ArrayList<Invoice> getPendingInvoices(DBInitialization db)
    {
        return select(db, COLUMN_i_status+"=0");
    }

    public static int getTotalInvoice(DBInitialization db)
    {
        Cursor c = db.getData("*",TABLE_Invoice,db.COLUMN_i_status+"!=0"+" OR "+db.COLUMN_i_status+"!=4",db.COLUMN_i_invoice_id);
        return c.getCount();
    }
    public static ArrayList<Invoice> select(DBInitialization db, String con)
    {
        Cursor c = db.getData("*",db.TABLE_Invoice,con,"");
        ArrayList<Invoice> invoices = new ArrayList<Invoice>();
        if (c !=null && c.getCount() > 0) {
            c.moveToFirst();
            do {
                Invoice invoice = new Invoice();
                invoice.setId(c.getInt(c.getColumnIndex(db.COLUMN_o_id)));
                invoice.setInvoice_id(c.getInt(c.getColumnIndex(db.COLUMN_i_invoice_id)));
                invoice.setOutlet_id(c.getInt(c.getColumnIndex(db.COLUMN_i_outlet_id)));
                invoice.setRoute(c.getString(c.getColumnIndex(db.COLUMN_i_route)));
                invoice.setProduct_id(c.getInt(c.getColumnIndex(db.COLUMN_i_product_id)));
                invoice.setQuantity(c.getInt(c.getColumnIndex(db.COLUMN_i_quantity)));
                invoice.setUnit_price(c.getFloat(c.getColumnIndex(db.COLUMN_i_unit_price)));
                invoice.setInvoice_date(c.getString(c.getColumnIndex(db.COLUMN_i_invoice_date)));
                invoice.setStatus(c.getInt(c.getColumnIndex(db.COLUMN_i_status)));
                invoices.add(invoice);
            } while (c.moveToNext());
        }
        return invoices;
    }

    public static void deletePendingInvoices(DBInitialization db)
    {
        db.deleteData( TABLE_Invoice ,COLUMN_i_status+"=0");
    }

    public static int getMaxInvoice(DBInitialization db)
    {
        return db.getMax(TABLE_Invoice,COLUMN_i_invoice_id, db.COLUMN_i_status + "!=0");
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getRoute() {
        return route;
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

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setUnit_price(double unit_price) {
        this.unit_price = unit_price;
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

    public int getProduct_id() {
        return product_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getUnit_price() {
        return unit_price;
    }

    public String getInvoice_date() {
        return invoice_date;
    }

    public int getStatus() {
        return status;
    }
}
