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

import java.util.ArrayList;

import static com.quickmas.salim.quickmasretail.Utility.UIComponent.updateListViewHeight;

/**
 * Created by Forhad on 03/02/2018.
 */

public class Adapter_StockList_Company extends BaseAdapter implements ListAdapter {
    private Context context;
    LayoutInflater inflater;
    ArrayList<Company> allCompany = new ArrayList<Company>();
    public Adapter_StockList_Company(Context context,ArrayList<Company> allCompany) {
        this.allCompany = allCompany;
        this.context = context;
    }

    private static class rowHolder{
        public TextView company_name;
        public TextView product_sku;
        public TextView product_qnty;
        public TextView total_product;
        //public TextView gift_sku;
       // public TextView gift_qnty;
        //public TextView total_gift;
        //public TextView product_title;
        //public TextView gift_title;
        public TextView product_sell;
        public TextView product_closing;
        public TextView total_product_sell;
        public TextView total_product_closing;
        public TextView total_product_count;
       // public TextView gift_sell;
       // public TextView gift_closing;
       // public TextView total_gift_closing;
       // public TextView total_gift__sell;
       // public TextView total_gift_count;
        public LinearLayout layout_holder;
        public ListView product_list;
       // public ListView gift_list;
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
            view = inflater.inflate(R.layout.stock_list_company, null);

            holder.company_name  = (TextView) view.findViewById(R.id.company_name);
            holder.product_sku  = (TextView) view.findViewById(R.id.product_sku);
            holder.product_qnty  = (TextView) view.findViewById(R.id.product_qnty);
            holder.total_product  = (TextView) view.findViewById(R.id.total_product);
            //holder.gift_sku  = (TextView) view.findViewById(R.id.gift_sku);
            //holder.gift_qnty  = (TextView) view.findViewById(R.id.gift_qnty);
            //holder.total_gift  = (TextView) view.findViewById(R.id.total_gift);
           // holder.product_title  = (TextView) view.findViewById(R.id.product_title);
            //holder.gift_title  = (TextView) view.findViewById(R.id.gift_title);
            holder.product_sell  = (TextView) view.findViewById(R.id.product_sell);
            holder.product_closing  = (TextView) view.findViewById(R.id.product_closing);
            //holder.gift_sell  = (TextView) view.findViewById(R.id.gift_sell);
           // holder.gift_closing  = (TextView) view.findViewById(R.id.gift_closing);
            holder.total_product_sell  = (TextView) view.findViewById(R.id.total_product_sell);
            holder.total_product_closing  = (TextView) view.findViewById(R.id.total_product_closing);
            holder.total_product_count  = (TextView) view.findViewById(R.id.total_product_count);
           // holder.total_gift_closing  = (TextView) view.findViewById(R.id.total_gift_closing);
            //holder.total_gift__sell  = (TextView) view.findViewById(R.id.total_gift__sell);
           // holder.total_gift_count  = (TextView) view.findViewById(R.id.total_gift_count);
            holder.product_list  = (ListView) view.findViewById(R.id.product_list);
            //holder.gift_list  = (ListView) view.findViewById(R.id.gift_list);
            holder.layout_holder  = (LinearLayout) view.findViewById(R.id.layout_holder);

            Typeface tf = FontSettings.getFont(context);

            holder.company_name.setTypeface(tf);
            holder.product_sku.setTypeface(tf);
            holder.product_qnty.setTypeface(tf);
            holder.total_product.setTypeface(tf);
            //holder.gift_sku.setTypeface(tf);
            //holder.gift_qnty.setTypeface(tf);
            //holder.total_gift.setTypeface(tf);
            //holder.product_title.setTypeface(tf);
           // holder.gift_title.setTypeface(tf);
            holder.product_sell.setTypeface(tf);
            holder.product_closing.setTypeface(tf);
           // holder.gift_sell.setTypeface(tf);
           // holder.gift_closing.setTypeface(tf);

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
        //String product_title = TextString.textSelectByVarname(db,"adaptar_acceptDataCompanyProductTitle").getText();
        String gift_title = TextString.textSelectByVarname(db,"adaptar_acceptDataCompanyGiftTitle").getText();

        holder.company_name.setText(company);
        holder.product_sku.setText(product_sku);
        holder.product_qnty.setText(TextString.textSelectByVarname(db,"stock_opening").getText());
        holder.total_product.setText(total_product);
        //holder.product_title.setText(product_title);

        holder.product_sell.setText(TextString.textSelectByVarname(db,"stock_sell").getText());
        holder.product_closing.setText(TextString.textSelectByVarname(db,"stock_closing").getText());

        Adaptar_StockList_Company_Product_Gift adapter = new Adaptar_StockList_Company_Product_Gift(context, allCompany.get(position).getProducts());
        holder.product_list.setAdapter(adapter);
        adapter = new Adaptar_StockList_Company_Product_Gift(context, allCompany.get(position).getGifts());
        //holder.gift_list.setAdapter(adapter);

        String total_product_count  = String.valueOf(getQuantiyCout(allCompany.get(position).getProducts()));
        String total_product_closing = String.valueOf(getClosingCout(allCompany.get(position).getProducts()));
        String total_product_sell = String.valueOf(getSellCount(allCompany.get(position).getProducts()));


        holder.total_product_sell.setText(total_product_sell);
        holder.total_product_closing.setText(total_product_closing);
        holder.total_product_count.setText(total_product_count);

        updateListViewHeight(holder.product_list,1);

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

