package com.quickmas.salim.quickmasretail.Module.RTM.Stock;

import android.content.Context;
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

import com.quickmas.salim.quickmasretail.Model.System.User;
import com.quickmas.salim.quickmasretail.Module.Dashboard.MainActivity;
import com.quickmas.salim.quickmasretail.Database.DBInitialization;
import com.quickmas.salim.quickmasretail.Model.System.DashboardMenu;
import com.quickmas.salim.quickmasretail.Model.RTM.Product;
import com.quickmas.salim.quickmasretail.Module.RTM.RTM.RTMInvoiceList.PrintView.Memo_Print;
import com.quickmas.salim.quickmasretail.R;
import com.quickmas.salim.quickmasretail.Structure.Company;
import com.quickmas.salim.quickmasretail.Utility.BluetoothPrinter;
import com.quickmas.salim.quickmasretail.Utility.DateTimeCalculation;
import com.quickmas.salim.quickmasretail.Utility.UIComponent;

import java.util.ArrayList;

import static com.quickmas.salim.quickmasretail.Model.System.DashboardMenu.selectByVarname;
import static com.quickmas.salim.quickmasretail.Utility.FileManagerSetting.getImageFromFile;
import static com.quickmas.salim.quickmasretail.Utility.FontSettings.getFont;
import static com.quickmas.salim.quickmasretail.Utility.FontSettings.setTextFont;
import static com.quickmas.salim.quickmasretail.Utility.UIComponent.setLinearLayoutBackground;

