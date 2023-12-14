package com.example.demo.controller.milestone;

import com.example.demo.dto.common.ResponseDto;
import com.example.demo.dto.milestone.MilestoneCreateResponseDto;
import com.example.demo.dto.milestone.request.MileStoneUpdateRequestDto;
import com.example.demo.dto.milestone.request.MilestoneCreateRequestDto;
import com.example.demo.dto.milestone.request.MilestoneUpdateContentRequestDto;
import com.example.demo.dto.milestone.request.MilestoneUpdateDateRequestDto;
import com.example.demo.dto.milestone.response.MilestoneReadResponseDto;
import com.example.demo.service.milestone.MileStoneFacade;
import com.example.demo.service.milestone.MilestoneService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MileStoneController {

    private final MilestoneService milestoneService;
    private final MileStoneFacade mileStoneFacade;

    @PostMapping("/api/milestone/project/{projectId}")
    public ResponseEntity<ResponseDto<?>> create(
            @PathVariable("projectId") Long projectId,
            @RequestBody MilestoneCreateRequestDto milestoneCreateRequestDto) {
        MilestoneCreateResponseDto result =
                mileStoneFacade.create(projectId, milestoneCreateRequestDto);
        return new ResponseEntity<>(ResponseDto.success("success", result), HttpStatus.OK);
    }

    @GetMapping("/api/milestone/project/{projectId}")
    public ResponseEntity<ResponseDto<?>> getAllByProject(
            @PathVariable("projectId") Long projectId) {
        List<MilestoneReadResponseDto> result = mileStoneFacade.getAllByProject(projectId);
        return new ResponseEntity<>(ResponseDto.success("success", result), HttpStatus.OK);
    }

    @GetMapping("/api/milestone/{milestoneId}")
    public ResponseEntity<ResponseDto<?>> getOne(@PathVariable("milestoneId") Long mileStoneId) {
        MilestoneReadResponseDto result = milestoneService.getOne(mileStoneId);
        return new ResponseEntity<>(ResponseDto.success("success", result), HttpStatus.OK);
    }

    @PatchMapping("/api/milestone/{milestoneId}")
    public ResponseEntity<ResponseDto<?>> update(
            @PathVariable("milestoneId") Long mileStoneId,
            @RequestBody MileStoneUpdateRequestDto mileStoneUpdateRequestDto) {
        milestoneService.update(mileStoneId, mileStoneUpdateRequestDto);
        return new ResponseEntity<>(ResponseDto.success("success"), HttpStatus.OK);
    }

    @DeleteMapping("/api/milestone/{milestoneId}")
    public ResponseEntity<ResponseDto<?>> delete(@PathVariable("milestoneId") Long mileStoneId) {
        milestoneService.delete(mileStoneId);
        return new ResponseEntity<>(ResponseDto.success("success"), HttpStatus.OK);
    }

    @PatchMapping("/api/milestone/{milestoneId}/content")
    public ResponseEntity<ResponseDto<?>> updateContent(
            @PathVariable("milestoneId") Long mileStoneId,
            @RequestBody MilestoneUpdateContentRequestDto milestoneUpdateContentRequestDto) {
        milestoneService.updateContent(mileStoneId, milestoneUpdateContentRequestDto);
        return new ResponseEntity<>(ResponseDto.success("success"), HttpStatus.OK);
    }

    @PatchMapping("/api/milestone/{milestoneId}/date")
    public ResponseEntity<ResponseDto<?>> updateDate(
            @PathVariable("milestoneId") Long mileStoneId,
            @RequestBody MilestoneUpdateDateRequestDto milestoneUpdateDateRequestDto) {
        milestoneService.updateDate(mileStoneId, milestoneUpdateDateRequestDto);
        return new ResponseEntity<>(ResponseDto.success("success"), HttpStatus.OK);
    }
}
