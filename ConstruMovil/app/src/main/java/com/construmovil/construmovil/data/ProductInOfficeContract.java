package com.construmovil.construmovil.data;

import android.provider.BaseColumns;

/**
 * Created by Malcolm Davis on 11/16/2016.
 * Email: me@malcolmdavis.xyz
 */

/**
 *Database ProductInOffice entity contract                                                                                                                                                                         contract to link the class to the database.
 **/
public class ProductInOfficeContract {
    public static abstract class ProductInOfficeEntry implements BaseColumns {
        public static final String TABLE_NAME = "ProductoEnSucursal";
        public static final String OfficeName = "NSucursal";
        public static final String ProductID = "IDProducto";
        public static final String Amount = "Cantidad";
    }
}