package com.hmzhkj.gene.util;

import com.hmzhkj.gene.constant.GenbankAnnotationKey;
import com.hmzhkj.gene.domain.Feature;
import com.hmzhkj.gene.domain.Sequence;
import com.hmzhkj.gene.model.Location;
import com.hmzhkj.gene.model.ParsedOptions;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.hmzhkj.common.core.utils.StringUtils.trimLeft;

public class GenBankToJson {
    private static final String UNTITLED_SEQUENCE_NAME= "Untitled Sequence";
    private static final String[] GB_DIVISIONS={"PRI","ROD","MAM","VRT","INV","PLN","BCT","VRL","PHG","SYN","UNA","EST","PAT","STS","GSS","HTG","HTC","ENV","CON"};
    private static final Pattern PATTERN_CIRCULAR =Pattern.compile("circular",Pattern.CASE_INSENSITIVE);
    private static final Pattern PATTERN_LINEAR =Pattern.compile("linear",Pattern.CASE_INSENSITIVE);
    private static final Pattern PATTERN_AA =Pattern.compile("aa",Pattern.CASE_INSENSITIVE);
    private static final Pattern PATTERN_DS_DNA =Pattern.compile("ds-dna",Pattern.CASE_INSENSITIVE);
    private static final Pattern PATTERN_SS_DNA =Pattern.compile("ss-dna",Pattern.CASE_INSENSITIVE);
    private static final Pattern PATTERN_DNA =Pattern.compile("dna",Pattern.CASE_INSENSITIVE);
    private static final Pattern PATTERN_DATE =Pattern.compile("-[A-Z]{3}-");
    private static final Pattern PATTERN_BLANK_BEGIN =Pattern.compile("^\\s*");
    private static final Pattern FEATURE_NUMBER_PATTERN = Pattern.compile("\\d+");
    private static final Pattern PATTERN_NOTE= Pattern.compile("^[\\s]*/[\\w]+=[\\S]+");
    private static final Pattern PATTERN_COMPLEMENT= Pattern.compile("complement");
    public static Sequence convert(List<String> lineList){
        if(lineList==null||lineList.isEmpty()){
            return null;
        }
        ParsedOptions options = new ParsedOptions();
        options.setLastLineWasFeaturesTag(false);
        options.setLastLineWasLocation(false);
        options.setInclusive1BasedStart(false);
        options.setInclusive1BasedEnd(false);
        options.setIsProtein(false);
        options.setIsEnd(false);
        String LINETYPE = null;
        boolean hasFoundLocus = false;
        for (String line : lineList) {
            String key = getLineKey(line);
            String val = getLineVal(line);
            boolean isKey = isKeyword(line);
            if (GenbankAnnotationKey.LOCUS_TAG.equals(key)) {
                LINETYPE = key;
                hasFoundLocus = true;
            } else if (GenbankAnnotationKey.REFERENCE_TAG.equals(key)) {
                LINETYPE = key;
            } else if (GenbankAnnotationKey.FEATURES_TAG.equals(key)) {
                LINETYPE = key;
            } else if (GenbankAnnotationKey.ORIGIN_TAG.equals(key)) {
                LINETYPE = key;
            } else if (GenbankAnnotationKey.END_SEQUENCE_TAG.equals(key)) {
                LINETYPE = key;
            } else if (isKey == true) {
                LINETYPE = key;
            }
            if(!hasFoundLocus){
                return null;
            }
            if(StringUtils.isBlank(line)||";".equals(key)){
                continue;
            }
            switch (LINETYPE) {
                case GenbankAnnotationKey.LOCUS_TAG:
                                         Sequence result=parseLocus(line,options);
                    if(result==null){
                        return null;
                    }
                    break;
                case GenbankAnnotationKey.FEATURES_TAG:
                                         if (StringUtils.isEmpty(val)) {
                                                 break;
                    }
                    parseFeatures(line, key, val,options);
                    break;
                case GenbankAnnotationKey.ORIGIN_TAG:
                    parseOrigin(line, key,options);
                    break;
                case GenbankAnnotationKey.END_SEQUENCE_TAG:
                    endSeq(options);
                    break;
                default:
                    if("KEYWORDS".equals(LINETYPE)){
                        extractExtraLine(line,options);
                    }
            }
        }
        if(!options.getIsEnd()){
            endSeq(options);
        }
        return options.getSequence();
    }
    public static void extractExtraLine(String line,ParsedOptions options){
        if(options.getSequence()!=null){
            if(options.getSequence().getExtraLines()==null){
                options.getSequence().setExtraLines(new ArrayList<>());
            }
            options.getSequence().getExtraLines().add(line);
        }
    }
    public static void parseOrigin(String line,String key,ParsedOptions options){
        if (!GenbankAnnotationKey.ORIGIN_TAG.equals(key)) {
            String newLine = line.replaceAll("[\\s]*[0-9]*","");
            Integer bpLength = options.getSequence().getBpLength();
            bpLength+=newLine.length();
            options.getSequence().setBpLength(bpLength);
            options.getSequence().getSequenceBuilder().append(newLine);
        }
    }
         public static void endSeq(ParsedOptions options){
        if(options.getSequence()!=null && options.getSequence().getFeatures()!=null){
            for (Feature feature : options.getSequence().getFeatures()) {
                postProcessGenbankFeature(feature);
            }
        }
        options.setIsEnd(true);
    }
    public static Feature postProcessGenbankFeature(Feature feat){
        String name = SeqUtil.getFeatureName(feat);
        Map<String,List<String>> notes = feat.getNotes();
        feat.setName(name);
        if(notes.containsKey("direction")){
            String dir = notes.get("direction").get(0).toUpperCase();
            String arrowheadType = null;
            if("BOTH".equals(dir)||"NONE".equals(dir)){
                arrowheadType =dir;
            }
            feat.setArrowheadType(arrowheadType);
            notes.remove("direction");
        }
        if("primer".equals(feat.getType())){
            feat.setType("primer_bind");
        }
        if(notes.containsKey("source")){
            feat.setSource(notes.get("source").get(0));
        }
        return feat;
    }
    private static String getLineKey(String line){
        String[] arr;
        line = line.replaceFirst("^[\\s]*","");
        if (line.indexOf("=") < 0) {
            arr = line.split("[\\s]+");
        } else {
            arr = line.split("=");
        }
        return arr[0];
    }
    private static String getLineVal(String line){
        String[] arr;
        if (line.indexOf("=") < 0) {
            line = line.replaceFirst("^[\\s]*[\\S]+[\\s]+|[\\s]+$", "");
            line = line.trim();
            return line;
        } else {
            arr = line.split("=");
            StringBuilder sb = new StringBuilder();
            for (int i = 1; i < arr.length; i++) {
                sb.append(arr[i]);
            }
            return sb.toString();
        }
    }
    private static boolean isKeyword(String line){
        boolean isKey = false;
        int endIndex = line.length()>10?10:line.length();
        if(Pattern.compile("^[\\S]+").matcher(line.substring(0,endIndex)).find()){
            isKey = true;
        }
        return isKey;
    }
    private static void parseFeatures(String line,String key,String val,ParsedOptions options){
                 Integer strand;
        if (GenbankAnnotationKey.FEATURES_TAG.equals(key)) {
            options.setLastLineWasFeaturesTag(true);
            return;
        }
        if(options.getLastLineWasFeaturesTag()){
            options.setFeatureLocationIndentation(getLengthOfWhiteSpaceBeforeStartOfLetters(line));
            options.setLastLineWasFeaturesTag(false);
        }
        if (isFeatureLineRunon(line, options.getFeatureLocationIndentation())) {
            if(options.getLastLineWasLocation()){
                parseFeatureLocation(line.trim(),options);
                options.setLastLineWasLocation(true);
            }else{
                if(options.getCurrentNote()!=null){
                                         String strA =trimLeft(line);
                    strA = strA.replaceAll("\"","");
                    String strB = options.getCurrentNote().get(options.getCurrentNote().size()-1);
                    strB += strA;
                    options.getCurrentNote().set(options.getCurrentNote().size()-1,strB);
                }
                options.setLastLineWasLocation(false);
            }
        }else{
            if(isNote(line)){
                if(getCurrentFeature(options)!=null){
                    parseFeatureNote(line,options);
                    options.setLastLineWasLocation(false);
                }else{
                    return;
                }
            }else{
                                 if (PATTERN_COMPLEMENT.matcher(val).find()) {
                    strand = -1;
                } else {
                    strand = 1;
                }
                                 Feature feat = newFeature(options);
                feat.setType(key);
                feat.setStrand(strand);
                parseFeatureLocation(val,options);
                options.setLastLineWasLocation(true);
            }
        }
    }
    public static Feature newFeature(ParsedOptions options){
        Feature feature = new Feature();
        feature.setSequenceId(options.getSequence().getId());
        feature.setLocations(new ArrayList<>());
        feature.setNotes(new HashMap<>());
        options.getSequence().getFeatures().add(feature);
        return feature;
    }
    private static void parseFeatureNote(String line,ParsedOptions options){
                 String newLine = trimLeft(line);
        newLine = newLine.replaceAll("^/|\"$","");
        String[] lineArr=newLine.split("=\"|=");
        String val = "";
        for (int i = 1; i < lineArr.length; i++) {
            val+=lineArr[i];
            if(i!=lineArr.length-1){
                val+="=";
            }
        }
        if(StringUtils.isNotEmpty(val)){
            val = val.replaceAll("\\\\"," ");
            if(Pattern.compile("=\"").matcher(line).find()){
                val = val.replaceAll("\".*","");
            }else if(Pattern.compile("^\\d+$").matcher(val).find()){
                              }
        }
        String key = lineArr[0];
        Map<String,List<String>> currentNotes = getCurrentFeature(options).getNotes();
                 if(currentNotes.containsKey(key)){
            currentNotes.get(key).add(val);
        }else{
            List<String> notes = new ArrayList<>();
            notes.add(val);
            currentNotes.put(key,notes);
        }
        options.setCurrentNote(currentNotes.get(key));
    }
    private static boolean isNote(String line){
        boolean qual = false;
        if(line.trim().startsWith("/")){
                         qual = true;
        }else if(PATTERN_NOTE.matcher(line).find()){
                         qual = true;
        }
        return qual;
    }
    private static void parseFeatureLocation(String locStr,ParsedOptions options){
        locStr = locStr.trim();
        List<Integer> locArr = new ArrayList<>();
        Matcher numberMatcher = FEATURE_NUMBER_PATTERN.matcher(locStr);
        while (numberMatcher.find()){
            String startEnd = numberMatcher.group();
            locArr.add(Integer.valueOf(startEnd));
        }
        if(locArr.size()%2!=0){
            System.out.println(locStr);
        }
        for (int i = 0; i < locArr.size(); i+=2) {
            Integer start = locArr.get(i) - (options.getInclusive1BasedStart()?0:1);
            int endIndex = i+1;
            Integer end =  endIndex==locArr.size()?start:locArr.get(i + 1) - (options.getInclusive1BasedEnd()?0:1);
            Location location = new Location(start,end);
            Feature feat = getCurrentFeature(options);
            Integer min = feat.getStart();
            Integer max = feat.getEnd();
            if(min == null){
                min = start;
            }
            if(max==null){
                max = end;
            }
            if(min>start){
                min = start;
            }
            if(max<end){
                max = end;
            }
            feat.setStart(min).setEnd(max);
            feat.getLocations().add(options.getIsProtein()?convertAACaretPositionOrRangeToDna(location):location);
        }
    }
    private static Location convertAACaretPositionOrRangeToDna(Location range){
        return new Location(range.getStart() > -1 ? range.getStart() * 3 : range.getStart(),range.getEnd() > -1 ? range.getEnd() * 3 + 2 : range.getEnd());
    }
    private static Feature getCurrentFeature(ParsedOptions options){
        List<Feature> features = options.getSequence().getFeatures();
        if(features.isEmpty()){
            return null;
        }
        return features.get(features.size()-1);
    }
    private static boolean isFeatureLineRunon(String line,int featureLocationIndentation){
        int indentationOfLine = getLengthOfWhiteSpaceBeforeStartOfLetters(line);
        if (featureLocationIndentation == indentationOfLine) {
                                                                                          return false;          }
        String trimmed = line.trim();
        if(trimmed.startsWith("/")){
                         return false;
        }
                 return true;
    }
    private static int getLengthOfWhiteSpaceBeforeStartOfLetters(String string){
        Matcher matcher =PATTERN_BLANK_BEGIN.matcher(string);
        if(matcher.find()){
            return matcher.group().length();
        }
        return 0;
    }
    private static Sequence parseLocus(String line, ParsedOptions options){
        Sequence sequence = new Sequence();
        String[] lineArr = line.split("[\\s]+");
        String locusName=null,date=null,gbDivision=null;
        Boolean circular = false;
        if(lineArr.length<=1){
                         return null;
        }
        locusName = lineArr[1];
        for (int i = 0; i < lineArr.length; i++) {
            if(PATTERN_CIRCULAR.matcher(lineArr[i]).find()){
                circular = true;
            }else if(PATTERN_LINEAR.matcher(lineArr[i]).find()){
                circular = false;
            }
        }
        for (int j = 0; j < lineArr.length; j++) {
            String item = lineArr[j];
            if(PATTERN_DATE.matcher(item).find()){
                date = item;
            }
            if (j == 3 && PATTERN_AA.matcher(item).find()) {
                options.setSequenceTypeFromLocus(item);
                options.setIsProtein(true);
            }
            if (j == 4 && (PATTERN_DS_DNA.matcher(item).find() || PATTERN_SS_DNA.matcher(item).find() || PATTERN_DNA.matcher(item).find())) {
                if(options.getIsProtein()==null){
                    options.setIsProtein(false);
                }
                options.setSequenceTypeFromLocus(item);
            }
            if(ArrayUtils.indexOf(GB_DIVISIONS,item.toUpperCase())>-1){
                gbDivision = item.toUpperCase();
            }
            if (!"Exported".equals(locusName) || UNTITLED_SEQUENCE_NAME.equals(sequence.getName())) {
                sequence.setName(locusName);
            }
        }
        sequence.setIsProtein(options.getIsProtein());
        sequence.setSequenceBuilder(new StringBuilder());
        sequence.setGbDivision(gbDivision);
        sequence.setDate(date);
        sequence.setCircular(circular);
        sequence.setSequenceTypeFromLocus(options.getSequenceTypeFromLocus());
        sequence.setFeatures(new ArrayList<>());
        sequence.setBpLength(0);
        options.setSequence(sequence);
        return sequence;
    }
}
