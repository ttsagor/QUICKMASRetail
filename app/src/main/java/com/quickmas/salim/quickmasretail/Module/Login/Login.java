package com.quickmas.salim.quickmasretail.Module.Login;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.quickmas.salim.quickmasretail.Database.DBInitialization;
import com.quickmas.salim.quickmasretail.Model.System.TextString;
import com.quickmas.salim.quickmasretail.Module.Dashboard.MainActivity;
import com.quickmas.salim.quickmasretail.Module.Loading.Loading;
import com.quickmas.salim.quickmasretail.Model.System.User;
import com.quickmas.salim.quickmasretail.Module.POS.PosInvoicePrint.PosInvoicePrint;
import com.quickmas.salim.quickmasretail.Module.PosSelection.PosSelection;
import com.quickmas.salim.quickmasretail.R;
import com.quickmas.salim.quickmasretail.Service.ApiSettings;
import com.quickmas.salim.quickmasretail.Service.DataloadImage;
import com.quickmas.salim.quickmasretail.Structure.PhotoPathTarDir;
import com.quickmas.salim.quickmasretail.Utility.CallBack;
import com.quickmas.salim.quickmasretail.Utility.DebugHelper;
import com.quickmas.salim.quickmasretail.Utility.FileManagerSetting;
import com.quickmas.salim.quickmasretail.Utility.FontSettings;
import com.quickmas.salim.quickmasretail.Utility.NetworkConfiguration;
import com.quickmas.salim.quickmasretail.Utility.Permission;
import com.quickmas.salim.quickmasretail.Utility.TypeConvertion;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;

import static com.quickmas.salim.quickmasretail.MainActivity.global_user;
import static com.quickmas.salim.quickmasretail.Service.ApiSettings.url_text_download_id;
import static com.quickmas.salim.quickmasretail.Service.ApiSettings.url_text_download_text;
import static com.quickmas.salim.quickmasretail.Service.ApiSettings.url_text_download_varname;

public class Login extends AppCompatActivity {

