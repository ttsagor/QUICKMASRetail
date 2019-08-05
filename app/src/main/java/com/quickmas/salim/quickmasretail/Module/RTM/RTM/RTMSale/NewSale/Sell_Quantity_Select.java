package com.quickmas.salim.quickmasretail.Module.RTM.RTM.RTMSale.NewSale;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.quickmas.salim.quickmasretail.Database.DBInitialization;
import com.quickmas.salim.quickmasretail.Model.System.DashboardMenu;
import com.quickmas.salim.quickmasretail.Model.RTM.Invoice;
import com.quickmas.salim.quickmasretail.Model.RTM.Product;
import com.quickmas.salim.quickmasretail.Model.System.DashboardSubMenu;
import com.quickmas.salim.quickmasretail.R;
import com.quickmas.salim.quickmasretail.Utility.FileManagerSetting;
import com.quickmas.salim.quickmasretail.Utility.UIComponent;

import java.util.Calendar;

import static com.quickmas.salim.quickmasretail.Model.System.DashboardMenu.selectByVarname;
import static com.quickmas.salim.quickmasretail.Model.System.TextString.textSelectByVarname;
import static com.quickmas.salim.quickmasretail.Utility.FileManagerSetting.getImageFromFile;
import static com.quickmas.salim.quickmasretail.Utility.FontSettings.getFont;
import static com.quickmas.salim.quickmasretail.Utility.FontSettings.setTextFont;
import static com.quickmas.salim.quickmasretail.Utility.UIComponent.setLinearLayoutBackground;

public class Sell_Quantity_Select extends AppCompatActivity {

