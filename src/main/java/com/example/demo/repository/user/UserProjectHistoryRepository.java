package com.example.demo.repository.user;

import com.example.demo.model.user.UserProjectHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProjectHistoryRepository
        extends JpaRepository<UserProjectHistory, Long>, UserProjectHistoryRepositoryCustom {}
