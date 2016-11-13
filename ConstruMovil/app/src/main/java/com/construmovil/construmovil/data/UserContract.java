package com.construmovil.construmovil.data;

import android.provider.BaseColumns;

/**
 * Created by Malcolm Davis on 11/12/2016.
 * Email: me@malcolmdavis.xyz
 */

/**
 *Database person contract to link the class to the database.
 **/
public class UserContract {
    public static abstract class UserEntry implements BaseColumns {
        public static final String TABLE_NAME = "Usuario";
        public static final String UserName = "Nombre";
        public static final String Password = "Contrasena";
    }
}