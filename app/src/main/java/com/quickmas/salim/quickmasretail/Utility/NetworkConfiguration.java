package com.quickmas.salim.quickmasretail.Utility;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;

import com.quickmas.salim.quickmasretail.R;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Forhad on 08/08/2018.
 */

public class NetworkConfiguration {
    public static boolean isInternetOn(Context context)
    {
        ConnectivityManager connec =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED) {
            return true;
        } else if (
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED) {
            return false;
        }
        return false;
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
}

