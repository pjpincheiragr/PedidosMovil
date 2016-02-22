package com.pluscel.pluscelmovil.serv;

import java.util.List;

/**
 * Created by Pablo Pincheira on 15/12/2015.
 */
public class FallaEquipoTecnicos {
    List<RestLink> links;
    Result result;

    public List<RestLink> getLinks() {
        return links;
    }

    public void setLinks(List<RestLink> links) {
        this.links = links;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public class Result{

        List<RestLink> value;

        public List<RestLink> getValue() {
            return value;
        }

        public void setValue(List<RestLink> value) {
            this.value = value;
        }
    }
}
