package com.home.mongocloud.models;

import lombok.*;
import lombok.experimental.*;

@Accessors(chain = true)
@NoArgsConstructor
@Data
@Getter
@Setter
public class HorizontalPoster {

    private String w360;
    private String w480;
    private String w720;
    private String w1080;
    private String w1440;
    
}

