package com.hmzhkj.gene.util;

import com.hmzhkj.gene.domain.Sequence;

import java.util.ArrayList;
import java.util.List;

public class JsonToFasta {
    public static List<String> convert(Sequence sequence){
        List<String> lineList = new ArrayList<>();
        String fastaString = ">" + sequence.getName()+"|";
        fastaString +="|"+sequence.getBpLength();
        fastaString += Boolean.TRUE.equals(sequence.getCircular())?"circular":"linear";
        lineList.add(fastaString);
        char[] charArray = sequence.getSequence().toCharArray();
        for (int i = 0; i < charArray.length; i+=80) {
            StringBuilder sb = new StringBuilder();
            for (int j = i; j < i+80 && j<charArray.length; j++) {
                sb.append(charArray[j]);
            }
            lineList.add(sb.toString());
        }
        return lineList;
    }
}
