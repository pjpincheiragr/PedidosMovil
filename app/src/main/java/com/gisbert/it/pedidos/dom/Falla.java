package com.pluscel.pluscelmovil.dom;

/**
 * Created by Pablo Pincheira on 16/12/2015.
 */
public class Falla {
    Members members;

    public Members getMembers() {
        return members;
    }

    public void setMembers(Members members) {
        this.members = members;
    }


    // nested class
    public class Members{
        DatoTipoFalla tipoFalla;
        DatoString descripcion;

        public DatoTipoFalla getTipoFalla() {
            return tipoFalla;
        }
        public void setTipoFalla(DatoTipoFalla tipoFalla){
            this.tipoFalla=tipoFalla;
        }

        public DatoString getDescripcion() {
            return descripcion;
        }
        public void setDescripcion(DatoString descripcion){
            this.descripcion=descripcion;
        }


        public class DatoString {
            String value;

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }
        }

        public class DatoTipoFalla {

            DatoTitle value;
            public DatoTitle getValue() {return value;}
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
