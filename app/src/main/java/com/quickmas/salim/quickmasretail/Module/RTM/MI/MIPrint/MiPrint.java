package com.quickmas.salim.quickmasretail.Module.RTM.MI.MIPrint;

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
import android.text.format.DateFormat;
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
import com.quickmas.salim.quickmasretail.Model.RTM.MI.MIInvoice;
import com.quickmas.salim.quickmasretail.Model.RTM.MI.MIInvoiceHead;
import com.quickmas.salim.quickmasretail.Model.RTM.Product;
import com.quickmas.salim.quickmasretail.Model.System.DashboardMenu;
import com.quickmas.salim.quickmasretail.Model.System.DashboardSubMenu;
import com.quickmas.salim.quickmasretail.Model.System.User;
import com.quickmas.salim.quickmasretail.R;
import com.quickmas.salim.quickmasretail.Structure.Company;
import com.quickmas.salim.quickmasretail.Utility.FileManagerSetting;

import java.util.ArrayList;
import java.util.Calendar;

import static com.quickmas.salim.quickmasretail.Model.System.DashboardMenu.selectByVarname;
import static com.quickmas.salim.quickmasretail.Utility.FontSettings.getFont;
import static com.quickmas.salim.quickmasretail.Utility.FontSettings.setTextFont;
import static com.quickmas.salim.quickmasretail.Utility.UIComponent.setLinearLayoutBackground;

