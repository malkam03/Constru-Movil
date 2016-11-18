package com.construmovil.construmovil.order;



import android.app.Activity;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.construmovil.construmovil.R;
import com.construmovil.construmovil.data.DbHelper;
import com.construmovil.construmovil.data.Order;
import com.construmovil.construmovil.data.OrderContract;

/**
 * Fragment for Add/Edit View.
 * Use the {@link AddEditOrderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddEditOrderFragment extends Fragment {
    private static final String ARG_ORDER_ID = "arg_order_id";

    private String mOrderId;

    private DbHelper mDbHelper;

    private FloatingActionButton mSaveButton;
    private TextInputEditText mIdOrderField;
    private TextInputEditText mUserOrderField;
    private TextInputEditText mBranchOfficeOrderField;
    private TextInputEditText mStateOrderField;
    private TextInputEditText mPhoneNumberOrderField;
    private TextInputEditText mEtaOrderField;
    private TextInputEditText mTimeOrderField;

    private TextInputLayout mIdOrderLabel;
    private TextInputLayout mUserOrderLabel;
    private TextInputLayout mBranchOfficeOrderLabel;
    private TextInputLayout mStateOrderLabel;
    private TextInputLayout mPhoneNumberOrderLabel;
    private TextInputLayout mEtaOrderLabel;
    private TextInputLayout mTimeOrderLabel;

    public AddEditOrderFragment() {
        // Required empty public constructor
    }


    public static AddEditOrderFragment newInstance(String orderId) {
        AddEditOrderFragment fragment = new AddEditOrderFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_add_edit_order, container, false);
        mSaveButton = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        mUserOrderField = (TextInputEditText) root.findViewById(R.id.et_user_order);
        mBranchOfficeOrderField = (TextInputEditText) root.findViewById(R.id.et_branch_office_order);
        mPhoneNumberOrderField = (TextInputEditText) root.findViewById(R.id.et_phone_number_order);
        mEtaOrderField = (TextInputEditText) root.findViewById(R.id.et_eta_order);
        mTimeOrderField = (TextInputEditText) root.findViewById(R.id.et_time_order);

        mUserOrderLabel = (TextInputLayout) root.findViewById(R.id.til_user_order);
        mBranchOfficeOrderLabel = (TextInputLayout) root.findViewById(R.id.til_branch_office_order);
        mPhoneNumberOrderLabel = (TextInputLayout) root.findViewById(R.id.til_phone_number_order);
        mEtaOrderLabel = (TextInputLayout) root.findViewById(R.id.til_eta_order);
        mTimeOrderLabel = (TextInputLayout) root.findViewById(R.id.til_time_order);

        //Eventos
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addEditOrder();
            }
        });

        mDbHelper = new DbHelper(getActivity());

        //Carga de datos
        if (mOrderId != null) {
            loadOrder();
        }
        return root;
    }

    private void loadOrder() {
        new GetOrderByIdTask().execute();
    }

    private void addEditOrder() {
        boolean error = false;

        String idOrder = OrderContract.OrderEntry._ID + mUserOrderField.getText().toString();
        String userOrder = mUserOrderField.getText().toString();
        String branchOfficeOrder = mBranchOfficeOrderField.getText().toString();
        String stateOrder = mStateOrderField.getText().toString();
        String phoneNumberOrder = mPhoneNumberOrderField.getText().toString();
        String etaOrder = mEtaOrderField.getText().toString();
        String timeOrder = mTimeOrderField.getText().toString();


        if(TextUtils.isEmpty(idOrder)) {
            mIdOrderLabel.setError(getString(R.string.field_error));
            error = true;
        }
        if(TextUtils.isEmpty(userOrder)) {
            mUserOrderLabel.setError(getString(R.string.field_error));
            error = true;
        }
        if(TextUtils.isEmpty(branchOfficeOrder)) {
            mBranchOfficeOrderLabel.setError(getString(R.string.field_error));
            error = true;
        }
        if(TextUtils.isEmpty(stateOrder)) {
            mStateOrderLabel.setError(getString(R.string.field_error));
            error = true;
        }
        if(TextUtils.isEmpty(phoneNumberOrder)) {
            mPhoneNumberOrderLabel.setError(getString(R.string.field_error));
            error = true;
        }
        if(TextUtils.isEmpty(etaOrder)) {
            mEtaOrderLabel.setError(getString(R.string.field_error));
            error = true;
        }
        if(TextUtils.isEmpty(timeOrder)) {
            mTimeOrderLabel.setError(getString(R.string.field_error));
            error = true;
        }
        if(error) {
            return;
        }
        Order order = new Order(idOrder,userOrder,branchOfficeOrder,stateOrder,phoneNumberOrder,etaOrder,timeOrder);
        new AddEditOrderTask().execute(order);

    }

    private void showOrderScreen(Boolean requery) {
        if (!requery) {
            showAddEditError();
            getActivity().setResult(Activity.RESULT_CANCELED);
        } else {
            getActivity().setResult(Activity.RESULT_OK);
        }
        getActivity().finish();
    }

    private void showAddEditError() {
        Toast.makeText(getActivity(),
                "Error al agregar nueva información", Toast.LENGTH_SHORT).show();
    }

    private void showOrder(Order order) {
        mIdOrderField.setText(order.getId());
        mUserOrderField.setText(order.getUserName());
        mBranchOfficeOrderField.setText(order.getOfficeName());
        mStateOrderField.setText(order.getState());
        mPhoneNumberOrderField.setText(order.getPhone());
        mEtaOrderField.setText(order.getEta());
        mTimeOrderField.setText(order.getOrderTime());
    }

    private void showLoadError() {
        Toast.makeText(getActivity(),
                "Error al editar Orden", Toast.LENGTH_SHORT).show();
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
                getActivity().setResult(Activity.RESULT_CANCELED);
                getActivity().finish();
            }
        }

    }

    private class AddEditOrderTask extends AsyncTask<Order, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Order... order) {
            if (mOrderId != null) {
                return mDbHelper.updateOrder(order[0], mOrderId) > 0;
            } else {
                return mDbHelper.saveOrder(order[0]) > 0;
            }
        }
        @Override
        protected void onPostExecute(Boolean result) {
            showOrderScreen(result);
        }
    }

    private class AddEditUserTask extends AsyncTask<Order, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Order... orders) {
            if (mOrderId != null) {
                return mDbHelper.updateOrder(orders[0], mOrderId) > 0;
            } else {
                return mDbHelper.saveOrder(orders[0]) > 0;
            }
        }
        @Override
        protected void onPostExecute(Boolean result) {
            showOrderScreen(result);
        }
    }

}
