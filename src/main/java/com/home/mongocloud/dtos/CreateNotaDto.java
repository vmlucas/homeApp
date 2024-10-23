package com.home.mongocloud.dtos;

import lombok.Getter;
import lombok.Setter;
import com.home.mongocloud.models.*;
    
@Setter
@Getter
public class CreateNotaDto {
    private String nomeAluno;
    private String turno;
    private String curso;
    private String turma;
    private int numChamada;
    private int ano;
    private String disciplina;
    private int periodo;
    private double nota;
    
    public Nota toNota() {
        return new Nota().setNomeAluno(nomeAluno).setAno(ano).setCurso(curso).setDisciplina(disciplina).setNota(nota).setNumChamada(numChamada).setPeriodo(periodo)
                         .setTurma(turma).setTurno(turno);
    }
}



