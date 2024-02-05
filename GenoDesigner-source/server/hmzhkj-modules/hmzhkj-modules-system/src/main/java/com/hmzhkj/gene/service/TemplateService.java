package com.hmzhkj.gene.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hmzhkj.gene.domain.Template;

import java.util.List;

public interface TemplateService extends IService<Template> {
    List<Template> getTemplateList(String name);

    Boolean checkName(String name, String id);

    void saveTemplate(Template template);

    void deleteTemplate(String id);
}
