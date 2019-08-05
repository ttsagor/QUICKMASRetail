package com.quickmas.salim.quickmasretail.Module.RTM.RTM.RTMSale.NewSale;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.quickmas.salim.quickmasretail.Model.RTM.Product;
import com.quickmas.salim.quickmasretail.R;
import com.quickmas.salim.quickmasretail.Structure.Company;

import java.util.ArrayList;

import static com.quickmas.salim.quickmasretail.Utility.FileManagerSetting.getImageFromFile;
import static com.quickmas.salim.quickmasretail.Utility.UIComponent.buildBackgroundColorSpan;

/**
 * Created by Forhad on 06/02/2018.
 */

public class Adaptar_sell_dashboard_company  extends BaseAdapter implements ListAdapter {
    private Context context;
    LayoutInflater inflater;
    ArrayList<Company> allCompany = new ArrayList<Company>();
    String invoiceOutlet;
    public Adaptar_sell_dashboard_company(Context context,ArrayList<Company> allCompany,String invoiceOutlet) {
        this.allCompany = allCompany;
        this.context = context;
        this.invoiceOutlet = invoiceOutlet;
    }
    private static class rowHolder {
        public TextView selldashboard_companylist_company_name;
        public LinearLayout product_list_data;
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
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        View view = convertView;
        Adaptar_sell_dashboard_company.rowHolder holder = new Adaptar_sell_dashboard_company.rowHolder();
        if (view == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.sell_dashboard_company, null);
            view.setTag(holder);
        }
        else
        {
            holder = (Adaptar_sell_dashboard_company.rowHolder) view.getTag();
        }


        final ArrayList<Product> products = allCompany.get(position).getProductAndGift();

        if(allCompany.get(position).getProductAndGift().size()>0) {

            holder.selldashboard_companylist_company_name  = (TextView) view.findViewById(R.id.selldashboard_companylist_company_name);
            holder.product_list_data  = (LinearLayout) view.findViewById(R.id.product_list_data);

            final String company = allCompany.get(position).getCompany_name();
            holder.selldashboard_companylist_company_name.setText(company);
            holder.selldashboard_companylist_company_name.setVisibility(View.VISIBLE);
            holder.product_list_data.removeAllViews();
            final LinearLayout pl = holder.product_list_data;
            holder.selldashboard_companylist_company_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (pl.getVisibility() == View.VISIBLE) {
                        pl.setVisibility(View.GONE);
                    } else {
                        pl.setVisibility(View.VISIBLE);
                    }

                }
            });

            if (position == 0) {
                pl.setVisibility(View.VISIBLE);
            }

            int lim = products.size() / 3;
            double div = products.size() % 3;
            if (div > 0) {
                lim++;
            }
            for (int i = 0; i < lim; i++) {
                LayoutInflater inflater = LayoutInflater.from(context);
                ViewGroup footer = (ViewGroup) inflater.inflate(R.layout.sell_dashboard_company_product_list, holder.product_list_data, false);


                final int cproduct1 = i * 3;
                final int cproduct2 = i * 3 + 1;
                final int cproduct3 = i * 3 + 2;


                if (products.size() > cproduct1) {
                    String name = products.get(cproduct1).getSku();
                    String quantity = String.valueOf(products.get(cproduct1).getSku_qty());

                    TextView quantity1 = (TextView) footer.findViewById(R.id.quantity1);
                    TextView sku1 = (TextView) footer.findViewById(R.id.sku1);
                    ImageView myImageView1 = (ImageView) footer.findViewById(R.id.myImageView1);
                    sku1.setText(name);

                    int color = Color.parseColor("#FFF5F19E");
                    SpannableString spannableString = new SpannableString(quantity);
                    spannableString = buildBackgroundColorSpan(spannableString, quantity, quantity, color);
                    quantity1.setText(spannableString);

                    myImageView1.setImageBitmap(getImageFromFile(products.get(cproduct1).getPhoto(), context));
                    LinearLayout product1 = (LinearLayout) footer.findViewById(R.id.product1);
                    product1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            String id = String.valueOf(products.get(cproduct1).getId());
                            Intent i = new Intent(context, Sell_Quantity_Select.class);
                            i.putExtra("id", id);
                            i.putExtra("invoiceOutlet", invoiceOutlet);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(i);
                        }
                    });

                } else {
                    LinearLayout product1 = (LinearLayout) footer.findViewById(R.id.product1);
                    product1.setVisibility(View.GONE);
                }


                if (products.size() > cproduct2) {
                    String name = products.get(cproduct2).getSku();
                    String quantity = String.valueOf(products.get(cproduct2).getSku_qty());

                    TextView quantity2 = (TextView) footer.findViewById(R.id.quantity2);
                    TextView sku2 = (TextView) footer.findViewById(R.id.sku2);
                    ImageView myImageView2 = (ImageView) footer.findViewById(R.id.myImageView2);
                    sku2.setText(name);

                    int color = Color.parseColor("#FFF5F19E");
                    SpannableString spannableString = new SpannableString(quantity);
                    spannableString = buildBackgroundColorSpan(spannableString, quantity, quantity, color);
                    quantity2.setText(spannableString);

                    myImageView2.setImageBitmap(getImageFromFile(products.get(cproduct2).getPhoto(), context));

                    LinearLayout product2 = (LinearLayout) footer.findViewById(R.id.product2);
                    product2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String id = String.valueOf(products.get(cproduct2).getId());
                            Intent i = new Intent(context, Sell_Quantity_Select.class);
                            i.putExtra("id", id);
                            i.putExtra("invoiceOutlet", invoiceOutlet);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(i);
                        }
                    });

                } else {
                    LinearLayout product2 = (LinearLayout) footer.findViewById(R.id.product2);
                    product2.setVisibility(View.GONE);
                }


                if (products.size() > cproduct3) {
                    String name = products.get(cproduct3).getSku();
                    String quantity = String.valueOf(products.get(cproduct3).getSku_qty());

                    TextView quantity3 = (TextView) footer.findViewById(R.id.quantity3);
                    TextView sku3 = (TextView) footer.findViewById(R.id.sku3);
                    ImageView myImageView3 = (ImageView) footer.findViewById(R.id.myImageView3);
                    sku3.setText(name);

                    int color = Color.parseColor("#FFF5F19E");
                    SpannableString spannableString = new SpannableString(quantity);
                    spannableString = buildBackgroundColorSpan(spannableString, quantity, quantity, color);
                    quantity3.setText(spannableString);

                    myImageView3.setImageBitmap(getImageFromFile(products.get(cproduct3).getPhoto(), context));

                    LinearLayout product3 = (LinearLayout) footer.findViewById(R.id.product3);
                    product3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String id = String.valueOf(products.get(cproduct3).getId());
                            Intent i = new Intent(context, Sell_Quantity_Select.class);
                            i.putExtra("id", id);
                            i.putExtra("invoiceOutlet", invoiceOutlet);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(i);
                        }
                    });
                } else {
                    LinearLayout product3 = (LinearLayout) footer.findViewById(R.id.product3);
                    product3.setVisibility(View.GONE);
                }

                holder.product_list_data.addView(footer);
            }
        }
        else {
            holder.selldashboard_companylist_company_name  = (TextView) view.findViewById(R.id.selldashboard_companylist_company_name);
            holder.selldashboard_companylist_company_name.setVisibility(View.GONE);
        }

        return view;
    }

}
