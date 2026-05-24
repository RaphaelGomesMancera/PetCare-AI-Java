package br.com.fiap.petcareai.domain;

import br.com.fiap.petcareai.domain.enums.TipoConsulta;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "consultas")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Consulta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Data da consulta é obrigatória")
    @Column(nullable = false)
    private LocalDate data;

    @NotBlank(message = "Nome do veterinário é obrigatório")
    @Size(max = 100)
    @Column(nullable = false)
    private String veterinario;

    @NotNull(message = "Tipo da consulta é obrigatório")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoConsulta tipo;

    @Size(max = 1000)
    private String diagnostico;

    @Size(max = 1000)
    private String prescricao;

    @Size(max = 500)
    private String observacoes;

    @Builder.Default
    private Double valorConsulta = 0.0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id", nullable = false)
    @ToString.Exclude
    private Pet pet;
}

