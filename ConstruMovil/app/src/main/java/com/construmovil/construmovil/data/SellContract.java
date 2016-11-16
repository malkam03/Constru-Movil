package com.construmovil.construmovil.data;

import android.provider.BaseColumns;

/**
 * Created by Malcolm Davis on 11/16/2016.
 * Email: me@malcolmdavis.xyz
 */

/**
 *Database Sell entity contract                                                                                                                                                                         contract to link the class to the database.
 **/
public class SellContract {
    public static abstract class SellEntry implements BaseColumns {
        public static final String TABLE_NAME = "Venta";
        public static final String ID = "ID";
        public static final String OrderID = "IDPedido";
        public static final String SalesmanUserName = "Nvendedor";
    }
}