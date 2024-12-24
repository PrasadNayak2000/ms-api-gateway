package com.ms.gateway_service.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer.withDefaults
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity.AuthorizeExchangeSpec
import org.springframework.security.config.web.server.ServerHttpSecurity.OAuth2ResourceServerSpec
import org.springframework.security.web.server.SecurityWebFilterChain


@Configuration
//@EnableWebSecurity
class KeycloakFilter {

    @Bean
    fun springSecurityFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
        http.authorizeExchange { auth: AuthorizeExchangeSpec ->
            auth.pathMatchers(
                "/auth/api/v1/auth/login",
                "/auth/api/v1/auth/new-token"
            ).permitAll()
                .anyExchange().authenticated()
        }
            .oauth2ResourceServer { oauth2: OAuth2ResourceServerSpec -> oauth2.jwt(withDefaults()) }
        http.csrf { it.disable() }
        return http.build()
    }
}
