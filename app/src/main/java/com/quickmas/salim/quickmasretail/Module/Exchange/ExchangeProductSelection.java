package com.quickmas.salim.quickmasretail.Module.Exchange;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
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
import com.quickmas.salim.quickmasretail.Model.POS.CashPaymentReceive;
import com.quickmas.salim.quickmasretail.Model.POS.PosInvoice;
import com.quickmas.salim.quickmasretail.Model.POS.PosProduct;
import com.quickmas.salim.quickmasretail.Model.System.TextString;
import com.quickmas.salim.quickmasretail.Module.POS.PosInvoicePrint.*;
import com.quickmas.salim.quickmasretail.Module.POS.PosSell.SelectProduct.PosSelectProduct;
import com.quickmas.salim.quickmasretail.R;
import com.quickmas.salim.quickmasretail.Structure.ExchangeProductQuantity;
import com.quickmas.salim.quickmasretail.Utility.DebugHelper;
import com.quickmas.salim.quickmasretail.Utility.FontSettings;
import com.quickmas.salim.quickmasretail.Utility.TypeConvertion;
import com.quickmas.salim.quickmasretail.Utility.UI.ScrollListView;
import com.quickmas.salim.quickmasretail.Utility.UI.ViewData;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

import static com.quickmas.salim.quickmasretail.Model.POS.PosInvoice.updateInvoiceStatus;
import static com.quickmas.salim.quickmasretail.Structure.ExchangeProductQuantity.getExchangeCount;
import static com.quickmas.salim.quickmasretail.Structure.ExchangeProductQuantity.getExchangeTotalAmount;
import static com.quickmas.salim.quickmasretail.Utility.FontSettings.setTextFont;

