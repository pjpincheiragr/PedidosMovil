package com.pluscel.pluscelmovil.dom;

/**
 * Created by Pablo Pincheira on 21/11/2015.
 */
public class Tecnico {


    Members members;

    public Members getMembers() {
        return members;
    }

    public void setMembers(Members members) {
        this.members = members;
    }


    // nested class
    public class Members{

        DatoString apellido;
        DatoString nombre;
        DatoString dni;
        DatoString sexo;
        DatoString nacionalidad;
        DatoString fechaNacimiento;
        DatoDireccion direccion;
        DatoString telefono;
        DatoString email;
        DatoString habilitado;


        public DatoString getApellido() {
            return apellido;
        }

        public DatoString getNombre() {
            return nombre;
        }

        public DatoString getDNI() {
            return dni;
        }

        public DatoString getSexo() {
            return sexo;
        }
        public DatoString getNacionalidad() {
            return nacionalidad;
        }
        public DatoString getFechaNacimiento() {
            return fechaNacimiento;
        }
        public DatoDireccion getDireccion() {
            return direccion;
        }

        public DatoString getTelefono() {
            return telefono;
        }
        public DatoString getEmail() {
            return email;
        }
        public DatoString getHabilitado() {
            return habilitado;
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

        public class DatoDireccion {

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
