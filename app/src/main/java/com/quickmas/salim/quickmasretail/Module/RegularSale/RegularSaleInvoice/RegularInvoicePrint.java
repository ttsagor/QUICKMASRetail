package com.quickmas.salim.quickmasretail.Module.RegularSale.RegularSaleInvoice;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.quickmas.salim.quickmasretail.Database.DBInitialization;
import com.quickmas.salim.quickmasretail.Model.POS.ExchangeDetails;
import com.quickmas.salim.quickmasretail.Model.POS.InvoiceProduct;
import com.quickmas.salim.quickmasretail.Model.POS.PosCustomer;
import com.quickmas.salim.quickmasretail.Model.POS.PosInvoice;
import com.quickmas.salim.quickmasretail.Model.POS.PosInvoiceHead;
import com.quickmas.salim.quickmasretail.Model.POS.PosInvoiceHeadRegular;
import com.quickmas.salim.quickmasretail.Model.POS.PosInvoiceHeadRegularWeb;
import com.quickmas.salim.quickmasretail.Model.POS.PosInvoiceHeadWeb;
import com.quickmas.salim.quickmasretail.Model.POS.PosInvoiceRegular;
import com.quickmas.salim.quickmasretail.Model.POS.PosInvoiceRegularWeb;
import com.quickmas.salim.quickmasretail.Model.POS.PosInvoiceWeb;
import com.quickmas.salim.quickmasretail.Model.POS.PosProduct;
import com.quickmas.salim.quickmasretail.Model.System.DashboardMenu;
import com.quickmas.salim.quickmasretail.Model.System.User;
import com.quickmas.salim.quickmasretail.Module.POS.PosInvoicePrint.PosInvoicePrint;
import com.quickmas.salim.quickmasretail.Module.RTM.RTM.RTMInvoiceList.PrintView.Memo_Print;
import com.quickmas.salim.quickmasretail.R;
import com.quickmas.salim.quickmasretail.Structure.PosInvoiceCombian;
import com.quickmas.salim.quickmasretail.Structure.PosInvoiceCombianRegular;
import com.quickmas.salim.quickmasretail.Structure.PosInvoiceHeadCombian;
import com.quickmas.salim.quickmasretail.Structure.PosInvoiceHeadCombianRegular;
import com.quickmas.salim.quickmasretail.Utility.BluetoothPrinter;
import com.quickmas.salim.quickmasretail.Utility.DateTimeCalculation;
import com.quickmas.salim.quickmasretail.Utility.FileManagerSetting;
import com.quickmas.salim.quickmasretail.Utility.FontSettings;
import com.quickmas.salim.quickmasretail.Utility.UI.ScrollListView;
import com.quickmas.salim.quickmasretail.Utility.UI.ViewData;
import com.quickmas.salim.quickmasretail.Utility.UIComponent;

import java.util.ArrayList;

import static com.quickmas.salim.quickmasretail.Utility.FileManagerSetting.getImageFromFile;
import static com.quickmas.salim.quickmasretail.Utility.FontSettings.setTextFont;
import static com.quickmas.salim.quickmasretail.Utility.UIComponent.hideSoftKeyboard;

