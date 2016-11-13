package com.construmovil.construmovil.clients;


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
import com.construmovil.construmovil.addeditclient.AddEditClientActivity;
import com.construmovil.construmovil.clientdetail.ClientDetailActivity;
import com.construmovil.construmovil.data.DbHelper;
import static com.construmovil.construmovil.data.PersonContract.PersonEntry;


/**
 * The Clients Fragment of Clients.
 * Use the {@link ClientsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ClientsFragment extends Fragment {

    public static final int REQUEST_UPDATE_DELETE_CLIENT = 2;

    private DbHelper mDbHelper;
    private ListView mClientsList;
    private ClientsCursorAdapter mClientsAdapter;
    private FloatingActionButton mAddButton;

    public ClientsFragment() {
        // Required empty public constructor
    }

    public static ClientsFragment newInstance() {
        return new ClientsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_clients, container, false);

        //Referencias UI
        mClientsList = (ListView) root.findViewById(R.id.clients_list);
        mClientsAdapter = new ClientsCursorAdapter(getActivity(), null);
        mAddButton = (FloatingActionButton) getActivity().findViewById(R.id.fab);

        //Setup
        mClientsList.setAdapter(mClientsAdapter);

        //Eventos
        mClientsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cursor currentItem = (Cursor) mClientsAdapter.getItem(i);
                String currentLawyerId = currentItem.getString(
                        currentItem.getColumnIndex(PersonEntry.ID));

                showDetailScreen(currentClientId);
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
        loadClients();

        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Activity.RESULT_OK == resultCode) {
            switch (requestCode) {
                case AddEditClientActivity.REQUEST_ADD_LAWYER:
                    showSuccessfullSavedMessage();
                    loadClients();
                    break;
                case REQUEST_UPDATE_DELETE_CLIENT:
                    loadClients();
                    break;
            }
        }

    }

    private void loadClients() {
        //Cargar datos
        new ClientsLoadTask().execute();
    }

    private void showSuccessfullSavedMessage() {
        Toast.makeText(getActivity(),
        "Cliente guardado correctamente", Toast.LENGTH_SHORT).show();
    }

    private void showAddScreen() {
        Intent intent = new Intent(getActivity(), AddEditClientActivity.class);
        startActivityForResult(intent, AddEditClientActivity.REQUEST_ADD_CLIENT);
    }

    private void showDetailScreen(String clientId) {
        Intent intent = new Intent(getActivity(), ClientDetailActivity.class);
        intent.putExtra(ClientsActivity.EXTRA_CLIENT_ID, clientId);
        startActivityForResult(intent, REQUEST_UPDATE_DELETE_CLIENT);
    }

    private class ClientsLoadTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return mDbHelper.getAllPersons();
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null && cursor.getCount() > 0) {
                mClientsAdapter.swapCursor(cursor);
            } else {
                // Mostrar empty state
            }
        }
    }

}
