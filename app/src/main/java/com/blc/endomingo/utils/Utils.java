package com.blc.endomingo.utils;

/**
 * Created by Pablo on 01/01/2017.
 */

public class Utils {

    public static String formatTime(long time){
        long second = (time / 1000) % 60;
        long minute = (time / (1000 * 60)) % 60;
        long hour = (time / (1000 * 60 * 60)) % 24;
        return String.format("%02d:%02d:%02d", hour, minute, second);
    }
}
