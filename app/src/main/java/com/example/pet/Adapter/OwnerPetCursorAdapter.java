package com.example.pet.Adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.pet.R;
import com.example.pet.Sell_Pet;
import com.example.pet.data.PetContract;

public class OwnerPetCursorAdapter extends RecyclerView.Adapter<OwnerPetCursorAdapter.TaskViewHolder> {
    final private ItemClickListener ItemClickListener;
    private byte img[];
    private int nameindex,typeindex,petindex,colorindex,ageindex,weightindex,emailindex,description,idindex,imgindex,currencyindex,location,contact;
    private Cursor Cursor;
    private Context mContext;
    private ImageButton edit;
    private ImageButton remove;
    private Bitmap b1;
    private String Email;
        public OwnerPetCursorAdapter(Context mContext,ItemClickListener listener,String Email) {
        this.mContext = mContext;
        this.ItemClickListener=listener;
        this.Email= Email;
    }
    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.owner_pet_item, parent, false);

        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
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
        b1= BitmapFactory.decodeByteArray(img, 0, img.length);
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
    public  interface ItemClickListener {
         void onItemClickListener(int itemId,String selection,String[] selectionArgs);
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
            edit=view.findViewById(R.id.edit);
            curreny=view.findViewById(R.id.Currency);
            imageView=view.findViewById(R.id.PetPhoto);
            Location=view.findViewById(R.id.Location);
            Contact=view.findViewById(R.id.contact);
            remove=view.findViewById(R.id.remove);
            remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int id = getAdapterPosition();
                    idindex= Cursor.getColumnIndex(PetContract.Pet._ID);
                    Cursor.moveToPosition(id);
                    String selection = PetContract.Pet._ID +"=?";
                    String [] selectionArgs ={
                            Cursor.getString(idindex),
                    };
                    ItemClickListener.onItemClickListener(id,selection,selectionArgs);
                }
            });
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int id = getAdapterPosition();
                    idindex= Cursor.getColumnIndex(PetContract.Pet._ID);
                    emailindex= Cursor.getColumnIndex(PetContract.Pet.COLUMN_Email);
                    nameindex= Cursor.getColumnIndex(PetContract.Pet.COLUMN_PetName);
                    ageindex=Cursor.getColumnIndex(PetContract.Pet.COLUMN_Age);
                    weightindex=Cursor.getColumnIndex(PetContract.Pet.COLUMN_Weight);
                    colorindex=Cursor.getColumnIndex(PetContract.Pet.COLUMN_Color);
                    petindex=Cursor.getColumnIndex(PetContract.Pet.COLUMN_PetType);
                    description=Cursor.getColumnIndex(PetContract.Pet.COLUMN_PetDetails);
                    currencyindex=Cursor.getColumnIndex(PetContract.Pet.COLUMN_Currency);
                    imgindex=Cursor.getColumnIndex(PetContract.Pet.COLUMN_image);
                    location=Cursor.getColumnIndex(PetContract.Pet.COLUMN_Location);
                    contact=Cursor.getColumnIndex(PetContract.Pet.COLUMN_Contact);
                    Cursor.moveToPosition(id);
                    Intent intent = new Intent(mContext,Sell_Pet.class);
                    intent.putExtra("id", Cursor.getString(idindex));
                    intent.putExtra("email", Cursor.getString(emailindex));
                    intent.putExtra("petname",Cursor.getString(nameindex));
                    intent.putExtra("petage",Cursor.getString(ageindex));
                    intent.putExtra("petweight",Cursor.getString(weightindex));
                    intent.putExtra("petcolor",Cursor.getString(colorindex));
                    intent.putExtra("pettype" ,Cursor.getString(petindex));
                    intent.putExtra("des",Cursor.getString(description));
                    intent.putExtra("cur",Cursor.getString(currencyindex));
                    intent.putExtra("contact",Cursor.getString(contact));
                    intent.putExtra("address",Cursor.getString(location));
                    intent.putExtra("pos_id",id);
                    intent.putExtra("Eemail",Email);
                    mContext.startActivity(intent);
                }
            });
        }
    }
}
