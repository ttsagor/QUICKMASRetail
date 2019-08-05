package com.quickmas.salim.quickmasretail.Model.POS;

import android.content.ContentValues;
import android.database.Cursor;

import com.quickmas.salim.quickmasretail.Database.DBInitialization;

import java.util.ArrayList;

/**
 * Created by Forhad on 11/05/2018.
 */

public class SalesPerson {
    int id;
    String name;
    String fullName;
    int isSelected;
    ContentValues getContectValue(DBInitialization db)
    {
        ContentValues insertValues = new ContentValues();
        insertValues.put(db.COLUMN_pos_sales_person_id, this.getId());
        insertValues.put(db.COLUMN_pos_sales_person_name, this.getName());
        insertValues.put(db.COLUMN_pos_sales_person_full_name, this.getFullName());
        insertValues.put(db.COLUMN_pos_sales_person_isSelected, this.getIsSelected());
        return  insertValues;
    }
    public void insert(DBInitialization db)
    {
        db.insertData(getContectValue(db),db.TABLE_PosSalesPerson,db.COLUMN_pos_sales_person_name+"='"+this.getName()+"'");
    }

    public void update(DBInitialization db)
    {
        db.updateData(getContectValue(db),db.TABLE_PosSalesPerson,db.COLUMN_pos_sales_person_name+"='"+this.getName()+"'");
    }
    public static ArrayList<SalesPerson> select(DBInitialization db, String con)
    {
        Cursor c = db.getData("*",db.TABLE_PosSalesPerson,con,"");
        ArrayList<SalesPerson> salesPeoples = new ArrayList<SalesPerson>();
        if (c !=null && c.getCount() > 0) {
            c.moveToFirst();
            do {
                SalesPerson salesPeople = new SalesPerson();
                salesPeople.setId(c.getInt(c.getColumnIndex(db.COLUMN_pos_sales_person_id)));
                salesPeople.setName(c.getString(c.getColumnIndex(db.COLUMN_pos_sales_person_name)));
                salesPeople.setFullName(c.getString(c.getColumnIndex(db.COLUMN_pos_sales_person_full_name)));
                salesPeople.setIsSelected(c.getInt(c.getColumnIndex(db.COLUMN_pos_sales_person_isSelected)));
                salesPeoples.add(salesPeople);
            } while (c.moveToNext());
        }
        return salesPeoples;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setIsSelected(int isSelected) {
        this.isSelected = isSelected;
    }

    public int getIsSelected() {
        return isSelected;
    }
}
