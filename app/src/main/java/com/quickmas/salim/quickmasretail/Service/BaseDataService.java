package com.quickmas.salim.quickmasretail.Service;

/**
 * Created by Forhad on 13/12/2018.
 */
import android.content.Context;
import android.net.Uri;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.android.volley.Request;
import com.android.volley.toolbox.Volley;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.quickmas.salim.quickmasretail.MainActivity;
import com.quickmas.salim.quickmasretail.Model.System.User;

import org.json.JSONArray;
import org.json.JSONObject;



public class BaseDataService {
    public Context context;
    public String url;
    public Map<String,String> parameter =  new HashMap<String,String>();
    public User parameterdataClass = new User();
    public ArrayList<String>  parameterExclueded = new ArrayList<String>();
    public Map<String,String> parametervariableMapping =  new HashMap<String,String>();
    public int callType = Request.Method.GET;
    public Object outputToModel = new Object();
    public Map<String,String> outputModelVariableMap =  new HashMap<String,String>();
    public ArrayList<String> parameterfixedExcluded = new ArrayList<String>(
            Arrays.asList("__PRIMARYKEY",
                    "__AUTOINCREMENT",
                    "__UNIQUE",
                    "__CONDITION",
                    "__NONDBDATA"
            ));
    public String generateURL()
    {
        //this.url+="?";
        String username = Uri.encode(parameterdataClass.getUser_name());
        String password = Uri.encode(parameterdataClass.getPassword());
        String company = Uri.encode(parameterdataClass.getCompany_id());
        String location = Uri.encode(parameterdataClass.getSelected_location());
        String pos = Uri.encode(parameterdataClass.getSelected_pos());
        this.url += "user_name="+username+"&password="+password+"&company="+company+"&"+"&location="+location+"&pos="+pos+"&"+"&selected_pos="+pos+"&";
        ArrayList<Object> __PARAMETERMODEL = new ArrayList<Object>(
                Arrays.asList(
                        new Object()
                ));

        for(Object currentObject : __PARAMETERMODEL)
        {
            for (Field f : currentObject.getClass().getDeclaredFields())
            {
                try {
                    ArrayList<String> exList = (ArrayList<String>)  currentObject.getClass().getDeclaredField("parameterExclueded").get(currentObject);
                    if(!exList.contains(f.getName()))
                    {
                        try {
                            Map<String, String> varMap = (Map<String, String>) currentObject.getClass().getDeclaredField("variableMapping").get(currentObject);
                            if (varMap.get(f.getName()) != null) {
                                this.parameter.put(varMap.get(f.getName()), currentObject.getClass().getDeclaredField(f.getName()).get(currentObject).toString());
                            } else {
                                this.parameter.put(f.getName(), currentObject.getClass().getDeclaredField(f.getName()).get(currentObject).toString());
                            }
                        }catch (Exception ex)
                        {
                            this.parameter.put(f.getName(), currentObject.getClass().getDeclaredField(f.getName()).get(currentObject).toString());
                        }
                    }
                }catch (Exception e){
                    try {
                        try {
                            Map<String, String> varMap = (Map<String, String>) currentObject.getClass().getDeclaredField("variableMapping").get(currentObject);
                            if (varMap.get(f.getName()) != null) {
                                this.parameter.put(varMap.get(f.getName()), currentObject.getClass().getDeclaredField(f.getName()).get(currentObject).toString());
                            } else {
                                this.parameter.put(f.getName(), currentObject.getClass().getDeclaredField(f.getName()).get(currentObject).toString());
                            }
                        }catch (Exception ex)
                        {
                            this.parameter.put(f.getName(), currentObject.getClass().getDeclaredField(f.getName()).get(currentObject).toString());
                        }
                    }catch (Exception ex){}
                }
            }
        }

        try {
            parameterfixedExcluded = this.parameterfixedExcluded;
        }catch (Exception e)
        {
            parameterfixedExcluded = parameterfixedExcluded;
        }
        for (Field f : parameterdataClass.getClass().getDeclaredFields())
        {
            if(parameterfixedExcluded.contains(f.getName()))
            {continue;}
            try {
                if(!this.parameterExclueded.contains(f.getName())) {
                    try {
                        if (this.parametervariableMapping.get(f.getName()) != null) {
                            this.parameter.put(this.parametervariableMapping.get(f.getName()), parameterdataClass.getClass().getDeclaredField(f.getName()).get(parameterdataClass).toString());
                        } else {
                            this.parameter.put(f.getName(), parameterdataClass.getClass().getDeclaredField(f.getName()).get(parameterdataClass).toString());
                        }
                    }catch (Exception e){
                        this.parameter.put(f.getName(), parameterdataClass.getClass().getDeclaredField(f.getName()).get(parameterdataClass).toString());
                    }
                }
            }
            catch (Exception e)
            {
                try {
                    try {
                        if (this.parametervariableMapping.get(f.getName()) != null) {
                            this.parameter.put(this.parametervariableMapping.get(f.getName()), parameterdataClass.getClass().getDeclaredField(f.getName()).get(parameterdataClass).toString());
                        } else {
                            this.parameter.put(f.getName(), parameterdataClass.getClass().getDeclaredField(f.getName()).get(parameterdataClass).toString());
                        }
                    }catch (Exception ex){
                        this.parameter.put(f.getName(), parameterdataClass.getClass().getDeclaredField(f.getName()).get(parameterdataClass).toString());
                    }
                }catch (Exception ex){}
            }
        }
        try {
            for (Map.Entry<String, String> entry : this.parameter.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                this.url += key + "=" + Uri.encode(value) + "&";
            }
        }catch (Exception e){}
        return this.url;
    }
    public void getDataFromURLModel(final Object callBackClass,final String callBackMethod)
    {
        getDataFromURL(callBackClass,callBackMethod,"model");
    }
    public void getDataFromURLString(final Object callBackClass,final String callBackMethod)
    {
        getDataFromURL(callBackClass,callBackMethod,"string");
    }
    public void getDataFromURL(final Object callBackClass,final String callBackMethod,final String output)
    {
        generateURL();
        System.out.println(this.url);
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(callType, this.url,
                new Response.Listener<String>() {
                    public void onResponse(String response) {
                        if(output.toUpperCase().equals("string".toUpperCase()))
                        {
                            try
                            {
                                Method m = callBackClass.getClass().getMethod(callBackMethod, new Class[]{String.class});
                                m.invoke(callBackClass, response);
                            }
                            catch (Exception e){
                                System.out.println("error: "+e.getMessage());
                            }
                        }
                        else if(output.toUpperCase().equals("model".toUpperCase()))
                        {
                            dataToModel(response,callBackClass,callBackMethod);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.toString());
                Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
            }
        });
        queue.add(stringRequest);
    }

