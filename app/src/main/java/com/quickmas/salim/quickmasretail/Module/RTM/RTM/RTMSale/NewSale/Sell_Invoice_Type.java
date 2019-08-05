package com.quickmas.salim.quickmasretail.Module.RTM.RTM.RTMSale.NewSale;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.quickmas.salim.quickmasretail.Model.System.DashboardSubMenu;
import com.quickmas.salim.quickmasretail.Database.DBInitialization;
import com.quickmas.salim.quickmasretail.Model.System.DashboardMenu;
import com.quickmas.salim.quickmasretail.Model.RTM.Invoice;
import com.quickmas.salim.quickmasretail.Model.RTM.Product;
import com.quickmas.salim.quickmasretail.Model.System.User;
import com.quickmas.salim.quickmasretail.Module.RTM.RTM.RTMInvoiceList.InvoiceList;
import com.quickmas.salim.quickmasretail.R;

import java.util.ArrayList;
import java.util.Calendar;

import static com.quickmas.salim.quickmasretail.Model.System.DashboardMenu.selectByVarname;
import static com.quickmas.salim.quickmasretail.Model.System.TextString.textSelectByVarname;
import static com.quickmas.salim.quickmasretail.Utility.FileManagerSetting.getImageFromFile;
import static com.quickmas.salim.quickmasretail.Utility.FontSettings.getFont;
import static com.quickmas.salim.quickmasretail.Utility.FontSettings.setTextFont;
import static com.quickmas.salim.quickmasretail.Utility.UIComponent.setLinearLayoutBackground;

