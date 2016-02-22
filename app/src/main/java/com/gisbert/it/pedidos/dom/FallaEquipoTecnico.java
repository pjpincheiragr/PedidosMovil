package com.pluscel.pluscelmovil.dom;

/**
 * Created by Pablo Pincheira on 15/12/2015.
 */
public class FallaEquipoTecnico {
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



        public class DatoTecnico {

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


        public class DatoOrden {

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
        public class DatoString {
            String value;

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }
        }
        public class DatoFalla {
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
