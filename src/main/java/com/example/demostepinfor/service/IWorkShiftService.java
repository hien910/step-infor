package com.example.demostepinfor.service;

import com.example.demostepinfor.DTO.WorkShiftDTO;

import java.time.LocalDateTime;

public interface IWorkShiftService {
    LocalDateTime calculateSLAEndTime(WorkShiftDTO workShiftDTO);
}
