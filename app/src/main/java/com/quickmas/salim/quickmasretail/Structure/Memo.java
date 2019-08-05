package com.quickmas.salim.quickmasretail.Structure;

import android.database.Cursor;

import com.quickmas.salim.quickmasretail.Database.DBInitialization;

import java.util.ArrayList;

/**
 * Created by Forhad on 12/04/2018.
 */

public class Memo {
    private int invoice_id;
    private int quantity;
    private Double amount;
    private int status;


    public static ArrayList<Memo> getMemoList(DBInitialization db)
    {
        String sel = db.COLUMN_i_invoice_id+", SUM("+db.COLUMN_i_quantity+"), SUM("+db.COLUMN_i_quantity+"*"+db.COLUMN_i_unit_price+"),"+db.COLUMN_i_status;
        Cursor c = db.getData(sel,db.TABLE_Invoice,db.COLUMN_i_status+"!=0",db.COLUMN_i_invoice_id);

        ArrayList<Memo> invoices = new ArrayList<Memo>();
        if (c.getCount() > 0) {
            c.moveToFirst();

            do {
                Memo invoice = new Memo();
                invoice.setInvoice_id(c.getInt(0));
                invoice.setQuantity(c.getInt(1));
                invoice.setAmount(c.getDouble(2));
                invoice.setStatus(c.getInt(3));
                invoices.add(invoice);
            } while (c.moveToNext());
        }
        db.close();
        return invoices;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setInvoice_id(int invoice_id) {
        this.invoice_id = invoice_id;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public int getInvoice_id() {
        return invoice_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public Double getAmount() {
        return amount;
    }
}
