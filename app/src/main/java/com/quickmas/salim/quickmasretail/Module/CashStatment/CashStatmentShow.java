package com.quickmas.salim.quickmasretail.Module.CashStatment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.quickmas.salim.quickmasretail.Database.DBInitialization;
import com.quickmas.salim.quickmasretail.Model.POS.CashAccountTransfer;
import com.quickmas.salim.quickmasretail.Model.POS.CashPaymentReceive;
import com.quickmas.salim.quickmasretail.Model.POS.CashStatment;
import com.quickmas.salim.quickmasretail.Model.POS.PosInvoiceHead;
import com.quickmas.salim.quickmasretail.Model.POS.SupplierCashPaymentReceive;
import com.quickmas.salim.quickmasretail.Model.System.DashboardMenu;
import com.quickmas.salim.quickmasretail.Model.System.TextString;
import com.quickmas.salim.quickmasretail.Model.System.User;
import com.quickmas.salim.quickmasretail.Module.CashTransfer.CashTransfer;
import com.quickmas.salim.quickmasretail.Module.POS.PosInvoicePrint.PosInvoicePrint;
import com.quickmas.salim.quickmasretail.R;
import com.quickmas.salim.quickmasretail.Service.UploadData;
import com.quickmas.salim.quickmasretail.Utility.DateTimeCalculation;
import com.quickmas.salim.quickmasretail.Utility.DebugHelper;
import com.quickmas.salim.quickmasretail.Utility.FontSettings;
import com.quickmas.salim.quickmasretail.Utility.TypeConvertion;
import com.quickmas.salim.quickmasretail.Utility.UI.ScrollListView;
import com.quickmas.salim.quickmasretail.Utility.UI.ViewData;
import com.tsongkha.spinnerdatepicker.DatePicker;
import com.tsongkha.spinnerdatepicker.DatePickerDialog;
import com.tsongkha.spinnerdatepicker.SpinnerDatePickerDialogBuilder;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.quickmas.salim.quickmasretail.Model.System.DashboardMenu.selectByVarname;
import static com.quickmas.salim.quickmasretail.Service.ApiSettings.getUserDetailsPar;
import static com.quickmas.salim.quickmasretail.Service.ApiSettings.url_cash_transfer_delete;
import static com.quickmas.salim.quickmasretail.Service.ApiSettings.url_cash_transfer_receive;
import static com.quickmas.salim.quickmasretail.Utility.DateTimeCalculation.getDate;
import static com.quickmas.salim.quickmasretail.Utility.FontSettings.getFont;
import static com.quickmas.salim.quickmasretail.Utility.FontSettings.setTextFont;
import static com.quickmas.salim.quickmasretail.Utility.UIComponent.setLinearLayoutBackground;

