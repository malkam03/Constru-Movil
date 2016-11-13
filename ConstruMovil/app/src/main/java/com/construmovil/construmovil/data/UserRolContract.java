package com.construmovil.construmovil.data;

import android.provider.BaseColumns;

/**
 * Created by Malcolm Davis on 11/12/2016.
 * Email: me@malcolmdavis.xyz
 */

/**
 *Database person contract to link the class to the database.
 **/
public class UserRolContract {
    public static abstract class UserRolEntry implements BaseColumns {
        public static final String TABLE_NAME = "UsuarioROL";
        public static final String IDRol = "IDRol";
        public static final String UserName = "NombreUsuario";
    }
}