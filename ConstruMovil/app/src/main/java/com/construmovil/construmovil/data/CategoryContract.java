package com.construmovil.construmovil.data;

import android.provider.BaseColumns;

/**
 * Created by Malcolm Davis on 11/14/2016.
 * Email: me@malcolmdavis.xyz
 */

/**
 *Database CategoryContract                                                                                                                                                                         contract to link the class to the database.
 **/
public class CategoryContract {
    public static abstract class CategoryEntry implements BaseColumns {
        public static final String TABLE_NAME = "Categoria";
        public static final String ID = "ID";
        public static final String Name = "Nombre";
        public static final String Description = "Descripcion";
    }
}