package br.com.alura.adopet.api.validacao;

import br.com.alura.adopet.api.dto.DadosCadastroAdocao;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.TutorRepository;
import br.com.alura.adopet.api.validacao.interfaces.IValidacao;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Adocao;
import br.com.alura.adopet.api.model.StatusAdocao;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class ValidacaoLimiteMaximoAdocoes implements IValidacao<DadosCadastroAdocao> {

    @Autowired
    private AdocaoRepository repository;

    private TutorRepository tutorRepository;

    @Override
    public void valida(DadosCadastroAdocao obj){
        Optional<Tutor> tutorOptional = tutorRepository.findById(obj.idTutor());

        if(tutorOptional.isEmpty()) {
            throw new ValidacaoException("Tutor nao encontrado!");
        }

        Tutor tutor = tutorOptional.get();

        List<Adocao> listaAdocoes = tutor.getAdocoes();

        int qtdeAdocoes = (int) tutor.getAdocoes().stream().filter(a -> a.getStatus() == StatusAdocao.APROVADO).count();

        if (qtdeAdocoes == 5) {
                throw new ValidacaoException("Tutor chegou ao limite máximo de 5 adoções!");
        }
    }

}
