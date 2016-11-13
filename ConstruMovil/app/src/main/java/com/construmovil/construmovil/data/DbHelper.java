package com.construmovil.construmovil.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.construmovil.construmovil.data.PersonContract.PersonEntry;
import static com.construmovil.construmovil.data.UserContract.UserEntry;

/**
 * Created by Malcolm Davis on 11/12/2016.
 * Email: me@malcolmdavis.xyz
 */

/**
 * Database helper to handles the person on teh database.
 */
public class DbHelper extends SQLiteOpenHelper{
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "EPATEC.db";

    /**
     * Constructor of the class.
     * @param pContext
     */
    public DbHelper(Context pContext){
        super(pContext, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase pDb){
        // ***************PERSON TABLE*************************//
        pDb.execSQL("CREATE TABLE " + PersonEntry.TABLE_NAME + " (" +
                    PersonEntry.ID + " TEXT PRIMARY KEY," +
                    PersonEntry.UserName + " TEXT UNIQUE NOT NULL," +
                    PersonEntry.Name + " TEXT NOT NULL," +
                    PersonEntry.MiddletName + " TEXT NOT NULL," +
                    PersonEntry.LastName + " TEXT NOT NULL," +
                    PersonEntry.Phone + " TEXT NOT NULL," +
                    PersonEntry.Address + " TEXT NOT NULL," +
                    PersonEntry.BirthDate + " TEXT NOT NULL)");
        // ***************USER TABLE*************************//
        pDb.execSQL("CREATE TABLE " + UserEntry.TABLE_NAME + " (" +
                    UserEntry.UserName + " TEXT PRIMARY KEY," +
                    UserEntry.Password + " TEXT NOT NULL)");
        mockData(pDb);
    }

    /**
     * Method that contains and add some test data to the database.
     * @param pSQLiteDatabase
     */
    public void mockData(SQLiteDatabase pSQLiteDatabase){
        User tmpUser = new User("malkam03", "1234");
        MockAdd(pSQLiteDatabase, UserEntry.TABLE_NAME, tmpUser.toContentValues());
        tmpUser = new User("arturok", "1234");
        MockAdd(pSQLiteDatabase, UserEntry.TABLE_NAME, tmpUser.toContentValues());
        tmpUser = new User("damarce", "1234");
        MockAdd(pSQLiteDatabase, UserEntry.TABLE_NAME, tmpUser.toContentValues());
        Person tmpPerson = new Person("702340755", "malkam03",
                "Malcolm", "Davis", "Steele", "89606261", "Cartago y otras senas", "17/03/1995");
        MockAdd(pSQLiteDatabase, PersonEntry.TABLE_NAME, tmpPerson.toContentValues());
        tmpPerson = new Person("102340755", "arturok",
                "Arturo", "Chichilla", "Sanchez", "89606263", "Cartago y otras senas", "17/03/1994");
        MockAdd(pSQLiteDatabase, PersonEntry.TABLE_NAME, tmpPerson.toContentValues());
        tmpPerson =  new Person("302340755", "damarce",
                "Marcela", "Ortega", "Soto", "89606262", "Cartago y otras senas", "17/03/1997");
        MockAdd(pSQLiteDatabase, PersonEntry.TABLE_NAME, tmpPerson.toContentValues());
    }

    /**
     * Method that add a test person to the database.
     * @param pDatabse
     * @param pTable
     * @param pContent
     * @return
     */
    public long MockAdd(SQLiteDatabase pDatabse, String pTable, ContentValues pContent){
        return pDatabse.insert(pTable, null, pContent);
    }

    /**
     * Method to upgrade the Database.
     * @param pDb database to upgrade.
     * @param pOldVersion a int representing the old version of the Database.
     * @param pNewVersion a int representing the new version of the Database.
     */
    @Override
    public void onUpgrade(SQLiteDatabase pDb, int pOldVersion, int pNewVersion) {
        // Later
    }

    /**
     * Method that saves a person data on the database.
     * @param pPerson
     * @return
     */
    public long savePerson(Person pPerson){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.insert(PersonEntry.TABLE_NAME, null, pPerson.toContentValues());
    }

    /**
     * Method that saves a User data on the database.
     * @param pUser
     * @return
     */
    public long saveUser(User pUser){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.insert(UserEntry.TABLE_NAME, null, pUser.toContentValues());
    }

    /**
     * Method that search for all the persons that are on the database.
     * @return a cursor with the first person of the database.
     */
    public Cursor getAllPersons() {
        return getReadableDatabase().query(
                PersonEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null,
                null);
    }

    /**
     * Method that search for all the users that are on the database.
     * @return a cursor with the first User of the database.
     */
    public Cursor getAllUser() {
        return getReadableDatabase().query(
                UserEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null,
                null);
    }

    /**
     * Method that search for a specific person whom the entered ID corresponds to.
     * @param pID the identifier of the person.
     * @return a cursor with the person that meets the search params.
     */
    public Cursor getLawyerById(String pID) {
        Cursor cursor = getReadableDatabase().query(
                PersonEntry.TABLE_NAME,
                null,
                PersonEntry.ID + " LIKE ?",
                new String[]{pID},
                null,
                null,
                null);
        return cursor;
    }

    /**
     * Method that verifies if the user credentials entered are valid.
     * @param pUsername the username of the User.
     * @param pPassword the password of the User.
     * @return a boolean with the loging result.
     */
    public boolean login(String pUsername, String pPassword) {
        Cursor cursor = getReadableDatabase().query(
                UserEntry.TABLE_NAME,
                null,
                UserEntry.UserName + " LIKE ?",
                new String[]{pUsername},
                null,
                null,
                null);
        User tmpUser = new User(cursor);
        return tmpUser.validPass(pPassword);
    }

    /**
     * Method that deletes a specific person whom the entered ID corresponds to.
     * @param pId the identifier of the person.
     * @return state of the delete query.
     */
    public int deletePerson(String pId) {
        return getWritableDatabase().delete(
                PersonEntry.TABLE_NAME,
                PersonEntry.ID + " LIKE ?",
                new String[]{pId});
    }

    /**
     * Method that deletes a specific user whom the entered username corresponds to.
     * @param pUsername the identifier of the person.
     * @return state of the delete query.
     */
    public int deleteUsername(String pUsername) {
        return getWritableDatabase().delete(
                UserEntry.TABLE_NAME,
                UserEntry.UserName + " LIKE ?",
                new String[]{pUsername});
    }


    /**
     * Method that updates a specific person whom the entered ID corresponds to.
     * @param pPerson new person to update.
     * @param pID the identifier of the person.
     * @return state of the update query.
     */
    public int updatePerson(Person pPerson, String pID) {
        return getWritableDatabase().update(
                PersonEntry.TABLE_NAME,
                pPerson.toContentValues(),
                PersonEntry.ID + " LIKE ?",
                new String[]{pID}
        );
    }

    /**
     * Method that updates a specific user whom the entered username corresponds to.
     * @param pUser new user to update.
     * @param pUsername the identifier of the user.
     * @return state of the update query.
     */
    public int updateUser(User pUser, String pUsername) {
        return getWritableDatabase().update(
                UserEntry.TABLE_NAME,
                pUser.toContentValues(),
                UserEntry.UserName + " LIKE ?",
                new String[]{pUsername}
        );
    }
}


