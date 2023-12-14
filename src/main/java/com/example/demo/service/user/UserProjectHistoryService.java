package com.example.demo.service.user;

import com.example.demo.dto.user.response.UserProjectHistoryInfoResponseDto;
import com.example.demo.model.project.Project;
import com.example.demo.model.user.User;
import com.example.demo.model.user.UserProjectHistory;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UserProjectHistoryService {

    public UserProjectHistory toUserProjectHistoryEntity(User user, Project project);

    public UserProjectHistory save(UserProjectHistory userProjectHistory);

    // 회원 프로젝트 이력 전체 개수 조회
    Long getUserProjectHistoryTotalCount(Long userId);

    // 회원 프로젝트 이력 조회
    List<UserProjectHistoryInfoResponseDto> getUserProjectHistoryList(Long userId, int pageNumber);
}
