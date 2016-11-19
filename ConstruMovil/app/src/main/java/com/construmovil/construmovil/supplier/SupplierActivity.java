package com.construmovil.construmovil.supplier;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.construmovil.construmovil.R;
import com.construmovil.construmovil.data.Supplier;

public class SupplierActivity extends AppCompatActivity {

    public static final String EXTRA_SUPPLIER_ID = "extra_supplier_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SupplierFragment fragment = (SupplierFragment)
                getSupportFragmentManager().findFragmentById(R.id.supplier_container);

        if (fragment == null) {
            fragment = SupplierFragment.newInstance();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.supplier_container, fragment)
                    .commit();
        }
    }

}
