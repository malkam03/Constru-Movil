package com.construmovil.construmovil.category;



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
import static com.construmovil.construmovil.data.CategoryContract.CategoryEntry;

/**
 * Category Fragment.
 * Use the {@link CategoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategoryFragment extends Fragment {
    public static final int REQUEST_UPDATE_DELETE_CATEGORY = 2;

    private DbHelper mDbHelper;
    private ListView mCategoryList;
    private CategoryCursorAdapter mCategoryAdapter;
    private FloatingActionButton mAddButton;

    public CategoryFragment() {
        // Required empty public constructor
    }

    public static CategoryFragment newInstance() {
        return new CategoryFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_category, container, false);

        //Referencias UI
        mCategoryList = (ListView) root.findViewById(R.id.category_list);
        mCategoryAdapter = new CategoryCursorAdapter(getActivity(), null);
        mAddButton = (FloatingActionButton) getActivity().findViewById(R.id.fab);

        //Setup
        mCategoryList.setAdapter(mCategoryAdapter);

        //Eventos
        mCategoryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cursor currentItem = (Cursor) mCategoryAdapter.getItem(i);
                String currentCategoryId = currentItem.getString(
                        currentItem.getColumnIndex(CategoryEntry.ID));

                showDetailScreen(currentCategoryId);
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
        loadCategory();

        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Activity.RESULT_OK == resultCode) {
            switch (requestCode) {
                case AddEditCategoryActivity.REQUEST_ADD_CATEGORY:
                    showSuccessfullSavedMessage();
                    loadCategory();
                    break;
                case REQUEST_UPDATE_DELETE_CATEGORY:
                    loadCategory();
                    break;
            }
        }

    }

    private void loadCategory() {
        //Cargar datos
        new CategoryLoadTask().execute();
    }

    private void showSuccessfullSavedMessage() {
        Toast.makeText(getActivity(),
                "Categoria guardado correctamente", Toast.LENGTH_SHORT).show();
    }

    private void showAddScreen() {
        Intent intent = new Intent(getActivity(), AddEditCategoryActivity.class);
        startActivityForResult(intent, AddEditCategoryActivity.REQUEST_ADD_CATEGORY);
    }

    private void showDetailScreen(String categoryId) {
        Intent intent = new Intent(getActivity(), CategoryDetailActivity.class);
        intent.putExtra(CategoryActivity.EXTRA_CATEGORY_ID, categoryId);
        startActivityForResult(intent, REQUEST_UPDATE_DELETE_CATEGORY);
    }

    private class CategoryLoadTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return mDbHelper.getAllCategory();
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null && cursor.getCount() > 0) {
                mCategoryAdapter.swapCursor(cursor);
            } else {
                // Mostrar empty state
            }
        }
    }
}
