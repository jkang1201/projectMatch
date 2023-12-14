package com.example.demo.repository.alert;

import com.example.demo.model.alert.Alert;
import com.example.demo.model.project.Project;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AlertRepository extends JpaRepository<Alert, Long> {

    Optional<List<Alert>> findAlertsByProject(Project project);

    @Query(
            "select alert from Alert alert where alert.project = :#{#project} and alert.type = com.example.demo.constant.AlertType.RECRUIT")
    Optional<List<Alert>> findRecruitAlertsByProject(@Param(value = "project") Project project);

    @Query(
            "select alert from Alert alert where alert.project = :#{#project} and alert.type = com.example.demo.constant.AlertType.WORK")
    Optional<List<Alert>> findWorkAlertsByProject(@Param(value = "project") Project project);

    @Query(
            "select alert from Alert alert where alert.project = :#{#project} and (alert.type = com.example.demo.constant.AlertType.ADD or alert.type = com.example.demo.constant.AlertType.WITHDRAWL or alert.type = com.example.demo.constant.AlertType.FORCEDWITHDRAWL)")
    Optional<List<Alert>> findCrewAlertsByProject(@Param(value = "project") Project project);
}
