package com.quickmas.salim.quickmasretail.Module.POS.POSInvoiceList;

import android.app.AlertDialog;
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
import com.quickmas.salim.quickmasretail.Model.POS.CashPaymentReceive;
import com.quickmas.salim.quickmasretail.Model.POS.CashPaymentReceivedDetails;
import com.quickmas.salim.quickmasretail.Model.POS.PaymentReceiveVoucher;
import com.quickmas.salim.quickmasretail.Model.POS.PosInvoice;
import com.quickmas.salim.quickmasretail.Model.POS.PosInvoiceHead;
import com.quickmas.salim.quickmasretail.Model.POS.PosInvoiceHeadWeb;
import com.quickmas.salim.quickmasretail.Model.POS.PosPaymentReceived;
import com.quickmas.salim.quickmasretail.Model.System.TextString;
import com.quickmas.salim.quickmasretail.Model.System.User;
import com.quickmas.salim.quickmasretail.R;
import com.quickmas.salim.quickmasretail.Structure.PosInvoiceHeadCombian;
import com.quickmas.salim.quickmasretail.Utility.DebugHelper;
import com.quickmas.salim.quickmasretail.Utility.FontSettings;
import com.quickmas.salim.quickmasretail.Utility.TypeConvertion;

import java.util.ArrayList;

import static com.quickmas.salim.quickmasretail.Module.PaymentSlipPrint.PaymentReceiveSlipPrint.printPaymentSlip;
import static com.quickmas.salim.quickmasretail.Utility.DateTimeCalculation.getCurrentDateTime;
import static com.quickmas.salim.quickmasretail.Utility.UIComponent.hideSoftKeyboard;
import static com.quickmas.salim.quickmasretail.Utility.UIComponent.spinnerDataLoad;

