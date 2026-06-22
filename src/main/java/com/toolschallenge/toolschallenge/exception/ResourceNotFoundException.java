package com.toolschallenge.toolschallenge.exception;

/**
 * Lançada quando um recurso (ex.: pagamento) não é encontrado. Mapeada para 404.
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
