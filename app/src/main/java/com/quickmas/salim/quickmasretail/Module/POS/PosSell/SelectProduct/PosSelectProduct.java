package com.quickmas.salim.quickmasretail.Module.POS.PosSell.SelectProduct;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.arasthel.asyncjob.AsyncJob;
import com.quickmas.salim.quickmasretail.Database.DBInitialization;
import com.quickmas.salim.quickmasretail.Model.POS.Bank;
import com.quickmas.salim.quickmasretail.Model.POS.Card;
import com.quickmas.salim.quickmasretail.Model.POS.CashPaymentReceivedDetails;
import com.quickmas.salim.quickmasretail.Model.POS.Discount;
import com.quickmas.salim.quickmasretail.Model.POS.ExcessCashReceived;
import com.quickmas.salim.quickmasretail.Model.POS.ExchangeDetails;
import com.quickmas.salim.quickmasretail.Model.POS.PosInvoiceHeadWeb;
import com.quickmas.salim.quickmasretail.Model.POS.PosPaymentReceived;
import com.quickmas.salim.quickmasretail.Model.System.DashboardMenu;
import com.quickmas.salim.quickmasretail.Model.POS.PosCustomer;
import com.quickmas.salim.quickmasretail.Model.POS.PosInvoice;
import com.quickmas.salim.quickmasretail.Model.POS.PosInvoiceHead;
import com.quickmas.salim.quickmasretail.Model.POS.PosInvoicePayLaterUser;
import com.quickmas.salim.quickmasretail.Model.POS.PosProduct;
import com.quickmas.salim.quickmasretail.Model.POS.SalesPerson;
import com.quickmas.salim.quickmasretail.Model.System.TextString;
import com.quickmas.salim.quickmasretail.Model.System.User;
import com.quickmas.salim.quickmasretail.Module.Dashboard.MainActivity;
import com.quickmas.salim.quickmasretail.Module.DataSync.AssignmentList.AssignmentList;
import com.quickmas.salim.quickmasretail.Module.POS.POSInvoiceList.InvoicePayment;
import com.quickmas.salim.quickmasretail.Module.POS.POSInvoiceList.POSInvoiceList;
import com.quickmas.salim.quickmasretail.Module.POS.PosInvoicePrint.PosInvoicePrint;
import com.quickmas.salim.quickmasretail.R;
import com.quickmas.salim.quickmasretail.Service.UploadData;
import com.quickmas.salim.quickmasretail.Structure.ExchangeProductQuantity;
import com.quickmas.salim.quickmasretail.Structure.PosInvoiceCombian;
import com.quickmas.salim.quickmasretail.Structure.PosInvoiceHeadCombian;
import com.quickmas.salim.quickmasretail.Structure.PosProductSet;
import com.quickmas.salim.quickmasretail.Structure.ProductCategory;
import com.quickmas.salim.quickmasretail.Utility.DateTimeCalculation;
import com.quickmas.salim.quickmasretail.Utility.DebugHelper;
import com.quickmas.salim.quickmasretail.Utility.FileManagerSetting;
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
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_invoiceID;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_status;
import static com.quickmas.salim.quickmasretail.Model.POS.PosInvoice.deleteIteamExchange;
import static com.quickmas.salim.quickmasretail.Model.POS.PosInvoice.deleteIteamFromPendingList;
import static com.quickmas.salim.quickmasretail.Model.POS.PosProduct.calDis;
import static com.quickmas.salim.quickmasretail.Model.POS.PosProduct.sumQuanitityLeftByTitle;
import static com.quickmas.salim.quickmasretail.Model.System.DashboardMenu.selectByVarname;
import static com.quickmas.salim.quickmasretail.Model.POS.PosInvoice.updateInvoiceStatus;
import static com.quickmas.salim.quickmasretail.Model.POS.PosInvoicePayLaterUser.checkIfUserExists;
import static com.quickmas.salim.quickmasretail.Module.Exchange.ExchangeProductSelection.exchangeProductQuantities;
import static com.quickmas.salim.quickmasretail.Service.ApiSettings.url_pos_invoice_co_ledger_upload;
import static com.quickmas.salim.quickmasretail.Service.ApiSettings.url_pos_invoice_details_upload;
import static com.quickmas.salim.quickmasretail.Service.ApiSettings.url_pos_invoice_upload;
import static com.quickmas.salim.quickmasretail.Structure.ProductCategory.findProductByCategory;
import static com.quickmas.salim.quickmasretail.Structure.ProductCategory.getAllCategory;
import static com.quickmas.salim.quickmasretail.Utility.DateTimeCalculation.getCurrentDateTime;
import static com.quickmas.salim.quickmasretail.Utility.FileManagerSetting.getImageFromFile;
import static com.quickmas.salim.quickmasretail.Utility.FontSettings.getContrastVersionForColor;
import static com.quickmas.salim.quickmasretail.Utility.FontSettings.getFont;
import static com.quickmas.salim.quickmasretail.Utility.FontSettings.setTextFont;
import static com.quickmas.salim.quickmasretail.Utility.FontSettings.setTwoDigitDec;
import static com.quickmas.salim.quickmasretail.Utility.NetworkConfiguration.isInternetOn;
import static com.quickmas.salim.quickmasretail.Utility.UIComponent.getMeasueredLine;
import static com.quickmas.salim.quickmasretail.Utility.UIComponent.hideSoftKeyboard;
import static com.quickmas.salim.quickmasretail.Utility.UIComponent.setGlobalProgessBarColor;
import static com.quickmas.salim.quickmasretail.Utility.UIComponent.setImageView;
import static com.quickmas.salim.quickmasretail.Utility.UIComponent.setLinearLayoutBackground;
import static com.quickmas.salim.quickmasretail.Utility.UIComponent.showSoftKeyboard;
import static com.quickmas.salim.quickmasretail.Utility.UIComponent.spinnerDataLoad;

