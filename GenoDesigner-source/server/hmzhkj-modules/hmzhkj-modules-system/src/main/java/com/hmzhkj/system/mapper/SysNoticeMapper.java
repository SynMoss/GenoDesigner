package com.hmzhkj.system.mapper;

import com.hmzhkj.system.domain.SysNotice;

import java.util.List;


public interface SysNoticeMapper
{

    SysNotice selectNoticeById(String noticeId);


    List<SysNotice> selectNoticeList(SysNotice notice);


    int insertNotice(SysNotice notice);


    int updateNotice(SysNotice notice);


    int deleteNoticeById(String noticeId);


    int deleteNoticeByIds(String[] noticeIds);
}