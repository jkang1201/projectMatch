package com.example.demo.service.project;

import com.example.demo.model.project.Project;
import com.example.demo.model.project.ProjectTechnology;
import com.example.demo.model.technology_stack.TechnologyStack;
import com.example.demo.repository.project.ProjectTechnologyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectTechnologyServiceImpl implements ProjectTechnologyService {
    private final ProjectTechnologyRepository projectTechnologyRepository;

    @Override
    public ProjectTechnology save(ProjectTechnology projectTechnology) {
        return projectTechnologyRepository.save(projectTechnology);
    }

    @Override
    public ProjectTechnology getProjectTechnologyEntity(
            Project project, TechnologyStack technologyStack) {
        return ProjectTechnology.builder()
                .project(project)
                .technologyStack(technologyStack)
                .build();
    }
}
