package com.example.demo.repository.board;

import com.example.demo.dto.board.request.BoardSearchRequestDto;
import com.example.demo.dto.board.response.BoardSearchResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardRepositoryCustom {

    Page<BoardSearchResponseDto> getBoardSearchPage(
            BoardSearchRequestDto boardSearchRequestDto, Pageable pageable);
}
