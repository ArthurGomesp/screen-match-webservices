package br.com.alura.screenmatch.exception;

public class erroDeConvercaoDeAno extends RuntimeException {
    private String message;
    public erroDeConvercaoDeAno(String message){
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
