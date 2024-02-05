package com.hmzhkj.system.service;

import java.util.List;

import com.hmzhkj.common.core.web.domain.AjaxResult;
import com.hmzhkj.system.domain.SysNotice;
import com.hmzhkj.system.domain.SysNoticeSend;


public interface ISysNoticeService
{

    public SysNotice selectNoticeById(String noticeId);


    List<SysNotice> selectNoticeList(SysNotice notice);


    List<SysNoticeSend> listNoticeSelf(SysNoticeSend noticeSend);


    AjaxResult updateNoticeSend(SysNoticeSend noticeSend);

    public int insertNotice(SysNotice notice);


    public int updateNotice(SysNotice notice);


    public int deleteNoticeById(String noticeId);
    

    public int deleteNoticeByIds(String[] noticeIds);
}
