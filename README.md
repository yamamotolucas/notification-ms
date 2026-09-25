<div align="center">

# 📬 Notification Microservice (`notification-ms`)

<p align="center">
  <b>Serviço assíncrono de mensageria e notificações transacionais por e-mail do ecossistema Barbearia.</b>
</p>

<!-- Badges de Tecnologias -->
<p align="center">
  <img src="https://img.shields.io/badge/Java-17-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java 17" />
  <img src="https://img.shields.io/badge/Spring_Boot-4.0.5-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white" alt="Spring Boot 4" />
  <img src="https://img.shields.io/badge/Spring_Cloud-2025.1.1-6DB33F?style=for-the-badge&logo=spring&logoColor=white" alt="Spring Cloud" />
  <img src="https://img.shields.io/badge/RabbitMQ-AMQP-FF6600?style=for-the-badge&logo=rabbitmq&logoColor=white" alt="RabbitMQ" />
  <img src="https://img.shields.io/badge/JavaMail-SMTP-D93025?style=for-the-badge&logo=gmail&logoColor=white" alt="JavaMail" />
  <img src="https://img.shields.io/badge/Netflix_Eureka_Client-Spring_Cloud-brightgreen?style=for-the-badge&logo=spring&logoColor=white" alt="Eureka" />
</p>

</div>

---

## 📌 Visão Geral

O **`notification-ms`** é o microsserviço responsável pelo processamento assíncrono de eventos transacionais e entrega de notificações do ecossistema. Operando orientado a eventos (Event-Driven Architecture), o serviço escuta filas do **RabbitMQ** publicadas por outros microsserviços (como o `booking-ms`) e orquestra o disparo automatizado de e-mails transacionais através de **JavaMail / SMTP**.

O design desacoplado garante que o fluxo principal de agendamento não seja onerado ou bloqueado pelo tempo de resposta de provedores externos de e-mail.

---

## 🛠️ Stack & Tecnologias

* **Linguagem & Framework:** Java 17 + Spring Boot 4.0.5 (Spring Web MVC)
* **Mensageria & AMQP:** Spring Boot Starter AMQP + RabbitMQ
* **Envio de E-mails:** Spring Boot Starter Mail (JavaMailSender / SMTP)
* **Resiliência de Mensagens:** Spring AMQP Retry Mechanism (política exponencial de retentativas)
* **Service Discovery:** Spring Cloud Netflix Eureka Client (Spring Cloud 2025.1.1)

---

## ✨ Funcionalidades Principais

- 📥 **Consumo Assíncrono via RabbitMQ:** Ingestão desacoplada de eventos de reserva (`BookingEvent`) através do listener `NotificationConsumer`.
- ✉️ **Disparo de E-mails Transacionais:** Envio automatizado de mensagens formatadas via `EmailService` contendo dados da reserva, serviços e status.
- 🔁 **Política de Retentativas (Retry Logic):** Mecanismo de até 5 retentativas automáticas com intervalos progressivos em caso de falha de conexão SMTP.
- 📦 **Contratos Imutáveis com Records:** Manipulação tipada de payloads através de Records Java (`BookingEvent`, `ServicesDto`).
- 🚦 **Controle de Ciclo com Enums:** Rastreamento do ciclo de vida das notificações atrelado ao `BookingStatus`.

---

## 🏗️ Estrutura Arquitetural

O serviço adota uma estrutura orientada a consumo de eventos e execução de serviços especialistas:

```text
notification-ms/
├── src/
│   ├── main/
│   │   ├── java/br/com/notification_ms/
│   │   │   ├── amqp/                       # Configurações de filas, exchanges e bindings do RabbitMQ (NotificationAMQPConfig)
│   │   │   ├── consumer/                   # Consumidores AMQP (@RabbitListener - NotificationConsumer)
│   │   │   ├── dto/                        # Records de eventos trafegados (BookingEvent, ServicesDto)
│   │   │   ├── service/                    # Lógica de composição e envio de e-mails (EmailService)
│   │   │   ├── BookingStatus.java          # Enum de estados do agendamento
│   │   │   └── NotificationMsApplication.java   
│   │   └── resources/
│   │       └── application.properties      # Configurações de runtime, broker e SMTP
│   └── test/
│       └── java/br/com/notification_ms/   
│
├── pom.xml
└── Dockerfile

```
---
## ⚠️ Observações de Execução no Ecossistema

> **Atenção:** Este microsserviço integra uma arquitetura distribuída. Para o seu funcionamento pleno (comunicação entre serviços, descoberta de rotas e persistência de dados), é indispensável que o **`eureka-server`**, uma instância ativa do **MySQL** e os demais microsserviços dependentes estejam em execução.
>
> Para executar ou clonar o ecossistema completo com todas as dependências e submódulos configurados, acesse o repositório central:  
> 🔗 [**barbearia-network-main**](https://github.com/yamamotolucas/barbearia-network-main)
