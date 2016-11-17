package com.construmovil.construmovil.category;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.construmovil.construmovil.R;
import com.construmovil.construmovil.data.CategoryContract.CategoryEntry;

/**
 * Created by mchin on 16/11/2016.
 * Adaptador para Categorías
 */

public class CategoryCursorAdapter extends CursorAdapter{

    public CategoryCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return inflater.inflate(R.layout.list_item_category, viewGroup, false);
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {

        // Referencias UI.
        TextView nameText = (TextView) view.findViewById(R.id.tv_category_name);

        // Get valores.
        String name = cursor.getString(cursor.getColumnIndex(CategoryEntry.Name));


        // Setup.
        nameText.setText(name);
    }
}
