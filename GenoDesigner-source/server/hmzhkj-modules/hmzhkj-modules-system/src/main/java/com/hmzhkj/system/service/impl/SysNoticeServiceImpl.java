package com.hmzhkj.system.service.impl;

import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hmzhkj.common.core.exception.ServiceException;
import com.hmzhkj.common.core.web.domain.AjaxResult;
import com.hmzhkj.framework.utils.SecurityUtils;
import com.hmzhkj.system.domain.SysNotice;
import com.hmzhkj.system.domain.SysNoticeSend;
import com.hmzhkj.system.mapper.SysNoticeSendMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.hmzhkj.system.mapper.SysNoticeMapper;
import com.hmzhkj.system.service.ISysNoticeService;
import org.springframework.util.StringUtils;

 
@Service
@RequiredArgsConstructor
public class SysNoticeServiceImpl implements ISysNoticeService
{
    private final SysNoticeMapper noticeMapper;
    private final SysNoticeSendMapper sysNoticeSendMapper;
     
    @Override
    public SysNotice selectNoticeById(String noticeId)
    {
        return noticeMapper.selectNoticeById(noticeId);
    }

     
    @Override
    public List<SysNotice> selectNoticeList(SysNotice notice)
    {
        return noticeMapper.selectNoticeList(notice);
    }

    @Override
    public List<SysNoticeSend> listNoticeSelf(SysNoticeSend noticeSend) {
        return sysNoticeSendMapper.listNoticeSelf(noticeSend);
    }

    @Override
    public AjaxResult updateNoticeSend(SysNoticeSend noticeSend) {
        SysNoticeSend updateObj = new SysNoticeSend();
        String userId = SecurityUtils.getUserId();
        Date now = new Date();
        updateObj.setUpdateBy(userId);
        updateObj.setUpdateTime(now);
        updateObj.setIsRead(noticeSend.getIsRead());
        updateObj.setIsDisposed(noticeSend.getIsDisposed());
        updateObj.setReadTime(now);
        if(StringUtils.hasLength(noticeSend.getNoticeContent())){
            SysNotice notice = noticeMapper.selectNoticeById(noticeSend.getNoticeId());
            if(notice == null){
                throw new ServiceException("Message data not found");
            }
            notice.setNoticeContent(noticeSend.getNoticeContent());
            noticeMapper.updateNotice(notice);
        }
        LambdaQueryWrapper<SysNoticeSend> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SysNoticeSend::getNoticeId,noticeSend.getNoticeId());
        lambdaQueryWrapper.eq(SysNoticeSend::getUserId,userId);
        return AjaxResult.success(sysNoticeSendMapper.update(updateObj,lambdaQueryWrapper));
    }

     
    @Override
    public int insertNotice(SysNotice notice)
    {
        notice.setCreateBy(SecurityUtils.getUserId());
        Date now = new Date();
        if(!StringUtils.hasLength(notice.getNoticeId())){
            notice.genId();
        }
        noticeMapper.insertNotice(notice);
                 if(notice.getUserIdList()!=null && !notice.getUserIdList().isEmpty()){
            for (String userId : notice.getUserIdList()) {
                SysNoticeSend sysNoticeSend = new SysNoticeSend();
                sysNoticeSend.setUserId(userId);
                sysNoticeSend.setNoticeId(notice.getNoticeId());
                sysNoticeSend.setCreateTime(now);
                sysNoticeSend.setCreateBy(SecurityUtils.getUserId());
                sysNoticeSendMapper.insert(sysNoticeSend);
            }
        }
        return 1;
    }

     
    @Override
    public int updateNotice(SysNotice notice)
    {
        return noticeMapper.updateNotice(notice);
    }

     
    @Override
    public int deleteNoticeById(String noticeId)
    {
        return noticeMapper.deleteNoticeById(noticeId);
    }

     
    @Override
    public int deleteNoticeByIds(String[] noticeIds)
    {
        return noticeMapper.deleteNoticeByIds(noticeIds);
    }
}
