package com.example.demo.dto.user.response;

import com.example.demo.dto.position.response.PositionInfoResponseDto;
import com.example.demo.dto.technology_stack.response.TechnologyStackInfoResponseDto;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserUpdateResponseDto {

    private String email;

    private String nickname;

    private PositionInfoResponseDto position;

    private List<TechnologyStackInfoResponseDto> techStacks;

    private String intro;

    public static UserUpdateResponseDto of(
            String email,
            String nickname,
            PositionInfoResponseDto positionInfoResponseDto,
            List<TechnologyStackInfoResponseDto> techStacks,
            String intro) {
        return UserUpdateResponseDto.builder()
                .email(email)
                .nickname(nickname)
                .position(positionInfoResponseDto)
                .techStacks(techStacks)
                .intro(intro)
                .build();
    }
}
