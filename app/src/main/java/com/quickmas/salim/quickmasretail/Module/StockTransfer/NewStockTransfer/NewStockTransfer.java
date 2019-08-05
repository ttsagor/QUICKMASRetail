package com.quickmas.salim.quickmasretail.Module.StockTransfer.NewStockTransfer;

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
import com.quickmas.salim.quickmasretail.Model.POS.PosProduct;
import com.quickmas.salim.quickmasretail.Model.POS.StockTransferDetails;
import com.quickmas.salim.quickmasretail.Model.POS.StockTransferHead;
import com.quickmas.salim.quickmasretail.Model.System.DashboardMenu;
import com.quickmas.salim.quickmasretail.Model.System.TextString;
import com.quickmas.salim.quickmasretail.Model.System.User;
import com.quickmas.salim.quickmasretail.Module.POS.PosInvoicePrint.PosInvoicePrint;
import com.quickmas.salim.quickmasretail.Module.StockTransfer.DeliverStockTransfer.DeliverStock;
import com.quickmas.salim.quickmasretail.Module.StockTransfer.RecevieStcokTransfer.ReceiveStock;
import com.quickmas.salim.quickmasretail.R;
import com.quickmas.salim.quickmasretail.Utility.DateTimeCalculation;
import com.quickmas.salim.quickmasretail.Utility.DebugHelper;
import com.quickmas.salim.quickmasretail.Utility.FontSettings;
import com.quickmas.salim.quickmasretail.Utility.UI.ScrollListView;
import com.quickmas.salim.quickmasretail.Utility.UI.ViewData;
import com.quickmas.salim.quickmasretail.Utility.UIComponent;
import com.tsongkha.spinnerdatepicker.DatePicker;
import com.tsongkha.spinnerdatepicker.DatePickerDialog;
import com.tsongkha.spinnerdatepicker.SpinnerDatePickerDialogBuilder;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import static com.quickmas.salim.quickmasretail.Model.System.DashboardMenu.selectByVarname;
import static com.quickmas.salim.quickmasretail.Utility.DateTimeCalculation.getDate;
import static com.quickmas.salim.quickmasretail.Utility.FontSettings.getFont;
import static com.quickmas.salim.quickmasretail.Utility.FontSettings.setTextFont;
import static com.quickmas.salim.quickmasretail.Utility.UIComponent.setLinearLayoutBackground;

