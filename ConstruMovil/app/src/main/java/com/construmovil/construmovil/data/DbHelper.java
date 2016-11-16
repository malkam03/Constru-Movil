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
import static com.construmovil.construmovil.data.OrderProductContract.OrderProductEntry;
import static com.construmovil.construmovil.data.OrderContract.OrderEntry;
import static com.construmovil.construmovil.data.SellContract.SellEntry;

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
        //*********************ORDER TABLE *********************//
        pDb.execSQL("CREATE TABLE " + OrderEntry.TABLE_NAME + " (" +
                    OrderEntry._ID +  " INTEGER PRIMARY KEY AUTOINCREMENT,"+
                    OrderEntry.ID +  " TEXT UNIQUE NOT NULL,"+
                    OrderEntry.UserName + " TEXT NOT NULL, " +
                    OrderEntry.OfficeName +  " TEXT UNIQUE NOT NULL,"+
                    OrderEntry.State + " TEXT NOT NULL, " +
                    OrderEntry.Phone +  " TEXT UNIQUE NOT NULL,"+
                    OrderEntry.ETA   + " TEXT NOT NULL, " +
                    OrderEntry.OrderTime + " TEXT NOT NULL )");
        //*********************OrderProduct TABLE *********************//
        pDb.execSQL("CREATE TABLE " + OrderProductEntry.TABLE_NAME + " (" +
                    OrderProductEntry._ID +  " INTEGER PRIMARY KEY AUTOINCREMENT,"+
                    OrderProductEntry.IDOrder +  " TEXT NOT NULL,"+
                    OrderProductEntry.IDProduct + " TEXT NOT NULL, " +
                    OrderProductEntry.Amount + " INTEGER DEFAULT 1 ," +
                    OrderProductEntry.ActualPrice + " INTEGER NOT NULL )");
        //*********************SELL TABLE *********************//
        pDb.execSQL("CREATE TABLE " + SellEntry.TABLE_NAME + " (" +
                SellEntry._ID +  " INTEGER PRIMARY KEY AUTOINCREMENT,"+
                SellEntry.ID +  " TEXT UNIQUE NOT NULL,"+
                SellEntry.OrderID + " TEXT UNIQUE NOT NULL, " +
                SellEntry.SalesmanUserName + " TEXT NOT NULL)");
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
                "A camera to study wildlife.", 200, 0 );
        MockAdd(pSQLiteDatabase, ProductEntry.TABLE_NAME, tmpProduct.toContentValues());
        tmpProduct = new Product("2", "Hephaestus", "3", "159746523",
                "The perfect toolman for your workshop.", 400, 0 );
        MockAdd(pSQLiteDatabase, ProductEntry.TABLE_NAME, tmpProduct.toContentValues());
        tmpProduct = new Product("3", "Artemis", "2", "159746521",
                "So you think you can hunt? with Artemis your game will come to you.", 300, 0 );
        MockAdd(pSQLiteDatabase, ProductEntry.TABLE_NAME, tmpProduct.toContentValues());
                        /*************ORDER*************/
        Order tmpOrder = new Order("1", "testClient", "Cartago", "Pagado",
                "2222-2222", "17/11/2016", "17/11/2016" );
        MockAdd(pSQLiteDatabase, OrderEntry.TABLE_NAME, tmpProduct.toContentValues());
        tmpOrder = new Order("2", "testClient", "Cartago", "Pagado",
                "2222-2222", "17/11/2016", "17/11/2016" );
        MockAdd(pSQLiteDatabase, OrderEntry.TABLE_NAME, tmpProduct.toContentValues());
        tmpOrder = new Order("3", "testClient", "Cartago", "Pagado",
                "2222-2222", "17/11/2016", "17/11/2016" );
        MockAdd(pSQLiteDatabase, OrderEntry.TABLE_NAME, tmpProduct.toContentValues());
        tmpOrder = new Order("4", "testClient", "Cartago", "Pagado",
                "2222-2222", "17/11/2016", "17/11/2016" );
        MockAdd(pSQLiteDatabase, OrderEntry.TABLE_NAME, tmpProduct.toContentValues());
                        /************* SELL TABLE *************/
        Sell tmpSell = new Sell("damarce1", "1", "damarce");
        MockAdd(pSQLiteDatabase, SellEntry.TABLE_NAME, tmpSell.toContentValues());
        tmpSell = new Sell("damarce2", "2", "damarce");
        MockAdd(pSQLiteDatabase, SellEntry.TABLE_NAME, tmpSell.toContentValues());
        tmpSell = new Sell("arturok1", "3", "arturok");
        MockAdd(pSQLiteDatabase, SellEntry.TABLE_NAME, tmpSell.toContentValues());
        tmpSell = new Sell("arturok2", "4", "arturok");
        MockAdd(pSQLiteDatabase, SellEntry.TABLE_NAME, tmpSell.toContentValues());
        /*************ORDERPRODUCT************/
        OrderProduct OP = new OrderProduct("1", "1", 3,100);
        MockAdd(pSQLiteDatabase, OrderProductEntry.TABLE_NAME, OP.toContentValues());
        OP = new OrderProduct("2", "1", 5,50);
        MockAdd(pSQLiteDatabase, OrderProductEntry.TABLE_NAME, OP.toContentValues());
        OP = new OrderProduct("3", "1", 2,10);
        MockAdd(pSQLiteDatabase, OrderProductEntry.TABLE_NAME, OP.toContentValues());
        OP = new OrderProduct("1", "2", 3,100);
        MockAdd(pSQLiteDatabase, OrderProductEntry.TABLE_NAME, OP.toContentValues());
        OP = new OrderProduct("2", "2", 5,50);
        MockAdd(pSQLiteDatabase, OrderProductEntry.TABLE_NAME, OP.toContentValues());
        OP = new OrderProduct("3", "3", 2,10);
        MockAdd(pSQLiteDatabase, OrderProductEntry.TABLE_NAME, OP.toContentValues());

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
     * Method that saves a OrderProduct on the database.
     * @param pOP the OrderProduct to store into the database.
     * @return state of the insert query.
     */
    public long saveOrderProduct(OrderProduct pOP){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.insert(OrderProductEntry.TABLE_NAME, null, pOP.toContentValues());
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
     * Method that saves a Sell data on the database.
     * @param pSell the Sell data to store into the database.
     * @return state of the insert query.
     */
    public long saveSell(Sell pSell){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.insert(SellEntry.TABLE_NAME, null, pSell.toContentValues());
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
     * @param pSuppl the supplier data to store into the database.
     * @return state of the insert query.
     */
    public long saveSupplier(Supplier pSuppl){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.insert(SupplierEntry.TABLE_NAME, null, pSuppl.toContentValues());
    }

    /**
     * Method that saves a Category on the database
     * @param pCat the Category data to store into the database.
     * @return state of the insert query.
     */
    public long saveCategory(Category pCat){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.insert(CategoryEntry.TABLE_NAME, null, pCat.toContentValues());
    }

    /**
     * Method that saves a Order on the database
     * @param pOrder the Order data to store into the database.
     * @return state of the insert query.
     */
    public long saveOrder(Order pOrder){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.insert(OrderEntry.TABLE_NAME, null, pOrder.toContentValues());
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
     * Method that search for all the sells that are on the database.
     * @return a cursor with the first sell of the database.
     */
    public Cursor getSells() {
        return getReadableDatabase().query(
                SellEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null,
                null);
    }

    /**
     * Method that search for all the Orders that are on the database.
     * @return a cursor with the first Orders of the database.
     */
    public Cursor getAllOrders() {
        return getReadableDatabase().query(
                OrderEntry.TABLE_NAME,
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
     * Method that search for all the categories that are on the database.
     * @return a cursor with the first category of the database.
     */
    public Cursor getAllCategory() {
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
     * Method that search for all the roles that a user have on the database.
     * @return a cursor with the first userRol of the database.
     */
    public Cursor getSellBySalesMan(String pSalesmanUserName) {
        return getReadableDatabase().query(
                SellEntry.TABLE_NAME,
                null,
                SellEntry.SalesmanUserName + " LIKE ?",
                new String[]{pSalesmanUserName},
                null,
                null,
                null,
                null);
    }

    /**
     * Method that search for all the orders on the database with a ID.
     * @return a cursor with the first order of the database.
     */
    public Cursor geOrderbyID(String pID) {
        return getReadableDatabase().query(
                OrderEntry.TABLE_NAME,
                null,
                OrderEntry.ID + " LIKE ?",
                new String[]{pID},
                null,
                null,
                null,
                null);
    }

    /**
     * Method that search for all the orders on the database of a userName.
     * @return a cursor with the first order of the database.
     */
    public Cursor geOrderbyUsername(String pUserName) {
        return getReadableDatabase().query(
                OrderEntry.TABLE_NAME,
                null,
                OrderEntry.UserName + " LIKE ?",
                new String[]{pUserName},
                null,
                null,
                null,
                null);
    }

    /**
     * Method that search by OrderID for all the orderproducts entries on the database.
     * @return a cursor with the first userRol of the database.
     */
    public Cursor getOrderProductsbyOrderID(String pOrderID) {
        return getReadableDatabase().query(
                OrderProductEntry.TABLE_NAME,
                null,
                OrderProductEntry.IDOrder + " LIKE ?",
                new String[]{pOrderID},
                null,
                null,
                null,
                null);
    }

    /**
     * Method that search by OrderID for all the orderproducts entries on the database.
     * @return a cursor with the first userRol of the database.
     */
    public Cursor getOrderProductsbyProdcuctID(String pProductID) {
        return getReadableDatabase().query(
                OrderProductEntry.TABLE_NAME,
                null,
                OrderProductEntry.IDProduct + " LIKE ?",
                new String[]{pProductID},
                null,
                null,
                null,
                null);
    }

    /**
     * Method that search by OrderID for all the orderproducts entries on the database.
     * @param pProductID  the id of the product.
     * @param pOrderID the id of the order.
     * @return a cursor with the first userRol of the database.
     */
    public Cursor getOrderProduct(String pProductID, String pOrderID) {
        return getReadableDatabase().query(
                OrderProductEntry.TABLE_NAME,
                null,
                OrderProductEntry.IDProduct + " LIKE ? AND " + OrderProductEntry.IDProduct  + " LIKE ? " ,
                new String[]{pProductID, pOrderID},
                null,
                null,
                null,
                null);
    }

    /**
     * Method that search fora category by ID on the database.
     * @param pID the id of the category
     * @return a cursor with the first category of the database.
     */
    public Cursor getCategory(String pID) {
        return getReadableDatabase().query(
                CategoryEntry.TABLE_NAME,
                null,
                CategoryEntry.ID + " LIKE ?",
                new String[]{pID},
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
     * Method that deletes a specific sell whom the entered ID corresponds to.
     * @param pId the identifier of the sell.
     * @return state of the delete query.
     */
    public int deleteSell(String pId) {
        return getWritableDatabase().delete(
                SellEntry.TABLE_NAME,
                SellEntry.ID + " LIKE ?",
                new String[]{pId});
    }

    /**
     * Method that deletes a specific rol whom the entered ID corresponds to.
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
     * Method that deletes a specific Order whom the entered ID corresponds to.
     * @param pID the username of the user that the rol will be deleted.
     * @return state of the delete query.
     */
    public int deleteOrder(String pID) {
        return getWritableDatabase().delete(
                OrderEntry.TABLE_NAME,
                OrderEntry.ID + " LIKE ? ",
                new String[]{pID});
    }


    /**
     * Method that delete by OrderID and Product ID for all the orderproducts entries on the database.
     * @param pProductID  the id of the product.
     * @param pOrderID the id of the order.
     * @return a cursor with the first userRol of the database.
     */
    public int deleteOrderProduct(String pProductID, String pOrderID) {
        return getReadableDatabase().delete(
                OrderProductEntry.TABLE_NAME,
                OrderProductEntry.IDProduct + " LIKE ? AND " + OrderProductEntry.IDProduct  + " LIKE ? " ,
                new String[]{pProductID, pOrderID});
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
     * Method that deletes a specific Category whom the entered id corresponds to.
     * @param pID the id of the category.
     * @return state of the delete query.
     */
    public int deleteCategory(String pID) {
        return getWritableDatabase().delete(
                CategoryEntry.TABLE_NAME,
                CategoryEntry.ID + " LIKE ?",
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
     * Method that updates a specific category whom the entered ID corresponds to.
     * @param pCat new category to update.
     * @param pID the identifier of the category.
     * @return state of the update query.
     */
    public int updateCategory(Category pCat, String pID) {
        return getWritableDatabase().update(
                CategoryEntry.TABLE_NAME,
                pCat.toContentValues(),
                CategoryEntry.ID + " LIKE ?",
                new String[]{pID}
        );
    }

    /**
     * Method that updates a specific Sell whom the entered ID corresponds to.
     * @param pSell new Sell to update.
     * @param pID the identifier of the Sell.
     * @return state of the update query.
     */
    public int updateSell(Category pSell, String pID) {
        return getWritableDatabase().update(
                SellEntry.TABLE_NAME,
                pSell.toContentValues(),
                SellEntry.ID + " LIKE ?",
                new String[]{pID}
        );
    }


    /**
     * Method that updates a specific order whom the entered ID corresponds to.
     * @param pOrd new order to update.
     * @param pID the identifier of the order.
     * @return state of the update query.
     */
    public int updateOrder(Order pOrd, String pID) {
        return getWritableDatabase().update(
                OrderEntry.TABLE_NAME,
                pOrd.toContentValues(),
                OrderEntry.ID + " LIKE ?",
                new String[]{pID}
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

    /**
     * Method that search by OrderID for all the orderproducts entries on the database.
     * @param pProductID  the id of the product.
     * @param pOrderID the id of the order.
     * @param pOrderProduct the new orderProduct
     * @return a cursor with the first userRol of the database.
     */
    public int updateOrderProduct(String pProductID, String pOrderID, OrderProduct pOrderProduct) {
        return getWritableDatabase().update(
                OrderProductEntry.TABLE_NAME,
                pOrderProduct.toContentValues(),
                OrderProductEntry.IDProduct + " LIKE ? AND " + OrderProductEntry.IDProduct  + " LIKE ? " ,
                new String[]{pProductID, pOrderID}
        );
    }
}


