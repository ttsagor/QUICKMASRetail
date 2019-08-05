package com.quickmas.salim.quickmasretail.Model.POS;

import android.content.ContentValues;
import android.database.Cursor;

import com.quickmas.salim.quickmasretail.Database.DBInitialization;

import java.util.ArrayList;

import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_head_additional_discount_percent_web;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_head_additional_discount_web;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_head_bank_web;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_head_card_amount_web;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_head_card_type_web;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_head_cash_amount_web;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_head_change_web;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_head_cheque_no_web;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_head_customer_web;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_head_delivery_expense_web;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_head_id_web;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_head_invoice_date_web;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_head_invoice_generate_by_web;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_head_invoice_id_web;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_head_iteam_discount_web;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_head_new_paid_amount_web;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_head_note_given_web;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_head_pay_later_amount_web;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_head_payment_mode_web;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_head_previous_invoice_id_web;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_head_sale_type_web;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_head_sales_person_web;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_head_status_web;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_head_total_amount_full_web;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_head_total_amount_web;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_head_total_paid_after_return_web;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_head_total_paid_amount_web;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_head_total_quantity_web;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_head_total_return_amount_web;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_head_total_tax_web;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_head_verify_user_web;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_head_voucher_no_web;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.TABLE_pos_invoice_head_web;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.TABLE_pos_invoice_regular_head_web;

public class PosInvoiceHeadRegularWeb {

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
    public double new_paid_amount;
    public double total_return_amount;
    public double total_paid_after_return;
    public String invoice_generate_by;
    public String invoice_date;
    public String voucher_no;
    public int status;

    ContentValues getContectValue(DBInitialization db)
    {
        ContentValues insertValues = new ContentValues();

        insertValues.put(COLUMN_pos_invoice_head_id_web, this.getId());
        insertValues.put(COLUMN_pos_invoice_head_invoice_id_web, this.getInvoice_id());
        insertValues.put(COLUMN_pos_invoice_head_previous_invoice_id_web, this.getPrevious_invoice_id());
        insertValues.put(COLUMN_pos_invoice_head_total_amount_full_web, this.getTotal_amount_full());
        insertValues.put(COLUMN_pos_invoice_head_total_amount_web, this.getTotal_amount());
        insertValues.put(COLUMN_pos_invoice_head_total_quantity_web, this.getTotal_quantity());
        insertValues.put(COLUMN_pos_invoice_head_total_tax_web, this.getTotal_tax());
        insertValues.put(COLUMN_pos_invoice_head_iteam_discount_web, this.getIteam_discount());
        insertValues.put(COLUMN_pos_invoice_head_additional_discount_web, this.getAdditional_discount());
        insertValues.put(COLUMN_pos_invoice_head_additional_discount_percent_web, this.getAdditional_discount_percent());
        insertValues.put(COLUMN_pos_invoice_head_delivery_expense_web, this.getDelivery_expense());
        insertValues.put(COLUMN_pos_invoice_head_sales_person_web, this.getSales_person());
        insertValues.put(COLUMN_pos_invoice_head_customer_web, this.getCustomer());
        insertValues.put(COLUMN_pos_invoice_head_payment_mode_web, this.getPayment_mode());
        insertValues.put(COLUMN_pos_invoice_head_cash_amount_web, this.getCash_amount());
        insertValues.put(COLUMN_pos_invoice_head_note_given_web, this.getNote_given());
        insertValues.put(COLUMN_pos_invoice_head_change_web, this.getChange());
        insertValues.put(COLUMN_pos_invoice_head_card_amount_web, this.getCard_amount());
        insertValues.put(COLUMN_pos_invoice_head_card_type_web, this.getCard_type());
        insertValues.put(COLUMN_pos_invoice_head_cheque_no_web, this.getCheque_no());
        insertValues.put(COLUMN_pos_invoice_head_bank_web, this.getBank());
        insertValues.put(COLUMN_pos_invoice_head_pay_later_amount_web, this.getPay_later_amount());
        insertValues.put(COLUMN_pos_invoice_head_verify_user_web, this.getVerify_user());
        insertValues.put(COLUMN_pos_invoice_head_sale_type_web, this.getSale_type());
        insertValues.put(COLUMN_pos_invoice_head_total_paid_amount_web, this.getTotal_paid_amount());
        insertValues.put(COLUMN_pos_invoice_head_new_paid_amount_web, this.getNew_paid_amount());
        insertValues.put(COLUMN_pos_invoice_head_total_return_amount_web, this.getTotal_return_amount());
        insertValues.put(COLUMN_pos_invoice_head_total_paid_after_return_web, this.getTotal_paid_after_return());
        insertValues.put(COLUMN_pos_invoice_head_invoice_generate_by_web, this.getInvoice_generate_by());
        insertValues.put(COLUMN_pos_invoice_head_invoice_date_web, this.getInvoice_date());
        insertValues.put(COLUMN_pos_invoice_head_status_web, this.getStatus());
        insertValues.put(COLUMN_pos_invoice_head_voucher_no_web, this.getVoucher_no());
        return  insertValues;
    }

    public void insert(DBInitialization db)
    {
        String con = COLUMN_pos_invoice_head_id_web+"="+this.getId();
        db.insertData(getContectValue(db), TABLE_pos_invoice_regular_head_web,con);
    }

    public void update(DBInitialization db)
    {
        db.updateData(getContectValue(db), TABLE_pos_invoice_regular_head_web, COLUMN_pos_invoice_head_id_web+"="+this.getId());
    }
    public static void update(DBInitialization db,String data,String con)
    {
        db.updateData(data,con, TABLE_pos_invoice_regular_head_web);
    }

