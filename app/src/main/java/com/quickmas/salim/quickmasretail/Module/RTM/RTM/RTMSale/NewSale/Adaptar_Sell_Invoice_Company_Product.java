package com.quickmas.salim.quickmasretail.Module.RTM.RTM.RTMSale.NewSale;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.quickmas.salim.quickmasretail.Database.DBInitialization;
import com.quickmas.salim.quickmasretail.Model.RTM.Invoice;
import com.quickmas.salim.quickmasretail.Model.RTM.Product;
import com.quickmas.salim.quickmasretail.R;
import com.quickmas.salim.quickmasretail.Utility.FontSettings;

import java.util.ArrayList;

/**
 * Created by Forhad on 03/02/2018.
 */

public class Adaptar_Sell_Invoice_Company_Product extends BaseAdapter implements ListAdapter {
    private Context context;
    LayoutInflater inflater;
    boolean editButtonFlag;
    ArrayList<Product> allProduct = new ArrayList<Product>();
    public Adaptar_Sell_Invoice_Company_Product(Context context,ArrayList<Product> allProduct,boolean editButtonFlag) {
        this.allProduct = allProduct;
        this.context = context;
        this.editButtonFlag = editButtonFlag;
    }

    private static class rowHolder{
        public TextView sku;
        public TextView qnty;
        public TextView qprice;
        public TextView amount;
        public Button edit_button;
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
        Adaptar_Sell_Invoice_Company_Product.rowHolder holder = new Adaptar_Sell_Invoice_Company_Product.rowHolder();
        if (view == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.sell_invoice_company_list, null);

            holder.sku =(TextView) view.findViewById(R.id.sku);
            holder.qnty = (TextView) view.findViewById(R.id.qnty);
            holder.qprice =(TextView) view.findViewById(R.id.qprice);
            holder.amount = (TextView) view.findViewById(R.id.amount);
            holder.edit_button = (Button) view.findViewById(R.id.edit_button);

            Typeface tf = FontSettings.getFont(context);

            holder.sku.setTypeface(tf);
            holder.qnty.setTypeface(tf);
            holder.qprice.setTypeface(tf);
            holder.amount.setTypeface(tf);
            holder.edit_button.setTypeface(tf);
            view.setTag(holder);
        }
        else
        {
            holder = (Adaptar_Sell_Invoice_Company_Product.rowHolder) view.getTag();
        }

        int qnty = allProduct.get(position).getSku_qty();
        double uprice = allProduct.get(position).getSku_price();
        double totalPrice = qnty*uprice;
        final DBInitialization db = new DBInitialization(context,null,null,1);
        holder.edit_button = FontSettings.setTextFont(holder.edit_button,context,db,"sellInvoice_invoice_edit");
        holder.sku.setText(allProduct.get(position).getSku());
        holder.qnty.setText(String.valueOf(qnty));
        holder.qprice.setText(String.format("%.2f", uprice));
        holder.amount.setText(String.format("%.2f", totalPrice));
        if(editButtonFlag){
            holder.edit_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent i = new Intent( context ,Sell_Quantity_Select.class);
                    i.putExtra("id", String.valueOf(allProduct.get(position).getId()));

                    String con = db.COLUMN_i_product_id+"="+allProduct.get(position).getId()+" AND "+db.COLUMN_i_status+"=0";
                    i.putExtra("invoiceOutlet", String.valueOf(Invoice.select(db,con).get(0).getOutlet_id()));
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(i);
                }
            });
        }
        else
        {
            holder.edit_button.setVisibility(View.GONE);
        }



        return view;
    }
}

