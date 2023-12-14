package com.example.demo.service.alert;

import com.example.demo.model.alert.Alert;
import com.example.demo.model.project.Project;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface AlertService {

    public Alert findById(Long id);

    public Alert save(Alert alert);

    public List<Alert> findAlertsByProjectId(Project project);

    public List<Alert> findRecruitAlertsByProject(Project project);

    public List<Alert> findWorkAlertsByProject(Project project);

    public List<Alert> findCrewAlertsByProject(Project project);
}
