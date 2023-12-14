package com.example.demo.controller.work;

import com.example.demo.dto.common.ResponseDto;
import com.example.demo.dto.work.request.*;
import com.example.demo.service.work.WorkFacade;
import com.example.demo.service.work.WorkService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class WorkController {

    private final WorkService workService;
    private final WorkFacade workFacade;

    @PostMapping("/api/work/project/{projectId}/milestone/{milestoneId}")
    public ResponseEntity<ResponseDto<?>> create(
            @PathVariable("projectId") Long projectId,
            @PathVariable("milestoneId") Long milestoneId,
            @RequestBody WorkCreateRequestDto workCreateRequestDto) {
        workFacade.create(projectId, milestoneId, workCreateRequestDto);
        return new ResponseEntity<>(ResponseDto.success("success"), HttpStatus.OK);
    }

    @GetMapping("/api/work/project/{projectId}")
    public ResponseEntity<ResponseDto<?>> getAllByProject(
            @PathVariable("projectId") Long projectId) {
        List<WorkReadResponseDto> result = workFacade.getAllByProject(projectId);
        return new ResponseEntity<>(ResponseDto.success("success", result), HttpStatus.OK);
    }

    @GetMapping("/api/work/project/{projectId}/milestone/{milestoneId}")
    public ResponseEntity<ResponseDto<?>> getAllByMilestone(
            @PathVariable("projectId") Long projectId,
            @PathVariable("milestoneId") Long milestoneId) {
        List<WorkReadResponseDto> result = workFacade.getAllByMilestone(projectId, milestoneId);
        return new ResponseEntity<>(ResponseDto.success("success", result), HttpStatus.OK);
    }

    @GetMapping("/api/work/{workId}")
    public ResponseEntity<ResponseDto<?>> getOne(@PathVariable("workId") Long workId) {
        WorkReadResponseDto result = workService.getOne(workId);
        return new ResponseEntity<>(ResponseDto.success("success", result), HttpStatus.OK);
    }

    @PatchMapping("/api/work/{workId}")
    public ResponseEntity<ResponseDto<?>> update(
            @PathVariable("workId") Long workId,
            @RequestBody WorkUpdateRequestDto workUpdateRequestDto) {
        workFacade.update(workId, workUpdateRequestDto);
        return new ResponseEntity<>(ResponseDto.success("success"), HttpStatus.OK);
    }

    @DeleteMapping("/api/work/{workId}")
    public ResponseEntity<ResponseDto<?>> update(@PathVariable("workId") Long workId) {
        workService.delete(workId);
        return new ResponseEntity<>(ResponseDto.success("success"), HttpStatus.OK);
    }

    @PatchMapping("/api/work/{workId}/content")
    public ResponseEntity<ResponseDto<?>> updateContent(
            @PathVariable("workId") Long workId,
            @RequestBody WorkUpdateContentRequestDto workUpdateContentRequestDto) {
        workFacade.updateContent(workId, workUpdateContentRequestDto);
        return new ResponseEntity<>(ResponseDto.success("success"), HttpStatus.OK);
    }

    @PatchMapping("/api/work/{workId}/complete")
    public ResponseEntity<ResponseDto<?>> updateCompleteStatus(
            @PathVariable("workId") Long workId,
            @RequestBody WorkUpdateCompleteStatusRequestDto workUpdateCompleteStatusRequestDto) {
        workFacade.updateCompleteStatus(workId, workUpdateCompleteStatusRequestDto);
        return new ResponseEntity<>(ResponseDto.success("success"), HttpStatus.OK);
    }

    @PatchMapping("/api/work/{workId}/assign")
    public ResponseEntity<ResponseDto<?>> updateAssign(
            @PathVariable("workId") Long workId,
            @RequestBody WorkUpdateAssignUserRequestDto workUpdateAssignUserRequestDto) {
        workFacade.updateAssignUser(workId, workUpdateAssignUserRequestDto);
        return new ResponseEntity<>(ResponseDto.success("success"), HttpStatus.OK);
    }
}
