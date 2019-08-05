package com.quickmas.salim.quickmasretail.Module.RTM.CRM.CRMList;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.quickmas.salim.quickmasretail.Database.DBInitialization;
import com.quickmas.salim.quickmasretail.Model.RTM.CRMInvoiceHead;
import com.quickmas.salim.quickmasretail.Model.RTM.Invoice;
import com.quickmas.salim.quickmasretail.Model.System.DashboardMenu;
import com.quickmas.salim.quickmasretail.Model.System.DashboardSubMenu;
import com.quickmas.salim.quickmasretail.Model.System.TextString;
import com.quickmas.salim.quickmasretail.Model.System.User;
import com.quickmas.salim.quickmasretail.Module.Dashboard.MainActivity;
import com.quickmas.salim.quickmasretail.Module.RTM.CRM.CRMPrint.CRMPrint;
import com.quickmas.salim.quickmasretail.R;
import com.quickmas.salim.quickmasretail.Utility.FileManagerSetting;
import com.quickmas.salim.quickmasretail.Utility.FontSettings;
import com.quickmas.salim.quickmasretail.Utility.UI.ScrollListView;
import com.quickmas.salim.quickmasretail.Utility.UI.ViewData;

import java.util.ArrayList;

import static com.quickmas.salim.quickmasretail.Model.System.DashboardMenu.selectByVarname;
import static com.quickmas.salim.quickmasretail.Utility.FontSettings.getFont;
import static com.quickmas.salim.quickmasretail.Utility.FontSettings.setTextFont;
import static com.quickmas.salim.quickmasretail.Utility.UIComponent.setLinearLayoutBackground;

