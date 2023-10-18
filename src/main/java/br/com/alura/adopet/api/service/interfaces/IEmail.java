package br.com.alura.adopet.api.service.interfaces;

public interface IEmail {

    public void sendEmail(String titulo, String msg, String remetente, String... destinatarios);

}
