package com.quickmas.salim.quickmasretail.Structure;

import com.quickmas.salim.quickmasretail.Model.POS.PosInvoice;
import com.quickmas.salim.quickmasretail.Model.POS.PosInvoiceWeb;

import java.util.ArrayList;

/**
 * Created by Forhad on 27/11/2018.
 */

public class PosInvoiceCombian {
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
    public String category;
    public static String posPrefix="INV-POS-";


    public static ArrayList<PosInvoiceCombian> setPosInvoiceCombian(ArrayList<PosInvoice> posInvoices)
    {
        ArrayList<PosInvoiceCombian> posInvoiceCombians = new ArrayList<>();
        for(PosInvoice posInvoice : posInvoices)
        {
            PosInvoiceCombian combian = new PosInvoiceCombian();
            combian.id=posInvoice.id;
            combian.invoiceID=posInvoice.invoiceID;
            combian.saleType=posInvoice.saleType;
            combian.productName=posInvoice.productName;
            combian.productId=posInvoice.productId;
            combian.quantity=posInvoice.quantity;
            combian.exchange=posInvoice.exchange;
            combian.unitPrice=posInvoice.unitPrice;
            combian.tax=posInvoice.tax;
            combian.additionalExpenses=posInvoice.additionalExpenses;
            combian.discount=posInvoice.discount;
            combian.taxIncluded=posInvoice.taxIncluded;
            combian.salesPerson=posInvoice.salesPerson;
            combian.CustomerName=posInvoice.CustomerName;
            combian.cardNumber=posInvoice.cardNumber;
            combian.bankName=posInvoice.bankName;
            combian.cashPayment=posInvoice.cashPayment;
            combian.cardPayment=posInvoice.cardPayment;
            combian.payLaterAmount=posInvoice.payLaterAmount;
            combian.status=posInvoice.status;
            combian.if_synced=posInvoice.if_synced;
            combian.category = "P";
            posInvoiceCombians.add(combian);
        }
        return posInvoiceCombians;
    }

    public static ArrayList<PosInvoiceCombian> setPosInvoiceCombianWeb(ArrayList<PosInvoiceWeb> posInvoices)
    {
        ArrayList<PosInvoiceCombian> posInvoiceCombians = new ArrayList<>();
        for(PosInvoiceWeb posInvoice : posInvoices)
        {
            PosInvoiceCombian combian = new PosInvoiceCombian();
            combian.id=posInvoice.id;
            combian.invoiceID=posInvoice.invoiceID;
            combian.saleType=posInvoice.saleType;
            combian.productName=posInvoice.productName;
            combian.productId=posInvoice.productId;
            combian.quantity=posInvoice.quantity;
            combian.exchange=posInvoice.exchange;
            combian.unitPrice=posInvoice.unitPrice;
            combian.tax=posInvoice.tax;
            combian.additionalExpenses=posInvoice.additionalExpenses;
            combian.discount=posInvoice.discount;
            combian.taxIncluded=posInvoice.taxIncluded;
            combian.salesPerson=posInvoice.salesPerson;
            combian.CustomerName=posInvoice.CustomerName;
            combian.cardNumber=posInvoice.cardNumber;
            combian.bankName=posInvoice.bankName;
            combian.cashPayment=posInvoice.cashPayment;
            combian.cardPayment=posInvoice.cardPayment;
            combian.payLaterAmount=posInvoice.payLaterAmount;
            combian.status=posInvoice.status;
            combian.category = "W";
            posInvoiceCombians.add(combian);
        }
        return posInvoiceCombians;
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

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setExchange(int exchange) {
        this.exchange = exchange;
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

    public static void setPosPrefix(String posPrefix) {
        PosInvoiceCombian.posPrefix = posPrefix;
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

    public String getProductName() {
        return productName;
    }

    public int getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getExchange() {
        return exchange;
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

    public static String getPosPrefix() {
        return posPrefix;
    }
}
