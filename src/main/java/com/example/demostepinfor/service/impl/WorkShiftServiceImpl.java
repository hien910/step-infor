package com.example.demostepinfor.service.impl;

import com.example.demostepinfor.DTO.WorkShiftDTO;
import com.example.demostepinfor.model.entity.DayOff;
import com.example.demostepinfor.model.entity.WorkShift;
import com.example.demostepinfor.model.entity.enums.TypeShift;
import com.example.demostepinfor.repository.DayOffRepository;
import com.example.demostepinfor.repository.WorkShiftRepository;
import com.example.demostepinfor.service.IWorkShiftService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkShiftServiceImpl implements IWorkShiftService {
    private final WorkShiftRepository workShiftRepository;
    private final DayOffRepository dayOffRepository;

    @Transactional
    public LocalDateTime calculateSLAEndTime(WorkShiftDTO workShiftDTO) {
        LocalDateTime finishDate;
        // Kiểm tra điều kiện * 10 % 5==0
        if (workShiftDTO.getNum() * 10 % 5 != 0) {
            throw new IllegalArgumentException("Invalid debt value: " + workShiftDTO.getNum());
        }
        // Xử lý kiểu
        if (workShiftDTO.getType().equals("HOUR")) {
            finishDate = functionHour(workShiftDTO.getNum(), workShiftDTO.getDate());
        } else if (workShiftDTO.getType().equals("DAY")) {
            Float taskHour = workShiftDTO.getNum() * 8;
            finishDate = functionHour(taskHour, workShiftDTO.getDate());
        } else {
            throw new IllegalArgumentException("Unsupported type: " + workShiftDTO.getType());
        }
        return finishDate;
    }

    @Transactional
    public LocalDateTime functionHour(Float debtValue, LocalDateTime startTime) {
        LocalDateTime finishDate;
        List<LocalDate> dateList = dayOffRepository.findAllDatesInYear(startTime.getYear());
        // lấy ca làm việc bắt đầu
        WorkShift workShiftStart = workShiftRepository.findCurrentWorkShift(startTime.toLocalDate(), startTime.toLocalTime());
        Duration duration = null;
        // tính thời gian từ lúc giao task đến hết ngày làm vc
        if (workShiftStart.getTypeShift().equals(TypeShift.MORNING)) {
            WorkShift afternoonShift = workShiftRepository.findWorkShiftsBySeasonAndType(workShiftStart.getSeason(), TypeShift.AFTERNOON);
            if (startTime.toLocalTime().isBefore(workShiftStart.getStartTime())) {
                duration = Duration.ofHours(8);
            } else if (startTime.toLocalTime().isAfter(workShiftStart.getStartTime()) && startTime.toLocalTime().isBefore(workShiftStart.getEndTime())) {
                duration = Duration.between(startTime.toLocalTime(), workShiftStart.getEndTime());
                duration = duration.plus(Duration.between(afternoonShift.getStartTime(), afternoonShift.getEndTime()));
            } else if (startTime.toLocalTime().isAfter(afternoonShift.getEndTime())) {
                duration = Duration.ofHours(0);
            }
        } else {
            WorkShift morningShift = workShiftRepository.findWorkShiftsBySeasonAndType(workShiftStart.getSeason(), TypeShift.MORNING);
            if (startTime.toLocalTime().isBefore(workShiftStart.getStartTime()) && startTime.toLocalTime().isAfter(morningShift.getEndTime())) {
                duration = Duration.between(workShiftStart.getStartTime(), workShiftStart.getEndTime());
            } else if (startTime.toLocalTime().isAfter(workShiftStart.getStartTime()) && startTime.toLocalTime().isBefore(workShiftStart.getEndTime())) {
                duration = Duration.between(startTime.toLocalTime(), workShiftStart.getEndTime());
            }
        }
        float numHour = duration != null ? (float) duration.toMinutes() / 60 : 0.0f; // số giờ từ lúc giao task đến hết ngày làm vc
        LocalDate dateEnd = startTime.toLocalDate();
        LocalTime timeEnd;
        int dayWorks = (debtValue - numHour) <= 0 ? 0 : (int) ((debtValue - numHour) / 8 + 1); // ngày cần làm việc
        float hourWorkEnd = (debtValue - numHour) % 8;  // số giờ dư ra
        while (dayWorks > 0) {
            dateEnd = dateEnd.plusDays(1);
            if (!dateList.contains(dateEnd)) {
                dayWorks--;
            }
        }
        WorkShift morShift = workShiftRepository.FindWorkShiftByDateAndType(dateEnd, String.valueOf(TypeShift.MORNING));
        WorkShift afterShift = workShiftRepository.FindWorkShiftByDateAndType(dateEnd, String.valueOf(TypeShift.AFTERNOON));
        if (debtValue - numHour <= 0) {
            float minutesOfAfterShift = Duration.between(afterShift.getStartTime(), afterShift.getEndTime()).toMinutes(); // thời gian của ca chiều
            float minutesRest = minutesOfAfterShift - (numHour - debtValue) * 60; //
            if (minutesRest < 0) {
                finishDate = LocalDateTime.of(dateEnd, morShift.getEndTime().minusMinutes((long) Math.abs(minutesRest)));
            } else {
                finishDate = LocalDateTime.of(dateEnd, afterShift.getEndTime().minusMinutes((long) ((numHour - debtValue) * 60)));
            }
            return finishDate;
        }
        // sau khi trừ hết số ngày lv hoàn chỉnh
        // tìm ra ca làm việc đầu tiên với thòi gian thừa
        Duration durationEnd = Duration.between(morShift.getStartTime(), morShift.getEndTime());
        float shiftEnd = (float) durationEnd.toMinutes() / 60;
        timeEnd = morShift.getStartTime().plusMinutes((long) (hourWorkEnd * 60));
        if (hourWorkEnd > shiftEnd) {
            finishDate = LocalDateTime.of(dateEnd,timeEnd.plusMinutes(Duration.between(morShift.getEndTime(), afterShift.getStartTime()).toMinutes()));
        } else {
            finishDate = LocalDateTime.of(dateEnd, timeEnd);
        }
        return finishDate;
    }
}
