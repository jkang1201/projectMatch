package com.example.demo.service.project;

import com.example.demo.global.exception.customexception.ProjectCustomException;
import com.example.demo.model.project.Project;
import com.example.demo.model.user.User;
import com.example.demo.repository.project.ProjectRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;

    public Project save(Project project) {
        return projectRepository.save(project);
    }

    @Override
    public List<Project> findProjectsByUser(User user) {
        return projectRepository
                .findProjectsByUser(user)
                .orElseThrow(() -> ProjectCustomException.NOT_FOUND_PROJECT);
    }

    public Project findById(Long id) {
        return projectRepository
                .findById(id)
                .orElseThrow(() -> ProjectCustomException.NOT_FOUND_PROJECT);
    }

    public int countProjectsByUser(User user) {
        return projectRepository.countProjectsByUser(user);
    }

    /**
     * 프로젝트 종료하기
     *
     * @param projectId
     */
    public void end(Long projectId) {
        Project project = findById(projectId);
        project.endProject();
    }
}
