package br.com.fiap.petcareai.dto;

import br.com.fiap.petcareai.domain.Consulta;
import br.com.fiap.petcareai.domain.enums.TipoConsulta;

import java.time.LocalDate;

public record ConsultaResponseDTO(
        Long id,
        LocalDate data,
        String veterinario,
        TipoConsulta tipo,
        String diagnostico,
        String prescricao,
        String observacoes,
        Double valorConsulta,
        Long petId,
        String petNome
) {
    public static ConsultaResponseDTO fromEntity(Consulta consulta) {
        return new ConsultaResponseDTO(
                consulta.getId(),
                consulta.getData(),
                consulta.getVeterinario(),
                consulta.getTipo(),
                consulta.getDiagnostico(),
                consulta.getPrescricao(),
                consulta.getObservacoes(),
                consulta.getValorConsulta(),
                consulta.getPet().getId(),
                consulta.getPet().getNome()
        );
    }
}

