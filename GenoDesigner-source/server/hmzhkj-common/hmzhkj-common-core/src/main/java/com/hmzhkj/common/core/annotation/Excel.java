package com.hmzhkj.common.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.math.BigDecimal;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import com.hmzhkj.common.core.utils.poi.ExcelHandlerAdapter;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Excel
{

    public int sort() default Integer.MAX_VALUE;


    public String name() default "";


    public String dateFormat() default "";


    public String readConverterExp() default "";


    public String separator() default ",";


    public int scale() default -1;


    public int roundingMode() default BigDecimal.ROUND_HALF_EVEN;


    public double height() default 14;


    public double width() default 16;


    public String suffix() default "";


    public String defaultValue() default "";


    public String prompt() default "";


    public String[] combo() default {};


    public boolean needMerge() default false;


    public boolean isExport() default true;


    public String targetAttr() default "";


    public boolean isStatistics() default false;


    public ColumnType cellType() default ColumnType.STRING;


    public IndexedColors headerBackgroundColor() default IndexedColors.GREY_50_PERCENT;


    public IndexedColors headerColor() default IndexedColors.WHITE;


    public IndexedColors backgroundColor() default IndexedColors.WHITE;


    public IndexedColors color() default IndexedColors.BLACK;


    public HorizontalAlignment align() default HorizontalAlignment.CENTER;


    public Class<?> handler() default ExcelHandlerAdapter.class;


    public String[] args() default {};


    Type type() default Type.ALL;

    public enum Type
    {
        ALL(0), EXPORT(1), IMPORT(2);
        private final int value;

        Type(int value)
        {
            this.value = value;
        }

        public int value()
        {
            return this.value;
        }
    }

    public enum ColumnType
    {
        NUMERIC(0), STRING(1), IMAGE(2);
        private final int value;

        ColumnType(int value)
        {
            this.value = value;
        }

        public int value()
        {
            return this.value;
        }
    }
}