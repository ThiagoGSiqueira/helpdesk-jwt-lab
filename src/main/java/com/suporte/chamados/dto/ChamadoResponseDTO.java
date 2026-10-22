package com.suporte.chamados.dto;

import com.suporte.chamados.model.Categoria;
import com.suporte.chamados.model.Chamado;
import com.suporte.chamados.model.Prioridade;
import com.suporte.chamados.model.StatusChamado;

import java.time.LocalDateTime;

/**
 * DTO DE SAIDA: o que a API devolve ao cliente.
 * Evita expor a entidade JPA diretamente.
 */
public record ChamadoResponseDTO(
        Long id,
        String titulo,
        String descricao,
        Categoria categoria,
        StatusChamado status,
        Prioridade prioridade,
        LocalDateTime dataCriacao
) {

    // Converte Entity -> DTO de resposta
    public static ChamadoResponseDTO de(Chamado chamado) {
        return new ChamadoResponseDTO(
                chamado.getId(),
                chamado.getTitulo(),
                chamado.getDescricao(),
                chamado.getCategoria(),
                chamado.getStatus(),
                chamado.getPrioridade(),
                chamado.getDataCriacao()
        );
    }
}
