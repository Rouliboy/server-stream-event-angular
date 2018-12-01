package org.jvi.sse.app.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import org.jvi.sse.app.notification.NewNotificationEvent;
import org.jvi.sse.app.notification.Notification;
import org.springframework.context.event.EventListener;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import lombok.extern.slf4j.Slf4j;

// lien à voir :
// https://stackoverflow.com/questions/34530544/java-spring-sseemitter-responsebodyemitter-detect-browser-reloads

@Service
@Slf4j
public class SseEmitterService {

  private final Collection<SseEmitter> emitters =
      Collections.synchronizedCollection(new HashSet<SseEmitter>());

  public void register(final SseEmitter emitter) {
    emitter.onTimeout(() -> timeout(emitter));
    emitter.onCompletion(() -> complete(emitter));

    emitters.add(emitter);
  }

  @EventListener
  public void onNewNotification(final NewNotificationEvent event) {

    notifyClients(
        Notification.builder().uneValeur(event.getMessage()).uneAutreValeur("autre").build());
  }

  private void notifyClients(final Notification notification) {
    for (final SseEmitter emitter : new ArrayList<>(emitters)) {
      try {
        emitter.send(notification, MediaType.APPLICATION_JSON);
      } catch (final Exception e) {
        log.debug("Error sending {} to client", notification, e);
      }
    }
  }

  private void complete(final SseEmitter emitter) {
    System.out.println("emitter completed");
    emitters.remove(emitter);
  }

  private void timeout(final SseEmitter emitter) {
    System.out.println("emitter timeout");
    emitters.remove(emitter);
  }

  // @Scheduled(fixedDelay = 3000)
  // public void sendSseEventsToUI() { // your model class
  // for (final SseEmitter emitter : emitters) {
  // try {
  // emitter.send(UUID.randomUUID().toString(), MediaType.APPLICATION_JSON);
  // } catch (final Throwable e) {
  // emitter.complete();
  // }
  // } ;
  // }
}
