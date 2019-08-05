package com.quickmas.salim.quickmasretail.Model.POS;

import android.content.ContentValues;
import android.database.Cursor;

import com.quickmas.salim.quickmasretail.Database.DBInitialization;

import java.util.ArrayList;

import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_cash_payemnt_id;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_customer_id;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_head_additional_discount;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_head_additional_discount_percent;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_head_bank;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_head_card_amount;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_head_card_type;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_head_cash_amount;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_head_change;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_head_cheque_no;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_head_customer;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_head_delivery_expense;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_head_id;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_head_invoice_date;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_head_invoice_generate_by;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_head_invoice_id;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_head_iteam_discount;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_head_note_given;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_head_pay_later_amount;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_head_payment_mode;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_head_sale_type;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_head_sales_person;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_head_status;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_head_total_amount;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_head_total_amount_full;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_head_total_paid_amount;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_head_total_quantity;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_head_total_tax;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_head_verify_user;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_supplier_cash_payemnt_id;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.TABLE_PosCustomer;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.TABLE_cash_payment;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.TABLE_pos_invoice_head;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.TABLE_supplier_cash_payment;

/**
 * Created by Forhad on 30/08/2018.
 */

public class SupplierCashPaymentReceive {
    public int id=0;
    public String account_name="";
    public String remark="";
    public double ca_amount=0.0;
    public double cash_amount=0.0;
    public double card_amount=0.0;
    public String card_type="";
    public String bank_account="";
    public String bank_name="";
    public String cheque_no="";
    public String customer_name="";
    public String received_by="";
    public String date_time="";
    public String po_id="";
    public int if_data_synced=0;
    public String voucher="";

    ContentValues getContectValue(DBInitialization db)
    {
        ContentValues insertValues = new ContentValues();
        if(this.getId()>0)
        {
            insertValues.put(COLUMN_supplier_cash_payemnt_id, this.getId());
        }
        insertValues.put(db.COLUMN_supplier_cash_payemnt_account_name, this.getAccount_name());
        insertValues.put(db.COLUMN_supplier_cash_payemnt_remark, this.getRemark());
        insertValues.put(db.COLUMN_supplier_cash_payemnt_ca_amount, this.getCa_amount());
        insertValues.put(db.COLUMN_supplier_cash_payemnt_cash_amount, this.getCash_amount());
        insertValues.put(db.COLUMN_supplier_cash_payemnt_card_amount, this.getCard_amount());
        insertValues.put(db.COLUMN_supplier_cash_payemnt_card_type, this.getCard_type());
        insertValues.put(db.COLUMN_supplier_cash_payemnt_bank_account, this.getBank_account());
        insertValues.put(db.COLUMN_supplier_cash_payemnt_bank_name, this.getBank_name());
        insertValues.put(db.COLUMN_supplier_cash_payemnt_customer_name, this.getCustomer_name());
        insertValues.put(db.COLUMN_supplier_cash_payemnt_cheque_no, this.getCheque_no());
        insertValues.put(db.COLUMN_supplier_cash_payemnt_received_by, this.getReceived_by());
        insertValues.put(db.COLUMN_supplier_cash_payemnt_date_time, this.getDate_time());
        insertValues.put(db.COLUMN_supplier_cash_payemnt_po_id, this.getPo_id());
        insertValues.put(db.COLUMN_supplier_cash_payemnt_if_synced, this.getIf_data_synced());
        return  insertValues;
    }
    public void insert(DBInitialization db,String con)
    {
        db.insertData(getContectValue(db), TABLE_supplier_cash_payment,con);
    }
    public void insert(DBInitialization db)
    {
        String con = COLUMN_supplier_cash_payemnt_id+"="+this.getId();
        db.insertData(getContectValue(db), TABLE_supplier_cash_payment,con);
    }
    public void update(DBInitialization db)
    {
        db.updateData(getContectValue(db), TABLE_supplier_cash_payment, COLUMN_supplier_cash_payemnt_id+"="+this.getId());
    }
    public static void update(DBInitialization db,String data,String con)
    {
        db.updateData(data,con, TABLE_supplier_cash_payment);
    }
    public static int getCount(DBInitialization db)
    {
        return db.getDataCount(TABLE_supplier_cash_payment,"1=1");
    }

