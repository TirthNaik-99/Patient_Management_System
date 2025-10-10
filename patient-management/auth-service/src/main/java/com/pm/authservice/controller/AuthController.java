package com.pm.authservice.controller;

import com.pm.authservice.AuthServiceApplication;
import com.pm.authservice.dto.LoginRequestDTO;
import com.pm.authservice.dto.LoginResponseDTO;
import com.pm.authservice.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class AuthController {


    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(summary = "Generate token on user login")
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO) {

        Optional<String> tokenOptional = authService.authenticate(loginRequestDTO); //optional means it would be either empty or String which will be given by authenticate method

        if(tokenOptional.isEmpty()) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String token = tokenOptional.get();
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    //any requests that we get to validate endpoint spring is going to get the authorization headers from the request headers
    //And pass that headers to us as a variable called authHeader which is going to type string
    //RFC standards and convention that is used for handling the access to tokens is to send them in authorization
    @Operation(summary = "Validate Token")
    @GetMapping("/validate")
    public ResponseEntity<Void> validateToken(@RequestHeader("Authorization") String authHeader) {
        //Authorization : Bearer <token>  is the standard that we use so that all the different systems around the world can understand
        //where the access token is coming from in a given request and how to access it
        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        //why beginIndex = 7 because Authorization: Bearer <token> starts at index 7 and will validate only token and this method will only return the boolean result
        return authService.validateToken(authHeader.substring(7))
                ? ResponseEntity.ok().build() //if true
                : ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();  //if false

    }
}
