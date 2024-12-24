package com.ms.gateway_service.config

import org.springframework.http.server.reactive.ServerHttpRequest
import java.util.function.Predicate

object RouteValidator {
    private val openApiEndpoints = listOf(
        "/auth/api/v1/user/registration",
        "/auth/api/v1/auth/login",
        "/eureka"
    )

    val isSecured = Predicate { request: ServerHttpRequest ->
        openApiEndpoints.stream().noneMatch { uri: String -> request.uri.path.contains(uri) }
    }
}