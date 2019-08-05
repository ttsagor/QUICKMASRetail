package com.quickmas.salim.quickmasretail.Utility;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Created by Forhad on 14/07/2018.
 */

public class DebugHelper {
    public static String print(Object ob)
    {
        String output="";
        Field[] fields = ob.getClass().getDeclaredFields();
        for (Field f : fields)
        {
            try {
                output += f.getName() + ":" + f.get(ob) + "; ";
            }catch (Exception e){System.out.println(e.getMessage());}
        }
        System.out.println(output);
        return output;
    }
    public static String print(ArrayList<?> obs)
    {
        String output="";
        for(Object ob : obs)
        {
            output+=print(ob);
        }
        return output;
    }
    public static String printA(ArrayList<?> obs)
    {
        String output="";
        for(Object ob : obs)
        {
            System.out.println(String.valueOf(ob));
        }
        return output;
    }
}
