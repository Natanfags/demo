package com.example.demo.controller;

import com.example.demo.entity.info.InfoMediaIdadePorTipoSanguineo;
import com.example.demo.service.PessoaService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/info-tipo-sanguineo-idade")
@AllArgsConstructor
public class InfoMediaIdadePorTipoSanguineoController {

    private final PessoaService pessoaService;

    @GetMapping
    public ResponseEntity<List<InfoMediaIdadePorTipoSanguineo>> find() {
        List<InfoMediaIdadePorTipoSanguineo> infoMediaIdadePorTipoSanguineos = pessoaService.calculaMediaIdadePorTipoSanguineo();
        return ResponseEntity.ok(infoMediaIdadePorTipoSanguineos);
    }
}
