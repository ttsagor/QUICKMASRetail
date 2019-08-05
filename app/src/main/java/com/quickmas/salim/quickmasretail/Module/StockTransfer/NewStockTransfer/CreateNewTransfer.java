package com.quickmas.salim.quickmasretail.Module.StockTransfer.NewStockTransfer;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.quickmas.salim.quickmasretail.Database.DBInitialization;
import com.quickmas.salim.quickmasretail.Model.POS.CashPaymentReceive;
import com.quickmas.salim.quickmasretail.Model.POS.PosCustomer;
import com.quickmas.salim.quickmasretail.Model.POS.PosProduct;
import com.quickmas.salim.quickmasretail.Model.POS.PurchaseLog;
import com.quickmas.salim.quickmasretail.Model.POS.PurchaseLogInvoice;
import com.quickmas.salim.quickmasretail.Model.POS.SalesPerson;
import com.quickmas.salim.quickmasretail.Model.POS.StockTransferDetails;
import com.quickmas.salim.quickmasretail.Model.POS.StockTransferHead;
import com.quickmas.salim.quickmasretail.Model.POS.Warehouse;
import com.quickmas.salim.quickmasretail.Model.System.DashboardMenu;
import com.quickmas.salim.quickmasretail.Model.System.TextString;
import com.quickmas.salim.quickmasretail.Model.System.User;
import com.quickmas.salim.quickmasretail.R;
import com.quickmas.salim.quickmasretail.Service.UploadData;
import com.quickmas.salim.quickmasretail.Structure.GoodsTransferAPI;
import com.quickmas.salim.quickmasretail.Utility.DateTimeCalculation;
import com.quickmas.salim.quickmasretail.Utility.DebugHelper;
import com.quickmas.salim.quickmasretail.Utility.FontSettings;
import com.quickmas.salim.quickmasretail.Utility.TypeConvertion;
import com.quickmas.salim.quickmasretail.Utility.UI.ScrollListView;
import com.quickmas.salim.quickmasretail.Utility.UI.ViewData;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

import static com.quickmas.salim.quickmasretail.Model.System.DashboardMenu.selectByVarname;
import static com.quickmas.salim.quickmasretail.Service.ApiSettings.url_stock_transfer_upload;
import static com.quickmas.salim.quickmasretail.Utility.DateTimeCalculation.getCurrentDateTime;
import static com.quickmas.salim.quickmasretail.Utility.FontSettings.getFont;
import static com.quickmas.salim.quickmasretail.Utility.NetworkConfiguration.isInternetOn;
import static com.quickmas.salim.quickmasretail.Utility.UIComponent.setLinearLayoutBackground;
import static com.quickmas.salim.quickmasretail.Utility.UIComponent.spinnerDataLoad;
import static com.quickmas.salim.quickmasretail.Utility.UIComponent.updateListViewHeight;

