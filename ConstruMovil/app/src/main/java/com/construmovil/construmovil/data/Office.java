package com.construmovil.construmovil.data;

import android.content.ContentValues;
import android.database.Cursor;
import com.construmovil.construmovil.data.OfficeContract.OfficeEntry;

/**
 * Created by Malcolm Davis on 11/16/2016.
 * Email: me@malcolmdavis.xyz
 */

/**
 * Office Entity
 */
public class Office {
    private String name;
    private String address;
    /**
     * Constructor of the office Class.
     * @param pName the ID of the office.
     * @param pAddress the ID of the office.
     */
    public Office(String pName, String pAddress){
        this.name = pName;
        this.address = pAddress;
    }

    /**
     * Second constructor of the Office Class.
     * @param pCursor a cursor with the data of the Office.
     */
    public Office(Cursor pCursor){
        this.name = pCursor.getString(pCursor.getColumnIndex(OfficeEntry.Name));
        this.address = pCursor.getString(pCursor.getColumnIndex(OfficeEntry.Address));
    }

    /**
     * Method that converts the data of the office to content values.
     * @return a ContentValues object with the office data.
     */
    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(OfficeEntry.Name, this.name);
        values.put(OfficeEntry.Address, this.address);
        return values;
    }



    /**
     * Method that returns the name of the office.
     * @return a string with the name of the office
     */
    public String getName() {
        return this.name;
    }

    /**
     * Method that returns the office's address.
     * @return a string with the address of the office.
     */
    public String getAddress() {
        return this.address;
    }

}
