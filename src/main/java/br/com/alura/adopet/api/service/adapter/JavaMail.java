package br.com.alura.adopet.api.service.adapter;

import br.com.alura.adopet.api.service.interfaces.IEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class JavaMail implements IEmail {

    @Autowired
    private JavaMailSender emailSender;

    @Override
    public void sendEmail(String titulo, String msg, String remetente, String... destinatarios) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setFrom(remetente);

        for(String destinatario : destinatarios) {
            email.setTo(destinatario);
        }

        email.setSubject(titulo);
        email.setText(msg);
        emailSender.send(email);
    }

}
