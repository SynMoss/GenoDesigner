package com.hmzhkj.gene.service;

import com.hmzhkj.gene.domain.DesignTBProjectMembers;
import com.hmzhkj.gene.domain.dto.AddProjectMembersDto;
import com.hmzhkj.gene.domain.dto.RemoveProjectMemberDto;

import java.util.List;
import java.util.Map;


public interface IDesignTBProjectMembersService {


    public List<Map<String, String>> selectDesignTBProjectMembersList(String projectId);



    public void insertDesignTBProjectMembers(AddProjectMembersDto addMembers);


    public void deleteDesignTBProjectMembers(RemoveProjectMemberDto removeMember);


    public List<Map<String, String>> selectNonDesignTBProjectMembersList(String projectId);


    public Boolean isProjectMember(String projectId);


    public void updatePermission(Map<String,String> map);


    public DesignTBProjectMembers selectMember(RemoveProjectMemberDto member);


    public void deleteMember(String[] staffIds);
}
