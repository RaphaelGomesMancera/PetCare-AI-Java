package br.com.fiap.petcareai.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

public record AtividadeIoTRequestDTO(

        @NotNull(message = "Data/hora do registro é obrigatória")
        LocalDateTime registradoEm,

        @DecimalMin(value = "0.0", message = "Passos não pode ser negativo")
        Double passosContados,

        @DecimalMin(value = "30.0", message = "Temperatura mínima: 30°C")
        @DecimalMax(value = "45.0", message = "Temperatura máxima: 45°C")
        Double temperaturaCorpo,

        Double frequenciaCardiacaBpm
) {}