public class PosSelectProduct extends AppCompatActivity {
    public static String previous_id="0";
    LinearLayout product_list_holder;
    DBInitialization db;
    Context mContex;
    //Button pos_product_pos_product_detail;
    public static int currentInvoiceID=0;
    ArrayList<PosInvoice> currentProductList = new ArrayList<PosInvoice>();
    AppBarLayout product_holder;
    Button add_customer;
    MyAsynctask runner = new MyAsynctask();
    private Context context;
    HashMap<String,Integer> productQuantity = new HashMap<String,Integer>();
    int selIndex=-1;
    ArrayList<String> productsArr =new ArrayList<String>();
    Button headtext;
    ArrayList<String> ArrSaleP;
    ProgressDialog progressDoalog;
    String walkinCustomer = "Walk-In-Customer";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        mContex = this;
        context = this;
        db = new DBInitialization(mContex,null,null,1);
        setContentView(R.layout.activity_pos_select_product);
        setGlobalProgessBarColor(this,db);
        setup();

    }

    public void closeProcess()
    {
        runner.progressDialog.dismiss();
    }
    public void setup()
    {
        AsyncJob.doInBackground(new AsyncJob.OnBackgroundJob() {
            @Override
            public void doOnBackground() {
                ArrayList<PosInvoice> posInvoices = PosInvoice.select(db,COLUMN_pos_invoice_status+"=9");
                for(PosInvoice posInvoice : posInvoices)
                {
                    updateProductQuantityLeft(mContex, db,posInvoices);
                }
                deleteIteamExchange(db);
                ArrayList<PosInvoicePayLaterUser> Pos = PosInvoicePayLaterUser.select(db,"1=1");
                ArrayList<SalesPerson> salesPeople =  SalesPerson.select(db,"1=1");
                ArrSaleP = new ArrayList<>();
                //ArrSaleP.add(TextString.textSelectByVarname(db,"pos_sale_person_box2_invoice").getText());
                for(SalesPerson p: salesPeople) {
                    ArrSaleP.add(p.getName());
                }


                ArrayList<PosInvoice> pendingPayment = PosInvoice.getPaymentPendingInvoices(db);
                final ArrayList<String> arraySpinner = new ArrayList<>();

                int count=0;
                for(PosInvoice p: pendingPayment)
                {
                    if(!arraySpinner.contains(String.valueOf(p.getInvoiceID())))
                    {
                        arraySpinner.add(String.valueOf(p.getInvoiceID()));
                        if(p.getStatus()==0)
                        {
                            selIndex = count;
                        }
                        count++;
                    }
                }
                final ArrayList<PosProduct> posProuct = PosProduct.select(db,"1=1");
                countQuanitySet(posProuct);


                for(PosProduct p : posProuct)
                {
                    productsArr.add(p.getProduct_name());
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
                        Button pending_sales = FontSettings.setTextFont ((Button) findViewById(R.id.pending_sales),mContex,db,"Pending Sales");
                        headtext = pending_sales;
                        pending_sales.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(mContex, POSInvoiceList.class);
                                startActivity(intent);
                            }
                        });

                        add_customer = FontSettings.setTextFont ((Button) findViewById(R.id.add_customer),mContex,db,"pos_sale_customer_new_invoice");
                        TextView pos_product_pos_product_add_new = FontSettings.setTextFont((TextView) findViewById(R.id.pos_product_pos_product_add_new),mContex,db,"pos_sale_new_invoice");
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

                        final Button pos_payment = FontSettings.setTextFont ((Button) findViewById(R.id.pos_payment),mContex,db,"pos_sale_payment_popbox_invoice");


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
                            final Spinner pos_product_pos_pending_invoice = (Spinner) findViewById(R.id.pos_product_pos_pending_invoice);
                            final AutoCompleteTextView customer_selection = (AutoCompleteTextView) findViewById(R.id.customer_selection);
                            TextView return_balance = FontSettings.setTextFont ((TextView) findViewById(R.id.return_balance),mContex,String.valueOf(ExchangeProductQuantity.getExchangeTotalAmount(exchangeProductQuantities)));
                            pos_product_pos_pending_invoice.setEnabled(false);
                            PosInvoiceHead posInvoiceHead = PosInvoiceHead.select(db,COLUMN_pos_invoice_head_id+"="+previous_id).get(0);
                            customer_selection.setText(posInvoiceHead.getCustomer());
                            customer_selection.setEnabled(false);
                            customer_selection.setDropDownWidth(getResources().getDisplayMetrics().widthPixels);
                            pos_product_pos_product_add_new.setEnabled(false);
                            pos_product_pos_product_make_void.setEnabled(false);
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
                            arraySpinner.add(String.valueOf(PosInvoice.getNewInvoice(db)+1));
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

                        product_holder = (AppBarLayout) findViewById(R.id.product_holder);
                        float heightDp = getResources().getDisplayMetrics().heightPixels - (getResources().getDisplayMetrics().heightPixels/10);
                        final CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams)product_holder.getLayoutParams();
                        lp.height = (int)heightDp;
                        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) product_holder.getLayoutParams();
                        final CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
                        params.setBehavior(new AppBarLayout.Behavior());
                        AppBarLayout.Behavior behavior = (AppBarLayout.Behavior) params.getBehavior();


                        final Button home_new_item = FontSettings.setTextFont ((Button) findViewById(R.id.home_new_item),mContex,db,"pos_sale_invoice_bottom_additem");
                        final TextView total_product_txt =(TextView) findViewById (R.id.total_product_txt);
                        final TextView add_discount_per =(TextView) findViewById (R.id.add_discount_per);


                        behavior.setDragCallback(new AppBarLayout.Behavior.DragCallback() {
                            @Override
                            public boolean canDrag(@NonNull AppBarLayout appBarLayout) {
                                return false;
                            }
                        });
                        final NestedScrollView mNestedScrollView = (NestedScrollView) findViewById(R.id.homeNestedScrollView);
                        final Button home_details_btn = FontSettings.setTextFont ((Button) findViewById(R.id.home_details_btn),mContex,db,"pos_sale_invoice_bottom_detail");
                        final LinearLayout product_layout_holder = (LinearLayout) findViewById(R.id.product_layout_holder);
                        final LinearLayout edit_per_value = (LinearLayout) findViewById(R.id.edit_per_value);
                        final ImageView edit_customer_name = (ImageView) findViewById(R.id.edit_customer_name);
                        final ImageView edit_sales_person_name = (ImageView) findViewById(R.id.edit_sales_person_name);

                        add_discount_per.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ////////////////////
                                LayoutInflater layoutInflater = (LayoutInflater) context
                                        .getSystemService(LAYOUT_INFLATER_SERVICE);
                                final View popupView = layoutInflater.inflate(R.layout.pos_payment_type_list_holder, null);
                                final PopupWindow popupWindow = new PopupWindow(popupView,LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                LinearLayout payment_type_list_holder = popupView.findViewById(R.id.payment_type_list_holder);
                                final ArrayList<Discount> discounts = Discount.selectValid(db);
                                //DebugHelper.print(discounts);
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
                                //popupWindow.showAsDropDown(pos_payment, 0, 0)


                                ////////////////
                            }
                        });
                        edit_per_value.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ////////////////////
                                LayoutInflater layoutInflater = (LayoutInflater) context
                                        .getSystemService(LAYOUT_INFLATER_SERVICE);
                                final View popupView = layoutInflater.inflate(R.layout.pos_payment_type_list_holder, null);
                                final PopupWindow popupWindow = new PopupWindow(popupView,LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                LinearLayout payment_type_list_holder = popupView.findViewById(R.id.payment_type_list_holder);
                                final ArrayList<Discount> discounts = Discount.selectValid(db);
                                //DebugHelper.print(discounts);
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
                                //popupWindow.showAsDropDown(pos_payment, 0, 0)


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

                        home_details_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                product_holder.setExpanded(false);
                                updateList(mContex,db);
                            }
                        });

                        home_new_item.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                product_holder.setExpanded(true);
                            }
                        });

                        product_holder.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
                            @Override
                            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                                if ((appBarLayout.getHeight() - appBarLayout.getBottom()) == 0)
                                {
                                    home_new_item.setVisibility(View.GONE);
                                    total_product.setVisibility(View.VISIBLE);
                                    home_details_btn.setVisibility(View.VISIBLE);
                                    total_product_txt.setVisibility(View.VISIBLE);
                                    pos_payment.setVisibility(View.GONE);
                                }
                                else
                                {
                                    home_new_item.setVisibility(View.VISIBLE);
                                    total_product.setVisibility(View.GONE);
                                    home_details_btn.setVisibility(View.GONE);
                                    total_product_txt.setVisibility(View.GONE);
                                    pos_payment.setVisibility(View.VISIBLE);
                                }
                            }
                        });

                        ///ScrollView scr = (ScrollView) findViewById(R.id.product_list_scroll);

                        LinearLayout layout_category_holder = (LinearLayout) findViewById(R.id.layout_category_holder);
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
                        //product_list_holder = (LinearLayout) findViewById(R.id.product_list_holder);
                        LinearLayout product_category_list_holder = (LinearLayout) findViewById(R.id.product_category_list_holder);
                        final ArrayList<ProductCategory> allCategory = getAllCategory(posProuct);
                        for(int i=0;i<allCategory.size();i++)
                        {
                            final ProductCategory productCategory = allCategory.get(i);
                            Button button  = new Button(mContex);
                            LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT );
                            button.setLayoutParams(lp1);
                            final String name = allCategory.get(i).getCategory_name();
                            button = FontSettings.setTextFont(button,mContex,name);
                            product_category_list_holder.addView(button);
                            productCategory.sub_category.add(0,"All");
                            button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if(productCategory.getSub_category().size()>1)
                                    {
                                        showSubCategory(productCategory);
                                    }
                                    else
                                    {
                                        showProducts(mContex,findProductByCategory(allCategory,name));
                                    }
                                }
                            });
                        }
                        showProducts(mContex,findProductByCategory(allCategory,"all"));
                        final Spinner pos_product_pos_product_sale_type_spinner = (Spinner) findViewById(R.id.pos_product_pos_product_sale_type_spinner);
                        //Spinner pos_product_pos_net_spinner = (Spinner) findViewById(R.id.pos_product_pos_net_spinner);
                        ArrayList<String> categories = new ArrayList<String>();
                        categories.add(TextString.textSelectByVarname(db,"pos_sale_retail_invoice").getText());
                        categories.add(TextString.textSelectByVarname(db,"pos_sale_wholesale_invoice").getText());
                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(mContex, android.R.layout.simple_spinner_item, categories);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        pos_product_pos_product_sale_type_spinner.setAdapter(dataAdapter);

                        currentInvoiceID = PosInvoice.getMaxInvoice(db)+1;


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
                                            PosProduct cProdcut = PosProduct.select(db, db.COLUMN_pos_product_name + " LIKE '" + pos_product_pos_product_id.getText().toString() + "'").get(0);
                                            PosInvoice invoice = new PosInvoice();
                                            invoice.setInvoiceID(currentInvoiceID);
                                            invoice.setProductId(cProdcut.getId());
                                            invoice.setProductName(cProdcut.getProduct_name());
                                            invoice.setUnitPrice(cProdcut.getPrice());
                                            String con = db.COLUMN_pos_invoice_productId + "=" + cProdcut.getId() + " AND " + db.COLUMN_pos_invoice_invoiceID + "=" + currentInvoiceID + " AND ("+COLUMN_pos_invoice_status+"=0 OR "+COLUMN_pos_invoice_status +"=9)";
                                            int quantity = 1;
                                            try
                                            {
                                                quantity = PosInvoice.select(db, con).get(0).getQuantity() + 1;
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

                                            if(cProdcut.getQuantity_left()-1 > -1)
                                            {
                                                cProdcut.setQuantity_left(cProdcut.getQuantity_left()-1);
                                                cProdcut.update(db);
                                                invoice.insert(db);
                                                updateProductList(mContex,cProdcut);
                                            }
                                            else
                                            {
                                                Toasty.error(getApplicationContext(), TextString.textSelectByVarname(db,"No More Product in Stock").getText(), Toast.LENGTH_SHORT, true).show();
                                            }
                                        }catch (Exception e){}
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
                                    updateProductQuantityLeft(mContex,db,currentInvoiceID);
                                    PosInvoice.deletePendingInvoices(db,currentInvoiceID);
                                    //updatePosView(mContex,db);
                                    //updateList(mContex,db);
                                    pos_product_pos_product_id.setText("");
                                    if(arraySpinner.contains(String.valueOf(currentInvoiceID))) {
                                        arraySpinner.remove(String.valueOf(currentInvoiceID));
                                    }
                                    currentInvoiceID=PosInvoice.getNewInvoice(db)+1;
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

                        pos_product_pos_product_add_new.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try {
                                    updateInvoiceStatus(db,currentInvoiceID,"1");
                                    currentInvoiceID=PosInvoice.getNewInvoice(db)+1;
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


                        pos_payment.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                LayoutInflater layoutInflater = (LayoutInflater) context
                                        .getSystemService(LAYOUT_INFLATER_SERVICE);
                                final View popupView = layoutInflater.inflate(R.layout.pos_payment_type_list_holder, null);
                                final PopupWindow popupWindow = new PopupWindow(popupView,LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                LinearLayout payment_type_list_holder = popupView.findViewById(R.id.payment_type_list_holder);
                                ArrayList<Card> banklist = Card.select(db,"1=1");
                                final ArrayList<String> payment_options = new ArrayList<>();

                                payment_options.add("Cash");

                                for(Card b : banklist)
                                {
                                    payment_options.add(b.getCardName());
                                }

                                payment_options.add("Pay Later");
                                payment_options.add("Multi");

                                HashMap<String,String> colormap = new HashMap<>();
                                colormap.put("Cash","#32c5d2");
                                colormap.put("Pay Later","#e26a6a");
                                colormap.put("Multi","#61527b");

                                int n = (int) Math.ceil((double) payment_options.size() / 3);
                                for(int i=0;i<n;i++)
                                {
                                    int btn1 = i*3;
                                    int btn2 = (i*3)+1;
                                    int btn3 = (i*3)+2;
                                    LayoutInflater layoutInflater1 = (LayoutInflater) context
                                            .getSystemService(LAYOUT_INFLATER_SERVICE);
                                    final View popupView1 = layoutInflater.inflate(R.layout.pos_payment_type_list, null);

                                    if(payment_options.size()>btn1)
                                    {
                                        final Button b = FontSettings.setTextFont((Button) popupView1.findViewById(R.id.btn_1),mContex,payment_options.get(btn1));
                                        if(colormap.get(payment_options.get(btn1))!=null)
                                        {
                                            b.setTextColor(Color.WHITE);
                                            b.setBackgroundColor(Color.parseColor(colormap.get(payment_options.get(btn1))));
                                        }
                                        else
                                        {
                                            b.setTextColor(Color.WHITE);
                                            b.setBackgroundColor(Color.parseColor("#635c00"));
                                        }

                                        b.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                    paymentpopup(b.getText().toString());
                                                }
                                        });

                                    }
                                    else
                                    {
                                        ((Button) popupView1.findViewById(R.id.btn_1)).setVisibility(View.GONE);
                                    }

                                    if(payment_options.size()>btn2)
                                    {
                                        final Button b = FontSettings.setTextFont((Button) popupView1.findViewById(R.id.btn_2),mContex,payment_options.get(btn2));
                                        if(colormap.get(payment_options.get(btn2))!=null)
                                        {
                                            b.setTextColor(Color.WHITE);
                                            b.setBackgroundColor(Color.parseColor(colormap.get(payment_options.get(btn2))));
                                        }
                                        else
                                        {
                                            b.setTextColor(Color.WHITE);
                                            b.setBackgroundColor(Color.parseColor("#635c00"));
                                        }
                                        b.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                paymentpopup(b.getText().toString());
                                            }
                                        });
                                    }
                                    else
                                    {
                                        ((Button) popupView1.findViewById(R.id.btn_2)).setVisibility(View.GONE);
                                    }

                                    if(payment_options.size()>btn3)
                                    {
                                        final Button b = FontSettings.setTextFont((Button) popupView1.findViewById(R.id.btn_3),mContex,payment_options.get(btn3));
                                        if(colormap.get(payment_options.get(btn3))!=null)
                                        {
                                            b.setTextColor(Color.WHITE);
                                            b.setBackgroundColor(Color.parseColor(colormap.get(payment_options.get(btn3))));
                                        }
                                        else
                                        {
                                            b.setTextColor(Color.WHITE);
                                            b.setBackgroundColor(Color.parseColor("#635c00"));
                                        }
                                        b.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                paymentpopup(b.getText().toString());
                                            }
                                        });
                                    }
                                    else
                                    {
                                        ((Button) popupView1.findViewById(R.id.btn_3)).setVisibility(View.GONE);
                                    }
                                    payment_type_list_holder.addView(popupView1);
                                }
                              //  payment_type_list_holder
                                // Closes the popup window when touch outside.
                                popupWindow.setOutsideTouchable(true);
                                popupWindow.setFocusable(true);
                                // Removes default background.
                                popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                popupWindow.showAtLocation(pos_payment, Gravity.BOTTOM, 0, pos_payment.getHeight());
                                //popupWindow.showAsDropDown(pos_payment, 0, 0);

                            }
                        });

                    }
                });
            }
        });
    }

    public void paymentpopup(String payment_option)
    {
        UIComponent.hideSoftKeyboard(PosSelectProduct.this);
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
                int total_quantity = TypeConvertion.parseInt(total_product.getText().toString().trim());
                String additional_discount = add_discount_amount.getText().toString().trim();
                String tax =  ((TextView) findViewById(R.id.tax)).getText().toString();
                String total_amount_full =  ((TextView) findViewById(R.id.total_amount_list)).getText().toString();
                String iteam_discount_amount =  ((TextView) findViewById(R.id.discount_amount)).getText().toString();
                String additional_discount_percent = add_discount_per.getText().toString().trim();
                String salesP = sales_person.getText().toString();
                final String customer = customer_selection.getText().toString().trim();
                String sale_type = pos_product_pos_product_sale_type_spinner.getSelectedItem().toString();
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

                final PosInvoiceHead posInvoiceHead = new PosInvoiceHead();
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
                                updateInvoiceStatus(db,currentInvoiceID,"2");

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
                                updateProductQuantityLeft(mContex,db,pi);
                                exchangeProductQuantities = new ArrayList<>();
                                previous_id ="0";
                                //DebugHelper.print(PosInvoiceHead.select(db,"1=1"));
                                hideSoftKeyboard(PosSelectProduct.this);
                                ssDialog.dismiss();
                                popupWindow.dismiss();
                                Intent i = new Intent(mContex, PosInvoicePrint.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                i.putExtra("id", String.valueOf(posInvoiceHead.getInvoice_id()));
                                i.putExtra("category", "M");
                                i.putExtra("directprint", "1");
                                if(cUser.getActive_online()==1 && isInternetOn(mContex))
                                {
                                    uploadInvoiceData(posInvoiceHead.getId(),i);
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
                int total_quantity = TypeConvertion.parseInt(total_product.getText().toString().trim());
                String additional_discount = add_discount_amount.getText().toString().trim();
                String tax =  ((TextView) findViewById(R.id.tax)).getText().toString();
                String total_amount_full =  ((TextView) findViewById(R.id.total_amount_list)).getText().toString();
                String iteam_discount_amount =  ((TextView) findViewById(R.id.discount_amount)).getText().toString();
                String additional_discount_percent = add_discount_per.getText().toString().trim();
                String salesP = sales_person.getText().toString();
                final String customer = customer_selection.getText().toString().trim();
                String sale_type = pos_product_pos_product_sale_type_spinner.getSelectedItem().toString();
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

                final PosInvoiceHead posInvoiceHead = new PosInvoiceHead();
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
                                updateInvoiceStatus(db,currentInvoiceID,"2");

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
                                updateProductQuantityLeft(mContex,db,pi);
                                exchangeProductQuantities = new ArrayList<>();
                                previous_id ="0";
                                //DebugHelper.print(PosInvoiceHead.select(db,"1=1"));
                                hideSoftKeyboard(PosSelectProduct.this);
                                ssDialog.dismiss();
                                popupWindow.dismiss();

                                Intent next = new Intent(PosSelectProduct.this, PosSelectProduct.class);
                                next.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                if(cUser.getActive_online()==1 && isInternetOn(mContex))
                                {
                                    uploadInvoiceData(posInvoiceHead.getId(),next);
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
        showSoftKeyboard(PosSelectProduct.this);
    }

    public void updateList(Context c,DBInitialization db)
    {
        final ListView pos_product_pos_product_product_list = (ListView) ((Activity) c).findViewById(R.id.pos_product_pos_product_product_list);
        final ArrayList<PosInvoice> invoices = PosInvoice.select(db,db.COLUMN_pos_invoice_invoiceID+"="+currentInvoiceID);

        for(PosInvoice i : invoices)
        {
            PosProduct cProduct = PosProduct.select(db,db.COLUMN_pos_product_id+"="+i.getProductId()).get(0);
            i.setProductName(cProduct.getProduct_name());
        }
        if(invoices.size()>0) {
            ScrollListView.loadListViewUpdateHeight(context, pos_product_pos_product_product_list, R.layout.pos_product_pos_item_list, invoices, "productListShow", 0, invoices.size(), true);
        }
        else
        {
            pos_product_pos_product_product_list.setAdapter(null);
        }
        calFinal(c);
    }


    public void productListShow(ViewData data)
    {
        final PosInvoice posInvoice = (PosInvoice) data.object;
        final LinearLayout layout_holder  = (LinearLayout) data.view.findViewById(R.id.layout_holder);
        final TextView sku = FontSettings.setTextFont((TextView) data.view.findViewById(R.id.sku),context,posInvoice.getProductName());
        final TextView quantity = FontSettings.setTextFont((TextView) data.view.findViewById(R.id.quantity),context,String.valueOf(posInvoice.getQuantity()));
        final TextView price = FontSettings.setTextFont((TextView) data.view.findViewById(R.id.price),context,String.valueOf(posInvoice.getUnitPrice()));
        final TextView amount = FontSettings.setTextFont((TextView) data.view.findViewById(R.id.amount),context,String.valueOf(posInvoice.getQuantity() *posInvoice.getUnitPrice()));

        getMeasueredLine(sku,context,35);
        layout_holder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final PosProduct product = PosProduct.select(db,db.COLUMN_pos_product_id+"="+posInvoice.getProductId()).get(0);
                LayoutInflater layoutInflater = (LayoutInflater) context
                        .getSystemService(LAYOUT_INFLATER_SERVICE);
                final View popupView = layoutInflater.inflate(R.layout.pos_sale_product_edit_pop_up, null);
                final PopupWindow popupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);


                Button remove_product  = FontSettings.setTextFont((Button) popupView.findViewById(R.id.remove_product),context,db,"pos_sale_popup_remove_invoice");
                TextView sku  = FontSettings.setTextFont((TextView) popupView.findViewById(R.id.sku),context,product.getProduct_name());
                TextView price  = FontSettings.setTextFont((TextView) popupView.findViewById(R.id.price),context,TextString.textSelectByVarname(db,"dashboard_settings_currency").getText()+String.valueOf(product.getPrice()));
                ImageView image  = (ImageView) popupView.findViewById(R.id.image);
                image.setImageBitmap(FileManagerSetting.getImageFromFile(product.getPhoto(),context));
                final TextView quantity  = FontSettings.setTextFont((TextView) popupView.findViewById(R.id.quantity),context,String.valueOf(posInvoice.getQuantity()));
                final Button btn_done = FontSettings.setTextFont((Button) popupView.findViewById(R.id.btn_done),context,db,"pos_sale_invoice_item_edit_done");


                TextView dis  = FontSettings.setTextFont((TextView) popupView.findViewById(R.id.dis),context,TextString.textSelectByVarname(db,"dashboard_settings_currency").getText()+String.valueOf(calDis(product,1)));
                TextView tax  = FontSettings.setTextFont((TextView) popupView.findViewById(R.id.tax),context,TextString.textSelectByVarname(db,"dashboard_settings_currency").getText()+String.valueOf(product.getTax()));

                TextView price_txt  = FontSettings.setTextFont((TextView) popupView.findViewById(R.id.price_txt),context,db,"pos_sale_popup_price_invoice");
                TextView dis_txt  = FontSettings.setTextFont((TextView) popupView.findViewById(R.id.dis_txt),context,db,"pos_sale_popup_discount_invoice");
                TextView tax_txt  = FontSettings.setTextFont((TextView) popupView.findViewById(R.id.tax_txt),context,db,"pos_sale_popup_tax_invoice");



                ImageView close_tab = (ImageView) popupView.findViewById(R.id.close_tab);
                ImageView minus = (ImageView) popupView.findViewById(R.id.minus);
                ImageView plus = (ImageView) popupView.findViewById(R.id.plus);

                close_tab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        updateProductList(context,product);
                        updatePosView(context,db);
                        updateList(context,db);
                        popupWindow.dismiss();
                    }
                });

                btn_done.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        updateProductList(context,product);
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
                            quantity = PosInvoice.select(db,con).get(0).getQuantity();
                        }catch (Exception e){}
                        product.setQuantity_left(product.getQuantity_left()+quantity);
                        product.update(db);
                        deleteIteamFromPendingList(db,currentInvoiceID,product.getId());
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
    void addRemoveIteam(final PosProduct product,final DBInitialization db,final Context c,final int count,final TextView textView)
    {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if(Integer.parseInt(textView.getText().toString())==1 && count < 0)
                    {
                        return;
                    }
                    PosInvoice invoice = new PosInvoice();
                    invoice.setInvoiceID(currentInvoiceID);
                    invoice.setProductId(product.getId());
                    invoice.setProductName(product.getProduct_name());
                    invoice.setUnitPrice(product.getPrice());

                    String con = db.COLUMN_pos_invoice_productId+"="+product.getId()+" AND "+db.COLUMN_pos_invoice_invoiceID+"="+currentInvoiceID+" AND ("+COLUMN_pos_invoice_status+"=0 OR "+COLUMN_pos_invoice_status +"=9)";
                    try{
                        quantity = PosInvoice.select(db,con).get(0).getQuantity()+count;
                    }catch (Exception e){}
                    invoice.setQuantity(quantity);
                    invoice.setDiscount(PosProduct.calDis(product,quantity));
                    if(!PosSelectProduct.previous_id.equals("0")) {
                        invoice.setStatus(9);
                    }else {
                        invoice.setStatus(0);
                    }

                    if(product.getQuantity_left()-count > -1) {
                        product.setQuantity_left(product.getQuantity_left()-count);
                        product.update(db);
                        invoice.insert(db);
                        //updatePosView(c,db);
                        ((Activity) c).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                textView.setText(String.valueOf(quantity));
                            }
                        });
                    }
                    else
                    {
                        Toasty.error(context, TextString.textSelectByVarname(db,"No More Product in Stock").getText(), Toast.LENGTH_SHORT, true).show();
                    }
                }
                catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }).start();
    }

    public static void updatePosView(final Context c,final DBInitialization db)
    {
        final ListView pos_product_pos_product_product_list = (ListView) ((Activity) c).findViewById(R.id.pos_product_pos_product_product_list);
        final ArrayList<PosInvoice> invoices = PosInvoice.select(db,db.COLUMN_pos_invoice_invoiceID+"="+currentInvoiceID);

        int totalQuantity=0;
        double totalAmount=0;
        double totalTax=0;
        double totalDiscount=0;
        for(PosInvoice i : invoices)
        {
            PosProduct cProduct = PosProduct.select(db,db.COLUMN_pos_product_id+"="+i.getProductId()).get(0);
            totalQuantity+=i.getQuantity();
            totalAmount+=(i.getQuantity()*i.getUnitPrice());
            totalTax+= PosProduct.calTax(cProduct,i.getQuantity());
            totalDiscount+=PosProduct.calDis(cProduct,i.getQuantity());
            i.setProductName(i.getProductName());
        }

        //AdapterProductList adapter = new AdapterProductList(c, invoices,db);
        TextView total_sku_qnty = FontSettings.setTextFont ((TextView) ((Activity) c).findViewById(R.id.total_sku_qnty),c,String.valueOf(totalQuantity));
        TextView total_sku_amount = FontSettings.setTextFont ((TextView) ((Activity) c).findViewById(R.id.total_sku_amount),c,String.valueOf(totalAmount));


        TextView total_product_txt = FontSettings.setTextFont ((TextView) ((Activity) c).findViewById(R.id.total_product_txt),c,TextString.textSelectByVarname(db,"pos_sale_title_total").getText()+":");
        final TextView total_amount_txt = FontSettings.setTextFont ((TextView) ((Activity) c).findViewById(R.id.total_amount_txt),c,TextString.textSelectByVarname(db,"pos_sale_title_total_amount").getText()+":");


        final TextView total_product = FontSettings.setTextFont ((TextView) ((Activity) c).findViewById(R.id.total_product),c,String.valueOf(totalQuantity));
        TextView total_amount = FontSettings.setTextFont ((TextView) ((Activity) c).findViewById(R.id.total_amount),c,setTwoDigitDec(totalAmount));

        TextView total_amount_list = FontSettings.setTextFont ((TextView) ((Activity) c).findViewById(R.id.total_amount_list),c,setTwoDigitDec(totalAmount));
        TextView tax = FontSettings.setTextFont ((TextView) ((Activity) c).findViewById(R.id.tax),c,setTwoDigitDec(totalTax));


        TextView discount_amount = FontSettings.setTextFont ((TextView) ((Activity) c).findViewById(R.id.discount_amount),c,setTwoDigitDec(totalDiscount));

        //pos_product_pos_product_product_list.setAdapter(adapter);
        //UIComponent.updateListViewHeight(pos_product_pos_product_product_list,0);
        calFinal(c);
    }

    public static void calFinal(Context c)
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

    public String getCountQuantity(final String product)
    {
        int size=0;
        String[] productA = product.split("-");
        try {
            size =   productQuantity.get(productA[0] + "-" + productA[1]);
        }catch (Exception e)
        {
            size =   productQuantity.get(product);
        }
        return String.valueOf(size);
    }
    public  void countQuanitySet(final ArrayList<PosProduct> posProuctNew)
    {
        productQuantity = new HashMap<String,Integer>();
        for(PosProduct cProduct : posProuctNew)
        {
            String[] cNameArray = cProduct.getProduct_name().split("-");
            try {
                if(productQuantity.get(cNameArray[0] + "-" + cNameArray[1])!=null) {
                    int size = productQuantity.get(cNameArray[0] + "-" + cNameArray[1]) + cProduct.getQuantity_left();
                    productQuantity.put(cNameArray[0] + "-" + cNameArray[1], size);
                }
                else
                {
                    productQuantity.put(cNameArray[0] + "-" + cNameArray[1], cProduct.getQuantity_left());
                }
            }catch (Exception e)
            {
                if(productQuantity.get(cProduct.getProduct_name())!=null) {
                    int size = productQuantity.get(cProduct.getProduct_name()) + cProduct.getQuantity_left();
                    productQuantity.put(cProduct.getProduct_name(), size);
                }
                else
                {
                    productQuantity.put(cProduct.getProduct_name(), cProduct.getQuantity_left());
                }
            }

        }
    }

    public  String countQuanity(final String product, final ArrayList<PosProduct> posProuctNew)
    {
        int count=0;
        for(PosProduct cProduct : posProuctNew)
        {
            String[] cNameArray = cProduct.getProduct_name().split("-");
            String[] productA = product.split("-");
            try {
                if ((productA[0] + "-" + productA[1]).equals(cNameArray[0] + "-" + cNameArray[1])) {
                    count += cProduct.getQuantity_left();
                }
            }catch (Exception e)
            {
                if (product.equals(cProduct.getProduct_name())) {
                    count += cProduct.getQuantity_left();
                }
            }
        }

        return String.valueOf(count);
    }
    public static String countColorQuanity(final String product, final ArrayList<PosProduct> posProuctNew)
    {
        int count=0;
        for(PosProduct cProduct : posProuctNew)
        {
            String[] cNameArray = cProduct.getProduct_name().split("-");
            String[] productA = product.split("-");
            if (cNameArray.length > 2)
            {
                if((productA[0]+"-"+productA[1]+"-"+productA[2]).equals(cNameArray[0]+"-"+cNameArray[1]+"-"+cNameArray[2]))
                {
                    count+=cProduct.getQuantity_left();
                }
            }

        }

        return String.valueOf(count);
    }
    public static String countSizeQuanity(final String product, final ArrayList<PosProduct> posProuctNew)
    {
        int count=0;
        for(PosProduct cProduct : posProuctNew)
        {
            String[] cNameArray = cProduct.getProduct_name().split("-");
            String[] productA = product.split("-");
            if (cNameArray.length > 3)
            {
                if((productA[0]+"-"+productA[1]+"-"+productA[2]+"-"+productA[3]).equals(cNameArray[0]+"-"+cNameArray[1]+"-"+cNameArray[2]+"-"+cNameArray[3]))
                {
                    count+=cProduct.getQuantity_left();
                }
            }

        }

        return String.valueOf(count);
    }

    public static PosProduct getProductFromColorize(final String product, final ArrayList<PosProduct> posProuctNew)
    {
        int count=0;
        for(PosProduct cProduct : posProuctNew)
        {
            if(cProduct.getProduct_name().equals(product)){
                return cProduct;
            }

        }

        return new PosProduct();
    }
    ArrayList<PosProduct> posProuctNew = new ArrayList<PosProduct>();
    void showProducts(final Context c,final ArrayList<PosProduct> posProuctNew)
    {
        final ArrayList<PosProduct> posProuct = new ArrayList<>();
        ArrayList<String> pName = new ArrayList<>();
        this.posProuctNew = posProuctNew;
        for(PosProduct cProduct : posProuctNew)
        {
            if(cProduct.getCategory_status().equals("1")) {
                String[] cNameArray = cProduct.getProduct_name().split("-");
                try {
                    if (!pName.contains(cNameArray[0] + "-" + cNameArray[1])) {
                        pName.add(cNameArray[0] + "-" + cNameArray[1]);
                        posProuct.add(cProduct);
                    }
                } catch (Exception e) {
                    if (!pName.contains(cProduct.getProduct_name())) {
                        pName.add(cProduct.getProduct_name());
                        posProuct.add(cProduct);
                    }
                }
            }
            else
            {
                cProduct.setTitle(cProduct.getProduct_name());
                posProuct.add(cProduct);
            }
        }

        ListView pos_product_list = (ListView) findViewById(R.id.pos_product_list);
        ScrollListView.loadListView(context,pos_product_list,R.layout.pos_sale_product_list_item,PosProductSet.getProductSet(posProuct),"productRow",0,200,true);
    }

    public void productRow(ViewData data)
    {
        PosProductSet posProductSet = (PosProductSet) data.object;

        if(posProductSet.getProduct1().getTitle()!=null)
        {
            setData((TextView) data.view.findViewById(R.id.quantity1),(TextView) data.view.findViewById(R.id.sku1),(TextView) data.view.findViewById(R.id.qnty1),(ImageView) data.view.findViewById(R.id.myImageView1),(ImageButton) data.view.findViewById(R.id.cart1),((PosProductSet) data.object).getProduct1());
        }
        else
        {
            LinearLayout product1 = (LinearLayout) data.view.findViewById(R.id.product1);
            product1.setVisibility(View.GONE);
        }
        if(posProductSet.getProduct2().getTitle()!=null)
        {
            setData((TextView) data.view.findViewById(R.id.quantity2),(TextView) data.view.findViewById(R.id.sku2),(TextView) data.view.findViewById(R.id.qnty2),(ImageView) data.view.findViewById(R.id.myImageView2),(ImageButton) data.view.findViewById(R.id.cart2),((PosProductSet) data.object).getProduct2());
        }
        else
        {
            LinearLayout product2 = (LinearLayout) data.view.findViewById(R.id.product2);
            product2.setVisibility(View.GONE);
        }
        LinearLayout loading_layout = (LinearLayout) findViewById(R.id.loading_layout);
        loading_layout.setVisibility(View.GONE);
    }


    void setData(final TextView quantity1,final TextView sku1,final TextView qnty1,final ImageView myImageView1,final ImageButton cart1,final PosProduct cproduct)
    {
            quantity1.setText(TextString.textSelectByVarname(db,"dashboard_settings_currency").getText()+String.valueOf(cproduct.getPrice()));
            sku1.setText(cproduct.getTitle());
            /*Drawable mDefaultBackground = getResources().getDrawable(R.drawable.no_photo);
            Glide.with(context)
                .load("file://"+FileManagerSetting.getImageFilePath(cproduct.getPhoto()))
                .apply(new RequestOptions().override(400,400)
                        .error(mDefaultBackground))
                .into(myImageView1);*/
            setImageView(context,myImageView1,cproduct.getPhoto());

           qnty1.setText(getCountQuantity((cproduct).getProduct_name()));
           //qnty1.setText(countQuanity((cproduct).getProduct_name(), posProuctNew));


            myImageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data[] = cproduct.getProduct_name().split("-");
                if(cproduct.getCategory_status().equals("1") && data.length>2 && !(data[2].toUpperCase().equals("N/A") && data[3].toUpperCase().equals("N/A")))
                {
                    LayoutInflater layoutInflater = (LayoutInflater) context
                            .getSystemService(LAYOUT_INFLATER_SERVICE);
                    final View popupView = layoutInflater.inflate(R.layout.pop_up_size_color_select, null);
                    final PopupWindow popupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                    final LinearLayout color_holder = (LinearLayout) popupView.findViewById(R.id.color_holder);
                    final LinearLayout size_holder = (LinearLayout) popupView.findViewById(R.id.size_holder);
                    final LinearLayout color_main = (LinearLayout) popupView.findViewById(R.id.color_main);
                    final LinearLayout size_main = (LinearLayout) popupView.findViewById(R.id.size_main);
                    final TextView product_sku = (TextView) popupView.findViewById(R.id.product_sku);
                    final LinearLayout layout_holder = (LinearLayout) popupView.findViewById(R.id.layout_holder);

                    final User cUser = new User();
                    cUser.select(db,"1=1");


                    FontSettings.setTextFont ((TextView) popupView.findViewById(R.id.size_select_txt),context,cUser.getChoose_size());
                    FontSettings.setTextFont ((TextView) popupView.findViewById(R.id.color_select_txt),context,cUser.getChoose_color());

                    //size_select_txt
                    int height = UIComponent.getDisplayHeight(context);
                    layout_holder.setMinimumHeight(height-(height/8));

                    final ArrayList<PosProduct> selProduct = new ArrayList<>();
                    product_sku.setText(cproduct.getTitle().toString());
                    final ImageView size_close_btn = (ImageView) popupView.findViewById(R.id.size_close_btn);
                    size_close_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            popupWindow.dismiss();
                        }
                    });

                    for (PosProduct cProduct : posProuctNew) {
                        String[] cNameArray = cProduct.getProduct_name().split("-");
                        String[] pNameArray = cproduct.getProduct_name().split("-");
                        if(cNameArray.length>2) {
                            if ((cNameArray[0] + cNameArray[1]).equals((pNameArray[0] + pNameArray[1]))) {
                                selProduct.add(cProduct);
                            }
                        }
                        else
                        {
                            if (cProduct.getProduct_name().equals((pNameArray[0] + pNameArray[1]))) {
                                selProduct.add(cProduct);
                            }
                        }
                    }

                    ArrayList<String> colorList = new ArrayList<>();
                    size_main.setVisibility(View.GONE);
                    final ArrayList<Button> allButtonColor = new ArrayList<>();
                    final ArrayList<Button> allButtonSize = new ArrayList<>();
                    boolean noColorProduct = true;
                    for (final PosProduct cProduct : selProduct) {
                        if (!cProduct.getProduct_name().split("-")[2].toUpperCase().equals("N/A"))
                        {
                            noColorProduct = false;
                            if (!colorList.contains(cProduct.getProduct_name().split("-")[2].toUpperCase())) {
                                colorList.add(cProduct.getProduct_name().split("-")[2].toUpperCase());
                                LinearLayout.LayoutParams params =
                                        new LinearLayout.LayoutParams(
                                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                                LinearLayout.LayoutParams.WRAP_CONTENT);
                                params.setMargins(5, 5, 5, 5);
                                final Button btnAddARoom = new Button(context);
                                btnAddARoom.setText(cProduct.getProduct_name().split("-")[2]+" ("+countColorQuanity(cProduct.getProduct_name(), posProuctNew)+")");
                               /* if (!cProduct.getColor().equals("")) {
                                    try {
                                        btnAddARoom.setTextColor(getContrastVersionForColor(Color.parseColor(cProduct.getColor())));
                                        btnAddARoom.setBackgroundColor(Color.parseColor(cProduct.getColor()));
                                    }catch (Exception e){
                                        btnAddARoom.setBackgroundColor(Color.parseColor("#cccccc"));
                                        btnAddARoom.setTextColor(getContrastVersionForColor(Color.parseColor("#cccccc")));
                                    }
                                } else */{
                                    btnAddARoom.setTextColor(Color.WHITE);
                                    btnAddARoom.setBackgroundColor(Color.parseColor("#bab86c"));
                                }

                                btnAddARoom.setLayoutParams(params);
                                final String cColor = cProduct.getProduct_name().split("-")[2].toUpperCase();

                                btnAddARoom.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        size_holder.removeAllViews();
                                        for (int i = 0; i < color_holder.getChildCount(); i++) {
                                            LinearLayout li = (LinearLayout) color_holder.getChildAt(i);
                                            for(int j = 0; j < li.getChildCount(); j++)
                                            {
                                                Button btn = (Button) li.getChildAt(j);
                                                btn.setTextAppearance(context, android.R.style.TextAppearance_Medium);
                                                btn.setShadowLayer(
                                                        0f, // radius
                                                        0f, // dx
                                                        0f, // dy
                                                        Color.parseColor("#FFFFFF")
                                                );
                                                btn.setTextColor(Color.WHITE);
                                            }

                                        }
                                        {
                                            btnAddARoom.setTextAppearance(context, android.R.style.TextAppearance_Large);
                                            btnAddARoom.setShadowLayer(
                                                    1.5f, // radius
                                                    5.0f, // dx
                                                    5.0f, // dy
                                                    Color.parseColor("#FFFFFF")
                                            );
                                            btnAddARoom.setTextColor(Color.RED);
                                        }

                                        boolean noDataFound = true;
                                        final ArrayList<Button> allButtons = new ArrayList<>();
                                        for (final PosProduct cProductSize : selProduct) {
                                            if (cProductSize.getProduct_name().split("-").length > 3) {
                                                if (cProductSize.getProduct_name().split("-")[2].toUpperCase().equals(cColor))
                                                {
                                                    noDataFound = false;
                                                    size_main.setVisibility(View.VISIBLE);
                                                    LinearLayout.LayoutParams params =
                                                            new LinearLayout.LayoutParams(
                                                                    LinearLayout.LayoutParams.WRAP_CONTENT,
                                                                    LinearLayout.LayoutParams.WRAP_CONTENT);
                                                    params.setMargins(5, 5, 5, 5);
                                                    Button btnAddARoom = new Button(context);
                                                    btnAddARoom.setText(cProductSize.getProduct_name().split("-")[3]+" ("+countSizeQuanity(cProductSize.getProduct_name(), posProuctNew)+")");
                                                    btnAddARoom.setBackgroundColor(Color.parseColor("#cccccc"));
                                                    btnAddARoom.setLayoutParams(params);
                                                    ShapeDrawable shapedrawable = new ShapeDrawable();
                                                    shapedrawable.setShape(new RectShape());
                                                    shapedrawable.getPaint().setColor(Color.WHITE);
                                                    shapedrawable.getPaint().setStrokeWidth(3f);
                                                    shapedrawable.getPaint().setStyle(Paint.Style.STROKE);
                                                    //btnAddARoom.setBackground(shapedrawable);
                                                    btnAddARoom.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            insertInvoiceData(context, cProductSize);
                                                            popupWindow.dismiss();
                                                        }
                                                    });
                                                    allButtons.add(btnAddARoom);
                                                }
                                            } else {
                                                size_main.setVisibility(View.GONE);
                                            }
                                        }

                                        if (noDataFound)
                                        {
                                            insertInvoiceData(context, cProduct);
                                            popupWindow.dismiss();
                                        }
                                        else
                                        {
                                            int lim = allButtons.size()/2;
                                            double div = allButtons.size()%2;

                                            if(div>0.0)
                                            {
                                                lim++;
                                            }
                                            for(int i=0;i<lim;i++)
                                            {
                                                final int btn1 = i * 2;
                                                final int btn2 = i * 2 + 1;
                                                LinearLayout parent = new LinearLayout(context);
                                                parent.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                                                parent.setOrientation(LinearLayout.HORIZONTAL);

                                                if(allButtons.size()>btn1)
                                                {
                                                    parent.addView(allButtons.get(btn1));
                                                }
                                                if(allButtons.size()>btn2)
                                                {
                                                    parent.addView(allButtons.get(btn2));
                                                }
                                                size_holder.addView(parent);
                                            }

                                        }
                                    }
                                });
                                allButtonColor.add(btnAddARoom);
                                //color_holder.addView(btnAddARoom);
                            }
                        }
                        else {
                            color_main.setVisibility(View.GONE);
                            size_main.setVisibility(View.VISIBLE);
                            LinearLayout.LayoutParams params =
                                    new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.WRAP_CONTENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT);
                            params.setMargins(5, 5, 5, 5);
                            Button btnAddARoom = new Button(context);
                            btnAddARoom.setText(cProduct.getProduct_name().split("-")[3]+" ("+countSizeQuanity(cProduct.getProduct_name(), posProuctNew)+")");
                            btnAddARoom.setLayoutParams(params);
                            btnAddARoom.setBackgroundColor(Color.parseColor("#cccccc"));
                            btnAddARoom.setTextColor(getContrastVersionForColor(Color.parseColor("#cccccc")));
                            ShapeDrawable shapedrawable = new ShapeDrawable();
                            shapedrawable.setShape(new RectShape());
                            shapedrawable.getPaint().setColor(Color.WHITE);
                            shapedrawable.getPaint().setStrokeWidth(3f);
                            shapedrawable.getPaint().setStyle(Paint.Style.STROKE);
                            btnAddARoom.setBackground(shapedrawable);
                            btnAddARoom.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    insertInvoiceData(context, cProduct);
                                    popupWindow.dismiss();
                                    System.out.println(cProduct.getProduct_name());
                                }
                            });
                            allButtonSize.add(btnAddARoom);
                        }
                    }
                    if(noColorProduct)
                    {
                        int lim = allButtonSize.size()/2;
                        double div = allButtonSize.size()%2;
                        if(div>0.0)
                        {
                            lim++;
                        }
                        for(int i=0;i<lim;i++)
                        {
                            final int btn1 = i * 2;
                            final int btn2 = i * 2 + 1;
                            LinearLayout parent = new LinearLayout(context);
                            parent.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                            parent.setOrientation(LinearLayout.HORIZONTAL);

                            if(allButtonSize.size()>btn1)
                            {
                                parent.addView(allButtonSize.get(btn1));
                            }
                            if(allButtonSize.size()>btn2)
                            {
                                parent.addView(allButtonSize.get(btn2));
                            }
                            size_holder.addView(parent);
                        }
                    }
                    int lim = allButtonColor.size()/2;
                    double div = allButtonColor.size()%2;
                    if(div>0.0)
                    {
                        lim++;
                    }
                    for(int i=0;i<lim;i++)
                    {
                        final int btn1 = i * 2;
                        final int btn2 = i * 2 + 1;
                        final int btn3 = i * 2 + 2;
                        LinearLayout parent = new LinearLayout(context);
                        parent.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                        parent.setOrientation(LinearLayout.HORIZONTAL);

                        if(allButtonColor.size()>btn1)
                        {
                            parent.addView(allButtonColor.get(btn1));
                        }
                        if(allButtonColor.size()>btn2)
                        {
                            parent.addView(allButtonColor.get(btn2));
                        }
                        color_holder.addView(parent);
                    }
                    popupWindow.setOutsideTouchable(true);
                    popupWindow.setFocusable(true);
                    popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    popupWindow.showAtLocation(qnty1, Gravity.CENTER, 0, 0);
                }
                else
                {
                    insertInvoiceData(context, cproduct);
                    System.out.println(cproduct.getProduct_name());
                }
            }
        });

        cart1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater = (LayoutInflater) context
                        .getSystemService(LAYOUT_INFLATER_SERVICE);
                final View popupView = layoutInflater.inflate(R.layout.pop_up_pos_product_info, null);
                final PopupWindow popupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

                final ImageView close_tab = (ImageView) popupView.findViewById(R.id.close_tab);
                final ImageView product_img = (ImageView) popupView.findViewById(R.id.product_img);

                final TextView product_sku = setTextFont((TextView) popupView.findViewById(R.id.product_sku),context,cproduct.getTitle());
                final TextView product_price = setTextFont((TextView) popupView.findViewById(R.id.product_price),context,TextString.textSelectByVarname(db,"dashboard_settings_currency").getText()+":"+String.valueOf(cproduct.getPrice()));
                final TextView product_quantity = setTextFont((TextView) popupView.findViewById(R.id.product_quantity),context,String.valueOf(countQuanity(cproduct.getProduct_name(),posProuctNew)));
                final TextView product_description_text = setTextFont((TextView) popupView.findViewById(R.id.product_description_text),context,String.valueOf(cproduct.getFeatures()));

                setImageView(context,product_img,cproduct.getPhoto());
                /*Glide.with(context)
                        .load("file://"+FileManagerSetting.getImageFilePath(cproduct.getPhoto()))
                        .apply(new RequestOptions().override(400,400).error(R.drawable.no_photo))
                        .into(product_img);*/

                final Button description = setTextFont((Button) popupView.findViewById(R.id.product_description),context,"   Description   ");
                final Button add_info = setTextFont((Button) popupView.findViewById(R.id.product_add_info),context,"   Additional Info   ");

                final TextView product_weight = setTextFont((TextView) popupView.findViewById(R.id.product_weight),context,"Weight: "+String.valueOf(cproduct.getWeight()));
                final TextView product_dimesion = setTextFont((TextView) popupView.findViewById(R.id.product_dimesion),context,"Dimension: "+String.valueOf(cproduct.getDimensions()));
                final TextView product_include = setTextFont((TextView) popupView.findViewById(R.id.product_include),context,"Includes: "+String.valueOf(cproduct.getIncludes()));
                final TextView product_garranty = setTextFont((TextView) popupView.findViewById(R.id.product_garranty),context,"Guarantee: "+String.valueOf(cproduct.getGuarantee()));

                final LinearLayout description_holder = (LinearLayout)  popupView.findViewById(R.id.description_holder);
                final LinearLayout add_info_holder = (LinearLayout)  popupView.findViewById(R.id.add_info_holder);

                description.setTextColor(Color.parseColor("#ffffff"));
                add_info.setTextColor(Color.parseColor("#00a81e"));

                description.setBackgroundColor(Color.parseColor("#00a81e"));
                add_info.setBackgroundColor(Color.parseColor("#ffffff"));

                add_info_holder.setVisibility(View.GONE);
                description_holder.setVisibility(View.VISIBLE);
                final Button ok_btn = setTextFont((Button) popupView.findViewById(R.id.ok_btn),context,"   OK   ");

                ok_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });

                description.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        description.setTextColor(Color.parseColor("#ffffff"));
                        add_info.setTextColor(Color.parseColor("#00a81e"));

                        description.setBackgroundColor(Color.parseColor("#00a81e"));
                        add_info.setBackgroundColor(Color.parseColor("#ffffff"));
                        add_info_holder.setVisibility(View.GONE);
                        description_holder.setVisibility(View.VISIBLE);
                    }
                });
                add_info.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        add_info.setTextColor(Color.parseColor("#ffffff"));
                        description.setTextColor(Color.parseColor("#00a81e"));

                        add_info.setBackgroundColor(Color.parseColor("#00a81e"));
                        description.setBackgroundColor(Color.parseColor("#ffffff"));

                        add_info_holder.setVisibility(View.VISIBLE);
                        description_holder.setVisibility(View.GONE);
                    }
                });




                close_tab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        popupWindow.dismiss();
                    }
                });

                ////
                final Spinner color_spinner = (Spinner) popupView.findViewById(R.id.color_spinner);
                final Spinner size_spinner = (Spinner) popupView.findViewById(R.id.size_spinner);
                final LinearLayout quantity_selector_holder = (LinearLayout)  popupView.findViewById(R.id.quantity_selector_holder);
                final TextView quantity = (TextView)  popupView.findViewById(R.id.quantity);
                final TextView product_sold_quantity = setTextFont((TextView) popupView.findViewById(R.id.product_sold_quantity),context,"");

                final ImageView minus = (ImageView) popupView.findViewById(R.id.minus);
                final ImageView plus = (ImageView) popupView.findViewById(R.id.plus);


                final ArrayList<String> colorArray = new ArrayList<String>();
                final ArrayList<String> sizeArray = new ArrayList<String>();
                final User cUser = new User();
                cUser.select(db,"1=1");

                colorArray.add(cUser.getChoose_color());
                for (final PosProduct cProduct : posProuctNew)
                {
                    if(cproduct.getTitle().equals(cProduct.getTitle()) && cproduct.getCategory_status().equals("1")) {
                        if (cProduct.getProduct_name().split("-").length > 2 && !cProduct.getProduct_name().split("-")[2].toUpperCase().equals("N/A"))
                        {
                            if(!colorArray.contains(cProduct.getProduct_name().split("-")[2]))
                            {
                                String txt = cProduct.getProduct_name().split("-")[2];
                                colorArray.add(txt);
                            }
                        }
                    }
                }


                color_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        sizeArray.removeAll(sizeArray);
                        sizeArray.add(cUser.getChoose_size());
                        for (final PosProduct cProduct : posProuctNew)
                        {
                            if(cproduct.getTitle().equals(cProduct.getTitle())) {
                                if (cProduct.getProduct_name().split("-").length > 3)
                                {
                                    if((cProduct.getProduct_name().split("-")[2]).equals(color_spinner.getSelectedItem().toString()) && !sizeArray.contains(cProduct.getProduct_name().split("-")[3]))
                                    {
                                        String txt = cProduct.getProduct_name().split("-")[3];
                                        sizeArray.add(txt);
                                    }
                                }
                            }
                        }
                        if(sizeArray.size()>1) {
                            size_spinner.setVisibility(View.VISIBLE);
                            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(context,R.layout.spinner_item,sizeArray){
                                @Override
                                public View getDropDownView(int position, View convertView,
                                                            ViewGroup parent) {
                                    View view = super.getDropDownView(position, convertView, parent);
                                    TextView tv = (TextView) view;
                                    if(position==0) {
                                        tv.setBackgroundColor(Color.parseColor("#00a81e"));
                                    }
                                    else {
                                        tv.setBackgroundColor(Color.parseColor("#ffffff"));
                                    }
                                    return view;
                                }
                                @Override
                                public boolean isEnabled(int position) {
                                    return position!=0;
                                }
                            };
                            spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
                            size_spinner.setAdapter(spinnerArrayAdapter);
                            size_spinner.performClick();
                        }
                        else
                        {
                            size_spinner.setVisibility(View.GONE);
                            if(color_spinner.getSelectedItemPosition()>0) {
                                quantity.setText(String.valueOf(0));
                                String[] arrays = cproduct.getProduct_name().split("-");
                                String productName="";
                                if(sizeArray.size()>1)
                                {
                                    if (colorArray.size() > 1) {
                                        productName = arrays[0] + "-" + arrays[1] + "-" + color_spinner.getSelectedItem().toString() + "-" + size_spinner.getSelectedItem().toString();
                                    } else {
                                        productName = arrays[0] + "-" + arrays[1] + "-N/A-" + size_spinner.getSelectedItem().toString();
                                    }
                                }
                                else if(colorArray.size()>1)
                                {
                                    productName = arrays[0] + "-" + arrays[1] + "-" + color_spinner.getSelectedItem().toString();
                                }
                                else
                                {
                                    productName = cproduct.getProduct_name();
                                }
                                PosProduct tProduct = getProductFromColorize(productName, posProuctNew);
                                product_quantity.setText(String.valueOf(tProduct.getQuantity_left()));
                                String con = db.COLUMN_pos_invoice_productId+"="+tProduct.getId()+" AND "+db.COLUMN_pos_invoice_invoiceID+"="+currentInvoiceID+" AND ("+COLUMN_pos_invoice_status+"=0 OR "+COLUMN_pos_invoice_status +"=9)";

                                try{
                                    product_sold_quantity.setText(String.valueOf(PosInvoice.select(db,con).get(0).getQuantity()));
                                }catch (Exception e){}
                                quantity_selector_holder.setVisibility(View.VISIBLE);
                            }
                            else
                            {
                                quantity.setText(String.valueOf(0));
                                product_sold_quantity.setText(String.valueOf(0));
                                quantity_selector_holder.setVisibility(View.GONE);
                            }
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {
                        // your code here
                    }

                });

                size_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        if(size_spinner.getSelectedItemPosition()>0)
                        {
                            quantity.setText(String.valueOf(0));
                            String[] arrays = cproduct.getProduct_name().split("-");
                            String productName="";

                            if(sizeArray.size()>1)
                            {
                                if (colorArray.size() > 1) {
                                    productName = arrays[0] + "-" + arrays[1] + "-" + color_spinner.getSelectedItem().toString()+ "-" + size_spinner.getSelectedItem().toString();
                                } else {
                                    productName = arrays[0] + "-" + arrays[1] + "-N/A-" + size_spinner.getSelectedItem().toString();
                                }
                            }
                            else if(colorArray.size()>1)
                            {
                                productName = arrays[0] + "-" + arrays[1] + "-" + color_spinner.getSelectedItem().toString();
                            }
                            else
                            {
                                productName = cproduct.getProduct_name();
                            }
                            PosProduct tProduct = getProductFromColorize(productName, posProuctNew);
                            product_quantity.setText(String.valueOf(tProduct.getQuantity_left()));
                            String con = db.COLUMN_pos_invoice_productId+"="+tProduct.getId()+" AND "+db.COLUMN_pos_invoice_invoiceID+"="+currentInvoiceID+" AND ("+COLUMN_pos_invoice_status+"=0 OR "+COLUMN_pos_invoice_status +"=9)";

                            try{
                                product_sold_quantity.setText(String.valueOf(PosInvoice.select(db,con).get(0).getQuantity()));
                            }catch (Exception e){}
                            quantity_selector_holder.setVisibility(View.VISIBLE);
                        }
                        else
                        {
                            quantity.setText(String.valueOf(0));
                            product_sold_quantity.setText(String.valueOf(0));
                            quantity_selector_holder.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {
                        // your code here
                    }

                });

                if(colorArray.size()>1) {
                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(context,R.layout.spinner_item,colorArray){
                        @Override
                        public View getDropDownView(int position, View convertView,
                                                    ViewGroup parent) {
                            View view = super.getDropDownView(position, convertView, parent);
                            TextView tv = (TextView) view;
                            if(position==0) {
                                tv.setBackgroundColor(Color.parseColor("#00a81e"));
                            }
                            else {
                                tv.setBackgroundColor(Color.parseColor("#ffffff"));
                            }
                            return view;
                        }
                        @Override
                        public boolean isEnabled(int position) {
                            return position!=0;
                        }
                    };
                    spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
                    color_spinner.setAdapter(spinnerArrayAdapter);
                }
                else
                {
                    color_spinner.setVisibility(View.GONE);
                    sizeArray.removeAll(sizeArray);
                    sizeArray.add(cUser.getChoose_size());
                    for (final PosProduct cProduct : posProuctNew)
                    {
                        if(cproduct.getTitle().equals(cProduct.getTitle())) {
                            if (cProduct.getProduct_name().split("-").length > 3 && cProduct.getCategory_status().equals("1") && !cProduct.getProduct_name().split("-")[3].equals("N/A"))
                            {
                                if(!sizeArray.contains(cProduct.getProduct_name().split("-")[3]))
                                {
                                    String txt = cProduct.getProduct_name().split("-")[3];
                                    sizeArray.add(txt);
                                }
                            }
                        }
                    }
                    if(sizeArray.size()>1) {
                        size_spinner.setVisibility(View.VISIBLE);
                        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(context,R.layout.spinner_item,sizeArray){
                            @Override
                            public View getDropDownView(int position, View convertView,
                                                        ViewGroup parent) {
                                View view = super.getDropDownView(position, convertView, parent);
                                TextView tv = (TextView) view;
                                if(position==0) {
                                    tv.setBackgroundColor(Color.parseColor("#00a81e"));
                                }
                                else {
                                    tv.setBackgroundColor(Color.parseColor("#ffffff"));
                                }
                                return view;
                            }
                            @Override
                            public boolean isEnabled(int position) {
                                return position!=0;
                            }
                        };
                        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
                        size_spinner.setAdapter(spinnerArrayAdapter);
                    }
                    else
                    {
                        size_spinner.setVisibility(View.GONE);
                    }
                }

                if(colorArray.size()>1 || sizeArray.size()>1)
                {
                    quantity_selector_holder.setVisibility(View.GONE);
                }


                minus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String[] arrays = cproduct.getProduct_name().split("-");
                        String productName="";
                        if(cproduct.getCategory_status().equals("1")) {
                            if (sizeArray.size() > 1) {
                                if (colorArray.size() > 1) {
                                    productName = arrays[0] + "-" + arrays[1] + "-" + color_spinner.getSelectedItem().toString() + "-" + size_spinner.getSelectedItem().toString();
                                } else {
                                    productName = arrays[0] + "-" + arrays[1] + "-N/A-" + size_spinner.getSelectedItem().toString();
                                }
                            } else if (colorArray.size() > 1) {
                                productName = arrays[0] + "-" + arrays[1] + "-" + color_spinner.getSelectedItem().toString();
                            } else {
                                productName = cproduct.getProduct_name();
                            }
                        }
                        else
                        {
                            productName = cproduct.getProduct_name();
                        }
                        addRemoveIteam(getProductFromColorize(productName, posProuctNew), db, -1, quantity,product_sold_quantity,product_quantity);
                    }
                });

                plus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String[] arrays = cproduct.getProduct_name().split("-");
                        String productName="";
                        if(cproduct.getCategory_status().equals("1")) {
                            if (sizeArray.size() > 1) {
                                if (colorArray.size() > 1) {
                                    productName = arrays[0] + "-" + arrays[1] + "-" + color_spinner.getSelectedItem().toString() + "-" + size_spinner.getSelectedItem().toString();
                                } else {
                                    productName = arrays[0] + "-" + arrays[1] + "-N/A-" + size_spinner.getSelectedItem().toString();
                                }
                            } else if (colorArray.size() > 1) {
                                productName = arrays[0] + "-" + arrays[1] + "-" + color_spinner.getSelectedItem().toString();
                            } else {
                                productName = cproduct.getProduct_name();
                            }
                        }
                        else
                        {
                            productName = cproduct.getProduct_name();
                        }
                        addRemoveIteam(getProductFromColorize(productName,posProuctNew),db,1,quantity,product_sold_quantity,product_quantity);
                    }
                });



                popupWindow.setOutsideTouchable(true);
                popupWindow.setFocusable(true);
                popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                popupWindow.showAtLocation(qnty1, Gravity.CENTER, 0, 0);

            }
        });

    }


    void insertInvoiceData(Context c, PosProduct product)
    {
        PosInvoice invoice = new PosInvoice();
        invoice.setInvoiceID(currentInvoiceID);
        invoice.setProductId(product.getId());
        invoice.setProductName(product.getProduct_name());
        invoice.setUnitPrice(product.getPrice());
        String con = db.COLUMN_pos_invoice_productId+"="+product.getId()+" AND "+db.COLUMN_pos_invoice_invoiceID+"="+currentInvoiceID+" AND ("+COLUMN_pos_invoice_status+"=0 OR "+COLUMN_pos_invoice_status +"=9)";
        int quantity = 1;
        try{
            quantity = PosInvoice.select(db,con).get(0).getQuantity()+1;
        }catch (Exception e){}

        invoice.setQuantity(quantity);
        PosProduct cProduct = PosProduct.select(db,db.COLUMN_pos_product_id+"="+product.getId()).get(0);
        invoice.setTax(PosProduct.calTax(cProduct,invoice.getQuantity()));
        invoice.setDiscount(PosProduct.calDis(cProduct,invoice.getQuantity()));
        if(previous_id.equals("0"))
        {
            invoice.setStatus(0);
        }
        else
        {
            invoice.setStatus(9);
        }
        if(cProduct.getQuantity_left()-1 > -1) {
            cProduct.setQuantity_left(cProduct.getQuantity_left()-1);
            cProduct.update(db);
            invoice.insert(db);
            DebugHelper.print(cProduct);
            //updateQuantityView(mContex,product,(LinearLayout) findViewById(R.id.product_list_holder));
            updatePosView(c, db);
        }
        else
        {
            Toasty.error(getApplicationContext(), TextString.textSelectByVarname(db,"No More Product in Stock").getText(), Toast.LENGTH_SHORT, true).show();
        }
    }
    public static int tempQuantity=0;
    public static boolean tempFlag=false;
    public static void updateQuantityViewR(Context c,PosProduct product,RelativeLayout linearLayout)
    {
        for (int i = 0; i < linearLayout.getChildCount(); i++)
        {
            String name = linearLayout.getChildAt(i).getClass().getName().toUpperCase();
            if(name.equals("android.widget.LinearLayout".toUpperCase()))
            {
                updateQuantityView(c,product,(LinearLayout) linearLayout.getChildAt(i));
            }
            else if(name.equals("ANDROID.WIDGET.RELATIVELAYOUT".toUpperCase()))
            {
                updateQuantityViewR(c,product,(RelativeLayout) linearLayout.getChildAt(i));
            }
            else if(name.contains("APPCOMPATTEXTVIEW".toUpperCase()))
            {
                TextView t = (TextView) linearLayout.getChildAt(i);
                String fName = t.getResources().getResourceName(t.getId()).split("/")[1];
                if(fName.equals("sku1") || fName.equals("sku2"))
                {
                    if(product.getTitle().equals(t.getText().toString().toUpperCase()))
                    {
                        DBInitialization db = new DBInitialization(c,null,null,1);
                        tempFlag = true;
                        tempQuantity= sumQuanitityLeftByTitle(db,product);
                    }
                }
                else if((fName.equals("qnty1") || fName.equals("qnty2")) && tempFlag )
                {
                    SpannableString spannableString = UIComponent.buildBackgroundColorSpan(new SpannableString(String.valueOf(tempQuantity)),
                            String.valueOf(tempQuantity), String.valueOf(tempQuantity),Color.parseColor("#FFF5F19E"));
                    t.setText(spannableString);
                    tempFlag = false;
                }
            }
        }
    }
    public static void updateQuantityView(Context c,PosProduct product,LinearLayout linearLayout)
    {
        for (int i = 0; i < linearLayout.getChildCount(); i++)
        {
            String name = linearLayout.getChildAt(i).getClass().getName().toUpperCase();
            if(name.equals("android.widget.LinearLayout".toUpperCase()))
            {
                updateQuantityView(c,product,(LinearLayout) linearLayout.getChildAt(i));
            }
            else if(name.equals("ANDROID.WIDGET.RELATIVELAYOUT".toUpperCase()))
            {
                updateQuantityViewR(c,product,(RelativeLayout) linearLayout.getChildAt(i));
            }
            else if(name.contains("APPCOMPATTEXTVIEW".toUpperCase()))
            {
                TextView t = (TextView) linearLayout.getChildAt(i);
                String fName = t.getResources().getResourceName(t.getId()).split("/")[1];
                if(fName.equals("sku1") || fName.equals("sku2"))
                {
                    if(product.getTitle().equals(t.getText().toString().toUpperCase()))
                    {
                        DBInitialization db = new DBInitialization(c,null,null,1);
                        tempFlag = true;
                        tempQuantity= sumQuanitityLeftByTitle(db,product);
                    }
                }
                else if((fName.equals("qnty1") || fName.equals("qnty2")) && tempFlag )
                {
                    SpannableString spannableString = UIComponent.buildBackgroundColorSpan(new SpannableString(String.valueOf(tempQuantity)),
                            String.valueOf(tempQuantity), String.valueOf(tempQuantity),Color.parseColor("#FFF5F19E"));
                    t.setText(spannableString);
                    tempFlag = false;
                }
            }
        }
    }
    public static void updateProductList(Context c,PosProduct product)
    {
       // final LinearLayout pos_product_pos_product_product_list = (LinearLayout) ((Activity) c).findViewById(R.id.product_list_holder);
        //updateQuantityView(c,product,pos_product_pos_product_product_list);
    }
    public static void updateProductQuantityLeft(Context c, DBInitialization db,int id)
    {
        ArrayList<PosInvoice> invoices = PosInvoice.select(db,"("+COLUMN_pos_invoice_status+"=0 OR "+COLUMN_pos_invoice_status +"=9) AND "+COLUMN_pos_invoice_invoiceID+"="+id);
        for(PosInvoice invoice : invoices)
        {
            PosProduct cProduct = PosProduct.select(db,db.COLUMN_pos_product_id+"="+invoice.getProductId()).get(0);
            cProduct.setQuantity_left(cProduct.getQuantity_left()+invoice.getQuantity());
            cProduct.update(db);
            updateProductList(c,cProduct);
        }
    }

    public static void updateProductQuantityLeft(Context c, DBInitialization db,ArrayList<PosInvoice> invoices)
    {
        for(PosInvoice invoice : invoices)
        {
            PosProduct cProduct = PosProduct.select(db,db.COLUMN_pos_product_id+"="+invoice.getProductId()).get(0);
            cProduct.setQuantity_left(cProduct.getQuantity_left()+invoice.getQuantity());
            cProduct.update(db);
            updateProductList(c,cProduct);
        }
    }

    //int quantity = 0;
    void addRemoveIteam(final PosProduct product,final DBInitialization db,final int count,final TextView textView,final TextView product_sold_quantity,final TextView available)
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if(TypeConvertion.parseInt(textView.getText().toString())==0 && count < 0)
                    {
                        return;
                    }
                    PosInvoice invoice = new PosInvoice();
                    invoice.setInvoiceID(currentInvoiceID);
                    invoice.setProductId(product.getId());
                    invoice.setProductName(product.getProduct_name());
                    invoice.setUnitPrice(product.getPrice());
                    String con = db.COLUMN_pos_invoice_productId+"="+product.getId()+" AND "+db.COLUMN_pos_invoice_invoiceID+"="+currentInvoiceID+" AND ("+COLUMN_pos_invoice_status+"=0 OR "+COLUMN_pos_invoice_status +"=9)";
                    int quantity = 1;
                    try{
                        quantity = PosInvoice.select(db,con).get(0).getQuantity()+count;
                    }catch (Exception e){}
                    invoice.setQuantity(quantity);
                    if(previous_id.equals("0"))
                    {
                        invoice.setStatus(0);
                    }
                    else
                    {
                        invoice.setStatus(9);
                    }
                    final int q = quantity;
                    if(product.getQuantity_left()-count >= 0) {
                        product.setQuantity_left(product.getQuantity_left()-count);
                        product.update(db);
                        invoice.insert(db);
                        //updatePosView(c,db);
                        ((Activity) mContex).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                textView.setText(String.valueOf(TypeConvertion.parseInt(textView.getText().toString())+count));
                                product_sold_quantity.setText(String.valueOf(q));
                                available.setText(String.valueOf(TypeConvertion.parseInt(available.getText().toString())-count));
                                updateProductList(mContex,product);
                                updatePosView(mContex, db);
                            }
                        });
                    }
                    else
                    {
                        Toasty.error(mContex, TextString.textSelectByVarname(db,"No More Product in Stock").getText(), Toast.LENGTH_SHORT, true).show();
                    }
                }
                catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }).start();
    }

    ProductCategory currentSubCategory = new ProductCategory();
    PopupWindow popupWindow;
    public void showSubCategory(ProductCategory productCategory)
    {
        currentSubCategory = productCategory;
        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        final View popupView = layoutInflater.inflate(R.layout.pos_product_sub_category_list, null);
        popupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        final ImageView close_tab = (ImageView) popupView.findViewById(R.id.close_tab);
        close_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                popupWindow.dismiss();
            }
        });

        ListView lv = (ListView) popupView.findViewById(R.id.subcategory_list);


        FontSettings.setTextFont ((TextView) popupView.findViewById(R.id.category),context,productCategory.getCategory_name());

        ScrollListView.loadListView(context, lv, R.layout.pos_product_sub_category_list_item, productCategory.getSub_category(), "productSubCategoryList", 0, productCategory.getSub_category().size(), true);

        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.showAtLocation(headtext, Gravity.CENTER, 0, 0);
    }

    public void productSubCategoryList(final ViewData data)
    {
        final String subCategory = (String) data.object;
        Button btn = data.view.findViewById(R.id.subcategory_list_btn);
        btn.setText(subCategory);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(data.position==0)
                {
                    showProducts(mContex,findProductByCategory(getAllCategory(currentSubCategory.getProducts(),currentSubCategory.getCategory_name()),currentSubCategory.getCategory_name()));
                }
                else
                {
                    showProducts(mContex,findProductByCategory(getAllCategory(currentSubCategory.getProducts(),currentSubCategory.getCategory_name(),subCategory),currentSubCategory.getCategory_name()));
                }
                popupWindow.dismiss();
            }
        });
    }
    int currentUploadid=0;
    Intent uploadNextIntent;
    public void uploadInvoiceData(int id,Intent i)
    {
        progressDoalog = new ProgressDialog(mContex);
        uploadNextIntent = i;
        try {
            progressDoalog.setMessage("Product Creating....");
            progressDoalog.setTitle("Please Wait");
            progressDoalog.show();

        } catch (Exception e) {

        }
        currentUploadid=id;
        final User cSystemInfo = new User();
        cSystemInfo.select(db,"1=1");
        final UploadData uploadData = new UploadData();
        try {
            uploadData.data = PosInvoiceHeadCombian.getPosInvoiceCombian(PosInvoiceHead.select(db,db.COLUMN_pos_invoice_head_id+"="+id),new ArrayList<PosInvoiceHeadWeb>());
        }catch (Exception e){
            Toasty.error(context, "Something went wrong.Please try agian", Toast.LENGTH_SHORT, true).show();
            Intent intent = new Intent(context, AssignmentList.class);
            context.startActivity(intent);
        }
        add_customer.post(new Runnable() {
            public void run() {
                String username = cSystemInfo.getUser_name();
                String password = cSystemInfo.getPassword();
                String company = cSystemInfo.getCompany_id();
                String location = cSystemInfo.getSelected_location();
                String pos = cSystemInfo.getSelected_pos();

                uploadData.url=url_pos_invoice_upload + "user_name="+username+"&password="+password+"&company="+company+"&"+"&location="+location+"&pos="+pos+"&";
                uploadData.uploaddata(context,context,"",context,"pos_product_details_upload");

            }
        });
    }

    public void pos_product_details_upload(ArrayList<String> data)
    {
        ArrayList<PosInvoiceHead> posInvoices = PosInvoiceHead.select(db,db.COLUMN_pos_invoice_head_id+"="+currentUploadid);
        for(PosInvoiceHead p : posInvoices)
        {
            p.setIf_synced(1);
            p.update(db);
        }
        UploadData uploadData = new UploadData();
        uploadData.data = PosInvoiceCombian.setPosInvoiceCombian(PosInvoice.select(db,db.COLUMN_pos_invoice_status+"=2 AND "+db.COLUMN_pos_invoice_invoiceID+"="+currentUploadid));
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
        uploadData.url=url_pos_invoice_details_upload + "user_name="+username+"&password="+password+"&company="+company+"&"+"&location="+location+"&pos="+pos+"&";
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
        UploadData uploadData = new UploadData();
        uploadData.data = PosInvoiceHeadCombian.setPosInvoiceHead(PosInvoiceHead.select(db,db.COLUMN_pos_invoice_head_id+"="+currentUploadid));
        final User cSystemInfo = new User();
        cSystemInfo.select(db,"1=1");
        String username = cSystemInfo.getUser_name();
        String password = cSystemInfo.getPassword();
        String company = cSystemInfo.getCompany_id();
        String location = cSystemInfo.getSelected_location();
        String pos = cSystemInfo.getSelected_pos();

        uploadData.url=url_pos_invoice_co_ledger_upload + "user_name="+username+"&password="+password+"&company="+company+"&"+"&location="+location+"&pos="+pos+"&";
        uploadData.uploaddata(this,this,"",this,"uploadComplete");

    }
    public void uploadComplete(ArrayList<String> data)
    {
        progressDoalog.dismiss();
        startActivity(uploadNextIntent);
        Toasty.success(getApplicationContext(),TextString.textSelectByVarname(db,"pos_sale_payment_popbox_invoice_complete").getText(), Toast.LENGTH_LONG).show();
        finish();
    }
}
