package com.quickmas.salim.quickmasretail.Utility;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import com.quickmas.salim.quickmasretail.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static com.quickmas.salim.quickmasretail.MainActivity.global_user;

/**
 * Created by Forhad on 17/03/2018.
 */

public class FileManagerSetting {
    public static String folder_name = "quickmaspos";
    public static String app_logo ="app_logo.png";
    public static String system_path ="system_path";
    public static String pos_product ="pos_product";
    public static String rtm_product ="rtm_product";
    public static String new_retail_folder ="new_retail_product";
    public static String rtm_free_product ="rtm_free_product";
    public static String rtm_crm_product ="rtm_crm_product";
    public static String rtm_posm_product ="rtm_posm_product";
    public static String rtm_mi_product ="rtm_mi_product";

    public static void deleteCache(Context context) {
        try {
            File dir = context.getCacheDir();
            deleteDir(dir);
        } catch (Exception e) {}
    }

    public static Bitmap getImageFromFileFullPath(String fileName,Context c)
    {
        String root = fileName;
        File file = new File(root);
        Bitmap bmp = null;
        if (file.exists())
        {
            bmp = BitmapFactory.decodeFile(root);
            bmp.setHasAlpha(true);
        }
        else
        {
            root = Environment.getExternalStorageDirectory().toString()+ "/"+folder_name+"/"+global_user.getBlank_img();
            file = new File(root);
            if (file.exists())
            {
                bmp = BitmapFactory.decodeFile(root);
                bmp.setHasAlpha(true);
            }
            else
            {
                Drawable myDrawable = c.getResources().getDrawable(R.drawable.no_photo);
                bmp = ((BitmapDrawable) myDrawable).getBitmap();
                bmp.setHasAlpha(true);
            }
        }
        return bmp;
    }
    public static Bitmap getImageFromFile(String fileName,Context c)
    {
        String root = Environment.getExternalStorageDirectory().toString()+ "/"+folder_name+"/"+fileName;
        File file = new File(root);
        Bitmap bmp = null;
        if (file.exists())
        {
            try {
                bmp = BitmapFactory.decodeFile(root);
                bmp.setHasAlpha(true);
            }catch (Exception e){
                bmp = BitmapFactory.decodeResource(c.getResources(),
                        R.drawable.no_photo);
            }
        }
        else
        {
            root = Environment.getExternalStorageDirectory().toString()+ "/"+folder_name+"/"+global_user.getBlank_img();
            file = new File(root);
            if (file.exists())
            {
                bmp = BitmapFactory.decodeFile(root);
                bmp.setHasAlpha(true);
            }
            else
            {
                Drawable myDrawable = c.getResources().getDrawable(R.drawable.no_photo);
                bmp = ((BitmapDrawable) myDrawable).getBitmap();
                bmp.setHasAlpha(true);
            }
        }
        return bmp;
    }



    public static Bitmap getImageFromFileBase(String root,Context c)
    {
        File file = new File(root);
        Bitmap bmp = null;
        if (file.exists())
        {
            bmp = BitmapFactory.decodeFile(root);
            bmp.setHasAlpha(true);
        }
        else
        {
            root = Environment.getExternalStorageDirectory().toString()+ "/"+folder_name+"/"+global_user.getBlank_img();
            file = new File(root);
            if (file.exists())
            {
                bmp = BitmapFactory.decodeFile(root);
                bmp.setHasAlpha(true);
            }
            else
            {
                Drawable myDrawable = c.getResources().getDrawable(R.drawable.no_photo);
                bmp = ((BitmapDrawable) myDrawable).getBitmap();
                bmp.setHasAlpha(true);
            }
        }
        return bmp;
    }

    public static Bitmap getImageFromFile(Context c,String fileName)
    {
        String root = Environment.getExternalStorageDirectory().toString()+ "/"+folder_name+"/"+fileName;
        File file = new File(root);
        Bitmap bmp = null;
        if (file.exists())
        {
            try {
                bmp = BitmapFactory.decodeFile(root);
                bmp.setHasAlpha(true);
            }catch (Exception e){
                bmp = BitmapFactory.decodeResource(c.getResources(),
                        R.drawable.no_photo);
            }
        }
        return bmp;
    }

    public static String getImageFilePath(String fileName)
    {
        return Environment.getExternalStorageDirectory().toString()+ "/"+folder_name+"/"+fileName;
    }

    public static String getImageOrBlack(String file)
    {
        String root = Environment.getExternalStorageDirectory().toString()+ "/"+folder_name+"/"+file;
        File fileP = new File(root);
        if (fileP.exists())
        {
            return Environment.getExternalStorageDirectory().toString()+ "/"+folder_name+"/"+file;
        }
        else
        {
            return Environment.getExternalStorageDirectory().toString()+ "/"+folder_name+"/"+global_user.getBlank_img();
        }
    }


    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        }
        else if(dir!= null && dir.isFile())
            return dir.delete();
        else {
            return false;
        }
    }
    public static Boolean saveImageToLocation(Context context,Bitmap bitmap,String tar)
    {
        ContextWrapper wrapper = new ContextWrapper(context);
        String root = Environment.getExternalStorageDirectory().toString();


        String[] folderStruct = tar.split("/");
        String tarPath="";
        for(int i=0;i<folderStruct.length-1;i++)
        {
            tarPath+=folderStruct[i]+"/";
        }
        File myDir = new File(tarPath.substring(0,tarPath.length()-1));
        if (!myDir.exists()) {
            myDir.mkdirs();
        }

        try {
            FileOutputStream out = new FileOutputStream(tar+".png");
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri,  proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }


    public static Boolean createFolder(Context context)
    {
        ContextWrapper wrapper = new ContextWrapper(context);
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/"+ FileManagerSetting.folder_name);

        if (!myDir.exists()) {
            myDir.mkdirs();
            return true;
        }
        return false;
    }
}
