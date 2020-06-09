package com.cacao.server.utlity;

import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Stream;

public class Id {

    private Id(){}

    private static final Random RANDOM = new Random();
    private static final StringBuilder sb = new StringBuilder();
    private static Character[] chars;
    //48-57, 65 - 90, 97 -122

    public static String generateOne(int size){
        if(chars == null) {
            setupChars();
        }
        sb.setLength(0);
        for (int i = 0; i < size; i++) {
            sb.append(randomChar());
        }
        return sb.toString();
    }

    private static char randomChar(){
        return chars[RANDOM.nextInt(chars.length)];
    }

    private static void setupChars() {
        var list = new ArrayList<Character>();
        list.add('_');
        list.add('-');
        for (int i = 48; i <= 57; i++) {
            list.add((char)i);
        }
        for (int i = 65; i <= 90; i++) {
            list.add((char)i);
        }
        for (int i = 97; i <= 122; i++) {
            list.add((char)i);
        }
        chars = list.toArray(Character[]::new);
    }
}
