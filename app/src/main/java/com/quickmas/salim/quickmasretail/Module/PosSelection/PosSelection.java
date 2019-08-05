package com.quickmas.salim.quickmasretail.Module.PosSelection;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.quickmas.salim.quickmasretail.Database.DBInitialization;
import com.quickmas.salim.quickmasretail.Model.POS.CashStatment;
import com.quickmas.salim.quickmasretail.Model.POS.PosProduct;
import com.quickmas.salim.quickmasretail.Model.POS.StockTransferDetails;
import com.quickmas.salim.quickmasretail.Model.POS.StockTransferHead;
import com.quickmas.salim.quickmasretail.Model.System.TextString;
import com.quickmas.salim.quickmasretail.Model.System.User;
import com.quickmas.salim.quickmasretail.Module.Loading.Loading;
import com.quickmas.salim.quickmasretail.R;
import com.quickmas.salim.quickmasretail.Service.BaseDataService;
import com.quickmas.salim.quickmasretail.Service.UploadData;
import com.quickmas.salim.quickmasretail.Structure.GoodsTransferAPI;
import com.quickmas.salim.quickmasretail.Structure.PhotoPathTarDir;
import com.quickmas.salim.quickmasretail.Utility.DebugHelper;
import com.quickmas.salim.quickmasretail.Utility.FileManagerSetting;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import cn.pedant.SweetAlert.SweetAlertDialog;
import es.dmoral.toasty.Toasty;

import static com.quickmas.salim.quickmasretail.Module.Login.Login.pos_info;
import static com.quickmas.salim.quickmasretail.Service.ApiSettings.getUserDetailsPar;
import static com.quickmas.salim.quickmasretail.Service.ApiSettings.url_active_pos_check;
import static com.quickmas.salim.quickmasretail.Service.ApiSettings.url_active_pos_check_update;
import static com.quickmas.salim.quickmasretail.Service.ApiSettings.url_cash_statment_download;
import static com.quickmas.salim.quickmasretail.Service.ApiSettings.url_dataSync_download_brand;
import static com.quickmas.salim.quickmasretail.Service.ApiSettings.url_dataSync_download_date_from;
import static com.quickmas.salim.quickmasretail.Service.ApiSettings.url_dataSync_download_date_to;
import static com.quickmas.salim.quickmasretail.Service.ApiSettings.url_dataSync_download_dimensions;
import static com.quickmas.salim.quickmasretail.Service.ApiSettings.url_dataSync_download_discount;
import static com.quickmas.salim.quickmasretail.Service.ApiSettings.url_dataSync_download_discount_type;
import static com.quickmas.salim.quickmasretail.Service.ApiSettings.url_dataSync_download_features;
import static com.quickmas.salim.quickmasretail.Service.ApiSettings.url_dataSync_download_guarantee;
import static com.quickmas.salim.quickmasretail.Service.ApiSettings.url_dataSync_download_hour_from;
import static com.quickmas.salim.quickmasretail.Service.ApiSettings.url_dataSync_download_hour_to;
import static com.quickmas.salim.quickmasretail.Service.ApiSettings.url_dataSync_download_id;
import static com.quickmas.salim.quickmasretail.Service.ApiSettings.url_dataSync_download_includes;
import static com.quickmas.salim.quickmasretail.Service.ApiSettings.url_dataSync_download_location;
import static com.quickmas.salim.quickmasretail.Service.ApiSettings.url_dataSync_download_location_balance;
import static com.quickmas.salim.quickmasretail.Service.ApiSettings.url_dataSync_download_photo;
import static com.quickmas.salim.quickmasretail.Service.ApiSettings.url_dataSync_download_price;
import static com.quickmas.salim.quickmasretail.Service.ApiSettings.url_dataSync_download_product_color;
import static com.quickmas.salim.quickmasretail.Service.ApiSettings.url_dataSync_download_product_name;
import static com.quickmas.salim.quickmasretail.Service.ApiSettings.url_dataSync_download_product_title;
import static com.quickmas.salim.quickmasretail.Service.ApiSettings.url_dataSync_download_sub_location;
import static com.quickmas.salim.quickmasretail.Service.ApiSettings.url_dataSync_download_sub_location_balance;
import static com.quickmas.salim.quickmasretail.Service.ApiSettings.url_dataSync_download_sub_type;
import static com.quickmas.salim.quickmasretail.Service.ApiSettings.url_dataSync_download_tax;
import static com.quickmas.salim.quickmasretail.Service.ApiSettings.url_dataSync_download_type;
import static com.quickmas.salim.quickmasretail.Service.ApiSettings.url_dataSync_download_weight;
import static com.quickmas.salim.quickmasretail.Service.ApiSettings.url_dataSync_download_wholesale;
import static com.quickmas.salim.quickmasretail.Service.ApiSettings.url_dataSync_quantity_left;
import static com.quickmas.salim.quickmasretail.Service.ApiSettings.url_dataSync_status;
import static com.quickmas.salim.quickmasretail.Service.ApiSettings.url_stock_transfer_upload;
import static com.quickmas.salim.quickmasretail.Utility.FileManagerSetting.getImageFromFile;
import static com.quickmas.salim.quickmasretail.Utility.UIComponent.SpinnerGetIndex;
import static com.quickmas.salim.quickmasretail.Utility.UIComponent.checkIfInternetOn;
import static com.quickmas.salim.quickmasretail.Utility.UIComponent.spinnerDataLoad;

