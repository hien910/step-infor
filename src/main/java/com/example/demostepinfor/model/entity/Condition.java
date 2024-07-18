package com.example.demostepinfor.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "conditions")
public class Condition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

//    @ManyToOne
//    @JoinColumn(name = "information_id")
//    Information information;

    @Column(name = "information_id")
    Integer informationId;

    // so sanh
    String compare;
    Float value;

//    @ManyToOne
//    @JoinColumn(name = "step_id")
//    Step step;

    @Column(name = "step_id")
    Integer stepId;

    // OR, AND
    String logic;

    @ManyToOne
    @JoinColumn(name = "condition_parent_id")
    @JsonBackReference
    Condition conditionParent;

    @OneToMany(mappedBy = "conditionParent")
    @JsonManagedReference
    List<Condition> conditionList;
}
