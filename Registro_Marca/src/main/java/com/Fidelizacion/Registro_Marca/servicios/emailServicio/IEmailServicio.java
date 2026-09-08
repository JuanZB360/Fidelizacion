package com.Fidelizacion.Registro_Marca.servicios.emailServicio;

public interface IEmailServicio {

    void enviarCorreoSimple(String destinatario, String asunto, String contenido);
    void enviarCorreoHtml(String destinatario, String asunto, String cuerpoHtml);

}
