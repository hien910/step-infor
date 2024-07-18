package com.example.demostepinfor.controller;

import com.example.demostepinfor.DTO.UpsertInfoRequest;
import com.example.demostepinfor.model.entity.Information;
import com.example.demostepinfor.service.IInformationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/information")
public class InformationController {
    private final IInformationService informationService;

    @PostMapping("/create")
    public ResponseEntity<?> createInfo(@RequestBody UpsertInfoRequest request){
        informationService.createInfo(request);
        return ResponseEntity.ok().body("Create Information successfully!");
    }
}
