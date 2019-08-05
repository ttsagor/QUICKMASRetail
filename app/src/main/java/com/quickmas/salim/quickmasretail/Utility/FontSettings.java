package com.quickmas.salim.quickmasretail.Utility;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.quickmas.salim.quickmasretail.Database.DBInitialization;
import com.quickmas.salim.quickmasretail.Model.POS.PosInvoicePayLaterUser;

import java.text.DecimalFormat;

import static com.quickmas.salim.quickmasretail.Model.System.TextString.textSelectByVarname;

/**
 * Created by Forhad on 25/03/2018.
 */

public class FontSettings {
    public static String fontPath = "fonts/SolaimanLipi.ttf";
    public static Typeface appFont;

    public static Typeface setFont(Context context)
    {
        appFont = Typeface.createFromAsset(context.getAssets(), fontPath);
        return appFont;
    }
    public static Typeface getFont(Context context)
    {
        //Typeface tf = Typeface.createFromAsset(context.getAssets(), fontPath);
        return appFont;
    }
    public static TextView setTextFont(TextView t, Context c, DBInitialization db, String var)
    {
        t.setTypeface(getFont(c));
        t.setText(textSelectByVarname(db,var).getText());
        t=UIComponent.getMeasueredLine(t,c);
        TextView tt = t;
        return t;
    }
    public static TextView setTextFontHint(TextView t, Context c, DBInitialization db, String var)
    {
        t.setTypeface(getFont(c));
        t.setHint(textSelectByVarname(db,var).getText());
        t=UIComponent.getMeasueredLine(t,c);
        TextView tt = t;
        return t;
    }
    public static RadioButton setTextFont(RadioButton t, Context c, DBInitialization db, String var)
    {
        t.setTypeface(getFont(c));
        t.setText(textSelectByVarname(db,var).getText());
        RadioButton tt = t;
        return t;
    }
    public static RadioButton setTextFont(RadioButton t, Context c, String var)
    {
        t.setTypeface(getFont(c));
        t.setText(var);
        RadioButton tt = t;
        return t;
    }

    public static TextView setTextFont(TextView t, Context c, String var)
    {
        t.setTypeface(getFont(c));
        t.setText(var);
        t=UIComponent.getMeasueredLine(t,c);
        TextView tt = t;
        return t;
    }

    public static Button setTextFont(Button t, Context c, String var)
    {
        t.setTypeface(getFont(c));
        t.setText(var);
        Button tt = t;
        return t;
    }

    public static EditText setTextFont(EditText t, Context c, String var)
    {
        t.setTypeface(getFont(c));
        t.setText(var);
        EditText tt = t;
        return t;
    }

    public static Button setTextFont(Button t, Context c, DBInitialization db, String var)
    {
        try {
            t.setTypeface(getFont(c));
            t.setText(textSelectByVarname(db, var).getText());
        }catch (Exception e)
        {}
        Button tt = t;
        return t;
    }

    public static String setTwoDigitDec(Double d)
    {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        float twoDigitsF = Float.valueOf(decimalFormat.format(d));
        return String.valueOf(twoDigitsF);
    }
    public static int getContrastVersionForColor(int color) {
        float[] hsv = new float[3];
        Color.RGBToHSV(Color.red(color), Color.green(color), Color.blue(color),
                hsv);
        if (hsv[2] < 0.5) {
            hsv[2] = 0.7f;
        } else {
            hsv[2] = 0.3f;
        }
        hsv[1] = hsv[1] * 0.2f;
        return Color.HSVToColor(hsv);
    }
}
