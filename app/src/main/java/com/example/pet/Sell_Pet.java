package com.example.pet;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.example.pet.data.PetContract;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.abhinay.input.CurrencyEditText;

public class Sell_Pet extends AppCompatActivity implements AdapterView.OnItemSelectedListener  {
private static final int EXISTING_PET_LOADER = 0;
private EditText PetName,PetAge,PetColor,PetWeight,PetContact,Address;
private  Bitmap bitmap = null;
private byte img[];
private Cursor cursor;
private ImageButton imageButton;
private ImageView imageView;
private TextView textView;
private CurrencyEditText etInput;
private TextView error,error2,error3,error4,error5,error6,error7,TopText,error8,error9,error10,error11;
private Button button;
private String petdetails,petname,age,weight,petcolor,pettype,currency,address,contact,Email;
private Spinner spinner2;
private int petage,petweight,petcurrency;
private Intent intent;
private  ArrayAdapter<CharSequence> adapter2,adapter;
private boolean mPetHasChanged = false;
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
        setContentView(R.layout.activity_sell__pet);
        intent = getIntent();
        if(intent.getStringExtra("Demail") != null){
            Email= intent.getStringExtra("Demail");
        }
        if(intent.getStringExtra("Eemail") != null){
            Email= intent.getStringExtra("Eemail");
        }
        button = findViewById(R.id.btnLogin);
        TopText=findViewById(R.id.topText);
        Spinner spinner = (Spinner) findViewById(R.id.PetType);
        spinner.setOnItemSelectedListener(this);
        adapter = ArrayAdapter.createFromResource(this,
                R.array.Pets, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner2 = (Spinner) findViewById(R.id.PetDetails);
        spinner.setOnItemSelectedListener(this);
        PetName = findViewById(R.id.PetName);
        PetAge =findViewById(R.id.PetAge);
        PetColor=findViewById(R.id.PetColor);
        PetWeight=findViewById(R.id.PetWeight);
        Address=findViewById(R.id.Petlocation);
        PetContact =findViewById(R.id.Contact);
        error=findViewById(R.id.PError);
        error2=findViewById(R.id.PError2);
        error3=findViewById(R.id.PError3);
        error4=findViewById(R.id.PError4);
        error5=findViewById(R.id.PError5);
       error6 = findViewById(R.id.PError6);
       error7 = findViewById(R.id.PError7);
        error8 = findViewById(R.id.PError8);
        error9 = findViewById(R.id.PError9);
        error10 = findViewById(R.id.PError10);
        error11 = findViewById(R.id.PError11);
        imageButton =findViewById(R.id.imageButton);
        imageView=findViewById(R.id.imageView);
        textView=findViewById(R.id.textView);
        etInput = (CurrencyEditText) findViewById(R.id.etInput);
        etInput.setDelimiter(false);
        etInput.setSpacing(false);
        etInput.setDecimals(false);
        etInput.setCurrency("$"+" ");
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage(v);
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage(v);
            }
        });
        if(intent.getStringExtra("petname") != null){
            cursor = getContentResolver().query(PetContract.Pet.CONTENT_URI,
                    projection,
                    PetContract.Pet.COLUMN_Email + "=?",
                    new String[]{Email},
                    null);
            int id= intent.getIntExtra("pos_id",0);
            button.setText("Edit Pet");
           TopText.setText("Edit Pet");
            PetName.setText(intent.getStringExtra("petname"));
            PetAge.setText(intent.getStringExtra("petage"));
           PetWeight.setText(intent.getStringExtra("petweight"));
           PetColor.setText(intent.getStringExtra("petcolor"));
            etInput.setText(intent.getStringExtra("cur"));
            PetContact.setText(intent.getStringExtra("contact"));
            Address.setText(intent.getStringExtra("address"));
            int index= cursor.getColumnIndex(PetContract.Pet.COLUMN_image);
            cursor.moveToPosition(id);
            img = cursor.getBlob(index);
            bitmap= BitmapFactory.decodeByteArray(img, 0, img.length);
            if( bitmap!= null){
                imageButton.setVisibility(View.GONE);
                imageView.setBackground(null);
                textView.setVisibility(View.GONE);}
            imageView.setImageBitmap(bitmap);
          int pos =  adapter.getPosition(intent.getStringExtra("pettype"));
         spinner.setSelection(pos);
        }
