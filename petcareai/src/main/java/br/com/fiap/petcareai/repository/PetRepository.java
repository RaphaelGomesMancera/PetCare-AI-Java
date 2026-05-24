package br.com.fiap.petcareai.repository;

import br.com.fiap.petcareai.domain.Pet;
import br.com.fiap.petcareai.domain.enums.EspeciePet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {

    Page<Pet> findByTutorId(Long tutorId, Pageable pageable);

    Page<Pet> findByEspecie(EspeciePet especie, Pageable pageable);

    Page<Pet> findByNomeContainingIgnoreCase(String nome, Pageable pageable);

    Page<Pet> findByEspecieAndNomeContainingIgnoreCase(EspeciePet especie, String nome, Pageable pageable);

    @Query("SELECT p FROM Pet p WHERE p.tutor.id = :tutorId AND (:especie IS NULL OR p.especie = :especie)")
    Page<Pet> findByTutorIdAndEspecie(@Param("tutorId") Long tutorId,
                                      @Param("especie") EspeciePet especie,
                                      Pageable pageable);
}

