package com.quickmas.salim.quickmasretail.Module.POS.PosSummary;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.quickmas.salim.quickmasretail.Database.DBInitialization;
import com.quickmas.salim.quickmasretail.MainActivity;
import com.quickmas.salim.quickmasretail.Model.POS.PosInvoice;
import com.quickmas.salim.quickmasretail.Model.POS.PosInvoiceHead;
import com.quickmas.salim.quickmasretail.Model.System.DashboardMenu;
import com.quickmas.salim.quickmasretail.Module.Summary.Product.Summary;
import com.quickmas.salim.quickmasretail.R;
import com.quickmas.salim.quickmasretail.Utility.DateTimeCalculation;
import com.quickmas.salim.quickmasretail.Utility.FontSettings;
import com.tsongkha.spinnerdatepicker.DatePicker;
import com.tsongkha.spinnerdatepicker.DatePickerDialog;
import com.tsongkha.spinnerdatepicker.SpinnerDatePickerDialogBuilder;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static com.quickmas.salim.quickmasretail.Model.System.DashboardMenu.selectByVarname;
import static com.quickmas.salim.quickmasretail.Utility.FontSettings.getFont;
import static com.quickmas.salim.quickmasretail.Utility.UIComponent.setLinearLayoutBackground;

public class PosSummary extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    EditText date_from;
    EditText date_to;
    boolean isStartDatePicked =false;
    Context mContex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_pos_summary);
        final DBInitialization db = new DBInitialization(this,null,null,1);
        DashboardMenu cMenu =  selectByVarname(db,"dashboard_summary_retail");
        LinearLayout headimg = (LinearLayout) findViewById(R.id.image1);
        headimg = setLinearLayoutBackground(headimg, cMenu.getImage(), this);
        TextView headtext = (TextView) findViewById(R.id.text1);
        headtext.setTypeface(getFont(this));
        headtext.setText(cMenu.getText());


        mContex = this;



        final Button show_report = FontSettings.setTextFont((Button) findViewById(R.id.show_report),this,"Show");


        date_from = (EditText) findViewById(R.id.date_from);
        date_to = (EditText) findViewById(R.id.date_to);
        date_to.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    isStartDatePicked = true;
                    showDate(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DATE), R.style.DatePickerSpinner);
                }
            }
        });

        date_from.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    isStartDatePicked = false;
                    showDate(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DATE), R.style.DatePickerSpinner);
                }
            }
        });




        final Button sell_invoice_home = FontSettings.setTextFont((Button) findViewById(R.id.sell_invoice_home),this,db,"dataUpload_backToHome");
        final Button sell_invoice_print = FontSettings.setTextFont((Button) findViewById(R.id.summary_print),this,db,"memopopup_print");
        sell_invoice_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PosSummary.this,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        show_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String con = db.COLUMN_pos_invoice_head_invoice_date+" BETWEEN '"+date_from.getText()+" 00:00:00' AND "+"'"+date_to.getText()+" 23:59:59'";
                System.out.println(con);
                ArrayList<PosInvoiceHead> posInvoiceHeads = PosInvoiceHead.select(db,con);

                double totalAmount=0.0;
                double totalCashAmount=0.0;
                double totalCardAmount=0.0;
                double totalCreditAmount=0.0;
                double totalTax=0.0;
                double totalIteamDiscount=0.0;
                double totalAdditionaDiscount=0.0;


                for(PosInvoiceHead posInvoice : posInvoiceHeads)
                {
                    totalCashAmount+=posInvoice.getCash_amount();
                    totalCardAmount+=posInvoice.getCard_amount();
                    totalCreditAmount+=posInvoice.getPay_later_amount();
                    totalTax+=posInvoice.getTotal_tax();
                    totalIteamDiscount+=posInvoice.getIteam_discount();
                    totalAdditionaDiscount+=posInvoice.getAdditional_discount();
                }
                totalAmount = totalCashAmount+totalCardAmount+totalCreditAmount;
                TextView invoice_no_txt = FontSettings.setTextFont((TextView) findViewById(R.id.invoice_no_txt),mContex,String.valueOf(posInvoiceHeads.size()));
                TextView cash_paid_txt = FontSettings.setTextFont((TextView) findViewById(R.id.cash_paid_txt),mContex,String.valueOf(totalCashAmount));
                TextView card_paid_txt = FontSettings.setTextFont((TextView) findViewById(R.id.card_paid_txt),mContex,String.valueOf(totalCardAmount));
                TextView pay_later_txt = FontSettings.setTextFont((TextView) findViewById(R.id.pay_later_txt),mContex,String.valueOf(totalCreditAmount));
                TextView total_txt = FontSettings.setTextFont((TextView) findViewById(R.id.total_txt),mContex,String.valueOf(totalAmount));

                TextView total_tax_txt = FontSettings.setTextFont((TextView) findViewById(R.id.total_tax_txt),mContex,String.valueOf(totalTax));
                TextView iteam_discount_txt = FontSettings.setTextFont((TextView) findViewById(R.id.iteam_discount_txt),mContex,String.valueOf(totalIteamDiscount));
                TextView total_additional_discount_txt = FontSettings.setTextFont((TextView) findViewById(R.id.total_additional_discount_txt),mContex,String.valueOf(totalAdditionaDiscount));


            }
        });
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
                .context(PosSummary.this)
                .callback(PosSummary.this)
                .spinnerTheme(spinnerTheme)
                .defaultDate(year, monthOfYear, dayOfMonth)
                .build()
                .show();
    }


}

