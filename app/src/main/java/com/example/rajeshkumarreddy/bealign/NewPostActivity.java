package com.example.rajeshkumarreddy.bealign;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.tapadoo.alerter.Alerter;
import com.wang.avi.AVLoadingIndicatorView;

public class NewPostActivity extends AppCompatActivity {
    ImageButton selectImage;
    EditText mPostTitle,mPostDesc;
    Button mSubmitBtn;
    Uri imageUri=null;
    private static final int GALLERY_REQUEST=1;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    AVLoadingIndicatorView avLoadingIndicatorView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        storageReference= FirebaseStorage.getInstance().getReference();
        databaseReference= FirebaseDatabase.getInstance().getReference("Feeds");

        selectImage=(ImageButton)findViewById(R.id.NPImage);
        mPostDesc=(EditText)findViewById(R.id.NPDesc);
        mPostTitle=(EditText)findViewById(R.id.NPTitle);
        mSubmitBtn=(Button)findViewById(R.id.NPPost);
        avLoadingIndicatorView=(AVLoadingIndicatorView)findViewById(R.id.rotateBox);

        selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Intent.ACTION_GET_CONTENT);
                i.setType("image/*");
                startActivityForResult(i,GALLERY_REQUEST);
            }
        });

        mSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String pTitle=mPostTitle.getText().toString().trim();
                final String pDesc=mPostDesc.getText().toString().trim();

                if (!TextUtils.isEmpty(pTitle)&&!TextUtils.isEmpty(pDesc)&&imageUri!=null){

                    selectImage.setClickable(false);
                    mPostTitle.setFocusableInTouchMode(false);
                    mPostTitle.setFocusable(false);
                    mPostTitle.setClickable(false);
                    mPostDesc.setFocusableInTouchMode(false);
                    mPostDesc.setFocusable(false);
                    mPostDesc.setClickable(false);
                    mSubmitBtn.setVisibility(View.GONE);
                    avLoadingIndicatorView.setVisibility(View.VISIBLE);
                    avLoadingIndicatorView.show();

                    StorageReference filePath=storageReference.child("Feed_Images").child(imageUri.getLastPathSegment());
                    filePath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Uri downloadUrl=taskSnapshot.getDownloadUrl();
                            DatabaseReference newPost=databaseReference.push();
                            NewFeed newFeed=new NewFeed(pTitle,pDesc,downloadUrl.toString());
                            newPost.setValue(newFeed).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        selectImage.setImageResource(R.drawable.select);
                                        selectImage.setClickable(true);
                                        mPostTitle.setText("");
                                        mPostTitle.setFocusable(true);
                                        mPostTitle.setFocusableInTouchMode(true);
                                        mPostTitle.setClickable(true);
                                        mPostDesc.setText("");
                                        mPostDesc.setFocusableInTouchMode(true);
                                        mPostDesc.setFocusable(true);
                                        mPostDesc.setClickable(true);
                                        avLoadingIndicatorView.hide();
                                        avLoadingIndicatorView.setVisibility(View.GONE);
                                        mSubmitBtn.setVisibility(View.VISIBLE);

                                        Alerter.create(NewPostActivity.this)
                                                .setTitle("Successfully posted!")
                                                .setIcon(R.drawable.ic_done)
                                                .setBackgroundColor(R.color.success)
                                                .show();
                                    }
                                    else {
                                        selectImage.setClickable(true);
                                        mPostTitle.setFocusable(true);
                                        mPostTitle.setFocusableInTouchMode(true);
                                        mPostTitle.setClickable(true);
                                        mPostDesc.setFocusableInTouchMode(true);
                                        mPostDesc.setFocusable(true);
                                        mPostDesc.setClickable(true);
                                        avLoadingIndicatorView.hide();
                                        avLoadingIndicatorView.setVisibility(View.GONE);
                                        mSubmitBtn.setVisibility(View.VISIBLE);

                                        Alerter.create(NewPostActivity.this)
                                                .setTitle("Couldn't post!")
                                                .setText("Please try again with active internet connection.")
                                                .setDuration(4000)
                                                .setIcon(R.drawable.ic_empty)
                                                .setBackgroundColor(R.color.error)
                                                .show();
                                    }
                                }
                            });

                        }
                    });
                }
                else {
                    Alerter.create(NewPostActivity.this)
                            .setTitle("Couldn't post!")
                            .setText("Please fill out all fields & pick an image to continue.")
                            .setDuration(4000)
                            .setIcon(R.drawable.ic_empty)
                            .setBackgroundColor(R.color.error)
                            .show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==GALLERY_REQUEST&&resultCode==RESULT_OK){
            imageUri=data.getData();
            selectImage.setImageURI(imageUri);
        }
    }
}
