package com.example.doctorsdiabeticapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.doctorsdiabeticapp.AddidiotalView.Loadingbar;
import com.example.doctorsdiabeticapp.BaseActivity.BaseActivity;
import com.example.doctorsdiabeticapp.Model.Doctor;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * The class responsible for the possibility of adding photos to the user or updating them.
 */
public class AddPhotoActivity extends BaseActivity implements View.OnClickListener {

    private static final int IMAGE_CAMERA_PICK_CODE = 2300;
    private static final int IMAGE_GALLERY_PICK_CODE = 2301;
    private static final int PERMISSION_CODE = 1001;
    private String title;
    private CardView cardView_photo_camera, cardView_photo_gallery;
    private CircleImageView mProfileImage;
    private Loadingbar loadingbar;
    private FirebaseAuth mAuth;
    private FirebaseStorage mStorageReference;
    private StorageReference mStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_photo);
        initialize();

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE},
                PackageManager.PERMISSION_GRANTED);

        showCurrentImage();
    }

    public void initialize() {
        title = getString(R.string.add_photo_string);
        setToolbarOther(title);
        cardView_photo_camera = findViewById(R.id.cardView_add_photo_on_camera);
        cardView_photo_gallery = findViewById(R.id.cardView_add_photo_on_galery);
        mProfileImage = findViewById(R.id.profile_image);
        cardView_photo_gallery.setOnClickListener(this);
        cardView_photo_camera.setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();
        mStorageReference = FirebaseStorage.getInstance();
        mStorage = mStorageReference.getReference();
        loadingbar = new Loadingbar(this);
    }


    /**
     * Selecting a photo from the gallery
     */
    private void pickImageFromGallery() {

        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto, IMAGE_GALLERY_PICK_CODE);
    }

    /**
     * Launch the camera and take a photo
     */
    private void pickImageFromCamera() {

        Intent makePhoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(makePhoto, IMAGE_CAMERA_PICK_CODE);

    }

    /**
     * Re-checking the required permissions, if they are performing the method of
     * selecting a photo from the gallery
     */
    private void checkPermissionGallery() {
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_DENIED) {
            String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
            requestPermissions(permissions, PERMISSION_CODE);
        } else {
            pickImageFromGallery();
        }
    }

    /**
     * Re-checking the required permission, if they are performing the method of
     * taking a photo from camera
     */
    private void checkPermissionCamera() {
        if (checkSelfPermission(Manifest.permission.CAMERA) ==
                PackageManager.PERMISSION_DENIED ||
                checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                        PackageManager.PERMISSION_DENIED) {
            String[] permission = {Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE};
            requestPermissions(permission, PERMISSION_CODE);
        } else {
            pickImageFromCamera();
        }

    }


    /**
     * Converting a bitmap to an byte array
     */
    private byte[] getByteData(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    /**
     * Retrieving user information from the database, in this case only if there is a path to image.
     * If there is no path, it displays the default image, otherwise it displays the image
     * from the path
     */
    private void showCurrentImage() {
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                .getReference("Doctors").child(mAuth.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               Doctor doctor = snapshot.getValue(Doctor.class);
                if (doctor.getUrlImage().equals("")) {
                    mProfileImage.setImageResource(R.drawable.example_profile);
                } else {
                    Glide.with(getBaseContext()).load(doctor.getUrlImage()).into(mProfileImage);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("TAG: " + getLocalClassName(), "Error: " + error.toString());
            }
        });
    }


    /**
     * The method responsible for saving the photo in the database and updating user
     * information by giving the url path to the uploaded photo.
     */
    private void saveImage(Uri filePath, byte[] data) {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading image");
        progressDialog.show();

        final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = firebaseDatabase.getReference("Doctors")
                .child(mAuth.getUid());
        final StorageReference reference = mStorage.child("Images/").child(mAuth.getUid())
                .child("Images_Profile/" + UUID.randomUUID().toString());

        final UploadTask uploadTask;
        if (filePath != null) {
            uploadTask = reference.putFile(filePath);
        } else {
            uploadTask = reference.putBytes(data);
        }
        uploadTask
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        progressDialog.dismiss();
                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                HashMap<String, Object> map = new HashMap<>();
                                map.put("urlImage", uri.toString());
                                databaseReference.updateChildren(map);
                            }
                        });
                        Toast.makeText(getApplicationContext(),
                                getResources().getString(R.string.add_image_success),
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),
                                getResources().getString(R.string.add_image_fail),
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnCanceledListener(new OnCanceledListener() {
                    @Override
                    public void onCanceled() {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),
                                getResources().getString(R.string.add_image_cancel),
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        double progress = (100.0 * snapshot.getBytesTransferred() / snapshot
                                .getTotalByteCount());
                        progressDialog.setMessage("Upload " + (int) progress + "%");
                    }
                });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (resultCode == RESULT_OK && IMAGE_CAMERA_PICK_CODE == requestCode) {

                assert data != null;
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                mProfileImage.setImageBitmap(imageBitmap);
                saveImage(data.getData(), getByteData(imageBitmap));

                loadingbar.dismissbar();

            } else if (resultCode == RESULT_OK && IMAGE_GALLERY_PICK_CODE == requestCode) {

                assert data != null;
                mProfileImage.setImageURI(data.getData());
                saveImage(data.getData(), null);
                loadingbar.dismissbar();
            } else {

                loadingbar.dismissbar();
                Toast.makeText(this,
                        getResources().getString(R.string.add_picture_fail),
                        Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Log.e("TAG", "Exception: " + e.toString());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cardView_add_photo_on_camera:
                loadingbar.showDialog();
                checkPermissionCamera();
                break;
            case R.id.cardView_add_photo_on_galery:
                loadingbar.showDialog();
                checkPermissionGallery();
                break;
            default:
                break;
        }
    }
}
