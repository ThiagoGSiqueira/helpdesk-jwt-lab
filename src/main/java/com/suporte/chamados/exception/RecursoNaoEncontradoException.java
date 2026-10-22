package com.suporte.chamados.exception;

/**
 * Lancada quando um registro nao existe no banco.
 * Capturada pelo GlobalExceptionHandler e convertida em HTTP 404.
 */
public class RecursoNaoEncontradoException extends RuntimeException {

    public RecursoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}
