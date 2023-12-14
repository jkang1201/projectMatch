package com.example.demo.service.project;

import com.example.demo.constant.ProjectMemberStatus;
import com.example.demo.global.exception.customexception.ProjectMemberCustomException;
import com.example.demo.model.position.Position;
import com.example.demo.model.project.Project;
import com.example.demo.model.project.ProjectMember;
import com.example.demo.model.project.ProjectMemberAuth;
import com.example.demo.model.user.User;
import com.example.demo.repository.project.ProjectMemberRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectMemberServiceImpl implements ProjectMemberService {
    private final ProjectMemberRepository projectMemberRepository;

    public ProjectMember toProjectMemberEntity(
            Project project,
            User user,
            ProjectMemberAuth projectMemberAuth,
            ProjectMemberStatus projectMemberStatus,
            Position position) {
        return ProjectMember.builder()
                .project(project)
                .user(user)
                .projectMemberAuth(projectMemberAuth)
                .status(projectMemberStatus)
                .position(position)
                .build();
    }

    @Override
    public ProjectMember findById(Long id) {
        return projectMemberRepository
                .findById(id)
                .orElseThrow(() -> ProjectMemberCustomException.NOT_FOUND_PROJECT_MEMBER);
    }

    public List<ProjectMember> findProjectsMemberByProject(Project project) {
        return projectMemberRepository
                .findProjectsMemberByProject(project)
                .orElseThrow(() -> ProjectMemberCustomException.NOT_FOUND_PROJECT_MEMBER);
    }

    public ProjectMember findProjectMemberByProjectAndUser(Project project, User user) {
        return projectMemberRepository
                .findProjectMemberByProjectAndUser(project, user)
                .orElseThrow(() -> ProjectMemberCustomException.NOT_FOUND_PROJECT_MEMBER);
    }

    @Override
    public ProjectMember save(ProjectMember projectMember) {
        return projectMemberRepository.save(projectMember);
    }

    /**
     * 프로젝트 멤버 탈퇴 수락하기
     *
     * @param projectMemberId
     */
    public void withdrawlConfirm(Long projectMemberId) {
        ProjectMember projectMember = findById(projectMemberId);
        projectMemberRepository.delete(projectMember);
    }

    /**
     * 프로젝트 멤버 강제 탈퇴하기.
     *
     * @param projectMemberId
     */
    public void withdrawlForce(Long projectMemberId) {
        ProjectMember projectMember = findById(projectMemberId);
        projectMemberRepository.delete(projectMember);
    }
}