public class InvoicePayment extends AppCompatActivity {
    DBInitialization db;
    String id="";
    String category="";
    PosInvoiceHeadCombian posInvoiceHead = new PosInvoiceHeadCombian();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.pop_up_pos_sale_payment);
        db = new DBInitialization(this,null,null,1);

        Intent iin= getIntent();
        Bundle b = iin.getExtras();
        if(b!=null)
        {
            id =(String) b.get("id");
        }
        if(b!=null)
        {
            category =(String) b.get("category");
        }

        if(category.toUpperCase().equals("W"))
        {
            posInvoiceHead = PosInvoiceHeadCombian.getPosInvoiceCombianInvoiceWeb( PosInvoiceHeadWeb.select(db,db.COLUMN_pos_invoice_head_id+"="+id)).get(0);
        }
        else
        {
            posInvoiceHead = PosInvoiceHeadCombian.getPosInvoiceCombianInvoice( PosInvoiceHead.select(db,db.COLUMN_pos_invoice_head_id+"="+id)).get(0);
        }
        Button customer_submit_pop  = FontSettings.setTextFont((Button) findViewById(R.id.customer_submit_pop),this,db,"pos_sale_customer_add_submit_invoice");
        TextView payment_title  = FontSettings.setTextFont((TextView) findViewById(R.id.payment_title),this,db,"pos_sale_payment_popbox_title_invoice");

        final ImageView close_tab = (ImageView) findViewById(R.id.close_tab);

        final Button cash_btn = FontSettings.setTextFont((Button) findViewById(R.id.cash_btn),this,db,"pos_sale_payment_popbox_cash_invoice");
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
        System.out.println(cUser.getUser_name()+" "+cUser.getPassword());
        final EditText pay_later_user_name = FontSettings.setTextFont((EditText) findViewById(R.id.pay_later_user_name),this,cUser.getUser_name());
        final EditText multi_pay_later_user_name = FontSettings.setTextFont((EditText) findViewById(R.id.multi_pay_later_user_name),this,cUser.getUser_name());
        final EditText multi_pay_later_password = FontSettings.setTextFont((EditText) findViewById(R.id.multi_pay_later_password),this,cUser.getPassword());
        final EditText pay_later_password = FontSettings.setTextFont((EditText) findViewById(R.id.pay_later_password),this,cUser.getPassword());

        TextView payable_cash_txt  = FontSettings.setTextFont((TextView) findViewById(R.id.payable_cash_txt),this,db,"pos_sale_payment_popbox_paylater_invoice_payable");
        TextView customer_name  = FontSettings.setTextFont((TextView) findViewById(R.id.customer_name),this,posInvoiceHead.getCustomer());


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

        double pay = posInvoiceHead.getTotal_amount() - posInvoiceHead.getTotal_paid_amount();
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
                final CashPaymentReceive cashPaymentReceive = new CashPaymentReceive();
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

                final PosPaymentReceived posPaymentReceived = new PosPaymentReceived();
                posPaymentReceived.setInvoice_id(TypeConvertion.parseInt(id));
                posPaymentReceived.setAmount(Double.parseDouble(amount));
                posPaymentReceived.setPayment_mode(payment_mode);
                posPaymentReceived.setCard_type(cardT);
                posPaymentReceived.setCheque_no(chequeN);
                posPaymentReceived.setBank(bank);
                posPaymentReceived.setReceived_by(cSystemInfo.getUser_name());
                posPaymentReceived.setReceived_date(date_time);


                if(category.toUpperCase().equals("W"))
                {
                    final PosInvoiceHeadWeb posInvoiceHead = PosInvoiceHeadWeb.select(db,db.COLUMN_pos_invoice_head_invoice_id_web+"="+id).get(0);
                    posInvoiceHead.setTotal_paid_amount(posInvoiceHead.getTotal_amount());
                    cashPaymentReceive.setCard_type(cardT);
                    cashPaymentReceive.setBank_name(bank);
                    cashPaymentReceive.setCheque_no(chequeN);
                    cashPaymentReceive.setCustomer_name(posInvoiceHead.getCustomer());
                    cashPaymentReceive.setReceived_by(cUser.getUser_name());
                    cashPaymentReceive.setDate_time(date_time);
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which){
                                case DialogInterface.BUTTON_POSITIVE:
                                    int sl_id = PaymentReceiveVoucher.getMaxIdByCategory("W",db);
                                    cashPaymentReceive.voucher = ("Rec-W-"+sl_id);
                                    posPaymentReceived.insert(db);
                                    cashPaymentReceive.insert(db);
                                    final CashPaymentReceivedDetails cashPaymentReceivedDetails = new CashPaymentReceivedDetails();
                                    cashPaymentReceivedDetails.setReceiveId(cashPaymentReceive.getMaxId(db));
                                    cashPaymentReceivedDetails.setInvoiceId(posInvoiceHead.getId());
                                    cashPaymentReceivedDetails.setAmount(cashPaymentReceive.getCash_amount()+cashPaymentReceive.getCard_amount());
                                    cashPaymentReceivedDetails.setDateTime(date_time);
                                    cashPaymentReceivedDetails.insert(db);

                                    PaymentReceiveVoucher paymentReceiveVoucher = new PaymentReceiveVoucher();
                                    paymentReceiveVoucher.setSl_id(sl_id);
                                    paymentReceiveVoucher.setInv_id("Rec-W-"+sl_id);
                                    paymentReceiveVoucher.setCategory("W");
                                    paymentReceiveVoucher.setCustomer(posInvoiceHead.getCustomer());
                                    paymentReceiveVoucher.setPayment_mode(posPaymentReceived.getPayment_mode());
                                    paymentReceiveVoucher.setPrint_by(cUser.getUser_name());
                                    paymentReceiveVoucher.setPrint_date(date_time);
                                    if(posPaymentReceived.getCard_type().equals(""))
                                    {
                                        paymentReceiveVoucher.setCard_info(posPaymentReceived.getCheque_no());
                                    }
                                    else {
                                        paymentReceiveVoucher.setCard_info(posPaymentReceived.getCard_type());
                                    }
                                    paymentReceiveVoucher.setCard_number(posPaymentReceived.getCheque_no());
                                    paymentReceiveVoucher.setAmount(cashPaymentReceive.getCash_amount()+cashPaymentReceive.getCard_amount());
                                    paymentReceiveVoucher.insert(db);
                                    //DebugHelper.print(PaymentReceiveVoucher.select(db,"1=1"));
                                    posInvoiceHead.setVoucher_no(paymentReceiveVoucher.getInv_id());
                                    posInvoiceHead.update(db);
                                    hideSoftKeyboard(InvoicePayment.this);
                                    dialog.dismiss();
                                    Toast.makeText(getApplicationContext(),TextString.textSelectByVarname(db,"pos_sale_payment_popbox_invoice_complete").getText(), Toast.LENGTH_LONG).show();
                                    finish();
                                    break;
                                case DialogInterface.BUTTON_NEGATIVE:
                                    break;
                            }
                        }
                    };
                    AlertDialog.Builder builder = new AlertDialog.Builder(InvoicePayment.this);
                    builder.setMessage(TextString.textSelectByVarname(db,"pos_sale_payment_popbox_confermation").getText()).setPositiveButton(TextString.textSelectByVarname(db,"pos_sale_payment_popbox_confermation_yes").getText(), dialogClickListener)
                            .setNegativeButton(TextString.textSelectByVarname(db,"pos_sale_payment_popbox_confermation_no").getText(), dialogClickListener).show();
                }
                else
                {
                    final PosInvoiceHead posInvoiceHead = PosInvoiceHead.select(db,db.COLUMN_pos_invoice_head_invoice_id+"="+id).get(0);
                    posInvoiceHead.setTotal_paid_amount(posInvoiceHead.getTotal_amount());
                    cashPaymentReceive.setCard_type(cardT);
                    cashPaymentReceive.setBank_name(bank);
                    cashPaymentReceive.setCheque_no(chequeN);
                    cashPaymentReceive.setCustomer_name(posInvoiceHead.getCustomer());
                    cashPaymentReceive.setReceived_by(cUser.getUser_name());
                    cashPaymentReceive.setDate_time(date_time);
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which){
                                case DialogInterface.BUTTON_POSITIVE:
                                    int sl_id = PaymentReceiveVoucher.getMaxIdByCategory("P",db);
                                    cashPaymentReceive.voucher = ("Rec-P-"+sl_id);
                                    posPaymentReceived.insert(db);
                                    cashPaymentReceive.insert(db);
                                    final CashPaymentReceivedDetails cashPaymentReceivedDetails = new CashPaymentReceivedDetails();
                                    cashPaymentReceivedDetails.setReceiveId(cashPaymentReceive.getMaxId(db));
                                    cashPaymentReceivedDetails.setInvoiceId(posInvoiceHead.getId());
                                    cashPaymentReceivedDetails.setAmount(cashPaymentReceive.getCash_amount()+cashPaymentReceive.getCard_amount());
                                    cashPaymentReceivedDetails.setDateTime(date_time);
                                    cashPaymentReceivedDetails.insert(db);

                                    PaymentReceiveVoucher paymentReceiveVoucher = new PaymentReceiveVoucher();
                                    paymentReceiveVoucher.setSl_id(sl_id);
                                    paymentReceiveVoucher.setInv_id("Rec-P-"+sl_id);
                                    paymentReceiveVoucher.setCategory("P");
                                    paymentReceiveVoucher.setCustomer(posInvoiceHead.getCustomer());
                                    paymentReceiveVoucher.setPayment_mode(posPaymentReceived.getPayment_mode());
                                    paymentReceiveVoucher.setPrint_by(cUser.getUser_name());
                                    paymentReceiveVoucher.setPrint_date(date_time);
                                    if(posPaymentReceived.getCard_type().equals(""))
                                    {
                                        paymentReceiveVoucher.setCard_info(posPaymentReceived.getCheque_no());
                                    }
                                    else {
                                        paymentReceiveVoucher.setCard_info(posPaymentReceived.getCard_type());
                                    }
                                    paymentReceiveVoucher.setCard_number(posPaymentReceived.getCheque_no());
                                    paymentReceiveVoucher.setAmount(cashPaymentReceive.getCash_amount()+cashPaymentReceive.getCard_amount());
                                    paymentReceiveVoucher.insert(db);
                                    //DebugHelper.print(PaymentReceiveVoucher.select(db,"1=1"));
                                    posInvoiceHead.setVoucher_no(paymentReceiveVoucher.getInv_id());

                                    posInvoiceHead.update(db);
                                    hideSoftKeyboard(InvoicePayment.this);
                                    dialog.dismiss();
                                    Toast.makeText(getApplicationContext(),TextString.textSelectByVarname(db,"pos_sale_payment_popbox_invoice_complete").getText(), Toast.LENGTH_LONG).show();
                                    finish();
                                    break;
                                case DialogInterface.BUTTON_NEGATIVE:
                                    break;
                            }
                        }
                    };
                    AlertDialog.Builder builder = new AlertDialog.Builder(InvoicePayment.this);
                    builder.setMessage(TextString.textSelectByVarname(db,"pos_sale_payment_popbox_confermation").getText()).setPositiveButton(TextString.textSelectByVarname(db,"pos_sale_payment_popbox_confermation_yes").getText(), dialogClickListener)
                            .setNegativeButton(TextString.textSelectByVarname(db,"pos_sale_payment_popbox_confermation_no").getText(), dialogClickListener).show();
                }
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
                final CashPaymentReceive cashPaymentReceive = new CashPaymentReceive();
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

                final PosPaymentReceived posPaymentReceived = new PosPaymentReceived();
                posPaymentReceived.setInvoice_id(TypeConvertion.parseInt(id));
                posPaymentReceived.setAmount(Double.parseDouble(amount));
                posPaymentReceived.setPayment_mode(payment_mode);
                posPaymentReceived.setCard_type(cardT);
                posPaymentReceived.setCheque_no(chequeN);
                posPaymentReceived.setBank(bank);
                posPaymentReceived.setReceived_by(cSystemInfo.getUser_name());
                posPaymentReceived.setReceived_date(date_time);


                if(category.toUpperCase().equals("W"))
                {
                    final PosInvoiceHeadWeb posInvoiceHead = PosInvoiceHeadWeb.select(db,db.COLUMN_pos_invoice_head_invoice_id_web+"="+id).get(0);
                    posInvoiceHead.setTotal_paid_amount(posInvoiceHead.getTotal_amount());
                    cashPaymentReceive.setCard_type(cardT);
                    cashPaymentReceive.setBank_name(bank);
                    cashPaymentReceive.setCheque_no(chequeN);
                    cashPaymentReceive.setCustomer_name(posInvoiceHead.getCustomer());
                    cashPaymentReceive.setReceived_by(cUser.getUser_name());
                    cashPaymentReceive.setDate_time(date_time);
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which){
                                case DialogInterface.BUTTON_POSITIVE:
                                    int sl_id = PaymentReceiveVoucher.getMaxIdByCategory("W",db);
                                    cashPaymentReceive.voucher = ("Rec-W-"+sl_id);
                                    posPaymentReceived.insert(db);
                                    cashPaymentReceive.insert(db);
                                    final CashPaymentReceivedDetails cashPaymentReceivedDetails = new CashPaymentReceivedDetails();
                                    cashPaymentReceivedDetails.setReceiveId(cashPaymentReceive.getMaxId(db));
                                    cashPaymentReceivedDetails.setInvoiceId(posInvoiceHead.getId());
                                    cashPaymentReceivedDetails.setAmount(cashPaymentReceive.getCash_amount()+cashPaymentReceive.getCard_amount());
                                    cashPaymentReceivedDetails.setDateTime(date_time);
                                    cashPaymentReceivedDetails.insert(db);

                                    PaymentReceiveVoucher paymentReceiveVoucher = new PaymentReceiveVoucher();
                                    paymentReceiveVoucher.setSl_id(sl_id);
                                    paymentReceiveVoucher.setInv_id("Rec-W-"+sl_id);
                                    paymentReceiveVoucher.setCategory("W");
                                    paymentReceiveVoucher.setCustomer(posInvoiceHead.getCustomer());
                                    paymentReceiveVoucher.setPayment_mode(posPaymentReceived.getPayment_mode());
                                    paymentReceiveVoucher.setPrint_by(cUser.getUser_name());
                                    paymentReceiveVoucher.setPrint_date(date_time);
                                    if(posPaymentReceived.getCard_type().equals(""))
                                    {
                                        paymentReceiveVoucher.setCard_info(posPaymentReceived.getCheque_no());
                                    }
                                    else {
                                        paymentReceiveVoucher.setCard_info(posPaymentReceived.getCard_type());
                                    }
                                    paymentReceiveVoucher.setCard_number(posPaymentReceived.getCheque_no());
                                    paymentReceiveVoucher.setAmount(cashPaymentReceive.getCash_amount()+cashPaymentReceive.getCard_amount());
                                    paymentReceiveVoucher.insert(db);
                                    //DebugHelper.print(PaymentReceiveVoucher.select(db,"1=1"));
                                    posInvoiceHead.setVoucher_no(paymentReceiveVoucher.getInv_id());
                                    posInvoiceHead.update(db);
                                    hideSoftKeyboard(InvoicePayment.this);
                                    dialog.dismiss();
                                    make_payment.setEnabled(false);
                                    make_payment_print_later.setEnabled(false);
                                    printPaymentSlip(getApplicationContext(), cashPaymentReceive.getVoucher(), card_layout);
                                    Toast.makeText(getApplicationContext(),TextString.textSelectByVarname(db,"pos_sale_payment_popbox_invoice_complete").getText(), Toast.LENGTH_LONG).show();
                                    //finish();
                                    break;
                                case DialogInterface.BUTTON_NEGATIVE:
                                    break;
                            }
                        }
                    };
                    AlertDialog.Builder builder = new AlertDialog.Builder(InvoicePayment.this);
                    builder.setMessage(TextString.textSelectByVarname(db,"pos_sale_payment_popbox_confermation").getText()).setPositiveButton(TextString.textSelectByVarname(db,"pos_sale_payment_popbox_confermation_yes").getText(), dialogClickListener)
                            .setNegativeButton(TextString.textSelectByVarname(db,"pos_sale_payment_popbox_confermation_no").getText(), dialogClickListener).show();
                }
                else
                {
                    final PosInvoiceHead posInvoiceHead = PosInvoiceHead.select(db,db.COLUMN_pos_invoice_head_invoice_id+"="+id).get(0);
                    posInvoiceHead.setTotal_paid_amount(posInvoiceHead.getTotal_amount());
                    cashPaymentReceive.setCard_type(cardT);
                    cashPaymentReceive.setBank_name(bank);
                    cashPaymentReceive.setCheque_no(chequeN);
                    cashPaymentReceive.setCustomer_name(posInvoiceHead.getCustomer());
                    cashPaymentReceive.setReceived_by(cUser.getUser_name());
                    cashPaymentReceive.setDate_time(date_time);
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which){
                                case DialogInterface.BUTTON_POSITIVE:
                                    int sl_id = PaymentReceiveVoucher.getMaxIdByCategory("P",db);
                                    cashPaymentReceive.voucher = ("Rec-P-"+sl_id);
                                    posPaymentReceived.insert(db);
                                    cashPaymentReceive.insert(db);
                                    final CashPaymentReceivedDetails cashPaymentReceivedDetails = new CashPaymentReceivedDetails();
                                    cashPaymentReceivedDetails.setReceiveId(cashPaymentReceive.getMaxId(db));
                                    cashPaymentReceivedDetails.setInvoiceId(posInvoiceHead.getId());
                                    cashPaymentReceivedDetails.setAmount(cashPaymentReceive.getCash_amount()+cashPaymentReceive.getCard_amount());
                                    cashPaymentReceivedDetails.setDateTime(date_time);
                                    cashPaymentReceivedDetails.insert(db);

                                    PaymentReceiveVoucher paymentReceiveVoucher = new PaymentReceiveVoucher();
                                    paymentReceiveVoucher.setSl_id(sl_id);
                                    paymentReceiveVoucher.setInv_id("Rec-P-"+sl_id);
                                    paymentReceiveVoucher.setCategory("P");
                                    paymentReceiveVoucher.setCustomer(posInvoiceHead.getCustomer());
                                    paymentReceiveVoucher.setPayment_mode(posPaymentReceived.getPayment_mode());
                                    paymentReceiveVoucher.setPrint_by(cUser.getUser_name());
                                    paymentReceiveVoucher.setPrint_date(date_time);
                                    if(posPaymentReceived.getCard_type().equals(""))
                                    {
                                        paymentReceiveVoucher.setCard_info(posPaymentReceived.getCheque_no());
                                    }
                                    else {
                                        paymentReceiveVoucher.setCard_info(posPaymentReceived.getCard_type());
                                    }
                                    paymentReceiveVoucher.setCard_number(posPaymentReceived.getCheque_no());
                                    paymentReceiveVoucher.setAmount(cashPaymentReceive.getCash_amount()+cashPaymentReceive.getCard_amount());
                                    paymentReceiveVoucher.insert(db);
                                    //DebugHelper.print(PaymentReceiveVoucher.select(db,"1=1"));
                                    posInvoiceHead.setVoucher_no(paymentReceiveVoucher.getInv_id());

                                    posInvoiceHead.update(db);
                                    hideSoftKeyboard(InvoicePayment.this);
                                    dialog.dismiss();
                                    make_payment.setEnabled(false);
                                    make_payment_print_later.setEnabled(false);
                                    printPaymentSlip(getApplicationContext(), cashPaymentReceive.getVoucher(), card_layout);
                                    Toast.makeText(getApplicationContext(),TextString.textSelectByVarname(db,"pos_sale_payment_popbox_invoice_complete").getText(), Toast.LENGTH_LONG).show();
                                    finish();
                                    break;
                                case DialogInterface.BUTTON_NEGATIVE:
                                    break;
                            }
                        }
                    };
                    AlertDialog.Builder builder = new AlertDialog.Builder(InvoicePayment.this);
                    builder.setMessage(TextString.textSelectByVarname(db,"pos_sale_payment_popbox_confermation").getText()).setPositiveButton(TextString.textSelectByVarname(db,"pos_sale_payment_popbox_confermation_yes").getText(), dialogClickListener)
                            .setNegativeButton(TextString.textSelectByVarname(db,"pos_sale_payment_popbox_confermation_no").getText(), dialogClickListener).show();
                }
            }
        });
    }
}
