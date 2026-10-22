package com.suporte.chamados.service;

import com.suporte.chamados.dto.ChamadoRequestDTO;
import com.suporte.chamados.dto.ChamadoResponseDTO;
import com.suporte.chamados.dto.ChamadoUpdateDTO;
import com.suporte.chamados.exception.RecursoNaoEncontradoException;
import com.suporte.chamados.model.Chamado;
import com.suporte.chamados.model.StatusChamado;
import com.suporte.chamados.repository.ChamadoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * CAMADA SERVICE: regras de negocio.
 * Recebe DTOs do controller, converte para entidade, usa o repository
 * e devolve DTOs de resposta. O controller nao conhece o repository.
 */
@Service
public class ChamadoService {

    private final ChamadoRepository repository;

    // Injecao de dependencia via construtor (boa pratica)
    public ChamadoService(ChamadoRepository repository) {
        this.repository = repository;
    }

    /** Cria um chamado novo. Todo chamado nasce com status ABERTO. */
    @Transactional
    public ChamadoResponseDTO criar(ChamadoRequestDTO dto) {
        Chamado chamado = new Chamado();
        chamado.setTitulo(dto.titulo());
        chamado.setDescricao(dto.descricao());
        chamado.setCategoria(dto.categoria());
        chamado.setPrioridade(dto.prioridade());
        chamado.setStatus(StatusChamado.ABERTO);

        return ChamadoResponseDTO.de(repository.save(chamado));
    }

    /** Busca um chamado pelo ID. Lanca 404 se nao existir. */
    @Transactional(readOnly = true)
    public ChamadoResponseDTO buscarPorId(Long id) {
        return ChamadoResponseDTO.de(buscarEntidade(id));
    }

    /** Lista todos os chamados (sem paginacao). */
    @Transactional(readOnly = true)
    public List<ChamadoResponseDTO> listarTodos() {
        return repository.findAll().stream()
                .map(ChamadoResponseDTO::de)
                .toList();
    }

    /** Atualiza status e/ou descricao. Campos null sao ignorados. */
    @Transactional
    public ChamadoResponseDTO atualizar(Long id, ChamadoUpdateDTO dto) {
        Chamado chamado = buscarEntidade(id);

        if (dto.status() != null) {
            chamado.setStatus(dto.status());
        }
        if (dto.descricao() != null) {
            chamado.setDescricao(dto.descricao());
        }

        // Nao precisa chamar save(): a entidade esta "gerenciada" dentro
        // da transacao e o JPA grava as alteracoes no commit (dirty checking).
        return ChamadoResponseDTO.de(chamado);
    }

    /** Remove um chamado. Lanca 404 se nao existir. */
    @Transactional
    public void deletar(Long id) {
        Chamado chamado = buscarEntidade(id);
        repository.delete(chamado);
    }

    // Metodo auxiliar reaproveitado por buscar, atualizar e deletar
    private Chamado buscarEntidade(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Chamado com id " + id + " nao encontrado"));
    }
}
