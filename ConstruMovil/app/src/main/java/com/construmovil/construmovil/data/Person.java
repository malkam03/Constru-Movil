package com.construmovil.construmovil.data;

import android.content.ContentValues;
import android.database.Cursor;
import com.construmovil.construmovil.data.PersonContract.PersonEntry;

/**
 * Created by Malcolm Davis on 11/12/2016.
 * Email: me@malcolmdavis.xyz
 */

/**
 * Person Entity
 */
public class Person {
    private String id;
    private String userName;
    private String name;
    private String middleName;
    private String lastName;
    private String phoneNumber;
    private String address;
    private String birthDate;

    /**
     * Constructor of the Person Class
     * @param pID The identification of the person.
     * @param pUserName The username related to this person.
     * @param pName The First name of the person.
     * @param pMiddleName The Middle name of the person.
     * @param pLastName The last name of the person.
     * @param pNumber The phone number of this person.
     * @param pAddress The person address.
     * @param pDate The birthdate of the person.
     */
    public Person(String pID, String pUserName, String pName, String pMiddleName,
                  String pLastName, String pNumber, String pAddress, String pDate){
        this.id = pID;
        this.userName = pUserName;
        this.name = pName;
        this.middleName = pMiddleName;
        this.lastName = pLastName;
        this.phoneNumber = pNumber;
        this.address = pAddress;
        this.birthDate  = pDate;
    }

    /**
     * Second constructor of the Person Class.
     * @param pCursor a cursor with the data of the person.
     */
    public Person(Cursor pCursor){
        this.id = pCursor.getString(pCursor.getColumnIndex(PersonEntry.ID));
        this.userName = pCursor.getString(pCursor.getColumnIndex(PersonEntry.UserName));
        this.name = pCursor.getString(pCursor.getColumnIndex(PersonEntry.Name));
        this.middleName = pCursor.getString(pCursor.getColumnIndex(PersonEntry.MiddletName));
        this.lastName = pCursor.getString(pCursor.getColumnIndex(PersonEntry.LastName));
        this.phoneNumber = pCursor.getString(pCursor.getColumnIndex(PersonEntry.Phone));
        this.address = pCursor.getString(pCursor.getColumnIndex(PersonEntry.Address));
        this.birthDate = pCursor.getString(pCursor.getColumnIndex(PersonEntry.BirthDate));
    }

    /**
     * Method that converts the data of the person to content values.
     * @return a ContentValues object with the data.
     */
    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(PersonEntry.ID, this.id);
        values.put(PersonEntry.UserName, this.userName);
        values.put(PersonEntry.Name, this.name);
        values.put(PersonEntry.MiddletName, this.middleName);
        values.put(PersonEntry.LastName, this.lastName);
        values.put(PersonEntry.Phone, this.phoneNumber);
        values.put(PersonEntry.Address, this.address);
        values.put(PersonEntry.BirthDate, this.birthDate);
        return values;
    }

    /**
     * Method that returns the person ID
     * @return a string with the id of the person
     */
    public String getId() {
        return id;
    }

    /**
     * Method that returns the person UserName
     * @return a string with the UserName of the person
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Method that returns the person Name
     * @return a string with the Name of the person
     */
    public String getName() {
        return name;
    }

    /**
     * Method that returns the person MiddleName
     * @return a string with the MiddleName of the person
     */
    public String getMiddleName() {
        return middleName;
    }

    /**
     * Method that returns the person LastName
     * @return a string with the LastName of the person
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Method that returns the person PhoneNumber
     * @return a string with the PhoneNumber of the person
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Method that returns the person Address
     * @return a string with the Address of the person
     */
    public String getAddress() {
        return address;
    }

    /**
     * Method that returns the person BirthDate
     * @return a string with the BirthDate of the person
     */
    public String getBirthDate() {
        return birthDate;
    }

}
