package com.example.demostepinfor.model.entity;

import com.example.demostepinfor.model.entity.enums.TypeSeason;
import com.example.demostepinfor.model.entity.enums.TypeShift;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "work_shifts")
public class WorkShift {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Enumerated(EnumType.STRING)
    TypeSeason season;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_shift")
    TypeShift typeShift;

    @Column(name = "start_time")
    LocalTime  startTime;

    @Column(name = "end_time")
    LocalTime endTime;

    @Column(name = "start_season")
    Date startSeason;
    @Column(name = "end_season")
    Date endSeason;
}
