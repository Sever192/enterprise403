package com.example.lab3.jms;

import com.example.lab3.dto.WelcomeEmailMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EmailNotificationConsumer {

    @JmsListener(destination = "${app.queue.email}", containerFactory = "queueListenerFactory")
    public void receiveWelcomeEmail(WelcomeEmailMessage message) {
        log.info("Получено сообщение для отправки email: {}", message);
        try {
            Thread.sleep(2000); // имитация отправки email
            log.info("[ИМИТАЦИЯ] Приветственное письмо отправлено на адрес: {} для {}",
                    message.getEmail(), message.getFirstName());
        } catch (InterruptedException e) {
            log.error("Ошибка при отправке email: {}", e.getMessage());
            Thread.currentThread().interrupt();
            throw new RuntimeException("Failed to send email", e);
        }
    }
}