package com.suporte.chamados.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuracao MINIMA de seguranca.
 *
 * Como o spring-boot-starter-security esta no classpath, o Spring bloquearia
 * todas as rotas por padrao (login + senha gerada no console). Enquanto o JWT
 * nao e implementado, liberamos tudo para o CRUD funcionar no Postman.
 *
 * TODO: substituir por filtro JWT + regras por rota na proxima etapa.
 */
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // CSRF desativado: API REST sem sessao/cookies nao precisa dele
            .csrf(AbstractHttpConfigurer::disable)
            // Permite o console do H2 abrir dentro de um frame
            .headers(h -> h.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
            // Libera todas as rotas
            .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());

        return http.build();
    }
}
