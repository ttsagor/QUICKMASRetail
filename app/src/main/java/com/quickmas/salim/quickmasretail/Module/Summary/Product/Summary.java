package com.quickmas.salim.quickmasretail.Module.Summary.Product;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.quickmas.salim.quickmasretail.Database.DBInitialization;
import com.quickmas.salim.quickmasretail.MainActivity;
import com.quickmas.salim.quickmasretail.Model.System.DashboardMenu;
import com.quickmas.salim.quickmasretail.Model.RTM.Invoice;
import com.quickmas.salim.quickmasretail.Model.RTM.Product;
import com.quickmas.salim.quickmasretail.Model.System.TextString;
import com.quickmas.salim.quickmasretail.Model.System.User;
import com.quickmas.salim.quickmasretail.R;
import com.quickmas.salim.quickmasretail.Structure.Company;
import com.quickmas.salim.quickmasretail.Utility.BluetoothPrinter;
import com.quickmas.salim.quickmasretail.Utility.DateTimeCalculation;
import com.quickmas.salim.quickmasretail.Utility.FontSettings;
import com.quickmas.salim.quickmasretail.Utility.UIComponent;

import java.util.ArrayList;
import java.util.Calendar;

import static com.quickmas.salim.quickmasretail.Model.System.DashboardMenu.selectByVarname;
import static com.quickmas.salim.quickmasretail.Module.RTM.RTM.RTMInvoiceList.PrintView.Memo_Print.getDataStringString;
import static com.quickmas.salim.quickmasretail.Utility.FileManagerSetting.getImageFromFile;
import static com.quickmas.salim.quickmasretail.Utility.FontSettings.getFont;
import static com.quickmas.salim.quickmasretail.Utility.UIComponent.setLinearLayoutBackground;

