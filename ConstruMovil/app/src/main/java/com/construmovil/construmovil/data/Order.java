package com.construmovil.construmovil.data;

import android.content.ContentValues;
import android.database.Cursor;
import com.construmovil.construmovil.data.OrderContract.OrderEntry;

/**
 * Created by Malcolm Davis on 11/15/2016.
 * Email: me@malcolmdavis.xyz
 */

/**
 * Order Entity
 */
public class Order {
    private String id;
    private String userName;
    private String officeName;
    private String state;
    private String phone;
    private int price;
    private String eta;
    private String orderTime;

    /**
     * Constructor of the Order Class
     * @param pID the ID of the Category.
     * @param pUserName the name of the Category.
     * @param pOfficeName the detailed description of what the Category is.
     * @param pState the ID of the Category.
     * @param pPhone the name of the Category.
     * @param pEta the detailed description of what the Category is.
     * @param pOrderTime the detailed description of what the Category is.
     */
    public Order(String pID, String pUserName, String pOfficeName, String pState,
                 String pPhone, String pEta, String pOrderTime){
        this.id = pID;
        this.userName = pUserName;
        this.officeName = pOfficeName;
        this.state = pState;
        this.phone = pPhone;
        this.price = 0;
        this.eta = pEta;
        this.orderTime = pOrderTime;
    }

    /**
     * Constructor of the Order Class
     * @param pID the ID of the Category.
     * @param pUserName the name of the Category.
     * @param pOfficeName the detailed description of what the Category is.
     * @param pState the ID of the Category.
     * @param pPhone the name of the Category.
     * @param pPrice tje price order.
     * @param pEta the detailed description of what the Category is.
     * @param pOrderTime the detailed description of what the Category is.
     */
    public Order(String pID, String pUserName, String pOfficeName, String pState,
                 String pPhone, String pEta, String pOrderTime, int pPrice){
        this.id = pID;
        this.userName = pUserName;
        this.officeName = pOfficeName;
        this.state = pState;
        this.phone = pPhone;
        this.price = pPrice;
        this.eta = pEta;
        this.orderTime = pOrderTime;
    }

    /**
     * Constructor of the Order Class.
     * @param pCursor a cursor with the data of the Order.
     */
    public Order(Cursor pCursor){
        this.id = pCursor.getString(pCursor.getColumnIndex(OrderEntry.ID));
        this.userName = pCursor.getString(pCursor.getColumnIndex(OrderEntry.UserName));
        this.officeName = pCursor.getString(pCursor.getColumnIndex(OrderEntry.OfficeName));
        this.state = pCursor.getString(pCursor.getColumnIndex(OrderEntry.State));
        this.phone = pCursor.getString(pCursor.getColumnIndex(OrderEntry.Phone));
        this.price = pCursor.getInt(pCursor.getColumnIndex(OrderEntry.Price));
        this.eta = pCursor.getString(pCursor.getColumnIndex(OrderEntry.ETA));
        this.orderTime = pCursor.getString(pCursor.getColumnIndex(OrderEntry.OrderTime));
    }

    /**
     * Method that converts the data of the Order to content values.
     * @return a ContentValues object with the Order.
     */
    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(OrderEntry.ID, this.id);
        values.put(OrderEntry.UserName, this.userName);
        values.put(OrderEntry.OfficeName, this.officeName);
        values.put(OrderEntry.State, this.state);
        values.put(OrderEntry.Phone, this.phone);
        values.put(OrderEntry.Price, this.price);
        values.put(OrderEntry.ETA, this.eta);
        values.put(OrderEntry.OrderTime, this.orderTime);
        return values;
    }



    /**
     * Method that returns the ID of the Order.
     * @return a string with the ID of the Order
     */
    public String getId() {
        return id;
    }

    /**
     * Method that returns the Order's userName.
     * @return a string with the userName of the Order.
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Method that returns the Order's OfficeName.
     * @return a string with the OfficeName of the Order.
     */
    public String getOfficeName() {
        return officeName;
    }

    /**
     * Method that returns the Order's state.
     * @return a string with the state of the Order.
     */
    public String getState() {
        return state;
    }

    /**
     * Method that returns the Order's phone number.
     * @return a string with the phone number of the Order.
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Method that returns the Order's Eta.
     * @return a string with the eta of the Order.
     */
    public String getEta() {
        return eta;
    }

    /**
     * Method that returns the Order's time.
     * @return a string with the time of the Order.
     */
    public String getOrderTime() {
        return orderTime;
    }

    /**
     * Method that sets the price of a order
     * @param price
     */
    public void setPrice(int price) {
        this.price = price;
    }

    /**
     * Method that returns the Order's Price.
     * @return a string with the price of the Order.
     */
    public int getPrice() {
        return price;
    }

}
