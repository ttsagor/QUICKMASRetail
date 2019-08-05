package com.quickmas.salim.quickmasretail.Module.Dashboard.FooterActivity.Settings;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.quickmas.salim.quickmasretail.Database.DBInitialization;
import com.quickmas.salim.quickmasretail.Model.System.DashboardMenu;
import com.quickmas.salim.quickmasretail.Model.System.DashboardSubMenu;
import com.quickmas.salim.quickmasretail.Model.System.User;
import com.quickmas.salim.quickmasretail.Module.DataSync.DataSync;
import com.quickmas.salim.quickmasretail.Module.Loading.Loading;
import com.quickmas.salim.quickmasretail.R;
import com.quickmas.salim.quickmasretail.Structure.MenuNameLink;
import com.quickmas.salim.quickmasretail.Structure.SettingsMenuStructure;
import com.quickmas.salim.quickmasretail.Utility.CallBack;
import com.quickmas.salim.quickmasretail.Utility.DebugHelper;
import com.quickmas.salim.quickmasretail.Utility.FileManagerSetting;
import com.quickmas.salim.quickmasretail.Utility.FontSettings;
import com.quickmas.salim.quickmasretail.Utility.NetworkConfiguration;
import com.quickmas.salim.quickmasretail.Utility.UI.ScrollListView;
import com.quickmas.salim.quickmasretail.Utility.UI.ViewData;

import java.lang.reflect.Method;
import java.util.ArrayList;

import static com.quickmas.salim.quickmasretail.Model.System.DashboardMenu.selectByVarname;
import static com.quickmas.salim.quickmasretail.Utility.FontSettings.getFont;

