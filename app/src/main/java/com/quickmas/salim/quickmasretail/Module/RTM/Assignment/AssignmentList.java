package com.quickmas.salim.quickmasretail.Module.RTM.Assignment;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.quickmas.salim.quickmasretail.Database.DBInitialization;
import com.quickmas.salim.quickmasretail.Model.RTM.Product;
import com.quickmas.salim.quickmasretail.Model.System.DashboardMenu;
import com.quickmas.salim.quickmasretail.Model.System.DashboardSubMenu;
import com.quickmas.salim.quickmasretail.R;
import com.quickmas.salim.quickmasretail.Structure.Company;
import com.quickmas.salim.quickmasretail.Utility.DateTimeCalculation;
import com.quickmas.salim.quickmasretail.Utility.FontSettings;
import com.quickmas.salim.quickmasretail.Utility.UI.ScrollListView;
import com.quickmas.salim.quickmasretail.Utility.UI.ViewData;
import com.quickmas.salim.quickmasretail.Utility.UIComponent;

import java.util.ArrayList;

import static com.quickmas.salim.quickmasretail.Model.System.DashboardMenu.selectByVarname;
import static com.quickmas.salim.quickmasretail.Model.System.TextString.textSelectByVarname;
import static com.quickmas.salim.quickmasretail.Structure.Company.getCompanyStructOnlyProductAndGift;
import static com.quickmas.salim.quickmasretail.Utility.FontSettings.getFont;
import static com.quickmas.salim.quickmasretail.Utility.FontSettings.setTextFont;
import static com.quickmas.salim.quickmasretail.Utility.UIComponent.setLinearLayoutBackground;

