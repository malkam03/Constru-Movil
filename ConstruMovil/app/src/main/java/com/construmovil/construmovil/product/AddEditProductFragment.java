package com.construmovil.construmovil.product;


        import android.app.Activity;
        import android.database.Cursor;
        import android.os.AsyncTask;
        import android.os.Bundle;
        import android.widget.EditText;
        import android.support.design.widget.FloatingActionButton;
        import android.support.design.widget.TextInputEditText;
        import android.support.design.widget.TextInputLayout;
        import android.support.v4.app.Fragment;
        import android.text.TextUtils;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Toast;

        import com.construmovil.construmovil.R;
        import com.construmovil.construmovil.data.Product;
        import com.construmovil.construmovil.data.ProductContract.ProductEntry;
        import com.construmovil.construmovil.data.DbHelper;
        import com.construmovil.construmovil.login.LoginActivity;

/**
 * Add Edit Product Fragment.
 * Use the {@link AddEditProductFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddEditProductFragment extends Fragment {
    private static final String ARG_PRODUCT_ID = "arg_product_id";

    private static final String idSeller = null;

    private String mProductId;
    private String mCurrentUser;

    private DbHelper mDbHelper;


    private FloatingActionButton mSaveButton;
    private TextInputEditText mNameProductField;
    private TextInputEditText mCategoryProductField;
    private TextInputEditText mCedJurProductField;
    private TextInputEditText mDescriptionProductField;
    private TextInputEditText mPriceProductField;
    private TextInputEditText mExemptProductField;

    private TextInputLayout mNameProductLabel;
    private TextInputLayout mCategoryProductLabel;
    private TextInputLayout mCedJurProductLabel;
    private TextInputLayout mDescriptionProductLabel;
    private TextInputLayout mPriceProductLabel;
    private TextInputLayout mExemptProductLabel;

    public AddEditProductFragment() {
        // Required empty public constructor
    }


    public static AddEditProductFragment newInstance(String productId) {
        AddEditProductFragment fragment = new AddEditProductFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_add_edit_product, container, false);
        mSaveButton = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        mNameProductField = (TextInputEditText) root.findViewById(R.id.et_product_name);
        mCategoryProductField = (TextInputEditText) root.findViewById(R.id.et_product_category_id);
        mCedJurProductField = (TextInputEditText) root.findViewById(R.id.et_product_ced_jur);
        mDescriptionProductField= (TextInputEditText) root.findViewById(R.id.et_product_description);
        mPriceProductField = (TextInputEditText) root.findViewById(R.id.et_product_price);
        mExemptProductField = (TextInputEditText) root.findViewById(R.id.et_product_exempt);

        mNameProductLabel = (TextInputLayout) root.findViewById(R.id.til_product_name);
        mCategoryProductLabel = (TextInputLayout) root.findViewById(R.id.til_product_category_id);
        mCedJurProductLabel = (TextInputLayout) root.findViewById(R.id.til_product_ced_jur);
        mDescriptionProductLabel = (TextInputLayout) root.findViewById(R.id.til_product_description);
        mPriceProductLabel = (TextInputLayout) root.findViewById(R.id.til_product_price);
        mExemptProductLabel = (TextInputLayout) root.findViewById(R.id.til_product_exempt);
        mCurrentUser = ((EditText) inflater.inflate(R.layout.activity_login, container, false).findViewById(R.id.til_login_user)).getText().toString();

        //Eventos
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addEditProduct();
            }
        });

        mDbHelper = new DbHelper(getActivity());

        //Carga de datos
        if (mProductId != null) {
            loadProduct();
        }
        return root;
    }

    private void loadProduct() {
        new GetProductByIdTask().execute();
    }

    private void addEditProduct() {
        boolean error = false;

        String idProduct = mCurrentUser;
        String nameProduct = mNameProductField.getText().toString();
        String idCategoryProduct = mCategoryProductField.getText().toString();
        String cedJurProduct = mCedJurProductField.getText().toString();
        String descriptionProduct = mDescriptionProductField.getText().toString();
        String priceProduct = mPriceProductField.getText().toString();
        String exemptProduct = mExemptProductField.getText().toString();

        if(TextUtils.isEmpty(nameProduct)) {
            mNameProductLabel.setError(getString(R.string.field_error));
            error = true;
        }
        if(TextUtils.isEmpty(idCategoryProduct)) {
            mCategoryProductLabel.setError(getString(R.string.field_error));
            error = true;
        }
        if(TextUtils.isEmpty(cedJurProduct)) {
            mCedJurProductLabel.setError(getString(R.string.field_error));
            error = true;
        }
        if(TextUtils.isEmpty(descriptionProduct)) {
            mDescriptionProductLabel.setError(getString(R.string.field_error));
            error = true;
        }
        if(TextUtils.isEmpty(priceProduct)) {
            mPriceProductLabel.setError(getString(R.string.field_error));
            error = true;
        }
        if(TextUtils.isEmpty(exemptProduct)) {
            mExemptProductLabel.setError(getString(R.string.field_error));
            error = true;
        }
        if(error) {
            return;
        }
        Product product = new Product(idProduct, nameProduct, idCategoryProduct, cedJurProduct,
                descriptionProduct, Integer.parseInt(priceProduct), Integer.parseInt(exemptProduct));
        new AddEditProductTask().execute(product);


    }

    private void showProductScreen(Boolean requery) {
        if (!requery) {
            showAddEditError();
            getActivity().setResult(Activity.RESULT_CANCELED);
        } else {
            getActivity().setResult(Activity.RESULT_OK);
        }
        getActivity().finish();
    }

    private void showAddEditError() {
        Toast.makeText(getActivity(),
                "Error al agregar nueva información", Toast.LENGTH_SHORT).show();
    }

    private void showProduct(Product product) {
        mNameProductField.setText(product.getProductName());
        mCategoryProductField.setText(product.getCategoryID());
        mCedJurProductField.setText(product.getSupplierID());
        mDescriptionProductField.setText(product.getDescription());
        mPriceProductField.setText(String.valueOf(product.getPrice()));
        mExemptProductField.setText(String.valueOf(product.getExempt()));
    }

    private void showLoadError() {
        Toast.makeText(getActivity(),
                "Error al editar Producto", Toast.LENGTH_SHORT).show();
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
                getActivity().setResult(Activity.RESULT_CANCELED);
                getActivity().finish();
            }
        }

    }

    private class AddEditProductTask extends AsyncTask<Product, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Product... product) {
            if (mProductId != null) {
                return mDbHelper.updateProduct(product[0], mProductId) > 0;
            } else {
                return mDbHelper.saveProduct(product[0]) > 0;
            }
        }
        @Override
        protected void onPostExecute(Boolean result) {
            showProductScreen(result);
        }
    }

}
