package com.gisbert.it.pedidos.serv;

import java.util.List;

/**
 * Created by Juli on 6/3/2016.
 */
public class PedidoItems {

    List<RestLink> value;
    Result result;

    public List<RestLink> getValue() {
        return value;
    }

    public void setLinks(List<RestLink> links) {
        this.value = value;
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
