package br.com.fiap.petcareai.dto;

import br.com.fiap.petcareai.domain.enums.TipoConsulta;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record ConsultaRequestDTO(

        @NotNull(message = "Data da consulta é obrigatória")
        LocalDate data,

        @NotBlank(message = "Nome do veterinário é obrigatório")
        @Size(max = 100)
        String veterinario,

        @NotNull(message = "Tipo da consulta é obrigatório")
        TipoConsulta tipo,

        @Size(max = 1000)
        String diagnostico,

        @Size(max = 1000)
        String prescricao,

        @Size(max = 500)
        String observacoes,

        Double valorConsulta
) {}

