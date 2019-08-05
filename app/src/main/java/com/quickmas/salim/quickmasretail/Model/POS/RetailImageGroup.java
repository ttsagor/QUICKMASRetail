package com.quickmas.salim.quickmasretail.Model.POS;

import android.content.ContentValues;
import android.database.Cursor;

import com.quickmas.salim.quickmasretail.Database.DBInitialization;
import com.quickmas.salim.quickmasretail.Utility.DebugHelper;

import java.util.ArrayList;

/**
 * Created by Forhad on 27/09/2018.
 */

public class RetailImageGroup {
    public int id;
    public String group_name;
    public int width;
    public int height;
    public String created_by;
    public String date_time;

    ContentValues getContectValue(DBInitialization db)
    {
        ContentValues insertValues = new ContentValues();
        if(this.getId()>0) {
            insertValues.put(db.COLUMN_product_add_retail_image_group_id, this.getId());
        }
        insertValues.put(db.COLUMN_product_add_retail_image_group_name, this.getGroup_name());
        insertValues.put(db.COLUMN_product_add_retail_image_group_height, this.getHeight());
        insertValues.put(db.COLUMN_product_add_retail_image_group_width, this.getWidth());
        insertValues.put(db.COLUMN_product_add_retail_image_group_entry_by, this.getCreated_by());
        insertValues.put(db.COLUMN_product_add_retail_image_group_date_time, this.getDate_time());
        return  insertValues;
    }

    public void insert(DBInitialization db)
    {
        String con = db.COLUMN_product_add_retail_image_group_name+"='"+this.getGroup_name()+"'";
        db.insertData(getContectValue(db),db.TABLE_product_add_retail_image_group,con);
    }
    public void update(DBInitialization db)
    {
        String con = db.COLUMN_product_add_retail_image_group_name+"='"+this.getGroup_name()+"'";
        db.updateData(getContectValue(db),db.TABLE_product_add_retail_image_group,con);
    }
    public static ArrayList<RetailImageGroup> select(DBInitialization db, String con)
    {
        Cursor c = db.getData("*",db.TABLE_product_add_retail_image_group,con,"");
        ArrayList<RetailImageGroup> retailImageGroups = new ArrayList<RetailImageGroup>();
        try {
            if (c != null && c.getCount() > 0) {
                c.moveToFirst();
                do {
                    RetailImageGroup retailImageGroup = new RetailImageGroup();
                    retailImageGroup.setId(c.getInt(c.getColumnIndex(db.COLUMN_product_add_retail_image_group_id)));
                    retailImageGroup.setGroup_name(c.getString(c.getColumnIndex(db.COLUMN_product_add_retail_image_group_name)));
                    retailImageGroup.setHeight(c.getInt(c.getColumnIndex(db.COLUMN_product_add_retail_image_group_height)));
                    retailImageGroup.setWidth(c.getInt(c.getColumnIndex(db.COLUMN_product_add_retail_image_group_width)));
                    retailImageGroup.setCreated_by(c.getString(c.getColumnIndex(db.COLUMN_product_add_retail_image_group_entry_by)));
                    retailImageGroup.setDate_time(c.getString(c.getColumnIndex(db.COLUMN_product_add_retail_image_group_date_time)));
                    retailImageGroups.add(retailImageGroup);
                } while (c.moveToNext());
            }
        }catch (Exception e){}
        return retailImageGroups;
    }

    public static RetailImageGroup getImageGroup(DBInitialization db, String name)
    {
        String con = db.COLUMN_product_add_retail_image_group_name+"='"+name+"'";

        ArrayList<RetailImageGroup> retailImageGroups = RetailImageGroup.select(db,con);
        if(retailImageGroups.size()>0)
        {
            return retailImageGroups.get(0);
        }
        return new RetailImageGroup();
    }
    public static ArrayList<String> getAllImageGroup(DBInitialization db)
    {
        ArrayList<String> list = new ArrayList<>();
        ArrayList<RetailImageGroup> retailProductManufactures = RetailImageGroup.select(db,"1=1");
        for(RetailImageGroup retailProductManufacture : retailProductManufactures)
        {
            list.add(retailProductManufacture.getGroup_name());
        }
        return list;
    }
    public void setId(int id) {
        this.id = id;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

    public int getId() {
        return id;
    }

    public String getGroup_name() {
        return group_name;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getCreated_by() {
        return created_by;
    }

    public String getDate_time() {
        return date_time;
    }
}
