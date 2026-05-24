package br.com.fiap.petcareai.repository;

import br.com.fiap.petcareai.domain.Tutor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TutorRepository extends JpaRepository<Tutor, Long> {

    Optional<Tutor> findByEmail(String email);

    Page<Tutor> findByNomeContainingIgnoreCase(String nome, Pageable pageable);

    boolean existsByEmail(String email);
}

