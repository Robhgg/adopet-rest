package br.com.alura.adopet.api.validacao;

import br.com.alura.adopet.api.dto.DadosAtualizaTutorDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.repository.TutorRepository;
import br.com.alura.adopet.api.validacao.interfaces.IValidacao;
import org.springframework.beans.factory.annotation.Autowired;

public class ValidacaoDadosInformadosJaCadastrados implements IValidacao<DadosAtualizaTutorDto> {

    @Autowired
    private TutorRepository repository;

    @Override
    public void valida(DadosAtualizaTutorDto dto) {
        if(repository.existsByTelefoneOrEmail(dto.telefone(), dto.email())) {
            throw new ValidacaoException("Outro tutor já contem os dados informados cadastrados!");
        }

    }
}
