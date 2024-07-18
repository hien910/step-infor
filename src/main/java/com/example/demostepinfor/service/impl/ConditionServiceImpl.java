package com.example.demostepinfor.service.impl;

import com.example.demostepinfor.DTO.ConditionRequest;
import com.example.demostepinfor.DTO.InputNumRequest;
import com.example.demostepinfor.model.entity.Condition;
import com.example.demostepinfor.model.entity.Information;
import com.example.demostepinfor.model.entity.Step;
import com.example.demostepinfor.repository.ConditionRepository;
import com.example.demostepinfor.repository.InformationRepository;
import com.example.demostepinfor.repository.StepRepository;
import com.example.demostepinfor.service.IConditionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ConditionServiceImpl implements IConditionService {
    private final ConditionRepository conditionRepository;
    private final StepRepository stepRepository;
    private final InformationRepository informationRepository;

    @Override
    public Condition createConditionRecursively(ConditionRequest request) {
        // Kiểm tra null cho các đối tượng liên quan
        Step step = stepRepository.findById(request.getStepId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid step ID: " + request.getStepId()));
        Information information;
        if (request.getInformationId() != null) {
            information = informationRepository.findById(request.getInformationId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid information ID: " + request.getInformationId()));
        } else {
            information = null;
        }

        Condition condition = Condition.builder()
                .stepId(request.getStepId())
                .informationId(request.getInformationId())
                .value(request.getValue())
                .logic(request.getLogic())
                .compare(request.getCompare())
                .conditionParent(request.getConditionParentId() != null ? conditionRepository.findConditionById(request.getConditionParentId()) : null)
                .build();

        // Lưu Condition vào cơ sở dữ liệu
        conditionRepository.save(condition);

        // Xử lý các điều kiện con
        if (request.getConditionList() != null) {
            for (ConditionRequest childRequest : request.getConditionList()) {
                childRequest.setConditionParentId(condition.getId()); // Đặt parentId cho điều kiện con
                createConditionRecursively(childRequest); // Đệ quy để tạo điều kiện con
            }
        }

        return condition;
    }

    @Override
    public Condition getCondition(Integer id) {
        return conditionRepository.findConditionById(id);
    }

    public Boolean checkCondition(List<InputNumRequest> requestList, Integer id) {
        Condition condition = conditionRepository.findConditionById(id);
        String logic = condition.getLogic();
        List<Condition> conditionList = condition.getConditionList();
        Boolean isCheck= false;
        if (conditionList.isEmpty()) {
            InputNumRequest request = requestList.stream()
                    .filter(req -> Objects.equals(req.getInformationId(), condition.getInformationId()))
                    .findFirst()
                    .orElse(null);
            if (request == null) {
                throw new IllegalArgumentException("Request không tồn tại cho condition id: " + condition.getInformationId());
            }
            isCheck =  checkBoolean(request, condition);
        } else {
            switch (logic) {
                case "AND" -> isCheck = handleAnd(conditionList, requestList);
                case "OR" -> isCheck = handleOr(conditionList, requestList);
                default -> {
                }
            }
        }
        return isCheck;
    }

    private Boolean handleOr(List<Condition> conditionList, List<InputNumRequest> requestList) {
        for (Condition c : conditionList) {
            if(checkCondition(requestList, c.getId())){
                return true;
            }
        }
        return false;
    }

    private Boolean handleAnd( List<Condition> conditionList,List<InputNumRequest> requestList) {
        for (Condition c : conditionList) {
            if(!checkCondition(requestList, c.getId())){
                return false;
            }
        }
        return true;
    }

    public Boolean checkBoolean(InputNumRequest request, Condition condition) {
        String compare = condition.getCompare();
        Float value = condition.getValue();
        Float num = request.getNum();
        float epsilon = 0.00000001f; // Định nghĩa epsilon cho phép

        return switch (compare) {
            case ">" -> (num > value);
            case ">=" -> (num >= value);
            case "<" -> (num < value);
            case "<=" -> (num <= value);
            case "==" -> (Math.abs(num - value) <= epsilon); // So sánh với epsilon
            case "!=" -> (Math.abs(num - value) > epsilon); // So sánh với epsilon
            default -> throw new IllegalArgumentException("Toán tử so sánh không hợp lệ: " + compare);
        };
    }


    @Override
    public Condition updateRootCondition(ConditionRequest request) {
        Condition condition = conditionRepository.findConditionById(request.getId());
        condition.setConditionParent(request.getConditionParentId() != null ?
                conditionRepository.findConditionById(request.getConditionParentId()) : null);
        condition.setCompare(request.getCompare());
        condition.setLogic(request.getLogic());
        condition.setValue(request.getValue());
        condition.setStepId(request.getStepId());
        condition.setInformationId(request.getInformationId());

        conditionRepository.save(condition);
        List<ConditionRequest> conditionList = request.getConditionList();
        // Xử lý các điều kiện con
        if (conditionList != null) {
            for (ConditionRequest childRequest : request.getConditionList()) {
                childRequest.setConditionParentId(condition.getId()); // Đặt parentId cho điều kiện con
                updateRootCondition(childRequest); // Đệ quy để tạo điều kiện con
            }
        }
        return condition;
    }
}
