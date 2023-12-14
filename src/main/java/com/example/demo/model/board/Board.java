package com.example.demo.model.board;

import com.example.demo.dto.board.request.BoardUpdateRequestDto;
import com.example.demo.global.common.BaseTimeEntity;
import com.example.demo.model.project.Project;
import com.example.demo.model.user.User;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "board")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Board extends BaseTimeEntity {
    @Id
    @Column(name = "board_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String title;
    private String content;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    @ColumnDefault("0")
    private int pageView;

    @ColumnDefault("false")
    private boolean completeStatus;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String contact;

    @OneToMany(mappedBy = "board", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<BoardPosition> positions = new ArrayList<>();

    @Builder
    private Board(
            String title,
            String content,
            Project project,
            int pageView,
            boolean completeStatus,
            User user,
            String contact) {
        this.title = title;
        this.content = content;
        this.project = project;
        this.pageView = pageView;
        this.completeStatus = completeStatus;
        this.user = user;
        this.contact = contact;
    }

    public void updateBoard(BoardUpdateRequestDto dto) {
        this.title = dto.getTitle();
        this.content = dto.getContent();
        this.contact = dto.getContact();
    }

    public void setPositions(List<BoardPosition> list) {
        this.positions.clear();
        this.positions.addAll(list);
    }

    public void updatePageView() {
        this.pageView++;
    }
}
