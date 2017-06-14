package com.example.casper.userregistration;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.*;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class Main2Activity extends AppCompatActivity {
    private static final int PIC_IMAGE = 234;
    public static Uri uploadedUri;
    FirebaseUser user;
    FirebaseDatabase database;
    DatabaseReference mRef;
    StorageReference storageReference;
    TextView emailText;
    TextView userId;
    TextView phoneText;
    TextView nameText;
    TextView addressText;
    Button submitBtn;
    Button snapBtn;
    private Uri filepath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        emailText=(TextView)findViewById(R.id.logged_mail);
        userId=(TextView) findViewById(R.id.user_id);
        nameText=(TextView)findViewById(R.id.user_name);
        addressText=(TextView)findViewById(R.id.user_address);
        phoneText=(TextView)findViewById(R.id.user_phone);
        user= FirebaseAuth.getInstance().getCurrentUser();
        submitBtn=(Button)findViewById(R.id.submitbtn);
        snapBtn=(Button)findViewById(R.id.snapbtn);

        if(user !=null){
            String email=user.getEmail();
            String userID=user.getUid();

            emailText.setText(email);
            userId.setText(userID);

        }
        database=FirebaseDatabase.getInstance();
        mRef=database.getReference();
        storageReference= FirebaseStorage.getInstance().getReference();

        //Update User information
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=nameText.getText().toString();
                String address=addressText.getText().toString();
                String phone_no=phoneText.getText().toString();

               // UserInfo userInfo=new UserInfo(name,address,phone_no);
               // mRef.child("users").child(user.getUid()).setValue(userInfo);
                Toast.makeText(Main2Activity.this,"Submitted",Toast.LENGTH_SHORT).show();
                nameText.setText("");
                addressText.setText("");
                phoneText.setText("");
            }
        });

        //Upload picture on the firebase
        snapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,PIC_IMAGE);
            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PIC_IMAGE && resultCode==RESULT_OK){
                 filepath=data.getData();
            try {
                Bitmap bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),filepath);
                Toast.makeText(this, "Successful", Toast.LENGTH_SHORT).show();
                updateUserPic();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateUserPic() {
      if(filepath!=null){
          final ProgressDialog progressDialog=new ProgressDialog(this);
          progressDialog.setMessage("Uploading.....");
          progressDialog.show();
          StorageReference riversRef= storageReference.child(user.getUid().toString()+"/"+"profile1.jpg");
          riversRef.putFile(filepath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
              @Override
              public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                  progressDialog.dismiss();
                  Toast.makeText(Main2Activity.this, "File Uploaded successfully", Toast.LENGTH_SHORT).show();
                  @SuppressWarnings("VisibleForTests") Uri uploaded= taskSnapshot.getDownloadUrl();
                  uploadedUri=uploaded;
                  startActivity(new Intent(Main2Activity.this,UserProfileActivity.class));
              }
          }).addOnFailureListener(new OnFailureListener() {
              @Override
              public void onFailure(@NonNull Exception e) {

              }
          }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
              @Override
              public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                  @SuppressWarnings("VisibleForTests") double progress = (100.0 * taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();
                  progressDialog.setMessage("uploading......"+ (int)(progress)+ "%");
              }
          });
      }
    }

}