public class Summary extends AppCompatActivity {
    ArrayList<Company> allCompany;
    ArrayList<Product> products;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_summary);

        final DBInitialization db = new DBInitialization(this,null,null,1);
        DashboardMenu cMenu =  selectByVarname(db,"dashboard_summary_rtm");
        LinearLayout headimg = (LinearLayout) findViewById(R.id.image1);
        headimg = setLinearLayoutBackground(headimg, cMenu.getImage(), this);
        TextView headtext = (TextView) findViewById(R.id.text1);
        headtext.setTypeface(getFont(this));
        headtext.setText(cMenu.getText());
        final TextView product_in_hand = FontSettings.setTextFont((TextView) findViewById(R.id.product_in_hand),this,db,"summary_text_in_hand");
        final TextView sales_history = FontSettings.setTextFont((TextView) findViewById(R.id.sales_history),this,db,"summary_text_sell_history");
        final TextView in_hand_amount = FontSettings.setTextFont((TextView) findViewById(R.id.in_hand_amount),this,db,"summary_in_hand_count_text");
        final TextView receivable_txt = FontSettings.setTextFont((TextView) findViewById(R.id.receivable),this,db,"summary_in_receivable");
        final Button summary_print = FontSettings.setTextFont((Button) findViewById(R.id.summary_print),this,db,"memopopup_print");
        final Button sell_invoice_home = FontSettings.setTextFont((Button) findViewById(R.id.sell_invoice_home),this,db,"dataUpload_backToHome");
        final Button sell_invoice_print = FontSettings.setTextFont((Button) findViewById(R.id.summary_print),this,db,"memopopup_print");

        final ListView stock_list = (ListView) findViewById(R.id.summay_list);
        final ListView stock_list2 = (ListView) findViewById(R.id.summay_list2);

        Calendar calendar = Calendar.getInstance();
        java.util.Date now = calendar.getTime();
        java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
        String currentDate = DateFormat.format("yyyy-MM-dd hh:mm:ss", currentTimestamp).toString();

        String con = db.COLUMN_product_id+" IN (SELECT "+db.COLUMN_i_product_id+" FROM "+db.TABLE_Invoice+" WHERE "+db.COLUMN_i_status+"!=0 AND "+db.COLUMN_i_status+"!=4)";
        products = Product.select(db,con);
        allCompany = Company.getCompanyStruct(products);

        int totalInvoice=0;
        int totalQuantity=0;
        double totalAmount=0.0;
        for(Company cCompany : allCompany)
        {
            for(Product cProudct : cCompany.getProductAndGift())
            {
                String con1 = db.COLUMN_i_product_id+"="+cProudct.getId()+" AND "+db.COLUMN_i_status+"!=0 AND "+db.COLUMN_i_status+"!=4";
                int memo_count = Invoice.count(db,con1);
                int sumQuantity = Invoice.sumData(db,db.COLUMN_i_quantity,con1);

                cProudct.setTotal_invoice(memo_count);
                cProudct.setSum_quantity(sumQuantity);
                totalInvoice+=memo_count;
                totalQuantity+=sumQuantity;

                totalAmount += (sumQuantity*cProudct.getSku_price());
            }
        }
        totalInvoice = Invoice.getTotalInvoice(db);
        Summary_SoldList_Company adapter = new Summary_SoldList_Company(getApplicationContext(), allCompany);
        LayoutInflater layoutinflater = getLayoutInflater();
        ViewGroup footer = (ViewGroup)layoutinflater.inflate(R.layout.stock__list_footer,stock_list,false);
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
        total_qnty.setText(String.valueOf(totalInvoice));
        total_sell.setText(String.valueOf(totalQuantity));
        total_closing.setText(String.format("%.2f", totalAmount));
        stock_list.addFooterView(footer);
        stock_list.setAdapter(adapter);
        UIComponent.updateListViewHeight(stock_list,-100);


        ViewGroup header = (ViewGroup)layoutinflater.inflate(R.layout.memo_in_hand_list,stock_list,false);
        final TextView company_name = (TextView) header.findViewById(R.id.company_name);
        final TextView in_hand = (TextView) header.findViewById(R.id.in_hand);
        final TextView pending_delivery = (TextView) header.findViewById(R.id.pending_delivery);
        final TextView in_hand_gift_head = (TextView) header.findViewById(R.id.in_hand_gift);

        company_name.setTextAppearance(this, android.R.style.TextAppearance_Medium);
        in_hand.setTextAppearance(this, android.R.style.TextAppearance_Medium);
        pending_delivery.setTextAppearance(this, android.R.style.TextAppearance_Medium);
        in_hand_gift_head.setTextAppearance(this, android.R.style.TextAppearance_Medium);
        company_name.setText(TextString.textSelectByVarname(db,"summary_in_hand_company_name").getText());
        in_hand.setText(TextString.textSelectByVarname(db,"summary_in_hand_in_hand").getText());
        pending_delivery.setText(TextString.textSelectByVarname(db,"summary_in_hand_pending_delivery").getText());
        in_hand_gift_head.setText(TextString.textSelectByVarname(db,"adaptar_acceptDataCompanyGiftTitle").getText());

        company_name.setTypeface(tf);
        in_hand.setTypeface(tf);
        pending_delivery.setTypeface(tf);
        in_hand_gift_head.setTypeface(tf);
        stock_list2.addHeaderView(header);

        int totalStatus1=0;
        int totalStatus2=0;
        int totalStatus3=0;
        int countTotalData=0;
        int countTotalDataGift=0;
        double inHandAmount=0.0;
        double receivable =0.0;
        products = Product.select(db,"1=1");
        allCompany = Company.getCompanyStructOnlyProductAndGift(products);

        for(Company cCompany : allCompany)
        {
            int tStatus1=0;
            int tStatus2=0;
            int tStatus3=0;
            int totalAllProduct=0;
            int totalAllGift=0;
            for(Product cProudct : cCompany.getProductAndGift())
            {
                String con1 = db.COLUMN_i_product_id+"="+cProudct.getId();
                ArrayList<Invoice> invoices = Invoice.select(db,con1);

                if(cProudct.getProduct_type().toUpperCase().equals("GIFT"))
                {
                    totalAllGift+= (cProudct.getTotal_sku()-cProudct.getSold_sku());
                    countTotalDataGift+=(cProudct.getTotal_sku()-cProudct.getSold_sku());
                }
                else
                {
                    totalAllProduct+=(cProudct.getTotal_sku()-cProudct.getSold_sku());
                    countTotalData+=(cProudct.getTotal_sku()-cProudct.getSold_sku());
                }

                for(Invoice cInvoice : invoices)
                {
                    if(cInvoice.getStatus()==1)
                    {
                        tStatus1+=cInvoice.getQuantity();
                        totalStatus1+=cInvoice.getQuantity();
                        receivable += (cInvoice.getQuantity()*cInvoice.getUnit_price());
                    }
                    else if(cInvoice.getStatus()==2)
                    {
                        inHandAmount += (cInvoice.getQuantity()*cInvoice.getUnit_price());
                        tStatus2+=cInvoice.getQuantity();
                        totalStatus2+=cInvoice.getQuantity();
                    }
                    else if(cInvoice.getStatus()==3)
                    {
                        inHandAmount += (cInvoice.getQuantity()*cInvoice.getUnit_price());
                        tStatus3+=cInvoice.getQuantity();
                        totalStatus3+=cInvoice.getQuantity();
                    }
                }
            }
            cCompany.setTotal_status_1(tStatus1);
            cCompany.setTotal_status_2(tStatus2);
            cCompany.setTotal_status_3(tStatus3);
            cCompany.setTotalQuantityAllProduct(totalAllProduct);
            cCompany.setTotalQuantityAllGift(totalAllGift);
        }

        ViewGroup in_hand_footer = (ViewGroup)layoutinflater.inflate(R.layout.memo_in_hand_list,stock_list,false);
        final TextView company_name_footer = (TextView) in_hand_footer.findViewById(R.id.company_name);
        final TextView in_hand_footer_txt = (TextView) in_hand_footer.findViewById(R.id.in_hand);
        final TextView pending_delivery_footer = (TextView) in_hand_footer.findViewById(R.id.pending_delivery);
        final TextView in_hand_gift = (TextView) in_hand_footer.findViewById(R.id.in_hand_gift);

        company_name_footer.setTextAppearance(this, android.R.style.TextAppearance_Medium);
        in_hand_footer_txt.setTextAppearance(this, android.R.style.TextAppearance_Medium);
        pending_delivery_footer.setTextAppearance(this, android.R.style.TextAppearance_Medium);
        in_hand_gift.setTextAppearance(this, android.R.style.TextAppearance_Medium);
        company_name_footer.setText(TextString.textSelectByVarname(db,"memo_total_count").getText());
        in_hand_footer_txt.setText(String.valueOf(countTotalData));
        in_hand_gift.setText(String.valueOf(countTotalDataGift));
        pending_delivery_footer.setText(String.valueOf(totalStatus1+totalStatus2));
        company_name_footer.setTypeface(tf);
        in_hand_footer_txt.setTypeface(tf);
        pending_delivery_footer.setTypeface(tf);
        in_hand_gift.setTypeface(tf);
        stock_list2.addFooterView(in_hand_footer);
        Adaptar_Summary_In_Hand_List adapter2 = new Adaptar_Summary_In_Hand_List(getApplicationContext(), allCompany);

        stock_list2.setAdapter(adapter2);
        UIComponent.updateListViewHeight(stock_list2,-100);


        int color = Color.parseColor("#FFF5F19E");
        String txt = TextString.textSelectByVarname(db,"summary_in_hand_count_text").getText()+" "+String.format("%.2f", inHandAmount);
        SpannableString spannableString = new SpannableString(txt);
        spannableString = UIComponent.buildBackgroundColorSpan(spannableString, txt, String.valueOf(inHandAmount), color);
        in_hand_amount.setTypeface(tf);
        in_hand_amount.setText(spannableString);



        color = Color.parseColor("#ffa672");
        txt = TextString.textSelectByVarname(db,"summary_in_receivable").getText()+" "+String.format("%.2f", receivable);
        spannableString = new SpannableString(txt);
        spannableString = UIComponent.buildBackgroundColorSpan(spannableString, txt, String.valueOf(receivable), color);
        receivable_txt.setTypeface(tf);
        receivable_txt.setText(spannableString);

        sell_invoice_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Summary.this,MainActivity.class);
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
                        "Date: "+ DateTimeCalculation.getCurrentDateTime()+"\n"+"\n";
                printData+="Sales History\n";
                printData+= getDataStringString("SKU",10,false);
                printData+= getDataStringString("Memo",5,true);
                printData+= getDataStringString("Qnty",8,true);
                printData+= getDataStringString("Taka",9,true);
                printData+="--------------------------------";
                allCompany = Company.getCompanyStruct(products);

                int totalInvoice=0;
                int totalQuantity=0;
                double totalAmount=0.0;
                for(Company cCompany : allCompany)
                {
                    for(Product cProudct : cCompany.getProductAndGift())
                    {
                        String con1 = db.COLUMN_i_product_id+"="+cProudct.getId()+" AND "+db.COLUMN_i_status+"!=0 AND "+db.COLUMN_i_status+"!=4";
                        int memo_count = Invoice.count(db,con1);
                        if(memo_count>0)
                        {
                            printData+=  getDataStringString(cProudct.getSku(), 10, false);

                            printData += getDataStringString(String.valueOf(memo_count), 5, true);
                            int sumQuantity = Invoice.sumData(db, db.COLUMN_i_quantity, con1);
                            printData += getDataStringString(String.valueOf(sumQuantity), 8, true);
                            cProudct.setTotal_invoice(memo_count);
                            cProudct.setSum_quantity(sumQuantity);
                            totalInvoice += memo_count;
                            totalQuantity += sumQuantity;
                            totalAmount += (sumQuantity * cProudct.getSku_price());
                            printData += getDataStringString(String.valueOf((sumQuantity * cProudct.getSku_price())), 9, true);
                        }
                    }
                }
                printData+="--------------------------------";
                printData+= getDataStringString("Total",10,false);
                printData +=   getDataStringString(String.valueOf(totalInvoice),5,true);
                printData +=   getDataStringString(String.valueOf(totalQuantity),8,true);
                printData +=   getDataStringString(String.valueOf(totalAmount),9,true);




                int totalStatus1=0;
                int totalStatus2=0;
                int totalStatus3=0;
                int countTotalData=0;
                int countTotalDataGift=0;
                double inHandAmount=0.0;
                double receivable =0.0;
                products = Product.select(db,"1=1");
                allCompany = Company.getCompanyStructOnlyProductAndGift(products);
                printData+="\n\nIn Hand\n";
                printData += getDataStringString("Company",10,false);
                printData +=   getDataStringString("Gift",5,true);
                printData +=   getDataStringString("Stock",8,true);
                printData +=   getDataStringString("Dlvrable",9,true);
                printData+="--------------------------------";

                int totalGift=0;
                int totalStock=0;
                int totalDeliverable=0;
                for(Company cCompany : allCompany)
                {
                    int tStatus1=0;
                    int tStatus2=0;
                    int tStatus3=0;
                    int totalAllProduct=0;
                    int totalAllGift=0;
                    for(Product cProudct : cCompany.getProductAndGift())
                    {
                        String con1 = db.COLUMN_i_product_id+"="+cProudct.getId();
                        ArrayList<Invoice> invoices = Invoice.select(db,con1);

                        if(cProudct.getProduct_type().toUpperCase().equals("GIFT"))
                        {
                            totalAllGift+= (cProudct.getTotal_sku()-cProudct.getSold_sku());
                            countTotalDataGift+=(cProudct.getTotal_sku()-cProudct.getSold_sku());
                        }
                        else
                        {
                            totalAllProduct+=(cProudct.getTotal_sku()-cProudct.getSold_sku());
                            countTotalData+=(cProudct.getTotal_sku()-cProudct.getSold_sku());
                        }

                        for(Invoice cInvoice : invoices)
                        {
                            if(cInvoice.getStatus()==1)
                            {
                                tStatus1+=cInvoice.getQuantity();
                                totalStatus1+=cInvoice.getQuantity();
                                receivable += (cInvoice.getQuantity()*cInvoice.getUnit_price());
                            }
                            else if(cInvoice.getStatus()==2)
                            {
                                inHandAmount += (cInvoice.getQuantity()*cInvoice.getUnit_price());
                                tStatus2+=cInvoice.getQuantity();
                                totalStatus2+=cInvoice.getQuantity();
                            }
                            else if(cInvoice.getStatus()==3)
                            {
                                inHandAmount += (cInvoice.getQuantity()*cInvoice.getUnit_price());
                                tStatus3+=cInvoice.getQuantity();
                                totalStatus3+=cInvoice.getQuantity();
                            }
                        }
                    }
                    totalGift+=totalAllGift;
                    totalStock+=totalAllProduct;
                    totalDeliverable+=(tStatus1+tStatus2);
                    printData += getDataStringString(cCompany.getCompany_name(),10,false);
                    printData +=   getDataStringString(String.valueOf(totalAllGift),5,true);
                    printData +=   getDataStringString(String.valueOf(totalAllProduct),8,true);
                    printData +=   getDataStringString(String.valueOf(tStatus1+tStatus2),9,true);
                }
                printData+="--------------------------------";
                printData+= getDataStringString("Total",10,false);
                printData +=   getDataStringString(String.valueOf(totalGift),5,true);
                printData +=   getDataStringString(String.valueOf(totalStock),8,true);
                printData +=   getDataStringString(String.valueOf(totalDeliverable),9,true);
                //printData+="Total"+getDataStringString(String.valueOf(totalQuantity),10,true)+getDataStringString(String.valueOf((int) Math.ceil(totalAmout)),17,true)+"\n";
               // printData+="In Word: "+   numberToWord((int) Math.ceil(totalAmout))+" Taka Only";
                printData+="--------------------------------";

                System.out.println(printData);
                b.findBT(printData);
            }
        });

    }
}
