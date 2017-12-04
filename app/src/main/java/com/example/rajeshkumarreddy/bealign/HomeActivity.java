package com.example.rajeshkumarreddy.bealign;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;

import es.dmoral.toasty.Toasty;
import me.toptas.fancyshowcase.FancyShowCaseView;
import me.toptas.fancyshowcase.OnViewInflateListener;
import ru.dimorinny.showcasecard.ShowCaseView;
import ru.dimorinny.showcasecard.position.ShowCasePosition;
import ru.dimorinny.showcasecard.position.TopLeft;
import ru.dimorinny.showcasecard.position.TopRight;
import ru.dimorinny.showcasecard.radius.Radius;

public class HomeActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle mToggle;
    NavigationView navigationView;
    BottomBar bottomBar;
    FirebaseAuth firebaseAuth;
    FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //showTooltip(new TopRight());

        authStateListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser()==null){
                    startActivity(new Intent(HomeActivity.this,LoginActivity.class));
                    finish();
                }
            }
        };

        drawerLayout=(DrawerLayout) findViewById(R.id.drawer);
        mToggle=new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView=(NavigationView)findViewById(R.id.navigation_view);
        firebaseAuth=FirebaseAuth.getInstance();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId()==R.id.SNProfile){
                    startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
                    drawerLayout.closeDrawers();
                }
                if (item.getItemId()==R.id.SNChat){

                    drawerLayout.closeDrawers();
                }
                if (item.getItemId()==R.id.SNAbout){
                    startActivity(new Intent(HomeActivity.this,AboutActivity.class));
                    drawerLayout.closeDrawers();
                }
                if (item.getItemId()==R.id.SNTimetable){
                    startActivity(new Intent(HomeActivity.this,TimetableActivity.class));
                    drawerLayout.closeDrawers();
                }
                if (item.getItemId()==R.id.SNTour){

                    drawerLayout.closeDrawers();
                }
                if (item.getItemId()==R.id.SNMore){
                    startActivity(new Intent(HomeActivity.this,MoreActivity.class));
                    drawerLayout.closeDrawers();
                }
                if (item.getItemId()==R.id.SNSignout){
                    firebaseAuth.signOut();
                    drawerLayout.closeDrawers();
                    finish();
                    Toasty.success(HomeActivity.this,"Successfully logged out!",Toast.LENGTH_LONG,true).show();
                }

                return false;
            }
        });

        bottomBar=BottomBar.attach( this,savedInstanceState);
        bottomBar.setItemsFromMenu(R.menu.bottom_navigation, new OnMenuTabClickListener() {
            @Override
            public void onMenuTabSelected(int menuItemId) {
                if (menuItemId==R.id.BNAcademics){
                    AcademicsFragment f=new AcademicsFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,f).commit();
                }
                else  if (menuItemId==R.id.BNAttendance){
                    AttendanceFragment f=new AttendanceFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,f).commit();
                }
                else if (menuItemId==R.id.BNEvents){
                    EventsFragment f=new EventsFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,f).commit();
                }
                else if (menuItemId==R.id.BNFeed){
                    NewsFeedFragment f=new NewsFeedFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,f).commit();
                }
                else if (menuItemId==R.id.BNNotifications){
                    NotificationsFragment f=new NotificationsFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,f).commit();
                }
            }

            @Override
            public void onMenuTabReSelected(int menuItemId) {

            }
        });

        bottomBar.mapColorForTab(0,"#23283a");
        bottomBar.mapColorForTab(1,"#23283a");
        bottomBar.mapColorForTab(2,"#23283a");
        bottomBar.mapColorForTab(3,"#23283a");
        bottomBar.mapColorForTab(4,"#23283a");

    }

    private void showTooltip(ShowCasePosition position) {
        new ShowCaseView.Builder(this)
                .withTypedPosition(position)
                .withTypedRadius(new Radius(200F))
                .withContent("Tap here to post to News Feed")
                .build()
                .show(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.topmenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }
        if (item.getItemId()==R.id.TMNewFeed){
            startActivity(new Intent(HomeActivity.this,NewPostActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    //@Override
    //protected void onStart() {
      //  super.onStart();
        //NewsFeedFragment f=new NewsFeedFragment();
        //getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,f).commit();
    //}
}
