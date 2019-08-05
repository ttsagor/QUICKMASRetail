package com.quickmas.salim.quickmasretail.Service;

import android.content.Context;
import android.net.Uri;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.quickmas.salim.quickmasretail.Model.System.User;
import com.quickmas.salim.quickmasretail.Utility.CallBack;

import java.net.URLEncoder;

/**
 * Created by Forhad on 25/03/2018.
 */

public class DataDownload {
    public static RequestQueue queue;
    public static void downloadData(final Context context,String url,final String par,final User cSystemInfo,final CallBack callBack)
    {
        String username =  Uri.encode(cSystemInfo.getUser_name());
        String password = Uri.encode(cSystemInfo.getPassword());
        String company = Uri.encode(cSystemInfo.getCompany_id());
        String location = Uri.encode(cSystemInfo.getSelected_location());
        String pos = Uri.encode(cSystemInfo.getSelected_pos());
        url += "user_name="+username+"&password="+password+"&company="+company+"&"+"&location="+location+"&pos="+pos+"&"+par;
        queue = Volley.newRequestQueue(context);
        System.out.println(url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    public void onResponse(String response) {
                        try
                        {
                            queue.getCache().clear();
                            callBack.call(response.toString(),context);
                        }
                        catch (Exception e){}

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                999999999,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        stringRequest.setShouldCache(false);
        queue.add(stringRequest);
    }
}
