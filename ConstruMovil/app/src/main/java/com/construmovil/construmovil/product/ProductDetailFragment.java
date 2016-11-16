package com.construmovil.construmovil.product;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.construmovil.construmovil.R;
import com.construmovil.construmovil.data.Product;
import com.construmovil.construmovil.data.DbHelper;

/**
 * Product Detail Fragment subclass.
 * Use the {@link ProductDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductDetailFragment extends Fragment {
    private static final String ARG_PRODUCT_ID = "productId";

    private String mProductId;

    private CollapsingToolbarLayout mCollapsingView;
    private TextView mIdProduct;
    private TextView mNameProduct;
    private TextView mCategoryProduct;
    private TextView mCedJurProduct;
    private TextView mDescriptionProduct;
    private TextView mPriceProduct;
    private TextView mExemptProduct;

    private DbHelper mDbHelper;

    public ProductDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param productId Parameter 1.
     * @return A new instance of fragment ProductDetailFragment.
     */
    public static ProductDetailFragment newInstance(String productId) {
        ProductDetailFragment fragment = new ProductDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PRODUCT_ID, productId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mProductId = getArguments().getString(ARG_PRODUCT_ID);
        }
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View root = inflater.inflate(R.layout.fragment_product_detail, container, false);
        mCollapsingView = (CollapsingToolbarLayout) getActivity().findViewById(R.id.toolbar_layout);
        mIdProduct = (TextView) root.findViewById(R.id.tv_product_id);
        mNameProduct = (TextView) root.findViewById(R.id.tv_product_name);
        mCategoryProduct = (TextView) root.findViewById(R.id.tv_product_category_id);
        mCedJurProduct = (TextView) root.findViewById(R.id.tv_product_ced_jur);
        mDescriptionProduct = (TextView) root.findViewById(R.id.tv_product_description);
        mPriceProduct = (TextView) root.findViewById(R.id.tv_product_price);
        mExemptProduct = (TextView) root.findViewById(R.id.tv_product_exempt);
        mDbHelper = new DbHelper(getActivity());

        loadProduct();

        return root;
    }

    private void loadProduct() {
        new GetProductByIdTask().execute();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit:
                showEditScreen();
                break;
            case R.id.action_delete:
                new DeleteProductTask().execute();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ProductFragment.REQUEST_UPDATE_DELETE_PRODUCT) {
            if (resultCode == Activity.RESULT_OK) {
                getActivity().setResult(Activity.RESULT_OK);
                getActivity().finish();
            }
        }
    }

    private void showProduct(Product product) {
        mCollapsingView.setTitle(product.getProductName());
        mIdProduct.setText(product.getId());
        mNameProduct.setText(product.getProductName());
        mCategoryProduct.setText(product.getCategoryID());
        mCedJurProduct.setText(product.getSupplierID());
        mDescriptionProduct.setText(product.getDescription());
        mPriceProduct.setText(product.getPrice());
        if (product.isExempt()) {
            mExemptProduct.setText("Sí");
        }
        else {
            mExemptProduct.setText("Sí");
        }

    }

    private void showEditScreen() {
        Intent intent = new Intent(getActivity(), AddEditProductActivity.class);
        intent.putExtra(ProductActivity.EXTRA_PRODUCT_ID, mProductId);
        startActivityForResult(intent, ProductFragment.REQUEST_UPDATE_DELETE_PRODUCT);
    }

    private void showProductScreen(boolean requery) {
        if (!requery) {
            showDeleteError();
        }
        getActivity().setResult(requery ? Activity.RESULT_OK : Activity.RESULT_CANCELED);
        getActivity().finish();
    }

    private void showLoadError() {
        Toast.makeText(getActivity(),
                "Error al cargar información", Toast.LENGTH_SHORT).show();
    }

    private void showDeleteError() {
        Toast.makeText(getActivity(),
                "Error al eliminar Producto", Toast.LENGTH_SHORT).show();
    }

    private class GetProductByIdTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return mDbHelper.getProductbyID(mProductId);
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null && cursor.moveToLast()) {
                showProduct(new Product(cursor));
            } else {
                showLoadError();
            }
        }

    }

    private class DeleteProductTask extends AsyncTask<Void, Void, Integer> {

        @Override
        protected Integer doInBackground(Void... voids) {
            return mDbHelper.deletePerson(mProductId);
        }

        @Override
        protected void onPostExecute(Integer integer) {
            showProductScreen(integer > 0);
        }

    }


}
