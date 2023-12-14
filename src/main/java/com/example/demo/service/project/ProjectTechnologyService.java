package com.example.demo.service.project;

import com.example.demo.model.project.Project;
import com.example.demo.model.project.ProjectTechnology;
import com.example.demo.model.technology_stack.TechnologyStack;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ProjectTechnologyService {

    public ProjectTechnology save(ProjectTechnology projectTechnology);

    public ProjectTechnology getProjectTechnologyEntity(
            Project project, TechnologyStack technologyStack);
}
