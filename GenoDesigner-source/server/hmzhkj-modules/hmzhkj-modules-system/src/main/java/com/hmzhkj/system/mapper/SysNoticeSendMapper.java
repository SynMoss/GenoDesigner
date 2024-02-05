package com.hmzhkj.system.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hmzhkj.system.domain.SysNoticeSend;

import java.util.List;

 
public interface SysNoticeSendMapper extends BaseMapper<SysNoticeSend>
{
    List<SysNoticeSend> listNoticeSelf(SysNoticeSend sysNoticeSend);
}