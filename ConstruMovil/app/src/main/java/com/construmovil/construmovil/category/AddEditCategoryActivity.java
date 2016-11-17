package com.construmovil.construmovil.category;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;


import com.construmovil.construmovil.R;

public class AddEditCategoryActivity extends AppCompatActivity {

    public static final int REQUEST_ADD_CATEGORY = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_category);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String categoryId = getIntent().getStringExtra(CategoryActivity.EXTRA_CATEGORY_ID);

        setTitle(categoryId == null ? "Añadir categoría" : "Editar categoría");

        AddEditCategoryFragment addEditCategoryFragment = (AddEditCategoryFragment)
                getSupportFragmentManager().findFragmentById(R.id.add_edit_category_container);
        if (addEditCategoryFragment == null) {
            addEditCategoryFragment = AddEditCategoryFragment.newInstance(categoryId);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.add_edit_category_container, addEditCategoryFragment)
                    .commit();
        }
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
