package com.example.demostepinfor.service;

import com.example.demostepinfor.DTO.ConditionRequest;
import com.example.demostepinfor.DTO.InputNumRequest;
import com.example.demostepinfor.model.entity.Condition;

import java.util.List;

public interface IConditionService {
    Condition createConditionRecursively(ConditionRequest request);
    Condition getCondition(Integer id);
    Boolean checkCondition(List<InputNumRequest> requestList, Integer parentId);
    Condition updateRootCondition(ConditionRequest request);
}
