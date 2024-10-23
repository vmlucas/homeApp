package com.home.mongocloud.models;

import org.springframework.data.mongodb.core.mapping.*;

import lombok.*;
import lombok.experimental.Accessors;

@Document(collection = "Notas")
@Accessors(chain = true)
@NoArgsConstructor
@Data
@Setter
@Getter
public class Nota {

    @MongoId(FieldType.OBJECT_ID)
    private String id;

    @Field(name = "Nome Aluno")
    private String nomeAluno;

    @Field(name = "Turno")
    private String turno;

    @Field(name = "Tipo Curso")
    private String curso;

    @Field(name = "Turma")
    private String turma;

    @Field(name = "No Chamada")
    private int numChamada;

    @Field(name = "Ano Letivo")
    private int ano;

    @Field(name = "Disciplina")
    private String disciplina;

    @Field(name="Periodo")      
    private int periodo;

    @Field(name="Nota")      
    private double nota;

}
