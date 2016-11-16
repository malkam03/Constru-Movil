package com.construmovil.construmovil.supplier;


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
import static com.construmovil.construmovil.data.SupplierContract.SupplierEntry;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SupplierFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SupplierFragment extends Fragment {
    public static final int REQUEST_UPDATE_DELETE_SUPPLIER = 2;

    private DbHelper mDbHelper;
    private ListView mSupplierList;
    private SupplierCursorAdapter mSupplierAdapter;
    private FloatingActionButton mAddButton;

    public SupplierFragment() {
        // Required empty public constructor
    }

    public static SupplierFragment newInstance() {
        return new SupplierFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_supplier, container, false);

        //Referencias UI
        mSupplierList = (ListView) root.findViewById(R.id.supplier_list);
        mSupplierAdapter = new SupplierCursorAdapter(getActivity(), null);
        mAddButton = (FloatingActionButton) getActivity().findViewById(R.id.fab);

        //Setup
        mSupplierList.setAdapter(mSupplierAdapter);

        //Eventos
        mSupplierList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cursor currentItem = (Cursor) mSupplierAdapter.getItem(i);
                String currentSupplierId = currentItem.getString(
                        currentItem.getColumnIndex(SupplierEntry.ID));

                showDetailScreen(currentSupplierId);
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
        loadSupplier();

        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Activity.RESULT_OK == resultCode) {
            switch (requestCode) {
                case AddEditSupplierActivity.REQUEST_ADD_SUPPLIER:
                    showSuccessfullSavedMessage();
                    loadSupplier();
                    break;
                case REQUEST_UPDATE_DELETE_SUPPLIER:
                    loadSupplier();
                    break;
            }
        }

    }

    private void loadSupplier() {
        //Cargar datos
        new SupplierLoadTask().execute();
    }

    private void showSuccessfullSavedMessage() {
        Toast.makeText(getActivity(),
                "Proveedor guardado correctamente", Toast.LENGTH_SHORT).show();
    }

    private void showAddScreen() {
        Intent intent = new Intent(getActivity(), AddEditSupplierActivity.class);
        startActivityForResult(intent, AddEditSupplierActivity.REQUEST_ADD_SUPPLIER);
    }

    private void showDetailScreen(String supplierId) {
        Intent intent = new Intent(getActivity(), SupplierDetailActivity.class);
        intent.putExtra(SupplierActivity.EXTRA_SUPPLIER_ID, supplierId);
        startActivityForResult(intent, REQUEST_UPDATE_DELETE_SUPPLIER);
    }

    private class SupplierLoadTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return mDbHelper.getAllSupplier();
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null && cursor.getCount() > 0) {
                mSupplierAdapter.swapCursor(cursor);
            } else {
                // Mostrar empty state
            }
        }
    }
}
