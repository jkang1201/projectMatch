package com.example.demo.service.board;

import com.example.demo.constant.ProjectMemberStatus;
import com.example.demo.dto.board.response.BoardCreateResponseDto;
import com.example.demo.dto.board.response.BoardUpdateResponseDto;
import com.example.demo.dto.board_project.request.BoardProjectCreateRequestDto;
import com.example.demo.dto.board_project.request.BoardProjectUpdateRequestDto;
import com.example.demo.dto.board_project.response.BoardProjectCreateResponseDto;
import com.example.demo.dto.board_project.response.BoardProjectUpdateResponseDto;
import com.example.demo.dto.project.response.ProjectCreateResponseDto;
import com.example.demo.dto.project.response.ProjectUpdateResponseDto;
import com.example.demo.model.board.Board;
import com.example.demo.model.board.BoardPosition;
import com.example.demo.model.position.Position;
import com.example.demo.model.project.Project;
import com.example.demo.model.project.ProjectMember;
import com.example.demo.model.project.ProjectMemberAuth;
import com.example.demo.model.project.ProjectTechnology;
import com.example.demo.model.technology_stack.TechnologyStack;
import com.example.demo.model.trust_grade.TrustGrade;
import com.example.demo.model.user.User;
import com.example.demo.model.user.UserProjectHistory;
import com.example.demo.service.position.PositionService;
import com.example.demo.service.project.ProjectMemberAuthService;
import com.example.demo.service.project.ProjectMemberService;
import com.example.demo.service.project.ProjectServiceImpl;
import com.example.demo.service.project.ProjectTechnologyService;
import com.example.demo.service.technology_stack.TechnologyStackService;
import com.example.demo.service.trust_grade.TrustGradeService;
import com.example.demo.service.user.UserProjectHistoryService;
import com.example.demo.service.user.UserService;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardFacade {
    private final BoardService boardService;
    private final UserService userService;
    private final TrustGradeService trustGradeService;
    private final ProjectServiceImpl projectServiceImpl;
    private final TechnologyStackService technologyStackService;
    private final ProjectTechnologyService projectTechnologyService;
    private final PositionService positionService;
    private final BoardPositionService boardPositionService;
    private final ProjectMemberAuthService projectMemberAuthService;
    private final ProjectMemberService projectMemberService;
    private final UserProjectHistoryService userProjectHistoryService;

    /**
     * 게시글, 프로젝트 생성, 프로젝트 기술 생성, 프로젝트 멤버 생성, 사용자 이력 생성, 게시글-포지션 생성 TODO : 현재 유저가 개발하도록 작성
     *
     * @param dto
     * @return
     */
    @Transactional
    public BoardProjectCreateResponseDto create(BoardProjectCreateRequestDto dto) {
        User tempUser = userService.findById(1L);

        // 신뢰등급 설정
        TrustGrade trustGrade =
                trustGradeService.getTrustGradeById(dto.getProject().getTrustGradeId());

        // project 생성
        Project project = dto.getProject().toProjectEntity(trustGrade, tempUser);
        Project savedProject = projectServiceImpl.save(project);

        // 프로젝트 기술 생성
        for (Long technolgoyId : dto.getProject().getTechnologyIds()) {
            TechnologyStack technologyStack = technologyStackService.findById(technolgoyId);
            ProjectTechnology projectTechnology =
                    projectTechnologyService.getProjectTechnologyEntity(
                            savedProject, technologyStack);
            projectTechnologyService.save(projectTechnology);
        }

        // board 생성
        Board board = dto.getBoard().toBoardEntity(savedProject, tempUser);
        Board savedBoard = boardService.save(board);

        // boardPosition 생성
        List<BoardPosition> boardPositionList = new ArrayList<>();
        for (Long positionId : dto.getBoard().getPositionIds()) {
            Position position = positionService.findById(positionId);

            BoardPosition boardPosition =
                    boardPositionService.getBoardPositionEntity(savedBoard, position);
            boardPositionService.save(boardPosition);
        }
        savedBoard.setPositions(boardPositionList);

        // 프로젝트 멤버 권한 일반인으로 설정
        // 프로젝트 멤버 생성
        ProjectMemberAuth projectMemberAuth =
                projectMemberAuthService.findProjectMemberAuthById(1L);
        ProjectMember projectMember =
                projectMemberService.toProjectMemberEntity(
                        project,
                        tempUser,
                        projectMemberAuth,
                        ProjectMemberStatus.PARTICIPATING,
                        project.getUser().getPosition());
        projectMemberService.save(projectMember);

        // 사용자 프로젝트 이력 생성
        UserProjectHistory userProjectHistory =
                userProjectHistoryService.toUserProjectHistoryEntity(tempUser, savedProject);
        userProjectHistoryService.save(userProjectHistory);

        // response값 생성
        BoardCreateResponseDto boardCreateResponseDto = BoardCreateResponseDto.of(board);
        ProjectCreateResponseDto projectCreateResponseDto = ProjectCreateResponseDto.of(project);

        return new BoardProjectCreateResponseDto(boardCreateResponseDto, projectCreateResponseDto);
    }

    /**
     * 게시글, 프로젝트 업데이트 게시글, 프로젝트, 프로젝트 기술, 게시글-포지션 TODO : 현재 유저가 업데이트 하도록 변경
     *
     * @param dto
     * @return
     */
    public BoardProjectUpdateResponseDto update(Long boardId, BoardProjectUpdateRequestDto dto) {
        Board board = boardService.findById(boardId);
        Project project = board.getProject();
        User tempUser = userService.findById(1L); // 나중에 Security로 고쳐야 함.

        TrustGrade trustGrade =
                trustGradeService.getTrustGradeById(dto.getProject().getTrustGradeId());

        // project 업데이트
        project.updateProject(dto.getProject(), trustGrade);

        // board 업데이트
        board.updateBoard(dto.getBoard());

        // 프로젝트 기술 업데이트
        List<ProjectTechnology> projectTechnologyList = new ArrayList<>();
        for (Long technologyId : dto.getProject().getTechnologyIds()) {
            TechnologyStack technologyStack = technologyStackService.findById(technologyId);
            ProjectTechnology projectTechnology =
                    projectTechnologyService.getProjectTechnologyEntity(project, technologyStack);
            projectTechnologyList.add(projectTechnology);
        }
        project.changeProjectTechnologys(projectTechnologyList);

        // position 받아서 다시 게시글-포지션 연결
        List<BoardPosition> boardPositionList = new ArrayList<>();
        for (Long positionId : dto.getBoard().getPositionIds()) {
            Position position = positionService.findById(positionId);
            BoardPosition boardPosition =
                    boardPositionService.getBoardPositionEntity(board, position);
            boardPositionList.add(boardPosition);
        }
        board.setPositions(boardPositionList);

        // response값 생성
        BoardUpdateResponseDto boardUpdateResponseDto = BoardUpdateResponseDto.of(board);
        ProjectUpdateResponseDto projectUpdateResponseDto = ProjectUpdateResponseDto.of(project);

        return new BoardProjectUpdateResponseDto(boardUpdateResponseDto, projectUpdateResponseDto);
    }
}
