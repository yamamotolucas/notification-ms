package br.com.notification_ms.dto;

import br.com.notification_ms.BookingStatus;

import java.time.LocalDateTime;
import java.util.List;

public record BookingEvent(

        Long bookingId,
        Long customerId,
        Long barberId,
        LocalDateTime startTime,
        LocalDateTime endTime,
        List<ServicesDto> services,
        BookingStatus bookingStatus,
        String confirmationToken


) {}

