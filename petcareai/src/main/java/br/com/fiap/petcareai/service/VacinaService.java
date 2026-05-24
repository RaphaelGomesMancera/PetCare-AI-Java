package br.com.fiap.petcareai.service;

import br.com.fiap.petcareai.domain.Vacina;
import br.com.fiap.petcareai.domain.Pet;
import br.com.fiap.petcareai.dto.VacinaRequestDTO;
import br.com.fiap.petcareai.dto.VacinaResponseDTO;
import br.com.fiap.petcareai.exception.ResourceNotFoundException;
import br.com.fiap.petcareai.repository.VacinaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VacinaService {

    private final VacinaRepository vacinaRepository;
    private final PetService petService;

    @Transactional(readOnly = true)
    public Page<VacinaResponseDTO> listarPorPet(Long petId, Pageable pageable) {
        return vacinaRepository.findByPetId(petId, pageable).map(VacinaResponseDTO::fromEntity);
    }

    @Transactional(readOnly = true)
    public VacinaResponseDTO buscarPorId(Long petId, Long id) {
        return VacinaResponseDTO.fromEntity(buscarEntidadeDoPet(petId, id));
    }

    @Transactional(readOnly = true)
    public List<VacinaResponseDTO> listarProximas(Long petId, int diasAntecedencia) {
        LocalDate hoje = LocalDate.now();
        LocalDate limite = hoje.plusDays(diasAntecedencia);
        return vacinaRepository.findVacinasProximas(petId, hoje, limite)
                .stream().map(VacinaResponseDTO::fromEntity).toList();
    }

    @Transactional
    @CacheEvict(value = "recomendacoes", key = "#petId")
    public VacinaResponseDTO criar(Long petId, VacinaRequestDTO dto) {
        Pet pet = petService.buscarEntidade(petId);
        Vacina vacina = Vacina.builder()
                .nome(dto.nome())
                .dataAplicacao(dto.dataAplicacao())
                .proximaDose(dto.proximaDose())
                .observacoes(dto.observacoes())
                .fabricante(dto.fabricante())
                .lote(dto.lote())
                .pet(pet)
                .build();
        return VacinaResponseDTO.fromEntity(vacinaRepository.save(vacina));
    }

    @Transactional
    @CacheEvict(value = "recomendacoes", key = "#petId")
    public VacinaResponseDTO atualizar(Long petId, Long id, VacinaRequestDTO dto) {
        Vacina vacina = buscarEntidadeDoPet(petId, id);
        vacina.setNome(dto.nome());
        vacina.setDataAplicacao(dto.dataAplicacao());
        vacina.setProximaDose(dto.proximaDose());
        vacina.setObservacoes(dto.observacoes());
        vacina.setFabricante(dto.fabricante());
        vacina.setLote(dto.lote());
        return VacinaResponseDTO.fromEntity(vacinaRepository.save(vacina));
    }

    @Transactional
    @CacheEvict(value = "recomendacoes", key = "#petId")
    public void deletar(Long petId, Long id) {
        vacinaRepository.delete(buscarEntidadeDoPet(petId, id));
    }

    public Vacina buscarEntidade(Long id) {
        return vacinaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vacina não encontrada com id: " + id));
    }

    public Vacina buscarEntidadeDoPet(Long petId, Long id) {
        return vacinaRepository.findByIdAndPetId(id, petId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Vacina não encontrada com id: " + id + " para o pet id: " + petId));
    }
}


