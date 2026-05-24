package br.com.fiap.petcareai.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "vacinas")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Vacina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome da vacina é obrigatório")
    @Size(max = 100, message = "Nome da vacina deve ter no máximo 100 caracteres")
    @Column(nullable = false)
    private String nome;

    @NotNull(message = "Data de aplicação é obrigatória")
    @PastOrPresent(message = "Data de aplicação não pode ser futura")
    @Column(nullable = false)
    private LocalDate dataAplicacao;

    private LocalDate proximaDose;

    @Size(max = 500)
    private String observacoes;

    @Size(max = 100)
    private String fabricante;

    @Size(max = 50)
    private String lote;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id", nullable = false)
    @ToString.Exclude
    private Pet pet;
}

