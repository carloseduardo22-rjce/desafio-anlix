package com.desafio_anlix.hospital.repository;

import com.desafio_anlix.hospital.model.PulmonaryIndex;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PulmonaryIndexRepository extends JpaRepository<PulmonaryIndex, Integer> {

    Optional<PulmonaryIndex> findFirstByPacienteCpfOrderByDataColetaDesc(String cpf);
    List<PulmonaryIndex> findByPacienteCpfOrderByDataColetaDesc(String cpf);
    List<PulmonaryIndex> findAllByDataColetaBetween(LocalDateTime start, LocalDateTime end);
    List<PulmonaryIndex> findByCpfAndDataColetaBetween(String cpf, LocalDateTime startDate, LocalDateTime endDate);
    List<PulmonaryIndex> findByCpfOrderByDataColetaDesc(String cpf);

}
