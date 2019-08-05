package com.quickmas.salim.quickmasretail.Model.POS;

import android.content.ContentValues;
import android.database.Cursor;

import com.quickmas.salim.quickmasretail.Database.DBInitialization;

import java.util.ArrayList;

import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_stock_transfer_details_product_id;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_stock_transfer_details_transfer_id;

/**
 * Created by Forhad on 17/09/2018.
 */

public class StockTransferDetails {
    public int id=0;
    public int transfer_id=0;
    public int product_id=0;
    public String product_name="";
    public int quantity=0;
    public double unit_price=0.0;
    public int received_qty=0;
    public int delivered_qty=0;
    public int status=0;
    public String date_time="";

    ContentValues getContectValue(DBInitialization db)
    {
        ContentValues insertValues = new ContentValues();
        if(this.getId()>0) {
            insertValues.put(db.COLUMN_stock_transfer_details_id, this.getId());
        }
        insertValues.put(COLUMN_stock_transfer_details_transfer_id, this.getTransfer_id());
        insertValues.put(COLUMN_stock_transfer_details_product_id, this.getProduct_id());
        insertValues.put(db.COLUMN_stock_transfer_details_product_name, this.getProduct_name());
        insertValues.put(db.COLUMN_stock_transfer_details_product_quantity, this.getQuantity());
        insertValues.put(db.COLUMN_stock_transfer_details_product_unit_price, this.getUnit_price());
        insertValues.put(db.COLUMN_stock_transfer_details_status, this.getStatus());
        insertValues.put(db.COLUMN_stock_transfer_details_product_date_time, this.getDate_time());
        insertValues.put(db.COLUMN_stock_transfer_details_received_qty, this.getReceived_qty());
        insertValues.put(db.COLUMN_stock_transfer_details_delivered_qty, this.getDelivered_qty());
        return  insertValues;
    }

    public void insert(DBInitialization db)
    {
        String con = COLUMN_stock_transfer_details_transfer_id+"="+this.getTransfer_id()+" AND "+db.COLUMN_stock_transfer_details_product_name+"=\""+this.getProduct_name()+"\"";
        db.insertData(getContectValue(db),db.TABLE_stock_transfer_details,con);
    }
    public void update(DBInitialization db)
    {
        String con = COLUMN_stock_transfer_details_transfer_id+"="+this.getTransfer_id()+" AND "+db.COLUMN_stock_transfer_details_product_name+"=\""+this.getProduct_name()+"\"";
        db.updateData(getContectValue(db),db.TABLE_stock_transfer_details,con);
    }
    public static void update(DBInitialization db,String data,String con)
    {
        db.updateData(data,con, db.TABLE_stock_transfer_details);
    }
    public static ArrayList<StockTransferDetails> select(DBInitialization db, String con)
    {
        Cursor c = db.getData("*",db.TABLE_stock_transfer_details,con,"");
        ArrayList<StockTransferDetails> stockTransferDetails = new ArrayList<StockTransferDetails>();
        try {
            if (c != null && c.getCount() > 0) {
                c.moveToFirst();
                do {
                    StockTransferDetails transferDetails = new StockTransferDetails();
                    transferDetails.setId(c.getInt(c.getColumnIndex(db.COLUMN_stock_transfer_details_id)));
                    transferDetails.setTransfer_id(c.getInt(c.getColumnIndex(COLUMN_stock_transfer_details_transfer_id)));
                    transferDetails.setProduct_id(c.getInt(c.getColumnIndex(COLUMN_stock_transfer_details_product_id)));
                    transferDetails.setProduct_name(c.getString(c.getColumnIndex(db.COLUMN_stock_transfer_details_product_name)));
                    transferDetails.setQuantity(c.getInt(c.getColumnIndex(db.COLUMN_stock_transfer_details_product_quantity)));
                    transferDetails.setUnit_price(c.getInt(c.getColumnIndex(db.COLUMN_stock_transfer_details_product_unit_price)));
                    transferDetails.setStatus(c.getInt(c.getColumnIndex(db.COLUMN_stock_transfer_details_status)));
                    transferDetails.setDate_time(c.getString(c.getColumnIndex(db.COLUMN_stock_transfer_details_product_date_time)));
                    transferDetails.setReceived_qty(c.getInt(c.getColumnIndex(db.COLUMN_stock_transfer_details_received_qty)));
                    transferDetails.setDelivered_qty(c.getInt(c.getColumnIndex(db.COLUMN_stock_transfer_details_delivered_qty)));
                    stockTransferDetails.add(transferDetails);
                } while (c.moveToNext());
            }
        }catch (Exception e){}
        return stockTransferDetails;
    }

    public static void delete(DBInitialization db,String con)
    {
        db.deleteData(db.TABLE_stock_transfer_details,con);
    }
    public int getId() {
        return id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getUnit_price() {
        return unit_price;
    }

    public int getStatus() {
        return status;
    }

    public String getDate_time() {
        return date_time;
    }

    public int getTransfer_id() {
        return transfer_id;
    }

    public void setTransfer_id(int transfer_id) {
        this.transfer_id = transfer_id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setUnit_price(double unit_price) {
        this.unit_price = unit_price;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

    public void setReceived_qty(int received_qty) {
        this.received_qty = received_qty;
    }

    public void setDelivered_qty(int delivered_qty) {
        this.delivered_qty = delivered_qty;
    }

    public int getReceived_qty() {
        return received_qty;
    }

    public int getDelivered_qty() {
        return delivered_qty;
    }
}