public class ExchangeProductSelection extends AppCompatActivity {
    DBInitialization db;
    String id="";
    Context mContext;
    TextView total_exchange;
    TextView total_amount;
    public static ArrayList<ExchangeProductQuantity> exchangeProductQuantities = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("exchange-update-message"));
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_exchange_product_selection);
        db = new DBInitialization(this,null,null,1);
        mContext = this;
        Intent iin= getIntent();
        Bundle b = iin.getExtras();
        if(b!=null)
        {
            id =(String) b.get("id");
        }

        final ArrayList<PosInvoice> posInvoice = PosInvoice.select(db,db.COLUMN_pos_invoice_invoiceID+"="+id);

        if(posInvoice.size()>0)
        {
            final ListView memo_list = (ListView) findViewById(R.id.product_list);

            LayoutInflater layoutinflater = getLayoutInflater();

            ViewGroup header = (ViewGroup)layoutinflater.inflate(R.layout.adaptar_exchange_product_list,memo_list,false);

            TextView invoice_id_totalHead = setTextFont((TextView) header.findViewById(R.id.product_name),this,"SKU");
            TextView quantity_totalHead = setTextFont((TextView) header.findViewById(R.id.product_quantity),this,"Quantity");
            TextView spinner_title = setTextFont((TextView) header.findViewById(R.id.spinner_title),this,"Exchange");
            Spinner exchange_quantity = (Spinner) header.findViewById(R.id.exchange_quantity);
            exchange_quantity.setVisibility(View.GONE);

            exchangeProductQuantities = new ArrayList<>();
            int quantity=0;
            for(PosInvoice cProduct : posInvoice)
            {
                ExchangeProductQuantity exchangeProductQuantity = new ExchangeProductQuantity();
                exchangeProductQuantity.posInvoice = cProduct;
                exchangeProductQuantity.name = cProduct.getProductName();
                exchangeProductQuantity.quantity = cProduct.getQuantity();
                exchangeProductQuantity.exchange = 0;
                exchangeProductQuantity.amount = 0.0;
                exchangeProductQuantities.add(exchangeProductQuantity);
                quantity+=cProduct.getQuantity();
            }

                ScrollListView.loadListView(mContext, memo_list, R.layout.adaptar_exchange_product_list, posInvoice, "dataShow", 0, 100, true);

            final Button exchange_btn = (Button) findViewById(R.id.exchange_btn);
            TextView total_quantity = FontSettings.setTextFont ((TextView) findViewById(R.id.total_quantity),this,String.valueOf(quantity));
            total_exchange = FontSettings.setTextFont ((TextView) findViewById(R.id.total_exchange),this,String.valueOf(0));
            total_amount = FontSettings.setTextFont ((TextView) findViewById(R.id.total_amount),this,String.valueOf(0.0));

            exchange_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(getExchangeCount(exchangeProductQuantities)>0)
                    {
                        updateInvoiceStatus(db,"1");
                        Intent i = new Intent(mContext, PosSelectProduct.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        i.putExtra("id", String.valueOf(id));
                        //i.putExtra("data", exchangeProductQuantities);
                        startActivity(i);
                    }
                    else
                    {
                        Toasty.error(getApplicationContext(), TextString.textSelectByVarname(db,"Please Select at least one Exchange Product").getText(), Toast.LENGTH_SHORT, true).show();
                    }
                }
            });
        }

    }
    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            total_exchange.setText(String.valueOf(String.valueOf(getExchangeCount(exchangeProductQuantities))));
            total_amount.setText(String.valueOf(String.valueOf(getExchangeTotalAmount(exchangeProductQuantities))));
        }
    };

    public void dataShow(final ViewData data)
    {
        final PosInvoice posInvoice = (PosInvoice) data.object;

        FontSettings.setTextFont((TextView) data.view.findViewById(R.id.product_name),mContext,posInvoice.getProductName());

        final Spinner exchange_quantity = (Spinner) data.view.findViewById(R.id.exchange_quantity);
        final LinearLayout layout_holder = (LinearLayout) data.view.findViewById(R.id.layout_holder);


        layout_holder.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                layout_holder.setBackgroundColor(Color.parseColor("#ffffff"));
                return false;
            }
        });

        ArrayList<String> exchangeList = new ArrayList<>();
        for(int i=0;i<=posInvoice.getQuantity() - posInvoice.getExchange();i++)
        {
            exchangeList.add(String.valueOf(i));
        }

        ArrayAdapter<String> adapter=new ArrayAdapter<String>(
                mContext, android.R.layout.simple_spinner_dropdown_item, exchangeList){

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view =super.getView(position, convertView, parent);
                TextView textView=(TextView) view.findViewById(android.R.id.text1);
                textView.setTextColor(Color.BLUE);
                return view;
            }
        };

        exchange_quantity.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                layout_holder.setBackgroundColor(Color.parseColor("#72a8ff"));
                return false;
            }
        });


        final String name = posInvoice.getProductName();
        exchange_quantity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position1, long id) {
                layout_holder.setBackgroundColor(Color.parseColor("#ffffff"));
                updateElement(name, TypeConvertion.parseInt(exchange_quantity.getSelectedItem().toString()),posInvoice);
                Intent intent = new Intent("exchange-update-message");
                LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                layout_holder.setBackgroundColor(Color.parseColor("#ffffff"));
            }

        });

        exchange_quantity.setAdapter(adapter);

        if(posInvoice.getExchange()>0) {
            String o = String.valueOf(posInvoice.getQuantity())+" (-"+String.valueOf(posInvoice.getExchange())+")";
            FontSettings.setTextFont ((TextView) data.view.findViewById(R.id.product_quantity),mContext,o);
        }
        else
        {
            FontSettings.setTextFont ((TextView) data.view.findViewById(R.id.product_quantity),mContext,String.valueOf(posInvoice.getQuantity()));
        }
    }

    public void updateElement(String name, int exchange,PosInvoice posInvoice)
    {
        for(int i=0;i<exchangeProductQuantities.size();i++)
        {
            if(exchangeProductQuantities.get(i).name.equals(name))
            {
                exchangeProductQuantities.get(i).exchange = exchange;
                PosProduct posProduct = PosProduct.select(db,db.COLUMN_pos_product_id+"="+posInvoice.getProductId()).get(0);
                exchangeProductQuantities.get(i).amount = (exchangeProductQuantities.get(i).exchange * posInvoice.getUnitPrice()) - PosProduct.calDis(posProduct,exchangeProductQuantities.get(i).exchange) +PosProduct.calTax(posProduct,exchangeProductQuantities.get(i).exchange);
                break;
            }
        }
    }
}
