package com.example.demo.service;

import com.example.demo.entity.InfoCandidatos;
import com.example.demo.entity.Pessoa;
import com.example.demo.repository.PessoaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PessoaService {

    private final PessoaRepository repository;

    public List<Pessoa> convertPessoaToFile() throws IOException {

        ObjectMapper mapper = JsonMapper.builder()
                .findAndAddModules()
                .build();
        return mapper.readValue(new File("src/main/resources/static/data.json"), mapper.getTypeFactory().constructCollectionType(List.class, Pessoa.class));
    }

    public void saveAll(List<Pessoa> pessoas) {
        repository.saveAll(pessoas).iterator();
    }

    public List<Pessoa> filtraPessoasAptasDoacao() {
        return repository.findPessoasAptasDoacao();
    }

    public List<InfoCandidatos> getInfosByEstados() {
        return repository.countPessoasAptasDoacao();
//        return null;
    }

    /*TODO

    • Quantos candidatos temos nessa lista em cada estado do Brasil?
    • IMC médio em cada faixa de idade de dez em dez anos: 0 a 10; 11 a 20; 21 a 30, etc. (IMC = p  eso /
    altura^2)
    • Qual o percentual de obesos entre os homens e entre as mulheres? (É obeso quem tem IMC > 30)
    • Qual a média de idade para cada tipo sanguíneo?
    • A quantidade de possíveis doadores para cada tipo sanguíneo receptor.
    ATENÇÃO: Somente pessoas com idade de 16 a 69 anos e com peso acima de 50 Kg podem doar sangue
     */
}
