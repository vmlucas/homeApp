package com.home.mongocloud.dtos;

import com.home.mongocloud.models.*;
import lombok.*;

@Setter
@Getter
public class CreateSerieDto {

    private int year;
    private String seriesName;
    private String season;
    private String provider;
    private String app;
    private String status; 
    private String imdbId;

    public Serie toSerie() {
        return new Serie().setYear(year).setSeriesName(seriesName).setSeason(season).setProvider(provider).setApp(app).setStatus(status).setImdbId(imdbId);
    }
}