public class Sell_Invoice_Type extends AppCompatActivity {
    DBInitialization db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_sell__invoice__type);
        db = new DBInitialization(this,null,null,1);
        DashboardMenu cMenu =  selectByVarname(db,"dashboard_sell_rtm");
        LinearLayout headimg = (LinearLayout) findViewById(R.id.image1);
        headimg = setLinearLayoutBackground(headimg, cMenu.getImage(), this);
        TextView headtext = (TextView) findViewById(R.id.text1);
        headtext.setTypeface(getFont(this));
        headtext.setText(DashboardSubMenu.getMenuText(db, "rtm_activity_1"));

        TextView sell_invoice_type_memo_txt = (TextView) findViewById(R.id.sell_invoice_type_memo_txt);

        Button sell_invoice_type_recheck = (Button) findViewById(R.id.sell_invoice_type_recheck);
        Button sell_invoice_type_save_print = (Button) findViewById(R.id.sell_invoice_type_save_print);
        Button sell_invoice_type_save_exit = (Button) findViewById(R.id.sell_invoice_type_save_exit);

        sell_invoice_type_recheck = setTextFont(sell_invoice_type_recheck,this,db,"sellInvoice_sell_invoice_type_recheck");
        sell_invoice_type_save_print = setTextFont(sell_invoice_type_save_print,this,db,"sellInvoice_sell_invoice_type_save_print");
        sell_invoice_type_save_exit = setTextFont(sell_invoice_type_save_exit,this,db,"sellInvoice_sell_invoice_type_save_exit");

        final ArrayList<Invoice> invoices = Invoice.getPendingInvoices(db);
        int quantity=0;
        double amount=0.0;
        for(Invoice cInvoice : invoices)
        {
            quantity+=cInvoice.getQuantity();
            amount+=(cInvoice.getUnit_price()*cInvoice.getQuantity());
        }

        String invoiceTxt = textSelectByVarname(db,"sellInvoice_sell_invoice").getText()+ " "+ invoices.get(0).getPrefix()+String.valueOf(invoices.get(0).getId());
        sell_invoice_type_memo_txt = setTextFont(sell_invoice_type_memo_txt,this,invoiceTxt);


        String o = textSelectByVarname(db,"sellInvoiceType_option1").getText();
        o=o.replace("$VAR1$",String.valueOf(quantity));
        o=o.replace("$VAR2$",String.valueOf(amount));
        final RadioButton option1 = setTextFont((RadioButton) findViewById(R.id.option1),this, o);

        o = textSelectByVarname(db,"sellInvoiceType_option2").getText();
        o=o.replace("$VAR2$",String.valueOf(quantity));
        o=o.replace("$VAR1$",String.valueOf(amount));
        final RadioButton option2 = setTextFont((RadioButton) findViewById(R.id.option2),this, o);

        o = textSelectByVarname(db,"sellInvoiceType_option2").getText();
        o=o.replace("$VAR2$",String.valueOf(quantity));
        o=o.replace("$VAR1$",String.valueOf(amount));
        final RadioButton option3 = setTextFont((RadioButton) findViewById(R.id.option3),this, o);

        Product product = Product.select(db,db.COLUMN_product_id+"="+String.valueOf(invoices.get(0).getProduct_id())).get(0);
        User cSystemInfo = new User();
        cSystemInfo.select(db,"1=1");
        if(!product.getOrder_permission().equals(cSystemInfo.getUser_name()))
        {
            option1.setEnabled(false);
        }
        if(!product.getPayment_permission().equals(cSystemInfo.getUser_name()))
        {
            option2.setEnabled(false);
        }
        if(!product.getSell_permission().equals(cSystemInfo.getUser_name()))
        {
            option3.setEnabled(false);
        }

        option1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                option2.setChecked(false);
                option3.setChecked(false);
            }
        });

        option2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                option1.setChecked(false);
                option3.setChecked(false);
            }
        });

        option3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                option1.setChecked(false);
                option2.setChecked(false);
            }
        });

        sell_invoice_type_recheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent( Sell_Invoice_Type.this ,Sell_Invoice_Company.class);
                startActivity(i);
            }
        });

        sell_invoice_type_save_print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                java.util.Date now = calendar.getTime();
                java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());

                if(option1.isChecked() || option2.isChecked() || option3.isChecked())
                {
                    int status=0;
                    if(option1.isChecked())
                    {
                        status=1;
                    }
                    else if(option2.isChecked())
                    {
                        status=2;
                    }
                    else if(option3.isChecked())
                    {
                        status=3;

                        for(Invoice cInvoice : invoices)
                        {
                            Product product = Product.select(db,db.COLUMN_product_id+"="+String.valueOf(cInvoice.getProduct_id())).get(0);
                            if(cInvoice.getQuantity()>product.getSku_qty())
                            {
                                Toast.makeText(getApplicationContext(),String.valueOf(product.getSku_qty())+" "+textSelectByVarname(db,"sellInvoice_invoice_limit_ex_data_selected").getText(), Toast.LENGTH_LONG).show();
                                return;
                            }

                        }
                    }
                    for(Invoice cInvoice : invoices)
                    {
                        Product product = Product.select(db,db.COLUMN_product_id+"="+String.valueOf(cInvoice.getProduct_id())).get(0);
                        cInvoice.setInvoice_date(currentTimestamp.toString());
                        cInvoice.setStatus(status);
                        cInvoice.update(db);
                        if(status==3)
                        {
                            product.setSku_qty(product.getSku_qty()-cInvoice.getQuantity());
                            product.setSold_sku(product.getSold_sku()+cInvoice.getQuantity());
                            product.update(db);
                        }
                    }

                    Intent intent = new Intent(Sell_Invoice_Type.this,InvoiceList.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), textSelectByVarname(db,"sellInvoiceType_no_option_selected").getText(), Toast.LENGTH_LONG).show();
                }
            }
        });

        sell_invoice_type_save_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                java.util.Date now = calendar.getTime();
                java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());

                if(option1.isChecked() || option2.isChecked() || option3.isChecked())
                {
                    int status=0;
                    if(option1.isChecked())
                    {
                        status=1;
                    }
                    else if(option2.isChecked())
                    {
                        status=2;
                    }
                    else if(option3.isChecked())
                    {
                        status=3;

                        for(Invoice cInvoice : invoices)
                        {
                            Product product = Product.select(db,db.COLUMN_product_id+"="+String.valueOf(cInvoice.getProduct_id())).get(0);
                            if(cInvoice.getQuantity()>product.getSku_qty())
                            {
                                Toast.makeText(getApplicationContext(),String.valueOf(product.getSku_qty())+" "+textSelectByVarname(db,"sellInvoice_invoice_limit_ex_data_selected").getText(), Toast.LENGTH_LONG).show();
                                return;
                            }

                        }
                    }
                    for(Invoice cInvoice : invoices)
                    {
                        Product product = Product.select(db,db.COLUMN_product_id+"="+String.valueOf(cInvoice.getProduct_id())).get(0);
                        cInvoice.setInvoice_date(currentTimestamp.toString());
                        cInvoice.setStatus(status);
                        cInvoice.update(db);
                        if(status==3)
                        {
                            product.setSku_qty(product.getSku_qty()-cInvoice.getQuantity());
                            product.setSold_sku(product.getSold_sku()+cInvoice.getQuantity());
                            product.update(db);
                        }
                    }

                    Intent intent = new Intent(Sell_Invoice_Type.this,InvoiceList.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), textSelectByVarname(db,"sellInvoiceType_no_option_selected").getText(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
