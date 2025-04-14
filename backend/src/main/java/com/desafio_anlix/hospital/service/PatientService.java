package com.desafio_anlix.hospital.service;

import com.desafio_anlix.hospital.model.CardiacIndex;
import com.desafio_anlix.hospital.model.Patient;
import com.desafio_anlix.hospital.model.PulmonaryIndex;
import com.desafio_anlix.hospital.repository.CardiacIndexRepository;
import com.desafio_anlix.hospital.repository.PatientRepository;
import com.desafio_anlix.hospital.repository.PulmonaryIndexRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PatientService {

    private final PatientRepository patientRepository;
    private final CardiacIndexRepository cardiacIndexRepository;
    private final PulmonaryIndexRepository pulmonaryIndexRepository;

    public PatientService(PatientRepository patientRepository, CardiacIndexRepository cardiacIndexRepository, PulmonaryIndexRepository pulmonaryIndexRepository) {
        this.patientRepository = patientRepository;
        this.cardiacIndexRepository = cardiacIndexRepository;
        this.pulmonaryIndexRepository = pulmonaryIndexRepository;
    }

    public Patient findPatientByCpf(String cpf) {
        Optional<Patient> optionalPatient = patientRepository.findByCpf(cpf);
        List<CardiacIndex> latestCardiac = cardiacIndexRepository.findByPacienteCpfOrderByDataColetaDesc(cpf);
        List<PulmonaryIndex> latestPulmonary = pulmonaryIndexRepository.findByPacienteCpfOrderByDataColetaDesc(cpf);

        Patient patient = null;

        if (optionalPatient.isPresent()) {
            patient = optionalPatient.get();
            patient.setListaIndicesCardiacos(latestCardiac);
            patient.setListaIndicesPulmonares(latestPulmonary);
        }

        return patient;
    }

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public Optional<CardiacIndex> findLatestCardiacIndex(String cpf) {
        return cardiacIndexRepository.findFirstByPacienteCpfOrderByDataColetaDesc(cpf);
    }

    public Optional<PulmonaryIndex> findLatestPulmonaryIndex(String cpf) {
        return pulmonaryIndexRepository.findFirstByPacienteCpfOrderByDataColetaDesc(cpf);
    }

    public Patient latestIndexes(String cpf) {
        Optional<Patient> optionalPatient = patientRepository.findByCpf(cpf);
        List<CardiacIndex> latestCardiac = cardiacIndexRepository.findByPacienteCpfOrderByDataColetaDesc(cpf);
        List<PulmonaryIndex> latestPulmonary = pulmonaryIndexRepository.findByPacienteCpfOrderByDataColetaDesc(cpf);

        Patient patient = null;

        if (optionalPatient.isPresent()) {
            patient = optionalPatient.get();
            patient.setListaIndicesCardiacos(latestCardiac);
            patient.setListaIndicesPulmonares(latestPulmonary);
        }

        return patient;

    }

    public Map<String, List<?>> getAllIndexesByDate(LocalDateTime data) {
        LocalDateTime startOfDay = data.toLocalDate().atStartOfDay();
        LocalDateTime endOfDay = data.toLocalDate().atTime(23, 59, 59, 999_999_999);

        List<CardiacIndex> cardiacIndexes = cardiacIndexRepository.findAllByDataColetaBetween(startOfDay, endOfDay);
        List<PulmonaryIndex> pulmonaryIndexes = pulmonaryIndexRepository.findAllByDataColetaBetween(startOfDay, endOfDay);

        Map<String, List<?>> response = new HashMap<>();
        response.put("indices_cardiacos", cardiacIndexes);
        response.put("indices_pulmonares", pulmonaryIndexes);

        return response;
    }

    public PulmonaryIndex getAleatoryPulmonaryIndexBetweenDates(String cpf, LocalDateTime start, LocalDateTime end) {
        LocalDateTime startOfDay = start.toLocalDate().atStartOfDay();
        LocalDateTime endOfDay = end.toLocalDate().atTime(23, 59, 59, 999_999_999);

        List<PulmonaryIndex> pulmonaryIndexes = pulmonaryIndexRepository.findByCpfAndDataColetaBetween(cpf, startOfDay, endOfDay);

        if (pulmonaryIndexes.isEmpty()) {
            return null;
        }

        int randomIndex = new Random().nextInt(pulmonaryIndexes.size());
        return pulmonaryIndexes.get(randomIndex);
    }

    public CardiacIndex getAleatoryCardiacIndexBetweenDates(String cpf, LocalDateTime start, LocalDateTime end) {
        LocalDateTime startOfDay = start.toLocalDate().atStartOfDay();
        LocalDateTime endOfDay = end.toLocalDate().atTime(23, 59, 59, 999_999_999);

        List<CardiacIndex> cardiacIndexes = cardiacIndexRepository.findByCpfAndDataColetaBetween(cpf, startOfDay, endOfDay);

        if (cardiacIndexes.isEmpty()) {
            return null;
        }

        int randomIndex = new Random().nextInt(cardiacIndexes.size());
        return cardiacIndexes.get(randomIndex);
    }

    public List<Patient> getPatientsByName(String name) {
        return patientRepository.findAll().stream()
                .filter(patient -> patient.getNome() != null && patient.getNome().toLowerCase().contains(name.toLowerCase()))
                .peek(patient -> {
                    patient.setListaIndicesPulmonares(null);
                    patient.setListaIndicesCardiacos(null);
                })
                .collect(Collectors.toList());
    }

    public Optional<CardiacIndex> getLatestCardiacIndexBetweenDates(String cpf, LocalDateTime start, LocalDateTime end) {
        LocalDateTime startOfDay = start.toLocalDate().atStartOfDay();
        LocalDateTime endOfDay = end.toLocalDate().atTime(23, 59, 59, 999_999_999);

        return cardiacIndexRepository.findFirstByCpfAndDataColetaBetweenOrderByDataColetaDesc(cpf, startOfDay, endOfDay);
    }

    public Patient latestIndexesByName(String name) {
        Patient patient = patientRepository.findByNome(name)
                .orElseThrow(() -> new RuntimeException("Patient not found!"));
        List<CardiacIndex> latestCardiac = cardiacIndexRepository.findByCpfOrderByDataColetaDesc(patient.getCpf());
        List<PulmonaryIndex> latestPulmonary = pulmonaryIndexRepository.findByCpfOrderByDataColetaDesc(patient.getCpf());

        if (!latestCardiac.isEmpty()) {
            latestCardiac.subList(1, latestCardiac.size()).clear();
        }
        if (!latestPulmonary.isEmpty()) {
            latestPulmonary.subList(1, latestPulmonary.size()).clear();
        }

        patient.setListaIndicesCardiacos(latestCardiac);
        patient.setListaIndicesPulmonares(latestPulmonary);

        return patient;
    }

}
