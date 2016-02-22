package com.pluscel.pluscelmovil.dom;

/**
 * Created by Pablo Pincheira on 08/12/2015.
 */
public class OrdenServicio {
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
        DatoTecnico tecnico;
        DatoCliente cliente;
        DatoEquipo equipo;
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
        public void setTecnico(DatoTecnico tecnico) {
            this.tecnico = tecnico;
        }
        public DatoTecnico getTecnico() {return tecnico;}
        public void setEquipo(DatoEquipo equipo) {this.equipo = equipo;}
        public DatoEquipo getEquipo() { return equipo;}

        public DatoCliente getCliente() {
            return cliente;
        }
        public void setCliente(DatoCliente cliente) {
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

        public class DatoCliente {

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

        public class DatoEquipo {

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
