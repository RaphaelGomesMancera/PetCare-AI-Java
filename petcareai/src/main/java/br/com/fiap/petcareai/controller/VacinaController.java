package br.com.fiap.petcareai.controller;

import br.com.fiap.petcareai.dto.VacinaRequestDTO;
import br.com.fiap.petcareai.dto.VacinaResponseDTO;
import br.com.fiap.petcareai.service.VacinaService;
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
@RequestMapping("/api/pets/{petId}/vacinas")
@RequiredArgsConstructor
@Tag(name = "Vacinas", description = "Controle de vacinas por pet")
public class VacinaController {

    private final VacinaService vacinaService;

    @GetMapping
    @Operation(summary = "Listar vacinas do pet com paginação")
    public ResponseEntity<Page<VacinaResponseDTO>> listar(
            @PathVariable Long petId,
            @PageableDefault(size = 10, sort = "dataAplicacao") Pageable pageable) {
        return ResponseEntity.ok(vacinaService.listarPorPet(petId, pageable));
    }

    @GetMapping("/proximas")
    @Operation(summary = "Listar vacinas com dose próxima",
               description = "Retorna vacinas cuja próxima dose vence nos próximos N dias (padrão: 30)")
    public ResponseEntity<List<VacinaResponseDTO>> proximas(
            @PathVariable Long petId,
            @RequestParam(defaultValue = "30") int dias) {
        return ResponseEntity.ok(vacinaService.listarProximas(petId, dias));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar vacina por ID")
    public ResponseEntity<VacinaResponseDTO> buscarPorId(
            @PathVariable Long petId, @PathVariable Long id) {
        return ResponseEntity.ok(vacinaService.buscarPorId(id));
    }

    @PostMapping
    @Operation(summary = "Registrar nova vacina para o pet")
    public ResponseEntity<VacinaResponseDTO> criar(
            @PathVariable Long petId, @Valid @RequestBody VacinaRequestDTO dto) {
        VacinaResponseDTO criada = vacinaService.criar(petId, dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(criada.id()).toUri();
        return ResponseEntity.created(location).body(criada);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar vacina")
    public ResponseEntity<VacinaResponseDTO> atualizar(
            @PathVariable Long petId, @PathVariable Long id,
            @Valid @RequestBody VacinaRequestDTO dto) {
        return ResponseEntity.ok(vacinaService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover vacina")
    public ResponseEntity<Void> deletar(@PathVariable Long petId, @PathVariable Long id) {
        vacinaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}

