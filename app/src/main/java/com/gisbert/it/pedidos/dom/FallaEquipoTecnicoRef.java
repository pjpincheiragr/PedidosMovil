package com.pluscel.pluscelmovil.dom;

/**
 * Created by Pablo Pincheira on 16/12/2015.
 */
public class FallaEquipoTecnicoRef {
    Members members;

    public Members getMembers() {
        return members;
    }

    public void setMembers(Members members) {
        this.members = members;
    }


    // nested class
    public class Members{
        DatoOrden ordenServicio;
        DatoString fechaHora;
        DatoTecnico tecnico;
        DatoFalla falla;
        DatoString estado;

        public DatoOrden getOrdenServicio() {
            return ordenServicio;
        }
        public void setOrdenServicio(DatoOrden ordenServicio){
            this.ordenServicio=ordenServicio;
        }

        public DatoString getFechaHora() {
            return fechaHora;
        }
        public void setFechaHora(DatoString fecha){
            this.fechaHora=fecha;
        }


        public void setTecnico(DatoTecnico tecnico) {
            this.tecnico = tecnico;
        }
        public DatoTecnico getTecnico() {return tecnico;}


        public void setFalla(DatoFalla falla) {this.falla = falla;}
        public DatoFalla getFalla() { return falla;}


        public DatoString getEstado() {return estado;}
        public void setEstado(DatoString estado){ this.estado=estado;}
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

        public class DatoTecnico {


            DatoHref value;
            public DatoHref getValue() {return value;}
            public void setValue(DatoHref value) {
                this.value = value;
            }

            public class DatoHref {
                String href;

                public String getHref() { return href;}

                public void setHref(String href) {this.href = href;}
            }
        }


        public class DatoOrden {


            DatoHref value;
            public DatoHref getValue() {return value;}
            public void setValue(DatoHref value) {
                this.value = value;
            }

            public class DatoHref {
                String href;

                public String getHref() { return href;}

                public void setHref(String href) {this.href = href;}
            }
        }
        public class DatoFalla {

            DatoHref value;
            public DatoHref getValue() {return value;}
            public void setValue(DatoHref value) {
                this.value = value;
            }

            public class DatoHref {
                String href;

                public String getHref() { return href;}

                public void setHref(String href) {this.href = href;}
            }
        }



    }
}
