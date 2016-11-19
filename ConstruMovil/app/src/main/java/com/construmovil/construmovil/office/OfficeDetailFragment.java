package com.construmovil.construmovil.office;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.construmovil.construmovil.R;
import com.construmovil.construmovil.data.Office;
import com.construmovil.construmovil.data.DbHelper;

/**
 * Office Detail Fragment.
 * Use the {@link OfficeDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OfficeDetailFragment extends Fragment {
    private static final String ARG_OFFICE_ID = "officeId";

    private String mOfficeId;

    private CollapsingToolbarLayout mCollapsingView;
    private TextView mNameoffice;
    private TextView mAddressoffice;

    private DbHelper mDbHelper;

    public OfficeDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param officeId Parameter 1.
     * @return A new instance of fragment OfficeDetailFragment.
     */
    public static OfficeDetailFragment newInstance(String officeId) {
        OfficeDetailFragment fragment = new OfficeDetailFragment();
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
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View root = inflater.inflate(R.layout.fragment_office_detail, container, false);
        mCollapsingView = (CollapsingToolbarLayout) getActivity().findViewById(R.id.toolbar_layout);
        mNameoffice = (TextView) root.findViewById(R.id.tv_office_name);
        mAddressoffice = (TextView) root.findViewById(R.id.tv_office_address);
        mDbHelper = new DbHelper(getActivity());

        loadOffice();

        return root;
    }

    private void loadOffice() {
        new GetOfficeByIdTask().execute();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit:
                showEditScreen();
                break;
            case R.id.action_delete:
                new DeleteOfficeTask().execute();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == OfficeFragment.REQUEST_UPDATE_DELETE_OFFICE) {
            if (resultCode == Activity.RESULT_OK) {
                getActivity().setResult(Activity.RESULT_OK);
                getActivity().finish();
            }
        }
    }

    private void showOffice(Office office) {
        mCollapsingView.setTitle(office.getName());
        mNameoffice.setText(office.getName());
        mAddressoffice.setText(office.getAddress());
    }

    private void showEditScreen() {
        Intent intent = new Intent(getActivity(), AddEditOfficeActivity.class);
        intent.putExtra(OfficeActivity.EXTRA_OFFICE_ID, mOfficeId);
        startActivityForResult(intent, OfficeFragment.REQUEST_UPDATE_DELETE_OFFICE);
    }

    private void showOfficeScreen(boolean requery) {
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
                "Error al eliminar Sucursal", Toast.LENGTH_SHORT).show();
    }

    private class GetOfficeByIdTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return mDbHelper.getOffice(mOfficeId);
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null && cursor.moveToLast()) {
                showOffice(new Office(cursor));
            } else {
                showLoadError();
            }
        }

    }

    private class DeleteOfficeTask extends AsyncTask<Void, Void, Integer> {

        @Override
        protected Integer doInBackground(Void... voids) {
            return mDbHelper.deleteOffice(mOfficeId);
        }

        @Override
        protected void onPostExecute(Integer integer) {
            showOfficeScreen(integer > 0);
        }

    }
}
