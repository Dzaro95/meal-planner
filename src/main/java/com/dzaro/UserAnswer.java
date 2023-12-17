package com.dzaro;

import java.util.Scanner;

public class UserAnswer {
    Scanner scanner = new Scanner(System.in);

    public UserAnswer() {
    }

    public String userAnswerString() {
        String answer = scanner.nextLine();
        return answer;
    }
}
