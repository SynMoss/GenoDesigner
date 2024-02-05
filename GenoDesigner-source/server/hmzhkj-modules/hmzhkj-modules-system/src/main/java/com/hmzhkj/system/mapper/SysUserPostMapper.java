package com.hmzhkj.system.mapper;

import java.util.List;
import com.hmzhkj.system.domain.SysUserPost;


public interface SysUserPostMapper
{

    int deleteUserPostByUserId(String userId);


    int countUserPostById(String postId);


    int deleteUserPost(String[] ids);


    int batchUserPost(List<SysUserPost> userPostList);
}
