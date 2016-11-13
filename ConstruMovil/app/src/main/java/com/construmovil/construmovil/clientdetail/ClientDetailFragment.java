package com.construmovil.construmovil.clientdetail;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.construmovil.construmovil.R;
import com.construmovil.construmovil.addeditclient.AddEditClientActivity;
import com.construmovil.construmovil.data.Person;
import com.construmovil.construmovil.data.DbHelper;
import com.construmovil.construmovil.clients.ClientsActivity;
import com.construmovil.construmovil.clients.ClientsFragment;

import com.construmovil.construmovil.R;

/**
 * Clients Detail Fragment.
 * Use the {@link ClientDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ClientDetailFragment extends Fragment {

    private static final String ARG_CLIENT_ID = "clientId";

    private String mClientId;

    private CollapsingToolbarLayout mCollapsingView;
    private TextView mId;
    private TextView mUser;
    private TextView mName;
    private TextView mMiddleName;
    private TextView mLastName;
    private TextView mPhoneNumber;
    private TextView mAddress;
    private TextView mBirthDate;

    private DbHelper mDbHelper;

    public ClientDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param clientId Parameter 1.
     * @return A new instance of fragment ClientDetailFragment.
     */
    public static ClientDetailFragment newInstance(String clientId) {
        ClientDetailFragment fragment = new ClientDetailFragment();
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
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View root = inflater.inflate(R.layout.fragment_client_detail, container, false);
        mCollapsingView = (CollapsingToolbarLayout) getActivity().findViewById(R.id.toolbar_layout);
        mId = (TextView) root.findViewById(R.id.tv_id);
        mUser = (TextView) root.findViewById(R.id.tv_user);
        mName = (TextView) root.findViewById(R.id.tv_name);
        mMiddleName = (TextView) root.findViewById(R.id.tv_middle_name);
        mLastName = (TextView) root.findViewById(R.id.tv_last_name);
        mPhoneNumber = (TextView) root.findViewById(R.id.tv_phone_number);
        mAddress = (TextView) root.findViewById(R.id.tv_address);
        mBirthDate = (TextView) root.findViewById(R.id.tv_birth_date);
        mDbHelper = new DbHelper(getActivity());

        loadClient();

        return root;
    }

    private void loadClient() {
        new GetClientByIdTask().execute();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit:
                showEditScreen();
                break;
            case R.id.action_delete:
                new DeleteClientTask().execute();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ClientsFragment.REQUEST_UPDATE_DELETE_CLIENT) {
            if (resultCode == Activity.RESULT_OK) {
                getActivity().setResult(Activity.RESULT_OK);
                getActivity().finish();
            }
        }
    }

    private void showClient(Person client) {
        mCollapsingView.setTitle(client.getName() + client.getMiddleName() + client.getLastName());
        mId.setText(client.getId());
        mUser.setText(client.getUserName());
        mName.setText(client.getName());
        mMiddleName.setText(client.getMiddleName());
        mLastName.setText(client.getLastName());
        mPhoneNumber.setText(client.getPhoneNumber());
        mAddress.setText(client.getAddress());
        mBirthDate.setText(client.getBirthDate());
    }

    private void showEditScreen() {
        Intent intent = new Intent(getActivity(), AddEditClientActivity.class);
        intent.putExtra(ClientsActivity.EXTRA_CLIENT_ID, mClientId);
        startActivityForResult(intent, ClientsFragment.REQUEST_UPDATE_DELETE_CLIENT);
    }

    private void showClientsScreen(boolean requery) {
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
                "Error al eliminar Cliente", Toast.LENGTH_SHORT).show();
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
            }
        }

    }

    private class DeleteClientTask extends AsyncTask<Void, Void, Integer> {

        @Override
        protected Integer doInBackground(Void... voids) {
            return mDbHelper.deletePerson(mClientId);
        }

        @Override
        protected void onPostExecute(Integer integer) {
            showClientsScreen(integer > 0);
        }

    }


}
