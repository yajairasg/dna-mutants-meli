package com.challenge.meli.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.challenge.meli.model.DnaStats;

public interface DnaRepository extends CrudRepository<DnaStats, Integer> {

    @Query("SELECT count(*) from DnaStats WHERE isMutant = :isMutant")
    int getDnaStats(@Param("isMutant") String isMutant);

}
