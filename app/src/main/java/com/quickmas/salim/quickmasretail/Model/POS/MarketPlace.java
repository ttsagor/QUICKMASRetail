package com.quickmas.salim.quickmasretail.Model.POS;

import android.content.ContentValues;
import android.database.Cursor;

import com.quickmas.salim.quickmasretail.Database.DBInitialization;

import java.util.ArrayList;

/**
 * Created by Forhad on 02/10/2018.
 */

public class MarketPlace {
    public int id;
    public String name;

    ContentValues getContectValue(DBInitialization db)
    {
        ContentValues insertValues = new ContentValues();
        if(this.getId()>0) {
            insertValues.put(db.COLUMN_market_place_id, this.getId());
        }
        insertValues.put(db.COLUMN_market_place_name, this.getName());
        return  insertValues;
    }

    public void insert(DBInitialization db)
    {
        db.insertData(getContectValue(db),db.TABLE_market_place,db.COLUMN_market_place_name+"='"+this.getName()+"'");
    }
    public void update(DBInitialization db)
    {
        db.updateData(getContectValue(db),db.TABLE_market_place,db.COLUMN_market_place_name+"='"+this.getName()+"'");
    }
    public static ArrayList<MarketPlace> select(DBInitialization db, String con)
    {
        Cursor c = db.getData("*",db.TABLE_market_place,con,"");
        ArrayList<MarketPlace> marketPlaces = new ArrayList<MarketPlace>();
        try {
            if (c != null && c.getCount() > 0) {
                c.moveToFirst();
                do {
                    MarketPlace marketPlace = new MarketPlace();
                    marketPlace.setId(c.getInt(c.getColumnIndex(db.COLUMN_market_place_id)));
                    marketPlace.setName(c.getString(c.getColumnIndex(db.COLUMN_market_place_name)));
                    marketPlaces.add(marketPlace);
                } while (c.moveToNext());
            }
        }catch (Exception e){}
        return marketPlaces;
    }
    public static ArrayList<String> getAllMarketPlace(DBInitialization db)
    {
        ArrayList<String> list = new ArrayList<>();
        ArrayList<MarketPlace> marketPlaces = MarketPlace.select(db,"1=1");
        for(MarketPlace marketPlace : marketPlaces)
        {
            System.out.println(marketPlace.getName());
            list.add(marketPlace.getName());
        }
        return list;
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
