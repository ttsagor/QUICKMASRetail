package com.quickmas.salim.quickmasretail.Module.CashTransfer;

import android.app.AlertDialog;
import android.app.ProgressDialog;
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
import com.quickmas.salim.quickmasretail.Model.POS.CashAccountTransfer;
import com.quickmas.salim.quickmasretail.Model.POS.CashPaymentReceive;
import com.quickmas.salim.quickmasretail.Model.POS.CashStatment;
import com.quickmas.salim.quickmasretail.Model.POS.PurchaseLog;
import com.quickmas.salim.quickmasretail.Model.POS.PurchaseLogInvoice;
import com.quickmas.salim.quickmasretail.Model.POS.StockTransferDetails;
import com.quickmas.salim.quickmasretail.Model.POS.StockTransferHead;
import com.quickmas.salim.quickmasretail.Model.System.DashboardMenu;
import com.quickmas.salim.quickmasretail.Model.System.TextString;
import com.quickmas.salim.quickmasretail.Model.System.User;
import com.quickmas.salim.quickmasretail.Module.CashStatment.CashStatmentShow;
import com.quickmas.salim.quickmasretail.Module.POS.PosInvoicePrint.PosInvoicePrint;
import com.quickmas.salim.quickmasretail.Module.PaymentReceived.CashPayment;
import com.quickmas.salim.quickmasretail.Module.StockTransfer.DeliverStockTransfer.DeliverStock;
import com.quickmas.salim.quickmasretail.Module.Summary.Product.Summary;
import com.quickmas.salim.quickmasretail.R;
import com.quickmas.salim.quickmasretail.Service.UploadData;
import com.quickmas.salim.quickmasretail.Structure.GoodsTransferAPI;
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

import cn.pedant.SweetAlert.SweetAlertDialog;
import es.dmoral.toasty.Toasty;

import static com.quickmas.salim.quickmasretail.Model.System.DashboardMenu.selectByVarname;
import static com.quickmas.salim.quickmasretail.Service.ApiSettings.getUserDetailsPar;
import static com.quickmas.salim.quickmasretail.Service.ApiSettings.url_cash_transfer_delete;
import static com.quickmas.salim.quickmasretail.Service.ApiSettings.url_cash_transfer_receive;
import static com.quickmas.salim.quickmasretail.Service.ApiSettings.url_cash_transfer_upload;
import static com.quickmas.salim.quickmasretail.Service.ApiSettings.url_stock_transfer_upload;
import static com.quickmas.salim.quickmasretail.Utility.DateTimeCalculation.getDate;
import static com.quickmas.salim.quickmasretail.Utility.FontSettings.getFont;
import static com.quickmas.salim.quickmasretail.Utility.FontSettings.setTextFont;
import static com.quickmas.salim.quickmasretail.Utility.UIComponent.checkIfInternetOn;
import static com.quickmas.salim.quickmasretail.Utility.UIComponent.hideSoftKeyboard;
import static com.quickmas.salim.quickmasretail.Utility.UIComponent.setLinearLayoutBackground;
import static com.quickmas.salim.quickmasretail.Utility.UIComponent.showSoftKeyboard;
import static com.quickmas.salim.quickmasretail.Utility.UIComponent.spinnerDataLoad;

