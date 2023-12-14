package com.example.demo.service.project;

import com.example.demo.constant.ProjectMemberStatus;
import com.example.demo.model.position.Position;
import com.example.demo.model.project.Project;
import com.example.demo.model.project.ProjectMember;
import com.example.demo.model.project.ProjectMemberAuth;
import com.example.demo.model.user.User;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ProjectMemberService {

    public ProjectMember toProjectMemberEntity(
            Project project,
            User user,
            ProjectMemberAuth projectMemberAuth,
            ProjectMemberStatus projectMemberStatus,
            Position position);

    public ProjectMember findById(Long id);

    public ProjectMember save(ProjectMember projectMember);

    public List<ProjectMember> findProjectsMemberByProject(Project project);

    public void withdrawlConfirm(Long projectMemberId);

    public void withdrawlForce(Long projectMemberId);

    public ProjectMember findProjectMemberByProjectAndUser(Project project, User user);
}
