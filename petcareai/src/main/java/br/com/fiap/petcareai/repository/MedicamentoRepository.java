package br.com.fiap.petcareai.repository;

import br.com.fiap.petcareai.domain.Medicamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MedicamentoRepository extends JpaRepository<Medicamento, Long> {

    Page<Medicamento> findByPetId(Long petId, Pageable pageable);

    List<Medicamento> findByPetIdAndAtivoTrue(Long petId);

    // Medicamentos ativos que vencem nos próximos dias (usada pela IA)
    List<Medicamento> findByPetIdAndAtivoTrueAndDataFimBefore(Long petId, LocalDate data);
}

