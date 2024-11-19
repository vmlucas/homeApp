package com.home.mongocloud.models;

import org.springframework.data.mongodb.core.mapping.*;
import lombok.*;
import lombok.experimental.*;

@Document(collection = "SERIES")
@Accessors(chain = true)
@NoArgsConstructor
@Data
@Getter
@Setter
public class Serie {
    @MongoId(FieldType.OBJECT_ID)
    private String id;

    private int year;
    private String seriesName;
    private String season;
    private String provider;
    private String app;
    private String status;
    private String imdbId;
    private String overview;
    private String imageURI;
    private String[] cast;
    private int rating;
}
