package com.example.password;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PasswordGenerator {
    private static final char [] LETTERS = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
            'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
            'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
    private static final char [] NUMBERS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    private static final char [] SYMBOLS = {'-','*', '$', '_'};

    public PasswordGenerator(){}

    public static String Generate(){
        StringBuilder password = new StringBuilder();
        int lettersNum = 9;
        int numbersNum = (int) (Math.random() * 3);
        int symbolsNum = 12 - lettersNum - numbersNum;

        for(int i = 0; i< lettersNum ; i++){
            password.append(LETTERS[(int) (Math.random() * LETTERS.length)]);
        }
        for(int i = 0; i< numbersNum ; i++){
            password.append(NUMBERS[(int) (Math.random() * NUMBERS.length)]);
        }
        for(int i = 0; i< symbolsNum ; i++){
            password.append(SYMBOLS[(int) (Math.random() * SYMBOLS.length)]);
        }

        return shuffle(password.toString()).toString();
    }

    private static StringBuilder shuffle(String password){
        char firstLetter = password.toUpperCase().toCharArray()[0];

        List<Character> l = new ArrayList<>();
        for(char c :  password.toCharArray())
            l.add(c);
        Collections.shuffle(l);

        StringBuilder sb = new StringBuilder();
        sb.append(firstLetter);
        for(char c : l){
            if(c == firstLetter)
                continue;
            sb.append(c);}

        return sb;
    }
}
