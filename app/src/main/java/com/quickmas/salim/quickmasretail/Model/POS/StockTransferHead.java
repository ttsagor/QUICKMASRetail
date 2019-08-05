package com.quickmas.salim.quickmasretail.Model.POS;

import android.content.ContentValues;
import android.database.Cursor;

import com.quickmas.salim.quickmasretail.Database.DBInitialization;

import java.util.ArrayList;

import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_invoiceID;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_stock_transfer_details_product_id;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_stock_transfer_details_transfer_id;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.TABLE_PosInvoice;

/**
 * Created by Forhad on 22/09/2018.
 */

public class StockTransferHead{
    public int id=0;
    public int total_quantity=0;
    public double total_amount=0.0;
    public int total_delivered_qty=0;
    public int total_received_qty=0;
    public String transfer_by="";
    public String transfer_to="";
    public int status=0;
    public String date_time="";
    public String entry_by="";
    public String pos_by="";


    ContentValues getContectValue(DBInitialization db)
    {
        ContentValues insertValues = new ContentValues();
        if(this.getId()>0) {
            insertValues.put(db.COLUMN_stock_transfer_id, this.getId());
        }
        insertValues.put(db.COLUMN_stock_transfer_product_quantity, this.getTotal_quantity());
        insertValues.put(db.COLUMN_stock_transfer_product_price, this.getTotal_amount());
        insertValues.put(db.COLUMN_stock_transfer_transfer_by, this.getTransfer_by());
        insertValues.put(db.COLUMN_stock_transfer_status, this.getStatus());
        insertValues.put(db.COLUMN_stock_transfer_product_transfer_to, this.getTransfer_to());
        insertValues.put(db.COLUMN_stock_transfer_product_date_time, this.getDate_time());
        insertValues.put(db.COLUMN_stock_transfer_product_entry_by, this.getEntry_by());
        insertValues.put(db.COLUMN_stock_transfer_product_total_delivered_qty, this.getTotal_delivered_qty());
        insertValues.put(db.COLUMN_stock_transfer_product_total_received_qty, this.getTotal_received_qty());
        insertValues.put(db.COLUMN_stock_transfer_product_pos_by, this.getPos_by());
        return  insertValues;
    }

    public void insert(DBInitialization db)
    {
        String con = db.COLUMN_stock_transfer_id+"="+this.getId();
        db.insertData(getContectValue(db),db.TABLE_stock_transfer,con);
    }
    public void update(DBInitialization db)
    {
        String con = db.COLUMN_stock_transfer_id+"="+this.getId();
        db.updateData(getContectValue(db),db.TABLE_stock_transfer,con);
    }
    public static void update(DBInitialization db,String data,String con)
    {
        db.updateData(data,con, db.TABLE_stock_transfer);
    }
    public static ArrayList<StockTransferHead> select(DBInitialization db, String con)
    {
        Cursor c = db.getData("*",db.TABLE_stock_transfer,con,"");
        ArrayList<StockTransferHead> stockTransferHeads = new ArrayList<StockTransferHead>();
        try {
            if (c != null && c.getCount() > 0) {
                c.moveToFirst();
                do {
                    StockTransferHead transferDetails = new StockTransferHead();
                    transferDetails.setId(c.getInt(c.getColumnIndex(db.COLUMN_stock_transfer_id)));
                    transferDetails.setTotal_quantity(c.getInt(c.getColumnIndex(db.COLUMN_stock_transfer_product_quantity)));
                    transferDetails.setTotal_amount(c.getInt(c.getColumnIndex(db.COLUMN_stock_transfer_product_price)));
                    transferDetails.setTransfer_by(c.getString(c.getColumnIndex(db.COLUMN_stock_transfer_transfer_by)));
                    transferDetails.setStatus(c.getInt(c.getColumnIndex(db.COLUMN_stock_transfer_status)));
                    transferDetails.setDate_time(c.getString(c.getColumnIndex(db.COLUMN_stock_transfer_product_date_time)));
                    transferDetails.setTransfer_to(c.getString(c.getColumnIndex(db.COLUMN_stock_transfer_product_transfer_to)));
                    transferDetails.setEntry_by(c.getString(c.getColumnIndex(db.COLUMN_stock_transfer_product_entry_by)));
                    transferDetails.setTotal_delivered_qty(c.getInt(c.getColumnIndex(db.COLUMN_stock_transfer_product_total_delivered_qty)));
                    transferDetails.setTotal_received_qty(c.getInt(c.getColumnIndex(db.COLUMN_stock_transfer_product_total_received_qty)));
                    transferDetails.setPos_by(c.getString(c.getColumnIndex(db.COLUMN_stock_transfer_product_pos_by)));
                    stockTransferHeads.add(transferDetails);
                } while (c.moveToNext());
            }
        }catch (Exception e){}
        return stockTransferHeads;
    }
    public static int getMax(DBInitialization db)
    {
        return db.getMax(db.TABLE_stock_transfer,db.COLUMN_stock_transfer_id, "1=1");
    }

    public static void delete(DBInitialization db,String con)
    {
        db.deleteData(db.TABLE_stock_transfer,con);
    }

    public void delete(DBInitialization db)
    {
        String con = db.COLUMN_stock_transfer_id+"="+this.getId();
        db.deleteData(db.TABLE_stock_transfer,con);
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTotal_quantity(int total_quantity) {
        this.total_quantity = total_quantity;
    }

    public void setTotal_amount(double total_amount) {
        this.total_amount = total_amount;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

    public void setTransfer_by(String transfer_by) {
        this.transfer_by = transfer_by;
    }

    public String getTransfer_by() {
        return transfer_by;
    }

    public int getId() {
        return id;
    }

    public int getTotal_quantity() {
        return total_quantity;
    }

    public double getTotal_amount() {
        return total_amount;
    }

    public int getStatus() {
        return status;
    }

    public String getDate_time() {
        return date_time;
    }

    public String getTransfer_to() {
        return transfer_to;
    }

    public void setTransfer_to(String transfer_to) {
        this.transfer_to = transfer_to;
    }

    public void setEntry_by(String entry_by) {
        this.entry_by = entry_by;
    }

    public String getEntry_by() {
        return entry_by;
    }

    public int getTotal_delivered_qty() {
        return total_delivered_qty;
    }

    public void setTotal_delivered_qty(int total_delivered_qty) {
        this.total_delivered_qty = total_delivered_qty;
    }

    public void setTotal_received_qty(int total_received_qty) {
        this.total_received_qty = total_received_qty;
    }

    public int getTotal_received_qty() {
        return total_received_qty;
    }

    public void setPos_by(String pos_by) {
        this.pos_by = pos_by;
    }

    public String getPos_by() {
        return pos_by;
    }
}
