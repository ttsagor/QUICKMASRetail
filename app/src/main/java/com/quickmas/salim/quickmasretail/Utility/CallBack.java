package com.quickmas.salim.quickmasretail.Utility;

import android.content.Context;

import org.json.JSONArray;

import java.lang.reflect.Method;

/**
 * Created by Forhad on 25/03/2018.
 */

public class CallBack {

    Method m;
    Object o;

    public CallBack(String className, String methodName)
    {
        try {
            Class[] paramTypes = new Class[2];
            paramTypes[0] = String.class;
            paramTypes[1] = Context.class;
            String funClass = "com.quickmas.salim.quickmasretail."+className;
            Class c = Class.forName(funClass);
            o = c.newInstance();
            m = c.getDeclaredMethod(methodName, paramTypes);
        }catch (Exception e){
            System.out.println(e.toString());
        }
    }

    public void call(Object respose,Object db)
    {
        try {
            m.invoke(o, respose,db);
        }catch (Exception e){
            System.out.println(e.toString());
        }
    }

}
