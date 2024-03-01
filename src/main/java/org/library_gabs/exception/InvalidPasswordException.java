package org.library_gabs.exception;

public class InvalidPasswordException extends RuntimeException {
    public InvalidPasswordException() {
        super("Usuário e/ou Senha incorreto(s)");
    }
}