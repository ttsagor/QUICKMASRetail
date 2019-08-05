package com.quickmas.salim.quickmasretail.Model.POS;

import android.content.ContentValues;
import android.database.Cursor;

import com.quickmas.salim.quickmasretail.Database.DBInitialization;

import java.util.ArrayList;

import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_customer_id;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.TABLE_PosCustomer;

/**
 * Created by Forhad on 11/05/2018.
 */

public class PosCustomer {
    int id;
    String name="";
    String full_name="";
    String phone="";
    Double balance;

    ContentValues getContectValue(DBInitialization db)
    {
        ContentValues insertValues = new ContentValues();
        insertValues.put(COLUMN_pos_customer_id, this.getId());
        insertValues.put(db.COLUMN_pos_customer_name, this.getName());
        insertValues.put(db.COLUMN_pos_customer_full_name, this.getFull_name());
        insertValues.put(db.COLUMN_pos_customer_phone, this.getPhone());
        insertValues.put(db.COLUMN_pos_customer_balance, this.getBalance());
        return  insertValues;
    }
    public void insert(DBInitialization db)
    {
        db.insertData(getContectValue(db), TABLE_PosCustomer,db.COLUMN_pos_customer_name+"=\""+this.getName()+"\"");
    }

    public void update(DBInitialization db)
    {
        db.updateData(getContectValue(db), TABLE_PosCustomer,db.COLUMN_pos_customer_name+"=\""+this.getName()+"\"");
    }

    public static ArrayList<PosCustomer> select(DBInitialization db, String con)
    {
        Cursor c = db.getData("*", TABLE_PosCustomer,con,"");
        ArrayList<PosCustomer> customers = new ArrayList<PosCustomer>();
        if (c !=null && c.getCount() > 0) {
            c.moveToFirst();
            do {
                PosCustomer customer = new PosCustomer();
                customer.setId(c.getInt(c.getColumnIndex(COLUMN_pos_customer_id)));
                customer.setName(c.getString(c.getColumnIndex(db.COLUMN_pos_customer_name)));
                customer.setFull_name(c.getString(c.getColumnIndex(db.COLUMN_pos_customer_full_name)));
                customer.setPhone(c.getString(c.getColumnIndex(db.COLUMN_pos_customer_phone)));
                customer.setBalance(c.getDouble(c.getColumnIndex(db.COLUMN_pos_customer_balance)));
                customers.add(customer);
            } while (c.moveToNext());
        }
        return customers;
    }

    public static int getMaxCustomerId(DBInitialization db)
    {
        return db.getMax(TABLE_PosCustomer,COLUMN_pos_customer_id, "1=1");
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Double getBalance() {
        return balance;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getFull_name() {
        return full_name;
    }

    public String getPhone() {
        return phone;
    }
}
