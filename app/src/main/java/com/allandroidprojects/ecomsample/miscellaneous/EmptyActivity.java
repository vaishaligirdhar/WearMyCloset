package com.allandroidprojects.ecomsample.miscellaneous;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import com.allandroidprojects.ecomsample.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;
import java.net.HttpURLConnection;

public class EmptyActivity extends AppCompatActivity {

    private ImageView imageHolder;
    private final int requestCode = 20;
    EditText prod_name;
    EditText prod_desc;
    EditText prod_price;
    EditText prod_contact;
    Spinner mySpinner1;
    Spinner mySpinner2;
    String fname;
    Bitmap bitmap;
    static String myUrl = "";
    Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty);

        prod_name = (EditText) findViewById(R.id.name_txt);
        prod_desc = (EditText) findViewById(R.id.description_txt);
        prod_price = (EditText) findViewById(R.id.price_txt);
        prod_contact = (EditText) findViewById(R.id.contact_txt);

        prod_desc.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (v.getId() == R.id.description_txt) {
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                    switch (event.getAction() & MotionEvent.ACTION_MASK) {
                        case MotionEvent.ACTION_UP:
                            v.getParent().requestDisallowInterceptTouchEvent(false);
                            break;
                    }
                }
                return false;
            }
        });

        mySpinner1 = (Spinner) findViewById(R.id.spinner_gender);
        mySpinner2 = (Spinner) findViewById(R.id.spinner_category);

        ArrayAdapter<String> myAdapter1 = new ArrayAdapter<String>(
                EmptyActivity.this,
                android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.gender_type));

        ArrayAdapter<String> myAdapter2 = new ArrayAdapter<String>(
                EmptyActivity.this,
                android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.category_type));


        myAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        myAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mySpinner1.setAdapter(myAdapter1);
        mySpinner2.setAdapter(myAdapter2);

        imageHolder = (ImageView)findViewById(R.id.captured_photo);
        Button capturedImageButton = (Button)findViewById(R.id.photo_button);
        capturedImageButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(photoCaptureIntent, requestCode);
            }
        });

        submitButton = (Button) findViewById(R.id.submit_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage(bitmap, fname);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(this.requestCode == requestCode && resultCode == RESULT_OK){
            //bitmap = Bitmap.createScaledBitmap((Bitmap) data.getExtras().get("data"), 200, 200, true);
            bitmap = (Bitmap)data.getExtras().get("data");

            String root = Environment.getExternalStorageDirectory().toString();
            File myDir = new File(root + "/req_images");
            myDir.mkdirs();
            Random generator = new Random();
            int n = 10000;
            n = generator.nextInt(n);
            fname = "Image-" + n + ".jpg";
            File file = new File(myDir, fname);

            MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, fname, "desc");

            if (file.exists())
                file.delete();
            try {
                FileOutputStream out = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                out.flush();
                out.close();

                Toast.makeText(this, "Image Saved", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(this, "Save Failed", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
            imageHolder.setImageBitmap(bitmap);
        }
    }


    protected void uploadImage(Bitmap bitmap, String fname){
        final String name = prod_name.getText().toString();
        final Double price = Double.parseDouble(prod_price.getText().toString());
        final String desc = prod_desc.getText().toString();
        final String gender = mySpinner1.getSelectedItem().toString();
        final String category = mySpinner2.getSelectedItem().toString();
        final String contact = prod_contact.getText().toString();

        FirebaseStorage storage = FirebaseStorage.getInstance();
        final StorageReference storageRef = storage.getReference();
        StorageReference mountainsRef = storageRef.child(fname);
        StorageReference mountainImagesRef = storageRef.child("images/" + fname + ".jpg");
        mountainsRef.getName().equals(mountainImagesRef.getName());    // true
        mountainsRef.getPath().equals(mountainImagesRef.getPath());    // false
        //imageHolder.setDrawingCacheEnabled(true);
        imageHolder.buildDrawingCache();
        bitmap = imageHolder.getDrawingCache();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data1 = baos.toByteArray();

        UploadTask uploadTask = mountainsRef.putBytes(data1);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                @SuppressWarnings("VisibleForTests") Uri downloadUrl = taskSnapshot.getDownloadUrl();
                myUrl = downloadUrl.toString();
                uploadToServer(name, price,desc, gender, category, myUrl, contact);
                Toast.makeText(getApplicationContext(), "Item uploaded.", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void uploadToServer(String name, Double price, String desc,
                               String gender, String category, String url, String contact){
        final String mName = name;
        final Double mDouble = price;
        final String mDesc = desc;
        final String mGender = gender;
        final String mCategory = category;
        final String mUrl = url;
        final String mContact = contact;

        AsyncTask<String, Void, String> task = new AsyncTask<String, Void, String>() {
            boolean success;

            @Override
            protected String doInBackground(String... arg0) {
                try {
                    URL conUrl = new URL("http://i.cs.hku.hk/~h3517511/dbHandler.php?request=20&name="+ mName
                            + "&gender="+ mGender + "&category=" + mCategory + "&price=" + mDouble + "&description=" + mDesc
                            + "&image=" + mUrl + "&contact=" + mContact);
                    HttpURLConnection urlConnection = (HttpURLConnection) conUrl.openConnection();
                    urlConnection.setRequestMethod("GET");
                    int code = urlConnection.getResponseCode();
                }catch(MalformedURLException e){

                }catch(IOException e){

                }
                return null;
            }

            @Override
            protected void onPostExecute(String result) {

            }
        }.execute("");
    }
}