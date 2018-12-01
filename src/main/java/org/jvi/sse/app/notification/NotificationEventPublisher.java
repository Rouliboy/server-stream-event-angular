package org.jvi.sse.app.notification;

import java.time.Instant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class NotificationEventPublisher {

  private final ApplicationEventPublisher publisher;

  @Autowired
  public NotificationEventPublisher(final ApplicationEventPublisher publisher) {
    this.publisher = publisher;
  }

  public void publish(final String message) {
    publisher.publishEvent(new NewNotificationEvent(1L, message + Instant.now().toString()));
  }

  // Simulate
  @Scheduled(fixedRate = 5000)
  public void simulate() {
    log.info("simulate send");
    publish("new notif at ");
  }
}
