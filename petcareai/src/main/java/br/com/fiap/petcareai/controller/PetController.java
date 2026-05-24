package br.com.fiap.petcareai.controller;

import br.com.fiap.petcareai.domain.enums.EspeciePet;
import br.com.fiap.petcareai.dto.PetRequestDTO;
import br.com.fiap.petcareai.dto.PetResponseDTO;
import br.com.fiap.petcareai.dto.RecomendacaoDTO;
import br.com.fiap.petcareai.service.PetService;
import br.com.fiap.petcareai.service.RecomendacaoService;
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
@RequestMapping("/api/pets")
@RequiredArgsConstructor
@Tag(name = "Pets", description = "Gerenciamento de pets e recomendações de saúde")
public class PetController {

    private final PetService petService;
    private final RecomendacaoService recomendacaoService;

    @GetMapping
    @Operation(summary = "Listar pets", description = "Suporta filtro por nome e espécie, paginação e ordenação")
    public ResponseEntity<Page<PetResponseDTO>> listar(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) EspeciePet especie,
            @PageableDefault(size = 10, sort = "nome") Pageable pageable) {
        return ResponseEntity.ok(petService.listarTodos(nome, especie, pageable));
    }

    @GetMapping("/tutor/{tutorId}")
    @Operation(summary = "Listar pets de um tutor")
    public ResponseEntity<Page<PetResponseDTO>> listarPorTutor(
            @PathVariable Long tutorId,
            @PageableDefault(size = 10, sort = "nome") Pageable pageable) {
        return ResponseEntity.ok(petService.listarPorTutor(tutorId, pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar pet por ID")
    public ResponseEntity<PetResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(petService.buscarPorId(id));
    }

    @GetMapping("/{id}/recomendacoes")
    @Operation(summary = "Gerar recomendações de saúde para o pet",
               description = "Motor de IA baseado em regras: analisa idade, vacinas, consultas e medicamentos")
    public ResponseEntity<RecomendacaoDTO> recomendacoes(@PathVariable Long id) {
        return ResponseEntity.ok(recomendacaoService.gerarRecomendacoes(id));
    }

    @PostMapping
    @Operation(summary = "Cadastrar novo pet")
    public ResponseEntity<PetResponseDTO> criar(@Valid @RequestBody PetRequestDTO dto) {
        PetResponseDTO criado = petService.criar(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(criado.id()).toUri();
        return ResponseEntity.created(location).body(criado);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar pet")
    public ResponseEntity<PetResponseDTO> atualizar(
            @PathVariable Long id, @Valid @RequestBody PetRequestDTO dto) {
        return ResponseEntity.ok(petService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover pet")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        petService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}

