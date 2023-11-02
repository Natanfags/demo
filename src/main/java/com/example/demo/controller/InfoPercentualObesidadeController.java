package com.example.demo.controller;

import com.example.demo.entity.info.InfoPercentualObesidade;
import com.example.demo.service.PessoaService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/info-percentual-obesidade")
@AllArgsConstructor
public class InfoPercentualObesidadeController {

    private final PessoaService pessoaService;

    @GetMapping
    public ResponseEntity<InfoPercentualObesidade> find() {
        InfoPercentualObesidade infoPercentualObesidade = pessoaService.calculaPercentualObesidade();
        return ResponseEntity.ok(infoPercentualObesidade);
    }
}