    public static ArrayList<PosInvoiceHeadRegularWeb> select(DBInitialization db, String con)
    {
        Cursor c = db.getData("*", TABLE_pos_invoice_regular_head_web,con,"");
        ArrayList<PosInvoiceHeadRegularWeb> invoices = new ArrayList<PosInvoiceHeadRegularWeb>();
        if (c !=null && c.getCount() > 0) {
            c.moveToFirst();
            do {
                PosInvoiceHeadRegularWeb invoice = new PosInvoiceHeadRegularWeb();
                invoice.setId(c.getInt(c.getColumnIndex(COLUMN_pos_invoice_head_id_web)));
                invoice.setInvoice_id(c.getString(c.getColumnIndex(COLUMN_pos_invoice_head_invoice_id_web)));
                invoice.setPrevious_invoice_id(c.getString(c.getColumnIndex(COLUMN_pos_invoice_head_previous_invoice_id_web)));
                invoice.setTotal_amount_full(c.getDouble(c.getColumnIndex(COLUMN_pos_invoice_head_total_amount_full_web)));
                invoice.setTotal_amount(c.getDouble(c.getColumnIndex(COLUMN_pos_invoice_head_total_amount_web)));
                invoice.setTotal_quantity(c.getInt(c.getColumnIndex(COLUMN_pos_invoice_head_total_quantity_web)));
                invoice.setTotal_tax(c.getDouble(c.getColumnIndex(COLUMN_pos_invoice_head_total_tax_web)));
                invoice.setIteam_discount(c.getDouble(c.getColumnIndex(db.COLUMN_pos_invoice_head_iteam_discount)));
                invoice.setAdditional_discount(c.getDouble(c.getColumnIndex(COLUMN_pos_invoice_head_additional_discount_web)));
                invoice.setAdditional_discount_percent(c.getFloat(c.getColumnIndex(COLUMN_pos_invoice_head_additional_discount_percent_web)));
                invoice.setDelivery_expense(c.getDouble(c.getColumnIndex(COLUMN_pos_invoice_head_delivery_expense_web)));
                invoice.setSales_person(c.getString(c.getColumnIndex(COLUMN_pos_invoice_head_sales_person_web)));
                invoice.setCustomer(c.getString(c.getColumnIndex(COLUMN_pos_invoice_head_customer_web)));
                invoice.setPayment_mode(c.getString(c.getColumnIndex(COLUMN_pos_invoice_head_payment_mode_web)));
                invoice.setCash_amount(c.getDouble(c.getColumnIndex(COLUMN_pos_invoice_head_cash_amount_web)));
                invoice.setNote_given(c.getInt(c.getColumnIndex(COLUMN_pos_invoice_head_note_given_web)));
                invoice.setChange(c.getDouble(c.getColumnIndex(COLUMN_pos_invoice_head_change_web)));
                invoice.setCard_amount(c.getDouble(c.getColumnIndex(COLUMN_pos_invoice_head_card_amount_web)));
                invoice.setCard_type(c.getString(c.getColumnIndex(COLUMN_pos_invoice_head_card_type_web)));
                invoice.setCheque_no(c.getString(c.getColumnIndex(COLUMN_pos_invoice_head_cheque_no_web)));
                invoice.setBank(c.getString(c.getColumnIndex(COLUMN_pos_invoice_head_bank_web)));
                invoice.setPay_later_amount(c.getFloat(c.getColumnIndex(COLUMN_pos_invoice_head_pay_later_amount_web)));
                invoice.setVerify_user(c.getString(c.getColumnIndex(COLUMN_pos_invoice_head_verify_user_web)));
                invoice.setSale_type(c.getString(c.getColumnIndex(COLUMN_pos_invoice_head_sale_type_web)));
                invoice.setTotal_paid_amount(c.getFloat(c.getColumnIndex(COLUMN_pos_invoice_head_total_paid_amount_web)));
                invoice.setNew_paid_amount(c.getFloat(c.getColumnIndex(COLUMN_pos_invoice_head_new_paid_amount_web)));
                invoice.setTotal_return_amount(c.getFloat(c.getColumnIndex(COLUMN_pos_invoice_head_total_return_amount_web)));
                invoice.setTotal_paid_after_return(c.getFloat(c.getColumnIndex(COLUMN_pos_invoice_head_total_paid_after_return_web)));
                invoice.setInvoice_generate_by(c.getString(c.getColumnIndex(COLUMN_pos_invoice_head_invoice_generate_by_web)));
                invoice.setInvoice_date(c.getString(c.getColumnIndex(COLUMN_pos_invoice_head_invoice_date_web)));
                invoice.setStatus(c.getInt(c.getColumnIndex(COLUMN_pos_invoice_head_status_web)));
                invoice.setVoucher_no(c.getString(c.getColumnIndex(COLUMN_pos_invoice_head_voucher_no_web)));
                invoices.add(invoice);
            } while (c.moveToNext());
        }
        return invoices;
    }
    public static int getCount(DBInitialization db)
    {
        return db.getDataCount(TABLE_pos_invoice_regular_head_web,"1=1");
    }

    public double getIteam_discount() {
        return iteam_discount;
    }

    public void setPrevious_invoice_id(String previous_invoice_id) {
        this.previous_invoice_id = previous_invoice_id;
    }

    public void setNew_paid_amount(double new_paid_amount) {
        this.new_paid_amount = new_paid_amount;
    }

    public double getNew_paid_amount() {
        return new_paid_amount;
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

    public void setVoucher_no(String voucher_no) {
        this.voucher_no = voucher_no;
    }

    public String getVoucher_no() {
        return voucher_no;
    }
}
