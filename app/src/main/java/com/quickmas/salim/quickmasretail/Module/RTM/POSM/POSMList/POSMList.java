package com.quickmas.salim.quickmasretail.Module.RTM.POSM.POSMList;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.quickmas.salim.quickmasretail.Database.DBInitialization;
import com.quickmas.salim.quickmasretail.Model.RTM.POSM.POSMInvoiceHead;
import com.quickmas.salim.quickmasretail.Model.System.DashboardMenu;
import com.quickmas.salim.quickmasretail.Model.System.DashboardSubMenu;
import com.quickmas.salim.quickmasretail.Model.System.TextString;
import com.quickmas.salim.quickmasretail.Module.Dashboard.MainActivity;
import com.quickmas.salim.quickmasretail.R;

import java.util.ArrayList;

import static com.quickmas.salim.quickmasretail.Model.System.DashboardMenu.selectByVarname;
import static com.quickmas.salim.quickmasretail.Utility.FontSettings.getFont;
import static com.quickmas.salim.quickmasretail.Utility.FontSettings.setTextFont;
import static com.quickmas.salim.quickmasretail.Utility.UIComponent.setLinearLayoutBackground;

public class POSMList extends AppCompatActivity {
    DBInitialization db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_posmlist);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_free_product_sale_list);
        db = new DBInitialization(this,null,null,1);
        DashboardMenu cMenu =  selectByVarname(db,"dashboard_sell_rtm_list");
        LinearLayout headimg = (LinearLayout) findViewById(R.id.image1);
        headimg = setLinearLayoutBackground(headimg, cMenu.getImage(), this);
        TextView headtext = (TextView) findViewById(R.id.text1);
        headtext.setTypeface(getFont(this));
        headtext.setText(DashboardSubMenu.getMenuText(db, "rtm_activity_list_4"));


        final TextView invoice_id = setTextFont((TextView) findViewById(R.id.invoice_id),this,db,"memo_invoice_id");
        final TextView quantity = setTextFont((TextView) findViewById(R.id.quantity),this,db,"Outlet");
        final TextView amount = setTextFont((TextView) findViewById(R.id.amount),this,db,"Quantity");
        final TextView action = setTextFont((TextView) findViewById(R.id.action),this,db,"memo_action");
        final ListView memo_list = (ListView) findViewById(R.id.memo_list);
        final Button memo_print = setTextFont((Button) findViewById(R.id.memo_print),this,db,"memopopup_print");
        final Button sell_invoice_home = setTextFont((Button) findViewById(R.id.sell_invoice_home),this,db,"datadownload_backToHome");

        ArrayList<POSMInvoiceHead> invoices = POSMInvoiceHead.select(db,db.COLUMN_crm_Invoice_head_status+"!=0");
        int totalCount = invoices.size();


        LayoutInflater layoutinflater = getLayoutInflater();
        ViewGroup footer = (ViewGroup)layoutinflater.inflate(R.layout.memo_list_footer,memo_list,false);

        String t = TextString.textSelectByVarname(db,"memo_total_count").getText() +" "+String.valueOf(totalCount);

        final TextView invoice_id_total = setTextFont((TextView) footer.findViewById(R.id.invoice_id_total),this,t);
        final TextView quantity_total = setTextFont((TextView) footer.findViewById(R.id.quantity_total),this,"");
        final TextView amount_total = setTextFont((TextView) footer.findViewById(R.id.amount_total),this,"");

        Adaptar_crm_product_list adapter = new Adaptar_crm_product_list(getApplicationContext(), invoices);
        memo_list.setAdapter(adapter);
        memo_list.addFooterView(footer);

        sell_invoice_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(POSMList.this,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                //startActivity(intent);
            }
        });
    }
}