public class CreateNewTransfer extends AppCompatActivity {
    DBInitialization db;
    Context mContex;
    ArrayAdapter<String> quantitySpinnerArrayAdapter;
    //ArrayAdapter<String> spinnerArrayAdapter;
    ArrayList<StockTransferDetails> stockTransferDetails = new ArrayList<>();
    AutoCompleteTextView product_list;
    //Spinner spinner;
    Spinner quantitySpinner;
    Button add_coa;
    int updatePosition=-1;
    int rec_id=0;
    ProgressDialog progressDoalog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("create-new-transfer"));
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_create_new_transfer);
        mContex = this;
        db = new DBInitialization(this,null,null,1);
        /*DashboardMenu cMenu =  selectByVarname(db,"dashboard_sell_pos_list");
        LinearLayout headimg = (LinearLayout) findViewById(R.id.image1);
        headimg = setLinearLayoutBackground(headimg, cMenu.getImage(), this);
        TextView headtext = (TextView) findViewById(R.id.text1);
        headtext.setTypeface(getFont(this));
        headtext.setText("Stock Transfer");*/



        Intent iin= getIntent();
        Bundle b = iin.getExtras();
        if(b!=null)
        {
            rec_id =(Integer) b.get("id");
        }
        final ImageView close_tab = (ImageView) findViewById(R.id.close_tab);

        Button receive  = FontSettings.setTextFont((Button) findViewById(R.id.receive),mContex,db,"Save Transfer");
        Button remove_coa  = FontSettings.setTextFont((Button) findViewById(R.id.remove_coa),mContex,db,"Remove");
        add_coa  = FontSettings.setTextFont((Button) findViewById(R.id.add_coa),mContex,db,"Add");


        final User cUser = new User();
        cUser.select(db,"1=1");


        close_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        product_list = (AutoCompleteTextView) findViewById(R.id.product_list);
        final ArrayList<PosProduct> posProducts = PosProduct.select(db,db.COLUMN_pos_product_quantity_left+">0");
        final ArrayList<String> ArrSaleP = new ArrayList<>();

        for(PosProduct p: posProducts) {
            ArrSaleP.add(p.getProduct_name());
        }
        product_list.setHint(TextString.textSelectByVarname(db,"Product Name").getText());
        ArrayAdapter<String> adapterS = new ArrayAdapter<String>(mContex,android.R.layout.select_dialog_item,ArrSaleP);
        product_list.setThreshold(1);//will start working from first character
        product_list.setAdapter(adapterS);//setting the adapter data into the AutoCompleteTextView
        product_list.setTextColor(Color.RED);

        final Spinner warehouse_list = (Spinner) findViewById(R.id.warehouse_list);

        warehouse_list.setAdapter(spinnerDataLoad(mContex,db,db.COLUMN_warehouse_name,db.TABLE_warehouse,db.COLUMN_warehouse_name+"!=\""+cUser.getSelected_location()+"\"",db.COLUMN_warehouse_name,"Select Warehouse"));


        product_list.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                for(PosProduct p: posProducts)
                {
                    if(p.getProduct_name().equals(s.toString()))
                    {
                        quantitySpinner = (Spinner) findViewById(R.id.quantity_list);
                        ArrayList<String> quantity = new ArrayList<>();
                        int prev_qnty = 0;
                        if(rec_id>0)
                        {
                            ArrayList<StockTransferDetails> stockTransferDetails1 = StockTransferDetails.select(db,db.COLUMN_stock_transfer_details_transfer_id+"="+rec_id+" AND "+db.COLUMN_stock_transfer_details_product_id+"="+p.getId());
                            if(stockTransferDetails1.size()>0)
                            {
                                prev_qnty = stockTransferDetails1.get(0).getQuantity();
                            }
                        }
                        for(int i=0;i<= p.getQuantity_left()+prev_qnty;i++)
                        {
                            quantity.add(String.valueOf(i));
                        }
                        quantitySpinnerArrayAdapter = new ArrayAdapter<String>(mContex,R.layout.spinner_item,quantity){
                            @Override
                            public View getDropDownView(int position, View convertView,
                                                        ViewGroup parent) {
                                View view = super.getDropDownView(position, convertView, parent);
                                TextView tv = (TextView) view;
                                tv.setBackgroundColor(Color.parseColor("#ffffff"));
                                return view;
                            }
                            @Override
                            public boolean isEnabled(int position) {
                                return true;
                            }
                        };
                        quantitySpinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
                        quantitySpinner.setAdapter(quantitySpinnerArrayAdapter);

                        for(StockTransferDetails transferDetails : stockTransferDetails)
                        {
                            if(transferDetails.getProduct_id()==p.getId())
                            {
                                quantitySpinner.setSelection(transferDetails.getQuantity()-1);
                                break;
                            }
                        }
                        break;
                    }
                    else
                    {
                        quantitySpinner = (Spinner) findViewById(R.id.quantity_list);
                        ArrayList<String> quantity = new ArrayList<>();
                        quantity.add("0");
                        quantitySpinnerArrayAdapter = new ArrayAdapter<String>(mContex,R.layout.spinner_item,quantity){
                            @Override
                            public View getDropDownView(int position, View convertView,
                                                        ViewGroup parent) {
                                View view = super.getDropDownView(position, convertView, parent);
                                TextView tv = (TextView) view;
                                tv.setBackgroundColor(Color.parseColor("#ffffff"));
                                return view;
                            }
                            @Override
                            public boolean isEnabled(int position) {
                                return true;
                            }
                        };
                        quantitySpinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
                        quantitySpinner.setAdapter(quantitySpinnerArrayAdapter);
                    }


                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        quantitySpinner = (Spinner) findViewById(R.id.quantity_list);
        final ListView coa_list = findViewById(R.id.coa_list);
        add_coa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PosProduct posProduct = new PosProduct();
                for(PosProduct p: posProducts)
                {
                    if(p.getProduct_name().equals(product_list.getText().toString().trim()))
                    {
                        posProduct = p;
                        break;
                    }
                }
                if(quantitySpinner.getSelectedItemPosition()==0 || posProduct.getId()==0 || warehouse_list.getSelectedItemPosition()==0)
                {
                    Toasty.error(getApplicationContext(), TextString.textSelectByVarname(db,"Please fill up properly").getText(), Toast.LENGTH_SHORT, true).show();
                    return;
                }

                StockTransferDetails cashPaymentReceive = new StockTransferDetails();
                cashPaymentReceive.setProduct_id(posProduct.getId());
                cashPaymentReceive.setProduct_name(posProduct.getProduct_name());
                cashPaymentReceive.setQuantity(TypeConvertion.parseInt(quantitySpinner.getSelectedItem().toString()));
                cashPaymentReceive.setUnit_price(posProduct.getPrice());
                cashPaymentReceive.setStatus(0);
                cashPaymentReceive.setDate_time(getCurrentDateTime());


                if(add_coa.getText().equals("Update"))
                {
                    stockTransferDetails.set(updatePosition,cashPaymentReceive);
                    add_coa.setText("Add");
                    updatePosition=-1;
                }
                else
                {
                    boolean flag=true;
                    for(int i=0;i<stockTransferDetails.size();i++)
                    {
                        StockTransferDetails transferDetails = stockTransferDetails.get(i);
                        if(transferDetails.getProduct_id() == cashPaymentReceive.getProduct_id())
                        {
                            cashPaymentReceive.setQuantity(cashPaymentReceive.getQuantity());
                            stockTransferDetails.set(i,cashPaymentReceive);
                            flag=false;
                            break;
                        }
                    }
                    if(flag)
                    {
                        stockTransferDetails.add(cashPaymentReceive);
                    }
                }
                product_list.setText("");
                quantitySpinner.setSelection(0);
                if(stockTransferDetails.size()>0)
                {
                    warehouse_list.setEnabled(false);
                }
                else
                {
                    warehouse_list.setEnabled(true);
                }
                ScrollListView.loadListViewUpdateHeight(mContex, coa_list, R.layout.adaptar_coa_list, stockTransferDetails, "transferList", 0, 100, true);
            }
        });
        remove_coa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        User cSystemInfo = new User();
                        cSystemInfo.select(db,"1=1");
                        if(updatePosition>-1)
                        {
                            stockTransferDetails.remove(updatePosition);
                            ScrollListView.loadListViewUpdateHeight(mContex, coa_list, R.layout.adaptar_coa_list, stockTransferDetails, "transferList", 0, 100, true);
                            product_list.setText("");
                            quantitySpinner.setSelection(0,true);
                            updatePosition=-1;
                            if(stockTransferDetails.size()>0)
                            {
                                warehouse_list.setEnabled(false);
                            }
                            else
                            {
                                warehouse_list.setEnabled(true);
                            }
                            add_coa.setText("Add");
                        }
            }
        });

        receive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        if(warehouse_list.getSelectedItemPosition()==0)
                        {
                            Toasty.error(getApplicationContext(), TextString.textSelectByVarname(db,"Please fill up Warehouse").getText(), Toast.LENGTH_SHORT, true).show();
                            return;
                        }
                        if(stockTransferDetails.size()>0) {
                            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    switch (which){
                                        case DialogInterface.BUTTON_POSITIVE:
                                            User cSystemInfo = new User();
                                            cSystemInfo.select(db, "1=1");
                                            double total_amount=0.0;
                                            int total_quantity=0;
                                            String dateTime = getCurrentDateTime();

                                            if(rec_id>0)
                                            {
                                                ArrayList<StockTransferDetails> stockTransferDetails = StockTransferDetails.select(db,db.COLUMN_stock_transfer_details_transfer_id+"="+rec_id);
                                                for(StockTransferDetails details : stockTransferDetails)
                                                {
                                                    PosProduct posProduct = PosProduct.select(db,db.COLUMN_pos_product_id+"="+details.getProduct_id()).get(0);
                                                    posProduct.setQuantity_left(posProduct.getQuantity_left() + details.getQuantity());
                                                    posProduct.update(db);
                                                }
                                                StockTransferDetails.delete(db,db.COLUMN_stock_transfer_details_transfer_id+"="+rec_id);
                                            }

                                            for(StockTransferDetails details : stockTransferDetails)
                                            {
                                                total_amount+=(details.getUnit_price()*details.getQuantity());
                                                total_quantity+= details.getQuantity();
                                            }


                                            StockTransferHead stockTransferHead = new StockTransferHead();
                                            if(rec_id>0)
                                            {
                                                stockTransferHead.setId(rec_id);
                                            }
                                            stockTransferHead.setTotal_amount(total_amount);
                                            stockTransferHead.setTotal_quantity(total_quantity);
                                            stockTransferHead.setTransfer_by(cUser.getSelected_location());
                                            stockTransferHead.setTransfer_to(warehouse_list.getSelectedItem().toString());
                                            stockTransferHead.setDate_time(dateTime);
                                            stockTransferHead.setEntry_by(cUser.getUser_name());
                                            stockTransferHead.setPos_by(cUser.getSelected_pos());
                                            stockTransferHead.setStatus(0);
                                            stockTransferHead.insert(db);


                                            int receive_id = rec_id;
                                            if(rec_id==0)
                                            {
                                                receive_id = StockTransferHead.getMax(db);
                                            }
                                            for(StockTransferDetails details : stockTransferDetails)
                                            {
                                                PosProduct posProduct = PosProduct.select(db,db.COLUMN_pos_product_id+"="+details.getProduct_id()).get(0);
                                                posProduct.setQuantity_left(posProduct.getQuantity_left() - details.getQuantity());
                                                posProduct.update(db);
                                                details.setTransfer_id(receive_id);
                                                details.insert(db);
                                            }

                                            if(cUser.getActive_online()==1 && isInternetOn(mContex))
                                            {
                                                updateData(receive_id);
                                            }
                                            else
                                            {
                                                Toasty.success(getApplicationContext(),TextString.textSelectByVarname(db,"pos_sale_payment_popbox_invoice_complete").getText(), Toast.LENGTH_LONG).show();
                                                finish();
                                            }

                                            break;

                                        case DialogInterface.BUTTON_NEGATIVE:
                                            //No button clicked
                                            break;
                                    }
                                }
                            };

                            AlertDialog.Builder builder = new AlertDialog.Builder(mContex);
                            builder.setMessage(TextString.textSelectByVarname(db,"are you sure?").getText()).setPositiveButton(TextString.textSelectByVarname(db,"yes").getText(), dialogClickListener)
                                    .setNegativeButton(TextString.textSelectByVarname(db,"no").getText(), dialogClickListener).show();

                        }

            }
        });

        //if(rec_id>0)
        {
            stockTransferDetails = StockTransferDetails.select(db,db.COLUMN_stock_transfer_details_transfer_id+"="+rec_id);
            ScrollListView.loadListViewUpdateHeight(mContex, coa_list, R.layout.adaptar_coa_list, stockTransferDetails, "transferList", 0, 100, true);
        }

    }
    int old_tr_id=0;
    public void updateData(int id)
    {
        old_tr_id = id;
        progressDoalog = new ProgressDialog(mContex);

        try {
            progressDoalog.setMessage("Product Creating....");
            progressDoalog.setTitle("Please Wait");
            progressDoalog.show();

        } catch (Exception e) {

        }
        ArrayList<StockTransferDetails> c = StockTransferDetails.select(db,db.COLUMN_stock_transfer_details_transfer_id+"="+id);
        ArrayList<GoodsTransferAPI> p = new ArrayList<>();
        for(StockTransferDetails cc : c)
        {
            try {
                StockTransferHead cs = StockTransferHead.select(db, db.COLUMN_stock_transfer_id + "='" + cc.transfer_id + "'").get(0);
                GoodsTransferAPI pp = new GoodsTransferAPI(cs, cc);
                p.add(pp);
            }catch (Exception e){}
        }
        final User cSystemInfo = new User();
        cSystemInfo.select(db,"1=1");
        String username = cSystemInfo.getUser_name();
        String password = cSystemInfo.getPassword();
        String company = cSystemInfo.getCompany_id();
        String location = cSystemInfo.getSelected_location();
        String pos = cSystemInfo.getSelected_pos();
        UploadData uploadData = new UploadData();
        uploadData.data = p;
        uploadData.url=url_stock_transfer_upload  + "user_name="+username+"&password="+password+"&company="+company+"&"+"&location="+location+"&pos="+pos+"&";
        uploadData.uploaddata(this,this,"",this,"uploadComplete");

    }
    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String position = intent.getStringExtra("position");

            try {
                StockTransferDetails stockTransfer = stockTransferDetails.get(TypeConvertion.parseInt(position));

                product_list.setText(stockTransfer.getProduct_name());
                quantitySpinner.setSelection(((ArrayAdapter<String>) quantitySpinner.getAdapter()).getPosition(String.valueOf(stockTransfer.getQuantity())));
                add_coa.setText("Update");
                updatePosition = TypeConvertion.parseInt(position);
            }catch (Exception e){}
        }
    };


    public void uploadComplete(ArrayList<String> data)
    {
        for(String rs : data)
        {
            try{
                System.out.println(rs);
                JSONArray jr = new JSONArray(data.get(0));
                if(rs.toString().charAt(0)=='[')
                {
                    jr = new JSONArray(rs.toString());
                }
                for (int i = 0; i < jr.length(); i++) {
                    try{
                        JSONObject jb = (JSONObject) jr.getJSONObject(i);
                        try {
                            String new_po_id = jb.get("transfer_no").toString();
                            String new_id = new_po_id.split("-")[new_po_id.split("-").length - 1];
                            StockTransferHead.update(db, db.COLUMN_stock_transfer_id + "=" + new_id, db.COLUMN_stock_transfer_id + "=" + old_tr_id);
                            StockTransferDetails.update(db, db.COLUMN_stock_transfer_details_transfer_id + "=" + new_id, db.COLUMN_stock_transfer_details_transfer_id + "=" + old_tr_id);
                        }catch (Exception e){}

                    }catch (Exception e){}
                }

            }catch (Exception e){}
        }
        progressDoalog.dismiss();
        Toasty.success(getApplicationContext(),TextString.textSelectByVarname(db,"pos_sale_payment_popbox_invoice_complete").getText(), Toast.LENGTH_LONG).show();
        finish();
    }

    public void transferList(final ViewData data)
    {
        StockTransferDetails stockTransferDetails = (StockTransferDetails) data.object;

        FontSettings.setTextFont((TextView) data.view.findViewById(R.id.coa),mContex,stockTransferDetails.getProduct_name());
        FontSettings.setTextFont((TextView) data.view.findViewById(R.id.remark),mContex,String.valueOf(stockTransferDetails.getQuantity()));
        FontSettings.setTextFont((TextView) data.view.findViewById(R.id.amount),mContex,String.valueOf(stockTransferDetails.getUnit_price()));
        Button update = FontSettings.setTextFont((Button) data.view.findViewById(R.id.update),mContex,String.valueOf("Update"));
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("create-new-transfer");
                intent.putExtra("position",String.valueOf(data.position));
                LocalBroadcastManager.getInstance(mContex).sendBroadcast(intent);
            }
        });
    }
}