    public static String pos_info="";
    User cSystem = new User();
    Context mContext;
    ProgressDialog progressDoalog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_login);
        mContext = this;
        Intent iin = getIntent();
        Bundle b = iin.getExtras();
        if (b != null) {
            try {
                String msg = b.get("msg").toString();
                Toast.makeText(getApplicationContext(),msg, Toast.LENGTH_LONG).show();
            }catch (Exception e){
            }
        }

        final EditText login_company_id = (EditText) findViewById(R.id.login_company_id);
        final EditText login_username = (EditText) findViewById(R.id.login_username);
        final EditText login_password = (EditText) findViewById(R.id.login_password);
        final TextView login_response = (TextView) findViewById(R.id.login_response);
        final Button login_submit = (Button) findViewById(R.id.login_submit);
        Loading.wait_text = (TextView) findViewById(R.id.login_response);
        final DBInitialization db = new DBInitialization(this,null,null,1);

        cSystem.select(db,"1=1");
        Typeface tf = FontSettings.getFont(this);
        login_company_id.setTypeface(tf);
        login_username.setTypeface(tf);
        login_password.setTypeface(tf);
        login_response.setTypeface(tf);
        login_submit.setTypeface(tf);


        if(cSystem.getStatus()==1)
        {
            login_company_id.setText(String.valueOf(cSystem.getCompany_id()));
            login_username.setText(String.valueOf(cSystem.getUser_name()));
            login_company_id.setEnabled(false);
            login_username.setEnabled(false);
            FontSettings.setTextFont((TextView) findViewById(R.id.warehouse), this, cSystem.getSelected_location());
            FontSettings.setTextFont((TextView) findViewById(R.id.pos), this, cSystem.getSelected_pos());

            FontSettings.setTextFont((TextView) findViewById(R.id.warehouse_txt), this,TextString.textSelectByVarnameDb(db,"pos_login_previous_warehouse").getText());
            FontSettings.setTextFont((TextView) findViewById(R.id.pos_txt), this,TextString.textSelectByVarnameDb(db,"pos_login_previous_pos").getText());
            LinearLayout create_account_holder = (LinearLayout) findViewById(R.id.create_account_holder);
            create_account_holder.setVisibility(View.GONE);
        }
        else
        {
            LinearLayout pos_name_holder = (LinearLayout) findViewById(R.id.pos_name_holder);
            pos_name_holder.setVisibility(View.GONE);
            TextView create_link = (TextView) findViewById(R.id.create_link);
            String text="Create a free trail account";
            SpannableString content = new SpannableString(text);
            content.setSpan(new UnderlineSpan(), 0, text.length(), 0);
            create_link.setText(content);

            create_link.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://quickmas.com/app/free-trial-app-q"));
                    startActivity(browserIntent);
                }
            });

        }

        if (Permission.isStoragePermissionGranted(this)) {

        }
        //if(Permission.isNetworkAvailable(getBaseContext()))
        {
            login_submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!NetworkConfiguration.checkIfInternetOn(mContext) && cSystem.getStatus()==0)
                    {
                        return;
                    }
                    String username = login_username.getText().toString().trim();
                    String password = login_password.getText().toString().trim();
                    String company = login_company_id.getText().toString().trim();
                    if(!(username.length()>0 && password.length()>0 && company.length()>0))
                    {
                        Toast.makeText(getApplicationContext(),getText(R.string.login_fill_up), Toast.LENGTH_LONG).show();
                        return;
                    }
                    login_submit.setEnabled(false);
                    final String url = ApiSettings.url_login +"user_name="+username+"&password="+password+"&company="+company;



                    if(cSystem.getStatus()==1)
                    {
                        if(cSystem.getUser_name().equals(username) && cSystem.getPassword().equals(password))
                        {
                            Intent intent = new Intent(Login.this,
                                    MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            startActivity(intent);
                        }
                        else
                            {
                            String msg = "User & Password not Matched";
                            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                            login_response.setText(msg);
                            login_submit.setEnabled(true);
                        }
                    }
                    else
                    {
                        progressDoalog = new ProgressDialog(mContext);
                        try {
                            progressDoalog.setMessage("Connecting....");
                            progressDoalog.setTitle("Please Wait");
                            progressDoalog.show();

                        } catch (Exception e) {

                        }
                        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        System.out.println("response:"+response);
                                        try {
                                            final JSONArray jr = new JSONArray("[" + response.toString() + "]");
                                            for (int i = 0; i < jr.length(); i++) {
                                                JSONObject jb = (JSONObject) jr.getJSONObject(i);
                                                User userData = new User();
                                                userData.setStatus(Integer.parseInt(jb.get("status").toString()));
                                                String msg = jb.get("msg").toString();
                                                if (userData.getStatus() == 1) {
                                                    pos_info = jb.get("pos_info").toString();
                                                    userData.setId(1);
                                                    userData.setUser_name(jb.get("user_name").toString());
                                                    userData.setPassword(jb.get("password").toString());
                                                    userData.setUser_full_name(jb.get("user_full_name").toString());
                                                    userData.setEmail(jb.get("email").toString());
                                                    userData.setImage(jb.get("image").toString());
                                                    userData.setCompany_name(jb.get("company_name").toString());
                                                    userData.setAddress1(jb.get("address").toString());
                                                    userData.setAddress2(jb.get("address2").toString());
                                                    userData.setAddress3(jb.get("address3").toString());
                                                    userData.setCountry(jb.get("country").toString());
                                                    userData.setPhone(jb.get("phone").toString());
                                                    userData.setCompany_id(jb.get("company_id").toString());
                                                    userData.setLogo(jb.get("logo").toString());
                                                    userData.setLocation(jb.get("location").toString());
                                                    userData.setPos(jb.get("pos").toString());
                                                    userData.setUser_order(Integer.parseInt(jb.get("order").toString()));
                                                    userData.setUser_payment(Integer.parseInt(jb.get("payment").toString()));
                                                    userData.setUser_delivery(Integer.parseInt(jb.get("delivery").toString()));
                                                    userData.setRtm_photo_limit(Integer.parseInt(jb.get("photo_limit").toString()));

                                                    userData.setApp_icon(jb.get("app_icon").toString());
                                                    userData.setLoading_color(jb.get("lauding_color").toString());
                                                    userData.setLoading_background(jb.get("lauding_body_color").toString());
                                                    userData.setBlank_img(jb.get("blank_image").toString());
                                                    userData.setPrint_company_name(jb.get("comp").toString());
                                                    userData.setPrint_web_name(jb.get("web").toString());

                                                    userData.setChoose_color(jb.get("color").toString());
                                                    userData.setChoose_size(jb.get("size").toString());

                                                    String loading_txt = (jb.get("loading_txt").toString());
                                                    System.out.println(loading_txt);
                                                    try {
                                                        final JSONArray jr1 = new JSONArray(loading_txt);
                                                        for (int j = 0; j < jr1.length(); j++) {
                                                            TextString ts = new TextString();
                                                            JSONObject jb1 = (JSONObject) jr1.getJSONObject(j);
                                                            ts.setId(Integer.parseInt(jb1.get(url_text_download_id).toString()));
                                                            ts.setVarname(jb1.get(url_text_download_varname).toString());
                                                            ts.setText(jb1.get(url_text_download_text).toString());
                                                            ts.insert(db);
                                                        }
                                                    }catch (Exception e){}
                                                    DebugHelper.print(TextString.select(db,"1=1"));
                                                    //userData.setCash_cu_balance(TypeConvertion.parseDouble(jb.get("cash_cu_balance").toString()));

                                                    userData.setStatus(0);
                                                    Boolean posAvailbe = false;

                                                    ArrayList<PhotoPathTarDir> paths = new ArrayList<>();
                                                    FileManagerSetting.createFolder(mContext);
                                                    PhotoPathTarDir p = new PhotoPathTarDir();
                                                    p.setUrl(userData.getApp_icon());
                                                    p.setTarget("system-company-logo");
                                                    paths.add(p);
                                                    p = new PhotoPathTarDir();
                                                    p.setUrl(userData.getBlank_img());
                                                    p.setTarget("system-blank-image");
                                                    paths.add(p);

                                                    userData.setApp_icon("system-company-logo.png");
                                                    userData.setBlank_img("system-blank-image.png");
                                                    userData.setActive_online(1);


                                                    if (db.getDataCount(DBInitialization.TABLE_USER, "1") > 0) {
                                                        userData.update(db);
                                                    } else {
                                                        userData.insert(db);
                                                    }


                                                    global_user = userData;
                                                    downloadImageAndSave(paths, "app_logo");


                                                } else {
                                                    msg = msg.replace("\\n",System.getProperty("line.separator"));
                                                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                                                    login_response.setText(msg);
                                                    login_submit.setEnabled(true);
                                                }
                                            }

                                        } catch (Exception e) {
                                            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                                            login_submit.setEnabled(true);
                                        }
                                        progressDoalog.dismiss();
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
            });
        }
        //else
        {

        }
    }

    public void gotoMainActivity(final String response,final Context context)
    {
        System.out.println("here download");
        Intent intent = new Intent(context,
                PosSelection.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        context.startActivity(intent);
    }


    void downloadImageAndSave(ArrayList<PhotoPathTarDir> paths,final String fileName)
    {
        DataloadImage di = new DataloadImage();
        CallBack c = new CallBack("Module.Login.Login", "eachimg");
        CallBack c1 = new CallBack("Module.Login.Login", "gotoMainActivity");
        di.downloadData(this,paths,c,c1);
    }

}
