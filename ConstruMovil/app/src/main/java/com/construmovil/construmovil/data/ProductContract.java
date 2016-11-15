package com.construmovil.construmovil.data;

import android.provider.BaseColumns;

/**
 * Created by Malcolm Davis on 11/14/2016.
 * Email: me@malcolmdavis.xyz
 */

/**
 *Database ProductContract                                                                                                                                                                         contract to link the class to the database.
 **/
public class ProductContract {
    public static abstract class ProductEntry implements BaseColumns {
        public static final String TABLE_NAME = "Producto";
        public static final String Name = "Nombre";
        public static final String ID = "ID";
        public static final String CategoryID = "IDCategoria";
        public static final String SupplierID = "CedJurProveedor";
        public static final String Description = "Descripcion";
        public static final String Price = "Precio";
        public static final String Exempt = "Excento";
    }
}