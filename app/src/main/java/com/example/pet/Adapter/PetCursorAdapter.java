package com.example.pet.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.pet.R;
import com.example.pet.data.PetContract;

public class PetCursorAdapter extends RecyclerView.Adapter<PetCursorAdapter.TaskViewHolder> {
    private byte img[];
    private Cursor Cursor;
    private Context mContext;
    public PetCursorAdapter(Context mContext) {
        this.mContext = mContext;
    }
    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.pet_item, parent, false);

        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PetCursorAdapter.TaskViewHolder holder, int position) {
        int nameindex,typeindex,petindex,colorindex,ageindex,weightindex,imgindex,currencyindex,location,contact;
        nameindex= Cursor.getColumnIndex(PetContract.Pet.COLUMN_PetName);
        ageindex=Cursor.getColumnIndex(PetContract.Pet.COLUMN_Age);
        weightindex=Cursor.getColumnIndex(PetContract.Pet.COLUMN_Weight);
        colorindex=Cursor.getColumnIndex(PetContract.Pet.COLUMN_Color);
        petindex=Cursor.getColumnIndex(PetContract.Pet.COLUMN_PetType);
        typeindex=Cursor.getColumnIndex(PetContract.Pet.COLUMN_PetDetails);
        currencyindex=Cursor.getColumnIndex(PetContract.Pet.COLUMN_Currency);
        imgindex=Cursor.getColumnIndex(PetContract.Pet.COLUMN_image);
        location=Cursor.getColumnIndex(PetContract.Pet.COLUMN_Location);
        contact=Cursor.getColumnIndex(PetContract.Pet.COLUMN_Contact);
        Cursor.moveToPosition(position);
        holder.Name.setText("Name: "+Cursor.getString(nameindex));
        holder.Age.setText("Age: "+Cursor.getInt(ageindex));
        holder.Weight.setText("Weight: "+Cursor.getInt(weightindex));
        holder.Color.setText("Color: "+Cursor.getString(colorindex));
        holder.Pet.setText("Pet: "+Cursor.getString(petindex));
        String Type = Cursor.getString(typeindex);
        if(Type.isEmpty() || Type == null){
            holder.Type.setText("Type: "+ "None");
        }else{holder.Type.setText("Type: "+Type);}
        holder.curreny.setText("Currency: "+Cursor.getString(currencyindex));
        img = Cursor.getBlob(imgindex);
        Bitmap b1= BitmapFactory.decodeByteArray(img, 0, img.length);
        holder.imageView.setImageBitmap(b1);
        holder.Location.setText("Address: "+Cursor.getString(location));
        holder.Contact.setText("Contact: "+Cursor.getString(contact));

    }

    @Override
    public int getItemCount() {
        if (Cursor == null) {
            return 0;
        }
        return Cursor.getCount();
    }
    public Cursor swapCursor(Cursor c) {
        if (Cursor == c) {
            return null;
        }
        Cursor temp = Cursor;
        this.Cursor = c;

        if (c != null) {
            this.notifyDataSetChanged();
        }
        return temp;
    }
    class TaskViewHolder extends RecyclerView.ViewHolder {
private TextView Name,Age,Weight,Color,Pet,Type,curreny,Location,Contact;
private ImageView imageView;
        public TaskViewHolder(@NonNull View view) {
            super(view);
            Name=view.findViewById(R.id.Name);
            Age=view.findViewById(R.id.Age);
            Weight=view.findViewById(R.id.Weight);
            Color=view.findViewById(R.id.Color);
            Pet=view.findViewById(R.id.Pet);
            Type=view.findViewById(R.id.Type);
            curreny=view.findViewById(R.id.Currency);
            imageView=view.findViewById(R.id.PetPhoto);
            Location=view.findViewById(R.id.Location);
            Contact=view.findViewById(R.id.contact);
        }
    }
}
