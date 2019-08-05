package com.quickmas.salim.quickmasretail.Module.RTM.RTM.RTMInvoiceList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.quickmas.salim.quickmasretail.Database.DBInitialization;
import com.quickmas.salim.quickmasretail.Model.RTM.Invoice;
import com.quickmas.salim.quickmasretail.Model.RTM.Product;
import com.quickmas.salim.quickmasretail.Model.System.TextString;
import com.quickmas.salim.quickmasretail.Model.System.User;
import com.quickmas.salim.quickmasretail.R;
import com.quickmas.salim.quickmasretail.Module.RTM.RTM.RTMInvoiceList.PrintView.Memo_Print;
import com.quickmas.salim.quickmasretail.Structure.Memo;
import com.quickmas.salim.quickmasretail.Utility.FontSettings;

import java.util.ArrayList;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;


/**
 * Created by Forhad on 03/02/2018.
 */

public class Adaptar_Memo_List extends BaseAdapter implements ListAdapter {
    private Context context;
    LayoutInflater inflater;
    PopupWindow mPopupWindow;
    TextView mText;
    int stats;
    ArrayList<Memo> allProduct = new ArrayList<Memo>();
    DBInitialization db;
    public Adaptar_Memo_List(Context context,ArrayList<Memo> allProduct) {
        this.allProduct = allProduct;
        this.context = context;
    }

    private static class rowHolder{
        public TextView invoice_id;
        public TextView quantity;
        public TextView amount;
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
        Adaptar_Memo_List.rowHolder holder = new Adaptar_Memo_List.rowHolder();
        if (view == null)
        {
            inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.memo_list, null);

            holder.invoice_id  = (TextView) view.findViewById(R.id.invoice_id);
            holder.quantity  = (TextView) view.findViewById(R.id.quantity);
            holder.amount  = (TextView) view.findViewById(R.id.amount);
            holder.action  = (Button) view.findViewById(R.id.action);
            holder.layout_holder = (LinearLayout) view.findViewById(R.id.layout_holder);

            Typeface tf = FontSettings.getFont(context);

            holder.invoice_id.setTypeface(tf);
            holder.quantity.setTypeface(tf);
            holder.amount.setTypeface(tf);

            view.setTag(holder);
        }
        else
        {
            holder = (Adaptar_Memo_List.rowHolder) view.getTag();
        }
        holder.action = FontSettings.setTextFont(holder.action,context,db,"memo_action");
        final String in = Invoice.prefix +String.valueOf(allProduct.get(position).getInvoice_id());
        String quantity = String.valueOf(allProduct.get(position).getQuantity());
        final String amount = String.format("%.2f", allProduct.get(position).getAmount());

        holder.invoice_id.setText(in);
        holder.quantity.setText(String.valueOf(quantity));
        holder.amount.setText(String.valueOf(amount));
        mText = holder.invoice_id;
        final Button btn = holder.action;
        final LinearLayout ly = holder.layout_holder;


        if(allProduct.get(position).getStatus()==1)
        {
            holder.invoice_id.setTextColor(Color.parseColor("#ff571b"));
            holder.quantity.setTextColor(Color.parseColor("#ff571b"));
            holder.amount.setTextColor(Color.parseColor("#ff571b"));
        }
        else if(allProduct.get(position).getStatus()==2)
        {
            holder.invoice_id.setTextColor(Color.parseColor("#006600"));
            holder.quantity.setTextColor(Color.parseColor("#006600"));
            holder.amount.setTextColor(Color.parseColor("#006600"));
        }
        else if(allProduct.get(position).getStatus()==4)
        {
            holder.invoice_id.setTextColor(Color.parseColor("#000000"));
            holder.quantity.setTextColor(Color.parseColor("#000000"));
            holder.amount.setTextColor(Color.parseColor("#000000"));

            holder.invoice_id.setBackgroundColor(Color.parseColor("#ff7a7a"));
            holder.quantity.setBackgroundColor(Color.parseColor("#ff7a7a"));
            holder.amount.setBackgroundColor(Color.parseColor("#ff7a7a"));

            holder.layout_holder.setBackgroundColor(Color.parseColor("#ff7a7a"));

            holder.action.setVisibility(View.GONE);
        }
        else
        {
            holder.invoice_id.setBackgroundColor(Color.parseColor("#ffffff"));
            holder.quantity.setBackgroundColor(Color.parseColor("#ffffff"));
            holder.amount.setBackgroundColor(Color.parseColor("#ffffff"));

            holder.layout_holder.setBackgroundColor(Color.parseColor("#ffffff"));

            holder.invoice_id.setTextColor(Color.parseColor("#000000"));
            holder.quantity.setTextColor(Color.parseColor("#000000"));
            holder.amount.setTextColor(Color.parseColor("#000000"));

            holder.action.setVisibility(View.VISIBLE);
        }


