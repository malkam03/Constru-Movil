package com.construmovil.construmovil.supplier;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;


import com.construmovil.construmovil.R;
import com.construmovil.construmovil.data.Supplier;

public class SupplierDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String id = getIntent().getStringExtra(SupplierActivity.EXTRA_SUPPLIER_ID);

        SupplierDetailFragment fragment = (SupplierDetailFragment)
                getSupportFragmentManager().findFragmentById(R.id.supplier_detail_container);
        if (fragment == null) {
            fragment = SupplierDetailFragment.newInstance(id);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.supplier_detail_container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_supplier_detail, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}