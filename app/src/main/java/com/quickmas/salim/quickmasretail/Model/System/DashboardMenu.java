package com.quickmas.salim.quickmasretail.Model.System;

import android.content.ContentValues;
import android.database.Cursor;

import com.quickmas.salim.quickmasretail.Database.DBInitialization;

import java.util.ArrayList;

/**
 * Created by Forhad on 25/03/2018.
 */

public class DashboardMenu {
    int id;
    String varname;
    String image;
    String text;
    String category;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setVarname(String varname) {
        this.varname = varname;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getVarname() {
        return varname;
    }

    public String getImage() {
        return image;
    }

    public String getText() {
        return text;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    ContentValues getContectValue(DBInitialization db)
    {
        ContentValues insertValues = new ContentValues();

        insertValues.put(db.COLUMN_dash_image_id, this.getId());
        insertValues.put(db.COLUMN_dash_image_varname, this.getVarname());
        insertValues.put(db.COLUMN_dash_image_image, this.getImage());
        insertValues.put(db.COLUMN_dash_image_text, this.getText());
        insertValues.put(db.COLUMN_dash_image_category, this.getCategory());
        return  insertValues;
    }

    public void insert(DBInitialization db)
    {
        db.insertData(getContectValue(db),db.TABLE_DASH_IMG,db.COLUMN_dash_image_id+"="+this.getId());
    }

    public void update(DBInitialization db)
    {
        db.updateData(getContectValue(db),db.TABLE_DASH_IMG,db.COLUMN_dash_image_id+"="+this.getId());
    }


    public static ArrayList<DashboardMenu> select(DBInitialization db,String con)
    {
        Cursor c = db.getData("*",db.TABLE_DASH_IMG,con,"");
        ArrayList<DashboardMenu> menus = new ArrayList<DashboardMenu>();
        if (c !=null && c.getCount() > 0) {
            c.moveToFirst();
            do {
                DashboardMenu cMenu = new DashboardMenu();
                cMenu.setId(c.getInt(c.getColumnIndex(db.COLUMN_dash_image_id)));
                cMenu.setVarname(c.getString(c.getColumnIndex(db.COLUMN_dash_image_varname)));
                cMenu.setImage(c.getString(c.getColumnIndex(db.COLUMN_dash_image_image)));
                cMenu.setText(c.getString(c.getColumnIndex(db.COLUMN_dash_image_text)));
                cMenu.setCategory(c.getString(c.getColumnIndex(db.COLUMN_dash_image_category)));
                menus.add(cMenu);
            } while (c.moveToNext());
        }
        return menus;
    }

    public static DashboardMenu selectByVarname(DBInitialization db,String varname)
    {
        try {
            return select(db, db.COLUMN_dash_image_varname + "='" + varname + "'").get(0);
        }catch (Exception e)
        {
            return new DashboardMenu();
        }
    }

}
