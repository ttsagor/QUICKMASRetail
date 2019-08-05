package com.quickmas.salim.quickmasretail.Module.StockTransfer.RecevieStcokTransfer;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.quickmas.salim.quickmasretail.Database.DBInitialization;
import com.quickmas.salim.quickmasretail.Model.POS.PosInvoice;
import com.quickmas.salim.quickmasretail.Model.POS.StockTransferDetails;
import com.quickmas.salim.quickmasretail.Model.POS.StockTransferHead;
import com.quickmas.salim.quickmasretail.Model.System.TextString;
import com.quickmas.salim.quickmasretail.Model.System.User;
import com.quickmas.salim.quickmasretail.Module.AddProductRetail.AddProduct.AddProduct;
import com.quickmas.salim.quickmasretail.Module.AddProductRetail.ProductList.ProductList;
import com.quickmas.salim.quickmasretail.R;
import com.quickmas.salim.quickmasretail.Service.UploadData;
import com.quickmas.salim.quickmasretail.Structure.GoodsTransferAPI;
import com.quickmas.salim.quickmasretail.Utility.DebugHelper;
import com.quickmas.salim.quickmasretail.Utility.FontSettings;
import com.quickmas.salim.quickmasretail.Utility.TypeConvertion;
import com.quickmas.salim.quickmasretail.Utility.UI.ScrollListView;
import com.quickmas.salim.quickmasretail.Utility.UI.ViewData;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;
import es.dmoral.toasty.Toasty;

import static com.quickmas.salim.quickmasretail.Service.ApiSettings.getUserDetailsPar;
import static com.quickmas.salim.quickmasretail.Service.ApiSettings.url_stock_transfer_upload;
import static com.quickmas.salim.quickmasretail.Utility.UIComponent.checkIfInternetOn;
import static com.quickmas.salim.quickmasretail.Utility.UIComponent.hideSoftKeyboard;
import static com.quickmas.salim.quickmasretail.Utility.UIComponent.spinnerDataLoad;

