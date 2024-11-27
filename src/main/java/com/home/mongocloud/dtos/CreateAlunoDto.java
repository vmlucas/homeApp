package com.home.mongocloud.dtos;

import lombok.Getter;
import lombok.Setter;
import com.home.mongocloud.models.*;
    
@Setter
@Getter
public class CreateAlunoDto {
    private String nome;
    private String matricula;
    private String turno;
    private String dataNascimento;
    private int numChamada;
    private String curso;
    private String turma;
    private int ano;
    
    public Aluno toAluno() {
        return new Aluno().setNome(nome).setMatricula(matricula).setDataNascimento(dataNascimento);
    }
}

