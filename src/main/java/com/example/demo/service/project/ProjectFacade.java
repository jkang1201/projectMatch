package com.example.demo.service.project;

import com.example.demo.constant.AlertType;
import com.example.demo.constant.ProjectMemberStatus;
import com.example.demo.dto.project.request.ProjectConfirmRequestDto;
import com.example.demo.dto.project.request.ProjectParticipateRequestDto;
import com.example.demo.dto.project.response.ProjectMeResponseDto;
import com.example.demo.dto.project.response.ProjectSpecificDetailResponseDto;
import com.example.demo.dto.projectmember.response.MyProjectMemberResponseDto;
import com.example.demo.dto.trust_grade.response.TrustGradeResponseDto;
import com.example.demo.dto.user.response.UserMyProjectResponseDto;
import com.example.demo.model.alert.Alert;
import com.example.demo.model.position.Position;
import com.example.demo.model.project.Project;
import com.example.demo.model.project.ProjectMember;
import com.example.demo.model.project.ProjectMemberAuth;
import com.example.demo.model.user.User;
import com.example.demo.service.alert.AlertService;
import com.example.demo.service.milestone.MilestoneService;
import com.example.demo.service.position.PositionService;
import com.example.demo.service.user.UserService;
import com.example.demo.service.work.WorkService;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProjectFacade {

    private final UserService userService;
    private final ProjectService projectService;
    private final ProjectMemberService projectMemberService;
    private final WorkService workService;
    private final PositionService positionService;
    private final AlertService alertService;
    private final ProjectMemberAuthService projectMemberAuthService;
    private final MilestoneService milestoneService;

    /**
     * 내 프로젝트 목록 조회 TODO : 현재 유저 가져오기.
     *
     * @return
     */
    @Transactional(readOnly = true)
    public List<ProjectMeResponseDto> getMyProjects() {
        User user = userService.findById(1L);

        List<Project> projects = projectService.findProjectsByUser(user);
        List<ProjectMeResponseDto> result = new ArrayList<>();

        for (Project project : projects) {
            TrustGradeResponseDto trustGradeDto = TrustGradeResponseDto.of(project.getTrustGrade());

            List<MyProjectMemberResponseDto> myProjectMemberResponseDtos = new ArrayList<>();
            for (ProjectMember projectMember : project.getProjectMembers()) {
                UserMyProjectResponseDto userMyProjectResponseDto =
                        UserMyProjectResponseDto.of(projectMember.getUser());
                MyProjectMemberResponseDto myProjectMemberResponseDto =
                        MyProjectMemberResponseDto.of(projectMember, userMyProjectResponseDto);
                myProjectMemberResponseDtos.add(myProjectMemberResponseDto);
            }

            ProjectMeResponseDto projectMeResponseDto =
                    ProjectMeResponseDto.of(project, trustGradeDto, myProjectMemberResponseDtos);
            result.add(projectMeResponseDto);
        }

        return result;
    }

    /**
     * 프로젝트 상세 목록
     *
     * @param projectId
     * @return
     */
    @Transactional(readOnly = true)
    public ProjectSpecificDetailResponseDto getDetail(Long projectId) {
        Project project = projectService.findById(projectId);
        TrustGradeResponseDto trustGradeDto = TrustGradeResponseDto.of(project.getTrustGrade());

        return ProjectSpecificDetailResponseDto.of(project, trustGradeDto);
    }

    /**
     * 참여하기 참여하는 경우 알림보내기 TODO : 지원자 아이디 jwt token으로 받기.
     *
     * @param projectId
     * @param projectParticipateRequestDto
     */
    public void sendParticipateAlert(
            Long projectId, ProjectParticipateRequestDto projectParticipateRequestDto) {
        Project project = projectService.findById(projectId);
        User user = userService.findById(1L);
        Position position = positionService.findById(projectParticipateRequestDto.getPositionId());
        Alert alert =
                Alert.builder()
                        .project(project)
                        .checkUser(project.getUser())
                        .sendUser(user)
                        .work(null)
                        .milestone(null)
                        .content("프로젝트 지원했습니다.")
                        .position(position)
                        .type(AlertType.RECRUIT)
                        .checked_YN(false)
                        .build();

        alertService.save(alert);
    }

    /**
     * 참여 수락하기 TODO : 사용자 jwt token으로 사용하기
     *
     * @param projectId
     * @param projectConfirmRequestDto
     */
    public void confirm(Long projectId, ProjectConfirmRequestDto projectConfirmRequestDto) {
        Project project = projectService.findById(projectId);
        User user = userService.findById(1L);
        ProjectMemberAuth projectMemberAuth = projectMemberAuthService.findTopByOrderByIdDesc();
        Position position = positionService.findById(projectConfirmRequestDto.getPositionId());

        ProjectMember projectMember =
                projectMemberService.toProjectMemberEntity(
                        project,
                        user,
                        projectMemberAuth,
                        ProjectMemberStatus.PARTICIPATING,
                        position);
        projectMemberService.save(projectMember);
    }
}
