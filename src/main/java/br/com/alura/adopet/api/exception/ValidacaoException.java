package br.com.alura.adopet.api.exception;

import jakarta.validation.ValidationException;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ValidacaoException extends Exception {
    public ValidacaoException(String msg) {
        super(msg);
    }
}
