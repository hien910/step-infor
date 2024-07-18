package com.example.demostepinfor.service;

import com.example.demostepinfor.DTO.UpsertInfoRequest;
import com.example.demostepinfor.model.entity.Step;

public interface IStepService {
    Step createStep(UpsertInfoRequest request);

    Step getStep(Integer id);
}
