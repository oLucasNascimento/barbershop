package br.com.barbermanager.barbershopmanagement.api.controller;

import br.com.barbermanager.barbershopmanagement.config.security.TokenService;
import br.com.barbermanager.barbershopmanagement.domain.model.user.AuthenticationDTO;
import br.com.barbermanager.barbershopmanagement.domain.model.user.LoginResponseDTO;
import br.com.barbermanager.barbershopmanagement.domain.model.user.RegisterDTO;
import br.com.barbermanager.barbershopmanagement.domain.model.user.User;
import br.com.barbermanager.barbershopmanagement.domain.repository.UserRepository;
import br.com.barbermanager.barbershopmanagement.domain.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    @Operation(summary = "Realizar Login", description = "Realizar login para usar os Endpoints", tags = "Login")

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data) {
        var token = tokenService.generateToken((User) this.userService.login(data).getPrincipal());
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

//    @PostMapping("/register")
//    public ResponseEntity register(@RequestBody @Valid RegisterDTO data) {
//        boolean auth = this.userService.register(data);
//        if (auth) return ResponseEntity.ok().build();
//        else return ResponseEntity.badRequest().build();
//    }
}
