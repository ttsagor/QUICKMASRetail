package com.quickmas.salim.quickmasretail.Model.POS;

import android.content.ContentValues;
import android.database.Cursor;

import com.quickmas.salim.quickmasretail.Database.DBInitialization;

import java.util.ArrayList;

/**
 * Created by Forhad on 27/09/2018.
 */

public class RetailProductBrand {
    int id;
    String name;

    ContentValues getContectValue(DBInitialization db)
    {
        ContentValues insertValues = new ContentValues();
        if(this.getId()>0) {
            insertValues.put(db.COLUMN_retail_product_brand_id, this.getId());
        }
        insertValues.put(db.COLUMN_retail_product_brand_name, this.getName());
        return  insertValues;
    }

    public void insert(DBInitialization db)
    {
        db.insertData(getContectValue(db),db.TABLE_retail_product_brand,db.COLUMN_retail_product_brand_name+"='"+this.getName()+"'");
    }
    public void update(DBInitialization db)
    {
        db.updateData(getContectValue(db),db.TABLE_retail_product_brand,db.COLUMN_retail_product_brand_name+"='"+this.getName()+"'");
    }
    public static ArrayList<RetailProductBrand> select(DBInitialization db, String con)
    {
        Cursor c = db.getData("*",db.TABLE_retail_product_brand,con,"");
        ArrayList<RetailProductBrand> retailProductBrands = new ArrayList<RetailProductBrand>();
        try {
            if (c != null && c.getCount() > 0) {
                c.moveToFirst();
                do {
                    RetailProductBrand retailProductBrand = new RetailProductBrand();
                    retailProductBrand.setId(c.getInt(c.getColumnIndex(db.COLUMN_retail_product_brand_id)));
                    retailProductBrand.setName(c.getString(c.getColumnIndex(db.COLUMN_retail_product_brand_name)));
                    retailProductBrands.add(retailProductBrand);
                } while (c.moveToNext());
            }
        }catch (Exception e){}
        return retailProductBrands;
    }
    public static ArrayList<String> getAllBrand(DBInitialization db)
    {
        ArrayList<String> list = new ArrayList<>();
        ArrayList<RetailProductBrand> retailProductManufactures = RetailProductBrand.select(db,"1=1");
        for(RetailProductBrand retailProductManufacture : retailProductManufactures)
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
