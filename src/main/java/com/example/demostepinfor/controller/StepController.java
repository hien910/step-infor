package com.example.demostepinfor.controller;

import com.example.demostepinfor.DTO.UpsertInfoRequest;
import com.example.demostepinfor.DTO.response.Response;
import com.example.demostepinfor.model.entity.Condition;
import com.example.demostepinfor.model.entity.Step;
import com.example.demostepinfor.repository.ConditionRepository;
import com.example.demostepinfor.service.IStepService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/step")
public class StepController {
    private final IStepService stepService;
    private final ConditionRepository conditionRepository;

    @PostMapping("/create")
    public ResponseEntity<?> createStep(@RequestBody UpsertInfoRequest request){
        stepService.createStep(request);
        return ResponseEntity.ok().body("Create Step successfully!");
    }
    @GetMapping("/get/{id}")
    public ResponseEntity<Response<Step,Condition>>  getStep(@PathVariable Integer id){
        Step step = stepService.getStep(id);
        Condition condition = conditionRepository.findConditionByStepIdAndConditionParentNull(id);
        return ResponseEntity.ok().body(new Response<>("200","Get successfully", step,condition));
    }

}
