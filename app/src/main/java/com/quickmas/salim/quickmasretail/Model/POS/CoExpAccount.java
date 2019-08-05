package com.quickmas.salim.quickmasretail.Model.POS;

import android.content.ContentValues;
import android.database.Cursor;

import com.quickmas.salim.quickmasretail.Database.DBInitialization;

import java.util.ArrayList;

/**
 * Created by Forhad on 03/09/2018.
 */

public class CoExpAccount {
    public int id;
    public String account_name;
    public String account_type;


    ContentValues getContectValue(DBInitialization db)
    {
        ContentValues insertValues = new ContentValues();
        if(this.getId()>0)
        {
            insertValues.put(db.COLUMN_co_exp_account_id, this.getId());
        }
        insertValues.put(db.COLUMN_co_exp_account_name, this.getAccount_name());
        insertValues.put(db.COLUMN_co_exp_account_type, this.getAccount_type());
        return  insertValues;
    }

    public void insert(DBInitialization db)
    {
        db.insertData(getContectValue(db),db.TABLE_co_exp_account,db.COLUMN_co_exp_account_name+"='"+this.getAccount_name()+"'");
    }
    public void update(DBInitialization db)
    {
        db.updateData(getContectValue(db),db.TABLE_co_exp_account,db.COLUMN_co_exp_account_name+"='"+this.getAccount_name()+"'");
    }
    public static ArrayList<CoExpAccount> select(DBInitialization db, String con)
    {
        Cursor c = db.getData("*",db.TABLE_co_exp_account,con,"");
        ArrayList<CoExpAccount> coAccounts = new ArrayList<CoExpAccount>();
        try {
            if (c != null && c.getCount() > 0) {
                c.moveToFirst();
                do {
                    CoExpAccount coAccount = new CoExpAccount();
                    coAccount.setId(c.getInt(c.getColumnIndex(db.COLUMN_co_exp_account_id)));
                    coAccount.setAccount_name(c.getString(c.getColumnIndex(db.COLUMN_co_exp_account_name)));
                    coAccount.setAccount_type(c.getString(c.getColumnIndex(db.COLUMN_co_exp_account_type)));
                    coAccounts.add(coAccount);
                } while (c.moveToNext());
            }
        }catch (Exception e){}
        return coAccounts;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }

    public void setAccount_type(String account_type) {
        this.account_type = account_type;
    }

    public String getAccount_type() {
        return account_type;
    }

    public int getId() {
        return id;
    }

    public String getAccount_name() {
        return account_name;
    }
}
