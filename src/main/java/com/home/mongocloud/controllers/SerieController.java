package com.home.mongocloud.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.home.mongocloud.repositories.*;
import com.home.mongocloud.models.*;
import com.home.mongocloud.dtos.*;
import java.util.*;

@RestController
public class SerieController {

    @Value("${authAPIKey}")
    private String authAPIKey;
    SerieRepository serieRepository;

    public SerieController(SerieRepository serieRepository) {
        this.serieRepository = serieRepository;
    }

    /**
     * Insert a new Serie on DB
     * @param key - a key to authorize the operation
     * @param createSerieDto - A Serie object to be inserted
     * @return - the status if it was inserted or forbidden if the key was wrong.
     */
    @PostMapping("/insertSerie")
    public ResponseEntity<Serie> createSerie(@RequestParam(value = "key", defaultValue = "sem chave") String key,
                                             @RequestBody CreateSerieDto createSerieDto) {
        
        if( key.equals(authAPIKey)) 
        {                                        
           Serie serieCreated = serieRepository.save(createSerieDto.toSerie());
           return new ResponseEntity<>(serieCreated, HttpStatus.CREATED);
        }
        else{
           return new ResponseEntity<>(HttpStatus.FORBIDDEN);   
        }
    }

    /**
     * return a list of Serie based on the year and status. If the year is zero or undefined, it returns all Serie.
     * 
     * @param ano - Year
     * @param status - status ( watched, Watching, Waiting...)
     * @return -  json output with the Serie list
     */
    @GetMapping("/buscarSeries")
    public ResponseEntity<List<Serie>> buscarSeries(@RequestParam(value = "ano", defaultValue = "0") int ano,
            @RequestParam(value = "status", defaultValue = "Watching") String status) {
        if (ano > 0) {
            List<Serie> series = serieRepository.buscarSeries(ano,status);
            return ResponseEntity.ok(series);
        } else {
            List<Serie> series = serieRepository.findAll();
            return ResponseEntity.ok(series);
        }
    }

    /**
     * return a list of Serie based on the name.
     * 
     * @param nome - Serie name
     * @return json output with the Serie list
     */
    @GetMapping("/buscarSerie")
    public ResponseEntity<List<Serie>> buscarSerie(@RequestParam(value = "nome", defaultValue = "Obi wan") String nome) {
        List<Serie> series = serieRepository.buscarSerie(nome);
        return ResponseEntity.ok(series);
        
    }

    /**
     * Update a Serie on DB based on the id.
     * 
     * @param key - a key to authorize the operation
     * @param id - Serie id to be updated
     * @param createSerieDto - A Serie object to be updated
     * @return - the status ok if it was inserted, no content if the id was not found or forbidden if the key was wrong.
     */
    @PostMapping("/updateSerie")
    public ResponseEntity<Serie> updateSerie(@RequestParam(value = "key", defaultValue = "sem chave") String key,
                                             @RequestParam(value = "id") String id, @RequestBody CreateSerieDto createSerieDto) {
        
        if( key.equals(authAPIKey)) 
        {
            Optional<Serie> optionalSerie = serieRepository.findById(id);
          
            if (optionalSerie.isEmpty()) {
              return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
          
            Serie serieToUpdate = optionalSerie.get()
              .setApp(createSerieDto.getApp())
              .setProvider(createSerieDto.getProvider())
              .setStatus(createSerieDto.getStatus())
              .setSeason(createSerieDto.getSeason())
              .setSeriesName(createSerieDto.getSeriesName())
              .setYear(createSerieDto.getYear());
        
            //save the new version
            Serie serieUpdated = serieRepository.save(serieToUpdate);
        
            return new ResponseEntity<>(serieUpdated, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);  
        }
    }

    /**
     * delete a Serie on DB based on the id.
     * 
     * @param key - a key to authorize the operation
     * @param id - Serie id to be deleted
     * @return - the status no content if it was deleted or forbidden if the key was wrong.
     */
    @PostMapping("/deleteSerie")
    public ResponseEntity<Void> deleteSerie(@RequestParam(value = "key", defaultValue = "sem chave") String key,
                                            @RequestParam(value = "id", defaultValue = "abc") String id) {
        
        if( key.equals(authAPIKey)) 
        {
          serieRepository.deleteById(id);
          return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        else{
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);  
        }
    }

}
