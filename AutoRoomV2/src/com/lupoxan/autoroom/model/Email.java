/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lupoxan.autoroom.model;

import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * Clase para la utilización del servicio de correo electrónico
 * @author lupo.xan
 * @since 19/10/2018
 * @version 0.8
 */
public class Email extends Thread{

    private String archivo;
    private final String asunto;
    private final String msg;
    private Properties props = new Properties();
    private final Ficheros F = new Ficheros();

    public Email(String asunto, String msg) {
        this.asunto = asunto;
        this.msg = msg;
    }

    public void setArchivo(String nombre) {
        this.archivo = nombre;
    }

    public String getArchivo() {
        return archivo;
    }
    
    @Override
    public void run() {
        try {
            props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.socketFactory.port", "465");
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.port", "465");

            Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(F.prop(Constantes.ALERTACORREOS), F.prop(Constantes.PASSWORDCORREO));
                }
            });
            session.setDebug(true);

            BodyPart texto = new MimeBodyPart();
            texto.setText(this.msg);

            BodyPart adjunto = new MimeBodyPart();
            adjunto.setDataHandler(new DataHandler(new FileDataSource(this.getArchivo())));
            String[] nombre = this.getArchivo().split("/");
            adjunto.setFileName(nombre[6]);

            MimeMultipart multiParte = new MimeMultipart();

            multiParte.addBodyPart(texto);
            multiParte.addBodyPart(adjunto);

            MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress(F.prop(Constantes.ALERTACORREOS)));
            message.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress(F.prop(Constantes.ALERTACORREOR)));
            message.setSubject(this.asunto);
            message.setContent(multiParte);

            Transport.send(message);
        } catch (MessagingException ex) {
            System.err.println(ex.getMessage());
        }
    }
}
