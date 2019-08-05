package com.quickmas.salim.quickmasretail.Module.DataSync.DataDownload;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.quickmas.salim.quickmasretail.Database.DBInitialization;
import com.quickmas.salim.quickmasretail.Model.RTM.Invoice;
import com.quickmas.salim.quickmasretail.Model.RTM.Outlet;
import com.quickmas.salim.quickmasretail.Model.System.DashboardMenu;
import com.quickmas.salim.quickmasretail.Model.System.User;
import com.quickmas.salim.quickmasretail.Module.Dashboard.MainActivity;
import com.quickmas.salim.quickmasretail.R;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.quickmas.salim.quickmasretail.Utility.FileManagerSetting;
import com.quickmas.salim.quickmasretail.Utility.FontSettings;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;

import static com.quickmas.salim.quickmasretail.Model.System.DashboardMenu.selectByVarname;
import static com.quickmas.salim.quickmasretail.Service.ApiSettings.url_rtm_invoice_final_upload;
import static com.quickmas.salim.quickmasretail.Service.ApiSettings.url_rtm_invoice_upload;

public class DataUpload extends AppCompatActivity {

    DBInitialization db;
    User cSystemInfo;
    ArrayList<Invoice> invoices;
    int totalData=0;
    int uploadCompleted=0;
    int uploadFailed=0;
    TextView online_count_positive;
    TextView online_count_negative;
    TextView online_count_failed;
    TextView online_running_text;
    ProgressBar progressBar1;
    Button datadownload_backToHome;
    String final_upload="false";
    String data_closing = "false";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_data_upload);
        cSystemInfo = new User();
        db = new DBInitialization(this,null,null,1);
        cSystemInfo = cSystemInfo.select(db,"1=1");
        invoices = Invoice.select(db,db.COLUMN_i_status+"!=0");
        totalData = invoices.size();
        DashboardMenu cMenu =  selectByVarname(db,"dashboard_sync");
        ImageView header_menu_logo = (ImageView) findViewById(R.id.header_menu_logo);
        header_menu_logo.setImageBitmap(FileManagerSetting.getImageFromFile(cMenu.getImage(), this));

        TextView dataDownlaing_top_bar = (TextView) findViewById(R.id.dataDownlaing_top_bar);
        TextView online_count_text = (TextView) findViewById(R.id.online_count_text);
        online_running_text = (TextView) findViewById(R.id.online_running_text);
        online_count_positive = (TextView) findViewById(R.id.online_count_positive);
        online_count_negative = (TextView) findViewById(R.id.online_count_negative);
        online_count_failed = (TextView) findViewById(R.id.online_count_failed);
        datadownload_backToHome = (Button) findViewById(R.id.datadownload_backToHome);
        progressBar1 = (ProgressBar) findViewById(R.id.progressBar1);
        datadownload_backToHome.setVisibility(View.INVISIBLE);
        String fontPath = FontSettings.fontPath;
        Typeface tf;
        tf = Typeface.createFromAsset(this.getAssets(), fontPath);
        dataDownlaing_top_bar.setTypeface(tf);
        online_count_text.setTypeface(tf);
        online_running_text.setTypeface(tf);
        online_count_positive.setTypeface(tf);
        online_count_negative.setTypeface(tf);
        online_count_failed.setTypeface(tf);
        datadownload_backToHome.setTypeface(tf);


        Intent iin= getIntent();
        Bundle b = iin.getExtras();

        if(b!=null)
        {
            final_upload =(String) b.get("final_upload");
            data_closing=(String) b.get("data_closing");
        }

        online_count_text.setText(getText(R.string.dataUpload_online_count_text)+String.valueOf(totalData));

        datadownload_backToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DataUpload.this,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        if(totalData>0)
        {
            uploadInvoice(0);
        }
        else
        {
            Toast.makeText(getApplicationContext(), getText(R.string.datadownload_notData), Toast.LENGTH_LONG).show();
        }
    }

    void updateGUI()
    {
        String completed = getText(R.string.dataUpload_online_count_positive)+String.valueOf(uploadCompleted);
        String pending = getText(R.string.dataUpload_online_count_negative)+String.valueOf((totalData-uploadCompleted+uploadFailed));
        String imgFailed = getText(R.string.dataUpload_online_count_failed)+String.valueOf(uploadFailed);

        online_count_positive.setText(completed);
        online_count_negative.setText(pending);
        online_count_failed.setText(imgFailed);

    }
    void uploadInvoice(final int count)
    {
        {
            //final DbHandler db = new DbHandler(this,null,null,1);
            String username = cSystemInfo.getUser_name();
            String password = cSystemInfo.getPassword();
            String company = cSystemInfo.getCompany_id();

            String product_id =URLEncoder.encode(String.valueOf(invoices.get(count).getProduct_id()));
            final String invoice_id =URLEncoder.encode(String.valueOf(invoices.get(count).getInvoice_id()));

            Outlet cOutlet = Outlet.select(db,db.COLUMN_o_id+"="+String.valueOf(invoices.get(count).getOutlet_id())).get(0);
            String outlet_id =URLEncoder.encode(String.valueOf(cOutlet.getOutlet_id()));
            String quantity =URLEncoder.encode(String.valueOf(invoices.get(count).getQuantity()));
            String unit_price =URLEncoder.encode(String.valueOf(invoices.get(count).getUnit_price()));
            String invoice_date =URLEncoder.encode(String.valueOf(invoices.get(count).getInvoice_date()));
            String status =URLEncoder.encode(String.valueOf(invoices.get(count).getStatus()));
            final String url = url_rtm_invoice_upload+"user_name="+username+"&password="+password+"&product_id="+product_id   +"&invoice_id="+invoice_id+"&outlet_id="+outlet_id+"&quantity="+quantity+"&unit_price="+unit_price+"&invoice_date="+invoice_date+"&status="+status+"&final_upload="+final_upload+"&company="+company;
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        public void onResponse(String response) {
                            System.out.println("response: "+response);
                            try {
                                final JSONArray jr = new JSONArray("[" + response.toString() + "]");

                                for (int i = 0; i < jr.length(); i++)
                                {
                                    JSONObject jb = (JSONObject) jr.getJSONObject(i);
                                    int status = Integer.parseInt(jb.get("status").toString());
                                    int user_error = Integer.parseInt(jb.get("user_error").toString());
                                    String msg = jb.get("msg").toString();
                                    if(status==1)
                                    {
                                        uploadCompleted++;
                                    }
                                    else
                                    {
                                        uploadFailed++;
                                    }
                                }
                            }catch (Exception e)
                            {
                                uploadFailed++;
                            }
                            if(count+1>=invoices.size())
                            {
                                if(final_upload.toUpperCase().equals("TRUE") && uploadFailed==0)
                                {

                                    if(data_closing.toUpperCase().equals("TRUE"))
                                    {

                                        finalUpload();
                                    }
                                    else
                                    {
                                        //db.deleteData(db.TABLE_Invoice,"1");
                                        //db.deleteData(db.TABLE_OUTLET,"1");
                                        //db.deleteData(db.TABLE_PRODUCT,"1");

                                        String completed = getText(R.string.dataUpload_online_count_positive)+String.valueOf(uploadCompleted);
                                        String pending = getText(R.string.dataUpload_online_count_negative)+String.valueOf((totalData-(uploadCompleted+uploadFailed)));
                                        String imgFailed = getText(R.string.dataUpload_online_count_failed)+String.valueOf(uploadFailed);

                                        online_count_positive.setText(completed);
                                        online_count_negative.setText(pending);
                                        online_count_failed.setText(imgFailed);

                                        online_running_text.setText(R.string.dataUpload_online_sucess);
                                        progressBar1.setVisibility(View.GONE);
                                        datadownload_backToHome.setVisibility(View.VISIBLE);
                                        Toast.makeText(getApplicationContext(), getText(R.string.dataUpload_online_sucess), Toast.LENGTH_LONG).show();
                                    }
                                }
                                else
                                {
                                    String completed = getText(R.string.dataUpload_online_count_positive)+String.valueOf(uploadCompleted);
                                    String pending = getText(R.string.dataUpload_online_count_negative)+String.valueOf((totalData-(uploadCompleted+uploadFailed)));
                                    String imgFailed = getText(R.string.dataUpload_online_count_failed)+String.valueOf(uploadFailed);

                                    online_count_positive.setText(completed);
                                    online_count_negative.setText(pending);
                                    online_count_failed.setText(imgFailed);

                                    online_running_text.setText(R.string.dataUpload_online_sucess);
                                    progressBar1.setVisibility(View.GONE);
                                    datadownload_backToHome.setVisibility(View.VISIBLE);
                                    Toast.makeText(getApplicationContext(), getText(R.string.dataUpload_online_sucess), Toast.LENGTH_LONG).show();
                                }
                            }
                            else
                            {
                                updateGUI();
                                uploadInvoice(count+1);
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    uploadFailed++;
                    updateGUI();
                    Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                }
            });
            queue.add(stringRequest);
        }
    }

    void finalUpload()
    {
        String username = cSystemInfo.getUser_name();
        String password = cSystemInfo.getPassword();
        String company = cSystemInfo.getCompany_id();
        final String url = url_rtm_invoice_final_upload+"user_name="+username+"&password="+password+"&company="+company;
        System.out.println(url);
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    public void onResponse(String response) {
                        System.out.println(response);
                        try {
                            final JSONArray jr = new JSONArray("[" + response.toString() + "]");

                            for (int i = 0; i < jr.length(); i++)
                            {
                                JSONObject jb = (JSONObject) jr.getJSONObject(i);
                                int status = Integer.parseInt(jb.get("status").toString());
                                int user_error = Integer.parseInt(jb.get("user_error").toString());
                                String msg = jb.get("msg").toString();
                                if(status==1)
                                {
                                    db.deleteData(db.TABLE_Invoice,"1");
                                    db.deleteData(db.TABLE_OUTLET,"1");
                                    db.deleteData(db.TABLE_PRODUCT,"1");

                                    String completed = getText(R.string.dataUpload_online_count_positive)+String.valueOf(uploadCompleted);
                                    String pending = getText(R.string.dataUpload_online_count_negative)+String.valueOf((totalData-(uploadCompleted+uploadFailed)));
                                    String imgFailed = getText(R.string.dataUpload_online_count_failed)+String.valueOf(uploadFailed);

                                    online_count_positive.setText(completed);
                                    online_count_negative.setText(pending);
                                    online_count_failed.setText(imgFailed);

                                    online_running_text.setText(R.string.dataUpload_online_sucess);
                                    progressBar1.setVisibility(View.GONE);
                                    datadownload_backToHome.setVisibility(View.VISIBLE);
                                    Toast.makeText(getApplicationContext(), getText(R.string.dataUpload_online_sucess), Toast.LENGTH_LONG).show();
                                }
                                else
                                {
                                    String completed = getText(R.string.dataUpload_online_count_positive)+String.valueOf(uploadCompleted);
                                    String pending = getText(R.string.dataUpload_online_count_negative)+String.valueOf((totalData-(uploadCompleted+uploadFailed)));
                                    String imgFailed = getText(R.string.dataUpload_online_count_failed)+String.valueOf(uploadFailed);

                                    online_count_positive.setText(completed);
                                    online_count_negative.setText(pending);
                                    online_count_failed.setText(imgFailed);

                                    online_running_text.setText(R.string.dataUpload_online_sucess);
                                    progressBar1.setVisibility(View.GONE);
                                    datadownload_backToHome.setVisibility(View.VISIBLE);
                                    Toast.makeText(getApplicationContext(), getText(R.string.dataUpload_online_failed), Toast.LENGTH_LONG).show();

                                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                                }
                            }
                        }catch (Exception e)
                        {
                            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        });
        queue.add(stringRequest);

    }
}
