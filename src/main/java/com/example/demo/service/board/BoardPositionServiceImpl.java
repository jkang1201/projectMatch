package com.example.demo.service.board;

import com.example.demo.model.board.Board;
import com.example.demo.model.board.BoardPosition;
import com.example.demo.model.position.Position;
import com.example.demo.repository.board.BoardPositionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardPositionServiceImpl implements BoardPositionService {

    private final BoardPositionRepository boardPositionRepository;

    public BoardPosition getBoardPositionEntity(Board board, Position position) {
        return BoardPosition.builder().board(board).position(position).build();
    }

    @Override
    public BoardPosition save(BoardPosition boardPosition) {
        return boardPositionRepository.save(boardPosition);
    }
}
