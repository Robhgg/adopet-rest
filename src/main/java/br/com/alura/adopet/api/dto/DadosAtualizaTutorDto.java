package br.com.alura.adopet.api.dto;

import br.com.alura.adopet.api.model.Adocao;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record DadosAtualizaTutorDto(@NotNull Long id, @NotNull String telefone, @NotNull String email, List<Adocao> adocoes) {

}
