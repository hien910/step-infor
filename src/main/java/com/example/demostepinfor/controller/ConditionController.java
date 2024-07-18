package com.example.demostepinfor.controller;

import com.example.demostepinfor.DTO.ConditionRequest;
import com.example.demostepinfor.DTO.InputNumRequest;
import com.example.demostepinfor.DTO.WorkShiftDTO;
import com.example.demostepinfor.model.entity.Condition;
import com.example.demostepinfor.model.entity.WorkShift;
import com.example.demostepinfor.repository.WorkShiftRepository;
import com.example.demostepinfor.service.IConditionService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/condition")
public class ConditionController {
    private final IConditionService conditionService;
    private final WorkShiftRepository workShiftRepository;

    @PostMapping("/create")
    public Condition createCondition(@RequestBody ConditionRequest request) {
        return conditionService.createConditionRecursively(request);
    }

    @GetMapping("/get/{id}")
    public Condition createCondition(@PathVariable Integer id) {
        return conditionService.getCondition(id);
    }

    @PostMapping("/check")
    public Boolean checkCondition(@RequestBody List<InputNumRequest> requestList, @RequestParam Integer id) {
        return conditionService.checkCondition(requestList, id);
    }

    @PutMapping("/update")
    public Condition updateRootCondition(@RequestBody ConditionRequest request) {
        return conditionService.updateRootCondition(request);
    }
}
