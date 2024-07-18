package com.example.demostepinfor.controller;

import com.example.demostepinfor.DTO.WorkShiftDTO;
import com.example.demostepinfor.DTO.response.Response;
import com.example.demostepinfor.service.IConditionService;
import com.example.demostepinfor.service.IInformationService;
import com.example.demostepinfor.service.IStepService;
import com.example.demostepinfor.service.IWorkShiftService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/task")
public class WebController {
    private final IConditionService conditionService;
    private final IStepService stepService;
    private final IInformationService informationService;
    private final IWorkShiftService workShiftService;
    @PostMapping("/create")
    public ResponseEntity<Response<LocalDateTime, ?>> createTask (@RequestBody WorkShiftDTO workShiftDTO) {
        LocalDateTime finishTask = workShiftService.calculateSLAEndTime(workShiftDTO);
        return ResponseEntity.ok().body(new Response<>("200","Thời gian dự kiến hoàn thành", finishTask, null));
    }
}
