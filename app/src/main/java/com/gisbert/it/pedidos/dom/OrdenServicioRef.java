package com.pluscel.pluscelmovil.dom;

/**
 * Created by Pablo Pincheira on 11/12/2015.
 */
public class OrdenServicioRef {

    Members members;

    public Members getMembers() {
        return members;
    }

    public void setMembers(Members members) {
        this.members = members;
    }


    // nested class
    public class Members{
        DatoString numero;
        DatoTecnicoRef tecnico;
        DatoClienteRef cliente;
        DatoEquipoRef equipo;
        DatoString fechaHora;
        DatoString falla;
        DatoString importe;
        DatoString comisionTecnico;
        DatoString estado;
        DatoString garantia;
        DatoString EnviarAlertaSinArreglo;
        DatoString EnviarAlertaTecnico;

        public DatoString getNumero() {
            return numero;
        }
        public void setTecnico(DatoTecnicoRef tecnico) {
            this.tecnico = tecnico;
        }
        public DatoTecnicoRef getTecnico() {return tecnico;}


        public void setEquipo(DatoEquipoRef equipo) {this.equipo = equipo;}
        public DatoEquipoRef getEquipo() { return equipo;}

        public DatoClienteRef getCliente() {
            return cliente;
        }
        public void setCliente(DatoClienteRef cliente) {
            this.cliente = cliente;
        }
        public DatoString getFechaHora() {
            return fechaHora;
        }
        public DatoString getFalla() {
            return falla;
        }
        public DatoString getImporte() {
            return importe;
        }
        public DatoString getComisionTecnico() {
            return comisionTecnico;
        }
        public DatoString getEstado() {
            return estado;
        }
        public DatoString getGarantia() {
            return garantia;
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


        public class DatoTecnicoRef {

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

        public class DatoClienteRef {

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
        public class DatoEquipoRef {

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
