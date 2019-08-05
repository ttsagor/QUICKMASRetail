package com.quickmas.salim.quickmasretail.Module.Summary.Product;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.quickmas.salim.quickmasretail.R;
import com.quickmas.salim.quickmasretail.Structure.Company;
import com.quickmas.salim.quickmasretail.Utility.FontSettings;

import java.util.ArrayList;

/**
 * Created by Forhad on 03/02/2018.
 */

public class Adaptar_Summary_In_Hand_List extends BaseAdapter implements ListAdapter {
    private Context context;
    LayoutInflater inflater;
    ArrayList<Company> allProduct = new ArrayList<Company>();
    public Adaptar_Summary_In_Hand_List(Context context,ArrayList<Company> allProduct) {
        this.allProduct = allProduct;
        this.context = context;
    }

    private static class rowHolder{
        public TextView company_name;
        public TextView in_hand_gift;
        public TextView in_hand;
        public TextView pending_delivery;
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
        Adaptar_Summary_In_Hand_List.rowHolder holder = new Adaptar_Summary_In_Hand_List.rowHolder();
        if (view == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.memo_in_hand_list, null);

            holder.company_name  = (TextView) view.findViewById(R.id.company_name);
            holder.in_hand  = (TextView) view.findViewById(R.id.in_hand);
            holder.pending_delivery  = (TextView) view.findViewById(R.id.pending_delivery);
            holder.in_hand_gift  = (TextView) view.findViewById(R.id.in_hand_gift);

            Typeface tf = FontSettings.getFont(context);

            holder.company_name.setTypeface(tf);
            holder.in_hand.setTypeface(tf);
            holder.pending_delivery.setTypeface(tf);
            holder.in_hand_gift.setTypeface(tf);
            view.setTag(holder);
        }
        else
        {
            holder = (Adaptar_Summary_In_Hand_List.rowHolder) view.getTag();
        }


        int status1 = allProduct.get(position).getTotal_status_1();
        int status2 = allProduct.get(position).getTotal_status_2();
        int status3 = allProduct.get(position).getTotal_status_3();

        holder.company_name.setText(allProduct.get(position).getCompany_name());
        holder.in_hand.setText(String.valueOf((allProduct.get(position).getTotalQuantityAllProduct())));
        holder.pending_delivery.setText(String.valueOf((status1+status2)));
        holder.in_hand_gift.setText(String.valueOf((allProduct.get(position).getTotalQuantityAllGift())));
        return view;
    }
}
