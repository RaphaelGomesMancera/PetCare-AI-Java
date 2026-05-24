package br.com.fiap.petcareai.repository;

import br.com.fiap.petcareai.domain.Consulta;
import br.com.fiap.petcareai.domain.enums.TipoConsulta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

    Page<Consulta> findByPetId(Long petId, Pageable pageable);

    Page<Consulta> findByPetIdAndTipo(Long petId, TipoConsulta tipo, Pageable pageable);

    // Últimas consultas em ordem decrescente (usada pela IA)
    List<Consulta> findTop5ByPetIdOrderByDataDesc(Long petId);
}

