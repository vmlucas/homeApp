package com.home.mongocloud.controllers;

import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import com.home.mongocloud.services.SeriesService;
import com.home.mongocloud.models.*;
import com.home.mongocloud.dtos.*;
import java.util.*;

@RestController
public class SerieController {

    
    SeriesService service;

    public SerieController(SeriesService service) {
        this.service = service;
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
        
        try{
           Serie serieCreated = service.createSerie(key, createSerieDto);
           return new ResponseEntity<>(serieCreated, HttpStatus.CREATED);
        }
        catch(Exception e){
           Serie s = new Serie();
           s.setSeriesName(e.getMessage());
           return new ResponseEntity<>(s, HttpStatus.FORBIDDEN);
     
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
    public ResponseEntity<Page<Serie>> buscarSeries(@RequestParam(value = "ano", defaultValue = "0") int ano,
            @RequestParam(value = "status", defaultValue = "Watching") String status,
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize) {

        Page<Serie> series = service.buscarSeries(ano, status, pageNo, pageSize);       
        return ResponseEntity.ok(series);
        
    }

    /**
     * return a list of Serie based on the name.
     * 
     * @param nome - Serie name
     * @return json output with the Serie list
     */
    @GetMapping("/buscarSerie")
    public ResponseEntity<List<Serie>> buscarSerie(@RequestParam(value = "nome", defaultValue = "Obi wan") String nome) {
        List<Serie> series = service.buscarSerie(nome);
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
        
        try{
            Serie serieUpdated = service.updateSerie(key, id, createSerieDto);
            return new ResponseEntity<>(serieUpdated, HttpStatus.OK);
        }
        catch(Exception e){
            Serie s = new Serie();
            s.setSeriesName(e.getMessage());
            return new ResponseEntity<>(s, HttpStatus.FORBIDDEN);
     
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
    public ResponseEntity<String> deleteSerie(@RequestParam(value = "key", defaultValue = "sem chave") String key,
                                            @RequestParam(value = "id", defaultValue = "abc") String id) {
        
        try{
            String msg = service.deleteSerie(key, id);
            return new ResponseEntity<>(msg,HttpStatus.NO_CONTENT);
        }
        catch(Exception e){
           return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
     
        }   
    }

}
