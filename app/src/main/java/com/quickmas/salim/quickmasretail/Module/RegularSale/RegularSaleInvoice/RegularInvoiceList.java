package com.quickmas.salim.quickmasretail.Module.RegularSale.RegularSaleInvoice;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.quickmas.salim.quickmasretail.Model.POS.PosInvoiceHead;
import com.quickmas.salim.quickmasretail.Model.POS.PosInvoiceHeadRegular;
import com.quickmas.salim.quickmasretail.Model.POS.PosInvoiceHeadRegularWeb;
import com.quickmas.salim.quickmasretail.Model.POS.PosInvoiceHeadWeb;
import com.quickmas.salim.quickmasretail.Model.POS.PosInvoiceRegularWeb;
import com.quickmas.salim.quickmasretail.Model.POS.PosPaymentReceived;
import com.quickmas.salim.quickmasretail.Model.System.DashboardMenu;
import com.quickmas.salim.quickmasretail.Model.System.TextString;
import com.quickmas.salim.quickmasretail.Model.System.User;
import com.quickmas.salim.quickmasretail.Module.POS.POSInvoiceList.InvoicePayment;
import com.quickmas.salim.quickmasretail.Module.POS.POSInvoiceList.POSInvoiceList;
import com.quickmas.salim.quickmasretail.Module.POS.PosInvoicePrint.PosInvoicePrint;
import com.quickmas.salim.quickmasretail.Module.RegularSale.RegularSaleCreate.RegularCreateInvoice;
import com.quickmas.salim.quickmasretail.R;
import com.quickmas.salim.quickmasretail.Structure.PosInvoiceHeadCombian;
import com.quickmas.salim.quickmasretail.Structure.PosInvoiceHeadCombianRegular;
import com.quickmas.salim.quickmasretail.Utility.DateTimeCalculation;
import com.quickmas.salim.quickmasretail.Utility.DebugHelper;
import com.quickmas.salim.quickmasretail.Utility.FontSettings;
import com.quickmas.salim.quickmasretail.Utility.UI.ScrollListView;
import com.quickmas.salim.quickmasretail.Utility.UI.ViewData;
import com.tsongkha.spinnerdatepicker.DatePicker;
import com.tsongkha.spinnerdatepicker.DatePickerDialog;
import com.tsongkha.spinnerdatepicker.SpinnerDatePickerDialogBuilder;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_head_customer;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_head_invoice_id;
import static com.quickmas.salim.quickmasretail.Model.System.DashboardMenu.selectByVarname;
import static com.quickmas.salim.quickmasretail.Utility.DateTimeCalculation.getDate;
import static com.quickmas.salim.quickmasretail.Utility.FontSettings.getFont;
import static com.quickmas.salim.quickmasretail.Utility.FontSettings.setTextFont;
import static com.quickmas.salim.quickmasretail.Utility.UIComponent.setLinearLayoutBackground;

