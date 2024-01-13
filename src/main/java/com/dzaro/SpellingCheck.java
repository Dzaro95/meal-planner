package com.dzaro;

import java.util.ArrayList;

public class SpellingCheck {
    public boolean checkWord(String s) {
        String check = s.replaceAll(",", "");

        if(s.contains(",,") || s.endsWith(",") || s.startsWith(",")) {
            return false;
        }

        if(check.isEmpty()) return false;
        if (check == null) // checks if the String is null {
            return false;
        int len = check.length();
        for (int i = 0; i < len; i++) {
            // checks whether the character is not a letter
            // if it is not a letter ,it will return false
            if ((Character.isLetter(check.charAt(i)) == false)) {
                return false;
            }
        }

        return true;
    }
    public ArrayList<String> deleteWhiteSpace(ArrayList<String> array) {
        ArrayList<String> arrayWithoutWhiteSpace = new ArrayList<>();
        for(String word : array) {
            arrayWithoutWhiteSpace.add(word.trim());
        }
        return arrayWithoutWhiteSpace;
    }

}
