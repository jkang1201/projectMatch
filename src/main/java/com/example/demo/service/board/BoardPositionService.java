package com.example.demo.service.board;

import com.example.demo.model.board.Board;
import com.example.demo.model.board.BoardPosition;
import com.example.demo.model.position.Position;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface BoardPositionService {

    public BoardPosition getBoardPositionEntity(Board board, Position position);

    public BoardPosition save(BoardPosition boardPosition);
}
