package org.library_gabs.exception;

public class LoanNotFoundException extends RuntimeException{

    public LoanNotFoundException() {
        super("Reserva não encontrada.");
    }
}
