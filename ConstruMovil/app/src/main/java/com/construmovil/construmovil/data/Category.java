package com.construmovil.construmovil.data;

import android.content.ContentValues;
import android.database.Cursor;
import com.construmovil.construmovil.data.CategoryContract.CategoryEntry;

/**
 * Created by Malcolm Davis on 11/15/2016.
 * Email: me@malcolmdavis.xyz
 */

/**
 * Category Entity
 */
public class Category {
    private String id;
    private String categoryName;
    private String description;

    /**
     * Constructor of the Category Class
     * @param pID the ID of the Category.
     * @param pCategoryName the name of the Category.
     * @param pDescription the detailed description of what the Category is.
     */
    public Category(String pID, String pCategoryName, String pDescription){
        this.id = pID;
        this.categoryName = pCategoryName;
        this.description = pDescription;
    }

    /**
     * Second constructor of the Category Class.
     * @param pCursor a cursor with the data of the Category.
     */
    public Category(Cursor pCursor){
        this.id = pCursor.getString(pCursor.getColumnIndex(CategoryEntry.ID));
        this.categoryName = pCursor.getString(pCursor.getColumnIndex(CategoryEntry.Name));
        this.description = pCursor.getString(pCursor.getColumnIndex(CategoryEntry.Description));
    }

    /**
     * Method that converts the data of the Category to content values.
     * @return a ContentValues object with the Category.
     */
    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(CategoryEntry.ID, this.id);
        values.put(CategoryEntry.Name, this.categoryName);
        values.put(CategoryEntry.Description, this.description);
        return values;
    }



    /**
     * Method that returns the ID of the Category.
     * @return a string with the ID of the Category
     */
    public String getId() {
        return id;
    }

    /**
     * Method that returns the Category's name.
     * @return a string with the name of the Category.
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * Method that returns the Category's Description.
     * @return a string with the description of the Category.
     */
    public String getDescription() {
        return description;
    }

}
