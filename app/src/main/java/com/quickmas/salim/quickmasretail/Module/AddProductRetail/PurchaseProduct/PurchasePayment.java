package com.quickmas.salim.quickmasretail.Module.AddProductRetail.PurchaseProduct;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.quickmas.salim.quickmasretail.Database.DBInitialization;
import com.quickmas.salim.quickmasretail.Model.POS.PurchaseLog;
import com.quickmas.salim.quickmasretail.Model.POS.PurchaseLogInvoice;
import com.quickmas.salim.quickmasretail.Model.POS.PurchasePaymentReceived;
import com.quickmas.salim.quickmasretail.Model.POS.Supplier;
import com.quickmas.salim.quickmasretail.Model.POS.SupplierCashPaymentReceive;
import com.quickmas.salim.quickmasretail.Model.POS.SupplierCashPaymentReceivedDetails;
import com.quickmas.salim.quickmasretail.Model.System.TextString;
import com.quickmas.salim.quickmasretail.Model.System.User;
import com.quickmas.salim.quickmasretail.R;
import com.quickmas.salim.quickmasretail.Service.UploadData;
import com.quickmas.salim.quickmasretail.Utility.DebugHelper;
import com.quickmas.salim.quickmasretail.Utility.FontSettings;
import com.quickmas.salim.quickmasretail.Utility.TypeConvertion;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

import static com.quickmas.salim.quickmasretail.Model.System.User.getUserDetails;
import static com.quickmas.salim.quickmasretail.Module.PaymentSlipPrint.PaymentPaidSlipPrint.printPaymentPaidSlip;
import static com.quickmas.salim.quickmasretail.Service.ApiSettings.url_payment_upload;
import static com.quickmas.salim.quickmasretail.Service.ApiSettings.url_purchase_log_invoice_upload;
import static com.quickmas.salim.quickmasretail.Service.ApiSettings.url_purchase_log_upload_details;
import static com.quickmas.salim.quickmasretail.Utility.DateTimeCalculation.getCurrentDateTime;
import static com.quickmas.salim.quickmasretail.Utility.NetworkConfiguration.isInternetOn;
import static com.quickmas.salim.quickmasretail.Utility.UIComponent.hideSoftKeyboard;
import static com.quickmas.salim.quickmasretail.Utility.UIComponent.spinnerDataLoad;

