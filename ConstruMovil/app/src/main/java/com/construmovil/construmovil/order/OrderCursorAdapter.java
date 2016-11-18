package com.construmovil.construmovil.order;

/**
 * Created by mchin on 14/11/2016.
 */


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
import com.construmovil.construmovil.data.OrderContract.OrderEntry;

/**
 * Adaptador para ordenes
 */
public class OrderCursorAdapter extends CursorAdapter {

    public OrderCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return inflater.inflate(R.layout.list_item_order, viewGroup, false);
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {

        // Referencias UI.
        TextView nameText = (TextView) view.findViewById(R.id.tv_ordername);

        // Get valores.
        String username = cursor.getString(cursor.getColumnIndex(OrderEntry.UserName));


        // Setup.
        nameText.setText(username);
    }
}
