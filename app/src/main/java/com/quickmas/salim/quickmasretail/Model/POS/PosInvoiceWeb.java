package com.quickmas.salim.quickmasretail.Model.POS;

import android.content.ContentValues;
import android.database.Cursor;

import com.quickmas.salim.quickmasretail.Database.DBInitialization;

import java.util.ArrayList;

import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_CustomerName_web;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_additionalExpenses_web;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_bankName_web;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_cardNumber_web;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_cardPayment_web;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_cashPayment_web;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_discount_web;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_exchange_web;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_id_web;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_invoiceID_web;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_payLaterAmount_web;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_productId_web;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_productSKU_web;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_quantity_web;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_saleType_web;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_salesPerson_web;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_status_web;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_taxIncluded_web;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_tax_web;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_unitPrice_web;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.TABLE_PosInvoice_web;


/**
 * Created by Forhad on 27/11/2018.
 */

public class PosInvoiceWeb {
    public int id;
    public int invoiceID;
    public String saleType;
    public String productName;
    public int productId;
    public int quantity;
    public int exchange;
    public double unitPrice;
    public double tax;
    public double additionalExpenses;
    public double discount;
    public int taxIncluded;
    public String salesPerson;
    public String CustomerName;
    public String cardNumber;
    public String bankName;
    public double cashPayment;
    public double cardPayment;
    public double payLaterAmount;
    public int status;
    public static String posPrefix="INV-POS-";


    ContentValues getContectValue(DBInitialization db)
    {
        ContentValues insertValues = new ContentValues();
        if(this.getId()>0)
        {
            insertValues.put(COLUMN_pos_invoice_id_web, this.getId());
        }
        insertValues.put(COLUMN_pos_invoice_invoiceID_web, this.getInvoiceID());
        insertValues.put(COLUMN_pos_invoice_saleType_web, this.getSaleType());
        insertValues.put(COLUMN_pos_invoice_productId_web, this.getProductId());
        insertValues.put(COLUMN_pos_invoice_productSKU_web, this.getProductName());
        insertValues.put(COLUMN_pos_invoice_quantity_web, this.getQuantity());
        insertValues.put(COLUMN_pos_invoice_exchange_web, this.getExchange());
        insertValues.put(COLUMN_pos_invoice_unitPrice_web, this.getUnitPrice());
        insertValues.put(COLUMN_pos_invoice_tax_web, this.getTax());
        insertValues.put(COLUMN_pos_invoice_additionalExpenses_web, this.getAdditionalExpenses());
        insertValues.put(COLUMN_pos_invoice_discount_web, this.getDiscount());
        insertValues.put(COLUMN_pos_invoice_taxIncluded_web, this.getTaxIncluded());
        insertValues.put(COLUMN_pos_invoice_salesPerson_web, this.getSalesPerson());
        insertValues.put(COLUMN_pos_invoice_CustomerName_web, this.getCustomerName());
        insertValues.put(COLUMN_pos_invoice_cardNumber_web, this.getCardNumber());
        insertValues.put(COLUMN_pos_invoice_bankName_web, this.getBankName());
        insertValues.put(COLUMN_pos_invoice_cashPayment_web, this.getCashPayment());
        insertValues.put(COLUMN_pos_invoice_cardPayment_web, this.getCardPayment());
        insertValues.put(COLUMN_pos_invoice_payLaterAmount_web, this.getPayLaterAmount());
        insertValues.put(COLUMN_pos_invoice_status_web, this.getStatus());
        return  insertValues;
    }

    public void insert(DBInitialization db)
    {
        String con = COLUMN_pos_invoice_invoiceID_web+"="+this.getInvoiceID()+" AND "+COLUMN_pos_invoice_productId_web+"="+this.getProductId();
        db.insertData(getContectValue(db), TABLE_PosInvoice_web,con);
    }

    public void update(DBInitialization db)
    {
        db.updateData(getContectValue(db), TABLE_PosInvoice_web,db.COLUMN_pos_invoice_id_web+"="+this.getId());
    }
    public static void update(DBInitialization db,String data,String con)
    {
        db.updateData(data,con, TABLE_PosInvoice_web);
    }

