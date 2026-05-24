package br.com.fiap.petcareai.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record VacinaRequestDTO(

        @NotBlank(message = "Nome da vacina é obrigatório")
        @Size(max = 100)
        String nome,

        @NotNull(message = "Data de aplicação é obrigatória")
        @PastOrPresent(message = "Data de aplicação não pode ser futura")
        LocalDate dataAplicacao,

        LocalDate proximaDose,

        @Size(max = 500)
        String observacoes,

        @Size(max = 100)
        String fabricante,

        @Size(max = 50)
        String lote
) {}

