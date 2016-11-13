package com.construmovil.construmovil.data;

import android.provider.BaseColumns;

/**
 * Created by Malcolm Davis on 11/12/2016.
 * Email: me@malcolmdavis.xyz
 */

/**
 *Database person contract to link the class to the database.
 **/
public class PersonContract {
    public static abstract class PersonEntry implements BaseColumns {
        public static final String TABLE_NAME = "Persona";
        public static final String Name = "Nombre";
        public static final String ID = "Cedula";
        public static final String UserName = "Usuario";
        public static final String MiddletName = "PrimerApellido";
        public static final String LastName = "SegundoApellido";
        public static final String Phone = "Telefono";
        public static final String Address = "Direccion";
        public static final String BirthDate = "FNacimiento";
    }
}