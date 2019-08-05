package com.quickmas.salim.quickmasretail.Module.DataSync.DataDownload;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.quickmas.salim.quickmasretail.Module.Dashboard.MainActivity;
import com.quickmas.salim.quickmasretail.Database.DBInitialization;
import com.quickmas.salim.quickmasretail.Model.System.DashboardMenu;
import com.quickmas.salim.quickmasretail.Model.RTM.Outlet;
import com.quickmas.salim.quickmasretail.Model.RTM.Product;
import com.quickmas.salim.quickmasretail.Model.System.User;
import com.quickmas.salim.quickmasretail.R;
import com.quickmas.salim.quickmasretail.Service.ApiSettings;
import com.quickmas.salim.quickmasretail.Service.DataDownload;
import com.quickmas.salim.quickmasretail.Service.DataloadImage;
import com.quickmas.salim.quickmasretail.Structure.PhotoPathTarDir;
import com.quickmas.salim.quickmasretail.Utility.CallBack;
import com.quickmas.salim.quickmasretail.Utility.DebugHelper;
import com.quickmas.salim.quickmasretail.Utility.FileManagerSetting;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.quickmas.salim.quickmasretail.Module.DataSync.DataDownload.PendingList.pendingProductList;
import static com.quickmas.salim.quickmasretail.Model.System.DashboardMenu.selectByVarname;
import static com.quickmas.salim.quickmasretail.Model.System.TextString.textSelectByVarname;
import static com.quickmas.salim.quickmasretail.Service.ApiSettings.url_outlook;
import static com.quickmas.salim.quickmasretail.Service.ApiSettings.url_outlook_address;
import static com.quickmas.salim.quickmasretail.Service.ApiSettings.url_outlook_cluster;
import static com.quickmas.salim.quickmasretail.Service.ApiSettings.url_outlook_outlet_for;
import static com.quickmas.salim.quickmasretail.Service.ApiSettings.url_outlook_outlet_id;
import static com.quickmas.salim.quickmasretail.Service.ApiSettings.url_outlook_outlet_type;
import static com.quickmas.salim.quickmasretail.Service.ApiSettings.url_outlook_outletname;
import static com.quickmas.salim.quickmasretail.Service.ApiSettings.url_outlook_owner;
import static com.quickmas.salim.quickmasretail.Service.ApiSettings.url_outlook_phone;
import static com.quickmas.salim.quickmasretail.Service.ApiSettings.url_outlook_routr;
import static com.quickmas.salim.quickmasretail.Service.ApiSettings.url_outlook_sales_for;
import static com.quickmas.salim.quickmasretail.Service.ApiSettings.url_putlook_id;
import static com.quickmas.salim.quickmasretail.Service.UncodeAndDecode.uncode;
import static com.quickmas.salim.quickmasretail.Utility.FileManagerSetting.getImageFromFile;
import static com.quickmas.salim.quickmasretail.Utility.FontSettings.getFont;
import static com.quickmas.salim.quickmasretail.Utility.FontSettings.setTextFont;
import static com.quickmas.salim.quickmasretail.Utility.UIComponent.setLinearLayoutBackground;

