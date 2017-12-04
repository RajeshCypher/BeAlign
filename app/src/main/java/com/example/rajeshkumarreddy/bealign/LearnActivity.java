package com.example.rajeshkumarreddy.bealign;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class LearnActivity extends AppCompatActivity {
    TextView qLogin,aLogin,qRegister,aRegister,Title;
    Typeface productBold,segoe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn);

        Title=(TextView)findViewById(R.id.solutions);
        qLogin=(TextView)findViewById(R.id.qLogin);
        aLogin=(TextView)findViewById(R.id.aLogin);
        qRegister=(TextView)findViewById(R.id.qRegister);
        aRegister=(TextView)findViewById(R.id.aRegister);

        productBold=Typeface.createFromAsset(getAssets(),"fonts/productsans/ProductSansBold.ttf");
        segoe=Typeface.createFromAsset(getAssets(),"fonts/segoeui.ttf");

        Title.setTypeface(productBold);
        qLogin.setTypeface(productBold);
        aLogin.setTypeface(segoe);
        qRegister.setTypeface(productBold);
        aRegister.setTypeface(segoe);

        findViewById(R.id.whatsapp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PackageManager pm=getPackageManager();
                try {
                    PackageInfo info=pm.getPackageInfo("com.whatsapp",PackageManager.GET_META_DATA);
                    Intent i = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:+917092962649"));
                    i.setPackage("com.whatsapp");
                    startActivity(i);

                } catch (PackageManager.NameNotFoundException e) {
                    Toast.makeText(LearnActivity.this, "Whatsapp not found in your phone", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
