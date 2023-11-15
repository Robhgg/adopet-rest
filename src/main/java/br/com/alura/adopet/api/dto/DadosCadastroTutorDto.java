package br.com.alura.adopet.api.dto;

import br.com.alura.adopet.api.model.Tutor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosCadastroTutorDto(@NotNull String nome, @NotNull String telefone, @NotNull String email) {

    public DadosCadastroTutorDto(Tutor tutor) {
        this(tutor.getNome(), tutor.getTelefone(), tutor.getEmail());
    }
}
