package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.DadosCadastroAbrigo;
import br.com.alura.adopet.api.dto.DadosCadastroPet;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.repository.AbrigoRepository;
import br.com.alura.adopet.api.repository.PetRepository;
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

    @Autowired
    private PetRepository petRepository;

    /**
     * Cadastra um novo Abrigo no repositório, verificando se os dados do Abrigo já existem.
     *
     * @param abrigo O Abrigo a ser cadastrado. Deve conter informações como nome, email e telefone.
     * @throws ValidacaoException Se os dados do Abrigo (nome, email ou telefone) já existirem no repositório, uma exceção de validação será lançada.
     */
    public void cadastrar(DadosCadastroAbrigo dadosCadastroAbrigoabrigo) throws ValidacaoException {
        boolean jaCadastrado = repository.existsByNomeOrEmailOrTelefone(dadosCadastroAbrigoabrigo.nome(), dadosCadastroAbrigoabrigo.email(),
                dadosCadastroAbrigoabrigo.telefone());

        if (jaCadastrado) {
            throw new ValidacaoException("Dados já cadastrados para outro abrigo!");
        }

        // TODO
        // Poderia ter passado o dto como parâmetro do construtor.
        Abrigo abrigo = new Abrigo(dadosCadastroAbrigoabrigo.nome(), dadosCadastroAbrigoabrigo.email(), dadosCadastroAbrigoabrigo.telefone(),
                dadosCadastroAbrigoabrigo.pets());

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
                    ? repository.findByAbrigoId(Long.parseLong(idOuNome))
                    : repository.findByNome(idOuNome).getPets();

        } catch (EntityNotFoundException e) {
            return Collections.emptyList();
        }
    }

    /**
     * Cadastra um novo pet em um abrigo com base em um ID numérico ou nome.
     * Se o parâmetro 'idOuNome' for numérico, o método busca o abrigo pelo ID.
     * Se for um nome, o método busca o abrigo pelo nome.
     * Em seguida, associa o pet ao abrigo e marca o pet como não adotado.
     * O abrigo é atualizado com o novo pet.
     *
     * @param idOuNome A ID numérica ou nome do abrigo.
     * @param dadosCadastroPet O objeto Pet a ser cadastrado.
     * @throws ValidacaoException Se o abrigo não for encontrado, uma exceção é lançada.
     */
    public void cadastrarPet(String idOuNome, DadosCadastroPet dadosCadastroPet) throws ValidacaoException {
        try {
            Abrigo abrigo = ValidadorUtil.isNumeric(idOuNome)
                    ? repository.getReferenceById(Long.parseLong(idOuNome))
                    : repository.findByNome(idOuNome);

            Pet pet = new Pet(
                    dadosCadastroPet.tipo(),
                    dadosCadastroPet.nome(),
                    dadosCadastroPet.raca(),
                    dadosCadastroPet.idade(),
                    dadosCadastroPet.cor(),
                    dadosCadastroPet.Peso(),
                    abrigo);

            abrigo.getPets().add(pet);
            repository.save(abrigo);

        } catch(EntityNotFoundException e) {
            throw new ValidacaoException("Nao foi possivel cadastrar o pet no abrigo.");

        }
    }

    public List<Abrigo> buscaTodos() {
        return repository.findAll();
    }
}
