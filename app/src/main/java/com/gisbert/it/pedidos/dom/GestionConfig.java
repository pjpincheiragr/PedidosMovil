package com.gisbert.it.pedidos.dom;

public class GestionConfig {

    String user;
    String pass;
    String urlRestful;
    String cadete;
    Boolean save;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getUrlRestful() {
        return urlRestful;
    }

    public void setUrlRestful(String urlRestful) {
        this.urlRestful = urlRestful;
    }

    public String getCadete() {
        return cadete;
    }

    public void setCadete(String cadete) {
        this.cadete = cadete;
    }

    public Boolean getSave() {
        return save;
    }

    public void setSave(Boolean save) {
        this.save = save;
    }
}
