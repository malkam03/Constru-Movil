package com.construmovil.construmovil.category;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.construmovil.construmovil.R;

public class CategoryActivity extends AppCompatActivity {

    public static final String EXTRA_CATEGORY_ID = "extra_category_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        CategoryFragment fragment = (CategoryFragment)
                getSupportFragmentManager().findFragmentById(R.id.category_container);

        if (fragment == null) {
            fragment = CategoryFragment.newInstance();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.category_container, fragment)
                    .commit();
        }
    }
}
