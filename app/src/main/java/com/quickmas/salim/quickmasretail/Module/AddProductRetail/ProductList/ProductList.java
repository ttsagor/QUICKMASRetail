package com.quickmas.salim.quickmasretail.Module.AddProductRetail.ProductList;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.quickmas.salim.quickmasretail.Database.DBInitialization;
import com.quickmas.salim.quickmasretail.Model.POS.NewRetailProduct;
import com.quickmas.salim.quickmasretail.Model.POS.NewRetailProductEntryHead;
import com.quickmas.salim.quickmasretail.Model.POS.PosInvoiceHead;
import com.quickmas.salim.quickmasretail.Model.System.DashboardMenu;
import com.quickmas.salim.quickmasretail.Module.AddProductRetail.AddProduct.AddProduct;
import com.quickmas.salim.quickmasretail.Module.POS.PosInvoicePrint.PosInvoicePrint;
import com.quickmas.salim.quickmasretail.R;
import com.quickmas.salim.quickmasretail.Utility.FileManagerSetting;
import com.quickmas.salim.quickmasretail.Utility.FontSettings;
import com.quickmas.salim.quickmasretail.Utility.UI.ScrollListView;
import com.quickmas.salim.quickmasretail.Utility.UI.ViewData;
import com.quickmas.salim.quickmasretail.Utility.UIComponent;

import java.util.ArrayList;

import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_new_retail_product_brand;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_new_retail_product_category;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_new_retail_product_entry_id;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_new_retail_product_head_id;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_new_retail_product_sku;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_new_retail_product_sub_category;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_head_customer;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_head_invoice_id;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.TABLE_new_retail_product;
import static com.quickmas.salim.quickmasretail.Model.System.DashboardMenu.selectByVarname;
import static com.quickmas.salim.quickmasretail.Utility.FontSettings.getFont;
import static com.quickmas.salim.quickmasretail.Utility.FontSettings.setTextFont;
import static com.quickmas.salim.quickmasretail.Utility.UIComponent.setLinearLayoutBackground;

public class ProductList extends AppCompatActivity {
    DBInitialization db;
    Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_product_list);
        mContext = this;

    }

    @Override
    protected void onResume() {
        super.onResume();
        db = new DBInitialization(this,null,null,1);
        DashboardMenu cMenu =  selectByVarname(db,"dashboard_sell_pos_list");
        LinearLayout headimg = (LinearLayout) findViewById(R.id.image1);
        headimg = setLinearLayoutBackground(headimg, cMenu.getImage(), this);
        TextView headtext = (TextView) findViewById(R.id.text1);
        headtext.setTypeface(getFont(this));
        headtext.setText("Product List");

        Button add_product = (Button) findViewById(R.id.add_product);

        add_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, AddProduct.class);
                mContext.startActivity(i);
            }
        });

        EditText search_box = setTextFont((EditText) findViewById(R.id.search_box),this,"");
        search_box.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateListview();
            }
        });
        updateListview();
    }

    public void updateListview()
    {
        ListView product_list = (ListView) findViewById(R.id.product_list);
        String txt = ((EditText) findViewById(R.id.search_box)).getText().toString();
        ArrayList<NewRetailProductEntryHead> entryHeads = new ArrayList<>();
        if(!txt.equals(""))
        {
            String con = COLUMN_new_retail_product_head_id+" IN (SELECT "+COLUMN_new_retail_product_entry_id+" FROM "+TABLE_new_retail_product+" WHERE "+COLUMN_new_retail_product_category+" LIKE '%"+txt+"%' OR "+COLUMN_new_retail_product_sub_category+" LIKE '%"+txt+"%'  or "+COLUMN_new_retail_product_sku+" LIKE '%"+txt+"%' or "+COLUMN_new_retail_product_brand+" LIKE '%"+txt+"%')";
            System.out.println(con);
            entryHeads = NewRetailProductEntryHead.select(db,con);
        }
        else
        {
            entryHeads = NewRetailProductEntryHead.select(db,"1=1");
        }

        ScrollListView.loadListView(mContext, product_list, R.layout.adaptar_retail_product_list, entryHeads, "showData", 0, 100, true);
    }

    public void showData(ViewData data)
    {
        NewRetailProductEntryHead newRetailProductEntryHead = (NewRetailProductEntryHead) data.object;
        final NewRetailProduct newRetailProduct = NewRetailProduct.select(db, COLUMN_new_retail_product_entry_id + "=" + newRetailProductEntryHead.getId()).get(0);

        final ImageView image = (ImageView) data.view.findViewById(R.id.image);
        final ImageView action = (ImageView) data.view.findViewById(R.id.action);
        final LinearLayout layout_holder = (LinearLayout) data.view.findViewById(R.id.layout_holder);

        FontSettings.setTextFont((TextView) data.view.findViewById(R.id.sell_price),mContext,String.valueOf(newRetailProduct.getRetail_price()));
        FontSettings.setTextFont((TextView) data.view.findViewById(R.id.sku),mContext,String.valueOf(newRetailProductEntryHead.getSku()));
        try {
            String img = FileManagerSetting.new_retail_folder + "/" + newRetailProduct.getImages().split(";")[0];
            System.out.println(img);
            UIComponent.setImageView(mContext, image, img);
        }catch (Exception e){

        }
        action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater = (LayoutInflater) mContext
                        .getSystemService(LAYOUT_INFLATER_SERVICE);
                final View popupView = layoutInflater.inflate(R.layout.memo_pop_up, null);
                final PopupWindow popupWindow = new PopupWindow(popupView,LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                Button print  = FontSettings.setTextFont((Button) popupView.findViewById(R.id.print),mContext,db,"memopopup_print");
                Button delivery  = FontSettings.setTextFont((Button) popupView.findViewById(R.id.delivery),mContext,db,"memopopup_devilery");
                Button payment  = FontSettings.setTextFont((Button) popupView.findViewById(R.id.payment),mContext,db,"Edit");
                Button make_void  = FontSettings.setTextFont((Button) popupView.findViewById(R.id.make_void),mContext,db,"memopopup_void");

                delivery.setVisibility(View.GONE);
                print.setVisibility(View.GONE);


                payment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //popupWindow.dismiss();
                        Intent i = new Intent(mContext, AddProduct.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        i.putExtra("entry_id", String.valueOf(newRetailProduct.getEntry_id()));
                        mContext.startActivity(i);
                        popupWindow.dismiss();
                    }
                });

                make_void.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                        Intent i = new Intent(mContext, PosInvoicePrint.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        // i.putExtra("id", String.valueOf(posInvoiceHead.getId()));
                        // context.startActivity(i);
                    }
                });

                popupWindow.dismiss();
                popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        layout_holder.setBackgroundColor(Color.parseColor("#e2e2e2"));
                    }
                });
                // Closes the popup window when touch outside.
                popupWindow.setOutsideTouchable(true);
                popupWindow.setFocusable(true);
                // Removes default background.
                popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                popupWindow.showAsDropDown(action, 0, 0);
                layout_holder.setBackgroundColor(Color.parseColor("#72a8ff"));
            }
        });
    }
}
