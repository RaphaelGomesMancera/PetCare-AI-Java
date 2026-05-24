package br.com.fiap.petcareai.controller;

import br.com.fiap.petcareai.dto.MedicamentoRequestDTO;
import br.com.fiap.petcareai.dto.MedicamentoResponseDTO;
import br.com.fiap.petcareai.service.MedicamentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/pets/{petId}/medicamentos")
@RequiredArgsConstructor
@Tag(name = "Medicamentos", description = "Controle de medicamentos por pet")
public class MedicamentoController {

    private final MedicamentoService medicamentoService;

    @GetMapping
    @Operation(summary = "Listar medicamentos do pet com paginação")
    public ResponseEntity<Page<MedicamentoResponseDTO>> listar(
            @PathVariable Long petId,
            @PageableDefault(size = 10, sort = "nome") Pageable pageable) {
        return ResponseEntity.ok(medicamentoService.listarPorPet(petId, pageable));
    }

    @GetMapping("/ativos")
    @Operation(summary = "Listar medicamentos ativos do pet")
    public ResponseEntity<List<MedicamentoResponseDTO>> listarAtivos(@PathVariable Long petId) {
        return ResponseEntity.ok(medicamentoService.listarAtivosPorPet(petId));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar medicamento por ID")
    public ResponseEntity<MedicamentoResponseDTO> buscarPorId(
            @PathVariable Long petId, @PathVariable Long id) {
        return ResponseEntity.ok(medicamentoService.buscarPorId(petId, id));
    }

    @PostMapping
    @Operation(summary = "Cadastrar novo medicamento para o pet")
    public ResponseEntity<MedicamentoResponseDTO> criar(
            @PathVariable Long petId, @Valid @RequestBody MedicamentoRequestDTO dto) {
        MedicamentoResponseDTO criado = medicamentoService.criar(petId, dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(criado.id()).toUri();
        return ResponseEntity.created(location).body(criado);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar medicamento")
    public ResponseEntity<MedicamentoResponseDTO> atualizar(
            @PathVariable Long petId, @PathVariable Long id,
            @Valid @RequestBody MedicamentoRequestDTO dto) {
        return ResponseEntity.ok(medicamentoService.atualizar(petId, id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover medicamento")
    public ResponseEntity<Void> deletar(@PathVariable Long petId, @PathVariable Long id) {
        medicamentoService.deletar(petId, id);
        return ResponseEntity.noContent().build();
    }
}