public class PosSelection extends AppCompatActivity {
    ProgressDialog progressDoalog;
    Context mContext;
    DBInitialization db;
    User cSystemInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_pos_selection);
        mContext = this;
        //final LinearLayout dashboard_menu_holder = (LinearLayout) findViewById(R.id.dashboard_menu_holder);
        db = new DBInitialization(this,null,null,1);
        final ImageView app_logo = (ImageView) findViewById(R.id.app_logo);
        final LinearLayout app_logo_holder = (LinearLayout) findViewById(R.id.app_logo_holder);
        if(getImageFromFile(getApplicationContext(),FileManagerSetting.app_logo)!=null)
        {
            app_logo.setImageBitmap(getImageFromFile(FileManagerSetting.app_logo,this));
        }
        else
        {
            User cSystemInfo = new User();
            cSystemInfo.select(db,"1=1");
            app_logo.setVisibility(View.GONE);
            TextView valueTV = new TextView(this);
            valueTV.setText(cSystemInfo.getCompany_name());
            valueTV.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
            valueTV.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            app_logo_holder.addView(valueTV);
        }
        final ArrayList<PhotoPathTarDir> posList = new ArrayList<>();
        cSystemInfo = new User();
        cSystemInfo.select(db,"1=1");
        Button pos_submit = (Button) findViewById(R.id.pos_submit);
        try {
            final JSONArray jr = new JSONArray(pos_info.toString());
            for (int i = 0; i < jr.length(); i++) {
                JSONObject jb = (JSONObject) jr.getJSONObject(i);
                PhotoPathTarDir p = new PhotoPathTarDir();
                p.setUrl(jb.get("name").toString());
                p.setTarget(jb.get("warehouse").toString());
                posList.add(p);
            }
        }catch (Exception e){}
        final Spinner location = (Spinner) findViewById(R.id.location_spinner);
        final Spinner pos_spinner = (Spinner) findViewById(R.id.pos_spinner);
        try {
            location.setAdapter(spinnerDataLoad(this, getLocation(posList, cSystemInfo)));
        }catch (Exception e){System.out.println(e.getMessage());}
        location.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                pos_spinner.setAdapter(spinnerDataLoad(mContext,getPos (posList,location.getSelectedItem().toString(),cSystemInfo)));
                if(cSystemInfo.getSelected_pos()!=null)
                {
                    pos_spinner.setSelection(SpinnerGetIndex(pos_spinner,cSystemInfo.getSelected_pos()));
                    pos_spinner.setEnabled(false);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        if(cSystemInfo.getSelected_location()!=null && cSystemInfo.getSelected_pos()!=null)
        {
            location.setSelection(SpinnerGetIndex(location,cSystemInfo.getSelected_location()));
            location.setEnabled(false);
        }

        pos_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if(location.getSelectedItemPosition()>0 && pos_spinner.getSelectedItemPosition()>0) {
                        if (checkIfInternetOn(mContext)) {
                            cSystemInfo.setSelected_location(location.getSelectedItem().toString());
                            cSystemInfo.setSelected_pos(pos_spinner.getSelectedItem().toString());
                            cSystemInfo.setPrint_message(getSelectedBrachMsg(cSystemInfo.getLocation(), cSystemInfo.getSelected_location()));
                            cSystemInfo.update(db);

                            BaseDataService m = new BaseDataService();
                            m.url = url_active_pos_check;
                            m.outputToModel = new CashStatment();
                            m.context = mContext;
                            m.parameterdataClass = cSystemInfo;
                            m.getDataFromURLString(mContext, "posActiveCheck");
                            progressDoalog = new ProgressDialog(mContext);
                            try {
                                progressDoalog.setMessage("Product Creating....");
                                progressDoalog.setTitle("Please Wait");
                                progressDoalog.show();

                            } catch (Exception e) {

                            }

                        } else {
                            Toasty.error(getApplicationContext(), TextString.textSelectByVarname(db, "Please Select Warehouse and POS").getText(), Toast.LENGTH_SHORT, true).show();

                        }
                    }
                }
            });
       // cSystemInfo.update(db);
        /*Intent intent = new Intent(Login.this,
                                          Loading.class);
                                  intent.putExtra("firstLogin","true");
                                  intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                  startActivity(intent);
                                  */
    }

    public void posActiveCheck(String data)
    {
        String status="0";
        String closing_amount="0.0";
        String msg="";
        progressDoalog.dismiss();
        try {
            final JSONArray jr = new JSONArray("["+data.toString()+"]");
            for (int i = 0; i < jr.length(); i++) {
                try{
                    PosProduct cProduct = new PosProduct();
                    JSONObject jb = (JSONObject) jr.getJSONObject(i);
                    status = jb.get("status").toString();
                    if(status.equals("1"))
                    {
                        closing_amount = jb.get("closing_amount").toString();
                    }
                    else if(status.equals("2"))
                    {
                        msg = jb.get("msg").toString();
                    }

                }catch (Exception e){}
            }
        }catch (Exception e){

        }

        if(status.equals("1"))
        {
            new SweetAlertDialog(mContext, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText(TextString.textSelectByVarname(db,"Cash in hand").getText())
                    .setContentText(TextString.textSelectByVarname(db,cSystemInfo.getSelected_pos()+" has balance \n"+closing_amount+".\nDo you want to accept it.").getText())
                    .setConfirmText(TextString.textSelectByVarname(db,"Yes").getText())
                    .setCancelText(TextString.textSelectByVarname(db,"No").getText())
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog ssDialog) {
                            ssDialog.dismissWithAnimation();
                            if (checkIfInternetOn(mContext))
                            {
                                BaseDataService m = new BaseDataService();
                                m.url = url_active_pos_check_update;
                                m.outputToModel = new CashStatment();
                                m.context = mContext;
                                m.parameterdataClass = cSystemInfo;
                                m.getDataFromURLString(mContext, "updatepos");
                                progressDoalog = new ProgressDialog(mContext);
                                try {
                                    progressDoalog.setMessage("Product Creating....");
                                    progressDoalog.setTitle("Please Wait");
                                    progressDoalog.show();

                                } catch (Exception e) {

                                }
                            }
                        }
                    })
                    .setCancelButton(TextString.textSelectByVarname(db,"No").getText(), new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.dismissWithAnimation();
                        }
                    })
                    .show();
        }
        else
        {
            SweetAlertDialog sDialog = new SweetAlertDialog(mContext, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Login Failed")
                    .setContentText(TextString.textSelectByVarname(db,msg).getText())
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.dismissWithAnimation();
                        }
                    });
            sDialog.setCancelable(false);
            sDialog.show();
        }
    }

    public void updatepos(String data)
    {
        progressDoalog.dismiss();
        Intent intent = new Intent(PosSelection.this,
                Loading.class);
        intent.putExtra("firstLogin","true");
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();
    }

    String getSelectedBrachMsg(String location,String selectedLoc)
    {
        String output ="";
        String[] loc =  location.split(",");
        for(String l : loc)
        {
            try
            {
                if(l.split(";")[0].equals(selectedLoc))
                {
                    output =  l.split(";")[1];
                }
            }
            catch (Exception e){}
        }
        return output;
    }
    ArrayList<String> getSplitedLocation(String location)
    {
        ArrayList<String> output = new ArrayList<>();
        String[] loc =  location.split(",");
        for(String l : loc)
        {
            try {
                output.add(l.split(";")[0]);
            }catch (Exception e){}
        }
        return output;
    }

    ArrayList<String> getLocation (ArrayList<PhotoPathTarDir> path,User cSystemInfo)
    {
        ArrayList<String> locs = new ArrayList<>();
        locs.add(0,"Select Location");
        ArrayList<String> userLocList = new ArrayList<String>(getSplitedLocation(cSystemInfo.getLocation()));
        for(PhotoPathTarDir loc : path)
        {
            if(!locs.contains(loc.getTarget()) && userLocList.contains(loc.getTarget()))
            {
                locs.add(loc.getTarget());
            }
        }
        return locs;
    }
    ArrayList<String> getPos (ArrayList<PhotoPathTarDir> path,String selectedLocation,User cSystemInfo) {
        ArrayList<String> locs = new ArrayList<>();
        locs.add(0,"Select POS");
        //ArrayList<String> userPosList = new ArrayList<String>(Arrays.asList(cSystemInfo.getPos().split(",")));
        for(PhotoPathTarDir loc : path)
        {
            if(loc.getTarget().equals(selectedLocation) && !locs.contains(loc.getUrl()))
            {
                locs.add(loc.getUrl());
            }
        }
        return locs;
    }
}