public class Dataloading extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_dataloading);

        DBInitialization db = new DBInitialization(this,null,null,1);
        DashboardMenu cMenu =  selectByVarname(db,"dashboard_sync");

        ImageView header_menu_logo = (ImageView) findViewById(R.id.header_menu_logo);
        header_menu_logo.setImageBitmap(FileManagerSetting.getImageFromFile(cMenu.getImage(), this));

        TextView headtext = (TextView) findViewById(R.id.text1);
        headtext.setTypeface(getFont(this));
        headtext.setText(textSelectByVarname(db,"dataSync_download").getText());

        TextView online_count_text = setTextFont((TextView) findViewById(R.id.online_count_text), this, db, "datadownload_online_count_text");
        TextView online_running_text = setTextFont((TextView) findViewById(R.id.online_running_text), this, db, "datadownload_online_running_text");
        TextView online_count_positive = setTextFont((TextView) findViewById(R.id.online_count_positive), this, db, "datadownload_online_count_positive");
        TextView online_count_failed = setTextFont((TextView) findViewById(R.id.online_count_failed), this, db, "datadownload_online_count_failed");
        TextView online_count_negative = setTextFont((TextView) findViewById(R.id.online_count_negative), this, db, "datadownload_online_count_negative");
        Button datadownload_backToHome = setTextFont((Button) findViewById(R.id.datadownload_backToHome), this, db, "datadownload_backToHome");
        online_count_text.setText(online_count_text.getText()+String.valueOf(pendingProductList.size()));
        datadownload_backToHome.setVisibility(View.GONE);
        ArrayList<PhotoPathTarDir> allProductPhoto = new ArrayList<PhotoPathTarDir>();

        for(Product c : pendingProductList)
        {
            PhotoPathTarDir p = new PhotoPathTarDir();
            p.setUrl(c.getPhoto());
            p.setTarget(String.valueOf(c.getId()));
            c.setPhoto(p.getTarget()+".png");
            DebugHelper.print(p);
            allProductPhoto.add(p);
            c.insert(db);
        }

        datadownload_backToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dataloading.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        DataloadImage di = new DataloadImage();
        CallBack c = new CallBack("Module.DataSync.DataDownload.Dataloading", "eachimg");
        CallBack c1 = new CallBack("Module.DataSync.DataDownload.Dataloading", "finalimg");
        di.downloadData(this,allProductPhoto,c,c1);

    }
    public int sucessDownload=0;
    public int failedDownload=0;
    public void eachimg(String response,Context context)
    {

        if(response.charAt(0)=='f')
        {
            sucessDownload++;
        }
        else
        {
            sucessDownload++;
        }
        DBInitialization db = new DBInitialization(context,null,null,1);
        String txt = textSelectByVarname(db,"datadownload_online_count_positive").getText()+response.trim();
        TextView textView = (TextView) ((Activity) context).findViewById(R.id.online_count_positive);
        textView.setText(txt);

        txt = textSelectByVarname(db,"datadownload_online_count_failed").getText()+failedDownload;
        textView = (TextView) ((Activity) context).findViewById(R.id.online_count_failed);
        textView.setText(txt);

        txt = textSelectByVarname(db,"datadownload_online_count_negative").getText()+ (pendingProductList.size()-sucessDownload-failedDownload);
        textView = (TextView) ((Activity) context).findViewById(R.id.online_count_negative);
        textView.setText(txt);
        System.out.println(response);
    }
    public void finalimg(String response,Context context)
    {
        DBInitialization db = new DBInitialization(context,null,null,1);
        CallBack c = new CallBack("Module.DataSync.DataDownload.Dataloading", "jsonInsertOut");
        User cSystemInfo = new User();
        cSystemInfo.select(db,"1=1");
        DataDownload.downloadData(context,url_outlook,"&route="+uncode(pendingProductList.get(0).getRoute())+"&section="+uncode(pendingProductList.get(0).getSection()),cSystemInfo,c);

    }

    public void jsonInsertOut(String response,Context context)
    {
        DBInitialization db1 = new DBInitialization(context,null,null,1);
        try
        {
            final JSONArray jr = new JSONArray(response.toString());
            for (int i = 0; i < jr.length(); i++)
            {
                Outlet outlet = new Outlet();
                JSONObject jb = (JSONObject) jr.getJSONObject(i);
                outlet.setId(Integer.parseInt(jb.get(url_putlook_id).toString()));
                outlet.setOutlet_id((jb.get(url_outlook_outlet_id).toString()));
                outlet.setOutlet_name(jb.get(url_outlook_outletname).toString());
                outlet.setOwner_name(jb.get(url_outlook_owner).toString());
                outlet.setRoute((jb.get(url_outlook_routr).toString()));
                outlet.setSection(jb.get(ApiSettings.url_outlook_sction).toString());
                outlet.setClaster(jb.get(url_outlook_cluster).toString());
                outlet.setAddress((jb.get(url_outlook_address).toString()));
                outlet.setPhone(jb.get(url_outlook_phone).toString());
                outlet.setOutlet_type(jb.get(url_outlook_outlet_type).toString());
                outlet.setSales_for((jb.get(url_outlook_sales_for).toString()));
                outlet.setOutlet_for(jb.get(url_outlook_outlet_for).toString());
                outlet.insert(db1);
            }
        }
        catch (Exception e){System.out.println(e.toString());}

        Button btn = (Button) ((Activity) context).findViewById(R.id.datadownload_backToHome);
        btn.setVisibility(View.VISIBLE);
        TextView online_running_text = (TextView) ((Activity) context).findViewById(R.id.online_running_text);
        ProgressBar progressBar1 = (ProgressBar) ((Activity) context).findViewById(R.id.progressBar1);

        online_running_text.setText(textSelectByVarname(db1,"datadownload_online_sucess").getText());
        progressBar1.setVisibility(View.GONE);
        Toast.makeText(context, textSelectByVarname(db1,"datadownload_online_sucess").getText(), Toast.LENGTH_LONG).show();

    }
}
