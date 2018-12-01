package org.jvi.sse.app.controller;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.jvi.sse.app.service.SseEmitterService;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import com.google.common.collect.ImmutableMap;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("api/sse")
public class AppController {

  private final SseEmitterService sseEmitterService;

  @RequestMapping("ping")
  public Map<String, String> ping() {
    return ImmutableMap.of("result", "pong");
  }

  @GetMapping(value = "stream", produces = "text/event-stream")
  public SseEmitter getEntityEvents(final HttpServletRequest request) {

    log.info("Registering new Emitter");
    final SseEmitter emitter = new SseEmitter();
    sseEmitterService.register(emitter);
    return emitter;
  }

  // This exception may be raised on a regular basis as sent events regularity is
  // not guaranty.
  // So we catch this exception to prevent log flooding.
  @ExceptionHandler(AsyncRequestTimeoutException.class)
  public void asyncRequestTimeoutExceptionHandler(final HttpServletRequest req) {
    log.debug("Async request to '{}' timed out", req.getRequestURI());
  }

}
