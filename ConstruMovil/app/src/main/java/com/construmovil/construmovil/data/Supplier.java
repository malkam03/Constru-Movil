package com.construmovil.construmovil.data;

import android.content.ContentValues;
import android.database.Cursor;
import com.construmovil.construmovil.data.SupplierContract.SupplierEntry;

/**
 * Created by Malcolm Davis on 11/14/2016.
 * Email: me@malcolmdavis.xyz
 */

/**
 * Supplier Entity
 */
public class Supplier {
    private String id;
    private String idPerson;
    private String businessName;
    /**
     * Constructor of the Supplier Class
     * @param pIDPerson The username of the user.
     * @param pBusinessName The password of the user.
     */
    public Supplier(String pIDPerson, String pBusinessName, String pId){
        this.id = pId;
        this.idPerson = pIDPerson;
        this.businessName=pBusinessName;
    }

    /**
     * Second constructor of the Supplier Class.
     * @param pCursor a cursor with the data of the Supplier.
     */
    public Supplier(Cursor pCursor){
        this.id = pCursor.getString(pCursor.getColumnIndex(SupplierEntry.ID));
        this.idPerson = pCursor.getString(pCursor.getColumnIndex(SupplierEntry.IDPerson));
        this.businessName = pCursor.getString(pCursor.getColumnIndex(SupplierEntry.BusinessName));
    }

    /**
     * Method that converts the data of the User to content values.
     * @return a ContentValues object with the data.
     */
    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(SupplierEntry.ID, this.id);
        values.put(SupplierEntry.IDPerson, this.idPerson);
        values.put(SupplierEntry.BusinessName, this.businessName);
        return values;
    }

    /**
     * Method that returns the ID(cedula juridica) of the Supplier.
     * @return a string with the username of the user
     */
    public String getId() {
        return id;
    }

    /**
     * Method that returns the id of the person in charge of the Supplier.
     * @return a string with the username of the user
     */
    public String getIdPerson() {
        return idPerson;
    }

    /**
     * Method that returns the businessname of the supplier.
     * @return a string with the username of the user
     */
    public String getBusinessName() {
        return businessName;
    }

}
