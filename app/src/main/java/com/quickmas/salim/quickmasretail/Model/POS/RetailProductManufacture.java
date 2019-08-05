package com.quickmas.salim.quickmasretail.Model.POS;

import android.content.ContentValues;
import android.database.Cursor;

import com.quickmas.salim.quickmasretail.Database.DBInitialization;

import java.util.ArrayList;

/**
 * Created by Forhad on 27/09/2018.
 */

public class RetailProductManufacture {
    int id;
    String name;

    ContentValues getContectValue(DBInitialization db)
    {
        ContentValues insertValues = new ContentValues();
        if(this.getId()>0) {
            insertValues.put(db.COLUMN_retail_product_manufacture_id, this.getId());
        }
        insertValues.put(db.COLUMN_retail_product_manufacture_name, this.getName());
        return  insertValues;
    }

    public void insert(DBInitialization db)
    {
        db.insertData(getContectValue(db),db.TABLE_retail_product_manufacture,db.COLUMN_retail_product_manufacture_name+"='"+this.getName()+"'");
    }
    public void update(DBInitialization db)
    {
        db.updateData(getContectValue(db),db.TABLE_retail_product_manufacture,db.COLUMN_retail_product_manufacture_name+"='"+this.getName()+"'");
    }
    public static ArrayList<RetailProductManufacture> select(DBInitialization db, String con)
    {
        Cursor c = db.getData("*",db.TABLE_retail_product_manufacture,con,"");
        ArrayList<RetailProductManufacture> retailProductManufactures = new ArrayList<RetailProductManufacture>();
        try {
            if (c != null && c.getCount() > 0) {
                c.moveToFirst();
                do {
                    RetailProductManufacture retailProductManufacture = new RetailProductManufacture();
                    retailProductManufacture.setId(c.getInt(c.getColumnIndex(db.COLUMN_retail_product_manufacture_id)));
                    retailProductManufacture.setName(c.getString(c.getColumnIndex(db.COLUMN_retail_product_manufacture_name)));
                    retailProductManufactures.add(retailProductManufacture);
                } while (c.moveToNext());
            }
        }catch (Exception e){}
        return retailProductManufactures;
    }

    public static ArrayList<String> getAllManufacture(DBInitialization db)
    {
        ArrayList<String> list = new ArrayList<>();
        ArrayList<RetailProductManufacture> retailProductManufactures = RetailProductManufacture.select(db,"1=1");
        for(RetailProductManufacture retailProductManufacture : retailProductManufactures)
        {
            list.add(retailProductManufacture.getName());
        }
        return list;
    }



    public void setId(int id) {
        this.id = id;
    }

    public void setName(String bankName) {
        this.name = bankName;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
