package com.example.demo.service.alert;

import com.example.demo.global.exception.customexception.AlertCustomException;
import com.example.demo.model.alert.Alert;
import com.example.demo.model.project.Project;
import com.example.demo.repository.alert.AlertRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AlertServiceImpl implements AlertService {
    private final AlertRepository alertRepository;

    @Override
    public Alert findById(Long id) {
        return alertRepository.findById(id).orElseThrow(() -> AlertCustomException.NOT_FOUND_ALERT);
    }

    @Override
    public Alert save(Alert alert) {
        return alertRepository.save(alert);
    }

    public List<Alert> findAlertsByProjectId(Project project) {
        return alertRepository
                .findAlertsByProject(project)
                .orElseThrow(() -> AlertCustomException.NOT_FOUND_ALERT);
    }

    public List<Alert> findRecruitAlertsByProject(Project project) {
        return alertRepository
                .findRecruitAlertsByProject(project)
                .orElseThrow(() -> AlertCustomException.NOT_FOUND_ALERT);
    }

    public List<Alert> findWorkAlertsByProject(Project project) {
        return alertRepository
                .findWorkAlertsByProject(project)
                .orElseThrow(() -> AlertCustomException.NOT_FOUND_ALERT);
    }

    public List<Alert> findCrewAlertsByProject(Project project) {
        return alertRepository
                .findCrewAlertsByProject(project)
                .orElseThrow(() -> AlertCustomException.NOT_FOUND_ALERT);
    }
}
