package com.quickmas.salim.quickmasretail.Model.POS;

import android.content.ContentValues;
import android.database.Cursor;

import com.quickmas.salim.quickmasretail.Database.DBInitialization;

import java.util.ArrayList;

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
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_head_if_synced;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_head_invoice_date;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_head_invoice_generate_by;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_head_invoice_id;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_head_iteam_discount;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_head_note_given;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_head_pay_later_amount;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_head_payment_mode;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_head_previous_invoice_id;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_head_sale_type;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_head_sales_person;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_head_status;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_head_total_amount;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_head_total_amount_full;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_head_total_paid_after_return;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_head_total_paid_amount;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_head_total_quantity;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_head_total_return_amount;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_head_total_tax;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_head_verify_user;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_head_voucher_no;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.TABLE_pos_invoice_head_regular;

public class PosInvoiceHeadRegular {
    public int id;
    public String invoice_id;
    public String previous_invoice_id;
    public double total_amount;
    public double total_amount_full;
    public int total_quantity;
    public double total_tax;
    public double iteam_discount;
    public double additional_discount;
    public double additional_discount_percent;
    public double delivery_expense;
    public String sales_person;
    public String customer;
    public String payment_mode;
    public double cash_amount;
    public double note_given;
    public double change;
    public double card_amount;
    public String card_type;
    public String cheque_no;
    public String bank;
    public double pay_later_amount;
    public String verify_user;
    public String sale_type;
    public double total_paid_amount;
    public double total_return_amount;
    public double total_paid_after_return;
    public String invoice_generate_by;
    public String invoice_date;
    public int status;
    public int if_synced;
    public String voucher_no;
    ContentValues getContectValue(DBInitialization db)
    {
        ContentValues insertValues = new ContentValues();

        insertValues.put(COLUMN_pos_invoice_head_id, this.getId());
        insertValues.put(COLUMN_pos_invoice_head_invoice_id, this.getInvoice_id());
        insertValues.put(COLUMN_pos_invoice_head_previous_invoice_id, this.getPrevious_invoice_id());
        insertValues.put(COLUMN_pos_invoice_head_total_amount_full, this.getTotal_amount_full());
        insertValues.put(COLUMN_pos_invoice_head_total_amount, this.getTotal_amount());
        insertValues.put(COLUMN_pos_invoice_head_total_quantity, this.getTotal_quantity());
        insertValues.put(COLUMN_pos_invoice_head_total_tax, this.getTotal_tax());
        insertValues.put(COLUMN_pos_invoice_head_iteam_discount, this.getIteam_discount());
        insertValues.put(COLUMN_pos_invoice_head_additional_discount, this.getAdditional_discount());
        insertValues.put(COLUMN_pos_invoice_head_additional_discount_percent, this.getAdditional_discount_percent());
        insertValues.put(COLUMN_pos_invoice_head_delivery_expense, this.getDelivery_expense());
        insertValues.put(COLUMN_pos_invoice_head_sales_person, this.getSales_person());
        insertValues.put(COLUMN_pos_invoice_head_customer, this.getCustomer());
        insertValues.put(COLUMN_pos_invoice_head_payment_mode, this.getPayment_mode());
        insertValues.put(COLUMN_pos_invoice_head_cash_amount, this.getCash_amount());
        insertValues.put(COLUMN_pos_invoice_head_note_given, this.getNote_given());
        insertValues.put(COLUMN_pos_invoice_head_change, this.getChange());
        insertValues.put(COLUMN_pos_invoice_head_card_amount, this.getCard_amount());
        insertValues.put(COLUMN_pos_invoice_head_card_type, this.getCard_type());
        insertValues.put(COLUMN_pos_invoice_head_cheque_no, this.getCheque_no());
        insertValues.put(COLUMN_pos_invoice_head_bank, this.getBank());
        insertValues.put(COLUMN_pos_invoice_head_pay_later_amount, this.getPay_later_amount());
        insertValues.put(COLUMN_pos_invoice_head_verify_user, this.getVerify_user());
        insertValues.put(COLUMN_pos_invoice_head_sale_type, this.getSale_type());
        insertValues.put(COLUMN_pos_invoice_head_total_paid_amount, this.getTotal_paid_amount());
        insertValues.put(COLUMN_pos_invoice_head_total_return_amount, this.getTotal_return_amount());
        insertValues.put(COLUMN_pos_invoice_head_total_paid_after_return, this.getTotal_paid_after_return());
        insertValues.put(COLUMN_pos_invoice_head_invoice_generate_by, this.getInvoice_generate_by());
        insertValues.put(COLUMN_pos_invoice_head_invoice_date, this.getInvoice_date());
        insertValues.put(COLUMN_pos_invoice_head_status, this.getStatus());
        insertValues.put(COLUMN_pos_invoice_head_if_synced, this.getIf_synced());
        insertValues.put(COLUMN_pos_invoice_head_voucher_no, this.getVoucher_no());
        return  insertValues;
    }

