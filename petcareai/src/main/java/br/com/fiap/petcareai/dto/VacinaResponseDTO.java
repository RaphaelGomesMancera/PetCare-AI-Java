package br.com.fiap.petcareai.dto;

import br.com.fiap.petcareai.domain.Vacina;

import java.time.LocalDate;

public record VacinaResponseDTO(
        Long id,
        String nome,
        LocalDate dataAplicacao,
        LocalDate proximaDose,
        boolean vencida,
        String observacoes,
        String fabricante,
        String lote,
        Long petId,
        String petNome
) {
    public static VacinaResponseDTO fromEntity(Vacina vacina) {
        boolean vencida = vacina.getProximaDose() != null
                && vacina.getProximaDose().isBefore(LocalDate.now());
        return new VacinaResponseDTO(
                vacina.getId(),
                vacina.getNome(),
                vacina.getDataAplicacao(),
                vacina.getProximaDose(),
                vencida,
                vacina.getObservacoes(),
                vacina.getFabricante(),
                vacina.getLote(),
                vacina.getPet().getId(),
                vacina.getPet().getNome()
        );
    }
}

