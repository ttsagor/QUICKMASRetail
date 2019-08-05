package com.quickmas.salim.quickmasretail.Module.RTM.RTM.RTMSale.NewSale;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.quickmas.salim.quickmasretail.Model.System.DashboardSubMenu;
import com.quickmas.salim.quickmasretail.Module.Dashboard.MainActivity;
import com.quickmas.salim.quickmasretail.Database.DBInitialization;
import com.quickmas.salim.quickmasretail.Model.System.DashboardMenu;
import com.quickmas.salim.quickmasretail.Model.RTM.Invoice;
import com.quickmas.salim.quickmasretail.Model.RTM.Outlet;
import com.quickmas.salim.quickmasretail.Model.RTM.Product;
import com.quickmas.salim.quickmasretail.R;
import com.quickmas.salim.quickmasretail.Structure.Company;

import java.util.ArrayList;
import java.util.Calendar;

import static com.quickmas.salim.quickmasretail.Model.System.DashboardMenu.selectByVarname;
import static com.quickmas.salim.quickmasretail.Model.System.TextString.textSelectByVarname;
import static com.quickmas.salim.quickmasretail.Utility.FileManagerSetting.getImageFromFile;
import static com.quickmas.salim.quickmasretail.Utility.FontSettings.getFont;
import static com.quickmas.salim.quickmasretail.Utility.FontSettings.setTextFont;
import static com.quickmas.salim.quickmasretail.Utility.UIComponent.setLinearLayoutBackground;

