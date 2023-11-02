package com.example.demo.controller;

import com.example.demo.entity.info.InfoMapaCompatibilidades;
import com.example.demo.service.PessoaService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/info-mapa-compatibilidade")
@AllArgsConstructor
public class InfoMapaCompatibilidadeController {

    private final PessoaService pessoaService;

    @GetMapping
    public ResponseEntity<List<InfoMapaCompatibilidades>> find() {
        List<InfoMapaCompatibilidades> infoMapaCompatibilidades = pessoaService.listCompatibilidadesPorTipoSanguineo();
        return ResponseEntity.ok(infoMapaCompatibilidades);
    }
}
