package com.quickmas.salim.quickmasretail.Structure;

import com.quickmas.salim.quickmasretail.Model.POS.CashPaymentReceive;
import com.quickmas.salim.quickmasretail.Model.POS.CashPaymentReceivedDetails;
import com.quickmas.salim.quickmasretail.Model.POS.CoAccountReceiveDetails;

public class CashPaymentReceiveAPI {
    public int id;
    public int receiveId;
    public double amount;
    public String dateTime;
    public String remark;
    public String po_id;
    public String uuid;
    public String account_name;
    public String sremark;
    public double ca_amount;
    public double cash_amount;
    public double card_amount;
    public String card_type;
    public String bank_account;
    public String bank_name;
    public String cheque_no;
    public String customer_name;
    public String received_by;
    public String date_time;

    public CashPaymentReceiveAPI(CashPaymentReceive cr, CoAccountReceiveDetails crd)
    {
        this.id=crd.id;
        this.receiveId = cr.id;
        this.amount=crd.amount;
        this.dateTime=cr.date_time;
        this.remark=crd.remarks;
        this.po_id=cr.po_id;
        this.uuid=crd.uuid;
        this.account_name=crd.account_name;
        this.sremark=cr.remark;
        this.ca_amount=cr.ca_amount;
        this.cash_amount=cr.cash_amount;
        this.card_amount=cr.card_amount;
        this.card_type=cr.card_type;
        this.bank_account=cr.bank_account;
        this.bank_name=cr.bank_name;
        this.cheque_no=cr.cheque_no;
        this.customer_name=cr.customer_name;
        this.received_by=cr.received_by;
    }
}
