package com.quickmas.salim.quickmasretail.Module.DataSync.AssignmentList;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.quickmas.salim.quickmasretail.Database.DBInitialization;
import com.quickmas.salim.quickmasretail.Model.RTM.RtmAssignmentList;
import com.quickmas.salim.quickmasretail.Model.System.DashboardMenu;
import com.quickmas.salim.quickmasretail.Module.Loading.Loading;
import com.quickmas.salim.quickmasretail.R;
import com.quickmas.salim.quickmasretail.Utility.DateTimeCalculation;
import com.quickmas.salim.quickmasretail.Utility.FileManagerSetting;
import com.quickmas.salim.quickmasretail.Utility.FontSettings;
import com.quickmas.salim.quickmasretail.Utility.UI.ScrollListView;
import com.quickmas.salim.quickmasretail.Utility.UI.ViewData;

import java.util.ArrayList;

import static com.quickmas.salim.quickmasretail.Model.System.DashboardMenu.selectByVarname;
import static com.quickmas.salim.quickmasretail.Utility.FontSettings.getFont;

public class AssignmentList extends AppCompatActivity {

    public static ArrayList<RtmAssignmentList> pendingRtmAssignmentLists = new ArrayList<>();
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_assignment_list2);
        context = this;
        DBInitialization db = new DBInitialization(this,null,null,1);
        DashboardMenu cMenu =  selectByVarname(db,"dashboard_assign_download");
        ImageView header_menu_logo = (ImageView) findViewById(R.id.header_menu_logo);
        header_menu_logo.setImageBitmap(FileManagerSetting.getImageFromFile(cMenu.getImage(), this));
        TextView headtext = (TextView) findViewById(R.id.text1);
        headtext.setTypeface(getFont(this));
        headtext.setText(cMenu.getText());

        ListView assignment_list = (ListView) findViewById(R.id.assignment_list);


        ScrollListView.loadListView(this, assignment_list, R.layout.adaptar_assignment_list_data, pendingRtmAssignmentLists, "showData", 0, 100, true);
    }


    public void showData(final ViewData data)
    {
        final RtmAssignmentList rtmAssignmentList = (RtmAssignmentList) data.object;


        String date = DateTimeCalculation.getDate(rtmAssignmentList.getDateFrom())+" to "+DateTimeCalculation.getDate(rtmAssignmentList.getDateTo());
        final LinearLayout layout_holder  = (LinearLayout) data.view.findViewById(R.id.layout_holder);


        TextView assign_no = FontSettings.setTextFont((TextView) data.view.findViewById(R.id.assign_no),context,rtmAssignmentList.getAsignNo());
        TextView date_from_to = FontSettings.setTextFont((TextView) data.view.findViewById(R.id.date_from_to),context,date);
        TextView quantity = FontSettings.setTextFont((TextView) data.view.findViewById(R.id.quantity),context,rtmAssignmentList.getQuantity());
        Button action = FontSettings.setTextFont((Button) data.view.findViewById(R.id.action),context,"View");


        if(rtmAssignmentList.getStatus().equals("1"))
        {
            layout_holder.setBackgroundColor(Color.parseColor("#717272"));
            assign_no.setTextColor(Color.WHITE);
            date_from_to.setTextColor(Color.WHITE);
            quantity.setTextColor(Color.WHITE);
            action.setTextColor(Color.WHITE);
        }
        else
        {
            layout_holder.setBackgroundColor(Color.parseColor("#e2e2e2"));
            assign_no.setTextColor(Color.BLACK);
            date_from_to.setTextColor(Color.BLACK);
            quantity.setTextColor(Color.BLACK);
            action.setTextColor(Color.WHITE);
        }

        action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,
                        Loading.class);
                intent.putExtra("dataDownloadPendingList","true");
                intent.putExtra("assign_no",rtmAssignmentList.getAsignNo());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

    }
}
