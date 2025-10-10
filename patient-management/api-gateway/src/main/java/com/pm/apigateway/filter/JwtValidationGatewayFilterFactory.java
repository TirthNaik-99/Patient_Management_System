package com.pm.apigateway.filter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.http.HttpHeaders;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

//this is a filter class
//it is a custom class that allows us to intercept HTTP requests apply custom logic and then decide whether or not to continue processing the request Or to cancel the request
// we can use a filter to call the auth service validation endpoint to check if the token is valid or not
//depending on that we can then handle the request in appropriate way by extending from the abstract gateway filter factory
// we are telling spring boot and spring boot cloud gateway dependencies that we want ot add this filter to request life cycle by extending abstract gateway and implementing apply method down here  which is what override annotation  is for
// it means that Spring boot cloud gateway will automatically apply our filter to all the requests

@Component
public class JwtValidationGatewayFilterFactory extends
        AbstractGatewayFilterFactory {


    private final WebClient webClient;
    //Dependency injected from WebClient.Builder
    public JwtValidationGatewayFilterFactory(WebClient.Builder webClientBuilder,
                                             @Value("${auth.service.url}") String authServiceUrl) {
        this.webClient = webClientBuilder.baseUrl(authServiceUrl).build();
    }
    @Override
    public GatewayFilter apply(Object config) {
        //exchange variable is an object is passed to us by Spring gateway that holds all the properties for the current request
        //chain variable is a variable that manages the chain of filters that currently exists in filter chain
        return (exchange, chain) -> {
            String token = exchange.getRequest().getHeaders().getFirst(AUTHORIZATION);
            if(token == null || !token.startsWith("Bearer ")) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            return webClient.get()
                    .uri("/validate")
                    .header(AUTHORIZATION, token)
                    .retrieve()
                    .toBodilessEntity()
                    .then(chain.filter(exchange));
        };
    }
}