PetContact.setOnTouchListener(mTouchListener);
Address.setOnTouchListener(mTouchListener);
etInput.setOnTouchListener(mTouchListener);
PetName.setOnTouchListener(mTouchListener);
PetAge.setOnTouchListener(mTouchListener);
PetWeight.setOnTouchListener(mTouchListener);
PetColor.setOnTouchListener(mTouchListener);
spinner.setOnTouchListener(mTouchListener);

    }
    public void Add_Pet(View view) {
        if(checkvalidation()){
            if(intent.getStringExtra("petname") == null) {
                // add pet
                if (AddPet()) {
                    error.setText("");
                    error2.setText("");
                    error3.setText("");
                    error4.setText("");
                    error5.setText("");
                    error6.setText("");
                    error7.setText("");
                    error8.setText("");
                    error9.setText("");
                    error10.setText("");
                    error11.setText("");
                    Toast.makeText(this,"Pet Added Successfully",Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(this,"Something Went Wrong pet cant be added",Toast.LENGTH_SHORT).show();
                }
            }else{
                // update pet
                if(UpdatePet()){
                    error.setText("");
                    error2.setText("");
                    error3.setText("");
                    error4.setText("");
                    error5.setText("");
                    error6.setText("");
                    error7.setText("");
                    error8.setText("");
                    error9.setText("");
                    error10.setText("");
                    error11.setText("");
                    Toast.makeText(this,"Pet Updated Successfully",Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Toast.makeText(this,"Something Went Wrong pet cant be updated",Toast.LENGTH_SHORT).show();
                }
            }
        }else{
            Toast.makeText(this,"Something Went Wrong check fields again",Toast.LENGTH_SHORT).show();
        }
    }
private boolean AddPet(){
    ContentValues values = new ContentValues();
    values.put(PetContract.Pet.COLUMN_Email,Email);
    values.put(PetContract.Pet.COLUMN_PetName,petname);
    values.put(PetContract.Pet.COLUMN_Age,petage);
    values.put(PetContract.Pet.COLUMN_Weight,petweight);
    values.put(PetContract.Pet.COLUMN_Color,petcolor);
    values.put(PetContract.Pet.COLUMN_PetType,pettype);
    values.put(PetContract.Pet.COLUMN_PetDetails,petdetails);
    values.put(PetContract.Pet.COLUMN_Currency,currency);
    values.put(PetContract.Pet.COLUMN_image,img);
    values.put(PetContract.Pet.COLUMN_Location,address);
    values.put(PetContract.Pet.COLUMN_Contact,contact);
    Uri newUri = getContentResolver().insert(PetContract.Pet.CONTENT_URI,values);
    if(newUri == null){
        return  false;
    }
        return true;
}
    private boolean UpdatePet(){
       ContentValues values = new ContentValues();
        values.put(PetContract.Pet.COLUMN_Email,Email);
        values.put(PetContract.Pet.COLUMN_PetName,petname);
        values.put(PetContract.Pet.COLUMN_Age,petage);
        values.put(PetContract.Pet.COLUMN_Weight,petweight);
        values.put(PetContract.Pet.COLUMN_Color,petcolor);
        values.put(PetContract.Pet.COLUMN_PetType,pettype);
        values.put(PetContract.Pet.COLUMN_PetDetails,petdetails);
        values.put(PetContract.Pet.COLUMN_Currency,currency);
        values.put(PetContract.Pet.COLUMN_image,img);
        values.put(PetContract.Pet.COLUMN_Location,address);
        values.put(PetContract.Pet.COLUMN_Contact,contact);
        String selection = PetContract.Pet._ID +"=?";
        String [] selectionArgs ={
               intent.getStringExtra("id"),
        };
        int effected = getContentResolver().update(PetContract.Pet.CONTENT_URI,values,selection,selectionArgs);
        if(effected != 0){
            return true;
        }
        return false;

    }
    private boolean checkvalidation() {
      //  pettype = spinner.getSelectedItem().toString(); // get spinner item #1
        petname = PetName.getText().toString();
         age = PetAge.getText().toString();
        if(age.isEmpty() || age == null){
            petage =0;}else{
            petage = Integer.parseInt(age);}
         weight = PetWeight.getText().toString();
        if(weight.isEmpty() || weight == null){
            petweight =0;}else{
            petweight = Integer.parseInt(weight);}
        petcolor = PetColor.getText().toString().trim();
        currency=etInput.getText().toString();
        if(currency.isEmpty() || currency == null){petcurrency=0;}else{
            String numberString = currency.replaceAll("[^\\d\\,\\.]", "");
            String C = numberString.replace(",","").trim();
            petcurrency=Integer.parseInt(C);
        }
        address=Address.getText().toString();
        contact=PetContact.getText().toString();
        if(petcolor.matches(".*\\d.*") || petcolor.matches(".*\\d.*")|| petname.matches(".*\\d.*")|| petname.isEmpty() || petname == null || age.isEmpty() || age == null || weight.isEmpty() || weight == null || petweight <= 0 || petage <= 0 ||
        petcolor.isEmpty() || petcolor == null || pettype.equals("Select Pet") || petdetails.equals("Select Dog Type") || petdetails.equals("Select Cat Type") || petdetails.equals("Select Bird Type")
       ||  petdetails == null || bitmap == null  || petcurrency <=0 || currency.isEmpty() || currency == null || address.isEmpty() || address == null ||contact.isEmpty() || contact == null || Integer.parseInt(contact) < 11 ){
            // check petname
            if(petname.isEmpty() || petname == null){
                error.setVisibility(View.VISIBLE);
                error.setText(R.string.PetNameError);
            }else if(petname.matches(".*\\d.*")){
                error.setVisibility(View.VISIBLE);
                error.setText(R.string.PetNameErrorr);
            }else{
                error.setText("");
                error.setVisibility(View.GONE);
            }
            // check petage
            if(age.isEmpty() || age == null){
                error2.setVisibility(View.VISIBLE);
                error2.setText(R.string.PetAgeError);
            }else if (petage <= 0){
                error2.setVisibility(View.VISIBLE);
                error2.setText(R.string.PetAgeerror);
            }else{
                error2.setText("");
                error2.setVisibility(View.GONE);
            }
            //check petweight
            if(weight.isEmpty() || weight == null){
                error3.setVisibility(View.VISIBLE);
                error3.setText(R.string.weightError);
            }else if(petweight <= 0){
                error3.setVisibility(View.VISIBLE);
                error3.setText(R.string.petweighterror);
            }else{
                error3.setText("");
                error3.setVisibility(View.GONE);
            }
            //check petcolor
            if(petcolor.isEmpty() || petcolor == null){
                error4.setVisibility(View.VISIBLE);
                error4.setText(R.string.petcolorerror);
            }else if (petcolor.matches(".*\\d.*")){
                error4.setVisibility(View.VISIBLE);
                error4.setText(R.string.colorerror);
            }else{
                error4.setText("");
                error4.setVisibility(View.GONE);
            }
            // check pettype
            if(pettype.equals("Select Pet")){
                error5.setVisibility(View.VISIBLE);
                error5.setText(R.string.Selectpeterror);
            }else{
                error5.setText("");
                error5.setVisibility(View.GONE);
            }
            // check petdetails
            if(petdetails == null){
                error6.setVisibility(View.VISIBLE);
                error6.setText(R.string.petdetailserror);
            }
            else if(petdetails.equals("Select Dog Type") || petdetails.equals("Select Cat Type") || petdetails.equals("Select Bird Type") ){
                error6.setVisibility(View.VISIBLE);
                error6.setText(R.string.petdetailserror);
            }else{
                error6.setText("");
                error6.setVisibility(View.GONE);
            }
            // check curreny
            if(currency.isEmpty() || currency == null){
                error8.setVisibility(View.VISIBLE);
                error8.setText(R.string.Currencyerror);
            }else if (petcurrency <= 0){
                error8.setVisibility(View.VISIBLE);
                error8.setText(R.string.Currenerror);
            }else{
                error8.setText("");
                error8.setVisibility(View.GONE);
            }
            // check image
           if(bitmap == null){
               error9.setVisibility(View.VISIBLE);
               error9.setText(R.string.imagerror);
           }else{
               error9.setText("");
               error9.setVisibility(View.GONE);
           }
           // check contact (phonenumber)
           if(contact.isEmpty() || contact == null){
               error11.setVisibility(View.VISIBLE);
               error11.setText("Contact Required");
           }else if(Integer.parseInt(contact) < 11){
               error11.setVisibility(View.VISIBLE);
               error11.setText("Phone number cant be less than 11");
           }else{
               error11.setText("");
               error11.setVisibility(View.GONE);
           }
           // check address
            if(address.isEmpty() || address == null){
                error10.setVisibility(View.VISIBLE);
                error10.setText("Address Required");
            } else{
                error10.setText("");
                error10.setVisibility(View.GONE);
            }
            return false;
        }
        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        pettype = parent.getItemAtPosition(position).toString();
        if(pettype.equals("Dog")){
            spinner2.setVisibility(View.VISIBLE);
             adapter2 = ArrayAdapter.createFromResource(this,
                    R.array.Dog, android.R.layout.simple_spinner_item);
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner2.setAdapter(adapter2);
            if(intent.getStringExtra("petname") != null){
            if(intent.getStringExtra("des").equals("None")){spinner2.setSelection(0);}else{
                int pos =  adapter2.getPosition(intent.getStringExtra("des"));
                spinner2.setSelection(pos);
            }}
            spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    petdetails = parent.getItemAtPosition(position).toString();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        }else if(pettype.equals("Cat")){
            spinner2.setVisibility(View.VISIBLE);
             adapter2 = ArrayAdapter.createFromResource(this,
                    R.array.Cat, android.R.layout.simple_spinner_item);
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner2.setAdapter(adapter2);
            if(intent.getStringExtra("petname") != null){
                if(intent.getStringExtra("des").equals("None")){spinner2.setSelection(0);}else{
                    int pos =  adapter2.getPosition(intent.getStringExtra("des"));
                    spinner2.setSelection(pos);
                }}
            spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    petdetails = parent.getItemAtPosition(position).toString();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }else if(pettype.equals("Bird")){
            spinner2.setVisibility(View.VISIBLE);
             adapter2 = ArrayAdapter.createFromResource(this,
                    R.array.Bird, android.R.layout.simple_spinner_item);
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner2.setAdapter(adapter2);
            if(intent.getStringExtra("petname") != null){
                if(intent.getStringExtra("des").equals("None")){spinner2.setSelection(0);}else{
                    int pos =  adapter2.getPosition(intent.getStringExtra("des"));
                    spinner2.setSelection(pos);
                }}
            spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    petdetails = parent.getItemAtPosition(position).toString();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        }else{
              spinner2.setVisibility(View.GONE);
              petdetails = "";
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            mPetHasChanged = true;
            return false;
        }
    };
    @Override
    public void onBackPressed() {
        if (!mPetHasChanged) {
          finish();
        }
        DialogInterface.OnClickListener discardButtonClickListener =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                };
        showUnsavedChangesDialog(discardButtonClickListener);
    }
    // unsaved Dialog while clicking back button
    private void showUnsavedChangesDialog(DialogInterface.OnClickListener discardButtonClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Discard your changes and quit editing?");
        builder.setPositiveButton("Discard", discardButtonClickListener);
        builder.setNegativeButton("Keep Editing", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    // Get image from mobile_Storage
    public void selectImage(View view)
    {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,0);
    }
    // when image is selected
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == this.RESULT_OK && data !=null)
        {
            imageButton.setVisibility(View.GONE);
            imageView.setBackground(null);
            textView.setVisibility(View.GONE);
            Uri selectedImage = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),
                        selectedImage);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG,100,bos);
                img = bos.toByteArray();
                imageView.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();

            }

        }
    }
}