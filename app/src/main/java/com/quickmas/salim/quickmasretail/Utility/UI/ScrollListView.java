package com.quickmas.salim.quickmasretail.Utility.UI;

/**
 * Created by Forhad on 17/11/2018.
 */

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.arasthel.asyncjob.AsyncJob;
import com.frosquivel.scrollinfinite.ScrollInfiniteAdapter;
import com.frosquivel.scrollinfinite.ScrollInfiniteListener;
import com.quickmas.salim.quickmasretail.R;
import com.quickmas.salim.quickmasretail.Utility.UIComponent;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Objects;

import static com.quickmas.salim.quickmasretail.Utility.UI.ViewData.getViewData;
import static com.quickmas.salim.quickmasretail.Utility.UIComponent.updateListViewHeight;
import static com.quickmas.salim.quickmasretail.Utility.UIComponent.updateListViewHeightManual;


public class ScrollListView {

    public static void loadListView(final Context context,final  ListView lv,final int layout,final ArrayList<?> ojects,final String rowMethod,final int firstCountToShow,final int setByStep,Boolean showFooter,Boolean updateHeight,Boolean spacial)
    {
        if(ojects.size()==0)
        {
            lv.setAdapter(null);
            return;
        }
        final View footer = ((Activity) context).getLayoutInflater().inflate(R.layout.progessbar, null);
        final ProgressBar progressBar = (ProgressBar) footer.findViewById(R.id.progressBar);

        if(lv.getFooterViewsCount()==0 || showFooter) {
            lv.addFooterView(footer);
        }

        final ArrayList<Object> obs= new ArrayList<Object>();
        for(Object o : ojects)
        {
            obs.add((Object) o);
        }

        final ScrollInfiniteAdapter adapter = new ScrollInfiniteAdapter((Activity) context, obs, layout, firstCountToShow, setByStep) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = null;
                Object obj = getItem(position);
                if (convertView == null) {
                    view = LayoutInflater.from(getContext()).inflate(layout, null);
                } else {
                    view = convertView;
                }
                try {
                    Method m = context.getClass().getMethod(rowMethod,new Class[]{ViewData.class});
                    m.invoke(context,getViewData(context,view,obj,position,obs));
                }catch (Exception e){e.printStackTrace();}

                return view;
            }
        };

        lv.setAdapter(adapter);
        lv.setOnScrollListener(new ScrollInfiniteListener(adapter, progressBar));
        if(updateHeight)
        {
            int hfCOunt=0;
            if(lv.getHeaderViewsCount()>0)
            {
                hfCOunt++;
            }
            if(lv.getFooterViewsCount()>0)
            {
                hfCOunt++;
            }
            if(spacial)
            {
               // if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M)
                {
                   // updateListViewHeightManual(context, lv, layout,(int) Math.floor(obs.size() + hfCOunt+1));
                }
               // else
                {
                    updateListViewHeightManual(context, lv, layout,(int) Math.floor(obs.size() + hfCOunt/2));
                }

            }
            else {
                updateListViewHeightManual(context, lv, layout, obs.size() + hfCOunt);
            }
        }
    }
    public static void loadListView(final Context context,final  ListView lv,final int layout,final ArrayList<?> ojects,final String rowMethod,final int firstCountToShow,final int setByStep,Boolean showFooter)
    {
        loadListView(context,lv,layout,ojects,rowMethod,0,ojects.size(),true,false,false);
    }
    public static void loadListView(final Context context,final  ListView lv,final int layout,final ArrayList<?> ojects,String rowMethod)
    {
        loadListView(context,lv,layout,ojects,rowMethod,0,ojects.size(),true,false,false);
    }
    public static void loadListViewNoFooter(final Context context,final  ListView lv,final int layout,final ArrayList<?> ojects,String rowMethod)
    {
        loadListView(context,lv,layout,ojects,rowMethod,0,ojects.size(),false,false,false);
    }
    public static void loadListViewUpdateHeight(final Context context,final  ListView lv,final int layout,final ArrayList<?> ojects,final String rowMethod,final int firstCountToShow,final int setByStep,Boolean showFooter)
    {
        loadListView(context,lv,layout,ojects,rowMethod,0,ojects.size(),true,true,false);
    }
    public static void loadListViewUpdateHeightSpecial(final Context context,final  ListView lv,final int layout,final ArrayList<?> ojects,final String rowMethod,final int firstCountToShow,final int setByStep,Boolean showFooter)
    {
        loadListView(context,lv,layout,ojects,rowMethod,0,ojects.size(),true,true,true);
    }
}