public class CashStatmentShow extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_cash_statment_show2);


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
        final Button show_report = FontSettings.setTextFont((Button) findViewById(R.id.date_selection),this,"Date Range");

        show_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateSelection(show_report);
            }
        });
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
                .context(CashStatmentShow.this)
                .callback(CashStatmentShow.this)
                .spinnerTheme(spinnerTheme)
                .defaultDate(year, monthOfYear, dayOfMonth)
                .build()
                .show();
    }

    void loadListView()
    {
        String con = db.COLUMN_cash_statmen_entry_time+" BETWEEN '"+from_date_string+" 00:00:00' AND "+"'"+to_date_string+" 23:59:59'";
        ArrayList<CashStatment> cashPaymentReceives = CashStatment.select(db,con);
        //if(cashPaymentReceives.size()>0.0)
        {
            con = db.COLUMN_supplier_cash_payemnt_date_time+" BETWEEN '"+from_date_string+" 00:00:00' AND "+"'"+to_date_string+" 23:59:59'";
            Double cu_amount =0.0;
            try {
                cu_amount = cashPaymentReceives.get(cashPaymentReceives.size() - 1).cash_cu_balance;
            }catch (Exception e){}

            ArrayList<SupplierCashPaymentReceive> scr = SupplierCashPaymentReceive.select(db, db.COLUMN_supplier_cash_payemnt_if_synced + "=0 AND "+con);
            for (SupplierCashPaymentReceive s : scr) {
                CashStatment c = new CashStatment();
                c.entry_time = s.date_time;
                c.voucher = "PAY-" + s.id;
                c.inv_coa = "Cash Paid";
                c.credit_amount = s.getCa_amount() + s.getCash_amount() + s.getCard_amount();
                cu_amount-=c.credit_amount;
                c.debit_amount = 0.0;
                c.cash_cu_balance = cu_amount;
                c.status = 9;
                cashPaymentReceives.add(c);
            }
            con = db.COLUMN_cash_payemnt_date_time+" BETWEEN '"+from_date_string+" 00:00:00' AND "+"'"+to_date_string+" 23:59:59'";

            ArrayList<CashPaymentReceive> cr = CashPaymentReceive.select(db, db.COLUMN_cash_payemnt_data_synced + "=0 AND "+con);
            for (CashPaymentReceive s : cr)
            {
                CashStatment c = new CashStatment();
                c.entry_time = s.date_time;
                c.voucher =  "REC-" + s.id;
                c.inv_coa = "Cash Received";
                c.debit_amount = s.getCa_amount() + s.getCash_amount() + s.getCard_amount();
                c.credit_amount = 0.0;
                cu_amount+=c.debit_amount;
                c.cash_cu_balance = cu_amount;
                c.status = 9;
                cashPaymentReceives.add(c);
            }

            con = db.COLUMN_pos_invoice_head_invoice_date+" BETWEEN '"+from_date_string+" 00:00:00' AND "+"'"+to_date_string+" 23:59:59'";
            ArrayList<PosInvoiceHead> inv = PosInvoiceHead.select(db, db.COLUMN_pos_invoice_head_if_synced + "=0 AND "+con);
            for (PosInvoiceHead s : inv)
            {
                CashStatment c = new CashStatment();
                c.entry_time = s.invoice_date;
                c.voucher =  cSystemInfo.getSelected_pos()+"-P-" + s.id;
                c.inv_coa = "Cash Received for Sale";
                c.debit_amount = s.getTotal_amount();
                c.credit_amount = 0.0;
                cu_amount+=c.debit_amount;
                c.cash_cu_balance = cu_amount;
                c.status = 9;
                cashPaymentReceives.add(c);
            }

            ListView memo_list = (ListView) findViewById(R.id.memo_list);
            ScrollListView.loadListView(mContex, memo_list, R.layout.cash_statment_row, cashPaymentReceives, "showScrollListView", 0, 100, true);
        }
    }

    public void showScrollListView(ViewData data)
    {
       final CashStatment cashStatmentShow = (CashStatment) data.object;
       if(cashStatmentShow.getDebit_amount()>0.0)
       {
           TextView debit = FontSettings.setTextFont((TextView) data.view.findViewById(R.id.debit),data.context,String.valueOf(cashStatmentShow.getDebit_amount()));
           if(cashStatmentShow.status==9) {debit.setTextColor(Color.MAGENTA);}
           else{debit.setTextColor(Color.BLACK);}
       }
       else
       {
           TextView debit = FontSettings.setTextFont((TextView) data.view.findViewById(R.id.debit),data.context,"-");
           if(cashStatmentShow.status==9) {debit.setTextColor(Color.MAGENTA);}
           else{debit.setTextColor(Color.BLACK);}
       }

        if(cashStatmentShow.getCredit_amount()>0.0)
        {
            TextView credit = FontSettings.setTextFont((TextView) data.view.findViewById(R.id.credit),data.context,String.valueOf(cashStatmentShow.getCredit_amount()));
            if(cashStatmentShow.status==9) {credit.setTextColor(Color.MAGENTA);}
            else{credit.setTextColor(Color.BLACK);}
        }
        else
        {
            TextView credit = FontSettings.setTextFont((TextView) data.view.findViewById(R.id.credit),data.context,"-");
            if(cashStatmentShow.status==9) {credit.setTextColor(Color.MAGENTA);}
            else{credit.setTextColor(Color.BLACK);}
        }
        //final String amount = String.format("%.2f", d);
        TextView entry_time = FontSettings.setTextFont((TextView) data.view.findViewById(R.id.entry_time),data.context,cashStatmentShow.getEntry_time());
        TextView voucher = FontSettings.setTextFont((TextView) data.view.findViewById(R.id.voucher),data.context,cashStatmentShow.getVoucher());
        TextView inv_co = FontSettings.setTextFont((TextView) data.view.findViewById(R.id.inv_co),data.context,cashStatmentShow.getInv_coa());
        TextView cu_amount = FontSettings.setTextFont((TextView) data.view.findViewById(R.id.cu_amount),data.context,String.valueOf(cashStatmentShow.getCash_cu_balance()));
        if(cashStatmentShow.status==9) {
            entry_time.setTextColor(Color.MAGENTA);
            voucher.setTextColor(Color.MAGENTA);
            inv_co.setTextColor(Color.MAGENTA);
            cu_amount.setTextColor(Color.MAGENTA);
        }
        else {
            entry_time.setTextColor(Color.BLACK);
            voucher.setTextColor(Color.BLACK);
            inv_co.setTextColor(Color.BLACK);
            cu_amount.setTextColor(Color.BLACK);
        }
    }
}
