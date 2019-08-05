package com.quickmas.salim.quickmasretail.Module.RTM.MI.MIPrint;


import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
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
import com.rey.material.widget.CheckBox;

import java.util.ArrayList;

/**
 * Created by Forhad on 03/02/2018.
 */

public class Adaptar_crm_invoice_company_product extends BaseAdapter implements ListAdapter {
    private Context context;
    LayoutInflater inflater;
    boolean editButtonFlag;
    ArrayList<Product> allProduct = new ArrayList<Product>();
    public Adaptar_crm_invoice_company_product(Context context,ArrayList<Product> allProduct,boolean editButtonFlag) {
        this.allProduct = allProduct;
        this.context = context;
        this.editButtonFlag = editButtonFlag;
    }

    private static class rowHolder{
        public TextView sku;
        public TextView qnty;
        public LinearLayout details_holder;
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
        Adaptar_crm_invoice_company_product.rowHolder holder = new Adaptar_crm_invoice_company_product.rowHolder();
        if (view == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.adaptar_crm_invoice_company_product, null);

            holder.sku =(TextView) view.findViewById(R.id.sku);
            holder.qnty = (TextView) view.findViewById(R.id.qnty);
            holder.details_holder = (LinearLayout) view.findViewById(R.id.details_holder);
            Typeface tf = FontSettings.getFont(context);
            holder.sku.setTypeface(tf);
            holder.qnty.setTypeface(tf);

            view.setTag(holder);
        }
        else
        {
            holder = (Adaptar_crm_invoice_company_product.rowHolder) view.getTag();
        }

        final DBInitialization db = new DBInitialization(context,null,null,1);
        holder.sku.setText(allProduct.get(position).getSku());
        holder.qnty.setText(String.valueOf(allProduct.get(position).getSub_sku()));

        for(String des : allProduct.get(position).getBrand().split(";"))
        {
            final CheckBox ch = new CheckBox(context);
            ch.setText(des);
            ch.setTextColor(Color.BLACK);
            ch.setChecked(true);
            ch.setEnabled(false);
            ch.setGravity(Gravity.CENTER_VERTICAL);
            holder.details_holder.addView(ch);
        }
        return view;
    }
}

