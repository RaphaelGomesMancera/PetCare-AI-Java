package br.com.fiap.petcareai.dto;

import java.util.List;

public record RecomendacaoDTO(
        Long petId,
        String petNome,
        String especie,
        int idadeAnos,
        int idadeMeses,
        String faixaEtaria,
        String frequenciaConsultaRecomendada,
        List<String> recomendacoes,
        List<String> alertas,
        List<String> vacinasVencidas,
        List<String> medicamentosVencendo
) {}

