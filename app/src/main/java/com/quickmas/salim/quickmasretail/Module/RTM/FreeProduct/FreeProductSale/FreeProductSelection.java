package com.quickmas.salim.quickmasretail.Module.RTM.FreeProduct.FreeProductSale;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.quickmas.salim.quickmasretail.Database.DBInitialization;
import com.quickmas.salim.quickmasretail.Model.RTM.Outlet;
import com.quickmas.salim.quickmasretail.Model.RTM.Product;
import com.quickmas.salim.quickmasretail.Model.System.DashboardMenu;
import com.quickmas.salim.quickmasretail.Model.System.DashboardSubMenu;
import com.quickmas.salim.quickmasretail.Model.System.User;
import com.quickmas.salim.quickmasretail.R;
import com.quickmas.salim.quickmasretail.Structure.Company;

import java.util.ArrayList;

import static com.quickmas.salim.quickmasretail.Model.System.DashboardMenu.selectByVarname;
import static com.quickmas.salim.quickmasretail.Model.System.TextString.textSelectByVarname;
import static com.quickmas.salim.quickmasretail.Structure.Company.getCompanyStruct;
import static com.quickmas.salim.quickmasretail.Utility.FontSettings.getFont;
import static com.quickmas.salim.quickmasretail.Utility.UIComponent.setLinearLayoutBackground;

public class FreeProductSelection extends AppCompatActivity {
    String invoiceOutlet="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_free_product_selection);

        final DBInitialization db = new DBInitialization(this,null,null,1);

        DashboardMenu cMenu =  selectByVarname(db,"dashboard_sell_rtm");
        LinearLayout headimg = (LinearLayout) findViewById(R.id.image1);
        headimg = setLinearLayoutBackground(headimg, cMenu.getImage(), this);
        TextView headtext = (TextView) findViewById(R.id.text1);
        headtext.setTypeface(getFont(this));
        headtext.setText(DashboardSubMenu.getMenuText(db, "rtm_activity_2"));
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
                Adaptar_outlet_select_company adapter = new Adaptar_outlet_select_company(getApplicationContext(), companies, invoiceOutlet);
                stock_list.setAdapter(adapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
    }
}
