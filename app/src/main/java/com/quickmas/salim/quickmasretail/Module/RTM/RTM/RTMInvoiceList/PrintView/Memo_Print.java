package com.quickmas.salim.quickmasretail.Module.RTM.RTM.RTMInvoiceList.PrintView;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
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
import com.quickmas.salim.quickmasretail.Model.System.User;
import com.quickmas.salim.quickmasretail.Module.Dashboard.MainActivity;
import com.quickmas.salim.quickmasretail.Database.DBInitialization;
import com.quickmas.salim.quickmasretail.Model.System.DashboardMenu;
import com.quickmas.salim.quickmasretail.Model.RTM.Invoice;
import com.quickmas.salim.quickmasretail.Model.RTM.Outlet;
import com.quickmas.salim.quickmasretail.Model.RTM.Product;
import com.quickmas.salim.quickmasretail.Model.System.TextString;
import com.quickmas.salim.quickmasretail.R;
import com.quickmas.salim.quickmasretail.Module.RTM.RTM.RTMSale.NewSale.Adaptar_sell_Invoice_Company;
import com.quickmas.salim.quickmasretail.Structure.Company;
import com.quickmas.salim.quickmasretail.Utility.BluetoothPrinter;
import com.quickmas.salim.quickmasretail.Utility.FontSettings;

import java.util.ArrayList;
import java.util.Calendar;

import static com.quickmas.salim.quickmasretail.Model.System.DashboardMenu.selectByVarname;
import static com.quickmas.salim.quickmasretail.Utility.FileManagerSetting.getImageFromFile;
import static com.quickmas.salim.quickmasretail.Utility.FontSettings.getFont;
import static com.quickmas.salim.quickmasretail.Utility.TypeConvertion.numberToWord;
import static com.quickmas.salim.quickmasretail.Utility.UIComponent.setLinearLayoutBackground;

public class Memo_Print extends AppCompatActivity {
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
        setContentView(R.layout.activity_memo__print);
        db = new DBInitialization(this,null,null,1);
        DashboardMenu cMenu =  selectByVarname(db,"dashboard_sell_rtm_list");
        LinearLayout headimg = (LinearLayout) findViewById(R.id.image1);
        headimg = setLinearLayoutBackground(headimg, cMenu.getImage(), this);
        TextView headtext = (TextView) findViewById(R.id.text1);
        headtext.setTypeface(getFont(this));
        headtext.setText(DashboardSubMenu.getMenuText(db, "rtm_activity_list_1"));


        final TextView sell_invoice_date_txt = FontSettings.setTextFont((TextView) findViewById(R.id.sell_invoice_date_txt),this,db,"sellInvoice_sell_date");
        final TextView sell_invoice_invoice_txt = FontSettings.setTextFont((TextView) findViewById(R.id.sell_invoice_invoice_txt),this,db,"sellInvoice_sell_invoice");
        final TextView sell_invoice_outlet_txt = FontSettings.setTextFont((TextView) findViewById(R.id.sell_invoice_outlet_txt),this,db,"sellInvoice_sell_outlet");
        final TextView sku = FontSettings.setTextFont((TextView) findViewById(R.id.sku),this,db,"sellInvoice_sell_sku");
        final TextView qnty = FontSettings.setTextFont((TextView) findViewById(R.id.qnty),this,db,"sellInvoice_sell_quantity");
        final TextView uprice = FontSettings.setTextFont((TextView) findViewById(R.id.uprice),this,db,"sellInvoice_sell_uprice");
        final TextView amount = FontSettings.setTextFont((TextView) findViewById(R.id.amount),this,db,"sellInvoice_sell_amount");

        final TextView memo_print_payment = FontSettings.setTextFont((TextView) findViewById(R.id.memo_print_payment),this,db,"memopopup_print");
        final TextView memo_print_product = FontSettings.setTextFont((TextView) findViewById(R.id.memo_print_product),this,db,"dataUpload_backToHome");


        final Button sell_invoice_print = FontSettings.setTextFont((Button) findViewById(R.id.sell_invoice_print),this,db,"memopopup_print");
        final Button sell_invoice_home = FontSettings.setTextFont((Button) findViewById(R.id.sell_invoice_home),this,db,"dataUpload_backToHome");

        ListView invoice_list = (ListView) findViewById(R.id.invoice_list);


        final TextView sell_invoice_date = FontSettings.setTextFont((TextView) findViewById(R.id.sell_invoice_date),this,db,"sellInvoice_sell_date");
        final TextView sell_invoice_invoice = FontSettings.setTextFont((TextView) findViewById(R.id.sell_invoice_invoice),this,db,"sellInvoice_sell_date");
        final TextView sell_invoice_outlet = FontSettings.setTextFont((TextView) findViewById(R.id.sell_invoice_outlet),this,db,"sellInvoice_sell_date");



        Calendar calendar = Calendar.getInstance();
        java.util.Date now = calendar.getTime();
        java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
        final String currentDate = DateFormat.format("dd-MM-yyyy", currentTimestamp).toString();

        sell_invoice_date.setText(currentDate);
        Intent iin= getIntent();
        Bundle b = iin.getExtras();
        String id="";
        if(b!=null)
        {
            id =(String) b.get("id");
        }
        ArrayList<Invoice> invoices = Invoice.select(db,db.COLUMN_i_invoice_id+"="+id);


