package br.com.fiap.petcareai.service;

import br.com.fiap.petcareai.domain.Pet;
import br.com.fiap.petcareai.domain.Vacina;
import br.com.fiap.petcareai.domain.Medicamento;
import br.com.fiap.petcareai.dto.RecomendacaoDTO;
import br.com.fiap.petcareai.repository.VacinaRepository;
import br.com.fiap.petcareai.repository.MedicamentoRepository;
import br.com.fiap.petcareai.repository.ConsultaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecomendacaoService {

    private final PetService petService;
    private final VacinaRepository vacinaRepository;
    private final MedicamentoRepository medicamentoRepository;
    private final ConsultaRepository consultaRepository;

    @Cacheable(value = "recomendacoes", key = "#petId")
    @Transactional(readOnly = true)
    public RecomendacaoDTO gerarRecomendacoes(Long petId) {
        Pet pet = petService.buscarEntidade(petId);

        Period idade = Period.between(pet.getDataNascimento(), LocalDate.now());
        int anos = idade.getYears();
        int meses = idade.getMonths();

        List<String> recomendacoes = new ArrayList<>();
        List<String> alertas = new ArrayList<>();

        // === REGRA 1: Faixa etária e frequência de consultas ===
        String faixaEtaria;
        String frequenciaConsulta;

        if (anos == 0 && meses < 6) {
            faixaEtaria = "Filhote Neonato (0-6 meses)";
            frequenciaConsulta = "Consulta mensal obrigatória";
            recomendacoes.add("📋 Filhote neonato: acompanhamento mensal essencial para vacinação e desenvolvimento");
            recomendacoes.add("💉 Iniciar protocolo de vacinação com veterinário");
            recomendacoes.add("🍼 Atenção especial à alimentação e peso");
        } else if (anos == 0) {
            faixaEtaria = "Filhote (6-12 meses)";
            frequenciaConsulta = "Consulta a cada 2 meses";
            recomendacoes.add("📋 Filhote em crescimento: consulta bimestral recomendada");
            recomendacoes.add("✂️ Considerar castração com veterinário");
            recomendacoes.add("🦷 Iniciar higiene dental");
        } else if (anos <= 2) {
            faixaEtaria = "Jovem (1-2 anos)";
            frequenciaConsulta = "Consulta semestral";
            recomendacoes.add("📋 Pet jovem: consulta semestral recomendada");
            recomendacoes.add("🏃 Incentivar atividade física regular");
        } else if (anos <= 7) {
            faixaEtaria = "Adulto (3-7 anos)";
            frequenciaConsulta = "Consulta semestral";
            recomendacoes.add("📋 Pet adulto saudável: consulta semestral recomendada");
            recomendacoes.add("🦷 Check-up dental anual");
            recomendacoes.add("🔬 Exames de rotina anuais recomendados");
        } else if (anos <= 10) {
            faixaEtaria = "Meia-idade (8-10 anos)";
            frequenciaConsulta = "Consulta trimestral";
            recomendacoes.add("⚠️ Pet em meia-idade: atenção redobrada — consulta trimestral recomendada");
            recomendacoes.add("🔬 Exames de sangue e urina semestrais");
            recomendacoes.add("🦴 Avaliação de articulações e mobilidade");
            alertas.add("⚠️ A partir dos 8 anos, doenças silenciosas como diabetes e problemas renais são mais comuns");
        } else {
            faixaEtaria = "Sênior (11+ anos)";
            frequenciaConsulta = "Consulta bimestral";
            recomendacoes.add("🚨 Pet sênior: consulta bimestral obrigatória");
            recomendacoes.add("🔬 Exames completos a cada 3 meses");
            recomendacoes.add("🍖 Dieta específica para pet sênior — consulte veterinário");
            recomendacoes.add("💊 Monitorar uso de medicamentos com atenção redobrada");
            alertas.add("🚨 SÊNIOR: risco elevado de insuficiência renal, cardíaca e câncer");
        }

        // === REGRA 2: Peso (se informado) ===
        if (pet.getPesoKg() != null) {
            switch (pet.getEspecie()) {
                case GATO -> {
                    if (pet.getPesoKg() > 5.0) {
                        alertas.add("⚖️ Gato com sobrepeso (" + pet.getPesoKg() + "kg). Peso ideal: até 5kg");
                        recomendacoes.add("🥗 Consultar veterinário para dieta de emagrecimento");
                    }
                }
                case CACHORRO -> {
                    if (pet.getPesoKg() > 40.0) {
                        recomendacoes.add("🏋️ Cão de grande porte: atenção às articulações e coluna");
                    }
                }
                default -> { }
            }
        }

        // === REGRA 3: Vacinas vencidas ===
        List<String> vacinasVencidas = vacinaRepository
                .findByPetIdAndProximaDoseBefore(petId, LocalDate.now())
                .stream()
                .map(v -> v.getNome() + " (venceu em " + v.getProximaDose() + ")")
                .toList();

        if (!vacinasVencidas.isEmpty()) {
            alertas.add("💉 " + vacinasVencidas.size() + " vacina(s) vencida(s)! Agende urgentemente");
            recomendacoes.add("💉 Regularizar vacinas em atraso o quanto antes");
        }

        // === REGRA 4: Medicamentos ativos vencendo em até 7 dias ===
        List<String> medicamentosVencendo = medicamentoRepository
                .findByPetIdAndAtivoTrueAndDataFimBefore(petId, LocalDate.now().plusDays(7))
                .stream()
                .map(m -> m.getNome() + " — " + m.getFrequencia() + " (termina em " + m.getDataFim() + ")")
                .toList();

        if (!medicamentosVencendo.isEmpty()) {
            alertas.add("💊 " + medicamentosVencendo.size() + " medicamento(s) terminando em até 7 dias");
        }

        // === REGRA 5: Tempo desde a última consulta ===
        var ultimasConsultas = consultaRepository.findTop5ByPetIdOrderByDataDesc(petId);
        if (ultimasConsultas.isEmpty()) {
            alertas.add("📅 Nenhuma consulta registrada! Agende uma consulta de rotina imediatamente");
        } else {
            LocalDate ultimaConsulta = ultimasConsultas.get(0).getData();
            long diasSemConsulta = java.time.temporal.ChronoUnit.DAYS.between(ultimaConsulta, LocalDate.now());
            if (anos >= 10 && diasSemConsulta > 60) {
                alertas.add("📅 Última consulta há " + diasSemConsulta + " dias. Pet sênior precisa de consulta bimestral!");
            } else if (anos >= 8 && diasSemConsulta > 90) {
                alertas.add("📅 Última consulta há " + diasSemConsulta + " dias. Recomendado consulta trimestral");
            } else if (diasSemConsulta > 180) {
                alertas.add("📅 Última consulta há " + diasSemConsulta + " dias. Agende uma consulta de rotina");
            }
        }

        return new RecomendacaoDTO(
                pet.getId(),
                pet.getNome(),
                pet.getEspecie().name(),
                anos,
                meses,
                faixaEtaria,
                frequenciaConsulta,
                recomendacoes,
                alertas,
                vacinasVencidas,
                medicamentosVencendo
        );
    }
}

