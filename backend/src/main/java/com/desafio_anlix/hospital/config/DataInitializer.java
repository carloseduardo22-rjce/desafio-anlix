package com.desafio_anlix.hospital.config;

import com.desafio_anlix.hospital.model.CardiacIndex;
import com.desafio_anlix.hospital.model.Patient;
import com.desafio_anlix.hospital.model.PulmonaryIndex;
import com.desafio_anlix.hospital.repository.CardiacIndexRepository;
import com.desafio_anlix.hospital.repository.PatientRepository;
import com.desafio_anlix.hospital.repository.PulmonaryIndexRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

@Component
public class DataInitializer implements ApplicationRunner, ResourceLoaderAware {

    private final PatientRepository patientRepository;
    private ResourceLoader resourceLoader;
    private final CardiacIndexRepository cardiacIndexRepository;
    private final PulmonaryIndexRepository pulmonaryIndexRepository;

    public DataInitializer(PatientRepository patientRepository, CardiacIndexRepository cardiacIndexRepository, PulmonaryIndexRepository pulmonaryIndexRepository) {
        this.patientRepository = patientRepository;
        this.cardiacIndexRepository = cardiacIndexRepository;
        this.pulmonaryIndexRepository = pulmonaryIndexRepository;
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        loadPatients();
        loadIndexes("classpath:data/indice_cardiaco", true);
        loadIndexes("classpath:data/indice_pulmonar", false);
    }

    private void loadPatients() throws Exception {
        Resource resource = resourceLoader.getResource("classpath:data/pacientes.json");
        try (InputStreamReader reader = new InputStreamReader(resource.getInputStream())) {
            ObjectMapper objectMapper = new ObjectMapper();
            List<Patient> patientList = objectMapper.readValue(reader, new TypeReference<>() {});
            patientRepository.saveAll(patientList);

            System.out.println("Patient list saved successfully!");
        }
    }

    private void loadIndexes(String resourcePattern, boolean isCardiac) throws IOException {
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(getClass().getClassLoader());
        Resource[] resources = resolver.getResources(resourcePattern + "/*");

        for (Resource resource : resources) {
            if (!resource.getURL().toString().endsWith(resourcePattern.replace("classpath:", ""))) {
                processResource(resource, isCardiac);
            }
        }

    }

    private void processResource(Resource resource, boolean isCardiac) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) continue;
                processLine(line, isCardiac);
            }
        } catch (IOException e) {
            System.err.println("Error reading resource: " + resource.getURL() + " - " + e.getMessage());
        }
    }

    private void processLine(String line, boolean isCardiac) {
        String[] fields = line.trim().split("\\s+");
        if (fields[1].equalsIgnoreCase("EPOCH")) return;

        String cpf = fields[0];
        LocalDateTime date = LocalDateTime.ofEpochSecond(Long.parseLong(fields[1]), 0, ZoneOffset.UTC);
        String index = fields[2];

        Optional<Patient> patient = patientRepository.findByCpf(cpf);

        if (patient.isPresent()) {
            if (isCardiac) {
                saveCardiacIndex(patient.get(), cpf, fields[1], date, index);
            } else {
                savePulmonaryIndex(patient.get(), cpf, fields[1], date, index);
            }
        } else {
            System.err.println("Patient with CPF " + cpf + " not found for index data.");
        }

    }

    private void saveCardiacIndex(Patient patient, String cpf, String epoch, LocalDateTime date, String index) {
        CardiacIndex cardiacIndex = new CardiacIndex();
        cardiacIndex.setPaciente(patient);
        cardiacIndex.setCpf(cpf);
        cardiacIndex.setEpoch(epoch);
        cardiacIndex.setDataColeta(date);
        cardiacIndex.setInd_card(index);
        cardiacIndexRepository.save(cardiacIndex);
    }

    private void savePulmonaryIndex(Patient patient, String cpf, String epoch, LocalDateTime date, String index) {
        PulmonaryIndex pulmonaryIndex = new PulmonaryIndex();
        pulmonaryIndex.setPaciente(patient);
        pulmonaryIndex.setCpf(cpf);
        pulmonaryIndex.setEpoch(epoch);
        pulmonaryIndex.setDataColeta(date);
        pulmonaryIndex.setInd_pulm(index);
        pulmonaryIndexRepository.save(pulmonaryIndex);
    }

}