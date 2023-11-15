package br.com.alura.adopet.api.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DadosCadastroAdocao(@NotNull Long idTutor, @NotNull Long idPet, String motivo) {

}
