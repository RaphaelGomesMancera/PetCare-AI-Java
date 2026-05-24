package br.com.fiap.petcareai.service;

import br.com.fiap.petcareai.domain.Medicamento;
import br.com.fiap.petcareai.domain.Pet;
import br.com.fiap.petcareai.dto.MedicamentoRequestDTO;
import br.com.fiap.petcareai.dto.MedicamentoResponseDTO;
import br.com.fiap.petcareai.exception.ResourceNotFoundException;
import br.com.fiap.petcareai.repository.MedicamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicamentoService {

    private final MedicamentoRepository medicamentoRepository;
    private final PetService petService;

    @Transactional(readOnly = true)
    public Page<MedicamentoResponseDTO> listarPorPet(Long petId, Pageable pageable) {
        return medicamentoRepository.findByPetId(petId, pageable).map(MedicamentoResponseDTO::fromEntity);
    }

    @Transactional(readOnly = true)
    public List<MedicamentoResponseDTO> listarAtivosPorPet(Long petId) {
        return medicamentoRepository.findByPetIdAndAtivoTrue(petId)
                .stream().map(MedicamentoResponseDTO::fromEntity).toList();
    }

    @Transactional(readOnly = true)
    public MedicamentoResponseDTO buscarPorId(Long petId, Long id) {
        return MedicamentoResponseDTO.fromEntity(buscarEntidadeDoPet(petId, id));
    }

    @Transactional
    @CacheEvict(value = "recomendacoes", key = "#petId")
    public MedicamentoResponseDTO criar(Long petId, MedicamentoRequestDTO dto) {
        Pet pet = petService.buscarEntidade(petId);
        Medicamento m = Medicamento.builder()
                .nome(dto.nome())
                .dosagem(dto.dosagem())
                .frequencia(dto.frequencia())
                .dataInicio(dto.dataInicio())
                .dataFim(dto.dataFim())
                .ativo(dto.ativo() != null ? dto.ativo() : true)
                .observacoes(dto.observacoes())
                .pet(pet)
                .build();
        return MedicamentoResponseDTO.fromEntity(medicamentoRepository.save(m));
    }

    @Transactional
    @CacheEvict(value = "recomendacoes", key = "#petId")
    public MedicamentoResponseDTO atualizar(Long petId, Long id, MedicamentoRequestDTO dto) {
        Medicamento m = buscarEntidadeDoPet(petId, id);
        m.setNome(dto.nome());
        m.setDosagem(dto.dosagem());
        m.setFrequencia(dto.frequencia());
        m.setDataInicio(dto.dataInicio());
        m.setDataFim(dto.dataFim());
        if (dto.ativo() != null) m.setAtivo(dto.ativo());
        m.setObservacoes(dto.observacoes());
        return MedicamentoResponseDTO.fromEntity(medicamentoRepository.save(m));
    }

    @Transactional
    @CacheEvict(value = "recomendacoes", key = "#petId")
    public void deletar(Long petId, Long id) {
        medicamentoRepository.delete(buscarEntidadeDoPet(petId, id));
    }

    public Medicamento buscarEntidade(Long id) {
        return medicamentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Medicamento não encontrado com id: " + id));
    }

    public Medicamento buscarEntidadeDoPet(Long petId, Long id) {
        return medicamentoRepository.findByIdAndPetId(id, petId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Medicamento não encontrado com id: " + id + " para o pet id: " + petId));
    }
}


