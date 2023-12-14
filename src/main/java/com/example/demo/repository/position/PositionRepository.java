package com.example.demo.repository.position;

import com.example.demo.model.position.Position;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PositionRepository extends JpaRepository<Position, Long> {}
