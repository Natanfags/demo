package com.example.demo.repository;

import com.example.demo.entity.InfoCandidatos;
import com.example.demo.entity.Pessoa;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PessoaRepository extends CrudRepository<Pessoa, String> {


    @Query(nativeQuery = true,
            value = "SELECT * FROM pessoa WHERE DATEDIFF('YEAR', data_nasc, CURRENT_DATE) BETWEEN 16 AND 69 AND peso > 50")
    List<Pessoa> findPessoasAptasDoacao();


    @Query(nativeQuery = true,
            value = "SELECT COUNT(*) AS quantidade, estado FROM pessoa WHERE DATEDIFF('YEAR', data_nasc, CURRENT_DATE) BETWEEN 16 AND 69 AND peso > 50 GROUP BY estado;")
    List<InfoCandidatos> countPessoasAptasDoacao();

}
