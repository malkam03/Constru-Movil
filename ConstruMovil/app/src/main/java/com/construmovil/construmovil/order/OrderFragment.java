package com.construmovil.construmovil.order;


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
import com.construmovil.construmovil.data.OrderContract.OrderEntry;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OrderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrderFragment extends Fragment {

    public static final int REQUEST_UPDATE_DELETE_ORDER = 2;

    private DbHelper mDbHelper;
    private ListView mOrderList;
    private OrderCursorAdapter mOrderAdapter;
    private FloatingActionButton mAddButton;

    public OrderFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of a OrderFragment
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment OrderFragment.
     */
    public static OrderFragment newInstance() {
        return new OrderFragment();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_order, container, false);

        //Referencias UI
        mOrderList = (ListView) root.findViewById(R.id.order_list);
        mOrderAdapter = new OrderCursorAdapter(getActivity(), null);
        mAddButton = (FloatingActionButton) getActivity().findViewById(R.id.fab);

        //Setup
        mOrderList.setAdapter(mOrderAdapter);

        //Eventos
        mOrderList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cursor currentItem = (Cursor) mOrderAdapter.getItem(i);
                String currentOrderId = currentItem.getString(
                        currentItem.getColumnIndex(OrderEntry.ID));

                showDetailScreen(currentOrderId);
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
        loadOrder();

        return root;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Activity.RESULT_OK == resultCode) {
            switch (requestCode) {
                case AddEditOrderActivity.REQUEST_ADD_ORDER:
                    showSuccessfullSavedMessage();
                    loadOrder();
                    break;
                case REQUEST_UPDATE_DELETE_ORDER:
                    loadOrder();
                    break;
            }
        }

    }

    private void loadOrder() {
        //Cargar datos
        new OrderLoadTask().execute();
    }

    private void showSuccessfullSavedMessage() {
        Toast.makeText(getActivity(),
                "Orden guardada correctamente", Toast.LENGTH_SHORT).show();
    }

    private void showAddScreen() {
        Intent intent = new Intent(getActivity(), AddEditOrderActivity.class);
        startActivityForResult(intent, AddEditOrderActivity.REQUEST_ADD_ORDER);
    }

    private void showDetailScreen(String orderId) {
        Intent intent = new Intent(getActivity(), OrderDetailActivity.class);
        intent.putExtra(OrderActivity.EXTRA_ORDER_ID, orderId);
        startActivityForResult(intent, REQUEST_UPDATE_DELETE_ORDER);
    }

    private class OrderLoadTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return mDbHelper.getAllPersons();
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null && cursor.getCount() > 0) {
                mOrderAdapter.swapCursor(cursor);
            } else {
                // Mostrar empty state
            }
        }
    }
}
