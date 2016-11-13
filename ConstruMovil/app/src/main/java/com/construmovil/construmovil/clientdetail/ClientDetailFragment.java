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

import com.bumptech.glide.Glide;
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
    private ImageView mAvatar;
    private TextView mPhoneNumber;
    private TextView mSpeciality;
    private TextView mBio;

    private TextView mCedula;
    private TextView mTelefono;
    private TextView mDireccion;
    private TextView mFNacimiento;

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
        mAvatar = (ImageView) getActivity().findViewById(R.id.iv_avatar);
        mPhoneNumber = (TextView) root.findViewById(R.id.tv_phone_number);
        mSpeciality = (TextView) root.findViewById(R.id.tv_specialty);
        mBio = (TextView) root.findViewById(R.id.tv_bio);
        mDbHelper = new DbHelper(getActivity());

        loadClient();

        return root;
    }

    private void loadClient() {
        new GetClientByIdTask().execut();
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
        if (requestCode == ClientsFragment.REQUEST_UPDATE_DELETE_LAWYER) {
            if (resultCode == Activity.RESULT_OK) {
                getActivity().setResult(Activity.RESULT_OK);
                getActivity().finish();
            }
        }
    }

    private void showClient(Person client) {
        mCollapsingView.setTitle(client.getName());
        Glide.with(this)
                .load(Uri.parse("file:///android_asset/" + client.getAvatarUri()))
                .centerCrop()
                .into(mAvatar);
        mPhoneNumber.setText(client.getPhoneNumber());
        mSpeciality.setText(client.getSpecialty());
        mBio.setText(client.getBio());
    }

    private void showEditScreen() {
        Intent intent = new Intent(getActivity(), AddEditClientActivity.class);
        intent.putExtra(ClientsActivity.EXTRA_CLIENT_ID, mClientId);
        startActivityForResult(intent, ClientsFragment.REQUEST_UPDATE_DELETE_CLIENT);
    }

    private void showLawyersScreen(boolean requery) {
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
            showLawyersScreen(integer > 0);
        }

    }


}
