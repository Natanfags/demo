package com.example.demo.repository;

import com.example.demo.entity.Pessoa;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PessoaRepository extends CrudRepository<Pessoa, String> {

    @Override
    List<Pessoa> findAll();

    @Query(nativeQuery = true,
            value = "SELECT * FROM pessoa WHERE DATEDIFF('YEAR', data_nasc, CURRENT_DATE) BETWEEN 16 AND 69 AND peso > 50")
    List<Pessoa> findPessoasAptasDoacao();

    @Query(nativeQuery = true,
            value = "select * from pessoa where DATEDIFF('YEAR', data_nasc, CURRENT_DATE) BETWEEN :faixaInferior AND :faixaSuperior")
    List<Pessoa> findPessoasByFaixaEtaria(int faixaInferior, int faixaSuperior);

    List<Pessoa> findPessoasBySexo(String sexo);

}
