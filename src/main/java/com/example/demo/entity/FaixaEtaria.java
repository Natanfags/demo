package com.example.demo.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class FaixaEtaria {
    private Integer faixaInferior;
    private Integer faixaSuperior;
    private double mediaIMC;

    public List<FaixaEtaria> todasAsFaixas() {

        List<FaixaEtaria> faixas = new ArrayList<>();

        for (int inferior = 0; inferior <= 100; inferior += 10) {
            FaixaEtaria faixa = new FaixaEtaria();
            faixa.setFaixaInferior(inferior + 1);
            faixa.setFaixaSuperior(inferior + 10);
            if (faixa.getFaixaSuperior() > 100) {
                faixa.setFaixaSuperior(100);
            }
            faixas.add(faixa);
        }
        return faixas;
    }
}