    public void dataToModel(String data,final Object callBackClass,final String callBackMethod)
    {
        ArrayList<Object> models = new ArrayList<>();
        try {
            JSONArray jr = new JSONArray("["+data.toString()+"]");
            if(data.toString().charAt(0)=='[')
            {
                jr = new JSONArray(data.toString());
            }
            for (int i = 0; i < jr.length(); i++)
            {
                Object baseModel = this.outputToModel.getClass().newInstance();
                JSONObject jb = (JSONObject) jr.getJSONObject(i);
                for (Field f : this.outputToModel.getClass().getDeclaredFields()) {
                    try {
                        if (this.outputModelVariableMap.get(f.getName()) != null) {
                            if (!this.outputModelVariableMap.get(f.getName()).equals("")) {
                                f.set(baseModel,toObject(f.getGenericType().toString(),jb.get(this.outputModelVariableMap.get(f.getName())).toString().trim()));
                            }
                        } else {
                            f.set(baseModel,toObject(f.getGenericType().toString(),jb.get(f.getName()).toString().trim()));
                        }
                    }catch (Exception e){System.out.println("Error: "+f.getName()+"-"+e.getMessage());}
                }
                models.add(baseModel);
            }
            try
            {
                Method m = callBackClass.getClass().getMethod(callBackMethod, new Class[]{ArrayList.class,Context.class});
                m.invoke(callBackClass, models,this.context);
            }
            catch (Exception e){
                System.out.println(e.getMessage());
            }
        }catch (Exception e){System.out.println(e.toString());}
    }

