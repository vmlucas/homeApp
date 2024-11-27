package com.home.mongocloud.repositories;

import com.home.mongocloud.models.*;
import java.util.*;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConsolidadoSerieRespository extends MongoRepository<ConsolidadoSerie, String>{

    /*
     * fetch on mongodb a list of ConsolidadoSerie. A list of objects with year, provider and a total with status "Watched". 
     * Total of series grouped by year and provider
     * 
     * return List<ConsolidadoSerie>
     */
    @Aggregation(pipeline = {
        "{'$match': {'status': 'Watched' }}",
        "{ $group:{'_id': { 'Ano': $year }, 'Total' : { $count : {} }} }",
        "{ $project:{'Ano' : '$_id.Ano', 'Total' : '$Total','_id':0} }",
        "{'$sort': { 'Ano': 1 } }"
    })
    List<ConsolidadoSerie> consolidadoQtdAno();  

   /*
     * fetch on mongodb a list of ConsolidadoSerie. A list of objects with year, provider and a total with status "Watched". 
     * Total of series grouped by year and provider
     * 
     * return List<ConsolidadoSerie>
     */
    @Aggregation(pipeline = {
        "{'$match': {'status': 'Watched' }}",
        "{ $group:{'_id': { 'Ano': $year, 'Provider': $provider }, 'Total' : { $count : {} }} }",
        "{ $project:{'Ano' : '$_id.Ano','Provider' : '$_id.Provider', 'Total' : '$Total','_id':0} }",
        "{'$sort': { 'Ano': 1,'Provider':1 } }"
    })
    List<ConsolidadoSerie> consolidadoQtdProviderAno();    

        

}
