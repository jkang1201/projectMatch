package com.example.demo.service.project;

import com.example.demo.constant.AlertType;
import com.example.demo.dto.position.response.PositionResponseDto;
import com.example.demo.dto.projectmember.response.*;
import com.example.demo.dto.technology_stack.response.TechnologyStackInfoResponseDto;
import com.example.demo.dto.trust_grade.response.TrustGradeResponseDto;
import com.example.demo.dto.user.response.UserCrewDetailResponseDto;
import com.example.demo.dto.user.response.UserReadProjectCrewResponseDto;
import com.example.demo.model.alert.Alert;
import com.example.demo.model.project.Project;
import com.example.demo.model.project.ProjectMember;
import com.example.demo.model.technology_stack.TechnologyStack;
import com.example.demo.model.user.UserTechnologyStack;
import com.example.demo.model.work.Work;
import com.example.demo.service.alert.AlertService;
import com.example.demo.service.trust_score.TrustScoreHistoryService;
import com.example.demo.service.work.WorkService;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProjectMemberFacade {

    private final ProjectMemberService projectMemberService;
    private final AlertService alertService;
    private final ProjectService projectService;
    private final WorkService workService;
    private final TrustScoreHistoryService trustScoreHistoryService;

    /**
     * 프로젝트 멤버 탈퇴 알림 보내기
     *
     * @param projectMemberId
     */
    public void sendWithdrawlAlert(Long projectMemberId) {
        ProjectMember projectMember = projectMemberService.findById(projectMemberId);

        Alert alert =
                Alert.builder()
                        .project(projectMember.getProject())
                        .checkUser(projectMember.getProject().getUser())
                        .sendUser(projectMember.getUser())
                        .work(null)
                        .milestone(null)
                        .content("프로젝트 지원했습니다.")
                        .position(projectMember.getPosition())
                        .type(AlertType.RECRUIT)
                        .checked_YN(false)
                        .build();

        alertService.save(alert);
    }

    /**
     * 크루정보 상세 페이지 TODO : 프로젝트 신뢰 이력 추가 해야함 유저 정보들, 유저 기술들, 프로젝트 개수, 신뢰점수 이력들
     *
     * @param projectMemberId
     */
    @Transactional(readOnly = true)
    public ProjectMemberReadCrewDetailResponseDto getCrewDetail(Long projectMemberId) {
        ProjectMember projectMember = projectMemberService.findById(projectMemberId);

        int projectCount = projectService.countProjectsByUser(projectMember.getUser());
        TrustGradeResponseDto trustGradeResponseDto =
                TrustGradeResponseDto.of(projectMember.getProject().getTrustGrade());
        PositionResponseDto positionResponseDto =
                PositionResponseDto.of(projectMember.getPosition());
        List<TechnologyStackInfoResponseDto> technologyStackInfoResponseDtoList = new ArrayList<>();

        for (UserTechnologyStack userTechnologyStack : projectMember.getUser().getTechStacks()) {
            TechnologyStack technologyStack = userTechnologyStack.getTechnologyStack();

            TechnologyStackInfoResponseDto technologyStackInfoResponseDto =
                    TechnologyStackInfoResponseDto.of(
                            technologyStack.getId(), technologyStack.getName());
            technologyStackInfoResponseDtoList.add(technologyStackInfoResponseDto);
        }

        // 임시
        UserCrewDetailResponseDto userCrewDetailResponseDto =
                UserCrewDetailResponseDto.of(
                        projectMember.getUser(),
                        1,
                        positionResponseDto,
                        trustGradeResponseDto,
                        technologyStackInfoResponseDtoList);

        return ProjectMemberReadCrewDetailResponseDto.of(
                projectMember, projectCount, userCrewDetailResponseDto, positionResponseDto);
    }

    @Transactional(readOnly = true)
    public ProjectMemberReadTotalProjectCrewsResponseDto getCrewsByProject(Long projectId) {
        Project project = projectService.findById(projectId);

        List<ProjectMemberReadProjectCrewsResponseDto> projectMemberReadProjectCrewsResponseDtos =
                new ArrayList<>();
        for (ProjectMember projectMember : project.getProjectMembers()) {
            UserReadProjectCrewResponseDto userReadProjectCrewResponseDto =
                    UserReadProjectCrewResponseDto.of(projectMember.getUser());
            ProjectMemberAuthResponseDto projectMemberAuthResponseDto =
                    ProjectMemberAuthResponseDto.of(projectMember.getProjectMemberAuth());
            PositionResponseDto positionResponseDto =
                    PositionResponseDto.of(projectMember.getPosition());
            Work lastCompleteWork =
                    workService.findLastCompleteWork(project, projectMember.getUser(), true);

            ProjectMemberReadProjectCrewsResponseDto projectMemberReadProjectCrewsResponseDto =
                    ProjectMemberReadProjectCrewsResponseDto.of(
                            projectMember,
                            userReadProjectCrewResponseDto,
                            projectMemberAuthResponseDto,
                            positionResponseDto,
                            lastCompleteWork.getCompleteDate());
            projectMemberReadProjectCrewsResponseDtos.add(projectMemberReadProjectCrewsResponseDto);
        }

        ProjectMemberReadTotalProjectCrewsResponseDto result =
                ProjectMemberReadTotalProjectCrewsResponseDto.of(
                        projectMemberReadProjectCrewsResponseDtos);
        return result;
    }
}
