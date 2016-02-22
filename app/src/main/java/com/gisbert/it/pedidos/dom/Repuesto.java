package com.pluscel.pluscelmovil.dom;

/**
 * Created by Pablo Pincheira on 21/11/2015.
 */
public class Repuesto {



    Members members;

    public Members getMembers() {
        return members;
    }

    public void setMembers(Members members) {
        this.members = members;
    }


    // nested class
    public class Members{

        DatoString tipoRepuesto;
        DatoString modelo;
        DatoString descripcion;
        DatoString fechaArribo;
        DatoString costo;
        DatoString cantidad;


        public DatoString getTipoRepuesto() {
            return tipoRepuesto;
        }

        public DatoString getModelo() {
            return modelo;
        }

        public DatoString getDescripcion() {
            return descripcion;
        }

        public DatoString getFechaArribo() {
            return fechaArribo;
        }
        public DatoString getCosto() {
            return costo;
        }
        public DatoString getCantidad() {
            return cantidad;
        }


        // nested classes

        public class DatoString {
            String value;

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }
        }


        public class DatoTipoRespuesto {

            DatoTitle value;

            public DatoTitle getValue() {
                return value;
            }

            public void setValue(DatoTitle value) {
                this.value = value;
            }

            public class DatoTitle {
                String title;

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }
            }
        }



    }
}
