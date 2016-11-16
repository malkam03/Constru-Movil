package com.construmovil.construmovil.data;

import android.provider.BaseColumns;

/**
 * Created by Malcolm Davis on 11/15/2016.
 * Email: me@malcolmdavis.xyz
 */

/**
 *Database OrderProduct                                                                                                                                                                         contract to link the class to the database.
 **/
public class OrderProductContract {
    public static abstract class OrderProductEntry implements BaseColumns {
        public static final String TABLE_NAME = "ProductoPedido";
        public static final String IDOrder = "IDPedido";
        public static final String IDProduct = "IDProducto";
        public static final String Amount = "Cantidad";
        public static final String ActualPrice = "PrecioActual";
    }
}