    /////-------------for advance sale max limit is lifted
    String invoiceOutlet;
    String id = "-1";
    int selectedQuantity=0;
    TextView sellquantity_selected_count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_sell__quantity__select);

        final DBInitialization db = new DBInitialization(this,null,null,1);

        DashboardMenu cMenu =  selectByVarname(db,"dashboard_sell_rtm");
        LinearLayout headimg = (LinearLayout) findViewById(R.id.image1);
        headimg = setLinearLayoutBackground(headimg, cMenu.getImage(), this);
        TextView headtext = (TextView) findViewById(R.id.text1);
        headtext.setTypeface(getFont(this));
        headtext.setText(DashboardSubMenu.getMenuText(db, "rtm_activity_1"));

        TextView quantity = (TextView) findViewById(R.id.quantity);
        TextView sku = (TextView) findViewById(R.id.sku);

        sellquantity_selected_count = (EditText) findViewById(R.id.sellquantity_selected_count);
        ImageView myImageView = (ImageView) findViewById(R.id.myImageView);

        Button sellQuantity_sell_quantity_submit  = (Button) findViewById(R.id.sellQuantity_sell_quantity_submit);
        TextView sellquantity_selected = (TextView) findViewById(R.id.sellquantity_selected);
        sellQuantity_sell_quantity_submit = setTextFont(sellQuantity_sell_quantity_submit,this,db,"sellQuantity_sell_quantity_submit");
        sellquantity_selected = setTextFont(sellquantity_selected,this,db,"sellQuantity_select");

        Intent iin= getIntent();
        Bundle b = iin.getExtras();
        if(b!=null)
        {
            id =(String) b.get("id");
            invoiceOutlet =(String) b.get("invoiceOutlet");
        }


        final Product cProduct = Product.select(db,db.COLUMN_product_id+"="+id).get(0);

        try {
            String con = db.COLUMN_i_product_id + "=" + id + " AND " + db.COLUMN_i_status + "=0";
            selectedQuantity = Invoice.select(db,con).get(0).getQuantity();
            sellquantity_selected_count.setText(String.valueOf(selectedQuantity));
        }catch (Exception e){
        }
        String quantityCount=String.valueOf(cProduct.getSku_qty());
        int color = Color.parseColor("#FFF5F19E");
        SpannableString spannableString = new SpannableString(quantityCount);
        spannableString = UIComponent.buildBackgroundColorSpan(spannableString, quantityCount, quantityCount, color);

        quantity.setText(spannableString);
        sku.setText(cProduct.getSku());
        sku.setTypeface(getFont(this));
        Bitmap bt = FileManagerSetting.getImageFromFile(getApplicationContext(),cProduct.getPhoto());
        if(bt!=null)
        {
            myImageView.setImageBitmap(FileManagerSetting.getImageFromFile(getApplicationContext(),cProduct.getPhoto()));
        }
        else
        {

        }


        Button p1000  = (Button) findViewById(R.id.p1000);
        Button p500  = (Button) findViewById(R.id.p500);
        Button p100  = (Button) findViewById(R.id.p100);
        Button p50  = (Button) findViewById(R.id.p50);
        Button p20  = (Button) findViewById(R.id.p20);
        Button p10  = (Button) findViewById(R.id.p10);
        Button p5  = (Button) findViewById(R.id.p5);
        Button p1  = (Button) findViewById(R.id.p1);

        Button m1000  = (Button) findViewById(R.id.m1000);
        Button m500  = (Button) findViewById(R.id.m500);
        Button m100  = (Button) findViewById(R.id.m100);
        Button m50  = (Button) findViewById(R.id.m50);
        Button m20  = (Button) findViewById(R.id.m20);
        Button m10  = (Button) findViewById(R.id.m10);
        Button m5  = (Button) findViewById(R.id.m5);
        Button m1  = (Button) findViewById(R.id.m1);

        p1000.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if(totalQuantity >= selectedQuantity+1000)
                {
                    selectedQuantity+=1000;
                    sellquantity_selected_count.setText(String.valueOf(selectedQuantity));
                }
            }
        });

        p500.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if(totalQuantity >= selectedQuantity+500)
                {
                    selectedQuantity+=500;
                    sellquantity_selected_count.setText(String.valueOf(selectedQuantity));
                }
            }
        });

        p100.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if(totalQuantity >= selectedQuantity+100)
                {
                    selectedQuantity+=100;
                    sellquantity_selected_count.setText(String.valueOf(selectedQuantity));
                }
            }
        });

        p50.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if(totalQuantity >= selectedQuantity+50)
                {
                    selectedQuantity+=50;
                    sellquantity_selected_count.setText(String.valueOf(selectedQuantity));
                }
            }
        });

        p20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if(totalQuantity >= selectedQuantity+20)
                {
                    selectedQuantity+=20;
                    sellquantity_selected_count.setText(String.valueOf(selectedQuantity));
                }
            }
        });

        p10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if(totalQuantity >= selectedQuantity+10)
                {
                    selectedQuantity+=10;
                    sellquantity_selected_count.setText(String.valueOf(selectedQuantity));
                }
            }
        });

        p5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if(totalQuantity >= selectedQuantity+5)
                {
                    selectedQuantity+=5;
                    sellquantity_selected_count.setText(String.valueOf(selectedQuantity));
                }
            }
        });
        p1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if(totalQuantity >= selectedQuantity+1)
                {
                    selectedQuantity+=1;
                    sellquantity_selected_count.setText(String.valueOf(selectedQuantity));
                }
            }
        });




        m1000.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedQuantity - 1000>=0){
                    selectedQuantity-=1000;
                    sellquantity_selected_count.setText(String.valueOf(selectedQuantity));
                }
            }
        });

        m500.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedQuantity - 500>=0){
                    selectedQuantity-=500;
                    sellquantity_selected_count.setText(String.valueOf(selectedQuantity));
                }
            }
        });

        m100.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedQuantity - 100>=0){
                    selectedQuantity-=100;
                    sellquantity_selected_count.setText(String.valueOf(selectedQuantity));
                }
            }
        });

        m50.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedQuantity - 50>=0){
                    selectedQuantity-=50;
                    sellquantity_selected_count.setText(String.valueOf(selectedQuantity));
                }
            }
        });

        m20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedQuantity - 20>=0){
                    selectedQuantity-=20;
                    sellquantity_selected_count.setText(String.valueOf(selectedQuantity));
                }
            }
        });

        m10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedQuantity - 10>=0){
                    selectedQuantity-=10;
                    sellquantity_selected_count.setText(String.valueOf(selectedQuantity));
                }
            }
        });

        m5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedQuantity - 5>=0){
                    selectedQuantity-=5;
                    sellquantity_selected_count.setText(String.valueOf(selectedQuantity));
                }
            }
        });
        m1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedQuantity - 1>=0){
                    selectedQuantity-=1;
                    sellquantity_selected_count.setText(String.valueOf(selectedQuantity));
                }
            }
        });

        sellquantity_selected_count.addTextChangedListener(new TextWatcher() {
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
                try {
                    selectedQuantity = Integer.parseInt(s.toString());
                }
                catch (Exception e)
                {
                    selectedQuantity=0;
                    sellquantity_selected_count.setText(String.valueOf(selectedQuantity));
                }
            }
        });


        sellQuantity_sell_quantity_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(selectedQuantity>0)
                {
                    Invoice invoice = new Invoice();
                    invoice.setInvoice_id(Invoice.getMaxInvoice(db) + 1);
                    invoice.setOutlet_id(Integer.parseInt(invoiceOutlet));
                    invoice.setRoute(cProduct.getRoute());
                    invoice.setProduct_id(Integer.parseInt(id));
                    invoice.setQuantity(selectedQuantity);
                    invoice.setUnit_price(cProduct.getSku_price());

                    Calendar calendar = Calendar.getInstance();
                    java.util.Date now = calendar.getTime();
                    java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
                    //String currentDate = DateFormat.format("yyyy-MM-dd hh:mm:ss", currentTimestamp).toString();
                    invoice.setInvoice_date(currentTimestamp.toString());
                    invoice.setStatus(0);
                    invoice.insert(db);
                    Intent i = new Intent(Sell_Quantity_Select.this, Sell_Invoice_Company.class);
                    startActivity(i);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),textSelectByVarname(db,"sellInvoice_invoice_no_data_selected").getText(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
