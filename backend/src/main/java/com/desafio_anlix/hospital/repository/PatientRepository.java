package com.desafio_anlix.hospital.repository;

import com.desafio_anlix.hospital.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer> {
    Optional<Patient> findByCpf(String cpf);
    Optional<Patient> findByNome(String name);
}
