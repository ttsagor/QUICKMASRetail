package com.quickmas.salim.quickmasretail.Module.RegularSale.RegularSaleCreate;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.arasthel.asyncjob.AsyncJob;
import com.quickmas.salim.quickmasretail.Database.DBInitialization;
import com.quickmas.salim.quickmasretail.Model.POS.Card;
import com.quickmas.salim.quickmasretail.Model.POS.CashPaymentReceivedDetails;
import com.quickmas.salim.quickmasretail.Model.POS.Discount;
import com.quickmas.salim.quickmasretail.Model.POS.ExcessCashReceived;
import com.quickmas.salim.quickmasretail.Model.POS.ExchangeDetails;
import com.quickmas.salim.quickmasretail.Model.POS.InvoiceProduct;
import com.quickmas.salim.quickmasretail.Model.POS.PosCustomer;
import com.quickmas.salim.quickmasretail.Model.POS.PosInvoice;
import com.quickmas.salim.quickmasretail.Model.POS.PosInvoiceHead;
import com.quickmas.salim.quickmasretail.Model.POS.PosInvoiceHeadRegular;
import com.quickmas.salim.quickmasretail.Model.POS.PosInvoiceHeadRegularWeb;
import com.quickmas.salim.quickmasretail.Model.POS.PosInvoiceHeadWeb;
import com.quickmas.salim.quickmasretail.Model.POS.PosInvoicePayLaterUser;
import com.quickmas.salim.quickmasretail.Model.POS.PosInvoiceRegular;
import com.quickmas.salim.quickmasretail.Model.POS.PosProduct;
import com.quickmas.salim.quickmasretail.Model.POS.SalesPerson;
import com.quickmas.salim.quickmasretail.Model.System.DashboardMenu;
import com.quickmas.salim.quickmasretail.Model.System.TextString;
import com.quickmas.salim.quickmasretail.Model.System.User;
import com.quickmas.salim.quickmasretail.Module.DataSync.AssignmentList.AssignmentList;
import com.quickmas.salim.quickmasretail.Module.POS.PosInvoicePrint.PosInvoicePrint;
import com.quickmas.salim.quickmasretail.Module.POS.PosSell.SelectProduct.PosSelectProduct;
import com.quickmas.salim.quickmasretail.Module.RegularSale.RegularSaleInvoice.RegularInvoiceList;
import com.quickmas.salim.quickmasretail.Module.RegularSale.RegularSaleInvoice.RegularInvoicePrint;
import com.quickmas.salim.quickmasretail.R;
import com.quickmas.salim.quickmasretail.Service.UploadData;
import com.quickmas.salim.quickmasretail.Structure.ExchangeProductQuantity;
import com.quickmas.salim.quickmasretail.Structure.PosInvoiceCombian;
import com.quickmas.salim.quickmasretail.Structure.PosInvoiceCombianRegular;
import com.quickmas.salim.quickmasretail.Structure.PosInvoiceHeadCombian;
import com.quickmas.salim.quickmasretail.Structure.PosInvoiceHeadCombianRegular;
import com.quickmas.salim.quickmasretail.Utility.DateTimeCalculation;
import com.quickmas.salim.quickmasretail.Utility.DebugHelper;
import com.quickmas.salim.quickmasretail.Utility.FontSettings;
import com.quickmas.salim.quickmasretail.Utility.MyAsynctask;
import com.quickmas.salim.quickmasretail.Utility.TypeConvertion;
import com.quickmas.salim.quickmasretail.Utility.UI.ScrollListView;
import com.quickmas.salim.quickmasretail.Utility.UI.ViewData;
import com.quickmas.salim.quickmasretail.Utility.UIComponent;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import cn.pedant.SweetAlert.SweetAlertDialog;
import es.dmoral.toasty.Toasty;

import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_head_id;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_status;
import static com.quickmas.salim.quickmasretail.Model.POS.PosInvoice.deleteIteamFromPendingList;
import static com.quickmas.salim.quickmasretail.Model.POS.PosInvoiceRegular.deleteIteamExchange;
import static com.quickmas.salim.quickmasretail.Model.POS.PosInvoiceRegular.updateInvoiceStatus;
import static com.quickmas.salim.quickmasretail.Model.POS.PosInvoicePayLaterUser.checkIfUserExists;
import static com.quickmas.salim.quickmasretail.Model.POS.InvoiceProduct.calDis;
import static com.quickmas.salim.quickmasretail.Model.System.DashboardMenu.selectByVarname;
import static com.quickmas.salim.quickmasretail.Module.Exchange.ExchangeProductSelection.exchangeProductQuantities;
import static com.quickmas.salim.quickmasretail.Service.ApiSettings.url_pos_invoice_co_ledger_upload;
import static com.quickmas.salim.quickmasretail.Service.ApiSettings.url_pos_invoice_details_upload;
import static com.quickmas.salim.quickmasretail.Service.ApiSettings.url_pos_invoice_upload;
import static com.quickmas.salim.quickmasretail.Structure.ProductCategory.findProductByCategory;
import static com.quickmas.salim.quickmasretail.Structure.ProductCategory.getAllCategory;
import static com.quickmas.salim.quickmasretail.Utility.DateTimeCalculation.getCurrentDateTime;
import static com.quickmas.salim.quickmasretail.Utility.FontSettings.setTwoDigitDec;
import static com.quickmas.salim.quickmasretail.Utility.NetworkConfiguration.isInternetOn;
import static com.quickmas.salim.quickmasretail.Utility.UIComponent.getMeasueredLine;
import static com.quickmas.salim.quickmasretail.Utility.UIComponent.hideSoftKeyboard;
import static com.quickmas.salim.quickmasretail.Utility.UIComponent.showSoftKeyboard;
import static com.quickmas.salim.quickmasretail.Utility.UIComponent.spinnerDataLoad;

