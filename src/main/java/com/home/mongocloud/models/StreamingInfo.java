package com.home.mongocloud.models;

import lombok.*;
import lombok.experimental.*;

@Accessors(chain = true)
@NoArgsConstructor
@Data
@Getter
@Setter
public class StreamingInfo {

    private String imdbId;
    private String overview;
    private ImageSet imageSet;
    private String[] cast;
    private int rating;
    
}
