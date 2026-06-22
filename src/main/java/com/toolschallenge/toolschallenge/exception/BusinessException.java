package com.toolschallenge.toolschallenge.exception;

/**
 * Lançada quando uma operação viola uma regra de negócio (ex.: estornar uma
 * transação negada). Mapeada para 409 Conflict.
 */
public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }
}
