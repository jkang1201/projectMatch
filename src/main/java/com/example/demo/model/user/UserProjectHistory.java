package com.example.demo.model.user;

import com.example.demo.constant.UserProjectHistoryStatus;
import com.example.demo.global.common.BaseTimeEntity;
import com.example.demo.model.project.Project;
import java.time.LocalDateTime;
import javax.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

// 사용자 프로젝트 이력 엔티티
@Entity
@Table(name = "user_project_history")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class UserProjectHistory extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_project_history_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Enumerated(value = EnumType.STRING)
    private UserProjectHistoryStatus status;

    @Builder
    public UserProjectHistory(
            User user,
            Project project,
            LocalDateTime startDate,
            LocalDateTime endDate,
            UserProjectHistoryStatus status) {
        this.user = user;
        this.project = project;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
    }
}
