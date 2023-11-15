package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.dto.DadosCadastroAdocao;
import br.com.alura.adopet.api.service.AdocaoService;
import br.com.alura.adopet.api.exception.ValidacaoException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/adocoes")
public class AdocaoController {

    @Autowired
    private AdocaoService service;

    @PostMapping
    @Transactional
    public ResponseEntity<String> solicitar(@RequestBody @Valid DadosCadastroAdocao dadosCadastroAdocao) {
        try {
            this.service.solicitar(dadosCadastroAdocao);

            return ResponseEntity.ok().build();

        } catch (ValidacaoException e) {
            return ResponseEntity.badRequest().body(e.getMessage());

        }
    }

    @PutMapping("/aprovar")
    @Transactional
    public ResponseEntity<String> aprovar(@RequestBody @Valid @NotNull Long idAdocao) {
        try {
            this.service.aprovar(idAdocao);

            return ResponseEntity.ok().build();

        } catch (ValidacaoException e) {
            return ResponseEntity.badRequest().body(e.getMessage());

        }
    }

    @PutMapping("/reprovar")
    @Transactional
    public ResponseEntity<String> reprovar(@RequestBody @Valid Long idAdocao) {
        try {
            this.service.reprovar(idAdocao);

            return ResponseEntity.ok().build();

        } catch (ValidacaoException e) {
            return ResponseEntity.badRequest().body(e.getMessage());

        }


    }

}