public class NewStockTransfer extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    DBInitialization db;
    Context mContex;
    EditText date_from;
    EditText date_to;
    boolean isStartDatePicked =false;
    String from_date_string="";
    String to_date_string="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_new_stock_transfer);

        mContex = this;
        db = new DBInitialization(this,null,null,1);
        DashboardMenu cMenu =  selectByVarname(db,"dashboard_sell_pos_list");
        LinearLayout headimg = (LinearLayout) findViewById(R.id.image1);
        headimg = setLinearLayoutBackground(headimg, cMenu.getImage(), this);
        TextView headtext = (TextView) findViewById(R.id.text1);
        headtext.setTypeface(getFont(this));
        headtext.setText("Stock Transfer");

        from_date_string = DateTimeCalculation.getCurrentDate();
        to_date_string= DateTimeCalculation.getCurrentDate();

        final Button new_transfer = (Button) findViewById(R.id.new_transfer);
        final Button show_report = (Button) findViewById(R.id.date_selection);
        final ListView memo_list = (ListView) findViewById(R.id.memo_list);

        //DebugHelper.print(StockTransferHead.select(db,"1=1"));
       // DebugHelper.print(StockTransferDetails.select(db,"1=1"));

        new_transfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContex, CreateNewTransfer.class);
                startActivity(intent);
                UIComponent.hideSoftKeyboard(NewStockTransfer.this);
            }
        });

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
    protected void onResume() {
        super.onResume();
        try{
            //loadListView();
        }catch (Exception ex){}
    }

    void loadListView()
    {
        String con = db.COLUMN_cash_payemnt_date_time+" BETWEEN '"+from_date_string+" 00:00:00' AND "+"'"+to_date_string+" 23:59:59'";
        ArrayList<StockTransferHead> cashPaymentReceives = StockTransferHead.select(db,con);


        ListView memo_list = (ListView) findViewById(R.id.memo_list);
        int totalCount = 0;
        double totalAmount=0.0;

        for (StockTransferHead cashPaymentReceive : cashPaymentReceives)
        {
            totalCount+=cashPaymentReceive.getTotal_quantity();
            totalAmount+= cashPaymentReceive.getTotal_amount();
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
        final TextView invoice_id_total_head = setTextFont((TextView) header.findViewById(R.id.invoice_id_total),this,"Trns No");
        final TextView invoice_id_date_head = setTextFont((TextView) header.findViewById(R.id.invoice_id_date),this,"Date");
        final TextView quantity_total_head = setTextFont((TextView) header.findViewById(R.id.quantity_total),this,"Quty");
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

        if(cashPaymentReceives.size()>0)
        {
            ScrollListView.loadListView(mContex, memo_list, R.layout.pos_stock_list, cashPaymentReceives, "showList", 0, 100, true);
        }
    }

    public void showList(final ViewData data)
    {
        final StockTransferHead stockTransferHead = (StockTransferHead) data.object;

        final String in = "TRA-" +String.valueOf(stockTransferHead.getId());

        final TextView invoice_id = FontSettings.setTextFont((TextView) data.view.findViewById(R.id.invoice_id),mContex,in);
        final TextView date = FontSettings.setTextFont((TextView) data.view.findViewById(R.id.date),mContex,getDate(stockTransferHead.getDate_time()));
        final TextView quantityT = FontSettings.setTextFont((TextView) data.view.findViewById(R.id.quantity),mContex,(String.valueOf(stockTransferHead.getTotal_quantity())));
        final TextView amountT = FontSettings.setTextFont((TextView) data.view.findViewById(R.id.amount),mContex,String.valueOf(String.format("%.2f", stockTransferHead.getTotal_amount())));
        final Button action = FontSettings.setTextFont((Button) data.view.findViewById(R.id.action),mContex,"Action");
        final LinearLayout layout_holder = (LinearLayout) data.view.findViewById(R.id.layout_holder);

        if(stockTransferHead.getStatus()==0){
            invoice_id.setTextColor(Color.parseColor("#ff7044"));
            quantityT.setTextColor(Color.parseColor("#ff7044"));
            amountT.setTextColor(Color.parseColor("#ff7044"));
            date.setTextColor(Color.parseColor("#ff7044"));
        }
        else if(stockTransferHead.getStatus()==1)
        {
            invoice_id.setTextColor(Color.parseColor("#006600"));
            quantityT.setTextColor(Color.parseColor("#006600"));
            amountT.setTextColor(Color.parseColor("#006600"));
            date.setTextColor(Color.parseColor("#006600"));
        }
        else
        {
           invoice_id.setTextColor(Color.parseColor("#000000"));
           quantityT.setTextColor(Color.parseColor("#000000"));
           amountT.setTextColor(Color.parseColor("#000000"));
           date.setTextColor(Color.parseColor("#000000"));
        }

        action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater = (LayoutInflater) mContex
                        .getSystemService(LAYOUT_INFLATER_SERVICE);
                final View popupView = layoutInflater.inflate(R.layout.stock_transfer_pop_up, null);
                final PopupWindow popupWindow = new PopupWindow(popupView,LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                Button print  = FontSettings.setTextFont((Button) popupView.findViewById(R.id.print),mContex,db,"memopopup_print");
                Button delivery  = FontSettings.setTextFont((Button) popupView.findViewById(R.id.delivery),mContex,db,"memopopup_devilery");
                Button payment  = FontSettings.setTextFont((Button) popupView.findViewById(R.id.payment),mContex,db,"Edit");
                Button make_void  = FontSettings.setTextFont((Button) popupView.findViewById(R.id.make_void),mContex,db,"memopopup_void");
                Button receive  = FontSettings.setTextFont((Button) popupView.findViewById(R.id.receive),mContex,db,"Receive");

                User cSystemInfo = new User();
                cSystemInfo = cSystemInfo.select(db,"1=1");
                if(stockTransferHead.getPos_by().equals(cSystemInfo.getSelected_pos()))
                {
                    receive.setVisibility(View.GONE);
                    if(stockTransferHead.getStatus()!=0)
                    {
                        delivery.setVisibility(View.GONE);
                        make_void.setVisibility(View.GONE);
                        payment.setVisibility(View.GONE);

                    }
                }
                else
                {
                    delivery.setVisibility(View.GONE);
                    make_void.setVisibility(View.GONE);
                    payment.setVisibility(View.GONE);
                }
                receive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                        Intent i = new Intent(mContex, ReceiveStock.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        i.putExtra("id", String.valueOf(stockTransferHead.getId()));
                        startActivity(i);
                    }
                });


                delivery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        /*
                        ArrayList<StockTransferDetails> transferDetails = StockTransferDetails.select(db,db.COLUMN_stock_transfer_details_transfer_id+"="+stockTransferHead.getId());

                        for(StockTransferDetails details : transferDetails)
                        {
                            details.setStatus(1);
                            details.update(db);
                        }
                        stockTransferHead.setStatus(1);
                        stockTransferHead.update(db);
                        loadListView();
                        popupWindow.dismiss();*/

                        popupWindow.dismiss();
                        Intent i = new Intent(mContex, DeliverStock.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        i.putExtra("id", String.valueOf(stockTransferHead.getId()));
                        startActivity(i);

                    }
                });

                make_void.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        ArrayList<StockTransferDetails> stockTransferDetails = StockTransferDetails.select(db,db.COLUMN_stock_transfer_details_transfer_id+"="+stockTransferHead.getId());
                        for(StockTransferDetails details : stockTransferDetails)
                        {
                            PosProduct posProduct = PosProduct.select(db,db.COLUMN_pos_product_id+"="+details.getProduct_id()).get(0);
                            posProduct.setQuantity_left(posProduct.getQuantity_left() + details.getQuantity());
                            posProduct.update(db);
                        }
                        StockTransferDetails.delete(db,db.COLUMN_stock_transfer_details_transfer_id+"="+stockTransferHead.getId());
                        stockTransferHead.delete(db);
                        data.objects.remove(data.position);
                        loadListView();
                        popupWindow.dismiss();
                    }
                });

                print.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                        Intent i = new Intent(mContex, PosInvoicePrint.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        i.putExtra("id", String.valueOf(stockTransferHead.getId()));
                        //context.startActivity(i);
                    }
                });

                payment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                        Intent i = new Intent(mContex, CreateNewTransfer.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        i.putExtra("id", stockTransferHead.getId());
                        mContex.startActivity(i);
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
        //System.out.println(DateTimeCalculation.getDateTime(calendar));
        //dateTextView.setText(simpleDateFormat.format(calendar.getTime()));
    }
    @VisibleForTesting
    void showDate(int year, int monthOfYear, int dayOfMonth, int spinnerTheme) {
        new SpinnerDatePickerDialogBuilder()
                .context(NewStockTransfer.this)
                .callback(NewStockTransfer.this)
                .spinnerTheme(spinnerTheme)
                .defaultDate(year, monthOfYear, dayOfMonth)
                .build()
                .show();
    }
}
