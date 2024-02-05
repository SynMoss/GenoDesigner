package com.hmzhkj.system.mapper;

import java.util.List;
import com.hmzhkj.system.domain.SysPost;


public interface SysPostMapper
{

    List<SysPost> selectPostList(SysPost post);


    List<SysPost> selectPostAll();


    SysPost selectPostById(String postId);


    List<String> selectPostListByUserId(String userId);


    List<SysPost> selectPostsByUserName(String userName);


    int deletePostById(String postId);


    int deletePostByIds(String[] postIds);


    int updatePost(SysPost post);


    int insertPost(SysPost post);


    SysPost checkPostNameUnique(String postName);


    SysPost checkPostCodeUnique(String postCode);
}
