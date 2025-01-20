package com.web.Lourenco.SpringWeb.servico.Autenticacao;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class LoginInterceptorAppConfig extends WebMvcConfigurationSupport {

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new LoginInterceptor())
        .excludePathPatterns("/login",
          "/error",
          "/logar",
          "/vendor/**",
          "/js/**",
          "/favicon.ico",
          "/css/**"
        );
  }
}

