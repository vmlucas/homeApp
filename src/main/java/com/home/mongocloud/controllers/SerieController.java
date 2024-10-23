package com.home.mongocloud.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.home.mongocloud.repositories.*;
import com.home.mongocloud.models.*;
import com.home.mongocloud.dtos.*;
import java.util.*;

@RestController
public class SerieController {

    SerieRepository serieRepository;

    public SerieController(SerieRepository serieRepository) {
        this.serieRepository = serieRepository;
    }

    @PostMapping("/insertSerie")
    public ResponseEntity<Serie> createSerie(@RequestParam(value = "key", defaultValue = "sem chave") String key,
                                             @RequestBody CreateSerieDto createSerieDto) {
        
        if( key.equals("voa7F6JJiagvMbZtT7BY0w==")) 
        {                                        
           Serie serieCreated = serieRepository.save(createSerieDto.toSerie());
           return new ResponseEntity<>(serieCreated, HttpStatus.CREATED);
        }
        else{
           return new ResponseEntity<>(HttpStatus.FORBIDDEN);   
        }
    }

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

    @GetMapping("/buscarSerie")
    public ResponseEntity<List<Serie>> buscarSerie(@RequestParam(value = "nome", defaultValue = "Obi wan") String nome) {
        List<Serie> series = serieRepository.buscarSerie(nome);
        return ResponseEntity.ok(series);
        
    }

    @PostMapping("/updateSerie")
    public ResponseEntity<Serie> updateSerie(@RequestParam(value = "key", defaultValue = "sem chave") String key,
                                             @RequestParam(value = "id") String id, @RequestBody CreateSerieDto createSerieDto) {
        
        if( key.equals("voa7F6JJiagvMbZtT7BY0w==")) 
        {
            Optional<Serie> optionalSerie = serieRepository.findById(id);
          
            if (optionalSerie.isEmpty()) {
              return new ResponseEntity<>(null, HttpStatus.OK);
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

    @PostMapping("/deleteSerie")
    public ResponseEntity<Void> deleteSerie(@RequestParam(value = "key", defaultValue = "sem chave") String key,
                                            @RequestParam(value = "id", defaultValue = "abc") String id) {
        
        if( key.equals("voa7F6JJiagvMbZtT7BY0w==")) 
        {
          serieRepository.deleteById(id);
          return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        else{
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);  
        }
    }

}
