package com.suporte.chamados.dto;

import com.suporte.chamados.model.Categoria;
import com.suporte.chamados.model.Prioridade;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * DTO DE ENTRADA para CRIAR um chamado.
 * Nao expoe id, status nem data: esses campos sao definidos pelo servidor.
 * As anotacoes de Bean Validation sao checadas pelo @Valid no controller.
 */
public record ChamadoRequestDTO(

        @NotBlank(message = "O titulo e obrigatorio")
        @Size(max = 150, message = "O titulo deve ter no maximo 150 caracteres")
        String titulo,

        @NotBlank(message = "A descricao e obrigatoria")
        @Size(max = 2000, message = "A descricao deve ter no maximo 2000 caracteres")
        String descricao,

        @NotNull(message = "A categoria e obrigatoria")
        Categoria categoria,

        @NotNull(message = "A prioridade e obrigatoria")
        Prioridade prioridade
) {
}
