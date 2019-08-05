package com.quickmas.salim.quickmasretail.Model.POS;

import android.content.ContentValues;
import android.database.Cursor;

import com.quickmas.salim.quickmasretail.Database.DBInitialization;

import java.util.ArrayList;

/**
 * Created by Forhad on 07/09/2018.
 */

public class CashAccount {
    public int id;
    public String name;

    ContentValues getContectValue(DBInitialization db)
    {
        ContentValues insertValues = new ContentValues();
        insertValues.put(db.COLUMN_cash_db_account_id, this.getId());
        insertValues.put(db.COLUMN_cash_db_account_account_name, this.getName());
        return  insertValues;
    }

    public void insert(DBInitialization db)
    {
        db.insertData(getContectValue(db),db.TABLE_cash_account,db.COLUMN_card_id+"="+this.getId());
    }
    public void update(DBInitialization db)
    {
        db.updateData(getContectValue(db),db.TABLE_cash_account,db.COLUMN_card_id+"="+this.getId());
    }
    public static ArrayList<CashAccount> select(DBInitialization db, String con)
    {
        Cursor c = db.getData("*",db.TABLE_cash_account,con,"");
        ArrayList<CashAccount> cashAccounts = new ArrayList<CashAccount>();
        try {
            if (c != null && c.getCount() > 0) {
                c.moveToFirst();
                do {
                    CashAccount cashAccount = new CashAccount();
                    cashAccount.setId(c.getInt(c.getColumnIndex(db.COLUMN_cash_db_account_id)));
                    cashAccount.setName(c.getString(c.getColumnIndex(db.COLUMN_cash_db_account_account_name)));
                    cashAccounts.add(cashAccount);
                } while (c.moveToNext());
            }
        }catch (Exception e){}
        return cashAccounts;
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
