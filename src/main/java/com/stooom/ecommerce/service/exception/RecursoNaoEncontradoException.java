package com.stooom.ecommerce.service.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class RecursoNaoEncontradoException extends RuntimeException {
    public RecursoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}
