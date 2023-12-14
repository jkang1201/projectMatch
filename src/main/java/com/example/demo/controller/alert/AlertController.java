package com.example.demo.controller.alert;

import com.example.demo.dto.alert.AlertCreateRequestDto;
import com.example.demo.dto.alert.response.AlertInfoResponseDto;
import com.example.demo.dto.common.ResponseDto;
import com.example.demo.service.alert.AlertFacade;
import com.example.demo.service.alert.AlertService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AlertController {

    private final AlertFacade alertFacade;
    private final AlertService alertService;

    @PostMapping("/api/alert")
    public ResponseEntity<ResponseDto<?>> send(
            @RequestBody AlertCreateRequestDto alertCreateRequestDto) {
        alertFacade.send(alertCreateRequestDto);
        return new ResponseEntity<>(ResponseDto.success("success"), HttpStatus.OK);
    }

    @GetMapping("/api/alert/project/{projectId}")
    public ResponseEntity<ResponseDto<?>> getAllByProject(
            @PathVariable("projectId") Long projectId) {
        List<AlertInfoResponseDto> result = alertFacade.getAllByProject(projectId);
        return new ResponseEntity<>(ResponseDto.success("success", result), HttpStatus.OK);
    }

    @GetMapping("/api/alert/project/{projectId}/recruits")
    public ResponseEntity<ResponseDto<?>> getRecruitsByProject(
            @PathVariable("projectId") Long projectId) {
        List<AlertInfoResponseDto> result = alertFacade.getRecruitsByProject(projectId);
        return new ResponseEntity<>(ResponseDto.success("success", result), HttpStatus.OK);
    }

    @GetMapping("/api/alert/project/{projectId}/works")
    public ResponseEntity<ResponseDto<?>> getWorksByProject(
            @PathVariable("projectId") Long projectId) {
        List<AlertInfoResponseDto> result = alertFacade.getWorksByProject(projectId);
        return new ResponseEntity<>(ResponseDto.success("success", result), HttpStatus.OK);
    }

    @GetMapping("/api/alert/project/{projectId}/crews")
    public ResponseEntity<ResponseDto<?>> getCrewsByProject(
            @PathVariable("projectId") Long projectId) {
        List<AlertInfoResponseDto> result = alertFacade.getCrewsByProject(projectId);
        return new ResponseEntity<>(ResponseDto.success("success", result), HttpStatus.OK);
    }
}
