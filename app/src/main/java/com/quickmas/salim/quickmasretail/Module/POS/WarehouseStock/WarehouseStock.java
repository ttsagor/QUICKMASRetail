package com.quickmas.salim.quickmasretail.Module.POS.WarehouseStock;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.quickmas.salim.quickmasretail.Database.DBInitialization;
import com.quickmas.salim.quickmasretail.Model.POS.PosInvoiceHead;
import com.quickmas.salim.quickmasretail.Model.POS.PosProduct;
import com.quickmas.salim.quickmasretail.Model.System.DashboardMenu;
import com.quickmas.salim.quickmasretail.Model.System.User;
import com.quickmas.salim.quickmasretail.Module.Dashboard.MainActivity;
import com.quickmas.salim.quickmasretail.Module.RTM.RTM.RTMInvoiceList.PrintView.Memo_Print;
import com.quickmas.salim.quickmasretail.R;
import com.quickmas.salim.quickmasretail.Utility.BluetoothPrinter;
import com.quickmas.salim.quickmasretail.Utility.DateTimeCalculation;
import com.quickmas.salim.quickmasretail.Utility.FontSettings;
import com.quickmas.salim.quickmasretail.Utility.UI.ScrollListView;
import com.quickmas.salim.quickmasretail.Utility.UI.ViewData;
import com.quickmas.salim.quickmasretail.Utility.UIComponent;

import java.util.ArrayList;

import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_head_customer;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_head_invoice_id;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_product_brand;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_product_name;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_product_sub_type;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_product_title;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_product_type;
import static com.quickmas.salim.quickmasretail.Model.System.DashboardMenu.selectByVarname;
import static com.quickmas.salim.quickmasretail.Utility.FontSettings.getFont;
import static com.quickmas.salim.quickmasretail.Utility.FontSettings.setTextFont;
import static com.quickmas.salim.quickmasretail.Utility.UIComponent.setLinearLayoutBackground;

