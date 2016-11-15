package com.construmovil.construmovil.clients;


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
import com.construmovil.construmovil.data.Person;
import com.construmovil.construmovil.data.User;
import com.construmovil.construmovil.data.UserRol;
import com.construmovil.construmovil.data.DbHelper;

/**
 * Add / Edit Client Fragment.
 * Use the {@link AddEditClientFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddEditClientFragment extends Fragment {
    private static final String ARG_CLIENT_ID = "arg_client_id";

    private String mClientId;

    private DbHelper mDbHelper;

    private FloatingActionButton mSaveButton;
    private TextInputEditText mIdField;
    private TextInputEditText mUserField;
    private TextInputEditText mPasswordField;
    private TextInputEditText mNameField;
    private TextInputEditText mMiddleNameField;
    private TextInputEditText mLastNameField;
    private TextInputEditText mPhoneNumberField;
    private TextInputEditText mAddressField;
    private TextInputEditText mBirthDateField;

    private TextInputLayout mIdLabel;
    private TextInputLayout mUserLabel;
    private TextInputLayout mPasswordLabel;
    private TextInputLayout mNameLabel;
    private TextInputLayout mMiddleNameLabel;
    private TextInputLayout mLastNameLabel;
    private TextInputLayout mPhoneNumberLabel;
    private TextInputLayout mAddressLabel;
    private TextInputLayout mBirthDateLabel;

    public AddEditClientFragment() {
        // Required empty public constructor
    }


    public static AddEditClientFragment newInstance(String clientId) {
        AddEditClientFragment fragment = new AddEditClientFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CLIENT_ID, clientId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mClientId = getArguments().getString(ARG_CLIENT_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_add_edit_client, container, false);
        mSaveButton = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        mIdField = (TextInputEditText) root.findViewById(R.id.et_id);
        mUserField = (TextInputEditText) root.findViewById(R.id.et_user);
        mPasswordField = (TextInputEditText) root.findViewById(R.id.et_pass);
        mNameField = (TextInputEditText) root.findViewById(R.id.et_name);
        mMiddleNameField = (TextInputEditText) root.findViewById(R.id.et_middle_name);
        mLastNameField = (TextInputEditText) root.findViewById(R.id.et_last_name);
        mPhoneNumberField = (TextInputEditText) root.findViewById(R.id.et_phone_number);
        mAddressField = (TextInputEditText) root.findViewById(R.id.et_address);
        mBirthDateField = (TextInputEditText) root.findViewById(R.id.et_birth_date);

        mIdLabel = (TextInputLayout) root.findViewById(R.id.til_id);
        mUserLabel = (TextInputLayout) root.findViewById(R.id.til_user);
        mPasswordLabel = (TextInputLayout) root.findViewById(R.id.til_pass);
        mNameLabel = (TextInputLayout) root.findViewById(R.id.til_name);
        mMiddleNameLabel = (TextInputLayout) root.findViewById(R.id.til_middle_name);
        mLastNameLabel = (TextInputLayout) root.findViewById(R.id.til_last_name);
        mPhoneNumberLabel = (TextInputLayout) root.findViewById(R.id.til_phone_number);
        mAddressLabel = (TextInputLayout) root.findViewById(R.id.til_address);
        mBirthDateLabel = (TextInputLayout) root.findViewById(R.id.til_birth_date);

        //Eventos
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addEditClient();
            }
        });

        mDbHelper = new DbHelper(getActivity());

        //Carga de datos
        if (mClientId != null) {
            loadClient();
        }
        return root;
    }

    private void loadClient() {
        new GetClientByIdTask().execute();
    }

    private void addEditClient() {
        boolean error = false;

        String id = mIdField.getText().toString();
        String username = mUserField.getText().toString();
        String password = mPasswordField.getText().toString();
        String name = mNameField.getText().toString();
        String middleName = mMiddleNameField.getText().toString();
        String lastName = mLastNameField.getText().toString();
        String phoneNumber = mPhoneNumberField.getText().toString();
        String address = mAddressField.getText().toString();
        String birthDate = mBirthDateField.getText().toString();


        if(TextUtils.isEmpty(id)) {
            mIdLabel.setError(getString(R.string.field_error));
            error = true;
        }
        if(TextUtils.isEmpty(username)) {
            mUserLabel.setError(getString(R.string.field_error));
            error = true;
        }
        if(TextUtils.isEmpty(password)) {
            mPasswordLabel.setError(getString(R.string.field_error));
            error = true;
        }
        if(TextUtils.isEmpty(name)) {
            mNameLabel.setError(getString(R.string.field_error));
            error = true;
        }
        if(TextUtils.isEmpty(middleName)) {
            mMiddleNameLabel.setError(getString(R.string.field_error));
            error = true;
        }
        if(TextUtils.isEmpty(lastName)) {
            mLastNameLabel.setError(getString(R.string.field_error));
            error = true;
        }
        if(TextUtils.isEmpty(phoneNumber)) {
            mPhoneNumberLabel.setError(getString(R.string.field_error));
            error = true;
        }
        if(TextUtils.isEmpty(address)) {
            mAddressLabel.setError(getString(R.string.field_error));
            error = true;
        }
        if(TextUtils.isEmpty(birthDate)) {
            mBirthDateLabel.setError(getString(R.string.field_error));
            error = true;
        }
        if(error) {
            return;
        }

        User user = new User(username, password);
        new AddEditUserTask().execute(user);

        Person client = new Person(id, username, name, middleName, lastName, phoneNumber, address, birthDate);
        new AddEditClientTask().execute(client);

        UserRol userRol = new UserRol("5", username);
        new AddUserRolTask().execute(userRol);



        //FALTA CREAR LA INSERSIÓN EN LA TABLA USERROL

    }

    private void showClientsScreen(Boolean requery) {
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

    private void showClient(Person client) {
        mIdField.setText(client.getId());
        mUserField.setText(client.getUserName());
        mNameField.setText(client.getName());
        mMiddleNameField.setText(client.getMiddleName());
        mLastNameField.setText(client.getLastName());
        mPhoneNumberField.setText(client.getPhoneNumber());
        mAddressField.setText(client.getAddress());
        mBirthDateField.setText(client.getBirthDate());
    }

    private void showLoadError() {
        Toast.makeText(getActivity(),
                "Error al editar Cliente", Toast.LENGTH_SHORT).show();
    }

    private class GetClientByIdTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return mDbHelper.getPersonById(mClientId);
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null && cursor.moveToLast()) {
                showClient(new Person(cursor));
            } else {
                showLoadError();
                getActivity().setResult(Activity.RESULT_CANCELED);
                getActivity().finish();
            }
        }

    }

    private class AddEditClientTask extends AsyncTask<Person, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Person... clients) {
            if (mClientId != null) {
                return mDbHelper.updatePerson(clients[0], mClientId) > 0;
            } else {
                return mDbHelper.savePerson(clients[0]) > 0;
            }
        }
        @Override
        protected void onPostExecute(Boolean result) {
            showClientsScreen(result);
        }
    }

    private class AddEditUserTask extends AsyncTask<User, Void, Boolean> {

        @Override
        protected Boolean doInBackground(User... users) {
            if (mClientId != null) {
                return mDbHelper.updateUser(users[0], mClientId) > 0;
            } else {
                return mDbHelper.saveUser(users[0]) > 0;
            }
        }
        @Override
        protected void onPostExecute(Boolean result) {
            showClientsScreen(result);
        }
    }

    private class AddUserRolTask extends AsyncTask<UserRol, Void, Boolean> {

        @Override
        protected Boolean doInBackground(UserRol... users) {
            return mDbHelper.saveUserRol(users[0]) > 0;

        }
        @Override
        protected void onPostExecute(Boolean result) {
            showClientsScreen(result);
        }
    }

}
