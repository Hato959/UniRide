package com.uniride.repository;

import com.uniride.model.TarifaSimbolica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TarifaSimbolicaRepository extends JpaRepository<TarifaSimbolica,Long> {

}
