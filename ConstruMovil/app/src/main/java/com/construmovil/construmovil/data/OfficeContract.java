package com.construmovil.construmovil.data;

import android.provider.BaseColumns;

/**
 * Created by Malcolm Davis on 11/16/2016.
 * Email: me@malcolmdavis.xyz
 */

/**
 *Database Office entity contract                                                                                                                                                                         contract to link the class to the database.
 **/
public class OfficeContract {
    public static abstract class OfficeEntry implements BaseColumns {
        public static final String TABLE_NAME = "Sucursal";
        public static final String Name = "Nombre";
        public static final String Address = "Direccion";
    }
}