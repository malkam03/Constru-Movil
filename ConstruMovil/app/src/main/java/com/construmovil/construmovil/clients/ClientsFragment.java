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
                        currentItem.getColumnIndex(GeneralPersonEntry.ID));

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

    }

}
