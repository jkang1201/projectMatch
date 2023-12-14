package com.example.demo.service.board;

import com.example.demo.dto.board.request.BoardSearchRequestDto;
import com.example.demo.dto.board.response.BoardSearchResponseDto;
import com.example.demo.dto.board.response.BoardTotalDetailResponseDto;
import com.example.demo.model.board.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface BoardService {

    @Transactional(readOnly = true)
    public Page<BoardSearchResponseDto> search(BoardSearchRequestDto dto, Pageable pageable);

    public Board findById(Long boardId);

    public Board save(Board board);

    public BoardTotalDetailResponseDto getDetail(Long boardId);

    public void delete(Long boardId);
}
