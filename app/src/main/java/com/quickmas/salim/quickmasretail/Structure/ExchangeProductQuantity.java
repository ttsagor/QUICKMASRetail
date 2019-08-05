package com.quickmas.salim.quickmasretail.Structure;

import com.quickmas.salim.quickmasretail.Model.POS.PosInvoice;

import java.util.ArrayList;

/**
 * Created by Forhad on 15/09/2018.
 */

public class ExchangeProductQuantity {
    public PosInvoice posInvoice;
    public String name;
    public int quantity;
    public int exchange;
    public double amount;

    public static int getExchangeCount(ArrayList<ExchangeProductQuantity> exchangeProductQuantities){
        int count=0;
        for(ExchangeProductQuantity exchangeProductQuantity : exchangeProductQuantities) {
            count+=exchangeProductQuantity.exchange;
        }
        return count;
    }
    public static double getExchangeTotalAmount(ArrayList<ExchangeProductQuantity> exchangeProductQuantities){
        double count=0;
        for(ExchangeProductQuantity exchangeProductQuantity : exchangeProductQuantities) {
            count+=exchangeProductQuantity.amount;
        }
        return count;
    }
}
