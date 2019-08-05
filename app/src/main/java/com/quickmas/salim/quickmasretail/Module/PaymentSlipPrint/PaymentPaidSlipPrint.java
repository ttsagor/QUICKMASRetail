package com.quickmas.salim.quickmasretail.Module.PaymentSlipPrint;

import android.content.Context;
import android.view.View;

import com.quickmas.salim.quickmasretail.Database.DBInitialization;
import com.quickmas.salim.quickmasretail.Model.POS.PaymentReceiveVoucher;
import com.quickmas.salim.quickmasretail.Model.POS.SupplierCashPaymentReceive;
import com.quickmas.salim.quickmasretail.Module.RTM.RTM.RTMInvoiceList.PrintView.Memo_Print;
import com.quickmas.salim.quickmasretail.Utility.BluetoothPrinter;

import java.util.ArrayList;

import static com.quickmas.salim.quickmasretail.Utility.TypeConvertion.numberToWord;

public class PaymentPaidSlipPrint {
    public static void printPaymentPaidSlip(Context context, String inv, View v)
    {
        DBInitialization db = new DBInitialization(context,null,null,1);
        ArrayList<SupplierCashPaymentReceive> ps = SupplierCashPaymentReceive.select(db,db.COLUMN_supplier_cash_payemnt_id+"='"+inv+"'");
        if(ps.size()>0) {
            SupplierCashPaymentReceive paymentReceiveVoucher =ps.get(0);
            BluetoothPrinter b = new BluetoothPrinter(context, v);
            String printData = Memo_Print.getDataCentralized("Pay Slip",32)+"\n";
            printData += Memo_Print.getDataCentralized("-------------",32)+"\n";
            printData += "Vo/N: Pay-" + paymentReceiveVoucher.getId()+"\n";
            printData += "\nDate: " + paymentReceiveVoucher.getDate_time();
            if(paymentReceiveVoucher.getCash_amount()>0.0)
            {
                printData += "\nMode: Cash";
            }
            else
            {
                printData += "\nMode: Card";
            }
            printData += "\nAmount Paid to: ";
            printData += "\n" + paymentReceiveVoucher.getCustomer_name();
            printData += "\nOn account of: " + paymentReceiveVoucher.getRemark()+".";
            if(paymentReceiveVoucher.getCash_amount()>0.0)
            {
                printData += "\nCash Amount: " + paymentReceiveVoucher.getCash_amount();
            }
            else
            {
                printData += "\nBank Amount: " + paymentReceiveVoucher.getCard_amount();
                printData += "\nCheque/Card Number: " + paymentReceiveVoucher.getCard_type()+"("+paymentReceiveVoucher.getCheque_no()+")";
            }
            printData += "\nIn Word: " + numberToWord((int) (paymentReceiveVoucher.getCard_amount()+paymentReceiveVoucher.getCash_amount()))+" Only\n\n";

            printData += "Received by:\n";
            printData += "Signature:__________________\n";
            printData += "Printed by:"+paymentReceiveVoucher.getReceived_by();
            System.out.println("\n"+printData);
            b.findBT(printData);
        }
    }
}
