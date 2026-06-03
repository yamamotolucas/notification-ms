package br.com.notification_ms.dto;
import java.math.BigDecimal;

public record ServicesDto(
        String name,
        BigDecimal price
) {
}
