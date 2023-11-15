package br.com.alura.adopet.api.dto;

import br.com.alura.adopet.api.model.Pet;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.util.List;

public record DadosCadastroAbrigo (@NotBlank
                                   String nome,
                                   @NotBlank
                                   @Pattern(regexp = "\\(?\\d{2}\\)?\\d?\\d{4}-?\\d{4}")
                                   String telefone,
                                   @NotBlank
                                   @Email
                                   String email,
                                   List<Pet> pets) {
}
