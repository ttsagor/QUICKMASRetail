package com.quickmas.salim.quickmasretail.Structure;

/**
 * Created by Forhad on 07/07/2018.
 */

public class MenuNameLink {
    public String name;
    public String link;

    public MenuNameLink(String name,String link)
    {
       this.name = name;
       this.link = link;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public String getLink() {
        return link;
    }
}