    public static int count(DBInitialization db, String con)
    {
        return db. getDataCount(TABLE_PosInvoice_web,con);
    }

    public static int sumData(DBInitialization db,String sel, String con)
    {
        return db.sumData(sel, TABLE_PosInvoice_web, con);
    }

    public static ArrayList<PosInvoiceWeb> getPendingInvoices(DBInitialization db)
    {
        return select(db, COLUMN_pos_invoice_status_web+"=0");
    }
    public static ArrayList<PosInvoiceWeb> getPaymentPendingInvoices(DBInitialization db)
    {
        return select(db, COLUMN_pos_invoice_status_web+"=1 OR "+COLUMN_pos_invoice_status_web+"=0");
    }
    public static ArrayList<PosInvoiceWeb> select(DBInitialization db, String con)
    {
        Cursor c = db.getData("*", TABLE_PosInvoice_web,con,"");
        ArrayList<PosInvoiceWeb> invoices = new ArrayList<PosInvoiceWeb>();
        if (c !=null && c.getCount() > 0) {
            c.moveToFirst();
            do {
                PosInvoiceWeb invoice = new PosInvoiceWeb();
                invoice.setId(c.getInt(c.getColumnIndex(db.COLUMN_pos_invoice_id_web)));
                invoice.setInvoiceID(c.getInt(c.getColumnIndex(COLUMN_pos_invoice_invoiceID_web)));
                invoice.setSaleType(c.getString(c.getColumnIndex(COLUMN_pos_invoice_saleType_web)));
                invoice.setProductId(c.getInt(c.getColumnIndex(COLUMN_pos_invoice_productId_web)));
                invoice.setProductName(c.getString(c.getColumnIndex(COLUMN_pos_invoice_productSKU_web)));
                invoice.setQuantity(c.getInt(c.getColumnIndex(COLUMN_pos_invoice_quantity_web)));
                invoice.setExchange(c.getInt(c.getColumnIndex(COLUMN_pos_invoice_exchange_web)));
                invoice.setUnitPrice(c.getFloat(c.getColumnIndex(COLUMN_pos_invoice_unitPrice_web)));
                invoice.setTax(c.getFloat(c.getColumnIndex(COLUMN_pos_invoice_tax_web)));
                invoice.setAdditionalExpenses(c.getFloat(c.getColumnIndex(COLUMN_pos_invoice_additionalExpenses_web)));
                invoice.setDiscount(c.getFloat(c.getColumnIndex(COLUMN_pos_invoice_discount_web)));
                invoice.setTaxIncluded(c.getInt(c.getColumnIndex(COLUMN_pos_invoice_taxIncluded_web)));
                invoice.setSalesPerson(c.getString(c.getColumnIndex(COLUMN_pos_invoice_salesPerson_web)));
                invoice.setCustomerName(c.getString(c.getColumnIndex(COLUMN_pos_invoice_CustomerName_web)));
                invoice.setCardNumber(c.getString(c.getColumnIndex(COLUMN_pos_invoice_cardNumber_web)));
                invoice.setBankName(c.getString(c.getColumnIndex(COLUMN_pos_invoice_bankName_web)));
                invoice.setCashPayment(c.getFloat(c.getColumnIndex(COLUMN_pos_invoice_cashPayment_web)));
                invoice.setCardPayment(c.getFloat(c.getColumnIndex(COLUMN_pos_invoice_cardPayment_web)));
                invoice.setPayLaterAmount(c.getFloat(c.getColumnIndex(COLUMN_pos_invoice_payLaterAmount_web)));
                invoice.setStatus(c.getInt(c.getColumnIndex(COLUMN_pos_invoice_status_web)));
                invoices.add(invoice);
            } while (c.moveToNext());
        }
        return invoices;
    }

