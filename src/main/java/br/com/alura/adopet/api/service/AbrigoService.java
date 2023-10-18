package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.repository.AbrigoRepository;
import br.com.alura.adopet.api.util.ValidadorUtil;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class AbrigoService {

    @Autowired
    private AbrigoRepository repository;

    /**
     * Cadastra um novo Abrigo no repositório, verificando se os dados do Abrigo já existem.
     *
     * @param abrigo O Abrigo a ser cadastrado. Deve conter informações como nome, email e telefone.
     * @throws ValidacaoException Se os dados do Abrigo (nome, email ou telefone) já existirem no repositório, uma exceção de validação será lançada.
     */
    public void cadastrar(Abrigo abrigo){
        boolean jaCadastrado = repository.existsByNomeOrEmailOrTelefone(abrigo.getNome(), abrigo.getEmail(), abrigo.getTelefone());

        if (jaCadastrado) {
            throw new ValidacaoException("Dados já cadastrados para outro abrigo!");
        }

        repository.save(abrigo);
    }

    /**
     * Lista os Pets com base no ID ou no nome fornecido.
     *
     * @param idOuNome Uma string contendo um ID numérico ou um nome.
     * @return Uma lista de Pets com base na entrada fornecida.
     */
    public List<Pet> listarPets(String idOuNome) {
        try {
            return ValidadorUtil.isNumeric(idOuNome)
                    ? repository.getReferenceById(Long.parseLong(idOuNome)).getPets()
                    : repository.findByNome(idOuNome).getPets();

        } catch (EntityNotFoundException e) {
            return Collections.emptyList();
        }
    }

    public void cadastrarPet(String idOuNome, Pet pet) {
        try {
            Abrigo abrigo = ValidadorUtil.isNumeric(idOuNome)
                    ? repository.getReferenceById(Long.parseLong(idOuNome))
                    : repository.findByNome(idOuNome);

            pet.setAbrigo(abrigo);
            pet.setAdotado(false);
            abrigo.getPets().add(pet);
            repository.save(abrigo);

        } catch(EntityNotFoundException e) {
            throw new ValidacaoException("Nao foi possivel cadastrar o pet no abrigo.");
        }
    }
}
