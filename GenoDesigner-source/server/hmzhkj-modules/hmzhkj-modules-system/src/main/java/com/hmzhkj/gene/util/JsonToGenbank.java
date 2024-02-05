package com.hmzhkj.gene.util;

import cn.hutool.core.date.DateUtil;
import com.hmzhkj.gene.domain.Feature;
import com.hmzhkj.gene.domain.Sequence;
import com.hmzhkj.gene.model.Location;
import com.hmzhkj.gene.model.ParsedOptions;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

 
public class JsonToGenbank {
    public static String[] MONTH_ABB = {"Jan","feb","Mar","Apr","May","Jun","Jul","Aug","Sept","Oct","Nov","Dec"};
    public static List<String> convert(Sequence serSeq){
        ParsedOptions options = new ParsedOptions();
        options.setInclusive1BasedStart(false);
        options.setInclusive1BasedEnd(false);
        if("protein".equals(serSeq.getSequenceTypeFromLocus())||"AA".equals(serSeq.getSequenceTypeFromLocus())){
            options.setIsProtein(true);
        }else{
            options.setIsProtein(false);
        }
        String content = null;
        List<String> lines = new ArrayList<>();
        lines.add(createGenbankLocus(serSeq,options));
        if(serSeq.getExtraLines()!=null && !serSeq.getExtraLines().isEmpty()){
            lines.addAll(serSeq.getExtraLines());
        }
        if(serSeq.getFeatures()!=null && !serSeq.getFeatures().isEmpty()){
            int longestFeatureTypeLength = 15;
            options.setFeaturePadLength(longestFeatureTypeLength+1);
            for (Feature feature : serSeq.getFeatures()) {
                if (feature.getType()!=null && feature.getType().length() > longestFeatureTypeLength) {
                    longestFeatureTypeLength = feature.getType().length();
                }
            }
            lines.add("FEATURES             Location/Qualifiers");
            for (Feature feature : serSeq.getFeatures()) {
                lines.addAll(featureToGenbankString(feature,options));
            }
        }
        lines.add("ORIGIN      ");
        char[] charArray = serSeq.getSequence().toCharArray();
        for (int i = 0; i < charArray.length; i+=60) {
            String ind = StringUtils.leftPad( String.valueOf(i + 1),  9);
            StringBuilder sb = new StringBuilder(ind);
            for (int j = i; j < i+60 && j<charArray.length; j++) {
                if(j%10==0){
                    sb.append(" ");
                }
                sb.append(charArray[j]);
            }
            if(sb.charAt(sb.length()-1)==' '){
                sb.deleteCharAt(sb.length()-1);
            }
            lines.add(sb.toString());
        }
        lines.add("//");
        return lines;
    }
    private static String createGenbankLocus(Sequence serSeq, ParsedOptions options){
        String tmp="",dnaType;
        if(options.getIsProtein()){
            dnaType = "";
        }else if("RNA".equals(serSeq.getSequenceTypeFromLocus())){
            dnaType = "RNA";
        }else{
            dnaType = "DNA";
        }
        Date now = new Date();
        DateUtil.month(now);
        String date = DateUtil.dayOfMonth(now)+"-"+MONTH_ABB[DateUtil.month(now)]+"-"+DateUtil.year(now);
        String line = StringUtils.rightPad("LOCUS",12);
        String nameToUse = serSeq.getName();
        line +=nameToUse;
        line += "       ";
        line +=StringUtils.leftPad(serSeq.getBpLength().toString(),11);
        line+=options.getIsProtein()?" aa " : " bp ";
        line+=StringUtils.leftPad(tmp,3);
        line+=StringUtils.leftPad(dnaType,6);
        if (!serSeq.getCircular()) {
            line += "     linear   "; //line += "        ";
        } else {
            line += "     circular   ";
        }
        line += " ";
        line += StringUtils.rightPad(serSeq.getGbDivision()==null?"SYN":serSeq.getGbDivision(),1);
        line+=" ";
        line+=date;
        return line;
    }

    private static List<String> featureToGenbankString(Feature feat,ParsedOptions options){
        List<String> lines = new ArrayList<>();
        String type = feat.getType()==null?"misc_feature":feat.getType();
        String line = "     "+StringUtils.rightPad(type,options.getFeaturePadLength());
        String locStr = "";
        for (int i = 0; i < feat.getLocations().size(); i++) {
            Location loc = feat.getLocations().get(i);
                         int start = loc.getStart()+1;
            int end = loc.getEnd()+1;
            locStr+=getProteinStart(start+(options.getInclusive1BasedStart()?1:0),options.getIsProtein());
            locStr+="..";
            locStr+=getProteinEnd(end+(options.getInclusive1BasedStart()?1:0),options.getIsProtein());
            if(i!=feat.getLocations().size()-1){
                locStr+=",";
            }
        }
        if(feat.getLocations().size()>1){
            locStr = "join("+locStr+")";
        }
        if(feat.getStrand().equals(-1)){
            locStr = "complement(" + locStr + ")";
        }
        lines.add(line+locStr);
        lines.add(featureNoteInDataToGenbankString("label",feat.getName()));
        if(feat.getNotes()!=null && !feat.getNotes().isEmpty()){
            feat.getNotes().forEach((key,value)->{
                for (String s : value) {
                    if(!"label".equals(key)){
                        lines.add(featureNoteInDataToGenbankString(key,s));
                    }
                }
            });
        }
        return lines;
    }
    private static String featureNoteInDataToGenbankString(String name,String value){
        return StringUtils.leftPad("/",22)+name+"=\""+value+"\"";
    }
    private static int getProteinStart(int val,boolean isProtein){
        if(!isProtein){
            return val;
        }
        return (int)Math.floor((val+2)/3);
    }
    private static int getProteinEnd(int val,boolean isProtein){
        if(!isProtein){
            return val;
        }
        return (int)Math.floor(val/3);
    }
}