public class PurchasePayment extends AppCompatActivity {
    DBInitialization db;
    String id="";
    ProgressDialog progressDoalog;
    Context mContext;
    String newEntryId="";
    int new_rec_id=0;
    Button cash_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.pop_up_pos_sale_payment);
        db = new DBInitialization(this,null,null,1);
        mContext = this;
        Intent iin= getIntent();
        Bundle b = iin.getExtras();
        if(b!=null)
        {
            id =(String) b.get("id");
        }

        final PurchaseLogInvoice posInvoiceHead = PurchaseLogInvoice.select(db,db.COLUMN_purchase_log_invoice_id+"="+id).get(0);
        Button customer_submit_pop  = FontSettings.setTextFont((Button) findViewById(R.id.customer_submit_pop),this,db,"pos_sale_customer_add_submit_invoice");
        TextView payment_title  = FontSettings.setTextFont((TextView) findViewById(R.id.payment_title),this,db,"pos_sale_payment_popbox_title_invoice");

        final ImageView close_tab = (ImageView) findViewById(R.id.close_tab);

        cash_btn = FontSettings.setTextFont((Button) findViewById(R.id.cash_btn),this,db,"pos_sale_payment_popbox_cash_invoice");
        final Button card_btn = FontSettings.setTextFont((Button) findViewById(R.id.card_btn),this,db,"pos_sale_payment_popbox_card_invoice");
        final Button pay_late_btn = FontSettings.setTextFont((Button) findViewById(R.id.pay_late_btn),this,db,"pos_sale_payment_popbox_payLater_invoice");
        final Button multi_btn = FontSettings.setTextFont((Button) findViewById(R.id.multi_btn),this,db,"pos_sale_payment_popbox_multi_invoice");

        final Button make_payment  = FontSettings.setTextFont((Button) findViewById(R.id.make_payment),this,db,"pos_sale_payment_popbox_invoice_done");
        final Button make_payment_print_later  = FontSettings.setTextFont((Button) findViewById(R.id.make_payment_print_later),this,db,"pos_sale_payment_popbox_invoice_done_noprint");

        final LinearLayout cash_layout = (LinearLayout) findViewById(R.id.cash_layout);
        final LinearLayout card_layout = (LinearLayout) findViewById(R.id.card_layout);
        final LinearLayout pay_later_layout = (LinearLayout) findViewById(R.id.pay_later_layout);
        final LinearLayout multi_pay_layout = (LinearLayout) findViewById(R.id.multi_pay_layout);
        final LinearLayout customer_name_holder = (LinearLayout) findViewById(R.id.customer_name_holder);

        multi_btn.setVisibility(View.GONE);
        pay_late_btn.setVisibility(View.GONE);
        cash_layout.setVisibility(View.VISIBLE);
        card_layout.setVisibility(View.GONE);
        pay_later_layout.setVisibility(View.GONE);
        multi_pay_layout.setVisibility(View.GONE);
        customer_name_holder.setVisibility(View.VISIBLE);

        final User cUser = new User();
        cUser.select(db,"1=1");
        final EditText pay_later_user_name = FontSettings.setTextFont((EditText) findViewById(R.id.pay_later_user_name),this,cUser.getUser_name());
        final EditText multi_pay_later_user_name = FontSettings.setTextFont((EditText) findViewById(R.id.multi_pay_later_user_name),this,cUser.getUser_name());
        final EditText multi_pay_later_password = FontSettings.setTextFont((EditText) findViewById(R.id.multi_pay_later_password),this,cUser.getPassword());
        final EditText pay_later_password = FontSettings.setTextFont((EditText) findViewById(R.id.pay_later_password),this,cUser.getPassword());

        TextView payable_cash_txt  = FontSettings.setTextFont((TextView) findViewById(R.id.payable_cash_txt),this,db,"pos_sale_payment_popbox_paylater_invoice_payable");
        TextView customer_name  = FontSettings.setTextFont((TextView) findViewById(R.id.customer_name),this,posInvoiceHead.getSupplier());


        cash_btn.setTextColor(Color.parseColor("#ffffff"));
        card_btn.setTextColor(Color.parseColor("#00a81e"));
        pay_late_btn.setTextColor(Color.parseColor("#00a81e"));
        multi_btn.setTextColor(Color.parseColor("#00a81e"));

        cash_btn.setBackgroundColor(Color.parseColor("#00a81e"));
        card_btn.setBackgroundColor(Color.parseColor("#ffffff"));
        pay_late_btn.setBackgroundColor(Color.parseColor("#ffffff"));
        multi_btn.setBackgroundColor(Color.parseColor("#ffffff"));
        close_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        cash_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cash_btn.setTextColor(Color.parseColor("#ffffff"));
                card_btn.setTextColor(Color.parseColor("#00a81e"));
                pay_late_btn.setTextColor(Color.parseColor("#00a81e"));
                multi_btn.setTextColor(Color.parseColor("#00a81e"));

                cash_btn.setBackgroundColor(Color.parseColor("#00a81e"));
                card_btn.setBackgroundColor(Color.parseColor("#ffffff"));
                pay_late_btn.setBackgroundColor(Color.parseColor("#ffffff"));
                multi_btn.setBackgroundColor(Color.parseColor("#ffffff"));

                cash_layout.setVisibility(View.VISIBLE);
                card_layout.setVisibility(View.GONE);
                pay_later_layout.setVisibility(View.GONE);
                multi_pay_layout.setVisibility(View.GONE);
            }
        });

        card_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cash_btn.setTextColor(Color.parseColor("#00a81e"));
                card_btn.setTextColor(Color.parseColor("#ffffff"));
                pay_late_btn.setTextColor(Color.parseColor("#00a81e"));
                multi_btn.setTextColor(Color.parseColor("#00a81e"));

                cash_btn.setBackgroundColor(Color.parseColor("#ffffff"));
                card_btn.setBackgroundColor(Color.parseColor("#00a81e"));
                pay_late_btn.setBackgroundColor(Color.parseColor("#ffffff"));
                multi_btn.setBackgroundColor(Color.parseColor("#ffffff"));

                cash_layout.setVisibility(View.GONE);
                card_layout.setVisibility(View.VISIBLE);
                pay_later_layout.setVisibility(View.GONE);
                multi_pay_layout.setVisibility(View.GONE);
            }
        });

        ///----cash section

        final TextView note_given_cash_txt  = FontSettings.setTextFont((TextView) findViewById(R.id.note_given_cash_txt),this,db,"pos_sale_payment_popbox_cash_invoice_note_given");
        final TextView change_cash_txt  = FontSettings.setTextFont((TextView) findViewById(R.id.change_cash_txt),this,db,"pos_sale_payment_popbox_cash_invoice_change");

        double pay = posInvoiceHead.getNet_payable() - posInvoiceHead.getAmount_paid();
        final TextView payable_cash  = FontSettings.setTextFont((TextView) findViewById(R.id.payable_cash),this,String.valueOf(pay));
        final EditText note_given_cash  = (EditText) findViewById(R.id.note_given_cash);
        final TextView change_cash  = FontSettings.setTextFont((TextView) findViewById(R.id.change_cash),this,"00");


        note_given_cash.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

                double payable_cash_c = 0.0;
                double note_given_cash_c = 0.0;
                double change_cash_c = 0.0;
                try {
                    payable_cash_c = Double.parseDouble(payable_cash.getText().toString());
                    note_given_cash_c = Double.parseDouble(note_given_cash.getText().toString());
                    change_cash_c = note_given_cash_c - payable_cash_c;
                    change_cash.setText(String.valueOf(change_cash_c));
                } catch (Exception e) {
                }
            }
        });

        //------card section
        final TextView cheque_no  = FontSettings.setTextFontHint((TextView) findViewById(R.id.cheque_no),this,db,"pos_sale_payment_popbox_card_invoice_cardno");
        final Spinner card_type = (Spinner) findViewById(R.id.card_type);
        final Spinner multi_card_type = (Spinner) findViewById(R.id.multi_card_type);
        ArrayAdapter<String> dataAdapter = spinnerDataLoad(this,db,db.COLUMN_card_name,db.TABLE_card,"1=1","", TextString.textSelectByVarname(db,"pos_sale_payment_popbox_card_invoice_cardtype").getText());
        card_type.setAdapter(dataAdapter);
        multi_card_type.setAdapter(dataAdapter);
        final Spinner banklist = (Spinner) findViewById(R.id.banklist);
        final Spinner multi_banklist = (Spinner) findViewById(R.id.multi_banklist);
        dataAdapter = spinnerDataLoad(this,db,db.COLUMN_bank_name,db.TABLE_bank,"1=1","",TextString.textSelectByVarname(db,"pos_sale_payment_popbox_card_invoice_bank").getText());
        banklist.setAdapter(dataAdapter);
        multi_banklist.setAdapter(dataAdapter);


        make_payment_print_later.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User cSystemInfo = new User();
                cSystemInfo.select(db,"1=1");
                String amount;
                String payment_mode ="";
                String cardT ="";
                String chequeN ="";
                String bank ="";
                amount = payable_cash.getText().toString().trim();
                final SupplierCashPaymentReceive cashPaymentReceive = new SupplierCashPaymentReceive();
                if(cash_layout.getVisibility()==View.VISIBLE)
                {
                    String change = change_cash.getText().toString().trim();
                    if(TypeConvertion.parseDouble(change)<0)
                    {
                        Toast.makeText(getApplicationContext(),TextString.textSelectByVarname(db,"pos_sale_payment_popbox_cash_invoice_note").getText(), Toast.LENGTH_LONG).show();
                        return;
                    }
                    cashPaymentReceive.setCash_amount(TypeConvertion.parseDouble(amount));
                    payment_mode="Cash";
                }
                else if(card_layout.getVisibility()==View.VISIBLE)
                {
                    if(card_type.getSelectedItemPosition()==0 || banklist.getSelectedItemPosition()==0 || cheque_no.getText().toString().trim().equals(""))
                    {
                        Toast.makeText(getApplicationContext(),TextString.textSelectByVarname(db,"pos_sale_payment_popbox_card_validation").getText(), Toast.LENGTH_LONG).show();
                        return;
                    }
                    payment_mode="Card";
                    cashPaymentReceive.setCard_amount(TypeConvertion.parseDouble(amount));
                    cardT = card_type.getSelectedItem().toString().trim();
                    chequeN = cheque_no.getText().toString().trim();
                    bank = banklist.getSelectedItem().toString().trim();
                }

                final String date_time = getCurrentDateTime();

                final PurchasePaymentReceived posPaymentReceived = new PurchasePaymentReceived();
                posPaymentReceived.setInvoice_id(TypeConvertion.parseInt(id));
                posPaymentReceived.setAmount(Double.parseDouble(amount));
                posPaymentReceived.setPayment_mode(payment_mode);
                posPaymentReceived.setCard_type(cardT);
                posPaymentReceived.setCheque_no(chequeN);
                posPaymentReceived.setBank(bank);
                posPaymentReceived.setReceived_by(cSystemInfo.getUser_name());
                posPaymentReceived.setReceived_date(date_time);

                newEntryId = id;
                final PurchaseLogInvoice posInvoiceHead = PurchaseLogInvoice.select(db,db.COLUMN_purchase_log_invoice_id+"="+id).get(0);
                posInvoiceHead.setAmount_paid(posInvoiceHead.getNet_payable());

                cashPaymentReceive.setCard_type(cardT);
                cashPaymentReceive.setBank_name(bank);
                cashPaymentReceive.setCheque_no(chequeN);
                cashPaymentReceive.setCustomer_name(posInvoiceHead.getSupplier());
                cashPaymentReceive.setReceived_by(cUser.getUser_name());
                cashPaymentReceive.setDate_time(date_time);
                cashPaymentReceive.setRemark("Bills-"+id+" payment");




                //DebugHelper.print(posInvoiceHead);
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:

                                posPaymentReceived.insert(db);
                                cashPaymentReceive.insert(db);

                                final SupplierCashPaymentReceivedDetails cashPaymentReceivedDetails = new SupplierCashPaymentReceivedDetails();
                                new_rec_id = cashPaymentReceive.getMaxId(db);
                                cashPaymentReceivedDetails.setReceiveId(new_rec_id);
                                cashPaymentReceivedDetails.setInvoiceId(posInvoiceHead.getId());
                                cashPaymentReceivedDetails.setAmount(cashPaymentReceive.getCash_amount()+cashPaymentReceive.getCard_amount());
                                cashPaymentReceivedDetails.setDateTime(date_time);

                                cashPaymentReceivedDetails.insert(db);

                                posInvoiceHead.update(db);

                                //DebugHelper.print(PurchasePaymentReceived.select(db,"1=1"));
                                //DebugHelper.print(SupplierCashPaymentReceive.select(db,"1=1"));
                                // DebugHelper.print(SupplierCashPaymentReceivedDetails.select(db,"1=1"));

                                //System.out.println(posInvoiceHead.getCount(db));
                                if(cUser.getActive_online()==1 && isInternetOn(mContext)) {
                                    progressDoalog = new ProgressDialog(PurchasePayment.this);
                                    progressDoalog.setMessage("Product Creating....");
                                    progressDoalog.setTitle("Please Wait");
                                    progressDoalog.show();
                                    uploadData();
                                }else {
                                    hideSoftKeyboard(PurchasePayment.this);
                                    dialog.dismiss();
                                    Toast.makeText(getApplicationContext(),TextString.textSelectByVarname(db,"pos_sale_payment_popbox_invoice_complete").getText(), Toast.LENGTH_LONG).show();
                                    finish();
                                }

                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(PurchasePayment.this);
                builder.setMessage(TextString.textSelectByVarname(db,"pos_sale_payment_popbox_confermation").getText()).setPositiveButton(TextString.textSelectByVarname(db,"pos_sale_payment_popbox_confermation_yes").getText(), dialogClickListener)
                        .setNegativeButton(TextString.textSelectByVarname(db,"pos_sale_payment_popbox_confermation_no").getText(), dialogClickListener).show();
            }
        });

        make_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User cSystemInfo = new User();
                cSystemInfo.select(db,"1=1");
                String amount;
                String payment_mode ="";
                String cardT ="";
                String chequeN ="";
                String bank ="";
                amount = payable_cash.getText().toString().trim();
                final SupplierCashPaymentReceive cashPaymentReceive = new SupplierCashPaymentReceive();
                if(cash_layout.getVisibility()==View.VISIBLE)
                {
                    String change = change_cash.getText().toString().trim();
                    if(TypeConvertion.parseDouble(change)<0)
                    {
                        Toast.makeText(getApplicationContext(),TextString.textSelectByVarname(db,"pos_sale_payment_popbox_cash_invoice_note").getText(), Toast.LENGTH_LONG).show();
                        return;
                    }
                    cashPaymentReceive.setCash_amount(TypeConvertion.parseDouble(amount));
                    payment_mode="Cash";
                }
                else if(card_layout.getVisibility()==View.VISIBLE)
                {
                    if(card_type.getSelectedItemPosition()==0 || banklist.getSelectedItemPosition()==0 || cheque_no.getText().toString().trim().equals(""))
                    {
                        Toast.makeText(getApplicationContext(),TextString.textSelectByVarname(db,"pos_sale_payment_popbox_card_validation").getText(), Toast.LENGTH_LONG).show();
                        return;
                    }
                    payment_mode="Card";
                    cashPaymentReceive.setCard_amount(TypeConvertion.parseDouble(amount));
                    cardT = card_type.getSelectedItem().toString().trim();
                    chequeN = cheque_no.getText().toString().trim();
                    bank = banklist.getSelectedItem().toString().trim();
                }

                final String date_time = getCurrentDateTime();

                final PurchasePaymentReceived posPaymentReceived = new PurchasePaymentReceived();
                posPaymentReceived.setInvoice_id(TypeConvertion.parseInt(id));
                posPaymentReceived.setAmount(Double.parseDouble(amount));
                posPaymentReceived.setPayment_mode(payment_mode);
                posPaymentReceived.setCard_type(cardT);
                posPaymentReceived.setCheque_no(chequeN);
                posPaymentReceived.setBank(bank);
                posPaymentReceived.setReceived_by(cSystemInfo.getUser_name());
                posPaymentReceived.setReceived_date(date_time);

                newEntryId = id;
                final PurchaseLogInvoice posInvoiceHead = PurchaseLogInvoice.select(db,db.COLUMN_purchase_log_invoice_id+"="+id).get(0);
                posInvoiceHead.setAmount_paid(posInvoiceHead.getNet_payable());

                cashPaymentReceive.setCard_type(cardT);
                cashPaymentReceive.setBank_name(bank);
                cashPaymentReceive.setCheque_no(chequeN);
                cashPaymentReceive.setCustomer_name(posInvoiceHead.getSupplier());
                cashPaymentReceive.setReceived_by(cUser.getUser_name());
                cashPaymentReceive.setDate_time(date_time);
                cashPaymentReceive.setRemark("Bills-"+id+" payment");



                //DebugHelper.print(posInvoiceHead);
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:

                                posPaymentReceived.insert(db);
                                cashPaymentReceive.insert(db);

                                final SupplierCashPaymentReceivedDetails cashPaymentReceivedDetails = new SupplierCashPaymentReceivedDetails();
                                new_rec_id = cashPaymentReceive.getMaxId(db);
                                cashPaymentReceivedDetails.setReceiveId(new_rec_id);
                                cashPaymentReceivedDetails.setInvoiceId(posInvoiceHead.getId());
                                cashPaymentReceivedDetails.setAmount(cashPaymentReceive.getCash_amount()+cashPaymentReceive.getCard_amount());
                                cashPaymentReceivedDetails.setDateTime(date_time);

                                cashPaymentReceivedDetails.insert(db);

                                posInvoiceHead.update(db);

                                //DebugHelper.print(PurchasePaymentReceived.select(db,"1=1"));
                                //DebugHelper.print(SupplierCashPaymentReceive.select(db,"1=1"));
                               // DebugHelper.print(SupplierCashPaymentReceivedDetails.select(db,"1=1"));

                                //System.out.println(posInvoiceHead.getCount(db));
                                make_payment.setEnabled(false);
                                make_payment_print_later.setEnabled(false);
                                if(cUser.getActive_online()==1 && isInternetOn(mContext)) {
                                    printNow = true;
                                    progressDoalog = new ProgressDialog(PurchasePayment.this);
                                    progressDoalog.setMessage("Product Creating....");
                                    progressDoalog.setTitle("Please Wait");
                                    progressDoalog.show();
                                    uploadData();
                                }else {
                                    hideSoftKeyboard(PurchasePayment.this);
                                    dialog.dismiss();
                                    Toast.makeText(getApplicationContext(),TextString.textSelectByVarname(db,"pos_sale_payment_popbox_invoice_complete").getText(), Toast.LENGTH_LONG).show();
                                    printPaymentPaidSlip(getApplicationContext(),String.valueOf(new_rec_id),cash_btn);
                                }

                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(PurchasePayment.this);
                builder.setMessage(TextString.textSelectByVarname(db,"pos_sale_payment_popbox_confermation").getText()).setPositiveButton(TextString.textSelectByVarname(db,"pos_sale_payment_popbox_confermation_yes").getText(), dialogClickListener)
                        .setNegativeButton(TextString.textSelectByVarname(db,"pos_sale_payment_popbox_confermation_no").getText(), dialogClickListener).show();
            }
        });
    }
    public void uploadData()
    {
        final User cUser = new User();
        cUser.select(db,"1=1");
        UploadData uploadData = new UploadData();
        uploadData.data = PurchaseLogInvoice.select(db,db.COLUMN_purchase_log_invoice_id+"="+id);
        uploadData.url=url_purchase_log_invoice_upload + getUserDetails(cUser);
        uploadData.uploaddata(mContext,mContext,"",mContext,"uploadcomplete");
    }
    public void uploadcomplete(ArrayList<String> data1)
    {
        for(PurchaseLogInvoice p : PurchaseLogInvoice.select(db,db.COLUMN_purchase_log_invoice_id+"="+id))
        {
            p.setIf_data_synced(1);
            p.insert(db);
        }
        /*for(String rs : data1)
        {
            try{
                JSONArray jr = new JSONArray(data1.get(0));
                if(rs.toString().charAt(0)=='[')
                {
                    jr = new JSONArray(rs.toString());
                }
                for (int i = 0; i < jr.length(); i++) {
                    try{
                        JSONObject jb = (JSONObject) jr.getJSONObject(i);
                        jb.get("po").toString();
                        try {
                            String new_po_id = jb.get("po").toString();
                            String new_id = new_po_id.split("-")[new_po_id.split("-").length - 1];
                            PurchaseLog.update(db, db.COLUMN_purchase_log_entry_id + "=" + new_id, db.COLUMN_purchase_log_entry_id + "=" + id);
                            PurchaseLogInvoice.update(db, db.COLUMN_purchase_log_invoice_id + "=" + new_id, db.COLUMN_purchase_log_invoice_id + "=" + id);
                            newEntryId = new_id;
                        }catch (Exception e){}

                    }catch (Exception e){}
                }

            }catch (Exception e){}
        }*/
        final User cSystemInfo = new User();
        cSystemInfo.select(db,"1=1");
        UploadData uploadData = new UploadData();
        uploadData.data = SupplierCashPaymentReceive.select(db,db.COLUMN_supplier_cash_payemnt_id+"="+new_rec_id);
        uploadData.url=url_payment_upload + getUserDetails(cSystemInfo);
        uploadData.uploaddata(mContext,mContext,"",mContext,"uploadBulkPaymentDataComplete");
    }
    boolean printNow = false;
    public void uploadBulkPaymentDataComplete(ArrayList<String> data)
    {
        int newId=0;
        try {
            SupplierCashPaymentReceive c = SupplierCashPaymentReceive.select(db, db.COLUMN_supplier_cash_payemnt_id + "=" + new_rec_id).get(0);
            final JSONArray jr = new JSONArray(data.get(0));
            for (int i = 0; i < jr.length(); i++)
            {
                try {
                    JSONObject jb = (JSONObject) jr.getJSONObject(i);
                    String[] vc = jb.get("voucher").toString().split("-");
                    newId = TypeConvertion.parseInt(vc[vc.length - 1]);
                }catch (Exception e){}
            }
            c.if_data_synced = 1;
            c.update(db);
            c.update(db,db.COLUMN_supplier_cash_payemnt_id+"="+newId,db.COLUMN_supplier_cash_payemnt_id+"="+c.id);
            progressDoalog.dismiss();
            if(printNow)
            {
                printPaymentPaidSlip(getApplicationContext(),String.valueOf(newId),cash_btn);
            }
        }catch (Exception e){}
        Toasty.success(getApplicationContext(),TextString.textSelectByVarname(db,"pos_sale_payment_popbox_invoice_complete").getText(), Toast.LENGTH_LONG).show();

    }
}
