package com.desafio_anlix.hospital.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "indice_pulmonar")
public class PulmonaryIndex {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String cpf;
    private String epoch;
    private LocalDateTime dataColeta;
    private String ind_pulm;

    @ManyToOne
    @JoinColumn(name = "paciente_id", nullable = false)
    @JsonIgnore
    private Patient paciente;

    public PulmonaryIndex() {
    }

    public PulmonaryIndex(Integer id, String cpf, String epoch, LocalDateTime dataColeta, String ind_pulm, Patient paciente) {
        this.id = id;
        this.cpf = cpf;
        this.epoch = epoch;
        this.ind_pulm = ind_pulm;
        this.paciente = paciente;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEpoch() {
        return epoch;
    }

    public void setEpoch(String epoch) {
        this.epoch = epoch;
    }

    public String getInd_pulm() {
        return ind_pulm;
    }

    public void setInd_pulm(String ind_pulm) {
        this.ind_pulm = ind_pulm;
    }

    public Patient getPaciente() {
        return paciente;
    }

    public void setPaciente(Patient paciente) {
        this.paciente = paciente;
    }

    public LocalDateTime getDataColeta() {
        return dataColeta;
    }

    public void setDataColeta(LocalDateTime dataColeta) {
        this.dataColeta = dataColeta;
    }

}
