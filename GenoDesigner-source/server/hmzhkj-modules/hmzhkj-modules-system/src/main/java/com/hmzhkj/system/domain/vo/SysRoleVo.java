package com.hmzhkj.system.domain.vo;

import com.hmzhkj.common.core.web.domain.BaseEntity;

public class SysRoleVo extends BaseEntity {
    private String [] ids;
    public String[] getIds(){
        return ids;
    }
    public void setIds(String[] ids){
        this.ids = ids;
    }
}
