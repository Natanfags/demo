package com.example.demo.controller;

import com.example.demo.entity.Pessoa;
import com.example.demo.service.PessoaService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@AllArgsConstructor
public class FileUploadController {

    private final PessoaService pessoaService;

    @PostMapping("/file-upload")
    public ResponseEntity<String> fileUpload(@RequestParam("file") MultipartFile file) {

        if (!file.isEmpty()) {
            try {
                List<Pessoa> pessoas = pessoaService.convertPessoaToFile(file.getInputStream());
                pessoaService.saveAll(pessoas);
                return ResponseEntity.ok("Sucesso");
            } catch (IOException e) {
                e.printStackTrace();
                return ResponseEntity.ok("Falha" + e.getMessage());
            }
        } else {
            return ResponseEntity.ok("Invalido");
        }

    }
}
