//package com.example.manojkumar.practiceui;
//
//import java.io.Serializable;
//
///**
// * Created by Manoj Kumar on 06-03-2018.
// */
//
//public class DateClass implements Serializable {
//    enum days{Sunday,Monday,Tuesday,Wednesday,Thursday,Friday,Saturday};
//    private days day;
//    private int sleepScore;
//
//    DateClass(){};
//    private void SleepScore(){
//        switch (day){
//            case Monday:
//                sleepScore=50;
//                break;
//            case Sunday:
//                sleepScore=10;
//                break;
//            case Friday:
//                sleepScore=20;
//                break;
//            case Tuesday:
//                sleepScore=70;
//                break;
//            case Saturday:
//                sleepScore=50;
//                break;
//            case Thursday:
//                sleepScore=30;
//                break;
//            case Wednesday:
//                sleepScore=90;
//                break;
//        }
//    }
//
//    public days getDay() {
//        return day;
//    }
//
//    void setDay(days day) {
//        this.day = day;
//        SleepScore();
//    }
//}
