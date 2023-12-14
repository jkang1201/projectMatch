package com.example.demo.repository.technology_stack;

import com.example.demo.model.technology_stack.TechnologyStack;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TechnologyStackRepository
        extends JpaRepository<TechnologyStack, Long>, TechnologyStackRepositoryCustom {}
