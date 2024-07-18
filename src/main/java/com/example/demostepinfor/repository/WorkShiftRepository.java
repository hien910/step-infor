package com.example.demostepinfor.repository;

import com.example.demostepinfor.model.entity.WorkShift;
import com.example.demostepinfor.model.entity.enums.TypeSeason;
import com.example.demostepinfor.model.entity.enums.TypeShift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public interface WorkShiftRepository extends JpaRepository<WorkShift, Integer> {
    @Procedure(procedureName = "FindCurrentWorkShift")
    WorkShift findCurrentWorkShift(@Param("currentDate") LocalDate currentDate, @Param("currentTime") LocalTime currentTime);

    @Procedure(procedureName = "FindWorkShiftByDateAndType")
    WorkShift FindWorkShiftByDateAndType(@Param("currentDate") LocalDate currentDate, @Param("shift_val") String type);

    @Query("SELECT w FROM WorkShift w " +
            "WHERE w.season = :season " +
            "AND w.typeShift = :type")
    WorkShift findWorkShiftsBySeasonAndType(@Param("season") TypeSeason season, @Param("type") TypeShift type);

}
