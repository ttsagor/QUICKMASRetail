package com.quickmas.salim.quickmasretail.Module.DataSync;

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
import android.widget.TextView;
import android.widget.Toast;

import com.quickmas.salim.quickmasretail.Database.DBInitialization;
import com.quickmas.salim.quickmasretail.Model.RTM.Product;
import com.quickmas.salim.quickmasretail.Model.System.User;
import com.quickmas.salim.quickmasretail.Module.DataSync.DataDownload.DataUpload;
import com.quickmas.salim.quickmasretail.Module.DataSync.DataDownload.PendingList;
import com.quickmas.salim.quickmasretail.Module.Loading.Loading;
import com.quickmas.salim.quickmasretail.Model.System.DashboardMenu;
import com.quickmas.salim.quickmasretail.R;
import com.quickmas.salim.quickmasretail.Utility.FileManagerSetting;
import com.quickmas.salim.quickmasretail.Utility.Permission;

import static com.quickmas.salim.quickmasretail.Model.System.DashboardMenu.selectByVarname;
import static com.quickmas.salim.quickmasretail.Utility.FileManagerSetting.getImageFromFile;
import static com.quickmas.salim.quickmasretail.Utility.FontSettings.getFont;
import static com.quickmas.salim.quickmasretail.Utility.FontSettings.setTextFont;
import static com.quickmas.salim.quickmasretail.Utility.Permission.isNetworkAvailable;
import static com.quickmas.salim.quickmasretail.Utility.UIComponent.setLinearLayoutBackground;

public class DataSync extends AppCompatActivity {
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_data_sync);
        context = this;
        DBInitialization db = new DBInitialization(this,null,null,1);

        DashboardMenu cMenu =  selectByVarname(db,"dashboard_sync");

        ImageView header_menu_logo = (ImageView) findViewById(R.id.header_menu_logo);
        header_menu_logo.setImageBitmap(FileManagerSetting.getImageFromFile(cMenu.getImage(), this));

        TextView headtext = (TextView) findViewById(R.id.text1);
        headtext.setTypeface(getFont(this));
        headtext.setText(cMenu.getText());


        TextView dataSync_downloadText = (TextView) findViewById(R.id.dataSync_downloadText);

        dataSync_downloadText = setTextFont(dataSync_downloadText, this, db, "dataSync_downloadText");

        TextView dataSync_uploadText = setTextFont((TextView) findViewById(R.id.dataSync_uploadText), this, db, "dataSync_uploadText");

        TextView dataSync_uploadText_final = setTextFont((TextView) findViewById(R.id.dataSync_uploadText_final), this, db, "dataSync_uploadText_final");

        Button dataSync_download = setTextFont((Button) findViewById(R.id.dataSync_download), this, db, "dataSync_download");

        Button dataSync_upload = setTextFont((Button) findViewById(R.id.dataSync_upload), this, db, "dataSync_upload");

        Button dataSync_upload_final = setTextFont((Button) findViewById(R.id.dataSync_upload_final), this, db, "dataSync_upload_final");

        dataSync_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DataSync.this,
                        Loading.class);
                intent.putExtra("dataDownloadPendingList","true");
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });

        dataSync_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isNetworkAvailable(context))
                {
                    Intent i = new Intent( DataSync.this ,DataUpload.class);
                    i.putExtra("final_upload", "false");
                    startActivity(i);
                }
                else
                {
                    Toast.makeText(getApplicationContext(), R.string.login_noInternet, Toast.LENGTH_LONG).show();
                }

            }
        });

        dataSync_upload_final.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isNetworkAvailable(context))
                {
                    DBInitialization db = new DBInitialization(getApplicationContext(),null,null,1);


                    User s = new User();
                    s = s.select(db,"1=1");

                    int sell_per = db.getDataCount(db.TABLE_PRODUCT,db.COLUMN_product_sell_permission+"=1");
                    int pay_per = db.getDataCount(db.TABLE_PRODUCT,db.COLUMN_product_payment_permission+"=1");
                    if(sell_per>0)
                    {
                        int c = db.getDataCount(db.TABLE_Invoice,db.COLUMN_i_status+"=1 OR "+db.COLUMN_i_status+"=2");
                        if(c>0)
                        {
                            Toast.makeText(getApplicationContext(), R.string.dataUpload_receivable_present, Toast.LENGTH_LONG).show();
                            return;
                        }
                    }
                    else if(pay_per>0)
                    {
                        int c = db.getDataCount(db.TABLE_Invoice,db.COLUMN_i_status+"=1");
                        if(c>0)
                        {
                            Toast.makeText(getApplicationContext(), R.string.dataUpload_receivable_present, Toast.LENGTH_LONG).show();
                            return;
                        }
                    }

                    Intent i = new Intent( DataSync.this ,DataUpload.class);
                    i.putExtra("final_upload", "true");
                    if(sell_per>0)
                    {
                        i.putExtra("data_closing", "true");
                    }
                    else
                    {
                        i.putExtra("data_closing", "true");
                    }
                    startActivity(i);

                }
                else
                {
                    Toast.makeText(getApplicationContext(), R.string.login_noInternet, Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}
