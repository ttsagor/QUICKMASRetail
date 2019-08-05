package com.quickmas.salim.quickmasretail.Module.Dashboard;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.location.SettingInjectorService;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.quickmas.salim.quickmasretail.Model.RTM.CRMInvoiceHead;
import com.quickmas.salim.quickmasretail.Model.RTM.FreeProductInvoiceHead;
import com.quickmas.salim.quickmasretail.Model.RTM.MI.MIInvoiceHead;
import com.quickmas.salim.quickmasretail.Model.RTM.POSM.POSMInvoiceHead;
import com.quickmas.salim.quickmasretail.Model.RTM.Product;
import com.quickmas.salim.quickmasretail.Model.System.DashboardSubMenu;
import com.quickmas.salim.quickmasretail.Model.System.TextString;
import com.quickmas.salim.quickmasretail.Module.AddProductRetail.AddProduct.AddProduct;
import com.quickmas.salim.quickmasretail.Module.AddProductRetail.PaymentPaid.PaymentPaid;
import com.quickmas.salim.quickmasretail.Module.AddProductRetail.ProductList.ProductList;
import com.quickmas.salim.quickmasretail.Module.AddProductRetail.PurchaseProduct.PurchaseProduct;
import com.quickmas.salim.quickmasretail.Module.CashStatment.CashStatmentShow;
import com.quickmas.salim.quickmasretail.Module.CashTransfer.CashTransfer;
import com.quickmas.salim.quickmasretail.Module.Dashboard.FooterActivity.Settings.Settings;
import com.quickmas.salim.quickmasretail.Module.DataSync.DataSync;
import com.quickmas.salim.quickmasretail.Database.DBInitialization;
import com.quickmas.salim.quickmasretail.Module.Loading.Loading;
import com.quickmas.salim.quickmasretail.Model.System.DashboardMenu;
import com.quickmas.salim.quickmasretail.Model.RTM.Invoice;
import com.quickmas.salim.quickmasretail.Model.System.User;
import com.quickmas.salim.quickmasretail.Module.POS.POSInvoiceList.POSInvoiceList;
import com.quickmas.salim.quickmasretail.Module.POS.PosSell.SelectProduct.PosSelectProduct;
import com.quickmas.salim.quickmasretail.Module.POS.PosSummary.PosSummary;
import com.quickmas.salim.quickmasretail.Module.POS.WarehouseStock.WarehouseStock;
import com.quickmas.salim.quickmasretail.Module.PaymentReceived.CashPayment;
import com.quickmas.salim.quickmasretail.Module.RTM.Assignment.AssignmentList;
import com.quickmas.salim.quickmasretail.Module.RTM.CRM.CRMList.CRMList;
import com.quickmas.salim.quickmasretail.Module.RTM.CRM.CRMSale.CRMCurrentInvoice;
import com.quickmas.salim.quickmasretail.Module.RTM.CRM.CRMSale.CRMSale;
import com.quickmas.salim.quickmasretail.Module.RTM.FreeProduct.FreeProductSale.FreeProductCurrentInvoice;
import com.quickmas.salim.quickmasretail.Module.RTM.FreeProduct.FreeProductSale.FreeProductSelection;
import com.quickmas.salim.quickmasretail.Module.RTM.FreeProduct.FreeProductSaleList.FreeProductSaleList;
import com.quickmas.salim.quickmasretail.Module.RTM.MI.MIList.MiList;
import com.quickmas.salim.quickmasretail.Module.RTM.MI.MISale.MiCurrentInvoice;
import com.quickmas.salim.quickmasretail.Module.RTM.MI.MISale.MiSale;
import com.quickmas.salim.quickmasretail.Module.RTM.POSM.POSMList.POSMList;
import com.quickmas.salim.quickmasretail.Module.RTM.POSM.POSMSale.PosmCurrentInvoice;
import com.quickmas.salim.quickmasretail.Module.RTM.POSM.POSMSale.PosmProductSelect;
import com.quickmas.salim.quickmasretail.Module.RegularSale.RegularSaleInvoice.RegularInvoiceList;
import com.quickmas.salim.quickmasretail.Module.StockTransfer.NewStockTransfer.NewStockTransfer;
import com.quickmas.salim.quickmasretail.R;
import com.quickmas.salim.quickmasretail.Module.RTM.RTM.RTMInvoiceList.InvoiceList;
import com.quickmas.salim.quickmasretail.Module.RTM.RTM.RTMSale.NewSale.SaleOutletSelection;
import com.quickmas.salim.quickmasretail.Module.RTM.RTM.RTMSale.NewSale.Sell_Invoice_Company;
import com.quickmas.salim.quickmasretail.Module.RTM.Stock.StockList;
import com.quickmas.salim.quickmasretail.Service.DataDownload;
import com.quickmas.salim.quickmasretail.Structure.MenuNameLink;
import com.quickmas.salim.quickmasretail.Module.Summary.Product.Summary;
import com.quickmas.salim.quickmasretail.Utility.CallBack;
import com.quickmas.salim.quickmasretail.Utility.DebugHelper;
import com.quickmas.salim.quickmasretail.Utility.FileManagerSetting;
import com.quickmas.salim.quickmasretail.Utility.FontSettings;
import com.quickmas.salim.quickmasretail.Utility.NetworkConfiguration;
import com.quickmas.salim.quickmasretail.Utility.UI.ScrollListView;
import com.quickmas.salim.quickmasretail.Utility.UI.ViewData;
import com.quickmas.salim.quickmasretail.Utility.UIComponent;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;
import es.dmoral.toasty.Toasty;

