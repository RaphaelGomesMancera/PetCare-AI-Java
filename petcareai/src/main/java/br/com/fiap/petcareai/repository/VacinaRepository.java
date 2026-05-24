package br.com.fiap.petcareai.repository;

import br.com.fiap.petcareai.domain.Vacina;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface VacinaRepository extends JpaRepository<Vacina, Long> {

    Page<Vacina> findByPetId(Long petId, Pageable pageable);

    // Vacinas com próxima dose vencida ou chegando (usada pela IA)
    List<Vacina> findByPetIdAndProximaDoseBefore(Long petId, LocalDate data);

    @Query("SELECT v FROM Vacina v WHERE v.pet.id = :petId AND v.proximaDose BETWEEN :hoje AND :limite")
    List<Vacina> findVacinasProximas(@Param("petId") Long petId,
                                     @Param("hoje") LocalDate hoje,
                                     @Param("limite") LocalDate limite);
}

