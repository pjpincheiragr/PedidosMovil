package com.gisbert.it.pedidos.actividad;

/**
 * Created by Juli on 31/3/2016.
 */
public class RowObject {

    private int ID;
    private String heading;
    private boolean firstChecked; // else second checked

    public RowObject(int iD, boolean firstChecked,String heading) {
        super();
        ID = iD;
        this.heading=heading;
        this.firstChecked = firstChecked;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public boolean isFirstChecked() {
        return firstChecked;
    }

    public void setFirstChecked(boolean firstChecked) {
        this.firstChecked = firstChecked;
    }
}
