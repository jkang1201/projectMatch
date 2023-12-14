package com.example.demo.repository.milestone;

import com.example.demo.model.milestone.Milestone;
import com.example.demo.model.project.Project;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MileStoneRepository extends JpaRepository<Milestone, Long> {
    @Override
    Optional<Milestone> findById(Long id);

    Optional<List<Milestone>> findMilestonesByProject(Project project);
}