    public static int getTotalInvoice(DBInitialization db)
    {
        Cursor c = db.getData("*",TABLE_PosInvoice_web, COLUMN_pos_invoice_status_web+"!=0"+" OR "+ COLUMN_pos_invoice_status_web+"!=4", COLUMN_pos_invoice_invoiceID_web);
        return c.getCount();
    }

    public static void deletePendingInvoices(DBInitialization db,int id)
    {
        db.deleteData( TABLE_PosInvoice_web ,COLUMN_pos_invoice_status_web+"=0 AND "+COLUMN_pos_invoice_invoiceID_web+"="+id);
    }



    public static void updateInvoiceStatus(DBInitialization db,int invoiceID,String newS)
    {
        db.updateData(COLUMN_pos_invoice_status_web+"="+newS,COLUMN_pos_invoice_invoiceID_web+"="+String.valueOf(invoiceID), TABLE_PosInvoice_web);
    }

    public static void updateInvoiceStatus(DBInitialization db,String newS)
    {
        db.updateData(COLUMN_pos_invoice_status_web+"="+newS,COLUMN_pos_invoice_status_web+"=0", TABLE_PosInvoice_web);
    }

    public static void deleteIteamExchange(DBInitialization db)
    {
        db.deleteData( TABLE_PosInvoice_web ,COLUMN_pos_invoice_status_web+"=9");
    }

    public static void deleteIteamFromPendingList(DBInitialization db,int invoiceID,int product)
    {
        db.deleteData( TABLE_PosInvoice_web ,COLUMN_pos_invoice_status_web+"=0 AND "+COLUMN_pos_invoice_invoiceID_web+"="+invoiceID +" AND "+COLUMN_pos_invoice_productId_web+"="+product);
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public static void setPosPrefix(String posPrefix) {
        PosInvoice.posPrefix = posPrefix;
    }

    public String getProductName() {
        return productName;
    }

    public static String getPosPrefix() {
        return posPrefix;
    }

    public static int getMaxInvoice(DBInitialization db)
    {
        return db.getMax(TABLE_PosInvoice_web,COLUMN_pos_invoice_invoiceID_web, COLUMN_pos_invoice_status_web + "!=0");
    }
    public static int getNewInvoice(DBInitialization db)
    {
        return db.getMax(TABLE_PosInvoice_web,COLUMN_pos_invoice_invoiceID_web,  "1=1");
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setInvoiceID(int invoiceID) {
        this.invoiceID = invoiceID;
    }

    public void setSaleType(String saleType) {
        this.saleType = saleType;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public void setAdditionalExpenses(double additionalExpenses) {
        this.additionalExpenses = additionalExpenses;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public void setTaxIncluded(int taxIncluded) {
        this.taxIncluded = taxIncluded;
    }

    public void setSalesPerson(String salesPerson) {
        this.salesPerson = salesPerson;
    }

    public void setCustomerName(String customerName) {
        CustomerName = customerName;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public void setCashPayment(double cashPayment) {
        this.cashPayment = cashPayment;
    }

    public void setCardPayment(double cardPayment) {
        this.cardPayment = cardPayment;
    }

    public void setPayLaterAmount(double payLaterAmount) {
        this.payLaterAmount = payLaterAmount;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setExchange(int exchange) {
        this.exchange = exchange;
    }

    public int getExchange() {
        return exchange;
    }

    public int getId() {
        return id;
    }

    public int getInvoiceID() {
        return invoiceID;
    }

    public String getSaleType() {
        return saleType;
    }

    public int getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public double getTax() {
        return tax;
    }

    public double getAdditionalExpenses() {
        return additionalExpenses;
    }

    public double getDiscount() {
        return discount;
    }

    public int getTaxIncluded() {
        return taxIncluded;
    }

    public String getSalesPerson() {
        return salesPerson;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getBankName() {
        return bankName;
    }

    public double getCashPayment() {
        return cashPayment;
    }

    public double getCardPayment() {
        return cardPayment;
    }

    public double getPayLaterAmount() {
        return payLaterAmount;
    }

    public int getStatus() {
        return status;
    }
}
