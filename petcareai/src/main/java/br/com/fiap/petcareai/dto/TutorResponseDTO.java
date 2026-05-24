package br.com.fiap.petcareai.dto;

import br.com.fiap.petcareai.domain.Tutor;

import java.time.LocalDateTime;

public record TutorResponseDTO(
        Long id,
        String nome,
        String email,
        String telefone,
        String endereco,
        int quantidadePets,
        LocalDateTime criadoEm
) {
    public static TutorResponseDTO fromEntity(Tutor tutor) {
        return new TutorResponseDTO(
                tutor.getId(),
                tutor.getNome(),
                tutor.getEmail(),
                tutor.getTelefone(),
                tutor.getEndereco(),
                tutor.getPets() != null ? tutor.getPets().size() : 0,
                tutor.getCriadoEm()
        );
    }
}

