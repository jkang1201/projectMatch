package com.example.demo.controller.board;

import com.example.demo.dto.board.request.BoardSearchRequestDto;
import com.example.demo.dto.board.response.BoardSearchResponseDto;
import com.example.demo.dto.board.response.BoardTotalDetailResponseDto;
import com.example.demo.dto.board_project.request.BoardProjectCreateRequestDto;
import com.example.demo.dto.board_project.request.BoardProjectUpdateRequestDto;
import com.example.demo.dto.board_project.response.BoardProjectCreateResponseDto;
import com.example.demo.dto.board_project.response.BoardProjectUpdateResponseDto;
import com.example.demo.dto.common.ResponseDto;
import com.example.demo.service.board.BoardFacade;
import com.example.demo.service.board.BoardService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/board")
public class BoardController {

    private final BoardService boardService;
    private final BoardFacade boardFacade;

    @PostMapping(value = {"/search", "/search/{page}"})
    public ResponseEntity<ResponseDto<?>> search(
            @RequestBody BoardSearchRequestDto dto, @PathVariable("page") Optional<Integer> page) {
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 5);
        Page<BoardSearchResponseDto> result = boardService.search(dto, pageable);
        return new ResponseEntity<>(ResponseDto.success("success", result), HttpStatus.OK);
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<ResponseDto<?>> getDetail(@PathVariable("boardId") Long boardId) {
        BoardTotalDetailResponseDto result = boardService.getDetail(boardId);
        return new ResponseEntity<>(ResponseDto.success("success", result), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<ResponseDto<?>> create(
            @RequestBody BoardProjectCreateRequestDto requestDto) {
        BoardProjectCreateResponseDto result = boardFacade.create(requestDto);
        return new ResponseEntity<>(ResponseDto.success("success", result), HttpStatus.OK);
    }

    @PatchMapping("/{boardId}")
    public ResponseEntity<ResponseDto<?>> update(
            @PathVariable("boardId") Long boardId,
            @RequestBody BoardProjectUpdateRequestDto requestDto) {
        try {
            BoardProjectUpdateResponseDto result = boardFacade.update(boardId, requestDto);
            return new ResponseEntity<>(ResponseDto.success("success", result), HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(ResponseDto.fail(ex.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<ResponseDto<?>> delete(@PathVariable("boardId") Long boardId) {
        boardService.delete(boardId);
        return new ResponseEntity<>(ResponseDto.success("success", null), HttpStatus.NO_CONTENT);
    }
}
