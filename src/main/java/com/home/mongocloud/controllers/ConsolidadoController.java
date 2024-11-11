package com.home.mongocloud.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.home.mongocloud.models.ConsolidadoSerie;
import com.home.mongocloud.repositories.ConsolidadoSerieRespository;


@RestController
public class ConsolidadoController {

    ConsolidadoSerieRespository consolidadoSerieRespository;

    public ConsolidadoController(ConsolidadoSerieRespository consolidadoSerieRespository) {
        this.consolidadoSerieRespository = consolidadoSerieRespository;
    }
    
    @GetMapping("/consolidadoQtdAno")
    public ResponseEntity<List<ConsolidadoSerie>> consolidadoQtdAno() {
        
            List<ConsolidadoSerie> lista = consolidadoSerieRespository.consolidadoQtdAno();
            return ResponseEntity.ok(lista);
        
    }

    @GetMapping("/consolidadoQtdProviderAno")
    public ResponseEntity<List<ConsolidadoSerie>> consolidadoQtdProviderAno(@RequestParam(value = "provider", defaultValue = "Netflix") String provider) {
            
            List<ConsolidadoSerie> lista = consolidadoSerieRespository.consolidadoQtdAno();
            List<ConsolidadoSerie> novaLista = lista.stream()
                                               .filter(c -> c.getProvider().equals(provider))
                                               .collect(Collectors.toList());

            System.out.println(novaLista);                                   
            return ResponseEntity.ok(novaLista);
        
    }
}
