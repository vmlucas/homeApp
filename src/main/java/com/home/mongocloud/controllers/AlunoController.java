package com.home.mongocloud.controllers;

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
    
  AlunoRepository alunoRepository;
  NotaRepository notaRepository;
  
  public AlunoController(AlunoRepository alunoRepository, NotaRepository notaRepository) {
    this.alunoRepository = alunoRepository;
    this.notaRepository = notaRepository;
  }
  
  @GetMapping("/buscarAlunos")
  public ResponseEntity<List<Aluno>> buscarAlunos() {
    
      List<Aluno> alunos = alunoRepository.findAll();
      return ResponseEntity.ok(alunos);
    
  }

  @GetMapping("/buscarAluno")
  public ResponseEntity<Aluno> buscarAluno(@RequestParam(value = "nome", defaultValue = "Victoria de Souza Pires Lucas" ) String nome) {
    
      Aluno aluno = alunoRepository.buscarAluno(nome);
      return ResponseEntity.ok(aluno);
    
  }

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


  @PostMapping("/insereAluno")
  public ResponseEntity<Aluno> insereAluno(@RequestBody CreateAlunoDto createAlunoDto) {
    Aluno alunoCreated = alunoRepository.save(createAlunoDto.toAluno());
    return new ResponseEntity<>(alunoCreated, HttpStatus.CREATED);
  }

  @PostMapping("/insereNotas")
  public ResponseEntity<List<Nota>> createPlayers(@RequestBody List<CreateNotaDto> createNotaDtoList) {
    List<Nota> notas = createNotaDtoList
        .stream()
        .map(CreateNotaDto::toNota)
        .collect(Collectors.toList());
        
    List<Nota> notasCreated = notaRepository.saveAll(notas);
    
    return new ResponseEntity<>(notasCreated, HttpStatus.CREATED);
  }

  
}