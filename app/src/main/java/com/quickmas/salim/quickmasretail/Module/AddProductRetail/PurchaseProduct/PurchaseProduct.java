package com.quickmas.salim.quickmasretail.Module.AddProductRetail.PurchaseProduct;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.VisibleForTesting;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.quickmas.salim.quickmasretail.Database.DBInitialization;
import com.quickmas.salim.quickmasretail.Model.POS.NewRetailProduct;
import com.quickmas.salim.quickmasretail.Model.POS.PosInvoice;
import com.quickmas.salim.quickmasretail.Model.POS.PosProduct;
import com.quickmas.salim.quickmasretail.Model.POS.PurchaseLog;
import com.quickmas.salim.quickmasretail.Model.POS.PurchaseLogInvoice;
import com.quickmas.salim.quickmasretail.Model.POS.RetailProductTaxGroup;
import com.quickmas.salim.quickmasretail.Model.POS.SupplierCashPaymentReceive;
import com.quickmas.salim.quickmasretail.Model.POS.SupplierCashPaymentReceivedDetails;
import com.quickmas.salim.quickmasretail.Model.POS.SupplierExcessCashReceived;
import com.quickmas.salim.quickmasretail.Model.System.DashboardMenu;
import com.quickmas.salim.quickmasretail.Model.System.TextString;
import com.quickmas.salim.quickmasretail.Model.System.User;
import com.quickmas.salim.quickmasretail.Module.AddProductRetail.PaymentPaid.PaymentPaid;
import com.quickmas.salim.quickmasretail.R;
import com.quickmas.salim.quickmasretail.Service.BaseImageDownload;
import com.quickmas.salim.quickmasretail.Service.DataDownload;
import com.quickmas.salim.quickmasretail.Service.DataloadImage;
import com.quickmas.salim.quickmasretail.Service.UploadData;
import com.quickmas.salim.quickmasretail.Structure.PhotoPathTarDir;
import com.quickmas.salim.quickmasretail.Utility.CallBack;
import com.quickmas.salim.quickmasretail.Utility.DateTimeCalculation;
import com.quickmas.salim.quickmasretail.Utility.DebugHelper;
import com.quickmas.salim.quickmasretail.Utility.FontSettings;
import com.quickmas.salim.quickmasretail.Utility.FormDataValidation;
import com.quickmas.salim.quickmasretail.Utility.TypeConvertion;
import com.quickmas.salim.quickmasretail.Utility.UI.ScrollListView;
import com.quickmas.salim.quickmasretail.Utility.UI.ViewData;
import com.quickmas.salim.quickmasretail.Utility.UIComponent;
import com.tsongkha.spinnerdatepicker.DatePicker;
import com.tsongkha.spinnerdatepicker.DatePickerDialog;
import com.tsongkha.spinnerdatepicker.SpinnerDatePickerDialogBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;

import es.dmoral.toasty.Toasty;

import static com.quickmas.salim.quickmasretail.Model.System.DashboardMenu.selectByVarname;
import static com.quickmas.salim.quickmasretail.Model.System.User.getUserDetails;
import static com.quickmas.salim.quickmasretail.Service.ApiSettings.url_dataSync_download_brand;
import static com.quickmas.salim.quickmasretail.Service.ApiSettings.url_dataSync_download_date_from;
import static com.quickmas.salim.quickmasretail.Service.ApiSettings.url_dataSync_download_date_to;
import static com.quickmas.salim.quickmasretail.Service.ApiSettings.url_dataSync_download_dimensions;
import static com.quickmas.salim.quickmasretail.Service.ApiSettings.url_dataSync_download_discount;
import static com.quickmas.salim.quickmasretail.Service.ApiSettings.url_dataSync_download_discount_type;
import static com.quickmas.salim.quickmasretail.Service.ApiSettings.url_dataSync_download_features;
import static com.quickmas.salim.quickmasretail.Service.ApiSettings.url_dataSync_download_guarantee;
import static com.quickmas.salim.quickmasretail.Service.ApiSettings.url_dataSync_download_hour_from;
import static com.quickmas.salim.quickmasretail.Service.ApiSettings.url_dataSync_download_hour_to;
import static com.quickmas.salim.quickmasretail.Service.ApiSettings.url_dataSync_download_id;
import static com.quickmas.salim.quickmasretail.Service.ApiSettings.url_dataSync_download_includes;
import static com.quickmas.salim.quickmasretail.Service.ApiSettings.url_dataSync_download_location;
import static com.quickmas.salim.quickmasretail.Service.ApiSettings.url_dataSync_download_location_balance;
import static com.quickmas.salim.quickmasretail.Service.ApiSettings.url_dataSync_download_photo;
import static com.quickmas.salim.quickmasretail.Service.ApiSettings.url_dataSync_download_price;
import static com.quickmas.salim.quickmasretail.Service.ApiSettings.url_dataSync_download_product_color;
import static com.quickmas.salim.quickmasretail.Service.ApiSettings.url_dataSync_download_product_name;
import static com.quickmas.salim.quickmasretail.Service.ApiSettings.url_dataSync_download_product_title;
import static com.quickmas.salim.quickmasretail.Service.ApiSettings.url_dataSync_download_sub_location;
import static com.quickmas.salim.quickmasretail.Service.ApiSettings.url_dataSync_download_sub_location_balance;
import static com.quickmas.salim.quickmasretail.Service.ApiSettings.url_dataSync_download_sub_type;
import static com.quickmas.salim.quickmasretail.Service.ApiSettings.url_dataSync_download_tax;
import static com.quickmas.salim.quickmasretail.Service.ApiSettings.url_dataSync_download_type;
import static com.quickmas.salim.quickmasretail.Service.ApiSettings.url_dataSync_download_weight;
import static com.quickmas.salim.quickmasretail.Service.ApiSettings.url_dataSync_download_wholesale;
import static com.quickmas.salim.quickmasretail.Service.ApiSettings.url_dataSync_quantity_left;
import static com.quickmas.salim.quickmasretail.Service.ApiSettings.url_dataSync_status;
import static com.quickmas.salim.quickmasretail.Service.ApiSettings.url_purchase_log_invoice_upload;
import static com.quickmas.salim.quickmasretail.Service.ApiSettings.url_purchase_log_upload_details;
import static com.quickmas.salim.quickmasretail.Service.ApiSettings.url_purchase_product_invoice;
import static com.quickmas.salim.quickmasretail.Service.BaseDataService.dataToModel;
import static com.quickmas.salim.quickmasretail.Utility.DateTimeCalculation.getDate;
import static com.quickmas.salim.quickmasretail.Utility.FontSettings.getFont;
import static com.quickmas.salim.quickmasretail.Utility.FontSettings.setTextFont;
import static com.quickmas.salim.quickmasretail.Utility.NetworkConfiguration.isInternetOn;
import static com.quickmas.salim.quickmasretail.Utility.UIComponent.hideSoftKeyboard;
import static com.quickmas.salim.quickmasretail.Utility.UIComponent.setLinearLayoutBackground;
import static com.quickmas.salim.quickmasretail.Utility.UIComponent.setSeletedIteamSpinner;
import static com.quickmas.salim.quickmasretail.Utility.UIComponent.showSoftKeyboard;
import static com.quickmas.salim.quickmasretail.Utility.UIComponent.spinnerDataLoad;
import static com.quickmas.salim.quickmasretail.Utility.UIComponent.updateListViewHeight;

