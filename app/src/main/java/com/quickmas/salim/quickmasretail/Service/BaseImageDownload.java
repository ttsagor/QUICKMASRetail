package com.quickmas.salim.quickmasretail.Service;


import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.webkit.URLUtil;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.quickmas.salim.quickmasretail.Model.POS.PosInvoice;
import com.quickmas.salim.quickmasretail.Model.POS.PosInvoiceHead;
import com.quickmas.salim.quickmasretail.Model.POS.PosProduct;
import com.quickmas.salim.quickmasretail.Module.Dashboard.MainActivity;
import com.quickmas.salim.quickmasretail.Module.Loading.Loading;
import com.quickmas.salim.quickmasretail.Structure.PhotoPathTarDir;
import com.quickmas.salim.quickmasretail.Utility.CallBack;
import com.quickmas.salim.quickmasretail.Utility.FileManagerSetting;
import com.quickmas.salim.quickmasretail.Utility.TypeConvertion;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.spec.ECField;
import java.util.ArrayList;
import java.util.List;

import static com.quickmas.salim.quickmasretail.Service.ApiSettings.url_pos_invoice_details_discount;
import static com.quickmas.salim.quickmasretail.Service.ApiSettings.url_pos_invoice_details_invoice_id;
import static com.quickmas.salim.quickmasretail.Service.ApiSettings.url_pos_invoice_details_product_name;
import static com.quickmas.salim.quickmasretail.Service.ApiSettings.url_pos_invoice_details_quantity;
import static com.quickmas.salim.quickmasretail.Service.ApiSettings.url_pos_invoice_details_tax;
import static com.quickmas.salim.quickmasretail.Service.ApiSettings.url_pos_invoice_details_unit_price;

/**
* Created by Forhad on 25/03/2018.
 */

public class BaseImageDownload extends AsyncTask<URL,Integer,List<Bitmap>> {
    Context context;
    int counter=0;
    ArrayList<PhotoPathTarDir> paths = new ArrayList<PhotoPathTarDir>();
    Object callBackObject;
    String callBackEach;
    String callBackFinal;
    RequestQueue requestQueue;
    public BaseImageDownload(){}
    BaseImageDownload(final Context context, ArrayList<PhotoPathTarDir> paths, final Object callBackObject,final String callBackEach, final String callBackFinal)
    {
        this.context = context;
        counter= 0;
        this.paths = paths;
        this.callBackObject = callBackObject;
        this.callBackEach = callBackEach;
        this.callBackFinal = callBackFinal;
    }
    public void downloadData(final Context context, ArrayList<PhotoPathTarDir> paths, final Object callBackObject,final String callBackEach, final String callBackFinal)
    {
        this.context = context;
        counter= 0;
        this.paths = paths;
        this.callBackObject = callBackObject;
        this.callBackEach = callBackEach;
        this.callBackFinal = callBackFinal;
        startDownload();

//        doInBackground();
    }
    public void downloadData(final Context context, PhotoPathTarDir path,final Object callBackObject, final String callBackEach, final String callBackFinal)
    {
        this.context = context;
        counter= 0;
        ArrayList<PhotoPathTarDir> newPath = new ArrayList<>();
        newPath.add(path);
        this.paths = newPath;
        this.callBackObject = callBackObject;
        this.callBackEach = callBackEach;
        this.callBackFinal = callBackFinal;
        startDownload();

//        doInBackground();
    }
    void startDownload()
    {
        AsyncTask mMyTask = new BaseImageDownload( context, paths,callBackObject, callBackEach, callBackFinal)
                .execute(

                );
    }



    // Before the tasks execution
    protected void onPreExecute(){
        // Display the progress dialog on async task start
        //mProgressDialog.show();
        //mProgressDialog.setProgress(0);
    }

