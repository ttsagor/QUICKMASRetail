package com.quickmas.salim.quickmasretail.Module.PaymentReceived;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.VisibleForTesting;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.quickmas.salim.quickmasretail.Database.DBInitialization;
import com.quickmas.salim.quickmasretail.Model.POS.CashPaymentReceive;
import com.quickmas.salim.quickmasretail.Model.POS.CashPaymentReceivedDetails;
import com.quickmas.salim.quickmasretail.Model.POS.CoAccount;
import com.quickmas.salim.quickmasretail.Model.POS.CoAccountReceiveDetails;
import com.quickmas.salim.quickmasretail.Model.POS.ExcessCashReceived;
import com.quickmas.salim.quickmasretail.Model.POS.PaymentReceiveVoucher;
import com.quickmas.salim.quickmasretail.Model.POS.PosInvoiceHead;
import com.quickmas.salim.quickmasretail.Model.System.DashboardMenu;
import com.quickmas.salim.quickmasretail.Model.System.TextString;
import com.quickmas.salim.quickmasretail.Model.System.User;
import com.quickmas.salim.quickmasretail.R;
import com.quickmas.salim.quickmasretail.Service.UploadData;
import com.quickmas.salim.quickmasretail.Structure.CashPaymentReceiveAPI;
import com.quickmas.salim.quickmasretail.Structure.CoAccountStruct;
import com.quickmas.salim.quickmasretail.Utility.DateTimeCalculation;
import com.quickmas.salim.quickmasretail.Utility.DebugHelper;
import com.quickmas.salim.quickmasretail.Utility.FontSettings;
import com.quickmas.salim.quickmasretail.Utility.TypeConvertion;
import com.quickmas.salim.quickmasretail.Utility.UI.ScrollListView;
import com.quickmas.salim.quickmasretail.Utility.UI.ViewData;
import com.quickmas.salim.quickmasretail.Utility.UIComponent;
import com.tsongkha.spinnerdatepicker.DatePicker;
import com.tsongkha.spinnerdatepicker.DatePickerDialog;
import com.tsongkha.spinnerdatepicker.SpinnerDatePickerDialogBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import es.dmoral.toasty.Toasty;

import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_cash_payemnt_id;
import static com.quickmas.salim.quickmasretail.Model.POS.PosInvoice.updateInvoiceStatus;
import static com.quickmas.salim.quickmasretail.Model.System.DashboardMenu.selectByVarname;
import static com.quickmas.salim.quickmasretail.Model.System.User.getUserDetails;
import static com.quickmas.salim.quickmasretail.Module.PaymentSlipPrint.PaymentReceiveSlipPrint.printPaymentSlip;
import static com.quickmas.salim.quickmasretail.Service.ApiSettings.url_cash_receive_details_upload;
import static com.quickmas.salim.quickmasretail.Service.ApiSettings.url_cash_receive_upload;
import static com.quickmas.salim.quickmasretail.Utility.DateTimeCalculation.getCurrentDateTime;
import static com.quickmas.salim.quickmasretail.Utility.DateTimeCalculation.getDate;
import static com.quickmas.salim.quickmasretail.Utility.FontSettings.getFont;
import static com.quickmas.salim.quickmasretail.Utility.FontSettings.setTextFont;
import static com.quickmas.salim.quickmasretail.Utility.NetworkConfiguration.isInternetOn;
import static com.quickmas.salim.quickmasretail.Utility.UIComponent.hideSoftKeyboard;
import static com.quickmas.salim.quickmasretail.Utility.UIComponent.setLinearLayoutBackground;
import static com.quickmas.salim.quickmasretail.Utility.UIComponent.showSoftKeyboard;
import static com.quickmas.salim.quickmasretail.Utility.UIComponent.spinnerDataLoad;

