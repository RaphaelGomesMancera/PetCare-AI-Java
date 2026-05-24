package br.com.fiap.petcareai.service;

import br.com.fiap.petcareai.domain.Tutor;
import br.com.fiap.petcareai.dto.TutorRequestDTO;
import br.com.fiap.petcareai.dto.TutorResponseDTO;
import br.com.fiap.petcareai.exception.BusinessException;
import br.com.fiap.petcareai.exception.ResourceNotFoundException;
import br.com.fiap.petcareai.repository.TutorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TutorService {

    private final TutorRepository tutorRepository;

    @Transactional(readOnly = true)
    public Page<TutorResponseDTO> listarTodos(String nome, Pageable pageable) {
        if (nome != null && !nome.isBlank()) {
            return tutorRepository.findByNomeContainingIgnoreCase(nome, pageable)
                    .map(TutorResponseDTO::fromEntity);
        }
        return tutorRepository.findAll(pageable).map(TutorResponseDTO::fromEntity);
    }

    @Transactional(readOnly = true)
    public TutorResponseDTO buscarPorId(Long id) {
        return TutorResponseDTO.fromEntity(buscarEntidade(id));
    }

    @Transactional
    public TutorResponseDTO criar(TutorRequestDTO dto) {
        if (tutorRepository.existsByEmail(dto.email())) {
            throw new BusinessException("Já existe um tutor cadastrado com o e-mail: " + dto.email());
        }
        Tutor tutor = Tutor.builder()
                .nome(dto.nome())
                .email(dto.email())
                .telefone(dto.telefone())
                .endereco(dto.endereco())
                .build();
        return TutorResponseDTO.fromEntity(tutorRepository.save(tutor));
    }

    @Transactional
    public TutorResponseDTO atualizar(Long id, TutorRequestDTO dto) {
        Tutor tutor = buscarEntidade(id);
        // Se o email mudou, verifica duplicidade
        if (!tutor.getEmail().equals(dto.email()) && tutorRepository.existsByEmail(dto.email())) {
            throw new BusinessException("Já existe um tutor cadastrado com o e-mail: " + dto.email());
        }
        tutor.setNome(dto.nome());
        tutor.setEmail(dto.email());
        tutor.setTelefone(dto.telefone());
        tutor.setEndereco(dto.endereco());
        return TutorResponseDTO.fromEntity(tutorRepository.save(tutor));
    }

    @Transactional
    public void deletar(Long id) {
        Tutor tutor = buscarEntidade(id);
        tutorRepository.delete(tutor);
    }

    public Tutor buscarEntidade(Long id) {
        return tutorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tutor não encontrado com id: " + id));
    }
}

