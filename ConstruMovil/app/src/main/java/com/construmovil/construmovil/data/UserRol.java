package com.construmovil.construmovil.data;

import android.content.ContentValues;
import android.database.Cursor;
import com.construmovil.construmovil.data.UserRolContract.UserRolEntry;

/**
 * Created by Malcolm Davis on 11/12/2016.
 * Email: me@malcolmdavis.xyz
 */

/**
 * UserRol Entity
 */
public class UserRol {
    private String idRol;
    private String userName;

    /**
     * Constructor of the UserRol Class
     * @param pIDRol The password of the user.{1:Administrator, 2:Employee, 3:SalesMan, 4:Provider, 5:Client }
     * @param pUserName The username of the user.
     */
    public UserRol(String pIDRol, String pUserName){
        this.idRol = pIDRol;
        this.userName = pUserName;
    }

    /**
     * Second constructor of the UserRol Class.
     * @param pCursor a cursor with the data of the UserRol.
     */
    public UserRol(Cursor pCursor){
        this.idRol = pCursor.getString(pCursor.getColumnIndex(UserRolEntry.IDRol));
        this.userName = pCursor.getString(pCursor.getColumnIndex(UserRolEntry.UserName));
    }

    /**
     * Method that converts the data of the UserRol to content values.
     * @return a ContentValues object with the data.
     */
    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(UserRolEntry.IDRol, this.idRol);
        values.put(UserRolEntry.UserName, this.userName);
        return values;
    }

    /**
     * Method that returns the Username.
     * @return a string with the username.
     */
    public String getUserName() {
        return this.userName;
    }

    /**
     * Method that returns the Rol ID.
     * @return a string with the Rol Id.
     */
    public String getIDRol() {
        return this.idRol;
    }


}
