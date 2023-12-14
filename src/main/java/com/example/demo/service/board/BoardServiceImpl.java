package com.example.demo.service.board;

import com.example.demo.dto.board.request.BoardSearchRequestDto;
import com.example.demo.dto.board.response.BoardDetailResponseDto;
import com.example.demo.dto.board.response.BoardSearchResponseDto;
import com.example.demo.dto.board.response.BoardTotalDetailResponseDto;
import com.example.demo.dto.boardposition.BoardPositionDetailResponseDto;
import com.example.demo.dto.position.response.PositionResponseDto;
import com.example.demo.dto.project.response.ProjectDetailResponseDto;
import com.example.demo.dto.technology_stack.response.TechnologyStackInfoResponseDto;
import com.example.demo.dto.trust_grade.response.TrustGradeResponseDto;
import com.example.demo.dto.user.response.UserBoardDetailResponseDto;
import com.example.demo.dto.user.response.UserProjectResponseDto;
import com.example.demo.global.exception.customexception.BoardCustomException;
import com.example.demo.model.board.Board;
import com.example.demo.model.board.BoardPosition;
import com.example.demo.model.project.ProjectTechnology;
import com.example.demo.model.technology_stack.TechnologyStack;
import com.example.demo.repository.board.BoardRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
    private final BoardRepository boardRepository;

    /**
     * 게시글 목록 검색
     *
     * @param dto
     * @return List<BoardSearchResponseDto>
     */
    @Transactional(readOnly = true)
    public Page<BoardSearchResponseDto> search(BoardSearchRequestDto dto, Pageable pageable) {
        return boardRepository.getBoardSearchPage(dto, pageable);
    }

    public Board findById(Long boardId) {
        return boardRepository
                .findById(boardId)
                .orElseThrow(() -> BoardCustomException.NOT_FOUND_BOARD);
    }

    public Board save(Board board) {
        return boardRepository.save(board);
    }

    /**
     * 게시글 상세 조회
     *
     * @param boardId
     * @return
     */
    public BoardTotalDetailResponseDto getDetail(Long boardId) {
        Board board = findById(boardId);

        // boardDetailResponseDto 생성
        UserBoardDetailResponseDto userBoardDetailResponseDto =
                UserBoardDetailResponseDto.of(board.getUser());

        List<BoardPositionDetailResponseDto> boardPositionDetailResponseDtos = new ArrayList<>();
        for (BoardPosition boardPosition : board.getPositions()) {
            PositionResponseDto positionResponseDto =
                    PositionResponseDto.of(boardPosition.getPosition());
            BoardPositionDetailResponseDto boardPositionDetailResponseDto =
                    BoardPositionDetailResponseDto.of(boardPosition, positionResponseDto);
            boardPositionDetailResponseDtos.add(boardPositionDetailResponseDto);
        }
        BoardDetailResponseDto boardDetailResponseDto =
                BoardDetailResponseDto.of(
                        board, userBoardDetailResponseDto, boardPositionDetailResponseDtos);

        // ProjectDetailResponseDto 부분
        TrustGradeResponseDto trustGradeDto =
                TrustGradeResponseDto.of(board.getProject().getTrustGrade());
        UserProjectResponseDto userProjectResponseDto =
                UserProjectResponseDto.of(board.getProject());

        // 기술 스택 부분
        List<TechnologyStackInfoResponseDto> technologyStackInfoResponseDtos = new ArrayList<>();
        for (ProjectTechnology projectTechnology : board.getProject().getProjectTechnologies()) {
            TechnologyStack technologyStack = projectTechnology.getTechnologyStack();

            TechnologyStackInfoResponseDto technologyStackInfoResponseDto =
                    TechnologyStackInfoResponseDto.of(
                            technologyStack.getId(), technologyStack.getName());
            technologyStackInfoResponseDtos.add(technologyStackInfoResponseDto);
        }
        ProjectDetailResponseDto projectDetailResponseDto =
                ProjectDetailResponseDto.of(
                        board.getProject(),
                        trustGradeDto,
                        userProjectResponseDto,
                        technologyStackInfoResponseDtos);

        // boardPageView 증가
        board.updatePageView();

        return BoardTotalDetailResponseDto.of(boardDetailResponseDto, projectDetailResponseDto);
    }

    /**
     * 게시글 삭제
     *
     * @param boardId
     */
    public void delete(Long boardId) {
        Board board = findById(boardId);
        boardRepository.delete(board);
    }
}
