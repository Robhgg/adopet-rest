package br.com.alura.adopet.api.validacao;

import br.com.alura.adopet.api.dto.DadosAtualizaTutorDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.TutorRepository;
import br.com.alura.adopet.api.validacao.interfaces.IValidacao;

import java.util.Optional;

public class ValidacaoTutorNaoCadastrado implements IValidacao<DadosAtualizaTutorDto> {

    private TutorRepository repository;

    @Override
    public void valida(DadosAtualizaTutorDto dto) {
        if(!this.repository.existsById(dto.id())) {
            throw new ValidacaoException("Cadastro do tutor informado nao encontrado!");
        }
    }
}
