package org.jvi.sse.app.notification;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class Notification {

  private final String uneValeur;

  private final String uneAutreValeur;
}
