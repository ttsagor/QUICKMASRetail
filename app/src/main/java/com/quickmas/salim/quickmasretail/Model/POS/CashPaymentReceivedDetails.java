package com.quickmas.salim.quickmasretail.Model.POS;

import android.content.ContentValues;
import android.database.Cursor;

import com.quickmas.salim.quickmasretail.Database.DBInitialization;

import java.util.ArrayList;

import static com.quickmas.salim.quickmasretail.Database.DBInitialization.TABLE_cash_payment;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.TABLE_cash_receive_details;

/**
 * Created by Forhad on 30/08/2018.
 */

public class CashPaymentReceivedDetails {
    public int id;
    public int receiveId;
    public int invoiceId;
    public double amount;
    public String dateTime;
    public String remark;
    public String po_uuid;
    public String uuid;
    public String voucher_no;

    //changed getid to getreceivedid
    ContentValues getContectValue(DBInitialization db)
    {
        ContentValues insertValues = new ContentValues();
        if(this.getId()>0)
        {
            insertValues.put(db.COLUMN_cash_receive_details_id, this.getId());
        }
        insertValues.put(db.COLUMN_cash_receive_details_received_id, this.getReceiveId());
        insertValues.put(db.COLUMN_cash_receive_details_invoice_id, this.getInvoiceId());
        insertValues.put(db.COLUMN_cash_receive_details_amount, this.getAmount());
        insertValues.put(db.COLUMN_cash_receive_details_date_time, this.getDateTime());
        insertValues.put(db.COLUMN_cash_receive_details_uuid, this.getUuid());
        insertValues.put(db.COLUMN_cash_receive_details_po_id, this.getPo_id());
        insertValues.put(db.COLUMN_cash_receive_details_voucher_no, this.getVoucher_no());
        return  insertValues;
    }

    public void insert(DBInitialization db)
    {
        String con =db.COLUMN_cash_receive_details_received_id+"="+this.getReceiveId()+" AND "+db.COLUMN_cash_receive_details_invoice_id+"="+this.getInvoiceId();
        db.insertData(getContectValue(db), TABLE_cash_receive_details,con);
    }
    public void update(DBInitialization db)
    {
        String con =db.COLUMN_cash_receive_details_received_id+"="+this.getReceiveId()+" AND "+db.COLUMN_cash_receive_details_invoice_id+"="+this.getInvoiceId();
        db.updateData(getContectValue(db), TABLE_cash_receive_details,con);
    }
    public static void update(DBInitialization db,String data,String con)
    {
        db.updateData(data,con, TABLE_cash_receive_details);
    }
    public static int getCount(DBInitialization db)
    {
        return db.getDataCount(TABLE_cash_receive_details,"1=1");
    }

    public static ArrayList<CashPaymentReceivedDetails> select(DBInitialization db, String con)
    {
        Cursor c = db.getData("*", TABLE_cash_receive_details,con,"");
        ArrayList<CashPaymentReceivedDetails> cashPaymentReceivedDetails = new ArrayList<CashPaymentReceivedDetails>();
        if (c !=null && c.getCount() > 0) {
            c.moveToFirst();
            do {
                CashPaymentReceivedDetails cashPaymentReceive = new CashPaymentReceivedDetails();
                cashPaymentReceive.setId(c.getInt(c.getColumnIndex(db.COLUMN_cash_receive_details_id)));
                cashPaymentReceive.setReceiveId(c.getInt(c.getColumnIndex(db.COLUMN_cash_receive_details_received_id)));
                cashPaymentReceive.setInvoiceId(c.getInt(c.getColumnIndex(db.COLUMN_cash_receive_details_invoice_id)));
                cashPaymentReceive.setAmount(c.getDouble(c.getColumnIndex(db.COLUMN_cash_receive_details_amount)));
                cashPaymentReceive.setDateTime(c.getString(c.getColumnIndex(db.COLUMN_cash_receive_details_date_time)));
                cashPaymentReceive.setUuid(c.getString(c.getColumnIndex(db.COLUMN_cash_receive_details_uuid)));
                cashPaymentReceive.setPo_uuid(c.getString(c.getColumnIndex(db.COLUMN_cash_receive_details_po_id)));
                cashPaymentReceive.setVoucher_no(c.getString(c.getColumnIndex(db.COLUMN_cash_receive_details_voucher_no)));
                cashPaymentReceivedDetails.add(cashPaymentReceive);
            } while (c.moveToNext());
        }
        return cashPaymentReceivedDetails;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRemark() {
        return remark;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setReceiveId(int receiveId) {
        this.receiveId = receiveId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public int getId() {
        return id;
    }

    public int getReceiveId() {
        return receiveId;
    }

    public int getInvoiceId() {
        return invoiceId;
    }

    public double getAmount() {
        return amount;
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getPo_id() {
        return po_uuid;
    }

    public String getUuid() {
        return uuid;
    }

    public void setPo_uuid(String po_uuid) {
        this.po_uuid = po_uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getPo_uuid() {
        return po_uuid;
    }

    public void setVoucher_no(String voucher_no) {
        this.voucher_no = voucher_no;
    }

    public String getVoucher_no() {
        return voucher_no;
    }
}
