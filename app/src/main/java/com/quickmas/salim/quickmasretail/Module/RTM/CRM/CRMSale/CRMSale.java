package com.quickmas.salim.quickmasretail.Module.RTM.CRM.CRMSale;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import com.quickmas.salim.quickmasretail.Model.RTM.CRMInvoice;
import com.quickmas.salim.quickmasretail.Model.RTM.CRMInvoiceHead;
import com.quickmas.salim.quickmasretail.Model.RTM.Outlet;
import com.quickmas.salim.quickmasretail.Model.RTM.Product;
import com.quickmas.salim.quickmasretail.Model.System.DashboardMenu;
import com.quickmas.salim.quickmasretail.Model.System.DashboardSubMenu;
import com.quickmas.salim.quickmasretail.Model.System.User;
import com.quickmas.salim.quickmasretail.R;
import com.quickmas.salim.quickmasretail.Structure.Company;
import com.quickmas.salim.quickmasretail.Utility.DateTimeCalculation;
import com.quickmas.salim.quickmasretail.Utility.FontSettings;
import com.quickmas.salim.quickmasretail.Utility.UI.ScrollListView;
import com.quickmas.salim.quickmasretail.Utility.UI.ViewData;
import com.rey.material.widget.CheckBox;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

import static com.quickmas.salim.quickmasretail.Model.System.DashboardMenu.selectByVarname;
import static com.quickmas.salim.quickmasretail.Model.System.TextString.textSelectByVarname;
import static com.quickmas.salim.quickmasretail.Structure.Company.getCompanyStruct;
import static com.quickmas.salim.quickmasretail.Utility.FileManagerSetting.getImageFromFile;
import static com.quickmas.salim.quickmasretail.Utility.FontSettings.getFont;
import static com.quickmas.salim.quickmasretail.Utility.UIComponent.buildBackgroundColorSpan;
import static com.quickmas.salim.quickmasretail.Utility.UIComponent.setLinearLayoutBackground;

