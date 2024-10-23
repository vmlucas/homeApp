package com.home.mongocloud.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.home.mongocloud.models.Nota;

@Repository
public interface NotaRepository extends MongoRepository<Nota, String>{

    @Aggregation(pipeline = {
            "  {'$match': {'Nome Aluno': ?0}}",
            "  {'$match': {'Ano Letivo': ?1}}"
    })
    List<Nota> buscarNotas(String nomeAluno, int ano);

    @Aggregation(pipeline = {
        "  {'$match': {'Nome Aluno': ?0}}",
        "  {'$match': {'Ano Letivo': ?1}}",
        "  {'$match': {'Disciplina': ?2}}"
    })
    List<Nota> buscarNotas(String nomeAluno, int ano,String materia);

}
