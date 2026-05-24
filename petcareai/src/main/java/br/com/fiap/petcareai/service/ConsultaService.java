package br.com.fiap.petcareai.service;

import br.com.fiap.petcareai.domain.Consulta;
import br.com.fiap.petcareai.domain.Pet;
import br.com.fiap.petcareai.dto.ConsultaRequestDTO;
import br.com.fiap.petcareai.dto.ConsultaResponseDTO;
import br.com.fiap.petcareai.domain.enums.TipoConsulta;
import br.com.fiap.petcareai.exception.ResourceNotFoundException;
import br.com.fiap.petcareai.repository.ConsultaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ConsultaService {

    private final ConsultaRepository consultaRepository;
    private final PetService petService;

    @Transactional(readOnly = true)
    public Page<ConsultaResponseDTO> listarPorPet(Long petId, TipoConsulta tipo, Pageable pageable) {
        if (tipo != null) {
            return consultaRepository.findByPetIdAndTipo(petId, tipo, pageable)
                    .map(ConsultaResponseDTO::fromEntity);
        }
        return consultaRepository.findByPetId(petId, pageable).map(ConsultaResponseDTO::fromEntity);
    }

    @Transactional(readOnly = true)
    public ConsultaResponseDTO buscarPorId(Long id) {
        return ConsultaResponseDTO.fromEntity(buscarEntidade(id));
    }

    @Transactional
    public ConsultaResponseDTO criar(Long petId, ConsultaRequestDTO dto) {
        Pet pet = petService.buscarEntidade(petId);
        Consulta consulta = Consulta.builder()
                .data(dto.data())
                .veterinario(dto.veterinario())
                .tipo(dto.tipo())
                .diagnostico(dto.diagnostico())
                .prescricao(dto.prescricao())
                .observacoes(dto.observacoes())
                .valorConsulta(dto.valorConsulta() != null ? dto.valorConsulta() : 0.0)
                .pet(pet)
                .build();
        return ConsultaResponseDTO.fromEntity(consultaRepository.save(consulta));
    }

    @Transactional
    public ConsultaResponseDTO atualizar(Long id, ConsultaRequestDTO dto) {
        Consulta consulta = buscarEntidade(id);
        consulta.setData(dto.data());
        consulta.setVeterinario(dto.veterinario());
        consulta.setTipo(dto.tipo());
        consulta.setDiagnostico(dto.diagnostico());
        consulta.setPrescricao(dto.prescricao());
        consulta.setObservacoes(dto.observacoes());
        consulta.setValorConsulta(dto.valorConsulta() != null ? dto.valorConsulta() : consulta.getValorConsulta());
        return ConsultaResponseDTO.fromEntity(consultaRepository.save(consulta));
    }

    @Transactional
    public void deletar(Long id) {
        consultaRepository.delete(buscarEntidade(id));
    }

    public Consulta buscarEntidade(Long id) {
        return consultaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Consulta não encontrada com id: " + id));
    }
}

