package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.DadosCadastroAdocao;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Adocao;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import br.com.alura.adopet.api.repository.PetRepository;
import br.com.alura.adopet.api.repository.TutorRepository;
import br.com.alura.adopet.api.service.interfaces.IEmail;
import br.com.alura.adopet.api.validacao.interfaces.IValidacao;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

public class AdocaoService {

    private static final String REMETENTE = "adopet@email.com.br";

    @Autowired
    private AdocaoRepository repository;

    private TutorRepository tutorRepository;

    private PetRepository petRepository;

    @Autowired
    private IEmail emailSender;

    @Autowired
    private List<IValidacao<DadosCadastroAdocao>> validacoes;


    public void solicitar(DadosCadastroAdocao dadosCadastroAdocao) throws ValidacaoException {
        for(IValidacao<DadosCadastroAdocao> validacao : validacoes) {
            validacao.valida(dadosCadastroAdocao);
        }

        Optional<Tutor> tutor = tutorRepository.findById(dadosCadastroAdocao.idTutor());
        Optional<Pet> pet = petRepository.findById(dadosCadastroAdocao.idPet());

        Adocao adocao = new Adocao(tutor.get(), pet.get(), "");

        repository.save(adocao);

        String msg = "Olá " +adocao.getPet().getAbrigo().getNome()
                +"!\n\nUma solicitação de adoção foi registrada hoje para o pet: "
                +adocao.getPet().getNome() +". \nFavor avaliar para aprovação ou reprovação.";

        this.emailSender.sendEmail("Solicitação de adoção", msg, REMETENTE, adocao.getPet().getAbrigo().getEmail());
    }

    public void aprovar(Long idAdocao) throws ValidacaoException {
        Optional<Adocao> adocaoOptional = repository.findById(idAdocao);

        if(adocaoOptional.isEmpty()) {
            throw new ValidacaoException("Adocao nao cadastrada!");
        }

        Adocao adocao = adocaoOptional.get();

        adocao.aprovaAdocao();

        String msg = "Parabéns " +adocao.getTutor().getNome()
                +"!\n\nSua adoção do pet " +adocao.getPet().getNome()
                +", solicitada em " +adocao.getData()
                .format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))
                +", foi aprovada.\nFavor entrar em contato com o abrigo " +adocao.getPet().getAbrigo().getNome()
                +" para agendar a busca do seu pet.";

        this.emailSender.sendEmail("Adoção aprovada", msg, REMETENTE, adocao.getTutor().getEmail());
    }

    @PutMapping("/reprovar")
    @Transactional
    public ResponseEntity<String> reprovar(Long idAdocao) throws ValidacaoException {
        Optional<Adocao> adocaoOptional = repository.findById(idAdocao);

        if(adocaoOptional.isEmpty()) {
            throw new ValidacaoException("Adocao nao cadastrada!");
        }

        Adocao adocao = adocaoOptional.get();

        adocao.reprovaAdocao();

        repository.save(adocao);

        String msg = "Olá " +adocao.getTutor().getNome() +"!\n\nInfelizmente sua adoção do pet "
                +adocao.getPet().getNome() +", solicitada em "
                +adocao.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))
                +", foi reprovada pelo abrigo " +adocao.getPet().getAbrigo().getNome()
                +" com a seguinte justificativa: " +adocao.getJustificativaStatus();

        this.emailSender.sendEmail("Adoção reprovada", msg, REMETENTE, adocao.getTutor().getEmail());

        return ResponseEntity.ok().build();
    }
}
