package com.desafio_anlix.hospital.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "paciente")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;
    private Integer idade;
    private String cpf;
    private String rg;
    private String data_nasc;
    private String sexo;
    private String signo;
    private String mae;
    private String pai;
    private String email;
    private String senha;
    private String cep;
    private String endereco;
    private Integer numero;
    private String bairro;
    private String cidade;
    private String estado;
    private String telefone_fixo;
    private String celular;
    private String altura;
    private Double peso;
    private String tipo_sanguineo;
    private String cor;

    @OneToMany(mappedBy = "paciente", fetch = FetchType.EAGER)
    private List<PulmonaryIndex> listaIndicesPulmonares;

    @OneToMany(mappedBy = "paciente", fetch = FetchType.EAGER)
    private List<CardiacIndex> listaIndicesCardiacos;

    public Patient() {
    }

    public Patient(Integer id, String nome, Integer idade, String cpf, String rg, String data_nasc, String sexo, String signo, String mae, String pai, String email, String senha, String cep, String endereco, Integer numero, String bairro, String cidade, String estado, String telefone_fixo, String celular, String altura, Double peso, String tipo_sanguineo, String cor, List<PulmonaryIndex> listaIndicesPulmonares, List<CardiacIndex> listaIndicesCardiacos) {
        this.id = id;
        this.nome = nome;
        this.idade = idade;
        this.cpf = cpf;
        this.rg = rg;
        this.data_nasc = data_nasc;
        this.sexo = sexo;
        this.signo = signo;
        this.mae = mae;
        this.pai = pai;
        this.email = email;
        this.senha = senha;
        this.cep = cep;
        this.endereco = endereco;
        this.numero = numero;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.telefone_fixo = telefone_fixo;
        this.celular = celular;
        this.altura = altura;
        this.peso = peso;
        this.tipo_sanguineo = tipo_sanguineo;
        this.cor = cor;
        this.listaIndicesPulmonares = listaIndicesPulmonares;
        this.listaIndicesCardiacos = listaIndicesCardiacos;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getIdade() {
        return idade;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getData_nasc() {
        return data_nasc;
    }

    public void setData_nasc(String data_nasc) {
        this.data_nasc = data_nasc;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getSigno() {
        return signo;
    }

    public void setSigno(String signo) {
        this.signo = signo;
    }

    public String getMae() {
        return mae;
    }

    public void setMae(String mae) {
        this.mae = mae;
    }

    public String getPai() {
        return pai;
    }

    public void setPai(String pai) {
        this.pai = pai;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTelefone_fixo() {
        return telefone_fixo;
    }

    public void setTelefone_fixo(String telefone_fixo) {
        this.telefone_fixo = telefone_fixo;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getAltura() {
        return altura;
    }

    public void setAltura(String altura) {
        this.altura = altura;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public String getTipo_sanguineo() {
        return tipo_sanguineo;
    }

    public void setTipo_sanguineo(String tipo_sanguineo) {
        this.tipo_sanguineo = tipo_sanguineo;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public List<PulmonaryIndex> getListaIndicesPulmonares() {
        return listaIndicesPulmonares;
    }

    public void setListaIndicesPulmonares(List<PulmonaryIndex> listaIndicesPulmonares) {
        this.listaIndicesPulmonares = listaIndicesPulmonares;
    }

    public List<CardiacIndex> getListaIndicesCardiacos() {
        return listaIndicesCardiacos;
    }

    public void setListaIndicesCardiacos(List<CardiacIndex> listaIndicesCardiacos) {
        this.listaIndicesCardiacos = listaIndicesCardiacos;
    }

}