public class RegularInvoicePrint extends AppCompatActivity {
    DBInitialization db;
    String id="";
    String category="";
    String directprint="0";
    User cUser;
    PosInvoiceHeadCombianRegular posInvoiceHead = new PosInvoiceHeadCombianRegular();
    ArrayList<PosInvoiceCombianRegular> posInvoice = new ArrayList<>();
    Context mContex;
    Button summary_print;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_regular_invoice_print);
        mContex = this;
        db = new DBInitialization(this,null,null,1);
        Intent iin= getIntent();
        Bundle b = iin.getExtras();
        if(b!=null)
        {
            id =(String) b.get("id");
        }
        if(b!=null)
        {
            category =(String) b.get("category");
        }
        try {
            if (b != null) {
                directprint = (String) b.get("directprint");
            }
        }catch (Exception e){}
        cUser = new User();
        cUser = cUser.select(db,"1=1");

        ImageView logo = (ImageView) findViewById(R.id.logo);
        if(getImageFromFile(getApplicationContext(),FileManagerSetting.app_logo)!=null)
        {
            logo.setImageBitmap(getImageFromFile(FileManagerSetting.app_logo,this));
        }

        String address1 = cUser.getAddress1();
        if(!cUser.getAddress2().equals(""))
        {
            address1+=", "+cUser.getAddress2();
        }

        String address2 = cUser.getCountry();
        if(!cUser.getAddress3().equals(""))
        {
            address2 = cUser.getAddress3() + ", "+cUser.getCountry();
        }
        String phone = cUser.getPhone();

        // DebugHelper.print(cUser);

        TextView address1txt = (TextView) findViewById(R.id.address1);
        address1txt.setText(address1);

        TextView address2txt = (TextView) findViewById(R.id.address2);
        address2txt.setText(address2);

        TextView phonetxt = (TextView) findViewById(R.id.phone);
        phonetxt.setText(phone);
        if(category.toUpperCase().equals("W"))
        {
            posInvoiceHead =  PosInvoiceHeadCombianRegular.getPosInvoiceCombianInvoiceWeb(PosInvoiceHeadRegularWeb.select(db,db.COLUMN_pos_invoice_head_id_web+"="+id)).get(0);
            posInvoice = PosInvoiceCombianRegular.setPosInvoiceCombianWeb(PosInvoiceRegularWeb.select(db,db.COLUMN_pos_invoice_invoiceID_web+"="+id));
        }
        else
        {
            posInvoiceHead =  PosInvoiceHeadCombianRegular.getPosInvoiceCombianInvoice(PosInvoiceHeadRegular.select(db,db.COLUMN_pos_invoice_head_id+"="+id)).get(0);
            posInvoice = PosInvoiceCombianRegular.setPosInvoiceCombian(PosInvoiceRegular.select(db,db.COLUMN_pos_invoice_invoiceID+"="+id));
        }
        PosCustomer posCustomer = new PosCustomer();
        try {
            posCustomer = PosCustomer.select(db, db.COLUMN_pos_customer_name + "='" + posInvoiceHead.getCustomer() + "'").get(0);
        }catch (Exception e){}
        TextView invoice_to = FontSettings.setTextFont((TextView) findViewById(R.id.invoice_to),this,posInvoiceHead.getCustomer());
        TextView inv_number = FontSettings.setTextFont((TextView) findViewById(R.id.inv_number),this,"Invoice Number: "+posInvoiceHead.getInvoice_id());
        TextView inv_delivery_date = FontSettings.setTextFont((TextView) findViewById(R.id.inv_delivery_date),this,"Invoice Date: "+posInvoiceHead.getInvoice_date());
        TextView inv_warehouse = FontSettings.setTextFont((TextView) findViewById(R.id.inv_warehouse),this,"Warehouse: "+cUser.getSelected_location());
        TextView previous_due = FontSettings.setTextFont((TextView) findViewById(R.id.previous_due),this,"Previous Due: "+posCustomer.getBalance());
        TextView sales_person = FontSettings.setTextFont((TextView) findViewById(R.id.sales_person),this,"Print by: "+posInvoiceHead.getSales_person());
        TextView sale_time = FontSettings.setTextFont((TextView) findViewById(R.id.sale_time),this,"Print time: "+DateTimeCalculation.getCurrentDateTime());


        TextView tax_amount = FontSettings.setTextFont((TextView) findViewById(R.id.tax_amount),this,String.format("%.2f",posInvoiceHead.getTotal_tax()));
        TextView delivery_expense_amount = FontSettings.setTextFont((TextView) findViewById(R.id.delivery_expense_amount),this,String.format("%.2f",posInvoiceHead.getDelivery_expense()));
        TextView iteam_amount = FontSettings.setTextFont((TextView) findViewById(R.id.iteam_amount),this,String.format("%.2f",posInvoiceHead.getDelivery_expense()));
        TextView additional_dis_amount = FontSettings.setTextFont((TextView) findViewById(R.id.additional_dis_amount),this,String.format("%.2f",posInvoiceHead.getAdditional_discount()));
        TextView net_payable_amount = FontSettings.setTextFont((TextView) findViewById(R.id.net_payable_amount),this,String.format("%.2f",posInvoiceHead.getTotal_amount()));
        TextView print_message = FontSettings.setTextFont((TextView) findViewById(R.id.print_message),this,cUser.getPrint_message());


        TextView total_amount_before_return = FontSettings.setTextFont((TextView) findViewById(R.id.total_amount_before_return),this,String.format("%.2f",posInvoiceHead.total_paid_after_return));
        TextView return_amount = FontSettings.setTextFont((TextView) findViewById(R.id.return_amount),this,String.format("%.2f",posInvoiceHead.getTotal_return_amount()));


        TextView print_web = FontSettings.setTextFont((TextView) findViewById(R.id.print_web),this,cUser.getPrint_web_name());
        TextView print_comp = FontSettings.setTextFont((TextView) findViewById(R.id.print_comp),this,"Powered by: "+cUser.getPrint_company_name());

        try {
            String img = DashboardMenu.select(db, db.COLUMN_dash_image_category + "='logo'").get(0).getImage();

            UIComponent.setImageView(this, (ImageView) findViewById(R.id.app_company_logo), img);
        }catch (Exception e){}

        //app_company_logo

        final ListView memo_list = (ListView) findViewById(R.id.inv_product_list);

        summary_print = setTextFont((Button) findViewById(R.id.summary_print),this,db,"memopopup_print");
        final Button sell_invoice_home = setTextFont((Button) findViewById(R.id.sell_invoice_home),this,db,"datadownload_backToHome");

        LayoutInflater layoutinflater = getLayoutInflater();
        ViewGroup footer = (ViewGroup)layoutinflater.inflate(R.layout.memo_list_footer,memo_list,false);

        TextView invoice_id_total = setTextFont((TextView) footer.findViewById(R.id.invoice_id_total),this,"Total:");
        TextView quantity_total = setTextFont((TextView) footer.findViewById(R.id.quantity_total),this,String.valueOf(posInvoiceHead.getTotal_quantity()));
        TextView amount_total = setTextFont((TextView) footer.findViewById(R.id.amount_total),this,"");
        TextView amount_final = setTextFont((TextView) footer.findViewById(R.id.amount_final),this,String.format("%.2f",posInvoiceHead.getTotal_amount_full()));


        LayoutInflater layoutinflaterHead = getLayoutInflater();
        ViewGroup header = (ViewGroup)layoutinflater.inflate(R.layout.memo_list_footer,memo_list,false);

        TextView invoice_id_totalHead = setTextFont((TextView) header.findViewById(R.id.invoice_id_total),this,"SKU");
        TextView quantity_totalHead = setTextFont((TextView) header.findViewById(R.id.quantity_total),this,"Qnty");
        TextView amount_totalHead = setTextFont((TextView) header.findViewById(R.id.amount_total),this,"u/price");
        TextView amount_finalHead = setTextFont((TextView) header.findViewById(R.id.amount_final),this,"Total");

        try {
            if (posInvoiceHead.getPrevious_invoice_id()!=null && !posInvoiceHead.getPrevious_invoice_id().equals("0")) {
                LinearLayout l = (LinearLayout) findViewById(R.id.return_holder);
                TextView return_amount_txt = (TextView) findViewById(R.id.return_amount_txt);
                l.setVisibility(View.VISIBLE);
                String text = "( - ) Return Quantity: " + ExchangeDetails.getCountByInvoice(db, posInvoiceHead.getInvoice_id()) + " & Amount";
                return_amount_txt.setText(text);
            }
        }catch (Exception e){}



        //DebugHelper.print(posInvoiceHead);
        //DebugHelper.print(posInvoice);


        sell_invoice_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegularInvoicePrint.this, com.quickmas.salim.quickmasretail.Module.Dashboard.MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        summary_print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                printInvoice(db,mContex,summary_print,category,id);
            }
        });



        memo_list.addFooterView(footer);
        memo_list.addHeaderView(header);
        ///updateListViewHeight(memo_list,1);

        if(posInvoice.size()>0)
        {
            ScrollListView.loadListViewUpdateHeight(this, memo_list, R.layout.pos_invoice_item_list, posInvoice, "productListShow", 0, posInvoice.size(), false);
        }
        else
        {
            memo_list.setAdapter(null);
        }

        hideSoftKeyboard((Activity) this);
        try {
            if (directprint.equals("1")) {
                Handler handler = new Handler();
                handler.postDelayed(runnable, 1000);
            }
        }catch (Exception e){}
    }
    public Runnable runnable = new Runnable() {

        public void run() {
            summary_print.callOnClick();
        }
    };
    @Override
    protected void onResume() {
        super.onResume();

    }

    public void productListShow(ViewData data) {
        PosInvoiceCombianRegular posInvoice = (PosInvoiceCombianRegular) data.object;

        String quantity = String.valueOf(posInvoice.getQuantity());
        final String amount = String.format("%.2f", posInvoice.getUnitPrice());
        final String total = String.format("%.2f", posInvoice.getUnitPrice()*posInvoice.getQuantity());
        String title = posInvoice.getProductName();
        Double free = 0.0;
        try{
            title = InvoiceProduct.select(db,db.COLUMN_invoice_product_name+"=\""+posInvoice.getProductName()+"\"").get(0).getNames();
        }catch (Exception e){

        }
        String details = "("+posInvoice.getProductName()+") ";
        if(posInvoice.getDiscount()>0.0)
        {
            details+="Dis: "+ posInvoice.getDiscount();
        }
        if(free>0.0)
        {
            details+=" Free: "+ free;
        }

        TextView memo_invoice_id = setTextFont((TextView) data.view.findViewById(R.id.invoice_id_total),data.context,title);
        setTextFont((TextView) data.view.findViewById(R.id.details),data.context,details);

        TextView quantity_total = setTextFont((TextView) data.view.findViewById(R.id.quantity_total),data.context,String.valueOf(posInvoice.getQuantity()));
        TextView amount_total = setTextFont((TextView) data.view.findViewById(R.id.amount_total),data.context,amount);
        TextView amount_final = setTextFont((TextView) data.view.findViewById(R.id.amount_final),data.context,total);
    }

    public static void printInvoice(DBInitialization db, Context mContex,Button summary_print,String category,String id)
    {
        PosInvoiceHeadRegularWeb posInvoiceHead = new PosInvoiceHeadRegularWeb();
        ArrayList<PosInvoiceRegularWeb> posInvoice = new ArrayList<>();
        if(category.toUpperCase().equals("W"))
        {
            posInvoiceHead = (PosInvoiceHeadRegularWeb.select(db,db.COLUMN_pos_invoice_head_id_web+"="+id)).get(0);
            posInvoice = (PosInvoiceRegularWeb.select(db,db.COLUMN_pos_invoice_invoiceID_web+"="+id));
        }
        else
        {
            posInvoiceHead = (PosInvoiceHeadRegularWeb.select(db,db.COLUMN_pos_invoice_head_id_web+"="+id)).get(0);
            posInvoice = (PosInvoiceRegularWeb.select(db,db.COLUMN_pos_invoice_invoiceID_web+"="+id));
        }
        final PosCustomer posCustomer = PosCustomer.select(db,db.COLUMN_pos_customer_name+"='"+posInvoiceHead.getCustomer()+"'").get(0);

        User cUser = new User();
        cUser.select(db,"1=1");
        BluetoothPrinter b = new BluetoothPrinter(mContex,summary_print);
        String printData =  Memo_Print.getDataCentralized(cUser.getCompany_name(),32);
        String address1 = cUser.getAddress1();
        if(!cUser.getAddress2().equals(""))
        {
            address1+=", "+cUser.getAddress2();
        }
        printData+= Memo_Print.getDataCentralized(address1,32);
        String address2 = cUser.getCountry();
        if(!cUser.getAddress3().equals(""))
        {
            address2 = cUser.getAddress3() + ", "+cUser.getCountry();
        }
        printData+= Memo_Print.getDataCentralized(address2,32);
        String phone = cUser.getPhone();
        printData+=  Memo_Print.getDataCentralized(phone,32);;
        printData+=Memo_Print.getDataCentralized("Sales Invoice",32)+"\n";
        printData+="--------------------------------"+"\n";
        printData+="Invoice to\n";
        printData+=posInvoiceHead.getCustomer()+"\n";
        printData+="Invoice Number: "+posInvoiceHead.getInvoice_id()+"\n";
        printData+="Invoice Date:"+posInvoiceHead.getInvoice_date()+"\n";
        printData+="Warehouse: "+cUser.getSelected_location()+"\n";

        printData += Memo_Print.getDataStringString("SKU",10,false);
        printData +=   Memo_Print.getDataStringString("Qty",7,true);
        printData +=   Memo_Print.getDataStringString("U/Price",7,true);
        printData +=   Memo_Print.getDataStringString("Total",8,true);
        printData+="--------------------------------";

        int totalQuantity=0;
        double totalAmount=0.0;
        for(PosInvoiceRegularWeb invoice: posInvoice)
        {
            totalQuantity+=invoice.getQuantity();
            totalAmount+=(invoice.getQuantity()*invoice.getUnitPrice());

            String title = invoice.getProductName();
            Double free = 0.0;
            try{
                title = InvoiceProduct.select(db,db.COLUMN_invoice_product_name+"=\""+invoice.getProductName()+"\"").get(0).getNames();
            }catch (Exception e){

            }

            printData += Memo_Print.getDataStringString(title,10,false);
            printData += Memo_Print.getDataStringString(String.valueOf(invoice.getQuantity()),7,true);
            printData += Memo_Print.getDataStringString(String.valueOf(invoice.getUnitPrice()),7,true);
            printData += Memo_Print.getDataStringString(String.valueOf((invoice.getQuantity()*invoice.getUnitPrice())),8,true);


            title = "";
            title += "("+invoice.getProductName()+") ";
            if(invoice.getDiscount()>0.0)
            {
                title+="Dis: "+ invoice.getDiscount();
            }
            if(free>0.0)
            {
                title+=" Free: "+ free;
            }
            printData += Memo_Print.getDataStringString(title,32,false)+"\n";
        }
        printData+="--------------------------------";
        printData += Memo_Print.getDataStringString("Total",10,false);
        printData +=   Memo_Print.getDataStringString(String.valueOf(totalQuantity),7,true);
        printData +=   Memo_Print.getDataStringString("",7,true);
        printData +=   Memo_Print.getDataStringString(String.valueOf(totalAmount),8,true);

        printData +=   Memo_Print.getDataStringString("(+) Tax",24,true);
        printData +=   Memo_Print.getDataStringString(String.valueOf(posInvoiceHead.getTotal_tax()),8,true);

        printData +=   Memo_Print.getDataStringString("(+) Delivery Expense",24,true);
        printData +=   Memo_Print.getDataStringString(String.valueOf(posInvoiceHead.getDelivery_expense()),8,true);

        printData +=   Memo_Print.getDataStringString("(-) Iteam Discount",24,true);
        printData +=   Memo_Print.getDataStringString(String.valueOf(posInvoiceHead.getIteam_discount()),8,true);

        printData +=   Memo_Print.getDataStringString("(-) Additional Discount",24,true);
        printData +=   Memo_Print.getDataStringString(String.valueOf(posInvoiceHead.getAdditional_discount()),8,true);

        printData +=   Memo_Print.getDataStringString("Net Payable",24,true);
        printData +=   Memo_Print.getDataStringString(String.valueOf(posInvoiceHead.getTotal_amount()),8,true);

        printData+="--------------------------------";

        printData +=   Memo_Print.getDataStringString("PAID/SETTLED",22,true);

        printData += "\nPrevious Due: "+posCustomer.getBalance()+"\n\n";
        printData += cUser.getPrint_message()+"\n";
        printData += "Print by: "+posInvoiceHead.getSales_person()+"\n";
        printData += "Print time: "+DateTimeCalculation.getCurrentDateTime()+"\n";
        printData+="--------------------------------";
        printData += "Powered by:"+cUser.getPrint_company_name()+"\n";
        printData += cUser.getPrint_web_name()+"\n";
        b.findBT(printData);
    }
}
