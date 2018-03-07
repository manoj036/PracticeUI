package com.example.manojkumar.practiceui;

import android.animation.ObjectAnimator;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.ActionMenuItemView;
import android.support.v7.widget.ActionMenuView;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.msquare.widget.mprogressbar.MProgressBar;

import static java.lang.Math.min;

public class MainActivity extends AppCompatActivity{

    private DrawerLayout drawerLayout;
    Boolean click=false;
    private TextView currentUser;
    private ViewPager viewPager;
    private CustomPagerAdapter pagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currentUser=findViewById(R.id.currentUser);
        android.support.v7.widget.Toolbar toolbar= findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        actionBar.setDisplayShowTitleEnabled(false);

        viewPager=findViewById(R.id.pager);
        pagerAdapter=new CustomPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);

        ProgressBar progressBar =  findViewById(R.id.progressBar);
        ObjectAnimator animation = ObjectAnimator.ofInt(progressBar, "progress", 0, 7000); // see this max value coming back here, we animale towards that value
        animation.setDuration (2000); //in milliseconds
        animation.setInterpolator (new DecelerateInterpolator());
        animation.start ();


        drawerLayout=findViewById(R.id.drawerLayout);
        BottomNavigationViewEx bnve = (BottomNavigationViewEx) findViewById(R.id.bottomBar);
        bnve.enableAnimation(false);
        bnve.enableShiftingMode(false);
        bnve.enableItemShiftingMode(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.users,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.user1:
                currentUser.setText(item.getTitle());
                return true;
            case R.id.user2:
                currentUser.setText(item.getTitle());
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void image_button(View view) {
        ImageButton imageButton = (ImageButton) view;
        if(click) {
            imageButton.setImageResource(R.drawable.ic_menu_white_24dp);
        }else {
            imageButton.setImageResource(R.drawable.ic_menu_black_24dp);
        }
        click=!click;
    }
}