package com.quickmas.salim.quickmasretail.Module.RTM.FreeProduct.FreeProductSaleList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Environment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.quickmas.salim.quickmasretail.Database.DBInitialization;
import com.quickmas.salim.quickmasretail.Model.RTM.FreeProductInvoiceHead;
import com.quickmas.salim.quickmasretail.Model.RTM.Invoice;
import com.quickmas.salim.quickmasretail.Model.RTM.Product;
import com.quickmas.salim.quickmasretail.Model.System.TextString;
import com.quickmas.salim.quickmasretail.Model.System.User;
import com.quickmas.salim.quickmasretail.R;
import com.quickmas.salim.quickmasretail.Utility.FileManagerSetting;
import com.quickmas.salim.quickmasretail.Utility.FontSettings;
import java.util.ArrayList;
import static android.content.Context.LAYOUT_INFLATER_SERVICE;


/**
 * Created by Forhad on 03/02/2018.
 */

public class Adaptar_free_product_list extends BaseAdapter implements ListAdapter {
    private Context context;
    LayoutInflater inflater;
    PopupWindow mPopupWindow;
    TextView mText;
    int stats;
    ArrayList<FreeProductInvoiceHead> allProduct = new ArrayList<FreeProductInvoiceHead>();
    DBInitialization db;
    public Adaptar_free_product_list(Context context,ArrayList<FreeProductInvoiceHead> allProduct) {
        this.allProduct = allProduct;
        this.context = context;
    }

    private static class rowHolder{
        public TextView invoice_id;
        public TextView quantity;
        public TextView amount;
        public TextView date;
        public Button action;
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
        Adaptar_free_product_list.rowHolder holder = new Adaptar_free_product_list.rowHolder();
        if (view == null)
        {
            inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.adaptar_free_product_list_invoice_list, null);

            holder.invoice_id  = (TextView) view.findViewById(R.id.invoice_id);
            holder.quantity  = (TextView) view.findViewById(R.id.quantity);
            holder.amount  = (TextView) view.findViewById(R.id.amount);
            holder.action  = (Button) view.findViewById(R.id.action);
            holder.date  = (TextView) view.findViewById(R.id.date);
            holder.layout_holder = (LinearLayout) view.findViewById(R.id.layout_holder);

            Typeface tf = FontSettings.getFont(context);

            holder.invoice_id.setTypeface(tf);
            holder.quantity.setTypeface(tf);
            holder.amount.setTypeface(tf);
            holder.date.setTypeface(tf);

            view.setTag(holder);
        }
        else
        {
            holder = (Adaptar_free_product_list.rowHolder) view.getTag();
        }



        String outlet = allProduct.get(position).getOutlet();
        if(allProduct.get(position).getOutlet().length()>5)
        {
            outlet = allProduct.get(position).getOutlet().substring(0,5)+"..";
        }

        holder.action = FontSettings.setTextFont(holder.action,context,db,"memo_action");
        final String in = Invoice.prefix +String.valueOf(allProduct.get(position).getId());

        final String amount = String.valueOf(allProduct.get(position).getProduct_quantity());
        String date =  allProduct.get(position).getInvoice_date().replace(" ","\n");
        holder.date.setText(date);
        holder.invoice_id.setText(in);
        holder.quantity.setText(outlet);
        holder.amount.setText(String.valueOf(amount));
        mText = holder.invoice_id;
        final Button btn = holder.action;
        final LinearLayout ly = holder.layout_holder;


        //holder.invoice_id.setBackgroundColor(Color.parseColor("#ffffff"));
        //holder.quantity.setBackgroundColor(Color.parseColor("#ffffff"));
        //holder.amount.setBackgroundColor(Color.parseColor("#ffffff"));

        holder.layout_holder.setBackgroundColor(Color.parseColor("#ffffff"));

        holder.invoice_id.setTextColor(Color.parseColor("#000000"));
        holder.quantity.setTextColor(Color.parseColor("#000000"));
        holder.amount.setTextColor(Color.parseColor("#000000"));
        holder.date.setTextColor(Color.parseColor("#000000"));


        final FreeProductInvoiceHead invoiceHead = allProduct.get(position);
        stats = allProduct.get(position).getStatus();
        db = new DBInitialization(context,null,null,1);
        final String invoice_id = String.valueOf(allProduct.get(position).getId());

