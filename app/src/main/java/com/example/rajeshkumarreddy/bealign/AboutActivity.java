package com.example.rajeshkumarreddy.bealign;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.sunfusheng.marqueeview.MarqueeView;
import com.tapadoo.alerter.Alerter;

import java.util.ArrayList;
import java.util.List;

import ru.whalemare.sheetmenu.SheetMenu;

public class AboutActivity extends AppCompatActivity {
    Typeface productBold, sans, segoe;
    TextView appTag, appName, appDesc, appVersion, contact, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        MarqueeView marqueeView = (MarqueeView) findViewById(R.id.marqueeView);
        productBold = Typeface.createFromAsset(getAssets(), "fonts/productsans/ProductSansBold.ttf");
        sans = Typeface.createFromAsset(getAssets(), "fonts/opensans/OpenSans-Regular.ttf");
        segoe = Typeface.createFromAsset(getAssets(), "fonts/segoeui.ttf");

        appName = (TextView) findViewById(R.id.appName);
        appTag = (TextView) findViewById(R.id.appTag);
        appDesc = (TextView) findViewById(R.id.appDesc);
        appVersion = (TextView) findViewById(R.id.appVersion);
        contact = (TextView) findViewById(R.id.contact);
        email = (TextView) findViewById(R.id.email);

        appName.setTypeface(productBold);
        appTag.setTypeface(sans);
        appDesc.setTypeface(segoe);
        appVersion.setTypeface(sans);
        contact.setTypeface(sans);
        email.setTypeface(sans);

        List<String> listText = new ArrayList<>();
        listText.add("To SHARE KNOWLEDGE");
        listText.add("To MAKE LIFE EASY");
        listText.add("To UNITE");
        listText.add("To EDUCATE");
        listText.add("To HELP");
        listText.add("To ENGAGE");
        listText.add("To MAKE SUCCESS");
        listText.add("To INTERACT");

        marqueeView.startWithList(listText);


        findViewById(R.id.sheet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SheetMenu.with(AboutActivity.this)
                        .setTitle("Contact Developer :")
                        .setMenu(R.menu.sheet_menu)
                        .setClick(new MenuItem.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem menuItem) {
                                if (menuItem.getItemId() == R.id.mail) {
                                    Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                                            "mailto", "rajeshvision777@gmail.com", null));
                                    intent.putExtra(Intent.EXTRA_SUBJECT, "Be Align Request");
                                    startActivity(Intent.createChooser(intent, "Complete action using"));
                                }

                                if (menuItem.getItemId() == R.id.call) {
                                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                                    callIntent.setData(Uri.parse("tel:+917092962649"));
                                    startActivity(callIntent);
                                }
                                if (menuItem.getItemId()==R.id.whats){
                                    PackageManager pm=getPackageManager();
                                    try {
                                        PackageInfo info=pm.getPackageInfo("com.whatsapp",PackageManager.GET_META_DATA);
                                        Intent i = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:+917092962649"));
                                        i.setPackage("com.whatsapp");
                                        startActivity(i);

                                    } catch (PackageManager.NameNotFoundException e) {
                                        Alerter.create(AboutActivity.this)
                                                .setBackgroundColor(R.color.error)
                                                .setIcon(R.drawable.ic_empty)
                                                .setText("Please intsall WhatsApp in your phone and try again.")
                                                .setTitle("WhatsApp not found!")
                                                .show();
                                    }
                                }

                                return false;
                            }
                        }).show();
            }
        });
    }


}