public class ReceiveStock extends AppCompatActivity {
    DBInitialization db;
    Context mContext;
    String id="";
    ArrayList<StockTransferDetails> newStock = new ArrayList<>();
    ProgressDialog progressDoalog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_receive_stock);
        db = new DBInitialization(this,null,null,1);
        mContext = this;
        Intent iin= getIntent();
        Bundle b = iin.getExtras();
        if(b!=null)
        {
            id =(String) b.get("id");
        }
        final ArrayList<StockTransferDetails> posInvoice = StockTransferDetails.select(db,db.COLUMN_stock_transfer_details_transfer_id+"="+id);
        final TextView transfer_from = (TextView) findViewById(R.id.transfer_from);
        try {
            FontSettings.setTextFont((TextView) findViewById(R.id.transfer_from), mContext, StockTransferHead.select(db, db.COLUMN_stock_transfer_id + "=" + posInvoice.get(0).transfer_id).get(0).getTransfer_by());
        }catch (Exception E){}

        final ListView memo_list = (ListView) findViewById(R.id.product_list);
        ScrollListView.loadListView(mContext, memo_list, R.layout.adaptar_exchange_product_list, posInvoice, "dataShow", 0, 100, true);

       Button exchange_btn =  FontSettings.setTextFont((Button) findViewById(R.id.exchange_btn),mContext,db,"Receive");

        exchange_btn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if(checkIfInternetOn(mContext))
                {

                    new SweetAlertDialog(mContext, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText(TextString.textSelectByVarname(db,"pos_sale_payment_popbox_confermation").getText())
                            .setContentText(TextString.textSelectByVarname(db,"pos_sale_payment_popbox_confermation").getText())
                            .setConfirmText(TextString.textSelectByVarname(db,"pos_sale_payment_popbox_confermation_yes").getText())
                            .setCancelText(TextString.textSelectByVarname(db,"pos_sale_payment_popbox_confermation_no").getText())
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog ssDialog) {
                                    ssDialog.dismiss();
                                    progressDoalog = new ProgressDialog(mContext);
                                    try {
                                        progressDoalog.setMessage("Product Creating....");
                                        progressDoalog.setTitle("Please Wait");
                                        progressDoalog.show();

                                    }catch (Exception e){

                                    }
                                    final ArrayList<GoodsTransferAPI> p = new ArrayList<>();
                                    new Thread(new Runnable() {
                                        public void run() {
                                            for (StockTransferDetails ns : newStock) {
                                                StockTransferDetails stockTransferDetails = StockTransferDetails.select(db, db.COLUMN_stock_transfer_details_id + "=" + ns.getId()).get(0);
                                                stockTransferDetails.setReceived_qty(stockTransferDetails.getReceived_qty() + ns.getReceived_qty());
                                                if(stockTransferDetails.getReceived_qty()>=stockTransferDetails.getQuantity())
                                                {
                                                    stockTransferDetails.setStatus(1);
                                                    StockTransferHead cs = StockTransferHead.select(db, db.COLUMN_stock_transfer_id + "='" + stockTransferDetails.transfer_id + "'").get(0);
                                                    cs.setStatus(1);
                                                    cs.update(db);
                                                }
                                                stockTransferDetails.update(db);
                                            }
                                            ArrayList<StockTransferDetails> uplist = StockTransferDetails.select(db,db.COLUMN_stock_transfer_details_transfer_id+"="+id);
                                            for(StockTransferDetails cc : uplist)
                                            {
                                                try {
                                                    StockTransferHead cs = StockTransferHead.select(db, db.COLUMN_stock_transfer_id + "='" + cc.transfer_id + "'").get(0);
                                                    GoodsTransferAPI pp = new GoodsTransferAPI(cs, cc);
                                                    p.add(pp);
                                                }catch (Exception e){}
                                            }
                                            transfer_from.post(new Runnable() {
                                                public void run() {
                                                    UploadData uploadData = new UploadData();
                                                    uploadData.data = p;
                                                    User cSystemInfo = new User();
                                                    cSystemInfo.select(db,"1=1");
                                                    uploadData.url=url_stock_transfer_upload + getUserDetailsPar(cSystemInfo);
                                                    uploadData.uploaddata(mContext,mContext,"",mContext,"uploadData");

                                                }
                                            });
                                        }
                                    }).start();

                                }
                            })
                            .setCancelButton(TextString.textSelectByVarname(db,"pos_sale_payment_popbox_confermation_no").getText(), new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
                                }
                            })
                            .show();

                }
            }
        });

    }

    public void uploadData(ArrayList<String> data)
    {
        hideSoftKeyboard(ReceiveStock.this);
        progressDoalog.dismiss();
        SweetAlertDialog sDialog = new SweetAlertDialog(mContext, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("Successful")
                .setContentText(TextString.textSelectByVarname(db,"pos_sale_payment_popbox_invoice_complete").getText())
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                        Toasty.success(getApplicationContext(), TextString.textSelectByVarname(db, "Product Receive Sucessful").getText(), Toast.LENGTH_LONG).show();
                        finish();
                    }
                });
        sDialog.setCancelable(false);
        sDialog.show();
    }

    public void dataShow(final ViewData data)
    {
        final StockTransferDetails posInvoice = (StockTransferDetails) data.object;
        if(newStock.contains(posInvoice))
        {
            newStock.remove(posInvoice);
        }
        newStock.add(data.position,posInvoice);

        FontSettings.setTextFont((TextView) data.view.findViewById(R.id.product_name),mContext,posInvoice.getProduct_name());
        FontSettings.setTextFont((TextView) data.view.findViewById(R.id.product_quantity),mContext,String.valueOf(posInvoice.getDelivered_qty() - posInvoice.getReceived_qty()));

        final Spinner exchange_quantity = (Spinner) data.view.findViewById(R.id.exchange_quantity);
        final LinearLayout layout_holder = (LinearLayout) data.view.findViewById(R.id.layout_holder);

        ArrayList<String> qty = new ArrayList<>();
        for(int i=0;i<=posInvoice.getDelivered_qty()- posInvoice.getReceived_qty();i++)
        {
            qty.add(String.valueOf(i));
        }
        exchange_quantity.setAdapter(spinnerDataLoad(mContext,qty));
        exchange_quantity.setSelection(qty.size()-1);



        exchange_quantity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                posInvoice.setReceived_qty(position);
                newStock.set(data.position,posInvoice);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
    }
}