public class CRMList extends AppCompatActivity {
    DBInitialization db;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_crmlist);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        context = this;
        setContentView(R.layout.activity_free_product_sale_list);
        db = new DBInitialization(this,null,null,1);
        DashboardMenu cMenu =  selectByVarname(db,"dashboard_sell_rtm_list");
        LinearLayout headimg = (LinearLayout) findViewById(R.id.image1);
        headimg = setLinearLayoutBackground(headimg, cMenu.getImage(), this);
        TextView headtext = (TextView) findViewById(R.id.text1);
        headtext.setTypeface(getFont(this));
        headtext.setText(DashboardSubMenu.getMenuText(db, "rtm_activity_list_3"));


        final TextView invoice_id = setTextFont((TextView) findViewById(R.id.invoice_id),this,db,"memo_invoice_id");
        final TextView quantity = setTextFont((TextView) findViewById(R.id.quantity),this,db,"Outlet");
        final TextView amount = setTextFont((TextView) findViewById(R.id.amount),this,db,"Quantity");
        final TextView action = setTextFont((TextView) findViewById(R.id.action),this,db,"memo_action");
        final ListView memo_list = (ListView) findViewById(R.id.memo_list);
        final Button memo_print = setTextFont((Button) findViewById(R.id.memo_print),this,db,"memopopup_print");
        final Button sell_invoice_home = setTextFont((Button) findViewById(R.id.sell_invoice_home),this,db,"datadownload_backToHome");

        ArrayList<CRMInvoiceHead> invoices = CRMInvoiceHead.select(db,db.COLUMN_crm_Invoice_head_status+"!=0");
        int totalCount = invoices.size();


        LayoutInflater layoutinflater = getLayoutInflater();
        ViewGroup footer = (ViewGroup)layoutinflater.inflate(R.layout.memo_list_footer,memo_list,false);

        String t = TextString.textSelectByVarname(db,"memo_total_count").getText() +" "+String.valueOf(totalCount);

        final TextView invoice_id_total = setTextFont((TextView) footer.findViewById(R.id.invoice_id_total),this,t);
        final TextView quantity_total = setTextFont((TextView) footer.findViewById(R.id.quantity_total),this,"");
        final TextView amount_total = setTextFont((TextView) footer.findViewById(R.id.amount_total),this,"");

        memo_list.addFooterView(footer);
        ScrollListView.loadListView(this, memo_list, R.layout.adaptar_free_product_list_invoice_list, invoices, "showData", 0, 100, true);

        sell_invoice_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CRMList.this,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                //startActivity(intent);
            }
        });
    }

    public void showData(ViewData data)
    {
        final CRMInvoiceHead crmInvoiceHead = (CRMInvoiceHead) data.object;

        String outlet = crmInvoiceHead.getOutlet();
        if(crmInvoiceHead.getOutlet().length()>5)
        {
            outlet = crmInvoiceHead.getOutlet().substring(0,5)+"..";
        }

        final LinearLayout layout_holder = (LinearLayout) data.view.findViewById(R.id.layout_holder);
        final Button action = FontSettings.setTextFont((Button) data.view.findViewById(R.id.action),context,db,"memo_action");
        final TextView invoice_id = FontSettings.setTextFont((TextView) data.view.findViewById(R.id.invoice_id),context,Invoice.prefix +String.valueOf(crmInvoiceHead.getId()));
        final TextView quantity = FontSettings.setTextFont((TextView) data.view.findViewById(R.id.quantity),context,outlet);
        final TextView date = FontSettings.setTextFont((TextView) data.view.findViewById(R.id.date),context,crmInvoiceHead.getInvoice_date().replace(" ","\n"));
        final TextView amount = FontSettings.setTextFont((TextView) data.view.findViewById(R.id.amount),context,String.valueOf(crmInvoiceHead.getQuantity()));

        layout_holder.setBackgroundColor(Color.parseColor("#ffffff"));

        invoice_id.setTextColor(Color.parseColor("#000000"));
        quantity.setTextColor(Color.parseColor("#000000"));
        amount.setTextColor(Color.parseColor("#000000"));
        date.setTextColor(Color.parseColor("#000000"));


        action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater = (LayoutInflater) context
                        .getSystemService(LAYOUT_INFLATER_SERVICE);
                final View popupView = layoutInflater.inflate(R.layout.memo_pop_up, null);
                final PopupWindow popupWindow = new PopupWindow(popupView,LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                Button print  = FontSettings.setTextFont((Button) popupView.findViewById(R.id.print),context,db,"memopopup_print");
                Button delivery  = FontSettings.setTextFont((Button) popupView.findViewById(R.id.delivery),context,db,"Photo");
                Button payment  = FontSettings.setTextFont((Button) popupView.findViewById(R.id.payment),context,db,"memopopup_payment");
                Button make_void  = FontSettings.setTextFont((Button) popupView.findViewById(R.id.make_void),context,db,"memopopup_void");

                //delivery.setVisibility(View.GONE);
                payment.setVisibility(View.GONE);


                User cSystemInfo = new User();
                cSystemInfo.select(db,"1=1");

                print.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent( context ,CRMPrint.class);
                        i.putExtra("invoice_id", crmInvoiceHead.getId());
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(i);
                    }
                });

                delivery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                        imagepopup(crmInvoiceHead,action);
                        //context.startActivity(i);
                    }
                });


                make_void.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //makePayment(amount,invoice_id,4,TextString.textSelectByVarname(db,"memo_diag_void_toast").getText(),position);
                        popupWindow.dismiss();
                    }
                });

                popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        layout_holder.setBackgroundColor(Color.parseColor("#ffffff"));
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

    void imagepopup(CRMInvoiceHead freeProductInvoiceHead,final Button btn)
    {
        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        final View popupView = layoutInflater.inflate(R.layout.pop_up_free_product_invoice_list_images, null);
        final PopupWindow popupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        final ImageView close_tab = (ImageView) popupView.findViewById(R.id.close_tab);
        final ImageView image = (ImageView) popupView.findViewById(R.id.image);
        final LinearLayout image_holder = (LinearLayout) popupView.findViewById(R.id.image_holder);
        close_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        String[] images = freeProductInvoiceHead.getPhoto().split(";");

        for(String img : images)
        {
            ImageView imageview = new ImageView(context);
            LinearLayout.LayoutParams params = new LinearLayout
                    .LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,400);
            imageview.setPadding(0,10,0,0);
            imageview.setImageURI(Uri.parse(Environment.getExternalStorageDirectory().toString() + "/" + FileManagerSetting.folder_name + "/" + FileManagerSetting.rtm_crm_product + "/" + img.trim()));
            imageview.setLayoutParams(params);
            image_holder.addView(imageview);

        }
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.showAtLocation(btn, Gravity.CENTER, 0, 0);
    }
}
