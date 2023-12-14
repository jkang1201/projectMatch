package com.example.demo.dto.alert;

import com.example.demo.constant.AlertType;
import com.example.demo.model.alert.Alert;
import com.example.demo.model.milestone.Milestone;
import com.example.demo.model.position.Position;
import com.example.demo.model.project.Project;
import com.example.demo.model.user.User;
import com.example.demo.model.work.Work;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AlertCreateRequestDto {
    @NotNull(message = "프로젝트 아이디는 필수입니다.")
    private Long projectId;

    @NotNull(message = "체크해야 하는 유저 아이디는 필수입니다.")
    private Long checkUserId;

    @NotNull(message = "보내는 유저 아이디는 필수입니다.")
    private Long sendUserId;

    private Long workId;
    private Long milestoneId;
    private Long positionId;

    @NotNull(message = "알림 타입은 필수입니다.")
    private AlertType type;

    @NotNull(message = "알림 내용은 필수입니다.")
    private String content;

    public Alert toAlertEntity(
            Project project,
            User checkUser,
            User sendUser,
            Work work,
            Milestone milestone,
            Position position) {
        return Alert.builder()
                .project(project)
                .checkUser(checkUser)
                .sendUser(sendUser)
                .work(work)
                .milestone(milestone)
                .position(position)
                .content(this.getContent())
                .type(this.getType())
                .checked_YN(false)
                .build();
    }
}
