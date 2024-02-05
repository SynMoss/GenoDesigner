package com.hmzhkj.system.service.impl;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.hmzhkj.common.core.constant.UserConstants;
import com.hmzhkj.common.core.exception.ServiceException;
import com.hmzhkj.common.core.utils.StringUtils;
import com.hmzhkj.system.domain.SysPost;
import com.hmzhkj.system.mapper.SysPostMapper;
import com.hmzhkj.system.mapper.SysUserPostMapper;
import com.hmzhkj.system.service.ISysPostService;

 
@Service
@RequiredArgsConstructor
public class SysPostServiceImpl implements ISysPostService
{
    private final SysPostMapper postMapper;

    private final SysUserPostMapper userPostMapper;

     
    @Override
    public List<SysPost> selectPostList(SysPost post)
    {
        return postMapper.selectPostList(post);
    }

     
    @Override
    public List<SysPost> selectPostAll()
    {
        return postMapper.selectPostAll();
    }

     
    @Override
    public SysPost selectPostById(String postId)
    {
        return postMapper.selectPostById(postId);
    }

     
    @Override
    public List<String> selectPostListByUserId(String userId)
    {
        return postMapper.selectPostListByUserId(userId);
    }

     
    @Override
    public String checkPostNameUnique(SysPost post)
    {
        SysPost info = postMapper.checkPostNameUnique(post.getPostName());
        if (StringUtils.isNotNull(info) && !info.getPostId().equals(post.getPostId()))
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

     
    @Override
    public String checkPostCodeUnique(SysPost post)
    {
        SysPost info = postMapper.checkPostCodeUnique(post.getPostCode());
        if (StringUtils.isNotNull(info) && !info.getPostId().equals(post.getPostId()))
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

     
    @Override
    public int countUserPostById(String postId)
    {
        return userPostMapper.countUserPostById(postId);
    }

     
    @Override
    public int deletePostById(String postId)
    {
        return postMapper.deletePostById(postId);
    }

     
    @Override
    public int deletePostByIds(String[] postIds)
    {
        for (String postId : postIds)
        {
            SysPost post = selectPostById(postId);
            if (countUserPostById(postId) > 0)
            {
                throw new ServiceException(String.format("%1$s assigned, cannot be deleted", post.getPostName()));
            }
        }
        return postMapper.deletePostByIds(postIds);
    }

     
    @Override
    public int insertPost(SysPost post)
    {
        post.genId();
        return postMapper.insertPost(post);
    }

     
    @Override
    public int updatePost(SysPost post)
    {
        return postMapper.updatePost(post);
    }
}
