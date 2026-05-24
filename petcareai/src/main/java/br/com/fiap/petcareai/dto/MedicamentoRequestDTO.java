package br.com.fiap.petcareai.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record MedicamentoRequestDTO(

        @NotBlank(message = "Nome do medicamento é obrigatório")
        @Size(max = 100)
        String nome,

        @NotBlank(message = "Dosagem é obrigatória")
        @Size(max = 100)
        String dosagem,

        @NotBlank(message = "Frequência é obrigatória")
        @Size(max = 100)
        String frequencia,

        @NotNull(message = "Data de início é obrigatória")
        LocalDate dataInicio,

        LocalDate dataFim,

        Boolean ativo,

        @Size(max = 500)
        String observacoes
) {}

