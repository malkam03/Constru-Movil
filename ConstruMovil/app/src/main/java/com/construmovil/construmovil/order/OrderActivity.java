package com.construmovil.construmovil.order;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.construmovil.construmovil.R;

public class OrderActivity extends AppCompatActivity {

    public static final String EXTRA_ORDER_ID = "extra_order_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        OrderFragment fragment = (OrderFragment)
                getSupportFragmentManager().findFragmentById(R.id.order_container);

        if (fragment == null) {
            fragment = OrderFragment.newInstance();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.order_container, fragment)
                    .commit();
        }
    }

}
