package org.jvi.sse.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ServerStreamEventAngularApplication {

  public static void main(final String[] args) {
    SpringApplication.run(ServerStreamEventAngularApplication.class, args);
  }
}
