package com.quickmas.salim.quickmasretail.Module.RTM.MI.MISale;


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
import android.widget.Toast;

import com.quickmas.salim.quickmasretail.Database.DBInitialization;

import com.quickmas.salim.quickmasretail.Model.RTM.MI.MIInvoice;
import com.quickmas.salim.quickmasretail.Model.RTM.MI.MIInvoiceHead;
import com.quickmas.salim.quickmasretail.Model.RTM.Product;
import com.quickmas.salim.quickmasretail.Model.System.User;
import com.quickmas.salim.quickmasretail.Module.RTM.CRM.CRMSale.CRMCurrentInvoice;
import com.quickmas.salim.quickmasretail.R;
import com.quickmas.salim.quickmasretail.Structure.Company;
import com.quickmas.salim.quickmasretail.Utility.DateTimeCalculation;
import com.quickmas.salim.quickmasretail.Utility.FontSettings;
import com.rey.material.widget.CheckBox;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static com.quickmas.salim.quickmasretail.Utility.FileManagerSetting.getImageFromFile;
import static com.quickmas.salim.quickmasretail.Utility.UIComponent.buildBackgroundColorSpan;

/**
 * Created by Forhad on 06/02/2018.
 */

public class Adaptar_crm_outlet_select_company  extends BaseAdapter implements ListAdapter {
    private Context context;
    LayoutInflater inflater;
    ArrayList<Company> allCompany = new ArrayList<Company>();
    String invoiceOutlet;
    public Adaptar_crm_outlet_select_company(Context context,ArrayList<Company> allCompany,String invoiceOutlet) {
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
        Adaptar_crm_outlet_select_company.rowHolder holder = new Adaptar_crm_outlet_select_company.rowHolder();
        if (view == null) {
            inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.sell_dashboard_company, null);
            view.setTag(holder);
        }
        else
        {
            holder = (Adaptar_crm_outlet_select_company.rowHolder) view.getTag();
        }


        final ArrayList<Product> products = allCompany.get(position).getMi();

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

    void showProductDetails(final Product product,final TextView skuView)
    {

        final ArrayList<String> checkedDescription = new ArrayList<>();
        final DBInitialization db = new DBInitialization(context,null,null,1);

        ArrayList<MIInvoice> cProduct = MIInvoice.select(db,db.COLUMN_crm_Invoice_product_id+"="+product.getId()+" AND "+db.COLUMN_free_Invoice_status+"=0");



        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        final View popupView = layoutInflater.inflate(R.layout.pop_up_crm_product_outlet_product_select, null);
        final PopupWindow popupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);


        Button add  = FontSettings.setTextFont((Button) popupView.findViewById(R.id.add),context,db,"Add Product");
        TextView sku  = FontSettings.setTextFont((TextView) popupView.findViewById(R.id.sku),context,product.getSku());
        final TextView payment_title  = FontSettings.setTextFont((TextView) popupView.findViewById(R.id.payment_title),context,"Marketing Intelligence");
        final EditText comment  = (EditText) popupView.findViewById(R.id.comment);
        final LinearLayout description_holder  = (LinearLayout) popupView.findViewById(R.id.description_holder);
        final ImageView close_tab = (ImageView) popupView.findViewById(R.id.close_tab);

        final String[] descriptions = product.getSub_sku().split(";");

        if(cProduct.size()>0)
        {
            comment.setText(cProduct.get(0).getComment());

            String[] dbDescriptions = cProduct.get(0).getDescription().split(";");
            for(String str : dbDescriptions)
            {
                checkedDescription.add(str);
            }
        }

        for(final String des : descriptions)
        {
            final CheckBox ch = new CheckBox(context);
            ch.setText(des);
            ch.setTextColor(Color.BLACK);
            if(checkedDescription.contains(des))
            {
                ch.setChecked(true);
            }
            ch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(ch.isChecked())
                    {
                        if(!checkedDescription.contains(des))
                        {
                            checkedDescription.add(des);
                        }
                    }
                    else
                    {
                        checkedDescription.remove(des);
                    }
                }
            });
            description_holder.addView(ch);
        }

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
                if(comment.getText().toString().equals("") || checkedDescription.size()==0)
                {
                   Toasty.error(context, "Please Fill up Comment and Check atleast one Description", Toast.LENGTH_SHORT, true).show();
                   return;
                }
                int invoice_id =1;
                ArrayList<MIInvoiceHead> pendingId = MIInvoiceHead.getPendingInvoices(db);
                if(pendingId.size()>0)
                {
                    invoice_id = pendingId.get(0).getId();
                }
                else
                {
                    invoice_id = MIInvoiceHead.getMaxInvoice(db)+1;
                    MIInvoiceHead crmProductInvoiceHead = new MIInvoiceHead();
                    crmProductInvoiceHead.setId(invoice_id);
                    crmProductInvoiceHead.setOutlet(invoiceOutlet);
                    crmProductInvoiceHead.setInvoice_by(cSystemInfo.getUser_name());
                    crmProductInvoiceHead.setInvoice_date(DateTimeCalculation.getCurrentDateTime());
                    crmProductInvoiceHead.setStatus(0);
                    crmProductInvoiceHead.insert(db);
                }
                String res = "";
                for(String str : checkedDescription)
                {
                    res+=str+";";
                }
                MIInvoice freeProductInvoice = new MIInvoice();
                freeProductInvoice.setInvoice_id(invoice_id);
                freeProductInvoice.setProduct_id(product.getId());
                freeProductInvoice.setComment(comment.getText().toString());
                freeProductInvoice.setDescription(res);
                freeProductInvoice.setInvoice_by(cSystemInfo.getUser_name());
                freeProductInvoice.setInvoice_date(DateTimeCalculation.getCurrentDateTime());
                freeProductInvoice.setStatus(0);
                freeProductInvoice.insert(db);


                Intent i = new Intent(context, MiCurrentInvoice.class);
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

