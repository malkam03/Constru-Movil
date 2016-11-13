package com.construmovil.construmovil.data;

import android.content.ContentValues;
import android.database.Cursor;
import com.construmovil.construmovil.data.UserContract.UserEntry;

/**
 * Created by Malcolm Davis on 11/12/2016.
 * Email: me@malcolmdavis.xyz
 */

/**
 * Person Entity
 */
public class User {
    private String userName;
    private String password;

    /**
     * Constructor of the User Class
     * @param pUserName The username of the user.
     * @param pPassword The password of the user.
     */
    public User(String pUserName, String pPassword){
        this.userName = pUserName;
        this.password = pPassword;
    }

    /**
     * Second constructor of the User Class.
     * @param pCursor a cursor with the data of the User.
     */
    public User(Cursor pCursor){
        this.password = pCursor.getString(pCursor.getColumnIndex(UserEntry.Password));
        this.userName = pCursor.getString(pCursor.getColumnIndex(UserEntry.UserName));
    }

    /**
     * Method that converts the data of the User to content values.
     * @return a ContentValues object with the data.
     */
    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(UserEntry.Password, this.password);
        values.put(UserEntry.UserName, this.userName);
        return values;
    }

    /**
     * Method that returns the Username.
     * @return a string with the username of the user
     */
    public String getUserName() {
        return this.userName;
    }

    /**
     * Method that checks the User's password.
     * @param pPass the password to check.
     * @return boolean value responding the question is the password.
     */
    public boolean validPass(String pPass) {
        return this.password == pPass;
    }


}