public class AssignmentList extends AppCompatActivity {
    Context context;
    DBInitialization db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_assignment_list);
        context = this;
        db = new DBInitialization(this,null,null,1);
        DashboardMenu cMenu =  selectByVarname(db,"dashboard_sync");
        LinearLayout headimg = (LinearLayout) findViewById(R.id.image1);
        headimg = setLinearLayoutBackground(headimg, cMenu.getImage(), this);
        TextView headtext = (TextView) findViewById(R.id.text1);
        headtext.setTypeface(getFont(this));
        headtext.setText(DashboardSubMenu.getMenuText(db, "rtm_activity_list_6"));

        ArrayList<Product> pendingProductList = Product.select(db,"1=1");

        ArrayList<Company> allCompany = getCompanyStructOnlyProductAndGift(pendingProductList);
        final ListView acceptData_list = (ListView) findViewById(R.id.acceptData_list);

        ScrollListView.loadListViewUpdateHeightSpecial(this, acceptData_list, R.layout.data_download_company_adaptar, allCompany, "showCompanyProductData", 0,allCompany.size(), true);


        TextView product_title  = (TextView) findViewById(R.id.product_title);
        product_title = setTextFont(product_title, context,db, "adaptar_acceptDataCompanyProductTitle");


        TextView gift_sku  = (TextView) findViewById(R.id.gift_sku);
        TextView gift_qnty  = (TextView) findViewById(R.id.gift_qnty);
        TextView total_gift  = (TextView) findViewById(R.id.total_gift);
        TextView gift_title  = (TextView) findViewById(R.id.gift_title);
        TextView total_gift_count  = (TextView)findViewById(R.id.total_gift_count);

        ListView gift_list  = (ListView) findViewById(R.id.gift_list);
        ListView free_list  = (ListView) findViewById(R.id.free_list);
        ListView crm_list  = (ListView) findViewById(R.id.crm_list);
        ListView posm_list  = (ListView) findViewById(R.id.posm_list);
        ListView mi_list  = (ListView)findViewById(R.id.mi_list);

        TextView free_sku = (TextView) findViewById(R.id.free_sku);
        TextView free_qnty = (TextView) findViewById(R.id.free_qnty);
        TextView total_free = (TextView) findViewById(R.id.total_free);
        TextView total_free_count = (TextView) findViewById(R.id.total_free_count);
        TextView free_title = (TextView) findViewById(R.id.free_title);

        TextView crm_title = (TextView) findViewById(R.id.crm_title);
        TextView crm_sku = (TextView) findViewById(R.id.crm_sku);
        TextView crm_qnty = (TextView) findViewById(R.id.crm_qnty);
        TextView total_crm = (TextView) findViewById(R.id.total_crm);
        TextView total_crm_count = (TextView) findViewById(R.id.total_crm_count);

        TextView posm_title = (TextView) findViewById(R.id.posm_title);
        TextView posm_sku = (TextView) findViewById(R.id.posm_sku);
        TextView posm_qnty = (TextView) findViewById(R.id.posm_qnty);
        TextView total_posm = (TextView) findViewById(R.id.total_posm);
        TextView total_posm_count = (TextView) findViewById(R.id.total_posm_count);

        TextView mi_title = (TextView) findViewById(R.id.mi_title);
        TextView mi_sku = (TextView) findViewById(R.id.mi_sku);
        TextView mi_qnty = (TextView) findViewById(R.id.mi_qnty);
        TextView total_mi = (TextView) findViewById(R.id.total_mi);
        TextView total_mi_count = (TextView) findViewById(R.id.total_mi_count);

        Company company =  Company.getCompanyStructAllCompany(pendingProductList);
        String total_gift_count_st = String.valueOf(getQuantiyCout(company.getGifts()));
        String total_free_count_st = String.valueOf(getQuantiyCout(company.getFreeProduct()));
        String total_crm_count_st = String.valueOf(getQuantiyCout(company.getCrm()));
        String total_posm_count_st = String.valueOf(getQuantiyCout(company.getPosm()));
        String total_mi_count_st = String.valueOf(getQuantiyCout(company.getMi()));

        total_gift_count.setText(total_gift_count_st);
        total_crm_count.setText(total_crm_count_st);
        total_free_count.setText(total_free_count_st);
        total_posm_count.setText(total_posm_count_st);
        total_mi_count.setText(total_mi_count_st);

        ScrollListView.loadListView(this, gift_list, R.layout.data_download_company_product_adaptar, company.getGifts(), "showProductData", 0, company.getGifts().size(), true);
        ScrollListView.loadListView(this, free_list, R.layout.data_download_company_product_adaptar, company.getFreeProduct(), "showProductData", 0, company.getGifts().size(), true);
        ScrollListView.loadListView(this, crm_list, R.layout.data_download_company_product_adaptar, company.getCrm(), "showProductData", 0, company.getCrm().size(), true);
        ScrollListView.loadListView(this, posm_list, R.layout.data_download_company_product_adaptar, company.getPosm(), "showProductData", 0, company.getPosm().size(), true);
        ScrollListView.loadListView(this, mi_list, R.layout.data_download_company_product_adaptar, company.getMi(), "showProductData", 0, company.getMi().size(), true);


        UIComponent.updateListViewHeight(gift_list,0);
        UIComponent.updateListViewHeight(free_list,0);
        UIComponent.updateListViewHeight(crm_list,0);
        UIComponent.updateListViewHeight(posm_list,0);
        UIComponent.updateListViewHeight(mi_list,0);
        //UIComponent.updateListViewHeight(acceptData_list,1);

        Button btn_all = setTextFont((Button) findViewById(R.id.btn_all), this, db, "All");
        Button btn_product = setTextFont((Button) findViewById(R.id.btn_product), this, db, "Product");
        Button btn_gift = setTextFont((Button) findViewById(R.id.btn_gift), this, db, "Gift");
        Button btn_crm = setTextFont((Button) findViewById(R.id.btn_crm), this, db, "CRM");
        Button btn_posm = setTextFont((Button) findViewById(R.id.btn_posm), this, db, "POSM");
        Button btn_mi = setTextFont((Button) findViewById(R.id.btn_mi), this, db, "MI");

        if (company.getGifts().size() > 0)
        {
            gift_sku = setTextFont(gift_sku, context, db, "adaptar_acceptDataCompanySKU");
            gift_qnty = setTextFont(gift_qnty, context, db, "adaptar_acceptDataCompanyQuantity");
            total_gift = setTextFont(total_gift, context, db, "adaptar_acceptDataCompanyTotalGift");
            gift_title = setTextFont(gift_title, context, db, "adaptar_acceptDataCompanyGiftTitle");
            gift_sku.setVisibility(View.GONE);
            gift_qnty.setVisibility(View.GONE);

        }
        else
        {
            gift_sku.setVisibility(View.GONE);
            gift_qnty.setVisibility(View.GONE);
            total_gift.setVisibility(View.GONE);
            gift_title.setVisibility(View.GONE);
            total_gift_count.setVisibility(View.GONE);
            btn_gift.setVisibility(View.GONE);
        }

        if (company.getFreeProduct().size() > 0)
        {
            free_sku = setTextFont(free_sku, context, db, "Free Product");
            free_qnty = setTextFont(free_qnty, context, db, "quantity");
            total_free = setTextFont(total_free, context, db, "adaptar_acceptDataCompanyTotalGift");
            free_title = setTextFont(free_title, context, db, "Free Product");
            free_sku.setVisibility(View.GONE);
            free_qnty.setVisibility(View.GONE);
        }
        else
        {
            free_sku.setVisibility(View.GONE);
            free_qnty.setVisibility(View.GONE);
            total_free.setVisibility(View.GONE);
            free_title.setVisibility(View.GONE);
            total_free_count.setVisibility(View.GONE);
        }
        if (company.getCrm().size() > 0)
        {
            crm_sku = setTextFont(crm_sku, context, db, "CRM");
            crm_qnty = setTextFont(crm_qnty, context, db, "quantity");
            total_crm = setTextFont(total_crm, context, db, "adaptar_acceptDataCompanyTotalGift");
            crm_title = setTextFont(crm_title, context, db, "CRM");
            crm_sku.setVisibility(View.GONE);
            crm_qnty.setVisibility(View.GONE);
        }
        else
        {
            crm_sku.setVisibility(View.GONE);
            crm_qnty.setVisibility(View.GONE);
            total_crm.setVisibility(View.GONE);
            crm_title.setVisibility(View.GONE);
            total_crm_count.setVisibility(View.GONE);
            btn_crm.setVisibility(View.GONE);
        }
        if (company.getPosm().size() > 0)
        {
            posm_sku = setTextFont(posm_sku, context, db, "POSM");
            posm_qnty = setTextFont(posm_qnty, context, db, "quantity");
            total_posm = setTextFont(total_posm, context, db, "adaptar_acceptDataCompanyTotalGift");
            posm_title = setTextFont(posm_title, context, db, "POSM");
            posm_sku.setVisibility(View.GONE);
            posm_qnty.setVisibility(View.GONE);
        }
        else
        {
            posm_sku.setVisibility(View.GONE);
            posm_qnty.setVisibility(View.GONE);
            total_posm.setVisibility(View.GONE);
            posm_title.setVisibility(View.GONE);
            total_posm_count.setVisibility(View.GONE);
            btn_posm.setVisibility(View.GONE);
        }

        if (company.getMi().size() > 0)
        {
            mi_sku = setTextFont(mi_sku, context, db, "MI");
            mi_qnty = setTextFont(mi_qnty, context, db, "quantity");
            total_mi = setTextFont(total_mi, context, db, "adaptar_acceptDataCompanyTotalGift");
            mi_title = setTextFont(mi_title, context, db, "MI");
            mi_sku.setVisibility(View.GONE);
            mi_qnty.setVisibility(View.GONE);
        }
        else
        {
            mi_sku.setVisibility(View.GONE);
            mi_qnty.setVisibility(View.GONE);
            total_mi.setVisibility(View.GONE);
            mi_title.setVisibility(View.GONE);
            total_mi_count.setVisibility(View.GONE);
            btn_mi.setVisibility(View.GONE);
        }

        btn_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleHolder(null,true);
            }
        });

        btn_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleHolder((LinearLayout) findViewById(R.id.products_holder),false);
            }
        });

        btn_gift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleHolder((LinearLayout) findViewById(R.id.gift_holder),false);
            }
        });

        btn_crm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleHolder((LinearLayout) findViewById(R.id.crm_holder),false);
            }
        });

        btn_posm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleHolder((LinearLayout) findViewById(R.id.posm_holder),false);
            }
        });

        btn_mi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleHolder((LinearLayout) findViewById(R.id.mi_holder),false);
            }
        });

        try {
            Product product = allCompany.get(0).getProducts().get(0);
            final TextView acceptData_target = (TextView) findViewById(R.id.acceptData_target);
            final TextView acceptData_route = (TextView) findViewById(R.id.acceptData_route);
            final TextView acceptData_preiod = (TextView) findViewById(R.id.acceptData_preiod);
            final TextView acceptData_qantity = (TextView) findViewById(R.id.acceptData_qantity);
            acceptData_target.setText(getText(R.string.acceptData_target) + String.valueOf(product.getTarget()));
            acceptData_route.setText(product.getRoute() + "/" + product.getSection() + "/" + product.getFrequency());
            acceptData_preiod.setText(DateTimeCalculation.getDate(product.getDate_from()) + " to " + DateTimeCalculation.getDate(product.getDate_to()));
        }catch (Exception e){}

    }
    int getQuantiyCout(ArrayList<Product> products)
    {
        int sum=0;
        for (Product cProduct :products)
        {
            sum+=cProduct.getTotal_sku();
        }
        return sum;
    }

    void toggleHolder(LinearLayout li,Boolean selectAll)
    {
        LinearLayout products_holder = (LinearLayout) findViewById(R.id.products_holder);
        LinearLayout gift_holder  = (LinearLayout) findViewById(R.id.gift_holder);
        LinearLayout crm_holder  = (LinearLayout) findViewById(R.id.crm_holder);
        LinearLayout posm_holder  = (LinearLayout) findViewById(R.id.posm_holder);
        LinearLayout mi_holder  = (LinearLayout) findViewById(R.id.mi_holder);

        if(selectAll)
        {
            products_holder.setVisibility(View.VISIBLE);
            gift_holder.setVisibility(View.VISIBLE);
            crm_holder.setVisibility(View.VISIBLE);
            posm_holder.setVisibility(View.VISIBLE);
            mi_holder.setVisibility(View.VISIBLE);
        }
        else
        {
            products_holder.setVisibility(View.GONE);
            gift_holder.setVisibility(View.GONE);
            crm_holder.setVisibility(View.GONE);
            posm_holder.setVisibility(View.GONE);
            mi_holder.setVisibility(View.GONE);

            li.setVisibility(View.VISIBLE);
        }
    }

    public void showProductData(final ViewData data)
    {
        final Product product = (Product) data.object;
        final TextView product_name  = FontSettings.setTextFont((TextView) data.view.findViewById(R.id.product_name),context,product.getSku());
        final TextView product_quantity  = FontSettings.setTextFont((TextView) data.view.findViewById(R.id.product_quantity),context,String.valueOf(product.getTotal_sku()));
        final LinearLayout layout_holder  = (LinearLayout) data.view.findViewById(R.id.layout_holder);

        try
        {
            Product Nproduct = Product.select(db, db.COLUMN_product_id + "=" +product.getId()).get(0);
            if(Nproduct.getTotal_sku()!=product.getTotal_sku())
            {
                layout_holder.setBackgroundColor(Color.parseColor("#adbedb"));
            }
        }
        catch (Exception ex)
        {
            layout_holder.setBackgroundColor(Color.parseColor("#adbedb"));
        }

    }

    public void showCompanyProductData(final ViewData data)
    {
        Company company = (Company) data.object;

        FontSettings.setTextFont((TextView) data.view.findViewById(R.id.company_name),context,company.getCompany_name());
        FontSettings.setTextFont((TextView) data.view.findViewById(R.id.product_sku),context,db, "adaptar_acceptDataCompanySKU");
        FontSettings.setTextFont((TextView) data.view.findViewById(R.id.product_qnty),context,db, "adaptar_acceptDataCompanyQuantity");
        FontSettings.setTextFont((TextView) data.view.findViewById(R.id.total_product),context,db, "adaptar_acceptDataCompanyTotalProduct");
        FontSettings.setTextFont((TextView) data.view.findViewById(R.id.total_product_count),context,String.valueOf(getQuantiyCout(company.getProducts())));
        ListView product_list  = (ListView) data.view.findViewById(R.id.product_list);

        LinearLayout full_layout_holder = (LinearLayout) data.view.findViewById(R.id.full_layout_holder);



        ScrollListView.loadListViewUpdateHeight(context, product_list, R.layout.data_download_company_product_adaptar, company.getProducts(), "showProductData", 0, company.getProducts().size(), true);


        //UIComponent.updateListViewHeight(product_list,0);

        full_layout_holder.setVisibility(View.VISIBLE);
    }
}
