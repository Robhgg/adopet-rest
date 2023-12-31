package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.dto.DadosAtualizaTutorDto;
import br.com.alura.adopet.api.dto.DadosCadastroTutorDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.service.TutorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tutores")
public class TutorController {

    @Autowired
    private TutorService service;


    @PostMapping
    @Transactional
    public ResponseEntity<String> cadastrar(@RequestBody @Valid DadosCadastroTutorDto dto) {
        try {
            this.service.cadastrar(dto);

            return ResponseEntity.ok().build();

        } catch(ValidacaoException e) {
            return ResponseEntity.badRequest().body(e.getMessage());

        }
    }

    @PutMapping
    @Transactional
    public ResponseEntity<String> atualizar(@RequestBody @Valid DadosAtualizaTutorDto dto) {
        try {
            this.service.atualizar(dto);

            return ResponseEntity.ok().build();

        } catch (ValidacaoException e) {
            return ResponseEntity.badRequest().body(e.getMessage());

        }

    }

}
