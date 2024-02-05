package com.hmzhkj.gene.util;

import com.hmzhkj.gene.domain.Sequence;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class FastaToJson {
    public static Sequence convert(List<String> lineList){
        Sequence sequence = new Sequence();
        sequence.setCircular(false);
        sequence.setFeatures(new ArrayList<>());
        StringBuilder seq = new StringBuilder();
        for (String line : lineList) {
            line = line.trim();
            if(line.startsWith(">")||line.startsWith(";")){
                sequence.setName(parseTitle(line));
            }else if(line.endsWith("*")){
                                 seq.append(line, 0, line.length()-1);
                break;
            }else{
                seq.append(line);
            }
        }
        if(StringUtils.isEmpty(sequence.getName())){
            sequence.setName("Untitled Sequence");
        }
        sequence.setSequenceBuilder(seq);
        sequence.setBpLength(seq.length());
        sequence.setIsProtein(false);
        return sequence;
    }
    private static String parseTitle(String line){
        int index = line.indexOf("|");
        String name;
        if(index>-1){
            name = line.substring(1,index);

        }else{
            name = line.substring(1);
        }
                 int spaceIndex = name.indexOf(" ");
        if(spaceIndex>-1){
            name = name.substring(0,spaceIndex);
        }
        return name;
    }
}