    public void insert(DBInitialization db)
    {
        String con = COLUMN_pos_invoice_head_id+"="+this.getId();
        db.insertData(getContectValue(db), TABLE_pos_invoice_head_regular,con);
    }

    public void update(DBInitialization db)
    {
        db.updateData(getContectValue(db), TABLE_pos_invoice_head_regular,db.COLUMN_pos_invoice_head_id+"="+this.getId());
    }
    public static void update(DBInitialization db,String data,String con)
    {
        db.updateData(data,con, TABLE_pos_invoice_head_regular);
    }

    public static ArrayList<PosInvoiceHeadRegular> select(DBInitialization db, String con)
    {
        Cursor c = db.getData("*", TABLE_pos_invoice_head_regular,con,"");
        ArrayList<PosInvoiceHeadRegular> invoices = new ArrayList<PosInvoiceHeadRegular>();
        if (c !=null && c.getCount() > 0) {
            c.moveToFirst();
            do {
                PosInvoiceHeadRegular invoice = new PosInvoiceHeadRegular();
                invoice.setId(c.getInt(c.getColumnIndex(db.COLUMN_pos_invoice_head_id)));
                invoice.setInvoice_id(c.getString(c.getColumnIndex(db.COLUMN_pos_invoice_head_invoice_id)));
                invoice.setPrevious_invoice_id(c.getString(c.getColumnIndex(db.COLUMN_pos_invoice_head_previous_invoice_id)));
                invoice.setTotal_amount_full(c.getDouble(c.getColumnIndex(db.COLUMN_pos_invoice_head_total_amount_full)));
                invoice.setTotal_amount(c.getDouble(c.getColumnIndex(db.COLUMN_pos_invoice_head_total_amount)));
                invoice.setTotal_quantity(c.getInt(c.getColumnIndex(db.COLUMN_pos_invoice_head_total_quantity)));
                invoice.setTotal_tax(c.getDouble(c.getColumnIndex(db.COLUMN_pos_invoice_head_total_tax)));
                invoice.setIteam_discount(c.getDouble(c.getColumnIndex(db.COLUMN_pos_invoice_head_iteam_discount)));
                invoice.setAdditional_discount(c.getDouble(c.getColumnIndex(db.COLUMN_pos_invoice_head_additional_discount)));
                invoice.setAdditional_discount_percent(c.getFloat(c.getColumnIndex(db.COLUMN_pos_invoice_head_additional_discount_percent)));
                invoice.setDelivery_expense(c.getDouble(c.getColumnIndex(db.COLUMN_pos_invoice_head_delivery_expense)));
                invoice.setSales_person(c.getString(c.getColumnIndex(db.COLUMN_pos_invoice_head_sales_person)));
                invoice.setCustomer(c.getString(c.getColumnIndex(db.COLUMN_pos_invoice_head_customer)));
                invoice.setPayment_mode(c.getString(c.getColumnIndex(db.COLUMN_pos_invoice_head_payment_mode)));
                invoice.setCash_amount(c.getDouble(c.getColumnIndex(db.COLUMN_pos_invoice_head_cash_amount)));
                invoice.setNote_given(c.getInt(c.getColumnIndex(db.COLUMN_pos_invoice_head_note_given)));
                invoice.setChange(c.getDouble(c.getColumnIndex(db.COLUMN_pos_invoice_head_change)));
                invoice.setCard_amount(c.getDouble(c.getColumnIndex(db.COLUMN_pos_invoice_head_card_amount)));
                invoice.setCard_type(c.getString(c.getColumnIndex(db.COLUMN_pos_invoice_head_card_type)));
                invoice.setCheque_no(c.getString(c.getColumnIndex(db.COLUMN_pos_invoice_head_cheque_no)));
                invoice.setBank(c.getString(c.getColumnIndex(db.COLUMN_pos_invoice_head_bank)));
                invoice.setPay_later_amount(c.getFloat(c.getColumnIndex(db.COLUMN_pos_invoice_head_pay_later_amount)));
                invoice.setVerify_user(c.getString(c.getColumnIndex(db.COLUMN_pos_invoice_head_verify_user)));
                invoice.setSale_type(c.getString(c.getColumnIndex(db.COLUMN_pos_invoice_head_sale_type)));
                invoice.setTotal_paid_amount(c.getFloat(c.getColumnIndex(db.COLUMN_pos_invoice_head_total_paid_amount)));
                invoice.setTotal_return_amount(c.getFloat(c.getColumnIndex(db.COLUMN_pos_invoice_head_total_return_amount)));
                invoice.setTotal_paid_after_return(c.getFloat(c.getColumnIndex(db.COLUMN_pos_invoice_head_total_paid_after_return)));
                invoice.setInvoice_generate_by(c.getString(c.getColumnIndex(db.COLUMN_pos_invoice_head_invoice_generate_by)));
                invoice.setInvoice_date(c.getString(c.getColumnIndex(db.COLUMN_pos_invoice_head_invoice_date)));
                invoice.setStatus(c.getInt(c.getColumnIndex(db.COLUMN_pos_invoice_head_status)));
                invoice.setIf_synced(c.getInt(c.getColumnIndex(db.COLUMN_pos_invoice_head_if_synced)));
                invoice.setVoucher_no(c.getString(c.getColumnIndex(db.COLUMN_pos_invoice_head_voucher_no)));
                invoices.add(invoice);
            } while (c.moveToNext());
        }
        return invoices;
    }
    public static int getCount(DBInitialization db)
    {
        return db.getDataCount(TABLE_pos_invoice_head_regular,"1=1");
    }

