package com.suporte.chamados.exception;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Formato padrao do JSON de erro devolvido pela API.
 * "detalhes" e usado principalmente nos erros de validacao (um item por campo).
 */
public record ApiErro(
        LocalDateTime timestamp,
        int status,
        String erro,
        List<String> detalhes
) {
}
