package br.com.notification_ms.consumer;

import br.com.notification_ms.dto.BookingEvent;
import br.com.notification_ms.service.EmailService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationConsumer {

    private final EmailService emailService;

    public NotificationConsumer(EmailService emailService) {
        this.emailService = emailService;
    }

    @RabbitListener(queues = "pending.bookings")
    public void sendConfirmationEmail(BookingEvent event) {
        emailService.sendConfirmationEmail(event);
    }

}
