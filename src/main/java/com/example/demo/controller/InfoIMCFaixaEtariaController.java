package com.example.demo.controller;

import com.example.demo.entity.info.InfoIMCFaixaEtaria;
import com.example.demo.service.PessoaService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/info-imc-faixa-etaria")
@AllArgsConstructor
public class InfoIMCFaixaEtariaController {

    private final PessoaService pessoaService;

    @GetMapping
    public ResponseEntity<List<InfoIMCFaixaEtaria>> find() {
        List<InfoIMCFaixaEtaria> infoIMCFaixaEtarias = pessoaService.calculaMediaPorFaixaEtaria();
        return ResponseEntity.ok(infoIMCFaixaEtarias);
    }
}
