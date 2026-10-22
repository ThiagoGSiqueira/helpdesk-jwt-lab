package com.suporte.chamados.controller;

import com.suporte.chamados.dto.ChamadoRequestDTO;
import com.suporte.chamados.dto.ChamadoResponseDTO;
import com.suporte.chamados.dto.ChamadoUpdateDTO;
import com.suporte.chamados.service.ChamadoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * CAMADA CONTROLLER: expoe os endpoints REST.
 * Cuida apenas de HTTP (rotas, status codes, validacao com @Valid)
 * e delega toda a logica para o service.
 */
@RestController
@RequestMapping("/chamados")
public class ChamadoController {

    private final ChamadoService service;

    public ChamadoController(ChamadoService service) {
        this.service = service;
    }

    /** POST /chamados -> cria um chamado (201 Created) */
    @PostMapping
    public ResponseEntity<ChamadoResponseDTO> criar(@Valid @RequestBody ChamadoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(dto));
    }

    /** GET /chamados/{id} -> busca por ID (200 ou 404) */
    @GetMapping("/{id}")
    public ResponseEntity<ChamadoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    /** GET /chamados -> lista todos (200) */
    @GetMapping
    public ResponseEntity<List<ChamadoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    /** PUT /chamados/{id} -> atualiza status e/ou descricao (200 ou 404) */
    @PutMapping("/{id}")
    public ResponseEntity<ChamadoResponseDTO> atualizar(@PathVariable Long id,
                                                        @Valid @RequestBody ChamadoUpdateDTO dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    /** DELETE /chamados/{id} -> remove (204 No Content ou 404) */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
