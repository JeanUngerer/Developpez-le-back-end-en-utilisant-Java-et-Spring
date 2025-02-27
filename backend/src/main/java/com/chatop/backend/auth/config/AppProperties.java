package com.chatop.backend.auth.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties("app")
public class AppProperties {

  private BasePath base = new BasePath();

  @Getter
  @Setter
  public static class BasePath {
    private String path;
  }
}
