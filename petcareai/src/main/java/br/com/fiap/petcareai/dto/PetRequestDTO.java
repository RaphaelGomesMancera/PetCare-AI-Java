package br.com.fiap.petcareai.dto;

import br.com.fiap.petcareai.domain.enums.EspeciePet;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record PetRequestDTO(

        @NotBlank(message = "Nome do pet é obrigatório")
        @Size(min = 1, max = 60, message = "Nome do pet deve ter entre 1 e 60 caracteres")
        String nome,

        @NotNull(message = "Espécie é obrigatória")
        EspeciePet especie,

        @Size(max = 60, message = "Raça deve ter no máximo 60 caracteres")
        String raca,

        @NotNull(message = "Data de nascimento é obrigatória")
        @Past(message = "Data de nascimento deve ser no passado")
        LocalDate dataNascimento,

        @DecimalMin(value = "0.1", message = "Peso deve ser no mínimo 0.1 kg")
        @DecimalMax(value = "200.0", message = "Peso deve ser no máximo 200 kg")
        Double pesoKg,

        @Size(max = 500, message = "Observações deve ter no máximo 500 caracteres")
        String observacoes,

        @NotNull(message = "ID do tutor é obrigatório")
        Long tutorId
) {}

