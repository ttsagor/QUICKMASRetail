package com.quickmas.salim.quickmasretail.Model.POS;

import android.content.ContentValues;
import android.database.Cursor;

import com.quickmas.salim.quickmasretail.Database.DBInitialization;

import java.util.ArrayList;

import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_customer_id;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.TABLE_Invoice;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.TABLE_PosCustomer;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.TABLE_pos_received_payment;

/**
 * Created by Forhad on 07/07/2018.
 */

public class PosPaymentReceived {
    private int id;
    private int invoice_id;
    private double amount;
    private String payment_mode;
    private String card_type;
    private String cheque_no;
    private String bank;
    private String received_by;
    private String received_date;


    ContentValues getContectValue(DBInitialization db)
    {
        ContentValues insertValues = new ContentValues();
        if(this.getId()>0) {
            insertValues.put(db.COLUMN_pos_received_payment_id, this.getId());
        }
        insertValues.put(db.COLUMN_pos_received_payment_invoice_id, this.getInvoice_id());
        insertValues.put(db.COLUMN_pos_received_payment_amount, this.getAmount());
        insertValues.put(db.COLUMN_pos_received_payment_mode, this.getPayment_mode());
        insertValues.put(db.COLUMN_pos_received_card_type, this.getCard_type());
        insertValues.put(db.COLUMN_pos_received_cheque_no, this.getCheque_no());
        insertValues.put(db.COLUMN_pos_received_bank, this.getBank());
        insertValues.put(db.COLUMN_pos_received_payment_received_by, this.getReceived_by());
        insertValues.put(db.COLUMN_pos_received_payment_received_date, this.getReceived_date());
        return  insertValues;
    }
    public void insert(DBInitialization db)
    {
        db.insertData(getContectValue(db), TABLE_pos_received_payment,db.COLUMN_pos_received_payment_id+"="+this.getId());
    }

    public void update(DBInitialization db)
    {
        db.updateData(getContectValue(db), TABLE_pos_received_payment,db.COLUMN_pos_received_payment_id+"="+this.getId());
    }

    public static ArrayList<PosPaymentReceived> select(DBInitialization db, String con)
    {
        Cursor c = db.getData("*", TABLE_pos_received_payment,con,"");
        ArrayList<PosPaymentReceived> posPaymentReceiveds = new ArrayList<PosPaymentReceived>();
        if (c !=null && c.getCount() > 0) {
            c.moveToFirst();
            do {
                PosPaymentReceived posPaymentReceived = new PosPaymentReceived();
                posPaymentReceived.setId(c.getInt(c.getColumnIndex(db.COLUMN_pos_received_payment_id)));
                posPaymentReceived.setInvoice_id(c.getInt(c.getColumnIndex(db.COLUMN_pos_received_payment_invoice_id)));
                posPaymentReceived.setAmount(c.getDouble(c.getColumnIndex(db.COLUMN_pos_received_payment_amount)));
                posPaymentReceived.setPayment_mode(c.getString(c.getColumnIndex(db.COLUMN_pos_received_payment_mode)));
                posPaymentReceived.setCard_type(c.getString(c.getColumnIndex(db.COLUMN_pos_received_card_type)));
                posPaymentReceived.setCheque_no(c.getString(c.getColumnIndex(db.COLUMN_pos_received_cheque_no)));
                posPaymentReceived.setBank(c.getString(c.getColumnIndex(db.COLUMN_pos_received_bank)));
                posPaymentReceived.setReceived_by(c.getString(c.getColumnIndex(db.COLUMN_pos_received_payment_received_by)));
                posPaymentReceived.setReceived_date(c.getString(c.getColumnIndex(db.COLUMN_pos_received_payment_received_date)));
                posPaymentReceiveds.add(posPaymentReceived);
            } while (c.moveToNext());
        }
        return posPaymentReceiveds;
    }
    public static double sumReceivedAmount(DBInitialization db, int id)
    {
        return sumData(db,db.COLUMN_pos_received_payment_amount,db.COLUMN_pos_received_payment_invoice_id+"="+ id);
    }
    public static double sumData(DBInitialization db,String sel, String con)
    {
        return db.sumDataDouble(sel, TABLE_pos_received_payment, con);
    }
    public String getPayment_mode() {
        return payment_mode;
    }

    public String getCard_type() {
        return card_type;
    }

    public String getCheque_no() {
        return cheque_no;
    }

    public String getBank() {
        return bank;
    }

    public void setPayment_mode(String payment_mode) {
        this.payment_mode = payment_mode;
    }

    public void setCard_type(String card_type) {
        this.card_type = card_type;
    }

    public void setCheque_no(String cheque_no) {
        this.cheque_no = cheque_no;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setInvoice_id(int invoice_id) {
        this.invoice_id = invoice_id;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setReceived_by(String received_by) {
        this.received_by = received_by;
    }

    public void setReceived_date(String received_date) {
        this.received_date = received_date;
    }

    public int getId() {
        return id;
    }

    public int getInvoice_id() {
        return invoice_id;
    }

    public double getAmount() {
        return amount;
    }

    public String getReceived_by() {
        return received_by;
    }

    public String getReceived_date() {
        return received_date;
    }
}