public class CRMSale extends AppCompatActivity {
    String invoiceOutlet="";
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_crmsale);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_free_product_selection);

        final DBInitialization db = new DBInitialization(this,null,null,1);
        context = this;
        DashboardMenu cMenu =  selectByVarname(db,"dashboard_sell_rtm");
        LinearLayout headimg = (LinearLayout) findViewById(R.id.image1);
        headimg = setLinearLayoutBackground(headimg, cMenu.getImage(), this);
        TextView headtext = (TextView) findViewById(R.id.text1);
        headtext.setTypeface(getFont(this));
        headtext.setText(DashboardSubMenu.getMenuText(db, "rtm_activity_3"));
        Spinner sell_dashboardSpinner = (Spinner) findViewById(R.id.sell_dashboardSpinner);
        final ListView stock_list = (ListView) findViewById(R.id.stock_list);
        final ArrayList<Outlet> outlets = Outlet.select(db, "1=1");

        Intent iin= getIntent();
        Bundle b = iin.getExtras();

        int intSelectedIndex=-1;
        int selectedIndex=-1;
        if(b!=null)
        {
            intSelectedIndex = Integer.parseInt((String) b.get("invoiceOutlet"));
        }

        ArrayList<String> categories = new ArrayList<String>();
        int count=0;
        categories.add(textSelectByVarname(db,"sellDashboard_selectOutlet").getText());
        for(Outlet o : outlets)
        {
            if(o.getId()==intSelectedIndex)
            {
                selectedIndex = count;
            }
            categories.add(o.getOutlet_id());
            count++;
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sell_dashboardSpinner.setAdapter(dataAdapter);
        if(selectedIndex>-1)
        {
            sell_dashboardSpinner.setSelection(selectedIndex+1);
            sell_dashboardSpinner.setEnabled(false);
        }
        sell_dashboardSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                if(position>0){
                    position--;
                }
                else
                {
                    return;
                }
                invoiceOutlet = String.valueOf(outlets.get(position).getId());
                String route = String.valueOf(outlets.get(position).getRoute());
                String section = String.valueOf(outlets.get(position).getSection());
                User cSystemInfo = new User();
                cSystemInfo.select(db,"1=1");

                ArrayList<Product> products = Product.select(db,db.COLUMN_product_route+"='"+route+"' AND "+db.COLUMN_product_section+" LIKE '%,"+section+",%' AND "+db.COLUMN_product_order_permission+"='"+cSystemInfo.getUser_name()+"'");

                ArrayList<Product> tempProducts = new ArrayList<Product>();
                for(Product cProuct : products)
                {
                    if(outlets.get(position).getOutlet_for().toUpperCase().contains(cProuct.getCompany().toUpperCase()))
                    {
                        tempProducts.add(0,cProuct);
                    }
                    else
                    {
                        tempProducts.add(cProuct);
                    }
                }
                ArrayList<Company> companies =  getCompanyStruct(tempProducts);
                ScrollListView.loadListView(context, stock_list, R.layout.sell_dashboard_company, companies, "showList", 0, companies.size(), true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
    }


    public void showList(ViewData data)
    {

        final ArrayList<Product> products = ((Company) data.object).getCrm();

        if(((Company) data.object).getGifts().size()>0) {

            final TextView selldashboard_companylist_company_name  = (TextView) data.view.findViewById(R.id.selldashboard_companylist_company_name);
            final LinearLayout product_list_data  = (LinearLayout) data.view.findViewById(R.id.product_list_data);

            final String company = ((Company) data.object).getCompany_name();
            selldashboard_companylist_company_name.setText(company);
            selldashboard_companylist_company_name.setVisibility(View.VISIBLE);
            product_list_data.removeAllViews();

            selldashboard_companylist_company_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (product_list_data.getVisibility() == View.VISIBLE) {
                        product_list_data.setVisibility(View.GONE);
                    } else {
                        product_list_data.setVisibility(View.VISIBLE);
                    }

                }
            });

            if (data.position == 0) {
                product_list_data.setVisibility(View.VISIBLE);
            }

            int lim = products.size() / 3;
            double div = products.size() % 3;
            if (div > 0) {
                lim++;
            }
            for (int i = 0; i < lim; i++) {
                LayoutInflater inflater = LayoutInflater.from(context);
                ViewGroup footer = (ViewGroup) inflater.inflate(R.layout.sell_dashboard_company_product_list, product_list_data, false);


                final int cproduct1 = i * 3;
                final int cproduct2 = i * 3 + 1;
                final int cproduct3 = i * 3 + 2;


                if (products.size() > cproduct1) {
                    String name = products.get(cproduct1).getSku();
                    String quantity = String.valueOf(products.get(cproduct1).getSku_qty());

                    TextView quantity1 = (TextView) footer.findViewById(R.id.quantity1);
                    final TextView sku1 = (TextView) footer.findViewById(R.id.sku1);
                    ImageView myImageView1 = (ImageView) footer.findViewById(R.id.myImageView1);
                    sku1.setText(name);

                    int color = Color.parseColor("#FFF5F19E");
                    SpannableString spannableString = new SpannableString(quantity);
                    spannableString = buildBackgroundColorSpan(spannableString, quantity, quantity, color);
                    quantity1.setText(spannableString);

                    myImageView1.setImageBitmap(getImageFromFile(products.get(cproduct1).getPhoto(), context));
                    LinearLayout product1 = (LinearLayout) footer.findViewById(R.id.product1);
                    product1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showProductDetails(products.get(cproduct1),sku1);
                            //Intent i = new Intent(context, Sell_Quantity_Select.class);
                            //i.putExtra("id", id);
                            ///i.putExtra("invoiceOutlet", invoiceOutlet);
                            //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            //context.startActivity(i);
                        }
                    });

                } else {
                    LinearLayout product1 = (LinearLayout) footer.findViewById(R.id.product1);
                    product1.setVisibility(View.GONE);
                }


                if (products.size() > cproduct2) {
                    String name = products.get(cproduct2).getSku();
                    String quantity = String.valueOf(products.get(cproduct2).getSku_qty());

                    TextView quantity2 = (TextView) footer.findViewById(R.id.quantity2);
                    final TextView sku2 = (TextView) footer.findViewById(R.id.sku2);
                    ImageView myImageView2 = (ImageView) footer.findViewById(R.id.myImageView2);
                    sku2.setText(name);

                    int color = Color.parseColor("#FFF5F19E");
                    SpannableString spannableString = new SpannableString(quantity);
                    spannableString = buildBackgroundColorSpan(spannableString, quantity, quantity, color);
                    quantity2.setText(spannableString);

                    myImageView2.setImageBitmap(getImageFromFile(products.get(cproduct2).getPhoto(), context));

                    LinearLayout product2 = (LinearLayout) footer.findViewById(R.id.product2);
                    product2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showProductDetails(products.get(cproduct2),sku2);
                            String id = String.valueOf(products.get(cproduct2).getId());
                            //Intent i = new Intent(context, Sell_Quantity_Select.class);
                            //i.putExtra("id", id);
                            //i.putExtra("invoiceOutlet", invoiceOutlet);
                            //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            //context.startActivity(i);
                        }
                    });

                } else {
                    LinearLayout product2 = (LinearLayout) footer.findViewById(R.id.product2);
                    product2.setVisibility(View.GONE);
                }


                if (products.size() > cproduct3) {
                    String name = products.get(cproduct3).getSku();
                    String quantity = String.valueOf(products.get(cproduct3).getSku_qty());

                    TextView quantity3 = (TextView) footer.findViewById(R.id.quantity3);
                    TextView sku3 = (TextView) footer.findViewById(R.id.sku3);
                    ImageView myImageView3 = (ImageView) footer.findViewById(R.id.myImageView3);
                    sku3.setText(name);

                    int color = Color.parseColor("#FFF5F19E");
                    SpannableString spannableString = new SpannableString(quantity);
                    spannableString = buildBackgroundColorSpan(spannableString, quantity, quantity, color);
                    quantity3.setText(spannableString);

                    myImageView3.setImageBitmap(getImageFromFile(products.get(cproduct3).getPhoto(), context));

                    LinearLayout product3 = (LinearLayout) footer.findViewById(R.id.product3);
                    product3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String id = String.valueOf(products.get(cproduct3).getId());
                            //Intent i = new Intent(context, Sell_Quantity_Select.class);
                            //i.putExtra("id", id);
                            //i.putExtra("invoiceOutlet", invoiceOutlet);
                            //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            //context.startActivity(i);
                        }
                    });
                } else {
                    LinearLayout product3 = (LinearLayout) footer.findViewById(R.id.product3);
                    product3.setVisibility(View.GONE);
                }

                product_list_data.addView(footer);
            }
        }
        else {
            TextView selldashboard_companylist_company_name  = (TextView) data.view.findViewById(R.id.selldashboard_companylist_company_name);
            selldashboard_companylist_company_name.setVisibility(View.GONE);
        }
    }

    void showProductDetails(final Product product,final TextView skuView)
    {

        final ArrayList<String> checkedDescription = new ArrayList<>();
        final DBInitialization db = new DBInitialization(context,null,null,1);

        ArrayList<CRMInvoice> cProduct = CRMInvoice.select(db,db.COLUMN_crm_Invoice_product_id+"="+product.getId()+" AND "+db.COLUMN_free_Invoice_status+"=0");



        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        final View popupView = layoutInflater.inflate(R.layout.pop_up_crm_product_outlet_product_select, null);
        final PopupWindow popupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);


        Button add  = FontSettings.setTextFont((Button) popupView.findViewById(R.id.add),context,db,"Add Product");
        TextView sku  = FontSettings.setTextFont((TextView) popupView.findViewById(R.id.sku),context,product.getSku());
        //final TextView sub_sku  = FontSettings.setTextFont((TextView) popupView.findViewById(R.id.sub_sku),context,product.getSub_sku());
        final EditText comment  = (EditText) popupView.findViewById(R.id.comment);
        final LinearLayout description_holder  = (LinearLayout) popupView.findViewById(R.id.description_holder);
        final ImageView close_tab = (ImageView) popupView.findViewById(R.id.close_tab);

        final String[] descriptions = product.getSub_sku().split(";");

        if(cProduct.size()>0)
        {
            comment.setText(cProduct.get(0).getComment());

            String[] dbDescriptions = cProduct.get(0).getDescription().split(";");
            for(String str : dbDescriptions)
            {
                checkedDescription.add(str);
            }
        }

        for(final String des : descriptions)
        {
            final CheckBox ch = new CheckBox(context);
            ch.setText(des);
            ch.setTextColor(Color.BLACK);
            if(checkedDescription.contains(des))
            {
                ch.setChecked(true);
            }
            ch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(ch.isChecked())
                    {
                        if(!checkedDescription.contains(des))
                        {
                            checkedDescription.add(des);
                        }
                    }
                    else
                    {
                        checkedDescription.remove(des);
                    }
                }
            });
            description_holder.addView(ch);
        }

        close_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                User cSystemInfo = new User();
                cSystemInfo.select(db,"1=1");
                if(comment.getText().toString().equals("") || checkedDescription.size()==0)
                {
                    Toasty.error(context, "Please Fill up Comment and Check atleast one Description", Toast.LENGTH_SHORT, true).show();
                    return;
                }
                int invoice_id =1;
                ArrayList<CRMInvoiceHead> pendingId = CRMInvoiceHead.getPendingInvoices(db);
                if(pendingId.size()>0)
                {
                    invoice_id = pendingId.get(0).getId();
                }
                else
                {
                    invoice_id = CRMInvoiceHead.getMaxInvoice(db)+1;
                    CRMInvoiceHead crmProductInvoiceHead = new CRMInvoiceHead();
                    crmProductInvoiceHead.setId(invoice_id);
                    crmProductInvoiceHead.setOutlet(invoiceOutlet);
                    crmProductInvoiceHead.setInvoice_by(cSystemInfo.getUser_name());
                    crmProductInvoiceHead.setInvoice_date(DateTimeCalculation.getCurrentDateTime());
                    crmProductInvoiceHead.setStatus(0);
                    crmProductInvoiceHead.insert(db);
                }
                String res = "";
                for(String str : checkedDescription)
                {
                    res+=str+";";
                }
                CRMInvoice freeProductInvoice = new CRMInvoice();
                freeProductInvoice.setInvoice_id(invoice_id);
                freeProductInvoice.setProduct_id(product.getId());
                freeProductInvoice.setComment(comment.getText().toString());
                freeProductInvoice.setDescription(res);
                freeProductInvoice.setInvoice_by(cSystemInfo.getUser_name());
                freeProductInvoice.setInvoice_date(DateTimeCalculation.getCurrentDateTime());
                freeProductInvoice.setStatus(0);
                freeProductInvoice.insert(db);


                Intent i = new Intent(context, CRMCurrentInvoice.class);
                i.putExtra("id", product.getId());
                i.putExtra("invoiceOutlet", invoiceOutlet);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });

        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.showAtLocation(skuView, Gravity.CENTER, 0, 0);

    }
}
