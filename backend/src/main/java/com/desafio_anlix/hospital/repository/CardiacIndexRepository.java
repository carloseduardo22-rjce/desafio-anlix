package com.desafio_anlix.hospital.repository;

import com.desafio_anlix.hospital.model.CardiacIndex;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CardiacIndexRepository extends JpaRepository<CardiacIndex, Integer> {

    Optional<CardiacIndex> findFirstByPacienteCpfOrderByDataColetaDesc(String cpf);
    List<CardiacIndex> findByPacienteCpfOrderByDataColetaDesc(String cpf);
    List<CardiacIndex> findAllByDataColetaBetween(LocalDateTime start, LocalDateTime end);
    List<CardiacIndex> findByCpfAndDataColetaBetween(String cpf, LocalDateTime startDate, LocalDateTime endDate);
    Optional<CardiacIndex> findFirstByCpfAndDataColetaBetweenOrderByDataColetaDesc(String cpf, LocalDateTime start, LocalDateTime end);
    List<CardiacIndex> findByCpfOrderByDataColetaDesc(String cpf);

}
