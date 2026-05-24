package br.com.fiap.petcareai.controller;

import br.com.fiap.petcareai.dto.AtividadeIoTRequestDTO;
import br.com.fiap.petcareai.dto.AtividadeIoTResponseDTO;
import br.com.fiap.petcareai.service.AtividadeIoTService;
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
@RequestMapping("/api/pets/{petId}/atividades")
@RequiredArgsConstructor
@Tag(name = "Atividades IoT", description = "Monitoramento de atividade física e sinais vitais do pet via sensores IoT simulados")
public class AtividadeIoTController {

    private final AtividadeIoTService atividadeIoTService;

    @GetMapping
    @Operation(summary = "Listar atividades IoT do pet com paginação")
    public ResponseEntity<Page<AtividadeIoTResponseDTO>> listar(
            @PathVariable Long petId,
            @PageableDefault(size = 10, sort = "registradoEm") Pageable pageable) {
        return ResponseEntity.ok(atividadeIoTService.listarPorPet(petId, pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar atividade por ID")
    public ResponseEntity<AtividadeIoTResponseDTO> buscarPorId(
            @PathVariable Long petId, @PathVariable Long id) {
        return ResponseEntity.ok(atividadeIoTService.buscarPorId(petId, id));
    }

    @PostMapping
    @Operation(summary = "Registrar nova atividade IoT",
               description = "Ao registrar, o sistema analisa automaticamente temperatura, passos e frequência cardíaca e gera alertas")
    public ResponseEntity<AtividadeIoTResponseDTO> registrar(
            @PathVariable Long petId, @Valid @RequestBody AtividadeIoTRequestDTO dto) {
        AtividadeIoTResponseDTO criada = atividadeIoTService.registrar(petId, dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(criada.id()).toUri();
        return ResponseEntity.created(location).body(criada);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover atividade IoT")
    public ResponseEntity<Void> deletar(@PathVariable Long petId, @PathVariable Long id) {
        atividadeIoTService.deletar(petId, id);
        return ResponseEntity.noContent().build();
    }
}


