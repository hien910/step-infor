package com.example.demostepinfor.repository;

import com.example.demostepinfor.model.entity.Step;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StepRepository extends JpaRepository<Step,Integer> {
    Step findStepById(Integer stepId);
}
