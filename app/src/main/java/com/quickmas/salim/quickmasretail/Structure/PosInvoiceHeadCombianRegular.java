package com.quickmas.salim.quickmasretail.Structure;

import com.quickmas.salim.quickmasretail.Model.POS.PosInvoiceHead;
import com.quickmas.salim.quickmasretail.Model.POS.PosInvoiceHeadRegular;
import com.quickmas.salim.quickmasretail.Model.POS.PosInvoiceHeadRegularWeb;
import com.quickmas.salim.quickmasretail.Model.POS.PosInvoiceHeadWeb;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class PosInvoiceHeadCombianRegular {
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
    public int status;
    public int if_synced;
    public String category;
    public String voucher_no;
    public String invoice_type="regular";

    public static ArrayList<PosInvoiceHeadCombianRegular> getPosInvoiceCombianInvoice(ArrayList<PosInvoiceHeadRegular> posInvoiceHeads)
    {
        ArrayList<PosInvoiceHeadCombianRegular> combians = setPosInvoiceHead(posInvoiceHeads);
        return sortDateWise(combians);
    }

    public static ArrayList<PosInvoiceHeadCombianRegular> getPosInvoiceCombianInvoiceWeb(ArrayList<PosInvoiceHeadRegularWeb> posInvoiceHeadWebs)
    {
        ArrayList<PosInvoiceHeadCombianRegular> combians = setPosInvoiceHeadWeb(posInvoiceHeadWebs);
        return sortDateWise(combians);
    }
    public static ArrayList<PosInvoiceHeadCombianRegular> getPosInvoiceCombian(ArrayList<PosInvoiceHeadRegular> posInvoiceHeads,ArrayList<PosInvoiceHeadRegularWeb> posInvoiceHeadWebs)
    {
        ArrayList<PosInvoiceHeadCombianRegular> combians = setPosInvoiceHead(posInvoiceHeads);
        combians.addAll(setPosInvoiceHeadWeb(posInvoiceHeadWebs));
        return sortDateWise(combians);
    }

    public static ArrayList<PosInvoiceHeadCombianRegular> sortDateWise(ArrayList<PosInvoiceHeadCombianRegular> posInvoiceHeadCombians)
    {
        ArrayList<String> dates =new ArrayList<>();
        ArrayList<PosInvoiceHeadCombianRegular> posInvoiceHeadCombianArrayList =new ArrayList<>();
        for(PosInvoiceHeadCombianRegular posInvoiceHeadCombian : posInvoiceHeadCombians)
        {
            if(!(dates.contains(posInvoiceHeadCombian.invoice_date)))
            {
                dates.add(posInvoiceHeadCombian.invoice_date);
            }
        }

        Collections.sort(dates, new Comparator<String>() {

            @Override
            public int compare(String arg0, String arg1) {
                SimpleDateFormat format = new SimpleDateFormat(
                        "dd-MM-yyyy hh:mm:ss");
                int compareResult = 0;
                try {
                    Date arg0Date = format.parse(arg0);
                    Date arg1Date = format.parse(arg1);
                    compareResult = arg0Date.compareTo(arg1Date);
                } catch (Exception e) {
                    e.printStackTrace();
                    compareResult = arg0.compareTo(arg1);
                }
                return compareResult;
            }
        });

        for(String cDate : dates)
        {
            posInvoiceHeadCombianArrayList.addAll(getInvoiceByDate(posInvoiceHeadCombians, cDate));
        }
        return posInvoiceHeadCombianArrayList;
    }

    public static ArrayList<PosInvoiceHeadCombianRegular> getInvoiceByDate(ArrayList<PosInvoiceHeadCombianRegular> combians, String date)
    {
        ArrayList<PosInvoiceHeadCombianRegular> posInvoiceHeadCombians = new ArrayList<>();
        for(PosInvoiceHeadCombianRegular posInvoiceHeadCombian : combians)
        {
            if(posInvoiceHeadCombian.getInvoice_date().equals(date))
            {
                posInvoiceHeadCombians.add(posInvoiceHeadCombian);
            }
        }
        return posInvoiceHeadCombians;
    }

    public static ArrayList<PosInvoiceHeadCombianRegular> setPosInvoiceHead(ArrayList<PosInvoiceHeadRegular> posInvoiceHeads)
    {
        ArrayList<PosInvoiceHeadCombianRegular> posInvoiceHeadCombians = new ArrayList<>();
        for(PosInvoiceHeadRegular invoiceHead : posInvoiceHeads)
        {
            PosInvoiceHeadCombianRegular combian = new PosInvoiceHeadCombianRegular();
            combian.id = invoiceHead.id;
            combian.invoice_id=invoiceHead.invoice_id;
            combian.previous_invoice_id=invoiceHead.previous_invoice_id;
            combian.total_amount=invoiceHead.total_amount;
            combian.total_amount_full=invoiceHead.total_amount_full;
            combian.total_quantity=invoiceHead.total_quantity;
            combian.total_tax=invoiceHead.total_tax;
            combian.iteam_discount=invoiceHead.iteam_discount;
            combian.additional_discount=invoiceHead.additional_discount;
            combian.additional_discount_percent=invoiceHead.additional_discount_percent;
            combian.delivery_expense=invoiceHead.delivery_expense;
            combian.sales_person=invoiceHead.sales_person;
            combian.customer=invoiceHead.customer;
            combian.payment_mode=invoiceHead.payment_mode;
            combian.cash_amount=invoiceHead.cash_amount;
            combian.note_given=invoiceHead.note_given;
            combian.change=invoiceHead.change;
            combian.card_amount=invoiceHead.card_amount;
            combian.card_type=invoiceHead.card_type;
            combian.cheque_no=invoiceHead.cheque_no;
            combian.bank=invoiceHead.bank;
            combian.pay_later_amount=invoiceHead.pay_later_amount;
            combian.verify_user=invoiceHead.verify_user;
            combian.sale_type=invoiceHead.sale_type;
            combian.total_paid_amount=invoiceHead.total_paid_amount;
            combian.new_paid_amount=0;
            combian.total_return_amount=invoiceHead.total_return_amount;
            combian.total_paid_after_return=invoiceHead.total_paid_after_return;
            combian.invoice_generate_by=invoiceHead.invoice_generate_by;
            combian.invoice_date=invoiceHead.invoice_date;
            combian.status=invoiceHead.status;
            combian.if_synced=invoiceHead.if_synced;
            combian.category="P";
            combian.voucher_no=invoiceHead.voucher_no;
            posInvoiceHeadCombians.add(combian);
        }
        return posInvoiceHeadCombians;
    }

    public static ArrayList<PosInvoiceHeadCombianRegular> setPosInvoiceHeadWeb(ArrayList<PosInvoiceHeadRegularWeb> posInvoiceHeads)
    {
        ArrayList<PosInvoiceHeadCombianRegular> posInvoiceHeadCombians = new ArrayList<>();
        for(PosInvoiceHeadRegularWeb invoiceHead : posInvoiceHeads)
        {
            PosInvoiceHeadCombianRegular combian = new PosInvoiceHeadCombianRegular();
            combian.id = invoiceHead.id;
            combian.invoice_id=invoiceHead.invoice_id;
            combian.previous_invoice_id=invoiceHead.previous_invoice_id;
            combian.total_amount=invoiceHead.total_amount;
            combian.total_amount_full=invoiceHead.total_amount_full;
            combian.total_quantity=invoiceHead.total_quantity;
            combian.total_tax=invoiceHead.total_tax;
            combian.iteam_discount=invoiceHead.iteam_discount;
            combian.additional_discount=invoiceHead.additional_discount;
            combian.additional_discount_percent=invoiceHead.additional_discount_percent;
            combian.delivery_expense=invoiceHead.delivery_expense;
            combian.sales_person=invoiceHead.sales_person;
            combian.customer=invoiceHead.customer;
            combian.payment_mode=invoiceHead.payment_mode;
            combian.cash_amount=invoiceHead.cash_amount;
            combian.note_given=invoiceHead.note_given;
            combian.change=invoiceHead.change;
            combian.card_amount=invoiceHead.card_amount;
            combian.card_type=invoiceHead.card_type;
            combian.cheque_no=invoiceHead.cheque_no;
            combian.bank=invoiceHead.bank;
            combian.pay_later_amount=invoiceHead.pay_later_amount;
            combian.verify_user=invoiceHead.verify_user;
            combian.sale_type=invoiceHead.sale_type;
            combian.total_paid_amount=invoiceHead.total_paid_amount;
            combian.new_paid_amount=invoiceHead.new_paid_amount;
            combian.total_return_amount=invoiceHead.total_return_amount;
            combian.total_paid_after_return=invoiceHead.total_paid_after_return;
            combian.invoice_generate_by=invoiceHead.invoice_generate_by;
            combian.invoice_date=invoiceHead.invoice_date;
            combian.status=invoiceHead.status;
            combian.category="W";
            combian.voucher_no=invoiceHead.voucher_no;
            posInvoiceHeadCombians.add(combian);
        }
        return posInvoiceHeadCombians;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setInvoice_id(String invoice_id) {
        this.invoice_id = invoice_id;
    }

    public void setPrevious_invoice_id(String previous_invoice_id) {
        this.previous_invoice_id = previous_invoice_id;
    }

    public void setTotal_amount(double total_amount) {
        this.total_amount = total_amount;
    }

    public void setTotal_amount_full(double total_amount_full) {
        this.total_amount_full = total_amount_full;
    }

    public void setTotal_quantity(int total_quantity) {
        this.total_quantity = total_quantity;
    }

    public void setTotal_tax(double total_tax) {
        this.total_tax = total_tax;
    }

    public void setIteam_discount(double iteam_discount) {
        this.iteam_discount = iteam_discount;
    }

    public void setAdditional_discount(double additional_discount) {
        this.additional_discount = additional_discount;
    }

    public void setAdditional_discount_percent(double additional_discount_percent) {
        this.additional_discount_percent = additional_discount_percent;
    }

    public void setDelivery_expense(double delivery_expense) {
        this.delivery_expense = delivery_expense;
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

    public void setTotal_paid_amount(double total_paid_amount) {
        this.total_paid_amount = total_paid_amount;
    }

    public void setNew_paid_amount(double new_paid_amount) {
        this.new_paid_amount = new_paid_amount;
    }

    public void setTotal_return_amount(double total_return_amount) {
        this.total_return_amount = total_return_amount;
    }

    public void setTotal_paid_after_return(double total_paid_after_return) {
        this.total_paid_after_return = total_paid_after_return;
    }

    public void setInvoice_generate_by(String invoice_generate_by) {
        this.invoice_generate_by = invoice_generate_by;
    }

    public void setInvoice_date(String invoice_date) {
        this.invoice_date = invoice_date;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public String getInvoice_id() {
        return invoice_id;
    }

    public String getPrevious_invoice_id() {
        return previous_invoice_id;
    }

    public double getTotal_amount() {
        return total_amount;
    }

    public double getTotal_amount_full() {
        return total_amount_full;
    }

    public int getTotal_quantity() {
        return total_quantity;
    }

    public double getTotal_tax() {
        return total_tax;
    }

    public double getIteam_discount() {
        return iteam_discount;
    }

    public double getAdditional_discount() {
        return additional_discount;
    }

    public double getAdditional_discount_percent() {
        return additional_discount_percent;
    }

    public double getDelivery_expense() {
        return delivery_expense;
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

    public double getTotal_paid_amount() {
        return total_paid_amount;
    }

    public double getNew_paid_amount() {
        return new_paid_amount;
    }

    public double getTotal_return_amount() {
        return total_return_amount;
    }

    public double getTotal_paid_after_return() {
        return total_paid_after_return;
    }

    public String getInvoice_generate_by() {
        return invoice_generate_by;
    }

    public String getInvoice_date() {
        return invoice_date;
    }

    public int getStatus() {
        return status;
    }

    public String getCategory() {
        return category;
    }
}
