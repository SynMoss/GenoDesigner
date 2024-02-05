package com.hmzhkj.common.core.constant;


public class GenConstants
{

    public static final String TPL_CRUD = "crud";


    public static final String TPL_TREE = "tree";


    public static final String TPL_SUB = "sub";


    public static final String TREE_CODE = "treeCode";


    public static final String TREE_PARENT_CODE = "treeParentCode";


    public static final String TREE_NAME = "treeName";


    public static final String PARENT_MENU_ID = "parentMenuId";


    public static final String PARENT_MENU_NAME = "parentMenuName";


    public static final String[] COLUMNTYPE_STR = { "char", "varchar", "nvarchar", "varchar2" };


    public static final String[] COLUMNTYPE_TEXT = { "tinytext", "text", "mediumtext", "longtext" };


    public static final String[] COLUMNTYPE_TIME = { "datetime", "time", "date", "timestamp" };


    public static final String[] COLUMNTYPE_NUMBER = { "tinyint", "smallint", "mediumint", "int", "number", "integer",
            "bigint", "float", "double", "decimal" };


    public static final String[] COLUMNNAME_NOT_EDIT = { "id", "create_by", "create_time", "del_flag" };


    public static final String[] COLUMNNAME_NOT_LIST = { "id", "create_by", "create_time", "del_flag", "update_by",
            "update_time" };


    public static final String[] COLUMNNAME_NOT_QUERY = { "id", "create_by", "create_time", "del_flag", "update_by",
            "update_time", "remark" };


    public static final String[] BASE_ENTITY = { "createBy", "createTime", "updateBy", "updateTime", "remark" };


    public static final String[] TREE_ENTITY = { "parentName", "parentId", "orderNum", "ancestors" };


    public static final String HTML_INPUT = "input";


    public static final String HTML_TEXTAREA = "textarea";


    public static final String HTML_SELECT = "select";


    public static final String HTML_RADIO = "radio";


    public static final String HTML_CHECKBOX = "checkbox";


    public static final String HTML_DATETIME = "datetime";


    public static final String HTML_IMAGE_UPLOAD = "imageUpload";


    public static final String HTML_FILE_UPLOAD = "fileUpload";


    public static final String HTML_EDITOR = "editor";


    public static final String TYPE_STRING = "String";


    public static final String TYPE_INTEGER = "Integer";


    public static final String TYPE_LONG = "Long";


    public static final String TYPE_DOUBLE = "Double";


    public static final String TYPE_BIGDECIMAL = "BigDecimal";


    public static final String TYPE_DATE = "Date";


    public static final String QUERY_LIKE = "LIKE";


    public static final String QUERY_EQ = "EQ";


    public static final String REQUIRE = "1";
}
