package com.construmovil.construmovil.home;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.construmovil.construmovil.R;
import com.construmovil.construmovil.category.CategoryActivity;
import com.construmovil.construmovil.clients.ClientsActivity;
import com.construmovil.construmovil.order.OrderActivity;
import com.construmovil.construmovil.product.ProductActivity;
import com.construmovil.construmovil.supplier.SupplierActivity;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        final Button clientButton = (Button) findViewById(R.id.btn_button_clients);
        clientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, ClientsActivity.class);
                startActivity(intent);
            }
        });

        final Button supplierButton = (Button) findViewById(R.id.btn_button_suppliers);
        supplierButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, SupplierActivity.class);
                startActivity(intent);
            }
        });

        final Button sellerButton = (Button) findViewById(R.id.btn_button_sellers);
        sellerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, SupplierActivity.class);
                startActivity(intent);
            }
        });

        final Button categoryButton = (Button) findViewById(R.id.btn_button_categories);
        categoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, CategoryActivity.class);
                startActivity(intent);
            }
        });

        final Button productButton = (Button) findViewById(R.id.btn_button_products);
        productButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, ProductActivity.class);
                startActivity(intent);
            }
        });

        final Button orderButton = (Button) findViewById(R.id.btn_button_orders);
        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, OrderActivity.class);
                startActivity(intent);
            }
        });
    }
}
