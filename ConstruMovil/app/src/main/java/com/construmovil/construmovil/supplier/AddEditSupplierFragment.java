package com.construmovil.construmovil.supplier;


import android.app.Activity;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
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
import com.construmovil.construmovil.data.Supplier;
import com.construmovil.construmovil.data.DbHelper;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddEditSupplierFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddEditSupplierFragment extends Fragment {
    private static final String ARG_SUPPLIER_ID = "arg_supplier_id";

    private String mSupplierId;

    private DbHelper mDbHelper;

    private FloatingActionButton mSaveButton;
    private TextInputEditText mBusinessNameField;
    private TextInputEditText mCedJurField;
    private TextInputEditText mCedRepField;


    private TextInputLayout mBusinessNameLabel;
    private TextInputLayout mCedJurLabel;
    private TextInputLayout mCedRepLabel;


    public AddEditSupplierFragment() {
        // Required empty public constructor
    }


    public static AddEditSupplierFragment newInstance(String supplierId) {
        AddEditSupplierFragment fragment = new AddEditSupplierFragment();
        Bundle args = new Bundle();
        args.putString(ARG_SUPPLIER_ID, supplierId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mSupplierId = getArguments().getString(ARG_SUPPLIER_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_add_edit_supplier, container, false);
        mSaveButton = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        mBusinessNameField = (TextInputEditText) root.findViewById(R.id.et_business_name);
        mCedJurField = (TextInputEditText) root.findViewById(R.id.et_ced_jur);
        mCedRepField= (TextInputEditText) root.findViewById(R.id.et_ced_rep);


        mBusinessNameLabel = (TextInputLayout) root.findViewById(R.id.til_business_name);
        mCedJurLabel = (TextInputLayout) root.findViewById(R.id.til_ced_jur);
        mCedRepLabel = (TextInputLayout) root.findViewById(R.id.til_ced_rep);


        //Eventos
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addEditSupplier();
            }
        });

        mDbHelper = new DbHelper(getActivity());

        //Carga de datos
        if (mSupplierId != null) {
            loadSupplier();
        }
        return root;
    }

    private void loadSupplier() {
        new GetSupplierByIdTask().execute();
    }

    private void addEditSupplier() {
        boolean error = false;

        String businessName = mBusinessNameField.getText().toString();
        String cedJur = mCedJurField.getText().toString();
        String cedRep = mCedRepField.getText().toString();


        if(TextUtils.isEmpty(businessName)) {
            mBusinessNameLabel.setError(getString(R.string.field_error));
            error = true;
        }
        if(TextUtils.isEmpty(cedJur)) {
            mCedJurLabel.setError(getString(R.string.field_error));
            error = true;
        }
        if(TextUtils.isEmpty(cedRep)) {
            mCedRepLabel.setError(getString(R.string.field_error));
            error = true;
        }

        if(error) {
            return;
        }

        Supplier supplier = new Supplier(cedRep,businessName,cedJur);
        new AddEditSupplierTask().execute(supplier);
    }

    private void showSupplierScreen(Boolean requery) {
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

    private void showSupplier(Supplier supplier) {
        mBusinessNameField.setText(supplier.getBusinessName());
        mCedJurField.setText(supplier.getId());
        mCedRepField.setText(supplier.getIdPerson());
    }

    private void showLoadError() {
        Toast.makeText(getActivity(),
                "Error al editar Proveedor", Toast.LENGTH_SHORT).show();
    }

    private class GetSupplierByIdTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return mDbHelper.getSupplierByID(mSupplierId);
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null && cursor.moveToLast()) {
                showSupplier(new Supplier(cursor));
            } else {
                showLoadError();
                getActivity().setResult(Activity.RESULT_CANCELED);
                getActivity().finish();
            }
        }

    }

    private class AddEditSupplierTask extends AsyncTask<Supplier, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Supplier... suppliers) {
            if (mSupplierId != null) {
                return mDbHelper.updateSupplier(suppliers[0], mSupplierId) > 0;
            } else {
                return mDbHelper.saveSupplier(suppliers[0]) > 0;
            }
        }
        @Override
        protected void onPostExecute(Boolean result) {
            showSupplierScreen(result);
        }
    }

}
