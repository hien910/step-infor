package com.example.demostepinfor.repository;

import com.example.demostepinfor.model.entity.DayOff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface DayOffRepository extends JpaRepository<DayOff, Integer> {

    @Query("SELECT d.date FROM DayOff d WHERE YEAR(d.date) = :year")
    List<LocalDate> findAllDatesInYear(@Param("year") int year);
}
