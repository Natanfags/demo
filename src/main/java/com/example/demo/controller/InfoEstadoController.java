package com.example.demo.controller;

import com.example.demo.entity.info.InfoEstado;
import com.example.demo.service.PessoaService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/info-estado")
@AllArgsConstructor
public class InfoEstadoController {

    private final PessoaService pessoaService;

    @GetMapping
    public ResponseEntity<List<InfoEstado>> find() {

        List<InfoEstado> infoPessoasByEstados = pessoaService.getInfoPessoasByEstados();
        return ResponseEntity.ok(infoPessoasByEstados);
    }
}
