package br.com.fiap.petcareai.service;

import br.com.fiap.petcareai.domain.Pet;
import br.com.fiap.petcareai.domain.Tutor;
import br.com.fiap.petcareai.domain.enums.EspeciePet;
import br.com.fiap.petcareai.dto.PetRequestDTO;
import br.com.fiap.petcareai.dto.PetResponseDTO;
import br.com.fiap.petcareai.exception.ResourceNotFoundException;
import br.com.fiap.petcareai.repository.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PetService {

    private final PetRepository petRepository;
    private final TutorService tutorService;

    @Transactional(readOnly = true)
    public Page<PetResponseDTO> listarTodos(String nome, EspeciePet especie, Pageable pageable) {
        if (nome != null && !nome.isBlank() && especie != null) {
            return petRepository.findByEspecieAndNomeContainingIgnoreCase(especie, nome, pageable)
                    .map(PetResponseDTO::fromEntity);
        } else if (especie != null) {
            return petRepository.findByEspecie(especie, pageable).map(PetResponseDTO::fromEntity);
        } else if (nome != null && !nome.isBlank()) {
            return petRepository.findByNomeContainingIgnoreCase(nome, pageable).map(PetResponseDTO::fromEntity);
        }
        return petRepository.findAll(pageable).map(PetResponseDTO::fromEntity);
    }

    @Transactional(readOnly = true)
    public Page<PetResponseDTO> listarPorTutor(Long tutorId, Pageable pageable) {
        return petRepository.findByTutorId(tutorId, pageable).map(PetResponseDTO::fromEntity);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "pets", key = "#id")
    public PetResponseDTO buscarPorId(Long id) {
        return PetResponseDTO.fromEntity(buscarEntidade(id));
    }

    @Transactional
    public PetResponseDTO criar(PetRequestDTO dto) {
        Tutor tutor = tutorService.buscarEntidade(dto.tutorId());
        Pet pet = Pet.builder()
                .nome(dto.nome())
                .especie(dto.especie())
                .raca(dto.raca())
                .dataNascimento(dto.dataNascimento())
                .pesoKg(dto.pesoKg())
                .observacoes(dto.observacoes())
                .tutor(tutor)
                .build();
        return PetResponseDTO.fromEntity(petRepository.save(pet));
    }

    @Transactional
    @CacheEvict(value = {"pets", "recomendacoes"}, key = "#id")
    public PetResponseDTO atualizar(Long id, PetRequestDTO dto) {
        Pet pet = buscarEntidade(id);
        Tutor tutor = tutorService.buscarEntidade(dto.tutorId());
        pet.setNome(dto.nome());
        pet.setEspecie(dto.especie());
        pet.setRaca(dto.raca());
        pet.setDataNascimento(dto.dataNascimento());
        pet.setPesoKg(dto.pesoKg());
        pet.setObservacoes(dto.observacoes());
        pet.setTutor(tutor);
        return PetResponseDTO.fromEntity(petRepository.save(pet));
    }

    @Transactional
    @CacheEvict(value = {"pets", "recomendacoes"}, key = "#id")
    public void deletar(Long id) {
        petRepository.delete(buscarEntidade(id));
    }

    public Pet buscarEntidade(Long id) {
        return petRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pet não encontrado com id: " + id));
    }
}

