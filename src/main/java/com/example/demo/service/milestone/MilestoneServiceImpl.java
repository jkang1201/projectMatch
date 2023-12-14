package com.example.demo.service.milestone;

import com.example.demo.dto.milestone.request.MileStoneUpdateRequestDto;
import com.example.demo.dto.milestone.request.MilestoneUpdateContentRequestDto;
import com.example.demo.dto.milestone.request.MilestoneUpdateDateRequestDto;
import com.example.demo.dto.milestone.response.MilestoneReadResponseDto;
import com.example.demo.global.exception.customexception.MilestoneCustomException;
import com.example.demo.model.milestone.Milestone;
import com.example.demo.model.project.Project;
import com.example.demo.repository.milestone.MileStoneRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MilestoneServiceImpl implements MilestoneService {
    private final MileStoneRepository mileStoneRepository;

    public Milestone findById(Long id) {
        return mileStoneRepository
                .findById(id)
                .orElseThrow(() -> MilestoneCustomException.NOT_FOUND_MILESTONE);
    }

    public Milestone save(Milestone milestone) {
        return mileStoneRepository.save(milestone);
    }

    public List<Milestone> findMilestonesByProject(Project project) {
        return mileStoneRepository
                .findMilestonesByProject(project)
                .orElseThrow(() -> MilestoneCustomException.NOT_FOUND_MILESTONE);
    }

    @Transactional(readOnly = true)
    public MilestoneReadResponseDto getOne(Long milestoneId) {
        Milestone milestone = findById(milestoneId);
        return MilestoneReadResponseDto.of(milestone);
    }

    /**
     * 프로젝트 내 마일스톤 수정(*매니저만 가능) TODO : 매니저만 수정 가능하도록 변경
     *
     * @param milestoneId
     * @param mileStoneUpdateRequestDto
     */
    public void update(Long milestoneId, MileStoneUpdateRequestDto mileStoneUpdateRequestDto) {
        Milestone milestone = findById(milestoneId);
        milestone.update(mileStoneUpdateRequestDto);
    }

    public void delete(Long milestoneId) {
        Milestone milestone = findById(milestoneId);
        mileStoneRepository.delete(milestone);
    }

    public void updateContent(
            Long milestoneId, MilestoneUpdateContentRequestDto milestoneUpdateContentRequestDto) {
        Milestone milestone = findById(milestoneId);
        milestone.updateContent(milestoneUpdateContentRequestDto);
    }

    public void updateDate(
            Long milestoneId, MilestoneUpdateDateRequestDto milestoneUpdateDateRequestDto) {
        Milestone milestone = findById(milestoneId);
        milestone.updateDate(milestoneUpdateDateRequestDto);
    }
}
