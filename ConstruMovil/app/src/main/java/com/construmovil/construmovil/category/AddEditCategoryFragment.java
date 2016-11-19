package com.construmovil.construmovil.category;


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
import com.construmovil.construmovil.data.Category;
import com.construmovil.construmovil.data.DbHelper;

/**
 * Add / Edit Category Fragment.
 * Use the {@link AddEditCategoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddEditCategoryFragment extends Fragment {
    private static final String ARG_CATEGORY_ID = "arg_category_id";

    private String mCategoryId;

    private DbHelper mDbHelper;

    private FloatingActionButton mSaveButton;
    private TextInputEditText mIdCategoryField;
    private TextInputEditText mNameCategoryField;
    private TextInputEditText mDescriptionCategoryField;

    private TextInputLayout mIdCategoryLabel;
    private TextInputLayout mNameCategoryLabel;
    private TextInputLayout mDescriptionCategoryLabel;


    public AddEditCategoryFragment() {
        // Required empty public constructor
    }


    public static AddEditCategoryFragment newInstance(String categoryId) {
        AddEditCategoryFragment fragment = new AddEditCategoryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CATEGORY_ID, categoryId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCategoryId = getArguments().getString(ARG_CATEGORY_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_add_edit_category, container, false);
        mSaveButton = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        mIdCategoryField = (TextInputEditText) root.findViewById(R.id.et_category_id);
        mNameCategoryField = (TextInputEditText) root.findViewById(R.id.et_category_name);
        mDescriptionCategoryField = (TextInputEditText) root.findViewById(R.id.et_category_category_description);

        mIdCategoryLabel = (TextInputLayout) root.findViewById(R.id.til_category_id);
        mNameCategoryLabel = (TextInputLayout) root.findViewById(R.id.til_category_name);
        mDescriptionCategoryLabel = (TextInputLayout) root.findViewById(R.id.til_category_description);

        //Eventos
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addEditCategory();
            }
        });

        mDbHelper = new DbHelper(getActivity());

        //Carga de datos
        if (mCategoryId != null) {
            loadCategory();
        }
        return root;
    }

    private void loadCategory() {
        new GetCategoryByIdTask().execute();
    }

    private void addEditCategory() {
        boolean error = false;

        String idCategory = mIdCategoryField.getText().toString();
        String nameCategory = mNameCategoryField.getText().toString();
        String descriptionCategory = mDescriptionCategoryField.getText().toString();

        if(TextUtils.isEmpty(idCategory)) {
            mIdCategoryLabel.setError(getString(R.string.field_error));
            error = true;
        }
        if(TextUtils.isEmpty(nameCategory)) {
            mNameCategoryLabel.setError(getString(R.string.field_error));
            error = true;
        }
        if(TextUtils.isEmpty(descriptionCategory)) {
            mDescriptionCategoryLabel.setError(getString(R.string.field_error));
            error = true;
        }
        if(error) {
            return;
        }

        Category category = new Category(idCategory, nameCategory, descriptionCategory);
        new AddEditCategoryTask().execute(category);

    }

    private void showCategoryScreen(Boolean requery) {
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

    private void showCategory(Category category) {
        mIdCategoryField.setText(category.getId());
        mNameCategoryField.setText(category.getCategoryName());
        mDescriptionCategoryField.setText(category.getDescription());
    }

    private void showLoadError() {
        Toast.makeText(getActivity(),
                "Error al editar categoria", Toast.LENGTH_SHORT).show();
    }

    private class GetCategoryByIdTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return mDbHelper.getCategory(mCategoryId);
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null && cursor.moveToLast()) {
                showCategory(new Category(cursor));
            } else {
                showLoadError();
                getActivity().setResult(Activity.RESULT_CANCELED);
                getActivity().finish();
            }
        }

    }

    private class AddEditCategoryTask extends AsyncTask<Category, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Category... categories) {
            if (mCategoryId != null) {
                return mDbHelper.updateCategory(categories[0], mCategoryId) > 0;
            } else {
                return mDbHelper.saveCategory(categories[0]) > 0;
            }
        }
        @Override
        protected void onPostExecute(Boolean result) {
            showCategoryScreen(result);
        }
    }

}
