package com.GestionCommande.Controller;

import com.GestionCommande.DTO.LoginRequest;
import com.GestionCommande.Entity.User;
import com.GestionCommande.Service.JwtService;
import com.GestionCommande.Service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/auth")
public class AuthController {
    private final JwtService jwtService;
    private final AuthenticationManager manager;

    private final UserService service;

    public AuthController(JwtService jwtService, AuthenticationManager manager, UserService service) {
        this.jwtService = jwtService;
        this.manager = manager;
        this.service = service;
    }

    @PostMapping("/register")
    public User register(@RequestBody LoginRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        return service.createUser(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request)
    {
        manager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        return jwtService.generateToken(request.getUsername());

    }
}
