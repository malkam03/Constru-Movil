package com.construmovil.construmovil.office;

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
import static com.construmovil.construmovil.data.OfficeContract.OfficeEntry;

/**
 * Office Fragment.
 * Use the {@link OfficeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OfficeFragment extends Fragment {

    public static final int REQUEST_UPDATE_DELETE_OFFICE = 2;

    private DbHelper mDbHelper;
    private ListView mOfficeList;
    private OfficeCursorAdapter mOfficeAdapter;
    private FloatingActionButton mAddButton;

    public OfficeFragment() {
        // Required empty public constructor
    }

    public static OfficeFragment newInstance() {
        return new OfficeFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_office, container, false);

        //Referencias UI
        mOfficeList = (ListView) root.findViewById(R.id.office_list);
        mOfficeAdapter = new OfficeCursorAdapter(getActivity(), null);
        mAddButton = (FloatingActionButton) getActivity().findViewById(R.id.fab);

        //Setup
        mOfficeList.setAdapter(mOfficeAdapter);

        //Eventos
        mOfficeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cursor currentItem = (Cursor) mOfficeAdapter.getItem(i);
                String currentOfficeId = currentItem.getString(
                        currentItem.getColumnIndex(OfficeEntry.Name));

                showDetailScreen(currentOfficeId);
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
        loadOffice();

        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Activity.RESULT_OK == resultCode) {
            switch (requestCode) {
                case AddEditOfficeActivity.REQUEST_ADD_OFFICE:
                    showSuccessfullSavedMessage();
                    loadOffice();
                    break;
                case REQUEST_UPDATE_DELETE_OFFICE:
                    loadOffice();
                    break;
            }
        }

    }

    private void loadOffice() {
        //Cargar datos
        new OfficeLoadTask().execute();
    }

    private void showSuccessfullSavedMessage() {
        Toast.makeText(getActivity(),
                "Sucursal guardada correctamente", Toast.LENGTH_SHORT).show();
    }

    private void showAddScreen() {
        Intent intent = new Intent(getActivity(), AddEditOfficeActivity.class);
        startActivityForResult(intent, AddEditOfficeActivity.REQUEST_ADD_OFFICE);
    }

    private void showDetailScreen(String OfficeId) {
        Intent intent = new Intent(getActivity(), OfficeDetailActivity.class);
        intent.putExtra(OfficeActivity.EXTRA_OFFICE_ID, OfficeId);
        startActivityForResult(intent, REQUEST_UPDATE_DELETE_OFFICE);
    }

    private class OfficeLoadTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return mDbHelper.getAllOffices();
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null && cursor.getCount() > 0) {
                mOfficeAdapter.swapCursor(cursor);
            } else {
                // Mostrar empty state
            }
        }
    }
}
