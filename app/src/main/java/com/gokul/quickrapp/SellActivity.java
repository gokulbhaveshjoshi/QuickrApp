package com.gokul.quickrapp;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SellActivity extends AppCompatActivity {

    int count = 0;

    // Log tag that is used to distinguish log info.
    private final static String TAG_BROWSE_PICTURE = "BROWSE_PICTURE";
    // Used when request action Intent.ACTION_GET_CONTENT
    private final static int REQUEST_CODE_BROWSE_PICTURE = 1;
    // Used when request read external storage permission.
    private final static int REQUEST_PERMISSION_READ_EXTERNAL = 2;
    ArrayList<Task<Uri>> imageTaskUri = null;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseStorage mStorageRef = FirebaseStorage.getInstance();
    FirebaseAuth auth;
    Button addImage, showImage;
    EditText productName, productCategory, productDescription, productPrice;
    String pName, pCategory, pDescription, pPrice ;
    // The image view that used to display user selected image.
    private ImageView selectedPictureImageView;
    // Save user selected image uri list.
    private List<Uri> userSelectedImageUriList = null;
    // Currently displayed user selected image index in userSelectedImageUriList.
    private int currentDisplayedUserSelectImageIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell);
        addImage = findViewById(R.id.btnAddImage);
        showImage = findViewById(R.id.btnShowImage);
        productName = findViewById(R.id.edProductTitle);
        productCategory = findViewById(R.id.etProductCategory);
        productDescription = findViewById(R.id.etProductDescription);
        selectedPictureImageView = findViewById(R.id.ivSelected);
        productPrice = findViewById(R.id.etProductPrice);
        auth = FirebaseAuth.getInstance();
        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int readExternalStoragePermission = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
                if (readExternalStoragePermission != PackageManager.PERMISSION_GRANTED) {
                    String[] requirePermission = {Manifest.permission.READ_EXTERNAL_STORAGE};
                    ActivityCompat.requestPermissions(SellActivity.this, requirePermission, REQUEST_PERMISSION_READ_EXTERNAL);
                } else {
                    openPictureGallery();
                }
            }
        });
        checkAuth();

        showImage.setOnClickListener(v -> {
            if (userSelectedImageUriList != null) {
                // Get current display image file uri.
                Uri currentUri = userSelectedImageUriList.get(currentDisplayedUserSelectImageIndex);
                ContentResolver contentResolver = getContentResolver();
                try {
                    // User content resolver to get uri input stream.
                    InputStream inputStream = contentResolver.openInputStream(currentUri);
                    // Get the bitmap.
                    Bitmap imgBitmap = BitmapFactory.decodeStream(inputStream);
                    // Show image bitmap in imageview object.
                    selectedPictureImageView.setImageBitmap(imgBitmap);
                } catch (FileNotFoundException ex) {
                    Log.e(TAG_BROWSE_PICTURE, ex.getMessage(), ex);
                }
                // Get total user selected image count.
                int size = userSelectedImageUriList.size();
                if (currentDisplayedUserSelectImageIndex >= (size - 1)) {
                    currentDisplayedUserSelectImageIndex = 0;
                } else {
                    currentDisplayedUserSelectImageIndex++;
                }
            }
        });
    }

    private void checkAuth() {
        if (auth == null) {
            FirebaseUser user = auth.getCurrentUser();
        }
    }


    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_BROWSE_PICTURE) {
            if (resultCode == RESULT_OK) {
                Uri fileUri = data.getData();
                // Save user choose image file uri in list.
                if (userSelectedImageUriList == null) {
                    userSelectedImageUriList = new ArrayList<>();
                }

                userSelectedImageUriList.add(fileUri);


                ContentResolver contentResolver = getContentResolver();
                try {
                    InputStream inputStream = contentResolver.openInputStream(fileUri);
                    Bitmap imgBitmap = BitmapFactory.decodeStream(inputStream);
                    selectedPictureImageView.setImageBitmap(imgBitmap);
                    inputStream.close();
                } catch (IOException ex) {
                    Log.e(TAG_BROWSE_PICTURE, ex.getMessage(), ex);
                }
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_READ_EXTERNAL) {
            if (grantResults.length > 0) {
                int grantResult = grantResults[0];
                if (grantResult == PackageManager.PERMISSION_GRANTED) {
                    openPictureGallery();
                } else {
                    Toast.makeText(getApplicationContext(), "You denied read external storage permission.", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    private void openPictureGallery() {

        Intent openAlbumIntent = new Intent();
        openAlbumIntent.setType("image/*");
        openAlbumIntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(openAlbumIntent, REQUEST_CODE_BROWSE_PICTURE);
    }

    public void submit(View view) {
        pName = productName.getText().toString();
        pDescription = productDescription.getText().toString();
        pCategory = productCategory.getText().toString();
        pPrice = productPrice.getText().toString();



        if ((!pName.isEmpty()) && (!pDescription.isEmpty()) && (!pCategory.isEmpty())) {
//            uploadToServer();
            uploadImage();
            showToast("this is working");
        } else {
            if (pName.isEmpty()) {
                showToast("please enter the product name or title");

            } else {
                if (pCategory.isEmpty()) {
                    showToast("Please enter the category");
                } else {
                    if (pDescription.isEmpty()) {
                        showToast("please enter the description");
                    }

                }
            }


        }

    }

    private void uploadImage() {
        StorageReference imageLocation = mStorageRef.getReference().child("Photos").child(FieldValue.serverTimestamp().toString());

        for (int i = 0; i < 5 && i < userSelectedImageUriList.size(); i++) {
            Uri upload_image = userSelectedImageUriList.get(i);
            imageLocation.putFile(upload_image).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
//                    Task<Uri> downloadUri = task.getResult().getStorage().getDownloadUrl();
                    String downloadUri = task.getResult().getStorage().getDownloadUrl().toString();
                    Map<String, String> product = new HashMap<>();
                    product.put("name", pName);
                    product.put("description", pDescription);
                    product.put("category", pCategory);
                    product.put("image", downloadUri.toString());
                    product.put("productPrice", pPrice);
                    db.collection("products").add(product)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {

                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Log.d("Response", "Document added id is" + documentReference.getId());
                                    goToNew();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull @NotNull Exception e) {
                                    Log.e("Response Failed", "Error " + e);
                                }
                            });
//                    addData(downloadUri);
//                    showLog("CheckURI", "" + downloadUri);
                }
            });
        }


    }

    private void goToNew() {
        startActivity(new Intent(this, BuyActivity.class));
        finish();
    }

    private void addData(Task<Uri> uri){
        count ++;
        if(imageTaskUri == null){
            imageTaskUri = new ArrayList<>();
        }
        imageTaskUri.add(uri);

        if(userSelectedImageUriList.size() == count){
            uploadToServer(imageTaskUri);
        }

    }



    private void uploadToServer(ArrayList<Task<Uri>> imageUrl) {
        Map<String, Object> product = new HashMap<>();
        product.put("name", pName);
        product.put("description", pDescription);
        product.put("category", pCategory);
        Log.d("ImageListSize", "" + imageUrl.size());
        for (int i = 0; i < imageUrl.size(); i++) {
            product.put("image" + i, imageUrl.get(i));
        }

        db.collection("products").add(product)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {

                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("Response", "Document added id is" + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        Log.e("Response Failed", "Error " + e);
                    }
                });
    }
}