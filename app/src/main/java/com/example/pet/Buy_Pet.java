package com.example.pet;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;

import com.example.pet.Adapter.PetCursorAdapter;
import com.example.pet.data.PetContract;

public class Buy_Pet extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor> {

    private static final int PET_LOADER = 0;

    PetCursorAdapter CursorAdapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_p_e_t);
        recyclerView = findViewById(R.id.RecycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        CursorAdapter = new PetCursorAdapter(this);
        recyclerView.setAdapter(CursorAdapter);
        getLoaderManager().initLoader(PET_LOADER, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return  new AsyncTaskLoader<Cursor>(this) {
            Cursor TaskData = null;

            @Override
            protected void onStartLoading() {
                    if (TaskData != null) {
                        // Delivers any previously loaded data immediately
                        deliverResult(TaskData);
                    } else {
                        // Force a new load
                        forceLoad();
                    }
            }

            @Override
            public Cursor loadInBackground() {
                try {
                    return getContentResolver().query(PetContract.Pet.CONTENT_URI,
                            null,
                            null,
                            null,
                            null);

                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
            public void deliverResult(Cursor data) {
                TaskData = data;
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        CursorAdapter.swapCursor(data);    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        CursorAdapter.swapCursor(null);
    }
    @Override
    protected void onResume() {
        super.onResume();
getLoaderManager().restartLoader(PET_LOADER, null, this);
    }
}


