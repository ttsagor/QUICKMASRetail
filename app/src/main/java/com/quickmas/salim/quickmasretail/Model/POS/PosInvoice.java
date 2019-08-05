package com.quickmas.salim.quickmasretail.Model.POS;

import android.content.ContentValues;
import android.database.Cursor;

import com.quickmas.salim.quickmasretail.Database.DBInitialization;

import java.util.ArrayList;


import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_CustomerName;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_additionalExpenses;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_bankName;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_cardNumber;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_cardPayment;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_cashPayment;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_discount;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_exchange;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_id;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_if_synced;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_invoiceID;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_payLaterAmount;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_productId;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_productSKU;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_quantity;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_saleType;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_salesPerson;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_status;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_tax;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_taxIncluded;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_unitPrice;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.TABLE_PosInvoice;

/**
 * Created by Forhad on 24/04/2018.
 */

public class PosInvoice {
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
    public int if_synced;
    public static String posPrefix="INV-POS-";


    ContentValues getContectValue(DBInitialization db)
    {
        ContentValues insertValues = new ContentValues();
        if(this.getId()>0)
        {
            insertValues.put(COLUMN_pos_invoice_id, this.getId());
        }
        insertValues.put(COLUMN_pos_invoice_invoiceID, this.getInvoiceID());
        insertValues.put(COLUMN_pos_invoice_saleType, this.getSaleType());
        insertValues.put(COLUMN_pos_invoice_productId, this.getProductId());
        insertValues.put(COLUMN_pos_invoice_productSKU, this.getProductName());
        insertValues.put(COLUMN_pos_invoice_quantity, this.getQuantity());
        insertValues.put(COLUMN_pos_invoice_exchange, this.getExchange());
        insertValues.put(COLUMN_pos_invoice_unitPrice, this.getUnitPrice());
        insertValues.put(COLUMN_pos_invoice_tax, this.getTax());
        insertValues.put(COLUMN_pos_invoice_additionalExpenses, this.getAdditionalExpenses());
        insertValues.put(COLUMN_pos_invoice_discount, this.getDiscount());
        insertValues.put(COLUMN_pos_invoice_taxIncluded, this.getTaxIncluded());
        insertValues.put(COLUMN_pos_invoice_salesPerson, this.getSalesPerson());
        insertValues.put(COLUMN_pos_invoice_CustomerName, this.getCustomerName());
        insertValues.put(COLUMN_pos_invoice_cardNumber, this.getCardNumber());
        insertValues.put(COLUMN_pos_invoice_bankName, this.getBankName());
        insertValues.put(COLUMN_pos_invoice_cashPayment, this.getCashPayment());
        insertValues.put(COLUMN_pos_invoice_cardPayment, this.getCardPayment());
        insertValues.put(COLUMN_pos_invoice_payLaterAmount, this.getPayLaterAmount());
        insertValues.put(COLUMN_pos_invoice_if_synced, this.getStatus());
        insertValues.put(COLUMN_pos_invoice_status, this.getIf_synced());
        return  insertValues;
    }

    public void insert(DBInitialization db)
    {
        String con = COLUMN_pos_invoice_invoiceID+"="+this.getInvoiceID()+" AND "+COLUMN_pos_invoice_productId+"="+this.getProductId();
        db.insertData(getContectValue(db), TABLE_PosInvoice,con);
    }

    public void update(DBInitialization db)
    {
        db.updateData(getContectValue(db), TABLE_PosInvoice,db.COLUMN_pos_invoice_id+"="+this.getId());
    }
    public static void update(DBInitialization db,String data,String con)
    {
        db.updateData(data,con, TABLE_PosInvoice);
    }

    public static int count(DBInitialization db, String con)
    {
        return db. getDataCount(TABLE_PosInvoice,con);
    }

    public static int sumData(DBInitialization db,String sel, String con)
    {
        return db.sumData(sel, TABLE_PosInvoice, con);
    }

