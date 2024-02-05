package com.hmzhkj.gene.constant;

import java.util.regex.Pattern;

public class Validate {

    public static Pattern SEQUENCE_NAME_PATTERN = Pattern.compile("\\.[^/.]+$");
    public static void main(String[] args) {
            String line = " LOCUS       NT_187405                998 bp    DNA     linear   CON 26-MAR-2018";
        for (String s : line.split("[\\s]+")) {
         }
        char a = 'I';
        System.out.println("123".matches("^\\d+$"));
           }
}
