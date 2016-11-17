package com.construmovil.construmovil.category;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.construmovil.construmovil.R;

public class CategoryDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String id = getIntent().getStringExtra(CategoryActivity.EXTRA_CATEGORY_ID);

        CategoryDetailFragment fragment = (CategoryDetailFragment)
                getSupportFragmentManager().findFragmentById(R.id.category_detail_container);
        if (fragment == null) {
            fragment = CategoryDetailFragment.newInstance(id);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.category_detail_container, fragment)
                    .commit();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_category_detail, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
