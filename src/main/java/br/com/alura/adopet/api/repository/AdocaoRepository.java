package br.com.alura.adopet.api.repository;

import br.com.alura.adopet.api.model.Adocao;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AdocaoRepository extends JpaRepository<Adocao, Long> {

    List<Adocao> findAdocaoByPetId(Long idPet);

    List<Adocao> findAdocaoByTutorId(Long idTutor);

}
