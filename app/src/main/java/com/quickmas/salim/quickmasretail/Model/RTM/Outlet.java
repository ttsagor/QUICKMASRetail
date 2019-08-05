package com.quickmas.salim.quickmasretail.Model.RTM;

import android.content.ContentValues;
import android.database.Cursor;

import com.quickmas.salim.quickmasretail.Database.DBInitialization;

import java.util.ArrayList;

/**
 * Created by Forhad on 02/04/2018.
 */

public class Outlet {
    private int id;
    private String outlet_id;
    private String outlet_name;
    private String owner_name;
    private String route;
    private String section;
    private String claster;
    private String address;
    private String phone;
    private String outlet_type;
    private String sales_for;
    private String outlet_for;


    ContentValues getContectValue(DBInitialization db)
    {
        ContentValues insertValues = new ContentValues();
        insertValues.put(db.COLUMN_o_id, this.getId());
        insertValues.put(db.COLUMN_o_outlet_id, this.getOutlet_id());
        insertValues.put(db.COLUMN_o_outlet_name, this.getOutlet_name());
        insertValues.put(db.COLUMN_o_owner_name, this.getOwner_name());
        insertValues.put(db.COLUMN_o_route, this.getRoute());
        insertValues.put(db.COLUMN_o_section, this.getSection());
        insertValues.put(db.COLUMN_o_claster, this.getClaster());
        insertValues.put(db.COLUMN_o_address, this.getAddress());
        insertValues.put(db.COLUMN_o_phone, this.getPhone());
        insertValues.put(db.COLUMN_o_outlet_type, this.getOutlet_type());
        insertValues.put(db.COLUMN_o_sales_for, this.getSales_for());
        insertValues.put(db.COLUMN_o_outlet_for, this.getOutlet_for());
        return  insertValues;
    }

    public void insert(DBInitialization db)
    {
        db.insertData(getContectValue(db),db.TABLE_OUTLET,db.COLUMN_i_id+"="+this.getId());
    }

    public void update(DBInitialization db)
    {
        db.updateData(getContectValue(db),db.TABLE_OUTLET,db.COLUMN_i_id+"="+this.getId());
    }

    public static ArrayList<Outlet> select(DBInitialization db, String con)
    {
        Cursor c = db.getData("*",db.TABLE_OUTLET,con,"");
        ArrayList<Outlet> outlets = new ArrayList<Outlet>();
        if (c !=null && c.getCount() > 0) {
            c.moveToFirst();
            do {
                Outlet cOutlet = new Outlet();
                cOutlet.setId(c.getInt(c.getColumnIndex(db.COLUMN_o_id)));
                cOutlet.setOutlet_id(c.getString(c.getColumnIndex(db.COLUMN_o_outlet_id)));
                cOutlet.setOutlet_name(c.getString(c.getColumnIndex(db.COLUMN_o_outlet_name)));
                cOutlet.setOwner_name(c.getString(c.getColumnIndex(db.COLUMN_o_owner_name)));
                cOutlet.setRoute(c.getString(c.getColumnIndex(db.COLUMN_o_route)));
                cOutlet.setSection(c.getString(c.getColumnIndex(db.COLUMN_o_section)));
                cOutlet.setClaster(c.getString(c.getColumnIndex(db.COLUMN_o_claster)));
                cOutlet.setAddress(c.getString(c.getColumnIndex(db.COLUMN_o_address)));
                cOutlet.setPhone(c.getString(c.getColumnIndex(db.COLUMN_o_phone)));
                cOutlet.setOutlet_type(c.getString(c.getColumnIndex(db.COLUMN_o_outlet_type)));
                cOutlet.setSales_for(c.getString(c.getColumnIndex(db.COLUMN_o_sales_for)));
                cOutlet.setOutlet_for(c.getString(c.getColumnIndex(db.COLUMN_o_outlet_for)));
                outlets.add(cOutlet);
            } while (c.moveToNext());
        }
        return outlets;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setOutlet_id(String outlet_id) {
        this.outlet_id = outlet_id;
    }

    public void setOutlet_name(String outlet_name) {
        this.outlet_name = outlet_name;
    }

    public void setOwner_name(String owner_name) {
        this.owner_name = owner_name;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public void setClaster(String claster) {
        this.claster = claster;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setOutlet_type(String outlet_type) {
        this.outlet_type = outlet_type;
    }

    public void setSales_for(String sales_for) {
        this.sales_for = sales_for;
    }

    public void setOutlet_for(String outlet_for) {
        this.outlet_for = outlet_for;
    }

    public int getId() {
        return id;
    }

    public String getOutlet_id() {
        return outlet_id;
    }

    public String getOutlet_name() {
        return outlet_name;
    }

    public String getOwner_name() {
        return owner_name;
    }

    public String getSection() {
        return section;
    }

    public String getRoute() {
        return route;
    }

    public String getClaster() {
        return claster;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public String getOutlet_type() {
        return outlet_type;
    }

    public String getSales_for() {
        return sales_for;
    }

    public String getOutlet_for() {
        return outlet_for;
    }
}