public class StockList extends AppCompatActivity {
    DBInitialization db;
    int totalProduct=0;
    int totalSell=0;
    int totalClosing=0;
    ArrayList<Company> allCompany;
    BluetoothPrinter bt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_stock_list);
        db = new DBInitialization(this,null,null,1);
        DashboardMenu cMenu =  selectByVarname(db,"dashboard_stock_assignment");
        LinearLayout headimg = (LinearLayout) findViewById(R.id.image1);
        headimg = setLinearLayoutBackground(headimg, cMenu.getImage(), this);
        TextView headtext = (TextView) findViewById(R.id.text1);
        headtext.setTypeface(getFont(this));
        headtext.setText(cMenu.getText());
        Context context = this;
        Button stock_print = setTextFont((Button) findViewById(R.id.stock_print),this,db,"memopopup_print");
        Button sell_invoice_home = setTextFont((Button) findViewById(R.id.sell_invoice_home),this,db,"dataUpload_backToHome");

        final ListView stock_list = (ListView) findViewById(R.id.stock_list);
        final ArrayList<Product> products = Product.select(db,"1=1");
        allCompany = Company.getCompanyStructOnlyProductAndGift(products);
       // Adapter_StockList_Company adapter = new Adapter_StockList_Company(getApplicationContext(), allCompany);

        LayoutInflater layoutinflater = getLayoutInflater();
        ViewGroup footer = (ViewGroup)layoutinflater.inflate(R.layout.stock__list_footer,stock_list,false);

        final TextView grand_total = setTextFont ((TextView) footer.findViewById(R.id.grand_total),this,db,"stock_grand_total");

        findTotalInformationProductAndGift(allCompany);
        final TextView total_qnty = setTextFont((TextView) footer.findViewById(R.id.total_qnty),this,String.valueOf(totalProduct));
        final TextView total_sell = setTextFont((TextView) footer.findViewById(R.id.total_sell),this,String.valueOf(totalSell));
        final TextView total_closing = setTextFont((TextView) footer.findViewById(R.id.total_closing),this,String.valueOf(totalClosing));

        //stock_list.addFooterView(footer);
        //stock_list.setAdapter(adapter);


        totalProduct=0;
        totalSell=0;
        totalClosing=0;

        final ArrayList<Company> allCompany1 = Company.getCompanyStructOnlyPOSM(products);
        LayoutInflater layoutinflater1 = getLayoutInflater();
        ViewGroup footer1 = (ViewGroup)layoutinflater1.inflate(R.layout.stock__list_footer,stock_list,false);
        final TextView grand_total1 = setTextFont ((TextView) footer1.findViewById(R.id.grand_total),this,db,"stock_grand_total");
        final TextView total_qnty1 = setTextFont((TextView) footer1.findViewById(R.id.total_qnty),this,String.valueOf(totalProduct));
        final TextView total_sell1 = setTextFont((TextView) footer1.findViewById(R.id.total_sell),this,String.valueOf(totalSell));
        final TextView total_closing1 = setTextFont((TextView) footer1.findViewById(R.id.total_closing),this,String.valueOf(totalClosing));



        bt = new BluetoothPrinter(getApplicationContext(),grand_total);

        sell_invoice_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StockList.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        stock_print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                print(products);
            }
        });


        //TextView gift_qnty  = (TextView) findViewById(R.id.gift_qnty);
        TextView total_gift  = (TextView) findViewById(R.id.total_gift);
        TextView gift_title  = (TextView) findViewById(R.id.gift_title);
        TextView total_gift_count  = (TextView)findViewById(R.id.total_gift_count);

        ListView gift_list  = (ListView) findViewById(R.id.gift_list);
        ListView free_list  = (ListView) findViewById(R.id.free_list);
        ListView posm_list  = (ListView) findViewById(R.id.posm_list);

        TextView free_sku = (TextView) findViewById(R.id.free_sku);
        TextView free_qnty = (TextView) findViewById(R.id.free_qnty);
        TextView total_free = (TextView) findViewById(R.id.total_free);
        TextView total_free_count = (TextView) findViewById(R.id.total_free_count);
        TextView free_title = (TextView) findViewById(R.id.free_title);

        TextView posm_title = (TextView) findViewById(R.id.posm_title);
        TextView posm_sku = (TextView) findViewById(R.id.posm_sku);
        //TextView posm_qnty = (TextView) findViewById(R.id.posm_qnty);
        ///TextView total_posm = (TextView) findViewById(R.id.total_posm);
        TextView total_posm_count = (TextView) findViewById(R.id.total_posm_count);





        Company company =  Company.getCompanyStructAllCompany(products);

        PSC freeProductCount = findTotalInformationProduct(company.getFreeProduct());
        PSC PosmCount = findTotalInformationProduct(company.getPosm());
        PSC giftCount = findTotalInformationProduct(company.getGifts());


        TextView gift_total_opening = setTextFont((TextView) findViewById(R.id.gift_total_opening),context,String.valueOf(giftCount.totalProduct));
        TextView gift_total_closing = setTextFont((TextView) findViewById(R.id.gift_total_closing),context,String.valueOf(giftCount.totalClosing));
        TextView gift_total_sales = setTextFont((TextView) findViewById(R.id.gift_total_sales),context,String.valueOf(giftCount.totalSell));

        TextView free_total_opening = setTextFont((TextView) findViewById(R.id.free_total_opening),context,String.valueOf(freeProductCount.totalProduct));
        TextView free_total_closing = setTextFont((TextView) findViewById(R.id.free_total_closing),context,String.valueOf(freeProductCount.totalClosing));
        TextView free_total_sales = setTextFont((TextView) findViewById(R.id.free_total_sales),context,String.valueOf(freeProductCount.totalSell));

        TextView posm_total_opening = setTextFont((TextView) findViewById(R.id.posm_total_opening),context,String.valueOf(PosmCount.totalProduct));
        TextView posm_total_closing = setTextFont((TextView) findViewById(R.id.posm_total_closing),context,String.valueOf(PosmCount.totalClosing));
        TextView posm_total_sales = setTextFont((TextView) findViewById(R.id.posm_total_sales),context,String.valueOf(PosmCount.totalSell));

        TextView total_free_product = setTextFont((TextView) findViewById(R.id.total_free_product), context, db, "adaptar_acceptDataCompanyTotalGift");

        final ListView acceptData_list = (ListView) findViewById(R.id.acceptData_list);
        Adapter_StockList_Company adapter = new Adapter_StockList_Company(getApplicationContext(), allCompany);
        acceptData_list.setAdapter(adapter);

        Adaptar_StockList_Company_Product_Gift adapter1 = new Adaptar_StockList_Company_Product_Gift(context, company.getGifts());
        gift_list.setAdapter(adapter1);

        adapter1 = new Adaptar_StockList_Company_Product_Gift(context, company.getFreeProduct());
        free_list.setAdapter(adapter1);

        adapter1 = new Adaptar_StockList_Company_Product_Gift(context, company.getPosm());
        posm_list.setAdapter(adapter1);


        UIComponent.updateListViewHeight(gift_list,1);
        UIComponent.updateListViewHeight(free_list,1);
        UIComponent.updateListViewHeight(posm_list,1);
        UIComponent.updateListViewHeight(acceptData_list,1);

        Button btn_all = setTextFont((Button) findViewById(R.id.btn_all), this, db, "All");
        Button btn_product = setTextFont((Button) findViewById(R.id.btn_product), this, db, "Product");
        Button btn_gift = setTextFont((Button) findViewById(R.id.btn_gift), this, db, "Gift");
        Button btn_posm = setTextFont((Button) findViewById(R.id.btn_posm), this, db, "POSM");

        if (company.getGifts().size() > 0)
        {
            total_gift = setTextFont(total_gift, context, db, "adaptar_acceptDataCompanyTotalGift");
            gift_title = setTextFont(gift_title, context, db, "adaptar_acceptDataCompanyGiftTitle");

        }
        else
        {
            total_gift.setVisibility(View.GONE);
            gift_title.setVisibility(View.GONE);
            try {
                total_gift_count.setVisibility(View.GONE);
            }catch (Exception e){}
            btn_gift.setVisibility(View.GONE);
            gift_total_opening.setVisibility(View.GONE);
            gift_total_closing.setVisibility(View.GONE);
            gift_total_sales .setVisibility(View.GONE);
        }

        if (company.getFreeProduct().size() > 0)
        {
            free_sku = setTextFont(free_sku, context, db, "Free Product");
            free_qnty = setTextFont(free_qnty, context, db, "quantity");
            free_title = setTextFont(free_title, context, db, "Free Product");
            free_sku.setVisibility(View.GONE);
            free_qnty.setVisibility(View.GONE);
        }
        else
        {
            free_sku.setVisibility(View.GONE);
            free_qnty.setVisibility(View.GONE);
            //total_free.setVisibility(View.GONE);
            free_title.setVisibility(View.GONE);
//            total_free_count.setVisibility(View.GONE);
            total_free_product.setVisibility(View.GONE);
            free_total_opening.setVisibility(View.GONE);
            free_total_closing.setVisibility(View.GONE);
            free_total_sales.setVisibility(View.GONE);
        }
        if (company.getPosm().size() > 0)
        {
            posm_sku = setTextFont(posm_sku, context, db, "POSM");
            //posm_qnty = setTextFont(posm_qnty, context, db, "quantity");
//            total_posm = setTextFont(total_posm, context, db, "adaptar_acceptDataCompanyTotalGift");
            posm_title = setTextFont(posm_title, context, db, "POSM");
            posm_sku.setVisibility(View.GONE);
           // posm_qnty.setVisibility(View.GONE);
        }
        else
        {
            posm_sku.setVisibility(View.GONE);
            //posm_qnty.setVisibility(View.GONE);
            //total_posm.setVisibility(View.GONE);
            posm_title.setVisibility(View.GONE);
//            total_posm_count.setVisibility(View.GONE);
            btn_posm.setVisibility(View.GONE);
            posm_total_opening.setVisibility(View.GONE);
            posm_total_closing.setVisibility(View.GONE);
            posm_total_sales.setVisibility(View.GONE);
        }

        btn_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleHolder(null,true);
            }
        });

        btn_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleHolder((LinearLayout) findViewById(R.id.products_holder),false);
            }
        });

        btn_gift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleHolder((LinearLayout) findViewById(R.id.gift_holder),false);
            }
        });

        btn_posm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleHolder((LinearLayout) findViewById(R.id.posm_holder),false);
            }
        });




    }
    void toggleHolder(LinearLayout li,Boolean selectAll)
    {
        LinearLayout products_holder = (LinearLayout) findViewById(R.id.products_holder);
        LinearLayout gift_holder  = (LinearLayout) findViewById(R.id.gift_holder);
        LinearLayout crm_holder  = (LinearLayout) findViewById(R.id.crm_holder);
        LinearLayout posm_holder  = (LinearLayout) findViewById(R.id.posm_holder);
        LinearLayout mi_holder  = (LinearLayout) findViewById(R.id.mi_holder);

        if(selectAll)
        {
            products_holder.setVisibility(View.VISIBLE);
            gift_holder.setVisibility(View.VISIBLE);
            posm_holder.setVisibility(View.VISIBLE);
        }
        else
        {
            products_holder.setVisibility(View.GONE);
            gift_holder.setVisibility(View.GONE);
            posm_holder.setVisibility(View.GONE);

            li.setVisibility(View.VISIBLE);
        }
    }

    void print(ArrayList<Product> products)
    {
        final ArrayList<Company> allCompany1 = Company.getCompanyStructOnlyPOSM(products);
        User cSystemInfo = new User();
        cSystemInfo.select(db,"1=1");
        String printData =  cSystemInfo.getCompany_name()+"\n"+
                "Date: "+ DateTimeCalculation.getCurrentDateTime()+"\n"+
                "Stock in Hand"+"\n\n";
        String cData1 = Memo_Print.getDataStringString("SKU",10,false);
        cData1 +=   Memo_Print.getDataStringString("Opening",7,true);
        cData1 +=   Memo_Print.getDataStringString("Sale",7,true);
        cData1 +=   Memo_Print.getDataStringString("Closing",8,true);
        printData+=cData1;
        printData+="--------------------------------";
        int totalOpening=0;
        int totalSell=0;
        int totalClosing=0;
        System.out.println("compnay size: "+allCompany.size());
        for(Company company: allCompany)
        {
            printData+=company.getCompany_name()+"\n";
            if(company.getProducts().size()>0)
            {
                printData+="Product\n";
            }
            for(Product product : company.getProducts())
            {
                int opening = product.getSku_qty();
                int sell = product.getSold_sku();
                int closing = opening-sell;
                totalOpening+=opening;
                totalSell+=sell;
                totalClosing+=closing;

                String cData = Memo_Print.getDataStringString(product.getSku(),10,false);
                cData +=   Memo_Print.getDataStringString(String.valueOf(opening),7,true);
                cData +=   Memo_Print.getDataStringString(String.valueOf(sell),7,true);
                cData +=   Memo_Print.getDataStringString(String.valueOf(closing),8,true);
                printData+=cData+"\n";
            }

            if(company.getGifts().size()>0)
            {
                printData+="Gifts\n";
            }

            for(Product product : company.getGifts())
            {
                int opening = product.getSku_qty();
                int sell = product.getSold_sku();
                int closing = opening-sell;
                totalOpening+=opening;
                totalSell+=sell;
                totalClosing+=closing;

                String cData = Memo_Print.getDataStringString(product.getSku(),10,false);
                cData +=   Memo_Print.getDataStringString(String.valueOf(opening),7,true);
                cData +=   Memo_Print.getDataStringString(String.valueOf(sell),7,true);
                cData +=   Memo_Print.getDataStringString(String.valueOf(closing),8,true);
                printData+=cData+"\n";
            }

        }

        for(Company company: allCompany1)
        {
            printData+=company.getCompany_name()+"\n";
            if(company.getPosm().size()>0)
            {
                printData+="POSM\n";
            }
            for(Product product : company.getPosm())
            {
                int opening = product.getSku_qty();
                int sell = product.getSold_sku();
                int closing = opening-sell;
                totalOpening+=opening;
                totalSell+=sell;
                totalClosing+=closing;

                String cData = Memo_Print.getDataStringString(product.getSku(),10,false);
                cData +=   Memo_Print.getDataStringString(String.valueOf(opening),7,true);
                cData +=   Memo_Print.getDataStringString(String.valueOf(sell),7,true);
                cData +=   Memo_Print.getDataStringString(String.valueOf(closing),8,true);
                printData+=cData+"\n";
            }

        }
        printData+="--------------------------------";

        String cData = Memo_Print.getDataStringString("Total",10,false);
        cData +=   Memo_Print.getDataStringString(String.valueOf(totalOpening),8,true);
        cData +=   Memo_Print.getDataStringString(String.valueOf(totalSell),7,true);
        cData +=   Memo_Print.getDataStringString(String.valueOf(totalClosing),7,true);
        printData+=cData+"\n"+"\n";
        printData+="        ----------------        ";
        printData+=Memo_Print.getDataCentralized(cSystemInfo.getUser_name(),32);

        System.out.println("print: "+printData);
        bt.findBT(printData);
        //bt.closeBT();
    }

    PSC findTotalInformationProduct(ArrayList<Product> products)
    {
        int tp = 0;
        int ts=0;
        int tc =0;
        for(Product cProduct : products)
        {
            int total = cProduct.getTotal_sku();
            int sold = cProduct.getSold_sku();
            int closing = total - sold;
            tp+=total;
            ts+=sold;
            tc+=closing;
        }
        return new PSC(tp,ts,tc);
    }

    void findTotalInformationProductAndGift(ArrayList<Company> companies)
    {
        for(Company company : companies)
        {
            for(Product cProduct : company.getProducts())
            {
                int total = cProduct.getTotal_sku();
                int sold = cProduct.getSold_sku();
                int closing = total - sold;
                totalProduct+=total;
                totalSell+=sold;
                totalClosing+=closing;
            }
            for(Product cProduct : company.getGifts())
            {
                int total = cProduct.getTotal_sku();
                int sold = cProduct.getSold_sku();
                int closing = total - sold;
                totalProduct+=total;
                totalSell+=sold;
                totalClosing+=closing;
            }
        }
    }

    class PSC
    {
        int totalProduct;
        int totalSell;
        int totalClosing;

        PSC(int totalProduct,int totalSell,int totalClosing)
        {
            this.totalProduct = totalProduct;
            this.totalSell = totalSell;
            this.totalClosing = totalClosing;
        }
    }
}
