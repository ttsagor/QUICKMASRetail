package com.quickmas.salim.quickmasretail.Model.POS;

import android.content.ContentValues;
import android.database.Cursor;

import com.quickmas.salim.quickmasretail.Database.DBInitialization;

import java.util.ArrayList;

/**
 * Created by Forhad on 27/09/2018.
 */

public class RetailProductSalesTaxGroup {
    int id;
    String name;

    ContentValues getContectValue(DBInitialization db)
    {
        ContentValues insertValues = new ContentValues();
        if(this.getId()>0) {
            insertValues.put(db.COLUMN_retail_sales_tax_group_id, this.getId());
        }
        insertValues.put(db.COLUMN_retail_sales_tax_group_name, this.getName());
        return  insertValues;
    }

    public void insert(DBInitialization db)
    {
        db.insertData(getContectValue(db),db.TABLE_retail_sales_tax_group,db.COLUMN_retail_sales_tax_group_name+"='"+this.getName()+"'");
    }
    public void update(DBInitialization db)
    {
        db.updateData(getContectValue(db),db.TABLE_retail_sales_tax_group,db.COLUMN_retail_sales_tax_group_name+"='"+this.getName()+"'");
    }
    public static ArrayList<RetailProductSalesTaxGroup> select(DBInitialization db, String con)
    {
        Cursor c = db.getData("*",db.TABLE_retail_sales_tax_group,con,"");
        ArrayList<RetailProductSalesTaxGroup> retailProductSalesTaxGroups = new ArrayList<RetailProductSalesTaxGroup>();
        try {
            if (c != null && c.getCount() > 0) {
                c.moveToFirst();
                do {
                    RetailProductSalesTaxGroup retailProductSalesTaxGroup = new RetailProductSalesTaxGroup();
                    retailProductSalesTaxGroup.setId(c.getInt(c.getColumnIndex(db.COLUMN_retail_sales_tax_group_id)));
                    retailProductSalesTaxGroup.setName(c.getString(c.getColumnIndex(db.COLUMN_retail_sales_tax_group_name)));
                    retailProductSalesTaxGroups.add(retailProductSalesTaxGroup);
                } while (c.moveToNext());
            }
        }catch (Exception e){}
        return retailProductSalesTaxGroups;
    }

    public static ArrayList<String> getAllSalesTax(DBInitialization db)
    {
        ArrayList<String> list = new ArrayList<>();
        ArrayList<RetailProductSalesTaxGroup> retailProductManufactures = RetailProductSalesTaxGroup.select(db,"1=1");
        for(RetailProductSalesTaxGroup retailProductManufacture : retailProductManufactures)
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
