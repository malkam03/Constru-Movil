package com.construmovil.construmovil.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.construmovil.construmovil.data.PersonContract.PersonEntry;
import static com.construmovil.construmovil.data.UserContract.UserEntry;
import static com.construmovil.construmovil.data.UserRolContract.UserRolEntry;
import static com.construmovil.construmovil.data.SupplierContract.SupplierEntry;
import static com.construmovil.construmovil.data.ProductContract.ProductEntry;
import static com.construmovil.construmovil.data.CategoryContract.CategoryEntry;

/**
 * Created by Malcolm Davis on 11/12/2016.
 * Email: me@malcolmdavis.xyz
 */

/**
 * Database helper to handles the person on teh database.
 */
public class DbHelper extends SQLiteOpenHelper{
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "EPATEC-Movil.db";

    /**
     * Constructor of the class.
     * @param pContext
     */
    public DbHelper(Context pContext){
        super(pContext, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase pDb){
        // ***************USER TABLE*************************//
        pDb.execSQL("CREATE TABLE " + UserEntry.TABLE_NAME + " (" +
                    UserEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+
                    UserEntry.UserName + " TEXT UNIQUE," +
                    UserEntry.Password + " TEXT NOT NULL)");
        // ***************UserRol TABLE*************************//
        pDb.execSQL("CREATE TABLE " + UserRolEntry.TABLE_NAME + " (" +
                    UserRolEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+
                    UserRolEntry.IDRol + " TEXT UNIQUE NOT NULL," +
                    UserRolEntry.UserName + " TEXT NOT NULL)");
        // ***************PERSON TABLE*************************//
        pDb.execSQL("CREATE TABLE " + PersonEntry.TABLE_NAME + " (" +
                    PersonEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+
                    PersonEntry.ID + " TEXT UNIQUE," +
                    PersonEntry.UserName + " TEXT UNIQUE NOT NULL," +
                    PersonEntry.Name + " TEXT NOT NULL," +
                    PersonEntry.MiddletName + " TEXT NOT NULL," +
                    PersonEntry.LastName + " TEXT NOT NULL," +
                    PersonEntry.Phone + " TEXT NOT NULL," +
                    PersonEntry.Address + " TEXT NOT NULL," +
                    PersonEntry.BirthDate + " TEXT NOT NULL)");
        // *********************** SUPPLIER TABLE ******************//
        pDb.execSQL("CREATE TABLE " + SupplierEntry.TABLE_NAME + " (" +
                    SupplierEntry._ID +  " INTEGER PRIMARY KEY AUTOINCREMENT,"+
                    SupplierEntry.ID +  " TEXT UNIQUE NOT NULL," +
                    SupplierEntry.IDPerson + " TEXT NOT NULL, "+
                    SupplierEntry.BusinessName + " TEXT NOT NULL )");
        //*********************PRODUCT TABLE *********************//
        pDb.execSQL("CREATE TABLE " + ProductEntry.TABLE_NAME + " (" +
                ProductEntry._ID +  " INTEGER PRIMARY KEY AUTOINCREMENT,"+
                ProductEntry.Name + " TEXT UNIQUE NOT NULL, "+
                ProductEntry.ID +  " TEXT UNIQUE NOT NULL," +
                ProductEntry.CategoryID + " TEXT NOT NULL, " +
                ProductEntry.SupplierID + " TEXT NOT NULL, " +
                ProductEntry.Description + " TEXT NOT NULL, " +
                ProductEntry.Price + "INTEGER NOT NULL, " +
                ProductEntry.Exempt + " INTEGER DEFAULT 0 )");
        //*********************CATEGORY TABLE *********************//
        pDb.execSQL("CREATE TABLE " + CategoryEntry.TABLE_NAME + " (" +
                CategoryEntry._ID +  " INTEGER PRIMARY KEY AUTOINCREMENT,"+
                CategoryEntry.ID +  " TEXT UNIQUE NOT NULL,"+
                CategoryEntry.Name + " TEXT UNIQUE NOT NULL, " +
                CategoryEntry.Description + " TEXT NOT NULL )");
        //*********************CONSTRAINS *********************//
        mockData(pDb);
    }

    /**
     * Method that contains and add some test data to the database.
     * @param pSQLiteDatabase
     */
    public void mockData(SQLiteDatabase pSQLiteDatabase){
                            /*****Users****/
        User tmpUser = new User("malkam03", "1234");
        MockAdd(pSQLiteDatabase, UserEntry.TABLE_NAME, tmpUser.toContentValues());
        tmpUser = new User("arturok", "1234");
        MockAdd(pSQLiteDatabase, UserEntry.TABLE_NAME, tmpUser.toContentValues());
        tmpUser = new User("damarce", "1234");
        MockAdd(pSQLiteDatabase, UserEntry.TABLE_NAME, tmpUser.toContentValues());
        tmpUser = new User("testClient", "1234");
        MockAdd(pSQLiteDatabase, UserEntry.TABLE_NAME, tmpUser.toContentValues());
        tmpUser = new User("testProvider", "1234");
        MockAdd(pSQLiteDatabase, UserEntry.TABLE_NAME, tmpUser.toContentValues());
                            /*****Rols****/
        UserRol tmpURol = new UserRol("1", "malkam03");
        MockAdd(pSQLiteDatabase, UserRolEntry.TABLE_NAME,  tmpURol.toContentValues());
        tmpURol = new UserRol("4", "malkam03");
        MockAdd(pSQLiteDatabase, UserRolEntry.TABLE_NAME,  tmpURol.toContentValues());
        tmpURol = new UserRol("2", "arturok");
        MockAdd(pSQLiteDatabase, UserRolEntry.TABLE_NAME,  tmpURol.toContentValues());
        tmpURol = new UserRol("3", "damarce");
        MockAdd(pSQLiteDatabase, UserRolEntry.TABLE_NAME,  tmpURol.toContentValues());
        tmpURol = new UserRol("5", "testClient");
        MockAdd(pSQLiteDatabase, UserRolEntry.TABLE_NAME,  tmpURol.toContentValues());
        tmpURol = new UserRol("4", "testProvider");
        MockAdd(pSQLiteDatabase, UserRolEntry.TABLE_NAME,  tmpURol.toContentValues());
                            /*****Persons****/
        Person tmpPerson = new Person("702340755", "malkam03",
                "Malcolm", "Davis", "Steele", "89606261", "Cartago y otras senas", "17/03/1995");
        MockAdd(pSQLiteDatabase, PersonEntry.TABLE_NAME, tmpPerson.toContentValues());
        tmpPerson = new Person("102340755", "arturok",
                "Arturo", "Chichilla", "Sanchez", "89606263", "Cartago y otras senas", "17/03/1994");
        MockAdd(pSQLiteDatabase, PersonEntry.TABLE_NAME, tmpPerson.toContentValues());
        tmpPerson =  new Person("302340755", "damarce",
                "Marcela", "Ortega", "Soto", "89606262", "Cartago y otras senas", "17/03/1997");
        MockAdd(pSQLiteDatabase, PersonEntry.TABLE_NAME, tmpPerson.toContentValues());
        tmpPerson =  new Person("010100101", "testClient",
                "John", "Doe", "Client", "77777777", "Hell's Kitchen", "17/03/1997");
        MockAdd(pSQLiteDatabase, PersonEntry.TABLE_NAME, tmpPerson.toContentValues());
        tmpPerson =  new Person("010100102", "testProvider",
        "John", "Doe", "Provider", "77777777", "Hell's Kitchen", "17/03/1997");
        MockAdd(pSQLiteDatabase, PersonEntry.TABLE_NAME, tmpPerson.toContentValues());
                            /*****Suppliers****/
        Supplier tmpSuppl = new Supplier("702340755", "Nabit", "159746523");
        MockAdd(pSQLiteDatabase, SupplierEntry.TABLE_NAME, tmpSuppl.toContentValues());
        tmpSuppl = new Supplier("010100102", "Nabit", "159746521");
        MockAdd(pSQLiteDatabase, SupplierEntry.TABLE_NAME, tmpSuppl.toContentValues());
                        /**************CATEGORY ************/
        Category tmpCategory = new Category("1", "Nature Oddities", "All tech related to nature.");
        tmpCategory = new Category("3", "Workshop tools", "Be a man while you craft your world.");
        tmpCategory = new Category("2", "Weapons", "All you need to take down your target.");
                        /*************PRODUCT************/
        Product tmpProduct = new Product("1", "Argus", "1", "159746523",
                "A camera to study wildlife.", 200, true );
        MockAdd(pSQLiteDatabase, SupplierEntry.TABLE_NAME, tmpProduct.toContentValues());
        tmpProduct = new Product("2", "Hephaestus", "3", "159746523",
                "The perfect toolman for your workshop.", 400, false );
        MockAdd(pSQLiteDatabase, SupplierEntry.TABLE_NAME, tmpProduct.toContentValues());
        tmpProduct = new Product("3", "Artemis", "2", "159746521",
                "So you think you can hunt? with Artemis your game will come to you.", 300, false );
        MockAdd(pSQLiteDatabase, SupplierEntry.TABLE_NAME, tmpProduct.toContentValues());

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
     * @param pPerson the person data to store into the database.
     * @return state of the insert query.
     */
    public long savePerson(Person pPerson){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.insert(PersonEntry.TABLE_NAME, null, pPerson.toContentValues());
    }

    /**
     * Method that saves a product on the database.
     * @param pProduct the product to store into the database.
     * @return state of the insert query.
     */
    public long saveProduct(Product pProduct){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.insert(ProductEntry.TABLE_NAME, null, pProduct.toContentValues());
    }

    /**
     * Method that saves a User data on the database.
     * @param pUser the user data to store into the database.
     * @return state of the insert query.
     */
    public long saveUser(User pUser){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.insert(UserEntry.TABLE_NAME, null, pUser.toContentValues());
    }

    /**
     * Method that saves a UserRol on the database
     * @param pURol the user data to store into the database.
     * @return state of the insert query.
     */
    public long saveUserRol(UserRol pURol){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.insert(UserRolEntry.TABLE_NAME, null, pURol.toContentValues());
    }

    /**
     * Method that saves a supplier on the database
     * @param pSuppl the supplier data to store into the dayabase.
     * @return state of the insert query.
     */
    public long saveSupplier(Supplier pSuppl){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.insert(SupplierEntry.TABLE_NAME, null, pSuppl.toContentValues());
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
     * Method that search for all the suppliers that are on the database.
     * @return a cursor with the first supplier of the database.
     */
    public Cursor getAllSupplier() {
        return getReadableDatabase().query(
                SupplierEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null,
                null);
    }

    /**
     * Method that search for all the products that are on the database.
     * @return a cursor with the first product of the database.
     */
    public Cursor getAllProducts() {
        return getReadableDatabase().query(
                ProductEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null,
                null);
    }

    /**
     * Method that search for all the roles that a user have on the database.
     * @return a cursor with the first userRol of the database.
     */
    public Cursor getRols(String pUserName) {
        return getReadableDatabase().query(
                UserRolEntry.TABLE_NAME,
                null,
                UserRolEntry.UserName + " LIKE ?",
                new String[]{pUserName},
                null,
                null,
                null,
                null);
    }

    /**
     * Method that search for a specific product on the database by it's id.
     * @param pID  the identifier of the product.
     * @return a cursor with the first product of the database.
     */
    public Cursor getProductbyID(String pID) {
        return getReadableDatabase().query(
                ProductEntry.TABLE_NAME,
                null,
                ProductEntry.ID + " LIKE ?",
                new String[]{pID},
                null,
                null,
                null,
                null);
    }

    /**
     * Method that search for a specific product on the database by it's Name.
     * @param pName  the name of the product.
     * @return a cursor with the first product of the database.
     */
    public Cursor getProductbyName(String pName) {
        return getReadableDatabase().query(
                ProductEntry.TABLE_NAME,
                null,
                ProductEntry.Name + " LIKE ?",
                new String[]{pName},
                null,
                null,
                null,
                null);
    }

    /**
     * Method that search for a specific product on the database by it's id.
     * @param pSupplierID  the identifier of the product.
     * @return a cursor with the first product of the database.
     */
    public Cursor getProductbySupplier(String pSupplierID) {
        return getReadableDatabase().query(
                ProductEntry.TABLE_NAME,
                null,
                ProductEntry.SupplierID + " LIKE ?",
                new String[]{pSupplierID},
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
    public Cursor getPersonById(String pID) {
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
     * Method that search for a specific supplier whom the entered ID corresponds to.
     * @param pID the identifier of the supplier.
     * @return a cursor with the supplier that meets the search params.
     */
    public Cursor getSupplierByID(String pID) {
        Cursor cursor = getReadableDatabase().query(
                SupplierEntry.TABLE_NAME,
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
     * Method that deletes a specific person whom the entered ID corresponds to.
     * @param pUserName the username of the user that the rol will be deleted.
     * @param pIDRol the rol that will be deleted.
     * @return state of the delete query.
     */
    public int deleteUserRol(String pUserName, String pIDRol) {
        return getWritableDatabase().delete(
                UserRolEntry.TABLE_NAME,
                UserRolEntry.UserName + " LIKE ? AND " + UserRolEntry.IDRol+ "LIKE ?",
                new String[]{pUserName, pIDRol});
    }


    /**
     * Method that deletes a specific user whom the entered username corresponds to.
     * @param pUsername the identifier of the person.
     * @return state of the delete query.
     */
    public int deleteUser(String pUsername) {
        return getWritableDatabase().delete(
                UserEntry.TABLE_NAME,
                UserEntry.UserName + " LIKE ?",
                new String[]{pUsername});
    }

    /**
     * Method that deletes a specific product whom the entered ID corresponds to.
     * @param pId the identifier of the product.
     * @return state of the delete query.
     */
    public int deleteProduct(String pId) {
        return getWritableDatabase().delete(
                ProductEntry.TABLE_NAME,
                ProductEntry.ID + " LIKE ?",
                new String[]{pId});
    }

    /**
     * Method that deletes a specific supplier whom the entered cedPerson corresponds to.
     * @param pID ced juridica of the business.
     * @return state of the delete query.
     */
    public int deleteSupplier(String pID) {
        return getWritableDatabase().delete(
                SupplierEntry.TABLE_NAME,
                SupplierEntry.ID + " LIKE ?",
                new String[]{pID});
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

    /**
     * Method that updates a specific supplier whom the entered ID corresponds to.
     * @param pSupplier the new supplier to update.
     * @param pID the ID of the business
     * @return state of the update query.
     */
    public int updateSupplier(Supplier pSupplier, String pID) {
        return getWritableDatabase().update(
                UserEntry.TABLE_NAME,
                pSupplier.toContentValues(),
                UserEntry.UserName + " LIKE ?",
                new String[]{pID}
        );
    }

    /**
     * Method that updates a specific product whom the entered ID corresponds to.
     * @param pProduct the new product to update.
     * @param pID the ID of the product
     * @return state of the update query.
     */
    public int updateProduct(Product pProduct, String pID) {
        return getWritableDatabase().update(
                ProductEntry.TABLE_NAME,
                pProduct.toContentValues(),
                ProductEntry.ID + " LIKE ?",
                new String[]{pID}
        );
    }
}


