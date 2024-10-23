package com.home.mongocloud.repositories;

import com.home.mongocloud.models.*;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public interface SerieRepository extends MongoRepository<Serie, String> {
    
    @Query("{'seriesName' : /.*?0.*/}")
    List<Serie> buscarSerie(String nome);

    @Aggregation(pipeline = {
            "  {'$match': {'status': ?1}}",
            "  {'$match': {'year': ?0}}"
    })
    List<Serie> buscarSeries(int ano,String status);

}
