package br.com.alura.adopet.api.validacao;

import br.com.alura.adopet.api.dto.DadosCadastroAdocao;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.repository.PetRepository;
import br.com.alura.adopet.api.validacao.interfaces.IValidacao;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Adocao;

import java.util.Optional;

public class ValidacaoPetAdotado  implements IValidacao<DadosCadastroAdocao> {

    private PetRepository repository;

    @Override
    public void valida(DadosCadastroAdocao obj) throws ValidacaoException {
        Optional<Pet> pet = repository.findById(obj.idPet());

        if(pet.isEmpty()) {
            throw new ValidacaoException("Pet nao encontrado!");
        }

        if (pet.get().getAdotado()) {
            throw new ValidacaoException("Pet já foi adotado!");
        }
    }
}
