package br.com.alura.screenmatch.principal;

public class teste {
    public static void main(String[] args) {
        var texto = "texto com espaços";
        texto = texto.replaceAll("\\s+", "+");
        System.out.println(texto);
    }
}
