package com.ms.orderms.controller;

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
public class OrderResourceController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderResourceController.class);

    @Autowired
    private WebClient webClient;

    @GetMapping("/client-users")
    @CircuitBreaker(name = "usermsclient", fallbackMethod = "userMsFallback")
    public Mono getUserFromUserms(){
        LOGGER.info("about to call userms");
        return webClient.get().uri("/user").retrieve().bodyToMono(Object.class);
    }

    /**
     * Failure handeling
     * @param exception
     * @return
     */
    private Mono userMsFallback(Exception exception){
        //option to return from cache or call a backup service
        LOGGER.info("Exception occured", exception);
        return Mono.just("Exception Occured");
    }

    /**
     * Failure handeling
     * @param exception
     * @return
     */
    private Mono userMsFallback(CallNotPermittedException exception){
        //option to return from cache or call a backup service
        LOGGER.info("CallNotPermittedException occured", exception);
        return Mono.just("CallNotPermittedException Occured");
    }

}
