package com.quickmas.salim.quickmasretail.Model.System;

import android.content.ContentValues;
import android.database.Cursor;

import com.quickmas.salim.quickmasretail.Database.DBInitialization;

import java.util.ArrayList;

/**
 * Created by Forhad on 07/09/2018.
 */

public class DashboardSubMenu {
    public int id;
    public String varname;
    public String text;
    public String new_var_name;

    ContentValues getContectValue(DBInitialization db)
    {
        ContentValues insertValues = new ContentValues();
        insertValues.put(db.COLUMN_dash_sub_image_id, this.getId());
        insertValues.put(db.COLUMN_dash_sub_varname, this.getVarname());
        insertValues.put(db.COLUMN_dash_sub_text, this.getText());
        insertValues.put(db.COLUMN_dash_sub_var, this.getNew_var_name());
        return  insertValues;
    }

    public void insert(DBInitialization db)
    {
        db.insertData(getContectValue(db),db.TABLE_DASH_SUB_MENU,db.COLUMN_dash_sub_image_id+"="+this.getId());
    }

    public void update(DBInitialization db)
    {
        db.updateData(getContectValue(db),db.TABLE_DASH_SUB_MENU,db.COLUMN_dash_sub_image_id+"="+this.getId());
    }


    public static ArrayList<DashboardSubMenu> select(DBInitialization db, String con)
    {
        Cursor c = db.getData("*",db.TABLE_DASH_SUB_MENU,con,"");
        ArrayList<DashboardSubMenu> menus = new ArrayList<DashboardSubMenu>();
        if (c !=null && c.getCount() > 0) {
            c.moveToFirst();
            do {
                DashboardSubMenu cMenu = new DashboardSubMenu();
                cMenu.setId(c.getInt(c.getColumnIndex(db.COLUMN_dash_sub_image_id)));
                cMenu.setVarname(c.getString(c.getColumnIndex(db.COLUMN_dash_sub_varname)));
                cMenu.setText(c.getString(c.getColumnIndex(db.COLUMN_dash_sub_text)));
                cMenu.setNew_var_name(c.getString(c.getColumnIndex(db.COLUMN_dash_sub_var)));
                menus.add(cMenu);
            } while (c.moveToNext());
        }
        return menus;
    }

    public static DashboardSubMenu selectByVarname(DBInitialization db,String varname)
    {
        try {
            return select(db, db.COLUMN_dash_sub_varname + "='" + varname + "'").get(0);
        }catch (Exception e)
        {
            return new DashboardSubMenu();
        }
    }
    public static DashboardSubMenu selectBySubVarname(DBInitialization db,String varname)
    {
        try {
            return select(db, db.COLUMN_dash_sub_var + "='" + varname + "'").get(0);
        }catch (Exception e)
        {
            return new DashboardSubMenu();
        }
    }
    public static String getMenuText(DBInitialization db, String varname)
    {
        try{
            return selectBySubVarname(db,varname).getText();
        }catch (Exception e){}
        return "";
    }

    public String getNew_var_name() {
        return new_var_name;
    }

    public int getId() {
        return id;
    }

    public String getVarname() {
        return varname;
    }

    public String getText() {
        return text;
    }

    public void setNew_var_name(String new_var_name) {
        this.new_var_name = new_var_name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setVarname(String varname) {
        this.varname = varname;
    }

    public void setText(String text) {
        this.text = text;
    }
}
