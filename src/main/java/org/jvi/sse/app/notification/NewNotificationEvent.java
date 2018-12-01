package org.jvi.sse.app.notification;

import org.springframework.context.ApplicationEvent;
import lombok.Getter;

@Getter
public class NewNotificationEvent extends ApplicationEvent {

  private static final long serialVersionUID = 8385137134073049578L;

  private String message;

  public NewNotificationEvent(final Object source, final String message) {
    super(source);
    this.message = message;
  }
}
