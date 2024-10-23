package com.home.mongocloud.models;

import org.springframework.data.mongodb.core.mapping.*;
import lombok.*;
import lombok.experimental.*;

@Document(collection = "Alunos")
@Accessors(chain = true)
@NoArgsConstructor
@Data
@Getter
@Setter
public class Aluno {  
    @MongoId(FieldType.OBJECT_ID)
    private String id;

    @Field(name = "Nome")
    private String nome;

    @Field(name = "Matrícula")
    private String matricula;

    @Field(name = "Data Nascimento")
    private String dataNascimento;

}
