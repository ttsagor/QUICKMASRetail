package com.quickmas.salim.quickmasretail.Utility.UI;

import android.content.Context;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by Forhad on 17/11/2018.
 */

public class ViewData {
    public Context context;
    public View view;
    public Object object;
    public int position;
    public ArrayList<Object> objects;
    ViewData(Context context,View view,Object object,int position,ArrayList<Object> objects)
    {
        this.context = context;
        this.view = view;
        this.object = object;
        this.objects = objects;
        this.position = position;
    }
    public static ViewData getViewData(Context context,View view,Object object,int position,ArrayList<Object> objects)
    {
        return new ViewData(context,view,object,position,objects);
    }
}
