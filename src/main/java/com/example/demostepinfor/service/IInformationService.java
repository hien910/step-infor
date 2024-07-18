package com.example.demostepinfor.service;

import com.example.demostepinfor.DTO.UpsertInfoRequest;
import com.example.demostepinfor.model.entity.Information;

public interface IInformationService {
    Information createInfo(UpsertInfoRequest request);
}
