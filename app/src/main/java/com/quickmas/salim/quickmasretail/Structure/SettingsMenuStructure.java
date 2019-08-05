package com.quickmas.salim.quickmasretail.Structure;

import com.quickmas.salim.quickmasretail.Model.System.DashboardMenu;
import com.quickmas.salim.quickmasretail.Module.Dashboard.FooterActivity.Settings.Settings;

import java.util.ArrayList;

/**
 * Created by Forhad on 30/10/2018.
 */

public class SettingsMenuStructure {
    public DashboardMenu menu1 = new DashboardMenu();
    public DashboardMenu menu2 = new DashboardMenu();

    public static ArrayList<SettingsMenuStructure> setMenu(ArrayList<DashboardMenu> menus)
    {
        ArrayList<SettingsMenuStructure> menuStructures = new ArrayList<>();
        int lim = menus.size()/2;
        double div = menus.size()%2;
        if(div>0)
        {
            lim++;
        }

        for(int i=0;i<lim;i++)
        {
            final int cproduct1 = i*2;
            final int cproduct2 = i*2+1;
            SettingsMenuStructure menu = new SettingsMenuStructure();
            if(menus.size()>cproduct1)
            {
                menu.menu1 = menus.get(cproduct1);
            }
            if(menus.size()>cproduct2)
            {
                menu.menu2 = menus.get(cproduct2);
            }

            menuStructures.add(menu);
        }
        return menuStructures;
    }
}
