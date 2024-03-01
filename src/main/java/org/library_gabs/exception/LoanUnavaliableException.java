package org.library_gabs.exception;

public class LoanUnavaliableException extends RuntimeException {
    public LoanUnavaliableException() {
        super("Order not found!");
    }
}