public class Settings extends AppCompatActivity {
    DBInitialization db;
    Context context;
    Activity activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_settings);
        context = this;
        activity=Settings.this;
        db = new DBInitialization(this,null,null,1);

        DashboardMenu cMenu =  selectByVarname(db,"dashboard_footer_settings");

        TextView headtext = (TextView) findViewById(R.id.text1);
        headtext.setTypeface(getFont(this));
        headtext.setText(cMenu.getText());
        ImageView header_menu_logo = (ImageView) findViewById(R.id.header_menu_logo);
        header_menu_logo.setImageBitmap(FileManagerSetting.getImageFromFile(getApplicationContext(),cMenu.getImage()));

        ArrayList<DashboardMenu> allMenu = DashboardMenu.select(db,db.COLUMN_dash_image_category+"='settings'");

        ListView menu_list = (ListView) findViewById(R.id.menu_list);
        ScrollListView.loadListView(context, menu_list, R.layout.adaptar_setting_list, SettingsMenuStructure.setMenu(allMenu), "showSettings", 0, 100, true);

    }


    public void showSettings(ViewData data)
    {
        final SettingsMenuStructure settingsMenuStructure = (SettingsMenuStructure) data.object;
        TextView text1  = (TextView) data.view.findViewById(R.id.text1);
        TextView text2  = (TextView) data.view.findViewById(R.id.text2);
        final ImageView image1  = (ImageView) data.view.findViewById(R.id.image1);
        final ImageView image2  = (ImageView) data.view.findViewById(R.id.image2);
        final LinearLayout menu1_holder  = (LinearLayout) data.view.findViewById(R.id.menu1_holder);
        final LinearLayout menu2_holder  = (LinearLayout) data.view.findViewById(R.id.menu2_holder);

        try
        {
            text1 = FontSettings.setTextFont(text1,context,settingsMenuStructure.menu1.getText());
            final LinearLayout ly = menu1_holder;
            menu1_holder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popup_generator(settingsMenuStructure.menu1,ly);
                }
            });
            image1.setImageBitmap(FileManagerSetting.getImageFromFile(getApplicationContext(),settingsMenuStructure.menu1.getImage()));
        }
        catch (Exception e)
        {}

        if(text1.getText().length()>0)
        {
            menu1_holder.setVisibility(View.VISIBLE);
        }
        else {
            menu1_holder.setVisibility(View.GONE);
        }


        try
        {
            text2 = FontSettings.setTextFont(text2,context,settingsMenuStructure.menu2.getText());
            final LinearLayout ly = menu2_holder;
            menu2_holder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popup_generator(settingsMenuStructure.menu2,ly);
                }
            });
            image2.setImageBitmap(FileManagerSetting.getImageFromFile(getApplicationContext(),settingsMenuStructure.menu2.getImage()));
        }
        catch (Exception e){}
        if(text2.getText().length()>0)
        {
            menu2_holder.setVisibility(View.VISIBLE);
        }
        else {
            menu2_holder.setVisibility(View.GONE);
        }
    }

    void popup_generator(final DashboardMenu menu1,final LinearLayout app_logo_holder)
    {

        final ArrayList<DashboardSubMenu> dashboardSubMenus = DashboardSubMenu.select(db,db.COLUMN_dash_sub_varname+"='"+ menu1.getVarname()+"'");
        DebugHelper.print(dashboardSubMenus);
        if(dashboardSubMenus.size()>0)
        {
            LayoutInflater layoutInflater = (LayoutInflater) context
                    .getSystemService(LAYOUT_INFLATER_SERVICE);
            final View popupView = layoutInflater.inflate(R.layout.pop_up_menu_settings_list, null);
            final PopupWindow popupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            popupWindow.setOutsideTouchable(true);
            popupWindow.setFocusable(true);
            popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            LinearLayout ll = (LinearLayout) popupView.findViewById(R.id.layout_holder);
            ImageView header_menu_logo = (ImageView) popupView.findViewById(R.id.header_menu_logo);

            header_menu_logo.setImageBitmap(FileManagerSetting.getImageFromFile(menu1.getImage(), context));
            TextView main_menu = FontSettings.setTextFont((TextView) popupView.findViewById(R.id.main_menu),context,menu1.getText());
            final ArrayList<MenuNameLink> menuList = new ArrayList<MenuNameLink>();

            for(DashboardSubMenu dm : dashboardSubMenus)
            {
                menuList.add(new MenuNameLink(dm.getText(),dm.getNew_var_name()));
            }
            for (int i = 0; i < menuList.size(); i++) {

                Button myButton = new Button(context);
                myButton.setText(menuList.get(i).getName());
                myButton.setBackgroundColor(Color.parseColor("#dbe8ff"));
                myButton.setTextAppearance(context,android.R.style.TextAppearance_Large);
                myButton.setTypeface(getFont(context));
                myButton.setTextColor(Color.parseColor("#000000"));
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lp.setMargins(5,5,5,5);
                ll.addView(myButton, lp);
                final String link = menuList.get(i).getLink();
                final int pos = i;
                if(menu1.getVarname().equals("dashboard_operating_mode"))
                {
                    User cUser = new User();
                    cUser.select(db,"1=1");
                    if(cUser.getActive_online()==1 && menuList.get(i).getLink().equals("dashboard_operating_mode_1"))
                    {
                        if(NetworkConfiguration.checkIfInternetOn(activity)) {
                            Drawable icon= context.getResources().getDrawable( R.drawable.greencheck);
                            myButton.setCompoundDrawablesWithIntrinsicBounds( icon, null, null, null );
                        }
                    }
                    else if(cUser.getActive_online()==0 && menuList.get(i).getLink().equals("dashboard_operating_mode_2"))
                    {
                        Drawable icon= context.getResources().getDrawable( R.drawable.greencheck);
                        myButton.setCompoundDrawablesWithIntrinsicBounds( icon, null, null, null );
                    }
                }
                myButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        if(menu1.getVarname().equals("dashboard_language"))
                        {
                            if(NetworkConfiguration.checkIfInternetOn(activity)) {
                                CallBack callBack = new CallBack("Module.Dashboard.MainActivity", "setLangauge");
                                callBack.call(String.valueOf(dashboardSubMenus.get(pos).getId()), context);
                            }

                        }
                        else
                        {
                            CallBack callBack = new CallBack("Module.Dashboard.MainActivity", link);
                            callBack.call(String.valueOf(dashboardSubMenus.get(pos).getId()), activity);
                        }
                        popupWindow.dismiss();
                    }
                });
            }
            popupWindow.showAtLocation(app_logo_holder, Gravity.CENTER, 0, 0);
        }
        else
        {
            callFunction(menu1.getVarname());
        }
    }

    void callFunction(String functionName)
    {
        try {
            Class<?> c = this.getClass();
            Method method = c.getDeclaredMethod(functionName);
            method.invoke(this);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void dashboard_sync()
    {
        if(NetworkConfiguration.checkIfInternetOn(activity)) {
            Intent intent = new Intent(context, DataSync.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }
    public void dashboard_logout()
    {
        User cUser = new User();
        cUser.select(db,"1=1");
        Intent intent = new Intent(context, com.quickmas.salim.quickmasretail.MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    public void dashboard_assign_download()
    {
        if(NetworkConfiguration.checkIfInternetOn(activity))
        {
            Intent intent = new Intent(context,
                    Loading.class);
            intent.putExtra("dataDownloadAssignmentList","true");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }
}
