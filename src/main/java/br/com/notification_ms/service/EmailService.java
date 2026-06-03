package br.com.notification_ms.service;


import br.com.notification_ms.dto.BookingEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${my.email}")
    private String emailFrom;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendConfirmationEmail(BookingEvent dto) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(emailFrom);
        message.setSubject("Confirmação de Agendamento - Barbearia");

        String body = String.format(
                "Olá! Seu agendamento foi pré-reservado com sucesso.\n\n" +
                        "📌 Detalhes do Agendamento:\n" +
                        "• Código do Cliente: %d\n" +
                        "• Código do Barbeiro: %d\n" +
                        "• Horário de Início: %s\n" +
                        "• Horário de Término: %s\n\n" +
                        "Por favor, clique no link abaixo para confirmar o seu horário:\n" +
                        "http://localhost:8080/booking-ms/v1/bookings/confirm/%s\n\n" +
                        "Te esperamos lá!",
                dto.customerId(),
                dto.barberId(),
                dto.startTime().toString(),
                dto.endTime().toString(),
                dto.confirmationToken()
        );

        message.setText(body);
        mailSender.send(message);
    }
}