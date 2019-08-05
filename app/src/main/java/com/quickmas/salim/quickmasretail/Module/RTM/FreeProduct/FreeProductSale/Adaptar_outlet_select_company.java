package com.quickmas.salim.quickmasretail.Module.RTM.FreeProduct.FreeProductSale;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.SpannableString;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.quickmas.salim.quickmasretail.Database.DBInitialization;
import com.quickmas.salim.quickmasretail.Model.RTM.FreeProductInvoice;
import com.quickmas.salim.quickmasretail.Model.RTM.FreeProductInvoiceHead;
import com.quickmas.salim.quickmasretail.Model.RTM.Product;
import com.quickmas.salim.quickmasretail.Model.System.User;
import com.quickmas.salim.quickmasretail.R;
import com.quickmas.salim.quickmasretail.Structure.Company;
import com.quickmas.salim.quickmasretail.Utility.DateTimeCalculation;
import com.quickmas.salim.quickmasretail.Utility.FontSettings;
import com.quickmas.salim.quickmasretail.Utility.TypeConvertion;
import com.rey.material.widget.CheckBox;

import java.util.ArrayList;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static com.quickmas.salim.quickmasretail.Utility.FileManagerSetting.getImageFromFile;
import static com.quickmas.salim.quickmasretail.Utility.UIComponent.buildBackgroundColorSpan;
import static com.quickmas.salim.quickmasretail.Utility.UIComponent.spinnerDataLoad;

/**
 * Created by Forhad on 06/02/2018.
 */

public class Adaptar_outlet_select_company  extends BaseAdapter implements ListAdapter {
    private Context context;
    LayoutInflater inflater;
    ArrayList<Company> allCompany = new ArrayList<Company>();
    String invoiceOutlet;
    public Adaptar_outlet_select_company(Context context,ArrayList<Company> allCompany,String invoiceOutlet) {
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
        Adaptar_outlet_select_company.rowHolder holder = new Adaptar_outlet_select_company.rowHolder();
        if (view == null) {
            inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.sell_dashboard_company, null);
            view.setTag(holder);
        }
        else
        {
            holder = (Adaptar_outlet_select_company.rowHolder) view.getTag();
        }


        final ArrayList<Product> products = allCompany.get(position).getGifts();

