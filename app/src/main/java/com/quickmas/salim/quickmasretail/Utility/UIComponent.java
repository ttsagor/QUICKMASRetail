package com.quickmas.salim.quickmasretail.Utility;

import android.app.Activity;
import android.content.Context;
import android.content.SyncStats;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.quickmas.salim.quickmasretail.Database.DBInitialization;
import com.quickmas.salim.quickmasretail.Model.System.User;
import com.quickmas.salim.quickmasretail.R;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.quickmas.salim.quickmasretail.MainActivity.global_user;
import static com.quickmas.salim.quickmasretail.Utility.FileManagerSetting.getImageFromFile;

/**
 * Created by Forhad on 02/04/2018.
 */

public class UIComponent {
    public static void updateListViewHeight(ListView listView,int tol) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter != null) {

            int numberOfItems = listAdapter.getCount();

            // Get total height of all items.
            int totalItemsHeight = 0;
            for (int itemPos = 0; itemPos < numberOfItems; itemPos++) {
                View item = listAdapter.getView(itemPos, null, listView);
                float px = 500 * (listView.getResources().getDisplayMetrics().density);
                item.measure(View.MeasureSpec.makeMeasureSpec((int)px, View.MeasureSpec.AT_MOST), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
                totalItemsHeight += item.getMeasuredHeight();
            }

            // Get total height of all item dividers.
            int totalDividersHeight = listView.getDividerHeight() *
                    (numberOfItems - 1);
            // Get padding
            int totalPadding = listView.getPaddingTop() + listView.getPaddingBottom();

            // Set list height.
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            double release=Double.parseDouble(Build.VERSION.RELEASE.replaceAll("(\\d+[.]\\d+)(.*)","$1"));

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M && tol==0)
            {
                params.height = totalItemsHeight/2+ totalDividersHeight + totalPadding;
            }
            else
            {
                params.height = totalItemsHeight+ totalDividersHeight + totalPadding;
            }

            listView.setLayoutParams(params);
            listView.requestLayout();
           // return true;

        } else {
            //return false;
        }

    }

    public static void updateListViewHeightManual(Context context,ListView listView,int id, int iteamCount) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter != null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View item = inflater.inflate(id, null);

            float px = 500 * (listView.getResources().getDisplayMetrics().density);
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            item.measure(View.MeasureSpec.makeMeasureSpec((int)px, View.MeasureSpec.AT_MOST), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));



            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M)
            {
                iteamCount = item.getMeasuredHeight()*iteamCount;
                params.height = iteamCount/2 - ((iteamCount/2)/7);
            }
            else
            {
                iteamCount = item.getMeasuredHeight()*iteamCount;
                params.height = iteamCount;
            }

            listView.setLayoutParams(params);
            listView.requestLayout();
            // return true;

        } else {
            //return false;
        }

    }

    public static int getDisplayHeight(Context context)
    {
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(metrics);
        return metrics.heightPixels;
    }

    public static int getDisplayWidth(Context context)
    {
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels;
    }


    public static SpannableString buildBackgroundColorSpan(SpannableString spannableString,
                                                           String text, String searchString, int color) {

        int indexOf = text.toUpperCase().indexOf(searchString.toUpperCase());
        try {
            spannableString.setSpan(new BackgroundColorSpan(color), indexOf,
                    (indexOf + searchString.length()), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        } catch (Exception e) {

        }
        return spannableString;
    }

    public static boolean viewToggle(View v)
    {
        if(v.getVisibility() == View.VISIBLE)
        {
            v.setVisibility(View.GONE);
            return false;
        }
        else
        {
            v.setVisibility(View.VISIBLE);
            return true;
        }
    }

    public static LinearLayout setLinearLayoutBackground(LinearLayout ln, String path, Context context)
    {
        ln.setBackgroundDrawable(new BitmapDrawable(getImageFromFile(path,context)));
        return ln;
    }

    public static void hideSoftKeyboard(Activity activity) {
        if (activity != null) {
            InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (activity.getCurrentFocus() != null && inputManager != null) {
                inputManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
                inputManager.hideSoftInputFromInputMethod(activity.getCurrentFocus().getWindowToken(), 0);
            }
        }
    }
    public static void showSoftKeyboard(Activity activity) {
        if (activity != null) {
            InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (activity.getCurrentFocus() != null && inputManager != null) {
                inputManager.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
            }
        }
    }
    public static ArrayAdapter<String> spinnerDataLoad(Context context,DBInitialization db,String col,String table,String con,String groupby,String firstRow)
    {
        Cursor c = db.getData(col,table,con,groupby);
        ArrayList<String> categories = new ArrayList<String>();
        categories.add(firstRow);
        try {
            if (c != null && c.getCount() > 0) {
                c.moveToFirst();
                do {
                    categories.add(c.getString(c.getColumnIndex(col)));
                } while (c.moveToNext());
            }
        }catch (Exception e){}
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return dataAdapter;
    }

    public static ArrayAdapter<String> spinnerDataLoad(Context context,ArrayList<String> categories)
    {
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return dataAdapter;
    }

    public static void setSeletedIteamSpinner(Spinner spinner, String txt)
    {
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).equals(txt)){
                spinner.setSelection(i);
                return;
            }
        }
        spinner.setSelection(0);
    }

    public static TextView getMeasueredLine(TextView textView,Context context,int percent) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        int screenWidth = getDisplayWidth(context); // Get full screen width
        int eightyPercent = (screenWidth * percent) / 100; // Calculate 80% of it
        // as mys adapter wa having almost 80% of the whole screen width
        float textWidth = textView.getPaint().measureText(textView.getText().toString());
        int numberOfLines = ((int) textWidth / eightyPercent) +1;
        // calculate number of lines it might take
        //if(!(textView.getLineCount()>1))
        {
            textView.setLines(numberOfLines);
        }
        return textView;
    }

    public static TextView getMeasueredLine(TextView textView,Context context) {

        int percent = 30;
        if(textView.getParent() instanceof LinearLayout)
        {
            try {
                LinearLayout ln = (LinearLayout) textView.getParent();
                percent = Math.round((((LinearLayout.LayoutParams) ln.getLayoutParams()).weight * 100));
                if(percent==0)
                {
                    return textView;
                }
            }catch (Exception e){
                return textView;
            }
        }

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        int screenWidth = getDisplayWidth(context); // Get full screen width
        int eightyPercent = (screenWidth * percent) / 100; // Calculate 80% of it
        // as mys adapter wa having almost 80% of the whole screen width
        float textWidth = textView.getPaint().measureText(textView.getText().toString());
        int numberOfLines = ((int) textWidth / eightyPercent) +1;
        // calculate number of lines it might take
        textView.setLines(numberOfLines);
        return textView;
    }

    public static boolean checkIfInternetOn(Context c)
    {
        if(!NetworkConfiguration.isInternetOn(c)) {
            SweetAlertDialog sdialog = new SweetAlertDialog((Activity) c, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                    .setTitleText("No Internet")
                    .setContentText("Please Connect to Internet")
                    .setCustomImage(R.drawable.no_internet);

            sdialog.show();
            sdialog.getButton(SweetAlertDialog.BUTTON_CONFIRM).setBackgroundColor(Color.RED);
           return false;
        }
        return true;
    }

    public static int SpinnerGetIndex(Spinner spinner, String myString){
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                return i;
            }
        }

        return 0;
    }

    public static void setGlobalProgessBarColor(Context context, DBInitialization db)
    {
        User user = new User();
        user.select(db,"1=1");
        setGlobalProgessBarColor(context, user.getLoading_color(),user.getLoading_background());
    }
    public static void setGlobalProgessBarColor(Context context, String barColor,String backGroundColor)
    {
        try {
            ProgressBar progBar = (ProgressBar) ((Activity) context).findViewById(R.id.global_progessbar);
            if (progBar != null) {
                progBar.setIndeterminate(true);
                progBar.getIndeterminateDrawable().setColorFilter(Color.parseColor(barColor), android.graphics.PorterDuff.Mode.MULTIPLY);
            }
        }catch (Exception e){}
        try {
            LinearLayout loading_layout = (LinearLayout) ((Activity) context).findViewById(R.id.loading_layout);
            loading_layout.setBackgroundColor(Color.parseColor(backGroundColor));
        }catch (Exception e){}
    }

    public static void setGlobalProgessBarColor(Context context,ProgressBar progBar,LinearLayout view, String barColor,String backGroundColor)
    {
        try {
            if (progBar != null) {
                progBar.setIndeterminate(true);
                progBar.getIndeterminateDrawable().setColorFilter(Color.parseColor(barColor), android.graphics.PorterDuff.Mode.MULTIPLY);
            }
        }catch (Exception e){}
        try {
            view.setBackgroundColor(Color.parseColor(backGroundColor));
        }catch (Exception e){}
    }

    public static void setImageView(Context context,ImageView imageView,String path)
    {
        Glide.with(context)
                .load("file://"+FileManagerSetting.getImageOrBlack(path))
                .apply(new RequestOptions().override(400,400)
                        .error(R.drawable.no_photo))
                .into(imageView);
    }

}
