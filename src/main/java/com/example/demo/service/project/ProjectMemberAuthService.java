package com.example.demo.service.project;

import com.example.demo.model.project.ProjectMemberAuth;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ProjectMemberAuthService {
    public ProjectMemberAuth save(ProjectMemberAuth projectMemberAuth);

    public ProjectMemberAuth findProjectMemberAuthById(Long id);

    public ProjectMemberAuth findTopByOrderByIdDesc();
}
