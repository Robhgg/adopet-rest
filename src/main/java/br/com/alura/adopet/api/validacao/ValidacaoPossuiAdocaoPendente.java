package br.com.alura.adopet.api.validacao;

import br.com.alura.adopet.api.validacao.interfaces.IValidacao;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Adocao;
import br.com.alura.adopet.api.model.StatusAdocao;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ValidacaoPossuiAdocaoPendente  implements IValidacao<Adocao> {

    @Autowired
    private AdocaoRepository repository;

    @Override
    public void valida(Adocao obj) {
        List<Adocao> adocoes = repository.findAll();
        for (Adocao a : adocoes) {
            if (a.getTutor() == obj.getTutor() && a.getStatus() == StatusAdocao.AGUARDANDO_AVALIACAO) {
                throw new ValidacaoException("Tutor já possui outra adoção aguardando avaliação!");
            }
        }
    }
}
