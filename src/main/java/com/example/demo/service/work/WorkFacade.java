package com.example.demo.service.work;

import com.example.demo.dto.work.request.*;
import com.example.demo.model.milestone.Milestone;
import com.example.demo.model.project.Project;
import com.example.demo.model.project.ProjectMember;
import com.example.demo.model.user.User;
import com.example.demo.model.work.Work;
import com.example.demo.service.milestone.MilestoneService;
import com.example.demo.service.project.ProjectMemberService;
import com.example.demo.service.project.ProjectService;
import com.example.demo.service.user.UserService;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class WorkFacade {
    private final WorkService workService;
    private final ProjectService projectService;
    private final MilestoneService milestoneService;
    private final UserService userService;
    private final ProjectMemberService projectMemberService;

    public void create(
            Long projectId, Long milestoneId, WorkCreateRequestDto workCreateRequestDto) {
        Project project = projectService.findById(projectId);
        Milestone milestone = milestoneService.findById(milestoneId);
        User user = userService.findById(workCreateRequestDto.getAssignedUserId());
        ProjectMember projectMember =
                projectMemberService.findProjectMemberByProjectAndUser(project, user);
        Work work = workCreateRequestDto.toWorkEntity(project, milestone, user, projectMember);

        workService.save(work);
    }

    @Transactional(readOnly = true)
    public List<WorkReadResponseDto> getAllByProject(Long projectId) {
        Project project = projectService.findById(projectId);
        List<Work> works = workService.findWorksByProject(project);

        List<WorkReadResponseDto> workReadResponseDtos = new ArrayList<>();
        for (Work work : works) {
            WorkReadResponseDto workReadResponseDto = WorkReadResponseDto.of(work);
            workReadResponseDtos.add(workReadResponseDto);
        }

        return workReadResponseDtos;
    }

    @Transactional(readOnly = true)
    public List<WorkReadResponseDto> getAllByMilestone(Long projectId, Long milestoneId) {
        Project project = projectService.findById(projectId);

        Milestone milestone = milestoneService.findById(milestoneId);
        List<Work> works = workService.findWorksByProjectAndMilestone(project, milestone);

        List<WorkReadResponseDto> workReadResponseDtos = new ArrayList<>();
        for (Work work : works) {
            WorkReadResponseDto workReadResponseDto = WorkReadResponseDto.of(work);
            workReadResponseDtos.add(workReadResponseDto);
        }

        return workReadResponseDtos;
    }

    /**
     * 업무 수정 TODO : 마지막 수정자 현재 유저인 구성원으로 변경
     *
     * @param workId
     */
    public void update(Long workId, WorkUpdateRequestDto workUpdateRequestDto) {
        Work work = workService.findById(workId);
        User user = userService.findById(1L);
        ProjectMember projectMember =
                projectMemberService.findProjectMemberByProjectAndUser(work.getProject(), user);
        work.update(workUpdateRequestDto);
    }

    /**
     * 업무 내용 수정 TODO : 마지막 변경자 바꿔줘야 함.
     *
     * @param workId
     * @param workUpdateContentRequestDto
     */
    public void updateContent(
            Long workId, WorkUpdateContentRequestDto workUpdateContentRequestDto) {
        Work work = workService.findById(workId);
        User user = userService.findById(1L);
        ProjectMember projectMember =
                projectMemberService.findProjectMemberByProjectAndUser(work.getProject(), user);
        work.updateContent(workUpdateContentRequestDto, projectMember);
    }

    /**
     * 업무 완료 여부 수정 TODO : 마지막 변경자 바꿔줘야 함.
     *
     * @param workId
     * @param workUpdateCompleteStatusRequestDto
     */
    public void updateCompleteStatus(
            Long workId, WorkUpdateCompleteStatusRequestDto workUpdateCompleteStatusRequestDto) {
        Work work = workService.findById(workId);
        User user = userService.findById(1L);
        ProjectMember projectMember =
                projectMemberService.findProjectMemberByProjectAndUser(work.getProject(), user);
        work.updateCompleteStatus(workUpdateCompleteStatusRequestDto, projectMember);
    }

    public void updateAssignUser(
            Long workId, WorkUpdateAssignUserRequestDto workUpdateAssignUserRequestDto) {
        Work work = workService.findById(workId);
        User user = userService.findById(workUpdateAssignUserRequestDto.getAssignUserId());
        ProjectMember projectMember =
                projectMemberService.findProjectMemberByProjectAndUser(work.getProject(), user);

        work.updateAssignedUserId(user, projectMember);
    }
}