public class CashTransfer extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    DBInitialization db;
    Context mContex;
    EditText date_from;
    EditText date_to;
    boolean isStartDatePicked =false;
    String from_date_string="";
    String to_date_string="";
    User cSystemInfo;
    ProgressDialog progressDoalog_bank;
    ProgressDialog progressDoalog_cash;
    CashAccountTransfer deletingRecord = new CashAccountTransfer();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_cash_transfer);
        mContex = this;
        db = new DBInitialization(this,null,null,1);
        cSystemInfo = new User();
        cSystemInfo.select(db,"1=1");

        DashboardMenu cMenu =  selectByVarname(db,"dashboard_sell_pos_list");
        LinearLayout headimg = (LinearLayout) findViewById(R.id.image1);
        headimg = setLinearLayoutBackground(headimg, cMenu.getImage(), this);
        TextView pos_name = (TextView) findViewById(R.id.pos_name);
        pos_name.setTypeface(getFont(this));
        pos_name.setText(cSystemInfo.getSelected_pos());

        TextView balance = (TextView) findViewById(R.id.balance);
        balance.setTypeface(getFont(this));
        balance.setText(String.valueOf(cSystemInfo.getCash_cu_balance()));
        balance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContex, CashStatmentShow.class);
                mContex.startActivity(intent);
            }
        });
        final Button bank_transfer = (Button) findViewById(R.id.bank_transfer);
        final Button cash_transfer = (Button) findViewById(R.id.cash_transfer);

        from_date_string = DateTimeCalculation.getCurrentDate();
        to_date_string= DateTimeCalculation.getCurrentDate();



        //DebugHelper.print(CashAccountTransfer.select(db,"1=1"));
        bank_transfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UIComponent.hideSoftKeyboard(CashTransfer.this);
                LayoutInflater layoutInflater = (LayoutInflater) mContex
                        .getSystemService(LAYOUT_INFLATER_SERVICE);
                final View popupView = layoutInflater.inflate(R.layout.pop_up_cash_transfer_bank, null);
                final PopupWindow popupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);



                final Button make_payment = (Button) popupView.findViewById(R.id.make_payment);
                final ImageView close_tab = (ImageView) popupView.findViewById(R.id.close_tab);

                final EditText amount_given = (EditText) popupView.findViewById(R.id.amount_given);
                final EditText remarks = (EditText) popupView.findViewById(R.id.remarks);


                close_tab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });

                final Spinner banklist = (Spinner) popupView.findViewById(R.id.bank_selection);
                ArrayAdapter<String> dataAdapter = spinnerDataLoad(mContex,db,db.COLUMN_bank_name,db.TABLE_bank,"1=1","",TextString.textSelectByVarname(db,"pos_sale_payment_popbox_card_invoice_bank").getText());
                banklist.setAdapter(dataAdapter);

                make_payment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(!checkIfInternetOn(mContex)) {
                            return;
                        }
                        if(!(banklist.getSelectedItemPosition()>0 && !remarks.getText().toString().equals("") && !amount_given.getText().toString().equals("")))
                        {
                            Toasty.error(getApplicationContext(), TextString.textSelectByVarname(db,"Please fill up properly").getText(), Toast.LENGTH_SHORT, true).show();
                            return;
                        }

                        final CashAccountTransfer cashAccountTransfer = new CashAccountTransfer();
                        cashAccountTransfer.setAccount_name(banklist.getSelectedItem().toString());
                        cashAccountTransfer.setReceive_from(cSystemInfo.getSelected_pos());
                        cashAccountTransfer.setRemarks(remarks.getText().toString());
                        cashAccountTransfer.setAmount(TypeConvertion.parseDouble(amount_given.getText().toString()));
                        cashAccountTransfer.setReceive_type("Bank");
                        cashAccountTransfer.setReceive_by(cSystemInfo.getUser_name());
                        cashAccountTransfer.setReceive_date(DateTimeCalculation.getCurrentDateTime());
                        cashAccountTransfer.setStatus(0);
                        cashAccountTransfer.setPos_by(cSystemInfo.getSelected_pos());

                        {

                            new SweetAlertDialog(mContex, SweetAlertDialog.WARNING_TYPE)
                                    .setTitleText(TextString.textSelectByVarname(db,"pos_sale_payment_popbox_confermation").getText())
                                    .setContentText(TextString.textSelectByVarname(db,"pos_sale_payment_popbox_confermation").getText())
                                    .setConfirmText(TextString.textSelectByVarname(db,"pos_sale_payment_popbox_confermation_yes").getText())
                                    .setCancelText(TextString.textSelectByVarname(db,"pos_sale_payment_popbox_confermation_no").getText())
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog ssDialog) {
                                            ssDialog.dismiss();
                                            progressDoalog_bank = new ProgressDialog(mContex);
                                            try {
                                                progressDoalog_bank.setMessage("Product Creating....");
                                                progressDoalog_bank.setTitle("Please Wait");
                                                progressDoalog_bank.show();

                                            }catch (Exception e){

                                            }
                                            cashAccountTransfer.insert(db);
                                            UploadData uploadData = new UploadData();
                                            ArrayList<CashAccountTransfer> ud = new ArrayList<>();
                                            ud.add(cashAccountTransfer);
                                            uploadData.data = ud;
                                            uploadData.url=url_cash_transfer_upload + getUserDetailsPar(cSystemInfo);
                                            uploadData.uploaddata(mContex,mContex,"",mContex,"uploadData");


                                        }
                                    })
                                    .setCancelButton(TextString.textSelectByVarname(db,"pos_sale_payment_popbox_confermation_no").getText(), new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sDialog) {
                                            sDialog.dismissWithAnimation();
                                        }
                                    })
                                    .show();

                        }
                    }
                });
                popupWindow.setOutsideTouchable(true);
                popupWindow.setFocusable(true);
                popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                popupWindow.showAtLocation(bank_transfer, Gravity.CENTER, 0, 0);
                showSoftKeyboard(CashTransfer.this);
                popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        showSoftKeyboard(CashTransfer.this);
                    }
                });
            }
        });

        cash_transfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UIComponent.hideSoftKeyboard(CashTransfer.this);
                LayoutInflater layoutInflater = (LayoutInflater) mContex
                        .getSystemService(LAYOUT_INFLATER_SERVICE);
                final View popupView = layoutInflater.inflate(R.layout.pop_up_cash_transfer_bank, null);
                final PopupWindow popupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);



                final Button make_payment = (Button) popupView.findViewById(R.id.make_payment);
                final ImageView close_tab = (ImageView) popupView.findViewById(R.id.close_tab);

                final EditText amount_given = (EditText) popupView.findViewById(R.id.amount_given);
                final EditText remarks = (EditText) popupView.findViewById(R.id.remarks);


                close_tab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });

                final Spinner banklist = (Spinner) popupView.findViewById(R.id.bank_selection);
                ArrayAdapter<String> dataAdapter = spinnerDataLoad(mContex,db,db.COLUMN_cash_db_account_account_name,db.TABLE_cash_account,db.COLUMN_cash_db_account_account_name+"!='"+cSystemInfo.getSelected_pos()+"'","",TextString.textSelectByVarname(db,"pos_sale_payment_popbox_card_invoice_bank").getText());
                banklist.setAdapter(dataAdapter);

                make_payment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!checkIfInternetOn(mContex)) {
                            return;
                        }
                        if(!(banklist.getSelectedItemPosition()>0 && !remarks.getText().toString().equals("") && !amount_given.getText().toString().equals("")))
                        {
                            Toasty.error(getApplicationContext(), TextString.textSelectByVarname(db,"Please fill up properly").getText(), Toast.LENGTH_SHORT, true).show();
                            return;
                        }

                        final CashAccountTransfer cashAccountTransfer = new CashAccountTransfer();
                        cashAccountTransfer.setAccount_name(banklist.getSelectedItem().toString());
                        cashAccountTransfer.setReceive_from(cSystemInfo.getSelected_pos());
                        cashAccountTransfer.setRemarks(remarks.getText().toString());
                        cashAccountTransfer.setAmount(TypeConvertion.parseDouble(amount_given.getText().toString()));
                        cashAccountTransfer.setReceive_type("Cash");
                        cashAccountTransfer.setReceive_by(cSystemInfo.getUser_name());
                        cashAccountTransfer.setReceive_date(DateTimeCalculation.getCurrentDateTime());
                        cashAccountTransfer.setStatus(0);
                        cashAccountTransfer.setPos_by(cSystemInfo.getSelected_pos());

                        new SweetAlertDialog(mContex, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText(TextString.textSelectByVarname(db,"pos_sale_payment_popbox_confermation").getText())
                                .setContentText(TextString.textSelectByVarname(db,"pos_sale_payment_popbox_confermation").getText())
                                .setConfirmText(TextString.textSelectByVarname(db,"pos_sale_payment_popbox_confermation_yes").getText())
                                .setCancelText(TextString.textSelectByVarname(db,"pos_sale_payment_popbox_confermation_no").getText())
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog ssDialog) {
                                        ssDialog.dismiss();
                                        progressDoalog_bank = new ProgressDialog(mContex);
                                        try {
                                            progressDoalog_bank.setMessage("Product Creating....");
                                            progressDoalog_bank.setTitle("Please Wait");
                                            progressDoalog_bank.show();

                                        }catch (Exception e){

                                        }
                                        cashAccountTransfer.insert(db);
                                        UploadData uploadData = new UploadData();
                                        ArrayList<CashAccountTransfer> ud = new ArrayList<>();
                                        ud.add(cashAccountTransfer);
                                        uploadData.data = ud;
                                        uploadData.url=url_cash_transfer_upload + getUserDetailsPar(cSystemInfo);
                                        uploadData.uploaddata(mContex,mContex,"",mContex,"uploadData");


                                    }
                                })
                                .setCancelButton(TextString.textSelectByVarname(db,"pos_sale_payment_popbox_confermation_no").getText(), new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        sDialog.dismissWithAnimation();
                                    }
                                })
                                .show();


                    }
                });
                popupWindow.setOutsideTouchable(true);
                popupWindow.setFocusable(true);
                popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                popupWindow.showAtLocation(bank_transfer, Gravity.CENTER, 0, 0);
                showSoftKeyboard(CashTransfer.this);
                popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        showSoftKeyboard(CashTransfer.this);
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

    public void uploadData(ArrayList<String> data)
    {
        int newEntryId = CashAccountTransfer.getMaxId(db);
        for(String rs : data)
        {
            System.out.println(rs);
            try{
                JSONArray jr = new JSONArray(data.get(0));
                if(rs.toString().charAt(0)=='[')
                {
                    jr = new JSONArray(rs.toString());
                }
                for (int i = 0; i < jr.length(); i++) {
                    try{
                        JSONObject jb = (JSONObject) jr.getJSONObject(i);
                        try {
                            String new_po_id = jb.get("contra").toString();
                            String new_id = new_po_id.split("-")[new_po_id.split("-").length - 1];
                            CashAccountTransfer.update(db, db.COLUMN_cash_account_id + "=" + new_id, db.COLUMN_cash_account_id + "=" + newEntryId);
                        }catch (Exception e){}

                    }catch (Exception e){}
                }

            }catch (Exception e){}
        }
        hideSoftKeyboard(CashTransfer.this);
        try {
            progressDoalog_bank.dismiss();
        }catch (Exception e){}
        try{
            progressDoalog_cash.dismiss();
        }catch (Exception e){}
        SweetAlertDialog sDialog = new SweetAlertDialog(mContex, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("Successful")
                .setContentText(TextString.textSelectByVarname(db,"Transfer sucessful").getText())
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                        Toasty.success(getApplicationContext(), TextString.textSelectByVarname(db, "Cash Transfer Sucessful").getText(), Toast.LENGTH_LONG).show();
                        finish();
                    }
                });
        sDialog.setCancelable(false);
        sDialog.show();
        loadListView();
    }

    public void deleteData(ArrayList<String> data)
    {
        hideSoftKeyboard(CashTransfer.this);
        if(data.size()==1 && data.get(0).trim().equals("0"))
        {
            try {
                progressDoalog_bank.dismiss();
            }catch (Exception e){}
            try{
                progressDoalog_cash.dismiss();
            }catch (Exception e){}
            SweetAlertDialog sDialog = new SweetAlertDialog(mContex, SweetAlertDialog.SUCCESS_TYPE)
                    .setTitleText("Successful")
                    .setContentText(TextString.textSelectByVarname(db,"Delete Sucessful").getText())
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.dismissWithAnimation();
                            deletingRecord.delete(db);
                            deletingRecord = new CashAccountTransfer();
                            loadListView();
                            Toasty.success(getApplicationContext(), TextString.textSelectByVarname(db, "Delete Sucessful").getText(), Toast.LENGTH_LONG).show();
                            //finish();
                        }
                    });
            sDialog.setCancelable(false);
            sDialog.show();
        }
        else
        {
            try {
                progressDoalog_bank.dismiss();
            }catch (Exception e){}
            try{
                progressDoalog_cash.dismiss();
            }catch (Exception e){}
            SweetAlertDialog sDialog = new SweetAlertDialog(mContex, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Fail")
                    .setContentText(TextString.textSelectByVarname(db,"Transfer already approved").getText())
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.dismissWithAnimation();
                            Toasty.error(getApplicationContext(), TextString.textSelectByVarname(db, "Delete Failed").getText(), Toast.LENGTH_LONG).show();
                            //finish();
                        }
                    });
            sDialog.setCancelable(false);
            sDialog.show();
        }
        loadListView();
    }

    public void receiveData(ArrayList<String> data)
    {
        hideSoftKeyboard(CashTransfer.this);
        if(data.size()==1 && data.get(0).trim().equals("0"))
        {
            try {
                progressDoalog_bank.dismiss();
            }catch (Exception e){}
            try{
                progressDoalog_cash.dismiss();
            }catch (Exception e){}
            SweetAlertDialog sDialog = new SweetAlertDialog(mContex, SweetAlertDialog.SUCCESS_TYPE)
                    .setTitleText("Successful")
                    .setContentText(TextString.textSelectByVarname(db,"Receive Sucessful").getText())
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.dismissWithAnimation();
                            deletingRecord.setStatus(1);
                            deletingRecord.setApprove_by(cSystemInfo.getUser_name());
                            deletingRecord.update(db);
                            deletingRecord = new CashAccountTransfer();
                            loadListView();
                            Toasty.success(getApplicationContext(), TextString.textSelectByVarname(db, "Receive Sucessful").getText(), Toast.LENGTH_LONG).show();
                            //finish();
                        }
                    });
            sDialog.setCancelable(false);
            sDialog.show();
        }
        else
        {
            try {
                progressDoalog_bank.dismiss();
            }catch (Exception e){}
            try{
                progressDoalog_cash.dismiss();
            }catch (Exception e){}
            SweetAlertDialog sDialog = new SweetAlertDialog(mContex, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Fail")
                    .setContentText(TextString.textSelectByVarname(db,"Already Receive").getText())
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.dismissWithAnimation();
                            Toasty.error(getApplicationContext(), TextString.textSelectByVarname(db, "Receive Failed").getText(), Toast.LENGTH_LONG).show();
                            //finish();
                        }
                    });
            sDialog.setCancelable(false);
            sDialog.show();
        }
        loadListView();
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
                .context(CashTransfer.this)
                .callback(CashTransfer.this)
                .spinnerTheme(spinnerTheme)
                .defaultDate(year, monthOfYear, dayOfMonth)
                .build()
                .show();
    }

    void loadListView()
    {
        String con = db.COLUMN_cash_account_receive_date+" BETWEEN '"+from_date_string+" 00:00:00' AND "+"'"+to_date_string+" 23:59:59'";
        ArrayList<CashAccountTransfer> cashPaymentReceives = CashAccountTransfer.select(db,con);

        DebugHelper.print(cashPaymentReceives);
        ListView memo_list = (ListView) findViewById(R.id.memo_list);
        int totalCount = cashPaymentReceives.size();
        double totalAmount=0.0;

        for (CashAccountTransfer cashPaymentReceive : cashPaymentReceives)
        {
            totalAmount+= cashPaymentReceive.getAmount();
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

        ScrollListView.loadListView(mContex, memo_list, R.layout.pos_stock_list, cashPaymentReceives, "showScrollListView", 0, 100, true);
    }

    public void showScrollListView(ViewData data)
    {
        final CashAccountTransfer cashAccountTransfer = (CashAccountTransfer) data.object;
        String in = "Rec-" +String.valueOf(cashAccountTransfer.getId());


        if(!cashAccountTransfer.getReceive_from().equals(cSystemInfo.getUser_name()))
        {
            in += "\n("+cashAccountTransfer.getReceive_from()+")";
        }
        final TextView invoice_id = FontSettings.setTextFont((TextView) data.view.findViewById(R.id.invoice_id),mContex,in);
        final TextView date = FontSettings.setTextFont((TextView) data.view.findViewById(R.id.date),mContex,getDate(cashAccountTransfer.getReceive_date()));
        final TextView quantityT = FontSettings.setTextFont((TextView) data.view.findViewById(R.id.quantity),mContex,String.valueOf(cashAccountTransfer.getAccount_name()));
        final TextView amountT = FontSettings.setTextFont((TextView) data.view.findViewById(R.id.amount),mContex,String.format("%.2f",cashAccountTransfer.getAmount()));
        final Button action  = (Button) data.view.findViewById(R.id.action);
        final LinearLayout layout_holder = (LinearLayout) data.view.findViewById(R.id.layout_holder);

        double d = cashAccountTransfer.getAmount();

        if(cashAccountTransfer.getStatus()>0) {
            invoice_id.setTextColor(Color.parseColor("#006600"));
            quantityT.setTextColor(Color.parseColor("#006600"));
            amountT.setTextColor(Color.parseColor("#006600"));
            date.setTextColor(Color.parseColor("#006600"));
        }
        else
        {
            invoice_id.setTextColor(Color.parseColor("#ff571b"));
            quantityT.setTextColor(Color.parseColor("#ff571b"));
            amountT.setTextColor(Color.parseColor("#ff571b"));
            date.setTextColor(Color.parseColor("#ff571b"));
        }



        action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater = (LayoutInflater) mContex
                        .getSystemService(LAYOUT_INFLATER_SERVICE);
                final View popupView = layoutInflater.inflate(R.layout.memo_pop_up, null);
                final PopupWindow popupWindow = new PopupWindow(popupView,LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                Button print  = FontSettings.setTextFont((Button) popupView.findViewById(R.id.print),mContex,db,"Approve");
                Button delivery  = FontSettings.setTextFont((Button) popupView.findViewById(R.id.delivery),mContex,db,"Receive");
                Button payment  = FontSettings.setTextFont((Button) popupView.findViewById(R.id.payment),mContex,db,"memopopup_payment");
                Button make_void  = FontSettings.setTextFont((Button) popupView.findViewById(R.id.make_void),mContex,db,"memopopup_void");



                if(cashAccountTransfer.getAccount_name().equals(cSystemInfo.getSelected_pos()) && cashAccountTransfer.getStatus()==0)
                {
                    payment.setVisibility(View.GONE);
                    print.setVisibility(View.GONE);
                    make_void.setVisibility(View.GONE);
                }
                else
                {
                    delivery.setVisibility(View.GONE);
                    payment.setVisibility(View.GONE);
                    print.setVisibility(View.GONE);
                }
                if(cashAccountTransfer.getStatus()>0) {
                    make_void.setVisibility(View.GONE);
                }

                make_void.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                        new SweetAlertDialog(mContex, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText(TextString.textSelectByVarname(db,"pos_sale_payment_popbox_confermation").getText())
                                .setContentText(TextString.textSelectByVarname(db,"pos_sale_payment_popbox_confermation").getText())
                                .setConfirmText(TextString.textSelectByVarname(db,"pos_sale_payment_popbox_confermation_yes").getText())
                                .setCancelText(TextString.textSelectByVarname(db,"pos_sale_payment_popbox_confermation_no").getText())
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog ssDialog) {
                                        ssDialog.dismiss();
                                        progressDoalog_bank = new ProgressDialog(mContex);
                                        try {
                                            progressDoalog_bank.setMessage("Updating information....");
                                            progressDoalog_bank.setTitle("Please Wait");
                                            progressDoalog_bank.show();

                                        }catch (Exception e){

                                        }
                                        UploadData uploadData = new UploadData();
                                        ArrayList<CashAccountTransfer> ud = new ArrayList<>();
                                        ud.add(cashAccountTransfer);
                                        deletingRecord = cashAccountTransfer;
                                        uploadData.data = ud;
                                        uploadData.url=url_cash_transfer_delete + getUserDetailsPar(cSystemInfo);
                                        uploadData.uploaddata(mContex,mContex,"",mContex,"deleteData");


                                    }
                                })
                                .setCancelButton(TextString.textSelectByVarname(db,"pos_sale_payment_popbox_confermation_no").getText(), new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        sDialog.dismissWithAnimation();
                                    }
                                })
                                .show();
                    }
                });


                delivery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                        new SweetAlertDialog(mContex, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText(TextString.textSelectByVarname(db,"pos_sale_payment_popbox_confermation").getText())
                                .setContentText(TextString.textSelectByVarname(db,"pos_sale_payment_popbox_confermation").getText())
                                .setConfirmText(TextString.textSelectByVarname(db,"pos_sale_payment_popbox_confermation_yes").getText())
                                .setCancelText(TextString.textSelectByVarname(db,"pos_sale_payment_popbox_confermation_no").getText())
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog ssDialog) {
                                        ssDialog.dismiss();
                                        progressDoalog_bank = new ProgressDialog(mContex);
                                        try {
                                            progressDoalog_bank.setMessage("Updating information....");
                                            progressDoalog_bank.setTitle("Please Wait");
                                            progressDoalog_bank.show();

                                        }catch (Exception e){

                                        }
                                        UploadData uploadData = new UploadData();
                                        ArrayList<CashAccountTransfer> ud = new ArrayList<>();
                                        ud.add(cashAccountTransfer);
                                        deletingRecord = cashAccountTransfer;
                                        uploadData.data = ud;
                                        uploadData.url=url_cash_transfer_receive + getUserDetailsPar(cSystemInfo);
                                        uploadData.uploaddata(mContex,mContex,"",mContex,"receiveData");


                                    }
                                })
                                .setCancelButton(TextString.textSelectByVarname(db,"pos_sale_payment_popbox_confermation_no").getText(), new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        sDialog.dismissWithAnimation();
                                    }
                                })
                                .show();
                    }
                });

                print.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                        Intent i = new Intent(mContex, PosInvoicePrint.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        i.putExtra("id", String.valueOf(cashAccountTransfer.getId()));
                        //context.startActivity(i);
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
}
