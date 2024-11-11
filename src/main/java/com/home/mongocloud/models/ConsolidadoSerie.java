package com.home.mongocloud.models;

import lombok.*;
import lombok.experimental.*;
import java.util.*;
import org.springframework.data.mongodb.core.mapping.*;

@Document(collection = "SERIES")
@Accessors(chain = true)
@NoArgsConstructor
@Data
@Getter
@Setter
public class ConsolidadoSerie {

    @Field(name="Ano")
    private int ano;

    @Field(name = "Provider")
    private String provider;

    @Field(name = "Total")
    private int totalSeries;

}
