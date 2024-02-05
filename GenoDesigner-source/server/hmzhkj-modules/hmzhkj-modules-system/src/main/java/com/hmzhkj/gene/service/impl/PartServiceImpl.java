package com.hmzhkj.gene.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hmzhkj.common.core.utils.StringUtils;
import com.hmzhkj.framework.utils.SecurityUtils;
import com.hmzhkj.gene.domain.Part;
import com.hmzhkj.gene.domain.dto.QueryParamDto;
import com.hmzhkj.gene.mapper.PartMapper;
import com.hmzhkj.gene.service.PartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PartServiceImpl extends ServiceImpl<PartMapper, Part> implements PartService {
    private final PartMapper partMapper;

    @Override
    public List<Part> listPage(QueryParamDto queryDto) {
        queryDto.setCreateStaffNo(SecurityUtils.getUserId());
        if(StringUtils.isNotEmpty(queryDto.getCode())){
            String[] split = queryDto.getCode().split(",");
            queryDto.setCodes(Arrays.asList(split));
        }
        return partMapper.queryListPage(queryDto);
    }

    @Override
    public boolean addPart(Part part) {
                 String type = part.getType();
        String code = type+"0001";
        QueryWrapper<Part> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("create_staff_no", SecurityUtils.getUserId());
        queryWrapper.likeRight("code", type);
        queryWrapper.orderByDesc("create_time");
        queryWrapper.last("LIMIT 1");
        Part lastPart = partMapper.selectOne(queryWrapper);
        System.err.println(lastPart);
        if(lastPart != null){
            String lastCode = lastPart.getCode();
            String str = lastCode.replace(type, "");
            int num = Integer.parseInt(str);
            code = type+String.format("%04d", num+1);
        }

        part.setCode(code);
        part.setLength(part.getContent()==null?0:part.getContent().length());
        part.setCreateStaffNo(SecurityUtils.getUserId());
        part.setCreateStaffName(SecurityUtils.getLoginUser().getSysUser().getNickName());
        part.setCreateTime(new Date());
        return save(part);
    }

    @Override
    public Boolean checkName(String name,String id) {
        QueryWrapper<Part> wrapper = new QueryWrapper<Part>()
                .eq("name", name)
                .eq("create_staff_no", SecurityUtils.getUserId());
                 if(id!=null){
            wrapper.ne("id", id);
        }
        Long count = partMapper.selectCount(wrapper);
        if(count == null || count == 0L){
            return  false;
        }
        return true;
    }
}
