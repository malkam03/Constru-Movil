package com.construmovil.construmovil.office;


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
import com.construmovil.construmovil.data.Office;
import com.construmovil.construmovil.data.DbHelper;

/**
 * Add / Edit Office Fragment.
 * Use the {@link AddEditOfficeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddEditOfficeFragment extends Fragment {


    private static final String ARG_OFFICE_ID = "arg_office_id";

    private String mOfficeId;

    private DbHelper mDbHelper;

    private FloatingActionButton mSaveButton;
    private TextInputEditText mNameOfficeField;
    private TextInputEditText mAddressOfficeField;

    private TextInputLayout mNameOfficeLabel;
    private TextInputLayout mAddressOfficeLabel;

    public AddEditOfficeFragment() {
        // Required empty public constructor
    }


    public static AddEditOfficeFragment newInstance(String officeId) {
        AddEditOfficeFragment fragment = new AddEditOfficeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_OFFICE_ID, officeId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mOfficeId = getArguments().getString(ARG_OFFICE_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_add_edit_office, container, false);
        mSaveButton = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        mAddressOfficeField = (TextInputEditText) root.findViewById(R.id.et_office_address);
        mNameOfficeField = (TextInputEditText) root.findViewById(R.id.et_office_name);


        mNameOfficeLabel = (TextInputLayout) root.findViewById(R.id.til_office_name);
        mAddressOfficeLabel = (TextInputLayout) root.findViewById(R.id.til_office_address);


        //Eventos
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addEditOffice();
            }
        });

        mDbHelper = new DbHelper(getActivity());

        //Carga de datos
        if (mOfficeId != null) {
            loadOffice();
        }
        return root;
    }

    private void loadOffice() {
        new GetOfficeByIdTask().execute();
    }

    private void addEditOffice() {
        boolean error = false;

        String nameOffice = mNameOfficeField.getText().toString();
        String addressOffice = mAddressOfficeField.getText().toString();


        if(TextUtils.isEmpty(nameOffice)) {
            mNameOfficeLabel.setError(getString(R.string.field_error));
            error = true;
        }
        if(TextUtils.isEmpty(addressOffice)) {
            mAddressOfficeLabel.setError(getString(R.string.field_error));
            error = true;
        }
        if(error) {
            return;
        }

        Office office = new Office(nameOffice, addressOffice);
        new AddEditOfficeTask().execute(office);



        //FALTA CREAR LA INSERSIÓN EN LA TABLA USERROL

    }

    private void showOfficeScreen(Boolean requery) {
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

    private void showOffice(Office office) {
        mNameOfficeField.setText(office.getName());
        mAddressOfficeField.setText(office.getAddress());

    }

    private void showLoadError() {
        Toast.makeText(getActivity(),
                "Error al editar Sucursal", Toast.LENGTH_SHORT).show();
    }

    private class GetOfficeByIdTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return mDbHelper.getPersonById(mOfficeId);
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null && cursor.moveToLast()) {
                showOffice(new Office(cursor));
            } else {
                showLoadError();
                getActivity().setResult(Activity.RESULT_CANCELED);
                getActivity().finish();
            }
        }

    }

    private class AddEditOfficeTask extends AsyncTask<Office, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Office... offices) {
            if (mOfficeId != null) {
                return mDbHelper.updateOffice(offices[0], mOfficeId) > 0;
            } else {
                return mDbHelper.saveOffice(offices[0]) > 0;
            }
        }
        @Override
        protected void onPostExecute(Boolean result) {
            showOfficeScreen(result);
        }
    }
}