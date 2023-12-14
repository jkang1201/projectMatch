package com.example.demo.controller.project;

import com.example.demo.dto.common.ResponseDto;
import com.example.demo.dto.projectmember.response.ProjectMemberReadCrewDetailResponseDto;
import com.example.demo.dto.projectmember.response.ProjectMemberReadTotalProjectCrewsResponseDto;
import com.example.demo.service.project.ProjectMemberFacade;
import com.example.demo.service.project.ProjectMemberService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/projectmember")
@AllArgsConstructor
public class ProjectMemberController {

    private final ProjectMemberService projectMemberService;
    private final ProjectMemberFacade projectMemberFacade;

    @PostMapping("/{projectMemberId}/withdrawl")
    public ResponseEntity<ResponseDto<?>> withdrawl(
            @PathVariable("projectMemberId") Long projectMemberId) {
        projectMemberFacade.sendWithdrawlAlert(projectMemberId);
        return new ResponseEntity<>(ResponseDto.success("success"), HttpStatus.OK);
    }

    @PostMapping("/{projectMemberId}/withdrawl/confirm")
    public ResponseEntity<ResponseDto<?>> withdrawlConfirm(
            @PathVariable("projectMemberId") Long projectMemberId) {
        projectMemberService.withdrawlConfirm(projectMemberId);
        return new ResponseEntity<>(ResponseDto.success("success"), HttpStatus.OK);
    }

    @PostMapping("/{projectMemberId}/withdrawl/force")
    public ResponseEntity<ResponseDto<?>> withdrawlForce(
            @PathVariable("projectMemberId") Long projectMemberId) {
        projectMemberService.withdrawlForce(projectMemberId);
        return new ResponseEntity<>(ResponseDto.success("success"), HttpStatus.OK);
    }

    @GetMapping("/{projectMemberId}")
    public ResponseEntity<ResponseDto<?>> getDetail(
            @PathVariable("projectMemberId") Long projectMemberId) {
        ProjectMemberReadCrewDetailResponseDto result =
                projectMemberFacade.getCrewDetail(projectMemberId);
        return new ResponseEntity<>(ResponseDto.success("success", result), HttpStatus.OK);
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<ResponseDto<?>> getCrewDetailsByProject(
            @PathVariable("projectId") Long projectId) {
        ProjectMemberReadTotalProjectCrewsResponseDto result =
                projectMemberFacade.getCrewsByProject(projectId);
        return new ResponseEntity<>(ResponseDto.success("success", result), HttpStatus.OK);
    }
}
