package com.construmovil.construmovil.product;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.construmovil.construmovil.R;
import com.construmovil.construmovil.data.DbHelper;
import static com.construmovil.construmovil.data.ProductContract.ProductEntry;

/**
 * The Products Fragment for ProductsActivity.
 * Use the {@link ProductFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductFragment extends Fragment {
    public static final int REQUEST_UPDATE_DELETE_PRODUCT = 2;

    private DbHelper mDbHelper;
    private ListView mProductList;
    private ProductCursorAdapter mProductAdapter;
    private FloatingActionButton mAddButton;

    public ProductFragment() {
        // Required empty public constructor
    }

    public static ProductFragment newInstance() {
        return new ProductFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_product, container, false);

        //Referencias UI
        mProductList = (ListView) root.findViewById(R.id.product_list);
        mProductAdapter = new ProductCursorAdapter(getActivity(), null);
        mAddButton = (FloatingActionButton) getActivity().findViewById(R.id.fab);

        //Setup
        mProductList.setAdapter(mProductAdapter);

        //Eventos
        mProductList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cursor currentItem = (Cursor) mProductAdapter.getItem(i);
                String currentProductId = currentItem.getString(
                        currentItem.getColumnIndex(ProductEntry.ID));

                showDetailScreen(currentProductId);
            }
        });
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddScreen();
            }
        });

        getActivity().deleteDatabase(DbHelper.DATABASE_NAME);

        // Instancia de helper
        mDbHelper = new DbHelper(getActivity());

        // Carga de datos
        loadProduct();

        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Activity.RESULT_OK == resultCode) {
            switch (requestCode) {
                case AddEditProductActivity.REQUEST_ADD_PRODUCT:
                    showSuccessfullSavedMessage();
                    loadProduct();
                    break;
                case REQUEST_UPDATE_DELETE_PRODUCT:
                    loadProduct();
                    break;
            }
        }

    }

    private void loadProduct() {
        //Cargar datos
        new ProductLoadTask().execute();
    }

    private void showSuccessfullSavedMessage() {
        Toast.makeText(getActivity(),
                "Producto guardado correctamente", Toast.LENGTH_SHORT).show();
    }

    private void showAddScreen() {
        Intent intent = new Intent(getActivity(), AddEditProductActivity.class);
        startActivityForResult(intent, AddEditProductActivity.REQUEST_ADD_PRODUCT);
    }

    private void showDetailScreen(String productId) {
        Intent intent = new Intent(getActivity(), ProductDetailActivity.class);
        intent.putExtra(ProductActivity.EXTRA_PRODUCT_ID, productId);
        startActivityForResult(intent, REQUEST_UPDATE_DELETE_PRODUCT);
    }

    private class ProductLoadTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return mDbHelper.getAllProducts();
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null && cursor.getCount() > 0) {
                mProductAdapter.swapCursor(cursor);
            } else {
                // Mostrar empty state
            }
        }
    }
}
