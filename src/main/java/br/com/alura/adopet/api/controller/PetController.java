package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.dto.DadosDetalhePet;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.repository.PetRepository;
import br.com.alura.adopet.api.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/pets")
public class PetController {

    @Autowired
    private PetService service;

    @GetMapping
    public ResponseEntity<List<DadosDetalhePet>> listarTodosDisponiveis() {
        List<DadosDetalhePet> listaDto = this.service.listarTodosDisponiveis();

        return ResponseEntity.ok(listaDto);
    }

}
