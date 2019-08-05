package com.quickmas.salim.quickmasretail.Module.Exchange;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import com.quickmas.salim.quickmasretail.Model.RTM.Invoice;
import com.quickmas.salim.quickmasretail.Model.System.DashboardMenu;
import com.quickmas.salim.quickmasretail.Model.POS.PosInvoiceHead;
import com.quickmas.salim.quickmasretail.Model.System.TextString;
import com.quickmas.salim.quickmasretail.R;
import com.quickmas.salim.quickmasretail.Utility.FontSettings;
import com.quickmas.salim.quickmasretail.Utility.UI.ScrollListView;
import com.quickmas.salim.quickmasretail.Utility.UI.ViewData;

import java.util.ArrayList;

import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_head_customer;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_head_invoice_id;
import static com.quickmas.salim.quickmasretail.Model.System.DashboardMenu.selectByVarname;
import static com.quickmas.salim.quickmasretail.Utility.DateTimeCalculation.getDate;
import static com.quickmas.salim.quickmasretail.Utility.FontSettings.getFont;
import static com.quickmas.salim.quickmasretail.Utility.FontSettings.setTextFont;
import static com.quickmas.salim.quickmasretail.Utility.UIComponent.setLinearLayoutBackground;

public class InvoiceList extends AppCompatActivity {
    DBInitialization db;
    ListView memo_list;
    Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_posinvoice_list);
        mContext = this;
        db = new DBInitialization(this,null,null,1);
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


        EditText search_box = setTextFont((EditText) findViewById(R.id.search_box),this,"");
        //Button search_btn = setTextFont((Button) findViewById(R.id.search_btn),this,"Search");

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
    }



    @Override
    protected void onResume() {
        super.onResume();
    }

    void updateListview()
    {
        String txt = ((EditText) findViewById(R.id.search_box)).getText().toString();
        memo_list = (ListView) findViewById(R.id.memo_list);
        memo_list.setAdapter(null);
        ArrayList<PosInvoiceHead> invoices = new ArrayList<>();
        if(txt.equals(""))
        {
            invoices = PosInvoiceHead.select(db,"1=1");
        }
        else
        {
            String con = COLUMN_pos_invoice_head_invoice_id+" LIKE "+'"'+"%"+txt+"%"+'"'+" OR "+COLUMN_pos_invoice_head_customer+" LIKE "+'"'+"%"+txt+"%"+'"';
            invoices = PosInvoiceHead.select(db,con);
        }
        int totalCount = invoices.size();
        int totalQuantity=0;
        double totalAmount=0.0;
        for (PosInvoiceHead memo : invoices)
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

        ScrollListView.loadListView(mContext, memo_list, R.layout.pos_stock_list, invoices, "showData", 0, 100, true);
    }

    public void showData(ViewData data)
    {
        final PosInvoiceHead posInvoiceHead = (PosInvoiceHead) data.object;

        final String in = Invoice.prefix +String.valueOf(posInvoiceHead.getInvoice_id())+"\n("+posInvoiceHead.getCustomer()+")";
        String quantity = String.valueOf(posInvoiceHead.getTotal_quantity());
        final String amount = String.format("%.2f", posInvoiceHead.getTotal_amount());


        final TextView invoice_id = FontSettings.setTextFont((TextView) data.view.findViewById(R.id.invoice_id),mContext,in);
        final TextView date = FontSettings.setTextFont((TextView) data.view.findViewById(R.id.date),mContext,getDate(posInvoiceHead.getInvoice_date()));
        final TextView quantityT = FontSettings.setTextFont((TextView) data.view.findViewById(R.id.quantity),mContext,String.valueOf(quantity));
        final TextView amountT = FontSettings.setTextFont((TextView) data.view.findViewById(R.id.amount),mContext,String.valueOf(amount));
        final Button action = FontSettings.setTextFont((Button) data.view.findViewById(R.id.action),mContext,"action");
        final LinearLayout layout_holder = (LinearLayout) data.view.findViewById(R.id.layout_holder);


        if(posInvoiceHead.getTotal_amount() - posInvoiceHead.getTotal_paid_amount()==0.0)
        {
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
                LayoutInflater layoutInflater = (LayoutInflater) mContext
                        .getSystemService(LAYOUT_INFLATER_SERVICE);
                final View popupView = layoutInflater.inflate(R.layout.memo_pop_up, null);
                final PopupWindow popupWindow = new PopupWindow(popupView,LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                Button print  = FontSettings.setTextFont((Button) popupView.findViewById(R.id.print),mContext,db,"memopopup_print");
                Button delivery  = FontSettings.setTextFont((Button) popupView.findViewById(R.id.delivery),mContext,db,"memopopup_devilery");
                Button payment  = FontSettings.setTextFont((Button) popupView.findViewById(R.id.payment),mContext,db,"memopopup_payment");
                Button make_void  = FontSettings.setTextFont((Button) popupView.findViewById(R.id.make_void),mContext,db,"memopopup_void");

                delivery.setVisibility(View.GONE);
                make_void.setVisibility(View.GONE);
                payment.setVisibility(View.GONE);

                print.setText("Select");

                print.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                        Intent i = new Intent(mContext, ExchangeProductSelection.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        i.putExtra("id", String.valueOf(posInvoiceHead.getId()));
                        mContext.startActivity(i);
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
