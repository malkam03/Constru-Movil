package com.construmovil.construmovil.data;

import android.content.ContentValues;
import android.database.Cursor;
import com.construmovil.construmovil.data.OrderProductContract.OrderProductEntry;

/**
 * Created by Malcolm Davis on 11/15/2016.
 * Email: me@malcolmdavis.xyz
 */

/**
 * OrderProduct Entity
 */
public class OrderProduct {
    private String idOrder;
    private String idProduct;
    private int amount;
    private int actualPrice;

    /**
     * Constructor of the OrderProduct Class.
     * @param pIDOrder the ID of the order.
     * @param pIDProduct the ID of the product.
     * @param pAmount the quantity of items of the product.
     * @param pActualPrice the actual price of the product.
     */
    public OrderProduct(String pIDOrder, String pIDProduct, int pAmount, int pActualPrice){
        this.idOrder = pIDOrder;
        this.idProduct = pIDProduct;
        this.amount = pAmount;
        this.actualPrice = pActualPrice;
    }

    /**
     * Second constructor of the OrderProduct Class.
     * @param pCursor a cursor with the data of the OrderProduct.
     */
    public OrderProduct(Cursor pCursor){
        this.idOrder = pCursor.getString(pCursor.getColumnIndex(OrderProductEntry.IDOrder));
        this.idProduct = pCursor.getString(pCursor.getColumnIndex(OrderProductEntry.IDProduct));
        this.amount = pCursor.getInt(pCursor.getColumnIndex(OrderProductEntry.Amount));
        this.actualPrice = pCursor.getInt(pCursor.getColumnIndex(OrderProductEntry.ActualPrice));
    }

    /**
     * Method that converts the data of the OrderProduct to content values.
     * @return a ContentValues object with the OrderProduct.
     */
    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(OrderProductEntry.IDOrder, this.idOrder);
        values.put(OrderProductEntry.IDProduct, this.idProduct);
        values.put(OrderProductEntry.Amount, this.amount);
        values.put(OrderProductEntry.ActualPrice, this.actualPrice);
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
     * Method that returns the Product's ID.
     * @return a string with the name of the Product.
     */
    public String getIdProduct() {
        return this.idProduct;
    }

    /**
     * Method that returns the Products item's amount.
     * @return a string with the amount of items of the product.
     */
    public int getAmount() {
        return this.amount;
    }

    /**
     * Method that returns the Product actual price per item.
     * @return a string with the actual price of items of the product.
     */
    public int getActualPrice() {
        return this.actualPrice;
    }


}
