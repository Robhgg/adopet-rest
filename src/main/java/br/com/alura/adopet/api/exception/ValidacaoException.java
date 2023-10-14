package br.com.alura.adopet.api.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ValidacaoException extends RuntimeException{
    public ValidacaoException(String msg) {
        super(msg);
    }
}
