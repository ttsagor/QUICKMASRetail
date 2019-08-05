package com.quickmas.salim.quickmasretail.Module.PaymentSlipPrint;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.quickmas.salim.quickmasretail.Database.DBInitialization;
import com.quickmas.salim.quickmasretail.Model.POS.CashPaymentReceive;
import com.quickmas.salim.quickmasretail.Model.POS.PaymentReceiveVoucher;
import com.quickmas.salim.quickmasretail.Model.System.TextString;
import com.quickmas.salim.quickmasretail.Module.RTM.RTM.RTMInvoiceList.PrintView.Memo_Print;
import com.quickmas.salim.quickmasretail.Utility.BluetoothPrinter;
import com.quickmas.salim.quickmasretail.Utility.DateTimeCalculation;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

import static com.quickmas.salim.quickmasretail.Utility.TypeConvertion.numberToWord;

public class PaymentReceiveSlipPrint {
    public static void printPaymentSlip(Context context, String inv, View v)
    {
        DBInitialization db = new DBInitialization(context,null,null,1);
        ArrayList<CashPaymentReceive> ps = CashPaymentReceive.select(db,db.COLUMN_cash_payemnt_id+"="+inv);
        if(ps.size()>0) {
            CashPaymentReceive paymentReceiveVoucher =ps.get(0);
            BluetoothPrinter b = new BluetoothPrinter(context, v);
            String printData = Memo_Print.getDataCentralized("MONEY RECEIPT",32)+"\n";
            printData += Memo_Print.getDataCentralized("-------------",32)+"\n";
            printData += "Vo/N: REC-" + paymentReceiveVoucher.getId()+"\n";
            printData += "\nDate: " + paymentReceiveVoucher.getDate_time();
            String pmode = "Cash";
            if(paymentReceiveVoucher.getCard_amount()>0.0)
            {
                pmode = "Card";
            }
            printData += "\nMode: " +pmode;

            printData += "\nAmount Receive with Thanks From:";
            printData += "\n" + paymentReceiveVoucher.getCustomer_name()+".";
            printData += "\nOn account of: " + paymentReceiveVoucher.getRemark()+".";
            double amount = (paymentReceiveVoucher.getCash_amount()+paymentReceiveVoucher.getCard_amount()+paymentReceiveVoucher.getCa_amount());
            if(pmode.toUpperCase().equals("CASH"))
            {
                printData += "\nCash Amount: " + amount;
            }
            else
            {

                printData += "\nBank Amount: " + amount;
                printData += "\nCheque/Card Number: " + paymentReceiveVoucher.getCard_type()+"("+paymentReceiveVoucher.getCheque_no()+")";
            }
            printData += "\nIn Word: " + numberToWord((int) amount)+" Only\n\n";
            printData += Memo_Print.getDataCentralized("Printed & Received by",32)+"\n";
            printData += Memo_Print.getDataCentralized("------------------",32)+"\n";
            printData += Memo_Print.getDataCentralized(paymentReceiveVoucher.getReceived_by(),32)+"\n";
            System.out.println("\n"+printData);
            b.findBT(printData);
        }
        else
        {
            Toasty.success(context,TextString.textSelectByVarname(db,"pos_sale_payment_popbox_invoice_complete").getText(), Toast.LENGTH_LONG).show();
        }
    }
}