    public static ArrayList<PosInvoice> getPendingInvoices(DBInitialization db)
    {
        return select(db, COLUMN_pos_invoice_status+"=0");
    }
    public static ArrayList<PosInvoice> getPaymentPendingInvoices(DBInitialization db)
    {
        return select(db, COLUMN_pos_invoice_status+"=1 OR "+COLUMN_pos_invoice_status+"=0");
    }
    public static ArrayList<PosInvoice> select(DBInitialization db, String con)
    {
        Cursor c = db.getData("*",db.TABLE_PosInvoice,con,"");
        ArrayList<PosInvoice> invoices = new ArrayList<PosInvoice>();
        if (c !=null && c.getCount() > 0) {
            c.moveToFirst();
            do {
                PosInvoice invoice = new PosInvoice();
                invoice.setId(c.getInt(c.getColumnIndex(db.COLUMN_pos_invoice_id)));
                invoice.setInvoiceID(c.getInt(c.getColumnIndex(db.COLUMN_pos_invoice_invoiceID)));
                invoice.setSaleType(c.getString(c.getColumnIndex(db.COLUMN_pos_invoice_saleType)));
                invoice.setProductId(c.getInt(c.getColumnIndex(db.COLUMN_pos_invoice_productId)));
                invoice.setProductName(c.getString(c.getColumnIndex(db.COLUMN_pos_invoice_productSKU)));
                invoice.setQuantity(c.getInt(c.getColumnIndex(db.COLUMN_pos_invoice_quantity)));
                invoice.setExchange(c.getInt(c.getColumnIndex(db.COLUMN_pos_invoice_exchange)));
                invoice.setUnitPrice(c.getFloat(c.getColumnIndex(db.COLUMN_pos_invoice_unitPrice)));
                invoice.setTax(c.getFloat(c.getColumnIndex(db.COLUMN_pos_invoice_tax)));
                invoice.setAdditionalExpenses(c.getFloat(c.getColumnIndex(db.COLUMN_pos_invoice_additionalExpenses)));
                invoice.setDiscount(c.getFloat(c.getColumnIndex(db.COLUMN_pos_invoice_discount)));
                invoice.setTaxIncluded(c.getInt(c.getColumnIndex(db.COLUMN_pos_invoice_taxIncluded)));
                invoice.setSalesPerson(c.getString(c.getColumnIndex(db.COLUMN_pos_invoice_salesPerson)));
                invoice.setCustomerName(c.getString(c.getColumnIndex(db.COLUMN_pos_invoice_CustomerName)));
                invoice.setCardNumber(c.getString(c.getColumnIndex(db.COLUMN_pos_invoice_cardNumber)));
                invoice.setBankName(c.getString(c.getColumnIndex(db.COLUMN_pos_invoice_bankName)));
                invoice.setCashPayment(c.getFloat(c.getColumnIndex(db.COLUMN_pos_invoice_cashPayment)));
                invoice.setCardPayment(c.getFloat(c.getColumnIndex(db.COLUMN_pos_invoice_cardPayment)));
                invoice.setPayLaterAmount(c.getFloat(c.getColumnIndex(db.COLUMN_pos_invoice_payLaterAmount)));
                invoice.setStatus(c.getInt(c.getColumnIndex(db.COLUMN_pos_invoice_status)));
                invoice.setIf_synced(c.getInt(c.getColumnIndex(db.COLUMN_pos_invoice_if_synced)));
                invoices.add(invoice);
            } while (c.moveToNext());
        }
        return invoices;
    }

    public static int getTotalInvoice(DBInitialization db)
    {
        Cursor c = db.getData("*",TABLE_PosInvoice,db.COLUMN_pos_invoice_status+"!=0"+" OR "+db.COLUMN_pos_invoice_status+"!=4",db.COLUMN_pos_invoice_invoiceID);
        return c.getCount();
    }

    public static void deletePendingInvoices(DBInitialization db,int id)
    {
        db.deleteData( TABLE_PosInvoice ,COLUMN_pos_invoice_status+"=0 AND "+COLUMN_pos_invoice_invoiceID+"="+id);
    }



    public static void updateInvoiceStatus(DBInitialization db,int invoiceID,String newS)
    {
        db.updateData(COLUMN_pos_invoice_status+"="+newS,COLUMN_pos_invoice_invoiceID+"="+String.valueOf(invoiceID), TABLE_PosInvoice);
    }

    public static void updateInvoiceStatus(DBInitialization db,String newS)
    {
        db.updateData(COLUMN_pos_invoice_status+"="+newS,COLUMN_pos_invoice_status+"=0", TABLE_PosInvoice);
    }

    public static void deleteIteamExchange(DBInitialization db)
    {
        db.deleteData( TABLE_PosInvoice ,COLUMN_pos_invoice_status+"=9");
    }

    public static void deleteIteamFromPendingList(DBInitialization db,int invoiceID,int product)
    {
        db.deleteData( TABLE_PosInvoice ,COLUMN_pos_invoice_status+"=0 AND "+COLUMN_pos_invoice_invoiceID+"="+invoiceID +" AND "+COLUMN_pos_invoice_productId+"="+product);
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
        return db.getMax(TABLE_PosInvoice,COLUMN_pos_invoice_invoiceID, db.COLUMN_pos_invoice_status + "!=0");
    }
    public static int getNewInvoice(DBInitialization db)
    {
        return db.getMax(TABLE_PosInvoice,COLUMN_pos_invoice_invoiceID,  "1=1");
    }

    public void setIf_synced(int if_synced) {
        this.if_synced = if_synced;
    }

    public int getIf_synced() {
        return if_synced;
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
