package com.quickmas.salim.quickmasretail.Model.POS;

import android.content.ContentValues;
import android.database.Cursor;

import com.quickmas.salim.quickmasretail.Database.DBInitialization;

import java.util.ArrayList;

public class CashStatment {
    public int id=0;
    public String entry_time="";
    public String voucher="";
    public String inv_coa="";
    public double debit_amount=0.0;
    public double credit_amount=0.0;
    public double cash_cu_balance=0.0;
    public int status=0;

    ContentValues getContectValue(DBInitialization db)
    {
        ContentValues insertValues = new ContentValues();
        if(this.getId()>0)
        {
            insertValues.put(db.COLUMN_cash_statment_id, this.getId());
        }
        insertValues.put(db.COLUMN_cash_statmen_entry_time, this.getEntry_time());
        insertValues.put(db.COLUMN_cash_statment_voucher, this.getVoucher());
        insertValues.put(db.COLUMN_cash_statment_inv_coa, this.getInv_coa());
        insertValues.put(db.COLUMN_cash_statment_debit_amount, this.getDebit_amount());
        insertValues.put(db.COLUMN_cash_statment_credit_amount, this.getCredit_amount());
        insertValues.put(db.COLUMN_cash_statment_cash_cu_balance, this.getCash_cu_balance());
        return  insertValues;
    }
    public void insert(DBInitialization db)
    {
        String con=db.COLUMN_cash_statmen_entry_time+"='"+this.entry_time+"' AND "+db.COLUMN_cash_statment_voucher+"='"+this.voucher+"' AND "+db.COLUMN_cash_statment_inv_coa+"='"+this.inv_coa+"' AND "+db.COLUMN_cash_statment_debit_amount+"='"+this.debit_amount+"' AND "+db.COLUMN_cash_statment_credit_amount+"="+this.credit_amount+" AND "+db.COLUMN_cash_statment_cash_cu_balance+"="+this.cash_cu_balance;
        db.insertData(getContectValue(db),db.TABLE_cash_statment,con);
    }
    public void update(DBInitialization db)
    {
        String con=db.COLUMN_cash_statmen_entry_time+"='"+this.entry_time+"' AND "+db.COLUMN_cash_statment_voucher+"='"+this.voucher+"' AND "+db.COLUMN_cash_statment_inv_coa+"='"+this.inv_coa+"' AND "+db.COLUMN_cash_statment_debit_amount+"='"+this.debit_amount+"' AND "+db.COLUMN_cash_statment_credit_amount+"="+this.credit_amount+" AND "+db.COLUMN_cash_statment_cash_cu_balance+"="+this.cash_cu_balance;
        db.updateData(getContectValue(db),db.TABLE_cash_statment,con);
    }
    public static ArrayList<CashStatment> select(DBInitialization db, String con)
    {
        Cursor c = db.getData("*",db.TABLE_cash_statment,con,"");
        ArrayList<CashStatment> cashStatments = new ArrayList<CashStatment>();
        try {
            if (c != null && c.getCount() > 0) {
                c.moveToFirst();
                do {
                    CashStatment cashStatment = new CashStatment();
                    cashStatment.setId(c.getInt(c.getColumnIndex(db.COLUMN_cash_statment_id)));
                    cashStatment.setEntry_time(c.getString(c.getColumnIndex(db.COLUMN_cash_statmen_entry_time)));
                    cashStatment.setVoucher(c.getString(c.getColumnIndex(db.COLUMN_cash_statment_voucher)));
                    cashStatment.setInv_coa(c.getString(c.getColumnIndex(db.COLUMN_cash_statment_inv_coa)));
                    cashStatment.setDebit_amount(c.getDouble(c.getColumnIndex(db.COLUMN_cash_statment_debit_amount)));
                    cashStatment.setCredit_amount(c.getDouble(c.getColumnIndex(db.COLUMN_cash_statment_credit_amount)));
                    cashStatment.setCash_cu_balance(c.getDouble(c.getColumnIndex(db.COLUMN_cash_statment_cash_cu_balance)));
                    cashStatments.add(cashStatment);
                } while (c.moveToNext());
            }
        }catch (Exception e){}
        return cashStatments;
    }


    public void setEntry_time(String entry_time) {
        this.entry_time = entry_time;
    }

    public void setVoucher(String voucher) {
        this.voucher = voucher;
    }

    public void setInv_coa(String inv_coa) {
        this.inv_coa = inv_coa;
    }

    public void setDebit_amount(double debit_amount) {
        this.debit_amount = debit_amount;
    }

    public void setCredit_amount(double credit_amount) {
        this.credit_amount = credit_amount;
    }

    public void setCash_cu_balance(double cash_cu_balance) {
        this.cash_cu_balance = cash_cu_balance;
    }

    public String getEntry_time() {
        return entry_time;
    }

    public String getVoucher() {
        return voucher;
    }

    public String getInv_coa() {
        return inv_coa;
    }

    public double getDebit_amount() {
        return debit_amount;
    }

    public double getCredit_amount() {
        return credit_amount;
    }

    public double getCash_cu_balance() {
        return cash_cu_balance;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
