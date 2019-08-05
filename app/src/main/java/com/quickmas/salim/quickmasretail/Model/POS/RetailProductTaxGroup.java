package com.quickmas.salim.quickmasretail.Model.POS;

import android.content.ContentValues;
import android.database.Cursor;

import com.quickmas.salim.quickmasretail.Database.DBInitialization;

import java.util.ArrayList;

/**
 * Created by Forhad on 27/09/2018.
 */

public class RetailProductTaxGroup {
    int id;
    String name;
    double percentage;

    ContentValues getContectValue(DBInitialization db)
    {
        ContentValues insertValues = new ContentValues();
        if(this.getId()>0) {
            insertValues.put(db.COLUMN_retail_purchase_tax_group_id, this.getId());
        }
        insertValues.put(db.COLUMN_retail_purchase_tax_group_name, this.getName());
        insertValues.put(db.COLUMN_retail_purchase_tax_group_percentage, this.getPercentage());
        return  insertValues;
    }

    public void insert(DBInitialization db)
    {
        db.insertData(getContectValue(db),db.TABLE_retail_purchase_tax_group,db.COLUMN_retail_purchase_tax_group_name+"='"+this.getName()+"'");
    }
    public void update(DBInitialization db)
    {
        db.updateData(getContectValue(db),db.TABLE_retail_purchase_tax_group,db.COLUMN_retail_purchase_tax_group_name+"='"+this.getName()+"'");
    }
    public static ArrayList<RetailProductTaxGroup> select(DBInitialization db, String con)
    {
        Cursor c = db.getData("*",db.TABLE_retail_purchase_tax_group,con,"");
        ArrayList<RetailProductTaxGroup> retailProductTaxGroups = new ArrayList<RetailProductTaxGroup>();
        try {
            if (c != null && c.getCount() > 0) {
                c.moveToFirst();
                do {
                    RetailProductTaxGroup retailProductTaxGroup = new RetailProductTaxGroup();
                    retailProductTaxGroup.setId(c.getInt(c.getColumnIndex(db.COLUMN_retail_purchase_tax_group_id)));
                    retailProductTaxGroup.setName(c.getString(c.getColumnIndex(db.COLUMN_retail_purchase_tax_group_name)));
                    retailProductTaxGroup.setPercentage(c.getDouble(c.getColumnIndex(db.COLUMN_retail_purchase_tax_group_percentage)));
                    retailProductTaxGroups.add(retailProductTaxGroup);
                } while (c.moveToNext());
            }
        }catch (Exception e){}
        return retailProductTaxGroups;
    }

    public static ArrayList<String> getAllPurchaseTax(DBInitialization db)
    {
        ArrayList<String> list = new ArrayList<>();
        ArrayList<RetailProductTaxGroup> retailProductManufactures = RetailProductTaxGroup.select(db,"1=1");
        for(RetailProductTaxGroup retailProductManufacture : retailProductManufactures)
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

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public double getPercentage() {
        return percentage;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
