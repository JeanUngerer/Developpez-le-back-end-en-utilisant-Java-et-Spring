package com.chatop.backend;

import com.chatop.backend.auth.config.AppProperties;
import com.chatop.backend.auth.config.RsaKeyProperties;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties({RsaKeyProperties.class, AppProperties.class})
@SpringBootApplication
@Slf4j
@SecurityScheme(name = "Authorization", description = "Get your token from <b>/aut/login</b> and give it valid credentials to get a jwt token. <br> Enter your bearer token in the format <b>Bearer &lt;token&gt;</b> " , scheme = "bearer", type = SecuritySchemeType.APIKEY, in = SecuritySchemeIn.HEADER)
public class BackendApplication {

  public static void main(String[] args) {
    SpringApplication.run(BackendApplication.class, args);
  }

}
