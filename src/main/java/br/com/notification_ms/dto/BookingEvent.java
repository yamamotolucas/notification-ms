package br.com.notification_ms.dto;

import br.com.notification_ms.BookingStatus;

import java.time.LocalDateTime;
import java.util.List;

public record BookingEvent(

        String customerName,
        String customerEmail,
        String barberName,
        LocalDateTime startTime,
        LocalDateTime endTime,
        List<ServicesDto> services,
        BookingStatus bookingStatus,
        String confirmationToken


) {}

