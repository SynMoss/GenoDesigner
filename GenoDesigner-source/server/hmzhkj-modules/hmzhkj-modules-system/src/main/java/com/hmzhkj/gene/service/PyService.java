package com.hmzhkj.gene.service;

import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hmzhkj.common.core.exception.ServiceException;
import com.hmzhkj.common.core.web.domain.AjaxResult;
import com.hmzhkj.gene.constant.PyFeatureEnum;
import com.hmzhkj.gene.domain.Feature;
import com.hmzhkj.gene.domain.Sequence;
import com.hmzhkj.gene.model.*;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;

@Service
public class PyService {
    @Value("${url.py}")
    private String pyDomain;
    public void faGffToGb(FaGffToGbParam faGffToGbParam){
        String faGffToGbResultStr = HttpRequest.post(pyDomain+"/faGffToGb")
                .body(faGffToGbParam.toJsonString())
                .execute().body();
        AjaxResult jsonObject = JSON.parseObject(faGffToGbResultStr,AjaxResult.class);
        if(jsonObject.isError()){
            throw new ServiceException(jsonObject.getMsg());
        }
    }
    public List<Sequence> cutFa(CutSequenceParam cutSequenceParam, String fileName) throws JsonProcessingException {
        List<Sequence> sequenceList = new ArrayList<>();
        String cutResultStr = HttpRequest.post(pyDomain+"/cutFa")
                .body(cutSequenceParam.toJsonString())
                .execute().body();
        ObjectMapper mapper = new ObjectMapper();
        AjaxResult jsonObject = mapper.readValue(cutResultStr,AjaxResult.class);
        if(jsonObject.isError()){
            throw new ServiceException(jsonObject.getMsg());
        }
        List jsonArray = (List)jsonObject.getData();
        for (Object o : jsonArray) {
            Sequence saveSeq = mapper.readValue(mapper.writeValueAsString(o),Sequence.class);
            sequenceList.add(saveSeq.setSourceFileName(fileName));
        }
        return sequenceList;
    }
    public List<Sequence> cutGb(CutSequenceParam cutSequenceParam, String fileName) throws JsonProcessingException {
        List<Sequence> sequenceList = new ArrayList<>();
        String cutResultStr = HttpRequest.post(pyDomain+"/cutGb")
                .body(cutSequenceParam.toJsonString())
                .execute().body();
        ObjectMapper mapper = new ObjectMapper();
        AjaxResult jsonObject = mapper.readValue(cutResultStr,AjaxResult.class);
        if(jsonObject.isError()){
            throw new ServiceException(jsonObject.getMsg());
        }
        List jsonArray = (List)jsonObject.getData();
        for (Object o : jsonArray) {
            Sequence saveSeq = mapper.readValue(mapper.writeValueAsString(o),Sequence.class);
            sequenceList.add(saveSeq.setSourceFileName(fileName));
        }
        return sequenceList;
    }
    public PyFeatureReturnParam operateFeature(PyFeatureParam pyFeatureParam, PyFeatureEnum pyFeatureEnum) throws IOException {
        List<Feature> featureList = new ArrayList<>();
        String cutResultStr = HttpRequest.post(pyDomain+"/"+pyFeatureEnum.getValue())
                .body(pyFeatureParam.toJsonString())
                .execute().body();
        ObjectMapper mapper = new ObjectMapper();
        AjaxResult jsonObject = mapper.readValue(cutResultStr,AjaxResult.class);
        if(jsonObject.isError()){
            throw new ServiceException(jsonObject.getMsg());
        }
        Map map = (HashMap)jsonObject.getData();
        List jsonArray = (List)map.get("features");
        for (Object o : jsonArray) {
            Feature feature = mapper.readValue(mapper.writeValueAsString(o),Feature.class);
            featureList.add(feature);
        }
        PyFeatureReturnParam result = new PyFeatureReturnParam();
        result.setFeatures(featureList);
        return result;
    }
    private void deleteFeatureExcel(Map map,String sequenceName) throws IOException {
        if(map.get("listA")!=null){
            List list = (List)map.get("listA");
            if(!list.isEmpty()){
                XSSFWorkbook workbook = new XSSFWorkbook();
                XSSFSheet sheet0 = workbook.createSheet("sheet");
                XSSFRow row0 = sheet0.createRow(0);
                row0.createCell(0).setCellValue("Chr");
                row0.createCell(1).setCellValue("Preserve_start");
                row0.createCell(2).setCellValue("Preserve_end");
                row0.createCell(3).setCellValue("Preserve_length");
                createCell(sheet0,list,sequenceName);
                workbook.close();
            }
        }
    }

    private void reduceGenomeExcel(Map map,String sequenceName) throws IOException {
        if(map.get("listA")!=null){
            List list = (List)map.get("listA");
            if(!list.isEmpty()){
                XSSFWorkbook workbook = new XSSFWorkbook();
                XSSFSheet sheet0 = workbook.createSheet("sheet");
                XSSFRow row0 = sheet0.createRow(0);
                row0.createCell(0).setCellValue("Chr");
                row0.createCell(1).setCellValue("Preserve_start");
                row0.createCell(2).setCellValue("Preserve_end");
                row0.createCell(3).setCellValue("Preserve_length");
                createCell(sheet0,list,sequenceName);
                workbook.close();
            }
        }
    }
    private void createCell(XSSFSheet sheet,List errArray,String sequenceName){
        int maxCell = sheet.getRow(0).getLastCellNum();
        int r=1;
        for (Object o : errArray) {
            XSSFRow row = sheet.createRow(r);
            List array = (List)o;
            int c=1;
            row.createCell(0).setCellValue(sequenceName);
            for (Object o1 : array) {
                if(c==maxCell){
                    break;
                }
                if(o1!=null){
                    row.createCell(c).setCellValue(o1.toString());
                }
                c++;
            }
            r++;
        }
    }
    public List<Feature> reduceGenome(PyFeatureParam pyFeatureParam) throws JsonProcessingException {
        List<Feature> featureList = new ArrayList<>();
        String cutResultStr = HttpRequest.post(pyDomain+"/reduceGenome")
                .body(pyFeatureParam.toJsonString())
                .execute().body();
        ObjectMapper mapper = new ObjectMapper();
        AjaxResult jsonObject = mapper.readValue(cutResultStr,AjaxResult.class);
        if(jsonObject.isError()){
            throw new ServiceException(jsonObject.getMsg());
        }
        List jsonArray = (List)jsonObject.getData();
        for (Object o : jsonArray) {
            Feature feature = mapper.readValue(mapper.writeValueAsString(o),Feature.class);
            featureList.add(feature);
        }
        return featureList;
    }

    public void convertHistory(ConvertHistoryParam convertHistoryParam){
        String result2 = HttpRequest.post(pyDomain+"/convertHistory")
                .body(convertHistoryParam.toJsonString())
                .execute().body();
        AjaxResult ajaxResult = JSON.parseObject(result2,AjaxResult.class);
        if(ajaxResult.isError()){
            throw new ServiceException(ajaxResult.getMsg());
        }
    }
}
