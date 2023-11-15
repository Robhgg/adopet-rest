package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.DadosDetalhePet;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class PetService {

    @Autowired
    private PetRepository repository;

    public List<DadosDetalhePet> listarTodosDisponiveis() {
        //Poderia ter feito a busca utilizando uma query findAllByAdotadoFalse
        List<Pet> disponiveis = this.repository.findAllByAdotadoFalse()
                .stream()
                .toList();

        if(disponiveis.isEmpty()) {
            return Collections.emptyList();
        }

        return disponiveis.stream()
                .map(DadosDetalhePet::new)
                .toList();
    }
}
