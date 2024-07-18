package com.example.demostepinfor.repository;

import com.example.demostepinfor.model.entity.Condition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConditionRepository extends JpaRepository<Condition, Integer> {
    Condition findConditionById(Integer id);

    Condition findConditionByStepIdAndConditionParentNull(Integer id);

    List<Condition> findConditionByInformationId(Integer informationId);
}
