package com.xyz.repo;

import com.xyz.modelo.Incidencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IncidentesRepository extends JpaRepository<Incidencia, Integer> {
}
