package com.hmzhkj.gene.util;

import com.hmzhkj.gene.domain.Feature;
import io.swagger.models.auth.In;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class SeqUtil {
    private static Map<String,String> map = new ConcurrentHashMap<>();
    public static String reverseComplement(String seq){
        if(map.isEmpty()){
            map.put("A","T");
            map.put("T","A");
            map.put("C","G");
            map.put("G","C");
            map.put("N","N");
            map.put("R","Y");
            map.put("Y","R");
            map.put("M","K");
            map.put("K","M");
            map.put("S","S");
            map.put("W","W");
            map.put("B","V");
            map.put("V","B");
            map.put("D","H");
            map.put("H","D");
        }
        StringBuilder sb = new StringBuilder(seq.toUpperCase());
        sb.reverse();
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < sb.length(); i++) {
            String letter = String.valueOf(sb.charAt(i));
            if(map.containsKey(letter)){
                str.append(map.get(letter));
            }else{
                str.append(letter);
            }

        }
        return str.toString();
    }
    public static String getFeatureName(Feature feature){
        if(feature.getNotes()==null||feature.getNotes().isEmpty()){
            return "Untitled Feature";
        }
        Map<String, List<String>> notes = feature.getNotes();
        String name;
        if (notes.containsKey("ID")) {
            name = notes.get("ID").get(0);
        }else if (notes.containsKey("Name")) {
            name = notes.get("Name").get(0);
        }else if(notes.containsKey("locus_tag")) {
            name = notes.get("locus_tag").get(0);
        } else if (notes.containsKey("label")) {
            name = notes.get("label").get(0);
        } else if (notes.containsKey("gene")) {
            name = notes.get("gene").get(0);
        } else if (notes.containsKey("ApEinfo_label")) {
            name = notes.get("ApEinfo_label").get(0);
        } else if (notes.containsKey("name")) {
            name = notes.get("name").get(0);
        } else if (notes.containsKey("organism")) {
            name = notes.get("organism").get(0);
        } else  if (notes.containsKey("note")) {
                         name = notes.get("note").get(0);
        } else {
            name = "Untitled Feature";
        }
        if(name.length()>100){
            name = name.substring(0,100);
        }
        return name;
    }
}
