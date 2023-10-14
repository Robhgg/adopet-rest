package br.com.alura.adopet.api.util;

public class ValidadorUtil {

    private ValidadorUtil() {
        throw new IllegalStateException("Essa classe nao pode ser instanciada.");
    }

    /**
     * Verifica se o parametro só contém numeros.
     * @param idOuNome
     * @return true se a String só conter numeros
     */
    public static boolean isNumeric(String idOuNome) {
        return idOuNome.matches("^[0-9]+$");
    }
}
