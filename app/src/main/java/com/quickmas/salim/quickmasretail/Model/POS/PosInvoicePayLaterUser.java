package com.quickmas.salim.quickmasretail.Model.POS;

import android.content.ContentValues;
import android.database.Cursor;

import com.quickmas.salim.quickmasretail.Database.DBInitialization;

import java.util.ArrayList;

import static com.quickmas.salim.quickmasretail.Database.DBInitialization.TABLE_pos_ver_user;

/**
 * Created by Forhad on 23/06/2018.
 */

public class PosInvoicePayLaterUser {
    String user_name;
    String password;

    ContentValues getContectValue(DBInitialization db)
    {
        ContentValues insertValues = new ContentValues();
        insertValues.put(db.COLUMN_pos_ver_username, this.getUser_name());
        insertValues.put(db.COLUMN_pos_ver_password, this.getPassword());
        return  insertValues;
    }
    public void insert(DBInitialization db)
    {
        db.insertData(getContectValue(db), TABLE_pos_ver_user,db.COLUMN_pos_ver_username+"='"+this.getUser_name()+"'");
    }
    public void update(DBInitialization db)
    {
        db.updateData(getContectValue(db), TABLE_pos_ver_user,db.COLUMN_pos_ver_username+"='"+this.getUser_name()+"'");
    }
    public static ArrayList<PosInvoicePayLaterUser> select(DBInitialization db, String con)
    {
        Cursor c = db.getData("*", TABLE_pos_ver_user,con,"");
        ArrayList<PosInvoicePayLaterUser> cards = new ArrayList<PosInvoicePayLaterUser>();
        try {
            if (c != null && c.getCount() > 0) {
                c.moveToFirst();
                do {
                    PosInvoicePayLaterUser card = new PosInvoicePayLaterUser();
                    card.setUser_name(c.getString(c.getColumnIndex(db.COLUMN_pos_ver_username)));
                    card.setPassword(c.getString(c.getColumnIndex(db.COLUMN_pos_ver_password)));
                    cards.add(card);
                } while (c.moveToNext());
            }
        }catch (Exception e){}
        return cards;
    }

    public static boolean checkIfUserExists(DBInitialization db, String user_name,String password)
    {
        String con = db.COLUMN_pos_ver_username+"='"+user_name+"' AND "+db.COLUMN_pos_ver_password+"='"+password+"'";
        if(db. getDataCount(TABLE_pos_ver_user,con)>0)
        {
            return true;
        }
        return false;
    }
    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getPassword() {
        return password;
    }
}
