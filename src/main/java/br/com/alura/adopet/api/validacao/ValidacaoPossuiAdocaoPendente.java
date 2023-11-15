package br.com.alura.adopet.api.validacao;

import br.com.alura.adopet.api.dto.DadosCadastroAdocao;
import br.com.alura.adopet.api.validacao.interfaces.IValidacao;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Adocao;
import br.com.alura.adopet.api.model.StatusAdocao;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ValidacaoPossuiAdocaoPendente  implements IValidacao<DadosCadastroAdocao> {

    @Autowired
    private AdocaoRepository repository;

    @Override
    public void valida(DadosCadastroAdocao obj) {
        List<Adocao> adocoes = repository.findAdocaoByTutorId(obj.idTutor());

        boolean aguardandoAvaliacao = adocoes.stream()
                .anyMatch(a -> a.getStatus() == StatusAdocao.AGUARDANDO_AVALIACAO);

        if(aguardandoAvaliacao) {
            throw new ValidacaoException("Tutor já possui outra adoção aguardando avaliação!");
        }
    }
}
