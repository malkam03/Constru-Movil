package com.construmovil.construmovil.order;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.construmovil.construmovil.R;

public class AddEditOrderActivity extends AppCompatActivity {

    public static final int REQUEST_ADD_ORDER = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_order);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String orderId = getIntent().getStringExtra(OrderActivity.EXTRA_ORDER_ID);

        setTitle(orderId == null ? "Añadir orden" : "Editar orden");

        AddEditOrderFragment addEditOrderFragment = (AddEditOrderFragment)
                getSupportFragmentManager().findFragmentById(R.id.add_edit_order_container);
        if (addEditOrderFragment == null) {
            addEditOrderFragment = AddEditOrderFragment.newInstance(orderId);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.add_edit_order_container, addEditOrderFragment)
                    .commit();
        }
    }

}