        // final View v =view;
        holder.action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater = (LayoutInflater) context
                        .getSystemService(LAYOUT_INFLATER_SERVICE);
                final View popupView = layoutInflater.inflate(R.layout.memo_pop_up, null);
                final PopupWindow popupWindow = new PopupWindow(popupView,LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                Button print  = FontSettings.setTextFont((Button) popupView.findViewById(R.id.print),context,db,"memopopup_print");
                Button delivery  = FontSettings.setTextFont((Button) popupView.findViewById(R.id.delivery),context,db,"Photo");
                Button payment  = FontSettings.setTextFont((Button) popupView.findViewById(R.id.payment),context,db,"memopopup_payment");
                Button make_void  = FontSettings.setTextFont((Button) popupView.findViewById(R.id.make_void),context,db,"memopopup_void");

                //delivery.setVisibility(View.GONE);
                payment.setVisibility(View.GONE);


                User cSystemInfo = new User();
                cSystemInfo.select(db,"1=1");

                print.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent( context ,FreeProductPrint.class);
                        i.putExtra("invoice_id", invoice_id);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(i);
                    }
                });

                delivery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                        imagepopup(invoiceHead,btn);
                        //context.startActivity(i);
                    }
                });


                make_void.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //makePayment(amount,invoice_id,4,TextString.textSelectByVarname(db,"memo_diag_void_toast").getText(),position);
                        popupWindow.dismiss();
                    }
                });

                popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        ly.setBackgroundColor(Color.parseColor("#ffffff"));
                    }
                });
                // Closes the popup window when touch outside.
                popupWindow.setOutsideTouchable(true);
                popupWindow.setFocusable(true);
                // Removes default background.
                popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                popupWindow.showAsDropDown(btn, 0, 0);
                ly.setBackgroundColor(Color.parseColor("#72a8ff"));
            }
        });
        return view;
    }

    void imagepopup(FreeProductInvoiceHead freeProductInvoiceHead,final Button btn)
    {

        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        final View popupView = layoutInflater.inflate(R.layout.pop_up_free_product_invoice_list_images, null);
        final PopupWindow popupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        final ImageView close_tab = (ImageView) popupView.findViewById(R.id.close_tab);
        final ImageView image = (ImageView) popupView.findViewById(R.id.image);
        final LinearLayout image_holder = (LinearLayout) popupView.findViewById(R.id.image_holder);
        close_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        String[] images = freeProductInvoiceHead.getPhoto().split(";");

        for(String img : images)
        {
            ImageView imageview = new ImageView(context);
            LinearLayout.LayoutParams params = new LinearLayout
                    .LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,400);
            imageview.setPadding(0,10,0,0);
            imageview.setImageURI(Uri.parse(Environment.getExternalStorageDirectory().toString() + "/" + FileManagerSetting.folder_name + "/" + FileManagerSetting.rtm_free_product + "/" + img.trim()));
            imageview.setLayoutParams(params);
            image_holder.addView(imageview);

        }



        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.showAtLocation(btn, Gravity.CENTER, 0, 0);
    }

    void makePayment(String amount,int invoice_id,int cStats,String toast,int position)
    {
        if(cStats==3)
        {
            ArrayList<Invoice> invoices = Invoice.select(db,db.COLUMN_i_invoice_id+"="+String.valueOf(invoice_id));

            for(Invoice cInvoice : invoices)
            {
                Product product = Product.select(db,db.COLUMN_product_id+"="+String.valueOf(cInvoice.getProduct_id())).get(0);
                if(cInvoice.getQuantity()>product.getSku_qty())
                {
                    Toast.makeText(context,String.valueOf(product.getSku_qty())+" "+TextString.textSelectByVarname(db,"sellInvoice_invoice_limit_ex_data_selected").getText(), Toast.LENGTH_LONG).show();
                    return;
                }

            }

            for(Invoice cInvoice : invoices)
            {
                int pID = cInvoice.getProduct_id();
                Product product = Product.select(db,db.COLUMN_product_id+"="+pID).get(0);

                int quantity = product.getSku_qty()-cInvoice.getQuantity();
                product.setSku_qty(quantity);
                quantity = product.getSold_sku()+cInvoice.getQuantity();
                product.setSold_sku(quantity);
                product.update(db);
            }
        }
        else if(cStats==4)
        {
            ArrayList<Invoice> invoices = Invoice.select(db,db.COLUMN_i_invoice_id+"="+String.valueOf(invoice_id));

            for(Invoice cInvoice : invoices)
            {
                int pID = cInvoice.getProduct_id();
                Product product = Product.select(db,db.COLUMN_product_id+"="+pID).get(0);
                int quantity = product.getSku_qty()+cInvoice.getQuantity();
                product.setSku_qty(quantity);
                product.update(db);
            }
        }
        Invoice.update(db,db.COLUMN_i_status+"="+String.valueOf(cStats),db.COLUMN_i_invoice_id+"="+String.valueOf(invoice_id));
        allProduct.get(position).setStatus(cStats);
        Toast.makeText(context,toast, Toast.LENGTH_LONG).show();
        notifyDataSetChanged();
    }
}


