package com.construmovil.construmovil.data;

import android.content.ContentValues;
import android.database.Cursor;
import com.construmovil.construmovil.data.ProductContract.ProductEntry;

/**
 * Created by Malcolm Davis on 11/15/2016.
 * Email: me@malcolmdavis.xyz
 */

/**
 * Product Entity
 */
public class Product {
    private String id;
    private String productName;
    private String categoryID;
    private String supplierID;
    private String description;
    private int price;
    private int exempt;

    /**
     * Constructor of the Product Class
     * @param pID the ID of the product.
     * @param pProductName the name of the product.
     * @param pCategoryID the category that the product belongs to.
     * @param pSupplierID the bussiness id of the supplier of the product.
     * @param pDescription the detailed description of what the product is.
     * @param pPrice the price of the product.
     * @param pExempt tells if the product is exempt from taxes.
     */
    public Product(String pID, String pProductName, String pCategoryID, String
                    pSupplierID, String pDescription, int pPrice, int pExempt){
        this.id = pID;
        this.productName = pProductName;
        this.categoryID = pCategoryID;
        this.supplierID = pSupplierID;
        this.description = pDescription;
        this.price = pPrice;
        this.exempt = pExempt;
    }

    /**
     * Second constructor of the Product Class.
     * @param pCursor a cursor with the data of the Product.
     */
    public Product(Cursor pCursor){
        this.id = pCursor.getString(pCursor.getColumnIndex(ProductEntry.ID));
        this.productName = pCursor.getString(pCursor.getColumnIndex(ProductEntry.Name));
        this.categoryID = pCursor.getString(pCursor.getColumnIndex(ProductEntry.CategoryID));
        this.supplierID = pCursor.getString(pCursor.getColumnIndex(ProductEntry.SupplierID));
        this.description = pCursor.getString(pCursor.getColumnIndex(ProductEntry.Description));
        this.price = pCursor.getInt(pCursor.getColumnIndex(ProductEntry.Price));
        this.exempt = pCursor.getInt(pCursor.getColumnIndex(ProductEntry.Exempt));
    }

    /**
     * Method that converts the data of the Product to content values.
     * @return a ContentValues object with the Product.
     */
    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(ProductEntry.ID, this.id);
        values.put(ProductEntry.Name, this.productName);
        values.put(ProductEntry.CategoryID, this.categoryID);
        values.put(ProductEntry.SupplierID, this.supplierID);
        values.put(ProductEntry.Description, this.description);
        values.put(ProductEntry.Price, this.price);
        values.put(ProductEntry.Exempt, this.exempt);
        return values;
    }



    /**
     * Method that returns the ID of the product.
     * @return a string with the id of the product
     */
    public String getId() {
        return id;
    }

    /**
     * Method that returns the product's name.
     * @return a string with the name of the product.
     */
    public String getProductName() {
        return productName;
    }

    /**
     * Method that returns the product's category id.
     * @return a string with the category id of the product.
     */
    public String getCategoryID() {
        return categoryID;
    }

    /**
     * Method that returns the product's SupplierID.
     * @return a string with the SupplierID of the product.
     */
    public String getSupplierID() {
        return supplierID;
    }

    /**
     * Method that returns the product's Description.
     * @return a string with the description of the product.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Method that returns the product's price.
     * @return a int with the price of the product.
     */
    public int getPrice() {
        return price;
    }

    /**
     * Method that says if the product is exempt from taxes.
     * @return a boolean with the condition isExempt?
     */
    public int getExempt() {
        return exempt;
    }
}
