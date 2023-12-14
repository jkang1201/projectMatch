package com.example.demo.service.milestone;

import com.example.demo.dto.milestone.MilestoneCreateResponseDto;
import com.example.demo.dto.milestone.request.MilestoneCreateRequestDto;
import com.example.demo.dto.milestone.response.MilestoneReadResponseDto;
import com.example.demo.model.milestone.Milestone;
import com.example.demo.model.project.Project;
import com.example.demo.service.project.ProjectService;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class MileStoneFacade {
    private final MilestoneService mileStoneService;
    private final ProjectService projectService;

    /**
     * 마일스톤 생성 TODO : 매니저만 가능하도록 USER 가져와서 비교해봐야함.
     *
     * @param projectId
     * @param milestoneCreateRequestDto
     * @return
     */
    public MilestoneCreateResponseDto create(
            Long projectId, MilestoneCreateRequestDto milestoneCreateRequestDto) {
        Project project = projectService.findById(projectId);
        Milestone milestone = milestoneCreateRequestDto.toMileStoneEntity(project);
        Milestone savedMilestone = mileStoneService.save(milestone);

        return MilestoneCreateResponseDto.of(savedMilestone);
    }

    @Transactional(readOnly = true)
    public List<MilestoneReadResponseDto> getAllByProject(Long projectId) {
        Project project = projectService.findById(projectId);
        List<Milestone> milestonesByProject = mileStoneService.findMilestonesByProject(project);

        List<MilestoneReadResponseDto> milestoneReadResponseDtos = new ArrayList<>();
        for (Milestone milestone : milestonesByProject) {
            MilestoneReadResponseDto milestoneReadResponseDto =
                    MilestoneReadResponseDto.of(milestone);
            milestoneReadResponseDtos.add(milestoneReadResponseDto);
        }

        return milestoneReadResponseDtos;
    }
}
