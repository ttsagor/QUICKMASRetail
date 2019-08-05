package com.quickmas.salim.quickmasretail.Service;

import java.net.URLEncoder;

/**
 * Created by Forhad on 22/04/2018.
 */

public class UncodeAndDecode {

    public static String uncode(String text)
    {
        return  URLEncoder.encode(text);
    }
}
