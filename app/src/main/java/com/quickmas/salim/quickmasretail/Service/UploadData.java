package com.quickmas.salim.quickmasretail.Service;

import android.content.Context;
import android.net.Uri;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Forhad on 14/07/2018.
 */

public class UploadData {
    public ArrayList<?> data = new ArrayList<>();
    public Map<String,String> map = new HashMap<>();
    public Object fixedParameter = new Object();
    public String url;
    public int counter=0;
    public Context context;
    public Object callbackCbjEach;
    public String callbackMethEach;
    public Object callbackCbjFinal;
    public String callbackMethFinal;
    public ArrayList<String> downloadedData = new ArrayList<String>();
    public int method = Request.Method.GET;
    public void uploaddata(Context context,Object callbackCbjEach,String callbackMethEach,Object callbackCbjFinal,String callbackMethFinal)
    {
        this.context = context;
        this.callbackCbjEach = callbackCbjEach;
        this.callbackMethEach = callbackMethEach;
        this.callbackCbjFinal = callbackCbjFinal;
        this.callbackMethFinal = callbackMethFinal;
        this.dataDownload();
    }

    void each(String res)
    {
        this.downloadedData.add(res);
        if(this.counter < this.data.size()-1)
        {
            if(!this.callbackMethEach.equals(""))
            {
                try {
                    Method method = this.callbackCbjEach.getClass().getMethod(this.callbackMethEach, Integer.TYPE, String.class);
                    method.invoke(this.callbackMethEach, this.callbackMethEach);
                }catch (Exception e){System.out.println(e.getMessage());}
            }
            this.counter++;
            dataDownload();
        }
        else
        {
            complete();
        }
    }
    void complete()
    {
        System.out.println("final method");
        if(!this.callbackMethFinal.equals(""))
        {
            try {
                Method method = this.callbackCbjFinal.getClass().getMethod(this.callbackMethFinal, ArrayList.class);
                method.invoke(this.callbackCbjFinal, this.downloadedData);
            }catch (Exception e){System.out.println(e.getMessage());}
        }
    }
    void dataDownload()
    {
        if(data.size()>0) {
            RequestQueue queue = Volley.newRequestQueue(this.context);
            String turl = this.url + this.parameterGenerator();
            System.out.println(turl);
            StringRequest stringRequest = new StringRequest(method, turl,
                    new Response.Listener<String>() {
                        public void onResponse(String response) {
                            each(response);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println(error.toString());
                    Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
                    each("");
                }
            });
            queue.add(stringRequest);
        }
        else
        {
            complete();
        }
    }

    String parameterGenerator()
    {
        String output="";
        Field[] fields = this.fixedParameter.getClass().getDeclaredFields();
        for (Field f : fields)
        {
            if(this.map.get(f.getName())!=null)
            {
                if(this.map.get(f.getName())!="") {
                    try {
                        output += this.map.get(f.getName()) + "=" + Uri.encode(f.get(this.fixedParameter).toString())+"&";
                    } catch (Exception e) {System.out.println(e.getMessage());}
                }
            }
            else
            {
                try {
                    output += f.getName() + "=" + Uri.encode(f.get(this.fixedParameter).toString())+"&";
                } catch (Exception e) {System.out.println(e.getMessage());}
            }
        }

        fields = this.data.get(this.counter).getClass().getDeclaredFields();
        for (Field f : fields)
        {
            if(this.map.get(f.getName())!=null)
            {
                if(this.map.get(f.getName())!="") {
                    try {
                        output += this.map.get(f.getName()) + "=" + Uri.encode(f.get(this.data.get(counter)).toString())+"&";
                    } catch (Exception e) {System.out.println(e.getMessage());}
                }
            }
            else
            {
                try {
                    output += f.getName() + "=" + Uri.encode(f.get(this.data.get(counter)).toString())+"&";
                } catch (Exception e) {System.out.println(e.getMessage());}
            }
        }
        return output;
    }
}
