package com.quickmas.salim.quickmasretail.Model.POS;

import android.content.ContentValues;
import android.database.Cursor;

import com.quickmas.salim.quickmasretail.Database.DBInitialization;

import java.util.ArrayList;


/**
 * Created by Forhad on 04/10/2018.
 */

public class NewRetailProductEntryHead {
    public int id;
    public String sku;
    public int quantity;
    public int photoQuantity;
    public String entry_by;
    public String entry_date;
    public int status;

    ContentValues getContectValue(DBInitialization db)
    {
        ContentValues insertValues = new ContentValues();
        if(this.getId()>0)
        {
            insertValues.put(db.COLUMN_new_retail_product_head_id, this.getId());
        }
        insertValues.put(db.COLUMN_new_retail_product_head_sku, this.getSku());
        insertValues.put(db.COLUMN_new_retail_product_head_quantity, this.getQuantity());
        insertValues.put(db.COLUMN_new_retail_product_head_sub_photoQuantity, this.getPhotoQuantity());
        insertValues.put(db.COLUMN_new_retail_product_head_entry_by, this.getEntry_by());
        insertValues.put(db.COLUMN_new_retail_product_head_entry_date, this.getEntry_date());
        insertValues.put(db.COLUMN_new_retail_product_head_status, this.getStatus());
        return  insertValues;
    }

    public void insert(DBInitialization db)
    {
        String con = db.COLUMN_new_retail_product_head_id+"="+this.getId();
        db.insertData(getContectValue(db), db.TABLE_new_retail_product_head,con);
    }
    public void update(DBInitialization db)
    {
        db.updateData(getContectValue(db), db.TABLE_new_retail_product_head, db.COLUMN_new_retail_product_head_id+"="+this.getId());
    }
    public static void update(DBInitialization db,String data,String con)
    {
        db.updateData(data,con, db.TABLE_new_retail_product_head);
    }
    public static int getCount(DBInitialization db)
    {
        return db.getDataCount(db.TABLE_new_retail_product_head,"1=1");
    }

    public static ArrayList<NewRetailProductEntryHead> select(DBInitialization db, String con)
    {
        Cursor c = db.getData("*", db.TABLE_new_retail_product_head,con,"");
        ArrayList<NewRetailProductEntryHead> newRetailProductEntryHeads = new ArrayList<NewRetailProductEntryHead>();
        if (c !=null && c.getCount() > 0) {
            c.moveToFirst();
            do {
                NewRetailProductEntryHead newRetailProductEntryHead = new NewRetailProductEntryHead();
                newRetailProductEntryHead.setId(c.getInt(c.getColumnIndex(db.COLUMN_new_retail_product_head_id)));
                newRetailProductEntryHead.setSku(c.getString(c.getColumnIndex(db.COLUMN_new_retail_product_head_sku)));
                newRetailProductEntryHead.setQuantity(c.getInt(c.getColumnIndex(db.COLUMN_new_retail_product_head_quantity)));
                newRetailProductEntryHead.setPhotoQuantity(c.getInt(c.getColumnIndex(db.COLUMN_new_retail_product_head_sub_photoQuantity)));
                newRetailProductEntryHead.setEntry_by(c.getString(c.getColumnIndex(db.COLUMN_new_retail_product_head_entry_by)));
                newRetailProductEntryHead.setEntry_date(c.getString(c.getColumnIndex(db.COLUMN_new_retail_product_head_entry_date)));
                newRetailProductEntryHead.setStatus(c.getInt(c.getColumnIndex(db.COLUMN_new_retail_product_head_status)));
                newRetailProductEntryHeads.add(newRetailProductEntryHead);
            } while (c.moveToNext());
        }
        return newRetailProductEntryHeads;
    }
    public static int getMaxId(DBInitialization db)
    {
        return db.getMax(db.TABLE_new_retail_product_head,db.COLUMN_new_retail_product_head_id, "1=1");
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPhotoQuantity(int photoQuantity) {
        this.photoQuantity = photoQuantity;
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

    public int getStatus() {
        return status;
    }

    public int getId() {
        return id;
    }

    public String getSku() {
        return sku;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getPhotoQuantity() {
        return photoQuantity;
    }

    public String getEntry_by() {
        return entry_by;
    }

    public String getEntry_date() {
        return entry_date;
    }
}
