package com.quickmas.salim.quickmasretail.Structure;

import com.quickmas.salim.quickmasretail.Model.POS.StockTransferDetails;
import com.quickmas.salim.quickmasretail.Model.POS.StockTransferHead;

public class GoodsTransferAPI {
    public int id=0;
    public int transfer_id=0;
    public int product_id=0;
    public String product_name="";
    public int quantity=0;
    public int received_qty=0;
    public int delivered_qty=0;
    public int receive_qty=0;
    public int billed_qty=0;
    public double unit_price=0.0;
    public int status=0;
    public String date_time="";
    public int total_quantity=0;
    public double total_amount=0.0;
    public String transfer_by="";
    public String transfer_to="";
    public String entry_by="";
    public String transfer_no="";
    public double sales_price=0.0;
    public String pos_by="";

    public GoodsTransferAPI() {

    }
    public GoodsTransferAPI(StockTransferHead h, StockTransferDetails d)
    {
        this.id=d.id;
        this.transfer_id=d.transfer_id;
        this.product_id=d.product_id;
        this.product_name=d.product_name;
        this.quantity=d.quantity;
        this.unit_price=d.unit_price;
        this.status=h.status;
        this.date_time=h.date_time;
        this.transfer_by=h.transfer_by;
        this.transfer_to=h.transfer_to;
        this.entry_by = h.entry_by;
        this.received_qty=d.received_qty;
        this.delivered_qty=d.delivered_qty;
        this.pos_by=h.pos_by;
    }
}
