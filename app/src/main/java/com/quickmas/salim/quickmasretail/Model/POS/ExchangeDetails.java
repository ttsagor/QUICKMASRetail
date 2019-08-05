package com.quickmas.salim.quickmasretail.Model.POS;

import android.content.ContentValues;
import android.database.Cursor;

import com.quickmas.salim.quickmasretail.Database.DBInitialization;

import java.util.ArrayList;

import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_exchange_details_new_inv_id;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.TABLE_exchange_details;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.TABLE_pos_invoice_head;

/**
 * Created by Forhad on 17/09/2018.
 */

public class ExchangeDetails {
    public int id;
    public String previous_inv_id;
    public String new_inv_id;
    public String product_id;
    public String product_name;
    public int product_quantity;
    public double product_amount;
    public int status;
    public String date_time;


    ContentValues getContectValue(DBInitialization db)
    {
        ContentValues insertValues = new ContentValues();
        if(this.getId()>0) {
            insertValues.put(db.COLUMN_exchange_details_id, this.getId());
        }
        insertValues.put(db.COLUMN_exchange_details_previous_inv_id, this.getPrevious_inv_id());
        insertValues.put(COLUMN_exchange_details_new_inv_id, this.getNew_inv_id());
        insertValues.put(db.COLUMN_exchange_details_product_id, this.getProduct_id());
        insertValues.put(db.COLUMN_exchange_details_product_name, this.getProduct_name());
        insertValues.put(db.COLUMN_exchange_details_product_quantity, this.getProduct_quantity());
        insertValues.put(db.COLUMN_exchange_details_product_amount, this.getProduct_amount());
        insertValues.put(db.COLUMN_exchange_details_status, this.getStatus());
        insertValues.put(db.COLUMN_exchange_details_date_time, this.getDate_time());
        return  insertValues;
    }

    public void insert(DBInitialization db)
    {
        db.insertData(getContectValue(db), TABLE_exchange_details,db.COLUMN_exchange_details_id+"="+this.getId());
    }
    public void update(DBInitialization db)
    {
        db.updateData(getContectValue(db), TABLE_exchange_details,db.COLUMN_exchange_details_id+"="+this.getId());
    }
    public static ArrayList<ExchangeDetails> select(DBInitialization db, String con)
    {
        Cursor c = db.getData("*", TABLE_exchange_details,con,"");
        ArrayList<ExchangeDetails> exchangeDetails = new ArrayList<ExchangeDetails>();
        try {
            if (c != null && c.getCount() > 0) {
                c.moveToFirst();
                do {
                    ExchangeDetails exchangeDetails1 = new ExchangeDetails();
                    exchangeDetails1.setId(c.getInt(c.getColumnIndex(db.COLUMN_exchange_details_id)));
                    exchangeDetails1.setPrevious_inv_id(c.getString(c.getColumnIndex(db.COLUMN_exchange_details_previous_inv_id)));
                    exchangeDetails1.setNew_inv_id(c.getString(c.getColumnIndex(COLUMN_exchange_details_new_inv_id)));
                    exchangeDetails1.setProduct_id(c.getString(c.getColumnIndex(db.COLUMN_exchange_details_product_id)));
                    exchangeDetails1.setProduct_name(c.getString(c.getColumnIndex(db.COLUMN_exchange_details_product_name)));
                    exchangeDetails1.setProduct_quantity(c.getInt(c.getColumnIndex(db.COLUMN_exchange_details_product_quantity)));
                    exchangeDetails1.setProduct_amount(c.getDouble(c.getColumnIndex(db.COLUMN_exchange_details_product_amount)));
                    exchangeDetails1.setStatus(c.getInt(c.getColumnIndex(db.COLUMN_exchange_details_status)));
                    exchangeDetails1.setDate_time(c.getString(c.getColumnIndex(db.COLUMN_exchange_details_date_time)));
                    exchangeDetails.add(exchangeDetails1);
                } while (c.moveToNext());
            }
        }catch (Exception e){}
        return exchangeDetails;
    }
    public static int getCountByInvoice(DBInitialization db,String invoice_id)
    {
        return db.getDataCount(TABLE_exchange_details,COLUMN_exchange_details_new_inv_id+"='"+invoice_id+"'");
    }
    public void setId(int id) {
        this.id = id;
    }

    public void setPrevious_inv_id(String previous_inv_id) {
        this.previous_inv_id = previous_inv_id;
    }

    public void setNew_inv_id(String new_inv_id) {
        this.new_inv_id = new_inv_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public void setProduct_quantity(int product_quantity) {
        this.product_quantity = product_quantity;
    }

    public void setProduct_amount(double product_amount) {
        this.product_amount = product_amount;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

    public int getId() {
        return id;
    }

    public String getPrevious_inv_id() {
        return previous_inv_id;
    }

    public String getNew_inv_id() {
        return new_inv_id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public int getProduct_quantity() {
        return product_quantity;
    }

    public double getProduct_amount() {
        return product_amount;
    }

    public int getStatus() {
        return status;
    }

    public String getDate_time() {
        return date_time;
    }
}