public class PurchaseProduct extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    DBInitialization db;
    Context mContex;
    int purchaseUpdatePosition = -1;
    ArrayList<PurchaseLog> purchaseLogs = new ArrayList<>();
    Spinner sku_spinner;
    Spinner supplier_spinner;
    EditText product_quantity;
    EditText product_unit_price;
    Button add_product;
    EditText date_from;
    EditText date_to;
    boolean isStartDatePicked =false;
    String from_date_string="";
    String to_date_string="";
    ProgressDialog progressDoalog;
    String newEntryId ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("add-purchase-log"));
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_purchase_product);
        mContex = this;
        db = new DBInitialization(this,null,null,1);
        DashboardMenu cMenu =  selectByVarname(db,"dashboard_sell_pos_list");
        LinearLayout headimg = (LinearLayout) findViewById(R.id.image1);
        headimg = setLinearLayoutBackground(headimg, cMenu.getImage(), this);
        TextView headtext = (TextView) findViewById(R.id.text1);
        headtext.setTypeface(getFont(this));
        headtext.setText("Purchase List");
        from_date_string = DateTimeCalculation.getCurrentDate();
        to_date_string= DateTimeCalculation.getCurrentDate();



        final Button purchase_product = (Button) findViewById(R.id.purchase_product);
        purchase_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIComponent.hideSoftKeyboard(PurchaseProduct.this);
                LayoutInflater layoutInflater = (LayoutInflater) mContex
                        .getSystemService(LAYOUT_INFLATER_SERVICE);
                final View popupView = layoutInflater.inflate(R.layout.pop_up_purchase_product_list, null);
                final PopupWindow popupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);


                final ImageView close_tab = (ImageView) popupView.findViewById(R.id.close_tab);


                add_product  = FontSettings.setTextFont((Button) popupView.findViewById(R.id.add_product),mContex,db,"Add");
                final Button receive  = FontSettings.setTextFont((Button) popupView.findViewById(R.id.receive),mContex,db,"Save Purchase");

                close_tab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });

                Button remove_product  = (Button) popupView.findViewById(R.id.remove_product);

                final ListView product_list  = (ListView) popupView.findViewById(R.id.product_list);
                sku_spinner  = (Spinner) popupView.findViewById(R.id.sku_spinner);
                supplier_spinner  = (Spinner) popupView.findViewById(R.id.supplier_spinner);


                product_quantity =(EditText)  popupView.findViewById(R.id.product_quantity);
                product_unit_price =(EditText)  popupView.findViewById(R.id.product_unit_price);
                final TextView per_balance =(TextView)  popupView.findViewById(R.id.per_balance);
                final LinearLayout per_balance_holder =(LinearLayout)  popupView.findViewById(R.id.per_balance_holder);



                supplier_spinner.setAdapter(spinnerDataLoad(mContex,db,db.COLUMN_supplier_name,db.TABLE_supplier,"1=1","","Select Supplier"));
                sku_spinner.setAdapter(spinnerDataLoad(mContex,db,db.COLUMN_new_retail_product_sku,db.TABLE_new_retail_product,"1=1","","Select SKU"));

                TextView discount_txt = (TextView) popupView.findViewById(R.id.total_discount);




                discount_txt.addTextChangedListener(new TextWatcher() {

                    @Override
                    public void afterTextChanged(Editable s) {}

                    @Override
                    public void beforeTextChanged(CharSequence s, int start,
                                                  int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start,
                                              int before, int count) {
                        TextView total_amount = (TextView) popupView.findViewById(R.id.total_amount);
                        TextView total_tax = (TextView) popupView.findViewById(R.id.total_tax);
                        TextView total_discount = (TextView) popupView.findViewById(R.id.total_discount);
                        TextView total_payable = (TextView) popupView.findViewById(R.id.total_payable);

                        double ta = TypeConvertion.parseDouble(total_amount.getText().toString());
                        double tax = TypeConvertion.parseDouble(total_tax.getText().toString());

                        double dis = TypeConvertion.parseDouble(total_discount.getText().toString());
                        double payable = ta + tax - dis;

                        total_amount.setText(String.valueOf(String.format("%.2f", ta)));
                        total_tax.setText(String.valueOf(String.format("%.2f", tax)));
                        total_payable.setText(String.valueOf(String.format("%.2f", payable)));
                    }
                });

                sku_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String sku = sku_spinner.getSelectedItem().toString();
                        try {
                            product_unit_price.setText(String.valueOf(NewRetailProduct.select(db, db.COLUMN_new_retail_product_sku + "='" + sku + "'").get(0).getApproximate_cost()));
                        }catch (Exception e){}
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                        // sometimes you need nothing here
                    }
                });

                supplier_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        System.out.println("Excess");
                        try {
                            ArrayList<SupplierExcessCashReceived> ec = SupplierExcessCashReceived.select(db, db.COLUMN_supplier_excss_cash_customer+"='"+supplier_spinner.getSelectedItem().toString()+"'");
                            DebugHelper.print(ec);
                            if(ec.get(ec.size()-1).getAmount()>0)
                            {
                                per_balance_holder.setVisibility(View.VISIBLE);
                                per_balance.setText(String.valueOf(ec.get(ec.size()-1).getAmount()));
                            }
                            else
                            {
                                per_balance_holder.setVisibility(View.GONE);
                            }


                        }catch (Exception e){
                            System.out.println(e.getMessage());
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                        // sometimes you need nothing here
                    }
                });

                if(purchaseLogs.size()>0)
                {
                    ScrollListView.loadListViewUpdateHeight(mContex, product_list, R.layout.adapter_add_product_sku_iteam_list, purchaseLogs, "showPurchaseProductPop", 0, 100, false);
                }
                add_product.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        LinkedHashMap<View, String> linkedHashMap = new LinkedHashMap<View, String>();
                        linkedHashMap.put(sku_spinner,"Please Select SKU");
                        linkedHashMap.put(supplier_spinner,"Please Select Supplier");
                        linkedHashMap.put(product_quantity,"Please Select Quantity");
                        linkedHashMap.put(product_unit_price,"Please Select Unit Price");
                        if(!FormDataValidation.validateData(linkedHashMap,mContex,false))
                        {
                            return;
                        }

                        if(!add_product.getText().toString().toUpperCase().equals("UPDATE") && getProductCount(sku_spinner.getSelectedItem().toString(), purchaseLogs))
                        {
                            Toasty.error(getApplicationContext(),TextString.textSelectByVarname(db,"Product Already added").getText(), Toast.LENGTH_LONG).show();
                            return;
                        }

                        supplier_spinner.setEnabled(false);
                        PurchaseLog purchaseLog = new PurchaseLog();
                        purchaseLog.setSku(sku_spinner.getSelectedItem().toString());
                        purchaseLog.setSupplier(supplier_spinner.getSelectedItem().toString());
                        purchaseLog.setQuantity(TypeConvertion.parseInt(product_quantity.getText().toString()));
                        purchaseLog.setUnit_price(TypeConvertion.parseDouble(product_unit_price.getText().toString()));
                        purchaseLog.setAmount(TypeConvertion.parseDouble(product_quantity.getText().toString())*TypeConvertion.parseDouble(product_unit_price.getText().toString()));

                        if(add_product.getText().toString().toUpperCase().equals("UPDATE"))
                        {

                            purchaseLogs.set(purchaseUpdatePosition,purchaseLog);
                            add_product.setText("Add");
                        }
                        else
                        {
                            purchaseLogs.add(purchaseLog);
                        }


                        if(purchaseLogs.size()>0)
                        {
                            ScrollListView.loadListViewUpdateHeight(mContex, product_list, R.layout.adapter_add_product_sku_iteam_list, purchaseLogs, "showPurchaseProductPop", 0, 100, false);
                        }
                        //setSeletedIteamSpinner(supplier_spinner,"");
                        setSeletedIteamSpinner(sku_spinner,"");
                        product_quantity.setText("");
                        product_unit_price.setText("");
                        purchaseUpdatePosition=-1;

                        int tq = 0;
                        double ta =0.0;
                        double tax = 0.0;
                        for(PurchaseLog log : purchaseLogs)
                        {
                            tq += log.getQuantity();
                            ta += log.getAmount();
                            double percentage =0.0;

                            try {
                                String taxGroup = NewRetailProduct.select(db, db.COLUMN_new_retail_product_sku + "='" + log.getSku() + "'").get(0).getPurchase_tax_group();
                                percentage = RetailProductTaxGroup.select(db, db.COLUMN_retail_purchase_tax_group_name + "='" + taxGroup + "'").get(0).getPercentage();
                            }catch (Exception e){}

                            tax+=(log.getAmount() * percentage)/100;
                        }

                        TextView total_amount = (TextView) popupView.findViewById(R.id.total_amount);
                        TextView total_quantity = (TextView) popupView.findViewById(R.id.total_quantity);
                        TextView total_tax = (TextView) popupView.findViewById(R.id.total_tax);
                        TextView total_discount = (TextView) popupView.findViewById(R.id.total_discount);
                        TextView total_payable = (TextView) popupView.findViewById(R.id.total_payable);


                        double dis = TypeConvertion.parseDouble(total_discount.getText().toString());
                        double payable = ta + tax - dis;

                        total_quantity.setText(String.valueOf(tq));
                        total_amount.setText(String.valueOf(String.format("%.2f", ta)));
                        total_tax.setText(String.valueOf(String.format("%.2f", tax)));
                        total_payable.setText(String.valueOf(String.format("%.2f", payable)));

                    }
                });

                remove_product.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(purchaseUpdatePosition>-1) {
                            purchaseLogs.remove(purchaseUpdatePosition);
                            add_product.setText("Add");
                            purchaseUpdatePosition=-1;

                            setSeletedIteamSpinner(sku_spinner,"");
                            product_quantity.setText("");
                            product_unit_price.setText("");

                            if(purchaseLogs.size()>0)
                            {
                                ScrollListView.loadListViewUpdateHeight(mContex, product_list, R.layout.adapter_add_product_sku_iteam_list, purchaseLogs, "showPurchaseProductPop", 0, 100, false);
                            }

                            int tq = 0;
                            double ta =0.0;
                            double tax = 0.0;
                            for(PurchaseLog log : purchaseLogs)
                            {
                                tq += log.getQuantity();
                                ta += log.getAmount();
                                double percentage =0.0;

                                try {
                                    String taxGroup = NewRetailProduct.select(db, db.COLUMN_new_retail_product_sku + "='" + log.getSku() + "'").get(0).getPurchase_tax_group();
                                    percentage = RetailProductTaxGroup.select(db, db.COLUMN_retail_purchase_tax_group_name + "='" + taxGroup + "'").get(0).getPercentage();
                                }catch (Exception e){}

                                tax+=(log.getAmount() * percentage)/100;
                            }

                            TextView total_amount = (TextView) popupView.findViewById(R.id.total_amount);
                            TextView total_quantity = (TextView) popupView.findViewById(R.id.total_quantity);
                            TextView total_tax = (TextView) popupView.findViewById(R.id.total_tax);
                            TextView total_discount = (TextView) popupView.findViewById(R.id.total_discount);
                            TextView total_payable = (TextView) popupView.findViewById(R.id.total_payable);


                            double dis = TypeConvertion.parseDouble(total_discount.getText().toString());
                            double payable = ta + tax - dis;

                            total_quantity.setText(String.valueOf(tq));
                            total_amount.setText(String.valueOf(String.format("%.2f", ta)));
                            total_tax.setText(String.valueOf(String.format("%.2f", tax)));
                            total_payable.setText(String.valueOf(String.format("%.2f", payable)));

                            if(purchaseLogs.size()==0)
                            {
                                setSeletedIteamSpinner(supplier_spinner,"");
                                supplier_spinner.setEnabled(true);
                            }
                        }
                    }
                });

                receive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(purchaseLogs.size()==0)
                        {
                            return;
                        }
                        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(final DialogInterface dialog, int which) {
                                switch (which){
                                    case DialogInterface.BUTTON_POSITIVE:

                                        if(purchaseLogs.size()==0)
                                        {
                                            return;
                                        }

                                        progressDoalog = new ProgressDialog(PurchaseProduct.this);
                                        progressDoalog.setMessage("Product Creating....");
                                        progressDoalog.setTitle("Please Wait");
                                        progressDoalog.show();

                                        new Thread(new Runnable() {
                                            public void run() {
                                                final User cUser = new User();
                                                cUser.select(db,"1=1");

                                                TextView total_amount = (TextView) popupView.findViewById(R.id.total_amount);
                                                TextView total_quantity = (TextView) popupView.findViewById(R.id.total_quantity);
                                                TextView total_tax = (TextView) popupView.findViewById(R.id.total_tax);
                                                TextView total_discount = (TextView) popupView.findViewById(R.id.total_discount);
                                                TextView total_payable = (TextView) popupView.findViewById(R.id.total_payable);
                                                TextView per_balance = (TextView) popupView.findViewById(R.id.per_balance);


                                                PurchaseLogInvoice purchaseLogInvoice = new PurchaseLogInvoice();
                                                purchaseLogInvoice.setSupplier(supplier_spinner.getSelectedItem().toString());
                                                purchaseLogInvoice.setSku_quantity(purchaseLogs.size());
                                                purchaseLogInvoice.setTotal_quantity(TypeConvertion.parseInt(total_quantity.getText().toString()));
                                                purchaseLogInvoice.setTotal_amount(TypeConvertion.parseDouble(total_amount.getText().toString()));
                                                purchaseLogInvoice.setTotal_tax(TypeConvertion.parseDouble(total_tax.getText().toString()));
                                                purchaseLogInvoice.setTotal_discount(TypeConvertion.parseDouble(total_discount.getText().toString()));
                                                purchaseLogInvoice.setNet_payable(TypeConvertion.parseDouble(total_payable.getText().toString()));
                                                //purchaseLogInvoice.setAmount_paid(TypeConvertion.parseDouble(per_balance.getText().toString()));
                                                purchaseLogInvoice.setEntry_by(cUser.getUser_name());
                                                purchaseLogInvoice.setEntry_date(DateTimeCalculation.getCurrentDateTime());

                                                int currentInvoiceID = purchaseLogInvoice.getMaxId(db)+1;
                                                ArrayList<SupplierExcessCashReceived> ec = SupplierExcessCashReceived.select(db, db.COLUMN_supplier_excss_cash_customer+"=\""+supplier_spinner.getSelectedItem().toString()+"\"");
                                                if(ec.size()>0 && ec.get(ec.size()-1).getAmount()>0.0)
                                                {
                                                    double amount_adjust = 0.0;
                                                    if(ec.get(ec.size()-1).getAmount() >= purchaseLogInvoice.getNet_payable())
                                                    {
                                                        double payment_to_make =  purchaseLogInvoice.getNet_payable();
                                                        amount_adjust = payment_to_make;
                                                        for(int i= ec.size()-1;i>=0;i--)
                                                        {
                                                            SupplierExcessCashReceived e = ec.get(i);
                                                            if(e.getReceived_amount() >= payment_to_make)
                                                            {
                                                                SupplierCashPaymentReceivedDetails d = new SupplierCashPaymentReceivedDetails();
                                                                d.setReceiveId(e.getReceiveId());
                                                                d.setInvoiceId(currentInvoiceID);
                                                                d.setAmount(payment_to_make);
                                                                d.setDateTime(DateTimeCalculation.getCurrentDateTime());
                                                                d.insert(db);
                                                                break;
                                                            }
                                                            else
                                                            {
                                                                SupplierCashPaymentReceivedDetails d = new SupplierCashPaymentReceivedDetails();
                                                                d.setReceiveId(e.getReceiveId());
                                                                d.setInvoiceId(currentInvoiceID);
                                                                d.setAmount(e.getReceived_amount());
                                                                d.setDateTime(DateTimeCalculation.getCurrentDateTime());
                                                                d.insert(db);
                                                                payment_to_make = payment_to_make - e.getReceived_amount();
                                                            }
                                                        }
                                                        SupplierExcessCashReceived e = ec.get(ec.size()-1);
                                                        e.setId(0);
                                                        e.setReceiveId(currentInvoiceID);
                                                        e.setReceived_amount(0);
                                                        e.setAmount(e.getAmount() - purchaseLogInvoice.getNet_payable());
                                                        e.insert(db);
                                                    }
                                                    else
                                                    {
                                                        double payment_to_make =  ec.get(ec.size()-1).getAmount();
                                                        amount_adjust = payment_to_make;
                                                        for(int i= ec.size()-1;i>=0;i--)
                                                        {
                                                            SupplierExcessCashReceived e = ec.get(i);
                                                            if(e.getReceived_amount() >= payment_to_make)
                                                            {
                                                                SupplierCashPaymentReceivedDetails d = new SupplierCashPaymentReceivedDetails();
                                                                d.setReceiveId(e.getReceiveId());
                                                                d.setInvoiceId(currentInvoiceID);
                                                                d.setAmount(payment_to_make);
                                                                d.setDateTime(DateTimeCalculation.getCurrentDateTime());
                                                                d.insert(db);
                                                                break;
                                                            }
                                                            else
                                                            {
                                                                SupplierCashPaymentReceivedDetails d = new SupplierCashPaymentReceivedDetails();
                                                                d.setReceiveId(e.getReceiveId());
                                                                d.setInvoiceId(currentInvoiceID);
                                                                d.setAmount(e.getReceived_amount());
                                                                d.setDateTime(DateTimeCalculation.getCurrentDateTime());
                                                                d.insert(db);
                                                                payment_to_make = payment_to_make - e.getReceived_amount();
                                                            }
                                                        }
                                                        SupplierExcessCashReceived e = ec.get(ec.size()-1);
                                                        e.setId(0);
                                                        e.setReceiveId(currentInvoiceID);
                                                        e.setReceived_amount(0);
                                                        e.setAmount(0.0);
                                                        e.insert(db);
                                                    }
                                                    purchaseLogInvoice.setAmount_paid(purchaseLogInvoice.getAmount_paid()+amount_adjust);

                                                }
                                                purchaseLogInvoice.insert(db);
                                                int entry_id = PurchaseLogInvoice.getMaxId(db);
                                                newEntryId = String.valueOf(entry_id);
                                                for(PurchaseLog purchaseLog : purchaseLogs)
                                                {
                                                    purchaseLog.setEntry_id(entry_id);
                                                    purchaseLog.setEntry_by(cUser.getUser_name());
                                                    purchaseLog.setEntry_date(DateTimeCalculation.getCurrentDateTime());
                                                    purchaseLog.insert(db);
                                                    NewRetailProduct.updateRetailPrice(db,purchaseLog.getSku(),purchaseLog.getUnit_price());
                                                }
                                                receive.post(new Runnable() {
                                                    public void run() {
                                                        hideSoftKeyboard(PurchaseProduct.this);
                                                        dialog.dismiss();
                                                        popupWindow.dismiss();
                                                        if(cUser.getActive_online()==1 && isInternetOn(mContex)) {
                                                            purchase_log_invoice_log();
                                                        }else {

                                                            progressDoalog.dismiss();
                                                            Toasty.success(getApplicationContext(), TextString.textSelectByVarname(db, "Purchase Successful").getText(), Toast.LENGTH_LONG).show();
                                                            finish();
                                                        }
                                                    }
                                                });
                                            }
                                        }).start();
                                        break;
                                    case DialogInterface.BUTTON_NEGATIVE:
                                        //No button clicked
                                        break;
                                }
                            }
                        };
                        AlertDialog.Builder builder = new AlertDialog.Builder(mContex);
                        builder.setMessage(TextString.textSelectByVarname(db,"pos_sale_payment_popbox_confermation").getText()).setPositiveButton(TextString.textSelectByVarname(db,"pos_sale_payment_popbox_confermation_yes").getText(), dialogClickListener)
                                .setNegativeButton(TextString.textSelectByVarname(db,"pos_sale_payment_popbox_confermation_no").getText(), dialogClickListener).show();
                    }
                });

                popupWindow.setOutsideTouchable(true);
                popupWindow.setFocusable(true);
                popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                popupWindow.showAtLocation(purchase_product, Gravity.CENTER, 0, 0);
                showSoftKeyboard(PurchaseProduct.this);
                popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        showSoftKeyboard(PurchaseProduct.this);
                    }
                });
            }
        });

        final Button date_selection = (Button) findViewById(R.id.date_selection);
        date_selection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateSelection(date_selection);
            }
        });
    }
    public void purchase_log_invoice_log()
    {
        final User cUser = new User();
        cUser.select(db,"1=1");
        UploadData uploadData = new UploadData();
        uploadData.data = PurchaseLogInvoice.select(db,db.COLUMN_purchase_log_invoice_id+"="+newEntryId);
        uploadData.url=url_purchase_log_invoice_upload + getUserDetails(cUser);
        uploadData.uploaddata(mContex,mContex,"",mContex,"purchase_log_details");

    }

    public void purchase_log_details(ArrayList<String> data)
    {
        for(PurchaseLogInvoice p : PurchaseLogInvoice.select(db,db.COLUMN_purchase_log_invoice_id+"="+newEntryId))
        {
            p.setIf_data_synced(1);
            p.insert(db);
        }
        for(String rs : data)
        {
            try{
                JSONArray jr = new JSONArray(data.get(0));
                if(rs.toString().charAt(0)=='[')
                {
                    jr = new JSONArray(rs.toString());
                }
                for (int i = 0; i < jr.length(); i++) {
                    try{
                        JSONObject jb = (JSONObject) jr.getJSONObject(i);
                        jb.get("po").toString();
                        try {
                            String new_po_id = jb.get("po").toString();
                            String new_id = new_po_id.split("-")[new_po_id.split("-").length - 1];
                            PurchaseLog.update(db, db.COLUMN_purchase_log_entry_id + "=" + new_id, db.COLUMN_purchase_log_entry_id + "=" + newEntryId);
                            PurchaseLogInvoice.update(db, db.COLUMN_purchase_log_invoice_id + "=" + new_id, db.COLUMN_purchase_log_invoice_id + "=" + newEntryId);
                            newEntryId = new_id;
                        }catch (Exception e){}

                    }catch (Exception e){}
                }

            }catch (Exception e){}
        }


        final User cUser = new User();
        cUser.select(db,"1=1");
        UploadData uploadData = new UploadData();
        uploadData.data = PurchaseLog.select(db,db.COLUMN_purchase_log_entry_id+"="+newEntryId);
        uploadData.url=url_purchase_log_upload_details + getUserDetails(cUser);
        uploadData.uploaddata(mContex,mContex,"",mContex,"downloadproduct");
    }
    public void downloadproduct(ArrayList<String> data1)
    {
        String new_po_id = "";
        for(PurchaseLog p : PurchaseLog.select(db,db.COLUMN_purchase_log_entry_id+"="+newEntryId))
        {
            p.setIf_data_sycned(1);
            p.insert(db);
        }
        final ArrayList<PhotoPathTarDir> paths = new ArrayList<PhotoPathTarDir>();
        for(String rs : data1)
        {
            try{
                JSONArray jr = new JSONArray("["+rs.toString()+"]");
                if(rs.toString().charAt(0)=='[')
                {
                    jr = new JSONArray(rs.toString());
                }
                for (int i = 0; i < jr.length(); i++) {
                    try{
                        PosProduct cProduct = new PosProduct();
                        JSONObject jb = (JSONObject) jr.getJSONObject(i);
                        cProduct.setId(Integer.parseInt(jb.get(url_dataSync_download_id).toString()));
                        cProduct.setProduct_name(jb.get(url_dataSync_download_product_name).toString());
                        cProduct.setTitle(jb.get(url_dataSync_download_product_title).toString());
                        cProduct.setColor(jb.get(url_dataSync_download_product_color).toString());
                        cProduct.setType(jb.get(url_dataSync_download_type).toString());
                        cProduct.setSub_type(jb.get(url_dataSync_download_sub_type).toString());
                        cProduct.setLocation((jb.get(url_dataSync_download_location).toString()));
                        cProduct.setSub_location(jb.get(url_dataSync_download_sub_location).toString());
                        cProduct.setFeatures(jb.get(url_dataSync_download_features).toString());
                        cProduct.setWeight(jb.get(url_dataSync_download_weight).toString());
                        cProduct.setDimensions(jb.get(url_dataSync_download_dimensions).toString());
                        cProduct.setIncludes(jb.get(url_dataSync_download_includes).toString());
                        cProduct.setGuarantee(jb.get(url_dataSync_download_guarantee) .toString());
                        cProduct.setQuantity(Integer.parseInt(jb.get(url_dataSync_download_location_balance).toString()));
                        cProduct.setSub_quantity((Integer.parseInt(jb.get(url_dataSync_download_sub_location_balance).toString())));
                        cProduct.setPrice(Double.parseDouble(jb.get(url_dataSync_download_price).toString()));
                        cProduct.setWhole_sale(Double.parseDouble(jb.get(url_dataSync_download_wholesale).toString()));
                        try {
                            cProduct.setTax(Double.parseDouble(jb.get(url_dataSync_download_tax).toString()));
                        }catch (Exception E){}
                        try {
                            cProduct.setDiscount(Double.parseDouble(jb.get(url_dataSync_download_discount).toString()));
                        }catch (Exception E){}
                        cProduct.setDiscount_type(jb.get(url_dataSync_download_discount_type).toString());
                        cProduct.setDate_from(jb.get(url_dataSync_download_date_from).toString());
                        cProduct.setDate_to(jb.get(url_dataSync_download_date_to).toString());
                        cProduct.setHour_from(jb.get(url_dataSync_download_hour_from).toString());
                        cProduct.setHour_to(jb.get(url_dataSync_download_hour_to).toString());
                        cProduct.setPhoto(jb.get(url_dataSync_download_photo).toString());
                        cProduct.setSold_quantity(0);
                        cProduct.setQuantity_left((Integer.parseInt(jb.get(url_dataSync_quantity_left).toString())));
                        cProduct.setBrand(jb.get(url_dataSync_download_brand).toString());
                        cProduct.setCategory_status(jb.get(url_dataSync_status).toString());

                        cProduct.setStatus(0);
                        PhotoPathTarDir p = new PhotoPathTarDir();
                        p.setUrl(cProduct.getPhoto());
                        p.setTarget("pos_product_"+cProduct.getId());
                        cProduct.setPhoto("pos_product_"+cProduct.getId()+".png");
                        paths.add(p);
                        ArrayList<PosProduct> oldp = PosProduct.select(db,db.COLUMN_pos_product_name+"='"+cProduct+"'");

                        try {
                            new_po_id = jb.get("new_po_no").toString();
                            String new_id = new_po_id.split("-")[new_po_id.split("-").length - 1];
                            PurchaseLog.update(db, db.COLUMN_purchase_log_entry_id + "=" + new_id, db.COLUMN_purchase_log_entry_id + "=" + newEntryId);
                            PurchaseLogInvoice.update(db, db.COLUMN_purchase_log_invoice_id + "=" + new_id, db.COLUMN_purchase_log_invoice_id + "=" + newEntryId);
                        }catch (Exception e){}

                        if(oldp.size()>0)
                        {
                            oldp.get(0).setQuantity(cProduct.getQuantity());
                            oldp.get(0).setQuantity(cProduct.getQuantity()-oldp.get(0).getSold_quantity());
                            oldp.get(0).insert(db);
                            progressDoalog.dismiss();
                            Toasty.success(getApplicationContext(), TextString.textSelectByVarname(db, "Purchase Successful").getText(), Toast.LENGTH_LONG).show();
                            finish();
                        }
                        else
                        {
                            cProduct.insert(db);
                            BaseImageDownload di = new BaseImageDownload();
                            //progressDoalog.dismiss();
                            //Toasty.success(getApplicationContext(), TextString.textSelectByVarname(db, "Purchase Successful").getText(), Toast.LENGTH_LONG).show();
                            //finish();
                            di.downloadData(mContex,paths,mContex,"eachimg","uploadcomplete");
                        }

                    }catch (Exception e){}
                }

            }catch (Exception e){}
        }
    }
    public void eachimg(final String response,final Context context)
    {
        System.out.println("at each");
    }
    public void uploadcomplete(final String response,final Context context)
    {
        System.out.println("at end");
        progressDoalog.dismiss();
        Toasty.success(getApplicationContext(), TextString.textSelectByVarname(db, "Purchase Successful").getText(), Toast.LENGTH_LONG).show();
        finish();
    }
    @Override
    protected void onResume() {
        super.onResume();
        final TextView invoice_id = setTextFont((TextView) findViewById(R.id.invoice_id),this,db,"memo_invoice_id");
        final TextView quantity = setTextFont((TextView) findViewById(R.id.quantity),this,db,"memo_quantity");
        final TextView amount = setTextFont((TextView) findViewById(R.id.amount),this,db,"memo_amount");
        final TextView action = setTextFont((TextView) findViewById(R.id.action),this,db,"memo_action");

        ArrayList<PurchaseLogInvoice> purchaseLogInvoices = PurchaseLogInvoice.select(db,"1=1");
        ScrollListView.loadListView(mContex, (ListView) findViewById(R.id.purchase_list), R.layout.pos_stock_list, purchaseLogInvoices, "showPurchaseProduct", 0, 100, true);

    }

    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String position = intent.getStringExtra("position");
            purchaseUpdatePosition= TypeConvertion.parseInt(position);
            PurchaseLog purchaseLog = purchaseLogs.get(purchaseUpdatePosition);

            setSeletedIteamSpinner(sku_spinner,purchaseLog.getSku());
            setSeletedIteamSpinner(supplier_spinner,purchaseLog.getSupplier());

            product_quantity.setText(String.valueOf(purchaseLog.getQuantity()));
            product_unit_price.setText(String.valueOf(purchaseLog.getUnit_price()));
            add_product.setText("Update");
        }
    };

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        Calendar calendar = new GregorianCalendar(year, monthOfYear, dayOfMonth);
        if(isStartDatePicked)
        {
            date_to.setText(DateTimeCalculation.getDate(calendar));
            date_to.clearFocus();
        }
        else
        {
            date_from.setText(DateTimeCalculation.getDate(calendar));
            date_from.clearFocus();
        }
    }

    @VisibleForTesting
    void showDate(int year, int monthOfYear, int dayOfMonth, int spinnerTheme) {
        new SpinnerDatePickerDialogBuilder()
                .context(PurchaseProduct.this)
                .callback(PurchaseProduct.this)
                .spinnerTheme(spinnerTheme)
                .defaultDate(year, monthOfYear, dayOfMonth)
                .build()
                .show();
    }

    void dateSelection(Button btn)
    {
        LayoutInflater layoutInflater = (LayoutInflater) mContex
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        final View popupView = layoutInflater.inflate(R.layout.date_selection_pop_up, null);
        final PopupWindow popupWindow = new PopupWindow(popupView,LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        Button date_search  = FontSettings.setTextFont((Button) popupView.findViewById(R.id.date_search),mContex,"Submit");
        date_from  = FontSettings.setTextFont((EditText) popupView.findViewById(R.id.date_from),mContex,"");
        date_to  = FontSettings.setTextFont((EditText) popupView.findViewById(R.id.date_to),mContex,"");



        date_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                from_date_string = date_from.getText().toString();
                to_date_string= date_to.getText().toString();
                String con = db.COLUMN_purchase_log_invoice_entry_date+" BETWEEN '"+from_date_string+" 00:00:00' AND "+"'"+to_date_string+" 23:59:59'";

                ArrayList<PurchaseLogInvoice> purchaseLogInvoices = PurchaseLogInvoice.select(db,con);
                ScrollListView.loadListView(mContex, (ListView) findViewById(R.id.purchase_list), R.layout.pos_stock_list, purchaseLogInvoices, "showPurchaseProduct", 0, 100, true);
                popupWindow.dismiss();
            }
        });

        date_from.setText(from_date_string);
        date_to.setText(to_date_string);


        date_to.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    isStartDatePicked = true;
                    showDate(DateTimeCalculation.getCalender(date_to.getText().toString()).get(Calendar.YEAR), DateTimeCalculation.getCalender(date_to.getText().toString()).get(Calendar.MONTH), DateTimeCalculation.getCalender(date_to.getText().toString()).get(Calendar.DATE), R.style.DatePickerSpinner);
                }
            }
        });

        date_from.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    isStartDatePicked = false;
                    showDate(DateTimeCalculation.getCalender(date_from.getText().toString()).get(Calendar.YEAR), DateTimeCalculation.getCalender(date_from.getText().toString()).get(Calendar.MONTH), DateTimeCalculation.getCalender(date_from.getText().toString()).get(Calendar.DATE), R.style.DatePickerSpinner);
                }
            }
        });

        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.showAsDropDown(btn, 0, 0);
    }


    public void showPurchaseProductPop(final ViewData data)
    {
        Button update  = (Button) data.view.findViewById(R.id.update);
        PurchaseLog purchaseLog = (PurchaseLog) data.object;
        double amount = purchaseLog.getUnit_price() * purchaseLog.getQuantity();

        FontSettings.setTextFont((TextView) data.view.findViewById(R.id.main),mContex,purchaseLog.getSku());
        FontSettings.setTextFont((TextView) data.view.findViewById(R.id.info),mContex,purchaseLog.getSupplier());
        FontSettings.setTextFont((TextView) data.view.findViewById(R.id.color),mContex,String.valueOf(purchaseLog.getQuantity()));
        FontSettings.setTextFont((TextView) data.view.findViewById(R.id.size),mContex,String.valueOf(amount));

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("add-purchase-log");
                intent.putExtra("position",data.position);
                LocalBroadcastManager.getInstance(mContex).sendBroadcast(intent);
            }
        });
    }


    public void showPurchaseProduct(ViewData data)
    {
        final PurchaseLogInvoice purchaseLogInvoice = (PurchaseLogInvoice) data.object;
        final String in = "Bills-" +String.valueOf(purchaseLogInvoice.getId());
        final String quantity = String.valueOf(purchaseLogInvoice.getTotal_quantity());
        final String amount = String.format("%.2f", purchaseLogInvoice.getNet_payable());

        TextView invoice_id = FontSettings.setTextFont((TextView) data.view.findViewById(R.id.invoice_id),mContex,in);
        TextView date= FontSettings.setTextFont((TextView) data.view.findViewById(R.id.date),mContex,getDate(purchaseLogInvoice.getEntry_date()));
        TextView quantityT= FontSettings.setTextFont((TextView) data.view.findViewById(R.id.quantity),mContex,String.valueOf(quantity));
        TextView amountT= FontSettings.setTextFont((TextView) data.view.findViewById(R.id.amount),mContex,String.valueOf(amount));
        TextView if_synced = (TextView) data.view.findViewById(R.id.if_synced);

        if(purchaseLogInvoice.getIf_data_synced()==0)
        {
            if_synced.setVisibility(View.VISIBLE);
        }
        else
        {
            if_synced.setVisibility(View.GONE);
        }
        final Button action  = (Button) data.view.findViewById(R.id.action);
        final LinearLayout layout_holder = (LinearLayout) data.view.findViewById(R.id.layout_holder);



        System.out.println(purchaseLogInvoice.getNet_payable() +"::"+ purchaseLogInvoice.getAmount_paid());
        if(purchaseLogInvoice.getNet_payable() - purchaseLogInvoice.getAmount_paid() == 0.0)
        {
            invoice_id.setTextColor(Color.parseColor("#006600"));
            quantityT.setTextColor(Color.parseColor("#006600"));
            amountT.setTextColor(Color.parseColor("#006600"));
            date.setTextColor(Color.parseColor("#006600"));
        }
        else
        {
            invoice_id.setTextColor(Color.parseColor("#ff571b"));
            quantityT.setTextColor(Color.parseColor("#ff571b"));
            amountT.setTextColor(Color.parseColor("#ff571b"));
            date.setTextColor(Color.parseColor("#ff571b"));
        }


        action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater = (LayoutInflater) mContex
                        .getSystemService(LAYOUT_INFLATER_SERVICE);
                final View popupView = layoutInflater.inflate(R.layout.memo_pop_up, null);
                final PopupWindow popupWindow = new PopupWindow(popupView,LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                Button print  = FontSettings.setTextFont((Button) popupView.findViewById(R.id.print),mContex,db,"memopopup_print");
                Button delivery  = FontSettings.setTextFont((Button) popupView.findViewById(R.id.delivery),mContex,db,"memopopup_devilery");
                Button payment  = FontSettings.setTextFont((Button) popupView.findViewById(R.id.payment),mContex,db,"memopopup_payment");
                Button make_void  = FontSettings.setTextFont((Button) popupView.findViewById(R.id.make_void),mContex,db,"memopopup_void");

                delivery.setVisibility(View.GONE);
                make_void.setVisibility(View.GONE);
                if(purchaseLogInvoice.getNet_payable() - purchaseLogInvoice.getAmount_paid()==0.0)
                {
                    payment.setVisibility(View.GONE);
                }

                payment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                        Intent i = new Intent(mContex, PurchasePayment.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        i.putExtra("id", String.valueOf(purchaseLogInvoice.getId()));
                        mContex.startActivity(i);
                    }
                });

                print.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();

                    }
                });

                popupWindow.dismiss();
                popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        layout_holder.setBackgroundColor(Color.parseColor("#e2e2e2"));
                    }
                });
                // Closes the popup window when touch outside.
                popupWindow.setOutsideTouchable(true);
                popupWindow.setFocusable(true);
                // Removes default background.
                popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                popupWindow.showAsDropDown(action, 0, 0);
                layout_holder.setBackgroundColor(Color.parseColor("#72a8ff"));
            }
        });
    }

    public Boolean getProductCount(String sku, ArrayList<PurchaseLog> purchaseLogs)
    {
        for(PurchaseLog purchaseLog : purchaseLogs)
        {
            if(purchaseLog.getSku().equals(sku))
            {
                return true;
            }
        }
        return false;
    }
}
