package com.construmovil.construmovil.data;

import android.content.ContentValues;
import android.database.Cursor;
import com.construmovil.construmovil.data.SellContract.SellEntry;

/**
 * Created by Malcolm Davis on 11/16/2016.
 * Email: me@malcolmdavis.xyz
 */

/**
 * Sell Entity
 */
public class Sell {
    private String id;
    private String idOrder;
    private String salesmanUserName;
    /**
     * Constructor of the Sell Class.
     * @param pID the ID of the sell.
     * @param pIDOrder the ID of the order.
     * @param pSalesmanUsername the user name of the salesman.
     */
    public Sell(String pID, String pIDOrder, String pSalesmanUsername){
        this.idOrder = pIDOrder;
        this.id = pID;
        this.salesmanUserName = pSalesmanUsername;
    }

    /**
     * Second constructor of the OrderProduct Class.
     * @param pCursor a cursor with the data of the OrderProduct.
     */
    public Sell(Cursor pCursor){
        this.idOrder = pCursor.getString(pCursor.getColumnIndex(SellEntry.OrderID));
        this.id = pCursor.getString(pCursor.getColumnIndex(SellEntry.ID));
        this.salesmanUserName = pCursor.getString(pCursor.getColumnIndex(SellEntry.SalesmanUserName));
    }

    /**
     * Method that converts the data of the Sell to content values.
     * @return a ContentValues object with the Sell.
     */
    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(SellEntry.OrderID, this.idOrder);
        values.put(SellEntry.ID, this.id);
        values.put(SellEntry.SalesmanUserName, this.salesmanUserName);
        return values;
    }



    /**
     * Method that returns the ID of the Order.
     * @return a string with the ID of the Order
     */
    public String getIdORder() {
        return idOrder;
    }

    /**
     * Method that returns the Sell's ID.
     * @return a string with the name of the Sell.
     */
    public String getID() {
        return this.id;
    }

    /**
     * Method that returns the Sell salesmane's username.
     * @return a string with the amount of items of the product.
     */
    public String getSalesmanUsername() {
        return this.salesmanUserName;
    }

}