public class MiPrint extends AppCompatActivity {
    DBInitialization db;
    int amountCount=0;
    int uPriceCount=0;
    int totalPriceCount=0;
    Context mContex;
    User cSystemInfo = new User();
    ArrayList<Uri> image_list = new ArrayList<>();
    ArrayList<MIInvoice> invoices = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_crmprint);

        mContex = this;
        db = new DBInitialization(this,null,null,1);

        cSystemInfo.select(db,"1=1");

        DashboardMenu cMenu =  selectByVarname(db,"dashboard_sell_rtm");
        LinearLayout headimg = (LinearLayout) findViewById(R.id.image1);
        headimg = setLinearLayoutBackground(headimg, cMenu.getImage(), this);
        TextView headtext = (TextView) findViewById(R.id.text1);
        headtext.setTypeface(getFont(this));
        headtext.setText(DashboardSubMenu.getMenuText(db, "rtm_activity_list_3"));

        TextView sell_invoice_date_txt = (TextView) findViewById(R.id.sell_invoice_date_txt);
        final TextView sell_invoice_date = (TextView) findViewById(R.id.sell_invoice_date);
        TextView sell_invoice_invoice_txt = (TextView) findViewById(R.id.sell_invoice_invoice_txt);
        TextView sell_invoice_invoice = (TextView) findViewById(R.id.sell_invoice_invoice);
        TextView sell_invoice_outlet_txt = (TextView) findViewById(R.id.sell_invoice_outlet_txt);
        TextView sell_invoice_outlet = (TextView) findViewById(R.id.sell_invoice_outlet);
        TextView sku = (TextView) findViewById(R.id.sku);
        TextView qnty = (TextView) findViewById(R.id.qnty);
        TextView description = (TextView) findViewById(R.id.description);

        Button sell_invoice_proceed = (Button) findViewById(R.id.sell_invoice_proceed);
        Button sell_invoice_add_new = (Button) findViewById(R.id.sell_invoice_add_new);
        Button sell_invoice_discard = (Button) findViewById(R.id.sell_invoice_discard);
        Button sell_invoice_add_photo = (Button) findViewById(R.id.sell_invoice_add_photo);

        ListView invoice_list = (ListView) findViewById(R.id.invoice_list);

        sell_invoice_date_txt = setTextFont(sell_invoice_date_txt, this,db,"sellInvoice_sell_date");

        sell_invoice_invoice_txt = setTextFont(sell_invoice_invoice_txt, this,db,"sellInvoice_sell_invoice");
        sell_invoice_outlet_txt = setTextFont(sell_invoice_outlet_txt, this,db,"sellInvoice_sell_outlet");
        description = setTextFont(description, this,db,"Details");
        sku = setTextFont(sku, this,db,"Description");
        qnty = setTextFont(qnty, this,db,"Remark");

        sell_invoice_proceed = setTextFont(sell_invoice_proceed, this,db,"sellInvoice_invoice_proceed");
        sell_invoice_add_new = setTextFont(sell_invoice_add_new, this,db,"sellInvoice_invoice_add_new");
        sell_invoice_discard = setTextFont(sell_invoice_discard, this,db,"sellInvoice_sell_invoice_discard");
        sell_invoice_add_photo = setTextFont(sell_invoice_add_photo, this,db,"Add Photo");
        Calendar calendar = Calendar.getInstance();
        java.util.Date now = calendar.getTime();
        java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
        String currentDate = DateFormat.format("dd-MM-yyyy", currentTimestamp).toString();

        sell_invoice_date.setTypeface(getFont(this));
        sell_invoice_date.setText(currentDate);

        Intent i= getIntent();
        String in_invoice = i.getStringExtra("invoice_id");

        invoices = MIInvoice.select(db,db.COLUMN_crm_Invoice_invoice_id+"="+in_invoice);

        MIInvoiceHead miInvoiceHead = MIInvoiceHead.select(db,db.COLUMN_crm_Invoice_head_id+"="+in_invoice).get(0);


        String invoice = invoices.get(0).getPrefix() + String.valueOf(invoices.get(0).getInvoice_id());

        sell_invoice_outlet = setTextFont(sell_invoice_outlet, this, miInvoiceHead.getOutlet());
        sell_invoice_invoice = setTextFont(sell_invoice_invoice, this, invoice);


        ArrayList<Product> products = new ArrayList<Product>();

        for(MIInvoice cInvoice : invoices)
        {
            Product product = Product.select(db,db.COLUMN_product_id+"="+cInvoice.getProduct_id()).get(0);
            product.setSub_sku(cInvoice.getComment());
            product.setBrand(cInvoice.getDescription());
            products.add(product);
        }

        for(String img : miInvoiceHead.getPhoto().split(";"))
        {
            image_list.add(Uri.parse(Environment.getExternalStorageDirectory().toString() + "/" + FileManagerSetting.folder_name + "/" + FileManagerSetting.rtm_mi_product + "/" + img.trim()));
        }

        ArrayList<Company> allCompany = Company.getCompanyStruct(products);



        Adaptar_crm_invoice_company adapter = new Adaptar_crm_invoice_company(getApplicationContext(), allCompany,true);
        invoice_list.setAdapter(adapter);

        LayoutInflater layoutinflater = getLayoutInflater();
        ViewGroup footer = (ViewGroup)layoutinflater.inflate(R.layout.adaptar_free_invoice_company,invoice_list,false);

        TextView grand_total = (TextView) footer.findViewById(R.id.sku);
        TextView total_qnty = (TextView) footer.findViewById(R.id.qnty);

        grand_total.setTypeface(getFont(this));
        total_qnty.setTypeface(getFont(this));


        grand_total = setTextFont(grand_total, this,  db, "");
        total_qnty = setTextFont(total_qnty,this,"");

        invoice_list.addFooterView(footer);


        sell_invoice_proceed.setVisibility(View.GONE);
        sell_invoice_add_new.setVisibility(View.GONE);
        sell_invoice_discard.setVisibility(View.GONE);
        sell_invoice_add_photo.setVisibility(View.GONE);

        if(image_list.size()>0)
        {
            createImageList();
        }
    }

    void createImageList()
    {
        final LinearLayout image_list_holder = findViewById(R.id.image_list_holder);
        if(((LinearLayout) image_list_holder).getChildCount() > 0)
        {
            ((LinearLayout) image_list_holder).removeAllViews();
        }

        int lim = image_list.size()/2;
        double div = image_list.size()%2;
        if(div>0)
        {
            lim++;
        }

        for(int i=0;i<lim;i++)
        {
            final int img1c = i * 2;
            final int img2c = i * 2 + 1;

            LayoutInflater inflater = LayoutInflater.from(mContex);
            ViewGroup product = (ViewGroup) inflater.inflate(R.layout.retail_new_product_image_list, image_list_holder, false);

            if(image_list.size()>img1c)
            {
                final Uri img1 = image_list.get(img1c);
                ImageView myImageView1  = (ImageView) product.findViewById(R.id.myImageView1);
                ImageView close_btn1  = (ImageView) product.findViewById(R.id.close_btn1);
                close_btn1.setVisibility(View.GONE);
                close_btn1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //image_list.remove(img1);
                        //createImageList();
                        return;
                    }
                });
                myImageView1.setImageURI(img1);

                myImageView1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LayoutInflater layoutInflater = (LayoutInflater) mContex
                                .getSystemService(LAYOUT_INFLATER_SERVICE);
                        final View popupView = layoutInflater.inflate(R.layout.pop_up_free_product_image_list_product_view, null);
                        final PopupWindow popupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                        final ImageView close_tab = (ImageView) popupView.findViewById(R.id.close_tab);
                        final ImageView image = (ImageView) popupView.findViewById(R.id.image);
                        close_tab.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                popupWindow.dismiss();
                            }
                        });
                        image.setImageURI(img1);
                        popupWindow.setOutsideTouchable(true);
                        popupWindow.setFocusable(true);
                        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        popupWindow.showAtLocation(image_list_holder, Gravity.CENTER, 0, 0);

                    }
                });
            }
            else
            {
                LinearLayout product1  = (LinearLayout) product.findViewById(R.id.product1);
                product1.setVisibility(View.GONE);
            }
            if(image_list.size()>img2c)
            {
                final Uri img2 = image_list.get(img2c);
                ImageView myImageView2  = (ImageView) product.findViewById(R.id.myImageView2);
                ImageView close_btn2  = (ImageView) product.findViewById(R.id.close_btn2);
                close_btn2.setVisibility(View.GONE);
                close_btn2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //image_list.remove(img2);
                        //createImageList();
                        return;
                    }
                });
                myImageView2.setImageURI(img2);

                myImageView2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LayoutInflater layoutInflater = (LayoutInflater) mContex
                                .getSystemService(LAYOUT_INFLATER_SERVICE);
                        final View popupView = layoutInflater.inflate(R.layout.pop_up_free_product_image_list_product_view, null);
                        final PopupWindow popupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                        final ImageView close_tab = (ImageView) popupView.findViewById(R.id.close_tab);
                        final ImageView image = (ImageView) popupView.findViewById(R.id.image);
                        close_tab.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                popupWindow.dismiss();
                            }
                        });
                        image.setImageURI(img2);
                        popupWindow.setOutsideTouchable(true);
                        popupWindow.setFocusable(true);
                        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        popupWindow.showAtLocation(image_list_holder, Gravity.CENTER, 0, 0);

                    }
                });
            }
            else
            {
                LinearLayout product1  = (LinearLayout) product.findViewById(R.id.product2);
                product1.setVisibility(View.GONE);
            }
            image_list_holder.addView(product);
        }
    }

}