        if(allCompany.get(position).getGifts().size()>0) {

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
                    final TextView sku1 = (TextView) footer.findViewById(R.id.sku1);
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
                            showProductDetails(products.get(cproduct1),sku1);
                            //Intent i = new Intent(context, Sell_Quantity_Select.class);
                            //i.putExtra("id", id);
                            ///i.putExtra("invoiceOutlet", invoiceOutlet);
                            //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            //context.startActivity(i);
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
                    final TextView sku2 = (TextView) footer.findViewById(R.id.sku2);
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
                            showProductDetails(products.get(cproduct2),sku2);
                            String id = String.valueOf(products.get(cproduct2).getId());
                            //Intent i = new Intent(context, Sell_Quantity_Select.class);
                            //i.putExtra("id", id);
                            //i.putExtra("invoiceOutlet", invoiceOutlet);
                            //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            //context.startActivity(i);
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
                            //Intent i = new Intent(context, Sell_Quantity_Select.class);
                            //i.putExtra("id", id);
                            //i.putExtra("invoiceOutlet", invoiceOutlet);
                            //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            //context.startActivity(i);
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
    int sale_count=1;
    void showProductDetails(final Product product,final TextView skuView)
    {
        if(product.getSku_qty()<=0)
        {
            return;
        }
        sale_count=1;
        final ArrayList<String> checkedDescription = new ArrayList<>();
        final DBInitialization db = new DBInitialization(context,null,null,1);

        ArrayList<FreeProductInvoice> cProduct = FreeProductInvoice.select(db,db.COLUMN_free_Invoice_product_id+"="+product.getId()+" AND "+db.COLUMN_free_Invoice_status+"=0");

        if (cProduct.size()>0)
        {
            sale_count = cProduct.get(0).getProduct_quantity();
        }

        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        final View popupView = layoutInflater.inflate(R.layout.pop_up_free_product_outlet_product_select, null);
        final PopupWindow popupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);


        Button add  = FontSettings.setTextFont((Button) popupView.findViewById(R.id.add),context,db,"Add Product");
        TextView sku  = FontSettings.setTextFont((TextView) popupView.findViewById(R.id.sku),context,product.getSku());
        final TextView quantity  = FontSettings.setTextFont((TextView) popupView.findViewById(R.id.quantity),context,String.valueOf(sale_count));

        final ImageView close_tab = (ImageView) popupView.findViewById(R.id.close_tab);
        final ImageView image = (ImageView) popupView.findViewById(R.id.image);
        final ImageView minus = (ImageView) popupView.findViewById(R.id.minus);
        final ImageView plus = (ImageView) popupView.findViewById(R.id.plus);
        final EditText comment  = (EditText) popupView.findViewById(R.id.comment);
        final LinearLayout description_holder  = (LinearLayout) popupView.findViewById(R.id.description_holder);
        image.setImageBitmap(getImageFromFile(product.getPhoto(), context));
        TextView av_quantity  = FontSettings.setTextFont((TextView) popupView.findViewById(R.id.av_quantity),context,String.valueOf(product.getSku_qty()));
        final String[] descriptions = product.getSub_sku().split(";");

        if(cProduct.size()>0)
        {
            comment.setText(cProduct.get(0).getRemark());

            String[] dbDescriptions = cProduct.get(0).getDetails().split(";");
            for(String str : dbDescriptions)
            {
                checkedDescription.add(str);
            }
        }

        for(final String des : descriptions)
        {
            if(!des.equals(""))
            {
                final CheckBox ch = new CheckBox(context);
                ch.setText(des);
                ch.setTextColor(Color.BLACK);
                if (checkedDescription.contains(des)) {
                    ch.setChecked(true);
                }
                ch.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (ch.isChecked()) {
                            if (!checkedDescription.contains(des)) {
                                checkedDescription.add(des);
                            }
                        } else {
                            checkedDescription.remove(des);
                        }
                    }
                });
                description_holder.addView(ch);
            }
        }
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sale_count-1 >= 1)
                {
                    sale_count--;
                    quantity.setText(String.valueOf(sale_count));
                }
            }
        });

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(product.getSku_qty() >= sale_count+1)
                {
                    sale_count++;
                    quantity.setText(String.valueOf(sale_count));
                }
            }
        });
        close_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User cSystemInfo = new User();
                cSystemInfo.select(db,"1=1");

                int invoice_id =1;
                ArrayList<FreeProductInvoiceHead> pendingId = FreeProductInvoiceHead.getPendingInvoices(db);
                if(pendingId.size()>0)
                {
                    invoice_id = pendingId.get(0).getId();
                }
                else
                {
                    invoice_id = FreeProductInvoiceHead.getMaxInvoice(db)+1;
                    FreeProductInvoiceHead freeProductInvoiceHead = new FreeProductInvoiceHead();
                    freeProductInvoiceHead.setId(invoice_id);
                    freeProductInvoiceHead.setInvoice_by(cSystemInfo.getUser_name());
                    freeProductInvoiceHead.setInvoice_date(DateTimeCalculation.getCurrentDateTime());
                    freeProductInvoiceHead.setStatus(0);
                    freeProductInvoiceHead.insert(db);
                }
                String res = "";
                for(String str : checkedDescription)
                {
                    res+=str+";";
                }
                FreeProductInvoice freeProductInvoice = new FreeProductInvoice();
                freeProductInvoice.setInvoice_id(invoice_id);
                freeProductInvoice.setOutlet_id(TypeConvertion.parseInt(invoiceOutlet));
                freeProductInvoice.setDetails(res);
                freeProductInvoice.setRemark(comment.getText().toString());
                freeProductInvoice.setRoute(product.getRoute());
                freeProductInvoice.setProduct_id(product.getId());
                freeProductInvoice.setProduct_quantity(TypeConvertion.parseInt(quantity.getText().toString()));
                freeProductInvoice.setInvoice_by(cSystemInfo.getUser_name());
                freeProductInvoice.setInvoice_date(DateTimeCalculation.getCurrentDateTime());
                freeProductInvoice.setStatus(0);
                freeProductInvoice.insert(db);

                popupWindow.dismiss();
                Intent i = new Intent(context, FreeProductCurrentInvoice.class);
                i.putExtra("id", product.getId());
                i.putExtra("invoiceOutlet", invoiceOutlet);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);

            }
        });

        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.showAtLocation(skuView, Gravity.CENTER, 0, 0);

    }

}

