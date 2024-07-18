package com.example.demostepinfor.repository;

import com.example.demostepinfor.model.entity.Information;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InformationRepository extends JpaRepository<Information, Integer> {
    Information findInformationById(Integer informationId);
}
