package br.com.alura.adopet.api.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "adocoes")
public class Adocao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    private LocalDateTime data;

    @Getter
    @Setter
    @ManyToOne
    private Tutor tutor;

    @Getter
    @Setter
    @OneToOne
    private Pet pet;

    private String motivo;

    @Getter
    @Enumerated(EnumType.STRING)
    private StatusAdocao status;

    @Getter
    private String justificativaStatus;

    public Adocao (Tutor tutor, Pet pet, String motivo) {
        this.data = LocalDateTime.now();
        this.tutor = tutor;
        this.pet = pet;
        this.status = StatusAdocao.AGUARDANDO_AVALIACAO;
    }

    public void aguardandoAvaliacao() {
        this.status = StatusAdocao.AGUARDANDO_AVALIACAO;
    }

    public void aprovaAdocao() {
        this.status = StatusAdocao.APROVADO;
    }

    public void reprovaAdocao() {
        this.status = StatusAdocao.REPROVADO;
    }
}
