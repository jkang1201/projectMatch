package com.example.demo.repository.project;

import com.example.demo.model.project.ProjectMemberAuth;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectMemberAuthRepository extends JpaRepository<ProjectMemberAuth, Long> {
    Optional<ProjectMemberAuth> findTopByOrderByIdDesc();
}
