package com.quickmas.salim.quickmasretail.Model.POS;

import android.content.ContentValues;
import android.database.Cursor;

import com.quickmas.salim.quickmasretail.Database.DBInitialization;

import java.util.ArrayList;

/**
 * Created by Forhad on 10/10/2018.
 */

public class Supplier {
    public int id;
    public String name;
    public String address;
    public String address2;
    public String address3;
    public String mobile;
    public String email;

    ContentValues getContectValue(DBInitialization db)
    {
        ContentValues insertValues = new ContentValues();
        if(this.getId()>0)
        {
            insertValues.put(db.COLUMN_supplier_id, this.getId());
        }
        insertValues.put(db.COLUMN_supplier_name, this.getName());
        insertValues.put(db.COLUMN_supplier_address, this.getAddress());
        insertValues.put(db.COLUMN_supplier_address2, this.getAddress2());
        insertValues.put(db.COLUMN_supplier_address3, this.getAddress3());
        insertValues.put(db.COLUMN_supplier_mobile, this.getMobile());
        insertValues.put(db.COLUMN_supplier_email, this.getEmail());
        return  insertValues;
    }

    public void insert(DBInitialization db)
    {
        String con = db.COLUMN_supplier_name+"='"+this.getName()+"'";
        db.insertData(getContectValue(db), db.TABLE_supplier,con);
    }
    public void update(DBInitialization db)
    {
        db.updateData(getContectValue(db), db.TABLE_supplier,  db.COLUMN_supplier_name+"='"+this.getName()+"'");
    }
    public static void update(DBInitialization db,String data,String con)
    {
        db.updateData(data,con, db.TABLE_supplier);
    }
    public static int getCount(DBInitialization db)
    {
        return db.getDataCount(db.TABLE_supplier,"1=1");
    }

    public static ArrayList<Supplier> select(DBInitialization db, String con)
    {
        Cursor c = db.getData("*", db.TABLE_supplier,con,"");
        ArrayList<Supplier> suppliers = new ArrayList<Supplier>();
        if (c !=null && c.getCount() > 0) {
            c.moveToFirst();
            do {
                Supplier supplier = new Supplier();
                supplier.setId(c.getInt(c.getColumnIndex(db.COLUMN_supplier_id)));
                supplier.setName(c.getString(c.getColumnIndex(db.COLUMN_supplier_name)));
                supplier.setAddress(c.getString(c.getColumnIndex(db.COLUMN_supplier_address)));
                supplier.setAddress2(c.getString(c.getColumnIndex(db.COLUMN_supplier_address2)));
                supplier.setAddress3(c.getString(c.getColumnIndex(db.COLUMN_supplier_address3)));
                supplier.setMobile(c.getString(c.getColumnIndex(db.COLUMN_supplier_mobile)));
                supplier.setEmail(c.getString(c.getColumnIndex(db.COLUMN_supplier_email)));
                suppliers.add(supplier);
            } while (c.moveToNext());
        }
        return suppliers;
    }
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getAddress2() {
        return address2;
    }

    public String getAddress3() {
        return address3;
    }

    public String getMobile() {
        return mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public void setAddress3(String address3) {
        this.address3 = address3;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
