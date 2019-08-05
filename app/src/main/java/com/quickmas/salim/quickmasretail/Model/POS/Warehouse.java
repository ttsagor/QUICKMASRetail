package com.quickmas.salim.quickmasretail.Model.POS;

import android.content.ContentValues;
import android.database.Cursor;

import com.quickmas.salim.quickmasretail.Database.DBInitialization;

import java.util.ArrayList;

/**
 * Created by Forhad on 17/09/2018.
 */

public class Warehouse {
    public int id;
    public String name;

    ContentValues getContectValue(DBInitialization db)
    {
        ContentValues insertValues = new ContentValues();
        insertValues.put(db.COLUMN_warehouse_id, this.getId());
        insertValues.put(db.COLUMN_warehouse_name, this.getName());
        return  insertValues;
    }

    public void insert(DBInitialization db)
    {
        db.insertData(getContectValue(db),db.TABLE_warehouse,db.COLUMN_warehouse_id+"="+this.getId());
    }
    public void update(DBInitialization db)
    {
        db.updateData(getContectValue(db),db.TABLE_warehouse,db.COLUMN_warehouse_id+"="+this.getId());
    }
    public static ArrayList<Warehouse> select(DBInitialization db, String con)
    {
        Cursor c = db.getData("*",db.TABLE_warehouse,con,"");
        ArrayList<Warehouse> warehouses = new ArrayList<Warehouse>();
        try {
            if (c != null && c.getCount() > 0) {
                c.moveToFirst();
                do {
                    Warehouse warehouse = new Warehouse();
                    warehouse.setId(c.getInt(c.getColumnIndex(db.COLUMN_warehouse_id)));
                    warehouse.setName(c.getString(c.getColumnIndex(db.COLUMN_warehouse_name)));
                    warehouses.add(warehouse);
                } while (c.moveToNext());
            }
        }catch (Exception e){}
        return warehouses;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
