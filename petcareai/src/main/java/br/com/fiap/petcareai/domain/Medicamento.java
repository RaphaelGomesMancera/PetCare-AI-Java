package br.com.fiap.petcareai.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "medicamentos")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Medicamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome do medicamento é obrigatório")
    @Size(max = 100)
    @Column(nullable = false)
    private String nome;

    @NotBlank(message = "Dosagem é obrigatória")
    @Size(max = 100)
    @Column(nullable = false)
    private String dosagem;

    @NotBlank(message = "Frequência é obrigatória")
    @Size(max = 100)
    @Column(nullable = false)
    private String frequencia;

    @NotNull(message = "Data de início é obrigatória")
    @Column(nullable = false)
    private LocalDate dataInicio;

    private LocalDate dataFim;

    @Column(nullable = false)
    @Builder.Default
    private boolean ativo = true;

    @Size(max = 500)
    private String observacoes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id", nullable = false)
    @ToString.Exclude
    private Pet pet;
}

