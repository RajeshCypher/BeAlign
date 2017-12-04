package com.example.rajeshkumarreddy.bealign;

import android.content.Intent;
import android.graphics.Typeface;
import android.media.Image;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hsalf.smilerating.SmileRating;
import com.tapadoo.alerter.Alerter;
import com.wang.avi.AVLoadingIndicatorView;

import org.w3c.dom.Text;

import es.dmoral.toasty.Toasty;

public class MoreActivity extends AppCompatActivity implements View.OnClickListener{
    TextView findLoc,findLocT,security,securityT,iCalc,iCalcT,feed,feedT,MCalc,MCalcT,MSmartT,MCgpa,MCgpaT,MDev;
    Switch MSmart;
    Typeface segoe,sans,productBold,product,sansSBold;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);

        findLoc=(TextView)findViewById(R.id.MFindClass);
        findLocT=(TextView)findViewById(R.id.MFindClassT);
        security=(TextView)findViewById(R.id.MSecrity);
        securityT=(TextView)findViewById(R.id.MSecrityT);
        iCalc=(TextView)findViewById(R.id.MICalc);
        iCalcT=(TextView)findViewById(R.id.MICalcT);
        feed=(TextView)findViewById(R.id.MFeedback);
        feedT=(TextView)findViewById(R.id.MFeedbackT);
        MCalc=(TextView)findViewById(R.id.MACalc);
        MCalcT=(TextView)findViewById(R.id.MACalcT);
        MSmartT=(TextView)findViewById(R.id.MsmartT);
        MSmart=(Switch)findViewById(R.id.MSmart);
        MCgpa=(TextView)findViewById(R.id.MCgpa);
        MCgpaT=(TextView)findViewById(R.id.MCgpaT);
        MDev=(TextView)findViewById(R.id.MDev);
        firebaseAuth=FirebaseAuth.getInstance();
        user=firebaseAuth.getCurrentUser();



        segoe=Typeface.createFromAsset(getAssets(),"fonts/segoeui.ttf");
        sans=Typeface.createFromAsset(getAssets(),"fonts/opensans/OpenSans-Regular.ttf");
        productBold=Typeface.createFromAsset(getAssets(),"fonts/productsans/ProductSansBold.ttf");
        product=Typeface.createFromAsset(getAssets(),"fonts/productsans/ProductSansRegular.ttf");
        sansSBold=Typeface.createFromAsset(getAssets(),"fonts/opensans/OpenSans-Semibold.ttf");

        findLoc.setTypeface(sansSBold);
        findLocT.setTypeface(segoe);
        security.setTypeface(sansSBold);
        securityT.setTypeface(segoe);
        iCalc.setTypeface(sansSBold);
        iCalcT.setTypeface(segoe);
        feed.setTypeface(sansSBold);
        feedT.setTypeface(segoe);
        MCalc.setTypeface(sansSBold);
        MCalcT.setTypeface(segoe);
        MSmart.setTypeface(sansSBold);
        MSmartT.setTypeface(segoe);
        MCgpa.setTypeface(sansSBold);
        MCgpaT.setTypeface(segoe);
        MDev.setTypeface(sansSBold);

        findLoc.setOnClickListener(this);
        findLocT.setOnClickListener(this);
        security.setOnClickListener(this);
        securityT.setOnClickListener(this);
        iCalc.setOnClickListener(this);
        iCalcT.setOnClickListener(this);
        feed.setOnClickListener(this);
        feedT.setOnClickListener(this);
        MCgpa.setOnClickListener(this);
        MCgpaT.setOnClickListener(this);
        MDev.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.MFindClass||view.getId()==R.id.MFindClassT){
            startActivity(new Intent(MoreActivity.this,FindClassActivity.class));
        }
        if (view.getId()==R.id.MCgpa||view.getId()==R.id.MCgpaT){
            startActivity(new Intent(MoreActivity.this,CCalculatorActivity.class));
        }
        if (view.getId()==R.id.MSecrity||view.getId()==R.id.MSecrityT){
            startActivity(new Intent(MoreActivity.this,SecurityActivity.class));
        }
        if (view.getId()==R.id.MICalc||view.getId()==R.id.MICalcT){
            startActivity(new Intent(MoreActivity.this,ICalculatorActivity.class));
        }
        if (view.getId()==R.id.MDev){
            AlertDialog.Builder builder=new AlertDialog.Builder(MoreActivity.this);
            View mView=getLayoutInflater().inflate(R.layout.developer_dialog,null);
            TextView name=(TextView)mView.findViewById(R.id.devName);
            TextView sign=(TextView)mView.findViewById(R.id.devSign);
            TextView socialT=(TextView)mView.findViewById(R.id.devSocialT);

            ImageButton fb=(ImageButton)mView.findViewById(R.id.devFacebook);
            ImageButton insta=(ImageButton)mView.findViewById(R.id.devInsta);
            ImageButton twitter=(ImageButton)mView.findViewById(R.id.devTwitter);

            fb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.github.com/RajeshCypher")));
                }
            });

            insta.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/call_me_rajesh")));
                }
            });

            twitter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.twitter.com/its__rajesh")));
                }
            });

            name.setTypeface(productBold);
            sign.setTypeface(sans);
            socialT.setTypeface(segoe);

            builder.setView(mView);
            AlertDialog dialog=builder.create();
            dialog.show();
        }
        if (view.getId()==R.id.MFeedback||view.getId()==R.id.MFeedbackT){
            AlertDialog.Builder builder=new AlertDialog.Builder(MoreActivity.this);
            View mView=getLayoutInflater().inflate(R.layout.feedback_dialog,null);
            TextView head=(TextView)mView.findViewById(R.id.feedback_head);
            TextView tag=(TextView)mView.findViewById(R.id.feedback_tag);
            TextView rTitle=(TextView)mView.findViewById(R.id.ratingTitle);
            head.setTypeface(productBold);
            tag.setTypeface(segoe);
            rTitle.setTypeface(sans);
            final EditText editText=(EditText)mView.findViewById(R.id.feedback_et);
            final SmileRating ratingBar=(SmileRating) mView.findViewById(R.id.stars);
            final AVLoadingIndicatorView ballSpin=(AVLoadingIndicatorView)mView.findViewById(R.id.ballSpin);

            final Button submit=(Button)mView.findViewById(R.id.feedback_submit);
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String value=editText.getText().toString().trim();
                    String number=Integer.toString(ratingBar.getRating());
                    if (!value.equals("")){
                        submit.setVisibility(View.GONE);
                        ballSpin.show();
                        editText.setFocusable(false);
                        editText.setFocusableInTouchMode(false);
                        editText.setClickable(false);
                        submit.setClickable(false);
                        String email=user.getEmail();
                        email=email.replace("@","A");
                        email=email.replace(".","D");
                        databaseReference= FirebaseDatabase.getInstance().getReference("Feedback").child(email);
                        //Feedback feedback=new Feedback(value,number);
                        databaseReference.child("feedback").setValue(value);
                        databaseReference.child("rating").setValue(number).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Toasty.success(MoreActivity.this,"We have taken your feedback!",Toast.LENGTH_LONG,true).show();
                                    ballSpin.hide();
                                    submit.setVisibility(View.VISIBLE);
                                    editText.setText("");
                                    editText.setFocusableInTouchMode(true);
                                    editText.setFocusable(true);
                                    editText.setClickable(true);
                                    submit.setClickable(true);
                                }
                                else{
                                    Toasty.error(MoreActivity.this,"Couldn't send feedback!",Toast.LENGTH_LONG,true).show();
                                    ballSpin.hide();
                                    submit.setVisibility(View.VISIBLE);
                                    editText.setFocusableInTouchMode(true);
                                    editText.setFocusable(true);
                                    editText.setClickable(true);
                                    submit.setClickable(true);
                                }
                            }
                        });
                    }
                    else{
                        Toasty.error(MoreActivity.this,"Please fill all fields!",Toast.LENGTH_LONG,true).show();
                        }
                }
            });

            builder.setView(mView);
            AlertDialog dialog=builder.create();
            dialog.show();
        }
    }

    public class Feedback{
        String feedback,rating;

        public Feedback(){

        }

        public Feedback(String feedback, String rating) {
            this.feedback = feedback;
            this.rating = rating;
        }
    }
}
