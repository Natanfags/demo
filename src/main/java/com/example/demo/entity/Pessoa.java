package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.Period;

@Entity
@Data
public class Pessoa {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String nome;
    private String cpf;
    private String rg;
    @JsonProperty("data_nasc")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataNasc;
    private String sexo;
    private String mae;
    private String pai;
    private String email;
    private String cep;
    private String endereco;
    private Long numero;
    private String bairro;
    private String cidade;
    private String estado;
    @JsonProperty("telefone_fixo")
    private String telefoneFixo;
    private String celular;
    private Double altura;
    private Double peso;
    @JsonProperty("tipo_sanguineo")
    private String tipoSanguineo;


    public int getIdade() {
        return Period.between(this.dataNasc, LocalDate.now()).getYears();
    }
}
