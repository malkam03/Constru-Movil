package com.construmovil.construmovil.supplier;


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
import com.construmovil.construmovil.data.Supplier;
import com.construmovil.construmovil.data.DbHelper;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SupplierDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SupplierDetailFragment extends Fragment {
    private static final String ARG_SUPPLIER_ID = "suppliertId";

    private String mSupplierId;

    private CollapsingToolbarLayout mCollapsingView;
    private TextView mBusinessName;
    private TextView mCedJur;
    private TextView mCedRep;

    private DbHelper mDbHelper;

    public SupplierDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param supplierId Parameter 1.
     * @return A new instance of fragment SupplierDetailFragment.
     */
    public static SupplierDetailFragment newInstance(String supplierId) {
        SupplierDetailFragment fragment = new SupplierDetailFragment();
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
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View root = inflater.inflate(R.layout.fragment_supplier_detail, container, false);
        mCollapsingView = (CollapsingToolbarLayout) getActivity().findViewById(R.id.toolbar_layout);
        mBusinessName = (TextView) root.findViewById(R.id.tv_business_name);
        mCedJur = (TextView) root.findViewById(R.id.tv_ced_jur);
        mCedRep = (TextView) root.findViewById(R.id.tv_ced_rep);
        mDbHelper = new DbHelper(getActivity());

        loadSupplier();

        return root;
    }

    private void loadSupplier() {
        new GetSupplierByIdTask().execute();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit:
                showEditScreen();
                break;
            case R.id.action_delete:
                new DeleteSupplierTask().execute();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SupplierFragment.REQUEST_UPDATE_DELETE_SUPPLIER) {
            if (resultCode == Activity.RESULT_OK) {
                getActivity().setResult(Activity.RESULT_OK);
                getActivity().finish();
            }
        }
    }

    private void showSupplier(Supplier supplier) {
        mCollapsingView.setTitle(supplier.getBusinessName());
        mBusinessName.setText(supplier.getId());
        mCedJur.setText(supplier.getId());
        mCedRep.setText(supplier.getIdPerson());

    }

    private void showEditScreen() {
        Intent intent = new Intent(getActivity(), AddEditSupplierActivity.class);
        intent.putExtra(SupplierActivity.EXTRA_SUPPLIER_ID, mSupplierId);
        startActivityForResult(intent, SupplierFragment.REQUEST_UPDATE_DELETE_SUPPLIER);
    }

    private void showSupplierScreen(boolean requery) {
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
                "Error al eliminar Proveedor", Toast.LENGTH_SHORT).show();
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
            }
        }

    }

    private class DeleteSupplierTask extends AsyncTask<Void, Void, Integer> {

        @Override
        protected Integer doInBackground(Void... voids) {
            return mDbHelper.deleteSupplier(mSupplierId);
        }

        @Override
        protected void onPostExecute(Integer integer) {
            showSupplierScreen(integer > 0);
        }

    }


}