public class Sell_Invoice_Company extends AppCompatActivity {
    DBInitialization db;
    int amountCount=0;
    int uPriceCount=0;
    int totalPriceCount=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_sell__invoice__company);

        db = new DBInitialization(this,null,null,1);
        DashboardMenu cMenu =  selectByVarname(db,"dashboard_sell_rtm");
        LinearLayout headimg = (LinearLayout) findViewById(R.id.image1);
        headimg = setLinearLayoutBackground(headimg, cMenu.getImage(), this);
        TextView headtext = (TextView) findViewById(R.id.text1);
        headtext.setTypeface(getFont(this));
        headtext.setText(DashboardSubMenu.getMenuText(db, "rtm_activity_1"));

        TextView sell_invoice_date_txt = (TextView) findViewById(R.id.sell_invoice_date_txt);
        TextView sell_invoice_date = (TextView) findViewById(R.id.sell_invoice_date);
        TextView sell_invoice_invoice_txt = (TextView) findViewById(R.id.sell_invoice_invoice_txt);
        TextView sell_invoice_invoice = (TextView) findViewById(R.id.sell_invoice_invoice);
        TextView sell_invoice_outlet_txt = (TextView) findViewById(R.id.sell_invoice_outlet_txt);
        TextView sell_invoice_outlet = (TextView) findViewById(R.id.sell_invoice_outlet);
        TextView sku = (TextView) findViewById(R.id.sku);
        TextView qnty = (TextView) findViewById(R.id.qnty);
        TextView uprice = (TextView) findViewById(R.id.uprice);
        TextView amount = (TextView) findViewById(R.id.amount);
        Button sell_invoice_proceed = (Button) findViewById(R.id.sell_invoice_proceed);
        Button sell_invoice_add_new = (Button) findViewById(R.id.sell_invoice_add_new);
        Button sell_invoice_discard = (Button) findViewById(R.id.sell_invoice_discard);

        ListView invoice_list = (ListView) findViewById(R.id.invoice_list);

        sell_invoice_date_txt = setTextFont(sell_invoice_date_txt, this,db,"sellInvoice_sell_date");

        sell_invoice_invoice_txt = setTextFont(sell_invoice_invoice_txt, this,db,"sellInvoice_sell_invoice");
        sell_invoice_outlet_txt = setTextFont(sell_invoice_outlet_txt, this,db,"sellInvoice_sell_outlet");
        sku = setTextFont(sku, this,db,"sellInvoice_sell_sku");
        qnty = setTextFont(qnty, this,db,"sellInvoice_sell_quantity");
        uprice = setTextFont(uprice, this,db,"sellInvoice_sell_uprice");
        amount = setTextFont(amount, this,db,"sellInvoice_sell_amount");
        sell_invoice_proceed = setTextFont(sell_invoice_proceed, this,db,"sellInvoice_invoice_proceed");
        sell_invoice_add_new = setTextFont(sell_invoice_add_new, this,db,"sellInvoice_invoice_add_new");
        sell_invoice_discard = setTextFont(sell_invoice_discard, this,db,"sellInvoice_sell_invoice_discard");
        Calendar calendar = Calendar.getInstance();
        java.util.Date now = calendar.getTime();
        java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
        String currentDate = DateFormat.format("dd-MM-yyyy", currentTimestamp).toString();

        sell_invoice_date.setTypeface(getFont(this));
        sell_invoice_date.setText(currentDate);


        ArrayList<Invoice> invoices = Invoice.getPendingInvoices(db);

        final Outlet cOutlet = Outlet.select(db,db.COLUMN_o_id+"="+invoices.get(0).getOutlet_id()).get(0);
        //final String outlet = db.getOutlet(db.COLUMN_o_id+"="+invoices.get(0).getOutlet_id()).get(0).getOutlet_id();
        String invoice = invoices.get(0).getPrefix() + String.valueOf(invoices.get(0).getInvoice_id());

        sell_invoice_outlet = setTextFont(sell_invoice_outlet, this, cOutlet.getOutlet_id());
        sell_invoice_invoice = setTextFont(sell_invoice_invoice, this, invoice);


        ArrayList<Product> products = new ArrayList<Product>();

        for(Invoice cInvoice : invoices)
        {
            Product product = Product.select(db,db.COLUMN_product_id+"="+cInvoice.getProduct_id()).get(0);
            product.setSku_qty(cInvoice.getQuantity());
            product.setSku_price(cInvoice.getUnit_price());
            amountCount += cInvoice.getQuantity();
            uPriceCount +=cInvoice.getUnit_price();
            totalPriceCount+= (cInvoice.getQuantity()*cInvoice.getUnit_price());
            products.add(product);
        }


        ArrayList<Company> allCompany = Company.getCompanyStructOnlyProductAndGift(products);

        Adaptar_sell_Invoice_Company adapter = new Adaptar_sell_Invoice_Company(getApplicationContext(), allCompany,true);
        invoice_list.setAdapter(adapter);

        LayoutInflater layoutinflater = getLayoutInflater();
        ViewGroup footer = (ViewGroup)layoutinflater.inflate(R.layout.sell_invoice_company_footer,invoice_list,false);

        TextView grand_total = (TextView) footer.findViewById(R.id.grand_total);
        TextView total_qnty = (TextView) footer.findViewById(R.id.total_qnty);
        TextView total_sell = (TextView) footer.findViewById(R.id.total_sell);
        TextView total_closing = (TextView) footer.findViewById(R.id.total_closing);
        grand_total.setTypeface(getFont(this));
        total_qnty.setTypeface(getFont(this));
        total_sell.setTypeface(getFont(this));
        total_closing.setTypeface(getFont(this));


        grand_total = setTextFont(grand_total, this,  db, "stock_grand_total");
        total_qnty = setTextFont(total_qnty,this,String.valueOf(amountCount));
        total_sell = setTextFont(total_sell,this,String.valueOf(""));
        total_closing= setTextFont(total_closing,this,String.valueOf(totalPriceCount));
        invoice_list.addFooterView(footer);

        sell_invoice_add_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent( Sell_Invoice_Company.this ,SaleOutletSelection.class);
                i.putExtra("invoiceOutlet", String.valueOf(cOutlet.getId()));
                startActivity(i);
            }
        });
        sell_invoice_proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent( Sell_Invoice_Company.this ,Sell_Invoice_Type.class);
                startActivity(i);
            }
        });

        sell_invoice_discard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Sell_Invoice_Company.this);

                builder.setTitle(textSelectByVarname(db,"sellInvoice_sell_invoice_dialog_title").getText());
                builder.setMessage(textSelectByVarname(db,"sellInvoice_sell_invoice_dialog_text").getText());

                builder.setPositiveButton(textSelectByVarname(db,"sellInvoice_sell_invoice_dialog_yes").getText(), new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {

                        Invoice.deletePendingInvoices(db);
                        Intent intent = new Intent(Sell_Invoice_Company.this,MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        dialog.dismiss();
                        finish();
                    }
                });

                builder.setNegativeButton(textSelectByVarname(db,"sellInvoice_sell_invoice_dialog_no").getText(), new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // Do nothing
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }
}