public class WarehouseStock extends AppCompatActivity {
    DBInitialization db;
    BluetoothPrinter bt;
    ArrayList<PosProduct> products;
    Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_warehouse_stock);
        db = new DBInitialization(this,null,null,1);
        DashboardMenu cMenu =  selectByVarname(db,"dashboard_ware_stock");
        LinearLayout headimg = (LinearLayout) findViewById(R.id.image1);
        headimg = setLinearLayoutBackground(headimg, cMenu.getImage(), this);
        TextView headtext = (TextView) findViewById(R.id.text1);
        headtext.setTypeface(getFont(this));
        headtext.setText(cMenu.getText());
        Button stock_print = setTextFont((Button) findViewById(R.id.stock_print),this,db,"memopopup_print");
        Button sell_invoice_home = setTextFont((Button) findViewById(R.id.sell_invoice_home),this,db,"dataUpload_backToHome");
        bt = new BluetoothPrinter(getApplicationContext(),stock_print);
        mContext=this;
        EditText search_box = setTextFont((EditText) findViewById(R.id.search_box),this,"");
        /*Button search_btn = setTextFont((Button) findViewById(R.id.search_btn),this,"Search");*/

        search_box.addTextChangedListener(new TextWatcher() {
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
                updateListview();
            }
        });

        sell_invoice_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WarehouseStock.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        stock_print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                User cSystemInfo = new User();
                cSystemInfo.select(db,"1=1");
                String printData =  cSystemInfo.getCompany_name()+"\n"+
                        "Date: "+ DateTimeCalculation.getCurrentDateTime()+"\n"+
                        "Stock in Hand"+"\n\n";
                String cData1 = Memo_Print.getDataStringString("SKU",12,false);
                cData1 +=   Memo_Print.getDataStringString("Location",10,true);
                cData1 +=   Memo_Print.getDataStringString("Stock",10,true);
                printData+=cData1;
                printData+="--------------------------------";
                int stock=0;
                for(PosProduct product : products)
                {
                    stock+=product.getQuantity_left();
                    String cData = Memo_Print.getDataStringString(product.getProduct_name(),12,false);
                    cData +=   Memo_Print.getDataStringString(product.getSub_location(),10,true);
                    cData +=   Memo_Print.getDataStringString(String.valueOf(product.getQuantity_left()),10,true);
                    printData+=cData+"\n";
                }
                printData+="--------------------------------";
                String cData = Memo_Print.getDataStringString("Total",12,false);
                cData +=   Memo_Print.getDataStringString("",10,true);
                cData +=   Memo_Print.getDataStringString(String.valueOf(stock),10,true);
                printData+=cData+"\n"+"\n";
                printData+="        ----------------        ";
                printData+=Memo_Print.getDataCentralized(cSystemInfo.getUser_name(),32);
                System.out.println("print: "+printData);
                bt.findBT(printData);
                //bt.closeBT();
            }
        });
        updateListview();
    }
    public void updateListview()
    {
        final ListView stock_list = (ListView) findViewById(R.id.stock_list);
        String txt = ((EditText) findViewById(R.id.search_box)).getText().toString();
        if(txt.equals(""))
        {
            products = PosProduct.select(db,"1=1");
        }
        else
        {
            String con = COLUMN_pos_product_title+" LIKE '%"+txt+"%' OR "+COLUMN_pos_product_name+" LIKE '%"+txt+"%' OR "+COLUMN_pos_product_brand+" LIKE '%"+txt+"%' OR "+COLUMN_pos_product_type+" LIKE '%"+txt+"%' OR "+COLUMN_pos_product_sub_type+" LIKE '%"+txt+"%'";
            products = PosProduct.select(db,con);
        }


        int stock=0;
        for(PosProduct product : products)
        {
            stock+=product.getQuantity_left();
        }


        LayoutInflater layoutinflater = getLayoutInflater();
        ViewGroup footer = (ViewGroup)layoutinflater.inflate(R.layout.stocklist_pos_product,stock_list,false);

        TextView grand_total = (TextView) footer.findViewById(R.id.product_name);
        TextView total_qnty = (TextView) footer.findViewById(R.id.product_qnty);
        TextView total_sell = (TextView) footer.findViewById(R.id.product_sell);

        grand_total.setTextAppearance( this,android.R.style.TextAppearance_Medium);
        total_sell.setTextAppearance( this,android.R.style.TextAppearance_Medium);

        grand_total = setTextFont (grand_total,this,db,"stock_grand_total");
        total_sell = setTextFont(total_sell,this,String.valueOf(stock));
        total_qnty = setTextFont(total_qnty,this,"");



        LayoutInflater layoutinflater1 = getLayoutInflater();
        ViewGroup head = (ViewGroup)layoutinflater.inflate(R.layout.stocklist_pos_product,stock_list,false);

        TextView headgrand_total = (TextView) head.findViewById(R.id.product_name);
        TextView headtotal_qnty = (TextView) head.findViewById(R.id.product_qnty);
        TextView headtotal_sell = (TextView) head.findViewById(R.id.product_sell);

        headgrand_total.setTextAppearance( this,android.R.style.TextAppearance_Medium);
        headtotal_qnty.setTextAppearance( this,android.R.style.TextAppearance_Medium);
        headtotal_sell.setTextAppearance( this,android.R.style.TextAppearance_Medium);

        headgrand_total = setTextFont (headgrand_total,this,db,"SKU");
        headtotal_qnty = setTextFont(headtotal_qnty,this,"Location");
        headtotal_sell = setTextFont(headtotal_sell,this,"Stock");

        //stock_list.addHeaderView(head);
       // stock_list.addFooterView(footer);
        ScrollListView.loadListView(mContext,stock_list,R.layout.stocklist_pos_product,products,"loadData",0,100,true);

    }
    public void loadData(ViewData data)
    {
        LinearLayout loading_layout = (LinearLayout) findViewById(R.id.loading_layout);
        loading_layout.setVisibility(View.GONE);
        PosProduct posProduct = (PosProduct) data.object;

        TextView product_name  = (TextView) data.view.findViewById(R.id.product_name);
        TextView product_total  = (TextView) data.view.findViewById(R.id.product_qnty);
        TextView product_sell  = (TextView) data.view.findViewById(R.id.product_sell);



        product_name.setText(posProduct.getProduct_name());
        product_total.setText(String.valueOf(posProduct.getSub_location()));
        product_sell.setText(String.valueOf(posProduct.getQuantity_left()));
       // TextView product_name = FontSettings.setTextFont((TextView) data.view.findViewById(R.id.product_name),data.context,posProduct.getProduct_name());
        //TextView product_total = FontSettings.setTextFont((TextView) data.view.findViewById(R.id.product_qnty),data.context,posProduct.getSub_location());
        //TextView product_sell = FontSettings.setTextFont((TextView) data.view.findViewById(R.id.product_sell),data.context,String.valueOf(posProduct.getQuantity_left()));

    }
}
