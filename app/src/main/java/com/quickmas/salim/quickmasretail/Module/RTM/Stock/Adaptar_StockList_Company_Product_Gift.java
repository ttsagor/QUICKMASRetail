package com.quickmas.salim.quickmasretail.Module.RTM.Stock;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.quickmas.salim.quickmasretail.Database.DBInitialization;
import com.quickmas.salim.quickmasretail.Model.RTM.Product;
import com.quickmas.salim.quickmasretail.R;
import com.quickmas.salim.quickmasretail.Utility.FontSettings;
import com.quickmas.salim.quickmasretail.Utility.UIComponent;

import java.util.ArrayList;

import static com.quickmas.salim.quickmasretail.Utility.FontSettings.setTextFont;

/**
 * Created by Forhad on 03/02/2018.
 */

public class Adaptar_StockList_Company_Product_Gift extends BaseAdapter implements ListAdapter {
    private Context context;
    LayoutInflater inflater;
    ArrayList<Product> allProduct = new ArrayList<Product>();
    public Adaptar_StockList_Company_Product_Gift(Context context,ArrayList<Product> allProduct) {
        this.allProduct = allProduct;
        this.context = context;
    }

    private static class rowHolder{
        public TextView product_name;
        public TextView product_total;
        public TextView product_sell;
        public TextView product_closing;
        public LinearLayout layout_holder;
    }

    @Override
    public int getCount() {
        return allProduct.size();
    }

    @Override
    public Object getItem(int pos) {
        return allProduct.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        Adaptar_StockList_Company_Product_Gift.rowHolder holder = new Adaptar_StockList_Company_Product_Gift.rowHolder();
        if (view == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.stocklist_product_product_gift, null);

            holder.product_name  = (TextView) view.findViewById(R.id.product_name);
            holder.product_total  = (TextView) view.findViewById(R.id.product_qnty);
            holder.product_sell  = (TextView) view.findViewById(R.id.product_sell);
            holder.product_closing  = (TextView) view.findViewById(R.id.product_closing);
            holder.layout_holder  = (LinearLayout) view.findViewById(R.id.layout_holder);
            Typeface tf = FontSettings.getFont(context);

            holder.product_name.setTypeface(tf);
            holder.product_total.setTypeface(tf);
            holder.product_sell.setTypeface(tf);
            holder.product_closing.setTypeface(tf);

            view.setTag(holder);
        }
        else
        {
            holder = (Adaptar_StockList_Company_Product_Gift.rowHolder) view.getTag();
        }

        int opening = allProduct.get(position).getTotal_sku();
        int sell = allProduct.get(position).getSold_sku();
        int closing = opening-sell;



        holder.product_name = setTextFont(holder.product_name, context,  allProduct.get(position).getSku());
        holder.product_total = setTextFont(holder.product_total, context,  String.valueOf(opening));
        holder.product_sell = setTextFont(holder.product_sell, context,  String.valueOf(sell));
        holder.product_closing = setTextFont(holder.product_closing, context,  String.valueOf(closing));

        return view;
    }
}
