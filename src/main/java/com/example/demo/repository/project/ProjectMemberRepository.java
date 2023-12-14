package com.example.demo.repository.project;

import com.example.demo.model.project.Project;
import com.example.demo.model.project.ProjectMember;
import com.example.demo.model.user.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectMemberRepository extends JpaRepository<ProjectMember, Long> {
    Optional<List<ProjectMember>> findProjectsMemberByProject(Project project);

    Optional<ProjectMember> findProjectMemberByProjectAndUser(Project project, User user);
}
