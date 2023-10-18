package br.com.alura.adopet.api.validacao;

import br.com.alura.adopet.api.validacao.interfaces.IValidacao;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Adocao;

public class ValidacaoPetAdotado  implements IValidacao<Adocao> {

    @Override
    public void valida(Adocao obj) {
        if (obj.getPet().getAdotado()) {
            throw new ValidacaoException("Pet já foi adotado!");
        }
    }
}