import static com.quickmas.salim.quickmasretail.MainActivity.global_user;
import static com.quickmas.salim.quickmasretail.Model.System.TextString.populateVarMap;
import static com.quickmas.salim.quickmasretail.Service.ApiSettings.url_set_language;
import static com.quickmas.salim.quickmasretail.Utility.FileManagerSetting.getImageFromFile;
import static com.quickmas.salim.quickmasretail.Utility.FontSettings.appFont;
import static com.quickmas.salim.quickmasretail.Utility.FontSettings.getFont;
import static com.quickmas.salim.quickmasretail.Utility.FontSettings.setTextFont;
import static com.quickmas.salim.quickmasretail.Utility.UIComponent.hideSoftKeyboard;
import static com.quickmas.salim.quickmasretail.Utility.UIComponent.setGlobalProgessBarColor;
import static com.quickmas.salim.quickmasretail.Utility.UIComponent.setLinearLayoutBackground;

public class MainActivity extends AppCompatActivity {
    DBInitialization db;
    Context mContext;

    @Override
    protected void onResume() {
        super.onResume();
        hideSoftKeyboard(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        db = new DBInitialization(this,null,null,1);
        setContentView(R.layout.activity_dashboard);

        setGlobalProgessBarColor(this,db);

        //final LinearLayout dashboard_menu_holder = (LinearLayout) findViewById(R.id.dashboard_menu_holder);

        final ImageView app_logo = (ImageView) findViewById(R.id.homepage_app_company_logo);
        final LinearLayout app_logo_holder = (LinearLayout) findViewById(R.id.app_logo_holder);
        mContext = this;
        hideSoftKeyboard(this);
        TextString.populateVarMap(db);
        FontSettings.setFont(mContext);
        UIComponent.setImageView(this,app_logo,global_user.getApp_icon());
       /* if(getImageFromFile(FileManagerSetting.app_logo)!=null)
        {
            app_logo.setImageBitmap(getImageFromFile(global_user.getApp_icon(),this));
        }
        else
        {
            User cSystemInfo = new User();
            cSystemInfo.select(db,"1=1");
            app_logo.setVisibility(View.GONE);
            TextView valueTV = new TextView(this);
            valueTV.setText(cSystemInfo.getCompany_name());
            valueTV.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
            valueTV.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            app_logo_holder.addView(valueTV);
        }*/
        UIComponent.hideSoftKeyboard((Activity) this);
        DashboardMenu dm = new DashboardMenu();
        ArrayList<DashboardMenu> allMenu = dm.select(db,db.COLUMN_dash_image_category+"='menu'");

        int size = (int) Math.ceil(allMenu.size()/2);

        float heightDp = ((getResources().getDisplayMetrics().heightPixels * 0.8f)/8);
        ListView main_menu_list = (ListView) findViewById(R.id.main_menu_list);
        ScrollListView.loadListView(this,main_menu_list,R.layout.dashboard_menu,allMenu,"loadMenu",0,allMenu.size(),true);


        allMenu = dm.select(db,db.COLUMN_dash_image_category+"='footer'");

        LinearLayout footer_holder = (LinearLayout) findViewById(R.id.footer_holder);
        for(int i=0;i < allMenu.size();i++)
        {
            final DashboardMenu menu1 = allMenu.get(i);

            LayoutInflater inflater = LayoutInflater.from(this);
            ViewGroup menuView = (ViewGroup)inflater.inflate(R.layout.dashboard_footer_iteam,footer_holder,false);

            LinearLayout layout_holder  = (LinearLayout) menuView.findViewById(R.id.layout_holder);
            ImageView image  = (ImageView) menuView.findViewById(R.id.image);
            TextView text  = (TextView) menuView.findViewById(R.id.text);
            text.setTypeface(getFont(this));
            text.setText(String.valueOf(menu1.getText()));
            try {
                image.setImageBitmap(getImageFromFile(menu1.getImage(), this));
            }
            catch (Exception e){}
            final String link = (new MenuNameLink(allMenu.get(i).getText(),allMenu.get(i).getVarname()).getLink());
            final DashboardMenu dmT  = allMenu.get(i);
            layout_holder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CallBack callBack = new CallBack("Module.Dashboard.MainActivity", link);
                    callBack.call(String.valueOf(dmT.getId()), mContext);
                }
            });
            footer_holder.addView(menuView);
        }

        allMenu = dm.select(db,db.COLUMN_dash_image_category+"='logo'");

        if(allMenu.size()>0)
        {
            ImageView homepage_app_logo = (ImageView) findViewById(R.id.homepage_app_logo);
            homepage_app_logo.setImageBitmap(getImageFromFile(allMenu.get(0).getImage(),this));
        }

        allMenu = dm.select(db,db.COLUMN_dash_image_category+"='cart'");
        if(allMenu.size()>0)
        {
            ImageView header_cart_logo = (ImageView) findViewById(R.id.header_cart_logo);
            header_cart_logo.setImageBitmap(getImageFromFile(allMenu.get(0).getImage(),this));
        }
    }

    public void loadMenu(ViewData data)
    {
        final DashboardMenu menu1 = (DashboardMenu)data.object;

        LinearLayout layout1  = (LinearLayout) data.view.findViewById(R.id.menu_img);
        TextView text1  = (TextView) data.view.findViewById(R.id.menu_txt);
        text1.setTypeface(appFont);
        text1.setText(menu1.getText());
        layout1 = setLinearLayoutBackground(layout1, menu1.getImage(), this);

        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams)layout1.getLayoutParams();
        lp.height = (int)((mContext.getResources().getDisplayMetrics().heightPixels * 0.8f)/8);;

        layout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ArrayList<DashboardSubMenu> dashboardSubMenus = DashboardSubMenu.select(db,db.COLUMN_dash_sub_varname+"='"+ menu1.getVarname()+"'");
                if(dashboardSubMenus.size()>0)
                {
                    LayoutInflater layoutInflater = (LayoutInflater) mContext
                            .getSystemService(LAYOUT_INFLATER_SERVICE);
                    final View popupView = layoutInflater.inflate(R.layout.pop_up_multi_option, null);
                    final PopupWindow popupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    popupWindow.setOutsideTouchable(true);
                    popupWindow.setFocusable(true);
                    popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    final LinearLayout app_logo_holder = (LinearLayout) ((Activity) mContext).findViewById(R.id.app_logo_holder);
                    popupWindow.showAtLocation(app_logo_holder, Gravity.CENTER, 0, 0);

                    LinearLayout ll = (LinearLayout) popupView.findViewById(R.id.layout_holder);


                   LinearLayout menu_name_holder = (LinearLayout) popupView.findViewById(R.id.menu_name_holder);

                   menu_name_holder = setLinearLayoutBackground((LinearLayout) popupView.findViewById(R.id.menu_name_holder), menu1.getImage(), mContext);


                    TextView main_menu = FontSettings.setTextFont((TextView) popupView.findViewById(R.id.main_menu),mContext,menu1.getText());
                    final ArrayList<MenuNameLink> menuList = new ArrayList<MenuNameLink>();

                    for(DashboardSubMenu dm : dashboardSubMenus)
                    {
                        menuList.add(new MenuNameLink(dm.getText(),dm.getNew_var_name()));
                    }
                    for (int i = 0; i < menuList.size(); i++) {

                        Button myButton = new Button(mContext);
                        myButton.setText(menuList.get(i).getName());
                        myButton.setTextColor(Color.parseColor("#000000"));
                        myButton.setBackgroundColor(Color.parseColor("#dbe8ff"));
                        myButton.setTextAppearance(mContext,android.R.style.TextAppearance_Large);
                        myButton.setTypeface(getFont(mContext));
                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        lp.setMargins(5,5,5,5);
                        ll.addView(myButton, lp);
                        final String link = menuList.get(i).getLink();
                        final int pos = i;
                        myButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v)
                            {
                                if(menu1.getVarname().equals("dashboard_settings"))
                                {
                                    CallBack callBack = new CallBack("Module.Dashboard.MainActivity", "setLangauge");
                                    callBack.call(String.valueOf(dashboardSubMenus.get(pos).getId()), mContext);
                                }
                                else
                                {
                                    CallBack callBack = new CallBack("Module.Dashboard.MainActivity", link);
                                    callBack.call(String.valueOf(dashboardSubMenus.get(pos).getId()), mContext);
                                }
                            }
                        });
                    }
                }
                else
                {
                    CallBack c = new CallBack("Module.Dashboard.MainActivity", menu1.getVarname());
                    c.call(menu1.getVarname(), mContext);
                }

            }
        });
        LinearLayout loading_layout = (LinearLayout) findViewById(R.id.loading_layout);
        loading_layout.setVisibility(View.GONE);
    }
    public void dashboard_attendance(String respose,Context c)
    {
        System.out.println(respose);
    }
    public void dashboard_settings_0(String respose,Context c)
    {
        setLangauge(respose,c);
    }
    public void dashboard_settings_1(String respose,Context c)
    {
        setLangauge(respose,c);
    }


    public void dashboard_footer_settings(String respose,Context c)
    {
        Intent intent = new Intent(c, Settings.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        c.startActivity(intent);
    }
    public void setLangauge(String respose,Context context)
    {
        if(NetworkConfiguration.isInternetOn(context)) {
            System.out.println(respose);
            DBInitialization db1 = new DBInitialization(context,null,null,1);
            CallBack c = new CallBack("Module.Dashboard.MainActivity", "updateLanguage");
            User cSystemInfo = new User();
            cSystemInfo.select(db1,"1=1");
            DataDownload.downloadData(context,url_set_language,"language="+respose,cSystemInfo,c);
        }else{

            Toasty.error(context, "Please Connect to Internet", Toast.LENGTH_SHORT, true).show();
        }
    }
    public void updateLanguage(String response,Context c)
    {

        if(NetworkConfiguration.isInternetOn(c)) {
            Intent intent = new Intent(c, Loading.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.putExtra("firstLogin","true");
            c.startActivity(intent);
        }else{
            Toasty.error(c, "Please Connect to Internet", Toast.LENGTH_SHORT, true).show();
        }
    }

    public void data_sync_2(String respose,Context c)
    {
        if(NetworkConfiguration.checkIfInternetOn(c)) {
            Intent intent = new Intent(c, Loading.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.putExtra("firstLogin","true");
            c.startActivity(intent);
        }
    }
    public void data_sync_1(String respose,Context c)
    {
        if(NetworkConfiguration.checkIfInternetOn(c)) {
            Intent intent = new Intent(c, Loading.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.putExtra("pos_data_sync","true");
            c.startActivity(intent);
        }
    }



    public void rtm_activity_1(String respose,final Context c)
    {
        DBInitialization db = new DBInitialization(c,null,null,1);
        if(Invoice.getPendingInvoices(db).size()>0)
        {
            Intent intent = new Intent(c, Sell_Invoice_Company.class);
            c.startActivity(intent);
        }
        else
        {
            Intent intent = new Intent(c, SaleOutletSelection.class);
            c.startActivity(intent);
        }
    }
    public void rtm_activity_2(String respose,final Context c)
    {
        DBInitialization db = new DBInitialization(c,null,null,1);
        if(FreeProductInvoiceHead.getPendingInvoices(db).size()>0)
        {
            Intent intent = new Intent(c, FreeProductCurrentInvoice.class);
            c.startActivity(intent);
        }
        else
        {
            Intent intent = new Intent(c, FreeProductSelection.class);
            c.startActivity(intent);
        }
    }
    public void rtm_activity_3(String respose,final Context c)
    {
        DBInitialization db = new DBInitialization(c,null,null,1);
        if(CRMInvoiceHead.getPendingInvoices(db).size()>0)
        {
            Intent intent = new Intent(c, CRMCurrentInvoice.class);
            c.startActivity(intent);
        }
        else
        {
            Intent intent = new Intent(c, CRMSale.class);
            c.startActivity(intent);
        }
    }
    public void rtm_activity_4(String respose,final Context c)
    {
        DBInitialization db = new DBInitialization(c,null,null,1);
        if(POSMInvoiceHead.getPendingInvoices(db).size()>0)
        {
            Intent intent = new Intent(c, PosmCurrentInvoice.class);
            c.startActivity(intent);
        }
        else
        {
            Intent intent = new Intent(c, PosmProductSelect.class);
            c.startActivity(intent);
        }
    }
    public void rtm_activity_5(String respose,final Context c)
    {
        DBInitialization db = new DBInitialization(c,null,null,1);
        if(MIInvoiceHead.getPendingInvoices(db).size()>0)
        {
            Intent intent = new Intent(c, MiCurrentInvoice.class);
            c.startActivity(intent);
        }
        else
        {
            Intent intent = new Intent(c, MiSale.class);
            c.startActivity(intent);
        }
    }
    public void dashboard_stock_assignment(String respose,final Context c)
    {
        Intent intent = new Intent(c, StockList.class);
        c.startActivity(intent);
    }
    public void add_produce_retail_1(String respose,final Context c)
    {
        Intent intent = new Intent(c, AddProduct.class);
        c.startActivity(intent);
    }
    public void add_produce_retail_3(String respose,final Context c)
    {
        Intent intent = new Intent(c, PurchaseProduct.class);
        c.startActivity(intent);
    }
    public void accounting_list_3(String respose,final Context c)
    {
        Intent intent = new Intent(c, PaymentPaid.class);
        c.startActivity(intent);
    }
    public void add_produce_retail_2(String respose,final Context c)
    {
        Intent intent = new Intent(c, ProductList.class);
        c.startActivity(intent);
    }

    public void rtm_activity_list_1(String respose,final Context c)
    {
        Intent intent = new Intent(c, InvoiceList.class);
        c.startActivity(intent);
    }
    public void rtm_activity_list_2(String respose,final Context c)
    {
        Intent intent = new Intent(c, FreeProductSaleList.class);
        c.startActivity(intent);
    }
    public void rtm_activity_list_3(String respose,final Context c)
    {
        Intent intent = new Intent(c, CRMList.class);
        c.startActivity(intent);
    }
    public void rtm_activity_list_4(String respose,final Context c)
    {
        Intent intent = new Intent(c, POSMList.class);
        c.startActivity(intent);
    }
    public void rtm_activity_list_5(String respose,final Context c)
    {
        Intent intent = new Intent(c, MiList.class);
        c.startActivity(intent);
    }
    public void rtm_activity_list_6(String respose,final Context c)
    {
        DBInitialization db1 = new DBInitialization(c,null,null,1);
        ArrayList<Product> pendingProductList = Product.select(db1,"1=1");
        if(pendingProductList.size()>0) {
            Intent intent = new Intent(c, AssignmentList.class);
            c.startActivity(intent);
        }
        else
        {
            if(NetworkConfiguration.checkIfInternetOn(c)) {
                Intent intent = new Intent(c, Loading.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.putExtra("dataDownloadAssignmentList","true");
                c.startActivity(intent);
            }
        }
    }
    public void dashboard_summary_retail(String respose,final Context c) {
        Intent intent = new Intent(c, PosSummary.class);
        c.startActivity(intent);
    }
    public void dashboard_summary_rtm(String respose,final Context c)
    {
        Intent intent = new Intent(c, Summary.class);
        c.startActivity(intent);
    }
    public void dashboard_sell_pos(String respose,final Context c)
    {
        Intent intent = new Intent(c, PosSelectProduct.class);
        c.startActivity(intent);
    }

    public void accounting_list_6(String respose,final Context c)
    {
        Intent intent = new Intent(c, CashStatmentShow.class);
        c.startActivity(intent);
    }
    public void accounting_list_4(String respose,final Context c)
    {
        Intent intent = new Intent(c, CashTransfer.class);
        c.startActivity(intent);
    }

    public void sales_list_1(String respose,final Context c)
    {
        Intent intent = new Intent(c, POSInvoiceList.class);
        c.startActivity(intent);
    }
    public void sales_list_2(String respose,final Context c)
    {
        Intent intent = new Intent(c, com.quickmas.salim.quickmasretail.Module.Exchange.InvoiceList.class);
        c.startActivity(intent);
    }

    public void accounting_list_2(String respose,final Context c)
    {
        Intent intent = new Intent(c, CashPayment.class);
        c.startActivity(intent);
    }

    public void warehouse_stock_1(String respose,final Context c)
    {
        Intent intent = new Intent(c, WarehouseStock.class);
        c.startActivity(intent);
    }
    public void warehouse_stock_2(String respose,final Context c)
    {
        Intent intent = new Intent(c, NewStockTransfer.class);
        c.startActivity(intent);
    }
    public void accounting_3(String respose,final Context c)
    {
        Intent intent = new Intent(c, RegularInvoiceList.class);
        c.startActivity(intent);
    }

    public void dashboard_operating_mode_1(String respose,final Context c)
    {
        DBInitialization db = new DBInitialization(c,null,null,1);
        User cUser = new User();
        cUser.select(db,"1=1");
        cUser.setActive_online(1);
        cUser.update(db);
        global_user = cUser;
        Toasty.success(c,"Online Mode Activated", Toast.LENGTH_LONG).show();
    }
    public void dashboard_operating_mode_2(String respose,final Context c)
    {
        DBInitialization db = new DBInitialization(c,null,null,1);
        User cUser = new User();
        cUser.select(db,"1=1");
        cUser.setActive_online(0);
        cUser.update(db);
        global_user = cUser;
        Toasty.success(c,"Offline Mode Activated", Toast.LENGTH_LONG).show();
    }
}
