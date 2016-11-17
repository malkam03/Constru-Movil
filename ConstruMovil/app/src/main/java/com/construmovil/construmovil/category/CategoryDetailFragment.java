package com.construmovil.construmovil.category;


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
import com.construmovil.construmovil.data.Category;
import com.construmovil.construmovil.data.DbHelper;
/**
 * Category Detail Fragment.
 * Use the {@link CategoryDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategoryDetailFragment extends Fragment {
    private static final String ARG_CATEGPRY_ID = "categoryId";

    private String mCategoryId;

    private CollapsingToolbarLayout mCollapsingView;
    private TextView mIdCategory;
    private TextView mNameCategory;
    private TextView mDescriptionCategory;


    private DbHelper mDbHelper;

    public CategoryDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param categoryId Parameter 1.
     * @return A new instance of fragment CategoryDetailFragment.
     */
    public static CategoryDetailFragment newInstance(String categoryId) {
        CategoryDetailFragment fragment = new CategoryDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CATEGPRY_ID, categoryId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCategoryId = getArguments().getString(ARG_CATEGPRY_ID);
        }
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View root = inflater.inflate(R.layout.fragment_category_detail, container, false);
        mCollapsingView = (CollapsingToolbarLayout) getActivity().findViewById(R.id.toolbar_layout);
        mIdCategory = (TextView) root.findViewById(R.id.tv_category_id);
        mNameCategory = (TextView) root.findViewById(R.id.tv_category_name);
        mDescriptionCategory = (TextView) root.findViewById(R.id.tv_category_description);

        mDbHelper = new DbHelper(getActivity());

        loadCategory();

        return root;
    }

    private void loadCategory() {
        new GetCategoryByIdTask().execute();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit:
                showEditScreen();
                break;
            case R.id.action_delete:
                new DeleteCategoryTask().execute();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CategoryFragment.REQUEST_UPDATE_DELETE_CATEGORY) {
            if (resultCode == Activity.RESULT_OK) {
                getActivity().setResult(Activity.RESULT_OK);
                getActivity().finish();
            }
        }
    }

    private void showCategory(Category category) {
        mCollapsingView.setTitle(category.getCategoryName());
        mIdCategory.setText(category.getId());
        mNameCategory.setText(category.getCategoryName());
        mDescriptionCategory.setText(category.getDescription());
    }

    private void showEditScreen() {
        Intent intent = new Intent(getActivity(), AddEditCategoryActivity.class);
        intent.putExtra(CategoryActivity.EXTRA_CATEGORY_ID, mCategoryId);
        startActivityForResult(intent, CategoryFragment.REQUEST_UPDATE_DELETE_CATEGORY);
    }

    private void showCategoryScreen(boolean requery) {
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
                "Error al eliminar Categoría", Toast.LENGTH_SHORT).show();
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
            }
        }

    }

    private class DeleteCategoryTask extends AsyncTask<Void, Void, Integer> {

        @Override
        protected Integer doInBackground(Void... voids) {
            return mDbHelper.deleteCategory(mCategoryId);
        }

        @Override
        protected void onPostExecute(Integer integer) {
            showCategoryScreen(integer > 0);
        }

    }
}
