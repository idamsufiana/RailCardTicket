package com.rail.card.ticket.exception;

public class TicketException extends Exception{

    private Integer errorCode;
    private String message;
    private String[] values;

    public TicketException(String message) {
        super(message);
        this.errorCode = 500;
        this.message = message;
    }

    public TicketException(Integer errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
        this.message = message;
    }

    public TicketException(Integer errorCode, String message, String... values) {
        super(message);
        this.errorCode = errorCode;
        this.message = message;
        this.values = values;
    }

    public TicketException(Exception e) {
    }

    public Integer getErrorCode() {
        return this.errorCode;
    }

    public String getMessage() {
        return this.message;
    }

    public String[] getValues() {
        return this.values;
    }
}
