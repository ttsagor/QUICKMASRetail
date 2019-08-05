package com.quickmas.salim.quickmasretail.Module.Summary.Product;
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

import java.util.ArrayList;

/**
 * Created by Forhad on 03/02/2018.
 */

public class Summary_SoldList_Product_List extends BaseAdapter implements ListAdapter {
    private Context context;
    LayoutInflater inflater;
    DBInitialization db;
    ArrayList<Product> allProduct = new ArrayList<Product>();
    public Summary_SoldList_Product_List(Context context,ArrayList<Product> allProduct) {
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
        Summary_SoldList_Product_List.rowHolder holder = new Summary_SoldList_Product_List.rowHolder();
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
            holder = (Summary_SoldList_Product_List.rowHolder) view.getTag();
        }

        int memo_count = allProduct.get(position).getTotal_invoice();
        int sumQuantity = allProduct.get(position).getSum_quantity();
        holder.product_name.setText(allProduct.get(position).getSku());

        double amount = sumQuantity*allProduct.get(position).getSku_price();
        holder.product_total.setText(String.valueOf(memo_count));
        holder.product_sell.setText(String.valueOf(sumQuantity));
        holder.product_closing.setText(String.format("%.2f", amount));

        return view;
    }
}

