package com.example.demo.service;

import com.example.demo.entity.FaixaEtaria;
import com.example.demo.entity.Pessoa;
import com.example.demo.entity.enums.TipoSanguineoEnum;
import com.example.demo.entity.info.InfoEstado;
import com.example.demo.entity.info.InfoIMCFaixaEtaria;
import com.example.demo.entity.info.InfoMapaCompatibilidades;
import com.example.demo.entity.info.InfoMediaIdadePorTipoSanguineo;
import com.example.demo.entity.info.InfoPercentualObesidade;
import com.example.demo.repository.PessoaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PessoaService {

    public static final int IMC_LIMITE_OBESIDADE = 30;
    public static final int CEM = 100;
    private final PessoaRepository repository;

    public List<Pessoa> convertPessoaToFile(InputStream inputStream) throws IOException {

        ObjectMapper mapper = JsonMapper.builder()
                .findAndAddModules()
                .build();
        return mapper.readValue(inputStream, mapper.getTypeFactory().constructCollectionType(List.class, Pessoa.class));
    }

    public void saveAll(List<Pessoa> pessoas) {
        repository.saveAll(pessoas).iterator();
    }

    public List<InfoEstado> getInfoPessoasByEstados() {

        List<Pessoa> pessoas = repository.findAll();

        Map<String, Long> resultado = pessoas.stream()
                .collect(Collectors.groupingBy(Pessoa::getEstado, Collectors.counting()));

        return resultado.entrySet().stream()
                .map(entry -> new InfoEstado(entry.getKey(), entry.getValue()))
                .toList();
    }

    public List<Pessoa> filtrarFaixaEtaria(int dataInferior, int dataSuperior) {
        return repository.findPessoasByFaixaEtaria(dataInferior, dataSuperior);
    }

    public Double calculaMediaIMCPorFaixaEtaria(int dataInferior, int dataSuperior) {

        List<Pessoa> pessoas = this.filtrarFaixaEtaria(dataInferior, dataSuperior);
        OptionalDouble average = getOptionalDouble(pessoas);

        if (average.isEmpty()) {
            return 0.0;
        }
        return average.getAsDouble();
    }

    public InfoPercentualObesidade calculaPercentualObesidade() {

        List<Pessoa> pessoasMasculino = repository.findPessoasBySexo("Masculino");
        List<Pessoa> pessoasFeminino = repository.findPessoasBySexo("Feminino");
        long totalMasculino = pessoasMasculino.size();
        long totalFeminino = pessoasFeminino.size();

        long masculinoObesidade = pessoasMasculino.stream().filter(p -> calcularIMCPessoa(p) > IMC_LIMITE_OBESIDADE).count();
        long femininoObesidade = pessoasFeminino.stream().filter(p -> calcularIMCPessoa(p) > IMC_LIMITE_OBESIDADE).count();

        double percentualMasculino = (double) masculinoObesidade / totalMasculino * CEM;
        double percentualFeminino = (double) femininoObesidade / totalFeminino * CEM;

        return new InfoPercentualObesidade(percentualMasculino, percentualFeminino);
    }

    public List<InfoMediaIdadePorTipoSanguineo> calculaMediaIdadePorTipoSanguineo() {

        List<Pessoa> all = repository.findAll();
        Map<String, Double> collect = all.stream().collect(Collectors.groupingBy(Pessoa::getTipoSanguineo, Collectors.averagingInt(Pessoa::getIdade)));

        List<InfoMediaIdadePorTipoSanguineo> info = new ArrayList<>();

        for (Map.Entry<String, Double> entry : collect.entrySet()) {
            InfoMediaIdadePorTipoSanguineo infoMediaIdadePorTipoSanguineo = new InfoMediaIdadePorTipoSanguineo();
            infoMediaIdadePorTipoSanguineo.setTipoSanguineo(entry.getKey());
            infoMediaIdadePorTipoSanguineo.setMediaIdade(entry.getValue());

            info.add(infoMediaIdadePorTipoSanguineo);
        }
        return info;
    }

    public List<InfoMapaCompatibilidades> listCompatibilidadesPorTipoSanguineo() {

        List<InfoMapaCompatibilidades> infos = new ArrayList<>();
        List<Pessoa> pessoasAptasDoacao = repository.findPessoasAptasDoacao();

        TipoSanguineoEnum[] tipoSanguineoEnums = TipoSanguineoEnum.values();

        for (TipoSanguineoEnum tipoSanguineoEnum : tipoSanguineoEnums) {
            long count = pessoasAptasDoacao.stream()
                    .filter(pessoa -> tipoSanguineoEnum.getTiposCompativeis().contains(pessoa.getTipoSanguineo()))
                    .count();

            InfoMapaCompatibilidades infoMapaCompatibilidades = new InfoMapaCompatibilidades();
            infoMapaCompatibilidades.setTipoSanguineo(tipoSanguineoEnum.getTipoSanguineo());
            infoMapaCompatibilidades.setQuantidade(count);

            infos.add(infoMapaCompatibilidades);
        }

        return infos;
    }

    public List<InfoIMCFaixaEtaria> calculaMediaPorFaixaEtaria() {

        FaixaEtaria faixaEtaria = new FaixaEtaria();
        List<FaixaEtaria> faixas = faixaEtaria.todasAsFaixas();
        List<InfoIMCFaixaEtaria> list = new ArrayList<>();

        faixas.forEach(f -> {
            InfoIMCFaixaEtaria infoIMCFaixaEtaria = new InfoIMCFaixaEtaria();
            Double v = calculaMediaIMCPorFaixaEtaria(f.getFaixaInferior(), f.getFaixaSuperior());
            infoIMCFaixaEtaria.setFaixa(f.getFaixaInferior().toString() + " a " + f.getFaixaSuperior().toString() + " anos");
            infoIMCFaixaEtaria.setMediaIMA(v);
            list.add(infoIMCFaixaEtaria);
        });

        return list;
    }

    private static double calcularIMCPessoa(Pessoa pessoa) {
        return pessoa.calcularIMC();
    }

    private static OptionalDouble getOptionalDouble(List<Pessoa> pessoas) {
        OptionalDouble mediaIMC = pessoas.stream().mapToDouble(p -> p.getPeso() / (p.getAltura() * p.getAltura())).average();
        if (mediaIMC.isEmpty()) {
            return OptionalDouble.empty();
        }
        return mediaIMC;
    }
}
