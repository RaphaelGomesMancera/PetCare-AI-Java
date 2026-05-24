package br.com.fiap.petcareai.controller;

import br.com.fiap.petcareai.domain.enums.TipoConsulta;
import br.com.fiap.petcareai.dto.ConsultaRequestDTO;
import br.com.fiap.petcareai.dto.ConsultaResponseDTO;
import br.com.fiap.petcareai.service.ConsultaService;
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

@RestController
@RequestMapping("/api/pets/{petId}/consultas")
@RequiredArgsConstructor
@Tag(name = "Consultas", description = "Histórico de consultas veterinárias por pet")
public class ConsultaController {

    private final ConsultaService consultaService;

    @GetMapping
    @Operation(summary = "Listar consultas do pet", description = "Filtra por tipo, suporta paginação e ordenação")
    public ResponseEntity<Page<ConsultaResponseDTO>> listar(
            @PathVariable Long petId,
            @RequestParam(required = false) TipoConsulta tipo,
            @PageableDefault(size = 10, sort = "data") Pageable pageable) {
        return ResponseEntity.ok(consultaService.listarPorPet(petId, tipo, pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar consulta por ID")
    public ResponseEntity<ConsultaResponseDTO> buscarPorId(
            @PathVariable Long petId, @PathVariable Long id) {
        return ResponseEntity.ok(consultaService.buscarPorId(id));
    }

    @PostMapping
    @Operation(summary = "Registrar nova consulta")
    public ResponseEntity<ConsultaResponseDTO> criar(
            @PathVariable Long petId, @Valid @RequestBody ConsultaRequestDTO dto) {
        ConsultaResponseDTO criada = consultaService.criar(petId, dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(criada.id()).toUri();
        return ResponseEntity.created(location).body(criada);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar consulta")
    public ResponseEntity<ConsultaResponseDTO> atualizar(
            @PathVariable Long petId, @PathVariable Long id,
            @Valid @RequestBody ConsultaRequestDTO dto) {
        return ResponseEntity.ok(consultaService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover consulta")
    public ResponseEntity<Void> deletar(@PathVariable Long petId, @PathVariable Long id) {
        consultaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}

