package com.example.demo.global.validation.validator;

import static com.example.demo.constant.TrustScoreTypeIdentifier.*;

import com.example.demo.dto.trust_score.AddPointDto;
import com.example.demo.global.exception.customexception.ProjectCustomException;
import com.example.demo.global.exception.customexception.UserCustomException;
import com.example.demo.global.exception.customexception.WorkCustomException;
import com.example.demo.global.validation.annotation.ValidAddPointDto;
import com.example.demo.model.project.Project;
import com.example.demo.model.project.ProjectMember;
import com.example.demo.model.user.User;
import com.example.demo.model.work.Work;
import com.example.demo.repository.project.ProjectMemberRepository;
import com.example.demo.repository.project.ProjectRepository;
import com.example.demo.repository.trust_score.TrustScoreRepository;
import com.example.demo.repository.trust_score.TrustScoreTypeRepository;
import com.example.demo.repository.user.UserRepository;
import com.example.demo.repository.work.WorkRepository;
import java.util.List;
import java.util.Optional;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AddPointDtoValidator implements ConstraintValidator<ValidAddPointDto, AddPointDto> {
    private final UserRepository userRepository;
    private final WorkRepository workRepository;
    private final TrustScoreRepository trustScoreRepository;
    private final ProjectMemberRepository projectMemberRepository;
    private final ProjectRepository projectRepository;
    private final TrustScoreTypeRepository trustScoreTypeRepository;

    @Override
    public void initialize(ValidAddPointDto constraintAnnotation) {}

    /**
     * AddPointDto 유효성 검증(validate)
     *
     * @param dto object to validate
     * @param context context in which the constraint is evaluated
     * @return
     */
    @Override
    public boolean isValid(AddPointDto dto, ConstraintValidatorContext context) {
        Long userId = dto.getUserId();
        Long scoreTypeId = dto.getScoreTypeId();
        Long projectId = dto.getProjectId();
        Long milestoneId = dto.getMilestoneId();
        Long workId = dto.getWorkId();

        if (!isValidScoreTypeId(scoreTypeId)) {
            return false;
        }

        if (scoreTypeId.equals(WORK_COMPLETE) || scoreTypeId.equals(WORK_INCOMPLETE)) {
            return isValidScoreRequest(userId, workId, projectId, milestoneId);
        } else if (scoreTypeId.equals(NEW_MEMBER)) {
            return isValidNewMemberRequest(userId, projectId, milestoneId, workId);
        } else if (scoreTypeId.equals(SELF_WITHDRAWAL) || scoreTypeId.equals(FORCE_WITHDRAWAL)) {
            return isValidWithdrawal(projectId, userId, milestoneId, workId);
        } else if (scoreTypeId.equals(LATE_WORK)) {
            return isValidLateWorkRequest(userId, workId, projectId, milestoneId);
        }

        return true;
    }

    private boolean isValidLateWorkRequest(
            Long userId, Long workId, Long projectId, Long milestoneId) {

        if (!isValidWorkUserInput(userId, workId, projectId, milestoneId)) {
            log.info("데이터 무결성 위배");
            return false;
        }

        Work work =
                workRepository
                        .findById(workId)
                        .orElseThrow(() -> WorkCustomException.NOT_FOUND_WORK);

        if (work.isCompleteStatus()) {
            log.info("데이터 무결성 위배. 업무 완료여부 불일치. workId : {}", workId);
            return false;
        }

        return true;
    }
    // TODO : Optional User Worker 큰 쿼리 해소하기
    private boolean isValidWorkUserInput(
            Long userId, Long workId, Long projectId, Long milestoneId) {
        if (projectId == null || milestoneId == null || workId == null) {
            log.info(
                    "잘못된 입력값. 프로젝트, 마일스톤, 업무 정보값 하나 이상 부재."
                            + " projectId : {}, milestoneId : {}, workId : {}",
                    projectId,
                    milestoneId,
                    workId);
            return false;
        }

        Optional<User> findUser = userRepository.findById(userId);
        Optional<Work> findWork = workRepository.findById(workId);

        if (findWork.isEmpty()) {
            log.info("잘못된 입력값. 해당 입력값과 일치하는 업무 없음. workId : {}", workId);
            return false;
        }

        if (findUser.isEmpty()) {
            log.info("잘못된 입력값. 해당 입력값과 일치하는 사용자 없음. userId : {}", userId);
            return false;
        }
        // TODO : 성능 최적화 existsByUserId
        if (!trustScoreRepository.existsByUserId(userId)) {
            log.info("데이터 무결성 위배. 신뢰점수 테이블에 사용자가 존재하지 않음. userId : {}", userId);
            return false;
        }

        try {
            Long findProjectId = findWork.get().getProject().getId();
            Long findMilestoneId = findWork.get().getMilestone().getId();
            if (!findProjectId.equals(projectId) || !findMilestoneId.equals(milestoneId)) {
                log.info(
                        "잘못된 입력값. 업무의 프로젝트와 마일스톤 값과 입력값 불일치. projectId : {}, milestoneId : {}",
                        projectId,
                        milestoneId);
                return false;
            }
        } catch (NullPointerException npe) {
            log.error("데이터 무결성 위배. 업무가 마일스톤, 프로젝트 값을 가지고 있지 않음. workId : {}", workId);
            return false;
        }

        Work work = findWork.orElseThrow(() -> WorkCustomException.NOT_FOUND_WORK);
        User user = findUser.orElseThrow(() -> UserCustomException.NOT_FOUND_USER);

        if (!work.getAssignedUserId().equals(user)) {
            log.info("잘못된 입력값. 입력값과 업무 배정 사용자 불울치. workId : {}, userId : {}", workId, userId);
            return false;
        }

        return true;
    }

    /**
     * 입력값과 일치하는 신뢰점수타입 대분류 존재여부 검증
     *
     * @param scoreTypeId
     * @return boolean
     */
    private boolean isValidScoreTypeId(Long scoreTypeId) {
        List<Long> upScoreTypeListList = trustScoreTypeRepository.findAllUpScoreTypeId();
        if (!upScoreTypeListList.contains(scoreTypeId)) {
            log.info("잘못된 입력값. 일치하는 대분류 신뢰점수타입 없음. scoreTypeId : {}", scoreTypeId);
            return false;
        }
        return true;
    }

    /**
     * 자진탈퇴, 강제탈퇴 유효성 검증
     *
     * @param projectId
     * @param userId
     * @param milestoneId
     * @param workId
     */
    private boolean isValidWithdrawal(Long projectId, Long userId, Long milestoneId, Long workId) {
        Optional<Project> findProject = projectRepository.findById(projectId);
        Optional<User> findUser = userRepository.findById(userId);

        if (findProject.isEmpty() || findUser.isEmpty()) {
            log.info(
                    "잘못된 입력값. 프로젝트 혹은 사용자 입력값 존재하지 않음. " + "projectId : {}, userId : {}",
                    projectId,
                    userId);
            return false;
        }

        Project project = findProject.orElseThrow(() -> ProjectCustomException.NOT_FOUND_PROJECT);
        User user = findUser.orElseThrow(() -> UserCustomException.NOT_FOUND_USER);

        Optional<ProjectMember> findProjectMember =
                projectMemberRepository.findProjectMemberByProjectAndUser(project, user);

        if (findProjectMember.isPresent()) {
            log.info(
                    "잘못된 입력값. 프로젝트에 회원 정보 존재. findProjectMemberId : {}",
                    findProjectMember.get().getId());
            return false;
        }

        if (milestoneId != null || workId != null) {
            log.info(
                    "잘못된 입력값. 불필요한 마일스톤, 업무 식별자. milestoneId : {}, workId : {}",
                    milestoneId,
                    workId);
            return false;
        }

        return true;
    }

    /**
     * 신규회원 유효성 검증
     *
     * @param userId
     * @param projectId
     * @param milestoneId
     * @param workId
     */
    private boolean isValidNewMemberRequest(
            Long userId, Long projectId, Long milestoneId, Long workId) {

        if (trustScoreRepository.existsByUserId(userId)) {
            log.info("잘못된 입력값. 신뢰점수 테이블 회원 존재여부 불일치. userId : {}", userId);
            return false;
        }

        if (projectId != null || milestoneId != null || workId != null) {
            log.info(
                    "잘못된 입력값. 불필요한 프로젝트, 마일스톤, 업무 식별자. "
                            + "projectId : {}, milestoneId : {}, workId : {}",
                    projectId,
                    milestoneId,
                    workId);
            return false;
        }

        return true;
    }

    /**
     * 업무완수 및 미흡 유효성 검증
     *
     * @param userId
     * @param workId
     * @param projectId
     * @param milestoneId
     */
    private boolean isValidScoreRequest(
            Long userId, Long workId, Long projectId, Long milestoneId) {

        if (!isValidWorkUserInput(userId, workId, projectId, milestoneId)) {
            log.info("데이터 무결성 위배");
            return false;
        }

        Work work =
                workRepository
                        .findById(workId)
                        .orElseThrow(() -> WorkCustomException.NOT_FOUND_WORK);

        if (!work.isCompleteStatus()) {
            log.info("데이터 무결성 위배. 업무 미완성. workId : {}", workId);
            return false;
        }

        return true;
    }
}
