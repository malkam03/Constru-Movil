package com.construmovil.construmovil.order;


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
import com.construmovil.construmovil.data.Order;
import com.construmovil.construmovil.data.DbHelper;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OrderDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrderDetailFragment extends Fragment {
    private static final String ARG_ORDER_ID = "orderId";

    private String mOrderId;

    private CollapsingToolbarLayout mCollapsingView;
    private TextView mIdOrder;
    private TextView mUserOrder;
    private TextView mBranchOfficeOrder;
    private TextView mStateOrder;
    private TextView mPhoneNumberOrder;
    private TextView mETAOrder;
    private TextView mTimeOrder;

    private DbHelper mDbHelper;

    public OrderDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param orderId Parameter 1.
     * @return A new instance of fragment OrderDetailFragment.
     */
    public static OrderDetailFragment newInstance(String orderId) {
        OrderDetailFragment fragment = new OrderDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ORDER_ID, orderId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mOrderId = getArguments().getString(ARG_ORDER_ID);
        }
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View root = inflater.inflate(R.layout.fragment_order_detail, container, false);
        mCollapsingView = (CollapsingToolbarLayout) getActivity().findViewById(R.id.toolbar_layout);
        mIdOrder = (TextView) root.findViewById(R.id.tv_id_order);
        mUserOrder = (TextView) root.findViewById(R.id.tv_user_order);
        mBranchOfficeOrder = (TextView) root.findViewById(R.id.tv_branch_office_order);
        mStateOrder = (TextView) root.findViewById(R.id.tv_state_order);
        mPhoneNumberOrder = (TextView) root.findViewById(R.id.tv_phone_number_order);
        mETAOrder = (TextView) root.findViewById(R.id.tv_eta_order);
        mTimeOrder = (TextView) root.findViewById(R.id.tv_time_order);
        mDbHelper = new DbHelper(getActivity());

        loadOrder();

        return root;
    }

    private void loadOrder() {
        new GetOrderByIdTask().execute();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit:
                showEditScreen();
                break;
            case R.id.action_delete:
                new DeleteOrderTask().execute();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == OrderFragment.REQUEST_UPDATE_DELETE_ORDER) {
            if (resultCode == Activity.RESULT_OK) {
                getActivity().setResult(Activity.RESULT_OK);
                getActivity().finish();
            }
        }
    }

    private void showOrder(Order order) {
        mCollapsingView.setTitle(order.getUserName() + "ID Orden #" + order.getId());
        mIdOrder.setText(order.getId());
        mUserOrder.setText(order.getUserName());
        mBranchOfficeOrder.setText(order.getOfficeName());
        mStateOrder.setText(order.getState());
        mPhoneNumberOrder.setText(order.getPhone());
        mETAOrder.setText(order.getEta());
        mTimeOrder.setText(order.getOrderTime());
    }

    private void showEditScreen() {
        Intent intent = new Intent(getActivity(), AddEditOrderActivity.class);
        intent.putExtra(OrderActivity.EXTRA_ORDER_ID, mOrderId);
        startActivityForResult(intent, OrderFragment.REQUEST_UPDATE_DELETE_ORDER);
    }

    private void showOrderScreen(boolean requery) {
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
                "Error al eliminar Orden", Toast.LENGTH_SHORT).show();
    }

    private class GetOrderByIdTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return mDbHelper.getOrderbyID(mOrderId);
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null && cursor.moveToLast()) {
                showOrder(new Order(cursor));
            } else {
                showLoadError();
            }
        }

    }

    private class DeleteOrderTask extends AsyncTask<Void, Void, Integer> {

        @Override
        protected Integer doInBackground(Void... voids) {
            return mDbHelper.deleteOrder(mOrderId);
        }

        @Override
        protected void onPostExecute(Integer integer) {
            showOrderScreen(integer > 0);
        }

    }


}
