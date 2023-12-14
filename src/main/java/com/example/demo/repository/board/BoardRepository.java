package com.example.demo.repository.board;

import com.example.demo.model.board.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface BoardRepository
        extends JpaRepository<Board, Long>,
                QuerydslPredicateExecutor<Board>,
                BoardRepositoryCustom {}
