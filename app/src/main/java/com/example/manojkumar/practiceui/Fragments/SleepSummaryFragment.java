package com.example.manojkumar.practiceui.Fragments;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.manojkumar.practiceui.DayData;
import com.example.manojkumar.practiceui.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;

/**
 * Created by Manoj Kumar on 05-03-2018.
 */

public class SleepSummaryFragment extends Fragment {

    private static final String ARG_PAGE="ARG_PAGE";
    private static final String TAG = "Date Fragment";
    private static DayData[] days_data;
    private static DayData pdayData,dayData;
    private int mPage;
    View[] mView=new View[7];

    public static SleepSummaryFragment newInstance(int page, DayData[] val){
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE,page);
        SleepSummaryFragment fragment=new SleepSummaryFragment();
        fragment.setArguments(args);
        days_data=val;
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage=getArguments().getInt(ARG_PAGE);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if(mView[mPage]==null){
            View view=inflater.inflate(R.layout.sleep_summary_fragment,container,false);
            dayData=days_data[mPage];
        EditFragment(view);
        mView[mPage]=view;
        }
        return mView[mPage];
    }

    @SuppressLint("SetTextI18n")
    private void EditFragment(View view) {
        ProgressBar progressBar=view.findViewById(R.id.sleepScorePB);
        TextView sleepScore=view.findViewById(R.id.sleep_score);
        TextView number_of_woke_up=view.findViewById(R.id.number_of_woke_up);
        TextView number_of_apnea_epoch=view.findViewById(R.id.number_of_apnea_epoch);
        TextView snore_time=view.findViewById(R.id.snore_time);
        TextView time_took=view.findViewById(R.id.time_took);
        TextView fell_asleep_hr=view.findViewById(R.id.fell_asleep_hr);
        TextView fell_asleep_min=view.findViewById(R.id.fell_asleep_min);
        TextView woke_up_hr=view.findViewById(R.id.woke_up_hr);
        TextView woke_up_min=view.findViewById(R.id.woke_up_min);
        TextView total_slept_hr=view.findViewById(R.id.total_slept_hr);
        TextView total_slept_min=view.findViewById(R.id.total_slept_min);
        TextView tv_greeting=view.findViewById(R.id.tv_greeting);

        if(days_data[mPage]!=null) {
            Log.d(TAG, "EditFragment: Here");
            sleepScore.setText(String.valueOf(dayData.getSleep_score()));

            if (dayData.getSleep_score() >= 80) {
                progressBar.setProgressDrawable(getResources().getDrawable(R.drawable.green_progress_drawable));
                sleepScore.setTextColor(Color.parseColor("#00ff00"));
                tv_greeting.setText("Your score is better than 90% of SleepDoc+ users");
            } else if (dayData.getSleep_score() >= 60) {
                progressBar.setProgressDrawable(getResources().getDrawable(R.drawable.yellow_progress_drawable));
                sleepScore.setTextColor(Color.parseColor("#ffff00"));
                tv_greeting.setText("Your score is better than 60% of SleepDoc+ users");
            } else {
                progressBar.setProgressDrawable(getResources().getDrawable(R.drawable.red_progress_drawable));
                sleepScore.setTextColor(Color.parseColor("#ff0000"));
                tv_greeting.setText("Your score is better than 30% of SleepDoc+ users");
            }
            DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();

            ObjectAnimator objectAnimator = ObjectAnimator.ofInt(progressBar, "progress", 0, dayData.getSleep_score() * 100);
            objectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
            objectAnimator.setDuration(1500);
            objectAnimator.start();

            NumberFormat f = new DecimalFormat("00");

            number_of_woke_up.setText(String.valueOf(dayData.getNumber_of_woke_up()));
            number_of_apnea_epoch.setText(String.valueOf(dayData.getNumber_of_apnea_epoch()));
            snore_time.setText(String.valueOf(dayData.getSnore_time()));
            time_took.setText(String.valueOf(dayData.getTime_took()));
            Calendar calendar = Calendar.getInstance();

            calendar.setTimeInMillis(dayData.getSleep_time() * 1000);
            fell_asleep_hr.setText(String.valueOf(calendar.get(Calendar.HOUR)));
            fell_asleep_min.setText(String.valueOf(f.format(calendar.get(Calendar.MINUTE))));

            calendar.setTimeInMillis(dayData.getWoke_up_time() * 1000);
            woke_up_hr.setText(String.valueOf(calendar.get(Calendar.HOUR)));
            woke_up_min.setText(String.valueOf(f.format(calendar.get(Calendar.MINUTE))));

            total_slept_hr.setText(String.valueOf((int) (dayData.getTotal_sleep() / 60 / 60)));
            total_slept_min.setText(String.valueOf(f.format((dayData.getTotal_sleep() / 60) % 60)));

        }
    }
}