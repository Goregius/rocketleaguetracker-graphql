package com.github.goregius.rankinflation.router

import com.github.goregius.rankinflation.handler.RatingsHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class RatingsRouters {
    @Bean
    fun ratingsRouter(ratingsHandler: RatingsHandler) = coRouter {
        "/api".nest {
            accept(APPLICATION_JSON).nest {
                "/ratings".nest {
                    GET("", ratingsHandler::findAll)
                    POST("/update", ratingsHandler::fetch)
                }
            }
        }
    }
}

