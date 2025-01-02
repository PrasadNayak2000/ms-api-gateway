package com.ms.gateway_service.config

import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory
import org.springframework.context.annotation.Bean
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity.AuthorizeExchangeSpec
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.stereotype.Component

@Component
class KeycloakFilter() : AbstractGatewayFilterFactory<Any>() {

    @Bean
    fun springSecurityFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
        http.authorizeExchange { auth: AuthorizeExchangeSpec ->
            auth.anyExchange().permitAll()
        }
        //.oauth2ResourceServer { oauth2: ServerHttpSecurity.OAuth2ResourceServerSpec -> oauth2.jwt(withDefaults()) }
        http.csrf { it.disable() }
        return http.build()
    }

    override fun apply(config: Any?): GatewayFilter? {
        return GatewayFilter { exchange, chain ->
            val newRequest = exchange.request.mutate()
                .header("x-gateway-service", "true")  // Forward the same Authorization header
                .build()

            val modifiedExchange = exchange.mutate().request(newRequest).build()
            chain.filter(modifiedExchange)
        }
    }
}
