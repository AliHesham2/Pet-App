package com.example.pet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;
import com.example.pet.Adapter.OwnerPetCursorAdapter;
import com.example.pet.data.PetContract;

;

public class Edit_Pet_Page extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor>,OwnerPetCursorAdapter.ItemClickListener {
    private Intent intent;
    private String Email;
    private static final int Owner_PET_LOADER = 1;
    OwnerPetCursorAdapter CursorAdapter;
    RecyclerView recyclerView;
    private Cursor cursor;
    private String[] projection = {
            PetContract.Pet._ID,
            PetContract.Pet.COLUMN_Email,
            PetContract.Pet.COLUMN_PetName,
            PetContract.Pet.COLUMN_Age,
            PetContract.Pet.COLUMN_Weight,
            PetContract.Pet.COLUMN_Color,
            PetContract.Pet.COLUMN_PetType,
            PetContract.Pet.COLUMN_PetDetails,
            PetContract.Pet.COLUMN_Currency,
            PetContract.Pet.COLUMN_image,
            PetContract.Pet.COLUMN_Location,
            PetContract.Pet.COLUMN_Contact
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__pet);
        intent = getIntent();
        if(intent.getStringExtra("Demail") != null){
            Email = intent.getStringExtra("Demail");
        }
        recyclerView = findViewById(R.id.Recycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        CursorAdapter = new OwnerPetCursorAdapter(this,this,Email);
        recyclerView.setAdapter(CursorAdapter);
        getLoaderManager().initLoader(Owner_PET_LOADER, null, this);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
               cursor = getContentResolver().query(PetContract.Pet.CONTENT_URI,
                        projection,
                       PetContract.Pet.COLUMN_Email + "=?",
                       new String[]{Email},
                        null);
                  int position= viewHolder.getAdapterPosition();
                int idindex= cursor.getColumnIndex(PetContract.Pet._ID);
                cursor.moveToPosition(position);
                showDeleteConfirmationDialog(position,PetContract.Pet._ID + "=?",new String[]{cursor.getString(idindex)});
            }
        }).attachToRecyclerView(recyclerView);
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
                 String selection = PetContract.Pet.COLUMN_Email + "=?";
                 String [] selectionArgs ={Email};
                try {
                    return getContentResolver().query(PetContract.Pet.CONTENT_URI,
                            projection,
                            selection,
                            selectionArgs,
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
        CursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        CursorAdapter.swapCursor(null);
    }
    protected void onResume() {
        super.onResume();
        getLoaderManager().restartLoader(Owner_PET_LOADER, null, this);
    }


    @Override
    public void onItemClickListener(int itemId,String selection,String[] selectionArgs) {
        showDeleteConfirmationDialog(itemId,selection,selectionArgs);
    }

    private void delete_pet(int id,String selection,String[] selectionArgs) {
        int rowdeleted = getContentResolver().delete(PetContract.Pet.CONTENT_URI,selection,selectionArgs);
        if(rowdeleted != 0){
            Toast.makeText(this,"Deleted Successfully",Toast.LENGTH_SHORT).show();
            getLoaderManager().restartLoader(Owner_PET_LOADER, null, this);
        }else{Toast.makeText(this,"Failed to Delete item",Toast.LENGTH_SHORT).show();}

    }
    private void showDeleteConfirmationDialog(int position,String selection,String[] selectionArgs) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Delete this pet?");
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                delete_pet(position,selection,selectionArgs);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (dialog != null) {
                    dialog.dismiss();
                Refresh();
                }
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    private void Refresh() {
        getLoaderManager().restartLoader(Owner_PET_LOADER, null, this);
    }

}