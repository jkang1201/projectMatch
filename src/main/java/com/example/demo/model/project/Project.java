package com.example.demo.model.project;

import com.example.demo.constant.ProjectStatus;
import com.example.demo.dto.project.request.ProjectUpdateRequestDto;
import com.example.demo.global.common.BaseTimeEntity;
import com.example.demo.model.trust_grade.TrustGrade;
import com.example.demo.model.user.User;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import lombok.*;

// 프로젝트 엔티티
@Entity
@Table(name = "project")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Project extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "project_id")
    private Long id;

    private String name;
    private String subject;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trust_grade_id")
    private TrustGrade trustGrade;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "project_status")
    @Enumerated(value = EnumType.STRING)
    private ProjectStatus status;

    @Column(nullable = true)
    private int crewNumber;

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @OneToMany(mappedBy = "project", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<ProjectMember> projectMembers = new ArrayList<>();

    @OneToMany(mappedBy = "project", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<ProjectTechnology> projectTechnologies = new ArrayList<>();

    @Builder
    public Project(
            String name,
            String subject,
            TrustGrade trustGrade,
            User user,
            ProjectStatus status,
            int crewNumber,
            LocalDateTime startDate,
            LocalDateTime endDate) {
        this.name = name;
        this.subject = subject;
        this.trustGrade = trustGrade;
        this.user = user;
        this.status = status;
        this.crewNumber = crewNumber;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public void updateProject(ProjectUpdateRequestDto dto, TrustGrade trustGrade) {
        this.name = dto.getName();
        this.subject = dto.getSubject();
        this.trustGrade = trustGrade;
        this.crewNumber = dto.getCrewNumber();
        this.startDate = dto.getStartDate();
        this.endDate = dto.getEndDate();
    }

    public void endProject() {
        this.status = ProjectStatus.FINISH;
    }

    public void changeProjectMembers(List<ProjectMember> projectMembers) {
        this.projectMembers.clear();
        this.projectMembers.addAll(projectMembers);
    }

    public void changeProjectTechnologys(List<ProjectTechnology> projectTechnologies) {
        this.projectTechnologies.clear();
        this.projectTechnologies.addAll(projectTechnologies);
    }
}
