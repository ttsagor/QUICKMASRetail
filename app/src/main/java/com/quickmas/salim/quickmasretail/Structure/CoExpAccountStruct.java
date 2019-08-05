package com.quickmas.salim.quickmasretail.Structure;

import com.quickmas.salim.quickmasretail.Model.POS.CoAccount;
import com.quickmas.salim.quickmasretail.Model.POS.CoExpAccount;

import java.util.ArrayList;

/**
 * Created by Forhad on 03/09/2018.
 */

public class CoExpAccountStruct {
    public String account_type;
    public ArrayList<String> account_name;

    public static ArrayList<CoExpAccountStruct> getAccount(ArrayList<CoExpAccount> coAccounts)
    {
        ArrayList<CoExpAccountStruct> coAccountStructs = new ArrayList<>();
        ArrayList<String> account_type = new ArrayList<>();
        for(CoExpAccount coAccount : coAccounts)
        {
            if(!account_type.contains(coAccount.getAccount_type())){
                account_type.add(coAccount.getAccount_type());

                CoExpAccountStruct accountStruct = new CoExpAccountStruct();
                accountStruct.account_type = coAccount.getAccount_type();
                accountStruct.account_name = getAccount_name(coAccounts,accountStruct.account_type);
                coAccountStructs.add(accountStruct);
            }
        }
        return coAccountStructs;
    }

    public static ArrayList<String> getAccount_name(ArrayList<CoExpAccount> coAccounts,String type)
    {
        ArrayList<String> account_name = new ArrayList<>();
        for(CoExpAccount coAccount : coAccounts)
        {
            if(coAccount.getAccount_type().equals(type) && !account_name.contains(coAccount.getAccount_name()))
            {
                account_name.add(coAccount.getAccount_name());
            }
        }
        return account_name;
    }
}
