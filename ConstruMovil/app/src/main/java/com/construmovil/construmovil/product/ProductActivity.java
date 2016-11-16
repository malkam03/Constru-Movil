package com.construmovil.construmovil.product;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.construmovil.construmovil.R;


public class ProductActivity extends AppCompatActivity {
    public static final String EXTRA_PRODUCT_ID = "extra_product_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ProductFragment fragment = (ProductFragment)
                getSupportFragmentManager().findFragmentById(R.id.product_container);

        if (fragment == null) {
            fragment = ProductFragment.newInstance();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.product_container, fragment)
                    .commit();
        }
    }
}