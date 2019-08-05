package com.quickmas.salim.quickmasretail.Module.RTM.MI.MIPrint;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.quickmas.salim.quickmasretail.R;
import com.quickmas.salim.quickmasretail.Structure.Company;
import com.quickmas.salim.quickmasretail.Utility.FontSettings;

import java.util.ArrayList;

/**
 * Created by Forhad on 03/02/2018.
 */

public class Adaptar_crm_invoice_company extends BaseAdapter implements ListAdapter {
    private Context context;
    LayoutInflater inflater;
    ArrayList<Company> allCompany = new ArrayList<Company>();
    boolean editButtonFlag;
    public Adaptar_crm_invoice_company(Context context,ArrayList<Company> allCompany,boolean editButtonFlag) {
        this.allCompany = allCompany;
        this.context = context;
        this.editButtonFlag = editButtonFlag;
    }

    private static class rowHolder{
        public TextView company_name;
        public ListView product_list;
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
        Adaptar_crm_invoice_company.rowHolder holder = new Adaptar_crm_invoice_company.rowHolder();
        if (view == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.sell_invoice_company, null);

            holder.company_name  = (TextView) view.findViewById(R.id.company_name);
            holder.product_list  = (ListView) view.findViewById(R.id.product_list);

            Typeface tf = FontSettings.getFont(context);


            holder.company_name.setTypeface(tf);
            view.setTag(holder);
        }
        else
        {
            holder = (Adaptar_crm_invoice_company.rowHolder) view.getTag();
        }

        String company = allCompany.get(position).getCompany_name();

        holder.company_name.setText(company);

        Adaptar_crm_invoice_company_product adapter = new Adaptar_crm_invoice_company_product(context, allCompany.get(position).getMi(),editButtonFlag);
        holder.product_list.setAdapter(adapter);
        updateListViewHeight(holder.product_list);
        return view;
    }

    public static void updateListViewHeight(ListView myListView) {
        ListAdapter myListAdapter = myListView.getAdapter();
        if (myListAdapter == null) {
            return;
        }
        int totalHeight = 0;
        int adapterCount = myListAdapter.getCount();
        for (int size = 0; size < adapterCount; size++) {
            View listItem = myListAdapter.getView(size, null, myListView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = myListView.getLayoutParams();
        params.height = (totalHeight + (myListView.getDividerHeight() * (adapterCount)))+50;
        myListView.setLayoutParams(params);
    }
}

