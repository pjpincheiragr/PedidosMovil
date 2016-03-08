package com.gisbert.it.pedidos.dom;

/**
 * Created by ASUS on 03/03/2016.
 */
public class PedidoItem {
    Members members;

    public Members getMembers() {
        return members;
    }

    public void setMembers(Members members) {
        this.members = members;
    }


    // nested class
    public class Members{
        DatoString muestra;
        DatoString codigo;
        DatoMarca marca;
        DatoInt cantidad;
        DatoString estado;
        DatoString observacion;


        public DatoString getEstado() {
            return estado;
        }
        public void setEstado(DatoString estado) {
            this.estado = estado;
        }

        public DatoString getMuestra() {
            return muestra;
        }

        public void setMuestra(DatoString muestra) {
            this.muestra = muestra;
        }

        public DatoString getCodigo() {
            return codigo;
        }

        public void setCodigo(DatoString codigo) {
            this.codigo = codigo;
        }

        public DatoMarca getMarca() {
            return marca;
        }

        public void setMarca(DatoMarca marca) {
            this.marca = marca;
        }

        public DatoInt getCantidad() {
            return cantidad;
        }

        public void setCantidad(DatoInt cantidad) {
            this.cantidad = cantidad;
        }

        public DatoString getObservacion() {
            return observacion;
        }

        public void setObservacion(DatoString observacion) {
            this.observacion = observacion;
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

        public class DatoInt {
            int value;

            public int getValue() {
                return value;
            }

            public void setValue(int value) {
                this.value = value;
            }
        }

        public class DatoMarca {

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
