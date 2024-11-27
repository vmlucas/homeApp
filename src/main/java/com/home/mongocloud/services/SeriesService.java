package com.home.mongocloud.services;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.*;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;
import com.home.mongocloud.dtos.CreateSerieDto;
import com.home.mongocloud.models.Serie;
import com.home.mongocloud.models.StreamingInfo;
import com.home.mongocloud.repositories.SerieRepository;
import com.home.mongocloud.repositories.SeriesIMDBRepository;

@Service
public class SeriesService {

    @Value("${authAPIKey}")
    private String authAPIKey;
    @Autowired
    MongoTemplate mongoTemplate;
    SerieRepository serieRepository;
    SeriesIMDBRepository imdbRepo;

    public SeriesService(SerieRepository serieRepository, SeriesIMDBRepository imdbRepo) {
        this.serieRepository = serieRepository;
        this.imdbRepo = imdbRepo;
    }

    public Serie createSerie(String key,CreateSerieDto createSerieDto) throws Exception{
        
        if( key.equals(authAPIKey)) 
        {                                        
           Serie serieCreated = serieRepository.save(createSerieDto.toSerie());
           return serieCreated;
        }
        else{
            throw new Exception("Chave Inválida");    
        }
    }

    public Page<Serie> buscarSeries(int ano, String status, int pageNo,int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        if (ano > 0) {
            Page<Serie> series = buscarSeries(ano,status,pageable);
            System.out.println("buscando Serie");
            for (Serie element : series) {
                if( element.getImdbId() != null && element.getImageURI() == null){
                   System.out.println("buscando dados API"); 
                   StreamingInfo info = imdbRepo.getStreamingInfo(element.getImdbId());
                   element.setOverview(info.getOverview());
                   if(info.getImageSet() != null ){
                     element.setImageURI(info.getImageSet().getHorizontalPoster().getW720());
                   }
                   element.setCast(info.getCast());
                   element.setRating(info.getRating());

                   serieRepository.save(element);
                }
            }
            return series;
        } else {
            Page<Serie> allSeries = serieRepository.findAll(pageable);
            for (Serie element : allSeries) {
                if( element.getImdbId() != null && element.getImageURI() == null){
                   StreamingInfo info = imdbRepo.getStreamingInfo(element.getImdbId());
                   element.setOverview(info.getOverview());
                   element.setImageURI(info.getImageSet().getHorizontalPoster().getW720());
                   element.setCast(info.getCast());
                   element.setRating(info.getRating());
                   
                   serieRepository.save(element);
                }
            }
            return allSeries;
        }
    }

    
    public List<Serie> buscarSerie( String nome) {
        List<Serie> series = serieRepository.buscarSerie(nome);
        for (Serie element : series) {
            if( element.getImdbId() != null && element.getImageURI() == null){
               StreamingInfo info = imdbRepo.getStreamingInfo(element.getImdbId());
               element.setOverview(info.getOverview());
               element.setImageURI(info.getImageSet().getHorizontalPoster().getW720());
               element.setCast(info.getCast());
               element.setRating(info.getRating());

               serieRepository.save(element);
            }
        }
        return series;
        
    }

    /*
     *  "  {'$match': {'status': ?1}}",
            "  {'$match': {'year': ?0}}"
     */
    private Page<Serie> buscarSeries(int ano, String status, Pageable pageable){
        Query query = new Query();
        query.addCriteria(
            new Criteria().andOperator(
                Criteria.where("year").is(ano),
                Criteria.where("status").is(status)
            )
        );
        query.with(pageable);
        Long totalCount = mongoTemplate.count(query, Serie.class);
        System.out.println("TOTAL "+totalCount);
        List<Serie> results = mongoTemplate.find(query.with(pageable), Serie.class);
        Page<Serie> resultsWithPage = PageableExecutionUtils.getPage(results, pageable, () -> totalCount);
        return resultsWithPage;
    }
    /**
     * Update a Serie on DB based on the id.
     * 
     * @param key - a key to authorize the operation
     * @param id - Serie id to be updated
     * @param createSerieDto - A Serie object to be updated
     * @return - the status ok if it was inserted, no content if the id was not found or forbidden if the key was wrong.
     */
    public Serie updateSerie(String key, String id, CreateSerieDto createSerieDto) throws Exception{
        
        if( key.equals(authAPIKey)) 
        {
            Optional<Serie> optionalSerie = serieRepository.findById(id);
          
            if (optionalSerie.isEmpty()) {
              return null;
            }
          
            Serie serieToUpdate = optionalSerie.get()
              .setApp(createSerieDto.getApp())
              .setProvider(createSerieDto.getProvider())
              .setStatus(createSerieDto.getStatus())
              .setSeason(createSerieDto.getSeason())
              .setSeriesName(createSerieDto.getSeriesName())
              .setYear(createSerieDto.getYear())
              .setImdbId( createSerieDto.getImdbId());
        
            //save the new version
            Serie serieUpdated = serieRepository.save(serieToUpdate);
        
            return serieUpdated;
        }
        else{
            throw new Exception("Chave Inválida");  
        }
    }

    /**
     * delete a Serie on DB based on the id.
     * 
     * @param key - a key to authorize the operation
     * @param id - Serie id to be deleted
     * @return - the status no content if it was deleted or if the key was wrong.
     */
    public String deleteSerie(String key, String id) throws Exception{
        
        if( key.equals(authAPIKey)) 
        {
          serieRepository.deleteById(id);
          return "Serie Apagada";
        }
        else{
            throw new Exception("Chave Inválida");  
        }
    }
}
