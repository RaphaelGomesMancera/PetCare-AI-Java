package br.com.fiap.petcareai.service;

import br.com.fiap.petcareai.domain.AtividadeIoT;
import br.com.fiap.petcareai.domain.Pet;
import br.com.fiap.petcareai.dto.AtividadeIoTRequestDTO;
import br.com.fiap.petcareai.dto.AtividadeIoTResponseDTO;
import br.com.fiap.petcareai.exception.ResourceNotFoundException;
import br.com.fiap.petcareai.repository.AtividadeIoTRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AtividadeIoTService {

    private final AtividadeIoTRepository atividadeIoTRepository;
    private final PetService petService;

    @Transactional(readOnly = true)
    public Page<AtividadeIoTResponseDTO> listarPorPet(Long petId, Pageable pageable) {
        return atividadeIoTRepository.findByPetId(petId, pageable).map(AtividadeIoTResponseDTO::fromEntity);
    }

    @Transactional(readOnly = true)
    public AtividadeIoTResponseDTO buscarPorId(Long id) {
        return AtividadeIoTResponseDTO.fromEntity(buscarEntidade(id));
    }

    @Transactional
    public AtividadeIoTResponseDTO registrar(Long petId, AtividadeIoTRequestDTO dto) {
        Pet pet = petService.buscarEntidade(petId);

        // Geração automática de alertas (lógica de IoT)
        String alerta = gerarAlerta(dto, pet.getNome());

        AtividadeIoT atividade = AtividadeIoT.builder()
                .registradoEm(dto.registradoEm())
                .passosContados(dto.passosContados())
                .temperaturaCorpo(dto.temperaturaCorpo())
                .frequenciaCardiacaBpm(dto.frequenciaCardiacaBpm())
                .alertaGerado(alerta)
                .pet(pet)
                .build();

        return AtividadeIoTResponseDTO.fromEntity(atividadeIoTRepository.save(atividade));
    }

    @Transactional
    public void deletar(Long id) {
        atividadeIoTRepository.delete(buscarEntidade(id));
    }

    private String gerarAlerta(AtividadeIoTRequestDTO dto, String nomePet) {
        List<String> alertas = new ArrayList<>();

        if (dto.temperaturaCorpo() != null) {
            if (dto.temperaturaCorpo() > 39.5) {
                alertas.add("🌡️ FEBRE DETECTADA: temperatura de " + dto.temperaturaCorpo() + "°C (acima de 39.5°C)");
            } else if (dto.temperaturaCorpo() < 37.5) {
                alertas.add("🌡️ HIPOTERMIA: temperatura de " + dto.temperaturaCorpo() + "°C (abaixo de 37.5°C)");
            }
        }

        if (dto.passosContados() != null && dto.passosContados() < 100) {
            alertas.add("🐾 BAIXA ATIVIDADE: apenas " + dto.passosContados().intValue() + " passos registrados");
        }

        if (dto.frequenciaCardiacaBpm() != null) {
            if (dto.frequenciaCardiacaBpm() > 180) {
                alertas.add("❤️ FREQUÊNCIA CARDÍACA ALTA: " + dto.frequenciaCardiacaBpm().intValue() + " bpm");
            } else if (dto.frequenciaCardiacaBpm() < 60) {
                alertas.add("❤️ FREQUÊNCIA CARDÍACA BAIXA: " + dto.frequenciaCardiacaBpm().intValue() + " bpm");
            }
        }

        return alertas.isEmpty() ? null : String.join(" | ", alertas);
    }

    public AtividadeIoT buscarEntidade(Long id) {
        return atividadeIoTRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Atividade IoT não encontrada com id: " + id));
    }
}

