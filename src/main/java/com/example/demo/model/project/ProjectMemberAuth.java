package com.example.demo.model.project;

import javax.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

// 프로젝트 관리 권한 엔티티
@Entity
@Table(name = "project_member_auth")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ProjectMemberAuth {

    @Id
    @Column(name = "project_member_auth_id")
    private Long id;

    @Column(name = "project_member_auth_name")
    private String name;

    @Column(name = "milestone_change_YN")
    private boolean milestoneChangeYN;

    @Column(name = "work_change_yn")
    private boolean workChangeYN;

    @Builder
    public ProjectMemberAuth(
            Long id, String name, boolean milestoneChangeYN, boolean workChangeYN) {
        this.id = id;
        this.name = name;
        this.milestoneChangeYN = milestoneChangeYN;
        this.workChangeYN = workChangeYN;
    }
}
