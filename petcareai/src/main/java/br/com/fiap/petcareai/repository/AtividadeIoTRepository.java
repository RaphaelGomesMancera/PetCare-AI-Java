package br.com.fiap.petcareai.repository;

import br.com.fiap.petcareai.domain.AtividadeIoT;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AtividadeIoTRepository extends JpaRepository<AtividadeIoT, Long> {

    Page<AtividadeIoT> findByPetId(Long petId, Pageable pageable);

    Optional<AtividadeIoT> findByIdAndPetId(Long id, Long petId);

    // Últimas atividades com alerta gerado (usada pela IA)
    List<AtividadeIoT> findTop10ByPetIdAndAlertaGeradoIsNotNullOrderByRegistradoEmDesc(Long petId);
}