    // Do the task in background/non UI thread
    protected List<Bitmap> doInBackground(URL...urls){
        int count = paths.size();
        //URL url = urls[0];
        HttpURLConnection connection = null;
        List<Bitmap> bitmaps = new ArrayList<>();

        // Loop through the urls
        for(int i=0;i<count;i++){

            // So download the image from this url
            try{

                if(!URLUtil.isValidUrl(paths.get(i).getUrl()))
                {
                    continue;
                }
                URL currentURL = stringToURL(paths.get(i).getUrl());
                // Initialize a new http url connection
                connection = (HttpURLConnection) currentURL.openConnection();

                // Connect the http url connection
                connection.connect();
                try {
                    // Get the input stream from http url connection
                    InputStream inputStream = connection.getInputStream();

                    // Initialize a new BufferedInputStream from InputStream
                    BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);

                    // Convert BufferedInputStream to Bitmap object
                    Bitmap bmp = BitmapFactory.decodeStream(bufferedInputStream);

                    Uri imageInternalUri = saveImageToInternalStorage(bmp,paths.get(i).getTarget());
                    Method m = callBackObject.getClass().getMethod(callBackEach, new Class[]{String.class,Context.class});
                    m.invoke(callBackObject, String.valueOf(i+1),this.context);
                    // Add the bitmap to list
                    // bitmaps.add(bmp);
                }catch (Exception e){
                    e.printStackTrace();
                }
                finally {

                }
                // Publish the async task progress
                // Added 1, because index start from 0
                //publishProgress((int) (((i+1) / (float) count) * 100));
                if(isCancelled()){
                    break;
                }

            }catch(IOException e){
                e.printStackTrace();
            }finally{

                try {
                    connection.disconnect();
                }catch (Exception e){}
            }
        }
        // Return bitmap list
        return bitmaps;
    }

    // On progress update
    protected void onProgressUpdate(Integer... progress){
        // Update the progress bar
        //mProgressDialog.setProgress(progress[0]);
    }

    // On AsyncTask cancelled
    protected void onCancelled(){
        //  Snackbar.make(mCLayout,"Task Cancelled.",Snackbar.LENGTH_LONG).show();
    }

    // When all async task done
    protected void onPostExecute(final List<Bitmap> result){
        try {
            Method m = callBackObject.getClass().getMethod(callBackFinal, new Class[]{String.class, Context.class});
            m.invoke(callBackObject, String.valueOf("Done"), this.context);
        }catch (Exception e){}
        /*new Thread(new Runnable()
        {
            public void run() {
                for(int i=0;i<result.size();i++){
                    Bitmap bitmap = result.get(i);
                    Uri imageInternalUri = saveImageToInternalStorage(bitmap,paths.get(i).getTarget());
                }
                Loading.wait_text.post(new Runnable() {
                    public void run() {

                    }
                });
            }
        }).start();*/

    }


    // Custom method to convert string to url
    protected URL stringToURL(String urlString){
        try{
            URL url = new URL(urlString);
            return url;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    Uri saveImageToInternalStorage(Bitmap bitmap,String tar){
        ContextWrapper wrapper = new ContextWrapper(context);
        String root = Environment.getExternalStorageDirectory().toString();
        String path = root + "/"+ FileManagerSetting.folder_name;

        String[] imagePath = tar.split("/");
        if(imagePath.length>1)
        {
            for(int i=0;i<imagePath.length-1;i++)
            {
                path+="/"+imagePath[i];
            }
            tar = imagePath[imagePath.length-1];
        }

        File myDir = new File(path);
        if (!myDir.exists()) {
            myDir.mkdirs();
        }

        myDir = new File(myDir, tar+".png");
        try{
            OutputStream stream = null;
            stream = new FileOutputStream(myDir);
            bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
            stream.flush();
            stream.close();

        }catch (IOException e)
        {
            e.printStackTrace();
        }
        Uri savedImageURI = Uri.parse(myDir.getAbsolutePath());
        return savedImageURI;
    }
}

