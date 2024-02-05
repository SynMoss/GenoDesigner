package com.hmzhkj.gene.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
 
public class Assembler {

    private static final Logger logger = LoggerFactory.getLogger(Assembler.class);

    public Assembler() {
    }

    public static void assemble(Object fromObject, Object toObject) {
        BeanUtils.copyProperties(fromObject, toObject);
    }

    public static void assemble(Object fromObject, Object toObject, String... ignoreProperties) {
        BeanUtils.copyProperties(fromObject, toObject, ignoreProperties);
    }

    public static <T> List<T> assembleList2NewList(List<?> fromList, Class<T> toClass, String... excludeFields) {
        List<T> toList = new ArrayList(fromList.size());

        Object toObject;
        try {
            for(Iterator<?> iterator = fromList.iterator(); iterator.hasNext(); toList.add((T) toObject)) {
                Object fromObject = iterator.next();
                toObject = toClass.newInstance();
                if (excludeFields.length > 0) {
                    assemble(fromObject, toObject, excludeFields);
                } else {
                    assemble(fromObject, toObject);
                }
            }
        } catch (Exception var7) {
            logger.error("assember error", var7);
        }

        return toList;
    }

    public static <T> List<T> assembleList2List(List<?> fromList, List<T> toList, Class<T> toClass, String... excludeFields) {
        try {
            for(int i = 0; i < fromList.size(); ++i) {
                Object fromObject = fromList.get(i);
                T toObject = i >= toList.size() ? toClass.newInstance() : toList.get(i);
                if (excludeFields.length > 0) {
                    assemble(fromObject, toObject, excludeFields);
                } else {
                    assemble(fromObject, toObject);
                }

                toList.add(toObject);
            }
        } catch (Exception var7) {
            logger.error("assember error", var7);
        }

        return toList;
    }

}
