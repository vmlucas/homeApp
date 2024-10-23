package com.home.mongocloud.repositories;

import com.home.mongocloud.models.*;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AlunoRepository extends MongoRepository<Aluno, String> {
    
    @Query("{'Nome' : ?0}")
    Aluno buscarAluno(String nome);
    
    
}

