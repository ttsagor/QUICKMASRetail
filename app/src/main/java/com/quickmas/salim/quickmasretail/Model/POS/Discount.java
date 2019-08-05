package com.quickmas.salim.quickmasretail.Model.POS;

import android.content.ContentValues;
import android.database.Cursor;

import com.quickmas.salim.quickmasretail.Database.DBInitialization;
import com.quickmas.salim.quickmasretail.Utility.DateTimeCalculation;

import java.util.ArrayList;

public class Discount {
    public String name;
    public double discount;
    public String valid_from;
    public String valid_to;

    ContentValues getContectValue(DBInitialization db)
    {
        ContentValues insertValues = new ContentValues();
        insertValues.put(db.COLUMN_discount_list_name, this.getName());
        insertValues.put(db.COLUMN_discount_list_discount, this.getDiscount());
        insertValues.put(db.COLUMN_discount_valid_from, this.getValid_from());
        insertValues.put(db.COLUMN_discount_valid_to, this.getValid_to());
        return  insertValues;
    }

    public void insert(DBInitialization db)
    {
        db.insertData(getContectValue(db),db.TABLE_discount_list,db.COLUMN_discount_list_name+"='"+this.getName()+"'");
    }
    public void update(DBInitialization db)
    {
        db.updateData(getContectValue(db),db.TABLE_discount_list,db.COLUMN_discount_list_name+"="+this.getName()+"'");
    }
    public static ArrayList<Discount> select(DBInitialization db, String con)
    {
        Cursor c = db.getData("*",db.TABLE_discount_list,con,"");
        ArrayList<Discount> discounts = new ArrayList<Discount>();
        try {
            if (c != null && c.getCount() > 0) {
                c.moveToFirst();
                do {
                    Discount discount = new Discount();
                    discount.setName(c.getString(c.getColumnIndex(db.COLUMN_discount_list_name)));
                    discount.setValid_from(c.getString(c.getColumnIndex(db.COLUMN_discount_valid_from)));
                    discount.setValid_to(c.getString(c.getColumnIndex(db.COLUMN_discount_valid_to)));
                    discount.setDiscount(c.getDouble(c.getColumnIndex(db.COLUMN_discount_list_discount)));
                    discounts.add(discount);
                } while (c.moveToNext());
            }
        }catch (Exception e){}
        return discounts;
    }

    public static ArrayList<Discount> selectValid(DBInitialization db)
    {
        ArrayList<Discount> dis = select(db,"1=1");
        ArrayList<Discount> output = new ArrayList<>();
        for(Discount d : dis)
        {
            if(DateTimeCalculation.checkValidityDateTime(d.valid_from,d.valid_to))
            {
                output.add(d);
            }
        }
        return output;
    }


    public void setValid_from(String valid_from) {
        this.valid_from = valid_from;
    }

    public void setValid_to(String valid_to) {
        this.valid_to = valid_to;
    }

    public String getValid_from() {
        return valid_from;
    }

    public String getValid_to() {
        return valid_to;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public String getName() {
        return name;
    }

    public double getDiscount() {
        return discount;
    }
}
