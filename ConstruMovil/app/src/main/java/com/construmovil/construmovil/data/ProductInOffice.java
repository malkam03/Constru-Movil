package com.construmovil.construmovil.data;

import android.content.ContentValues;
import android.database.Cursor;
import com.construmovil.construmovil.data.ProductInOfficeContract.ProductInOfficeEntry;

/**
 * Created by Malcolm Davis on 11/16/2016.
 * Email: me@malcolmdavis.xyz
 */

/**
 * ProductInOffice Entity
 */
public class ProductInOffice {
    private String officeName;
    private String productID;
    private int amount;
    /**
     * Constructor of the office Class.
     * @param pOfficeName the name of the office.
     * @param pProductId the ID of the product.
     * @param pAmount the quantity of the items of the product.
     */
    public ProductInOffice(String pOfficeName, String pProductId, int pAmount){
        this.officeName = pOfficeName;
        this.productID = pProductId;
        this.amount = pAmount;
    }

    /**
     * Second constructor of the ProductInOffice Class.
     * @param pCursor a cursor with the data of the ProductInOffice.
     */
    public ProductInOffice(Cursor pCursor){
        this.officeName = pCursor.getString(pCursor.getColumnIndex(ProductInOfficeEntry.OfficeName));
        this.productID = pCursor.getString(pCursor.getColumnIndex(ProductInOfficeEntry.ProductID));
        this.amount = pCursor.getInt(pCursor.getColumnIndex(ProductInOfficeEntry.Amount));
    }

    /**
     * Method that converts the data of the ProductInOffice record to content values.
     * @return a ContentValues object with the ProductInOffice record data.
     */
    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(ProductInOfficeEntry.OfficeName, this.officeName);
        values.put(ProductInOfficeEntry.ProductID, this.productID);
        values.put(ProductInOfficeEntry.Amount, this.amount);
        return values;
    }



    /**
     * Method that returns the name of the office.
     * @return a string with the name of the office
     */
    public String getOfficeName() {
        return this.officeName;
    }

    /**
     * Method that returns the product's id.
     * @return a string with the id of the product.
     */
    public String getProductID() {
        return this.productID;
    }

    /**
     * Method that returns the amount of items of the product.
     * @return a int with the item amount of the product.
     */
    public int getAmount(){
        return this.amount;
    }

}
