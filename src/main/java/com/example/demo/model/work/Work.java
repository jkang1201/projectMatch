package com.example.demo.model.work;

import com.example.demo.dto.work.request.WorkUpdateCompleteStatusRequestDto;
import com.example.demo.dto.work.request.WorkUpdateContentRequestDto;
import com.example.demo.dto.work.request.WorkUpdateRequestDto;
import com.example.demo.global.common.BaseTimeEntity;
import com.example.demo.model.milestone.Milestone;
import com.example.demo.model.project.Project;
import com.example.demo.model.project.ProjectMember;
import com.example.demo.model.user.User;
import java.time.LocalDateTime;
import javax.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

// 프로젝트 업무 엔티티
@Entity
@Table(name = "work")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Work extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "work_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "milestone_id")
    private Milestone milestone;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User assignedUserId;

    @Column(name = "work_content")
    private String content;

    @Column(name = "expire_statue")
    private boolean expireStatus;

    @Column(name = "complete_status")
    private boolean completeStatus;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @OneToOne
    @JoinColumn(name = "project_member_id")
    private ProjectMember lastModifiedMember;

    @Column(name = "complete_date")
    private LocalDateTime completeDate;

    @Builder
    public Work(
            Project project,
            Milestone milestone,
            User assignedUserId,
            String content,
            boolean expireStatus,
            boolean completeStatus,
            LocalDateTime startDate,
            LocalDateTime endDate,
            ProjectMember lastModifiedMember) {
        this.project = project;
        this.milestone = milestone;
        this.assignedUserId = assignedUserId;
        this.content = content;
        this.expireStatus = expireStatus;
        this.completeStatus = completeStatus;
        this.startDate = startDate;
        this.endDate = endDate;
        this.lastModifiedMember = lastModifiedMember;
        this.completeDate = null;
    }

    public void update(WorkUpdateRequestDto dto) {
        this.content = dto.getContent();
        this.startDate = dto.getStartDate();
        this.endDate = dto.getEndDate();
    }

    public void updateContent(WorkUpdateContentRequestDto dto, ProjectMember projectMember) {
        this.content = dto.getContent();
        this.lastModifiedMember = projectMember;
    }

    public void updateCompleteStatus(
            WorkUpdateCompleteStatusRequestDto dto, ProjectMember projectMember) {
        this.completeStatus = dto.getCompleteStatus();
        this.lastModifiedMember = projectMember;
    }

    public void updateAssignedUserId(User user, ProjectMember projectMember) {
        this.assignedUserId = user;
        this.lastModifiedMember = projectMember;
    }

    public void updateCompleteDate(LocalDateTime completeDate, ProjectMember projectMember) {
        this.completeDate = completeDate;
        this.lastModifiedMember = projectMember;
    }
}
