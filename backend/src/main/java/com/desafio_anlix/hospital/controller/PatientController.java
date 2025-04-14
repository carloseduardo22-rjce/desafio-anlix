package com.desafio_anlix.hospital.controller;

import com.desafio_anlix.hospital.model.CardiacIndex;
import com.desafio_anlix.hospital.model.Patient;
import com.desafio_anlix.hospital.model.PulmonaryIndex;
import com.desafio_anlix.hospital.service.PatientService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/pacientes")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping
    public ResponseEntity<List<Patient>> getAllPatients() {
        List<Patient> patientList = patientService.getAllPatients();
        return ResponseEntity.ok(patientList);
    }

    @GetMapping(value = "/{cpf}")
    public ResponseEntity<Patient> getPatient(@PathVariable String cpf) {
        Patient patient = patientService.findPatientByCpf(cpf);
        return ResponseEntity.ok(patient);
    }

    @GetMapping("/{cpf}/ultimoIndiceCardiaco")
    public ResponseEntity<CardiacIndex> getLatestCardiacIndex(@PathVariable String cpf) {
        return patientService.findLatestCardiacIndex(cpf)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{cpf}/ultimoIndicePulmonar")
    public ResponseEntity<PulmonaryIndex> getLatestPulmonaryIndex(@PathVariable String cpf) {
        return patientService.findLatestPulmonaryIndex(cpf)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{cpf}/indicesMaisRecentes")
    public ResponseEntity<Patient> getLatestIndexesByCpf(@PathVariable String cpf) {
        Patient patient = patientService.latestIndexes(cpf);
        return ResponseEntity.ok(patient);
    }

    @GetMapping("/nome/{name}/indicesMaisRecentes")
    public ResponseEntity<Patient> getLatestIndexesByName(@PathVariable String name) {
        Patient patient = patientService.latestIndexesByName(name);
        return ResponseEntity.ok(patient);
    }

    @GetMapping("/indiceRecenteCardiacoEntreDatas/{cpf}/{startDate}/{endDate}")
    public CardiacIndex getLatestCardiacIndexBetweenDates (
            @PathVariable String cpf,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        Optional<CardiacIndex> optionalCardiacIndex = patientService.getLatestCardiacIndexBetweenDates(
                cpf,
                startDate.atStartOfDay(),
                endDate.atTime(23, 59, 59, 999_999_999)
        );
        return optionalCardiacIndex.orElseThrow();
    }

    @GetMapping("/indicesPacientesData/{data}")
    public ResponseEntity<Map<String, List<?>>> getIndexesByDate(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data
    ) {
        Map<String, List<?>> response = patientService.getAllIndexesByDate(data.atStartOfDay());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/indiceCardiacoEntreDatas/{cpf}/{startDate}/{endDate}")
    public CardiacIndex getCardiacIndexesBetweenDates(
            @PathVariable String cpf,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        return patientService.getAleatoryCardiacIndexBetweenDates(
                cpf,
                startDate.atStartOfDay(),
                endDate.atTime(23, 59, 59, 999_999_999)
        );
    }

    @GetMapping("/indicePulmonarEntreDatas/{cpf}/{startDate}/{endDate}")
    public PulmonaryIndex getPulmonaryIndexesBetweenDates(
            @PathVariable String cpf,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate startDate,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate endDate
    ) {
        return patientService.getAleatoryPulmonaryIndexBetweenDates(cpf,
                startDate.atStartOfDay(),
                endDate.atTime(23, 59, 59, 999_999_999));
    }

    @GetMapping("/buscarPorNome/{name}")
    public List<Patient> getPatientsByName(@PathVariable String name) {
        return patientService.getPatientsByName(name);
    }

}