    public static ArrayList<SupplierCashPaymentReceive> select(DBInitialization db, String con)
    {
        Cursor c = db.getData("*", TABLE_supplier_cash_payment,con,"");
        ArrayList<SupplierCashPaymentReceive> cashPaymentReceives = new ArrayList<SupplierCashPaymentReceive>();
        if (c !=null && c.getCount() > 0) {
            c.moveToFirst();
            do {
                SupplierCashPaymentReceive cashPaymentReceive = new SupplierCashPaymentReceive();
                cashPaymentReceive.setId(c.getInt(c.getColumnIndex(COLUMN_supplier_cash_payemnt_id)));
                cashPaymentReceive.setAccount_name(c.getString(c.getColumnIndex(db.COLUMN_supplier_cash_payemnt_account_name)));
                cashPaymentReceive.setRemark(c.getString(c.getColumnIndex(db.COLUMN_supplier_cash_payemnt_remark)));
                cashPaymentReceive.setCa_amount(c.getDouble(c.getColumnIndex(db.COLUMN_supplier_cash_payemnt_ca_amount)));
                cashPaymentReceive.setCash_amount(c.getDouble(c.getColumnIndex(db.COLUMN_supplier_cash_payemnt_cash_amount)));
                cashPaymentReceive.setCard_amount(c.getDouble(c.getColumnIndex(db.COLUMN_supplier_cash_payemnt_card_amount)));
                cashPaymentReceive.setCard_type(c.getString(c.getColumnIndex(db.COLUMN_supplier_cash_payemnt_card_type)));
                cashPaymentReceive.setBank_account(c.getString(c.getColumnIndex(db.COLUMN_supplier_cash_payemnt_bank_account)));
                cashPaymentReceive.setBank_name(c.getString(c.getColumnIndex(db.COLUMN_supplier_cash_payemnt_bank_name)));
                cashPaymentReceive.setCheque_no(c.getString(c.getColumnIndex(db.COLUMN_supplier_cash_payemnt_cheque_no)));
                cashPaymentReceive.setCustomer_name(c.getString(c.getColumnIndex(db.COLUMN_supplier_cash_payemnt_customer_name)));
                cashPaymentReceive.setReceived_by(c.getString(c.getColumnIndex(db.COLUMN_supplier_cash_payemnt_received_by)));
                cashPaymentReceive.setDate_time(c.getString(c.getColumnIndex(db.COLUMN_supplier_cash_payemnt_date_time)));
                cashPaymentReceive.setPo_id(c.getString(c.getColumnIndex(db.COLUMN_supplier_cash_payemnt_po_id)));
                cashPaymentReceive.setIf_data_synced(c.getInt(c.getColumnIndex(db.COLUMN_supplier_cash_payemnt_if_synced)));
                cashPaymentReceives.add(cashPaymentReceive);
            } while (c.moveToNext());
        }
        return cashPaymentReceives;
    }
    public static int getMaxId(DBInitialization db)
    {
        return db.getMax(TABLE_supplier_cash_payment,COLUMN_supplier_cash_payemnt_id, "1=1");
    }
    public void setId(int id) {
        this.id = id;
    }

    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setCa_amount(double ca_amount) {
        this.ca_amount = ca_amount;
    }

    public void setCash_amount(double cash_amount) {
        this.cash_amount = cash_amount;
    }

    public void setCard_amount(double card_amount) {
        this.card_amount = card_amount;
    }

    public void setCard_type(String card_type) {
        this.card_type = card_type;
    }

    public void setBank_account(String bank_account) {
        this.bank_account = bank_account;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

    public void setReceived_by(String received_by) {
        this.received_by = received_by;
    }

    public void setCheque_no(String cheque_no) {
        this.cheque_no = cheque_no;
    }

    public void setPo_id(String po_id) {
        this.po_id = po_id;
    }

    public String getPo_id() {
        return po_id;
    }

    public String getCheque_no() {
        return cheque_no;
    }

    public String getReceived_by() {
        return received_by;
    }

    public int getId() {
        return id;
    }

    public String getAccount_name() {
        return account_name;
    }

    public String getRemark() {
        return remark;
    }

    public double getCa_amount() {
        return ca_amount;
    }

    public double getCash_amount() {
        return cash_amount;
    }

    public double getCard_amount() {
        return card_amount;
    }

    public String getCard_type() {
        return card_type;
    }

    public String getBank_account() {
        return bank_account;
    }

    public String getBank_name() {
        return bank_name;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public String getDate_time() {
        return date_time;
    }

    public void setIf_data_synced(int if_data_synced) {
        this.if_data_synced = if_data_synced;
    }

    public int getIf_data_synced() {
        return if_data_synced;
    }
}
