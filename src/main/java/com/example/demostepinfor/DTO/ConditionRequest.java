package com.example.demostepinfor.DTO;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ConditionRequest {
    Integer id;
    Integer conditionParentId;
    Integer informationId;
    Integer stepId;
    String logic;
    Float value;
    String compare;
    List<ConditionRequest> conditionList;
}
