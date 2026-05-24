package br.com.fiap.petcareai.dto;

import br.com.fiap.petcareai.domain.Pet;
import br.com.fiap.petcareai.domain.enums.EspeciePet;

import java.time.LocalDate;
import java.time.Period;

public record PetResponseDTO(
        Long id,
        String nome,
        EspeciePet especie,
        String raca,
        LocalDate dataNascimento,
        int idadeAnos,
        int idadeMeses,
        Double pesoKg,
        String observacoes,
        Long tutorId,
        String tutorNome
) {
    public static PetResponseDTO fromEntity(Pet pet) {
        Period idade = Period.between(pet.getDataNascimento(), LocalDate.now());
        return new PetResponseDTO(
                pet.getId(),
                pet.getNome(),
                pet.getEspecie(),
                pet.getRaca(),
                pet.getDataNascimento(),
                idade.getYears(),
                idade.getMonths(),
                pet.getPesoKg(),
                pet.getObservacoes(),
                pet.getTutor().getId(),
                pet.getTutor().getNome()
        );
    }
}

