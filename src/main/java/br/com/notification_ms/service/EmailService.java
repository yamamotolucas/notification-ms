package br.com.notification_ms.service;


import br.com.notification_ms.dto.BookingEvent;
import br.com.notification_ms.dto.ServicesDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.swing.text.DateFormatter;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

@Service
public class EmailService {

    private final JavaMailSender mailSender;
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendConfirmationEmail(BookingEvent dto) {

        SimpleMailMessage message = new SimpleMailMessage();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        BigDecimal totalPrice = dto.services().stream()
                .map(ServicesDto::price)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        String formattedServices = dto.services().stream()
                .map(ServicesDto::name)
                .collect(Collectors.joining(", "));

        message.setTo(dto.customerEmail());
        message.setSubject("Confirmação de Agendamento - Barbearia");

        String body = String.format(
                "Olá, %s!\n\n" +
                        "Seu agendamento foi pré-reservado com sucesso. Estamos quase lá!\n\n" +
                        "📌 Detalhes do Agendamento:\n" +
                        "• Serviços: %s\n" +
                        "• Barbeiro: %s\n" +
                        "• Horário de Início: %s\n" +
                        "• Horário de Término: %s\n" +
                        "• Valor Total: R$ %s\n\n" +
                        "Por favor, clique no link abaixo para confirmar o seu horário:\n" +
                        "http://localhost:8080/booking-ms/v1/bookings/confirm/%s\n\n" +
                        "⚠️ Atenção: O seu horário só estará garantido após a confirmação no link acima.\n\n" +
                        "Te esperamos lá! 💈",
                dto.customerName(),
                formattedServices,
                dto.barberName(),
                dto.startTime().format(formatter),
                dto.endTime().format(formatter),
                totalPrice,
                dto.confirmationToken()
        );
        message.setText(body);
        mailSender.send(message);
    }
}