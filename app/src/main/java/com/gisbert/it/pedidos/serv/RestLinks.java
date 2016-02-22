package com.pluscel.pluscelmovil.serv;

import java.util.List;

/**
 * Created by Pablo Pincheira on 23/10/2015.
 */
public class RestLinks {

    List<RestLink> links;
    Object extensions;

    public List<RestLink> getLinks() {
        return links;
    }

    public void setLinks(List<RestLink> links) {
        this.links = links;
    }

    public Object getExtensions() {
        return extensions;
    }

    public void setExtensions(Object extensions) {
        this.extensions = extensions;
    }
}
