package com.example.demo.service.user;

import com.example.demo.model.technology_stack.TechnologyStack;
import com.example.demo.model.user.User;
import com.example.demo.model.user.UserTechnologyStack;
import java.util.List;

public interface UserTechnologyStackService {

    // 회원 기술스택 목록 저장 및 반환
    List<UserTechnologyStack> saveUserTechStacksAndReturnResponse(
            User user, List<TechnologyStack> technologyStackList);

    // 회원 기술스택 목록 삭제
    void deleteUserTechStacks(List<UserTechnologyStack> technologyStackList);
}
