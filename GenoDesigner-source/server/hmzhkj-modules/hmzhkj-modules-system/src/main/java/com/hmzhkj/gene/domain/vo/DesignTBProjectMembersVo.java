package com.hmzhkj.gene.domain.vo;

import com.hmzhkj.gene.domain.DesignTBProjectMembers;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DesignTBProjectMembersVo implements Serializable {
    private DesignTBProjectMembers designTBProjectMembers;
    private String nickName;
    private String userName;
    private String userId;
}
