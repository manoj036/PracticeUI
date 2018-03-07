package com.example.manojkumar.practiceui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Manoj Kumar on 05-03-2018.
 */

public class DateFragment extends Fragment {
//    public static DateClass date = null;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Bundle args= getArguments();
//        date=(DateClass)args.getSerializable("MyDate");
        return inflater.inflate(R.layout.date_fragment,container,false);
    }
}