        final Outlet cOutlet = Outlet.select(db,db.COLUMN_o_id+"="+invoices.get(0).getOutlet_id()).get(0);
        //final String outlet = db.getOutlet(db.COLUMN_o_id+"="+invoices.get(0).getOutlet_id()).get(0).getOutlet_id();
        final String invoice = Invoice.prefix + String.valueOf(invoices.get(0).getInvoice_id());
        sell_invoice_outlet.setText(cOutlet.getOutlet_id());
        sell_invoice_invoice.setText(invoice);

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


        final ArrayList<Company> allCompany = Company.getCompanyStruct(products);

        Adaptar_sell_Invoice_Company adapter = new Adaptar_sell_Invoice_Company(getApplicationContext(), allCompany,false);
        invoice_list.setAdapter(adapter);

        LayoutInflater layoutinflater = getLayoutInflater();
        ViewGroup footer = (ViewGroup)layoutinflater.inflate(R.layout.sell_invoice_company_footer,invoice_list,false);

        final TextView grand_total = (TextView) footer.findViewById(R.id.grand_total);
        final TextView total_qnty = (TextView) footer.findViewById(R.id.total_qnty);
        final TextView total_sell = (TextView) footer.findViewById(R.id.total_sell);
        final TextView total_closing = (TextView) footer.findViewById(R.id.total_closing);
        Typeface tf = FontSettings.getFont(this);
        grand_total.setTypeface(tf);
        total_qnty.setTypeface(tf);
        total_sell.setTypeface(tf);
        total_closing.setTypeface(tf);



        grand_total.setText(TextString.textSelectByVarname(db,"stock_grand_total").getText());
        total_qnty.setText(String.valueOf(amountCount));
        total_sell.setText("");
        total_closing.setText(String.valueOf(totalPriceCount));


        invoice_list.addFooterView(footer);

        if(invoices.get(0).getStatus()==1)
        {
            memo_print_payment.setText(TextString.textSelectByVarname(db,"memo_payment_unpaid").getText());
            memo_print_product.setText(TextString.textSelectByVarname(db,"memo_product_undelivered").getText());
        }
        else if(invoices.get(0).getStatus()==2)
        {
            memo_print_payment.setText(TextString.textSelectByVarname(db,"memo_payment_paid").getText());
            memo_print_product.setText(TextString.textSelectByVarname(db,"memo_product_undelivered").getText());
        }
        else if(invoices.get(0).getStatus()==3)
        {
            memo_print_payment.setText(TextString.textSelectByVarname(db,"memo_payment_paid").getText());
            memo_print_product.setText(TextString.textSelectByVarname(db,"memo_product_delivered").getText());
        }


        sell_invoice_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Memo_Print.this,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        sell_invoice_print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User cSystemInfo = new User();
                cSystemInfo.select(db,"1=1");
                BluetoothPrinter b = new BluetoothPrinter(getApplicationContext(),grand_total);
                String printData =  cSystemInfo.getCompany_name()+"\n"+
                        "Date: "+currentDate+"\n"+
                        "Memo No: "+invoice+"\n"+
                        "Outlet: "+cOutlet.getOutlet_id()+"\n\n";
                String cData1 = getDataStringString("SKU",10,false);
                cData1 +=   getDataStringString("Qty",5,true);
                cData1 +=   getDataStringString("U/Price",8,true);
                cData1 +=   getDataStringString("Price",9,true);
                printData+=cData1;
                printData+="--------------------------------";
                int totalQuantity=0;
                double totalAmout =0.0;
                for(Company company: allCompany)
                {
                    printData+=company.getCompany_name()+"\n";

                    for(Product product : company.getProducts())
                    {
                        double total = product.getSku_qty()* product.getSku_price();
                        totalQuantity += product.getSku_qty();
                        totalAmout += total;
                        String cData = getDataStringString(product.getSku(),10,false);
                        cData +=   getDataStringString(String.valueOf(product.getSku_qty()),5,true);
                        cData +=   getDataStringString(String.format("%.1f", product.getSku_price()),8,true);
                        cData +=   getDataStringString(String.format("%.1f", total),9,true);
                        printData+=cData+"\n";
                    }

                    for(Product product : company.getGifts())
                    {
                        double total = product.getSku_qty()* product.getSku_price();
                        totalQuantity += product.getSku_qty();
                        totalAmout += total;
                        String cData = getDataStringString(product.getSku(),10,false);
                        cData +=   getDataStringString(String.valueOf(product.getSku_qty()),5,true);
                        cData +=   getDataStringString(String.format("%.1f", product.getSku_price()),8,true);
                        cData +=   getDataStringString(String.format("%.1f", total),9,true);
                        printData+=cData+"\n";
                    }
                }
                printData+="--------------------------------";
                printData+="Total"+getDataStringString(String.valueOf(totalQuantity),10,true)+getDataStringString(String.valueOf((int) Math.ceil(totalAmout)),17,true)+"\n";
                printData+="In Word: "+   numberToWord((int) Math.ceil(totalAmout))+" Taka Only";
                System.out.println(printData);
                b.findBT(printData);

            }
        });
    }

    public static String getDataStringString(String data,int len,boolean rightToLeft)
    {
        if(data.length()>len)
        {
            data = data.substring(0,len);
        }
        String output="";
        for(int i=0;i<len - data.length();i++)
        {
            output+=" ";
        }
        if(!rightToLeft)
        {
            output=data+output;
        }
        else
        {
            output+=data;
        }
        return output.substring(0,len);
    }

    public static String getDataCentralized(String data,int len)
    {
        String output="";
        for(int i=0;i<(len-data.length())/2;i++)
        {
            output+=" ";
        }
        output+=data+output;
        if(output.length()>len)
        {
            return output.substring(0,len);
        }
        return output;
    }
}