public class CashPayment extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
    DBInitialization db;
    Context mContex;
    EditText date_from;
    EditText date_to;
    boolean isStartDatePicked =false;
    ArrayAdapter<String> spinnerArrayAdapter;
    ArrayList<CashPaymentReceive> cashPaymentReceivesList = new ArrayList<>();
    TextView remarksPop;
    TextView amountPop;
    Spinner spinner;
    Button add_coa;
    int updatePosition=-1;
    String from_date_string="";
    String to_date_string="";
    ProgressDialog progressDoalog_bank;
    Button bulk_payment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("custom-message"));
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_cash_payment);
        mContex = this;
        db = new DBInitialization(this,null,null,1);
        DashboardMenu cMenu =  selectByVarname(db,"dashboard_sell_pos_list");
        LinearLayout headimg = (LinearLayout) findViewById(R.id.image1);
        headimg = setLinearLayoutBackground(headimg, cMenu.getImage(), this);
        TextView headtext = (TextView) findViewById(R.id.text1);
        headtext.setTypeface(getFont(this));
        headtext.setText("Receive");
        from_date_string = DateTimeCalculation.getCurrentDate();
        to_date_string= DateTimeCalculation.getCurrentDate();
        bulk_payment = (Button) findViewById(R.id.bulk_payment);
        final Button ca_payment = (Button) findViewById(R.id.ca_payment);


        bulk_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UIComponent.hideSoftKeyboard(CashPayment.this);
                LayoutInflater layoutInflater = (LayoutInflater) mContex
                        .getSystemService(LAYOUT_INFLATER_SERVICE);
                final View popupView = layoutInflater.inflate(R.layout.pop_up_cash_payment_bulk_payment, null);
                final PopupWindow popupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);


                Button customer_submit_pop  = FontSettings.setTextFont((Button) popupView.findViewById(R.id.customer_submit_pop),mContex,db,"pos_sale_customer_add_submit_invoice");
                TextView payment_title  = FontSettings.setTextFont((TextView) popupView.findViewById(R.id.payment_title),mContex,db,"pos_sale_payment_popbox_title_invoice");

                final ImageView close_tab = (ImageView) popupView.findViewById(R.id.close_tab);

                final Button cash_btn = FontSettings.setTextFont((Button) popupView.findViewById(R.id.cash_btn),mContex,db,"pos_sale_payment_popbox_cash_invoice");
                final Button card_btn = FontSettings.setTextFont((Button) popupView.findViewById(R.id.card_btn),mContex,db,"pos_sale_payment_popbox_card_invoice");

                Button make_payment  = FontSettings.setTextFont((Button) popupView.findViewById(R.id.make_payment),mContex,db,"pos_sale_payment_popbox_invoice_done");
                Button make_payment_print_later  = FontSettings.setTextFont((Button) popupView.findViewById(R.id.make_payment_print_later),mContex,db,"pos_sale_payment_popbox_invoice_done_noprint");

                final LinearLayout cash_layout = (LinearLayout) popupView.findViewById(R.id.cash_layout);
                final LinearLayout card_layout = (LinearLayout) popupView.findViewById(R.id.card_layout);

                cash_layout.setVisibility(View.VISIBLE);
                card_layout.setVisibility(View.GONE);

                User cUser = new User();
                cUser.select(db,"1=1");
                System.out.println(cUser.getUser_name()+" "+cUser.getPassword());


                cash_btn.setTextColor(Color.parseColor("#ffffff"));
                card_btn.setTextColor(Color.parseColor("#00a81e"));

                cash_btn.setBackgroundColor(Color.parseColor("#00a81e"));
                card_btn.setBackgroundColor(Color.parseColor("#ffffff"));

                close_tab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });

                cash_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cash_btn.setTextColor(Color.parseColor("#ffffff"));
                        card_btn.setTextColor(Color.parseColor("#00a81e"));

                        cash_btn.setBackgroundColor(Color.parseColor("#00a81e"));
                        card_btn.setBackgroundColor(Color.parseColor("#ffffff"));

                        cash_layout.setVisibility(View.VISIBLE);
                        card_layout.setVisibility(View.GONE);
                    }
                });

                card_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cash_btn.setTextColor(Color.parseColor("#00a81e"));
                        card_btn.setTextColor(Color.parseColor("#ffffff"));

                        cash_btn.setBackgroundColor(Color.parseColor("#ffffff"));
                        card_btn.setBackgroundColor(Color.parseColor("#00a81e"));

                        cash_layout.setVisibility(View.GONE);
                        card_layout.setVisibility(View.VISIBLE);
                    }
                });


                final Spinner customer_selection = (Spinner) popupView.findViewById(R.id.customer_selection);
                ArrayAdapter<String> dataAdapterc = spinnerDataLoad(mContex,db,db.COLUMN_pos_customer_name,db.TABLE_PosCustomer,"1=1","","Select Customer");
                customer_selection.setAdapter(dataAdapterc);

                ///----cash section

                final EditText note_given_cash  = (EditText) popupView.findViewById(R.id.note_given_cash);
                final EditText remark1  = (EditText) popupView.findViewById(R.id.remark1);
                final EditText remark2  = (EditText) popupView.findViewById(R.id.remark2);

                //------card section
                final TextView cheque_no  = FontSettings.setTextFontHint((TextView) popupView.findViewById(R.id.cheque_no),mContex,db,"pos_sale_payment_popbox_card_invoice_cardno");
                final TextView payment_amount  = (TextView) popupView.findViewById(R.id.payment_amount);
                final Spinner card_type = (Spinner) popupView.findViewById(R.id.card_type);
                ArrayAdapter<String> dataAdapter = spinnerDataLoad(mContex,db,db.COLUMN_card_name,db.TABLE_card,"1=1","",TextString.textSelectByVarname(db,"pos_sale_payment_popbox_card_invoice_cardtype").getText());
                card_type.setAdapter(dataAdapter);
                final Spinner banklist = (Spinner) popupView.findViewById(R.id.banklist);
                dataAdapter = spinnerDataLoad(mContex,db,db.COLUMN_bank_name,db.TABLE_bank,"1=1","",TextString.textSelectByVarname(db,"pos_sale_payment_popbox_card_invoice_bank").getText());
                banklist.setAdapter(dataAdapter);


                make_payment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final User cSystemInfo = new User();
                        cSystemInfo.select(db,"1=1");

                        final CashPaymentReceive cashPaymentReceive = new CashPaymentReceive();

                        if(customer_selection.getSelectedItemPosition()==0)
                        {
                            Toasty.error(getApplicationContext(), TextString.textSelectByVarname(db,"Please fill up properly").getText(), Toast.LENGTH_SHORT, true).show();
                            return;
                        }
                        cashPaymentReceive.setCustomer_name(customer_selection.getSelectedItem().toString());
                        cashPaymentReceive.setReceived_by(cSystemInfo.getUser_name());
                        if(cash_layout.getVisibility()==View.VISIBLE)
                        {
                            if(TypeConvertion.parseDouble(note_given_cash.getText().toString())>0.0 && remark1.getText().length()>0)
                            {
                                cashPaymentReceive.setCash_amount(TypeConvertion.parseDouble(note_given_cash.getText().toString()));
                                cashPaymentReceive.setRemark(remark1.getText().toString());
                            }
                            else
                            {
                                Toasty.error(getApplicationContext(), TextString.textSelectByVarname(db,"Please fill up properly").getText(), Toast.LENGTH_SHORT, true).show();
                                return;
                            }
                        }
                        else if(card_layout.getVisibility()==View.VISIBLE)
                        {
                            if(TypeConvertion.parseDouble(payment_amount.getText().toString())>0.0 && card_type.getSelectedItemPosition()>0 && banklist.getSelectedItemPosition()>0 && !cheque_no.equals("") && remark2.getText().length()>0)
                            {
                                cashPaymentReceive.setCard_amount(TypeConvertion.parseDouble(payment_amount.getText().toString()));
                                cashPaymentReceive.setCard_type(card_type.getSelectedItem().toString());
                                cashPaymentReceive.setBank_name(banklist.getSelectedItem().toString());
                                cashPaymentReceive.setCheque_no(cheque_no.getText().toString());
                                cashPaymentReceive.setRemark(remark2.getText().toString());
                            }
                            else
                            {
                                Toasty.error(getApplicationContext(), TextString.textSelectByVarname(db,"Please fill up properly").getText(), Toast.LENGTH_SHORT, true).show();
                                return;
                            }
                        }
                        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which){
                                    case DialogInterface.BUTTON_POSITIVE:
                                        int sl_id = PaymentReceiveVoucher.getMaxIdByCategory("P",db);
                                        String date_time = DateTimeCalculation.getCurrentDateTime();
                                        cashPaymentReceive.setDate_time(date_time);
                                        cashPaymentReceive.voucher = ("Rec-P-"+sl_id);
                                        cashPaymentReceive.insert(db);
                                        final int inId = cashPaymentReceive.getMaxId(db);

                                        PaymentReceiveVoucher paymentReceiveVoucher = new PaymentReceiveVoucher();
                                        paymentReceiveVoucher.setSl_id(sl_id);
                                        paymentReceiveVoucher.setInv_id("Rec-P-"+sl_id);
                                        paymentReceiveVoucher.setCategory("P");
                                        paymentReceiveVoucher.setCustomer(cashPaymentReceive.getCustomer_name());
                                        if(cash_layout.getVisibility()==View.VISIBLE)
                                        {
                                            paymentReceiveVoucher.setPayment_mode("Cash");
                                        }
                                        else
                                        {
                                            paymentReceiveVoucher.setPayment_mode("Card");
                                        }
                                        paymentReceiveVoucher.setPrint_by(cSystemInfo.getUser_name());
                                        paymentReceiveVoucher.setPrint_date(date_time);
                                        if(cashPaymentReceive.getCard_type().equals(""))
                                        {
                                            paymentReceiveVoucher.setCard_info(cashPaymentReceive.getCheque_no());
                                        }
                                        else {
                                            paymentReceiveVoucher.setCard_info(cashPaymentReceive.getCard_type());
                                        }
                                        paymentReceiveVoucher.setReamrk(cashPaymentReceive.getRemark());
                                        paymentReceiveVoucher.setCard_number(cashPaymentReceive.getCheque_no());
                                        paymentReceiveVoucher.setAmount(cashPaymentReceive.getCash_amount()+cashPaymentReceive.getCard_amount());
                                        paymentReceiveVoucher.insert(db);



                                        updatePosPaid(cashPaymentReceive.getCustomer_name(), cashPaymentReceive.getCash_amount()+cashPaymentReceive.getCard_amount(),inId);
                                        cSystemInfo.setCash_cu_balance(cSystemInfo.getCash_cu_balance()+cashPaymentReceive.getCash_amount());
                                        cSystemInfo.update(db);
                                        hideSoftKeyboard(CashPayment.this);
                                        dialog.dismiss();
                                        popupWindow.dismiss();
                                        if(cSystemInfo.getActive_online()==1 && isInternetOn(mContex)) {
                                            printNow = true;
                                            uploadBulkPaymentData(inId);
                                        }
                                        else
                                        {
                                            printPaymentSlip(mContex, cashPaymentReceive.getVoucher(), card_layout);
                                            Toasty.success(getApplicationContext(),TextString.textSelectByVarname(db,"pos_sale_payment_popbox_invoice_complete").getText(), Toast.LENGTH_LONG).show();

                                        }
                                       break;

                                    case DialogInterface.BUTTON_NEGATIVE:
                                        //No button clicked
                                        break;
                                }
                            }
                        };
                        AlertDialog.Builder builder = new AlertDialog.Builder(mContex);
                        builder.setMessage(TextString.textSelectByVarname(db,"pos_sale_payment_popbox_confermation").getText()).setPositiveButton(TextString.textSelectByVarname(db,"pos_sale_payment_popbox_confermation_yes").getText(), dialogClickListener)
                                .setNegativeButton(TextString.textSelectByVarname(db,"pos_sale_payment_popbox_confermation_no").getText(), dialogClickListener).show();
                    }
                });
                make_payment_print_later.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final User cSystemInfo = new User();
                        cSystemInfo.select(db,"1=1");

                        final CashPaymentReceive cashPaymentReceive = new CashPaymentReceive();

                        if(customer_selection.getSelectedItemPosition()==0)
                        {
                            Toasty.error(getApplicationContext(), TextString.textSelectByVarname(db,"Please fill up properly").getText(), Toast.LENGTH_SHORT, true).show();
                            return;
                        }
                        cashPaymentReceive.setCustomer_name(customer_selection.getSelectedItem().toString());
                        cashPaymentReceive.setReceived_by(cSystemInfo.getUser_name());
                        if(cash_layout.getVisibility()==View.VISIBLE)
                        {

                            if(TypeConvertion.parseDouble(note_given_cash.getText().toString())>0.0 && remark1.getText().length()>0)
                            {
                                cashPaymentReceive.setCash_amount(TypeConvertion.parseDouble(note_given_cash.getText().toString()));
                                cashPaymentReceive.setRemark(remark1.getText().toString());
                            }
                            else
                            {
                                Toasty.error(getApplicationContext(), TextString.textSelectByVarname(db,"Please fill up properly").getText(), Toast.LENGTH_SHORT, true).show();
                                return;
                            }
                        }
                        else if(card_layout.getVisibility()==View.VISIBLE)
                        {
                            if(TypeConvertion.parseDouble(payment_amount.getText().toString())>0.0 && card_type.getSelectedItemPosition()>0 && banklist.getSelectedItemPosition()>0 && !cheque_no.equals("") && remark2.getText().length()>0)
                            {
                                cashPaymentReceive.setCard_amount(TypeConvertion.parseDouble(payment_amount.getText().toString()));
                                cashPaymentReceive.setCard_type(card_type.getSelectedItem().toString());
                                cashPaymentReceive.setBank_name(banklist.getSelectedItem().toString());
                                cashPaymentReceive.setCheque_no(cheque_no.getText().toString());
                                cashPaymentReceive.setRemark(remark2.getText().toString());
                            }
                            else
                            {
                                Toasty.error(getApplicationContext(), TextString.textSelectByVarname(db,"Please fill up properly").getText(), Toast.LENGTH_SHORT, true).show();
                                return;
                            }
                        }

                        //DebugHelper.print(CashPaymentReceive.select(db,"1=1"));

                        //DebugHelper.print(cashPaymentReceive);
                        //return;

                        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which){
                                    case DialogInterface.BUTTON_POSITIVE:

                                        int sl_id = PaymentReceiveVoucher.getMaxIdByCategory("P",db);
                                        String date_time = DateTimeCalculation.getCurrentDateTime();
                                        cashPaymentReceive.setDate_time(date_time);
                                        cashPaymentReceive.voucher = ("Rec-P-"+sl_id);
                                        cashPaymentReceive.insert(db);
                                        final int inId = cashPaymentReceive.getMaxId(db);

                                        PaymentReceiveVoucher paymentReceiveVoucher = new PaymentReceiveVoucher();
                                        paymentReceiveVoucher.setSl_id(sl_id);
                                        paymentReceiveVoucher.setInv_id("Rec-P-"+sl_id);
                                        paymentReceiveVoucher.setCategory("P");
                                        paymentReceiveVoucher.setCustomer(cashPaymentReceive.getCustomer_name());
                                        if(cash_layout.getVisibility()==View.VISIBLE)
                                        {
                                            paymentReceiveVoucher.setPayment_mode("Cash");
                                        }
                                        else
                                        {
                                            paymentReceiveVoucher.setPayment_mode("Card");
                                        }
                                        paymentReceiveVoucher.setPrint_by(cSystemInfo.getUser_name());
                                        paymentReceiveVoucher.setPrint_date(date_time);
                                        if(cashPaymentReceive.getCard_type().equals(""))
                                        {
                                            paymentReceiveVoucher.setCard_info(cashPaymentReceive.getCheque_no());
                                        }
                                        else {
                                            paymentReceiveVoucher.setCard_info(cashPaymentReceive.getCard_type());
                                        }
                                        paymentReceiveVoucher.setReamrk(cashPaymentReceive.getRemark());
                                        paymentReceiveVoucher.setCard_number(cashPaymentReceive.getCheque_no());
                                        paymentReceiveVoucher.setAmount(cashPaymentReceive.getCash_amount()+cashPaymentReceive.getCard_amount());
                                        paymentReceiveVoucher.insert(db);

                                        updatePosPaid(cashPaymentReceive.getCustomer_name(), cashPaymentReceive.getCash_amount()+cashPaymentReceive.getCard_amount(),inId);
                                        cSystemInfo.setCash_cu_balance(cSystemInfo.getCash_cu_balance()+cashPaymentReceive.getCash_amount());
                                        cSystemInfo.update(db);
                                        hideSoftKeyboard(CashPayment.this);
                                        dialog.dismiss();
                                        popupWindow.dismiss();
                                        if(cSystemInfo.getActive_online()==1 && isInternetOn(mContex)) {
                                            uploadBulkPaymentData(inId);
                                        }
                                        else
                                        {
                                            Toasty.success(getApplicationContext(),TextString.textSelectByVarname(db,"pos_sale_payment_popbox_invoice_complete").getText(), Toast.LENGTH_LONG).show();

                                        }
                                        break;

                                    case DialogInterface.BUTTON_NEGATIVE:
                                        //No button clicked
                                        break;
                                }
                            }
                        };
                        AlertDialog.Builder builder = new AlertDialog.Builder(mContex);
                        builder.setMessage(TextString.textSelectByVarname(db,"pos_sale_payment_popbox_confermation").getText()).setPositiveButton(TextString.textSelectByVarname(db,"pos_sale_payment_popbox_confermation_yes").getText(), dialogClickListener)
                                .setNegativeButton(TextString.textSelectByVarname(db,"pos_sale_payment_popbox_confermation_no").getText(), dialogClickListener).show();

                    }
                });
                popupWindow.setOutsideTouchable(true);
                popupWindow.setFocusable(true);
                popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                popupWindow.showAtLocation(bulk_payment, Gravity.CENTER, 0, 0);
                showSoftKeyboard(CashPayment.this);
                popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        showSoftKeyboard(CashPayment.this);
                    }
                });
            }
        });

        ca_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UIComponent.hideSoftKeyboard(CashPayment.this);
                LayoutInflater layoutInflater = (LayoutInflater) mContex
                        .getSystemService(LAYOUT_INFLATER_SERVICE);
                final View popupView = layoutInflater.inflate(R.layout.pop_up_co_receive, null);
                final PopupWindow popupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);



                final ImageView close_tab = (ImageView) popupView.findViewById(R.id.close_tab);


                Button make_payment  = FontSettings.setTextFont((Button) popupView.findViewById(R.id.make_payment),mContex,db,"pos_sale_payment_popbox_invoice_done");
                Button make_payment_print_later  = FontSettings.setTextFont((Button) popupView.findViewById(R.id.make_payment_print_later),mContex,db,"pos_sale_payment_popbox_invoice_done_noprint");

                Button receive  = FontSettings.setTextFont((Button) popupView.findViewById(R.id.receive),mContex,db,"Receive");

                add_coa  = FontSettings.setTextFont((Button) popupView.findViewById(R.id.add_coa),mContex,db,"Add");
                Button remove_coa  = FontSettings.setTextFont((Button) popupView.findViewById(R.id.remove_coa),mContex,db,"Remove");


                final User cUser = new User();
                cUser.select(db,"1=1");


                close_tab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });


                spinner = (Spinner) popupView.findViewById(R.id.co_list);

                final ArrayList<String> ac_type = new ArrayList<>();
                final ArrayList<String> ac_name = new ArrayList<>();

                ArrayList<CoAccountStruct> coAccountStructs = CoAccountStruct.getAccount(CoAccount.select(db,"1=1"));

                for(CoAccountStruct coAccountStruct : coAccountStructs)
                {
                    if(!ac_type.contains(coAccountStruct.account_type))
                    {
                        ac_type.add(coAccountStruct.account_type);
                        ac_name.add(coAccountStruct.account_type);
                    }
                    ac_name.addAll(coAccountStruct.account_name);
                }

               // ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(mContex,R.layout.spinner_item,ac_name);
                // Initializing an ArrayAdapter
                spinnerArrayAdapter = new ArrayAdapter<String>(mContex,R.layout.spinner_item,ac_name){
                    @Override
                    public View getDropDownView(int position, View convertView,
                                                ViewGroup parent) {
                        View view = super.getDropDownView(position, convertView, parent);
                        TextView tv = (TextView) view;
                        if(ac_type.contains(tv.getText().toString())) {
                            tv.setBackgroundColor(Color.parseColor("#00a81e"));
                        }
                        else {
                            tv.setText("    "+tv.getText().toString());
                            tv.setBackgroundColor(Color.parseColor("#ffffff"));
                            // Set the alternate item background color
                           // tv.setBackgroundColor(Color.parseColor("#FFAF89E5"));
                        }
                        return view;
                    }
                    @Override
                    public boolean isEnabled(int position) {
                        String val = spinnerArrayAdapter.getItem(position);
                        return !ac_type.contains(val);
                    }
                };
                spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
                spinner.setAdapter(spinnerArrayAdapter);

                remarksPop = (TextView) popupView.findViewById(R.id.remarks);
                amountPop = (TextView) popupView.findViewById(R.id.amount);


                cashPaymentReceivesList = new ArrayList<>();
                final ListView coa_list = popupView.findViewById(R.id.coa_list);

                ScrollListView.loadListViewUpdateHeight(mContex, coa_list, R.layout.adaptar_coa_list, cashPaymentReceivesList, "AdaptarCoAList", 0, 100, true);


                add_coa.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(spinner.getSelectedItemPosition()>0 && !remarksPop.getText().equals("") && !amountPop.getText().equals(""))
                        {

                            CashPaymentReceive cashPaymentReceive = new CashPaymentReceive();
                            cashPaymentReceive.setAccount_name(spinner.getSelectedItem().toString());
                            cashPaymentReceive.setRemark(remarksPop.getText().toString());
                            cashPaymentReceive.setCa_amount(TypeConvertion.parseDouble(amountPop.getText().toString()));

                            if(add_coa.getText().equals("Update"))
                            {
                                cashPaymentReceivesList.set(updatePosition,cashPaymentReceive);
                                add_coa.setText("Add");
                                updatePosition=-1;
                            }
                            else
                            {
                                cashPaymentReceivesList.add(cashPaymentReceive);
                            }

                            ScrollListView.loadListViewUpdateHeight(mContex, coa_list, R.layout.adaptar_coa_list, cashPaymentReceivesList, "AdaptarCoAList", 0, 100, true);

                            spinner.setSelection(0,true);
                            remarksPop.setText("");
                            amountPop.setText("");

                            //coa_list.notify();
                        }
                        else
                        {
                            Toasty.error(getApplicationContext(), TextString.textSelectByVarname(db,"Please fill up properly").getText(), Toast.LENGTH_SHORT, true).show();
                            return;
                        }
                    }
                });
                remove_coa.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        User cSystemInfo = new User();
                        cSystemInfo.select(db,"1=1");
                            if(updatePosition>-1)
                            {
                                cashPaymentReceivesList.remove(updatePosition);
                                ScrollListView.loadListViewUpdateHeight(mContex, coa_list, R.layout.adaptar_coa_list, cashPaymentReceivesList, "AdaptarCoAList", 0, 100, true);

                                add_coa.setText("Add");
                                spinner.setSelection(0,true);
                                remarksPop.setText("");
                                amountPop.setText("");
                                updatePosition=-1;
                            }
                        }
                });

                receive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(cashPaymentReceivesList.size()>0) {
                            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    switch (which){
                                        case DialogInterface.BUTTON_POSITIVE:
                                            final User cSystemInfo = new User();
                                            cSystemInfo.select(db, "1=1");
                                            double total_amount=0.0;
                                            String dateTime = getCurrentDateTime();
                                            int receive_id = CashPaymentReceive.getMaxId(db)+1;
                                            for(CashPaymentReceive cashPaymentReceivesList : cashPaymentReceivesList)
                                            {
                                                total_amount+=cashPaymentReceivesList.getCa_amount();
                                                CoAccountReceiveDetails coAccountReceiveDetails = new CoAccountReceiveDetails();
                                                coAccountReceiveDetails.setReceive_id(receive_id);
                                                coAccountReceiveDetails.setAccount_name(cashPaymentReceivesList.getAccount_name());
                                                coAccountReceiveDetails.setRemarks(cashPaymentReceivesList.getRemark());
                                                coAccountReceiveDetails.setAmount(cashPaymentReceivesList.getCa_amount());
                                                coAccountReceiveDetails.setDate_time(dateTime);
                                                coAccountReceiveDetails.insert(db);
                                            }

                                            CashPaymentReceive cashPaymentReceive = new CashPaymentReceive();
                                            cashPaymentReceive.setCa_amount(total_amount);
                                            cashPaymentReceive.setCustomer_name("Income Receive");
                                            cashPaymentReceive.setReceived_by(cUser.getUser_name());
                                            cashPaymentReceive.setDate_time(dateTime);
                                            cashPaymentReceive.insert(db);
                                            cSystemInfo.setCash_cu_balance(cSystemInfo.getCash_cu_balance()+total_amount);
                                            cSystemInfo.update(db);
                                            popupWindow.dismiss();

                                            if(cSystemInfo.getActive_online()==1 && isInternetOn(mContex)) {
                                                uploadCAPaymentData(receive_id);
                                            }
                                            else
                                            {
                                                Toasty.success(getApplicationContext(),TextString.textSelectByVarname(db,"pos_sale_payment_popbox_invoice_complete").getText(), Toast.LENGTH_LONG).show();

                                            }
                                            break;

                                        case DialogInterface.BUTTON_NEGATIVE:
                                            //No button clicked
                                            break;
                                    }
                                }
                            };

                            AlertDialog.Builder builder = new AlertDialog.Builder(mContex);
                            builder.setMessage(TextString.textSelectByVarname(db,"pos_sale_payment_popbox_confermation").getText()).setPositiveButton(TextString.textSelectByVarname(db,"pos_sale_payment_popbox_confermation_yes").getText(), dialogClickListener)
                                    .setNegativeButton(TextString.textSelectByVarname(db,"pos_sale_payment_popbox_confermation_no").getText(), dialogClickListener).show();

                        }

                    }
                });
                popupWindow.setOutsideTouchable(true);
                popupWindow.setFocusable(true);
                popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                popupWindow.showAtLocation(bulk_payment, Gravity.CENTER, 0, 0);
                showSoftKeyboard(CashPayment.this);
                popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        showSoftKeyboard(CashPayment.this);
                    }
                });
            }
        });


        final Button show_report = FontSettings.setTextFont((Button) findViewById(R.id.date_selection),this,"Date Range");

        show_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateSelection(show_report);
            }
        });

        loadListView();

    }

    void loadListView()
    {
        String con = db.COLUMN_cash_payemnt_date_time+" BETWEEN '"+from_date_string+" 00:00:00' AND "+"'"+to_date_string+" 23:59:59'";
        ArrayList<CashPaymentReceive> cashPaymentReceives = CashPaymentReceive.select(db,con);

        DebugHelper.print(cashPaymentReceives);
        DebugHelper.print(CoAccountReceiveDetails.select(db,"1=1"));

        ListView memo_list = (ListView) findViewById(R.id.memo_list);
        int totalCount = cashPaymentReceives.size();
        double totalAmount=0.0;

        for (CashPaymentReceive cashPaymentReceive : cashPaymentReceives)
        {
            totalAmount+= cashPaymentReceive.getCa_amount()+cashPaymentReceive.getCash_amount()+cashPaymentReceive.getCard_amount();
        }

        LayoutInflater layoutinflater = getLayoutInflater();
        ViewGroup footer = (ViewGroup)layoutinflater.inflate(R.layout.cash_receive_header,memo_list,false);
        String t = TextString.textSelectByVarname(db,"memo_total_count").getText();
        final TextView invoice_id_total = setTextFont((TextView) footer.findViewById(R.id.invoice_id_total),this,"");
        final TextView invoice_id_date = setTextFont((TextView) footer.findViewById(R.id.invoice_id_date),this,t+" "+String.valueOf(totalCount));
        final TextView quantity_total = setTextFont((TextView) footer.findViewById(R.id.quantity_total),this,String.valueOf(""));
        final TextView amount_total = setTextFont((TextView) footer.findViewById(R.id.amount_total),this,String.format("%.2f", totalAmount));



        LayoutInflater layoutinflater1 = getLayoutInflater();
        ViewGroup header = (ViewGroup)layoutinflater1.inflate(R.layout.cash_receive_header,memo_list,false);
        final TextView invoice_id_total_head = setTextFont((TextView) header.findViewById(R.id.invoice_id_total),this,"Rec No");
        final TextView invoice_id_date_head = setTextFont((TextView) header.findViewById(R.id.invoice_id_date),this,"Date");
        final TextView quantity_total_head = setTextFont((TextView) header.findViewById(R.id.quantity_total),this,"Mode");
        final TextView amount_total_head = setTextFont((TextView) header.findViewById(R.id.amount_total),this,"Amount");
        final TextView amount_amount_final = setTextFont((TextView) header.findViewById(R.id.amount_final),this,"Action");

        LinearLayout header_holder = (LinearLayout) findViewById(R.id.header_holder);
        LinearLayout footer_holder = (LinearLayout) findViewById(R.id.footer_holder);

        if(header_holder.getChildCount() > 0) {
            header_holder.removeAllViews();
        }

        if(footer_holder.getChildCount() > 0) {
            footer_holder.removeAllViews();
        }

        if(totalCount>0)
        {
            header_holder.addView(header);
            footer_holder.addView(footer);
        }
        ScrollListView.loadListView(mContex, memo_list, R.layout.pos_stock_list, cashPaymentReceives, "AdaptarReceivedList", 0, 100, true);

    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        Calendar calendar = new GregorianCalendar(year, monthOfYear, dayOfMonth);
        if(isStartDatePicked)
        {
            date_to.setText(DateTimeCalculation.getDate(calendar));
            date_to.clearFocus();
        }
        else
        {
            date_from.setText(DateTimeCalculation.getDate(calendar));
            date_from.clearFocus();
        }
        System.out.println(DateTimeCalculation.getDateTime(calendar));
        //dateTextView.setText(simpleDateFormat.format(calendar.getTime()));
    }

    @VisibleForTesting
    void showDate(int year, int monthOfYear, int dayOfMonth, int spinnerTheme) {
        new SpinnerDatePickerDialogBuilder()
                .context(CashPayment.this)
                .callback(CashPayment.this)
                .spinnerTheme(spinnerTheme)
                .defaultDate(year, monthOfYear, dayOfMonth)
                .build()
                .show();
    }

    void updatePosPaid(String customer, Double amount,int receive_id)
    {
        double full_amount = amount;
        ArrayList<PosInvoiceHead> posInvoiceHeads = PosInvoiceHead.select(db,db.COLUMN_pos_invoice_head_customer+"='"+customer+"' AND "+db.COLUMN_pos_invoice_head_total_amount+">"+db.COLUMN_pos_invoice_head_total_paid_amount);
        for(PosInvoiceHead posInvoiceHead : posInvoiceHeads)
        {
            if(amount<=0)
            {
                break;
            }
            if(posInvoiceHead.getTotal_amount() - posInvoiceHead.getTotal_paid_amount() <= amount)
            {
                CashPaymentReceivedDetails d = new CashPaymentReceivedDetails();
                d.setReceiveId(receive_id);
                d.setInvoiceId(posInvoiceHead.getId());
                d.setDateTime(DateTimeCalculation.getCurrentDateTime());
                amount = amount - (posInvoiceHead.getTotal_amount() - posInvoiceHead.getTotal_paid_amount());
                //DebugHelper.print(posInvoiceHead);
                d.setAmount((posInvoiceHead.getTotal_amount() - posInvoiceHead.getTotal_paid_amount()));
                posInvoiceHead.setTotal_paid_amount(posInvoiceHead.getTotal_amount());
                posInvoiceHead.update(db);
                d.insert(db);
            }
            else
            {
                posInvoiceHead.setTotal_paid_amount(posInvoiceHead.getTotal_paid_amount()+ amount);
                posInvoiceHead.update(db);
                CashPaymentReceivedDetails d = new CashPaymentReceivedDetails();
                d.setReceiveId(receive_id);
                d.setInvoiceId(posInvoiceHead.getId());
                d.setAmount(amount);
                d.setDateTime(DateTimeCalculation.getCurrentDateTime());
                d.insert(db);
                amount=0.0;
                break;
            }
        }

        ArrayList<ExcessCashReceived> ec = ExcessCashReceived.select(db, db.COLUMN_excss_cash_customer+"='"+customer+"'");

        if(amount>0.0)
        {
            if (ec.size() > 0) {
                ExcessCashReceived ex = ec.get(ec.size() - 1);
                ex.setId(0);
                ex.setCustomerName(customer);
                ex.setAmount(ex.getAmount() + amount);
                ex.setReceiveId(receive_id);
                ex.setReceived_amount(amount);
                ex.insert(db);
            } else {
                ExcessCashReceived ex = new ExcessCashReceived();
                ex.setId(0);
                ex.setCustomerName(customer);
                ex.setAmount(amount);
                ex.setReceiveId(receive_id);
                ex.setReceived_amount(amount);
                ex.insert(db);
            }
        }



        //ec = ExcessCashReceived.select(db, "1=1");
       // posInvoiceHeads = PosInvoiceHead.select(db,"1=1");

        //ArrayList<CashPaymentReceivedDetails> d = CashPaymentReceivedDetails.select(db, "1=1");

        //DebugHelper.print(d);
        //DebugHelper.print(posInvoiceHeads);
    }

    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String position = intent.getStringExtra("position");
            CashPaymentReceive cashPaymentReceive =  cashPaymentReceivesList.get(TypeConvertion.parseInt(position));

            remarksPop.setText(cashPaymentReceive.getRemark());
            amountPop.setText(String.valueOf(cashPaymentReceive.getCa_amount()));
            spinner.setSelection(((ArrayAdapter<String>)spinner.getAdapter()).getPosition(cashPaymentReceive.getAccount_name()));
            add_coa.setText("Update");
            updatePosition = TypeConvertion.parseInt(position);
        }
    };

    public void AdaptarReceivedList(final ViewData data)
    {
        final CashPaymentReceive cashPaymentReceive = (CashPaymentReceive) data.object;

        final String in = "Rec-" +String.valueOf(cashPaymentReceive.getId());

        double d = cashPaymentReceive.getCa_amount()+cashPaymentReceive.getCash_amount()+cashPaymentReceive.getCard_amount();
        final String amount = String.format("%.2f", d);

        String quantity ="Cash Income";
        if(cashPaymentReceive.getCash_amount()+cashPaymentReceive.getCard_amount()>0)
        {
            quantity = String.valueOf(cashPaymentReceive.getCustomer_name());
        }

        TextView invoice_id = FontSettings.setTextFont((TextView) data.view.findViewById(R.id.invoice_id),mContex,in);
        TextView date=FontSettings.setTextFont((TextView) data.view.findViewById(R.id.date),mContex,getDate(cashPaymentReceive.getDate_time()));
        TextView quantityT=FontSettings.setTextFont((TextView) data.view.findViewById(R.id.quantity),mContex,String.valueOf(quantity));
        TextView amountT=FontSettings.setTextFont((TextView) data.view.findViewById(R.id.amount),mContex,String.valueOf(amount));
        final Button action=FontSettings.setTextFont((Button) data.view.findViewById(R.id.action),mContex,"Action");
        final LinearLayout layout_holder = (LinearLayout) data.view.findViewById(R.id.layout_holder);

        invoice_id.setTextColor(Color.parseColor("#006600"));
        quantityT.setTextColor(Color.parseColor("#006600"));
        amountT.setTextColor(Color.parseColor("#006600"));
        date.setTextColor(Color.parseColor("#006600"));
        action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater = (LayoutInflater) mContex
                        .getSystemService(LAYOUT_INFLATER_SERVICE);
                final View popupView = layoutInflater.inflate(R.layout.memo_pop_up, null);
                final PopupWindow popupWindow = new PopupWindow(popupView,LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                Button print  = FontSettings.setTextFont((Button) popupView.findViewById(R.id.print),mContex,db,"memopopup_print");
                Button delivery  = FontSettings.setTextFont((Button) popupView.findViewById(R.id.delivery),mContex,db,"memopopup_devilery");
                Button payment  = FontSettings.setTextFont((Button) popupView.findViewById(R.id.payment),mContex,db,"memopopup_payment");
                Button make_void  = FontSettings.setTextFont((Button) popupView.findViewById(R.id.make_void),mContex,db,"memopopup_void");

                delivery.setVisibility(View.GONE);
                payment.setVisibility(View.GONE);


                make_void.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                        Intent i = new Intent(mContex, CashPayment.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        //i.putExtra("id", String.valueOf(posInvoiceHead.getId()));
                        //context.startActivity(i);
                    }
                });

                print.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                        printPaymentSlip(mContex, String.valueOf(cashPaymentReceive.getId()), action);
                    }
                });

                popupWindow.dismiss();
                popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        layout_holder.setBackgroundColor(Color.parseColor("#e2e2e2"));
                    }
                });
                // Closes the popup window when touch outside.
                popupWindow.setOutsideTouchable(true);
                popupWindow.setFocusable(true);
                // Removes default background.
                popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                popupWindow.showAsDropDown(action, 0, 0);
                layout_holder.setBackgroundColor(Color.parseColor("#72a8ff"));
            }
        });

    }
    public void AdaptarCoAList(final ViewData data)
    {
        CashPaymentReceive cashPaymentReceive = (CashPaymentReceive) data.object;
        FontSettings.setTextFont((TextView) data.view.findViewById(R.id.coa),mContex,cashPaymentReceive.getAccount_name());
        FontSettings.setTextFont((TextView) data.view.findViewById(R.id.remark),mContex,cashPaymentReceive.getRemark());
        FontSettings.setTextFont((TextView) data.view.findViewById(R.id.amount),mContex,String.valueOf(cashPaymentReceive.getCa_amount()));
        Button update =  FontSettings.setTextFont((Button) data.view.findViewById(R.id.update),mContex,"Update");

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("custom-message");
                intent.putExtra("position",String.valueOf(data.position));
                LocalBroadcastManager.getInstance(mContex).sendBroadcast(intent);
            }
        });
    }

    void dateSelection(Button btn)
    {
        LayoutInflater layoutInflater = (LayoutInflater) mContex
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        final View popupView = layoutInflater.inflate(R.layout.date_selection_pop_up, null);
        final PopupWindow popupWindow = new PopupWindow(popupView,LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        Button date_search  = FontSettings.setTextFont((Button) popupView.findViewById(R.id.date_search),mContex,"Submit");
        date_from  = FontSettings.setTextFont((EditText) popupView.findViewById(R.id.date_from),mContex,"");
        date_to  = FontSettings.setTextFont((EditText) popupView.findViewById(R.id.date_to),mContex,"");



        date_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                from_date_string = date_from.getText().toString();
                to_date_string= date_to.getText().toString();
                loadListView();
                popupWindow.dismiss();
            }
        });

        date_from.setText(from_date_string);
        date_to.setText(to_date_string);


        date_to.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    isStartDatePicked = true;
                    showDate(DateTimeCalculation.getCalender(date_to.getText().toString()).get(Calendar.YEAR), DateTimeCalculation.getCalender(date_to.getText().toString()).get(Calendar.MONTH), DateTimeCalculation.getCalender(date_to.getText().toString()).get(Calendar.DATE), R.style.DatePickerSpinner);
                }
            }
        });

        date_from.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    isStartDatePicked = false;
                    showDate(DateTimeCalculation.getCalender(date_from.getText().toString()).get(Calendar.YEAR), DateTimeCalculation.getCalender(date_from.getText().toString()).get(Calendar.MONTH), DateTimeCalculation.getCalender(date_from.getText().toString()).get(Calendar.DATE), R.style.DatePickerSpinner);
                }
            }
        });

        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.showAsDropDown(btn, 0, 0);
    }

    int newBulkPaymentId = 0;
    boolean printNow = false;
    public void uploadBulkPaymentData(int id)
    {
        newBulkPaymentId=id;
        progressDoalog_bank = new ProgressDialog(mContex);
        try {
            progressDoalog_bank.setMessage("Payment Saving....");
            progressDoalog_bank.setTitle("Please Wait");
            progressDoalog_bank.show();

        }catch (Exception e){

        }
        final User cSystemInfo = new User();
        cSystemInfo.select(db,"1=1");
        UploadData uploadData = new UploadData();
        uploadData.data = CashPaymentReceive.select(db,COLUMN_cash_payemnt_id+"="+id);
        uploadData.url=url_cash_receive_upload + getUserDetails(cSystemInfo);
        uploadData.uploaddata(mContex,mContex,"",mContex,"uploadBulkPaymentDataComplete");

    }

    public void uploadBulkPaymentDataComplete(ArrayList<String> data)
    {
        try {
            int newId=0;
            CashPaymentReceive c = CashPaymentReceive.select(db, COLUMN_cash_payemnt_id + "=" + newBulkPaymentId).get(0);
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
            c.update(db,db.COLUMN_cash_payemnt_id+"="+newId,db.COLUMN_cash_payemnt_id+"="+c.id);
            progressDoalog_bank.dismiss();
            if(printNow)
            {
                printPaymentSlip(mContex, c.getVoucher(), bulk_payment);
            }
        }catch (Exception e){progressDoalog_bank.dismiss();}
        Toasty.success(getApplicationContext(),TextString.textSelectByVarname(db,"pos_sale_payment_popbox_invoice_complete").getText(), Toast.LENGTH_LONG).show();
    }


    int newCAPaymentId = 0;
    public void uploadCAPaymentData(int id)
    {
        newCAPaymentId=id;
        progressDoalog_bank = new ProgressDialog(mContex);
        try {
            progressDoalog_bank.setMessage("Payment Saving....");
            progressDoalog_bank.setTitle("Please Wait");
            progressDoalog_bank.show();

        }catch (Exception e){

        }
        final User cSystemInfo = new User();
        cSystemInfo.select(db,"1=1");
        UploadData uploadData = new UploadData();
        uploadData.data = CashPaymentReceive.select(db,COLUMN_cash_payemnt_id+"="+id);
        uploadData.url=url_cash_receive_upload + getUserDetails(cSystemInfo);
        uploadData.uploaddata(mContex,mContex,"",mContex,"uploadCAPaymentDataDetails");
    }

    public void uploadCAPaymentDataDetails(ArrayList<String> data)
    {
        int oldCAPaymentId = newCAPaymentId;
        try {
            CashPaymentReceive c = CashPaymentReceive.select(db, COLUMN_cash_payemnt_id + "=" + newCAPaymentId).get(0);
            final JSONArray jr = new JSONArray(data.get(0));
            for (int i = 0; i < jr.length(); i++)
            {
                try {
                    JSONObject jb = (JSONObject) jr.getJSONObject(i);
                    String[] vc = jb.get("voucher").toString().split("-");
                    newCAPaymentId = TypeConvertion.parseInt(vc[vc.length - 1]);
                }catch (Exception e){}
            }
            c.if_data_synced = 1;
            c.update(db);
            c.update(db,db.COLUMN_cash_payemnt_id+"="+newCAPaymentId,db.COLUMN_cash_payemnt_id+"="+c.id);
        }catch (Exception e){}

        final User cSystemInfo = new User();
        cSystemInfo.select(db,"1=1");
        ArrayList<CoAccountReceiveDetails> c = CoAccountReceiveDetails.select(db,db.COLUMN_cash_receive_coa_details_received_id+"="+oldCAPaymentId);
        DebugHelper.print(c);
        ArrayList<CashPaymentReceiveAPI> p = new ArrayList<>();

        for(CoAccountReceiveDetails cc : c)
        {
            cc.receive_id = newCAPaymentId;
            cc.update(db);
            CashPaymentReceive cs = CashPaymentReceive.select(db,db.COLUMN_supplier_cash_payemnt_id+"='"+cc.receive_id+"'").get(0);
            CashPaymentReceiveAPI pp = new CashPaymentReceiveAPI(cs,cc);
            p.add(pp);
        }

        UploadData uploadData = new UploadData();
        uploadData.data = p;
        uploadData.url=url_cash_receive_details_upload + getUserDetails(cSystemInfo);
        uploadData.uploaddata(mContex,mContex,"",mContex,"uploadCAPaymentDataComplete");



    }
    public void uploadCAPaymentDataComplete(ArrayList<String> data)
    {
        try {
            ArrayList<CoAccountReceiveDetails> c = CoAccountReceiveDetails.select(db,db.COLUMN_cash_receive_coa_details_received_id+"="+newCAPaymentId);
            for(CoAccountReceiveDetails cc : c)
            {
                cc.if_data_synced = 1;
                cc.update(db);
            }
        }catch (Exception e){}
        progressDoalog_bank.dismiss();
        Toasty.success(getApplicationContext(),TextString.textSelectByVarname(db,"pos_sale_payment_popbox_invoice_complete").getText(), Toast.LENGTH_LONG).show();

    }
}
