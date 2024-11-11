package com.home.mongocloud.repositories;

import com.home.mongocloud.models.*;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public interface SerieRepository extends MongoRepository<Serie, String> {
    

    /*
     * fetch on mongodb a list based on Serie name
     * 
     * input - serie name
     * return List<Serie>
     */
    @Query("{'seriesName' : /.*?0.*/}")
    List<Serie> buscarSerie(String nome);


    /*
     * fetch on mongodb a list based on Serie status and year
     * 
     * input - serie year and status
     * return List<Serie>
     */
    @Aggregation(pipeline = {
            "  {'$match': {'status': ?1}}",
            "  {'$match': {'year': ?0}}"
    })
    List<Serie> buscarSeries(int ano,String status);

}
