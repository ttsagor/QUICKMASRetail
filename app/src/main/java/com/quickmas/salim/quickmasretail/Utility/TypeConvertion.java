package com.quickmas.salim.quickmasretail.Utility;

/**
 * Created by Forhad on 07/07/2018.
 */

public class TypeConvertion {
    public static double parseDouble(String data)
    {
        try {
            if (data.trim().equals("")) {
                return 0.0;
            } else {
                return Double.parseDouble(data);
            }
        }catch (Exception e){return 0.0;}
    }
    public static int parseInt(String data)
    {
        try {

            if (data.trim().equals("")) {
                return 0;
            } else {
                return Integer.parseInt(data);
            }
        }catch (Exception e){return 0;}
    }
    public static String numberToWord(Integer i) {
        String[] units = {"Zero","One","Two","Three","Four",
                "Five","Six","Seven","Eight","Nine","Ten",
                "Eleven","Twelve","Thirteen","Fourteen","Fifteen",
                "Sixteen","Seventeen","Eighteen","Nineteen"};
        String[] tens = {"","","Twenty","Thirty","Forty","Fifty",
                "Sixty","Seventy","Eighty","Ninety"};
        if( i < 20)  return units[i];
        if( i < 100) return tens[i/10] + ((i % 10 > 0)? " " + numberToWord(i % 10):"");
        if( i < 1000) return units[i/100] + " Hundred" + ((i % 100 > 0)?" and " + numberToWord(i % 100):"");
        if( i < 1000000) return numberToWord(i / 1000) + " Thousand " + ((i % 1000 > 0)? " " + numberToWord(i % 1000):"") ;
        return numberToWord(i / 1000000) + " Million " + ((i % 1000000 > 0)? " " + numberToWord(i % 1000000):"") ;
    }
}
