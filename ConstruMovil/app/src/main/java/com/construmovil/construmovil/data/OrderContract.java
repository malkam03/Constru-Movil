package com.construmovil.construmovil.data;

import android.provider.BaseColumns;

/**
 * Created by Malcolm Davis on 11/15/2016.
 * Email: me@malcolmdavis.xyz
 */

/**
 *Database OrderContract                                                                                                                                                                         contract to link the class to the database.
 **/
public class OrderContract {
    public static abstract class OrderEntry implements BaseColumns {
        public static final String TABLE_NAME = "Pedido";
        public static final String ID = "ID";
        public static final String UserName = "NUsuario";
        public static final String OfficeName = "NSucursal";
        public static final String State = "Estado";
        public static final String Phone = "Telefono";
        public static final String ETA = "ETA";
        public static final String OrderTime = "Hora";
    }
}