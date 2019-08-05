package com.quickmas.salim.quickmasretail.Structure;

import com.quickmas.salim.quickmasretail.Model.POS.CoExpAccountReceiveDetails;
import com.quickmas.salim.quickmasretail.Model.POS.SupplierCashPaymentReceive;

public class PaymentPaidAPI {
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
    public int receive_id;
    public String remarks;
    public String sremarks;
    public double amount;
    public String uuid;

    public PaymentPaidAPI(SupplierCashPaymentReceive s, CoExpAccountReceiveDetails c)
    {
        this.id=c.id;
        this.account_name=c.account_name;
        this.remark=c.remarks;
        this.sremarks=s.remark;
        this.ca_amount=s.ca_amount;
        this.cash_amount=s.cash_amount;
        this.card_amount=s.card_amount;
        this.card_type=s.card_type;
        this.bank_account=s.bank_account;
        this.bank_name=s.bank_name;
        this.cheque_no=s.cheque_no;
        this.customer_name=s.customer_name;
        this.received_by=s.received_by;
        this.date_time=s.date_time;
        this.po_id=s.po_id;
        this.receive_id = s.id;
        this.amount=c.amount;
        this.uuid=c.uuid;
    }
}
