package br.com.fiap.petcareai.dto;

import br.com.fiap.petcareai.domain.Medicamento;

import java.time.LocalDate;

public record MedicamentoResponseDTO(
        Long id,
        String nome,
        String dosagem,
        String frequencia,
        LocalDate dataInicio,
        LocalDate dataFim,
        boolean ativo,
        String observacoes,
        Long petId,
        String petNome
) {
    public static MedicamentoResponseDTO fromEntity(Medicamento m) {
        return new MedicamentoResponseDTO(
                m.getId(),
                m.getNome(),
                m.getDosagem(),
                m.getFrequencia(),
                m.getDataInicio(),
                m.getDataFim(),
                m.isAtivo(),
                m.getObservacoes(),
                m.getPet().getId(),
                m.getPet().getNome()
        );
    }
}

