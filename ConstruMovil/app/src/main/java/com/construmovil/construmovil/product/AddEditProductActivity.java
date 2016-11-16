package com.construmovil.construmovil.product;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.construmovil.construmovil.R;

public class AddEditProductActivity extends AppCompatActivity {

    public static final int REQUEST_ADD_PRODUCT = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_product);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String productId = getIntent().getStringExtra(ProductActivity.EXTRA_PRODUCT_ID);

        setTitle(productId == null ? "Añadir Producto" : "Editar Producto");

        AddEditProductFragment addEditProductFragment = (AddEditProductFragment)
                getSupportFragmentManager().findFragmentById(R.id.add_edit_product_container);
        if (addEditProductFragment == null) {
            addEditProductFragment = AddEditProductFragment.newInstance(productId);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.add_edit_product_container, addEditProductFragment)
                    .commit();
        }
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}

