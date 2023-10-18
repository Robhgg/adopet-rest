package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.service.interfaces.IEmail;
import org.springframework.beans.factory.annotation.Autowired;

public class EmailService {

    @Autowired
    private IEmail emailSender;

    public void sendEmail(String titulo, String msg, String remetente, String... destinatarios) {
        emailSender.sendEmail(titulo, msg, remetente, destinatarios);
    }

}
