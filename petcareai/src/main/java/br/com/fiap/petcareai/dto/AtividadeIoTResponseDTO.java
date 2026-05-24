package br.com.fiap.petcareai.dto;

import br.com.fiap.petcareai.domain.AtividadeIoT;

import java.time.LocalDateTime;

public record AtividadeIoTResponseDTO(
        Long id,
        LocalDateTime registradoEm,
        Double passosContados,
        Double temperaturaCorpo,
        Double frequenciaCardiacaBpm,
        String alertaGerado,
        Long petId,
        String petNome
) {
    public static AtividadeIoTResponseDTO fromEntity(AtividadeIoT a) {
        return new AtividadeIoTResponseDTO(
                a.getId(),
                a.getRegistradoEm(),
                a.getPassosContados(),
                a.getTemperaturaCorpo(),
                a.getFrequenciaCardiacaBpm(),
                a.getAlertaGerado(),
                a.getPet().getId(),
                a.getPet().getNome()
        );
    }
}

