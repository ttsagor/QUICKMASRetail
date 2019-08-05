package com.quickmas.salim.quickmasretail.Utility;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import java.lang.reflect.Method;

/**
 * Created by Forhad on 31/07/2018.
 */

public class MyAsynctask extends AsyncTask<String, String, String> {
    public ProgressDialog progressDialog;

    public Object callBackPerObj = new Object();
    public Object callBackRunObj = new Object();
    public Object callBackPostObj = new Object();
    public Object callBackUpdateObj = new Object();

    public String callBackPerMethod = "";
    public String callBackRunMethod = "";
    public String callBackPostMethod = "";
    public String callBackUpdateMethod = "";

    public void setObj(Object ob)
    {
        callBackPerObj =ob;
        callBackRunObj =ob;
        callBackPostObj =ob;
        callBackUpdateObj=ob;
    }
    @Override
    protected String doInBackground(String... params) {
       try {
           Method method = callBackRunObj.getClass().getMethod(callBackRunMethod);
           method.invoke(callBackRunObj);
       }catch (Exception e){
           System.out.println(e.getMessage());
       }
        return "";
    }


    @Override
    protected void onPostExecute(String result) {
        try {
            Method method = callBackPostObj.getClass().getMethod(callBackPostMethod, callBackPostObj.getClass());
            method.invoke(callBackPostObj,callBackPostMethod);
        }catch (Exception e){}
    }


    @Override
    protected void onPreExecute() {
        try {
            Method method = callBackPerObj.getClass().getMethod(callBackPerMethod, callBackPerObj.getClass());
            method.invoke(callBackPerObj,callBackPerMethod);
        }catch (Exception e){}
    }

    public void updateView(String data)
    {
        publishProgress(data);
    }
    @Override
    protected void onProgressUpdate(String... text) {
        try {
            Method method = callBackUpdateObj.getClass().getMethod(callBackUpdateMethod, callBackUpdateObj.getClass());
            method.invoke(callBackUpdateObj,callBackUpdateMethod);
        }catch (Exception e){}

    }
}
