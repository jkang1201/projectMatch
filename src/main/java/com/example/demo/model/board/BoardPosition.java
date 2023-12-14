package com.example.demo.model.board;

import com.example.demo.global.common.BaseTimeEntity;
import com.example.demo.model.position.Position;
import javax.persistence.*;
import lombok.*;

@Entity
@Table(name = "board_position")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class BoardPosition extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "board_position_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "position_id")
    private Position position;

    @Builder
    public BoardPosition(Board board, Position position) {
        this.board = board;
        this.position = position;
    }
}
