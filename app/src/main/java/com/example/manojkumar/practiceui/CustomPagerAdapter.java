package com.example.manojkumar.practiceui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;

/**
 * Created by Manoj Kumar on 06-03-2018.
 */

public class CustomPagerAdapter extends FragmentPagerAdapter {
//    private DateClass dateClass=new DateClass();
    public DateFragment dateFragment=new DateFragment();
    public CustomPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new DateFragment();
            case 1:
                return new DateFragment();
            case 2:
                return new DateFragment();
            case 3:
                return new DateFragment();
            case 4:
                return new DateFragment();
            case 5:
                return new DateFragment();
            case 6:
                return new DateFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 7;
    }
}

//package com.example.manojkumar.practiceui;

//        import android.os.Bundle;
//        import android.support.annotation.NonNull;
//        import android.support.v4.app.Fragment;
//        import android.support.v4.app.FragmentManager;
//        import android.support.v4.app.FragmentPagerAdapter;
//        import android.support.v4.view.PagerAdapter;
//        import android.view.View;
//
///**
// * Created by Manoj Kumar on 06-03-2018.
// */
//
//public class CustomPagerAdapter extends FragmentPagerAdapter {
//    //    private DateClass dateClass=new DateClass();
//    public DateFragment dateFragment=new DateFragment();
//    public CustomPagerAdapter(FragmentManager fm) {
//        super(fm);
//    }
//    Bundle args=new Bundle();
//
//    @Override
//    public Fragment getItem(int position) {
//        switch (position) {
//            case 0:
////                dateClass.setDay(DateClass.days.Sunday);
//            case 1:
////                dateClass.setDay(DateClass.days.Monday);
//            case 2:
////                dateClass.setDay(DateClass.days.Tuesday);
//            case 3:
////                dateClass.setDay(DateClass.days.Wednesday);
//            case 4:
////                dateClass.setDay(DateClass.days.Thursday);
//            case 5:
////                dateClass.setDay(DateClass.days.Friday);
//            case 6:
////                dateClass.setDay(DateClass.days.Saturday);
//        }
////        args.putSerializable("MyDate",dateClass);
////        dateFragment.setArguments(args);
//        return dateFragment;
//    }
//
//    @Override
//    public int getCount() {
//        return 7;
//    }
//}
//
