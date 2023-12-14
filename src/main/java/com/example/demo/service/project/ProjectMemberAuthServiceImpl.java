package com.example.demo.service.project;

import com.example.demo.global.exception.customexception.ProjectMemberAuthCustomException;
import com.example.demo.model.project.ProjectMemberAuth;
import com.example.demo.repository.project.ProjectMemberAuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectMemberAuthServiceImpl implements ProjectMemberAuthService {
    private final ProjectMemberAuthRepository projectMemberAuthRepository;

    @Override
    public ProjectMemberAuth save(ProjectMemberAuth projectMemberAuth) {
        return projectMemberAuthRepository.save(projectMemberAuth);
    }

    public ProjectMemberAuth findProjectMemberAuthById(Long id) {
        return projectMemberAuthRepository
                .findById(1L)
                .orElseThrow(() -> ProjectMemberAuthCustomException.NOT_FOUND_PROJECT_MEMBER_AUTH);
    }

    public ProjectMemberAuth findTopByOrderByIdDesc() {
        return projectMemberAuthRepository
                .findTopByOrderByIdDesc()
                .orElseThrow(() -> ProjectMemberAuthCustomException.NOT_FOUND_PROJECT_MEMBER_AUTH);
    }
}
