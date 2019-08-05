package com.quickmas.salim.quickmasretail.Module.RTM.POSM.POSMSale;


import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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
        public TextView description;
        //public TextView amount;
        //public Button edit_button;
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
            view = inflater.inflate(R.layout.adaptar_posm_invoice_company_product, null);

            holder.sku =(TextView) view.findViewById(R.id.sku);
            holder.qnty = (TextView) view.findViewById(R.id.qnty);
            holder.description =(TextView) view.findViewById(R.id.description);

            Typeface tf = FontSettings.getFont(context);

            holder.sku.setTypeface(tf);
            holder.qnty.setTypeface(tf);
            holder.description.setTypeface(tf);

            view.setTag(holder);
        }
        else
        {
            holder = (Adaptar_crm_invoice_company_product.rowHolder) view.getTag();
        }

        int qnty = allProduct.get(position).getSku_qty();

        final DBInitialization db = new DBInitialization(context,null,null,1);

        holder.sku = FontSettings.setTextFont(holder.sku,context,allProduct.get(position).getSku());
        holder.qnty.setText(String.valueOf(qnty));
        holder.description.setText(String.valueOf(allProduct.get(position).getSub_sku()));
        return view;
    }
}

