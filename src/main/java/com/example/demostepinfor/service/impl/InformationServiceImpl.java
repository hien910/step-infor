package com.example.demostepinfor.service.impl;

import com.example.demostepinfor.DTO.UpsertInfoRequest;
import com.example.demostepinfor.model.entity.Information;
import com.example.demostepinfor.repository.InformationRepository;
import com.example.demostepinfor.service.IInformationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InformationServiceImpl implements IInformationService {
    private final InformationRepository informationRepository;
    @Override
    public Information createInfo(UpsertInfoRequest request){
        Information information = Information.builder()
                .code(request.getCode())
                .name(request.getName())
                .build();
        return informationRepository.save(information);
    }
}
