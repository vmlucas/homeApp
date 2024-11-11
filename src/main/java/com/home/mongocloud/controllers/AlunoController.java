package com.home.mongocloud.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.home.mongocloud.repositories.*;
import com.home.mongocloud.models.*;
import com.home.mongocloud.dtos.*;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class AlunoController {   
    
  @Value("${authAPIKey}")
  private String authAPIKey;
  AlunoRepository alunoRepository;
  NotaRepository notaRepository;
  
  public AlunoController(AlunoRepository alunoRepository, NotaRepository notaRepository) {
    this.alunoRepository = alunoRepository;
    this.notaRepository = notaRepository;
  }
  
  /**
   * 
   * @return - json output for all Aluno
   */
  @GetMapping("/buscarAlunos")
  public ResponseEntity<List<Aluno>> buscarAlunos() {
    
      List<Aluno> alunos = alunoRepository.findAll();
      return ResponseEntity.ok(alunos);
    
  }

  /**
   * 
   * @param nome - Student name
   * @return - json output for the Aluno
   */
  @GetMapping("/buscarAluno")
  public ResponseEntity<Aluno> buscarAluno(@RequestParam(value = "nome", defaultValue = "Victoria de Souza Pires Lucas" ) String nome) {
    
      Aluno aluno = alunoRepository.buscarAluno(nome);
      return ResponseEntity.ok(aluno);
    
  }

  /**
   * if ano = 0, then return a list of all Nota
   * if ano > 0, return a list based on the discipline or all diciplines if discipline is "todas"
   * 
   * @param nome - student name
   * @param ano - student year
   * @param materia - discipline
   * @return json output for the list of Nota 
   */
  @GetMapping("/buscarNotas")
  public ResponseEntity<List<Nota>> buscarNotas(@RequestParam(value = "nome", defaultValue = "Victoria de Souza Pires Lucas" ) String nome,
  @RequestParam(value = "ano", defaultValue = "0" ) int ano,
  @RequestParam(value = "materia", defaultValue = "todas" ) String materia) {
    
    if( ano == 0){
      List<Nota> notas = notaRepository.findAll();
      return ResponseEntity.ok(notas);
    }
    else{
      if( materia.equals("todas")){
        List<Nota> notas = notaRepository.buscarNotas(nome, ano);
        return ResponseEntity.ok(notas);
      }
      else{
        List<Nota> notas = notaRepository.buscarNotas(nome, ano, materia);
      return ResponseEntity.ok(notas); 
      }      
    }
  }


  /**
   * Insert a new Aluno on DB
   * 
   * @param createAlunoDto - An Aluno object to be inserted
   * @return - the status if it was inserted or forbidden if the key was wrong.
   */
  @PostMapping("/insereAluno")
  public ResponseEntity<Aluno> insereAluno(@RequestParam(value = "key", defaultValue = "sem chave") String key,
                                           @RequestBody CreateAlunoDto createAlunoDto) {

    if( key.equals(authAPIKey)) 
    {                                        
      Aluno alunoCreated = alunoRepository.save(createAlunoDto.toAluno());
      return new ResponseEntity<>(alunoCreated, HttpStatus.CREATED);
    }
    else{
      return new ResponseEntity<>(HttpStatus.FORBIDDEN);   
    }
    
  }


  /**
   * Insert a list of Nota on DB
   * 
   * @param createNotaDtoList -  A list of Nota to be inserted
   * @return - the status if it was inserted or forbidden if the key was wrong.
   */
  @PostMapping("/insereNotas")
  public ResponseEntity<List<Nota>> insertNotas(@RequestParam(value = "key", defaultValue = "sem chave") String key,
                                                @RequestBody List<CreateNotaDto> createNotaDtoList) {

    if( key.equals(authAPIKey)) 
    {                                        
      List<Nota> notas = createNotaDtoList
        .stream()
        .map(CreateNotaDto::toNota)
        .collect(Collectors.toList());
        
      List<Nota> notasCreated = notaRepository.saveAll(notas);
    
      return new ResponseEntity<>(notasCreated, HttpStatus.CREATED);
    }
    else{
      return new ResponseEntity<>(HttpStatus.FORBIDDEN);   
    }
    
  }

  
}