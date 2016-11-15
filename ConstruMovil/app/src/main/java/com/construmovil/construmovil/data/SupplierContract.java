package com.construmovil.construmovil.data;

import android.provider.BaseColumns;

/**
 * Created by Malcolm Davis on 11/14/2016.
 * Email: me@malcolmdavis.xyz
 */

/**
 *Database Supplier contract to link the class to the database.
 **/
public class SupplierContract {
    public static abstract class SupplierEntry implements BaseColumns {
        public static final String TABLE_NAME = "Proveedor";
        public static final String ID = "CedJuridica";
        public static final String IDPerson = "CedRepresentante";
        public static final String BusinessName = "Empresa";
    }
}