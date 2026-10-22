package com.suporte.chamados.dto;

import com.suporte.chamados.model.StatusChamado;
import jakarta.validation.constraints.Size;

/**
 * DTO DE ENTRADA para ATUALIZAR um chamado.
 * Permite alterar somente status e/ou descricao.
 * Os dois campos sao opcionais: o que vier null nao e alterado.
 */
public record ChamadoUpdateDTO(

        StatusChamado status,

        @Size(min = 1, max = 2000, message = "A descricao deve ter entre 1 e 2000 caracteres")
        String descricao
) {
}
