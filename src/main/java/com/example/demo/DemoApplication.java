package com.example.demo;

import com.example.demo.entity.InfoCandidatos;
import com.example.demo.entity.Pessoa;
import com.example.demo.service.PessoaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

    @Autowired
    private PessoaService pessoaService;


    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        List<Pessoa> pessoas = pessoaService.convertPessoaToFile();
        pessoaService.saveAll(pessoas);
        pessoaService.filtraPessoasAptasDoacao();
        List<InfoCandidatos> infosByEstados = pessoaService.getInfosByEstados();

        System.out.println(infosByEstados);
    }
}