    public static ArrayList<Object> dataToModel(String data,final Object callBackClass)
    {
        ArrayList<Object> models = new ArrayList<>();
        try {
            JSONArray jr = new JSONArray("["+data.toString()+"]");
            if(data.toString().charAt(0)=='[')
            {
                jr = new JSONArray(data.toString());
            }
            for (int i = 0; i < jr.length(); i++)
            {
                Object baseModel = callBackClass.getClass().newInstance();
                JSONObject jb = (JSONObject) jr.getJSONObject(i);
                for (Field f : callBackClass.getClass().getDeclaredFields()) {
                    try {
                        f.set(baseModel,toObject(f.getGenericType().toString(),jb.get(f.getName()).toString().trim()));
                    }catch (Exception e){System.out.println("Error: "+f.getName()+"-"+e.getMessage());}
                }
                models.add(baseModel);
            }
        }catch (Exception e){System.out.println(e.toString());}
        return models;
    }

    public static String dbDataTypeSelection(String type)
    {
        type = type.toUpperCase();
        if("Boolean".toUpperCase().equals(type)){ return "TEXT";}
        if("byte".toUpperCase().equals(type)){ return "INTEGER";}
        if("short".toUpperCase().equals(type)){ return "INTEGER";}
        if("int".toUpperCase().equals(type)){ return "INTEGER";}
        if("long".toUpperCase().equals(type)){ return "INTEGER";}
        if("float".toUpperCase().equals(type)){ return "REAL";}
        if("double".toUpperCase().equals(type)){ return "REAL";}
        return "TEXT";
    }
    public static String dbDataTypeCondition(String type)
    {
        type = type.toUpperCase();
        if("Boolean".toUpperCase().equals(type)){ return "'";}
        if("byte".toUpperCase().equals(type)){ return "";}
        if("short".toUpperCase().equals(type)){ return "";}
        if("int".toUpperCase().equals(type)){ return "";}
        if("long".toUpperCase().equals(type)){ return "";}
        if("float".toUpperCase().equals(type)){ return "";}
        if("double".toUpperCase().equals(type)){ return "";}
        return "'";
    }
    public static Object toObject( String clazz, String value ) {
        clazz = clazz.toUpperCase();
        if("Boolean".toUpperCase().equals(clazz)){ return Boolean.parseBoolean(value);}
        if("byte".toUpperCase().equals(clazz)){ return Byte.parseByte(value);}
        if("short".toUpperCase().equals(clazz)){ return Short.parseShort(value);}
        if("int".toUpperCase().equals(clazz)){ return Integer.parseInt(value);}
        if("long".toUpperCase().equals(clazz)){ return Long.parseLong(value);}
        if("float".toUpperCase().equals(clazz)){ return Float.parseFloat(value);}
        if("double".toUpperCase().equals(clazz)){ return Double.parseDouble(value);}
        return value;
    }
    public static Object toObject( Class clazz, String value ) {

        if( Boolean.class == clazz ) return Boolean.parseBoolean( value );
        if( Byte.class == clazz ) return Byte.parseByte( value );
        if( Short.class == clazz ) return Short.parseShort( value );
        if( Integer.class == clazz ) return Integer.parseInt( value );
        if( Long.class == clazz ) return Long.parseLong( value );
        if( Float.class == clazz ) return Float.parseFloat( value );
        if( Double.class == clazz ) return Double.parseDouble( value );
        return value;
    }
}