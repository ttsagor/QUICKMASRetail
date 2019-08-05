package com.quickmas.salim.quickmasretail.Model.POS;

import android.content.ContentValues;
import android.database.Cursor;

import com.quickmas.salim.quickmasretail.Database.DBInitialization;

import java.util.ArrayList;

import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_invoiceID;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.TABLE_PosInvoice;

public class PaymentReceiveVoucher {
    public int id;
    public int sl_id;
    public String inv_id="";
    public String category="";
    public String customer="";
    public String payment_mode="";
    public String card_info="";
    public String card_number="";
    public String print_by="";
    public String print_date="";
    public String reamrk="";
    public double amount;


    ContentValues getContectValue(DBInitialization db)
    {
        ContentValues insertValues = new ContentValues();
        if(this.getId()>0)
        {
            insertValues.put(db.COLUMN_payment_rece_vou_id, this.getId());
        }
        insertValues.put(db.COLUMN_payment_rece_vou_sl_id, this.getSl_id());
        insertValues.put(db.COLUMN_payment_rece_vou_inv_id, this.getInv_id());
        insertValues.put(db.COLUMN_payment_rece_vou_category, this.getCategory());
        insertValues.put(db.COLUMN_payment_rece_vou_customer, this.getCustomer());
        insertValues.put(db.COLUMN_payment_rece_vou_payment_mode, this.getPayment_mode());
        insertValues.put(db.COLUMN_payment_rece_vou_print_by, this.getPrint_by());
        insertValues.put(db.COLUMN_payment_rece_vou_print_date, this.getPrint_date());
        insertValues.put(db.COLUMN_payment_rece_vou_amount, this.getAmount());
        insertValues.put(db.COLUMN_payment_rece_vou_card_info, this.getCard_info());
        insertValues.put(db.COLUMN_payment_rece_vou_card_no, this.getCard_number());
        insertValues.put(db.COLUMN_payment_rece_vou_remark, this.getReamrk());
        return  insertValues;
    }

    public void insert(DBInitialization db)
    {
        db.insertData(getContectValue(db),db.TABLE_payment_rece_vou,db.COLUMN_payment_rece_vou_id+"="+this.getId());
    }
    public void update(DBInitialization db)
    {
        db.updateData(getContectValue(db),db.TABLE_payment_rece_vou,db.COLUMN_payment_rece_vou_id+"="+this.getId());
    }
    public static ArrayList<PaymentReceiveVoucher> select(DBInitialization db, String con)
    {
        Cursor c = db.getData("*",db.TABLE_payment_rece_vou,con,"");
        ArrayList<PaymentReceiveVoucher> paymentReceiveVouchers = new ArrayList<PaymentReceiveVoucher>();
        try {
            if (c != null && c.getCount() > 0) {
                c.moveToFirst();
                do {
                    PaymentReceiveVoucher paymentReceiveVoucher = new PaymentReceiveVoucher();
                    paymentReceiveVoucher.setId(c.getInt(c.getColumnIndex(db.COLUMN_payment_rece_vou_id)));
                    paymentReceiveVoucher.setSl_id(c.getInt(c.getColumnIndex(db.COLUMN_payment_rece_vou_sl_id)));
                    paymentReceiveVoucher.setInv_id(c.getString(c.getColumnIndex(db.COLUMN_payment_rece_vou_inv_id)));
                    paymentReceiveVoucher.setCategory(c.getString(c.getColumnIndex(db.COLUMN_payment_rece_vou_category)));
                    paymentReceiveVoucher.setCustomer(c.getString(c.getColumnIndex(db.COLUMN_payment_rece_vou_customer)));
                    paymentReceiveVoucher.setPayment_mode(c.getString(c.getColumnIndex(db.COLUMN_payment_rece_vou_payment_mode)));
                    paymentReceiveVoucher.setPrint_by(c.getString(c.getColumnIndex(db.COLUMN_payment_rece_vou_print_by)));
                    paymentReceiveVoucher.setPrint_date(c.getString(c.getColumnIndex(db.COLUMN_payment_rece_vou_print_date)));
                    paymentReceiveVoucher.setAmount(c.getDouble(c.getColumnIndex(db.COLUMN_payment_rece_vou_amount)));
                    paymentReceiveVoucher.setCard_info(c.getString(c.getColumnIndex(db.COLUMN_payment_rece_vou_card_info)));
                    paymentReceiveVoucher.setCard_number(c.getString(c.getColumnIndex(db.COLUMN_payment_rece_vou_card_no)));
                    paymentReceiveVoucher.setReamrk(c.getString(c.getColumnIndex(db.COLUMN_payment_rece_vou_remark)));
                    paymentReceiveVouchers.add(paymentReceiveVoucher);
                } while (c.moveToNext());
            }
        }catch (Exception e){}
        return paymentReceiveVouchers;
    }
    public static int getMaxIdByCategory(String category,DBInitialization db)
    {
        return db.getMax(db.TABLE_payment_rece_vou,db.COLUMN_payment_rece_vou_sl_id, db.COLUMN_payment_rece_vou_category + "='"+category+"'")+1;
    }
    public void setId(int id) {
        this.id = id;
    }

    public void setSl_id(int sl_id) {
        this.sl_id = sl_id;
    }

    public void setInv_id(String inv_id) {
        this.inv_id = inv_id;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public void setPrint_by(String print_by) {
        this.print_by = print_by;
    }

    public void setPrint_date(String print_date) {
        this.print_date = print_date;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public int getSl_id() {
        return sl_id;
    }

    public String getInv_id() {
        return inv_id;
    }

    public String getCategory() {
        return category;
    }

    public String getCustomer() {
        return customer;
    }

    public String getPrint_by() {
        return print_by;
    }

    public String getPrint_date() {
        return print_date;
    }

    public double getAmount() {
        return amount;
    }

    public void setPayment_mode(String payment_mode) {
        this.payment_mode = payment_mode;
    }

    public String getPayment_mode() {
        return payment_mode;
    }

    public void setCard_info(String card_info) {
        this.card_info = card_info;
    }

    public String getCard_info() {
        return card_info;
    }

    public void setCard_number(String card_number) {
        this.card_number = card_number;
    }

    public String getCard_number() {
        return card_number;
    }

    public void setReamrk(String reamrk) {
        this.reamrk = reamrk;
    }

    public String getReamrk() {
        return reamrk;
    }
}
