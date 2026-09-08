package com.Fidelizacion.Registro_Marca.servicios.emailServicio;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor 
public class ImpEmailServicio implements IEmailServicio {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String remitente;

    @Override
    @Async
    public void enviarCorreoSimple(String destinatario, String asunto, String contenido) {
        try {
            log.info("Iniciando envío de correo simple a: {}", destinatario);
            SimpleMailMessage mensaje = new SimpleMailMessage();
            mensaje.setFrom(remitente);
            mensaje.setTo(destinatario);
            mensaje.setSubject(asunto);
            mensaje.setText(contenido);

            mailSender.send(mensaje);
            log.info("Correo simple enviado exitosamente a: {}", destinatario);
        } catch (Exception e) {
            log.error("Error al enviar correo simple a {}: {}", destinatario, e.getMessage());
        }
    }

    @Override
    @Async 
    public void enviarCorreoHtml(String destinatario, String asunto, String cuerpoHtml) {
        try {
            log.info("Iniciando envío de correo HTML a: {}", destinatario);
            MimeMessage mensaje = mailSender.createMimeMessage();
            // El flag 'true' indica que es multipart/html y admite codificación UTF-8
            MimeMessageHelper helper = new MimeMessageHelper(mensaje, true, "UTF-8");

            helper.setFrom(remitente);
            helper.setTo(destinatario);
            helper.setSubject(asunto);
            helper.setText(cuerpoHtml, true); // 'true' habilita renderizado HTML

            mailSender.send(mensaje);
            log.info("Correo HTML enviado exitosamente a: {}", destinatario);
        } catch (MessagingException | MailException e) {
            log.error("Error al enviar el correo HTML a {}: {}", destinatario, e.getMessage(), e);
        }
    }

}
