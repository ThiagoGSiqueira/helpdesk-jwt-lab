package com.suporte.chamados.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Tratamento CENTRALIZADO de excecoes.
 * O @ControllerAdvice intercepta excecoes lancadas por qualquer controller
 * e as transforma em respostas HTTP padronizadas (ApiErro).
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    // 404: chamado nao encontrado
    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<ApiErro> tratarNaoEncontrado(RecursoNaoEncontradoException ex) {
        return montarResposta(HttpStatus.NOT_FOUND, "Recurso nao encontrado", List.of(ex.getMessage()));
    }

    // 400: falha no @Valid do corpo da requisicao
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErro> tratarValidacao(MethodArgumentNotValidException ex) {
        List<String> detalhes = ex.getBindingResult().getFieldErrors().stream()
                .map(erro -> erro.getField() + ": " + erro.getDefaultMessage())
                .toList();
        return montarResposta(HttpStatus.BAD_REQUEST, "Erro de validacao", detalhes);
    }

    // 400: JSON malformado ou valor de enum invalido (ex: status "XYZ")
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiErro> tratarJsonInvalido(HttpMessageNotReadableException ex) {
        return montarResposta(HttpStatus.BAD_REQUEST, "Corpo da requisicao invalido",
                List.of("JSON malformado ou com valor invalido (verifique os valores dos enums)"));
    }

    // 400: id da URL nao numerico (ex: /chamados/abc)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiErro> tratarTipoInvalido(MethodArgumentTypeMismatchException ex) {
        return montarResposta(HttpStatus.BAD_REQUEST, "Parametro invalido",
                List.of("O parametro '" + ex.getName() + "' possui um valor invalido"));
    }

    // 500: qualquer erro inesperado (nao vaza detalhes internos ao cliente)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErro> tratarErroGenerico(Exception ex) {
        return montarResposta(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno do servidor",
                List.of("Ocorreu um erro inesperado"));
    }

    // Metodo auxiliar para nao repetir a montagem do ApiErro
    private ResponseEntity<ApiErro> montarResposta(HttpStatus status, String erro, List<String> detalhes) {
        ApiErro corpo = new ApiErro(LocalDateTime.now(), status.value(), erro, detalhes);
        return ResponseEntity.status(status).body(corpo);
    }
}