        final int invoice_id = allProduct.get(position).getInvoice_id();
        stats = allProduct.get(position).getStatus();
        db = new DBInitialization(context,null,null,1);
        // final View v =view;
        holder.action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater = (LayoutInflater) context
                        .getSystemService(LAYOUT_INFLATER_SERVICE);
                final View popupView = layoutInflater.inflate(R.layout.memo_pop_up, null);
                final PopupWindow popupWindow = new PopupWindow(popupView,LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                Button print  = FontSettings.setTextFont((Button) popupView.findViewById(R.id.print),context,db,"memopopup_print");
                Button delivery  = FontSettings.setTextFont((Button) popupView.findViewById(R.id.delivery),context,db,"memopopup_devilery");
                Button payment  = FontSettings.setTextFont((Button) popupView.findViewById(R.id.payment),context,db,"memopopup_payment");
                Button make_void  = FontSettings.setTextFont((Button) popupView.findViewById(R.id.make_void),context,db,"memopopup_void");

                Invoice invoice = Invoice.select(db,db.COLUMN_i_invoice_id +"="+allProduct.get(position).getInvoice_id()).get(0);
                Product product = Product.select(db,db.COLUMN_product_id+"="+invoice.getProduct_id()).get(0);

                if(allProduct.get(position).getStatus()==1)
                {
                    //delivery.setVisibility(View.GONE);
                }
                else if(allProduct.get(position).getStatus()==2)
                {
                    payment.setVisibility(View.GONE);
                    make_void.setVisibility(View.GONE);
                }
                else if(allProduct.get(position).getStatus()==3)
                {
                    delivery.setVisibility(View.GONE);
                    payment.setVisibility(View.GONE);
                    make_void.setVisibility(View.GONE);
                }
                else if(allProduct.get(position).getStatus()==4)
                {
                    delivery.setVisibility(View.GONE);
                    payment.setVisibility(View.GONE);
                    make_void.setVisibility(View.GONE);
                    print.setVisibility(View.GONE);
                }
                else if(allProduct.get(position).getStatus()==5)
                {
                    delivery.setVisibility(View.GONE);
                    make_void.setVisibility(View.GONE);
                }
                else if(allProduct.get(position).getStatus()==6)
                {
                    delivery.setVisibility(View.GONE);
                    payment.setVisibility(View.GONE);
                    make_void.setVisibility(View.GONE);
                }
                User cSystemInfo = new User();
                cSystemInfo.select(db,"1=1");
                if(!product.getPayment_permission().equals(cSystemInfo.getUser_name()))
                {
                    payment.setEnabled(false);
                }
                if(!product.getSell_permission().equals(cSystemInfo.getUser_name()))
                {
                    delivery.setEnabled(false);
                }

                print.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                        Intent i = new Intent( context ,Memo_Print.class);
                        i.putExtra("id", String.valueOf(invoice_id));
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(i);
                    }
                });

                payment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(allProduct.get(position).getStatus()==5)
                        {
                            makePayment(amount,invoice_id,6, TextString.textSelectByVarname(db,"memo_diag_payment_toast").getText(),position);
                            popupWindow.dismiss();
                        }
                        else
                        {
                            makePayment(amount,invoice_id,2, TextString.textSelectByVarname(db,"memo_diag_payment_toast").getText(),position);
                            popupWindow.dismiss();
                        }

                    }
                });


                popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        ly.setBackgroundColor(Color.parseColor("#ffffff"));
                    }
                });

                delivery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(allProduct.get(position).getStatus()==1)
                        {
                            makePayment(amount,invoice_id,5, TextString.textSelectByVarname(db,"memo_diag_delivery_toast").getText(),position);
                            popupWindow.dismiss();
                        }
                        else
                        {
                            makePayment(amount,invoice_id,3, TextString.textSelectByVarname(db,"memo_diag_delivery_toast").getText(),position);
                            popupWindow.dismiss();
                        }

                    }
                });


                make_void.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        makePayment(amount,invoice_id,4,TextString.textSelectByVarname(db,"memo_diag_void_toast").getText(),position);
                        popupWindow.dismiss();
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


