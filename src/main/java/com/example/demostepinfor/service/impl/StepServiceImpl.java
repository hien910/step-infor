package com.example.demostepinfor.service.impl;

import com.example.demostepinfor.DTO.UpsertInfoRequest;
import com.example.demostepinfor.model.entity.Information;
import com.example.demostepinfor.model.entity.Step;
import com.example.demostepinfor.repository.ConditionRepository;
import com.example.demostepinfor.repository.StepRepository;
import com.example.demostepinfor.service.IStepService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StepServiceImpl implements IStepService {
    private final StepRepository stepRepository;
    private final ConditionRepository conditionRepository;
    @Override
    public Step createStep(UpsertInfoRequest request){
        Step step = Step.builder()
                .code(request.getCode())
                .name(request.getName())
                .build();
        return stepRepository.save(step);
    }

    @Override
    public Step getStep(Integer id) {
        return stepRepository.findStepById(id);
    }
}
