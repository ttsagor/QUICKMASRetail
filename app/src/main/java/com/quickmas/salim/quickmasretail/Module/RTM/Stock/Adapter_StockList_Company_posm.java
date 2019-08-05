package com.quickmas.salim.quickmasretail.Module.RTM.Stock;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.quickmas.salim.quickmasretail.Database.DBInitialization;
import com.quickmas.salim.quickmasretail.Model.RTM.Product;
import com.quickmas.salim.quickmasretail.Model.System.TextString;
import com.quickmas.salim.quickmasretail.R;
import com.quickmas.salim.quickmasretail.Structure.Company;
import com.quickmas.salim.quickmasretail.Utility.FontSettings;
import com.quickmas.salim.quickmasretail.Utility.UIComponent;

import java.util.ArrayList;

import static com.quickmas.salim.quickmasretail.Utility.UIComponent.updateListViewHeight;

/**
 * Created by Forhad on 03/02/2018.
 */

public class Adapter_StockList_Company_posm extends BaseAdapter implements ListAdapter {
    private Context context;
    LayoutInflater inflater;
    ArrayList<Company> allCompany = new ArrayList<Company>();
    public Adapter_StockList_Company_posm(Context context,ArrayList<Company> allCompany) {
        this.allCompany = allCompany;
        this.context = context;
    }

    private static class rowHolder{
        public TextView company_name;
        public TextView posm_sku;
        public TextView posm_qnty;
        public TextView posm_sell;
        public TextView posm_closing;
        public TextView total_posm;
        public TextView total_posm_count;
        public TextView total_posm__sell;
        public TextView total_posm_closing;
        public LinearLayout layout_holder;
        public ListView posm_list;
    }

    @Override
    public int getCount() {
        return allCompany.size();
    }

    @Override
    public Object getItem(int pos) {
        return allCompany.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        rowHolder holder = new rowHolder();
        if (view == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.stock_list_company_posm, null);

            holder.company_name  = (TextView) view.findViewById(R.id.company_name);
            holder.posm_list  = (ListView) view.findViewById(R.id.posm_list);
            holder.posm_sku  = (TextView) view.findViewById(R.id.posm_sku);
            holder.posm_qnty  = (TextView) view.findViewById(R.id.posm_qnty);
            holder.posm_sell  = (TextView) view.findViewById(R.id.posm_sell);
            holder.posm_closing  = (TextView) view.findViewById(R.id.posm_closing);
            holder.total_posm  = (TextView) view.findViewById(R.id.total_posm);
            holder.total_posm_count  = (TextView) view.findViewById(R.id.total_posm_count);
            holder.total_posm__sell  = (TextView) view.findViewById(R.id.total_posm__sell);
            holder.total_posm_closing  = (TextView) view.findViewById(R.id.total_posm_closing);
            holder.layout_holder  = (LinearLayout) view.findViewById(R.id.layout_holder);

            Typeface tf = FontSettings.getFont(context);

            holder.company_name.setTypeface(tf);
            holder.posm_sku.setTypeface(tf);
            holder.posm_qnty.setTypeface(tf);
            holder.posm_sell.setTypeface(tf);
            holder.posm_closing.setTypeface(tf);
            holder.total_posm.setTypeface(tf);
            holder.total_posm_count.setTypeface(tf);
            holder.total_posm__sell.setTypeface(tf);
            holder.total_posm_closing.setTypeface(tf);

            view.setTag(holder);
        }
        else
        {
            holder = (rowHolder) view.getTag();
        }

        String company = allCompany.get(position).getCompany_name();
        DBInitialization db = new DBInitialization(context,null,null,1);
        String product_sku = TextString.textSelectByVarname(db,"adaptar_acceptDataCompanySKU").getText();
        String product_qnty = TextString.textSelectByVarname(db,"adaptar_acceptDataCompanyQuantity").getText();
        String total_product = TextString.textSelectByVarname(db,"adaptar_acceptDataCompanyTotalProduct").getText();
        String total_gift = TextString.textSelectByVarname(db,"adaptar_acceptDataCompanyTotalGift").getText();
        String product_title = TextString.textSelectByVarname(db,"adaptar_acceptDataCompanyProductTitle").getText();
        String gift_title = TextString.textSelectByVarname(db,"adaptar_acceptDataCompanyGiftTitle").getText();

        holder.company_name.setText(company);
        Adaptar_StockList_Company_Product_Gift adapter = new Adaptar_StockList_Company_Product_Gift(context, allCompany.get(position).getPosm());
        holder.posm_list.setAdapter(adapter);

        String total_product_count  = String.valueOf(getQuantiyCout(allCompany.get(position).getProducts()));
        String total_product_closing = String.valueOf(getClosingCout(allCompany.get(position).getProducts()));
        String total_product_sell = String.valueOf(getSellCount(allCompany.get(position).getProducts()));
        holder.posm_sku = UIComponent.getMeasueredLine(holder.posm_sku,context,25);
        updateListViewHeight(holder.posm_list,0);

        if(allCompany.get(position).getPosm().size()>0)
        {
            holder.posm_sku.setText(product_sku);
            holder.posm_qnty.setText(product_qnty);
            holder.posm_sell.setText(TextString.textSelectByVarname(db,"stock_sell").getText());
            holder.posm_closing.setText(TextString.textSelectByVarname(db,"stock_closing").getText());
            holder.total_posm.setText(TextString.textSelectByVarname(db,"adaptar_acceptDataCompanyTotalProduct").getText());

            holder.total_posm_count.setText(String.valueOf(getQuantiyCout(allCompany.get(position).getPosm())));
            holder.total_posm__sell.setText(String.valueOf(getSellCount(allCompany.get(position).getPosm())));
            holder.total_posm_closing.setText(String.valueOf(getClosingCout(allCompany.get(position).getPosm())));
        }
        else
        {
            holder.posm_sku.setVisibility(view.GONE);
            holder.posm_qnty.setVisibility(view.GONE);
            holder.posm_sell.setVisibility(view.GONE);
            holder.posm_closing.setVisibility(view.GONE);
            holder.total_posm.setVisibility(view.GONE);

            holder.total_posm_count.setVisibility(view.GONE);
            holder.total_posm__sell.setVisibility(view.GONE);
            holder.total_posm_closing.setVisibility(view.GONE);
        }
        return view;
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
    int getSellCount(ArrayList<Product> products)
    {
        int sum=0;
        for (Product cProduct :products)
        {
            sum+=cProduct.getSold_sku();
        }
        return sum;
    }

    int getClosingCout(ArrayList<Product> products)
    {
        int sum=0;
        for (Product cProduct :products)
        {
            sum+=(cProduct.getTotal_sku() - cProduct.getSold_sku());
        }
        return sum;
    }
}