public class RegularInvoiceList extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    DBInitialization db;
    ListView memo_list;
    Context context;
    User cSystem = new User();
    EditText date_from;
    EditText date_to;
    String from_date_string="";
    String to_date_string="";
    boolean isStartDatePicked =false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_regular_invoice_list);
        context=this;
        db = new DBInitialization(this,null,null,1);
        cSystem.select(db,"1=1");
        DashboardMenu cMenu =  selectByVarname(db,"dashboard_sell_pos_list");
        LinearLayout headimg = (LinearLayout) findViewById(R.id.image1);
        headimg = setLinearLayoutBackground(headimg, cMenu.getImage(), this);
        TextView headtext = (TextView) findViewById(R.id.text1);
        headtext.setTypeface(getFont(this));
        headtext.setText("Sales List");
        final TextView invoice_id = setTextFont((TextView) findViewById(R.id.invoice_id),this,db,"memo_invoice_id");
        final TextView quantity = setTextFont((TextView) findViewById(R.id.quantity),this,db,"memo_quantity");
        final TextView amount = setTextFont((TextView) findViewById(R.id.amount),this,db,"memo_amount");
        final TextView action = setTextFont((TextView) findViewById(R.id.action),this,db,"memo_action");

        final Button memo_print = setTextFont((Button) findViewById(R.id.memo_print),this,db,"memopopup_print");
        final Button sell_invoice_home = setTextFont((Button) findViewById(R.id.sell_invoice_home),this,db,"datadownload_backToHome");
        from_date_string = DateTimeCalculation.getCurrentDate();
        to_date_string= DateTimeCalculation.getCurrentDate();
        final Button show_report = FontSettings.setTextFont((Button) findViewById(R.id.date_selection),this,"Date Range");
        final Button create_new = (Button) findViewById(R.id.create_new);
                show_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateSelection(show_report);

            }
        });
        EditText search_box = setTextFont((EditText) findViewById(R.id.search_box),this,"");
        /*Button search_btn = setTextFont((Button) findViewById(R.id.search_btn),this,"Search");*/
        create_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegularInvoiceList.this, RegularCreateInvoice.class);
                startActivity(intent);
            }
        });

        search_box.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateListview();
            }
        });

        /*search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateListview();
            }
        });*/
        updateListview();
    }
    @Override
    protected void onResume() {
        super.onResume();

    }
    void dateSelection(Button btn)
    {
        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        final View popupView = layoutInflater.inflate(R.layout.date_selection_pop_up, null);
        final PopupWindow popupWindow = new PopupWindow(popupView,LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        Button date_search  = FontSettings.setTextFont((Button) popupView.findViewById(R.id.date_search),context,"Submit");
        date_from  = FontSettings.setTextFont((EditText) popupView.findViewById(R.id.date_from),context,"");
        date_to  = FontSettings.setTextFont((EditText) popupView.findViewById(R.id.date_to),context,"");



        date_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                from_date_string = date_from.getText().toString();
                to_date_string= date_to.getText().toString();
                updateListview();
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
    void updateListview()
    {
        memo_list = (ListView) findViewById(R.id.memo_list);
        String txt = ((EditText) findViewById(R.id.search_box)).getText().toString();
        ArrayList<PosInvoiceHeadRegularWeb> invoices = new ArrayList<>();
        if(txt.equals(""))
        {
            String con = db.COLUMN_pos_invoice_head_invoice_date+" BETWEEN '"+from_date_string+" 00:00:00' AND "+"'"+to_date_string+" 23:59:59'";
            invoices = PosInvoiceHeadRegularWeb.select(db,con);
        }
        else
        {
            String con = COLUMN_pos_invoice_head_invoice_id+" LIKE "+'"'+"%"+txt+"%"+'"'+" OR "+COLUMN_pos_invoice_head_customer+" LIKE "+'"'+"%"+txt+"%"+'"'+" AND "+db.COLUMN_pos_invoice_head_invoice_date+" BETWEEN '"+from_date_string+" 00:00:00' AND "+"'"+to_date_string+" 23:59:59'";
            invoices = PosInvoiceHeadRegularWeb.select(db,con);
        }
        int totalCount = invoices.size();
        int totalQuantity=0;
        double totalAmount=0.0;
        for (PosInvoiceHeadRegularWeb memo : invoices)
        {
            totalQuantity+= memo.getTotal_quantity();
            totalAmount+= (memo.getTotal_amount());
        }

        LayoutInflater layoutinflater = getLayoutInflater();
        ViewGroup footer = (ViewGroup)layoutinflater.inflate(R.layout.pos_stock_list_footer,memo_list,false);

        String t = TextString.textSelectByVarname(db,"memo_total_count").getText();

        final TextView invoice_id_total = setTextFont((TextView) footer.findViewById(R.id.invoice_id_total),this,String.valueOf(totalCount));
        final TextView invoice_id_date = setTextFont((TextView) footer.findViewById(R.id.invoice_id_date),this,t);
        final TextView quantity_total = setTextFont((TextView) footer.findViewById(R.id.quantity_total),this,String.valueOf(totalQuantity));
        final TextView amount_total = setTextFont((TextView) footer.findViewById(R.id.amount_total),this,String.format("%.2f", totalAmount));


        ArrayList<PosInvoiceHeadRegular> posInvoiceHeadWebs = new ArrayList<>();
        if(txt.equals(""))
        {
            String con = db.COLUMN_pos_invoice_head_invoice_date_web+" BETWEEN '"+from_date_string+" 00:00:00' AND "+"'"+to_date_string+" 23:59:59'";
            posInvoiceHeadWebs = PosInvoiceHeadRegular.select(db,con);
        }
        else
        {
            String con = db.COLUMN_pos_invoice_head_invoice_id_web+" LIKE "+'"'+"%"+txt+"%"+'"'+" OR "+db.COLUMN_pos_invoice_head_customer_web+" LIKE "+'"'+"%"+txt+"%"+'"'+" AND "+db.COLUMN_pos_invoice_head_invoice_date_web+" BETWEEN '"+from_date_string+" 00:00:00' AND "+"'"+to_date_string+" 23:59:59'";
            posInvoiceHeadWebs = PosInvoiceHeadRegular.select(db,con);
        }
        ScrollListView.loadListView(context,memo_list,R.layout.pos_invoice_list_iteam, PosInvoiceHeadCombianRegular.getPosInvoiceCombian(posInvoiceHeadWebs,invoices),"invoiceList",0,200,true);

    }

    public void invoiceList(ViewData data)
    {
        final PosInvoiceHeadCombianRegular posInvoiceHead = (PosInvoiceHeadCombianRegular) data.object;
        TextView invoice_id  = (TextView) data.view.findViewById(R.id.invoice_id);
        TextView date  = (TextView) data.view.findViewById(R.id.date);
        TextView quantity  = (TextView) data.view.findViewById(R.id.quantity);
        TextView amount  = (TextView) data.view.findViewById(R.id.amount);
        TextView if_synced  = (TextView) data.view.findViewById(R.id.if_synced);
        Button action  = (Button) data.view.findViewById(R.id.action);
        LinearLayout layout_holder = (LinearLayout) data.view.findViewById(R.id.layout_holder);

        Typeface tf = FontSettings.getFont(context);

        invoice_id.setTypeface(tf);
        quantity.setTypeface(tf);
        amount.setTypeface(tf);
        date.setTypeface(tf);


        final String in = cSystem.getSelected_pos()+"-"+posInvoiceHead.category+"-" +String.valueOf(posInvoiceHead.getInvoice_id());
        String quantity1 = String.valueOf(posInvoiceHead.getTotal_quantity());
        final String amount1 = String.format("%.2f", posInvoiceHead.getTotal_amount());

        invoice_id.setText(in);
        date.setText(getDate(posInvoiceHead.getInvoice_date()));
        quantity.setText(String.valueOf(quantity1));
        amount.setText(String.valueOf(amount1));
        TextView mText =  invoice_id;
        action = FontSettings.setTextFont(action,context,db,"memo_action");
        final Button btn = action;
        final LinearLayout ly = layout_holder;

        db = new DBInitialization(context,null,null,1);
        if(posInvoiceHead.getTotal_amount() - posInvoiceHead.getTotal_paid_amount()==0.0)
        //if(allProduct.get(position).getPay_later_amount()- PosPaymentReceived.sumReceivedAmount(db, allProduct.get(position).getId())==0.0)
        {
            invoice_id.setTextColor(Color.parseColor("#006600"));
            quantity.setTextColor(Color.parseColor("#006600"));
            amount.setTextColor(Color.parseColor("#006600"));
            date.setTextColor(Color.parseColor("#006600"));
        }
        else
        {
            invoice_id.setTextColor(Color.parseColor("#ff571b"));
            quantity.setTextColor(Color.parseColor("#ff571b"));
            amount.setTextColor(Color.parseColor("#ff571b"));
            date.setTextColor(Color.parseColor("#ff571b"));
        }

        //if(posInvoiceHead.if_synced==0 && posInvoiceHead.category.toUpperCase().equals("P"))
        {
            //if_synced.setVisibility(View.VISIBLE);
        }
        //else {
            if_synced.setVisibility(View.GONE);
       // }
        action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater = (LayoutInflater) context
                        .getSystemService(LAYOUT_INFLATER_SERVICE);
                final View popupView = layoutInflater.inflate(R.layout.memo_pop_up, null);
                final PopupWindow popupWindow = new PopupWindow(popupView,LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                Button print  = FontSettings.setTextFont((Button) popupView.findViewById(R.id.print),context,db,"memopopup_print");
                Button delivery  = FontSettings.setTextFont((Button) popupView.findViewById(R.id.delivery),context,db,"memopopup_devilery");
                Button payment  = FontSettings.setTextFont((Button) popupView.findViewById(R.id.payment),context,db,"memopopup_payment");
                Button make_void  = FontSettings.setTextFont((Button) popupView.findViewById(R.id.make_void),context,db,"memopopup_void");

                delivery.setVisibility(View.GONE);
                make_void.setVisibility(View.GONE);
                ArrayList<PosPaymentReceived> ps = PosPaymentReceived.select(db,"1=1");

                if(posInvoiceHead.getTotal_amount() - posInvoiceHead.getTotal_paid_amount()==0.0)
                {
                    payment.setVisibility(View.GONE);
                }

                payment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                        Intent i = new Intent(context, RegularInvoicePayment.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        i.putExtra("id", String.valueOf(posInvoiceHead.getId()));
                        i.putExtra("category", String.valueOf(posInvoiceHead.category));
                        context.startActivity(i);
                    }
                });

                print.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                        Intent i = new Intent(context, RegularInvoicePrint.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        i.putExtra("id", String.valueOf(posInvoiceHead.getId()));
                        i.putExtra("category", String.valueOf(posInvoiceHead.category));
                        context.startActivity(i);
                    }
                });

                popupWindow.dismiss();
                popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        ly.setBackgroundColor(Color.parseColor("#e2e2e2"));
                    }
                });
                // Closes the popup window when touch outside.
                popupWindow.setOutsideTouchable(true);
                popupWindow.setFocusable(true);
                // Removes default background.
                popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                popupWindow.showAsDropDown(btn, 0, 0);
                ly.setBackgroundColor(Color.parseColor("#72a8ff"));
            }
        });
    }
    @VisibleForTesting
    void showDate(int year, int monthOfYear, int dayOfMonth, int spinnerTheme) {
        new SpinnerDatePickerDialogBuilder()
                .context(RegularInvoiceList.this)
                .callback(RegularInvoiceList.this)
                .spinnerTheme(spinnerTheme)
                .defaultDate(year, monthOfYear, dayOfMonth)
                .build()
                .show();
    }
}
