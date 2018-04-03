package com.example.manojkumar.practiceui;

    import android.content.ActivityNotFoundException;
    import android.content.Intent;
    import android.graphics.Bitmap;
    import android.net.Uri;
    import android.os.Handler;
    import android.support.design.widget.TabLayout;
    import android.support.v4.content.FileProvider;
    import android.support.v4.view.ViewPager;
    import android.support.v4.widget.DrawerLayout;
    import android.os.Bundle;
    import android.util.DisplayMetrics;
    import android.util.Log;
    import android.view.View;
    import android.widget.ListView;
    import android.widget.ShareActionProvider;
    import android.widget.TextView;
    import android.widget.Toast;

    import com.google.firebase.auth.FirebaseAuth;
    import com.google.firebase.database.DataSnapshot;
    import com.google.firebase.database.DatabaseError;
    import com.google.firebase.database.DatabaseReference;
    import com.google.firebase.database.FirebaseDatabase;
    import com.google.firebase.database.ValueEventListener;

    import java.io.File;
    import java.io.FileOutputStream;
    import java.util.Calendar;
    import java.util.Date;
    import java.util.TimeZone;

public class SleepSummaryActivity extends BaseActivity{

    private static final String TAG = "Sleep Summary Activity";
    private DrawerLayout drawerLayout;
    Boolean click=false;

    public static DayData daysData[];
    private ListView listView;
    private ViewPager viewPager;
    private DatabaseReference[] pager_data_reference = new DatabaseReference[7];

    private ShareActionProvider mShareActionProvider;

    @Override
    protected void onResume() {
        CURRENT_BTM_ACTIVITY =R.id.sleep_summary_button;
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView text = findViewById(R.id.text);

        TextView title=findViewById(R.id.toolbar_title);
        title.setText(R.string.sleep_summary);

        DataAccessObject dataAccessObject=DataAccessObject.getInstance();
        DayData[] data = dataAccessObject.getArray();

        daysData=new DayData[7];

        viewPager = findViewById(R.id.pager);
        WeekSummaryPagerAdapter pagerAdapter = new WeekSummaryPagerAdapter("Sleep", getSupportFragmentManager(), this);

        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(7);
        viewPager.setOffscreenPageLimit(7);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                int diff = position-6;
                TextView dateText =findViewById(R.id.todays_date);
                Calendar calendar=Calendar.getInstance();
                calendar.add(Calendar.DATE,diff);
                Date d=calendar.getTime();

                String day=(String) android.text.format.DateFormat.format("EEEE",d);
                String month=(String) android.text.format.DateFormat.format("MMM",d);
                String date=(String) android.text.format.DateFormat.format("dd",d);
                String date_format =day+", "+month+" "+date;
                dateText.setText(date_format);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        TabLayout tabLayout = findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        float dpHeight = displayMetrics.heightPixels / displayMetrics.density;
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        Log.d(TAG, "onCreate: height="+dpHeight);
        Log.d(TAG, "onCreate: width="+dpWidth);


        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();

        handler.post(FireBasePushData);
        UpdatePager();
    }

    private void UpdatePager() {
        for(int i=0;i<7;i++){
            Calendar calendar=Calendar.getInstance(TimeZone.getTimeZone("UTC"));
            calendar.add(Calendar.DATE,-i);
            Date d=calendar.getTime();
            calendar.setTime(d);
            calendar.set(Calendar.HOUR,0);
            calendar.set(Calendar.MINUTE,0);
            calendar.set(Calendar.SECOND,0);
            calendar.set(Calendar.MILLISECOND,0);
            calendar.set(Calendar.AM_PM,Calendar.AM);

            Long startOfDayTimeStamp=calendar.getTimeInMillis()/1000;

            daysData[i]=new DayData();
            pager_data_reference[i]=FirebaseDatabase.getInstance().getReference()
                    .child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .child(startOfDayTimeStamp.toString()).child("User Health Data");

            int finalI = i;
            pager_data_reference[i].addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    daysData[finalI].setSnore_time(dataSnapshot.child("snore_time").getValue(Long.class));
                    daysData[finalI].setNumber_of_apnea_epoch(dataSnapshot.child("number_of_apnea_epoch").getValue(Integer.class));
                    daysData[finalI].setNumber_of_woke_up(dataSnapshot.child("number_of_woke_up").getValue(Integer.class));
                    daysData[finalI].setTime_took(dataSnapshot.child("time_took").getValue(Long.class));
                    daysData[finalI].setWoke_up_time(dataSnapshot.child("woke_up_time").getValue(Long.class));
                    daysData[finalI].setSleep_time(dataSnapshot.child("sleep_time").getValue(Long.class));
                    daysData[finalI].setTotal_sleep(dataSnapshot.child("total_sleep").getValue(Long.class));
                    daysData[finalI].setSleep_score(dataSnapshot.child("sleep_score").getValue(Integer.class));

                    viewPager.getAdapter().notifyDataSetChanged();
                    Log.d(TAG, "onDataChange: "+daysData[finalI].getSnore_time());
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    private Handler handler=new Handler();
    private Runnable FireBasePushData = () -> {
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
        for(int i=0;i<7;i++) {
            Calendar calendar=Calendar.getInstance(TimeZone.getTimeZone("UTC"));
            calendar.add(Calendar.DATE,-i);
            Date d=calendar.getTime();
            calendar.setTime(d);
            calendar.set(Calendar.HOUR,0);
            calendar.set(Calendar.MINUTE,0);
            calendar.set(Calendar.SECOND,0);
            calendar.set(Calendar.MILLISECOND,0);
            calendar.set(Calendar.AM_PM,Calendar.AM);

            Long startOfDayTimeStamp=calendar.getTimeInMillis()/1000;
            myRef.child("Users")
                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .child(startOfDayTimeStamp.toString())
                    .child("User Health Data")
                    .setValue(DataAccessObject.getInstance().getArray()[6-i]);
        }
    };

    public Bitmap getScreenShot() {
        View view = getWindow().getDecorView().findViewById(android.R.id.content);
        View screenView = view.getRootView();
        screenView.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(screenView.getDrawingCache());
        screenView.setDrawingCacheEnabled(false);
        return bitmap;
    }

    private void shareImage(File file){
        Uri uri = FileProvider.getUriForFile(this,this.getApplicationContext().getPackageName()+ ".my.package.name.provider",file);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("image/*");

        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "");
        intent.putExtra(android.content.Intent.EXTRA_TEXT, "");
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        try {
            startActivity(Intent.createChooser(intent, "Share Screenshot"));
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "No App Available", Toast.LENGTH_SHORT).show();
        }
    }

    public void ShareButton(View view) {
        Log.d(TAG, "ShareButton: ");
        View screenView = getWindow().getDecorView().findViewById(android.R.id.content).getRootView();
        screenView.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(screenView.getDrawingCache());
        screenView.setDrawingCacheEnabled(false);
        try {
            File file = new File(this.getExternalCacheDir(),"logicchip.png");
            FileOutputStream fOut = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();
            final Intent intent = new Intent(android.content.Intent.ACTION_SEND);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(Intent.EXTRA_STREAM,GenericFileProvider.getUriForFile(this,BuildConfig.APPLICATION_ID+".fileprovider",file));
            intent.setType("image/png");
            startActivity(Intent.createChooser(intent, "Share image via"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getContentViewId() {
        return R.layout.sleep_summary_layout;
    }

    @Override
    public int getNavigationMenuItemId() {
        return R.id.sleep_summary_button;
    }

}