    public double getIteam_discount() {
        return iteam_discount;
    }

    public void setPrevious_invoice_id(String previous_invoice_id) {
        this.previous_invoice_id = previous_invoice_id;
    }

    public String getPrevious_invoice_id() {
        return previous_invoice_id;
    }

    public void setIteam_discount(double iteam_discount) {
        this.iteam_discount = iteam_discount;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setInvoice_id(String invoice_id) {
        this.invoice_id = invoice_id;
    }

    public void setTotal_amount(double total_amount) {
        this.total_amount = total_amount;
    }

    public void setAdditional_discount(double additional_discount) {
        this.additional_discount = additional_discount;
    }

    public void setAdditional_discount_percent(double additional_discount_percent) {
        this.additional_discount_percent = additional_discount_percent;
    }

    public void setSales_person(String sales_person) {
        this.sales_person = sales_person;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public void setPayment_mode(String payment_mode) {
        this.payment_mode = payment_mode;
    }

    public void setCash_amount(double cash_amount) {
        this.cash_amount = cash_amount;
    }

    public void setNote_given(double note_given) {
        this.note_given = note_given;
    }

    public void setChange(double change) {
        this.change = change;
    }

    public void setCard_amount(double card_amount) {
        this.card_amount = card_amount;
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

    public void setPay_later_amount(double pay_later_amount) {
        this.pay_later_amount = pay_later_amount;
    }

    public void setVerify_user(String verify_user) {
        this.verify_user = verify_user;
    }

    public void setSale_type(String sale_type) {
        this.sale_type = sale_type;
    }

    public void setInvoice_date(String invoice_date) {
        this.invoice_date = invoice_date;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setDelivery_expense(double delivery_expense) {
        this.delivery_expense = delivery_expense;
    }

    public void setTotal_quantity(int total_quantity) {
        this.total_quantity = total_quantity;
    }

    public void setInvoice_generate_by(String invoice_generate_by) {
        this.invoice_generate_by = invoice_generate_by;
    }

    public void setTotal_return_amount(double total_return_amount) {
        this.total_return_amount = total_return_amount;
    }

    public void setTotal_paid_after_return(double total_paid_after_return) {
        this.total_paid_after_return = total_paid_after_return;
    }

    public double getTotal_return_amount() {
        return total_return_amount;
    }

    public double getTotal_paid_after_return() {
        return total_paid_after_return;
    }

    public void setTotal_amount_full(double total_amount_full) {
        this.total_amount_full = total_amount_full;
    }

    public double getTotal_paid_amount() {
        return total_paid_amount;
    }

    public void setTotal_paid_amount(double total_paid_amount) {
        this.total_paid_amount = total_paid_amount;
    }

    public double getTotal_amount_full() {
        return total_amount_full;
    }

    public String getInvoice_generate_by() {
        return invoice_generate_by;
    }

    public int getTotal_quantity() {
        return total_quantity;
    }

    public double getDelivery_expense() {
        return delivery_expense;
    }

    public int getId() {
        return id;
    }

    public double getTotal_tax() {
        return total_tax;
    }

    public void setTotal_tax(double total_tax) {
        this.total_tax = total_tax;
    }

    public String getInvoice_id() {
        return invoice_id;
    }

    public double getTotal_amount() {
        return total_amount;
    }

    public double getAdditional_discount() {
        return additional_discount;
    }

    public double getAdditional_discount_percent() {
        return additional_discount_percent;
    }

    public String getSales_person() {
        return sales_person;
    }

    public String getCustomer() {
        return customer;
    }

    public String getPayment_mode() {
        return payment_mode;
    }

    public double getCash_amount() {
        return cash_amount;
    }

    public double getNote_given() {
        return note_given;
    }

    public double getChange() {
        return change;
    }

    public double getCard_amount() {
        return card_amount;
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

    public double getPay_later_amount() {
        return pay_later_amount;
    }

    public String getVerify_user() {
        return verify_user;
    }

    public String getSale_type() {
        return sale_type;
    }

    public String getInvoice_date() {
        return invoice_date;
    }

    public int getStatus() {
        return status;
    }

    public void setIf_synced(int if_synced) {
        this.if_synced = if_synced;
    }

    public int getIf_synced() {
        return if_synced;
    }

    public void setVoucher_no(String voucher_no) {
        this.voucher_no = voucher_no;
    }

    public String getVoucher_no() {
        return voucher_no;
    }
}
