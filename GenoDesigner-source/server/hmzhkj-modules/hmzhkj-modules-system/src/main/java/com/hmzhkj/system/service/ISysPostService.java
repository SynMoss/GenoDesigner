package com.hmzhkj.system.service;

import java.util.List;
import com.hmzhkj.system.domain.SysPost;


public interface ISysPostService
{

    public List<SysPost> selectPostList(SysPost post);


    public List<SysPost> selectPostAll();


    public SysPost selectPostById(String postId);


    public List<String> selectPostListByUserId(String userId);


    public String checkPostNameUnique(SysPost post);


    public String checkPostCodeUnique(SysPost post);

    public int countUserPostById(String postId);


    public int deletePostById(String postId);


    public int deletePostByIds(String[] postIds);


    public int insertPost(SysPost post);


    public int updatePost(SysPost post);
}
