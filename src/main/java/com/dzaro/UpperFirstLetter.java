package com.dzaro;

public class UpperFirstLetter {
    public static String upper(String word) {
        return word.substring(0,1).toUpperCase() + word.substring(1).toLowerCase();
    }
}
