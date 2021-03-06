package com.example.manojkumar.practiceui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.example.manojkumar.practiceui.Fragments.EnvironmentFragment;
import com.example.manojkumar.practiceui.Fragments.HeartRateFragment;
import com.example.manojkumar.practiceui.Fragments.RespirationFragment;
import com.example.manojkumar.practiceui.Fragments.SleepSummaryFragment;

import java.util.Calendar;

import static com.example.manojkumar.practiceui.SleepSummaryActivity.daysData;

/**
 * Created by Manoj Kumar on 06-03-2018.
 */

public class WeekSummaryPagerAdapter extends FragmentStatePagerAdapter {
    private static final String TAG = "PAGER_ADAPTER";
    private int current_day;
    private String tab_titles[]={"Sun","Mon","Tue","Wed","Thu","Fri","Sat"};
    public SleepSummaryFragment mSleepSummaryFragment =new SleepSummaryFragment();
    private String callActivity;

    WeekSummaryPagerAdapter(String callActivity,FragmentManager fm, Context context) {
        super(fm);
        this.callActivity=callActivity;
        Calendar calendar = Calendar.getInstance();
        current_day = calendar.get(Calendar.DAY_OF_WEEK);
    }

    @Override
    public Fragment getItem(int position) {
        Log.d(TAG, "getItem: yolo");
        switch (callActivity) {
            case "Sleep":
                return SleepSummaryFragment.newInstance(position, daysData);
            case "Respiration":
                return RespirationFragment.newInstance(position, daysData);
            case "HeartRate":
                return HeartRateFragment.newInstance(position, daysData);
            case "Environment":
                return EnvironmentFragment.newInstance(position,daysData);
            default:
                return null;
        }
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        return 7;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        int val=(current_day+position)%7;
        return tab_titles[val];
    }
}