public class RegularCreateInvoice extends AppCompatActivity {
    DBInitialization db;
    Context mContex;
    ArrayList<String> ArrSaleP;
    Button add_customer;
    ArrayList<String> productsArr=new ArrayList<String>();
    public static int currentInvoiceID=0;
    ArrayList<InvoiceProduct> currentProductList = new ArrayList<InvoiceProduct>();
    AppBarLayout product_holder;
    MyAsynctask runner = new MyAsynctask();
    private Context context;
    HashMap<String,Integer> productQuantity = new HashMap<String,Integer>();
    int selIndex=-1;
    Button headtext;
    ProgressDialog progressDoalog;
    String walkinCustomer = "Walk-In-Customer";
    public static String previous_id="0";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_regular_create_invoice);
        mContex=this;
        context=this;
        db = new DBInitialization(this,null,null,1);
        setup();
    }

    public void setup()
    {
        AsyncJob.doInBackground(new AsyncJob.OnBackgroundJob() {
            @Override
            public void doOnBackground() {
                ArrayList<PosInvoiceRegular> posInvoices = PosInvoiceRegular.select(db,COLUMN_pos_invoice_status+"=9");
                for(PosInvoiceRegular posInvoice : posInvoices)
                {
                    //updateProductQuantityLeft(mContex, db,posInvoices);
                }
                PosInvoiceRegular.deleteIteamExchange(db);
                ArrayList<PosInvoicePayLaterUser> Pos = PosInvoicePayLaterUser.select(db,"1=1");
                ArrayList<SalesPerson> salesPeople =  SalesPerson.select(db,"1=1");
                ArrSaleP = new ArrayList<>();
                //ArrSaleP.add(TextString.textSelectByVarname(db,"pos_sale_person_box2_invoice").getText());
                for(SalesPerson p: salesPeople) {
                    ArrSaleP.add(p.getName());
                }


                ArrayList<PosInvoiceRegular> pendingPayment = PosInvoiceRegular.getPaymentPendingInvoices(db);
                final ArrayList<String> arraySpinner = new ArrayList<>();


                final ArrayList<InvoiceProduct> posProuct = InvoiceProduct.select(db,"1=1");



                for(InvoiceProduct p : posProuct)
                {
                    productsArr.add(p.getName());
                }

                final ArrayList<String> cusArr =new ArrayList<String>();
                final ArrayList<PosCustomer> posCustomers = PosCustomer.select(db,"1=1");

                for(PosCustomer p : posCustomers)
                {
                    cusArr.add(p.getName());
                }
                final boolean result = true;
                AsyncJob.doOnMainThread(new AsyncJob.OnMainThreadJob() {
                    @Override
                    public void doInUIThread() {

                        DashboardMenu cMenu =  selectByVarname(db,"dashboard_sell_pos");
                        // LinearLayout headimg = (LinearLayout) findViewById(R.id.image1);
                        //headimg = setLinearLayoutBackground(headimg, cMenu.getImage(), mContex);
                        //headtext = (TextView) findViewById(R.id.text1);
                        //headtext.setTypeface(getFont(mContex));
                        //headtext.setText(cMenu.getText());


                        add_customer = FontSettings.setTextFont ((Button) findViewById(R.id.add_customer),mContex,db,"pos_sale_customer_new_invoice");
//                        TextView pos_product_pos_product_add_new = FontSettings.setTextFont((TextView) findViewById(R.id.pos_product_pos_product_add_new),mContex,db,"pos_sale_new_invoice");
                        TextView pos_product_pos_product_make_void = FontSettings.setTextFont ((TextView) findViewById(R.id.pos_product_pos_product_make_void),mContex,db,"pos_sale_cancel_invoice");
                        //pos_product_pos_product_detail = FontSettings.setTextFont ((Button) findViewById(R.id.pos_product_pos_product_detail),mContex,db,"pos_sale_detail_invoice");
                        Button pos_product_pos_product_enter = FontSettings.setTextFont ((Button) findViewById(R.id.pos_product_pos_product_enter),mContex,db,"pos_sale_add_invoice");

                        TextView sku = FontSettings.setTextFont ((TextView) findViewById(R.id.sku),mContex,db,"pos_sale_goods_invoice");
                        TextView qnty = FontSettings.setTextFont ((TextView) findViewById(R.id.qnty),mContex,db,"pos_sale_qty_invoice");
                        TextView uprice = FontSettings.setTextFont ((TextView) findViewById(R.id.uprice),mContex,db,"pos_sale_unit_price_invoice");
                        TextView total = FontSettings.setTextFont ((TextView) findViewById(R.id.total),mContex,db,"pos_sale_amount_invoice");
                        TextView total_amount_list_txt = FontSettings.setTextFont ((TextView) findViewById(R.id.total_amount_list_txt),mContex,db,"pos_sale_total_amount_invoice");
                        TextView tax_txt = FontSettings.setTextFont ((TextView) findViewById(R.id.tax_txt),mContex,db,"pos_sale_tax_invoice");
                        TextView delivery_ex_txt = FontSettings.setTextFont ((TextView) findViewById(R.id.delivery_ex_txt),mContex,db,"pos_sale_delivery_exp_invoice");
                        TextView discount_txt = FontSettings.setTextFont ((TextView) findViewById(R.id.discount_txt),mContex,db,"pos_sale_discount_invoice");
                        TextView add_discount_txt = FontSettings.setTextFont ((TextView) findViewById(R.id.add_discount_txt),mContex,db,"pos_sale_additional_discount_invoice");
                        final TextView net_payable_txt = FontSettings.setTextFont ((TextView) findViewById(R.id.net_payable_txt),mContex,db,"pos_sale_net_payable_invoice");
                        TextView due_txt = FontSettings.setTextFont ((TextView) findViewById(R.id.due_txt),mContex,db,"pos_sale_previous_dues_invoice");
                        final TextView total_product = (TextView) findViewById(R.id.total_product);
                        final Button pos_payment = (Button) findViewById(R.id.pos_payment);



                        final AutoCompleteTextView sales_person = (AutoCompleteTextView) findViewById(R.id.sales_person);
                        /////
                        sales_person.setHint(TextString.textSelectByVarname(db,"pos_sale_invoice_sales_person").getText());
                        ArrayAdapter<String> adapterS = new ArrayAdapter<String>(mContex,android.R.layout.select_dialog_item,ArrSaleP);
                        sales_person.setThreshold(1);//will start working from first character
                        sales_person.setAdapter(adapterS);//setting the adapter data into the AutoCompleteTextView
                        sales_person.setTextColor(Color.parseColor("#fc6d2a"));
                        try {
                            sales_person.setText(SalesPerson.select(db, db.COLUMN_pos_sales_person_isSelected + "=1").get(0).getName());
                        }
                        catch (Exception e){}
                        sales_person.setDropDownWidth(getResources().getDisplayMetrics().widthPixels);
                        Intent iin= getIntent();
                        Bundle b = iin.getExtras();
                        if(b!=null)
                        {
                            previous_id =(String) b.get("id");
                        }
                        else {
                            previous_id ="0";
                        }

                        if(!previous_id.equals("0"))
                        {
                            /*final Spinner pos_product_pos_pending_invoice = (Spinner) findViewById(R.id.pos_product_pos_pending_invoice);
                            final AutoCompleteTextView customer_selection = (AutoCompleteTextView) findViewById(R.id.customer_selection);
                            TextView return_balance = FontSettings.setTextFont ((TextView) findViewById(R.id.return_balance),mContex,String.valueOf(ExchangeProductQuantity.getExchangeTotalAmount(exchangeProductQuantities)));
                            pos_product_pos_pending_invoice.setEnabled(false);
                            PosInvoiceHead posInvoiceHead = PosInvoiceHead.select(db,COLUMN_pos_invoice_head_id+"="+previous_id).get(0);
                            customer_selection.setText(posInvoiceHead.getCustomer());
                            customer_selection.setEnabled(false);
                            customer_selection.setDropDownWidth(getResources().getDisplayMetrics().widthPixels);
                            //pos_product_pos_product_add_new.setEnabled(false);
                            pos_product_pos_product_make_void.setEnabled(false);*/
                        }
                        else
                        {
                            LinearLayout payable_layout = (LinearLayout) findViewById(R.id.payable_layout);
                            LinearLayout return_layout = (LinearLayout) findViewById(R.id.return_layout);
                            payable_layout.setVisibility(View.GONE);
                            return_layout.setVisibility(View.GONE);
                        }
                        ////

                        final Spinner pos_product_pos_pending_invoice = (Spinner) findViewById(R.id.pos_product_pos_pending_invoice);

                        if(selIndex > -1)
                        {
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContex,android.R.layout.simple_spinner_item, arraySpinner);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            pos_product_pos_pending_invoice.setAdapter(adapter);
                            pos_product_pos_pending_invoice.setSelection(selIndex);
                        }
                        else
                        {
                            arraySpinner.add(String.valueOf(PosInvoiceRegular.getMaxInvoice(db)));
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContex,android.R.layout.simple_spinner_item, arraySpinner);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            pos_product_pos_pending_invoice.setAdapter(adapter);
                            pos_product_pos_pending_invoice.setSelection(arraySpinner.size()-1);
                        }



                        pos_product_pos_pending_invoice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                                updateInvoiceStatus(db,currentInvoiceID,"1");
                                currentInvoiceID = Integer.parseInt(pos_product_pos_pending_invoice.getSelectedItem().toString());
                                updateInvoiceStatus(db,currentInvoiceID,"0");
                                updatePosView(mContex,db);
                                updateList(mContex,db);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parentView) {
                                // your code here
                            }

                        });



                        final Button home_new_item = FontSettings.setTextFont ((Button) findViewById(R.id.home_new_item),mContex,db,"pos_sale_invoice_bottom_additem");
                        final TextView total_product_txt =(TextView) findViewById (R.id.total_product_txt);
                        final TextView add_discount_per =(TextView) findViewById (R.id.add_discount_per);



                        final LinearLayout product_layout_holder = (LinearLayout) findViewById(R.id.product_layout_holder);
                        final LinearLayout edit_per_value = (LinearLayout) findViewById(R.id.edit_per_value);
                        final ImageView edit_customer_name = (ImageView) findViewById(R.id.edit_customer_name);
                        final ImageView edit_sales_person_name = (ImageView) findViewById(R.id.edit_sales_person_name);

                        add_discount_per.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ////////////////////

                                LayoutInflater layoutInflater = (LayoutInflater) mContex
                                        .getSystemService(LAYOUT_INFLATER_SERVICE);
                                final View popupView = layoutInflater.inflate(R.layout.pos_payment_type_list_holder, null);
                                final PopupWindow popupWindow = new PopupWindow(popupView,LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                LinearLayout payment_type_list_holder = popupView.findViewById(R.id.payment_type_list_holder);

                                final ArrayList<Discount> discounts = Discount.selectValid(db);
                                if(discounts.size()==0)
                                {
                                    return;
                                }
                                DebugHelper.print(discounts);
                                System.out.println("sdoo asdasd");
                                final TextView total_amount_list = (TextView) findViewById(R.id.total_amount_list);
                                final EditText add_discount_amount = (EditText) findViewById(R.id.add_discount_amount);
                                int n = (int) Math.ceil((double) discounts.size() / 3);
                                for(int i=0;i<n;i++)
                                {
                                    final int btn1 = i*3;
                                    final int btn2 = (i*3)+1;
                                    final int btn3 = (i*3)+2;
                                    LayoutInflater layoutInflater1 = (LayoutInflater) context
                                            .getSystemService(LAYOUT_INFLATER_SERVICE);
                                    final View popupView1 = layoutInflater.inflate(R.layout.pos_payment_type_list, null);

                                    if(discounts.size()>btn1)
                                    {
                                        final Button b = FontSettings.setTextFont((Button) popupView1.findViewById(R.id.btn_1),mContex,discounts.get(btn1).name);
                                        {
                                            b.setTextColor(Color.WHITE);
                                            b.setBackgroundColor(Color.parseColor("#635c00"));
                                        }

                                        b.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                add_discount_per.setText(String.valueOf(discounts.get(btn1).discount));
                                                double total = 0.0;
                                                double discount_per = 0.0;
                                                double dis_amount = 0.0;
                                                try {
                                                    total = Double.parseDouble(total_amount_list.getText().toString());
                                                } catch (Exception e) {
                                                }
                                                try {
                                                    discount_per = Double.parseDouble(add_discount_per.getText().toString());
                                                } catch (Exception e) {
                                                }

                                                dis_amount = (total * discount_per) / 100;

                                                try {
                                                    DecimalFormat decimalFormat = new DecimalFormat("#.##");
                                                    float twoDigitsF = Float.valueOf(decimalFormat.format(dis_amount));
                                                    add_discount_amount.setText(String.valueOf(twoDigitsF));
                                                }catch (Exception e){
                                                    add_discount_amount.setText(String.valueOf(dis_amount));
                                                }
                                                calFinal(mContex);
                                                popupWindow.dismiss();
                                            }
                                        });

                                    }
                                    else
                                    {
                                        ((Button) popupView1.findViewById(R.id.btn_1)).setVisibility(View.GONE);
                                    }

                                    if(discounts.size()>btn2)
                                    {
                                        final Button b = FontSettings.setTextFont((Button) popupView1.findViewById(R.id.btn_2),mContex,discounts.get(btn2).name);

                                        {
                                            b.setTextColor(Color.WHITE);
                                            b.setBackgroundColor(Color.parseColor("#635c00"));
                                        }
                                        b.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                add_discount_per.setText(String.valueOf(discounts.get(btn2).discount));
                                                double total = 0.0;
                                                double discount_per = 0.0;
                                                double dis_amount = 0.0;
                                                try {
                                                    total = Double.parseDouble(total_amount_list.getText().toString());
                                                } catch (Exception e) {
                                                }
                                                try {
                                                    discount_per = Double.parseDouble(add_discount_per.getText().toString());
                                                } catch (Exception e) {
                                                }

                                                dis_amount = (total * discount_per) / 100;

                                                try {
                                                    DecimalFormat decimalFormat = new DecimalFormat("#.##");
                                                    float twoDigitsF = Float.valueOf(decimalFormat.format(dis_amount));
                                                    add_discount_amount.setText(String.valueOf(twoDigitsF));
                                                }catch (Exception e){
                                                    add_discount_amount.setText(String.valueOf(dis_amount));
                                                }
                                                calFinal(mContex);
                                                popupWindow.dismiss();
                                            }
                                        });
                                    }
                                    else
                                    {
                                        ((Button) popupView1.findViewById(R.id.btn_2)).setVisibility(View.GONE);
                                    }

                                    if(discounts.size()>btn3)
                                    {
                                        final Button b = FontSettings.setTextFont((Button) popupView1.findViewById(R.id.btn_3),mContex,discounts.get(btn3).name);

                                        {
                                            b.setTextColor(Color.WHITE);
                                            b.setBackgroundColor(Color.parseColor("#635c00"));
                                        }
                                        b.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                add_discount_per.setText(String.valueOf(discounts.get(btn3).discount));
                                                double total = 0.0;
                                                double discount_per = 0.0;
                                                double dis_amount = 0.0;
                                                try {
                                                    total = Double.parseDouble(total_amount_list.getText().toString());
                                                } catch (Exception e) {
                                                }
                                                try {
                                                    discount_per = Double.parseDouble(add_discount_per.getText().toString());
                                                } catch (Exception e) {
                                                }

                                                dis_amount = (total * discount_per) / 100;

                                                try {
                                                    DecimalFormat decimalFormat = new DecimalFormat("#.##");
                                                    float twoDigitsF = Float.valueOf(decimalFormat.format(dis_amount));
                                                    add_discount_amount.setText(String.valueOf(twoDigitsF));
                                                }catch (Exception e){
                                                    add_discount_amount.setText(String.valueOf(dis_amount));
                                                }
                                                calFinal(mContex);
                                                popupWindow.dismiss();
                                            }
                                        });
                                    }
                                    else
                                    {
                                        ((Button) popupView1.findViewById(R.id.btn_3)).setVisibility(View.GONE);
                                    }
                                    payment_type_list_holder.addView(popupView1);
                                }
                                popupWindow.setOutsideTouchable(true);
                                popupWindow.setFocusable(true);
                                popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                popupWindow.showAtLocation(edit_per_value, Gravity.CENTER, 0, 0);
                                popupWindow.showAsDropDown(add_discount_per, 0, 0);
                                ////////////////
                            }
                        });
                        edit_sales_person_name.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                sales_person.setText("");
                            }
                        });
                        edit_customer_name.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                final AutoCompleteTextView customer_selection = (AutoCompleteTextView) findViewById(R.id.customer_selection);
                                customer_selection.setText("");
                            }
                        });





                        ///ScrollView scr = (ScrollView) findViewById(R.id.product_list_scroll);

                       // LinearLayout layout_category_holder = (LinearLayout) findViewById(R.id.layout_category_holder);
                        adapterS = new ArrayAdapter<String>(mContex,android.R.layout.select_dialog_item,productsArr);
                        final AutoCompleteTextView pos_product_pos_product_id = (AutoCompleteTextView) findViewById(R.id.pos_product_pos_product_id);
                        pos_product_pos_product_id.setHint(TextString.textSelectByVarname(db,"pos_sale_code_box_invoice").getText());

                        pos_product_pos_product_id.setThreshold(1);//will start working from first character
                        pos_product_pos_product_id.setAdapter(adapterS);//setting the adapter data into the AutoCompleteTextView
                        pos_product_pos_product_id.setTextColor(Color.RED);

                        pos_product_pos_product_id.setDropDownWidth(getResources().getDisplayMetrics().widthPixels);

                        final AutoCompleteTextView customer_selection = (AutoCompleteTextView) findViewById(R.id.customer_selection);
                        customer_selection.setHint(TextString.textSelectByVarname(db,"pos_sale_customer_box1_invoice").getText());




                        //cusArr.add(TextString.textSelectByVarname(db,"pos_sale_customer_box2_invoice").getText());
                        ArrayAdapter<String> adapterC = new ArrayAdapter<String>(mContex,android.R.layout.select_dialog_item,cusArr);
                        customer_selection.setThreshold(1);//will start working from first character
                        customer_selection.setAdapter(adapterC);//setting the adapter data into the AutoCompleteTextView
                        customer_selection.setTextColor(Color.parseColor("#fc6d2a"));
                        if(previous_id.equals("0")) {
                            customer_selection.setText(walkinCustomer);
                        }

                        ///showProducts(mContex,findProductByCategory(allCategory,"all"));
                        final Spinner pos_product_pos_product_sale_type_spinner = (Spinner) findViewById(R.id.pos_product_pos_product_sale_type_spinner);
                        //Spinner pos_product_pos_net_spinner = (Spinner) findViewById(R.id.pos_product_pos_net_spinner);
                        ArrayList<String> categories = new ArrayList<String>();
                        categories.add(TextString.textSelectByVarname(db,"pos_sale_retail_invoice").getText());
                        categories.add(TextString.textSelectByVarname(db,"pos_sale_wholesale_invoice").getText());
                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(mContex, android.R.layout.simple_spinner_item, categories);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        //pos_product_pos_product_sale_type_spinner.setAdapter(dataAdapter);

                        currentInvoiceID = PosInvoiceRegular.getMaxInvoice(db);

                        updatePosView(mContex,db);

                        final EditText delivery_ex = (EditText) findViewById(R.id.delivery_ex);
                        //final EditText add_discount_per = (EditText) findViewById(R.id.add_discount_per);
                        final EditText add_discount_amount = (EditText) findViewById(R.id.add_discount_amount);

                        final TextView total_amount_list = (TextView) findViewById(R.id.total_amount_list);
                        delivery_ex.addTextChangedListener(new TextWatcher() {

                            @Override
                            public void afterTextChanged(Editable s) {}

                            @Override
                            public void beforeTextChanged(CharSequence s, int start,
                                                          int count, int after) {
                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start,
                                                      int before, int count) {
                                calFinal(mContex);
                            }
                        });

                        add_discount_per.addTextChangedListener(new TextWatcher() {

                            @Override
                            public void afterTextChanged(Editable s) {}

                            @Override
                            public void beforeTextChanged(CharSequence s, int start,
                                                          int count, int after) {
                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start,
                                                      int before, int count) {
                                if (getCurrentFocus() == add_discount_per) {
                                    double total = 0.0;
                                    double discount_per = 0.0;
                                    double dis_amount = 0.0;


                                    try {
                                        total = Double.parseDouble(total_amount_list.getText().toString());
                                    } catch (Exception e) {
                                    }
                                    try {
                                        discount_per = Double.parseDouble(add_discount_per.getText().toString());
                                    } catch (Exception e) {
                                    }

                                    dis_amount = (total * discount_per) / 100;

                                    try {
                                        DecimalFormat decimalFormat = new DecimalFormat("#.##");
                                        float twoDigitsF = Float.valueOf(decimalFormat.format(dis_amount));
                                        add_discount_amount.setText(String.valueOf(twoDigitsF));
                                    }catch (Exception e){
                                        add_discount_amount.setText(String.valueOf(dis_amount));
                                    }
                                    calFinal(mContex);
                                }
                            }
                        });

                        add_discount_amount.addTextChangedListener(new TextWatcher() {

                            @Override
                            public void afterTextChanged(Editable s) {}

                            @Override
                            public void beforeTextChanged(CharSequence s, int start,
                                                          int count, int after) {
                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start,
                                                      int before, int count) {

                                if (getCurrentFocus() == add_discount_amount) {

                                    double total = 0.0;
                                    double discount_per = 0.0;
                                    double dis_amount = 0.0;
                                    try {
                                        total = Double.parseDouble(total_amount_list.getText().toString());
                                    } catch (Exception e) {
                                    }

                                    try {
                                        dis_amount = Double.parseDouble(add_discount_amount.getText().toString());
                                    } catch (Exception e) {
                                    }

                                    try {
                                        discount_per = (100 * dis_amount) / total;
                                    } catch (Exception e) {
                                    }
                                    try {
                                        DecimalFormat decimalFormat = new DecimalFormat("#.##");
                                        float twoDigitsF = Float.valueOf(decimalFormat.format(discount_per));
                                        add_discount_per.setText(String.valueOf(twoDigitsF));
                                    }catch (Exception e){add_discount_per.setText(String.valueOf(discount_per));}
                                    calFinal(mContex);
                                }
                            }
                        });

                        pos_product_pos_product_enter.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                new Thread(new Runnable() {
                                    public void run() {
                                        try {
                                            InvoiceProduct cProdcut = InvoiceProduct.select(db, db.COLUMN_invoice_product_name + " LIKE '" + pos_product_pos_product_id.getText().toString() + "'").get(0);
                                            PosInvoiceRegular invoice = new PosInvoiceRegular();
                                            invoice.setInvoiceID(currentInvoiceID);
                                            invoice.setProductId(cProdcut.getId());
                                            invoice.setProductName(cProdcut.getName());
                                            invoice.setUnitPrice(cProdcut.getPrice());
                                            String con = db.COLUMN_pos_invoice_productId + "=" + cProdcut.getId() + " AND " + db.COLUMN_pos_invoice_invoiceID + "=" + currentInvoiceID + " AND ("+COLUMN_pos_invoice_status+"=0 OR "+COLUMN_pos_invoice_status +"=9)";
                                            int quantity = 1;
                                            try
                                            {
                                                quantity = PosInvoiceRegular.select(db, con).get(0).getQuantity() + 1;
                                            }
                                            catch (Exception e) {}
                                            invoice.setQuantity(quantity);
                                            if(previous_id.equals("0"))
                                            {
                                                invoice.setStatus(0);
                                            }
                                            else
                                            {
                                                invoice.setStatus(9);
                                            }
                                            invoice.insert(db);
                                        }catch (Exception e){
                                            System.out.println(e.getMessage());
                                        }
                                        pos_product_pos_product_id.post(new Runnable() {
                                            public void run()
                                            {
                                                updatePosView(mContex,db);
                                                updateList(mContex,db);
                                                pos_product_pos_product_id.setText("");
                                            }
                                        });
                                    }
                                }).start();
                            }
                        });

                        pos_product_pos_product_make_void.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try {
                                    //updateProductQuantityLeft(mContex,db,currentInvoiceID);
                                    PosInvoiceRegular.deletePendingInvoices(db,currentInvoiceID);
                                    //updatePosView(mContex,db);
                                    //updateList(mContex,db);
                                    pos_product_pos_product_id.setText("");
                                    add_discount_per.setText("0.0");
                                    add_discount_amount.setText("");
                                    if(arraySpinner.contains(String.valueOf(currentInvoiceID))) {
                                        arraySpinner.remove(String.valueOf(currentInvoiceID));
                                    }
                                    currentInvoiceID=PosInvoiceRegular.getMaxInvoice(db);
                                    updatePosView(mContex,db);
                                    updateList(mContex,db);
                                    if(!arraySpinner.contains(String.valueOf(currentInvoiceID)))
                                    {
                                        arraySpinner.add(String.valueOf(currentInvoiceID));
                                    }
                                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContex,android.R.layout.simple_spinner_item, arraySpinner);
                                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    pos_product_pos_pending_invoice.setAdapter(adapter);
                                    pos_product_pos_pending_invoice.setSelection(arraySpinner.size()-1);
                                }catch (Exception e){}
                            }
                        });

                        pos_payment.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                paymentpopup("Pay Later");
                            }
                        });

                        add_customer.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                LayoutInflater layoutInflater = (LayoutInflater) mContex
                                        .getSystemService(LAYOUT_INFLATER_SERVICE);
                                final View popupView = layoutInflater.inflate(R.layout.pop_up_pos_customer_add, null);
                                final PopupWindow popupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);


                                Button customer_submit_pop  = FontSettings.setTextFont((Button) popupView.findViewById(R.id.customer_submit_pop),mContex,db,"pos_sale_customer_add_submit_invoice");
                                final TextView customer_cutomer_title_pop  = FontSettings.setTextFont((TextView) popupView.findViewById(R.id.customer_cutomer_title_pop),mContex,db,"pos_sale_customer_add_title_invoice");
                                final TextView customer_name_pop  = FontSettings.setTextFont((TextView) popupView.findViewById(R.id.customer_name_pop),mContex,db,"pos_sale_customer_add_name_invoice");
                                final TextView customer_full_name_pop  = FontSettings.setTextFont((TextView) popupView.findViewById(R.id.customer_full_name_pop),mContex,db,"pos_sale_customer_add_fname_invoice");
                                final TextView customer_phone_pop  = FontSettings.setTextFont((TextView) popupView.findViewById(R.id.customer_phone_pop),mContex,db,"pos_sale_customer_add_mobile_invoice");

                                final EditText customer_name_pop_edit  = FontSettings.setTextFont((EditText) popupView.findViewById(R.id.customer_name_pop_edit),mContex,"");
                                final EditText customer_full_name_pop_edit  = FontSettings.setTextFont((EditText) popupView.findViewById(R.id.customer_full_name_pop_edit),mContex,"");
                                final EditText customer_phone_pop_edit = FontSettings.setTextFont((EditText) popupView.findViewById(R.id.customer_phone_pop_edit),mContex,"");

                                final ImageView close_tab = (ImageView) popupView.findViewById(R.id.close_tab);


                                close_tab.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        popupWindow.dismiss();
                                    }
                                });

                                customer_submit_pop.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        String name = customer_name_pop_edit.getText().toString().trim();
                                        if(name.length()>0)
                                        {
                                            int id = PosCustomer.getMaxCustomerId(db)+1;

                                            String full_name = customer_full_name_pop_edit.getText().toString().trim();
                                            String phone = customer_phone_pop_edit.getText().toString().trim();
                                            PosCustomer customer = new PosCustomer();
                                            customer.setId(id);
                                            customer.setName(name);
                                            customer.setFull_name(full_name);
                                            customer.setPhone(phone);
                                            customer.setBalance(0.0);

                                            customer.insert(db);
                                            if(!cusArr.contains(name)) {
                                                cusArr.add(name);
                                                posCustomers.add(customer);
                                                ArrayAdapter<String> adapterC = new ArrayAdapter<String>(mContex, android.R.layout.select_dialog_item, cusArr);
                                                customer_selection.setThreshold(1);//will start working from first character
                                                customer_selection.setAdapter(adapterC);
                                            }
                                            popupWindow.dismiss();
                                        }

                                    }
                                });
                                popupWindow.setOutsideTouchable(true);
                                popupWindow.setFocusable(true);
                                popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                popupWindow.showAtLocation(add_customer, Gravity.CENTER, 0, 0);
                            }
                        });




                    }
                });
            }
        });
    }
    public void updatePosView(final Context c,final DBInitialization db)
    {
        final ListView pos_product_pos_product_product_list = (ListView) ((Activity) c).findViewById(R.id.pos_product_pos_product_product_list);
        final ArrayList<PosInvoiceRegular> invoices = PosInvoiceRegular.select(db,db.COLUMN_pos_invoice_invoiceID+"="+currentInvoiceID);

        int totalQuantity=0;
        double totalAmount=0;
        double totalTax=0;
        double totalDiscount=0;
        for(PosInvoiceRegular i : invoices)
        {
            InvoiceProduct cProduct = InvoiceProduct.select(db,db.COLUMN_invoice_product_id+"="+i.getProductId()).get(0);
            totalQuantity+=i.getQuantity();
            totalAmount+=(i.getQuantity()*i.getUnitPrice());
            totalTax+= InvoiceProduct.calTax(cProduct,i.getQuantity());
            totalDiscount+=InvoiceProduct.calDis(cProduct,i.getQuantity());
            i.setProductName(i.getProductName());
        }

        //AdapterProductList adapter = new AdapterProductList(c, invoices,db);
        TextView total_sku_qnty = FontSettings.setTextFont ((TextView) ((Activity) c).findViewById(R.id.total_sku_qnty),c,String.valueOf(totalQuantity));
        TextView total_sku_amount = FontSettings.setTextFont ((TextView) ((Activity) c).findViewById(R.id.total_sku_amount),c,String.valueOf(totalAmount));




       // final TextView total_product = FontSettings.setTextFont ((TextView) ((Activity) c).findViewById(R.id.total_product),c,String.valueOf(totalQuantity));
///        TextView total_amount = FontSettings.setTextFont ((TextView) ((Activity) c).findViewById(R.id.total_amount),c,setTwoDigitDec(totalAmount));

        TextView total_amount_list = FontSettings.setTextFont ((TextView) ((Activity) c).findViewById(R.id.total_amount_list),c,setTwoDigitDec(totalAmount));
        TextView tax = FontSettings.setTextFont ((TextView) ((Activity) c).findViewById(R.id.tax),c,setTwoDigitDec(totalTax));


        TextView discount_amount = FontSettings.setTextFont ((TextView) ((Activity) c).findViewById(R.id.discount_amount),c,setTwoDigitDec(totalDiscount));

        //pos_product_pos_product_product_list.setAdapter(adapter);
        //UIComponent.updateListViewHeight(pos_product_pos_product_product_list,0);
        calFinal(c);
    }
    public void calFinal(Context c)
    {
        EditText delivery_ex = (EditText) ((Activity) c).findViewById(R.id.delivery_ex);
        EditText add_discount_per = (EditText) ((Activity) c).findViewById(R.id.add_discount_per);
        EditText add_discount_amount = (EditText) ((Activity) c).findViewById(R.id.add_discount_amount);

        TextView total_amount_list = (TextView) ((Activity) c).findViewById(R.id.total_amount_list);
        TextView tax = (TextView) ((Activity) c).findViewById(R.id.tax);
        TextView discount_amount = (TextView) ((Activity) c).findViewById(R.id.discount_amount);
        TextView net_payable = (TextView) ((Activity) c).findViewById(R.id.net_payable);


        double dev = 0.0;
        double discount_per = 0.0;
        double dis_amount = 0.0;

        double total =0.0;
        double taxA = 0.0;
        double dis = 0.0;
        double net_pay = 0.0;

        try{
            dev = Double.parseDouble(delivery_ex.getText().toString());
        }catch (Exception e) {}

        try{
            discount_per = Double.parseDouble(add_discount_per.getText().toString());
        }catch (Exception e) {}

        try{
            dis_amount = Double.parseDouble(add_discount_amount.getText().toString());
        }catch (Exception e) {}

        try{
            total = Double.parseDouble(total_amount_list.getText().toString());
        }catch (Exception e) {}

        try{
            taxA = Double.parseDouble(tax.getText().toString());
        }catch (Exception e) {}

        try{
            dis = Double.parseDouble(discount_amount.getText().toString());
        }catch (Exception e) {}

        net_pay = total+taxA+dev-dis-dis_amount;
        net_payable.setText(setTwoDigitDec(net_pay));

        if(!previous_id.equals("0"))
        {
            TextView payable_amount = (TextView) ((Activity) c).findViewById(R.id.payable_amount);
            TextView return_balance = (TextView) ((Activity) c).findViewById(R.id.return_balance);

            payable_amount.setText(String.valueOf(net_pay - TypeConvertion.parseDouble(return_balance.getText().toString())));

        }
    }
    public void paymentpopup(String payment_option)
    {
        UIComponent.hideSoftKeyboard(RegularCreateInvoice.this);
        LayoutInflater layoutInflater = (LayoutInflater) mContex
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        final View popupView = layoutInflater.inflate(R.layout.pop_up_pos_sale_payment, null);
        final PopupWindow popupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        final Button cash_btn = FontSettings.setTextFont((Button) popupView.findViewById(R.id.cash_btn),mContex,db,"pos_sale_payment_popbox_cash_invoice");
        final Button card_btn = FontSettings.setTextFont((Button) popupView.findViewById(R.id.card_btn),mContex,db,"pos_sale_payment_popbox_card_invoice");
        final Button pay_late_btn = FontSettings.setTextFont((Button) popupView.findViewById(R.id.pay_late_btn),mContex,db,"pos_sale_payment_popbox_payLater_invoice");
        final Button multi_btn = FontSettings.setTextFont((Button) popupView.findViewById(R.id.multi_btn),mContex,db,"pos_sale_payment_popbox_multi_invoice");

        final Button make_payment  = FontSettings.setTextFont((Button) popupView.findViewById(R.id.make_payment),mContex,db,"pos_sale_payment_popbox_invoice_done");
        final Button make_payment_print_later  = FontSettings.setTextFont((Button) popupView.findViewById(R.id.make_payment_print_later),mContex,db,"pos_sale_payment_popbox_invoice_done_noprint");

        final LinearLayout cash_layout = (LinearLayout) popupView.findViewById(R.id.cash_layout);
        final LinearLayout card_layout = (LinearLayout) popupView.findViewById(R.id.card_layout);
        final LinearLayout pay_later_layout = (LinearLayout) popupView.findViewById(R.id.pay_later_layout);
        final LinearLayout multi_pay_layout = (LinearLayout) popupView.findViewById(R.id.multi_pay_layout);
        final AutoCompleteTextView customer_selection = (AutoCompleteTextView) findViewById(R.id.customer_selection);
        TextView customer_full_name =(TextView) findViewById(R.id.customer_full_name);
        TextView net_payablet =(TextView) findViewById(R.id.net_payable);
        ArrayList<PosCustomer> posCustomer = PosCustomer.select(db,db.COLUMN_pos_customer_name+"=\""+customer_selection.getText().toString().trim()+"\"");
        final TextView total_product = (TextView) findViewById(R.id.total_product);
        final AutoCompleteTextView sales_person = (AutoCompleteTextView) findViewById(R.id.sales_person);
        final EditText delivery_ex = (EditText) findViewById(R.id.delivery_ex);
        final EditText add_discount_per = (EditText) findViewById(R.id.add_discount_per);
        final EditText add_discount_amount = (EditText) findViewById(R.id.add_discount_amount);
        final Spinner pos_product_pos_product_sale_type_spinner = (Spinner) findViewById(R.id.pos_product_pos_product_sale_type_spinner);


        if(payment_option.toUpperCase().equals("CASH"))
        {
            cash_btn.setTextColor(Color.parseColor("#000000"));
            card_btn.setTextColor(Color.parseColor("#000000"));
            pay_late_btn.setTextColor(Color.parseColor("#000000"));
            multi_btn.setTextColor(Color.parseColor("#000000"));

            cash_btn.setBackgroundColor(Color.parseColor("#ffc99b"));
            card_btn.setBackgroundColor(Color.parseColor("#ffffff"));
            pay_late_btn.setBackgroundColor(Color.parseColor("#ffffff"));
            multi_btn.setBackgroundColor(Color.parseColor("#ffffff"));

            card_btn.setVisibility(View.GONE);
            pay_late_btn.setVisibility(View.GONE);
            multi_btn.setVisibility(View.GONE);

            cash_layout.setVisibility(View.VISIBLE);
            card_layout.setVisibility(View.GONE);
            pay_later_layout.setVisibility(View.GONE);
            multi_pay_layout.setVisibility(View.GONE);
        }
        else if(payment_option.toUpperCase().equals("PAY LATER"))
        {
            if(customer_selection.getText().toString().trim().equals(walkinCustomer))
            {
                Toasty.error(getApplicationContext(),TextString.textSelectByVarname(db,"pos_sale_payment_popbox_select_customer").getText(), Toast.LENGTH_LONG).show();
                return;
            }
            cash_btn.setTextColor(Color.parseColor("#000000"));
            card_btn.setTextColor(Color.parseColor("#000000"));
            pay_late_btn.setTextColor(Color.parseColor("#000000"));
            multi_btn.setTextColor(Color.parseColor("#000000"));

            cash_btn.setBackgroundColor(Color.parseColor("#ffffff"));
            card_btn.setBackgroundColor(Color.parseColor("#ffffff"));
            pay_late_btn.setBackgroundColor(Color.parseColor("#ffc99b"));
            multi_btn.setBackgroundColor(Color.parseColor("#ffffff"));

            card_btn.setVisibility(View.GONE);
            cash_btn.setVisibility(View.GONE);
            multi_btn.setVisibility(View.GONE);

            cash_layout.setVisibility(View.GONE);
            card_layout.setVisibility(View.GONE);
            pay_later_layout.setVisibility(View.VISIBLE);
            multi_pay_layout.setVisibility(View.GONE);
        }
        else if(payment_option.toUpperCase().equals("MULTI"))
        {
            cash_btn.setTextColor(Color.parseColor("#000000"));
            card_btn.setTextColor(Color.parseColor("#000000"));
            pay_late_btn.setTextColor(Color.parseColor("#000000"));
            multi_btn.setTextColor(Color.parseColor("#000000"));

            cash_btn.setBackgroundColor(Color.parseColor("#ffffff"));
            card_btn.setBackgroundColor(Color.parseColor("#ffffff"));
            pay_late_btn.setBackgroundColor(Color.parseColor("#ffffff"));
            multi_btn.setBackgroundColor(Color.parseColor("#ffc99b"));

            card_btn.setVisibility(View.GONE);
            cash_btn.setVisibility(View.GONE);
            pay_late_btn.setVisibility(View.GONE);

            cash_layout.setVisibility(View.GONE);
            card_layout.setVisibility(View.GONE);
            pay_later_layout.setVisibility(View.GONE);
            multi_pay_layout.setVisibility(View.VISIBLE);
        }
        else
        {
            cash_btn.setTextColor(Color.parseColor("#000000"));
            card_btn.setTextColor(Color.parseColor("#000000"));
            pay_late_btn.setTextColor(Color.parseColor("#000000"));
            multi_btn.setTextColor(Color.parseColor("#000000"));

            cash_btn.setBackgroundColor(Color.parseColor("#ffffff"));
            card_btn.setBackgroundColor(Color.parseColor("#ffc99b"));
            pay_late_btn.setBackgroundColor(Color.parseColor("#ffffff"));
            multi_btn.setBackgroundColor(Color.parseColor("#ffffff"));

            multi_btn.setVisibility(View.GONE);
            cash_btn.setVisibility(View.GONE);
            pay_late_btn.setVisibility(View.GONE);

            cash_layout.setVisibility(View.GONE);
            card_layout.setVisibility(View.VISIBLE);
            pay_later_layout.setVisibility(View.GONE);
            multi_pay_layout.setVisibility(View.GONE);
        }

        if(posCustomer.size()>0)
        {
            FontSettings.setTextFont ((TextView) findViewById(R.id.customer_full_name),mContex,posCustomer.get(0).getFull_name());
            FontSettings.setTextFont ((TextView) findViewById(R.id.customer_mobile),mContex,posCustomer.get(0).getPhone());
            FontSettings.setTextFont ((TextView) findViewById(R.id.due),mContex,FontSettings.setTwoDigitDec(posCustomer.get(0).getBalance()));
        }
        else
        {
            FontSettings.setTextFont ((TextView) findViewById(R.id.customer_full_name),mContex,"");
            FontSettings.setTextFont ((TextView) findViewById(R.id.customer_mobile),mContex,"");
            FontSettings.setTextFont ((TextView) findViewById(R.id.due),mContex,FontSettings.setTwoDigitDec(0.0));
        }

        if(!previous_id.equals("0"))
        {
            customer_full_name.setText(customer_selection.getText().toString());
        }
        TextView payable_amount = (TextView) findViewById(R.id.payable_amount);

        if(!previous_id.equals("0") && TypeConvertion.parseDouble(payable_amount.getText().toString()) < 0)
        {
            Toasty.error(getApplicationContext(), TextString.textSelectByVarname(db,"Please Select More Product").getText(), Toast.LENGTH_SHORT, true).show();
            return;
        }

        if(customer_full_name.getText().toString().equals("") || !ArrSaleP.contains(sales_person.getText().toString().trim()))
        {
            Toasty.error(getApplicationContext(), TextString.textSelectByVarname(db,"pos_sale_payment_popbox_payment_validation").getText(), Toast.LENGTH_SHORT, true).show();
            // Toast.makeText(getApplicationContext(),TextString.textSelectByVarname(db,"pos_sale_payment_popbox_payment_validation").getText(), Toast.LENGTH_LONG).show();
            return;
        }
        if(TypeConvertion.parseDouble(net_payablet.getText().toString().trim()) < 0.0)
        {
            Toasty.error(getApplicationContext(),TextString.textSelectByVarname(db,"Invalid Payment Amount").getText(), Toast.LENGTH_LONG).show();
            return;
        }


        Button customer_submit_pop  = FontSettings.setTextFont((Button) popupView.findViewById(R.id.customer_submit_pop),mContex,db,"pos_sale_customer_add_submit_invoice");
        TextView payment_title  = FontSettings.setTextFont((TextView) popupView.findViewById(R.id.payment_title),mContex,db,"pos_sale_payment_popbox_title_invoice");

        final ImageView close_tab = (ImageView) popupView.findViewById(R.id.close_tab);




        final User cUser = new User();
        cUser.select(db,"1=1");
        //System.out.println(cUser.getUser_name()+" "+cUser.getPassword());
        final EditText pay_later_user_name = FontSettings.setTextFont((EditText) popupView.findViewById(R.id.pay_later_user_name),mContex,cUser.getUser_name());
        final EditText multi_pay_later_user_name = FontSettings.setTextFont((EditText) popupView.findViewById(R.id.multi_pay_later_user_name),mContex,cUser.getUser_name());
        final EditText multi_pay_later_password = FontSettings.setTextFont((EditText) popupView.findViewById(R.id.multi_pay_later_password),mContex,cUser.getPassword());
        final EditText pay_later_password = FontSettings.setTextFont((EditText) popupView.findViewById(R.id.pay_later_password),mContex,cUser.getPassword());

        TextView payable_cash_txt  = FontSettings.setTextFont((TextView) popupView.findViewById(R.id.payable_cash_txt),mContex,db,"pos_sale_payment_popbox_paylater_invoice_payable");

        /*cash_btn.setTextColor(Color.parseColor("#000000"));
        card_btn.setTextColor(Color.parseColor("#000000"));
        pay_late_btn.setTextColor(Color.parseColor("#000000"));
        multi_btn.setTextColor(Color.parseColor("#000000"));

        cash_btn.setBackgroundColor(Color.parseColor("#ffc99b"));
        card_btn.setBackgroundColor(Color.parseColor("#ffffff"));
        pay_late_btn.setBackgroundColor(Color.parseColor("#ffffff"));
        multi_btn.setBackgroundColor(Color.parseColor("#ffffff"));*/
        close_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        /*cash_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cash_btn.setTextColor(Color.parseColor("#000000"));
                card_btn.setTextColor(Color.parseColor("#000000"));
                pay_late_btn.setTextColor(Color.parseColor("#000000"));
                multi_btn.setTextColor(Color.parseColor("#000000"));

                cash_btn.setBackgroundColor(Color.parseColor("#ffc99b"));
                card_btn.setBackgroundColor(Color.parseColor("#ffffff"));
                pay_late_btn.setBackgroundColor(Color.parseColor("#ffffff"));
                multi_btn.setBackgroundColor(Color.parseColor("#ffffff"));

                cash_layout.setVisibility(View.VISIBLE);
                card_layout.setVisibility(View.GONE);
                pay_later_layout.setVisibility(View.GONE);
                multi_pay_layout.setVisibility(View.GONE);
            }
        });

        card_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cash_btn.setTextColor(Color.parseColor("#000000"));
                card_btn.setTextColor(Color.parseColor("#000000"));
                pay_late_btn.setTextColor(Color.parseColor("#000000"));
                multi_btn.setTextColor(Color.parseColor("#000000"));

                cash_btn.setBackgroundColor(Color.parseColor("#ffffff"));
                card_btn.setBackgroundColor(Color.parseColor("#ffc99b"));
                pay_late_btn.setBackgroundColor(Color.parseColor("#ffffff"));
                multi_btn.setBackgroundColor(Color.parseColor("#ffffff"));

                cash_layout.setVisibility(View.GONE);
                card_layout.setVisibility(View.VISIBLE);
                pay_later_layout.setVisibility(View.GONE);
                multi_pay_layout.setVisibility(View.GONE);
            }
        });

        pay_late_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(customer_selection.getText().toString().trim().equals("WALK-IN"))
                {
                    Toasty.error(getApplicationContext(),TextString.textSelectByVarname(db,"pos_sale_payment_popbox_select_customer").getText(), Toast.LENGTH_LONG).show();
                    return;
                }
                cash_btn.setTextColor(Color.parseColor("#000000"));
                card_btn.setTextColor(Color.parseColor("#000000"));
                pay_late_btn.setTextColor(Color.parseColor("#000000"));
                multi_btn.setTextColor(Color.parseColor("#000000"));

                cash_btn.setBackgroundColor(Color.parseColor("#ffffff"));
                card_btn.setBackgroundColor(Color.parseColor("#ffffff"));
                pay_late_btn.setBackgroundColor(Color.parseColor("#ffc99b"));
                multi_btn.setBackgroundColor(Color.parseColor("#ffffff"));

                cash_layout.setVisibility(View.GONE);
                card_layout.setVisibility(View.GONE);
                pay_later_layout.setVisibility(View.VISIBLE);
                multi_pay_layout.setVisibility(View.GONE);
            }
        });

        multi_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cash_btn.setTextColor(Color.parseColor("#000000"));
                card_btn.setTextColor(Color.parseColor("#000000"));
                pay_late_btn.setTextColor(Color.parseColor("#000000"));
                multi_btn.setTextColor(Color.parseColor("#000000"));

                cash_btn.setBackgroundColor(Color.parseColor("#ffffff"));
                card_btn.setBackgroundColor(Color.parseColor("#ffffff"));
                pay_late_btn.setBackgroundColor(Color.parseColor("#ffffff"));
                multi_btn.setBackgroundColor(Color.parseColor("#ffc99b"));

                cash_layout.setVisibility(View.GONE);
                card_layout.setVisibility(View.GONE);
                pay_later_layout.setVisibility(View.GONE);
                multi_pay_layout.setVisibility(View.VISIBLE);
            }
        });*/

        ///----cash section

        final TextView note_given_cash_txt  = FontSettings.setTextFont((TextView) popupView.findViewById(R.id.note_given_cash_txt),mContex,db,"pos_sale_payment_popbox_cash_invoice_note_given");
        final TextView change_cash_txt  = FontSettings.setTextFont((TextView) popupView.findViewById(R.id.change_cash_txt),mContex,db,"pos_sale_payment_popbox_cash_invoice_change");

        final TextView net_payable = (TextView) findViewById(R.id.net_payable);

        final TextView payable_cash  = FontSettings.setTextFont((TextView) popupView.findViewById(R.id.payable_cash),mContex,net_payable.getText().toString());

        if(!previous_id.equals("0"))
        {
            payable_cash.setText(payable_amount.getText().toString());
        }
        final EditText note_given_cash  = (EditText) popupView.findViewById(R.id.note_given_cash);
        final TextView change_cash  = FontSettings.setTextFont((TextView) popupView.findViewById(R.id.change_cash),mContex,"00");


        note_given_cash.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

                double payable_cash_c = 0.0;
                double note_given_cash_c = 0.0;
                double change_cash_c = 0.0;
                try {
                    payable_cash_c = Double.parseDouble(payable_cash.getText().toString());
                    note_given_cash_c = Double.parseDouble(note_given_cash.getText().toString());
                    change_cash_c = note_given_cash_c - payable_cash_c;
                    change_cash.setText(String.valueOf(change_cash_c));
                } catch (Exception e) {
                }
            }
        });

        //------card section
        final TextView cheque_no  = FontSettings.setTextFontHint((TextView) popupView.findViewById(R.id.cheque_no),mContex,db,"pos_sale_payment_popbox_card_invoice_cardno");
        final Spinner card_type = (Spinner) popupView.findViewById(R.id.card_type);
        final Spinner multi_card_type = (Spinner) popupView.findViewById(R.id.multi_card_type);
        ArrayAdapter<String> dataAdapter = spinnerDataLoad(mContex,db,db.COLUMN_card_name,db.TABLE_card,"1=1","",TextString.textSelectByVarname(db,"pos_sale_payment_popbox_card_invoice_cardtype").getText());
        card_type.setAdapter(dataAdapter);
        multi_card_type.setAdapter(dataAdapter);
        final Spinner banklist = (Spinner) popupView.findViewById(R.id.banklist);
        final Spinner multi_banklist = (Spinner) popupView.findViewById(R.id.multi_banklist);
        dataAdapter = spinnerDataLoad(mContex,db,db.COLUMN_bank_name,db.TABLE_bank,"1=1","",TextString.textSelectByVarname(db,"pos_sale_payment_popbox_card_invoice_bank").getText());
        banklist.setAdapter(dataAdapter);
        multi_banklist.setAdapter(dataAdapter);

        UIComponent.setSeletedIteamSpinner(card_type,payment_option);
        card_type.setEnabled(false);
        //-----pay later

        final TextView pay_later_user_name_txt  = FontSettings.setTextFont((TextView) popupView.findViewById(R.id.pay_later_user_name_txt),mContex,db,"pos_sale_payment_popbox_payLater_invoice_user");
        final TextView pay_later_password_txt  = FontSettings.setTextFont((TextView) popupView.findViewById(R.id.pay_later_password_txt),mContex,db,"pos_sale_payment_popbox_payLater_invoice_password");
        final Button approve_by_btn  = FontSettings.setTextFont((Button) popupView.findViewById(R.id.approve_by_btn),mContex,db,"pos_sale_payment_popbox_payLater_invoice_verify");


        //------Multi Section
        final TextView multi_payable_cash_txt  = FontSettings.setTextFont((TextView) popupView.findViewById(R.id.multi_payable_cash_txt),mContex,db,"pos_sale_payment_popbox_multi_invoice_cash");
        final TextView multi_card_txt  = FontSettings.setTextFont((TextView) popupView.findViewById(R.id.multi_card_txt),mContex,db,"pos_sale_payment_popbox_multi_invoice_card");
        final TextView multi_pay_later_txt  = FontSettings.setTextFont((TextView) popupView.findViewById(R.id.multi_pay_later_txt),mContex,db,"pos_sale_payment_popbox_multi_invoice_payLater");
        final TextView multi_balance_txt  = FontSettings.setTextFont((TextView) popupView.findViewById(R.id.multi_balance_txt),mContex,db,"pos_sale_payment_popbox_multi_invoice_balance");

        final TextView multi_cheque_no  = FontSettings.setTextFontHint((TextView) popupView.findViewById(R.id.multi_cheque_no),mContex,db,"pos_sale_payment_popbox_card_invoice_cardno");

        final TextView multi_pay_later_user_name_txt  = FontSettings.setTextFont((TextView) popupView.findViewById(R.id.multi_pay_later_user_name_txt),mContex,db,"pos_sale_payment_popbox_payLater_invoice_user");
        final TextView multi_pay_later_password_txt  = FontSettings.setTextFont((TextView) popupView.findViewById(R.id.multi_pay_later_password_txt),mContex,db,"pos_sale_payment_popbox_payLater_invoice_password");

        final TextView multi_balance  = FontSettings.setTextFont((TextView) popupView.findViewById(R.id.multi_balance),mContex,payable_cash.getText().toString());
        final EditText multi_payable_cash  = FontSettings.setTextFont((EditText) popupView.findViewById(R.id.multi_payable_cash),mContex,"");
        final EditText multi_pay_later  = FontSettings.setTextFont((EditText) popupView.findViewById(R.id.multi_pay_later),mContex,"");
        final EditText multi_card_cash  = FontSettings.setTextFont((EditText) popupView.findViewById(R.id.multi_card_cash),mContex,"");

        final LinearLayout multi_card_layout = (LinearLayout) popupView.findViewById(R.id.multi_card_layout);
        final LinearLayout multi_pay_later_layout = (LinearLayout) popupView.findViewById(R.id.multi_pay_later_layout);

        final Button multi_approve_by_btn  = FontSettings.setTextFont((Button) popupView.findViewById(R.id.multi_approve_by_btn),mContex,db,"pos_sale_payment_popbox_payLater_invoice_verify");

        multi_card_layout.setVisibility(View.GONE);
        multi_pay_later_layout.setVisibility(View.GONE);

        multi_payable_cash.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                double cash_paid = 0.0;
                double card_paid = 0.0;
                double paylater = 0.0;
                double balance = 0.0;
                double amount = 0.0;
                try {
                    if(multi_payable_cash.getText().toString().equals(""))
                    {
                        cash_paid =0.0;
                    }
                    else
                    {
                        cash_paid = Double.parseDouble(multi_payable_cash.getText().toString());
                    }

                    if(multi_card_cash.getText().toString().equals(""))
                    {
                        card_paid =0.0;
                    }
                    else
                    {
                        card_paid = Double.parseDouble(multi_card_cash.getText().toString());
                    }
                    if(multi_pay_later.getText().toString().equals(""))
                    {
                        paylater =0.0;
                    }
                    else
                    {
                        paylater = Double.parseDouble(multi_pay_later.getText().toString());
                    }
                    if(payable_cash.getText().toString().equals(""))
                    {
                        amount =0.0;
                    }
                    else
                    {
                        amount = Double.parseDouble(payable_cash.getText().toString());
                    }
                    balance = amount-cash_paid-card_paid-paylater;
                    multi_balance.setText(String.valueOf(balance));
                } catch (Exception e) {
                }
            }
        });
        multi_card_cash.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                double cash_paid = 0.0;
                double card_paid = 0.0;
                double paylater = 0.0;
                double balance = 0.0;
                double amount = 0.0;
                try {
                    if(multi_payable_cash.getText().toString().equals(""))
                    {
                        cash_paid =0.0;
                    }
                    else
                    {
                        cash_paid = Double.parseDouble(multi_payable_cash.getText().toString());
                    }

                    if(multi_card_cash.getText().toString().equals(""))
                    {
                        card_paid =0.0;
                    }
                    else
                    {
                        card_paid = Double.parseDouble(multi_card_cash.getText().toString());
                        if(card_paid>0)
                        {
                            multi_card_layout.setVisibility(View.VISIBLE);
                        }
                        else
                        {
                            multi_card_layout.setVisibility(View.GONE);
                        }
                    }
                    if(multi_pay_later.getText().toString().equals(""))
                    {
                        paylater =0.0;
                    }
                    else
                    {
                        paylater = Double.parseDouble(multi_pay_later.getText().toString());
                    }
                    if(payable_cash.getText().toString().equals(""))
                    {
                        amount =0.0;
                    }
                    else
                    {
                        amount = Double.parseDouble(payable_cash.getText().toString());
                    }
                    balance = amount-cash_paid-card_paid-paylater;
                    multi_balance.setText(String.valueOf(balance));
                } catch (Exception e) {
                }
            }
        });

        multi_pay_later.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    if(customer_selection.getText().toString().trim().equals(walkinCustomer))
                    {
                        Toasty.error(getApplicationContext(),TextString.textSelectByVarname(db,"pos_sale_payment_popbox_select_customer").getText(), Toast.LENGTH_LONG).show();
                        multi_pay_later.setEnabled(false);
                        return;
                    }
                    else {
                        multi_pay_later.setEnabled(true);
                    }
                }
            }
        });

        multi_pay_later.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

                double cash_paid = 0.0;
                double card_paid = 0.0;
                double paylater = 0.0;
                double balance = 0.0;
                double amount = 0.0;
                try {
                    if (multi_payable_cash.getText().toString().equals("")) {
                        cash_paid = 0.0;
                    } else {
                        cash_paid = Double.parseDouble(multi_payable_cash.getText().toString());
                    }

                    if (multi_card_cash.getText().toString().equals("")) {
                        card_paid = 0.0;
                    } else {
                        card_paid = Double.parseDouble(multi_card_cash.getText().toString());
                    }
                    if (multi_pay_later.getText().toString().equals("")) {
                        paylater = 0.0;
                    } else {
                        paylater = Double.parseDouble(multi_pay_later.getText().toString());
                    }
                    if (paylater > 0) {
                        multi_pay_later_layout.setVisibility(View.VISIBLE);
                    } else {
                        multi_pay_later_layout.setVisibility(View.GONE);
                    }
                    if (payable_cash.getText().toString().equals("")) {
                        amount = 0.0;
                    } else {
                        amount = Double.parseDouble(payable_cash.getText().toString());
                    }
                    balance = amount - cash_paid - card_paid - paylater;
                    multi_balance.setText(String.valueOf(balance));
                } catch (Exception e) {
                }

            }
        });
        approve_by_btn.setText(TextString.textSelectByVarname(db,"pos_sale_payment_popbox_paylater_verify").getText());
        multi_approve_by_btn.setText(TextString.textSelectByVarname(db,"pos_sale_payment_popbox_paylater_verify").getText());
        approve_by_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_name = pay_later_user_name.getText().toString();
                String password = pay_later_password.getText().toString();
                if(user_name.equals("") || password.equals(""))
                {
                    Toasty.error(getApplicationContext(),TextString.textSelectByVarname(db,"pos_sale_payment_popbox_paylater_validation_fillup").getText(), Toast.LENGTH_LONG).show();
                }
                else
                {
                    if(checkIfUserExists(db,user_name,password))
                    {
                        pay_later_user_name.setEnabled(false);
                        pay_later_password.setEnabled(false);
                        approve_by_btn.setEnabled(false);
                        approve_by_btn.setText(TextString.textSelectByVarname(db,"pos_sale_payment_popbox_paylater_verifed").getText());
                    }
                    else
                    {
                        Toasty.error(getApplicationContext(),TextString.textSelectByVarname(db,"pos_sale_payment_popbox_paylater_validation_incorrect").getText(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        multi_approve_by_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_name = multi_pay_later_user_name.getText().toString();
                String password = multi_pay_later_password.getText().toString();
                if(user_name.equals("") || password.equals(""))
                {
                    Toasty.error(getApplicationContext(),TextString.textSelectByVarname(db,"pos_sale_payment_popbox_paylater_validation_fillup").getText(), Toast.LENGTH_LONG).show();
                }
                else
                {
                    if(checkIfUserExists(db,user_name,password))
                    {
                        multi_pay_later_user_name.setEnabled(false);
                        multi_pay_later_password.setEnabled(false);
                        multi_approve_by_btn.setEnabled(false);
                        multi_approve_by_btn.setText(TextString.textSelectByVarname(db,"pos_sale_payment_popbox_paylater_verifed").getText());
                    }
                    else
                    {
                        Toasty.error(getApplicationContext(),TextString.textSelectByVarname(db,"pos_sale_payment_popbox_paylater_validation_incorrect").getText(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        make_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User cSystemInfo = new User();
                cSystemInfo.select(db,"1=1");
                String invoice_id = String.valueOf(currentInvoiceID);
                String total_amount = payable_cash.getText().toString().trim();
                TextView total_sku_qnty = (TextView) findViewById(R.id.total_sku_qnty);
                int total_quantity = TypeConvertion.parseInt(total_sku_qnty.getText().toString().trim());
                String additional_discount = add_discount_amount.getText().toString().trim();
                String tax =  ((TextView) findViewById(R.id.tax)).getText().toString();
                String total_amount_full =  ((TextView) findViewById(R.id.total_amount_list)).getText().toString();
                String iteam_discount_amount =  ((TextView) findViewById(R.id.discount_amount)).getText().toString();
                String additional_discount_percent = add_discount_per.getText().toString().trim();
                String salesP = sales_person.getText().toString();
                final String customer = customer_selection.getText().toString().trim();
                String sale_type = "";
                String payment_mode ="";
                String cash_amount ="";
                String note_given ="";
                String change ="";
                String card_amount ="";
                String cardT ="";
                String chequeN ="";
                String bank ="";
                String pay_later_amount ="";
                String verify_user ="";
                String delivery_expense = delivery_ex.getText().toString().trim();

                if(total_quantity <= 0)
                {
                    Toasty.error(getApplicationContext(),TextString.textSelectByVarname(db,"pos_sale_payment_popbox_validation_product").getText(), Toast.LENGTH_LONG).show();
                    return;
                }
                if(TypeConvertion.parseDouble(total_amount) < 0)
                {
                    Toasty.error(getApplicationContext(),TextString.textSelectByVarname(db,"Invalid Payment Amount").getText(), Toast.LENGTH_LONG).show();
                    return;
                }
                if(cash_layout.getVisibility()==View.VISIBLE)
                {
                    change = change_cash.getText().toString().trim();
                    if(TypeConvertion.parseDouble(change)<0)
                    {
                        Toasty.error(getApplicationContext(),TextString.textSelectByVarname(db,"pos_sale_payment_popbox_cash_invoice_note").getText(), Toast.LENGTH_LONG).show();
                        return;
                    }
                    payment_mode="Cash";
                    cash_amount = total_amount;
                    note_given = note_given_cash.getText().toString().trim();
                }
                else if(card_layout.getVisibility()==View.VISIBLE)
                {
                    if(card_type.getSelectedItemPosition()==0 || banklist.getSelectedItemPosition()==0 || cheque_no.getText().toString().trim().equals(""))
                    {
                        Toasty.error(getApplicationContext(),TextString.textSelectByVarname(db,"pos_sale_payment_popbox_card_validation").getText(), Toast.LENGTH_LONG).show();
                        return;
                    }
                    payment_mode="Card";
                    card_amount = total_amount;
                    cardT = card_type.getSelectedItem().toString().trim();
                    chequeN = cheque_no.getText().toString().trim();
                    bank = banklist.getSelectedItem().toString().trim();
                }
                else if(pay_later_layout.getVisibility()==View.VISIBLE)
                {
                    if(approve_by_btn.isEnabled())
                    {
                        Toasty.error(getApplicationContext(),TextString.textSelectByVarname(db,"pos_sale_payment_popbox_paylater_validation").getText(), Toast.LENGTH_LONG).show();
                        return;
                    }
                    payment_mode="Pay Later";
                    pay_later_amount = total_amount;
                    verify_user = pay_later_user_name.getText().toString().trim();
                }
                else if(multi_pay_layout.getVisibility()==View.VISIBLE)
                {
                    cash_amount = multi_payable_cash.getText().toString().trim();
                    card_amount = multi_card_cash.getText().toString().trim();
                    pay_later_amount = multi_pay_later.getText().toString().trim();
                    if(TypeConvertion.parseDouble(multi_balance.getText().toString().trim())!=0)
                    {
                        Toasty.error(getApplicationContext(),TextString.textSelectByVarname(db,"pos_sale_payment_popbox_multi_validation").getText(), Toast.LENGTH_LONG).show();
                        return;
                    }
                    if(!card_amount.equals("") && TypeConvertion.parseDouble(card_amount)>0 && (multi_card_type.getSelectedItemPosition()==0 || multi_banklist.getSelectedItemPosition()==0 || multi_cheque_no.getText().toString().trim().equals("")))
                    {
                        Toasty.error(getApplicationContext(),TextString.textSelectByVarname(db,"pos_sale_payment_popbox_multi_validation").getText(), Toast.LENGTH_LONG).show();
                        return;
                    }
                    if(!pay_later_amount.equals("") && TypeConvertion.parseDouble(pay_later_amount)>0 && multi_approve_by_btn.isEnabled())
                    {
                        Toasty.error(getApplicationContext(),TextString.textSelectByVarname(db,"pos_sale_payment_popbox_multi_validation").getText(), Toast.LENGTH_LONG).show();
                        return;
                    }

                    payment_mode="Multi";
                    cardT = multi_card_type.getSelectedItem().toString().trim();
                    chequeN = multi_cheque_no.getText().toString().trim();
                    bank = multi_banklist.getSelectedItem().toString().trim();
                    pay_later_amount = multi_pay_later.getText().toString().trim();
                    verify_user = multi_pay_later_user_name.getText().toString().trim();
                }

                final PosInvoiceHeadRegular posInvoiceHead = new PosInvoiceHeadRegular();
                posInvoiceHead.setId(currentInvoiceID);
                posInvoiceHead.setInvoice_id(invoice_id);
                posInvoiceHead.setTotal_amount_full(Double.parseDouble(total_amount_full));
                posInvoiceHead.setTotal_amount(TypeConvertion.parseDouble(total_amount));
                posInvoiceHead.setTotal_quantity(total_quantity);
                posInvoiceHead.setTotal_tax(Double.parseDouble(tax));
                posInvoiceHead.setTotal_tax(Double.parseDouble(tax));
                posInvoiceHead.setIteam_discount(TypeConvertion.parseDouble(iteam_discount_amount));
                posInvoiceHead.setAdditional_discount(TypeConvertion.parseDouble(additional_discount));
                posInvoiceHead.setAdditional_discount_percent(TypeConvertion.parseDouble(additional_discount_percent));
                posInvoiceHead.setDelivery_expense(TypeConvertion.parseDouble(delivery_expense));
                posInvoiceHead.setSales_person(salesP);
                posInvoiceHead.setCustomer(customer);
                posInvoiceHead.setPayment_mode(payment_mode);
                posInvoiceHead.setCash_amount(TypeConvertion.parseDouble(cash_amount));
                posInvoiceHead.setNote_given(TypeConvertion.parseDouble(note_given));
                posInvoiceHead.setChange(TypeConvertion.parseDouble(change));
                posInvoiceHead.setCard_amount(TypeConvertion.parseDouble(card_amount));
                posInvoiceHead.setCard_type(cardT);
                posInvoiceHead.setCheque_no(chequeN);
                posInvoiceHead.setBank(bank);
                posInvoiceHead.setPay_later_amount(TypeConvertion.parseDouble(pay_later_amount));
                posInvoiceHead.setVerify_user(verify_user);
                posInvoiceHead.setSale_type(sale_type);
                posInvoiceHead.setTotal_paid_amount(posInvoiceHead.getCard_amount()+posInvoiceHead.getCash_amount());
                posInvoiceHead.setInvoice_generate_by(cSystemInfo.getUser_name());
                posInvoiceHead.setInvoice_date(getCurrentDateTime());
                posInvoiceHead.setStatus(1);
                posInvoiceHead.setPrevious_invoice_id(previous_id);
                if(!previous_id.equals("0"))
                {
                    TextView net_payable = (TextView) findViewById(R.id.net_payable);
                    TextView return_balance = (TextView) findViewById(R.id.return_balance);
                    posInvoiceHead.setTotal_return_amount(TypeConvertion.parseDouble(return_balance.getText().toString()));
                    posInvoiceHead.setTotal_paid_after_return(TypeConvertion.parseDouble(net_payable.getText().toString()));
                    cSystemInfo.setCash_cu_balance(cSystemInfo.getCash_cu_balance()+TypeConvertion.parseDouble(net_payable.getText().toString()));
                    cSystemInfo.update(db);
                }
                else
                {
                    cSystemInfo.setCash_cu_balance(cSystemInfo.getCash_cu_balance()+TypeConvertion.parseDouble(cash_amount));
                    cSystemInfo.update(db);
                }

                new SweetAlertDialog(mContex, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText(TextString.textSelectByVarname(db,"pos_sale_payment_popbox_confermation").getText())
                        .setContentText(TextString.textSelectByVarname(db,"pos_sale_payment_popbox_confermation").getText())
                        .setConfirmText(TextString.textSelectByVarname(db,"pos_sale_payment_popbox_confermation_yes").getText())
                        .setCancelText(TextString.textSelectByVarname(db,"pos_sale_payment_popbox_confermation_no").getText())
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog ssDialog) {
                                ArrayList<ExcessCashReceived> ec = ExcessCashReceived.select(db, db.COLUMN_excss_cash_customer+"='"+customer+"'");
                                if(ec.size()>0 && ec.get(ec.size()-1).getAmount()>0.0)
                                {
                                    double amount_adjust = 0.0;
                                    if(ec.get(ec.size()-1).getAmount() >= posInvoiceHead.pay_later_amount)
                                    {
                                        double payment_to_make =  posInvoiceHead.pay_later_amount;
                                        amount_adjust = payment_to_make;
                                        for(int i= ec.size()-1;i>=0;i--)
                                        {
                                            ExcessCashReceived e = ec.get(i);
                                            if(e.getReceived_amount() >= payment_to_make)
                                            {
                                                CashPaymentReceivedDetails d = new CashPaymentReceivedDetails();
                                                d.setReceiveId(e.getReceiveId());
                                                d.setInvoiceId(currentInvoiceID);
                                                d.setAmount(payment_to_make);
                                                d.setDateTime(DateTimeCalculation.getCurrentDateTime());
                                                d.insert(db);
                                                break;
                                            }
                                            else
                                            {
                                                CashPaymentReceivedDetails d = new CashPaymentReceivedDetails();
                                                d.setReceiveId(e.getReceiveId());
                                                d.setInvoiceId(currentInvoiceID);
                                                d.setAmount(e.getReceived_amount());
                                                d.setDateTime(DateTimeCalculation.getCurrentDateTime());
                                                d.insert(db);
                                                payment_to_make = payment_to_make - e.getReceived_amount();
                                            }
                                        }
                                        ExcessCashReceived e = ec.get(ec.size()-1);
                                        e.setId(0);
                                        e.setReceiveId(currentInvoiceID);
                                        e.setReceived_amount(0);
                                        e.setAmount(e.getAmount() - posInvoiceHead.pay_later_amount);
                                        e.insert(db);
                                    }
                                    else
                                    {
                                        double payment_to_make =  ec.get(ec.size()-1).getAmount();
                                        amount_adjust = payment_to_make;
                                        for(int i= ec.size()-1;i>=0;i--)
                                        {
                                            ExcessCashReceived e = ec.get(i);
                                            if(e.getReceived_amount() >= payment_to_make)
                                            {
                                                CashPaymentReceivedDetails d = new CashPaymentReceivedDetails();
                                                d.setReceiveId(e.getReceiveId());
                                                d.setInvoiceId(currentInvoiceID);
                                                d.setAmount(payment_to_make);
                                                d.setDateTime(DateTimeCalculation.getCurrentDateTime());
                                                d.insert(db);
                                                break;
                                            }
                                            else
                                            {
                                                CashPaymentReceivedDetails d = new CashPaymentReceivedDetails();
                                                d.setReceiveId(e.getReceiveId());
                                                d.setInvoiceId(currentInvoiceID);
                                                d.setAmount(e.getReceived_amount());
                                                d.setDateTime(DateTimeCalculation.getCurrentDateTime());
                                                d.insert(db);
                                                payment_to_make = payment_to_make - e.getReceived_amount();
                                            }
                                        }
                                        ExcessCashReceived e = ec.get(ec.size()-1);
                                        e.setId(0);
                                        e.setReceiveId(currentInvoiceID);
                                        e.setReceived_amount(0);
                                        e.setAmount(0.0);
                                        e.insert(db);
                                    }
                                    posInvoiceHead.setTotal_paid_amount(posInvoiceHead.getTotal_paid_amount()+amount_adjust);

                                }

                                posInvoiceHead.insert(db);
                                PosInvoiceRegular.updateInvoiceStatus(db,currentInvoiceID,"2");

                                ArrayList<PosInvoice> pi = new ArrayList<>();
                                for(ExchangeProductQuantity exchangeProductQuantity : exchangeProductQuantities)
                                {
                                    if(exchangeProductQuantity.exchange!=0)
                                    {
                                        exchangeProductQuantity.posInvoice.setExchange(exchangeProductQuantity.exchange);
                                        exchangeProductQuantity.posInvoice.update(db);
                                        exchangeProductQuantity.posInvoice.setQuantity(exchangeProductQuantity.exchange);
                                        pi.add(exchangeProductQuantity.posInvoice);

                                        ExchangeDetails exchangeDetails = new ExchangeDetails();
                                        exchangeDetails.setPrevious_inv_id(previous_id);
                                        exchangeDetails.setNew_inv_id(String.valueOf(currentInvoiceID));
                                        exchangeDetails.setProduct_id(String.valueOf(exchangeProductQuantity.posInvoice.getProductId()));
                                        exchangeDetails.setProduct_name(exchangeProductQuantity.posInvoice.getProductName());
                                        exchangeDetails.setProduct_quantity(exchangeProductQuantity.exchange);
                                        exchangeDetails.setProduct_amount(exchangeProductQuantity.amount);
                                        exchangeDetails.setStatus(0);
                                        exchangeDetails.setDate_time(getCurrentDateTime());
                                        exchangeDetails.insert(db);
                                    }
                                }
                                //updateProductQuantityLeft (mContex,db,pi);
                                exchangeProductQuantities = new ArrayList<>();
                                previous_id ="0";
                                //DebugHelper.print(PosInvoiceHead.select(db,"1=1"));
                                hideSoftKeyboard(RegularCreateInvoice.this);
                                ssDialog.dismiss();
                                popupWindow.dismiss();
                                Intent i = new Intent(mContex, RegularInvoicePrint.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                i.putExtra("id", String.valueOf(posInvoiceHead.getInvoice_id()));
                                i.putExtra("category", "M");
                                i.putExtra("directprint", "1");
                                if(cUser.getActive_online()==1 && isInternetOn(mContex))
                                {
                                    uploadInvoiceData(posInvoiceHead.getId(),i);
                                    //mContex.startActivity(i);
                                    //Toasty.success(getApplicationContext(),TextString.textSelectByVarname(db,"pos_sale_payment_popbox_invoice_complete").getText(), Toast.LENGTH_LONG).show();
                                    //finish();
                                }
                                else
                                {

                                    finish();
                                    mContex.startActivity(i);
                                    Toasty.success(getApplicationContext(),TextString.textSelectByVarname(db,"pos_sale_payment_popbox_invoice_complete").getText(), Toast.LENGTH_LONG).show();
                                }
                            }
                        })
                        .setCancelButton(TextString.textSelectByVarname(db,"pos_sale_payment_popbox_confermation_no").getText(), new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                            }
                        })
                        .show();

            }
        });
        make_payment_print_later.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User cSystemInfo = new User();
                cSystemInfo.select(db,"1=1");
                String invoice_id = String.valueOf(currentInvoiceID);
                String total_amount = payable_cash.getText().toString().trim();
                TextView total_sku_qnty = (TextView) findViewById(R.id.total_sku_qnty);
                int total_quantity = TypeConvertion.parseInt(total_sku_qnty.getText().toString().trim());
                String additional_discount = add_discount_amount.getText().toString().trim();
                String tax =  ((TextView) findViewById(R.id.tax)).getText().toString();
                String total_amount_full =  ((TextView) findViewById(R.id.total_amount_list)).getText().toString();
                String iteam_discount_amount =  ((TextView) findViewById(R.id.discount_amount)).getText().toString();
                String additional_discount_percent = add_discount_per.getText().toString().trim();
                String salesP = sales_person.getText().toString();
                final String customer = customer_selection.getText().toString().trim();
                String sale_type = "";
                String payment_mode ="";
                String cash_amount ="";
                String note_given ="";
                String change ="";
                String card_amount ="";
                String cardT ="";
                String chequeN ="";
                String bank ="";
                String pay_later_amount ="";
                String verify_user ="";
                String delivery_expense = delivery_ex.getText().toString().trim();
                if(total_quantity <= 0)
                {
                    Toasty.error(getApplicationContext(),TextString.textSelectByVarname(db,"pos_sale_payment_popbox_validation_product").getText(), Toast.LENGTH_LONG).show();
                    return;
                }
                if(TypeConvertion.parseDouble(total_amount) < 0)
                {
                    Toasty.error(getApplicationContext(),TextString.textSelectByVarname(db,"Invalid Payment Amount").getText(), Toast.LENGTH_LONG).show();
                    return;
                }
                if(cash_layout.getVisibility()==View.VISIBLE)
                {
                    change = change_cash.getText().toString().trim();
                    if(TypeConvertion.parseDouble(change)<0)
                    {
                        Toasty.error(getApplicationContext(),TextString.textSelectByVarname(db,"pos_sale_payment_popbox_cash_invoice_note").getText(), Toast.LENGTH_LONG).show();
                        return;
                    }
                    payment_mode="Cash";
                    cash_amount = total_amount;
                    note_given = note_given_cash.getText().toString().trim();
                }
                else if(card_layout.getVisibility()==View.VISIBLE)
                {
                    if(card_type.getSelectedItemPosition()==0 || banklist.getSelectedItemPosition()==0 || cheque_no.getText().toString().trim().equals(""))
                    {
                        Toasty.error(getApplicationContext(),TextString.textSelectByVarname(db,"pos_sale_payment_popbox_card_validation").getText(), Toast.LENGTH_LONG).show();
                        return;
                    }
                    payment_mode="Card";
                    card_amount = total_amount;
                    cardT = card_type.getSelectedItem().toString().trim();
                    chequeN = cheque_no.getText().toString().trim();
                    bank = banklist.getSelectedItem().toString().trim();
                }
                else if(pay_later_layout.getVisibility()==View.VISIBLE)
                {
                    if(approve_by_btn.isEnabled())
                    {
                        Toasty.error(getApplicationContext(),TextString.textSelectByVarname(db,"pos_sale_payment_popbox_paylater_validation").getText(), Toast.LENGTH_LONG).show();
                        return;
                    }
                    payment_mode="Pay Later";
                    pay_later_amount = total_amount;
                    verify_user = pay_later_user_name.getText().toString().trim();
                }
                else if(multi_pay_layout.getVisibility()==View.VISIBLE)
                {
                    cash_amount = multi_payable_cash.getText().toString().trim();
                    card_amount = multi_card_cash.getText().toString().trim();
                    pay_later_amount = multi_pay_later.getText().toString().trim();
                    if(TypeConvertion.parseDouble(multi_balance.getText().toString().trim())!=0)
                    {
                        Toasty.error(getApplicationContext(),TextString.textSelectByVarname(db,"pos_sale_payment_popbox_multi_validation").getText(), Toast.LENGTH_LONG).show();
                        return;
                    }
                    if(!card_amount.equals("") && TypeConvertion.parseDouble(card_amount)>0 && (multi_card_type.getSelectedItemPosition()==0 || multi_banklist.getSelectedItemPosition()==0 || multi_cheque_no.getText().toString().trim().equals("")))
                    {
                        Toasty.error(getApplicationContext(),TextString.textSelectByVarname(db,"pos_sale_payment_popbox_multi_validation").getText(), Toast.LENGTH_LONG).show();
                        return;
                    }
                    if(!pay_later_amount.equals("") && TypeConvertion.parseDouble(pay_later_amount)>0 && multi_approve_by_btn.isEnabled())
                    {
                        Toasty.error(getApplicationContext(),TextString.textSelectByVarname(db,"pos_sale_payment_popbox_multi_validation").getText(), Toast.LENGTH_LONG).show();
                        return;
                    }

                    payment_mode="Multi";
                    cardT = multi_card_type.getSelectedItem().toString().trim();
                    chequeN = multi_cheque_no.getText().toString().trim();
                    bank = multi_banklist.getSelectedItem().toString().trim();
                    pay_later_amount = multi_pay_later.getText().toString().trim();
                    verify_user = multi_pay_later_user_name.getText().toString().trim();
                }

                final PosInvoiceHeadRegular posInvoiceHead = new PosInvoiceHeadRegular();
                posInvoiceHead.setId(currentInvoiceID);
                posInvoiceHead.setInvoice_id(invoice_id);
                posInvoiceHead.setTotal_amount_full(Double.parseDouble(total_amount_full));
                posInvoiceHead.setTotal_amount(TypeConvertion.parseDouble(total_amount));
                posInvoiceHead.setTotal_quantity(total_quantity);
                posInvoiceHead.setTotal_tax(Double.parseDouble(tax));
                posInvoiceHead.setTotal_tax(Double.parseDouble(tax));
                posInvoiceHead.setIteam_discount(TypeConvertion.parseDouble(iteam_discount_amount));
                posInvoiceHead.setAdditional_discount(TypeConvertion.parseDouble(additional_discount));
                posInvoiceHead.setAdditional_discount_percent(TypeConvertion.parseDouble(additional_discount_percent));
                posInvoiceHead.setDelivery_expense(TypeConvertion.parseDouble(delivery_expense));
                posInvoiceHead.setSales_person(salesP);
                posInvoiceHead.setCustomer(customer);
                posInvoiceHead.setPayment_mode(payment_mode);
                posInvoiceHead.setCash_amount(TypeConvertion.parseDouble(cash_amount));
                posInvoiceHead.setNote_given(TypeConvertion.parseDouble(note_given));
                posInvoiceHead.setChange(TypeConvertion.parseDouble(change));
                posInvoiceHead.setCard_amount(TypeConvertion.parseDouble(card_amount));
                posInvoiceHead.setCard_type(cardT);
                posInvoiceHead.setCheque_no(chequeN);
                posInvoiceHead.setBank(bank);
                posInvoiceHead.setPay_later_amount(TypeConvertion.parseDouble(pay_later_amount));
                posInvoiceHead.setVerify_user(verify_user);
                posInvoiceHead.setSale_type(sale_type);
                posInvoiceHead.setTotal_paid_amount(posInvoiceHead.getCard_amount()+posInvoiceHead.getCash_amount());
                posInvoiceHead.setInvoice_generate_by(cSystemInfo.getUser_name());
                posInvoiceHead.setInvoice_date(getCurrentDateTime());
                posInvoiceHead.setStatus(1);
                posInvoiceHead.setPrevious_invoice_id(previous_id);
                if(!previous_id.equals("0"))
                {
                    TextView net_payable = (TextView) findViewById(R.id.net_payable);
                    TextView return_balance = (TextView) findViewById(R.id.return_balance);
                    posInvoiceHead.setTotal_return_amount(TypeConvertion.parseDouble(return_balance.getText().toString()));
                    posInvoiceHead.setTotal_paid_after_return(TypeConvertion.parseDouble(net_payable.getText().toString()));
                    cSystemInfo.setCash_cu_balance(cSystemInfo.getCash_cu_balance()+TypeConvertion.parseDouble(net_payable.getText().toString()));
                    cSystemInfo.update(db);
                }
                else
                {
                    cSystemInfo.setCash_cu_balance(cSystemInfo.getCash_cu_balance()+TypeConvertion.parseDouble(cash_amount));
                    cSystemInfo.update(db);
                }

                new SweetAlertDialog(mContex, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText(TextString.textSelectByVarname(db,"pos_sale_payment_popbox_confermation").getText())
                        .setContentText(TextString.textSelectByVarname(db,"pos_sale_payment_popbox_confermation").getText())
                        .setConfirmText(TextString.textSelectByVarname(db,"pos_sale_payment_popbox_confermation_yes").getText())
                        .setCancelText(TextString.textSelectByVarname(db,"pos_sale_payment_popbox_confermation_no").getText())
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog ssDialog) {
                                ArrayList<ExcessCashReceived> ec = ExcessCashReceived.select(db, db.COLUMN_excss_cash_customer+"='"+customer+"'");
                                if(ec.size()>0 && ec.get(ec.size()-1).getAmount()>0.0)
                                {
                                    double amount_adjust = 0.0;
                                    if(ec.get(ec.size()-1).getAmount() >= posInvoiceHead.pay_later_amount)
                                    {
                                        double payment_to_make =  posInvoiceHead.pay_later_amount;
                                        amount_adjust = payment_to_make;
                                        for(int i= ec.size()-1;i>=0;i--)
                                        {
                                            ExcessCashReceived e = ec.get(i);
                                            if(e.getReceived_amount() >= payment_to_make)
                                            {
                                                CashPaymentReceivedDetails d = new CashPaymentReceivedDetails();
                                                d.setReceiveId(e.getReceiveId());
                                                d.setInvoiceId(currentInvoiceID);
                                                d.setAmount(payment_to_make);
                                                d.setDateTime(DateTimeCalculation.getCurrentDateTime());
                                                d.insert(db);
                                                break;
                                            }
                                            else
                                            {
                                                CashPaymentReceivedDetails d = new CashPaymentReceivedDetails();
                                                d.setReceiveId(e.getReceiveId());
                                                d.setInvoiceId(currentInvoiceID);
                                                d.setAmount(e.getReceived_amount());
                                                d.setDateTime(DateTimeCalculation.getCurrentDateTime());
                                                d.insert(db);
                                                payment_to_make = payment_to_make - e.getReceived_amount();
                                            }
                                        }
                                        ExcessCashReceived e = ec.get(ec.size()-1);
                                        e.setId(0);
                                        e.setReceiveId(currentInvoiceID);
                                        e.setReceived_amount(0);
                                        e.setAmount(e.getAmount() - posInvoiceHead.pay_later_amount);
                                        e.insert(db);
                                    }
                                    else
                                    {
                                        double payment_to_make =  ec.get(ec.size()-1).getAmount();
                                        amount_adjust = payment_to_make;
                                        for(int i= ec.size()-1;i>=0;i--)
                                        {
                                            ExcessCashReceived e = ec.get(i);
                                            if(e.getReceived_amount() >= payment_to_make)
                                            {
                                                CashPaymentReceivedDetails d = new CashPaymentReceivedDetails();
                                                d.setReceiveId(e.getReceiveId());
                                                d.setInvoiceId(currentInvoiceID);
                                                d.setAmount(payment_to_make);
                                                d.setDateTime(DateTimeCalculation.getCurrentDateTime());
                                                d.insert(db);
                                                break;
                                            }
                                            else
                                            {
                                                CashPaymentReceivedDetails d = new CashPaymentReceivedDetails();
                                                d.setReceiveId(e.getReceiveId());
                                                d.setInvoiceId(currentInvoiceID);
                                                d.setAmount(e.getReceived_amount());
                                                d.setDateTime(DateTimeCalculation.getCurrentDateTime());
                                                d.insert(db);
                                                payment_to_make = payment_to_make - e.getReceived_amount();
                                            }
                                        }
                                        ExcessCashReceived e = ec.get(ec.size()-1);
                                        e.setId(0);
                                        e.setReceiveId(currentInvoiceID);
                                        e.setReceived_amount(0);
                                        e.setAmount(0.0);
                                        e.insert(db);
                                    }
                                    posInvoiceHead.setTotal_paid_amount(posInvoiceHead.getTotal_paid_amount()+amount_adjust);

                                }
                                posInvoiceHead.insert(db);
                                PosInvoiceRegular.updateInvoiceStatus(db,currentInvoiceID,"2");

                                ArrayList<PosInvoice> pi = new ArrayList<>();
                                for(ExchangeProductQuantity exchangeProductQuantity : exchangeProductQuantities)
                                {
                                    if(exchangeProductQuantity.exchange!=0)
                                    {
                                        exchangeProductQuantity.posInvoice.setExchange(exchangeProductQuantity.exchange);
                                        exchangeProductQuantity.posInvoice.update(db);
                                        exchangeProductQuantity.posInvoice.setQuantity(exchangeProductQuantity.exchange);
                                        pi.add(exchangeProductQuantity.posInvoice);

                                        ExchangeDetails exchangeDetails = new ExchangeDetails();
                                        exchangeDetails.setPrevious_inv_id(previous_id);
                                        exchangeDetails.setNew_inv_id(String.valueOf(currentInvoiceID));
                                        exchangeDetails.setProduct_id(String.valueOf(exchangeProductQuantity.posInvoice.getProductId()));
                                        exchangeDetails.setProduct_name(exchangeProductQuantity.posInvoice.getProductName());
                                        exchangeDetails.setProduct_quantity(exchangeProductQuantity.exchange);
                                        exchangeDetails.setProduct_amount(exchangeProductQuantity.amount);
                                        exchangeDetails.setStatus(0);
                                        exchangeDetails.setDate_time(getCurrentDateTime());
                                        exchangeDetails.insert(db);
                                    }
                                }
                                //updateProductQuantityLeft(mContex,db,pi);
                                exchangeProductQuantities = new ArrayList<>();
                                previous_id ="0";
                                //DebugHelper.print(PosInvoiceHead.select(db,"1=1"));
                                hideSoftKeyboard(RegularCreateInvoice.this);
                                ssDialog.dismiss();
                                popupWindow.dismiss();

                                Intent next = new Intent(RegularCreateInvoice.this, RegularInvoiceList.class);
                                next.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                if(cUser.getActive_online()==1 && isInternetOn(mContex))
                                {
                                    uploadInvoiceData(posInvoiceHead.getId(),next);
                                    //startActivity(next);
                                    //Toasty.success(getApplicationContext(),TextString.textSelectByVarname(db,"pos_sale_payment_popbox_invoice_complete").getText(), Toast.LENGTH_LONG).show();
                                    //finish();
                                }
                                else
                                {
                                    startActivity(next);
                                    Toasty.success(getApplicationContext(),TextString.textSelectByVarname(db,"pos_sale_payment_popbox_invoice_complete").getText(), Toast.LENGTH_LONG).show();
                                    finish();
                                }

                            }
                        })
                        .setCancelButton(TextString.textSelectByVarname(db,"pos_sale_payment_popbox_confermation_no").getText(), new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                            }
                        })
                        .show();
            }
        });
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.showAtLocation(add_customer, Gravity.CENTER, 0, 0);
        showSoftKeyboard(RegularCreateInvoice.this);
    }
    Intent uploadNextIntent;
    int currentUploadid=0;
    public void uploadInvoiceData(int id,Intent i)
    {
        progressDoalog = new ProgressDialog(mContex);
        uploadNextIntent = i;
        try {
            progressDoalog.setMessage("Invoice Creating....");
            progressDoalog.setTitle("Please Wait");
            progressDoalog.show();

        } catch (Exception e) {

        }

        currentUploadid=id;
        final User cSystemInfo = new User();
        cSystemInfo.select(db,"1=1");
        final UploadData uploadData = new UploadData();
        try {
            uploadData.data = PosInvoiceHeadCombianRegular.getPosInvoiceCombian(PosInvoiceHeadRegular.select(db,db.COLUMN_pos_invoice_head_id+"="+id),new ArrayList<PosInvoiceHeadRegularWeb>());
        }catch (Exception e){
            Toasty.error(mContex, "Something went wrong.Please try agian", Toast.LENGTH_SHORT, true).show();
            Intent intent = new Intent(mContex, AssignmentList.class);
            mContex.startActivity(intent);
        }
        add_customer.post(new Runnable() {
            public void run() {
                String username = cSystemInfo.getUser_name();
                String password = cSystemInfo.getPassword();
                String company = cSystemInfo.getCompany_id();
                String location = cSystemInfo.getSelected_location();
                String pos = cSystemInfo.getSelected_pos();

                uploadData.url=url_pos_invoice_upload + "user_name="+username+"&password="+password+"&company="+company+"&"+"&location="+location+"&pos="+pos+"&";
                uploadData.uploaddata(mContex,mContex,"",mContex,"pos_product_details_upload");

            }
        });
    }
    public void updateList(Context c,DBInitialization db)
    {
        final ListView pos_product_pos_product_product_list = (ListView) ((Activity) c).findViewById(R.id.pos_product_pos_product_product_list);
        final ArrayList<PosInvoiceRegular> invoices = PosInvoiceRegular.select(db,db.COLUMN_pos_invoice_invoiceID+"="+currentInvoiceID);
        //DebugHelper.print(PosInvoiceRegular.select(db,db.COLUMN_pos_invoice_invoiceID+"="+currentInvoiceID));
        for(PosInvoiceRegular i : invoices)
        {
            InvoiceProduct cProduct = InvoiceProduct.select(db,db.COLUMN_invoice_product_id+"="+i.getProductId()).get(0);
            i.setProductName(cProduct.getName());
        }
        if(invoices.size()>0) {
            ScrollListView.loadListViewUpdateHeight(mContex, pos_product_pos_product_product_list, R.layout.pos_product_pos_item_list, invoices, "productListShow", 0, invoices.size(), true);
        }
        else
        {
            pos_product_pos_product_product_list.setAdapter(null);
        }
        calFinal(c);
    }

    public void productListShow(ViewData data)
    {
        final PosInvoiceRegular posInvoice = (PosInvoiceRegular) data.object;
        final LinearLayout layout_holder  = (LinearLayout) data.view.findViewById(R.id.layout_holder);
        final TextView sku = FontSettings.setTextFont((TextView) data.view.findViewById(R.id.sku),context,posInvoice.getProductName());
        final TextView quantity = FontSettings.setTextFont((TextView) data.view.findViewById(R.id.quantity),context,String.valueOf(posInvoice.getQuantity()));
        final TextView price = FontSettings.setTextFont((TextView) data.view.findViewById(R.id.price),context,String.valueOf(posInvoice.getUnitPrice()));
        final TextView amount = FontSettings.setTextFont((TextView) data.view.findViewById(R.id.amount),context,String.valueOf(posInvoice.getQuantity() *posInvoice.getUnitPrice()));

        getMeasueredLine(sku,context,35);
        layout_holder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final InvoiceProduct product = InvoiceProduct.select(db,db.COLUMN_invoice_product_id+"="+posInvoice.getProductId()).get(0);
                LayoutInflater layoutInflater = (LayoutInflater) context
                        .getSystemService(LAYOUT_INFLATER_SERVICE);
                final View popupView = layoutInflater.inflate(R.layout.pos_sale_product_edit_pop_up, null);
                final PopupWindow popupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);


                Button remove_product  = FontSettings.setTextFont((Button) popupView.findViewById(R.id.remove_product),context,db,"pos_sale_popup_remove_invoice");
                TextView sku  = FontSettings.setTextFont((TextView) popupView.findViewById(R.id.sku),context,product.getName());
                TextView price  = FontSettings.setTextFont((TextView) popupView.findViewById(R.id.price),context,TextString.textSelectByVarname(db,"dashboard_settings_currency").getText()+String.valueOf(product.getPrice()));
                LinearLayout image  = (LinearLayout) popupView.findViewById(R.id.image_holder);
                image.setVisibility(View.GONE);
                //image.setImageBitmap(FileManagerSetting.getImageFromFile(product.getPhoto(),context));
                final TextView quantity  = FontSettings.setTextFont((TextView) popupView.findViewById(R.id.quantity),context,String.valueOf(posInvoice.getQuantity()));
                final Button btn_done = FontSettings.setTextFont((Button) popupView.findViewById(R.id.btn_done),context,db,"pos_sale_invoice_item_edit_done");


                TextView dis  = FontSettings.setTextFont((TextView) popupView.findViewById(R.id.dis),context,TextString.textSelectByVarname(db,"dashboard_settings_currency").getText()+String.valueOf(calDis(product,1)));
                TextView tax  = FontSettings.setTextFont((TextView) popupView.findViewById(R.id.tax),context,TextString.textSelectByVarname(db,"dashboard_settings_currency").getText()+String.valueOf(product.getVat()));

                TextView price_txt  = FontSettings.setTextFont((TextView) popupView.findViewById(R.id.price_txt),context,db,"pos_sale_popup_price_invoice");
                TextView dis_txt  = FontSettings.setTextFont((TextView) popupView.findViewById(R.id.dis_txt),context,db,"pos_sale_popup_discount_invoice");
                TextView tax_txt  = FontSettings.setTextFont((TextView) popupView.findViewById(R.id.tax_txt),context,db,"pos_sale_popup_tax_invoice");



                ImageView close_tab = (ImageView) popupView.findViewById(R.id.close_tab);
                ImageView minus = (ImageView) popupView.findViewById(R.id.minus);
                ImageView plus = (ImageView) popupView.findViewById(R.id.plus);

                close_tab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       // updateProductList(context,product);
                        updatePosView(context,db);
                        updateList(context,db);
                        popupWindow.dismiss();
                    }
                });

                btn_done.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //updateProductList(context,product);
                        updatePosView(context,db);
                        updateList(context,db);
                        popupWindow.dismiss();
                    }
                });
                minus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addRemoveIteam(product,db,context,-1,quantity);
                    }
                });

                plus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addRemoveIteam(product,db,context,1,quantity);
                    }
                });

                remove_product.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String con = db.COLUMN_pos_invoice_productId+"="+product.getId()+" AND "+db.COLUMN_pos_invoice_invoiceID+"="+currentInvoiceID+" AND ("+COLUMN_pos_invoice_status+"=0 OR "+COLUMN_pos_invoice_status +"=9)";
                        int quantity =0;
                        try{
                            quantity = PosInvoiceRegular.select(db,con).get(0).getQuantity();
                        }catch (Exception e){}
                       // product.setQuantity_left(product.getQuantity_left()+quantity);
                        product.update(db);
                        PosInvoiceRegular.deleteIteamFromPendingList(db,currentInvoiceID,product.getId());
                        updatePosView(context,db);
                        updateList(context,db);
                        popupWindow.dismiss();
                    }
                });
                popupWindow.setOutsideTouchable(true);
                popupWindow.setFocusable(true);
                popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                popupWindow.showAtLocation(layout_holder, Gravity.CENTER, 0, 0);

            }
        });

    }

    int quantity=0;
    void addRemoveIteam(final InvoiceProduct product, final DBInitialization db, final Context c, final int count, final TextView textView)
    {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if(Integer.parseInt(textView.getText().toString())==1 && count < 0)
                    {
                        return;
                    }
                    PosInvoiceRegular invoice = new PosInvoiceRegular();
                    invoice.setInvoiceID(currentInvoiceID);
                    invoice.setProductId(product.getId());
                    invoice.setProductName(product.getName());
                    invoice.setUnitPrice(product.getPrice());

                    String con = db.COLUMN_pos_invoice_productId+"="+product.getId()+" AND "+db.COLUMN_pos_invoice_invoiceID+"="+currentInvoiceID+" AND ("+COLUMN_pos_invoice_status+"=0 OR "+COLUMN_pos_invoice_status +"=9)";
                    try{
                        quantity = PosInvoiceRegular.select(db,con).get(0).getQuantity()+count;
                    }catch (Exception e){}
                    invoice.setQuantity(quantity);
                    invoice.setDiscount(InvoiceProduct.calDis(product,quantity));
                    if(!PosSelectProduct.previous_id.equals("0")) {
                        invoice.setStatus(9);
                    }else {
                        invoice.setStatus(0);
                    }
                    invoice.insert(db);
                    //updatePosView(c,db);
                    ((Activity) c).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textView.setText(String.valueOf(quantity));
                        }
                    });
                }
                catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }).start();
    }


    public void pos_product_details_upload(ArrayList<String> data)
    {
        ArrayList<PosInvoiceHeadRegular> posInvoices = PosInvoiceHeadRegular.select(db,db.COLUMN_pos_invoice_head_id+"="+currentUploadid);
        for(PosInvoiceHeadRegular p : posInvoices)
        {
            p.setIf_synced(1);
            p.update(db);
        }
        UploadData uploadData = new UploadData();
        uploadData.data = PosInvoiceCombianRegular.setPosInvoiceCombian(PosInvoiceRegular.select(db,db.COLUMN_pos_invoice_status+"=2 AND "+db.COLUMN_pos_invoice_invoiceID+"="+currentUploadid));
        uploadData.map.put("saleType","");
        uploadData.map.put("payLaterAmount","");
        uploadData.map.put("cardPayment","");
        uploadData.map.put("cashPayment","");
        uploadData.map.put("cardNumber","");
        uploadData.map.put("CustomerName","");
        uploadData.map.put("salesPerson","");
        uploadData.map.put("additionalExpenses","");
        uploadData.map.put("id","");
        uploadData.map.put("taxIncluded","");
        uploadData.map.put("posPrefix","");

        final User cSystemInfo = new User();
        cSystemInfo.select(db,"1=1");
        String username = cSystemInfo.getUser_name();
        String password = cSystemInfo.getPassword();
        String company = cSystemInfo.getCompany_id();
        String location = cSystemInfo.getSelected_location();
        String pos = cSystemInfo.getSelected_pos();
        uploadData.url=url_pos_invoice_details_upload + "user_name="+username+"&password="+password+"&company="+company+"&"+"&location="+location+"&pos="+pos+"&sale_type=Regular&";
        uploadData.uploaddata(this,this,"",this,"pos_product_co_ledger_upload");

    }
    public void pos_product_co_ledger_upload(ArrayList<String> data)
    {
        ArrayList<PosInvoice> posInvoices = PosInvoice.select(db,db.COLUMN_pos_invoice_status+"=2 AND "+db.COLUMN_pos_invoice_invoiceID+"="+currentUploadid);
        for(PosInvoice p : posInvoices)
        {
            p.setIf_synced(1);
            p.update(db);
        }
        progressDoalog.dismiss();
        startActivity(uploadNextIntent);
        Toasty.success(getApplicationContext(),TextString.textSelectByVarname(db,"pos_sale_payment_popbox_invoice_complete").getText(), Toast.LENGTH_LONG).show();
        finish();
    }
}
