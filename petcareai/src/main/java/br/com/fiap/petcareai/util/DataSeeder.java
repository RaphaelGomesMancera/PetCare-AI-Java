package br.com.fiap.petcareai.util;

import br.com.fiap.petcareai.domain.*;
import br.com.fiap.petcareai.domain.enums.EspeciePet;
import br.com.fiap.petcareai.domain.enums.TipoConsulta;
import br.com.fiap.petcareai.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
@Profile("!test")
public class DataSeeder implements CommandLineRunner {

    private final TutorRepository tutorRepository;
    private final PetRepository petRepository;
    private final VacinaRepository vacinaRepository;
    private final ConsultaRepository consultaRepository;
    private final MedicamentoRepository medicamentoRepository;
    private final AtividadeIoTRepository atividadeIoTRepository;

    @Override
    public void run(String... args) {
        if (tutorRepository.count() > 0) return;

        log.info("🌱 Iniciando carga de dados de exemplo...");

        // === TUTORES ===
        Tutor tutor1 = tutorRepository.save(Tutor.builder()
                .nome("Ana Silva")
                .email("ana.silva@email.com")
                .telefone("11987654321")
                .endereco("Rua das Flores, 123 - São Paulo/SP")
                .build());

        Tutor tutor2 = tutorRepository.save(Tutor.builder()
                .nome("Carlos Souza")
                .email("carlos.souza@email.com")
                .telefone("21976543210")
                .endereco("Av. Atlântica, 456 - Rio de Janeiro/RJ")
                .build());

        // === PETS ===
        Pet rex = petRepository.save(Pet.builder()
                .nome("Rex")
                .especie(EspeciePet.CACHORRO)
                .raca("Labrador")
                .dataNascimento(LocalDate.of(2015, 3, 10))  // ~11 anos — SÊNIOR
                .pesoKg(32.5)
                .observacoes("Histórico de displasia no quadril")
                .tutor(tutor1)
                .build());

        Pet mimi = petRepository.save(Pet.builder()
                .nome("Mimi")
                .especie(EspeciePet.GATO)
                .raca("Persa")
                .dataNascimento(LocalDate.of(2023, 6, 15))  // ~2 anos — jovem
                .pesoKg(4.2)
                .tutor(tutor1)
                .build());

        Pet bolt = petRepository.save(Pet.builder()
                .nome("Bolt")
                .especie(EspeciePet.CACHORRO)
                .raca("Border Collie")
                .dataNascimento(LocalDate.of(2022, 1, 20))  // ~3 anos — adulto
                .pesoKg(18.0)
                .tutor(tutor2)
                .build());

        // === VACINAS ===
        vacinaRepository.save(Vacina.builder()
                .nome("V10 - Polivalente")
                .dataAplicacao(LocalDate.of(2024, 3, 10))
                .proximaDose(LocalDate.of(2025, 3, 10))    // VENCIDA — gera alerta
                .fabricante("Zoetis")
                .lote("ZT2024A")
                .pet(rex)
                .build());

        vacinaRepository.save(Vacina.builder()
                .nome("Antirrábica")
                .dataAplicacao(LocalDate.of(2024, 8, 5))
                .proximaDose(LocalDate.now().plusDays(20))  // PRÓXIMA — aparece em /proximas
                .fabricante("MSD")
                .pet(rex)
                .build());

        vacinaRepository.save(Vacina.builder()
                .nome("Quádrupla Felina")
                .dataAplicacao(LocalDate.of(2025, 1, 10))
                .proximaDose(LocalDate.now().plusDays(60))
                .fabricante("Boehringer")
                .pet(mimi)
                .build());

        vacinaRepository.save(Vacina.builder()
                .nome("V8 - Polivalente")
                .dataAplicacao(LocalDate.of(2025, 1, 20))
                .proximaDose(LocalDate.now().plusMonths(11))
                .pet(bolt)
                .build());

        // === CONSULTAS ===
        consultaRepository.save(Consulta.builder()
                .data(LocalDate.of(2024, 6, 15))
                .veterinario("Dra. Mariana Costa")
                .tipo(TipoConsulta.ROTINA)
                .diagnostico("Animal saudável. Leve sobrepeso observado.")
                .prescricao("Dieta controlada e caminhadas diárias de 30 min")
                .valorConsulta(250.0)
                .pet(rex)
                .build());

        consultaRepository.save(Consulta.builder()
                .data(LocalDate.of(2025, 1, 10))
                .veterinario("Dr. Felipe Alves")
                .tipo(TipoConsulta.EXAME)
                .diagnostico("Exames de sangue normais. Função renal dentro do esperado para a idade.")
                .valorConsulta(180.0)
                .pet(rex)
                .build());

        consultaRepository.save(Consulta.builder()
                .data(LocalDate.of(2025, 3, 5))
                .veterinario("Dra. Mariana Costa")
                .tipo(TipoConsulta.ROTINA)
                .diagnostico("Filhote saudável. Desenvolvimento normal.")
                .valorConsulta(200.0)
                .pet(mimi)
                .build());

        // === MEDICAMENTOS ===
        medicamentoRepository.save(Medicamento.builder()
                .nome("Carprofeno")
                .dosagem("50mg")
                .frequencia("1x ao dia")
                .dataInicio(LocalDate.of(2025, 1, 10))
                .dataFim(LocalDate.now().plusDays(5))   // vencendo em breve — gera alerta
                .ativo(true)
                .observacoes("Anti-inflamatório para displasia")
                .pet(rex)
                .build());

        medicamentoRepository.save(Medicamento.builder()
                .nome("Omeprazol Veterinário")
                .dosagem("10mg")
                .frequencia("1x ao dia em jejum")
                .dataInicio(LocalDate.of(2025, 4, 1))
                .dataFim(LocalDate.now().plusMonths(2))
                .ativo(true)
                .pet(bolt)
                .build());

        // === ATIVIDADES IoT ===
        atividadeIoTRepository.save(AtividadeIoT.builder()
                .registradoEm(LocalDateTime.now().minusHours(2))
                .passosContados(50.0)           // BAIXA ATIVIDADE — gera alerta
                .temperaturaCorpo(39.8)         // FEBRE — gera alerta
                .frequenciaCardiacaBpm(95.0)
                .alertaGerado("🌡️ FEBRE DETECTADA: temperatura de 39.8°C | 🐾 BAIXA ATIVIDADE: apenas 50 passos")
                .pet(rex)
                .build());

        atividadeIoTRepository.save(AtividadeIoT.builder()
                .registradoEm(LocalDateTime.now().minusHours(1))
                .passosContados(1200.0)
                .temperaturaCorpo(38.5)
                .frequenciaCardiacaBpm(110.0)
                .alertaGerado(null)
                .pet(bolt)
                .build());

        log.info("✅ Dados de exemplo carregados com sucesso!");
        log.info("📖 Swagger UI: http://localhost:8080/swagger-ui.html");
        log.info("🗄️  H2 Console: http://localhost:8080/h2-console");
    }
}

