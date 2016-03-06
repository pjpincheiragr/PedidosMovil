package com.gisbert.it.pedidos.dom;

/**
 * Created by ASUS on 03/03/2016.
 */
public class Pedido {
    Members members;

    public Members getMembers() {
        return members;
    }

    public void setMembers(Members members) {
        this.members = members;
    }


    // nested class
    public class Members{
        DatoTipo tipo;
        DatoProveedor proveedor;
        DatoString fechaHora;
        DatoVendedor vendedor;
        DatoString valor;
        DatoString estado;
        DatoSucursal sucursal;
        DatoString urgencia;
        DatoString observacion;
        DatoString activo;


        public DatoTipo getTipo() {
            return tipo;
        }
        public void setTipo(DatoTipo tipo) {
            this.tipo = tipo;
        }
        public DatoProveedor getProveedor() {return proveedor;}
        public void setProveedor(DatoProveedor proveedor) {this.proveedor = proveedor;}
        public DatoString getFechaHora() { return fechaHora;}
        public void setFechaHora(DatoString fechaHora) {this.fechaHora = fechaHora;}

        public DatoVendedor getVendedor() {
            return vendedor;
        }
        public void setVendedor(DatoVendedor vendedor) {
            this.vendedor = vendedor;
        }
        public DatoString getValor() {
            return valor;
        }
        public void setValor(DatoString valor) {
            this.valor = valor;
        }
        public DatoString getEstado() {
            return estado;
        }
        public void setEstado(DatoString estado) {
            this.estado = estado;
        }

        public DatoSucursal getSucursal() {
            return sucursal;
        }
        public void setSucursal(DatoSucursal sucursal) {
            this.sucursal = sucursal;
        }


        public DatoString getUrgencia() {
            return urgencia;
        }
        public void setUrgencia(DatoString urgencia) {
            this.urgencia = urgencia;
        }

        public DatoString getObservacion() {
            return observacion;
        }
        public void setObservacion(DatoString observacion) {
            this.observacion = observacion;
        }

        public DatoString getActivo() {
            return activo;
        }
        public void setActivo(DatoString activo) {
            this.activo = activo;
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

        public class DatoTipo {

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
        public class DatoProveedor {

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

        public class DatoVendedor {

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

        public class DatoSucursal {

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
