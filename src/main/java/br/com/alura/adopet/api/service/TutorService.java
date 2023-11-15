package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.DadosAtualizaTutorDto;
import br.com.alura.adopet.api.dto.DadosCadastroTutorDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.TutorRepository;
import br.com.alura.adopet.api.validacao.interfaces.IValidacao;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;


public class TutorService {

    @Autowired
    private TutorRepository repository;

    @Autowired
    private List<IValidacao<DadosAtualizaTutorDto>> validacoes;

    public boolean cadastrar(DadosCadastroTutorDto dto) throws ValidacaoException {
        if(repository.existsByTelefoneOrEmail(dto.telefone(), dto.email())) {
            throw new ValidacaoException("Dados ja cadastrados!");
        }

        Tutor tutor = new Tutor(dto);

        repository.save(tutor);

        return true;
    }


    /**
     * A existencia do cadastro do tutor já esta sendo verificada na lista de validacoes, por isso esta sendo
     * feito o get() direto do optional
     * @param dto
     * @return
     */
    @PutMapping
    @Transactional
    public boolean atualizar(@RequestBody @Valid DadosAtualizaTutorDto dto) throws ValidacaoException {
        for(IValidacao<DadosAtualizaTutorDto> validacao : validacoes) {
            validacao.valida(dto);
        }

        Tutor tutor = this.repository.findById(dto.id()).get();

        tutor.setEmail(dto.email());
        tutor.setTelefone(dto.telefone());

        this.repository.save(tutor);

        return true;
    }

}
