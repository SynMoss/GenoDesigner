package com.hmzhkj.gene.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hmzhkj.gene.domain.DesignTBProjectMembers;
import com.hmzhkj.gene.domain.dto.RemoveProjectMemberDto;

import java.util.List;


public interface DesignTBProjectMembersMapper extends BaseMapper<DesignTBProjectMembers> {


    public List<DesignTBProjectMembers> selectDesignTBProjectMembersList(DesignTBProjectMembers designTBProjectMembers);


    public String[] selectDesignTBProjectMembersStaffIds(String projectId);


    public int removeProjectMember(RemoveProjectMemberDto removeMember);
}
