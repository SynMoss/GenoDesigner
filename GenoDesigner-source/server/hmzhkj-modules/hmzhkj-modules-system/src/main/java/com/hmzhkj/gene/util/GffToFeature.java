package com.hmzhkj.gene.util;


import com.hmzhkj.gene.constant.GenbankAnnotationKey;
import com.hmzhkj.gene.domain.Feature;
import com.hmzhkj.gene.model.Location;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GffToFeature {
    private static final Pattern FEATURE_LINE = Pattern.compile("^\\s*[^#\\s>]");
    public static List<Feature> convert(List<String> lineList, String type){
        List<Feature> featureList = new ArrayList<>();
        for (String s : lineList) {
            Matcher featureMatcher = FEATURE_LINE.matcher(s);
            if(!featureMatcher.find()){
                continue;
            }
            String[] arr =s.split("\t");
            String[] array = new String[arr.length];
            if(array.length<5){
                continue;
            }
            for (int i = 0; i < arr.length; i++) {
                if(".".equals(arr[i])||"".equals(arr[i])){
                    array[i] = null;
                }else{
                    array[i] = arr[i];
                }
            }
            if(array[0]==null){
                continue;
            }
            Feature feature = new Feature();
            feature.setSequenceName(unescape(array[0]));
            if(array[1]!=null){
                feature.setSource(unescape(array[1]));
            }
            if(array[2]!=null) {
                feature.setType(unescape(array[2]));
            }
            if(array[3]==null||array[4]==null){
                continue;
            }
            feature.setStart(Integer.valueOf(array[3]));
            feature.setEnd(Integer.valueOf(array[4]));
            Location l = new Location(feature.getStart(),feature.getEnd());
            List<Location> locationList = new ArrayList<>();
            locationList.add(l);
            feature.setLocations(locationList);
            if(array.length>6&&"+".equals(array[6])){
                feature.setStrand(1);
            }else{
                feature.setStrand(-1);
            }
            Map<String,List<String>> notes;
            if(array.length>8){
                notes = parseAttributes(array[8],type);
            }else{
                notes = new HashMap<>();
            }
            if(StringUtils.isNotEmpty(feature.getSource())){
                List<String> sourceList = new ArrayList<>();
                sourceList.add(feature.getSource());
                notes.put("source",sourceList);
            }
            feature.setNotes(notes);
            GenBankToJson.postProcessGenbankFeature(feature);
            featureList.add(feature);
        }
        return featureList;
    }

    public static void main(String[] args) {
        parseAttributes("gene_id \"D1866_RS00010\"; transcript_id \"\"; db_xref \"GeneID:42778071\"; gbkey \"Gene\"; gene \"glgX\"; gene_biotype \"protein_coding\"; locus_tag \"D1866_RS00010\"; old_locus_tag \"D1866_00010\"; ","gtf");
    }
    private static Map<String,List<String>> parseAttributes(String attrString,String type){
        if(StringUtils.isEmpty(attrString)){
            return null;
        }
        Map<String,List<String>> notes = new HashMap<>();
        String[] array =attrString.split(";");
        for (String a : array) {
            String regex = "=";
            if("gtf".equals(type)){
                regex = " ";
                a = a.trim();
                a = a.replaceAll("\"","");
            }
            String[] nv = a.split(regex, 2);
            if(nv.length!=2){
                continue;
            }
            if(StringUtils.isEmpty(nv[0]) || StringUtils.isEmpty(nv[1])){
                continue;
            }
            String key = nv[0].trim();
            List<String> list ;
            if(notes.containsKey(key)){
                list = notes.get(key);
            }else {
                list = new ArrayList<>();
                notes.put(key,list);
            }
            for (String s : nv[1].split(",")) {
                list.add(unescape(s.trim()));
            }
        }
        return notes;
    }
     
    private static String unescape(String str) {
         Pattern pattern = Pattern.compile("%([0-9A-Fa-f]{2})");
        Matcher matcher =  pattern.matcher(str);
        while (matcher.find()){
            String s = matcher.group();
            str = str.replaceAll(s,String.valueOf ((char)Integer.parseInt(matcher.group(1),16)));
        }
        return str;
    }
}
