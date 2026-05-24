package br.com.fiap.petcareai.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "atividades_iot")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class AtividadeIoT {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Data/hora do registro é obrigatória")
    @Column(nullable = false)
    private LocalDateTime registradoEm;

    @DecimalMin(value = "0.0", message = "Passos não pode ser negativo")
    private Double passosContados;

    @DecimalMin(value = "30.0", message = "Temperatura mínima: 30°C")
    @DecimalMax(value = "45.0", message = "Temperatura máxima: 45°C")
    private Double temperaturaCorpo;

    private Double frequenciaCardiacaBpm;

    // Gerado automaticamente pelo sistema ao salvar
    @Size(max = 500)
    private String alertaGerado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id", nullable = false)
    @ToString.Exclude
    private Pet pet;
}

