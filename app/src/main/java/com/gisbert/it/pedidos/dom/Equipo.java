package com.gisbert.it.pedidos.dom;

/**
 * Created by Pablo Pincheira on 20/10/2015.
 */
public class    Equipo {

    Members members;

    public Members getMembers() {
        return members;
    }

    public void setMembers(Members members) {
        this.members = members;
    }


    // nested class
    public class Members{

        DatoMarca marca;
        DatoModelo modelo;
        DatoString imei;
        DatoImagen attachment;
        DatoString AltaAccesorioEquipo;
        DatoString listaAccesorioDeEquipo;
        DatoString altaEquipoTecnico;
        DatoString listaTecnicoDeEquipo;
        DatoString altaFallaPorEquipoPorTecnico;
        DatoString altaOrdenDeServicio;
        DatoString buscarPorReparaciones;
        DatoString altaPresupuesto;

        public DatoMarca getMarca() {
            return marca;
        }

        public void setMarca(DatoMarca marca) {
            this.marca = marca;
        }

        public DatoModelo getModelo() {
            return modelo;
        }

        public void setModelo(DatoModelo modelo) {
            this.modelo = modelo;
        }

        public DatoString getImei() {
            return imei;
        }

        public DatoImagen getAttachment() {
            return attachment;
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

        public class DatoCurso {

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

        public class DatoModelo {

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

        public class DatoImagen {

            DatoValue value;

            public DatoValue getValue() {
                return value;
            }

            public void setValue(DatoValue value) {
                this.value = value;
            }

            public class DatoValue {
                String value;

                public String getValue() {
                    return value;
                }

                public void setValue(String value) {
                    this.value = value;
                }
            }
        }

    }
}
