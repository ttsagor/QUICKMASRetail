package com.quickmas.salim.quickmasretail.Model.POS;

import android.content.ContentValues;
import android.database.Cursor;

import com.quickmas.salim.quickmasretail.Database.DBInitialization;

import java.util.ArrayList;

/**
 * Created by Forhad on 01/06/2018.
 */

public class Bank {
    int id;
    String bankName;

    ContentValues getContectValue(DBInitialization db)
    {
        ContentValues insertValues = new ContentValues();
        insertValues.put(db.COLUMN_bank_id, this.getId());
        insertValues.put(db.COLUMN_bank_name, this.getBankName());
        return  insertValues;
    }

    public void insert(DBInitialization db)
    {
        db.insertData(getContectValue(db),db.TABLE_bank,db.COLUMN_bank_id+"="+this.getId());
    }
    public void update(DBInitialization db)
    {
        db.updateData(getContectValue(db),db.TABLE_bank,db.COLUMN_bank_id+"="+this.getId());
    }
    public static ArrayList<Bank> select(DBInitialization db, String con)
    {
        Cursor c = db.getData("*",db.TABLE_bank,con,"");
        ArrayList<Bank> banks = new ArrayList<Bank>();
        try {
            if (c != null && c.getCount() > 0) {
                c.moveToFirst();
                do {
                    Bank bank = new Bank();
                    bank.setId(c.getInt(c.getColumnIndex(db.COLUMN_bank_id)));
                    bank.setBankName(c.getString(c.getColumnIndex(db.COLUMN_bank_name)));
                    banks.add(bank);
                } while (c.moveToNext());
            }
        }catch (Exception e){}
        return banks;
    }



    public void setId(int id) {
        this.id = id;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public int getId() {
        return id;
    }

    public String getBankName() {
        return bankName;
    }
}
