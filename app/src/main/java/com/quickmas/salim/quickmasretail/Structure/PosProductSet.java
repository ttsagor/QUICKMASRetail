package com.quickmas.salim.quickmasretail.Structure;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.quickmas.salim.quickmasretail.Model.POS.PosProduct;
import com.quickmas.salim.quickmasretail.R;

import java.util.ArrayList;

/**
 * Created by Forhad on 14/11/2018.
 */

public class PosProductSet {
    PosProduct product1;
    PosProduct product2;

    public PosProductSet(PosProduct product1,PosProduct product2)
    {
        this.product1 = product1;
        this.product2 = product2;
    }


    public static ArrayList<PosProductSet> getProductSet(ArrayList<PosProduct> posProuct)
    {
        ArrayList<PosProductSet> posProductSets = new ArrayList<PosProductSet>();
        int lim = posProuct.size()/2;
        double div = posProuct.size()%2;
        if(div>0)
        {
            lim++;
        }

        for(int i=0;i<lim;i++)
        {
            final int cproduct1 = i*2;
            final int cproduct2 = i*2+1;
            PosProduct product1 = new PosProduct();
            PosProduct product2 = new PosProduct();
            if(posProuct.size()>cproduct1)
            {
                product1 = posProuct.get(cproduct1);
            }
            if(posProuct.size()>cproduct2)
            {
                product2 = posProuct.get(cproduct2);
            }
            posProductSets.add(new PosProductSet(product1,product2));
        }
        return posProductSets;
    }

    public void setProduct1(PosProduct product1) {
        this.product1 = product1;
    }

    public void setProduct2(PosProduct product2) {
        this.product2 = product2;
    }

    public PosProduct getProduct1() {
        return product1;
    }

    public PosProduct getProduct2() {
        return product2;
    }
}
