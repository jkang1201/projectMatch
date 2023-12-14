package com.example.demo.dto.board.request;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BoardSearchRequestDto {
    private List<Long> technologyIds;
    private String keyWord;
    private List<Long> positionIds;
}
